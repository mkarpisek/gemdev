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

import net.karpisek.gemdev.net.actions.broker.CheckConnection;
import net.karpisek.gemdev.net.actions.broker.Profile;

public class BrokerServer {
	public static final String DEFAULT_HOST_NAME = "192.168.140.130"; //$NON-NLS-1$
	public static final int DEFAULT_PORT = 2707;

	/**
	 * Performs call to broker server with requested connection profile.
	 * 
	 * @param hostName of broker server
	 * @param port of broker server for our client
	 * @return null on success or error message.
	 */
	public static String checkConnection(final String hostName, final int port, final Profile params) {
		try {
			final BrokerServerClient client = new BrokerServerClient(hostName, port);
			return client.execute(new CheckConnection(params));
		} catch (final ActionException e) {
			NetPlugin.getDefault().logError(e);
			return Messages.CHECK_BROKER_SERVER_CONNECTION_ERROR;
		}
	}
}
