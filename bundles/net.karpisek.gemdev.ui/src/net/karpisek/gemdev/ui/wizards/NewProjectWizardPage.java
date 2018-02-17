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
package net.karpisek.gemdev.ui.wizards;

import java.text.MessageFormat;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import net.karpisek.gemdev.core.resources.ProjectCreator;
import net.karpisek.gemdev.net.BrokerServer;
import net.karpisek.gemdev.net.actions.broker.Profile;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.preferences.ServerConfigurationPanel;
import net.karpisek.gemdev.utils.Utils;

/**
 * Page with definition of values for GemDev project.
 */
public class NewProjectWizardPage extends WizardPage {
	private StringFieldEditor projectNameEditor;
	private ServerConfigurationPanel confPanel;

	public NewProjectWizardPage(final ISelection selection) {
		super("projectPage"); //$NON-NLS-1$
		setTitle(Messages.NEW_PROJECT_WIZARD_TITLE);
		setDescription(Messages.NEW_PROJECT_WIZARD_DESCRIPTION);
	}

	@Override
	public void createControl(final Composite parent) {
		final GridLayoutFactory f = GridLayoutFactory.swtDefaults();
		final Composite c = new Composite(parent, SWT.NONE);
		GridDataFactory.swtDefaults().hint(GridData.FILL_HORIZONTAL, GridData.FILL_VERTICAL).grab(true, true).applyTo(c);

		createProjectNameControls(c);
		confPanel = new ServerConfigurationPanel(c, BrokerServer.DEFAULT_HOST_NAME, BrokerServer.DEFAULT_PORT,
				new Profile(Profile.DEFAULT_NAME, Profile.DEFAULT_USER_NAME, Utils.md5sum(Profile.DEFAULT_PASSWORD))) {

			@Override
			protected void dialogChanged() {
				NewProjectWizardPage.this.dialogChanged();
			}

			@Override
			protected void setErrorMessage(final String message) {
				NewProjectWizardPage.this.setErrorMessage(message);
			}

			@Override
			protected void setMessage(final String message) {
				NewProjectWizardPage.this.setMessage(message);
			}
		};

		f.numColumns(3);
		f.generateLayout(c);

		projectNameEditor.setFocus();

		dialogChanged();
		setControl(c);
	}

	/**
	 * Creates {@link ProjectCreator} based on values from the page.
	 * 
	 * @return null in case page can not be finished (error in input values) or new instance of creator.
	 */
	public ProjectCreator getProjectCreator() {
		if (!isPageComplete()) {
			return null;
		}

		return new ProjectCreator(projectNameEditor.getStringValue(), confPanel.getServerHostName(), confPanel.getServerPort(), getCurrentProfile());
	}

	private void createEmptyLabel(final Composite parent) {
		new Label(parent, SWT.NONE);
	}

	private void createProjectNameControls(final Composite parent) {
		projectNameEditor = new StringFieldEditor("projectName", Messages.NEW_PROJECT_WIZARD_PROJECT_NAME + ":", parent); //$NON-NLS-1$ //$NON-NLS-2$
		projectNameEditor.setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
		projectNameEditor.setPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent event) {
				dialogChanged();
			}
		});
		createEmptyLabel(parent);
		createSeparator(parent);
	}

	private void createSeparator(final Composite parent) {
		final Label separator2 = new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR);
		GridDataFactory.swtDefaults().span(3, 1).align(SWT.FILL, SWT.BEGINNING).hint(GridData.FILL_HORIZONTAL, SWT.DEFAULT).grab(true, false)
				.applyTo(separator2);
	}

	private void dialogChanged() {
		final String projectName = this.projectNameEditor.getStringValue();

		if (projectName.length() == 0) {
			updateStatus(Messages.NEW_PROJECT_WIZARD_PROJECT_NAME_IS_EMPTY_ERROR);
			return;
		}

		if (ResourcesPlugin.getWorkspace().getRoot().getProject(projectName).exists()) {
			updateStatus(MessageFormat.format(Messages.NEW_PROJECT_WIZARD_PROJECT_ALREADY_EXISTS_ERROR, projectName));
			return;
		}

		if (confPanel.getServerHostName().length() == 0) {
			updateStatus(Messages.NEW_PROJECT_WIZARD_IP_ADDRESS_IS_EMPTY_ERROR);
			return;
		}

		String status = null;
		final int port = confPanel.getServerPort();		
		if (port < 1024 || port > 65536) {
			status = Messages.NEW_PROJECT_WIZARD_PORT_NUMBER_ERROR;
		}

		if (confPanel.getProfileName().length() <= 0) {
			updateStatus(MessageFormat.format(Messages.NEW_PROJECT_WIZARD_GS_PARAMETER_MISSING_ERROR, Messages.PROFILE_NAME));
			return;
		}

		if (confPanel.getProfileUserName().length() <= 0) {
			updateStatus(MessageFormat.format(Messages.NEW_PROJECT_WIZARD_GS_PARAMETER_MISSING_ERROR, Messages.PROFILE_USER_NAME));
			return;
		}

		if (confPanel.getProfilePassword().length() <= 0) {
			updateStatus(MessageFormat.format(Messages.NEW_PROJECT_WIZARD_GS_PARAMETER_MISSING_ERROR, Messages.PROFILE_PASSWORD));
			return;
		}

		if (status != null) {
			updateStatus(status);
			return;
		}
		updateStatus(null);
	}

	/**
	 * @return new profile object initialised from field editor values
	 */
	private Profile getCurrentProfile() {
		return confPanel.getCurrentProfile();
	}

	private void updateStatus(final String message) {
		if (message == null) {
			setErrorMessage(null);
		} else {
			setErrorMessage(message);
		}
		setPageComplete(message == null);
	}
}