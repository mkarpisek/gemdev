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
package net.karpisek.gemdev.lang.parser;

/**
 *  Exception used for reporting any syntax parsing errors.

 */
public class SyntaxError extends RuntimeException {
	private static final long serialVersionUID = -5106014540762648530L;

	private final int line;
	private final int column;

	private final int length;

	public SyntaxError(final String message, final int line, final int column, final int length){
		super(message);
		this.line = line;
		this.column = column;
		this.length = length;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}

	public int getLength() {
		return length;
	}
}
