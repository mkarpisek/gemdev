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

import org.eclipse.core.runtime.IProgressMonitor;

public interface IObjectCacheFactory {
	/**
	 * Creates new object cache object initialised with current state of remote system.
	 * 
	 * @param session to remote system
	 * @return new cache
	 */
	IObjectCache build(ISession session, IProgressMonitor monitor);

	/**
	 * Creates new empty object cache.
	 * 
	 * @param session to remote system
	 * @return new cache
	 */
	IObjectCache createEmpty(ISession session);
}
