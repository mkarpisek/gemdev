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

import net.karpisek.gemdev.net.actions.MethodReference;

/**
 * Smalltalk method.
 */
public interface IMethod {
	/**
	 * Answers {@link MethodReference} for receiver.
	 */
	MethodReference asReference();

	/**
	 * Answer behavior which owns this method.
	 */
	IBehavior getBehavior();

	/**
	 * Answers category of this method. Each method should be in some category.
	 */
	ICategory getCategory();

	/**
	 * Answers description of method in format behaviorName#methodName
	 */
	String getDescription();

	/**
	 * Answers description of method in format behaviorName#methodName [categoryName] @ projectName
	 */
	String getFullDescription();

	/**
	 * Answer method selector.
	 */
	String getName();

	/**
	 * Answers smalltalk source code for this method
	 */
	String getSourceCode();

	/**
	 * Answer if category is on DbClass or DbMetaclass.
	 * 
	 * @return true if it is DbClass category
	 */
	boolean isInstanceSide();
}
