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
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.collect.TreeMultimap;

import net.karpisek.gemdev.core.CorePlugin;
import net.karpisek.gemdev.core.Messages;
import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.net.actions.system.GetAllClassAndSuperclassNames;
import net.karpisek.gemdev.net.actions.system.GetAllMethods;
import net.karpisek.gemdev.net.actions.system.GetAllObjectNames;

/**
 * Default implementation of {@link IObjectCacheFactory}.
 */
public class ObjectCacheFactory implements IObjectCacheFactory {

	@Override
	public IObjectCache build(final ISession session, final IProgressMonitor monitor) {
		// fetching from DB
		final long t0 = System.currentTimeMillis();
		monitor.setTaskName(Messages.SYNCHRONIZE_OBJECT_NAMES_TASK_NAME);
		final Set<String> objectNames = session.execute(new GetAllObjectNames());
		if (monitor.isCanceled()) {
			return null;
		}

		final long t1 = System.currentTimeMillis();
		monitor.setTaskName(Messages.SYNCHRONIZE_CLASSES_TASK_NAME);
		final Map<String, String> classAndSuperclassNames = session.execute(new GetAllClassAndSuperclassNames());
		if (monitor.isCanceled()) {
			return null;
		}

		final long t2 = System.currentTimeMillis();
		monitor.setTaskName(Messages.ObjectCacheFactory_READING_METHODS);
		final Set<MethodReference> methodNames = session.execute(new GetAllMethods());
		if (monitor.isCanceled()) {
			return null;
		}

		// building
		final long t3 = System.currentTimeMillis();
		final Multimap<Selector, MethodReference> methodReferences = newMethodReferencesCollection();
		for (final MethodReference methodReference : methodNames) {
			methodReferences.put(new Selector(methodReference.getMethodName(), session), methodReference);
		}
		final long t4 = System.currentTimeMillis();

		final ClassHierarchyBuilder builder = new ClassHierarchyBuilder(classAndSuperclassNames, session);
		final Map<String, DbClass> classes = builder.getClasses();
		final long t5 = System.currentTimeMillis();

		if (monitor.isCanceled()) {
			return null;
		}

		// log everything
		final StringWriter sw = new StringWriter();
		sw.append(String.format("New cache created for '%s' in %f[s]", //$NON-NLS-1$
				session.getProject().getName(), (t5 - t0) / 1000.0f));
		sw.append("\n\t"); //$NON-NLS-1$
		sw.append(String.format("Fetch phase: total time %f[s] globals=%dx %f[s] classAndSuperclassNames=%dx %f[s] selectors=%dx %f[s]", //$NON-NLS-1$
				(t3 - t0) / 1000.0f, objectNames.size(), (t1 - t0) / 1000.0f, classAndSuperclassNames.size(), (t2 - t1) / 1000.0f, methodReferences.size(),
				(t3 - t2) / 1000.0f));
		sw.append("\n\t"); //$NON-NLS-1$
		sw.append(String.format("Build phase: classes=%dx %f[s] selectors=%dx %f[s]", //$NON-NLS-1$
				classes.size(), (t5 - t4) / 1000.0f, methodReferences.size(), (t4 - t3) / 1000.0f));
		sw.append("\n"); //$NON-NLS-1$
		CorePlugin.getDefault().logInfo(sw.toString());

		return new ObjectCache(session, objectNames, classes, methodReferences);
	}

	@Override
	public IObjectCache createEmpty(final ISession session) {
		return new ObjectCache(session, newObjectNamesCollection(), newClassesCollection(), newMethodReferencesCollection());
	}

	private HashMap<String, DbClass> newClassesCollection() {
		return Maps.newHashMap();
	}

	private TreeMultimap<Selector, MethodReference> newMethodReferencesCollection() {
		return TreeMultimap.create(new Comparator<Selector>() {
			@Override
			public int compare(final Selector o1, final Selector o2) {
				return o1.getName().compareTo(o2.getName());
			}
		}, new Comparator<MethodReference>() {

			@Override
			public int compare(final MethodReference o1, final MethodReference o2) {
				return o1.toString().compareTo(o2.toString());
			}

		});
	}

	private HashSet<String> newObjectNamesCollection() {
		return Sets.newHashSet();
	}
}
