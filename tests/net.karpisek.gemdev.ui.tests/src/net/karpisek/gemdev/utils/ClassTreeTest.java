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
package net.karpisek.gemdev.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.karpisek.gemdev.core.db.ClassHierarchyBuilder;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.ui.tests.IUnitTests;
import net.karpisek.gemdev.ui.tests.SessionAdapter;
import net.karpisek.gemdev.ui.utils.ClassTree;
import net.karpisek.gemdev.ui.utils.TreeNode;

@Category({ IUnitTests.class })
public class ClassTreeTest {
	private Map<String, DbClass> classes;
	private List<DbClass> classesList;

	public void assertEnabled(final boolean enabled, final ClassTree tree, final List<String> classNames) {
		final List<TreeNode<DbClass>> failed = Lists.newLinkedList();
		for (final String className : classNames) {
			final TreeNode<DbClass> node = tree.getNode(classes.get(className));
			assertNotNull("DbClass " + className + " not found", node);

			if (node.isEnabled() != enabled) {
				failed.add(node);
			}
		}
		assertTrue(String.format("Nodes %s does not have isEnabled=%b", failed.toString(), enabled), failed.isEmpty());
	}

	@Before
	public void setUp() throws Exception {
		final Map<String, String> classAndSuperClassNames = Maps.newHashMap();
		classAndSuperClassNames.put("C1", null);
		classAndSuperClassNames.put("C11", "C1");
		classAndSuperClassNames.put("C12", "C1");
		classAndSuperClassNames.put("C121", "C12");
		classAndSuperClassNames.put("C122", "C12");
		classAndSuperClassNames.put("C1221", "C122");
		classAndSuperClassNames.put("C1x", null);

		final ClassHierarchyBuilder builder = new ClassHierarchyBuilder(classAndSuperClassNames, new SessionAdapter());
		classes = builder.getClasses();
		classesList = Lists.newLinkedList();
		classesList.addAll(classes.values());
	}

	@Test
	public void testEmptyFilterdTree() throws Exception {
		classesList.clear(); // empty

		final ClassTree tree = ClassTree.createFilteredTree(classesList);
		assertEquals("", tree.toString());
		assertTrue(tree.getRoots().isEmpty());
	}

	@Test
	public void testFilteredTreeWithAllExistingClassesEnabled() throws Exception {
		final ClassTree tree = ClassTree.createFilteredTree(classesList);
		assertEquals("(C1 C11 (C12 C121 (C122 C1221)))C1x", tree.toString());

		assertEnabled(true, tree, Lists.newArrayList("C1", "C11", "C12", "C121", "C122", "C1221"));
		assertTreeHasRootsNamed(tree, Sets.newHashSet("C1", "C1x"));
	}

	@Test
	public void testFilteredTreeWithSomeClassesEnabled() throws Exception {
		final List<DbClass> leaves = Lists.newArrayList(classes.get("C11"), classes.get("C121"));
		final ClassTree tree = ClassTree.createFilteredTree(leaves);
		assertEquals("(C1 C11 (C12 C121))", tree.toString());

		assertEnabled(true, tree, Lists.newArrayList("C11", "C121"));
		assertEnabled(false, tree, Lists.newArrayList("C1", "C12"));
		assertTreeHasRootsNamed(tree, Sets.newHashSet("C1"));
	}

	@Test
	public void testFullTree() throws Exception {
		final ClassTree tree = ClassTree.createFullTree(classes.get("C12"));
		assertEquals("(C1 (C12 C121 (C122 C1221)))", tree.toString());

		assertEnabled(true, tree, Lists.newArrayList("C1", "C12", "C121", "C122", "C1221"));// all should be enabled by
																							// default
		assertTreeHasRootsNamed(tree, Sets.newHashSet("C1"));
	}

	@Test
	public void testFullTreeCreatedFromClassWithSuperclassChainlongerThanOne() throws Exception {
		final ClassTree tree = ClassTree.createFullTree(classes.get("C122"));
		assertEquals("(C1 (C12 (C122 C1221)))", tree.toString());
	}

	private void assertTreeHasRootsNamed(final ClassTree tree, final Set<String> expected) {
		final Set<String> actual = Sets.newHashSet();
		for (final TreeNode<DbClass> s : tree.getRoots()) {
			actual.add(s.getValue().getClassName());
		}

		assertEquals(expected, actual);
	}
}
