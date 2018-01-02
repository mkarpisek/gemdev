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
package net.karpisek.gemdev.ui.editor;

import net.karpisek.gemdev.lang.model.Element;
import net.karpisek.gemdev.lang.model.MethodModel;

/**

 */
public interface IGsSourceViewerListener {
	public void modelChanged(MethodModel oldModel, MethodModel newModel);

	public void selectedElementChanged(MethodModel currentModel, Element selectedElement);
}
