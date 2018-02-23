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
package net.karpisek.gemdev.net.actions.method;

import com.google.common.base.Preconditions;

import net.karpisek.gemdev.net.SessionAction;

/**
 * Moves method to new category. Does not perform commit.
 */
public class MoveMethod extends SessionAction<String> {
	private final String className;
	private final boolean instanceSide;
	private final String methodName;
	private final String newCategoryName;

	public MoveMethod(final String className, final boolean instanceSide, final String methodName, final String newCategoryName) {
		this.className = className;
		this.instanceSide = instanceSide;
		this.methodName = methodName;
		this.newCategoryName = newCategoryName;
	}

	@Override
	public String asRequestString() {
		final String script = newScriptWithArguments(getClass().getSimpleName(), className, Boolean.toString(instanceSide), methodName,
				newCategoryName.replaceAll("'", "''") //$NON-NLS-1$ //$NON-NLS-2$
		);
		return createExecuteOperationRequest(script);
	}

	@Override
	public String asResponse(final String responseString) {
		Preconditions.checkNotNull(responseString);

		return responseString;
	}

}
