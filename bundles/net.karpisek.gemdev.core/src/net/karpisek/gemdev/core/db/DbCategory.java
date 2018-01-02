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

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.model.ICategory;
import net.karpisek.gemdev.core.model.IMethod;
import net.karpisek.gemdev.net.actions.clazz.GetMethodNames;

/**
 * Representation of method category of class DB.
 */
public class DbCategory implements ICategory {
	private final String name;
	private final DbBehavior c;

	/**
	 * @param name of category
	 * @param c is class owning category (does not matter if in DB it is class or metaclass)
	 * @param instanceSide is true if category is on instance side of class or false for class side
	 */
	public DbCategory(final String name, final DbBehavior c) {
		this.name = name;
		this.c = c;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof DbCategory) {
			final DbCategory other = (DbCategory) obj;
			return Objects.equal(name, other.getName()) && Objects.equal(isInstanceSide(), other.isInstanceSide())
					&& Objects.equal(getBehavior(), other.getBehavior());
		}
		return false;
	}

	@Override
	public DbBehavior getBehavior() {
		return c;
	}

	@Override
	public IMethod getMethod(final String methodName) {
		Preconditions.checkNotNull(methodName);

		for (final IMethod method : getMethods()) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		return null;
	}

	/**
	 * Answer list of methods in this category.
	 */
	@Override
	public List<IMethod> getMethods() {
		final Set<String> names = c.getSession().execute(new GetMethodNames(c.getClassName(), isInstanceSide(), name));
		final List<IMethod> result = Lists.newArrayListWithExpectedSize(names.size());
		for (final String methodName : names) {
			result.add(new DbMethod(methodName, this));
		}
		return result;
	}

	@Override
	public String getName() {
		return name;
	}

	public ISession getSession() {
		return getBehavior().getSession();
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getName(), isInstanceSide(), getBehavior());
	}

	@Override
	public boolean isInstanceSide() {
		return getBehavior().isInstanceSide();
	}

	@Override
	public String toString() {
		return String.format("aCategory(name=%s class=%s %s)", name, c.getClassName(), (isInstanceSide() ? "instanceSide" : "classSide")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
