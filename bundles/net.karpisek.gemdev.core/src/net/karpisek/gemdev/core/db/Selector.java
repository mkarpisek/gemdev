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
/**
 * 
 */
package net.karpisek.gemdev.core.db;

import com.google.common.base.Objects;

/**
 * Selector of method is method name.
 */
public class Selector {
	private final String name;
	private final ISession session;

	public Selector(final String name, final ISession session) {
		this.name = name;
		this.session = session;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Selector) {
			final Selector other = (Selector) obj;
			return Objects.equal(name, other.name) && Objects.equal(session, other.session);
		}
		return false;
	}

	public String getFullLabel() {
		return getName() + " - " + getSession().getProject().getName(); //$NON-NLS-1$
	}

	public String getName() {
		return name;
	}

	public ISession getSession() {
		return session;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name, session);
	}

	@Override
	public String toString() {
		return getFullLabel();
	}
}