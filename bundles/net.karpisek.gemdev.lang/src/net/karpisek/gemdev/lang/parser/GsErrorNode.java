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


import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonErrorNode;

/**

 */
public class GsErrorNode extends GsTree {
	private final CommonErrorNode commonErrorNode;

	public GsErrorNode(final TokenStream input, final Token start, final Token stop, final RecognitionException e) {
		commonErrorNode = new CommonErrorNode(input, start, stop, e);
	}

	@Override
	public boolean isNil() {
		return commonErrorNode.isNil();
	}

	@Override
	public int getType() {
		return commonErrorNode.getType();
	}

	@Override
	public Token getToken() {
		return commonErrorNode.start;
	}

	@Override
	public String getText() {
		return commonErrorNode.getText();
	}

	@Override
	public String toString() {
		return commonErrorNode.toString();
	}
}
