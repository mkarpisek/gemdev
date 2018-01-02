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
package net.karpisek.gemdev.net.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;

import net.karpisek.gemdev.net.SessionClientTestCase;

public class ExportSetTest extends SessionClientTestCase {
	@Test
	public void test() throws Exception {
		final String oop1 = execute(new Evaluate("1+2"));
		final String oop2 = execute(new Evaluate("2+3"));

		assertExportSetIncludesAll(Sets.newHashSet(oop1, oop2));
		execute(new RemoveFromExportSet(oop1));

		assertExportSetDoesNotIncludesAny(Sets.newHashSet(oop1));
		assertExportSetIncludesAll(Sets.newHashSet(oop2));

		execute(new RemoveFromExportSet(oop2));
		assertExportSetDoesNotIncludesAny(Sets.newHashSet(oop1, oop2));
	}

	@Test
	public void testEmptyResponse() {
		assertEquals(Sets.newHashSet(), new GetExportSet().asResponse(""));
	}

	@Test
	public void testResponse() {
		assertEquals(Sets.newHashSet("0", "1", "2"), new GetExportSet().asResponse("2\n1\n0\n"));
	}

	private void assertExportSetDoesNotIncludesAny(final Set<String> oopsOfExportSetObjects) {
		final Set<String> exportSet = execute(new GetExportSet());

		for (final String oop : oopsOfExportSetObjects) {
			assertFalse("Export set contains object with oop " + oop + " which should not be there", exportSet.contains(oop));
		}
	}

	private void assertExportSetIncludesAll(final Set<String> oopsOfExportSetObjects) {
		final Set<String> exportSet = execute(new GetExportSet());

		for (final String oop : oopsOfExportSetObjects) {
			assertFalse("Export set does not contain object with oop " + oop + " which should be there", !exportSet.contains(oop));
		}
	}

}
