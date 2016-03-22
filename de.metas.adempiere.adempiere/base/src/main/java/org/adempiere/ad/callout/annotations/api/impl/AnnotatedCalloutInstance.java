package org.adempiere.ad.callout.annotations.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.callout.annotations.api.ICalloutMethodPointcut;
import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.exceptions.CalloutExecutionException;
import org.adempiere.util.Check;

public final class AnnotatedCalloutInstance implements ICalloutInstance
{
	private static final transient Logger logger = LogManager.getLogger(AnnotatedCalloutInstance.class);

	private final String id;
	private final Object annotatedObject;
	private final String tableName;
	private final Set<String> columnNames = new HashSet<String>();
	private final Set<String> columnNamesRO = Collections.unmodifiableSet(columnNames);

	private final transient Map<CalloutMethodPointcutKey, List<CalloutMethodPointcut>> mapPointcuts;

	/* package */AnnotatedCalloutInstance(final String id,
			final String tableName,
			final Collection<String> columnNames,
			final Object annotatedObject,
			final Map<CalloutMethodPointcutKey, List<CalloutMethodPointcut>> mapPointcuts)
	{
		super();

		Check.assumeNotEmpty(id, "id not empty");
		this.id = id;

		Check.assumeNotEmpty(tableName, "tableName not empty");
		this.tableName = tableName;

		Check.assumeNotEmpty(columnNames, "columnNames not empty");
		this.columnNames.addAll(columnNames);

		Check.assumeNotNull(annotatedObject, "annotatedObject not null");
		this.annotatedObject = annotatedObject;

		Check.assumeNotEmpty(mapPointcuts, "mapPointcuts not empty");
		this.mapPointcuts = mapPointcuts;
	}

	private CalloutMethodPointcutKey mkKey(final String columnName)
	{
		final CalloutMethodPointcutKey key = new CalloutMethodPointcutKey(columnName);
		return key;
	}

	public String getTableName()
	{
		return tableName;
	}

	/**
	 * Gets a set of column names on which this callout instance is listening
	 * 
	 * @return set of column names
	 */
	public Set<String> getColumnNames()
	{
		return columnNamesRO;
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public void execute(final ICalloutExecutor executor, final ICalloutField field)
	{
		final String columnName = field.getColumnName();
		final CalloutMethodPointcutKey pointcutKey = mkKey(columnName);
		final List<CalloutMethodPointcut> pointcutsForKey = mapPointcuts.get(pointcutKey);
		if (pointcutsForKey.isEmpty())
		{
			return;
		}

		for (final ICalloutMethodPointcut pointcut : pointcutsForKey)
		{
			executePointcut(pointcut, executor, field);
		}
	}

	private void executePointcut(final ICalloutMethodPointcut pointcut, final ICalloutExecutor executor, final ICalloutField field)
	{
		// Skip executing this callout if we were asked to skip when record copying mode is active
		if (pointcut.isSkipIfCopying() && field.isRecordCopyingMode())
		{
			logger.info("Skip invoking callout because we are in copying mode: {}", pointcut.getMethod());
			return;
		}

		final Method method = pointcut.getMethod();
		final Object model = field.getModel(pointcut.getModelClass());
		try
		{
			// make sure the method can be executed
			if (!method.isAccessible())
			{
				method.setAccessible(true);
			}

			if (pointcut.isParamCalloutFieldRequired())
			{
				method.invoke(annotatedObject, model, field);
			}
			else
			{
				method.invoke(annotatedObject, model);
			}
		}
		catch (final CalloutExecutionException e)
		{
			throw e;
		}
		catch (final Exception e)
		{
			throw CalloutExecutionException.of(e)
					.setCalloutExecutor(executor)
					.setCalloutInstance(this)
					.setField(field);
		}
	}
}
