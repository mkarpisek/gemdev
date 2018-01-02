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

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.model.IBehavior;
import net.karpisek.gemdev.core.model.ICategory;
import net.karpisek.gemdev.core.model.IMethod;
import net.karpisek.gemdev.net.ISessionAction;
import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.net.actions.clazz.GetCategoryNames;
import net.karpisek.gemdev.net.actions.clazz.GetMethodCategoryName;
import net.karpisek.gemdev.net.actions.clazz.HasClassCategoryNamed;
import net.karpisek.gemdev.net.actions.clazz.HasClassMethodNamed;

/**
 * Common superclass of DbClass and DbMetaclass. Smalltalk class is represented by pair of objects (instance of DbClass and DbMetaclass) connected together.
 * DbClass in this pair represents instance side and DbMetaclass class side. DbBehavior implements functionality common for both subclassses. These are
 * categories and method organisation.
 */
public abstract class DbBehavior implements IBehavior {
	private final ISession session;

	public DbBehavior(final ISession session) {
		this.session = session;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof DbBehavior) {
			final DbBehavior other = (DbBehavior) obj;
			if (getSession() == null || other.getSession() == null) {
				return false;
			}
			return Objects.equal(isInstanceSide(), other.isInstanceSide()) && Objects.equal(getClassName(), other.getClassName())
					&& Objects.equal(getSession(), other.getSession());
		}
		return false;
	}

	public List<MethodReference> getCachedMethodsYouAreResponding() {
		final List<DbBehavior> inheritanceChain = Lists.newLinkedList();
		inheritanceChain.add(this);
		inheritanceChain.addAll(getSuperclasses());

		if (!isInstanceSide()) {
			final DbClass c = getSession().getCachedClass("Class");
			if (c != null) {
				inheritanceChain.add(c);
				inheritanceChain.addAll(c.getSuperclasses());
			}
		}

		final List<MethodReference> methods = Lists.newLinkedList();
		for (final DbBehavior b : inheritanceChain) {
			methods.addAll(b.getSession().getCachedMethods(b));
		}
		return methods;
	}

	/**
	 * Answers receiver categories.
	 */
	@Override
	public List<ICategory> getCategories() {
		Preconditions.checkNotNull(getSession());

		final Set<String> names = getSession().execute(new GetCategoryNames(getClassName(), isInstanceSide()));

		final List<ICategory> result = Lists.newLinkedList();
		for (final String categoryName : names) {
			result.add(new DbCategory(categoryName, this));
		}
		return result;
	}

	/**
	 * Answers receivers category or null if class on DB side does not have any such category.
	 */
	@Override
	public DbCategory getCategory(final String categoryName) {
		if (execute(new HasClassCategoryNamed(getClassName(), isInstanceSide(), categoryName))) {
			return new DbCategory(categoryName, this);
		}
		return null;
	}

	/**
	 * Answers name of class. DbMetaclass also answers name of class (which is difference from smalltalk).
	 */
	@Override
	public abstract String getClassName();

	/**
	 * Answers receivers method or null if class on DB side does not have any such method.
	 */
	@Override
	public DbMethod getMethod(final String methodName) {
		if (execute(new HasClassMethodNamed(getClassName(), isInstanceSide(), methodName))) {
			final String categoryName = execute(new GetMethodCategoryName(getClassName(), isInstanceSide(), methodName));
			if (categoryName != null) {
				return new DbMethod(methodName, new DbCategory(categoryName, this));
			}
		}
		return null;
	}

	@Override
	public List<IMethod> getMethods() {
		// TODO: fetching all methods of behavior could be optimized
		final List<IMethod> result = Lists.newLinkedList();
		for (final ICategory c : getCategories()) {
			result.addAll(c.getMethods());
		}
		return result;
	}

	/**
	 * Answers session in which is this object created.
	 */
	public ISession getSession() {
		return session;
	}

	public abstract DbBehavior getSuperclass();

	/**
	 * @return list of all super classes (excluding class itself) from superclass of self to root
	 */
	public List<DbBehavior> getSuperclasses() {
		final LinkedList<DbBehavior> result = Lists.newLinkedList();
		DbBehavior c = getSuperclass();
		for (;;) {
			if (c == null) {
				return result;
			}
			result.add(c);
			c = c.getSuperclass();
		}
	}

	@Override
	public int hashCode() {
		if (getSession() == null) {
			return Objects.hashCode(getSession());
		}

		return Objects.hashCode(isInstanceSide(), getClassName(), getSession());
	}

	/**
	 * Answers if receiver is DbClass.
	 */
	@Override
	public boolean isInstanceSide() {
		return false;
	}

	@Override
	public String toString() {
		return getClassName();
	}

	/**
	 * Execute action in session of receiver.
	 * 
	 * @param <T>
	 * @param action
	 * @return
	 */
	<T> T execute(final ISessionAction<T> action) {
		return session.execute(action);
	}
}
