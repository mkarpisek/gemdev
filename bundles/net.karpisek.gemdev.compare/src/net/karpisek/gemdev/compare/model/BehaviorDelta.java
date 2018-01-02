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

import net.karpisek.gemdev.core.model.IBehavior;

public class BehaviorDelta extends Delta<IBehavior> {
	private final List<CategoryDelta> categoryDeltas;

	public BehaviorDelta(final IBehavior source, final IBehavior target, final List<CategoryDelta> categoryDeltas) {
		super(source, target);

		this.categoryDeltas = categoryDeltas;
	}

	public List<CategoryDelta> getCategoryDeltas() {
		return categoryDeltas;
	}

}
