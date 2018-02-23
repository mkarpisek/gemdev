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
package net.karpisek.gemdev.team.actions;

import com.google.common.base.Preconditions;

import net.karpisek.gemdev.net.SessionAction;
import net.karpisek.gemdev.team.TeamPlugin;
import net.karpisek.gemdev.utils.IPluginExtensions;

/**
 * Answers string with sources of one class in .gsc format.
 */
public class GetSourcesInGscFormat extends SessionAction<String> {
	private final String className;

	public GetSourcesInGscFormat(final String className) {
		this.className = className;
	}

	@Override
	public String asRequestString() {
		final String script = newScriptWithArguments(getClass().getSimpleName(), className);
		return createExecuteOperationRequest(script);
	}

	@Override
	public String asResponse(final String responseString) {
		Preconditions.checkNotNull(responseString);
		return responseString;
	}

	@Override
	public boolean isIdempotent() {
		return true;
	}

	@Override
	protected IPluginExtensions getPlugin() {
		return TeamPlugin.getDefault();
	}
}
