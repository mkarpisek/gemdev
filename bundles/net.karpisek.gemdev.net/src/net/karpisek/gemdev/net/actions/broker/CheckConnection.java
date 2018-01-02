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
package net.karpisek.gemdev.net.actions.broker;

import com.google.common.base.Preconditions;

import net.karpisek.gemdev.net.ActionException;
import net.karpisek.gemdev.net.SessionAction;

/**
 * Performs test connecting to GS. Answers null in case connection works or error message.
 */
public class CheckConnection extends SessionAction<String> implements IBrokerAction<String> {
	private final Profile profile;

	public CheckConnection(final Profile profile) {
		this.profile = profile;
	}

	@Override
	public String asRequestString() {
		return String.format("#('' '' #checkConnection %s)", profile.asSmalltalkArrayString()); //$NON-NLS-1$
	}

	@Override
	public String asResponse(final String responseString) throws ActionException {
		Preconditions.checkNotNull(responseString);

		if ("nil".equals(responseString)) { //$NON-NLS-1$
			return null;
		}

		return responseString;
	}

	@Override
	public boolean isIdempotent() {
		return true;
	}
}
