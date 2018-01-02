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
package net.karpisek.gemdev.net.actions;

import com.google.common.base.Objects;

/**
 * Data class for representing method in db. Used by some implementors,senders actions.
 */
public class MethodReference {
	private final String className;
	private final boolean instanceSide;
	private final String methodName;

	public MethodReference(final String className, final boolean instanceSide, final String methodName) {
		this.className = className;
		this.instanceSide = instanceSide;
		this.methodName = methodName;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof MethodReference) {
			final MethodReference other = (MethodReference) obj;
			return Objects.equal(className, other.className) && Objects.equal(instanceSide, other.instanceSide) && Objects.equal(methodName, other.methodName);
		}
		return false;
	}

	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(className, instanceSide, methodName);
	}

	public boolean isInstanceSide() {
		return instanceSide;
	}

	@Override
	public String toString() {
		return String.format("%s%s#%s", getClassName(), isInstanceSide() ? "" : " class", getMethodName()); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	}
}