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
import net.karpisek.gemdev.net.actions.CompilationError;

/**
 * Compiles method in class. Does not perform commit.
 */
public class CreateMethod extends SessionAction<String> {
	private final String className;
	private final boolean instanceSide;
	private final String categoryName;
	private final String sourceCode;

	public CreateMethod(final String className, final boolean instanceSide, final String categoryName, final String sourceCode) {
		this.className = className;
		this.instanceSide = instanceSide;
		this.categoryName = categoryName;
		this.sourceCode = sourceCode;
	}

	@Override
	public String asRequestString() {
		final String script = newScriptWithArguments(getClass().getSimpleName(), className, Boolean.toString(instanceSide), categoryName.replaceAll("'", "''"), //$NON-NLS-1$ //$NON-NLS-2$
				sourceCode.replaceAll("'", "''") //$NON-NLS-1$ //$NON-NLS-2$
		);

		return createExecuteOperationRequest(script);
	}

	@Override
	public String asResponse(final String responseString) {
		Preconditions.checkNotNull(responseString);

		checkCompilationError(responseString);

		return responseString;
	}

	protected void checkCompilationError(final String responseString) throws CompilationError {
		final String errorHeader = "CompilationError"; //$NON-NLS-1$
		if (responseString.startsWith(errorHeader)) {
			final String[] errors = responseString.split("\n"); //$NON-NLS-1$

			// take first error and throw it as exception - ignore others
			final String error = errors[1];
			final int endOfErrNumber = error.indexOf(' ', 0);
			final int endOfErrOffset = error.indexOf(' ', endOfErrNumber + 1);

			final String errNumberString = error.substring(0, endOfErrNumber);
			final String errOfffset = error.substring(endOfErrNumber + 1, endOfErrOffset);
			final String message = error.substring(endOfErrOffset + 1);

			final int errNumber = Integer.valueOf(errNumberString);
			final int javaErrOffset = Integer.valueOf(errOfffset) - 1;// because smalltalk is counting from 1!
			throw new CompilationError(getClass().getSimpleName(), message, asRequestString(), errNumber, javaErrOffset, sourceCode);
		}
	}
}
