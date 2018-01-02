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
package net.karpisek.gemdev.net.actions.clazz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;

import net.karpisek.gemdev.net.SessionClientTestCase;
import net.karpisek.gemdev.net.UnhandledErrorException;

public class GetMethodNamesTest extends SessionClientTestCase {
	@Test
	public void testConvertingResponse() {
		final Set<String> actual = new GetMethodNames("ScaledDecimal", true).asResponse("at:\nat:put:\nnew\n");
		assertEquals(Sets.newHashSet("at:", "at:put:", "new"), actual);
	}

	@Test
	public void testConvertingResponseWithNoSelectors() {
		final Set<String> actual = new GetMethodNames("ScaledDecimal", true).asResponse("");
		assertEquals(Sets.newHashSet(), actual);
	}

	@Test
	public void testExecutingForClassSide() {
		final Set<String> expected = Sets.newHashSet("numerator:denominator:scale:", "_fromString:", "fromString:", "loadFrom:");
		final Set<String> actual = execute(new GetMethodNames("ScaledDecimal", false));
		assertEquals(expected, actual);
	}

	@Test
	public void testExecutingForInstanceSide() {
		final Set<String> expected = Sets.newHashSet("/", "asDecimalFloat", "asString", "odd", "_generality", "numerator", "-", "scale", "at:", "+", "=",
				"instVarAt:put:", "_numerator:denominator:scale:", "asFloat", "negated", "isZero", "_scale:", "size:", "asScaledDecimal", "<", "reciprocal",
				"truncated", ">=", "reduced", "_reduce", "writeTo:", "denominator", "at:put:", "asFraction", "*", "_coerce:", "even", "withScale:", "<=");
		final Set<String> actual = execute(new GetMethodNames("ScaledDecimal", true));
		assertEquals(expected, actual);
	}

	@Test
	public void testExecutingForInstanceSideInExistingCategory() {
		final Set<String> actual = execute(new GetMethodNames("ScaledDecimal", true, "Arithmetic"));
		assertEquals(Sets.newHashSet("*", "+", "-", "/", "negated", "reciprocal"), actual);
	}

	@Test
	public void testExecutingForInstanceSideInNonExistingCategory() {
		final Set<String> actual = execute(new GetMethodNames("ScaledDecimal", true, "_SOME_NON_EXISTIN_CATEGORY_NAME"));
		assertNull("Expected null because category does not exist", actual);
	}

	@Test
	public void testExecutingWithNonExistingClass() {
		try {
			execute(new GetMethodNames("SOMETHING_NON_EXISTING", true));
		} catch (final UnhandledErrorException e) {
			assertEquals("User defined error, Class SOMETHING_NON_EXISTING not found.", e.getMessage());
		}
	}

	/**
	 * method #match: is originally in standard category Comparing. but it is also defined in extension category *squeak and because GS always takes
	 * method(compiledMethodAt:, sourceCodeAt:, execution) from extension category, we try to simulate this behavior
	 */
	/*
	 * mka2011-08-20 disabled test because it is not in current gemstone image available
	 * 
	 * @Test public void testOverrides(){
	 * 
	 * final Set<String> names1 = execute(new GetMethodNames("CharacterCollection", true, "*squeak")); final Set<String> names2 = execute(new
	 * GetMethodNames("CharacterCollection", true, "Comparing"));
	 * 
	 * assertTrue("expected extension category will contain overiden method", names1.contains("match:"));
	 * assertFalse("expected standard category will not contain overiden method", names2.contains("match:")); }
	 */
}
