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
package net.karpisek.gemdev.compare;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "net.karpisek.gemdev.compare.messages"; //$NON-NLS-1$
	public static String CLASS_NOT_FOUND_IN_SESSION_ERROR;
	public static String COMPARE_CLASSES_JOB_NAME;
	public static String COMPARE_EDITOR_TITLE;
	public static String COMPARE_JOB_NAME;
	public static String NO_DIFFERENCES_FOUND_MESSAGE;
	public static String SELECT_OTHER_CLASS_MESSAGE;
	public static String SELECT_OTHER_SESSION_MESSAGE;
	public static String WORKING_COPY;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
