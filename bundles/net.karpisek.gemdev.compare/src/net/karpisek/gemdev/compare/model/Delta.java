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
package net.karpisek.gemdev.compare.model;

import net.karpisek.gemdev.compare.DeltaBuilder;

/**
 * Difference between two objects of smalltalk model.
 * 
 * @param <T>
 * @see DeltaBuilder
 */
public abstract class Delta<T> {
	public static final String ADDED_MESSAGE = "[+] %s"; //$NON-NLS-1$
	public static final String REMOVED_MESSAGE = "[-] %s"; //$NON-NLS-1$
	public static final String CHANGED_MESSAGE = "[!] %s"; //$NON-NLS-1$

	private final T source;
	private final T target;

	public Delta(final T source, final T target) {
		this.source = source;
		this.target = target;
	}

	public T getObject() {
		if (source == null) {
			return target;
		}
		return source;
	}

	public T getSource() {
		return source;
	}

	public T getTarget() {
		return target;
	}
}
