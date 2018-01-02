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
package net.karpisek.gemdev.core.analysis;

import java.util.Set;

import com.google.common.collect.Sets;

import net.karpisek.gemdev.core.db.DbBehavior;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.db.Selector;
import net.karpisek.gemdev.core.model.IBehavior;
import net.karpisek.gemdev.net.actions.clazz.GetAllInstVarNames;

/**
 * Default implementation of interface {@link IMethodContext} for some DbBehavior.
 */
public class MethodContext implements IMethodContext {
	private final DbBehavior targetBehavior;
	private final Set<String> implementedMessages;
	private final Set<String> knownIdentifiers;

	/**
	 * Construct context based on one side of class.
	 * 
	 * @param targetBehavior for which should be context created
	 */
	public MethodContext(final DbBehavior targetBehavior) {
		this.targetBehavior = targetBehavior;
		this.implementedMessages = Sets.newHashSet();
		this.knownIdentifiers = Sets.newHashSet();

		final ISession session = targetBehavior.getSession();
		for (final Selector s : session.getCachedSelectors()) {
			implementedMessages.add(s.getName());
		}
		knownIdentifiers.addAll(session.getCachedObjectNames());
		knownIdentifiers.addAll(session.execute(new GetAllInstVarNames(targetBehavior.getClassName() + (targetBehavior.isInstanceSide() ? "" : " class")))); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Override
	public ISession getSession() {
		return targetBehavior.getSession();
	}

	public DbBehavior getTargetClass() {
		return targetBehavior;
	}

	@Override
	public boolean isSystemDeclaring(final String identifierName) {
		return knownIdentifiers.contains(identifierName);
	}

	@Override
	public boolean isSystemImplementing(final String name) {
		return implementedMessages.contains(name);
	}

	@Override
	public IBehavior resolve(final String literal) {
		if (literal == null) {
			return null;
		}
		final ISession session = targetBehavior.getSession();

		if ("true".equals(literal) || "false".equals(literal))
			return session.getCachedClass("Boolean");
		if ("nil".equals(literal))
			return session.getCachedClass("UndefinedObject");
		if ("self".equals(literal))
			return targetBehavior;

		if ("super".equals(literal)) {
			return targetBehavior.getSuperclass();
		}

		final DbClass clazz = session.getCachedClass(literal);
		if (clazz == null)
			return null;

		return clazz.getClassSide();
	}
}
