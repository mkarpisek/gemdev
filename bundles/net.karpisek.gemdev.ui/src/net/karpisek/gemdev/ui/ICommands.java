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
package net.karpisek.gemdev.ui;

/**
 * All command IDs.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICommands {
	public static final String CONNECT = "net.karpisek.gemdev.ui.connectCommand"; //$NON-NLS-1$
	public static final String DISCONNECT = "net.karpisek.gemdev.ui.disconnectCommand"; //$NON-NLS-1$

	// new/delete commands
	public static final String NEW_CLASS = "net.karpisek.gemdev.ui.newClassCommand"; //$NON-NLS-1$
	public static final String DELETE_CLASS = "net.karpisek.gemdev.ui.deleteClassCommand"; //$NON-NLS-1$
	public static final String NEW_CATEGORY = "net.karpisek.gemdev.ui.newCategoryCommand"; //$NON-NLS-1$
	public static final String DELETE_CATEGORY = "net.karpisek.gemdev.ui.deleteCategoryCommand"; //$NON-NLS-1$
	public static final String NEW_METHOD = "net.karpisek.gemdev.ui.newMethodCommand"; //$NON-NLS-1$
	public static final String DELETE_METHOD = "net.karpisek.gemdev.ui.deleteMethodCommand"; //$NON-NLS-1$

	// navigation commands
	public static final String FIND_DECLARATIONS = "net.karpisek.gemdev.ui.findDeclarationsCommand"; //$NON-NLS-1$
	public static final String FIND_REFERENCES = "net.karpisek.gemdev.ui.findReferencesCommand"; //$NON-NLS-1$
	public static final String FIND_METHOD = "net.karpisek.gemdev.ui.findMethodCommand"; //$NON-NLS-1$
	public static final String MESSAGES = "net.karpisek.gemdev.ui.messagesCommand"; //$NON-NLS-1$

	// inspect commands
	public static final String INSPECT_IT = "net.karpisek.gemdev.ui.inspectItCommand"; //$NON-NLS-1$
	public static final String PRINT_IT = "net.karpisek.gemdev.ui.printItCommand"; //$NON-NLS-1$
	public static final String PROPERTIES = "net.karpisek.gemdev.ui.propertiesCommand"; //$NON-NLS-1$

	// refactoring commands
	public static final String MOVE = "net.karpisek.gemdev.ui.moveCommand"; //$NON-NLS-1$
	public static final String RENAME = "net.karpisek.gemdev.ui.renameCommand"; //$NON-NLS-1$

	// edit commands
	public static final String TOGGLE_COMMENT = "net.karpisek.gemdev.ui.toggleCommentCommand"; //$NON-NLS-1$
}
