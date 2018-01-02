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
package net.karpisek.gemdev.net.actions.clazz;

import com.google.common.base.Preconditions;

import net.karpisek.gemdev.net.ActionException;
import net.karpisek.gemdev.net.SessionAction;

/**
 * Deletes class with corresponding name from DB. Does not perform abort or commit - that is responsibility of client.
 */
public class DeleteClass extends SessionAction<String> {
	private final String className;

	public DeleteClass(final String className) {
		this.className = className;
	}

	@Override
	public String asRequestString() {
		final String script = newScriptWithArguments(getClass().getSimpleName(), className);
		return createExecuteOperationRequest(script);
	}

	@Override
	public String asResponse(final String responseString) throws ActionException {
		Preconditions.checkNotNull(responseString);
		return responseString;
	}
}
