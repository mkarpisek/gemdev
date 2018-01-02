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
package net.karpisek.gemdev.core.db;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.karpisek.gemdev.core.CorePlugin;
import net.karpisek.gemdev.core.Messages;
import net.karpisek.gemdev.core.model.IMethod;
import net.karpisek.gemdev.core.resources.MethodFileCache;
import net.karpisek.gemdev.core.resources.ProjectNature;
import net.karpisek.gemdev.net.ActionException;
import net.karpisek.gemdev.net.BrokerServerClient;
import net.karpisek.gemdev.net.FatalTransportException;
import net.karpisek.gemdev.net.ISessionAction;
import net.karpisek.gemdev.net.SessionClient;
import net.karpisek.gemdev.net.actions.KillSessionServer;
import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.net.actions.PrintIt;
import net.karpisek.gemdev.net.actions.broker.CreateSession;
import net.karpisek.gemdev.net.actions.broker.Profile;
import net.karpisek.gemdev.net.actions.category.CreateCategory;
import net.karpisek.gemdev.net.actions.category.DeleteCategory;
import net.karpisek.gemdev.net.actions.clazz.CreateClass;
import net.karpisek.gemdev.net.actions.clazz.DeleteClass;
import net.karpisek.gemdev.net.actions.method.CreateMethod;
import net.karpisek.gemdev.net.actions.system.AbortTransaction;
import net.karpisek.gemdev.net.actions.system.CommitTransaction;
import net.karpisek.gemdev.utils.Pair;

/**
 * Default implementation of session interface.
 */
public class Session extends AbstractSession {
	private final String id;
	private final MethodFileCache fileCache;
	private final Map<TargetSession, SessionClient> clients;
	private final Profile profile;

	public Session(final IProject project) throws CoreException {
		super(project, new ObjectCacheFactory());
		this.id = createId(project);
		this.fileCache = new MethodFileCache(ResourcesPlugin.getWorkspace());

		final ProjectNature nature = (ProjectNature) project.getNature(ProjectNature.ID);

		final String ipAddress = nature.getServerHost();
		int portNumber = nature.getServerPort();
		int portNumber2 = -1;
		profile = nature.getProfile();
		if (profile != null) {
			// we are using broker server ! - we need to get/create GS session for us
			final BrokerServerClient brokerClient = new BrokerServerClient(nature.getServerHost(), nature.getServerPort());
			portNumber = brokerClient.execute(new CreateSession(getId() + "_1", profile)); //$NON-NLS-1$
			portNumber2 = brokerClient.execute(new CreateSession(getId() + "_2", profile)); //$NON-NLS-1$
		}

		clients = Maps.newHashMap();
		clients.put(TargetSession.MAIN, new SessionClient(ipAddress, portNumber));
		if (portNumber2 != -1) {
			clients.put(TargetSession.SEARCH, new SessionClient(ipAddress, portNumber2));
		}
	}

	@Override
	public void abortTransaction() {
		execute(new AbortTransaction());
	}

	@Override
	public void commitTransaction() {
		if (!execute(new CommitTransaction())) {
			abortTransaction();
			throw new CommitFailedException();
		}
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Session) {
			final Session other = (Session) obj;
			return Objects.equal(getProject().getName(), other.getProject().getName());
		}
		return false;
	}

	@Override
	public <T> T execute(final ISessionAction<T> action) {
		return execute(TargetSession.MAIN, action);
	}

	@Override
	public <T> T execute(final TargetSession target, final ISessionAction<T> action) {
		SessionClient targetSession = clients.get(target);
		if (targetSession == null) {
			targetSession = clients.get(TargetSession.MAIN);
		}
		return targetSession.execute(action);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public DbMethod getMethod(final IFile file) {
		try {
			final MethodReference ref = fileCache.getMethodReference(file);
			if (ref == null) {
				return null;
			}
			final DbClass c = getCachedClass(ref.getClassName());
			if (c == null) {
				return null;
			}
			return (DbMethod) c.getMethod(ref.getMethodName(), ref.isInstanceSide());
		} catch (final CoreException e) {
			CorePlugin.getDefault().logError(e);
		}
		return null;
	}

	@Override
	public IFile getMethodFile(final MethodReference ref) throws CoreException {
		return fileCache.getFile(getProject().getName(), ref);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getProject().getName());
	}

	@Override
	public void refreshCachedValues(final IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			return;
		}
		// abort current session transaction to get new view of DB
		abortTransaction();

		// fetching
		super.refreshCachedValues(monitor);
	}

	@Override
	public void setUp(final IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			return;
		}

		refreshCachedValues(monitor);
		executeInitializationExpression(monitor);
	}

	@Override
	public void tearDown(final IProgressMonitor monitor) {
		try {
			fileCache.save();
			if (profile != null) {
				// this means broker server created for us new GS session -> wee need to close it
				for (final TargetSession target : clients.keySet()) {
					try {
						execute(target, new KillSessionServer());
					} catch (final FatalTransportException e) {
						// be silent - this is ok - server is not able to send response if it is dead
					}
				}
			}
			for (final SessionClient client : clients.values()) {
				client.close();
			}
		} catch (final CoreException e) {
			CorePlugin.getDefault().logError(e);
		}
	}

	@Override
	public String toString() {
		// using new session server created by broker server
		try {
			final ProjectNature nature = (ProjectNature) getProject().getNature(ProjectNature.ID);
			final StringBuilder sessionsString = new StringBuilder();
			sessionsString.append(clients.get(TargetSession.MAIN).getUrlString()).append(","); //$NON-NLS-1$
			sessionsString.append(clients.get(TargetSession.SEARCH).getUrlString());

			return MessageFormat.format(Messages.SESSION_CREATED_BY_BROKER_REPORT, nature.getProject().getName(), sessionsString, nature.getServerUrl());
		} catch (final CoreException e) {
			CorePlugin.getDefault().logError(e);
		}
		return super.toString();
	}

	private void executeInitializationExpression(final IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			return;
		}

		try {
			final ProjectNature nature = (ProjectNature) getProject().getNature(ProjectNature.ID);
			final String expr = nature.getSessionInitializationExpression();
			if (expr != null && expr.length() > 0) {
				try {
					for (final TargetSession target : clients.keySet()) {
						execute(target, new PrintIt(expr));
					}
				} catch (final ActionException e) {
					CorePlugin.getDefault().logInfo("Error during executing initialization string%n%s%n", expr); //$NON-NLS-1$
				}
			}
		} catch (final CoreException e) {
			CorePlugin.getDefault().logError(e);
		}
	}

	protected String createId(final IProject project) {
		String ipAddress = "unknown"; //$NON-NLS-1$
		String hash = "unknown"; //$NON-NLS-1$
		try {
			ipAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (final UnknownHostException e) {
			throw new RuntimeException("Session ID generation error", e); //$NON-NLS-1$
		}
		final String s = project.getLocationURI().toASCIIString();
		try {
			final MessageDigest m = MessageDigest.getInstance("MD5"); //$NON-NLS-1$
			m.update(s.getBytes(), 0, s.length());
			hash = new BigInteger(1, m.digest()).toString(16);
		} catch (final NoSuchAlgorithmException e) {
			throw new RuntimeException("Session ID generation error", e); //$NON-NLS-1$
		}
		return ipAddress + "_" + hash; //$NON-NLS-1$
	}

	@Override
	protected void createRemoteCategory(final DbBehavior receiver, final String categoryName) {
		abortTransaction();
		execute(new CreateCategory(receiver.getClassName(), receiver.isInstanceSide(), categoryName));
		commitTransaction();
	}

	@Override
	protected Pair<String, String> createRemoteClass(final String definition) {
		abortTransaction();
		final String className = execute(new CreateClass(definition));
		commitTransaction();

		final String superclassName = execute(new PrintIt(String.format("%s superclass name", className))); //$NON-NLS-1$
		return new Pair<String, String>(className, superclassName);
	}

	@Override
	protected String createRemoteMethod(final DbCategory category, final String sourceCode) {
		abortTransaction();
		final String className = category.getBehavior().getClassName();
		final boolean instanceSide = category.isInstanceSide();
		final String selector = execute(new CreateMethod(className, instanceSide, category.getName(), sourceCode));
		commitTransaction();
		return selector;
	}

	@Override
	protected List<MethodReference> deleteRemoteCategory(final DbCategory category) {
		abortTransaction();
		final List<MethodReference> deletedMethods = Lists.newLinkedList();
		for (final IMethod m : category.getMethods()) {
			deletedMethods.add(m.asReference());
		}
		execute(new DeleteCategory(category.getBehavior().getClassName(), category.isInstanceSide(), category.getName()));
		commitTransaction();
		return deletedMethods;
	}

	@Override
	protected void deleteRemoteClass(final DbClass c) {
		abortTransaction();
		execute(new DeleteClass(c.getClassName()));
		commitTransaction();
	}

	@Override
	protected void deleteRemoteMethod(final DbMethod m) {
		abortTransaction();
		final String className = m.getBehavior().getClassName();
		final String expr = String.format("%s %s removeSelector: #%s ifAbsent: []", //$NON-NLS-1$
				className, m.isInstanceSide() ? "" : "class", //$NON-NLS-1$ //$NON-NLS-2$
				m.getName());
		execute(new PrintIt(expr));
		commitTransaction();
	}
}