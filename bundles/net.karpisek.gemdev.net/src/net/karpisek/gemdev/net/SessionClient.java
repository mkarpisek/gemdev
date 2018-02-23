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
package net.karpisek.gemdev.net;

/**
 * Network client for connecting to session server. It is assumed that there is relation session client = session server = GS session.
 */
public class SessionClient extends Client {
	/**
	 * Constructor for new session client with disabled tracing.
	 */
	public SessionClient(final String hostName, final int port) {
		this(hostName, port, false);
	}

	/**
	 * Constructor for new session client.
	 * 
	 * @param hostName of session server
	 * @param port on which is session server listening
	 */
	public SessionClient(final String hostName, final int port, final boolean enableTracing) {
		super(hostName, port, enableTracing);
	}

	/**
	 * Execute the action. Action execution can be repeated in case it answers true on {@link SessionAction#isIdempotent()}.
	 * 
	 * @param <T>
	 * @param action to be executed
	 * @return
	 */
	public <T> T execute(final ISessionAction<T> action) {
		final String responseString = executePostMethod(action.getClass().getSimpleName(), action.asRequestString(), action.isIdempotent());
		return action.asResponse(responseString);
	}
}
