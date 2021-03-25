package de.metas.common.manufacturing;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Value
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonDeserialize(builder = JsonRequestIssueToManufacturingOrder.JsonRequestIssueToManufacturingOrderBuilder.class)
public class JsonRequestIssueToManufacturingOrder
{
	@Nullable
	String requestId;

	@NonNull
	JsonMetasfreshId orderId;

	@NonNull
	BigDecimal qtyToIssueInStockUOM;

	@NonNull
	ZonedDateTime date;

	@NonNull
	String productNo;

	@NonNull
	@Singular
	List<JsonRequestHULookup> handlingUnits;

	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonRequestIssueToManufacturingOrderBuilder
	{
	}
}
