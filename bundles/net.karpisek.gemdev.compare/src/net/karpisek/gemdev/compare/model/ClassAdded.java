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
package net.karpisek.gemdev.compare.model;

import java.util.List;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.model.ICategory;
import net.karpisek.gemdev.core.model.IClass;

/**
 * 

 *
 */
public class ClassAdded extends ClassDelta {

	public ClassAdded(final IClass target) {
		super(null, target);
	}

	@Override
	public List<CategoryDelta> getCategoryDeltas() {
		final List<CategoryDelta> result = Lists.newLinkedList();
		for (final ICategory c : getObject().getCategories()) {
			result.add(new CategoryAdded(c));
		}
		return result;
	}

	@Override
	public String toString() {
		return String.format(ADDED_MESSAGE, super.toString());
	}

}
