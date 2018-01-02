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
 * In memory implementation of {@link ICategory}.
 */
public class LocalCategory implements ICategory {
	private final String name;
	private final LocalBehavior behavior;

	private final Map<String, IMethod> methods;

	public LocalCategory(final String name, final LocalBehavior behavior) {
		this.name = name;
		this.behavior = behavior;
		this.methods = Maps.newHashMap();
	}

	@Override
	public IBehavior getBehavior() {
		return behavior;
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
	public String getName() {
		return name;
	}

	@Override
	public boolean isInstanceSide() {
		return getBehavior().isInstanceSide();
	}

	void add(final LocalMethod method) {
		methods.put(method.getName(), method);
	}

	void remove(final LocalMethod method) {
		methods.remove(method.getName());
	}
}
