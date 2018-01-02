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

import com.google.common.base.Objects;

import net.karpisek.gemdev.core.model.IClass;

/**
 * 

 *
 */
public abstract class ClassDelta extends Delta<IClass> {

	public ClassDelta(final IClass source, final IClass target) {
		super(source, target);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof ClassDelta) {
			final ClassDelta other = ((ClassDelta) obj);
			return getSource() == other.getSource() && getTarget() == other.getTarget();
		}
		return false;
	}

	public abstract List<CategoryDelta> getCategoryDeltas();

	@Override
	public int hashCode() {
		return Objects.hashCode(getSource(), getTarget());
	}

	@Override
	public String toString() {
		return getObject().getName();
	}

}
