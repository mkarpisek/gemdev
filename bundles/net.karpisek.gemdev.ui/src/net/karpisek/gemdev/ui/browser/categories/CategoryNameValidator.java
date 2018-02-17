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

import org.eclipse.jface.dialogs.IInputValidator;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

import net.karpisek.gemdev.core.model.ICategory;
import net.karpisek.gemdev.core.model.IClass;
import net.karpisek.gemdev.ui.Messages;

/**
 * Performs checks if tested string can be used as category name. Checks are: 1) name must not be empty 2) name must be unique.
 */
public class CategoryNameValidator implements IInputValidator {
	public static CategoryNameValidator on(final IClass targetClass, final boolean initialInstanceSide) {
		final Function<ICategory, String> collectNames = new Function<ICategory, String>() {
			@Override
			public String apply(final ICategory from) {
				return from.getName();
			}
		};
		
		return new CategoryNameValidator(targetClass.getName(), ImmutableSet.copyOf(Iterables.transform(targetClass.getCategories(true), collectNames)),
				ImmutableSet.copyOf(Iterables.transform(targetClass.getCategories(false), collectNames)), initialInstanceSide);
	}

	private final String className;
	private final ImmutableSet<String> instanceCategoryNames;
	private final ImmutableSet<String> classCategoryNames;

	private boolean instanceSide;

	public CategoryNameValidator(final String className, final ImmutableSet<String> instanceCategoryNames, final ImmutableSet<String> classCategoryNames,
			final boolean initialInstanceSide) {
		this.className = className;
		this.instanceCategoryNames = instanceCategoryNames;
		this.classCategoryNames = classCategoryNames;
		this.instanceSide = initialInstanceSide;
	}

	public String getClassName() {
		return className;
	}

	public boolean isInstanceSide() {
		return instanceSide;
	}

	@Override
	public String isValid(final String newText) {
		if (newText == null || newText.isEmpty()) {
			return Messages.CATEGORY_NAME_IS_EMPTY_ERROR;
		}

		final ImmutableSet<String> existingNames = isInstanceSide() ? instanceCategoryNames : classCategoryNames;

		if (existingNames.contains(newText)) {
			return Messages.CATEGORY_ALREADY_EXISTS_ERROR;
		}

		return null;
	}

	public void setInstanceSide(final boolean instanceSide) {
		this.instanceSide = instanceSide;
	}

}
