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

package de.metas.ui.web.order.products_proposal.process;

import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRow;
import de.metas.ui.web.order.products_proposal.view.ProductsProposalView;
import de.metas.ui.web.order.products_proposal.view.QuotationHistoryProductsProposalViewFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class WEBUI_ProductsProposal_QuotationHistory extends ProductsProposalViewBasedProcess
{
	@Autowired
	private QuotationHistoryProductsProposalViewFactory quotationHistoryProductsProposalViewFactory;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("nothing selected");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ProductsProposalView view = getView();
		final List<ProductsProposalRow> selectedRows = getSelectedRows();

		final ProductsProposalView quotationHistoryView = quotationHistoryProductsProposalViewFactory.createView(view, selectedRows);
		afterCloseOpenView(quotationHistoryView.getViewId());

		return MSG_OK;
	}
}
