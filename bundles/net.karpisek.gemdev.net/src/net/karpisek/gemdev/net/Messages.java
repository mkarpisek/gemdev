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

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "net.karpisek.gemdev.net.messages"; //$NON-NLS-1$
	public static String CHECK_BROKER_SERVER_CONNECTION_ERROR;
	public static String CHECK_SESSION_SERVER_CONNECTION_ERROR;
	public static String CHECK_SESSION_SERVER_CONNECTION_MALFORMED_ERROR;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
