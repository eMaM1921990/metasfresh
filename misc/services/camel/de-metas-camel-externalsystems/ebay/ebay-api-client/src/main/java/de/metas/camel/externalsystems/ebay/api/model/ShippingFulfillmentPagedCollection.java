/*
 * Fulfillment API
 * Use the Fulfillment API to complete the process of packaging, addressing, handling, and shipping each order on behalf of the seller, in accordance with the payment method and timing specified at checkout.
 *
 * The version of the OpenAPI document: v1.19.7
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package de.metas.camel.externalsystems.ebay.api.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import de.metas.camel.externalsystems.ebay.api.model.Error;
import de.metas.camel.externalsystems.ebay.api.model.ShippingFulfillment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This type contains the specifications for the entire collection of shipping fulfillments that are associated with the order specified by a getShippingFulfillments call. The fulfillments container returns an array of all the fulfillments in the collection.
 */
@ApiModel(description = "This type contains the specifications for the entire collection of shipping fulfillments that are associated with the order specified by a getShippingFulfillments call. The fulfillments container returns an array of all the fulfillments in the collection.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-07-20T14:45:55.981728+02:00[Europe/Berlin]")
public class ShippingFulfillmentPagedCollection
{
	public static final String SERIALIZED_NAME_FULFILLMENTS = "fulfillments";
	@SerializedName(SERIALIZED_NAME_FULFILLMENTS)
	private List<ShippingFulfillment> fulfillments = null;

	public static final String SERIALIZED_NAME_TOTAL = "total";
	@SerializedName(SERIALIZED_NAME_TOTAL)
	private Integer total;

	public static final String SERIALIZED_NAME_WARNINGS = "warnings";
	@SerializedName(SERIALIZED_NAME_WARNINGS)
	private List<Error> warnings = null;

	public ShippingFulfillmentPagedCollection fulfillments(List<ShippingFulfillment> fulfillments)
	{

		this.fulfillments = fulfillments;
		return this;
	}

	public ShippingFulfillmentPagedCollection addFulfillmentsItem(ShippingFulfillment fulfillmentsItem)
	{
		if (this.fulfillments == null)
		{
			this.fulfillments = new ArrayList<>();
		}
		this.fulfillments.add(fulfillmentsItem);
		return this;
	}

	/**
	 * This array contains one or more fulfillments required for the order that was specified in method endpoint.
	 * 
	 * @return fulfillments
	 **/
	@javax.annotation.Nullable
	@ApiModelProperty(value = "This array contains one or more fulfillments required for the order that was specified in method endpoint.")

	public List<ShippingFulfillment> getFulfillments()
	{
		return fulfillments;
	}

	public void setFulfillments(List<ShippingFulfillment> fulfillments)
	{
		this.fulfillments = fulfillments;
	}

	public ShippingFulfillmentPagedCollection total(Integer total)
	{

		this.total = total;
		return this;
	}

	/**
	 * The total number of fulfillments in the specified order. Note: If no fulfillments are found for the order, this field is returned with a value of 0.
	 * 
	 * @return total
	 **/
	@javax.annotation.Nullable
	@ApiModelProperty(value = "The total number of fulfillments in the specified order. Note: If no fulfillments are found for the order, this field is returned with a value of 0.")

	public Integer getTotal()
	{
		return total;
	}

	public void setTotal(Integer total)
	{
		this.total = total;
	}

	public ShippingFulfillmentPagedCollection warnings(List<Error> warnings)
	{

		this.warnings = warnings;
		return this;
	}

	public ShippingFulfillmentPagedCollection addWarningsItem(Error warningsItem)
	{
		if (this.warnings == null)
		{
			this.warnings = new ArrayList<>();
		}
		this.warnings.add(warningsItem);
		return this;
	}

	/**
	 * This array is only returned if one or more errors or warnings occur with the call request.
	 * 
	 * @return warnings
	 **/
	@javax.annotation.Nullable
	@ApiModelProperty(value = "This array is only returned if one or more errors or warnings occur with the call request.")

	public List<Error> getWarnings()
	{
		return warnings;
	}

	public void setWarnings(List<Error> warnings)
	{
		this.warnings = warnings;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		ShippingFulfillmentPagedCollection shippingFulfillmentPagedCollection = (ShippingFulfillmentPagedCollection)o;
		return Objects.equals(this.fulfillments, shippingFulfillmentPagedCollection.fulfillments) &&
				Objects.equals(this.total, shippingFulfillmentPagedCollection.total) &&
				Objects.equals(this.warnings, shippingFulfillmentPagedCollection.warnings);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(fulfillments, total, warnings);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("class ShippingFulfillmentPagedCollection {\n");
		sb.append("    fulfillments: ").append(toIndentedString(fulfillments)).append("\n");
		sb.append("    total: ").append(toIndentedString(total)).append("\n");
		sb.append("    warnings: ").append(toIndentedString(warnings)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(Object o)
	{
		if (o == null)
		{
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

}
