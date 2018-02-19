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
package net.karpisek.gemdev.ui.browser.categories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.collect.ImmutableSet;

import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.tests.IUnitTests;

@Category({ IUnitTests.class })
public class CategoryNameValidatorTest {

	@Test
	public void testEmptyNameError() {
		assertEquals(Messages.CATEGORY_NAME_IS_EMPTY_ERROR, newValidator(true).isValid("")); //$NON-NLS-1$
	}

	@Test
	public void testExistingNameError_classSide() {
		assertEquals(Messages.CATEGORY_ALREADY_EXISTS_ERROR, newValidator(false).isValid("name3")); //$NON-NLS-1$

		assertNull(newValidator(false).isValid("name1"));
	}

	@Test
	public void testExistingNameError_instanceSide() {
		assertEquals(Messages.CATEGORY_ALREADY_EXISTS_ERROR, newValidator(true).isValid("name1")); //$NON-NLS-1$

		assertNull(newValidator(true).isValid("name3"));
	}

	@Test
	public void testValidName_bothSides() {
		assertNull(newValidator(true).isValid("something")); //$NON-NLS-1$

		assertNull(newValidator(false).isValid("something")); //$NON-NLS-1$
	}

	private CategoryNameValidator newValidator(final boolean initialInstanceSide) {
		return new CategoryNameValidator(getClass().getSimpleName(), ImmutableSet.of("name1", "name2"), ImmutableSet.of("name3"), initialInstanceSide);
	}

}
