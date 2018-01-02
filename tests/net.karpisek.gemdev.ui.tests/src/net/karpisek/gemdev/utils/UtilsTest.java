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
package net.karpisek.gemdev.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import net.karpisek.gemdev.ui.tests.IUnitTests;

@Category({ IUnitTests.class })
public class UtilsTest {
	@Test
	public void testMd5Sum() {
		assertEquals("15b29ffdce66e10527a65bc6d71ad94d", Utils.md5sum("swordfish"));
	}
}
