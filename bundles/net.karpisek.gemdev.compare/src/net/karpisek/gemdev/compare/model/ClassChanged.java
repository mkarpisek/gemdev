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

import net.karpisek.gemdev.core.model.IClass;

/**
 * 

 *
 */
public class ClassChanged extends ClassDelta {
	private final BehaviorDelta instanceDelta;
	private final BehaviorDelta classDelta;

	public ClassChanged(final IClass source, final IClass target, final BehaviorDelta instanceDelta, final BehaviorDelta classDelta) {
		super(source, target);

		this.instanceDelta = instanceDelta;
		this.classDelta = classDelta;
	}

	@Override
	public List<CategoryDelta> getCategoryDeltas() {
		final List<CategoryDelta> result = Lists.newLinkedList();
		if (instanceDelta != null) {
			result.addAll(instanceDelta.getCategoryDeltas());
		}
		if (classDelta != null) {
			result.addAll(classDelta.getCategoryDeltas());
		}
		return result;
	}

	public BehaviorDelta getClassDelta() {
		return classDelta;
	}

	public BehaviorDelta getInstanceDelta() {
		return instanceDelta;
	}

	@Override
	public String toString() {
		return String.format(CHANGED_MESSAGE, super.toString());
	}

}
