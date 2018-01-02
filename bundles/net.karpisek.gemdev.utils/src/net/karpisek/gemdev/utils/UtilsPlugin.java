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
package net.karpisek.gemdev.utils;

import org.osgi.framework.BundleContext;

/**
 * Plugin used for various utility classes available for all plugins in GemDev project.
 */
public class UtilsPlugin extends AbstractGsPlugin {
	public static final String PLUGIN_ID = "net.karpisek.gemdev.utils"; //$NON-NLS-1$

	private static UtilsPlugin plugin;

	public static UtilsPlugin getDefault() {
		return plugin;
	}

	public UtilsPlugin() {
		super(PLUGIN_ID);
	}

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
}
