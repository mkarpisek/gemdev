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
package net.karpisek.gemdev.ui.search;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.search.ui.ISearchPage;
import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.Messages;

/**
 * Search source code of all methods which contains requested text.
 */
public class TextSearchPage extends DialogPage implements ISearchPage {
	private Combo projects;
	private StringFieldEditor searchStringEditor;
	private Button caseSensitiveCheckbox;
	private List<ISession> sessions;

	@Override
	public void createControl(final Composite parent) {
		final GridLayoutFactory f = GridLayoutFactory.swtDefaults();

		final Composite c = new Composite(parent, SWT.NONE);
		GridDataFactory.swtDefaults().hint(GridData.FILL_HORIZONTAL, GridData.FILL_VERTICAL).grab(true, true).applyTo(c);

		sessions = Lists.newArrayList();
		sessions.addAll(GemDevUiPlugin.getSessionManager().getAllSessions());
		Collections.sort(sessions, new Comparator<ISession>() {
			@Override
			public int compare(final ISession o1, final ISession o2) {
				return o1.getProject().getName().compareTo(o2.getProject().getName());
			}
		});

		final String[] projectNames = new String[sessions.size()];
		int i = 0;
		for (final ISession s : sessions) {
			projectNames[i++] = s.getProject().getName();
		}
		final Label label = new Label(c, SWT.LEFT);
		label.setText(Messages.TEXT_SEARCH_CONNECTED_PROJECTS + ":"); //$NON-NLS-1$

		projects = new Combo(c, SWT.DROP_DOWN | SWT.READ_ONLY);
		projects.setItems(projectNames);
		projects.select(0);

		searchStringEditor = new StringFieldEditor("searchString", Messages.TEXT_SEARCH_INPUT_DIALOG + ":", c); //$NON-NLS-1$ //$NON-NLS-2$
		caseSensitiveCheckbox = new Button(c, SWT.CHECK);
		caseSensitiveCheckbox.setText(Messages.TEXT_SEARCH_CASE_SENSITIVE_CHECKBOX);

		f.numColumns(2);
		f.generateLayout(c);
		setControl(c);
	}

	@Override
	public boolean performAction() {
		final int index = projects.getSelectionIndex();
		if (index == -1) {
			MessageDialog.openInformation(getShell(), Messages.TEXT_SEARCH_WARNING, Messages.TEXT_SEARCH_NO_PROJECT_SELECTED_ERROR);
			return false;
		}
		if (searchStringEditor.getStringValue().length() <= 0) {
			MessageDialog.openInformation(getShell(), Messages.TEXT_SEARCH_WARNING, Messages.TEXT_SEARCH_EMPTY_TEXT_ERROR);
			return false;
		}

		final ISession session = sessions.get(index);
		NewSearchUI
				.runQueryInBackground(new MethodsWithSubstringSearchQuery(session, searchStringEditor.getStringValue(), caseSensitiveCheckbox.getSelection()));
		return true;
	}

	@Override
	public void setContainer(final ISearchPageContainer container) {
	}
}
