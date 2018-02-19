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
package net.karpisek.gemdev.ui.editor.rewriters;

import static org.junit.Assert.assertEquals;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import net.karpisek.gemdev.ui.tests.IUnitTests;
import net.karpisek.gemdev.ui.tests.UiTestsPlugin;

@Category({ IUnitTests.class })
public class ToggleCommentRewriterTest {
	@Test
	public void test_addCommentOnEmptyLine() throws BadLocationException {
		assertRewriteResult("ToggleCommentRewriter_addCommentOnEmptyLine.gsm", 9, 0);
	}

	@Test
	public void test_addCommentOnMoreLines() throws BadLocationException {
		assertRewriteResult("ToggleCommentRewriter_addCommentOnMoreLines.gsm", 33, 33);
	}

	@Test
	public void test_addCommentOnMoreLinesWhereSomeAreUncommented() throws BadLocationException {
		assertRewriteResult("ToggleCommentRewriter_addCommentOnMoreLinesWhereSomeAreUncommented.gsm", 9, 5);
	}

	@Test
	public void test_removeCommentFromMoreLines() throws BadLocationException {
		assertRewriteResult("ToggleCommentRewriter_removeCommentFromMoreLines.gsm", 10, 15);
	}

	@Test
	public void test_removeCommentFromOneLine() throws BadLocationException {
		assertRewriteResult("ToggleCommentRewriter_removeCommentFromOneLine.gsm", 11, 0);
	}

	private void assertRewriteResult(final String fileWithExpectedRewrittenText, final int selectionOffset, final int selectionLength)
			throws BadLocationException {
		final IDocument doc = new Document(getResource("ToggleCommentRewriter.gsm"));

		// enable when debugging offsets
		// System.out.printf("numberOfLines=%d%n", doc.getNumberOfLines());
		// for (int i = 0; i <doc.getNumberOfLines(); i++) {
		// System.out.printf("%d. offset=%d length=%d%n", i+1, doc.getLineOffset(i),
		// doc.getLineLength(i));
		// }

		final TextSelection selection = new TextSelection(doc, selectionOffset, selectionLength);

		final ToggleCommentRewriter rewriter = new ToggleCommentRewriter(doc);
		rewriter.toggleComment(selection);

		final String expected = getResource(fileWithExpectedRewrittenText);
		assertEquals(expected, doc.get());
	}

	private String getResource(final String fileName) {
		return UiTestsPlugin.getDefault().getFileContents("/resources/editor/" + fileName);
	}

}
