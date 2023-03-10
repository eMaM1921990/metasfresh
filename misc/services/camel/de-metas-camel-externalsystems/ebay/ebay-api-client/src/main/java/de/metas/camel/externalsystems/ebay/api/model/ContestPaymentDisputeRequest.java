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
import de.metas.camel.externalsystems.ebay.api.model.ReturnAddress;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * This type is used by the request payload of the contestPaymentDispute method.
 */
@ApiModel(description = "This type is used by the request payload of the contestPaymentDispute method.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-07-20T14:45:55.981728+02:00[Europe/Berlin]")
public class ContestPaymentDisputeRequest
{
	public static final String SERIALIZED_NAME_RETURN_ADDRESS = "returnAddress";
	@SerializedName(SERIALIZED_NAME_RETURN_ADDRESS)
	private ReturnAddress returnAddress;

	public static final String SERIALIZED_NAME_REVISION = "revision";
	@SerializedName(SERIALIZED_NAME_REVISION)
	private Integer revision;

	public ContestPaymentDisputeRequest returnAddress(ReturnAddress returnAddress)
	{

		this.returnAddress = returnAddress;
		return this;
	}

	/**
	 * Get returnAddress
	 * 
	 * @return returnAddress
	 **/
	@javax.annotation.Nullable
	@ApiModelProperty(value = "")

	public ReturnAddress getReturnAddress()
	{
		return returnAddress;
	}

	public void setReturnAddress(ReturnAddress returnAddress)
	{
		this.returnAddress = returnAddress;
	}

	public ContestPaymentDisputeRequest revision(Integer revision)
	{

		this.revision = revision;
		return this;
	}

	/**
	 * This integer value indicates the revision number of the payment dispute. This field is required. The current revision number for a payment dispute can be retrieved with the getPaymentDispute method. Each time an action is taken against a payment dispute, this integer value increases by 1.
	 * 
	 * @return revision
	 **/
	@javax.annotation.Nullable
	@ApiModelProperty(value = "This integer value indicates the revision number of the payment dispute. This field is required. The current revision number for a payment dispute can be retrieved with the getPaymentDispute method. Each time an action is taken against a payment dispute, this integer value increases by 1.")

	public Integer getRevision()
	{
		return revision;
	}

	public void setRevision(Integer revision)
	{
		this.revision = revision;
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
		ContestPaymentDisputeRequest contestPaymentDisputeRequest = (ContestPaymentDisputeRequest)o;
		return Objects.equals(this.returnAddress, contestPaymentDisputeRequest.returnAddress) &&
				Objects.equals(this.revision, contestPaymentDisputeRequest.revision);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(returnAddress, revision);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("class ContestPaymentDisputeRequest {\n");
		sb.append("    returnAddress: ").append(toIndentedString(returnAddress)).append("\n");
		sb.append("    revision: ").append(toIndentedString(revision)).append("\n");
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
