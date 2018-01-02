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

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.resources.ProjectNature;
import net.karpisek.gemdev.net.ActionException;
import net.karpisek.gemdev.net.BrokerServer;
import net.karpisek.gemdev.net.BrokerServerClient;
import net.karpisek.gemdev.net.actions.broker.GetBrokerServerInfo;
import net.karpisek.gemdev.net.actions.broker.GetBrokerServerInfo.Info;
import net.karpisek.gemdev.net.actions.broker.Profile;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.utils.Utils;

/**
 * Widget for configuration of connection used by GemDev project.
 */
public abstract class ServerConfigurationPanel {
	private StringFieldEditor serverHostNameEditor;
	private IntegerFieldEditor serverPortEditor;
	private StringFieldEditor profileNameEditor;
	private StringFieldEditor profileUserNameEditor;
	private StringFieldEditor profilePasswordEditor;
	private Button selectProfileButton;
	private Button validateButton;

	private boolean newPasswordEntered;

	/**
	 * @param parent component
	 * @param serverHostName initial value
	 * @param serverPort initial value
	 * @param profile initial value
	 */
	public ServerConfigurationPanel(final Composite parent, final String serverHostName, final int serverPort, final Profile profile) {
		createServerControls(parent, serverHostName, serverPort, profile);
		createValidationControls(parent);
	}

	/**
	 * @return new profile object initialised from field editor values
	 */
	public Profile getCurrentProfile() {
		return new Profile(profileNameEditor.getStringValue(), profileUserNameEditor.getStringValue(),
				newPasswordEntered ? Utils.md5sum(profilePasswordEditor.getStringValue()) : profilePasswordEditor.getStringValue());
	}

	public String getProfileName() {
		return profileNameEditor.getStringValue();
	}

	public String getProfilePassword() {
		return profilePasswordEditor.getStringValue();
	}

	public String getProfileUserName() {
		return profileUserNameEditor.getStringValue();
	}

	public String getServerHostName() {
		return serverHostNameEditor.getStringValue();
	}

	public int getServerPort() {
		return serverPortEditor.getIntValue();
	}

	public void setEnabled(final Composite parent, final boolean enabled) {
		for (final StringFieldEditor editor : Lists.newArrayList(serverHostNameEditor, serverPortEditor, profileNameEditor, profileUserNameEditor,
				profilePasswordEditor)) {
			editor.getLabelControl(parent).setEnabled(enabled);
			editor.getTextControl(parent).setEnabled(enabled);
		}

		selectProfileButton.setEnabled(enabled);
		validateButton.setEnabled(enabled);
	}

	public void setProfileName(final String newValue) {
		profileNameEditor.setStringValue(newValue);
	}

	public void setProfilePassword(final String newValue) {
		newPasswordEntered = false;
		profilePasswordEditor.setStringValue(newValue);
	}

	public void setProfileUserName(final String newValue) {
		profileUserNameEditor.setStringValue(newValue);
	}

	public void setServerHostName(final String newValue) {
		serverHostNameEditor.setStringValue(newValue);
	}

	public void setServerPort(final int newValue) {
		serverPortEditor.setStringValue(Integer.toString(newValue));
	}

	private void createBrowseConnectionProfilesButton(final Composite parent) {
		selectProfileButton = new Button(parent, SWT.PUSH);
		selectProfileButton.setText(Messages.BROWSE_BUTTON_LABEL);
		selectProfileButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final BrokerServerClient client = new BrokerServerClient(serverHostNameEditor.getStringValue(), serverPortEditor.getIntValue());
				try {
					final Info info = client.execute(new GetBrokerServerInfo());
					final ElementListSelectionDialog dlg = new ElementListSelectionDialog(parent.getShell(), new LabelProvider());
					dlg.setElements(info.getProfileNames().toArray());
					dlg.setTitle(Messages.SELECT_PROFILE_DIALOG_TITLE);
					dlg.setMessage(Messages.SELECT_PROFILE_DIALOG_MESSAGE);
					if (dlg.open() == Window.OK) {
						profileNameEditor.setStringValue((String) dlg.getResult()[0]);
					}
				} catch (final ActionException ex) {
					GemDevUiPlugin.getDefault().logError(ex);
					setErrorMessage(Messages.BROKER_SERVER_CONNECTION_ERROR);
				} finally {
					client.close();
				}
			}
		});
	}

	private void createEmptyLabel(final Composite parent) {
		new Label(parent, SWT.NONE);
	}

	private void createSeparator(final Composite parent) {
		final Label separator2 = new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR);
		GridDataFactory.swtDefaults().span(3, 1).align(SWT.FILL, SWT.BEGINNING).hint(GridData.FILL_HORIZONTAL, SWT.DEFAULT).grab(true, false)
				.applyTo(separator2);
	}

	private void createServerControls(final Composite parent, final String serverHost, final int serverPort, final Profile profile) {
		createTitleLabel(parent, Messages.BROKER_SERVER);

		serverHostNameEditor = new StringFieldEditor("serverHost", Messages.PREFERENCES_IP_ADDRESS + ":", parent); //$NON-NLS-1$ //$NON-NLS-2$
		serverHostNameEditor.setStringValue(serverHost);
		createEmptyLabel(parent);
		serverPortEditor = new IntegerFieldEditor("serverPortNumber", Messages.PREFERENCES_PORT + ":", parent); //$NON-NLS-1$ //$NON-NLS-2$
		serverPortEditor.setStringValue(Integer.toString(serverPort));
		createEmptyLabel(parent);
		createSeparator(parent);

		createTitleLabel(parent, Messages.PROFILE_TITLE);
		profileNameEditor = new StringFieldEditor(ProjectNature.PROFILE_NAME, Messages.PROFILE_NAME + ":", parent); //$NON-NLS-1$
		profileNameEditor.setStringValue(profile.getName());
		createBrowseConnectionProfilesButton(parent);

		profileUserNameEditor = new StringFieldEditor(ProjectNature.PROFILE_USER, Messages.PROFILE_USER_NAME + ":", parent); //$NON-NLS-1$
		profileUserNameEditor.setStringValue(profile.getUserName());
		createEmptyLabel(parent);

		profilePasswordEditor = new StringFieldEditor(ProjectNature.PROFILE_PASSWORD, Messages.PROFILE_PASSWORD + ":", parent); //$NON-NLS-1$
		profilePasswordEditor.setStringValue(profile.getPassword());
		profilePasswordEditor.getTextControl(parent).setEchoChar('*');
		createEmptyLabel(parent);

		for (final StringFieldEditor editor : Lists.newArrayList(profileNameEditor, profileUserNameEditor)) {
			editor.getTextControl(parent).addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(final ModifyEvent e) {
					dialogChanged();
				}
			});
		}

		profilePasswordEditor.getTextControl(parent).addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				newPasswordEntered = true;
				dialogChanged();
			}
		});

		createSeparator(parent);
	}

	private void createTitleLabel(final Composite parent, final String title) {
		final Label serverLabel = new Label(parent, SWT.LEFT);
		serverLabel.setText(title);
		GridDataFactory.swtDefaults().span(3, 1).align(SWT.FILL, SWT.BEGINNING).hint(GridData.FILL_HORIZONTAL, SWT.DEFAULT).grab(true, false)
				.applyTo(serverLabel);
	}

	private void createValidationControls(final Composite parent) {
		createEmptyLabel(parent);
		createEmptyLabel(parent);

		validateButton = new Button(parent, SWT.PUSH);
		validateButton.setText(Messages.PREFERENCES_VALIDATE);
		validateButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final String errorMessage = BrokerServer.checkConnection(serverHostNameEditor.getStringValue(), serverPortEditor.getIntValue(),
						getCurrentProfile());

				if (errorMessage == null) {
					setErrorMessage(null);
					setMessage(Messages.NEW_PROJECT_WIZARD_CONNECTION_WORKS);
				} else {
					setErrorMessage(errorMessage);
					setMessage(null);
				}
			}
		});
	}

	protected abstract void dialogChanged();

	protected abstract void setErrorMessage(final String message);

	protected abstract void setMessage(String message);
}
