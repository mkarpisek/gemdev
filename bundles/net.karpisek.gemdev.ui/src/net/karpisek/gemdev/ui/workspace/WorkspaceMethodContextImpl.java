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
package net.karpisek.gemdev.ui.workspace;

import java.util.Set;

import com.google.common.collect.Sets;

import net.karpisek.gemdev.core.analysis.IMethodContext;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.db.Selector;
import net.karpisek.gemdev.core.model.IBehavior;

public class WorkspaceMethodContextImpl implements IMethodContext {
	private final ISession session;
	private final Set<String> implementedMessages;
	private final Set<String> knownIdentifiers;

	public WorkspaceMethodContextImpl(final ISession session) {
		this.session = session;
		this.implementedMessages = Sets.newHashSet();
		this.knownIdentifiers = Sets.newHashSet();

		if (session != null) {
			for (final Selector s : session.getCachedSelectors()) {
				implementedMessages.add(s.getName());
			}
			knownIdentifiers.addAll(session.getCachedObjectNames());
		}
	}

	@Override
	public ISession getSession() {
		return session;
	}

	@Override
	public boolean isSystemDeclaring(final String identifierName) {
		return knownIdentifiers.contains(identifierName);
	}

	@Override
	public boolean isSystemImplementing(final String methodName) {
		return implementedMessages.contains(methodName);
	}

	@Override
	public IBehavior resolve(final String literal) {
		if (session == null || literal == null || literal.length() == 0) {
			return null;
		}

		if ("true".equals(literal) || "false".equals(literal))
			return session.getCachedClass("Boolean");
		if ("nil".equals(literal))
			return session.getCachedClass("UndefinedObject");

		final DbClass c = session.getCachedClass(literal);
		if (c == null)
			return null;

		return c.getClassSide();
	}

}
