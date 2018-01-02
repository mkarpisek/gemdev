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
 * Common interface for class and metaclass. DbClass and metaclass forms pair and in the model it is possible to switch between both.
 */
public interface IBehavior {
	/**
	 * Answer names of receiver method categories.
	 */
	List<ICategory> getCategories();

	/**
	 * Answer receiver method category.
	 */
	ICategory getCategory(String name);

	/**
	 * Answers name of class (always, even for metaclass it will answer name of relevant class)
	 */
	String getClassName();

	/**
	 * Answers DbMetaclass from DbClass<->DbMetaclass pair.
	 */
	IMetaclass getClassSide();

	/**
	 * Answers DbClass from DbClass<->DbMetaclass pair.
	 */
	IClass getInstanceSide();

	/**
	 * Answer receiver method.
	 */
	IMethod getMethod(String name);

	/**
	 * Answer names of receiver methods.
	 */
	List<IMethod> getMethods();

	/**
	 * Answers name of receiver in same format as is in smalltalk (with or without 'class' suffix).
	 */
	String getName();

	/**
	 * Answers if receiver is DbClass from DbClass<->DbMetaclass pair.
	 */
	boolean isInstanceSide();
}
