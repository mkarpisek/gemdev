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
package net.karpisek.gemdev.compare.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.karpisek.gemdev.compare.DeltaBuilder;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.net.ISessionAction;
import net.karpisek.gemdev.net.SessionClientTestCase;
import net.karpisek.gemdev.net.actions.category.CreateCategory;
import net.karpisek.gemdev.net.actions.clazz.CreateClass;
import net.karpisek.gemdev.net.actions.clazz.GetCategoryNames;
import net.karpisek.gemdev.net.actions.clazz.GetMethodNames;
import net.karpisek.gemdev.net.actions.method.CreateMethod;
import net.karpisek.gemdev.net.actions.system.AbortTransaction;
import net.karpisek.gemdev.ui.tests.IIntegrationTests;
import net.karpisek.gemdev.ui.tests.SessionAdapter;

@Category({ IIntegrationTests.class })
public class ClassMembersComparatorTest extends SessionClientTestCase {
	private String classS1;
	private String classT1;
	private String emptyCategory;
	private String removedCategory;
	private String addedCategory;
	private String methodCategory;
	private String removedMethod;
	private String addedMethod;
	private String changedMethod;
	private String dummyMethod;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		emptyCategory = "emptyCategory";
		removedCategory = "removedCategory";
		addedCategory = "addedCategory";

		methodCategory = "methodCategory";

		removedMethod = "removedMethod";
		addedMethod = "addedMethod";
		changedMethod = "changedMethod";

		dummyMethod = "dummyMethod";

		execute(new AbortTransaction());
		classS1 = execute(new CreateClass.Builder("Object", "S1", "TestCategory").build());
		classT1 = execute(new CreateClass.Builder("Object", "T1", "TestCategory").build());
		execute(new CreateCategory(classS1, true, emptyCategory));
		execute(new CreateCategory(classT1, true, emptyCategory));

		execute(new CreateCategory(classS1, true, removedCategory));
		execute(new CreateCategory(classT1, true, addedCategory));

		execute(new CreateCategory(classS1, true, methodCategory));
		execute(new CreateCategory(classT1, true, methodCategory));

		execute(new CreateMethod(classS1, true, methodCategory, removedMethod));
		execute(new CreateMethod(classT1, true, methodCategory, addedMethod));
		execute(new CreateMethod(classS1, true, methodCategory, changedMethod));
		execute(new CreateMethod(classT1, true, methodCategory, changedMethod + "\n1+2"));

		execute(new CreateMethod(classS1, true, removedCategory, dummyMethod));
		execute(new CreateMethod(classT1, true, addedCategory, dummyMethod));

		assertEquals(Sets.newHashSet(emptyCategory, removedCategory, methodCategory), execute(new GetCategoryNames(classS1, true)));
		assertEquals(Sets.newHashSet(emptyCategory, addedCategory, methodCategory), execute(new GetCategoryNames(classT1, true)));

		assertEquals(Sets.newHashSet(removedMethod, changedMethod, dummyMethod), execute(new GetMethodNames(classS1, true)));
		assertEquals(Sets.newHashSet(addedMethod, changedMethod, dummyMethod), execute(new GetMethodNames(classT1, true)));
	}

	@Override
	@After
	public void tearDown() throws Exception {
		execute(new AbortTransaction());
		super.tearDown();
	}

	@Test
	public void test() throws Exception {
		final ISession session = new SessionAdapter() {
			@Override
			public <T> T execute(final ISessionAction<T> action) {
				return client.execute(action);
			}
		};
		final DbClass source = new DbClass(classS1, null, session);
		final DbClass target = new DbClass(classT1, null, session);

		final ClassDelta delta = DeltaBuilder.diffClass(source, target);

		assertNotNull(delta);

		final List<String> expected = Lists.newArrayList("[+] addedCategory", "[!] methodCategory", "[-] removedCategory");
		final List<CategoryDelta> deltas = delta.getCategoryDeltas();
		assertEquals(Joiner.on("\n").join(expected), Joiner.on("\n").join(deltas));

		final List<String> mexpected = Lists.newArrayList("[+] addedMethod", "[!] changedMethod", "[-] removedMethod");
		assertEquals(Joiner.on("\n").join(mexpected), Joiner.on("\n").join(deltas.get(1).getMethodDeltas()));
	}
}
