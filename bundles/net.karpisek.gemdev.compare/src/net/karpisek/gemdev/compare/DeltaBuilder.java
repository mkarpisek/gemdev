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
package net.karpisek.gemdev.compare;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.karpisek.gemdev.compare.model.BehaviorDelta;
import net.karpisek.gemdev.compare.model.CategoryAdded;
import net.karpisek.gemdev.compare.model.CategoryChanged;
import net.karpisek.gemdev.compare.model.CategoryDelta;
import net.karpisek.gemdev.compare.model.CategoryRemoved;
import net.karpisek.gemdev.compare.model.ClassAdded;
import net.karpisek.gemdev.compare.model.ClassChanged;
import net.karpisek.gemdev.compare.model.ClassDelta;
import net.karpisek.gemdev.compare.model.ClassRemoved;
import net.karpisek.gemdev.compare.model.MethodAdded;
import net.karpisek.gemdev.compare.model.MethodChanged;
import net.karpisek.gemdev.compare.model.MethodDelta;
import net.karpisek.gemdev.compare.model.MethodRemoved;
import net.karpisek.gemdev.core.model.IBehavior;
import net.karpisek.gemdev.core.model.ICategory;
import net.karpisek.gemdev.core.model.IClass;
import net.karpisek.gemdev.core.model.IMethod;

/**
 * Compares source and target objects structurally. TODO: difference of class definitions not implemented yet
 */
public class DeltaBuilder {
	private static final Comparator<CategoryDelta> CATEGORY_COMPARATOR = new Comparator<CategoryDelta>() {
		@Override
		public int compare(final CategoryDelta o1, final CategoryDelta o2) {
			return o1.getObject().getName().compareTo(o2.getObject().getName());
		}
	};

	private static final Comparator<MethodDelta> METHOD_COMPARATOR = new Comparator<MethodDelta>() {
		@Override
		public int compare(final MethodDelta o1, final MethodDelta o2) {
			return o1.getObject().getName().compareTo(o2.getObject().getName());
		}
	};

	/**
	 * Compares 2 behaviors structurally. Both behaviors must be on same side (both must represent class, or both has to be metaclasses)
	 * 
	 * @param source behavior
	 * @param target behavior
	 * @return delta object describing difference or null if no difference was found.
	 */
	public static BehaviorDelta diffBehavior(final IBehavior source, final IBehavior target) {
		Preconditions.checkArgument(source != null, "Source behavior must not be null."); //$NON-NLS-1$
		Preconditions.checkArgument(target != null, "Target behavior must not be null."); //$NON-NLS-1$
		Preconditions.checkArgument(source.isInstanceSide() == target.isInstanceSide(),
				"Source and target behavior must represent same side of Class<-> Metaclass pair."); //$NON-NLS-1$

		final List<CategoryDelta> deltas = Lists.newLinkedList();

		final Set<String> sourceNames = collectCategoryNames(source.getCategories());
		final Set<String> targetNames = collectCategoryNames(target.getCategories());

		// removed categories
		for (final String name : Sets.difference(sourceNames, targetNames)) {
			deltas.add(diffCategory(source.getCategory(name), null));
		}

		// added categories
		for (final String name : Sets.difference(targetNames, sourceNames)) {
			deltas.add(diffCategory(null, target.getCategory(name)));
		}

		// categories existing in both behaviors
		final Set<String> same = Sets.intersection(sourceNames, targetNames);
		for (final String name : same) {
			final CategoryDelta delta = diffCategory(source.getCategory(name), target.getCategory(name));
			if (delta != null) {
				deltas.add(delta);
			}
		}

		// remove all categories which does not contain any method deltas
		// this way we will remove problem with comparing missing and empty category
		final List<CategoryDelta> toBeRemoved = Lists.newLinkedList();
		for (final CategoryDelta delta : deltas) {
			if (delta.getMethodDeltas().isEmpty()) {
				toBeRemoved.add(delta);
			}
		}
		deltas.removeAll(toBeRemoved);

		if (deltas.isEmpty()) {
			return null;
		}

		Collections.sort(deltas, CATEGORY_COMPARATOR);

		return new BehaviorDelta(source, target, deltas);
	}

	public static CategoryDelta diffCategory(final ICategory source, final ICategory target) {
		if (source == null && target == null) {
			return null;
		}
		if (source == null) {
			return new CategoryAdded(target);
		}
		if (target == null) {
			return new CategoryRemoved(source);
		}
		Preconditions.checkArgument(source.isInstanceSide() == target.isInstanceSide(),
				"Source and target behavior must represent same side of Class<-> Metaclass pair."); //$NON-NLS-1$

		final List<MethodDelta> deltas = Lists.newLinkedList();

		final Set<String> sourceNames = collectMethodNames(source.getMethods());
		final Set<String> targetNames = collectMethodNames(target.getMethods());

		// removed methods
		for (final String categoryName : Sets.difference(sourceNames, targetNames)) {
			deltas.add(diffMethod(source.getMethod(categoryName), null));
		}

		// added methods
		for (final String categoryName : Sets.difference(targetNames, sourceNames)) {
			deltas.add(diffMethod(null, target.getMethod(categoryName)));
		}

		// methods existing in both categories
		final Set<String> same = Sets.intersection(sourceNames, targetNames);
		for (final String name : same) {
			final MethodDelta delta = diffMethod(source.getMethod(name), target.getMethod(name));
			if (delta != null) {
				deltas.add(delta);
			}
		}

		if (deltas.isEmpty()) {
			return null;
		}

		Collections.sort(deltas, METHOD_COMPARATOR);

		return new CategoryChanged(source, target, deltas);
	}

	/**
	 * Compares definition and structure of source class to target class.
	 * 
	 * @param source class
	 * @param target class
	 * @return delta object describing difference or null if no difference is found
	 */
	public static ClassDelta diffClass(final IClass source, final IClass target) {
		// first check for easy possibilities - one or another or both classes does not exist in their system
		if (source == null && target == null) {
			return null;
		}
		if (source == null) {
			return new ClassAdded(target);
		}
		if (target == null) {
			return new ClassRemoved(source);
		}
		// now it is clear we have to compare by content
		final BehaviorDelta instanceDelta = diffBehavior(source.getInstanceSide(), target.getInstanceSide());
		final BehaviorDelta classDelta = diffBehavior(source.getClassSide(), target.getClassSide());
		if (instanceDelta == null && classDelta == null) {
			return null;
		}
		return new ClassChanged(source, target, instanceDelta, classDelta);
	}

	/**
	 * Compares definition and structure of source collection of classes to target collection of classes.
	 * 
	 * @param source list of classes
	 * @param target list of classes
	 * @return list of class deltas
	 */
	public static List<ClassDelta> diffClasses(final List<IClass> source, final List<IClass> target) {
		final List<ClassDelta> result = Lists.newLinkedList();

		final Map<String, IClass> sourceMap = asMap(source);
		final Map<String, IClass> targetMap = asMap(target);

		final Set<String> processed = Sets.newHashSet();
		for (final Map.Entry<String, IClass> entry : sourceMap.entrySet()) {
			final IClass c = entry.getValue();

			if (!processed.contains(c.getName())) {
				final ClassDelta delta = diffClass(c, targetMap.get(c.getName()));
				if (delta != null) {
					result.add(delta);
				}
				processed.add(c.getName());
			}
		}

		for (final Map.Entry<String, IClass> entry : targetMap.entrySet()) {
			final IClass c = entry.getValue();

			if (!processed.contains(c.getName())) {
				final ClassDelta delta = diffClass(sourceMap.get(c.getName()), c);
				if (delta != null) {
					result.add(delta);
				}
				processed.add(c.getName());
			}
		}

		// sort deltas by name of class
		Collections.sort(result, new Comparator<ClassDelta>() {
			@Override
			public int compare(final ClassDelta o1, final ClassDelta o2) {
				return o1.getObject().getName().compareTo(o2.getObject().getName());
			}
		});

		return result;
	}

	public static MethodDelta diffMethod(final IMethod source, final IMethod target) {
		if (source == null && target == null) {
			return null;
		}
		if (source == null) {
			return new MethodAdded(target);
		}
		if (target == null) {
			return new MethodRemoved(source);
		}
		Preconditions.checkArgument(source.isInstanceSide() == target.isInstanceSide(),
				"Source and target behavior must represent same side of Class<-> Metaclass pair."); //$NON-NLS-1$

		if (!source.getSourceCode().equals(target.getSourceCode())) {
			return new MethodChanged(source, target);
		}
		return null;
	}

	private static Map<String, IClass> asMap(final List<IClass> source) {
		final Map<String, IClass> result = Maps.newHashMap();
		for (final IClass c : source) {
			result.put(c.getName(), c);
		}
		return result;
	}

	private static Set<String> collectCategoryNames(final List<ICategory> categories) {
		final Set<String> names = Sets.newHashSet();
		for (final ICategory c : categories) {
			names.add(c.getName());
		}
		return names;
	}

	private static Set<String> collectMethodNames(final List<IMethod> methods) {
		final Set<String> names = Sets.newHashSet();
		for (final IMethod c : methods) {
			names.add(c.getName());
		}
		return names;
	}
}
