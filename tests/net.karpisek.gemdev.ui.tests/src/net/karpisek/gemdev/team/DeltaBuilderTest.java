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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.compare.DeltaBuilder;
import net.karpisek.gemdev.compare.model.BehaviorDelta;
import net.karpisek.gemdev.compare.model.CategoryChanged;
import net.karpisek.gemdev.compare.model.CategoryDelta;
import net.karpisek.gemdev.compare.model.ClassAdded;
import net.karpisek.gemdev.compare.model.ClassChanged;
import net.karpisek.gemdev.compare.model.ClassDelta;
import net.karpisek.gemdev.compare.model.ClassRemoved;
import net.karpisek.gemdev.core.model.IClass;
import net.karpisek.gemdev.team.model.LocalClass;
import net.karpisek.gemdev.team.model.LocalMethod;
import net.karpisek.gemdev.ui.tests.IUnitTests;

@Category({ IUnitTests.class })
public class DeltaBuilderTest extends TeamTestCase {
	private LocalClass testClass;

	@Before
	public void setUp() throws IOException {
		testClass = readClass("FullClass.gsc");
	}

	@Test
	public void testAddedClass() {
		final ClassDelta delta = DeltaBuilder.diffClass(null, testClass);
		assertTrue(delta instanceof ClassAdded);
	}

	@Test
	public void testAddedMethod() {
		final LocalClass target = testClass.deepCopy();
		target.createMethod("action3", "cat1", "action3");

		final CategoryDelta delta = DeltaBuilder.diffCategory(testClass.getCategory("cat1"), target.getCategory("cat1"));
		assertTrue(delta instanceof CategoryChanged);
		assertEquals("[[+] action3]", ((CategoryChanged) delta).getMethodDeltas().toString());
	}

	@Test
	public void testCompareAddedCategory() {
		final BehaviorDelta delta = DeltaBuilder.diffBehavior(testClass.emptyCopy().getInstanceSide(), testClass.getInstanceSide());
		assertNotNull(delta);

		assertEquals("[[+] cat1, [+] cat2]", delta.getCategoryDeltas().toString());
	}

	@Test
	public void testCompareEmptyToMissingCategory() {
		final LocalClass source = testClass.emptyCopy();
		final LocalClass target = source.emptyCopy();
		target.createCategory("emptyCategory");

		final ClassDelta delta = DeltaBuilder.diffClass(source, target);
		assertNull("Expected no difference becasue empty and missing category should be considered equal", delta);
	}

	@Test
	public void testCompareMultipleClasses() {
		final List<IClass> source = Lists.newLinkedList();
		final List<IClass> target = Lists.newLinkedList();
		assertTrue(DeltaBuilder.diffClasses(source, target).isEmpty());

		final LocalClass c2 = new LocalClass(null, "C2");
		source.add(testClass);
		target.add(c2);
		assertEquals(Lists.newArrayList(new ClassAdded(c2), new ClassRemoved(testClass)), DeltaBuilder.diffClasses(source, target));

		final LocalClass testClass2 = testClass.emptyCopy();
		target.add(testClass2);
		assertEquals(Lists.newArrayList(new ClassAdded(c2), new ClassChanged(testClass, testClass2, null, null)), DeltaBuilder.diffClasses(source, target));
	}

	@Test
	public void testCompareOfNonExistingClasses() {
		assertNull(DeltaBuilder.diffClass(null, null));
	}

	@Test
	public void testCompareRemovedCategory() {
		final BehaviorDelta delta = DeltaBuilder.diffBehavior(testClass.getInstanceSide(), testClass.emptyCopy().getInstanceSide());
		assertNotNull(delta);

		assertEquals("[[-] cat1, [-] cat2]", delta.getCategoryDeltas().toString());
	}

	@Test
	public void testCompareSameBehavior() {
		assertNull(DeltaBuilder.diffBehavior(testClass.getInstanceSide(), testClass.getInstanceSide()));
	}

	@Test
	public void testCompareSameClass() {
		assertNull(DeltaBuilder.diffClass(testClass, testClass));
	}

	@Test
	public void testChangedMethod() {
		final LocalClass target = testClass.deepCopy();
		target.createMethod("action1", "cat2", "action1");

		final CategoryDelta delta = DeltaBuilder.diffCategory(testClass.getCategory("cat2"), target.getCategory("cat2"));
		assertTrue(delta instanceof CategoryChanged);
		assertEquals("[[!] action1]", ((CategoryChanged) delta).getMethodDeltas().toString());

		final BehaviorDelta delta1 = DeltaBuilder.diffBehavior(testClass.getInstanceSide(), target.getInstanceSide());
		assertEquals("[[!] cat2]", delta1.getCategoryDeltas().toString());
	}

	@Test
	public void testIllegalBehaviorArguments() {
		try {
			DeltaBuilder.diffBehavior(testClass.getInstanceSide(), null);
			fail("Should throw illegal argument exception");
		} catch (final IllegalArgumentException e) {
			// ok
		}
		try {
			DeltaBuilder.diffBehavior(null, testClass.getInstanceSide());
			fail("Should throw illegal argument exception");
		} catch (final IllegalArgumentException e) {
			// ok
		}
		try {
			DeltaBuilder.diffBehavior(testClass.getInstanceSide(), testClass.getClassSide());
			fail("Should throw illegal argument exception");
		} catch (final IllegalArgumentException e) {
			// ok
		}
	}

	@Test
	public void testRemovedClass() {
		final ClassDelta delta = DeltaBuilder.diffClass(testClass, null);
		assertTrue(delta instanceof ClassRemoved);
	}

	@Test
	public void testRemovedMethod() {
		final LocalClass target = testClass.deepCopy();
		((LocalMethod) target.getMethod("action1")).delete();

		final CategoryDelta delta = DeltaBuilder.diffCategory(testClass.getCategory("cat2"), target.getCategory("cat2"));
		assertTrue(delta instanceof CategoryChanged);
		assertEquals("[[-] action1]", ((CategoryChanged) delta).getMethodDeltas().toString());
	}

	private LocalClass readClass(final String name) throws IOException {
		return LocalClass.read(null, new StringReader(getResource(name)));
	}
}
