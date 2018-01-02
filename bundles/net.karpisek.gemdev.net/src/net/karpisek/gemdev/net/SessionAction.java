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

import net.karpisek.gemdev.utils.IPluginExtensions;

/**
 * Abstract superclass for action implementations with some common methods. Default implementation is not idempotent - must not be retried - subclasses should
 * overwrite.
 *
 * @param <T> see {@link ISessionAction}
 */
public abstract class SessionAction<T> implements ISessionAction<T> {

	@Override
	public boolean isIdempotent() {
		return false;
	}

	protected String createExecuteOperationRequest(final String expr) {
		return String.format("#('' '' #execute #('%s'))", expr.replaceAll("'", "''")); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	}

	/**
	 * @return plugin which contains scripts
	 */
	protected IPluginExtensions getPlugin() {
		return NetPlugin.getDefault();
	}

	protected String newScriptWithArguments(final String scriptName, final Object... args) {
		return String.format(getPlugin().getScript(scriptName), args);
	}
}
