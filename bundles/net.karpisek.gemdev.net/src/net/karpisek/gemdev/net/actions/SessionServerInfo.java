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
package net.karpisek.gemdev.net.actions;

/**
 * Data object with information about current state of session server.
 */
public class SessionServerInfo {
	private final int portNumber;
	private final String clientId;
	private final String serverStartTime;
	private final String lastRequestTime;
	private final String userName;
	private final String stoneName;

	public SessionServerInfo(final int portNumber, final String clientId, final String serverStartTime, final String lastRequestTime, final String userName,
			final String stoneName) {
		this.portNumber = portNumber;
		this.clientId = clientId;
		this.serverStartTime = serverStartTime;
		this.lastRequestTime = lastRequestTime;
		this.userName = userName;
		this.stoneName = stoneName;
	}

	public String getClientId() {
		return clientId;
	}

	public String getLastRequestTime() {
		return lastRequestTime;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public String getServerStartTime() {
		return serverStartTime;
	}

	public String getStoneName() {
		return stoneName;
	}

	public String getUserName() {
		return userName;
	}

	@Override
	public String toString() {
		final String[] tokens = getClientId().split("_"); //$NON-NLS-1$
		return String.format("%d. IP:%s#%s Last:%s", getPortNumber(), tokens[0], tokens[2], getLastRequestTime()); //$NON-NLS-1$
	}
}