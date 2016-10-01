package de.metas.ui.web.window.descriptor;

import java.util.Collection;
import java.util.NoSuchElementException;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

public interface DocumentQueryFilterDescriptorsProvider
{
	Collection<DocumentQueryFilterDescriptor> getAll();

	DocumentQueryFilterDescriptor getByFilterIdOrNull(final String filterId);

	default DocumentQueryFilterDescriptor getByFilterId(final String filterId) throws NoSuchElementException
	{
		final DocumentQueryFilterDescriptor filterDescriptor = getByFilterIdOrNull(filterId);
		if (filterDescriptor == null)
		{
			throw new NoSuchElementException("Filter '" + filterId + "' was not found in " + this);
		}
		return filterDescriptor;
	}
}
