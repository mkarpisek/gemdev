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
package net.karpisek.gemdev.team.model;

import net.karpisek.gemdev.core.model.IClass;
import net.karpisek.gemdev.core.model.IMetaclass;

/**
 * In memory implementation of {@link IMetaclass}.
 */
public class LocalMetaclass extends LocalBehavior implements IMetaclass {
	private final LocalClass myClass;

	public LocalMetaclass(final LocalClass myClass) {
		this.myClass = myClass;
	}

	@Override
	public String getClassName() {
		return myClass.getClassName();
	}

	@Override
	public IMetaclass getClassSide() {
		return this;
	}

	@Override
	public IClass getInstanceSide() {
		return myClass;
	}

	@Override
	public String getName() {
		return getClassName() + " class"; //$NON-NLS-1$
	}

}
