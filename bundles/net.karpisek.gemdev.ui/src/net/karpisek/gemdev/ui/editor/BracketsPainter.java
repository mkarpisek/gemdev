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

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ICharacterPairMatcher;
import org.eclipse.jface.text.source.ISourceViewer;

/**
 * Highlight pairs of corresponding brackets by using annotation model of viewer. Main bracket is under cursor and peer is the other bracket. Main and peer
 * bracket can be LEFT of RIGHT. Color and type style of highlight is driven by annotation {@link BracketsPainter#ANNOTATION_ID}.
 */
public class BracketsPainter {
	private static class Matcher implements ICharacterPairMatcher {
		private static final char[] BRACKETS = { '{', '}', '(', ')', '[', ']' };
		protected int anchor;
		private IRegion region;

		@Override
		public void clear() {
			anchor = -1;
			region = null;
		}

		@Override
		public void dispose() {
		}

		@Override
		public int getAnchor() {
			return anchor;
		}

		@Override
		public IRegion match(final IDocument doc, final int offset) {
			final int length = doc.getLength();
			if (offset >= length) {
				return null;
			}

			try {
				final char ch = doc.getChar(offset);

				for (int bi = 0; bi < BRACKETS.length; bi += 2) {
					final char left = BRACKETS[bi];
					final char right = BRACKETS[bi + 1];
					if (ch == left) {
						int counter = 0;
						for (int i = offset + 1; i < length; i++) {
							final char ch2 = doc.getChar(i);
							if (ch2 == left) {
								counter++;
							}
							if (ch2 == right) {
								if (counter == 0 && ch2 == right) {
									return peerFound(i, ICharacterPairMatcher.RIGHT);
								}
								counter--;
							}
						}
					}
					if (ch == right) {
						int counter = 0;
						for (int i = offset - 1; i >= 0; i--) {
							final char ch2 = doc.getChar(i);
							if (ch2 == right) {
								counter++;
							}
							if (ch2 == left) {
								if (counter == 0 && ch2 == left) {
									return peerFound(i, ICharacterPairMatcher.LEFT);
								}
								counter--;
							}
						}
					}
				}
			} catch (final BadLocationException e) {
				// be silent and return null correctly
			}

			return null;
		}

		protected IRegion peerFound(final int peerOffset, final int anchor) {
			this.anchor = anchor;
			this.region = new Region(peerOffset, 1);
			return region;
		}

	}
	public static final String ANNOTATION_ID = "net.karpisek.gemdev.ui.bracketsAnnotationType"; //$NON-NLS-1$

	protected final ISourceViewer viewer;
	protected Annotation main;

	protected Annotation peer;
	protected final ICharacterPairMatcher mainMatcher;

	protected final ICharacterPairMatcher peerMatcher;

	public BracketsPainter(final ISourceViewer viewer) {
		this.viewer = viewer;

		mainMatcher = new Matcher() {
			@Override
			public IRegion match(final IDocument doc, final int offset) {
				final IRegion match = super.match(doc, offset);
				if (match == null) {
					return null;
				}

				if (anchor == ICharacterPairMatcher.LEFT) {
					anchor = ICharacterPairMatcher.RIGHT;
				}
				return peerFound(offset, anchor);
			}
		};
		peerMatcher = new Matcher();
	}

	/**
	 * If on offset is some opening or closing bracket marks it and its opposite character by annotations
	 */
	public void refresh(final int offset) {
		if (viewer.getAnnotationModel() == null) {
			return;
		}

		clean();
		markBrackets(offset);
	}

	/**
	 * Removes all existing annotations
	 */
	private void clean() {
		final IAnnotationModel am = viewer.getAnnotationModel();
		if (main != null) {
			am.removeAnnotation(main);
		}
		if (peer != null) {
			am.removeAnnotation(peer);
		}
	}

	private void markBrackets(final int offset) {
		final IAnnotationModel am = viewer.getAnnotationModel();
		final IDocument doc = viewer.getDocument();

		final IRegion r0 = mainMatcher.match(doc, offset);
		final IRegion r1 = peerMatcher.match(doc, offset);
		if (r0 == null || r1 == null) {
			return;
		}

		main = new Annotation(ANNOTATION_ID, false, "c"); //$NON-NLS-1$
		am.addAnnotation(main, new Position(r0.getOffset(), 1));
		peer = new Annotation(ANNOTATION_ID, false, "c"); //$NON-NLS-1$
		am.addAnnotation(peer, new Position(r1.getOffset(), 1));
	}
}
