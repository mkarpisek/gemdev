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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;

/**
 * Common logging functionality used by all plugins in GemDev project.
 */
public class Logger {
	private final String pluginId;
	private final Plugin plugin;

	public Logger(final String pluginId, final Plugin plugin) {
		this.pluginId = pluginId;
		this.plugin = plugin;
	}

	public void log(final int severity, final int code, final String message, final Throwable exception) {
		log(createStatus(severity, code, message, exception));
	}

	public void log(final IStatus status) {
		plugin.getLog().log(status);
	}

	public void logError(final String message) {
		logError(message, null);
	}

	public void logError(final String message, final Throwable exception) {
		log(IStatus.ERROR, IStatus.OK, message, exception);
	}

	public void logError(final Throwable exception) {
		logError("Unexpected Error", exception); //$NON-NLS-1$
	}

	public void logInfo(final String message) {
		log(IStatus.INFO, IStatus.OK, message, null);
	}

	private IStatus createStatus(final int severity, final int code, final String message, final Throwable exception) {
		return new Status(severity, pluginId, code, message, exception);
	}
}
