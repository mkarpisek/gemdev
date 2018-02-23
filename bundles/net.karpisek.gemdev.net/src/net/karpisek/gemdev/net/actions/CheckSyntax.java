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
package net.karpisek.gemdev.net.actions;

import com.google.common.base.Preconditions;

import net.karpisek.gemdev.net.SessionAction;

/**
 * Performs syntax check of smalltalk code on server side. Can throw exception on syntax error or only true/false.
 */
public class CheckSyntax extends SessionAction<Boolean> {
	protected final String smalltalkExpr;
	protected final String contextOop;
	protected final boolean silently;

	/**
	 * @param smalltalkExpr to be checked if it is syntactically ok
	 * @param useExportSet if true result will be added to export set on db side, if false result will not be (can be garbage collected!)
	 * @param silently if true than no exception is thrown, if false than execution throws CompilationError
	 */
	public CheckSyntax(final String smalltalkExpr, final String contextOop, final boolean silently) {
		this.smalltalkExpr = smalltalkExpr;
		this.contextOop = contextOop;
		this.silently = silently;
	}

	@Override
	public String asRequestString() {
		final String script = newScriptWithArguments(getClass().getSimpleName(), smalltalkExpr.replaceAll("'", "''"), contextOop == null ? "nil" : contextOop //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		);

		return createExecuteOperationRequest(script);
	}

	@Override
	public Boolean asResponse(final String responseString) {
		Preconditions.checkNotNull(responseString);

		return checkCompilationError(responseString, silently);
	}

	protected boolean checkCompilationError(final String responseString, final boolean checkSilently) {
		final String errorHeader = "CompilationError"; //$NON-NLS-1$
		if (!responseString.startsWith(errorHeader)) {
			return true;
		}

		if (checkSilently) {
			return false;
		}

		final String[] errors = responseString.split("\n"); //$NON-NLS-1$

		// take first error and throw it as exception - ignore others
		final String error = errors[1];
		final int endOfErrNumber = error.indexOf(' ', 0);
		final int endOfErrOffset = error.indexOf(' ', endOfErrNumber + 1);

		final String errNumberString = error.substring(0, endOfErrNumber);
		final String errOfffset = error.substring(endOfErrNumber + 1, endOfErrOffset);
		final String message = error.substring(endOfErrOffset + 1);

		final int errNumber = Integer.parseInt(errNumberString);
		final int javaErrOffset = Integer.valueOf(errOfffset) - 1;// because smalltalk is counting from 1!
		throw new CompilationError(getClass().getSimpleName(), message, asRequestString(), errNumber, javaErrOffset, smalltalkExpr);
	}
}
