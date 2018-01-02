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
package net.karpisek.gemdev.team;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "net.karpisek.gemdev.team.messages"; //$NON-NLS-1$
	public static String CLASS_READ_ERROR_MESSAGE;
	public static String UNKNOWN_PROJECT;
	public static String WRITE_CATEGORIES_JOB_NAME;
	public static String WRITE_CLASSES_ERROR_MESSAGE;
	public static String WRITE_CLASSES_JOB_NAME;
	public static String WRITE_METHODS_ERROR_MESSAGE;
	public static String WRITE_METHODS_JOB_NAME;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
