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
package net.karpisek.gemdev.team;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import net.karpisek.gemdev.core.db.DbBehavior;
import net.karpisek.gemdev.core.db.DbCategory;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.db.DbMethod;
import net.karpisek.gemdev.core.model.ICategory;
import net.karpisek.gemdev.core.model.IMethod;
import net.karpisek.gemdev.team.model.LocalClass;
import net.karpisek.gemdev.ui.tests.IUnitTests;

@Category({ IUnitTests.class })
public class LocalMergeTest extends TeamTestCase {
	private class MockCategory extends DbCategory {
		boolean instanceSide;
		private Set<String> methodNames;

		public MockCategory(final String name, final boolean instanceSide, final Set<String> methodNames) {
			super(name, null);
			this.instanceSide = instanceSide;
			this.methodNames = methodNames;
		}

		public MockCategory(final String name, final DbBehavior c) {
			super(name, c);
		}

		@Override
		public DbMethod getMethod(final String methodName) {
			if (!methodNames.contains(methodName)) {
				return null;
			}
			return new MockMethod(methodName, instanceSide, getName());
		}

		@Override
		public List<IMethod> getMethods() {
			final List<IMethod> result = Lists.newLinkedList();

			for (final String methodName : methodNames) {
				result.add(getMethod(methodName));
			}
			return result;
		}

		@Override
		public boolean isInstanceSide() {
			return instanceSide;
		}
	}

	private class MockClass extends DbClass {

		private final Multimap<String, String> instanceCategories;
		private final Multimap<String, String> classCategories;

		public MockClass(final String name, final Multimap<String, String> instanceCategories, final Multimap<String, String> classCategories) {
			super(name, null, null);

			this.instanceCategories = instanceCategories;
			this.classCategories = classCategories;
		}

		@Override
		public List<ICategory> getCategories(final boolean instanceSide) {
			Multimap<String, String> categories = instanceCategories;
			if (!instanceSide) {
				categories = classCategories;
			}

			final List<ICategory> result = Lists.newLinkedList();
			for (final String categoryName : categories.keySet()) {
				final Set<String> methodNames = Sets.newHashSet();
				methodNames.addAll(categories.get(categoryName));

				result.add(new MockCategory(categoryName, instanceSide, methodNames));
			}

			return result;
		}
	}

	private class MockMethod extends DbMethod {
		private final String sourceCode;

		public MockMethod(final String name, final boolean instanceSide, final String categoryName) {
			this(name, instanceSide, categoryName, name);
		}

		public MockMethod(final String name, final boolean instanceSide, final String categoryName, final String sourceCode) {
			super(name, new MockCategory(categoryName, instanceSide, Sets.newHashSet(name)));
			this.sourceCode = sourceCode;
		}

		@Override
		public String getSourceCode() {
			return sourceCode;
		}

		@Override
		public boolean isInstanceSide() {
			return getCategory().isInstanceSide();
		}
	}

	private LocalClass target;

	@Before
	public void setUp() throws Exception {
		final String s = getResource("LocalMerge.gsc");
		target = LocalClass.read(null, new StringReader(s));
	}

	@Test
	public void testMergeCategoryWithNewMethod() {
		final LocalMerge merge = new LocalMerge(target);
		final List<ICategory> list = Lists.newLinkedList();
		list.add(new MockCategory("iCat1", true, Sets.newHashSet("iMsg2", "iMsg2b")));
		merge.mergeCategories(list);
		assertHasMethods("[C1 class#cMsg1[cCat1], C1#iMsg2[iCat1], C1#iMsg2b[iCat1], C1#iMsg3[iCat2]]", merge.getResult());
		assertEquals("iMsg2", merge.getResult().getMethod("iMsg2", true).getSourceCode());// should be used new source
																							// code
	}

	@Test
	public void testMergeClass() throws Exception {
		final LocalMerge merge = new LocalMerge(target);

		final Multimap<String, String> instanceCategories = HashMultimap.create();
		instanceCategories.put("iCat1", "iMsg1"); // existing category/changed method
		final Multimap<String, String> classCategories = HashMultimap.create();
		classCategories.put("cCat2", "cMsg2"); // new category/new method

		merge.mergeClassContents(new MockClass("test", instanceCategories, classCategories));

		assertHasMethods("[C1 class#cMsg2[cCat2], C1#iMsg1[iCat1]]", merge.getResult());
		assertEquals("iMsg1", merge.getResult().getMethod("iMsg1", true).getSourceCode());// should be used new source
																							// code
	}

	@Test
	public void testMergeEmptyCategory() {
		final LocalMerge merge = new LocalMerge(target);
		final List<ICategory> list = Lists.newLinkedList();
		list.add(new MockCategory("iCat1", true, new HashSet<String>()));
		merge.mergeCategories(list);
		assertHasMethods("[C1 class#cMsg1[cCat1], C1#iMsg3[iCat2]]", merge.getResult());
	}

	@Test
	public void testMergeExistingMethodInDifferentCategory() throws Exception {
		final LocalMerge merge = new LocalMerge(target);
		final List<IMethod> newArrayList = Lists.newLinkedList();
		newArrayList.add(new MockMethod("iMsg3", true, "iCat1"));
		merge.mergeMethods(newArrayList);
		assertHasMethods("[C1 class#cMsg1[cCat1], C1#iMsg1[iCat1], C1#iMsg2[iCat1], C1#iMsg3[iCat1]]", merge.getResult());

		assertEquals("iMsg3", merge.getResult().getMethod("iMsg3", true).getSourceCode());// should be used new source
																							// code
	}

	@Test
	public void testMergeNewCategory() {
		final LocalMerge merge = new LocalMerge(target);
		final List<ICategory> list = Lists.newLinkedList();
		list.add(new MockCategory("cCat2", false, Sets.newHashSet("cMsg1")));
		merge.mergeCategories(list);
		assertHasMethods("[C1 class#cMsg1[cCat2], C1#iMsg1[iCat1], C1#iMsg2[iCat1], C1#iMsg3[iCat2]]", merge.getResult());
	}

	@Test
	public void testMergeNewMethodInExistingCategory() throws Exception {
		final LocalMerge merge = new LocalMerge(target);
		final List<IMethod> newArrayList = Lists.newLinkedList();
		newArrayList.add(new MockMethod("iMsg4", true, "iCat1"));
		newArrayList.add(new MockMethod("cMsg2", false, "cCat1"));
		merge.mergeMethods(newArrayList);
		assertHasMethods("[C1 class#cMsg1[cCat1], C1 class#cMsg2[cCat1], C1#iMsg1[iCat1], C1#iMsg2[iCat1], C1#iMsg3[iCat2], C1#iMsg4[iCat1]]",
				merge.getResult());
	}

	@Test
	public void testMergeNewMethodInNewCategory() throws Exception {
		final LocalMerge merge = new LocalMerge(target);
		final List<IMethod> list = Lists.newLinkedList();
		list.add(new MockMethod("iMsg4", true, "iCat3"));
		list.add(new MockMethod("cMsg2", false, "cCat2"));
		merge.mergeMethods(list);
		assertHasMethods("[C1 class#cMsg1[cCat1], C1 class#cMsg2[cCat2], C1#iMsg1[iCat1], C1#iMsg2[iCat1], C1#iMsg3[iCat2], C1#iMsg4[iCat3]]",
				merge.getResult());
	}
}
