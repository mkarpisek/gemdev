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

public class GetAllInstVarNamesTest extends SessionClientTestCase {
	@Test
	public void testConverrtingResponseWithNoInstVarNames() {
		final Set<String> actual = new GetAllInstVarNames("Association").asResponse("");
		assertEquals(Sets.newHashSet(), actual);
	}

	@Test
	public void testConvertingResponse() {
		final Set<String> actual = new GetAllInstVarNames("Association").asResponse("key\nvalue\n");
		assertEquals(Sets.newHashSet("key", "value"), actual);
	}

	@Test
	public void testExecuting() {
		final Set<String> expected = Sets.newHashSet("key", "value");
		assertEquals(expected, execute(new GetAllInstVarNames("Association")));
	}
}
