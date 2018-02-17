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

import java.util.Map;

import org.eclipse.swt.graphics.RGB;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

/**
 * Text style of one type of syntax element.
 */
public class SyntaxColoringStyle {
	public enum FontModifier {
		BOLD, ITALIC, STRIKETHROUGHT, UNDERLINE
	}

	public enum Name {
		DEFAULT, COMMENT, STRING, SYMBOL, NUMBER, PSEUDO_VARIABLE
	}

	private final Name name;

	private boolean enabled;
	private RGB color;
	private final Map<FontModifier, Boolean> fontModifiers;

	/**
	 * Creates black enabled syntax coloring style without any font styles enabled.
	 * 
	 * @param name of style
	 */
	public SyntaxColoringStyle(final Name name) {
		this.name = name;

		enabled = true;
		color = new RGB(0, 0, 0);
		fontModifiers = Maps.newHashMap();
		for (final FontModifier modifier : FontModifier.values()) {
			fontModifiers.put(modifier, false);
		}
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof SyntaxColoringStyle) {
			final SyntaxColoringStyle other = (SyntaxColoringStyle) obj;
			return Objects.equal(name, other.name) && Objects.equal(enabled, other.enabled) && Objects.equal(color, other.color)
					&& Objects.equal(fontModifiers, other.fontModifiers);
		}
		return false;
	}

	public RGB getColor() {
		return color;
	}

	public boolean getFontModifier(final FontModifier modifier) {
		return fontModifiers.get(modifier);
	}

	public Name getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name, enabled, color, fontModifiers);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setColor(final RGB color) {
		this.color = color;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public void setFontModifier(final FontModifier modifier, final boolean value) {
		fontModifiers.put(modifier, value);
	}
}
