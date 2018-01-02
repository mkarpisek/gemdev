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
package net.karpisek.gemdev.net.actions.system;

import net.karpisek.gemdev.net.ActionException;
import net.karpisek.gemdev.net.SessionAction;

/**
 * Sets current session author initials. Author initials is string which is used for example in method compilation stamps.
 */
public class SetAuthorInitials extends SessionAction<String> {
	private final String value;

	public SetAuthorInitials(final String value) {
		this.value = value;
	}

	@Override
	public String asRequestString() {
		final String expr = String.format("GsPackagePolicy current authorInitials: '%s'", value.replaceAll("'", "''")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return createExecuteOperationRequest(expr);
	}

	@Override
	public String asResponse(final String responseString) throws ActionException {
		return responseString;
	}
}
