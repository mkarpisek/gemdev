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

import net.karpisek.gemdev.core.model.IMethod;

/**
 * 

 *
 */
public class MethodRemoved extends MethodDelta {

	public MethodRemoved(final IMethod source) {
		super(source, null);
	}

	@Override
	public String toString() {
		return String.format(REMOVED_MESSAGE, super.toString());
	}
}
