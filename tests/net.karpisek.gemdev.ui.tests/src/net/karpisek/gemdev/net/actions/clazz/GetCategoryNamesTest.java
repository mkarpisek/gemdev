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

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;

import net.karpisek.gemdev.net.SessionClientTestCase;

public class GetCategoryNamesTest extends SessionClientTestCase {
	@Test
	public void testConverrtingResponseWithNoCategoryNames() {
		final Set<String> actual = new GetCategoryNames("ScaledDecimal", true).asResponse("");
		assertEquals(Sets.newHashSet(), actual);
	}

	@Test
	public void testConvertingResponse() {
		final Set<String> actual = new GetCategoryNames("ScaledDecimal", true).asResponse("cat1\ncat2\ncat3\n");
		assertEquals(Sets.newHashSet("cat1", "cat2", "cat3"), actual);
	}

	@Test
	public void testExecutingForClassSide() {
		final Set<String> expected = Sets.newHashSet("Storing and Loading", "Private", "Instance Creation");
		assertEquals(expected, execute(new GetCategoryNames("ScaledDecimal", false)));
	}

	@Test
	public void testExecutingForInstanceSide() {
		final Set<String> expected = Sets.newHashSet("Storing and Loading", "Comparing", "Updating", "Truncation and Rounding", "Converting", "Formatting",
				"Accessing", "Testing", "Private", "Arithmetic");
		assertEquals(expected, execute(new GetCategoryNames("ScaledDecimal", true)));
	}
}
