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

package de.metas.lims.process.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResultModel
{
	@NotBlank(message = "GRN_NO_REQUIRED")
	private String grnNo;
	@NotBlank(message = "GRN_NO_REQUIRED")
	private String report;
	@NotBlank(message = "STATUS_REQUIRED")
	private String status;
}
