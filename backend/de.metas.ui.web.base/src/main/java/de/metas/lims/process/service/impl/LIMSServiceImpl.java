/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.lims.process.service.impl;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.listener.TableAttachmentListenerService;
import de.metas.lims.process.model.ResultModel;
import de.metas.lims.process.service.LIMSService;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.events.DocumentWebsocketPublisher;
import de.metas.util.Services;
import lombok.extern.slf4j.Slf4j;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LIMSServiceImpl implements LIMSService
{
	private final IProductBOMDAO productBOMDAO = Services.get(IProductBOMDAO.class);
	private final IProductDAO iProductDAO = Services.get(IProductDAO.class);

	private UserSession userSession;
	private DocumentDescriptorFactory documentDescriptorFactory;
	private DocumentWebsocketPublisher websocketPublisher;
	private AttachmentEntryService attachmentEntryService;
	private TableAttachmentListenerService tableAttachmentListenerService;

	public LIMSServiceImpl(final UserSession userSession,
			final DocumentDescriptorFactory documentDescriptorFactory,
			final DocumentWebsocketPublisher websocketPublisher,
			final AttachmentEntryService attachmentEntryService,
			final TableAttachmentListenerService tableAttachmentListenerService)
	{
		this.userSession = userSession;
		this.documentDescriptorFactory = documentDescriptorFactory;
		this.websocketPublisher = websocketPublisher;
		this.attachmentEntryService = attachmentEntryService;
		this.tableAttachmentListenerService = tableAttachmentListenerService;
	}


	@Override
	public void updateResult(final String bomCode, final List<ResultModel> resultModels)
	{
		userSession.assertLoggedIn();
		I_PP_Product_BOM i_pp_product_bom = retrieveBOM(bomCode);

		Map<Integer, Integer> bomLinesMap = retrieveBOMLines(retrieveBOM(bomCode))
				.stream()
						.collect(Collectors.toMap(I_PP_Product_BOMLine::getM_Product_ID, I_PP_Product_BOMLine::getPP_Product_BOMLine_ID));
		resultModels.forEach(r -> {
			ProductId productId = iProductDAO.getProductIdByBarcode(r.getGrnNo(), ClientId.ofRepoId(Env.getAD_Client_ID()))
							.orElseThrow(() -> new AdempiereException("Product with barcode number " + r.getGrnNo() + " is not found"));
			createPdf(r, i_pp_product_bom);
			updateBOMLineStatus(bomLinesMap.get(productId.getRepoId()), r.getStatus());
		});
	}

	@Override
	public List<I_PP_Product_BOMLine> retrieveBOMLines(final I_PP_Product_BOM i_pp_product_bom)
	{
		return  productBOMDAO.retrieveLines(i_pp_product_bom);
	}

	@Override
	public I_PP_Product_BOM retrieveBOM(final String code)
	{
		ProductBOMId productBOMId = ProductBOMId.ofRepoId(Integer.valueOf(code));
		return productBOMDAO.getById(productBOMId);
	}

	@Override
	public void updateLIMSSyncStatus(final int bomId)
	{
		String sql = "UPDATE " + I_PP_Product_BOMLine.Table_Name + " SET isasync = 'Y' WHERE  " + I_PP_Product_BOMLine.COLUMNNAME_PP_Product_BOM_ID + " =  " + bomId;
		try
		{
			DB.prepareStatement(sql, "").execute();
		}
		catch (SQLException e)
		{
			throw new AdempiereException("Error during update sync status. error : " + e.getMessage());
		}
	}

	private void createPdf(final ResultModel resultModel, final I_PP_Product_BOM i_pp_product_bom) {
		byte[] decoder = Base64.getDecoder().decode(resultModel.getReport());
		AttachmentEntry entry = attachmentEntryService.createNewAttachment(i_pp_product_bom, resultModel.getGrnNo() + ".pdf", decoder);
	}


	private void updateBOMLineStatus(final int id, final String status) {
		String sql = "UPDATE " + I_PP_Product_BOMLine.Table_Name + " SET sample_result = ' " + status + " ' WHERE  " + I_PP_Product_BOMLine.COLUMNNAME_PP_Product_BOMLine_ID + " =  " + id;
		try
		{
			DB.prepareStatement(sql, "").execute();
		}
		catch (SQLException e)
		{
			throw new AdempiereException("Error during update sync status. error : " + e.getMessage());
		}
	}


}
