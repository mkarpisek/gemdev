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

import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

import com.google.common.base.Preconditions;

import net.karpisek.gemdev.ui.GemDevUiPlugin;

/**
 * Initialiser of plugin preferences and holder of utility methods for working with preference values. author initials are set on session creation, they are
 * used for example for method stamps.
 */
public class GsPreferences extends AbstractPreferenceInitializer {
	public static final String AUTHOR_INITIALS = "authorInitials"; //$NON-NLS-1$
	public static final String SYNTAX_COLORING = "syntaxColoring"; //$NON-NLS-1$

	public static final String ENABLE_SYNTAX_ERROR_ANALYSIS = "enableSyntaxErrorAnalysis"; //$NON-NLS-1$
	public static final String ENABLE_UNUSED_LOCAL_IDENTIFIERS_ANALYSIS = "enableUnusedLocalIdientifierAnalysis"; //$NON-NLS-1$
	public static final String ENABLE_UNIMPLEMENTED_MESSAGES_ANALYSIS = "enableUnimplementedMessagesAnalysis"; //$NON-NLS-1$
	public static final String ENABLE_UNDECLARED_IDENTIFIERS_ANALYSIS = "enableUndeclaredIdentifiersAnalysis"; //$NON-NLS-1$

	public static Preferences getPluginPreferences() {
		return GemDevUiPlugin.getDefault().getPluginPreferences();
	}

	public static String loadAuthorInitials() {
		return getPluginPreferences().getString(AUTHOR_INITIALS);
	}

	public static SyntaxColoringStyleSet loadStyles() {
		return SyntaxColoringStyleSet.toStyles(getPluginPreferences().getString(SYNTAX_COLORING));
	}

	public static void saveAuthorInitials(final String value) {
		Preconditions.checkNotNull(value);
		getPluginPreferences().setValue(AUTHOR_INITIALS, value);
		GemDevUiPlugin.getDefault().savePluginPreferences();
	}

	public static void saveStyles(final SyntaxColoringStyleSet styles) {
		getPluginPreferences().setValue(SYNTAX_COLORING, SyntaxColoringStyleSet.toString(styles));
		GemDevUiPlugin.getDefault().savePluginPreferences();
	}

	public GsPreferences() {
	}

	@Override
	public void initializeDefaultPreferences() {
		final Preferences p = getPluginPreferences();
		p.setDefault(SYNTAX_COLORING, SyntaxColoringStyleSet.toString(SyntaxColoringStyleSet.createDefault()));
		p.setDefault(AUTHOR_INITIALS, ""); //$NON-NLS-1$
		p.setDefault(ENABLE_SYNTAX_ERROR_ANALYSIS, true);
		p.setDefault(ENABLE_UNUSED_LOCAL_IDENTIFIERS_ANALYSIS, true);
		p.setDefault(ENABLE_UNIMPLEMENTED_MESSAGES_ANALYSIS, true);
		p.setDefault(ENABLE_UNDECLARED_IDENTIFIERS_ANALYSIS, true);
	}
}
