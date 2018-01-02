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

import net.karpisek.gemdev.core.model.IMethod;

/**
 * 

 *
 */
public class MethodDelta extends Delta<IMethod> {

	public MethodDelta(final IMethod source, final IMethod target) {
		super(source, target);
	}

	public String getSourceCode() {
		String code = ""; //$NON-NLS-1$
		if (getSource() != null) {
			code = getSource().getSourceCode();
		}
		return code;
	}

	public String getSourceTitle() {
		String code = ""; //$NON-NLS-1$
		if (getSource() != null) {
			code = getSource().getFullDescription();
		}
		return code;
	}

	public String getTargetCode() {
		String code = ""; //$NON-NLS-1$
		if (getTarget() != null) {
			code = getTarget().getSourceCode();
		}
		return code;
	}

	public String getTargetTitle() {
		String code = ""; //$NON-NLS-1$
		if (getTarget() != null) {
			code = getTarget().getFullDescription();
		}
		return code;
	}

	public boolean isInstanceSide() {
		return getObject().isInstanceSide();
	}

	@Override
	public String toString() {
		return getObject().getName();
	}
}
