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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ListenerList;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.utils.Pair;

/**
 * Default implementation of {@link ISession}. TODO: implement refresh
 */
public abstract class AbstractSession implements ISession {
	private final IProject project;
	private final ListenerList<ISessionListener> listeners;

	private final IObjectCacheFactory objectCacheFactory;
	private IObjectCache objectCache;

	public AbstractSession(final IProject project, final IObjectCacheFactory objectCacheFactory) {
		this.project = project;
		this.listeners = new ListenerList<>();

		this.objectCacheFactory = objectCacheFactory;
		this.objectCache = objectCacheFactory.createEmpty(this);
	}

	@Override
	public void addListener(final ISessionListener listener) {
		listeners.add(listener);
	}

	@Override
	public DbCategory createCategory(final DbBehavior receiver, final String categoryName) {
		createRemoteCategory(receiver, categoryName);
		return createLocalCategory(receiver, categoryName);
	}

	@Override
	public DbClass createClass(final String definition) {
		final Pair<String, String> pair = createRemoteClass(definition);
		if (pair == null) {
			return null;
		}
		return createLocalClass(pair.getKey(), pair.getValue());
	}

	@Override
	public DbMethod createMethod(final DbCategory category, final String sourceCode) {
		final String selector = createRemoteMethod(category, sourceCode);
		return createLocalMethod(category, selector);
	}

	@Override
	public void deleteCategory(final DbCategory c) {
		final List<MethodReference> deletedMethods = deleteRemoteCategory(c);
		deleteLocalCategory(c, deletedMethods);
	}

	@Override
	public void deleteClass(final DbClass c) {
		deleteRemoteClass(c);
		deleteLocalClass(c);
	}

	@Override
	public void deleteMethod(final DbMethod m) {
		deleteRemoteMethod(m);
		deleteLocalMethod(m);
	}

	@Override
	public DbClass getCachedClass(final String name) {
		return objectCache.getClass(name);
	}

	@Override
	public List<DbClass> getCachedClasses() {
		return objectCache.getClasses();
	}

	@Override
	public List<MethodReference> getCachedMethods(final DbBehavior behavior) {
		return objectCache.getMethods(behavior);
	}

	@Override
	public List<MethodReference> getCachedMethods(final Selector selector) {
		return objectCache.getMethods(selector);
	}

	@Override
	public Set<String> getCachedObjectNames() {
		return objectCache.getObjectNames();
	}

	@Override
	public List<Selector> getCachedSelectors() {
		return objectCache.getSelectors();
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public void refreshCachedValues(final IProgressMonitor monitor) {
		final IObjectCache newCache = objectCacheFactory.build(this, monitor);
		if (newCache != null && !monitor.isCanceled()) {
			final IObjectCache oldCache = objectCache;
			objectCache = newCache;

			// inform all listeners about newly created or deleted classes/methods
			final Set<DbClass> addedClasses = Sets.newHashSet(newCache.getClasses());
			addedClasses.removeAll(oldCache.getClasses());
			fireAddedClasses(Lists.newArrayList(addedClasses));

			final Set<DbClass> removedClasses = Sets.newHashSet(oldCache.getClasses());
			removedClasses.removeAll(newCache.getClasses());
			fireRemovedClasses(Lists.newArrayList(removedClasses));

			final Set<MethodReference> addedMethods = Sets.newHashSet(newCache.getMethods());
			addedClasses.removeAll(oldCache.getClasses());
			fireAddedMethods(Lists.newArrayList(addedMethods));

			final Set<MethodReference> removedMethods = Sets.newHashSet(oldCache.getMethods());
			removedClasses.removeAll(newCache.getClasses());
			fireRemovedMethods(Lists.newArrayList(removedMethods));
		}
	}

	@Override
	public void removeListener(final ISessionListener listener) {
		listeners.remove(listener);
	}

	private void addClasses(final DbClass... values) {
		final List<DbClass> list = objectCache.addClasses(values);
		if (!list.isEmpty()) {
			fireAddedClasses(list);
		}
	}

	private void addMethods(final MethodReference... values) {
		final List<MethodReference> list = objectCache.addMethods(values);
		if (!list.isEmpty()) {
			fireAddedMethods(list);
		}
	}

	private void fireAddedClasses(final List<DbClass> list) {
		for (final Object o : listeners.getListeners()) {
			((ISessionListener) o).addedClasses(list);
		}
	}

	private void fireAddedMethods(final List<MethodReference> list) {
		for (final Object o : listeners.getListeners()) {
			((ISessionListener) o).addedMethodReferences(list);
		}
	}

	private void fireRemovedClasses(final List<DbClass> list) {
		for (final Object o : listeners.getListeners()) {
			((ISessionListener) o).removedClasses(list);
		}
	}

	private void fireRemovedMethods(final List<MethodReference> list) {
		for (final Object o : listeners.getListeners()) {
			((ISessionListener) o).removedMethodReferences(list);
		}
	}

	private void removeClasses(final DbClass... values) {
		final List<DbClass> list = objectCache.removeClasses(values);
		if (!list.isEmpty()) {
			fireRemovedClasses(list);
		}
	}

	private void removeMethods(final MethodReference... values) {
		final List<MethodReference> list = objectCache.removeMethods(values);
		if (!list.isEmpty()) {
			fireRemovedMethods(list);
		}
	}

	protected DbCategory createLocalCategory(final DbBehavior receiver, final String categoryName) {
		return new DbCategory(categoryName, receiver);
	}

	protected DbClass createLocalClass(final String className, final String superclassName) {
		final DbClass c = new DbClass(className, objectCache.getClass(superclassName), this);
		addClasses(c);
		return c;
	}

	protected DbMethod createLocalMethod(final DbCategory category, final String selector) {
		final MethodReference ref = new MethodReference(category.getBehavior().getClassName(), category.isInstanceSide(), selector);
		addMethods(ref);

		return new DbMethod(selector, category);
	}

	protected abstract void createRemoteCategory(DbBehavior receiver, String categoryName);

	protected abstract Pair<String, String> createRemoteClass(final String definition);

	protected abstract String createRemoteMethod(final DbCategory category, final String sourceCode);

	protected void deleteLocalCategory(final DbCategory category, final List<MethodReference> categoryMethods) {
		removeMethods(categoryMethods.toArray(new MethodReference[categoryMethods.size()]));
	}

	protected void deleteLocalClass(final DbClass c) {
		c.setSuperclass(null);
		removeClasses(c);
	}

	protected void deleteLocalMethod(final DbMethod m) {
		removeMethods(new MethodReference(m.getBehavior().getClassName(), m.isInstanceSide(), m.getName()));
	}

	protected abstract List<MethodReference> deleteRemoteCategory(DbCategory category);

	protected abstract void deleteRemoteClass(DbClass c);

	protected abstract void deleteRemoteMethod(final DbMethod m);
}
