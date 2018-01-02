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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;

/**
 * Common implementation for all non UI GemDev plugins .
 */
public abstract class AbstractGsPlugin extends Plugin implements IPluginExtensions {
	private final Logger logger;
	private Map<String, String> scripts;

	public AbstractGsPlugin(final String pluginId) {
		this.logger = new Logger(pluginId, this);
	}

	/**
	 * Reads complete contents of file located in this bundle on file path
	 * 
	 * @param filepath to file
	 * @return contents in string or null on error.
	 */
	@Override
	public String getFileContents(final String filepath) {
		try (InputStream fis = FileLocator.openStream(getBundle(), new Path(filepath), false);
				InputStreamReader reader = new InputStreamReader(fis, Charsets.UTF_8)) {
			return CharStreams.toString(reader);
		} catch (final IOException e) {
			logError(e);
		}
		return null;
	}

	/**
	 * Answers text script from in /resources/scripts/<name>.gsw
	 * 
	 * @param name of script without extension
	 * @return string with text or null if script does not exists
	 */
	@Override
	public String getScript(final String name) {
		String script = scripts.get(name);
		if (script == null) {
			script = getFileContents(String.format("/resources/scripts/%s.gsw", name)); //$NON-NLS-1$
			scripts.put(name, script);
		}
		return script;
	}

	@Override
	public void logError(final String format, final Object... objects) {
		logger.logError(String.format(format, objects));
	}

	@Override
	public void logError(final Throwable exception) {
		logger.logError(exception);
	}

	@Override
	public void logInfo(final String format, final Object... objects) {
		logger.logInfo(String.format(format, objects));
	}

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		scripts = Maps.newHashMap();
	}
}
