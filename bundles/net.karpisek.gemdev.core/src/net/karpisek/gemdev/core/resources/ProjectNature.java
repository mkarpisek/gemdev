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
package net.karpisek.gemdev.core.resources;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;

import com.google.common.base.Preconditions;

import net.karpisek.gemdev.core.CorePlugin;
import net.karpisek.gemdev.net.actions.broker.Profile;

/**
 * Nature used for GemDev projects.
 */
public class ProjectNature implements IProjectNature {
	public static final String ID = "net.karpisek.gemdev.core.projectNature"; //$NON-NLS-1$

	public static final String SERVER_PORT = "serverPortNumber"; //$NON-NLS-1$
	public static final String SERVER_HOST = "serverIpAddress"; //$NON-NLS-1$
	public static final String PROFILE_NAME = "profileName"; //$NON-NLS-1$
	public static final String PROFILE_USER = "profileUserName"; //$NON-NLS-1$
	public static final String PROFILE_PASSWORD = "profilePassword"; //$NON-NLS-1$
	public static final String SESSION_INITIALIZATION_EXPRESSION = "sessionServerInitString"; //$NON-NLS-1$

	private IProject project;

	@Override
	public void configure() throws CoreException {

	}

	@Override
	public void deconfigure() throws CoreException {

	}

	/**
	 * Answers connection profile of this project.
	 */
	public Profile getProfile() throws CoreException {
		final String profileName = getProjectPersistentProperty(PROFILE_NAME);
		if (profileName == null) {
			return null;
		}
		return new Profile(profileName, getProjectPersistentProperty(PROFILE_USER), getProjectPersistentProperty(PROFILE_PASSWORD));
	}

	@Override
	public IProject getProject() {
		return project;
	}

	public String getServerHost() throws CoreException {
		return getProjectPersistentProperty(SERVER_HOST);
	}

	public int getServerPort() throws CoreException {
		final String portValue = getProjectPersistentProperty(SERVER_PORT);

		Preconditions.checkNotNull(portValue);
		return Integer.valueOf(portValue).intValue();
	}

	public String getServerUrl() {
		try {
			return String.format("http://%s:%d", getServerHost(), getServerPort()); //$NON-NLS-1$
		} catch (final CoreException e) {
			CorePlugin.getDefault().logError(e);
		}
		return ""; //$NON-NLS-1$
	}

	public String getSessionInitializationExpression() throws CoreException {
		return getProjectPersistentProperty(SESSION_INITIALIZATION_EXPRESSION);
	}

	public void setProfile(final Profile p) throws CoreException {
		setProjectPersistentProperty(PROFILE_NAME, p.getName());
		setProjectPersistentProperty(PROFILE_USER, p.getUserName());
		setProjectPersistentProperty(PROFILE_PASSWORD, p.getPassword());
	}

	@Override
	public void setProject(final IProject project) {
		this.project = project;
	}

	public void setServerHost(final String host) throws CoreException {
		Preconditions.checkNotNull(host);

		setProjectPersistentProperty(SERVER_HOST, host);
	}

	public void setServerPort(final int portNumber) throws CoreException {
		setProjectPersistentProperty(SERVER_PORT, String.valueOf(portNumber));
	}

	public void setSessionInitializationExpression(final String value) throws CoreException {
		Preconditions.checkNotNull(value);

		setProjectPersistentProperty(SESSION_INITIALIZATION_EXPRESSION, value);
	}

	private String getProjectPersistentProperty(final String localName) throws CoreException {
		Preconditions.checkNotNull(project);

		return project.getPersistentProperty(new QualifiedName(CorePlugin.PLUGIN_ID, localName));
	}

	private void setProjectPersistentProperty(final String localName, final String value) throws CoreException {
		Preconditions.checkNotNull(project);

		project.setPersistentProperty(new QualifiedName(CorePlugin.PLUGIN_ID, localName), value);
	}
}
