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

import org.osgi.framework.BundleContext;

import net.karpisek.gemdev.ui.AbstractGsUiPlugin;

/**
 * Plugin providing structural compare functionality.
 */
public class ComparePlugin extends AbstractGsUiPlugin {
	public static final String PLUGIN_ID = "net.karpisek.gemdev.compare"; //$NON-NLS-1$

	private static ComparePlugin plugin;

	public static ComparePlugin getDefault() {
		return plugin;
	}

	public ComparePlugin() {
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
