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
package net.karpisek.gemdev.ui.preferences;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.graphics.RGB;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.karpisek.gemdev.ui.preferences.SyntaxColoringStyle.FontModifier;
import net.karpisek.gemdev.ui.preferences.SyntaxColoringStyle.Name;

/**
 * Collection of styles for defined types {@link Name} of syntax elements.
 */
public class SyntaxColoringStyleSet {
	private static final String MAJOR_SEPARATOR = "\n"; //$NON-NLS-1$
	private static final String MINOR_SEPARATOR = " "; //$NON-NLS-1$

	/**
	 * @return collection of default styles
	 */
	public static SyntaxColoringStyleSet createDefault() {
		final SyntaxColoringStyle defaultStyle = new SyntaxColoringStyle(Name.DEFAULT);

		final SyntaxColoringStyle comment = new SyntaxColoringStyle(Name.COMMENT);
		comment.setColor(new RGB(63, 127, 95));

		final SyntaxColoringStyle string = new SyntaxColoringStyle(Name.STRING);
		string.setColor(new RGB(42, 0, 255));

		final SyntaxColoringStyle symbol = new SyntaxColoringStyle(Name.SYMBOL);
		symbol.setColor(new RGB(0, 0, 160));
		symbol.setFontModifier(FontModifier.BOLD, true);

		final SyntaxColoringStyle number = new SyntaxColoringStyle(Name.NUMBER);
		number.setColor(new RGB(255, 0, 0));

		final SyntaxColoringStyle pseudoVariable = new SyntaxColoringStyle(Name.PSEUDO_VARIABLE);
		pseudoVariable.setColor(new RGB(127, 0, 85));
		pseudoVariable.setFontModifier(FontModifier.BOLD, true);

		final Map<Name, SyntaxColoringStyle> styles = Maps.newHashMap();
		for (final SyntaxColoringStyle style : Lists.newArrayList(defaultStyle, comment, string, symbol, number, pseudoVariable)) {
			styles.put(style.getName(), style);
		}
		return new SyntaxColoringStyleSet(styles);
	}

	/**
	 * Serialises set of styles to string.
	 * 
	 * @param styles
	 * @return
	 */
	public static String toString(final SyntaxColoringStyleSet styles) {
		final StringBuilder sb = new StringBuilder();
		for (final Name styleName : styles.getNames()) {
			final SyntaxColoringStyle style = styles.getStyle(styleName);
			sb.append(Joiner.on(MINOR_SEPARATOR).join(style.getName(), style.isEnabled(), style.getColor().red, style.getColor().green, style.getColor().blue));
			for (final FontModifier fontStyle : FontModifier.values()) {
				sb.append(MINOR_SEPARATOR);
				sb.append(style.getFontModifier(fontStyle));
			}
			sb.append(MAJOR_SEPARATOR);
		}
		return sb.toString();
	}

	/**
	 * Converts serialised list of styles to collection of objects.
	 * 
	 * @param string which has to be output of {@link #toString(List)}
	 * @return set of styles
	 */
	public static SyntaxColoringStyleSet toStyles(final String string) {
		final Map<Name, SyntaxColoringStyle> styles = Maps.newHashMap();
		for (final String s : string.split(MAJOR_SEPARATOR)) {
			int i = 0;
			final String[] array = s.split(MINOR_SEPARATOR);
			final SyntaxColoringStyle style = new SyntaxColoringStyle(Name.valueOf(array[i++]));
			style.setEnabled(Boolean.valueOf(array[i++]));
			style.setColor(new RGB(Integer.valueOf(array[i++]), Integer.valueOf(array[i++]), Integer.valueOf(array[i++])));
			for (final FontModifier fontStyle : FontModifier.values()) {
				style.setFontModifier(fontStyle, Boolean.valueOf(array[i++]));
			}
			styles.put(style.getName(), style);
		}
		return new SyntaxColoringStyleSet(styles);
	}

	private final Map<Name, SyntaxColoringStyle> styles;

	private SyntaxColoringStyleSet(final Map<Name, SyntaxColoringStyle> styles) {
		this.styles = styles;
	}

	public List<SyntaxColoringStyle> asList() {
		return Lists.newArrayList(styles.values().iterator());
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof SyntaxColoringStyleSet) {
			final SyntaxColoringStyleSet other = (SyntaxColoringStyleSet) obj;
			return Objects.equal(styles, other.styles);
		}
		return false;
	}

	public List<Name> getNames() {
		return Lists.newArrayList(styles.keySet().iterator());
	}

	public SyntaxColoringStyle getStyle(final Name name) {
		return styles.get(name);
	}

	@Override
	public int hashCode() {
		return styles.hashCode();
	}
}
