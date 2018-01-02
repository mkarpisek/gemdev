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

import org.eclipse.ui.texteditor.templates.TemplatePreferencePage;

import net.karpisek.gemdev.ui.GemDevUiPlugin;

/**
 * Page for custom templates for smalltalk editors.
 */
public class GsTemplatePreferencePage extends TemplatePreferencePage {
	public GsTemplatePreferencePage() {
		setPreferenceStore(GemDevUiPlugin.getDefault().getPreferenceStore());
		setTemplateStore(GemDevUiPlugin.getDefault().getTemplateStore());
		setContextTypeRegistry(GemDevUiPlugin.getDefault().getContextTypeRegistry());

	}

	@Override
	public boolean performOk() {
		final boolean ok = super.performOk();

		GemDevUiPlugin.getDefault().savePluginPreferences();

		return ok;
	}

	@Override
	protected boolean isShowFormatterSetting() {
		return false;
	}
}
