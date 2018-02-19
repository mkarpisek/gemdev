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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import net.karpisek.gemdev.team.model.LocalClass;
import net.karpisek.gemdev.ui.tests.IUnitTests;

@Category({ IUnitTests.class })
public class WorkingCopyTest extends TeamTestCase {
	private IProject project;

	@Before
	public void setUp() throws CoreException {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject("WorkingCopyTest");
		project.create(new NullProgressMonitor());
		project.open(new NullProgressMonitor());
	}

	@After
	public void tearDown() throws CoreException {
		project.close(new NullProgressMonitor());
		project.delete(true, new NullProgressMonitor());
	}

	@Test
	public void testCreatingNonExistingFolder() throws IOException, CoreException {
		final LocalClass c = LocalClass.read(null, new StringReader(getResource("EmptyClass.gsc")));

		final WorkingCopy wc = new WorkingCopy(project);
		final IFile file = wc.getFile(c);
		assertEquals(project.getLocation().append("src").append(c.getCategoryName()).append(c.getName() + "." + WorkingCopy.FILE_EXTENSION),
				file.getLocation());
		assertFalse("File should not be created yet", file.exists());

		wc.write(new NullProgressMonitor(), c);

		assertTrue("File with class contents should exist", file.exists());

		assertEquals(c.toString(), getFileContents(file));

		final LocalClass c2 = wc.read(c.getName());
		assertNotNull("Expected new loaded class", c2);
		assertEquals(c.toString(), c2.toString());

		assertNull(wc.read("something non existing"));

		// now test move of class file
		c2.setCategoryName("SystemCategory2");
		final IFile file2 = wc.getFile(c2);
		assertEquals(project.getLocation().append("src").append(c2.getCategoryName()).append(c.getName() + "." + WorkingCopy.FILE_EXTENSION),
				file2.getLocation());
		wc.write(new NullProgressMonitor(), c2);

		assertFalse("File " + file.getLocation() + " should not exist", file.exists());
		assertTrue("File " + file2.getLocation() + " should exist", file2.exists());
	}

	private String getFileContents(final IFile file) throws CoreException, IOException {
		try (InputStreamReader reader = new InputStreamReader(file.getContents(), Charsets.UTF_8)) {
			return CharStreams.toString(reader);
		}
	}
}
