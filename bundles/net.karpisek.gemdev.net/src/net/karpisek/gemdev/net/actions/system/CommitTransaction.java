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

import com.google.common.base.Preconditions;

import net.karpisek.gemdev.net.SessionAction;

/**
 * Performs commit in Db.
 */
public class CommitTransaction extends SessionAction<Boolean> {
	@Override
	public String asRequestString() {
		return createExecuteOperationRequest("System commitTransaction"); //$NON-NLS-1$
	}

	@Override
	public Boolean asResponse(final String responseString) {
		Preconditions.checkNotNull(responseString);
		return "true".equals(responseString); //$NON-NLS-1$
	}
}
