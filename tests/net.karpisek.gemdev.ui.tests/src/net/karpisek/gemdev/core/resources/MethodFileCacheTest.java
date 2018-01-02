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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.ui.tests.IUnitTests;

@Category({ IUnitTests.class })
public class MethodFileCacheTest {
	private static final String ILLEGAL = "\\/*?\"<>|";
	private IWorkspace workspace;

	private MethodFileCache cache;

	@Before
	public void setUp() throws Exception {
		workspace = ResourcesPlugin.getWorkspace();
		cache = new MethodFileCache(workspace);
		cache.delete(new NullProgressMonitor());
	}

	@After
	public void tearDown() throws Exception {
		cache.delete(new NullProgressMonitor());
	}

	@Test
	public void test() throws CoreException {
		final String projectName = "p1";
		final IFile file0 = cache.getFile(projectName, new MethodReference("Class1", true, "method1"));
		final IFile file0b = cache.getFile(projectName, new MethodReference("Class1", true, "method1"));
		final IFile file1 = cache.getFile(projectName, new MethodReference("Class1", false, "method1"));
		final IFile file2 = cache.getFile(projectName, new MethodReference("Class2", true, "kw1:kw2:"));
		final IFile file3 = cache.getFile(projectName, new MethodReference("Class3", true, ILLEGAL));
		final IFile file4 = cache.getFile(projectName, new MethodReference("Class4", true, ILLEGAL));

		assertEquals("Class1#method1.gsm", file0.getName());
		assertEquals("Class1#method1.gsm", file0b.getName());
		assertEquals("Class1 class#method1.gsm", file1.getName());
		assertEquals("Class3#3.gsm", file3.getName());
		assertEquals("Class4#4.gsm", file4.getName());

		final BiMap<MethodReference, IFile> expectedMapping = HashBiMap.create();
		expectedMapping.put(new MethodReference("Class1", true, "method1"), file0);
		expectedMapping.put(new MethodReference("Class1", false, "method1"), file1);
		expectedMapping.put(new MethodReference("Class2", true, "kw1:kw2:"), file2);
		expectedMapping.put(new MethodReference("Class3", true, ILLEGAL), file3);
		expectedMapping.put(new MethodReference("Class4", true, ILLEGAL), file4);

		final BiMap<MethodReference, IFile> actualMapping = cache.getMapping(projectName);
		assertEquals(expectedMapping, actualMapping);

		cache.save();
		final MethodFileCache cache2 = new MethodFileCache(workspace);
		assertEquals("Expected newly created cache will load its contents from disk", expectedMapping, cache2.getMapping(projectName));
	}
}
