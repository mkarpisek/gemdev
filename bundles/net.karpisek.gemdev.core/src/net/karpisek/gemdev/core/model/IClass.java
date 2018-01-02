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
 * Interface for DbClass in DbClass<->DbMetaclass pair.
 */
public interface IClass extends IBehavior {
	List<ICategory> getCategories(boolean instanceSide);

	ICategory getCategory(String name, boolean instanceSide);

	/**
	 * Answer system category of this class.
	 */
	String getCategoryName();

	/**
	 * Answer list of class variables in order as defined in smalltalk.
	 */
	List<String> getClassVariables();

	/**
	 * Answer list of instance variables in order as defined in smalltalk.
	 */
	List<String> getInstanceVariables();

	IMethod getMethod(String name, boolean instanceSide);

	List<IMethod> getMethods(boolean instanceSide);

	/**
	 * Answer name of superclass or null if receiver does not know its superclass name.
	 */
	String getSuperclassName();
}
