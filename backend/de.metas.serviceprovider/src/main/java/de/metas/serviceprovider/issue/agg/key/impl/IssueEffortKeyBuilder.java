/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.issue.agg.key.impl;

import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.util.agg.key.AbstractHeaderAggregationKeyBuilder;
import org.adempiere.util.lang.ObjectUtils;

/**
 * Responsible for building an aggregation key from a given {@link I_S_Issue}.
 */
public class IssueEffortKeyBuilder extends AbstractHeaderAggregationKeyBuilder<I_S_Issue>
{
	public static final String REGISTRATION_KEY = I_S_Issue.Table_Name;

	public IssueEffortKeyBuilder()
	{
		super(REGISTRATION_KEY);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
