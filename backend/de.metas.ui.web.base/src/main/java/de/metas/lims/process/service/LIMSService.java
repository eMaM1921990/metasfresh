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

package de.metas.lims.process.service;

import de.metas.lims.process.model.ResultModel;
import de.metas.util.ISingletonService;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import java.util.List;

public interface LIMSService extends ISingletonService
{
	void updateResult(String bomCode, List<ResultModel> resultModels);
	List<I_PP_Product_BOMLine> retrieveBOMLines(I_PP_Product_BOM i_pp_product_bom);
	I_PP_Product_BOM retrieveBOM(String code);
	void updateLIMSSyncStatus(int bomId);
}
