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
 * Evaluates Smalltalk source code in database and returns result as string.
 */
public class PrintIt extends SessionAction<String> {
	private final String sourceCode;

	/**
	 * @param sourceCode must be valid smalltalk expression
	 */
	public PrintIt(final String sourceCode) {
		Preconditions.checkNotNull(sourceCode);

		this.sourceCode = sourceCode;
	}

	@Override
	public String asRequestString() {
		return createExecuteOperationRequest(String.format("'%s' evaluate asString", sourceCode.replaceAll("'", "''"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Override
	public String asResponse(final String responseString) {
		Preconditions.checkNotNull(responseString);

		return responseString;
	}

	@Override
	public String toString() {
		return String.format("printItAction%n%s%n", sourceCode); //$NON-NLS-1$
	}
}
