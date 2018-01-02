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
package net.karpisek.gemdev.core.analysis;

import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.model.IBehavior;

/**
 * Empty context which should be used if we can not provide real context based on {@link ISession}.
 */
public class NullMethodContext implements IMethodContext {
	@Override
	public ISession getSession() {
		return null;
	}

	@Override
	public boolean isSystemDeclaring(final String identifierName) {
		return false;
	}

	@Override
	public boolean isSystemImplementing(final String name) {
		return false;
	}

	@Override
	public IBehavior resolve(final String literal) {
		return null;
	}
}
