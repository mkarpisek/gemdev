/*******************************************************************************
 * Copyright (c) 2008, 2018 Martin Karpisek.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Martin Karpisek - initial API and implementation
 *******************************************************************************/
package net.karpisek.gemdev.ui.inspector;

import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.net.ISessionAction;
import net.karpisek.gemdev.net.SessionAction;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.utils.IPluginExtensions;

/**
 * Base action implementation for fetching information for inspected objects in inspector. It is by default idempotent.
 * 
 * @param <T> type of object returned from asResponse method.
 */
public abstract class BaseAction<T> extends SessionAction<T> {
	protected final ISession session;
	protected final String oop;

	public BaseAction(final ISession session, final String oop) {
		this.session = session;
		this.oop = oop;
	}

	public String getOop() {
		return oop;
	}

	public ISession getSession() {
		return session;
	}

	@Override
	public boolean isIdempotent() {
		return true;
	}

	/**
	 * Executes action in same session as is receiver action created.
	 */
	protected <T2> T2 execute(final ISessionAction<T2> action) {
		return session.execute(action);
	}

	@Override
	protected IPluginExtensions getPlugin() {
		return GemDevUiPlugin.getDefault();
	}
}
