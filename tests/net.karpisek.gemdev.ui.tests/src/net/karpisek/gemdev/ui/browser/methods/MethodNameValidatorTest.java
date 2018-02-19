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
package net.karpisek.gemdev.ui.browser.methods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.collect.Sets;

import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.tests.IUnitTests;

@Category({ IUnitTests.class })
public class MethodNameValidatorTest {
	private Set<String> existingNames;
	private MethodNameValidator validator;

	@Before
	public void tearDown() {
		existingNames = Sets.newHashSet("name1", "name2"); //$NON-NLS-1$//$NON-NLS-2$
		validator = new MethodNameValidator(existingNames);
	}

	@Test
	public void testEmptyNameError() {
		assertEquals(Messages.METHOD_NAME_IS_EMPTY_ERROR, validator.isValid("")); //$NON-NLS-1$
	}

	@Test
	public void testExistingNameError() {
		assertEquals(Messages.METHOD_ALREADY_EXISTS_ERROR, validator.isValid("name1")); //$NON-NLS-1$
	}

	@Test
	public void testValidName() {
		assertNull(validator.isValid("something")); //$NON-NLS-1$
	}

}
