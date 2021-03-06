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
import net.karpisek.gemdev.core.model.IMethod;

/**
 * 

 *
 */
public class CategoryRemoved extends CategoryDelta {

	public CategoryRemoved(final ICategory source) {
		super(source, null);
	}

	@Override
	public List<MethodDelta> getMethodDeltas() {
		final List<MethodDelta> result = Lists.newLinkedList();
		for (final IMethod m : getObject().getMethods()) {
			result.add(new MethodRemoved(m));
		}
		return result;
	}

	@Override
	public String toString() {
		return String.format(REMOVED_MESSAGE, super.toString());
	}

}
