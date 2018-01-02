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
package net.karpisek.gemdev.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import net.karpisek.gemdev.lang.lexer.GsLexer;
import net.karpisek.gemdev.ui.tests.IUnitTests;

@Category({ IUnitTests.class })
public class GsLexerTest {
	@Test
	public void testFloatWithoutExponent() throws IOException {
		final List<CommonToken> tokens = getTokens("1.1");

		assertEquals("expected differnet number of tokens", 1, tokens.size());
		assertToken(GsLexer.FLOAT_LITERAL, "1.1", tokens.get(0));
	}

	@Test
	public void testInteger() throws IOException {
		final List<CommonToken> tokens = getTokens("1");

		assertEquals("expected differnet number of tokens", 1, tokens.size());
		assertToken(GsLexer.INT_LITERAL, "1", tokens.get(0));
	}

	@Test
	public void testIntegerAndDot() throws IOException {
		final List<CommonToken> tokens = getTokens("1.");

		assertEquals("expected differnet number of tokens", 2, tokens.size());
		assertToken(GsLexer.INT_LITERAL, "1", tokens.get(0));
		assertToken(GsLexer.DOT, ".", tokens.get(1));
	}

	@Test
	public void testSymbolAndDot() throws IOException {
		final List<CommonToken> tokens = getTokens("#symbol.");

		assertEquals("expected differnet number of tokens", 2, tokens.size());
		assertToken(GsLexer.SYMBOL_LITERAL, "#symbol", tokens.get(0));
		assertToken(GsLexer.DOT, ".", tokens.get(1));
	}

	@Test
	public void testX() throws IOException {
		final List<CommonToken> tokens = getTokens("coll at:index");

		assertEquals("expected differnet number of tokens", 5, tokens.size());
		assertToken(GsLexer.ID, "coll", tokens.get(0));
		assertToken(GsLexer.WS, " ", tokens.get(1));
		assertToken(GsLexer.ID, "at", tokens.get(2));
		assertToken(GsLexer.COLON, ":", tokens.get(3));
		assertToken(GsLexer.ID, "index", tokens.get(4));
	}

	private void assertToken(final int tokenType, final String tokenText, final CommonToken token) {
		assertNotNull(token);
		assertEquals("Wrong token type", tokenType, token.getType());
		assertEquals("Wrrong token text", tokenText, token.getText());
	}

	@SuppressWarnings("unchecked")
	private List<CommonToken> getTokens(final String sourceCode) throws IOException {
		final ANTLRReaderStream input = new ANTLRReaderStream(new StringReader(sourceCode));
		final GsLexer lexer = new GsLexer(input);
		final CommonTokenStream tokens = new CommonTokenStream(lexer);
		return tokens.getTokens();
	}
}
