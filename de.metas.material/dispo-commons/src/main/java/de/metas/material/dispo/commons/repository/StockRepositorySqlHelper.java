package de.metas.material.dispo.commons.repository;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import de.metas.logging.LogManager;
import de.metas.material.dispo.model.I_MD_Candidate_Stock_v;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.StorageAttributesKey;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2017 metas GmbH
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

@UtilityClass
/* package */ final class StockRepositorySqlHelper
{
	private static final Logger logger = LogManager.getLogger(StockRepositorySqlHelper.class);

	@VisibleForTesting
	public static IQueryBuilder<I_MD_Candidate_Stock_v> createDBQueryForMaterialQuery(@NonNull final MaterialQuery query)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_MD_Candidate_Stock_v> queryBuilder = queryBL.createQueryBuilder(I_MD_Candidate_Stock_v.class);

		//
		// Date
		queryBuilder.addEqualsFilter(I_MD_Candidate_Stock_v.COLUMN_DateProjected, query.getDate());

		//
		// Warehouse
		final Set<Integer> warehouseIds = query.getWarehouseIds();
		if (!warehouseIds.isEmpty())
		{
			queryBuilder.addInArrayFilter(I_MD_Candidate_Stock_v.COLUMN_M_Warehouse_ID, warehouseIds);
		}

		//
		// Product
		final List<Integer> productIds = query.getProductIds();
		if (!productIds.isEmpty())
		{
			queryBuilder.addInArrayFilter(I_MD_Candidate_Stock_v.COLUMN_M_Product_ID, productIds);
		}

		//
		// BPartner
		// FIXME fix/uncomment the code on https://github.com/metasfresh/metasfresh/issues/3098
		final int bpartnerId = query.getBpartnerId();
		if (bpartnerId == MaterialQuery.BPARTNER_ID_ANY)
		{
			// nothing to filter
		}
		else if (bpartnerId == MaterialQuery.BPARTNER_ID_NONE)
		{
			// queryBuilder.addEqualsFilter(I_MD_Candidate_Stock_v.COLUMN_C_BPartner_ID, null);
			logger.warn("Ignoring BPartnerId from query because it's not implemented yet: {}", query);
		}
		else
		{
			// queryBuilder.addEqualsFilter(I_MD_Candidate_Stock_v.COLUMN_C_BPartner_ID, bpartnerId);
			logger.warn("Ignoring BPartnerId from query because it's not implemented yet: {}", query);
		}

		//
		// Storage Attributes Key
		queryBuilder.filter(createANDFilterForStorageAttributesKeys(query));
		
		//
		return queryBuilder;
	}

	private static ICompositeQueryFilter<I_MD_Candidate_Stock_v> createANDFilterForStorageAttributesKeys(@NonNull final MaterialQuery query)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryFilter<I_MD_Candidate_Stock_v> orFilterForDifferentStorageAttributesKeys = queryBL
				.createCompositeQueryFilter(I_MD_Candidate_Stock_v.class)
				.setJoinOr();

		for (final StorageAttributesKey storageAttributesKey : query.getStorageAttributesKeys())
		{
			final IQueryFilter<I_MD_Candidate_Stock_v> andFilterForCurrentStorageAttributesKey = createANDFilterForStorageAttributesKey(query, storageAttributesKey);
			orFilterForDifferentStorageAttributesKeys.addFilter(andFilterForCurrentStorageAttributesKey);
		}
		
		return orFilterForDifferentStorageAttributesKeys;
	}

	private static ICompositeQueryFilter<I_MD_Candidate_Stock_v> createANDFilterForStorageAttributesKey(
			@NonNull final MaterialQuery query,
			@NonNull final StorageAttributesKey storageAttributesKey)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryFilter<I_MD_Candidate_Stock_v> filterForCurrentStorageAttributesKey = queryBL.createCompositeQueryFilter(I_MD_Candidate_Stock_v.class)
				.setJoinAnd();

		if (Objects.equals(storageAttributesKey, ProductDescriptor.STORAGE_ATTRIBUTES_KEY_OTHER))
		{
			addNotLikeFiltersForAttributesKeys(filterForCurrentStorageAttributesKey, query.getStorageAttributesKeys());
		}
		else if (Objects.equals(storageAttributesKey, ProductDescriptor.STORAGE_ATTRIBUTES_KEY_ALL))
		{
			// nothing to add to the initial productIds filters
		}
		else
		{
			addLikeFilterForAttributesKey(storageAttributesKey, filterForCurrentStorageAttributesKey);
		}

		return filterForCurrentStorageAttributesKey;
	}

	private static void addNotLikeFiltersForAttributesKeys(
			@NonNull final ICompositeQueryFilter<I_MD_Candidate_Stock_v> compositeFilter,
			@NonNull final List<StorageAttributesKey> attributesKeys)
	{
		for (final StorageAttributesKey storageAttributesKeyAgain : attributesKeys)
		{
			if (!Objects.equals(storageAttributesKeyAgain, ProductDescriptor.STORAGE_ATTRIBUTES_KEY_OTHER))
			{
				final String likeExpression = createLikeExpression(storageAttributesKeyAgain);
				compositeFilter.addStringNotLikeFilter(I_MD_Candidate_Stock_v.COLUMN_StorageAttributesKey, likeExpression, false);
			}
		}
	}

	private static void addLikeFilterForAttributesKey(final StorageAttributesKey storageAttributesKey, final ICompositeQueryFilter<I_MD_Candidate_Stock_v> andFilterForCurrentStorageAttributesKey)
	{
		final String likeExpression = createLikeExpression(storageAttributesKey);
		andFilterForCurrentStorageAttributesKey.addStringLikeFilter(I_MD_Candidate_Stock_v.COLUMN_StorageAttributesKey, likeExpression, false);
	}

	private static String createLikeExpression(@NonNull final StorageAttributesKey storageAttributesKey)
	{
		final String storageAttributesKeyLikeExpression = storageAttributesKey.getSqlLikeString();
		return "%" + storageAttributesKeyLikeExpression + "%";
	}

}
