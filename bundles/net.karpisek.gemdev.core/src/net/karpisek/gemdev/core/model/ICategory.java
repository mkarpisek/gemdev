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
package net.karpisek.gemdev.core.model;

import java.util.List;

/**
 * DbMethod category.
 */
public interface ICategory {
	/**
	 * Answer behavior which owns this category.
	 */
	IBehavior getBehavior();

	/**
	 * Answer method with required name or null if such method is not in this category.
	 */
	IMethod getMethod(final String name);

	/**
	 * Answer methods in this category.
	 */
	List<IMethod> getMethods();

	/**
	 * Answer category name;
	 */
	String getName();

	/**
	 * Answer if category is on DbClass or DbMetaclass.
	 * 
	 * @return true if it is DbClass category
	 */
	boolean isInstanceSide();
}
