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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

/**
 * 

 * 
 */
public class SessionFactory implements ISessionFactory {
	@Override
	public ISession newSession(final IProject project) throws CoreException {
		return new Session(project);
	}
}