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
import java.util.Set;

import net.karpisek.gemdev.net.actions.MethodReference;

public interface IObjectCache {
	List<DbClass> addClasses(final DbClass... values);

	List<MethodReference> addMethods(final MethodReference... values);

	DbClass getClass(String name);

	List<DbClass> getClasses();

	List<MethodReference> getMethods();

	List<MethodReference> getMethods(DbBehavior behavior);

	List<MethodReference> getMethods(Selector selector);

	Set<String> getObjectNames();

	List<Selector> getSelectors();

	List<DbClass> removeClasses(final DbClass... values);

	List<MethodReference> removeMethods(final MethodReference... values);

}
