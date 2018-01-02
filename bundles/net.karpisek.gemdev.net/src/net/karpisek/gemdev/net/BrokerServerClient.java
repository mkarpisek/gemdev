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

import net.karpisek.gemdev.net.actions.broker.IBrokerAction;

/**
 * Client to broker server.
 */
public class BrokerServerClient extends Client {
	/**
	 * Constructor for new broker client with disabled tracing.
	 */
	public BrokerServerClient(final String hostName, final int port) {
		this(hostName, port, false);
	}

	/**
	 * Constructor for new broker client.
	 * 
	 * @param hostName of broker server
	 * @param port on which is broker server listening
	 */
	public BrokerServerClient(final String hostName, final int port, final boolean enableTracing) {
		super(hostName, port, enableTracing);
	}

	/**
	 * Execute the action. Action execution can be repeated in case it answers true on {@link SessionAction#isIdempotent()}.
	 * 
	 * @param <T>
	 * @param action to be executed
	 * @return
	 * @throws SessionClientException
	 */
	public <T> T execute(final IBrokerAction<T> action) {
		final String responseString = executePostMethod(action.getClass().getSimpleName(), action.asRequestString(), action.isIdempotent());
		return action.asResponse(responseString);
	}
}
