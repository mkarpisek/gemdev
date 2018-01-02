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

import java.util.List;
import java.util.Map;

import net.karpisek.gemdev.net.actions.MethodReference;

/**
 * Used for listening fo changes in session itself.
 */
public interface ISessionListener {
	/**
	 * Newly created classes were added to system. (with until now not existing names)
	 */
	public void addedClasses(List<DbClass> classes);

	public void addedMethodReferences(List<MethodReference> allAdded);

	/**
	 * Classes changed there definition (does not report changes in categories/methods, only in definition)
	 */
	public void changedDefinitionOfClasses(final Map<DbClass, DbClass> classes);

	/**
	 * Old classes were removed from system. (so nothing with that name does not exists)
	 */
	public void removedClasses(List<DbClass> classes);

	public void removedMethodReferences(List<MethodReference> allRemoved);
}
