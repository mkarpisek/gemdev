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
package net.karpisek.gemdev.net.actions.method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;

import net.karpisek.gemdev.net.SessionClientTestCase;
import net.karpisek.gemdev.net.UnhandledErrorException;

public class GetMessagesSentByMethodTest extends SessionClientTestCase {
	@Test
	public void testExecuting() {
		final Set<String> expected = Sets.newHashSet("+", ">=", "at:", "at:put:", "new:", "size", "speciesForCollect", "value:");
		final Set<String> actual = execute(new GetMessagesSentByMethod("SequenceableCollection", true, "collect:"));

		assertEquals(expected, actual);
	}

	@Test
	public void testExecutingForNonExistingClass() {
		try {
			execute(new GetMessagesSentByMethod("SequenceableCollectionX", true, "collect:"));
			fail("Expected to throw class not found error");
		} catch (final UnhandledErrorException e) {
			assertEquals("User defined error, Class SequenceableCollectionX not found.", e.getMessage());
		}
	}

	@Test
	public void testExecutingForNonExistingMethod() {
		assertNull("Expected null in case method does not exists", execute(new GetMessagesSentByMethod("SequenceableCollection", true, "__x")));
	}
}
