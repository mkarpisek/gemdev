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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.graphics.RGB;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import net.karpisek.gemdev.ui.preferences.SyntaxColoringStyle.FontModifier;
import net.karpisek.gemdev.ui.preferences.SyntaxColoringStyle.Name;
import net.karpisek.gemdev.ui.tests.IUnitTests;

@Category({ IUnitTests.class })
public class SyntaxColoringStyleTest {
	@Test
	public void test() {
		final SyntaxColoringStyleSet styles = SyntaxColoringStyleSet.createDefault();
		final SyntaxColoringStyle s2 = styles.getStyle(Name.COMMENT);
		s2.setColor(new RGB(255, 128, 64));
		s2.setEnabled(true);
		s2.setFontModifier(FontModifier.BOLD, true);
		s2.setFontModifier(FontModifier.STRIKETHROUGHT, true);

		final SyntaxColoringStyle s3 = styles.getStyle(Name.NUMBER);
		s3.setColor(new RGB(128, 129, 130));
		s3.setEnabled(true);
		s3.setFontModifier(FontModifier.ITALIC, true);
		s3.setFontModifier(FontModifier.UNDERLINE, true);

		final String s = SyntaxColoringStyleSet.toString(styles);
		assertNotNull(s);
		assertTrue(s.length() > 0);

		final SyntaxColoringStyleSet styles2 = SyntaxColoringStyleSet.toStyles(s);
		assertEquals(styles, styles2);
	}
}
