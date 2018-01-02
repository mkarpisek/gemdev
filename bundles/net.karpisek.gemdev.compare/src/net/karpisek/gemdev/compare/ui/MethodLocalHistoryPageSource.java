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
package net.karpisek.gemdev.compare.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.team.ui.history.ElementLocalHistoryPageSource;

import net.karpisek.gemdev.compare.ComparePlugin;
import net.karpisek.gemdev.core.db.DbMethod;

/**
 * Local history of method is history of its local file. So we can reuse complete framework used for local files also for in-memory methods.
 */
public class MethodLocalHistoryPageSource extends ElementLocalHistoryPageSource {

	private static MethodLocalHistoryPageSource instance;

	public static MethodLocalHistoryPageSource getInstance() {
		if (instance == null) {
			instance = new MethodLocalHistoryPageSource();
		}
		return instance;
	}

	@Override
	public IFile getFile(final Object input) {
		if (input instanceof DbMethod) {
			try {
				return ((DbMethod) input).getFile();
			} catch (final CoreException e) {
				ComparePlugin.getDefault().logError(e);
			}
		}
		return null;
	}
}
