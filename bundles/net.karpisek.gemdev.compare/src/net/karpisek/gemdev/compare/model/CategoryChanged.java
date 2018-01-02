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

import net.karpisek.gemdev.core.model.ICategory;

/**
 * 

 *
 */
public class CategoryChanged extends CategoryDelta {

	private final List<MethodDelta> methodDeltas;

	public CategoryChanged(final ICategory source, final ICategory target, final List<MethodDelta> methodDeltas) {
		super(source, target);

		this.methodDeltas = methodDeltas;
	}

	@Override
	public List<MethodDelta> getMethodDeltas() {
		return methodDeltas;
	}

	@Override
	public String toString() {
		return String.format(CHANGED_MESSAGE, super.toString());
	}
}
