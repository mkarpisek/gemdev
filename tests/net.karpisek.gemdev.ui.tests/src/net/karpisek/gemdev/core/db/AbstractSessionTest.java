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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.karpisek.gemdev.net.ISessionAction;
import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.utils.Pair;

public class AbstractSessionTest {
	private IProject project;

	@Before
	public void setUp() throws Exception {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject("test");
	}

	@Test
	public void test() {
		final ISession session = new AbstractSession(project, new ObjectCacheFactory()) {
			@Override
			public void abortTransaction() {

			}

			@Override
			public void commitTransaction() throws CommitFailedException {

			}

			@Override
			public <T> T execute(final ISessionAction<T> action) {

				return null;
			}

			@Override
			public <T> T execute(final TargetSession target, final ISessionAction<T> action) {

				return null;
			}

			@Override
			public String getId() {

				return null;
			}

			@Override
			public DbMethod getMethod(final IFile file) {

				return null;
			}

			@Override
			public IFile getMethodFile(final MethodReference ref) throws CoreException {

				return null;
			}

			@Override
			@Before
			public void setUp(final IProgressMonitor monitor) {

			}

			@Override
			@After
			public void tearDown(final IProgressMonitor monitor) {

			}

			@Override
			protected void createRemoteCategory(final DbBehavior receiver, final String categoryName) {
			}

			@Override
			protected Pair<String, String> createRemoteClass(final String definition) {
				final String[] array = definition.split("->");

				return new Pair<String, String>(array[1], array[0]);
			}

			@Override
			protected String createRemoteMethod(final DbCategory category, final String sourceCode) {
				return sourceCode;
			}

			@Override
			protected List<MethodReference> deleteRemoteCategory(final DbCategory category) {
				return Lists.newLinkedList();
			}

			@Override
			protected void deleteRemoteClass(final DbClass c) {
				// no remote changes -> do nothing
			}

			@Override
			protected void deleteRemoteMethod(final DbMethod m) {

			}
		};

		final List<DbClass> addedClasses = Lists.newLinkedList();
		final List<DbClass> removedClasses = Lists.newLinkedList();
		session.addListener(new ISessionListener() {

			@Override
			public void addedClasses(final List<DbClass> classes) {
				addedClasses.addAll(classes);
			}

			@Override
			public void addedMethodReferences(final List<MethodReference> allAdded) {
			}

			@Override
			public void changedDefinitionOfClasses(final Map<DbClass, DbClass> classes) {
			}

			@Override
			public void removedClasses(final List<DbClass> classes) {
				removedClasses.addAll(classes);
			}

			@Override
			public void removedMethodReferences(final List<MethodReference> allRemoved) {
			}

		});

		final DbClass c1 = session.createClass("null->C1");
		assertSame(c1, session.getCachedClass(c1.getClassName()));

		final DbClass c11 = session.createClass("C1->C11");
		assertSame(c11, session.getCachedClass(c11.getClassName()));

		final DbClass c12 = session.createClass("C1->C12");
		assertSame(c12, session.getCachedClass(c12.getClassName()));

		final DbClass c121 = session.createClass("C12->C121");
		assertSame(c121, session.getCachedClass(c121.getClassName()));

		final DbClass c2 = session.createClass("null->C2");
		assertSame(c2, session.getCachedClass(c2.getClassName()));

		assertEquals(Lists.newArrayList(c1, c11, c12, c121, c2), session.getCachedClasses());
		assertEquals("listener was not properly called for adding classes", Lists.newArrayList(c1, c11, c12, c121, c2), addedClasses);
		assertEquals(Sets.newHashSet(c11, c12), c1.getSubclasses());
		assertSame(null, c1.getSuperclass());
		assertSame(c1, c11.getSuperclass());
		assertSame(c1, c12.getSuperclass());
		assertSame(c12, c121.getSuperclass());
		assertSame(null, c2.getSuperclass());

		session.deleteClass(c2);
		assertEquals(Lists.newArrayList(c1, c11, c12, c121), session.getCachedClasses());
		session.deleteClass(c12);
		assertEquals(Sets.newHashSet(c11), c1.getSubclasses());
		assertEquals("listener was not properly called for removing classes", Lists.newArrayList(c2, c12), removedClasses);

		final DbCategory cat1 = session.createCategory(c1, "cat1");
		final DbMethod msg1 = session.createMethod(cat1, "msg1");
		final DbMethod msg2 = session.createMethod(cat1, "msg2");

		final DbCategory cat2 = session.createCategory(c11, "cat2");
		final DbMethod msg2b = session.createMethod(cat2, "msg2");
		final DbMethod msg3 = session.createMethod(cat2, "msg3");

		assertEquals("[msg1 - test, msg2 - test, msg3 - test]", session.getCachedSelectors().toString());
		assertEquals(asMethodReferences(msg1), session.getCachedMethods(new Selector("msg1", session)));
		assertEquals(asMethodReferences(msg2, msg2b), session.getCachedMethods(new Selector("msg2", session)));
		assertEquals(asMethodReferences(msg3), session.getCachedMethods(new Selector("msg3", session)));

	}

	private Object asMethodReferences(final DbMethod... methods) {
		final List<MethodReference> refs = Lists.newLinkedList();
		for (final DbMethod m : methods) {
			refs.add(new MethodReference(m.getBehavior().getClassName(), m.isInstanceSide(), m.getName()));
		}
		return refs;
	}
}
