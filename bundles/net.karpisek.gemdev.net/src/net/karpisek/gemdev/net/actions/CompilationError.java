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

import com.google.common.base.Objects;

import net.karpisek.gemdev.net.ActionException;

public class CompilationError extends ActionException {
	private static final long serialVersionUID = 2747765587347772246L;
	private final int number;
	private final int offset;
	private final String sourceCode;

	/**
	 * @param actionName
	 * @param number of error
	 * @param offset into original source code where error occured
	 * @param message with error description
	 * @param sourceCode which was compiled and caused error
	 */
	public CompilationError(final String actionName, final String message, final String request, final int number, final int offset, final String sourceCode) {
		super(actionName, message, request, null);

		this.number = number;
		this.offset = offset;
		this.sourceCode = sourceCode;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof CompilationError) {
			final CompilationError other = (CompilationError) obj;
			return (Objects.equal(getNumebr(), other.getNumebr()) && Objects.equal(getOffset(), other.getOffset())
					&& Objects.equal(getMessage(), other.getMessage()) && Objects.equal(getSourceCode(), other.getSourceCode()));
		}
		return false;
	}

	public int getNumebr() {
		return number;
	}

	public int getOffset() {
		return offset;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getNumebr(), getOffset(), getMessage(), getSourceCode());
	}
}
