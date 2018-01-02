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

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import net.karpisek.gemdev.net.actions.MethodReference;

/**
 * Default implementation of {@link IObjectCache} interface.
 */
public class ObjectCache implements IObjectCache {
	private final ISession session;
	private final Set<String> objectNames;
	private final Map<String, DbClass> classes;
	private final Multimap<DbBehavior, MethodReference> methods;
	private final Multimap<Selector, MethodReference> methodReferences;

	public ObjectCache(final ISession session, final Set<String> objectNames, final Map<String, DbClass> classes,
			final Multimap<Selector, MethodReference> methodReferences) {
		this.session = session;
		this.objectNames = objectNames;
		this.classes = classes;
		this.methodReferences = methodReferences;
		this.methods = LinkedListMultimap.create();
		for (final Entry<Selector, MethodReference> entry : methodReferences.entries()) {
			final MethodReference ref = entry.getValue();
			final DbClass c = classes.get(ref.getClassName());
			if (c != null) {
				final DbBehavior b = ref.isInstanceSide() ? c : c.getClassSide();
				methods.put(b, ref);
			}
		}
	}

	@Override
	public List<DbClass> addClasses(final DbClass... values) {
		final List<DbClass> list = Lists.newLinkedList();
		for (final DbClass c : values) {
			if (!classes.containsKey(c.getClassName())) {
				list.add(c);
				classes.put(c.getClassName(), c);
			}

			objectNames.add(c.getName());
		}
		return list;
	}

	@Override
	public List<MethodReference> addMethods(final MethodReference... values) {
		final List<MethodReference> list = Lists.newLinkedList();
		for (final MethodReference c : values) {
			if (methodReferences.put(new Selector(c.getMethodName(), session), c)) {
				list.add(c);
			}
		}
		return list;
	}

	@Override
	public DbClass getClass(final String name) {
		return classes.get(name);
	}

	@Override
	public List<DbClass> getClasses() {
		// TODO: use immutable sorted set as return value
		final List<DbClass> list = Lists.newLinkedList();
		list.addAll(classes.values());
		Collections.sort(list, new Comparator<DbClass>() {
			@Override
			public int compare(final DbClass o1, final DbClass o2) {
				return o1.getClassName().compareTo(o2.getClassName());
			}
		});
		return list;
	}

	@Override
	public List<MethodReference> getMethods() {
		final List<MethodReference> allRefs = Lists.newLinkedList();
		allRefs.addAll(methodReferences.values());
		return allRefs;
	}

	@Override
	public List<MethodReference> getMethods(final DbBehavior behavior) {
		final List<MethodReference> allRefs = Lists.newLinkedList();
		final Collection<MethodReference> list = methods.get(behavior);
		if (list != null) {
			allRefs.addAll(list);
		}
		return allRefs;
	}

	@Override
	public List<MethodReference> getMethods(final Selector selector) {
		// TODO: use immutable sorted set as return value
		final List<MethodReference> list = Lists.newLinkedList();
		list.addAll(methodReferences.get(selector));
		Collections.sort(list, new Comparator<MethodReference>() {
			@Override
			public int compare(final MethodReference o1, final MethodReference o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});
		return list;
	}

	@Override
	public Set<String> getObjectNames() {
		return objectNames;
	}

	@Override
	public List<Selector> getSelectors() {
		// TODO: use immutable sorted set as return value
		final List<Selector> list = Lists.newLinkedList();
		list.addAll(methodReferences.keySet());
		Collections.sort(list, new Comparator<Selector>() {
			@Override
			public int compare(final Selector o1, final Selector o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return list;
	}

	@Override
	public List<DbClass> removeClasses(final DbClass... values) {
		final List<DbClass> list = Lists.newLinkedList();
		for (final DbClass c : values) {
			if (classes.containsKey(c.getClassName())) {
				list.add(c);
				classes.remove(c.getClassName());
			}

			objectNames.remove(c.getName());
		}
		return list;
	}

	@Override
	public List<MethodReference> removeMethods(final MethodReference... values) {
		final List<MethodReference> list = Lists.newLinkedList();
		for (final MethodReference c : values) {
			if (methodReferences.remove(new Selector(c.getMethodName(), session), c)) {
				list.add(c);
			}
		}
		return list;
	}
}
