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
package net.karpisek.gemdev.core.db;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import net.karpisek.gemdev.core.CorePlugin;
import net.karpisek.gemdev.core.resources.ProjectNature;

/**
 * Manager for handling life cycle of sessions. Each session is associated with GemDev project, there should not be more than one session per project. You can
 * add listener to be notified about changes.
 */
public class SessionManager {
	private final ISessionFactory sessionFactory;
	private final ListenerList<ISessionManagerListener> listeners;
	private final Map<IProject, ISession> sessions;
	private final Map<IProject, IProject> connectingProjects;

	public SessionManager(final ISessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.listeners = new ListenerList<>();
		this.sessions = new ConcurrentHashMap<IProject, ISession>();
		this.connectingProjects = new ConcurrentHashMap<IProject, IProject>();
	}

	public void addListener(final ISessionManagerListener listener) {
		listeners.add(listener);
	}

	/**
	 * Closes all currently opened sessions.
	 * 
	 * @param monitor
	 */
	public void closeAllSessions(final IProgressMonitor monitor) {
		Preconditions.checkNotNull(monitor);

		for (final ISession session : getAllSessions()) {
			closeSession(session.getProject(), monitor);
		}
	}

	public void closeSession(final IProject project, final IProgressMonitor monitor) {
		Preconditions.checkNotNull(monitor);

		checkProjectIsValidArgument(project);

		final ISession session = sessions.get(project);
		sessions.remove(project);
		session.tearDown(monitor);

		for (final Object o : listeners.getListeners()) {
			((ISessionManagerListener) o).sessionClosed(session);
		}
	}

	/**
	 * @return copy of collection (modification of it does not affect manager internals) with all currently opened sessions.
	 */
	public Set<ISession> getAllSessions() {
		return Sets.newHashSet(sessions.values().iterator());
	}

	public ISession getSession(final IProject project) {
		return sessions.get(project);
	}

	public boolean hasSession(final IProject selectedProject) {
		return getSession(selectedProject) != null;
	}

	public boolean isConnectingProject(final IProject project) {
		return connectingProjects.containsKey(project);
	}

	/**
	 * Opens new session on requested project.
	 * 
	 * @param project in eclipse workspace with GemDev nature ({@link ProjectNature})
	 * @param monitor which can be used for cancelling operation (or {@link NullProgressMonitor})
	 * @return new session
	 * @throws SessionManagerException in case session already exists for requested project.
	 */
	public ISession openSession(final IProject project, final IProgressMonitor monitor) throws SessionManagerException {
		checkProjectIsValidArgument(project);

		if (sessions.containsKey(project)) {
			throw new SessionManagerException(String.format("Session for project '%s' already exists.", project.getName())); //$NON-NLS-1$
		}
		if (monitor.isCanceled()) {
			return null;
		}
		connectingProjects.put(project, project);

		try {
			// create new session object
			final ISession session = sessionFactory.newSession(project);
			if (monitor.isCanceled()) {
				return null;
			}

			// setup session internally
			session.setUp(monitor);
			if (monitor.isCanceled()) {
				return null;
			}

			// inform everybody that we have new session
			sessions.put(project, session);
			connectingProjects.remove(project);
			for (final Object o : listeners.getListeners()) {
				((ISessionManagerListener) o).sessionOpened(session);
			}
			if (monitor.isCanceled()) {
				return null;
			}
			return session;
		} catch (final CoreException e) {
			throw new SessionManagerException("Error openning session", e.getCause()); //$NON-NLS-1$
		} finally {
			connectingProjects.remove(project);

			if (monitor.isCanceled()) {
				sessions.remove(project);
			}
		}
	}

	public void removeListener(final ISessionManagerListener listener) {
		listeners.remove(listener);
	}

	@Override
	public String toString() {
		return String.format("anSessionManager(sessions=%d listeners=%d)", sessions.size(), listeners.size()); //$NON-NLS-1$
	}

	/**
	 * Ensure that for passed project can be created session.
	 * 
	 * @param project in workspace which should exist, be opened and it has GemDev nature
	 * @throws IllegalArgumentException in case something is wrong with project
	 */
	private void checkProjectIsValidArgument(final IProject project) throws IllegalArgumentException {
		Preconditions.checkNotNull(project);

		if (!project.exists()) {
			throw new IllegalArgumentException("Session can not be opened on non-existing project."); //$NON-NLS-1$
		}
		if (!project.isOpen()) {
			throw new IllegalArgumentException("Session can not be opened on closed project."); //$NON-NLS-1$
		}
		try {
			if (!project.hasNature(ProjectNature.ID)) {
				throw new IllegalArgumentException("Project for session is not GemDev project."); //$NON-NLS-1$
			}
		} catch (final CoreException e) {
			CorePlugin.getDefault().logError(e);
		}
	}
}
