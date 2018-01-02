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
/**
 * 
 */
package net.karpisek.gemdev.core.resources;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import net.karpisek.gemdev.core.CorePlugin;
import net.karpisek.gemdev.net.actions.broker.Profile;

/**
 * Encapsulation of process of creating new GemDev project in eclipse workspace. Project is identified by name.
 */
public class ProjectCreator {
	private final String name;
	private final IProject project;
	private final String serverHostName;
	private final int serverPort;
	private final Profile profile;

	public ProjectCreator(final String name, final String serverHostName, final int serverPort, final Profile profile) {
		this.name = name;
		this.serverHostName = serverHostName;
		this.serverPort = serverPort;
		this.project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		this.profile = profile;
	}

	public IProject createProject(final IProgressMonitor monitor) throws CoreException {
		if (this.exists()) {
			throw new CoreException(new Status(IStatus.ERROR, CorePlugin.PLUGIN_ID, String.format("Project with name '%s' already exists.", name))); //$NON-NLS-1$
		}

		try {
			project.create(monitor);
			project.open(monitor);
			createProjectNature();
		} finally {
			if (monitor.isCanceled()) {
				cleanUp();
			}
		}
		return project;
	}

	public boolean exists() {
		return project.exists();
	}

	public String getProjectName() {
		return name;
	}

	public String getServerHostName() {
		return serverHostName;
	}

	public int getServerPortNumber() {
		return serverPort;
	}

	private void cleanUp() throws CoreException {
		if (!project.exists()) {
			return;
		}

		project.delete(true, null);
	}

	private void createProjectNature() throws CoreException {
		final IProjectDescription description = project.getDescription();
		final String[] natures = description.getNatureIds();
		final String[] newNatures = new String[natures.length + 1];
		System.arraycopy(natures, 0, newNatures, 0, natures.length);
		newNatures[natures.length] = ProjectNature.ID;
		final IStatus status = ResourcesPlugin.getWorkspace().validateNatureSet(natures);

		if (status.getCode() == IStatus.OK) {
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
		} else {
			throw new CoreException(status);
		}

		final ProjectNature nature = (ProjectNature) project.getNature(ProjectNature.ID);
		nature.setServerHost(serverHostName);
		nature.setServerPort(serverPort);
		nature.setSessionInitializationExpression(""); //$NON-NLS-1$
		if (profile != null) {
			nature.setProfile(profile);
		}
	}
}
