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

import org.eclipse.search.ui.text.Match;

import net.karpisek.gemdev.core.db.DbMethod;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.net.actions.MethodReference;

/**
 * Own implementation of match for {@link MethodReference} instances.
 */
public class MethodReferenceMatch extends Match {

	private final ISession session;
	private final String text;

	public MethodReferenceMatch(final ISession session, final MethodReference ref, final int offset, final String text) {
		super(ref, offset, text.length());

		this.session = session;
		this.text = text;
	}

	public DbMethod getMethod() {
		final MethodReference ref = getMethodReference();
		return (DbMethod) session.getCachedClass(ref.getClassName()).getMethod(ref.getMethodName(), ref.isInstanceSide());
	}

	public MethodReference getMethodReference() {
		return (MethodReference) getElement();
	}

	public ISession getSession() {
		return session;
	}

	public String getText() {
		return text;
	}
}
