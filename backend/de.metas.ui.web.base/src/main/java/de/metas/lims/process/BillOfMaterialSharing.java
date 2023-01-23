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

package de.metas.lims.process;

import de.metas.lims.process.model.LIMSModel;
import de.metas.lims.process.model.LIMSSampleModel;
import de.metas.lims.process.service.LIMSIntegrationClient;
import de.metas.lims.process.service.LIMSService;
import de.metas.process.JavaProcess;
import de.metas.product.IProductDAO;
import de.metas.util.Services;
import lombok.extern.slf4j.Slf4j;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
public class BillOfMaterialSharing extends JavaProcess
{
	private final IProductBOMDAO productBOMDAO = Services.get(IProductBOMDAO.class);
	private final IProductDAO iProductDAO = Services.get(IProductDAO.class);
	private final LIMSIntegrationClient limsIntegrationClient = Services.get(LIMSIntegrationClient.class);
	private final LIMSService limsService = Services.get(LIMSService.class);

	@Override
	protected String doIt() throws Exception
	{
		I_PP_Product_BOM i_pp_product_bom = limsService.retrieveBOM(""+getRecord_ID());
		List<LIMSSampleModel> i_pp_product_bomLines = retrieveProductBOMLine(limsService.retrieveBOM(""+getRecord_ID()));
		LIMSModel limsModel = buildLIMSModel(i_pp_product_bomLines, i_pp_product_bom.getPP_Product_BOM_ID(), i_pp_product_bom.getM_Product_ID());
		limsIntegrationClient.sendSamples(limsModel);
		limsService.updateLIMSSyncStatus(getRecord_ID());
		return MSG_OK;
	}

	private List<LIMSSampleModel> retrieveProductBOMLine(I_PP_Product_BOM i_pp_product_bom) {
		List<I_PP_Product_BOMLine> i_pp_product_bomLines = limsService.retrieveBOMLines(i_pp_product_bom);
		return i_pp_product_bomLines.stream()
						.map(i_pp_product_bomLine -> LIMSSampleModel.builder()
							   .sampleName("Sample1")
							   .grnNo(
									   Optional.ofNullable(iProductDAO.getById(i_pp_product_bomLine.getM_Product_ID()).getSKU())
											   .orElse(""+i_pp_product_bomLine.getM_Product_ID()))
							   .quarantineLabel("Demo")
							   .build())
						.collect(Collectors.toList());
	}


	private LIMSModel buildLIMSModel(final List<LIMSSampleModel> limsSampleModels, final int bomId, final int productId) {
		return LIMSModel.builder()
				.bomCode("" + bomId)
				.productCode( Optional.ofNullable(iProductDAO.getById(productId).getSKU())
									  .orElse(""+productId))
				.sampleList(limsSampleModels)
				.build();
	}

}
