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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.NumberRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.preferences.SyntaxColoringStyle;
import net.karpisek.gemdev.ui.preferences.SyntaxColoringStyle.FontModifier;
import net.karpisek.gemdev.ui.preferences.SyntaxColoringStyle.Name;
import net.karpisek.gemdev.ui.preferences.SyntaxColoringStyleSet;

/**
 * Scanner for creating tokens for syntax coloring of smalltalk source code.
 */
public class Scanner extends RuleBasedScanner {
	/**
	 * Multiline rule which does not start with start sequence if there is '$' before it Because in that case it is Character $' or $" and nmot start of string
	 * or comment
	 */
	private static class MultiLineRule2 extends MultiLineRule {

		public MultiLineRule2(final String startSequence, final String endSequence, final IToken token) {
			super(startSequence, endSequence, token, (char) 0, true);
		}

		@Override
		protected IToken doEvaluate(final ICharacterScanner scanner, final boolean resume) {
			if (resume) {
				if (endSequenceDetected(scanner)) {
					return fToken;
				}
			} else {
				final int c1 = scanner.read();
				final int c2 = scanner.read();
				if (c1 == '$' && c2 == fStartSequence[0]) {
					return Token.UNDEFINED;
				}
				scanner.unread();

				if (c1 == fStartSequence[0]) {
					if (sequenceDetected(scanner, fStartSequence, false)) {
						if (endSequenceDetected(scanner)) {
							return fToken;
						}
					}
				}
			}
			scanner.unread();
			return Token.UNDEFINED;
		}
	}

	private static class SymbolRule implements IPredicateRule {
		private final IToken successToken;

		public SymbolRule(final IToken successToken) {
			this.successToken = successToken;
		}

		@Override
		public IToken evaluate(final ICharacterScanner scanner) {
			return evaluate(scanner, false);
		}

		@Override
		public IToken evaluate(final ICharacterScanner scanner, final boolean resume) {
			int c = scanner.read();
			if (c == '#') {
				c = scanner.read();
				if (c != '(' && c != '{' && c != '[') {
					endSequenceDetected(scanner);
					return successToken;
				}
				scanner.unread();
			}
			scanner.unread();
			return Token.UNDEFINED;
		}

		@Override
		public IToken getSuccessToken() {
			return successToken;
		}

		private boolean isSymbolCharacter(final int c) {
			if (Character.isWhitespace(c)) {
				return false;
			}
			return Character.isLetterOrDigit(c) || c == ':' || c == '\'' || c == '_';
		}

		protected boolean endSequenceDetected(final ICharacterScanner scanner) {
			int c;
			while ((c = scanner.read()) != ICharacterScanner.EOF && isSymbolCharacter(c)) {

			}
			scanner.unread();
			return true;
		}
	}

	private static class WordDetector implements IWordDetector {
		@Override
		public boolean isWordPart(final char character) {
			return Character.isJavaIdentifierPart(character);
		}

		@Override
		public boolean isWordStart(final char character) {
			return Character.isJavaIdentifierStart(character);
		}
	}

	public Scanner(final SyntaxColoringStyleSet styles) {
		final Map<Name, IToken> tokens = Maps.newHashMap();
		for (final Name name : styles.getNames()) {
			tokens.put(name, createToken(styles.getStyle(name)));
		}
		final List<IRule> rules = new ArrayList<IRule>();

		if (styles.getStyle(Name.COMMENT).isEnabled()) {
			final String sequence = "\""; //$NON-NLS-1$
			rules.add(new MultiLineRule2(sequence, sequence, tokens.get(Name.COMMENT)));
		}

		if (styles.getStyle(Name.STRING).isEnabled()) {
			final String sequence = "'"; //$NON-NLS-1$
			rules.add(new MultiLineRule2(sequence, sequence, tokens.get(Name.STRING)));
		}

		if (styles.getStyle(Name.SYMBOL).isEnabled()) {
			rules.add(new SymbolRule(tokens.get(Name.SYMBOL)));
		}

		if (styles.getStyle(Name.NUMBER).isEnabled()) {
			rules.add(new NumberRule(tokens.get(Name.NUMBER)));
		}

		if (styles.getStyle(Name.PSEUDO_VARIABLE).isEnabled()) {
			final WordRule wordRule = new WordRule(new WordDetector(), tokens.get(Name.DEFAULT));
			for (final String element : Lists.newArrayList("nil", "true", "false", "self", "super", "thisContext")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
				wordRule.addWord(element, tokens.get(Name.PSEUDO_VARIABLE));
			}
			rules.add(wordRule);
		}
		final IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		setRules(result);
	}

	private IToken createToken(final SyntaxColoringStyle style) {
		int textAttributeStyle = SWT.NONE;
		if (style.getFontModifier(FontModifier.BOLD)) {
			textAttributeStyle |= SWT.BOLD;
		}
		if (style.getFontModifier(FontModifier.ITALIC)) {
			textAttributeStyle |= SWT.ITALIC;
		}
		if (style.getFontModifier(FontModifier.STRIKETHROUGHT)) {
			textAttributeStyle |= TextAttribute.STRIKETHROUGH;
		}
		if (style.getFontModifier(FontModifier.UNDERLINE)) {
			textAttributeStyle |= TextAttribute.UNDERLINE;
		}
		return new Token(new TextAttribute(GemDevUiPlugin.getDefault().getColor(style.getColor()), null, textAttributeStyle));
	}
}
