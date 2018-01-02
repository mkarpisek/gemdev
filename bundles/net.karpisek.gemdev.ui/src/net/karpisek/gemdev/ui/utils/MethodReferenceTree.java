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
package net.karpisek.gemdev.ui.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import net.karpisek.gemdev.core.db.DbBehavior;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.net.actions.MethodReference;

/**
 * This tree arranges method references in tree defined by class hierarchy. For each referenced class there is class node, references to methods of this class
 * are created as children of this node.
 */
public class MethodReferenceTree extends Tree<MethodReference> {
	public static class MethodReferenceTreeNode extends TreeNode<MethodReference> {
		private final String className;

		public MethodReferenceTreeNode(final MethodReference ref, final String className) {
			super(ref);
			this.className = className;
		}

		public String getClassName() {
			return className;
		}

		@Override
		public String toString() {
			if (getValue() == null) {
				return className;
			}
			return super.toString();
		}
	}

	/**
	 * Creates filtered method tree (hierarchy based on classes)
	 * 
	 * @param existingClasses which are used for definition of class hierarchy
	 * @param methods from which tree has to be constructed (leaves of final tree)
	 * @return tree instance
	 */
	public static MethodReferenceTree createFilteredTree(final List<DbClass> existingClasses, final List<MethodReference> methods) {
		final Map<String, DbClass> existingClassesMap = Maps.newHashMap();
		for (final DbClass c : existingClasses) {
			existingClassesMap.put(c.getClassName(), c);
		}

		final Multimap<String, MethodReferenceTreeNode> classNameToMethodNodes = LinkedListMultimap.create();
		final BiMap<MethodReference, MethodReferenceTreeNode> nodes = HashBiMap.create();

		for (final MethodReference m : Sets.newHashSet(methods.iterator())) {
			final MethodReferenceTreeNode node = new MethodReferenceTreeNode(m, m.getClassName());
			classNameToMethodNodes.put(m.getClassName(), node);
			nodes.put(m, node);
		}

		final Map<String, List<MethodReferenceTreeNode>> nodesByClassName = Maps.newHashMap();
		for (final String className : classNameToMethodNodes.keySet()) {
			final List<MethodReferenceTreeNode> sortedNodes = Lists.newArrayList();
			sortedNodes.addAll(classNameToMethodNodes.get(className));

			Collections.sort(sortedNodes, new Comparator<MethodReferenceTreeNode>() {
				@Override
				public int compare(final MethodReferenceTreeNode o1, final MethodReferenceTreeNode o2) {
					return o1.toString().compareTo(o2.toString());
				}
			});

			nodesByClassName.put(className, sortedNodes);
		}

		final List<MethodReferenceTreeNode> roots = Lists.newLinkedList();
		for (final String className : asSortedCollection(nodesByClassName.keySet())) {
			final List<MethodReferenceTreeNode> list = nodesByClassName.get(className);

			// list has always size>0
			final MethodReferenceTreeNode node = getFirst(list);

			final DbClass c = existingClassesMap.get(node.getValue().getClassName());
			if (c == null) {
				roots.addAll(list);
			} else {
				boolean connectedToParent = false;
				for (final DbBehavior sc : c.getSuperclasses()) {
					if (!connectedToParent) {
						final List<MethodReferenceTreeNode> scNode = nodesByClassName.get(sc.getClassName());
						if (scNode != null) {
							final MethodReferenceTreeNode parent = getLast(scNode);

							for (final MethodReferenceTreeNode n : list) {
								n.setParent(parent);
								parent.getChildren().add(n);
							}

							connectedToParent = true;
						}
					}
				}
				if (!connectedToParent) {
					roots.addAll(list);
				}
			}
		}

		return new MethodReferenceTree(roots, nodes);
	}

	private static List<String> asSortedCollection(final Set<String> keySet) {
		final List<String> result = Lists.newLinkedList();
		result.addAll(keySet);
		Collections.sort(result);
		return result;
	}

	private static MethodReferenceTreeNode getFirst(final List<MethodReferenceTreeNode> list) {
		Preconditions.checkArgument(list.size() >= 1);

		return list.get(0);
	}

	private static MethodReferenceTreeNode getLast(final List<MethodReferenceTreeNode> list) {
		Preconditions.checkArgument(list.size() >= 1);

		return list.get(list.size() - 1);
	}

	protected MethodReferenceTree(final List<MethodReferenceTreeNode> roots, final BiMap<MethodReference, MethodReferenceTreeNode> nodes) {
		super(roots, nodes);
	}
}
