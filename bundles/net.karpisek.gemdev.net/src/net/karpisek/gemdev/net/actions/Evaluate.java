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

import net.karpisek.gemdev.net.ActionException;
import net.karpisek.gemdev.net.SessionAction;

/**
 * Evaluates smalltalk expression on db side and returns oopString. Always adds result into export set, client is responsible to call
 * {@link RemoveFromExportSet} action on such oop. throws <code>CompilationErrorM</code> for syntax errors.
 */
public class Evaluate extends SessionAction<String> {
	private final CheckSyntax checkSyntaxAction;
	private final boolean useExportSet;

	/**
	 * Default constructor - using export set for evaluated result and nil context
	 * 
	 * @param smalltalkExpr
	 */
	public Evaluate(final String smalltalkExpr) {
		this(smalltalkExpr, null, true);
	}

	/**
	 * @param smalltalkExpr
	 * @param useExportSet if true result will be added to export set on db side, if false result will not be (can be garbage collected!)
	 */
	public Evaluate(final String smalltalkExpr, final String contextOop, final boolean useExportSet) {
		this.checkSyntaxAction = new CheckSyntax(smalltalkExpr, contextOop, useExportSet);
		this.useExportSet = useExportSet;
	}

	@Override
	public String asRequestString() {
		final String script = newScriptWithArguments(getClass().getSimpleName(), checkSyntaxAction.smalltalkExpr.replaceAll("'", "''"), //$NON-NLS-1$//$NON-NLS-2$
				checkSyntaxAction.contextOop == null ? "nil" : checkSyntaxAction.contextOop, //$NON-NLS-1$
				useExportSet ? "true" : "false" //$NON-NLS-1$//$NON-NLS-2$
		);

		return createExecuteOperationRequest(script);
	}

	@Override
	public String asResponse(final String responseString) throws ActionException {
		Preconditions.checkNotNull(responseString);
		checkSyntaxAction.checkCompilationError(responseString, false);
		return responseString;
	}
}
