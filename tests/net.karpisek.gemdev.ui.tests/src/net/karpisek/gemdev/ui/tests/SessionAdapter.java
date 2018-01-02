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
package net.karpisek.gemdev.ui.tests;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.junit.After;
import org.junit.Before;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.karpisek.gemdev.core.db.CommitFailedException;
import net.karpisek.gemdev.core.db.DbBehavior;
import net.karpisek.gemdev.core.db.DbCategory;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.db.DbMethod;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.db.ISessionListener;
import net.karpisek.gemdev.core.db.Selector;
import net.karpisek.gemdev.net.ActionException;
import net.karpisek.gemdev.net.ISessionAction;
import net.karpisek.gemdev.net.actions.CompilationError;
import net.karpisek.gemdev.net.actions.MethodReference;

public class SessionAdapter implements ISession {

	@Override
	public void abortTransaction() {
	}

	@Override
	public void addListener(final ISessionListener listener) {

	}

	@Override
	public void commitTransaction() throws CommitFailedException {
	}

	@Override
	public DbCategory createCategory(final DbBehavior receiver, final String categoryName) throws CommitFailedException {
		return null;
	}

	@Override
	public DbClass createClass(final String definition) throws ActionException {
		return null;
	}

	@Override
	public DbMethod createMethod(final DbCategory category, final String sourceCode) throws CompilationError, CommitFailedException {
		return null;
	}

	@Override
	public void deleteCategory(final DbCategory c) throws CommitFailedException {
	}

	@Override
	public void deleteClass(final DbClass c) throws ActionException {
	}

	@Override
	public void deleteMethod(final DbMethod m) throws CommitFailedException {
	}

	@Override
	public <T> T execute(final ISessionAction<T> action) {
		return null;
	}

	@Override
	public <T> T execute(final TargetSession target, final ISessionAction<T> action) {
		return execute(action);
	}

	@Override
	public DbClass getCachedClass(final String name) {
		return null;
	}

	@Override
	public List<DbClass> getCachedClasses() {
		return Lists.newLinkedList();
	}

	@Override
	public List<MethodReference> getCachedMethods(final DbBehavior behavior) {
		return null;
	}

	@Override
	public List<MethodReference> getCachedMethods(final Selector selector) {
		return Lists.newLinkedList();
	}

	@Override
	public Set<String> getCachedObjectNames() {

		return null;
	}

	@Override
	public List<Selector> getCachedSelectors() {
		return Lists.newLinkedList();
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public DbMethod getMethod(final IFile file) {
		return null;
	}

	@Override
	public IFile getMethodFile(final MethodReference ref) throws CoreException {
		return null;
	}

	public Map<Selector, List<MethodReference>> getMethods() {
		return Maps.newHashMap();
	}

	@Override
	public IProject getProject() {
		return null;
	}

	@Override
	public void refreshCachedValues(final IProgressMonitor monitor) {

	}

	@Override
	public void removeListener(final ISessionListener listener) {

	}

	@Override
	@Before
	public void setUp(final IProgressMonitor monitor) {

	}

	@Override
	@After
	public void tearDown(final IProgressMonitor monitor) {

	}

}
