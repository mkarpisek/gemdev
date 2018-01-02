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

import net.karpisek.gemdev.net.SessionAction;

/**
 * Answers name of category for some method.
 */
public class GetMethodCategoryName extends SessionAction<String> {
	private final String className;
	private final boolean instanceSide;
	private final String methodName;

	/**
	 * @param className in which we want to search
	 * @param instanceSide on which side we search
	 * @param methodName for which we want to find category where it is located
	 */
	public GetMethodCategoryName(final String className, final boolean instanceSide, final String methodName) {
		this.className = className;
		this.instanceSide = instanceSide;
		this.methodName = methodName;
	}

	@Override
	public String asRequestString() {
		final String script = newScriptWithArguments(getClass().getSimpleName(), className, Boolean.toString(instanceSide), methodName.replaceAll("'", "''") //$NON-NLS-1$//$NON-NLS-2$
		);
		return createExecuteOperationRequest(script);
	}

	@Override
	public String asResponse(final String responseString) {
		Preconditions.checkNotNull(responseString);

		// TODO: refactor nil checking to method
		if ("nil".equals(responseString)) { //$NON-NLS-1$
			return null;
		}
		return responseString;
	}

	@Override
	public boolean isIdempotent() {
		return true;
	}

}
