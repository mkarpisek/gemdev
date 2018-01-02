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
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.lang.ParserUtils.Result;
import net.karpisek.gemdev.lang.model.Message.Type;
import net.karpisek.gemdev.ui.tests.IUnitTests;
import net.karpisek.gemdev.ui.tests.UiTestsPlugin;

@Category({ IUnitTests.class })
public class GsParserTest {
	public void assertEqualsAst(final String expectedAstFileName, final String sourceCodeFileName) throws IOException, RecognitionException {
		final String sourceCode = getResource(sourceCodeFileName);
		final String expected = getResource(expectedAstFileName);

		final Tree t = parse(sourceCode);
		final String actual = t.toStringTree();
		assertEquals(expected, actual);
	}

	@Test
	public void testArrayBuilder() throws RecognitionException, IOException {
		assertEqualsAst("arrayBuilder_expectedAst.txt", "arrayBuilder.gsm");
	}

	/**
	 * It is optional if array builder has ',' separators only between items or after each item (#[a,b,] is valid)
	 */
	@Test
	public void testArrayBuilder2() throws RecognitionException, IOException {
		assertEqualsAst("arrayBuilder_expectedAst.txt", "arrayBuilder2.gsm");
	}

	@Test
	public void testArrayLiteral() throws RecognitionException, IOException {
		assertEqualsAst("arrayLiteral_expectedAst.txt", "arrayLiteral.gsm");
	}

	@Test
	public void testAssign() throws RecognitionException, IOException {
		assertEqualsAst("assign_expectedAst.txt", "assign.gsm");
	}

	@Test
	public void testBinaryMessage() throws RecognitionException, IOException {
		assertEqualsAst("binaryMessage_expectedAst.txt", "binaryMessage.gsm");
	}

	@Test
	public void testBinaryMethodPattern() throws RecognitionException, IOException {
		assertEqualsAst("binaryPattern_expectedAst.txt", "binaryPattern.gsm");
	}

	@Test
	public void testBlock() throws RecognitionException, IOException {
		assertEqualsAst("block_expectedAst.txt", "block.gsm");
	}

	/**
	 * Bug1 - lexer wrongly generated tokens which should be: input "1" - INT_LITERAL, input "1." - INT_LITERAL, DOT input "1.1" - FLOAT_LITERAL
	 */
	@Test
	public void testBug1() throws RecognitionException, IOException {
		assertEqualsAst("bug1_expectedAst.txt", "bug1.gsm");
	}

	/**
	 * Bug2 - lexer reported ID+blockParameter instead of keyword+ID
	 */
	@Test
	public void testBug2() throws RecognitionException, IOException {
		assertEqualsAst("bug2_expectedAst.txt", "bug2.gsm");
	}

	/**
	 * Bug3 - introduction of selectionBlock caused problems with BINARY_SELECTOR
	 * 
	 * @throws RecognitionException
	 * @throws IOException
	 */
	@Test
	public void testBug3() throws RecognitionException, IOException {
		assertEqualsAst("bug3_expectedAst.txt", "bug3.gsm");
	}

	/**
	 * Bug4 - problem with wrong interpretation of BINARY_SELECTOR, -> was not recognised
	 */
	@Test
	public void testBug4() throws RecognitionException, IOException {
		assertEqualsAst("bug4_expectedAst.txt", "bug4.gsm");
	}

	/**
	 * Bug5 - '|' was not correctly recognised as binary selector
	 */
	@Test
	public void testBug5() throws RecognitionException, IOException {
		assertEqualsAst("bug5_expectedAst.txt", "bug5.gsm");
	}

	/**
	 * Bug6 - problem with different combinations of present/missing parameters/tmpVars
	 */
	@Test
	public void testBug6() throws RecognitionException, IOException {
		assertEqualsAst("bug6_expectedAst.txt", "bug6.gsm");
	}

	/**
	 * Bug7 - symbol definition did not contained also binary selector symbols (for example: #++)
	 */
	@Test
	public void testBug7() throws RecognitionException, IOException {
		assertEqualsAst("bug7_expectedAst.txt", "bug7.gsm");
	}

	@Test
	public void testKeywordMessage() throws RecognitionException, IOException {
		assertEqualsAst("keywordMessage_expectedAst.txt", "keywordMessage.gsm");
	}

	@Test
	public void testKeywordMethodPattern() throws RecognitionException, IOException {
		assertEqualsAst("keywordPattern_expectedAst.txt", "keywordPattern.gsm");
	}

	@Test
	public void testMethodTemporaries() throws RecognitionException, IOException {
		assertEqualsAst("methodTemporaries_expectedAst.txt", "methodTemporaries.gsm");
	}

	@Test
	public void testParsingSelector() throws RecognitionException, IOException {
		assertNull(ParserUtils.parseSelector(""));
		assertEquals(new ParserUtils.ParsedSelector(Type.UNARY, Lists.newArrayList("msg")), ParserUtils.parseSelector("msg"));
		assertEquals(new ParserUtils.ParsedSelector(Type.BINARY, Lists.newArrayList("++")), ParserUtils.parseSelector("++"));
		assertEquals(new ParserUtils.ParsedSelector(Type.KEYWORD, Lists.newArrayList("kw1", "kw2")), ParserUtils.parseSelector("kw1:kw2:"));
	}

	@Test
	public void testPrimitive() throws RecognitionException, IOException {
		assertEqualsAst("primitive_expectedAst.txt", "primitive.gsm");
	}

	@Test
	public void testPrimitiveProtected() throws RecognitionException, IOException {
		assertEqualsAst("primitiveProtected_expectedAst.txt", "primitiveProtected.gsm");
	}

	@Test
	public void testPrimitiveUnprotected() throws RecognitionException, IOException {
		assertEqualsAst("primitiveUnprotected_expectedAst.txt", "primitiveUnprotected.gsm");
	}

	@Test
	public void testSelectionBlock() throws RecognitionException, IOException {
		assertEqualsAst("selectionBlock_expectedAst.txt", "selectionBlock.gsm");
	}

	@Test
	public void testStatements() throws RecognitionException, IOException {
		assertEqualsAst("statements_expectedAst.txt", "statements.gsm");
	}

	@Test
	public void testUnaryMessage() throws RecognitionException, IOException {
		assertEqualsAst("unaryMessage_expectedAst.txt", "unaryMessage.gsm");
	}

	@Test
	public void testUnaryMethodPattern() throws RecognitionException, IOException {
		assertEqualsAst("unaryPattern_expectedAst.txt", "unaryPattern.gsm");
	}

	private String getResource(final String fileName) {
		return UiTestsPlugin.getDefault().getFileContents("/resources/parser/" + fileName);
	}

	private Tree parse(final String sourceCode) throws IOException, RecognitionException {
		final Result parse = ParserUtils.parse(sourceCode);
		if (!parse.getSyntaxErrors().isEmpty()) {
			throw parse.getSyntaxErrors().get(0);
		}
		return parse.getAst();
	}
}
