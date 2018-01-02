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

import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.model.IBehavior;

/**
 * Describes environment where should be some method model analysed. It is able to answer what method model can see from surrounding environment. Basically it
 * is DbBehavior.
 */
public interface IMethodContext {
	/**
	 * @return session for which was this context created or null if there is no session
	 */
	public ISession getSession();

	/**
	 * Answer if in system exists any object with this name.
	 */
	public boolean isSystemDeclaring(String identifierName);

	/**
	 * Answer if in system exists any method implementing this selector.
	 */
	public boolean isSystemImplementing(final String methodName);

	/**
	 * Resolves literal in this context.
	 * 
	 * @param text of class, 'self', 'super', 'true', 'false', 'nil'
	 * @return respective behavior
	 */
	public IBehavior resolve(String receiverName);
}
