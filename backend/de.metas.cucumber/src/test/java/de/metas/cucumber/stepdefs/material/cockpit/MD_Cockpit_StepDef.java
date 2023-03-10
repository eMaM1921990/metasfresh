/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.material.cockpit;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeSetInstance;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class MD_Cockpit_StepDef
{
	private final static Logger logger = LogManager.getLogger(MD_Cockpit_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Product_StepDefData productTable;
	private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;

	public MD_Cockpit_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_AttributeSetInstance_StepDefData attributeSetInstanceTable)
	{
		this.productTable = productTable;
		this.attributeSetInstanceTable = attributeSetInstanceTable;
	}

	@And("^after not more than (.*)s, metasfresh has this MD_Cockpit data$")
	public void metasfresh_has_this_md_cockpit_data(
			final int timeoutSec,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateCockpitRecord(timeoutSec, tableRow);
		}
	}

	private void validateCockpitRecord(
			final int timeoutSec,
			@NonNull final Map<String, String> tableRow) throws InterruptedException
	{
		final String identifier = DataTableUtil.extractRecordIdentifier(tableRow, I_MD_Cockpit.Table_Name);
		final ExpectedResults expectedResults = buildExpectedResults(tableRow);

		final I_MD_Cockpit mdCockpitRecord = getCockpitByProductIdAttributesKeyAndDateGeneral(timeoutSec, expectedResults);

		final ItemProvider<I_MD_Cockpit> getValidCockpit = () -> {
			InterfaceWrapperHelper.refresh(mdCockpitRecord);

			return validateCockpitRecord(identifier, expectedResults, mdCockpitRecord);
		};

		StepDefUtil.tryAndWaitForItem(timeoutSec, 500, getValidCockpit, () -> logCurrentContext(expectedResults));
	}

	@NonNull
	private ExpectedResults buildExpectedResults(@NonNull final Map<String, String> tableRow)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_MD_Cockpit.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final ProductId productId = ProductId.ofRepoId(productTable.get(productIdentifier).getM_Product_ID());

		final LocalDate dateGeneral = DataTableUtil.extractLocalDateForColumnName(tableRow, I_MD_Cockpit.COLUMNNAME_DateGeneral);
		final BigDecimal qtyDemandSum = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtyDemandSum_AtDate);
		final BigDecimal qtyDemandSalesOrder = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtyDemand_SalesOrder_AtDate);
		final BigDecimal qtyStockCurrent = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtyStockCurrent_AtDate);
		final BigDecimal qtyExpectedSurplus = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtyExpectedSurplus_AtDate);
		final BigDecimal qtyInventoryCount = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtyInventoryCount_AtDate);
		final BigDecimal qtySupplyPurchaseOrder = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtySupply_PurchaseOrder_AtDate);
		final BigDecimal qtySupplySum = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_QtySupplySum_AtDate);

		final String asiIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit.COLUMNNAME_AttributesKey + "." + TABLECOLUMN_IDENTIFIER);
		final AttributesKey attributeStorageKey = getAttributesKey(asiIdentifier);

		return ExpectedResults.builder()
				.productId(productId)
				.storageAttributesKey(attributeStorageKey)
				.dateGeneral(dateGeneral)
				.qtyDemandSumAtDate(qtyDemandSum)
				.qtyDemandSalesOrderAtDate(qtyDemandSalesOrder)
				.qtyStockCurrentAtDate(qtyStockCurrent)
				.qtyExpectedSurplusAtDate(qtyExpectedSurplus)
				.qtyInventoryCountAtDate(qtyInventoryCount)
				.qtySupplyPurchaseOrderAtDate(qtySupplyPurchaseOrder)
				.qtySupplySumAtDate(qtySupplySum)
				.build();
	}

	@NonNull
	private AttributesKey getAttributesKey(@Nullable final String asiIdentifier)
	{
		if (Check.isNotBlank(asiIdentifier))
		{
			final I_M_AttributeSetInstance attributeSetInstance = attributeSetInstanceTable.get(asiIdentifier);

			final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(attributeSetInstance.getM_AttributeSetInstance_ID());
			return AttributesKeys
					.createAttributesKeyFromASIStorageAttributes(asiId)
					.orElse(AttributesKey.NONE);
		}
		else
		{
			return AttributesKey.NONE;
		}
	}

	@NonNull
	private ItemProvider.ProviderResult<I_MD_Cockpit> validateCockpitRecord(
			@NonNull final String cockpitIdentifier,
			@NonNull final ExpectedResults expectedResults,
			@NonNull final I_MD_Cockpit cockpitRecord)
	{
		final List<String> errorCollector = new ArrayList<>();

		final BigDecimal qtyDemandSumAtDate = expectedResults.getQtyDemandSumAtDate();
		if (qtyDemandSumAtDate != null && !cockpitRecord.getQtyDemandSum_AtDate().equals(qtyDemandSumAtDate))
		{
			errorCollector.add(MessageFormat.format("MD_Cockpit.Identifier={0}; Expecting QtyDemandSumAtDate={1} but actual is {2}",
													cockpitIdentifier, qtyDemandSumAtDate, cockpitRecord.getQtyDemandSum_AtDate()));
		}

		final BigDecimal qtyDemandSalesOrderAtDate = expectedResults.getQtyDemandSalesOrderAtDate();
		if (qtyDemandSalesOrderAtDate != null && !cockpitRecord.getQtyDemand_SalesOrder_AtDate().equals(qtyDemandSalesOrderAtDate))
		{
			errorCollector.add(MessageFormat.format("MD_Cockpit.Identifier={0}; Expecting QtyDemandSalesOrderAtDate={1} but actual is {2}",
													cockpitIdentifier, qtyDemandSalesOrderAtDate, cockpitRecord.getQtyDemand_SalesOrder_AtDate()));
		}

		final BigDecimal qtyStockCurrentAtDate = expectedResults.getQtyStockCurrentAtDate();
		if (qtyStockCurrentAtDate != null && !cockpitRecord.getQtyStockCurrent_AtDate().equals(qtyStockCurrentAtDate))
		{
			errorCollector.add(MessageFormat.format("MD_Cockpit.Identifier={0}; Expecting QtyStockCurrentAtDate={1} but actual is {2}",
													cockpitIdentifier, qtyStockCurrentAtDate, cockpitRecord.getQtyStockCurrent_AtDate()));
		}

		final BigDecimal qtyExpectedSurplusAtDate = expectedResults.getQtyExpectedSurplusAtDate();
		if (qtyExpectedSurplusAtDate != null && !cockpitRecord.getQtyExpectedSurplus_AtDate().equals(qtyExpectedSurplusAtDate))
		{
			errorCollector.add(MessageFormat.format("MD_Cockpit.Identifier={0}; Expecting QtyExpectedSurplusAtDate={1} but actual is {2}",
													cockpitIdentifier, qtyExpectedSurplusAtDate, cockpitRecord.getQtyExpectedSurplus_AtDate()));
		}

		final BigDecimal qtyInventoryCountAtDate = expectedResults.getQtyInventoryCountAtDate();
		if (qtyInventoryCountAtDate != null && !cockpitRecord.getQtyInventoryCount_AtDate().equals(qtyInventoryCountAtDate))
		{
			errorCollector.add(MessageFormat.format("MD_Cockpit.Identifier={0}; Expecting QtyInventoryCountAtDate={1} but actual is {2}",
													cockpitIdentifier, qtyInventoryCountAtDate, cockpitRecord.getQtyInventoryCount_AtDate()));
		}

		final BigDecimal qtySupplyPurchaseOrderAtDate = expectedResults.getQtySupplyPurchaseOrderAtDate();
		if (qtySupplyPurchaseOrderAtDate != null && !cockpitRecord.getQtySupply_PurchaseOrder_AtDate().equals(qtySupplyPurchaseOrderAtDate))
		{
			errorCollector.add(MessageFormat.format("MD_Cockpit.Identifier={0}; Expecting QtySupplyPurchaseOrderAtDate={1} but actual is {2}",
													cockpitIdentifier, qtySupplyPurchaseOrderAtDate, cockpitRecord.getQtySupply_PurchaseOrder_AtDate()));
		}

		final BigDecimal qtySupplySumAtDate = expectedResults.getQtySupplySumAtDate();
		if (qtySupplySumAtDate != null && !cockpitRecord.getQtySupplySum_AtDate().equals(qtySupplySumAtDate))
		{
			errorCollector.add(MessageFormat.format("MD_Cockpit.Identifier={0}; Expecting QtySupplySumAtDate={1} but actual is {2}",
													cockpitIdentifier, qtySupplySumAtDate, cockpitRecord.getQtySupplySum_AtDate()));
		}

		if (errorCollector.size() > 0)
		{
			final String errorMessages = String.join(" && \n", errorCollector);
			return ItemProvider.ProviderResult.resultWasNotFound(errorMessages);
		}

		return ItemProvider.ProviderResult.resultWasFound(cockpitRecord);
	}

	@NonNull
	private I_MD_Cockpit getCockpitByProductIdAttributesKeyAndDateGeneral(
			final int timeoutSec,
			@NonNull final ExpectedResults expectedResults) throws InterruptedException
	{
		final Supplier<Optional<I_MD_Cockpit>> mdCockpitIsFound = () -> queryBL.createQueryBuilder(I_MD_Cockpit.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Cockpit.COLUMNNAME_DateGeneral, expectedResults.getDateGeneral(), DateTruncQueryFilterModifier.DAY)
				.addEqualsFilter(I_MD_Cockpit.COLUMNNAME_M_Product_ID, expectedResults.getProductId())
				.addEqualsFilter(I_MD_Cockpit.COLUMNNAME_AttributesKey, expectedResults.getStorageAttributesKey().getAsString())
				.create()
				.firstOnlyOptional(I_MD_Cockpit.class);

		return StepDefUtil.tryAndWaitForItem(timeoutSec, 500, mdCockpitIsFound, () -> logCurrentContext(expectedResults));
	}

	private void logCurrentContext(@NonNull final ExpectedResults expectedResults)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Looking for instance with:").append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_M_Product_ID).append(" : ").append(expectedResults.getProductId()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_AttributesKey).append(" : ").append(expectedResults.getStorageAttributesKey().getAsString()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_DateGeneral).append(" : ").append(expectedResults.getDateGeneral()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtyDemandSum_AtDate).append(" : ").append(expectedResults.getQtyDemandSumAtDate()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtyDemand_SalesOrder_AtDate).append(" : ").append(expectedResults.getQtyDemandSalesOrderAtDate()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtyStockCurrent_AtDate).append(" : ").append(expectedResults.getQtyStockCurrentAtDate()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtyExpectedSurplus_AtDate).append(" : ").append(expectedResults.getQtyExpectedSurplusAtDate()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtyInventoryCount_AtDate).append(" : ").append(expectedResults.getQtyInventoryCountAtDate()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtySupply_PurchaseOrder_AtDate).append(" : ").append(expectedResults.getQtySupplyPurchaseOrderAtDate()).append("\n")
				.append(I_MD_Cockpit.COLUMNNAME_QtySupplySum_AtDate).append(" : ").append(expectedResults.getQtySupplySumAtDate()).append("\n");

		message.append("MD_Cockpit records filtered by product:").append("\n");

		queryBL.createQueryBuilder(I_MD_Cockpit.class)
				.addEqualsFilter(I_MD_Cockpit.COLUMNNAME_M_Product_ID, expectedResults.getProductId())
				.create()
				.stream(I_MD_Cockpit.class)
				.forEach(cockpitEntry -> message
						.append(I_MD_Cockpit.COLUMNNAME_M_Product_ID).append(" : ").append(cockpitEntry.getM_Product_ID()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_M_Warehouse_ID).append(" : ").append(cockpitEntry.getM_Warehouse_ID()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_AttributesKey).append(" : ").append(cockpitEntry.getAttributesKey()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_DateGeneral).append(" : ").append(cockpitEntry.getDateGeneral()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_QtyDemand_SalesOrder_AtDate).append(" : ").append(cockpitEntry.getQtyDemand_SalesOrder_AtDate()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_QtyDemandSum_AtDate).append(" : ").append(cockpitEntry.getQtyDemandSum_AtDate()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_QtyStockCurrent_AtDate).append(" : ").append(cockpitEntry.getQtyStockCurrent_AtDate()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_QtyExpectedSurplus_AtDate).append(" : ").append(cockpitEntry.getQtyExpectedSurplus_AtDate()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_QtyInventoryCount_AtDate).append(" : ").append(cockpitEntry.getQtyInventoryCount_AtDate()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_QtySupply_PurchaseOrder_AtDate).append(" : ").append(cockpitEntry.getQtySupply_PurchaseOrder_AtDate()).append(" ; ")
						.append(I_MD_Cockpit.COLUMNNAME_QtySupplySum_AtDate).append(" : ").append(cockpitEntry.getQtySupplySum_AtDate()).append(" ; ")
						.append("\n"));

		logger.error("*** Error while looking for MD_Cockpit records, see current context: \n" + message);
	}

	@Builder
	@Value
	private static class ExpectedResults
	{
		@NonNull
		ProductId productId;

		@NonNull
		AttributesKey storageAttributesKey;

		@NonNull
		LocalDate dateGeneral;

		@Nullable
		BigDecimal qtyDemandSumAtDate;

		@Nullable
		BigDecimal qtyDemandSalesOrderAtDate;

		@Nullable
		BigDecimal qtyStockCurrentAtDate;

		@Nullable
		BigDecimal qtyExpectedSurplusAtDate;

		@Nullable
		BigDecimal qtyInventoryCountAtDate;

		@Nullable
		BigDecimal qtySupplyPurchaseOrderAtDate;

		@Nullable
		BigDecimal qtySupplySumAtDate;
	}
}
