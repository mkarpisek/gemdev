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
package net.karpisek.gemdev.ui.search;

import org.eclipse.search.ui.ISearchQuery;

import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.db.ISession.TargetSession;
import net.karpisek.gemdev.net.ISessionAction;
import net.karpisek.gemdev.net.actions.system.AbortTransaction;

/**
 * Base implementation for search query in SEARCH target session.
 */
public abstract class SearchQuery implements ISearchQuery {
	protected ISession session;

	public SearchQuery(final ISession session) {
		this.session = session;
	}

	@Override
	public boolean canRerun() {
		return true;
	}

	@Override
	public boolean canRunInBackground() {
		return true;
	}

	public ISession getSession() {
		return session;
	}

	/**
	 * Executes in SEARCH target session action, perform abort before executing it
	 * 
	 * @param <T>
	 * @param action
	 * @return
	 */
	protected <T> T execute(final ISessionAction<T> action) {
		session.execute(TargetSession.SEARCH, new AbortTransaction());
		return session.execute(TargetSession.SEARCH, action);
	}
}
