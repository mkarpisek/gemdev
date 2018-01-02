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
 * Creates new session on session server and returns port on which it will be listening
 */
public class CreateSession extends SessionAction<Integer> implements IBrokerAction<Integer> {
	private final String clientId;
	private final Profile parameters;

	public CreateSession(final String clientId, final Profile parameters) {
		this.clientId = clientId;
		this.parameters = parameters;
	}

	@Override
	public String asRequestString() {
		return String.format("#('' '' #newSession #('%s' %s))", clientId, parameters.asSmalltalkArrayString()); //$NON-NLS-1$
	}

	@Override
	public Integer asResponse(final String responseString) throws ActionException {
		Preconditions.checkNotNull(responseString);

		checkResponseForErrors(responseString);

		return Integer.valueOf(responseString);
	}

	@Override
	public boolean isIdempotent() {
		return false;
	}

	private void checkResponseForErrors(final String responseString) {
		final String errorHeader = "NewSessionError"; //$NON-NLS-1$
		if (responseString.startsWith(errorHeader)) {
			final String[] parameters = responseString.split("\n"); //$NON-NLS-1$

			Preconditions.checkArgument(parameters.length == 2);
			throw new CreateSessionException(getClass().getSimpleName(), parameters[1], asRequestString());
		}
	}
}
