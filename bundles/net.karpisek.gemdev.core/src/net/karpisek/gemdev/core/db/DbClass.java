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

import org.eclipse.core.runtime.IAdaptable;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.karpisek.gemdev.core.model.IBehavior;
import net.karpisek.gemdev.core.model.ICategory;
import net.karpisek.gemdev.core.model.IClass;
import net.karpisek.gemdev.core.model.IMethod;
import net.karpisek.gemdev.net.actions.PrintIt;
import net.karpisek.gemdev.net.actions.clazz.GetAllInstVarNames;

/**
 * Implementation of instance side of smalltalk class.
 * 
 * @see DbBehavior
 */
public class DbClass extends DbBehavior implements IClass, IAdaptable {
	private final String name;
	private DbClass superclass;
	private final Set<DbClass> subclasses;

	private final DbMetaclass metaclass;

	/**
	 * Creates new named class in hierarchy.
	 * 
	 * @param name of class
	 * @param superclass of this class
	 * @param session in which class is created
	 */
	public DbClass(final String name, final DbClass superclass, final ISession session) {
		super(session);
		this.name = name;
		this.superclass = superclass;
		this.subclasses = Sets.newHashSet();
		this.metaclass = new DbMetaclass(this);

		if (superclass != null) {
			superclass.subclasses.add(this);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object getAdapter(final java.lang.Class adapter) {
		return null;
	}

	public IBehavior getBehavior(final boolean instanceSide) {
		if (instanceSide) {
			return getInstanceSide();
		}
		return getClassSide();
	}

	@Override
	public List<ICategory> getCategories(final boolean instanceSide) {
		return getBehavior(instanceSide).getCategories();
	}

	@Override
	public ICategory getCategory(final String categoryName, final boolean instanceSide) {
		return getBehavior(instanceSide).getCategory(categoryName);
	}

	/**
	 * Answers system category of receiver.
	 */
	@Override
	public String getCategoryName() {
		return execute(new PrintIt(String
				.format("|c| c := System myUserProfile symbolList objectNamed: '%s' asSymbol. c == nil ifTrue: [''] ifFalse: [c category]", getClassName())) { //$NON-NLS-1$
			@Override
			public boolean isIdempotent() {
				return true;
			}
		});
	}

	@Override
	public String getClassName() {
		return name;
	}

	@Override
	public DbMetaclass getClassSide() {
		return metaclass;
	}

	@Override
	public List<String> getClassVariables() {
		throw new RuntimeException("DbClass#getClassVariables not implemented yet"); //$NON-NLS-1$
	}

	public String getDefinition() {
		return getSession().execute(new PrintIt(String.format("%s definition", getClassName()))); //$NON-NLS-1$
	}

	@Override
	public DbClass getInstanceSide() {
		return this;
	}

	@Override
	public List<String> getInstanceVariables() {
		// TODO: this should be refactored (action uses set of names - that should be list of names)
		final List<String> result = Lists.newLinkedList();
		execute(new GetAllInstVarNames(getCategoryName()));
		return result;
	}

	@Override
	public IMethod getMethod(final String methodName, final boolean instanceSide) {
		return getBehavior(instanceSide).getMethod(methodName);
	}

	@Override
	public List<IMethod> getMethods(final boolean instanceSide) {
		return getBehavior(instanceSide).getMethods();
	}

	@Override
	public String getName() {
		return getClassName();
	}

	public Set<DbClass> getSubclasses() {
		return Sets.newHashSet(subclasses.iterator());
	}

	@Override
	public DbClass getSuperclass() {
		return superclass;
	}

	@Override
	public String getSuperclassName() {
		if (getSuperclass() == null) {
			return null;
		}
		return getSuperclass().getClassName();
	}

	@Override
	public boolean isInstanceSide() {
		return true;
	}

	/**
	 * Answers true if current user has this class in its session list.
	 */
	public boolean isInSymbolList() {
		return getSession().getCachedObjectNames().contains(getClassName());
	}

	/**
	 * @param superclass of this class or null if this class should become root
	 */
	public void setSuperclass(final DbClass superclass) {
		final DbClass currentSuperclass = getSuperclass();
		if (currentSuperclass != null) {
			currentSuperclass.subclasses.remove(this);
		}

		this.superclass = superclass;
		if (superclass != null) {
			this.superclass.subclasses.add(this);
		}
	}
}
