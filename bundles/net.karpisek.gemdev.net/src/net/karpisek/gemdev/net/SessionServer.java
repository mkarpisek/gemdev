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

import net.karpisek.gemdev.net.actions.Echo;

/**
 * Session server is used for submitting requests by {@link SessionClient} to database.
 */
public class SessionServer {
	public static final String DEFAULT_HOST_NAME = "127.0.0.1"; //$NON-NLS-1$
	public static final int DEFAULT_PORT = 2708;

	/**
	 * Performs call to session server with requested connection profile.
	 * 
	 * @param hostName of session server
	 * @param port of session server for our client
	 * @return null on success or error message.
	 */
	public static String checkConnection(final String hostName, final int port) {
		final SessionClient client = new SessionClient(hostName, port);
		try {
			final String msg = "test"; //$NON-NLS-1$

			final String value = client.execute(new Echo(msg));

			if (!value.equals(msg)) {
				return Messages.CHECK_SESSION_SERVER_CONNECTION_MALFORMED_ERROR;
			}
		} catch (final ActionException e) {
			NetPlugin.getDefault().logError(e);
			return Messages.CHECK_SESSION_SERVER_CONNECTION_ERROR;
		}
		return null;
	}
}
