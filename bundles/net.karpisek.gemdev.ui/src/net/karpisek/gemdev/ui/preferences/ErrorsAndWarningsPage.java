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

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.google.common.collect.Maps;

import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.Messages;

/**
 * Enable showing problem markers in method editor for various kinds of problems.
 */
public class ErrorsAndWarningsPage extends PreferencePage implements IWorkbenchPreferencePage {

	private Map<String, BooleanFieldEditor> editors;

	public ErrorsAndWarningsPage() {
	}

	public ErrorsAndWarningsPage(final String title) {
		super(title);
	}

	public ErrorsAndWarningsPage(final String title, final ImageDescriptor image) {
		super(title, image);
	}

	@Override
	public void init(final IWorkbench workbench) {
	}

	@Override
	public boolean performOk() {
		for (final BooleanFieldEditor editor : editors.values()) {
			editor.store();
		}
		GemDevUiPlugin.getDefault().savePluginPreferences();

		return true;
	}

	@Override
	protected Control createContents(final Composite parent) {
		final Composite c = new Composite(parent, SWT.NONE);

		final String[][] keys = new String[][] { { GsPreferences.ENABLE_SYNTAX_ERROR_ANALYSIS, Messages.PREFERENCES_SHOW_SYNTAX_ERRORS },
				{ GsPreferences.ENABLE_UNDECLARED_IDENTIFIERS_ANALYSIS, Messages.PREFERENCES_MARK_UNDECLARED_IDENTIFIERS },
				{ GsPreferences.ENABLE_UNUSED_LOCAL_IDENTIFIERS_ANALYSIS, Messages.PREFERENCES_MARK_UNUSED_PARAMETERS },
				{ GsPreferences.ENABLE_UNIMPLEMENTED_MESSAGES_ANALYSIS, Messages.PREFERENCES_MARK_UNIMPLEMENTED_MESSAGES } };

		editors = Maps.newHashMap();
		for (final String key[] : keys) {
			final BooleanFieldEditor editor = new BooleanFieldEditor(key[0], key[1], c);
			editor.setPreferenceStore(GemDevUiPlugin.getDefault().getPreferenceStore());
			editor.load();
			editors.put(key[0], editor);
		}
		return c;
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();

		for (final BooleanFieldEditor editor : editors.values()) {
			editor.loadDefault();
		}
	}
}
