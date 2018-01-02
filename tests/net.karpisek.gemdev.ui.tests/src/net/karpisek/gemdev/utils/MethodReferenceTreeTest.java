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

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.karpisek.gemdev.core.db.ClassHierarchyBuilder;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.ui.tests.IUnitTests;
import net.karpisek.gemdev.ui.tests.SessionAdapter;
import net.karpisek.gemdev.ui.utils.MethodReferenceTree;

@Category({ IUnitTests.class })
public class MethodReferenceTreeTest {
	private Map<String, DbClass> classes;
	private List<DbClass> classesList;

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
	public void testFilteredTree() throws Exception {
		final List<MethodReference> methods = Lists.newArrayList(new MethodReference("C12", true, "msg"), new MethodReference("C121", true, "a"),
				new MethodReference("C122", true, "instMsg"), new MethodReference("C122", true, "instMsg2"), new MethodReference("C122", false, "classMsg"),
				new MethodReference("C1221", true, "x"), new MethodReference("C1x", true, "c1xMsg"), new MethodReference("X", true, "xMsg"));

		final MethodReferenceTree tree = MethodReferenceTree.createFilteredTree(classesList, methods);
		// assertEquals("(C1 (C12 C12#msg (C122 C122#instMsg C122#instMsg2 C122
		// class#classMsg)))(C1x C1x#c1xMsg)(X X#xMsg)", tree.toString());
		assertEquals("(C12#msg C121#a C122 class#classMsg C122#instMsg (C122#instMsg2 C1221#x))C1x#c1xMsgX#xMsg", tree.toString());
	}
}
