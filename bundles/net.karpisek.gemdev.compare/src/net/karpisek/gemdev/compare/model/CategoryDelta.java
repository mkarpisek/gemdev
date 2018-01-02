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
public abstract class CategoryDelta extends Delta<ICategory> {

	public CategoryDelta(final ICategory source, final ICategory target) {
		super(source, target);
	}

	public abstract List<MethodDelta> getMethodDeltas();

	public boolean isInstanceSide() {
		return getObject().isInstanceSide();
	}

	@Override
	public String toString() {
		return getObject().getName();
	}
}
