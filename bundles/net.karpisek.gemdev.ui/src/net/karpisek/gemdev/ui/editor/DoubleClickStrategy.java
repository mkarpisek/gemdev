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
package net.karpisek.gemdev.ui.editor;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;

/**
 * Selects text on double click.
 */
public class DoubleClickStrategy implements ITextDoubleClickStrategy {
	@Override
	public void doubleClicked(final ITextViewer viewer) {
		final IRegion region = doubleClicked(viewer.getDocument().get(), viewer.getSelectedRange().x);
		if (region != null) {
			viewer.setSelectedRange(region.getOffset(), region.getLength());
		}
	}

	public IRegion doubleClicked(final String text, final int clickOffset) {
		IRegion region = null;

		// out of bounds click - silently return null
		if (text.length() == 0 || clickOffset < 0 || clickOffset > text.length()) {
			return null;
		}

		// clicking on beginning or end of text selects complete text
		if (clickOffset == 0 || clickOffset == text.length()) {
			return new Region(0, text.length());
		}

		if ((region = matchBrackets(text, clickOffset)) != null) {
			return region;
		}
		if ((region = matchBraces(text, clickOffset)) != null) {
			return region;
		}
		if ((region = matchParens(text, clickOffset)) != null) {
			return region;
		}
		if ((region = matchComment(text, clickOffset)) != null) {
			return region;
		}
		if ((region = matchString(text, clickOffset)) != null) {
			return region;
		}
		return matchWord(text, clickOffset);
	}

	private boolean isWordCharacter(final char ch) {
		return '_' == ch || Character.isLetter(ch) || Character.isDigit(ch);
	}

	private IRegion matchBraces(final String text, final int clickOffset) {
		return matchPair(text, clickOffset, '{', '}');
	}

	private IRegion matchBrackets(final String text, final int clickOffset) {
		return matchPair(text, clickOffset, '[', ']');
	}

	private IRegion matchComment(final String text, final int clickOffset) {
		return matchPair(text, clickOffset, '"');
	}

	private IRegion matchPair(final String text, final int clickOffset, final char endChar) {
		char ch = text.charAt(clickOffset);
		if (ch == endChar) {
			int index = clickOffset - 1;
			while (index >= 0) {
				final char charAtIndex = text.charAt(index);
				if (charAtIndex == endChar) {
					if (index > 0) {
						if (text.charAt(index - 1) == endChar) {
							index--;
						} else {
							return new Region(index + 1, clickOffset - index - 1);
						}
					}
				}
				index--;
			}
			return new Region(0, clickOffset);
		}
		ch = text.charAt(clickOffset - 1);
		if (ch == endChar) {
			int index = clickOffset;
			while (index < text.length()) {
				final char charAtIndex = text.charAt(index);
				if (charAtIndex == endChar) {
					if (index < text.length()) {
						if (text.charAt(index + 1) == endChar) {
							index++;
						} else {
							return new Region(clickOffset, index - clickOffset);
						}
					}
				}
				index++;
			}
			return new Region(clickOffset, text.length() - clickOffset);
		}
		return null;
	}

	private IRegion matchPair(final String text, final int clickOffset, final char startChar, final char endChar) {
		char ch = text.charAt(clickOffset);
		if (ch == endChar) {
			int index = clickOffset - 1;
			int stack = 0;
			while (index >= 0) {
				final char charAtIndex = text.charAt(index);
				if (charAtIndex == endChar) {
					stack++;
				}
				if (charAtIndex == startChar) {
					if (stack == 0) {
						return new Region(index + 1, clickOffset - index - 1);
					} else {
						stack--;
					}
				}

				index--;
			}
			return new Region(0, clickOffset);
		}
		ch = text.charAt(clickOffset - 1);
		if (ch == startChar) {
			int index = clickOffset;
			int stack = 0;
			while (index < text.length()) {
				final char charAtIndex = text.charAt(index);
				if (charAtIndex == startChar) {
					stack++;
				}
				if (charAtIndex == endChar) {
					if (stack == 0) {
						return new Region(clickOffset, index - clickOffset);
					} else {
						stack--;
					}
				}
				index++;
			}
			return new Region(clickOffset, text.length() - clickOffset);
		}
		return null;
	}

	private IRegion matchParens(final String text, final int clickOffset) {
		return matchPair(text, clickOffset, '(', ')');
	}

	private IRegion matchString(final String text, final int clickOffset) {
		return matchPair(text, clickOffset, '\'');
	}

	private IRegion matchWord(final String text, final int clickOffset) {
		if (!isWordCharacter(text.charAt(clickOffset))) {
			return null;
		}

		final int hashIndex = text.substring(0, clickOffset).lastIndexOf('#');
		boolean isSymbol = hashIndex >= 0;
		if (isSymbol) {
			final String potentialSymbol = text.substring(hashIndex + 1, clickOffset);
			for (int i = 0; i < potentialSymbol.length(); i++) {
				final char ch = potentialSymbol.charAt(i);
				isSymbol = isSymbol && (isWordCharacter(ch) || ':' == ch);
			}
		}

		int firstIndex = clickOffset;
		if (isSymbol) {
			firstIndex = hashIndex + 1;
		} else {
			while (firstIndex > 0 && isWordCharacter(text.charAt(firstIndex))) {
				firstIndex--;
			}
			if (firstIndex < 0) {
				firstIndex = 0;
			} else {
				if (!isWordCharacter(text.charAt(firstIndex))) {
					firstIndex++;
				}
			}
		}

		int lastIndex = clickOffset;
		while (lastIndex < text.length() && (isWordCharacter(text.charAt(lastIndex)) || (isSymbol && ':' == text.charAt(lastIndex)))) {
			lastIndex++;
		}
		if (lastIndex > text.length()) {
			lastIndex = text.length();
		}
		return new Region(firstIndex, lastIndex - firstIndex);
	}
}
