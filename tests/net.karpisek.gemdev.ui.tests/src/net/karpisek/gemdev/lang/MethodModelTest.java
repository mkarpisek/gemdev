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
import static org.junit.Assert.assertSame;

import java.io.IOException;
import java.util.List;

import org.antlr.runtime.RecognitionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import net.karpisek.gemdev.lang.model.Context;
import net.karpisek.gemdev.lang.model.MethodModel;
import net.karpisek.gemdev.ui.tests.IUnitTests;
import net.karpisek.gemdev.ui.tests.UiTestsPlugin;

@Category({ IUnitTests.class })
public class MethodModelTest {
	private String sourceCode;
	private MethodModel model;

	@Before
	public void setUp() throws Exception {
		sourceCode = getResource("method.gsm");
		model = ParserUtils.build(sourceCode);
	}

	@Test
	public void testContexts() {
		final Context methodContext = model.getContext();
		final List<Context> allContexts = model.getContexts();

		assertNotNull(methodContext);
		assertEquals("Expecting 3 contexts - 1 for method and 2 for blocks", 3, allContexts.size());
		assertSame("Expecting first context is method context", methodContext, allContexts.get(0));
	}

	@Test
	public void testDeclaredIdentifiers() {
		final List<String> expected = Lists.newArrayList("method parameter arg1[(1:5),(3:7)]", "method parameter p1[(1:15)]",
				"method parameter unusedMethodParam[(1:23)]", "temporary variable t1[(2:2),(3:1),(7:18)]", "temporary variable t2[(2:5),(3:14)]",
				"temporary variable unusedTmpVar[(2:8)]", "block parameter p0[(4:3),(7:8)]", "block parameter p1[(4:7),(7:13)]",
				"block parameter unusedBlockParam[(4:11)]", "temporary variable t2[(5:3),(7:2)]", "temporary variable t3[(5:6),(8:2)]",
				"temporary variable unusedBlockTmpVar[(5:9)]", "block parameter p0[(8:24)]");

		assertEquals(Joiner.on("\n").join(expected), Joiner.on("\n").join(model.getLocalIdentifiers(null)));
	}

	@Test
	public void testGetBinarySelector() throws IOException, RecognitionException {
		model = build("binaryPattern.gsm");

		assertEquals(Lists.newArrayList("++"), model.getSelectorParts());
		assertEquals("++", model.getSelector());
	}

	@Test
	public void testGetKeywordSelector() throws IOException, RecognitionException {
		model = build("keywordPattern.gsm");

		assertEquals(Lists.newArrayList("kw1:", "kw2:"), model.getSelectorParts());
		assertEquals("kw1:kw2:", model.getSelector());
	}

	@Test
	public void testGetUnarySelector() throws IOException, RecognitionException {
		model = build("unaryPattern.gsm");

		assertEquals(Lists.newArrayList("msg1"), model.getSelectorParts());
		assertEquals("msg1", model.getSelector());
	}

	@Test
	public void testMessages() {
		final List<String> expected = Lists.newArrayList("binaryMsg +[(3:12),(7:11),(7:16),(7:24)]", "unaryMsg m1[(3:17),(7:21)]",
				"keywordMsg kw1:kw2:[[(8:10),(8:17)],[(12:9),(12:16)]]", "keywordMsg value:[[(9:3)]]"

		);

		assertEquals(Joiner.on("\n").join(expected), Joiner.on("\n").join(model.getMessages(null)));
	}

	@Test
	public void testMethodType() {
		assertEquals(MethodModel.Type.KEYWORD, model.getType());
	}

	@Test
	public void testSymbols() {
		final List<String> expected = Lists.newArrayList("#symbol1[(11:2),(12:22)]", "#symbol2[(11:12)]");

		assertEquals(Joiner.on("\n").join(expected), Joiner.on("\n").join(model.getSymbols()));
	}

	@Test
	public void testUndeclaredIdentifiers() {
		final List<String> expected = Lists.newArrayList("identifier undeclared[(7:26),(9:10)]", "identifier Object[(12:2)]");

		assertEquals(Joiner.on("\n").join(expected), Joiner.on("\n").join(model.getUndeclaredIdentifiers()));
	}

	private MethodModel build(final String fileName) throws IOException, RecognitionException {
		return ParserUtils.build(getResource(fileName));
	}

	private String getResource(final String fileName) {
		return UiTestsPlugin.getDefault().getFileContents("/resources/parser/" + fileName);
	}
}
