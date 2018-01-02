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
package net.karpisek.gemdev.ui.editor;

import static org.junit.Assert.assertEquals;

import org.eclipse.jface.text.Region;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import net.karpisek.gemdev.net.actions.CompilationError;
import net.karpisek.gemdev.ui.tests.IUnitTests;

@Category({ IUnitTests.class })
public class SourceCodeDecoratorTest {
	@Test
	public void test() {
		final CompilationError e = new CompilationError("TestActionName", "unexpected token", "TestRequest", 1034, 14, "testSourceCode&&&");
		final SourceCodeDecorator decorator = new SourceCodeDecorator(e);
		assertEquals("testSourceCode unexpected token->&&&", decorator.getDecoratedSourceCode());
		assertEquals(new Region(14, 19), decorator.getFirstDecoratedRegion());
	}
}
