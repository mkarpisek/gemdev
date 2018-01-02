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
package net.karpisek.gemdev.ui.inspector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.progress.IElementCollector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.net.ISessionAction;
import net.karpisek.gemdev.net.SessionClientTestCase;
import net.karpisek.gemdev.net.actions.Evaluate;
import net.karpisek.gemdev.net.actions.RemoveFromExportSet;
import net.karpisek.gemdev.ui.tests.SessionAdapter;

public class InspectorTest extends SessionClientTestCase {
	private static class CollectorList<T> implements IElementCollector {
		private final List<T> elements;

		public CollectorList() {
			elements = Lists.newLinkedList();
		}

		@Override
		@SuppressWarnings("unchecked")
		public void add(final Object element, final IProgressMonitor monitor) {
			elements.add((T) element);
		}

		@Override
		public void add(final Object[] elements, final IProgressMonitor monitor) {
			for (final Object object : elements) {
				add(object, monitor);
			}
		}

		@Override
		public void done() {
		}

		public T get(final int index) {
			return elements.get(index);
		}

	}

	/*
	 * |a| a := OrderedCollection new. a add: nil. a add: 'string'. a add: #symbol. a add: 0. a add: 1.23456. a add: (Dictionary new add: 'a' -> 1; add: 'b' ->
	 * 2; yourself). a add: (Set new add: 'a'; add: 'b'; add: 'c'; yourself). a add: Object. a add: 1 @ 2. a
	 * 
	 * anOrderedCollection( 'anOrderedCollection( Object, UndefinedObject) anOrderedCollection( Object, Collection, SequenceableCollection, CharacterCollection,
	 * String) anOrderedCollection( Object, Collection, SequenceableCollection, CharacterCollection, String, Symbol) anOrderedCollection( Object, Magnitude,
	 * Number, Integer, SmallInteger) anOrderedCollection( Object, Magnitude, Number, BinaryFloat, SmallDouble) anOrderedCollection( Object, Collection,
	 * AbstractDictionary, Dictionary) anOrderedCollection( Object, Collection, UnorderedCollection, Set) anOrderedCollection( Object, DbBehavior, DbClass,
	 * Object class)
	 * 
	 */
	public static final String TEST_OBJECT_EXPR = "" + "|a|\r\n" + "a := OrderedCollection new.\r\n" + "a add: nil.\r\n" + "a add: 'string'.\r\n"
			+ "a add: #symbol.\r\n" + "a add: 0.\r\n" + "a add: 1.23456.\r\n" + "a add: (Dictionary new add: 'a' -> 1; add: 'b' -> 2; yourself).\r\n"
			+ "a add: (Set new add: 'a'; add: 'b'; add: 'c'; yourself).\r\n" + "a add: Object.\r\n" + "a\r\n";

	private ISession session;

	private Set<String> oopsToRelease;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		session = new SessionAdapter() {
			@Override
			public <T> T execute(final ISessionAction<T> action) {
				return client.execute(action);
			}
		};
		oopsToRelease = Sets.newHashSet();
	}

	@Override
	@After
	public void tearDown() throws Exception {
		for (final String oop : oopsToRelease) {
			execute(new RemoveFromExportSet(oop));
		}

		super.tearDown();
	}

	@Test
	public void test() {
		final String oopString = execute(new Evaluate(TEST_OBJECT_EXPR));
		assertNotNull(oopString);

		final ObjectInfo object = execute(new GetObjectInfo(session, oopString));
		assertNotNull(object);
		assertTrue(object instanceof SequenceableCollectionInfo);

		final SequenceableCollectionInfo info = (SequenceableCollectionInfo) object;
		final CollectorList<InspectorNode> items = getUnnamedVarNodes(info);

		assertTrue(items.get(0).getValue() instanceof UndefinedObjectInfo);
		assertTrue(items.get(1).getValue() instanceof StringInfo);
		assertTrue(items.get(2).getValue() instanceof StringInfo);
		assertTrue(items.get(3).getValue() instanceof NumberInfo);
		assertTrue(items.get(4).getValue() instanceof NumberInfo);
		assertTrue(items.get(5).getValue() instanceof AbstractDictionaryInfo);
		assertTrue(items.get(6).getValue() instanceof UnorderedCollectionInfo);
		assertTrue(items.get(7).getValue() instanceof ClassInfo);
		assertTrue(items.get(6).getValue() instanceof ObjectInfo);
	}

	@Test
	public void testConvertingResponseForSequenceableCollection() {
		final ObjectInfo response = new GetObjectInfo(session, "").asResponse("20\nArray\nSequenceableCollection\n10\n2\nprintString");
		assertNotNull(response);
		assertTrue(response instanceof SequenceableCollectionInfo);
		assertEquals("20", response.getOop());
		assertEquals("Array", response.getClassName());
		assertEquals(10, response.getNamedVarSize());
		assertEquals(2, response.getUnnamedVarSize());
		assertEquals("printString", response.getPrintString());
	}

	@Test
	public void testDictionaryChildren() {
		final String oopString = execute(new Evaluate("Dictionary new add: 'a' -> 1; add: 'b' -> 2; yourself"));
		assertNotNull(oopString);

		final ObjectInfo object = execute(new GetObjectInfo(session, oopString));
		assertNotNull(object);

		final CollectorList<InspectorNode> items = getUnnamedVarNodes(object);
		assertEquals("Expected different number of fetched children", 2, items.elements.size());
	}

	@Test
	public void testUnorderedCollectionChildren() {
		final String oopString = execute(new Evaluate("Set new add: 'a'; add: 'b'; add: 'c'; yourself"));
		assertNotNull(oopString);

		final ObjectInfo object = execute(new GetObjectInfo(session, oopString));
		assertNotNull(object);

		final CollectorList<InspectorNode> items = getUnnamedVarNodes(object);
		assertEquals("Expected different number of fetched children", 3, items.elements.size());
	}

	private CollectorList<InspectorNode> getUnnamedVarNodes(final ObjectInfo info) {
		final CollectorList<InspectorNode> items = new CollectorList<InspectorNode>();
		info.fetchUnnamedVars(items, new NullProgressMonitor());
		return items;
	}
}
