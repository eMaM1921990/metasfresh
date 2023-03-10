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
import de.metas.camel.externalsystems.ebay.api.model.Amount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * This type contains information about a sales promotion that is applied to a line item.
 */
@ApiModel(description = "This type contains information about a sales promotion that is applied to a line item.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-07-20T14:45:55.981728+02:00[Europe/Berlin]")
public class AppliedPromotion
{
	public static final String SERIALIZED_NAME_DESCRIPTION = "description";
	@SerializedName(SERIALIZED_NAME_DESCRIPTION)
	private String description;

	public static final String SERIALIZED_NAME_DISCOUNT_AMOUNT = "discountAmount";
	@SerializedName(SERIALIZED_NAME_DISCOUNT_AMOUNT)
	private Amount discountAmount;

	public static final String SERIALIZED_NAME_PROMOTION_ID = "promotionId";
	@SerializedName(SERIALIZED_NAME_PROMOTION_ID)
	private String promotionId;

	public AppliedPromotion description(String description)
	{

		this.description = description;
		return this;
	}

	/**
	 * A description of the applied sales promotion.
	 * 
	 * @return description
	 **/
	@javax.annotation.Nullable
	@ApiModelProperty(value = "A description of the applied sales promotion.")

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public AppliedPromotion discountAmount(Amount discountAmount)
	{

		this.discountAmount = discountAmount;
		return this;
	}

	/**
	 * Get discountAmount
	 * 
	 * @return discountAmount
	 **/
	@javax.annotation.Nullable
	@ApiModelProperty(value = "")

	public Amount getDiscountAmount()
	{
		return discountAmount;
	}

	public void setDiscountAmount(Amount discountAmount)
	{
		this.discountAmount = discountAmount;
	}

	public AppliedPromotion promotionId(String promotionId)
	{

		this.promotionId = promotionId;
		return this;
	}

	/**
	 * An eBay-generated unique identifier of the sales promotion. Multiple types of sales promotions are available to eBay Store owners, including order size/volume discounts, shipping discounts, special coupons, and price markdowns. Sales promotions can be managed through the Marketing tab of Seller Hub in My eBay, or by using the Trading API&#39;s SetPromotionalSale call or the Marketing
	 * API&#39;s createItemPromotion method.
	 * 
	 * @return promotionId
	 **/
	@javax.annotation.Nullable
	@ApiModelProperty(value = "An eBay-generated unique identifier of the sales promotion. Multiple types of sales promotions are available to eBay Store owners, including order size/volume discounts, shipping discounts, special coupons, and price markdowns. Sales promotions can be managed through the Marketing tab of Seller Hub in My eBay, or by using the Trading API's SetPromotionalSale call or the Marketing API's createItemPromotion method.")

	public String getPromotionId()
	{
		return promotionId;
	}

	public void setPromotionId(String promotionId)
	{
		this.promotionId = promotionId;
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
		AppliedPromotion appliedPromotion = (AppliedPromotion)o;
		return Objects.equals(this.description, appliedPromotion.description) &&
				Objects.equals(this.discountAmount, appliedPromotion.discountAmount) &&
				Objects.equals(this.promotionId, appliedPromotion.promotionId);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(description, discountAmount, promotionId);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("class AppliedPromotion {\n");
		sb.append("    description: ").append(toIndentedString(description)).append("\n");
		sb.append("    discountAmount: ").append(toIndentedString(discountAmount)).append("\n");
		sb.append("    promotionId: ").append(toIndentedString(promotionId)).append("\n");
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
