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

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Maps;

import net.karpisek.gemdev.lang.ParserUtils.Result;
import net.karpisek.gemdev.lang.parser.SyntaxError;
import net.karpisek.gemdev.net.SessionClientTestCase;
import net.karpisek.gemdev.net.actions.clazz.GetMethodNames;
import net.karpisek.gemdev.net.actions.method.GetMethodSourceCode;

/**
 * This test parses all methods in DB for checking how compatible parser is. Compatibility is measured by number of methods parsed without error (if AST is what
 * it should be is not checked - that is little bit different question). Expects that session actions are working (all tests works).
 */
public class GsParserCompatibilityTest extends SessionClientTestCase {
	Map<String, List<SyntaxError>> failedMethods;

	private int totalParsedMethods;

	@Override
	@Before
	public void tearDown() throws Exception {
		super.setUp();

		totalParsedMethods = 0;
		failedMethods = Maps.newHashMap();
	}

	@Test
	public void test() throws IOException {
		final Map<String, String> sources = getSources("Object");
		for (final Map.Entry<String, String> entry : sources.entrySet()) {
			totalParsedMethods++;

			if ((totalParsedMethods % 100) == 0) {
				log("Parsed %d methods%n", totalParsedMethods);
			}

			final Result result = ParserUtils.parse(entry.getValue());
			if (!result.getSyntaxErrors().isEmpty()) {
				failedMethods.put(entry.getKey(), result.getSyntaxErrors());
			}
		}

		log("Total parsed: %d%n", totalParsedMethods);
		log("Failed to parse: %d%n", failedMethods.size());
		log("Failed methods:%n%s%n", failedMethods.keySet());

		assertEquals("Total number of parsed methods changed, expected failure rate will not apply any more", 330, totalParsedMethods);
	}

	private Map<String, String> getSources(final String className) {
		final Map<String, String> sources = Maps.newHashMap();
		sources.putAll(getSources(className, true));
		sources.putAll(getSources(className, false));
		return sources;
	}

	private Map<String, String> getSources(final String className, final boolean isInstanceSide) {
		final Map<String, String> sources = Maps.newHashMap();
		final Set<String> selectors = execute(new GetMethodNames(className, isInstanceSide));
		for (final String selector : selectors) {
			final String name = String.format("%s%s#%s", className, isInstanceSide ? "" : " class", selector);
			sources.put(name, execute(new GetMethodSourceCode(className, isInstanceSide, selector)));
		}
		return sources;
	}

	private void log(final String format, final Object... objects) {
		System.out.printf(format, objects);
	}
}
