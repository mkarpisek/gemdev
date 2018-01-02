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

/***
 * Interface for additional functionality for GS plugins.
 */
public interface IPluginExtensions {
	public String getFileContents(final String filepath);

	public String getScript(final String name);

	public void logError(final String format, final Object... objects);

	public void logError(final Throwable exception);

	public void logInfo(final String format, final Object... objects);
}
