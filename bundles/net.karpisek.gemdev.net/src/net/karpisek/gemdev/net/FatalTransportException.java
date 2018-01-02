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

import java.io.PrintStream;

/**
 * This exception represents failure to connect to destination or if action was not possible to complete because existing connection is not working any more.
 */
public class FatalTransportException extends ActionException {
	private static final long serialVersionUID = 5989522806338199261L;

	private final String hostName;
	private final int port;

	public FatalTransportException(final String actionName, final String message, final String request, final String hostName, final int port,
			final Throwable cause) {
		super(actionName, message, request, cause);

		this.hostName = hostName;
		this.port = port;
	}

	public String getHostName() {
		return hostName;
	}

	public int getPort() {
		return port;
	}

	@Override
	public void printStackTrace(final PrintStream s) {
		super.printStackTrace(s);
		s.printf("Server: http://%s:%d", getHostName(), getPort());

	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(super.toString());

		sb.append(System.lineSeparator());
		sb.append("HostName:").append(getHostName()).append(System.lineSeparator());
		sb.append("Port:").append(getPort()).append(System.lineSeparator());

		return sb.toString();
	}
}
