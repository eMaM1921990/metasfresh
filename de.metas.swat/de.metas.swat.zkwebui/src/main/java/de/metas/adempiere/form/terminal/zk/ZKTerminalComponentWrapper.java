/**
 * 
 */
package de.metas.adempiere.form.terminal.zk;

/*
 * #%L
 * de.metas.swat.zkwebui
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


import org.slf4j.Logger;
import de.metas.logging.LogManager;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * @author tsa
 * 
 */
public class ZKTerminalComponentWrapper implements IComponent, IComponentZK
{
	private final ITerminalContext tc;
	private final org.zkoss.zk.ui.Component component;
	protected final Logger log = LogManager.getLogger(getClass());

	public ZKTerminalComponentWrapper(final ITerminalContext tc, final org.zkoss.zk.ui.Component component)
	{
		this.tc = tc;
		this.component = component;
	}

	@Override
	public org.zkoss.zk.ui.Component getComponent()
	{
		return component;
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return tc;
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}
}
