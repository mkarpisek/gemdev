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
package net.karpisek.gemdev.core.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import net.karpisek.gemdev.net.SessionServer;
import net.karpisek.gemdev.ui.tests.IUnitTests;

@Category({ IUnitTests.class })
public class ProjectCreatorTest {

	private IWorkspaceRoot root;

	@Before
	public void setUp() throws Exception {
		root = ResourcesPlugin.getWorkspace().getRoot();
	}

	@After
	public void tearDown() throws Exception {
		root.delete(true, new NullProgressMonitor());
	}

	@Test
	public void testCreatingNewProject() throws CoreException {
		final ProjectCreator creator = newProjectCreator();
		final IProject project = creator.createProject(new NullProgressMonitor());
		assertNotNull("Expected Project obect, got null", project);
		assertTrue("Project should be really existing in workspace after creating it", project.exists());
		assertTrue("Project should be created as open", project.isOpen());
		assertTrue("Project should have GemDev nature", project.hasNature(ProjectNature.ID));

		final ProjectNature nature = (ProjectNature) project.getNature(ProjectNature.ID);
		assertEquals(SessionServer.DEFAULT_HOST_NAME, nature.getServerHost());
		assertEquals(SessionServer.DEFAULT_PORT, nature.getServerPort());
	}

	@Test
	public void testCreatingNewProject_shouldFailBecauseProjectWithThatNameExists() throws CoreException {
		final IProject project = root.getProject("testProject");
		project.create(new NullProgressMonitor());
		assertTrue(project.exists());

		final ProjectCreator creator = newProjectCreator();
		try {
			creator.createProject(new NullProgressMonitor());
			fail("Creating project should fail because there is already project with that name in workspace");
		} catch (final CoreException e) {
			// success
		}

		assertTrue(project.exists());
	}

	@Test
	public void testExists_checkExistingProject() throws Exception {
		final IProject project = root.getProject("testProject");
		project.create(new NullProgressMonitor());
		assertTrue(project.exists());

		final ProjectCreator creator = newProjectCreator();
		assertTrue(creator.exists());
	}

	@Test
	public void testExists_checkNonExistingProject() throws Exception {
		final ProjectCreator creator = newProjectCreator();
		assertFalse(creator.exists());
	}

	private ProjectCreator newProjectCreator() {
		return new ProjectCreator("testProject", SessionServer.DEFAULT_HOST_NAME, SessionServer.DEFAULT_PORT, null);
	}
}
