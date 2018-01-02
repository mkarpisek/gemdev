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
package net.karpisek.gemdev.team;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageRegistry;
import org.osgi.framework.BundleContext;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.ui.AbstractGsUiPlugin;

/**
 * 

 */
public class TeamPlugin extends AbstractGsUiPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "net.karpisek.gemdev.team"; //$NON-NLS-1$

	public static final String REPOSITORY_ICON = "repository.gif"; //$NON-NLS-1$
	public static final String FOLDER_ICON = "folder.gif"; //$NON-NLS-1$

	// The shared instance
	private static TeamPlugin plugin;

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static TeamPlugin getDefault() {
		return plugin;
	}

	private Map<IProject, WorkingCopy> workingCopies;

	public TeamPlugin() {
		super(PLUGIN_ID);
	}

	public synchronized WorkingCopy getWorkingCopy(final ISession session) {
		WorkingCopy wc = workingCopies.get(session.getProject());
		if (wc == null) {
			wc = new WorkingCopy(session.getProject());
			workingCopies.put(wc.getProject(), wc);
		}
		return wc;
	}

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		workingCopies = Maps.newHashMap();
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		workingCopies.clear();
		plugin = this;
		super.stop(context);
	}

	@Override
	protected void initializeImageRegistry(final ImageRegistry registry) {
		initializeImageRegistry(registry, Lists.newArrayList(REPOSITORY_ICON, FOLDER_ICON));
	}

}
