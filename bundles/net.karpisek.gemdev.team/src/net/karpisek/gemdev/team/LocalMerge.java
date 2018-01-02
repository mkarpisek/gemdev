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
package net.karpisek.gemdev.team;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.model.ICategory;
import net.karpisek.gemdev.core.model.IClass;
import net.karpisek.gemdev.core.model.IMethod;
import net.karpisek.gemdev.team.model.LocalBehavior;
import net.karpisek.gemdev.team.model.LocalClass;
import net.karpisek.gemdev.team.model.LocalMethod;

/**
 * Database categories/methods are merged into local representation. Merge is performed on copy of target which is returned as result. Merge methods can be
 * invoked repeatedly.
 */
public class LocalMerge {
	private LocalClass result;

	public LocalMerge(final DbClass target) {
		result = new LocalClass(TeamPlugin.getDefault().getWorkingCopy(target.getSession()), target.getClassName());
		result.setSuperClassName(target.getSuperclassName());
		result.setCategoryName(target.getCategoryName());
		mergeClassContents(target);
	}

	/**
	 * Creates copy of target which will be used for merge operations.
	 * 
	 * @param target
	 */
	public LocalMerge(final LocalClass target) {
		result = target.deepCopy();
	}

	public LocalClass getResult() {
		return result;
	}

	/**
	 * Merges categories and their methods into target class. If category does not exist it is created, all methods are created there (if methods are in
	 * different categories they are moved and set source code). If category exists then: All methods which are not in source category are removed. All methods
	 * which exists in source category got source code from source method. All methods which does not exist in target category but in source category are
	 * created.
	 * 
	 * @param sourceCategories which should be merged into target class.
	 */
	public void mergeCategories(final List<ICategory> sourceCategories) {
		for (final ICategory sourceCategory : sourceCategories) {
			final List<IMethod> sourceMethods = sourceCategory.getMethods();
			final ICategory targetCategory = ((LocalBehavior) getResult().getBehavior(sourceCategory.isInstanceSide()))
					.createCategory(sourceCategory.getName());

			// merge new or changed methods
			mergeMethods(sourceMethods);

			// remove those existing locally, but not remotely
			final Set<String> sourceMethodNames = Sets.newHashSet();
			for (final IMethod m : sourceMethods) {
				sourceMethodNames.add(m.getName());
			}

			final List<IMethod> toBeRemoved = Lists.newLinkedList();
			for (final IMethod targetMethod : targetCategory.getMethods()) {
				if (!sourceMethodNames.contains(targetMethod.getName())) {
					toBeRemoved.add(targetMethod);
				}
			}
			for (final IMethod m : toBeRemoved) {
				((LocalMethod) m).delete();
			}
		}
	}

	/**
	 * Merges source class categories & methods into target class. Removes all target categories & methods and replaces them those from source class.
	 * 
	 * @param sourceClass to be merged
	 */
	public void mergeClassContents(final IClass sourceClass) {
		// throw away any method by making just copy of class declaration
		this.result = getResult().emptyCopy();

		// add all methods from remote class - (non empty) categories will be automatically created
		mergeCategories(sourceClass.getCategories(true));
		mergeCategories(sourceClass.getCategories(false));
	}

	/**
	 * Merges methods into target class. If method does not exist in target class it is created in correct category (category is created if necessary). If
	 * method exist in target class, but in different category it is moved into category of source method (category is created if necessary). Source code of
	 * target method is always set to that of source method.
	 * 
	 * @param sourceMethods which should be merged into target class.
	 */
	public void mergeMethods(final List<IMethod> sourceMethods) {
		for (final IMethod sourceMethod : sourceMethods) {
			final LocalBehavior targetBehavior = (LocalBehavior) getResult().getBehavior(sourceMethod.isInstanceSide());
			targetBehavior.createMethod(sourceMethod.getName(), sourceMethod.getCategory().getName(), sourceMethod.getSourceCode());
		}
	}
}
