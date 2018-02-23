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

import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import net.karpisek.gemdev.net.ISessionAction;
import net.karpisek.gemdev.net.actions.MethodReference;

/**
 * Session represents one connection to session server. There should be always only one session per smalltalk project. Use {@link SessionManager} for managing
 * sessions.
 */
public interface ISession {
	public enum TargetSession {
		MAIN, SEARCH
	}

	/**
	 * Execute abort in {@link TargetSession#MAIN} target session
	 */
	public void abortTransaction();

	public void addListener(ISessionListener listener);

	/**
	 * Execute commit in {@link TargetSession#MAIN} target session
	 * 
	 * @throws CommitFailedException if commit into db fail
	 */
	public void commitTransaction();

	/**
	 * Creates new category in receiver in {@link TargetSession#MAIN} target session. Updates session cache. Performs abort + commit.
	 * 
	 * @return new category object
	 * @throws SessionActionException in case it fails.
	 */
	public DbCategory createCategory(DbBehavior receiver, String categoryName);

	/**
	 * Creates new DB class with definition expression in {@link TargetSession#MAIN} target session. Updates session cache. Performs abort + commit.
	 * 
	 * @return newly created class
	 * @throws SessionActionException in case it fails.
	 */
	public DbClass createClass(String definition);

	/**
	 * Creates new category in class in {@link TargetSession#MAIN} target session. Updates session cache. Performs abort + commit.
	 * 
	 * @return new method object
	 * @throws SessionActionException in case it fails.
	 */
	public DbMethod createMethod(DbCategory category, String sourceCode);

	public void deleteCategory(DbCategory c);

	/**
	 * Deletes existing class from DB in {@link TargetSession#MAIN} target session Updates session cache. Performs abort + commit.
	 * 
	 * @throws SessionActionException in case it fails.
	 */
	public void deleteClass(DbClass c);

	/**
	 * Deletes existing method from DB in {@link TargetSession#MAIN} target session Updates session cache. Performs abort + commit.
	 * 
	 * @throws SessionActionException in case it fails.
	 */
	public void deleteMethod(DbMethod m);

	/**
	 * Execute action in {@link TargetSession#MAIN} target session
	 * 
	 * @param <T> is type of return value of action
	 * @param action which should be executed
	 * @return result of action
	 */
	public <T> T execute(ISessionAction<T> action);

	/**
	 * Execute action on session server for this session. In case target session does not exists and it is not MAIN target session do execution in MAIN.
	 * 
	 * @param <T> is type of return value of action
	 * @param action which should be executed
	 * @return result of action
	 */
	public <T> T execute(TargetSession target, ISessionAction<T> action);

	/**
	 * DbClass in DB at time of last cache refresh.
	 * 
	 * @param name of class
	 * @return class with requested name or null in case no such exists (at the time of refresh)
	 */
	public DbClass getCachedClass(String name);

	/**
	 * Answer list of classes in DB at time of last cache refresh.
	 * 
	 * @return list of classes
	 */
	public List<DbClass> getCachedClasses();

	/**
	 * Answer unsorted list of methods for this behavior.
	 * 
	 * @param behavior
	 * @return list of methods or empty list of behavior does not exist
	 */
	public List<MethodReference> getCachedMethods(DbBehavior behavior);

	/**
	 * List of method references implementing some selector at the time of last cache refresh.
	 * 
	 * @param selector of method
	 * @return list of method references
	 */
	public List<MethodReference> getCachedMethods(Selector selector);

	/**
	 * Answer list of all symbols in symbol lists at time of last cache refresh.
	 * 
	 * @return list of classes
	 */
	public Set<String> getCachedObjectNames();

	/**
	 * List of selectors in DB at the time of last cache refresh.
	 * 
	 * @return list of selectors.
	 */
	public List<Selector> getCachedSelectors();

	/**
	 * @return identifier of session for session server identification
	 */
	public String getId();

	/**
	 * For local file representing some method in this session return method object.
	 * 
	 * @param file
	 * @return
	 */
	public DbMethod getMethod(IFile file);

	/**
	 * @param ref is reference to method
	 * @return local file in workspace
	 */
	public IFile getMethodFile(MethodReference ref) throws CoreException;

	/**
	 * @return project in workspace for which was this session opened
	 */
	public IProject getProject();

	/**
	 * class names, class hierarchy, selectors...
	 */
	public void refreshCachedValues(IProgressMonitor monitor);

	public void removeListener(ISessionListener listener);

	/**
	 * Initialisation of session after its creation, should not be called by client, it is called by manager itself
	 */
	public void setUp(IProgressMonitor monitor);

	/**
	 * Deinitialisation of session before closing.
	 */
	public void tearDown(IProgressMonitor monitor);
}
