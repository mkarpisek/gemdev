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
package net.karpisek.gemdev.core.analysis;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import net.karpisek.gemdev.lang.ParserUtils;
import net.karpisek.gemdev.lang.model.MethodModel;
import net.karpisek.gemdev.ui.tests.IUnitTests;
import net.karpisek.gemdev.ui.tests.UiTestsPlugin;

@Category({ IUnitTests.class })
public class MethodAnalysisTest {
	private static final String PROJECT_NAME = "testProject";
	private static final String FILE_NAME = "testFile";

	private String sourceCode;
	private MethodModel model;
	private IProject project;
	private IFile file;

	@Before
	public void setUp() throws IOException, CoreException {
		sourceCode = getResource("method.gsm");
		model = ParserUtils.build(sourceCode);

		project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME);
		project.create(new NullProgressMonitor());
		project.open(new NullProgressMonitor());

		file = project.getFile(FILE_NAME);
		file.create(new ByteArrayInputStream(sourceCode.getBytes("UTF8")), true, null);
	}

	@After
	public void tearDown() throws CoreException {
		project.delete(true, null);
	}

	@Test
	public void testFindUndeclaredIdentifiers() {
		final MethodAnalysis a = new UndeclaredIdentifiersAnalysis(new NullMethodContext() {
			@Override
			public boolean isSystemDeclaring(final String identifierName) {
				return "Object".equals(identifierName);
			}
		}, model);
		final List<IMarker> markers = a.createProblemMarkers(file);

		final List<String> expected = Lists.newArrayList("Undeclared identifier 'undeclared'", "Undeclared identifier 'undeclared'");
		assertEquals(Joiner.on("\n").join(expected), toString(markers));
	}

	@Test
	public void testFindUnimplementedMessages() {
		final MethodAnalysis a = new UnimplementedMessagesAnalysis(new NullMethodContext(), model);
		final List<IMarker> markers = a.createProblemMarkers(file);

		final List<String> expected = Lists.newArrayList("Message '+' is not implemented.", "Message '+' is not implemented.",
				"Message '+' is not implemented.", "Message '+' is not implemented.", "Message 'm1' is not implemented.", "Message 'm1' is not implemented.",
				"Message 'kw1:kw2:' is not implemented.", "Message 'kw1:kw2:' is not implemented.", "Message 'kw1:kw2:' is not implemented.",
				"Message 'kw1:kw2:' is not implemented.", "Message 'value:' is not implemented.");
		assertEquals(Joiner.on("\n").join(expected), toString(markers));
	}

	@Test
	public void testFindUnusedLocalIdentifiers() {
		final MethodAnalysis a = new UnusedLocalIdentifierssAnalysis(new NullMethodContext(), model);
		final List<IMarker> markers = a.createProblemMarkers(file);
		final List<String> expected = Lists.newArrayList("Unused method parameter 'p1'", "Unused method parameter 'unusedMethodParam'",
				"Unused temporary variable 'unusedTmpVar'", "Unused block parameter 'unusedBlockParam'", "Unused temporary variable 'unusedBlockTmpVar'",
				"Unused block parameter 'p0'");
		assertEquals(Joiner.on("\n").join(expected), toString(markers));
	}

	public String toString(final List<IMarker> markers) {
		final List<String> strings = Lists.newLinkedList();
		for (final IMarker m : markers) {
			strings.add(m.getAttribute(IMarker.MESSAGE, ""));
		}
		return Joiner.on("\n").join(strings);
	}

	private String getResource(final String fileName) {
		return UiTestsPlugin.getDefault().getFileContents("/resources/parser/" + fileName);
	}
}
