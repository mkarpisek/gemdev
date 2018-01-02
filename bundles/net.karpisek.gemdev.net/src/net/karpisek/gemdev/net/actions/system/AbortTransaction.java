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
 * Performs abort in DB.
 */
public class AbortTransaction extends SessionAction<String> {
	@Override
	public String asRequestString() {
		return createExecuteOperationRequest("System abortTransaction"); //$NON-NLS-1$
	}

	@Override
	public String asResponse(final String responseString) throws ActionException {
		return null;
	}

	@Override
	public boolean isIdempotent() {
		return true;
	}
}
