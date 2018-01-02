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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.collect.Sets;

import net.karpisek.gemdev.core.resources.ProjectCreator;
import net.karpisek.gemdev.net.SessionServer;
import net.karpisek.gemdev.ui.tests.IUnitTests;
import net.karpisek.gemdev.ui.tests.SessionAdapter;

@Category({ IUnitTests.class })
public class SessionManagerTest {
	private class MockSessionManager extends SessionManager {

		public MockSessionManager(final ISessionFactory sessionFactory) {
			super(sessionFactory);
		}
	}

	private SessionManager manager;

	private IWorkspaceRoot root;

	@Before
	public void setUp() throws Exception {
		this.manager = new MockSessionManager(new ISessionFactory() {
			@Override
			public ISession newSession(final IProject project) throws CoreException {
				return new SessionAdapter() {
					@Override
					public IProject getProject() {
						return project;
					}
				};
			}

		});
		this.root = ResourcesPlugin.getWorkspace().getRoot();
	}

	@After
	public void tearDown() throws Exception {
		root.delete(true, new NullProgressMonitor());
	}

	@Test
	public void testCloseAllSessions() throws CoreException {
		final IProject project1 = newGemDevProject("project1");
		assertTrue(project1.exists());
		final IProject project2 = newGemDevProject("project2");
		assertTrue(project2.exists());
		assertNotSame(project1, project2);

		final ISession session1 = manager.openSession(project1, new NullProgressMonitor());
		final ISession session2 = manager.openSession(project2, new NullProgressMonitor());

		assertEquals(Sets.newHashSet(session1, session2), manager.getAllSessions());
		manager.closeAllSessions(new NullProgressMonitor());

		assertTrue("Expected that all sessions will be closed", manager.getAllSessions().isEmpty());

	}

	@Test
	public void testLifecycleWithListeners() throws CoreException {
		final IProject project = newGemDevProject();

		final Set<ISession> openedSessions = Sets.newHashSet();
		final Set<ISession> closedSessions = Sets.newHashSet();

		final ISessionManagerListener listener = new ISessionManagerListener() {
			@Override
			public void sessionClosed(final ISession session) {
				closedSessions.add(session);
			}

			@Override
			public void sessionOpened(final ISession session) {
				openedSessions.add(session);
			}
		};

		assertEquals("anSessionManager(sessions=0 listeners=0)", manager.toString());
		manager.addListener(listener);
		assertEquals("anSessionManager(sessions=0 listeners=1)", manager.toString());
		manager.addListener(listener);
		assertEquals("Expected that adding listener which is already added will be noop", "anSessionManager(sessions=0 listeners=1)", manager.toString());

		final ISession session = manager.openSession(project, new NullProgressMonitor());
		assertNotNull(session);
		assertEquals(Sets.newHashSet(session), openedSessions);
		assertEquals(Sets.newHashSet(), closedSessions);
		assertEquals("anSessionManager(sessions=1 listeners=1)", manager.toString());
		assertSame(session, manager.getSession(project));

		manager.closeSession(project, new NullProgressMonitor());
		assertEquals(Sets.newHashSet(session), closedSessions);
		assertEquals("anSessionManager(sessions=0 listeners=1)", manager.toString());
		assertNull("Expected that closed session will be removed", manager.getSession(project));

		manager.removeListener(listener);
		assertEquals("anSessionManager(sessions=0 listeners=0)", manager.toString());
	}

	@Test
	public void testOpenSession() throws CoreException {
		final IProject project = newGemDevProject();

		final ISession session = manager.openSession(project, new NullProgressMonitor());
		assertNotNull(session);
		assertSame("Expected that session will have assigned same project as on which it was created", project, session.getProject());
	}

	@Test
	public void testOpenSession_openingTwoSessionsOnSameProjectShouldFail() throws CoreException {
		final IProject project = newGemDevProject();

		final ISession session = manager.openSession(project, new NullProgressMonitor());
		assertNotNull(session);
		try {
			manager.openSession(project, new NullProgressMonitor());
			fail("Expected to throw exception - it is not allowed to create multiple sessions on one project");
		} catch (final SessionManagerException e) {
			// correct behavior
		}
	}

	@Test
	public void testOpenSession_shouldFailBecauseItIsNotSmalltalkProject() throws CoreException {
		final IProject project = root.getProject("testProject");
		project.create(new NullProgressMonitor());
		assertTrue(project.exists());

		try {
			manager.openSession(project, new NullProgressMonitor());
			fail("Expected to throw exception because we tried to open session on non-smalltalk project");
		} catch (final IllegalArgumentException e) {
			// correct behavior
		}
	}

	@Test
	public void testOpenSession_shouldFailBecauseProjectIsClosed() throws CoreException {
		final IProject project = newGemDevProject();
		project.close(new NullProgressMonitor());

		try {
			manager.openSession(project, new NullProgressMonitor());
			fail("Expected to throw exception because we tried to open session on closed project");
		} catch (final IllegalArgumentException e) {
			// correct behavior
		}
	}

	@Test
	public void testOpenSession_shouldFailBecauseProjectIsNotExisting() throws CoreException {
		final IProject project = newGemDevProject();
		project.delete(true, new NullProgressMonitor());
		assertFalse(project.exists());

		try {
			manager.openSession(project, new NullProgressMonitor());
			fail("Expected to throw exception because we tried to open session on non-existing smalltalk project");
		} catch (final IllegalArgumentException e) {
			// correct behavior
		}
	}

	private IProject newGemDevProject() throws CoreException {
		return newGemDevProject("unnamed");
	}

	private IProject newGemDevProject(final String name) throws CoreException {
		final ProjectCreator creator = new ProjectCreator(name, SessionServer.DEFAULT_HOST_NAME, SessionServer.DEFAULT_PORT, null);
		final IProject project = creator.createProject(new NullProgressMonitor());
		assertNotNull(project);
		assertTrue(project.exists());
		return project;
	}
}
