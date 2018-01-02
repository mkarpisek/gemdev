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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PropertyPage;

import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.resources.ProjectNature;
import net.karpisek.gemdev.net.BrokerServer;
import net.karpisek.gemdev.net.actions.broker.Profile;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.editor.GsSourceViewer;
import net.karpisek.gemdev.ui.wizards.NewProjectWizard;
import net.karpisek.gemdev.utils.Utils;

/**
 * Page for setting broker/session server values. Initial values comes from {@link NewProjectWizard} on project creation action. Values are not editable if
 * project is currently connected to db.
 */
public class ProjectServerPropertyPage extends PropertyPage implements IWorkbenchPropertyPage {
	private ServerConfigurationPanel confPanel;
	private SourceViewer text;

	private ProjectNature nature;
	private Profile oldProfile;

	public ProjectServerPropertyPage() {

	}

	@Override
	public boolean performOk() {
		try {
			nature.setServerHost(confPanel.getServerHostName());
			nature.setServerPort(confPanel.getServerPort());
			nature.setSessionInitializationExpression(text.getDocument().get());

			final Profile newProfile = confPanel.getCurrentProfile();
			nature.setProfile(newProfile);
			oldProfile = newProfile;
		} catch (final CoreException e) {
			GemDevUiPlugin.getDefault().logError(e);
			return false;
		}
		return true;
	}

	@Override
	protected Control createContents(final Composite parent) {
		final GridLayoutFactory f = GridLayoutFactory.swtDefaults();
		final Composite c = new Composite(parent, SWT.NONE);
		GridDataFactory.swtDefaults().hint(GridData.FILL_HORIZONTAL, GridData.FILL_VERTICAL).grab(true, true).applyTo(c);

		try {
			nature = (ProjectNature) ((IProject) getElement()).getNature(ProjectNature.ID);
			oldProfile = nature.getProfile();

			confPanel = new ServerConfigurationPanel(c, nature.getServerHost(), nature.getServerPort(), oldProfile) {

				@Override
				protected void dialogChanged() {

				}

				@Override
				protected void setErrorMessage(final String message) {
					ProjectServerPropertyPage.this.setErrorMessage(message);
				}

				@Override
				protected void setMessage(final String message) {
					ProjectServerPropertyPage.this.setMessage(message);
				}
			};

			final Label label = new Label(c, SWT.LEFT);
			label.setText(Messages.PREFERENCES_SESSION_INITIALIZATION_EXPRESSION);
			GridDataFactory.swtDefaults().span(3, 1).align(SWT.FILL, SWT.BEGINNING).applyTo(label);

			text = new GsSourceViewer(c, nature.getSessionInitializationExpression());
			GridDataFactory.swtDefaults().span(3, 1).grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(text.getTextWidget());
		} catch (final CoreException e) {
			GemDevUiPlugin.getDefault().logError(e);
		}

		// disable all widgets if there is opened session on this project
		// it must not be possible to change connection details in the middle of session
		final ISession session = GemDevUiPlugin.getSessionManager().getSession((IProject) getElement());
		if (session != null) {
			confPanel.setEnabled(c, false);
			text.setEditable(false);

			// create short info about current session
			final StyledText text = new StyledText(parent, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
			text.setText(session.toString());
			text.setEditable(false);
			text.setBackground(PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		}

		f.numColumns(3);
		f.generateLayout(c);

		return c;
	}

	@Override
	protected void performDefaults() {
		confPanel.setServerHostName(BrokerServer.DEFAULT_HOST_NAME);
		confPanel.setServerPort(BrokerServer.DEFAULT_PORT);
		text.getDocument().set(""); //$NON-NLS-1$

		confPanel.setProfileName(Profile.DEFAULT_NAME);
		confPanel.setProfileUserName(Profile.DEFAULT_USER_NAME);
		confPanel.setProfilePassword(Utils.md5sum(Profile.DEFAULT_PASSWORD));

		oldProfile = new Profile(confPanel.getProfileName(), confPanel.getProfileUserName(), confPanel.getProfilePassword());

		super.performDefaults();
	}
}
