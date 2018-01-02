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

import java.io.StringWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

/**
 * Utility class for construction of hierarchy of classes from pairs of names of class and superclass. Classes which has as superclass null, "nil" or uknown
 * class name are created as roots of hierarchy. In usual conditions there should be always only one root (usually Object), but in case of non standard
 * environment there can be more than one root class.
 */
public class ClassHierarchyBuilder {
	private final ISession session;
	private final List<DbClass> rootClasses;
	private final Map<String, DbClass> allClasses;

	public ClassHierarchyBuilder(final Map<String, String> classAndSuperclassNames, final ISession session) {
		this.session = session;
		this.rootClasses = Lists.newLinkedList();
		this.allClasses = Maps.newHashMap();

		final List<String> rootClassNames = Lists.newLinkedList();
		final Multimap<String, String> subclassNames = LinkedHashMultimap.create();

		// first collect for each class its subclasses
		for (final Map.Entry<String, String> entry : classAndSuperclassNames.entrySet()) {
			final String className = entry.getKey();
			final String superclassName = entry.getValue();

			if (superclassName == null || "nil".equals(superclassName) || !classAndSuperclassNames.containsKey(superclassName)) { //$NON-NLS-1$
				rootClassNames.add(className);
			} else {
				subclassNames.put(superclassName, className);
			}
		}

		// recursively go from roots down and create all classses
		for (final String className : rootClassNames) {
			final DbClass c = createRootClass(className);
			createSubclasses(c, subclassNames);
		}
	}

	public Map<String, DbClass> getClasses() {
		return allClasses;
	}

	@Override
	public String toString() {
		final StringWriter w = new StringWriter();
		for (final DbClass root : rootClasses) {
			printHierarchy(w, root);
			w.append(' ');
		}
		return w.toString().trim();
	}

	private DbClass createRootClass(final String className) {
		final DbClass c = new DbClass(className, null, session);
		rootClasses.add(c);
		allClasses.put(c.getClassName(), c);
		Collections.sort(rootClasses, new Comparator<DbClass>() {
			@Override
			public int compare(final DbClass o1, final DbClass o2) {
				return o1.getClassName().compareTo(o2.getClassName());
			}
		});
		return c;
	}

	private List<DbClass> createSubclasses(final DbClass c, final Multimap<String, String> superclassToSubclassNames) {
		Collection<String> subclassNames = superclassToSubclassNames.get(c.getClassName());
		if (subclassNames == null) {
			subclassNames = Lists.newLinkedList();
		}

		final List<String> sortedNames = Lists.newArrayList(subclassNames.iterator());
		Collections.sort(sortedNames);

		final List<DbClass> subclasses = Lists.newLinkedList();
		for (final String subclassName : sortedNames) {
			final DbClass subclass = new DbClass(subclassName, c, session);
			subclasses.add(subclass);
			allClasses.put(subclass.getClassName(), subclass);

			createSubclasses(subclass, superclassToSubclassNames);
		}

		return subclasses;
	}

	/**
	 * Print class and its subclasses in lisp-like string.
	 * 
	 * @param writer to which hierarchy sohould be printed.
	 * @param c is root of class tree which should be printed.
	 */
	private void printHierarchy(final StringWriter writer, final DbClass c) {
		Preconditions.checkNotNull(c);

		final Set<DbClass> subclasses = c.getSubclasses();
		if (subclasses.isEmpty()) {
			writer.append(c.getClassName());
		} else {
			writer.append("(").append(c.getClassName()); //$NON-NLS-1$
			for (final DbClass sc : subclasses) {
				writer.append(" "); //$NON-NLS-1$
				printHierarchy(writer, sc);
			}
			writer.append(")"); //$NON-NLS-1$
		}
	}

}
