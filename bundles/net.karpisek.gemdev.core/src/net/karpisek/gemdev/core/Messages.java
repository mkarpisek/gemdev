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
package net.karpisek.gemdev.core;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "net.karpisek.gemdev.core.messages"; //$NON-NLS-1$
	public static String ObjectCacheFactory_READING_METHODS;
	public static String SESSION_CREATED_BY_BROKER_REPORT;
	public static String SYNCHRONIZE_CLASSES_TASK_NAME;
	public static String SYNCHRONIZE_OBJECT_NAMES_TASK_NAME;
	public static String UNDECLARED_IDENTIFIERS_WARNING;
	public static String UNIMPLEMENTED_MESSAGES_WARNING;
	public static String UNKNOWN_PROJECT;
	public static String UNUSED_LOCAL_IDENTIFIERS_WARNING;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
