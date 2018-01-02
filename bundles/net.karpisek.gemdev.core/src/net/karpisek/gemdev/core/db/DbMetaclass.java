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
package net.karpisek.gemdev.core.db;

import net.karpisek.gemdev.core.model.IMetaclass;

/**
 * Implementation of class side of smalltalk class.
 * 
 * @see DbBehavior
 */
public class DbMetaclass extends DbBehavior implements IMetaclass {
	private final DbClass myClass;

	public DbMetaclass(final DbClass myClass) {
		super(myClass.getSession());
		this.myClass = myClass;
	}

	@Override
	public String getClassName() {
		return myClass.getClassName();
	}

	@Override
	public DbMetaclass getClassSide() {
		return this;
	}

	@Override
	public DbClass getInstanceSide() {
		return myClass;
	}

	@Override
	public String getName() {
		return getClassName() + " class"; //$NON-NLS-1$
	}

	@Override
	public DbMetaclass getSuperclass() {
		final DbClass isc = getInstanceSide().getSuperclass();
		if (isc == null) {
			return null;
		}
		return isc.getClassSide();
	}
}
