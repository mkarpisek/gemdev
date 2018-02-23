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
package net.karpisek.gemdev.net.actions.category;

import com.google.common.base.Preconditions;

import net.karpisek.gemdev.net.SessionAction;

/**
 * Deletes existing category from class including all methods in it. TODO: add exception handling for non-existing category.
 */
public class DeleteCategory extends SessionAction<String> {
	private final String className;
	private final boolean instanceSide;
	private final String categoryName;

	public DeleteCategory(final String className, final boolean instanceSide, final String categoryName) {
		this.className = className;
		this.instanceSide = instanceSide;
		this.categoryName = categoryName;
	}

	@Override
	public String asRequestString() {
		final String script = newScriptWithArguments(getClass().getSimpleName(), className, (instanceSide ? "true" : "false"), //$NON-NLS-1$//$NON-NLS-2$
				categoryName.replaceAll("'", "''") //$NON-NLS-1$//$NON-NLS-2$
		);
		return createExecuteOperationRequest(script);
	}

	@Override
	public String asResponse(final String responseString) {
		Preconditions.checkNotNull(responseString);
		return responseString;
	}

}
