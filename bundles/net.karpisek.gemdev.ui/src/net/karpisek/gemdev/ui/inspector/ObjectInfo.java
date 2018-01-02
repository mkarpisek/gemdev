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
package net.karpisek.gemdev.ui.inspector;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.progress.IElementCollector;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.net.ISessionAction;
import net.karpisek.gemdev.net.actions.PrintIt;
import net.karpisek.gemdev.net.actions.clazz.GetAllInstVarNames;
import net.karpisek.gemdev.ui.GemDevUiPlugin;

/**
 * Basic information about object in db used as its representation in java side.
 */
public class ObjectInfo {
	private final String oop;
	private final String className;
	private final String printString;
	private final int namedVarSize;
	private final int unnamedVarSize;

	private final ISession session;

	public ObjectInfo(final ISession session, final String oop, final String className, final String printString, final int namedVarSize,
			final int unnamedVarSize) {
		this.session = session;
		this.oop = oop;
		this.className = className;
		this.printString = printString;
		this.namedVarSize = namedVarSize;
		this.unnamedVarSize = unnamedVarSize;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof ObjectInfo) {
			final ObjectInfo other = (ObjectInfo) obj;
			return Objects.equal(oop, other.oop) && Objects.equal(session, other.session);
		}
		return false;
	}

	public void fetchDeferredChildren(final IElementCollector collector, final IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			return;
		}

		fetchNamedVars(collector, monitor);
		fetchUnnamedVars(collector, monitor);
	}

	public void fetchNamedVars(final IElementCollector collector, final IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			return;
		}

		final List<String> names = Lists.newArrayList(execute(new GetAllInstVarNames(getClassName())).iterator());
		Collections.sort(names);

		for (final String varName : names) {
			if (monitor.isCanceled()) {
				return;
			}

			final ObjectInfo o = get(varName);
			collector.add(new InspectorNode(varName + ": " + o.getPrintString(), o), monitor); //$NON-NLS-1$
		}
	}

	public void fetchUnnamedVars(final IElementCollector collector, final IProgressMonitor monitor) {
		// nothing by default
	}

	public ObjectInfo get(final String varName) {
		final String expr = String.format("((Object _objectForOop: %s) instVarNamed: #%s) asOop", getOop(), varName); //$NON-NLS-1$
		final String instVarOop = execute(new PrintIt(expr) {
			@Override
			public boolean isIdempotent() {
				return true;
			}
		});
		return execute(new GetObjectInfo(getSession(), instVarOop));
	}

	public String getClassName() {
		return className;
	}

	public Image getIcon() {
		return GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.OBJECT_ICON);
	}

	public int getNamedVarSize() {
		return namedVarSize;
	}

	public String getOop() {
		return oop;
	}

	public String getPrintString() {
		return printString;
	}

	public ISession getSession() {
		return session;
	}

	public int getUnnamedVarSize() {
		return unnamedVarSize;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(oop, session);
	}

	@Override
	public String toString() {
		return getPrintString();
	}

	/**
	 * Executes action in session of receiver
	 */
	protected <T> T execute(final ISessionAction<T> action) {
		return session.execute(action);
	}
}
