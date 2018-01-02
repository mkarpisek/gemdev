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

import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentRewriteSessionType;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension4;
import org.eclipse.jface.text.ITextSelection;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * Comments or uncomments lines of smalltalk source code.
 */
public class ToggleCommentRewriter {
	private final IDocument document;

	/**
	 * @param document which contains smalltalk source code to be edited.
	 */
	public ToggleCommentRewriter(final IDocument document) {
		this.document = document;
	}

	/**
	 * Rewrites edited document by adding/removing comment on the selected lines. Always either adds comment on all lines or removes comment from all lines.
	 * Decision what to do is: check number of selected lines, if only one line is selected then invert its comment status (remove comment if it is uncommented,
	 * or add if it is regular) in more then one line is selected then check if all lines are uncommented if yes then uncomment them all, if no add comment to
	 * all lines.
	 * 
	 * @param selection of currently selected text
	 */
	public void toggleComment(final ITextSelection selection) {
		final int startLine = selection.getStartLine();
		final int endLine = selection.getEndLine();

		if (startLine == -1 || endLine == -1) {
			return;
		}
		try {
			final List<String> lines = Lists.newLinkedList();
			for (int lineNumber = startLine; lineNumber < endLine + 1; lineNumber++) {
				final int lineOffset = document.getLineOffset(lineNumber);
				final int lineLength = document.getLineLength(lineNumber);
				final String line = document.get(lineOffset, lineLength);
				lines.add(line);
			}

			boolean addComment = true;
			if (lines.size() == 1) {
				addComment = !isCommentedOut(lines.get(0));
			} else {
				boolean allAreCommentedOut = true;
				for (final String line : lines) {
					allAreCommentedOut = allAreCommentedOut && isCommentedOut(line);
				}
				addComment = !allAreCommentedOut;
			}

			if (addComment) {
				addComment(startLine, endLine + 1);
			} else {
				removeComment(startLine, endLine + 1);
			}
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}

	private void addComment(final int startLine, final int endLine) {
		try {
			if (document instanceof IDocumentExtension4) {
				((IDocumentExtension4) document).startRewriteSession(DocumentRewriteSessionType.SEQUENTIAL);
			}
			for (int lineIndex = startLine; lineIndex < endLine; lineIndex++) {
				final int lineOffset = document.getLineOffset(lineIndex);
				final int length = document.getLineLength(lineIndex);

				final String lineDelimiter = document.getLineDelimiter(lineIndex);
				final int lineDelimiterSize = lineDelimiter == null ? 0 : lineDelimiter.length();

				final String line = document.get(lineOffset, length - lineDelimiterSize);
				final String newLine = "\"" + line.replaceAll("\"", "\"\"") + "\"";
				document.replace(lineOffset, length - lineDelimiterSize, newLine);
			}
			if (document instanceof IDocumentExtension4) {
				((IDocumentExtension4) document).stopRewriteSession(((IDocumentExtension4) document).getActiveRewriteSession());
			}
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}

	private boolean isCommentedOut(final String line) {
		final String trimmed = Strings.nullToEmpty(line).trim();
		return trimmed.length() >= 2 && trimmed.startsWith("\"") && trimmed.endsWith("\"");
	}

	private void removeComment(final int startLine, final int endLine) {
		try {
			if (document instanceof IDocumentExtension4) {
				((IDocumentExtension4) document).startRewriteSession(DocumentRewriteSessionType.SEQUENTIAL);
			}
			for (int lineIndex = startLine; lineIndex < endLine; lineIndex++) {
				final int lineOffset = document.getLineOffset(lineIndex);
				final int length = document.getLineLength(lineIndex);

				final String lineDelimiter = document.getLineDelimiter(lineIndex);
				final int lineDelimiterSize = lineDelimiter == null ? 0 : lineDelimiter.length();

				final String line = document.get(lineOffset, length - lineDelimiterSize);
				final String trimmed = Strings.nullToEmpty(line).trim();
				if (trimmed.length() >= 2 && trimmed.charAt(0) == '"' && trimmed.charAt(trimmed.length() - 1) == '"') {
					final int first = line.indexOf('"');
					final int last = line.lastIndexOf('"');
					final String prefix = line.substring(0, first);
					final String content = line.substring(first + 1, last).replaceAll("\"\"", "\"");

					final String newLine = prefix + content;
					document.replace(lineOffset, length - lineDelimiterSize, newLine);
				}
			}
			if (document instanceof IDocumentExtension4) {
				((IDocumentExtension4) document).stopRewriteSession(((IDocumentExtension4) document).getActiveRewriteSession());
			}

		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}
}
