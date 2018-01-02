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
package net.karpisek.gemdev.lang;

/**
 * Smalltak aware analysis of line of source code. Depending on offset of cursor it will guess prefix before cursor and possible receiver name.
 */
public class CursorContext {
	/**
	 * @param line of smalltalk code to be analyzed
	 * @param offset into line where is supposed to be cursor
	 * @return new context object
	 */
	public static CursorContext analyze(final String line, final int offset) {
		final String p = getPrefix(line, offset);
		final String rec = getReceiverString(line, offset - p.length());
		return new CursorContext(line, offset, p, rec);
	}

	/**
	 * Guess from offset into text what substring should be completed. Handles identifiers, message keyword parts, symbols, does not handle symbols with space
	 * embedded in body of symbol.
	 *
	 * @param documentText with complete text of document (for example IDocument#get)
	 * @param offset into text
	 * @return guessed string, empty if nothing could be determined
	 */
	private static String getPrefix(final String documentText, final int offset) {
		final String text = documentText + " ";

		if (offset <= 0 || offset >= text.length())
			return "";

		int cursor = 0;
		for (cursor = offset - 1; cursor >= 0; cursor--) {
			final char ch = text.charAt(cursor);
			if (!(Character.isLetterOrDigit(ch) || ch == ':' || ch == '_')) {
				if (ch != '#') {
					cursor++;
				}
				break;
			}

		}
		if (cursor < 0) {
			cursor = 0;
		}

		return text.substring(cursor, offset);
	}

	/**
	 * Primitive heuristics for finding receiver identifier of message prefix. Skips all whitespace and find first possible prefix than
	 * 
	 * @return receiver string or empty string if nothing was found
	 */
	private static String getReceiverString(final String documentText, final int offset) {
		if (offset <= 0 || offset > documentText.length())
			return "";
		int cursor = 0;
		for (cursor = offset - 1; cursor >= 0; cursor--) {
			final char ch = documentText.charAt(cursor);
			if (!(Character.isWhitespace(ch))) {
				break;
			}
		}

		for (cursor = offset - 1; cursor >= 0; cursor--) {
			final char ch = documentText.charAt(cursor);
			if (!Character.isWhitespace(ch)) {
				break;
			}
		}
		cursor++;

		return getPrefix(documentText, cursor);
	}

	private final String line;

	private final int offset;
	private final String prefix;

	private final String receiver;

	private CursorContext(final String line, final int offset, final String prefix, final String receiver) {
		this.line = line;
		this.offset = offset;
		this.prefix = prefix;
		this.receiver = receiver;
	}

	public String getLine() {
		return line;
	}

	public int getOffset() {
		return offset;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getReceiver() {
		return receiver;
	}

	@Override
	public String toString() {
		return String.format("...<%s> <%s>...", getReceiver(), getPrefix());
	}

}
