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

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.karpisek.gemdev.ui.Messages;

/**
 * General preferences for GemDev plugins.
 */
public class MainPage extends PreferencePage implements IWorkbenchPreferencePage {

	private StringFieldEditor authorInitials;

	public MainPage() {
	}

	public MainPage(final String title) {
		super(title);
	}

	public MainPage(final String title, final ImageDescriptor image) {
		super(title, image);
	}

	@Override
	public void init(final IWorkbench workbench) {

	}

	@Override
	public boolean performOk() {
		GsPreferences.saveAuthorInitials(authorInitials.getStringValue());
		return true;
	}

	@Override
	protected Control createContents(final Composite parent) {
		final Composite c = new Composite(parent, SWT.NONE);
		authorInitials = new StringFieldEditor("authorInitials", Messages.PREFERENCES_AUTHOR_INITIALS + ":", c); //$NON-NLS-1$ //$NON-NLS-2$
		authorInitials.setStringValue(GsPreferences.loadAuthorInitials());
		return c;
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();

		authorInitials.setStringValue(""); //$NON-NLS-1$
	}
}
