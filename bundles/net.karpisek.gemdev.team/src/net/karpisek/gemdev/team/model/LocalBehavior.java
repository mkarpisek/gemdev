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

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.karpisek.gemdev.core.model.IBehavior;
import net.karpisek.gemdev.core.model.ICategory;
import net.karpisek.gemdev.core.model.IMethod;

/**
 * In memory implementation of {@link IBehavior}.
 */
public abstract class LocalBehavior implements IBehavior {
	private final Map<String, ICategory> categories;
	private final Map<String, IMethod> methods;

	public LocalBehavior() {
		this.categories = Maps.newHashMap();
		this.methods = Maps.newHashMap();
	}

	public LocalCategory createCategory(final String name) {
		LocalCategory c = (LocalCategory) getCategory(name);
		if (c == null) {
			c = new LocalCategory(name, this);
			categories.put(c.getName(), c);
		}
		return c;
	}

	public LocalMethod createMethod(final String name, final String categoryName, final String sourceCode) {
		final IMethod oldMethod = getMethod(name);
		if (oldMethod != null) {
			((LocalMethod) oldMethod).delete();
		}

		final LocalMethod newMethod = new LocalMethod(createCategory(categoryName), name, sourceCode);
		add(newMethod);
		return newMethod;
	}

	@Override
	public List<ICategory> getCategories() {
		return Lists.newLinkedList(categories.values());
	}

	@Override
	public ICategory getCategory(final String name) {
		return categories.get(name);
	}

	@Override
	public IMethod getMethod(final String name) {
		return methods.get(name);
	}

	@Override
	public List<IMethod> getMethods() {
		return Lists.newLinkedList(methods.values());
	}

	@Override
	public boolean isInstanceSide() {
		return false;
	}

	void add(final LocalMethod method) {
		methods.put(method.getName(), method);
	}

	void remove(final LocalMethod method) {
		methods.remove(method.getName());
	}
}
