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
import java.util.Set;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.karpisek.gemdev.core.db.DbBehavior;
import net.karpisek.gemdev.core.db.DbClass;

/**
 * DbClass hierarchy tree. Hierarchy is defined by superclass-subclass relations. DbClass tree can have multiple roots.
 */
public class ClassTree extends Tree<DbClass> {
	/**
	 * Creates tree where defined by leaves. Considers all input classes as leaves of tree and creates on top of them tree of superclasses. All leaf tree nodes
	 * are set as enabled, rest is disabled.
	 * 
	 * @param classes which are leaves
	 * @return newly created ClassTree instance
	 */
	public static ClassTree createFilteredTree(final List<DbClass> classes) {
		final BiMap<DbClass, TreeNode<DbClass>> nodes = HashBiMap.create();

		// create all paths where list is input class
		for (final DbClass c : classes) {
			final List<DbClass> path = getPath(c);
			Collections.reverse(path);
			path.add(c);
			createPath(path, nodes);
		}

		// compute roots (there can be multiple nodes with parent=null) so basically multiple trees are treated as one
		final List<TreeNode<DbClass>> roots = Lists.newLinkedList();
		for (final TreeNode<DbClass> node : nodes.values()) {
			if (node.getParent() == null) {
				roots.add(node);
			}
		}
		sort(roots);

		// for all non-input classes set their nodes as disabled
		final Set<DbClass> disabledClasses = Sets.newHashSet();
		disabledClasses.addAll(nodes.keySet());
		disabledClasses.removeAll(classes);
		for (final DbClass c : disabledClasses) {
			nodes.get(c).setEnabled(false);
		}

		return new ClassTree(roots, nodes);
	}

	/**
	 * Creates tree defined by one class. Tree consists of superclass chain and all subclasses of input class. All tree nodes are enabled by default.
	 * 
	 * @param c is input class
	 * @return newly created ClassTree instance
	 */
	public static ClassTree createFullTree(final DbClass c) {
		final BiMap<DbClass, TreeNode<DbClass>> nodes = HashBiMap.create();

		// superclass chain
		final List<DbClass> path = getPath(c);
		Collections.reverse(path);
		path.add(c);
		createPath(path, nodes);

		// subclasses
		createSubclassNodes(nodes.get(c), nodes);

		final List<TreeNode<DbClass>> roots = Lists.newLinkedList();
		roots.add(nodes.get(path.get(0)));
		return new ClassTree(roots, nodes);
	}

	public static List<DbClass> getPath(final DbClass b) {
		final List<DbClass> list = Lists.newLinkedList();
		for (final DbBehavior sc : b.getSuperclasses()) {
			list.add((DbClass) sc);
		}
		return list;
	}

	private static List<TreeNode<DbClass>> createPath(final List<DbClass> path, final BiMap<DbClass, TreeNode<DbClass>> nodes) {
		final List<TreeNode<DbClass>> pathNodes = Lists.newLinkedList();

		TreeNode<DbClass> last = null;
		for (final DbClass c : path) {
			TreeNode<DbClass> node = nodes.get(c);
			if (node == null) {
				node = new TreeNode<DbClass>(c);
				nodes.put(c, node);

				if (last != null) {
					node.setParent(last);
					last.getChildren().add(node);
					sort(last.getChildren());
				}
			}
			pathNodes.add(node);
			last = node;
		}
		return pathNodes;
	}

	private static void createSubclassNodes(final TreeNode<DbClass> node, final BiMap<DbClass, TreeNode<DbClass>> nodes) {
		final DbClass c = node.getValue();
		for (final DbClass sc : c.getSubclasses()) {
			final TreeNode<DbClass> scNode = new TreeNode<DbClass>(sc);
			nodes.put(sc, scNode);
			scNode.setParent(node);
			node.getChildren().add(scNode);
		}
		sort(node.getChildren());

		for (final TreeNode<DbClass> scNode : node.getChildren()) {
			createSubclassNodes(scNode, nodes);
		}
	}

	private static void sort(final List<TreeNode<DbClass>> nodes) {
		Collections.sort(nodes, new Comparator<TreeNode<DbClass>>() {
			@Override
			public int compare(final TreeNode<DbClass> o1, final TreeNode<DbClass> o2) {
				return o1.getValue().getClassName().compareTo(o2.getValue().getClassName());
			}
		});
	}

	public ClassTree(final List<TreeNode<DbClass>> roots, final BiMap<DbClass, TreeNode<DbClass>> nodes) {
		super(roots, nodes);
	}
}
