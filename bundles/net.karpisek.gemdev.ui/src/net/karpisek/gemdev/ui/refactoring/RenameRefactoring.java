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
package net.karpisek.gemdev.ui.refactoring;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Shell;

import net.karpisek.gemdev.lang.model.Element;
import net.karpisek.gemdev.lang.model.MethodModel;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.Messages;

/**

 */
public class RenameRefactoring extends ProcessorBasedRefactoring {
	/**
	 * Opens rename refactoring dialog. Also performs refactoring itself.
	 * 
	 * @param document in which should be refactoring done.
	 * @param model of passed document
	 * @param selectedElement to be renamed.
	 */
	public static void openDialog(final Shell shell, final IDocument document, final MethodModel model, final Element selectedElement) {
		final Document copy = new Document(document.get());

		final RenameInfo info = new RenameInfo(copy, model, selectedElement);
		final RenameRefactoring ref = new RenameRefactoring(new RenameProcessor(info));
		final RenameWizard wizard = new RenameWizard(ref, info);
		final RefactoringWizardOpenOperation op = new RefactoringWizardOpenOperation(wizard);
		try {
			if (op.run(shell, Messages.RENAME_DIALOG_TITLE) == IDialogConstants.OK_ID) {
				document.set(copy.get());
			}
		} catch (final InterruptedException e) {
			GemDevUiPlugin.getDefault().logError(e);
		}
	}

	private final RefactoringProcessor processor;

	public RenameRefactoring(final RefactoringProcessor processor) {
		super(processor);
		this.processor = processor;
	}

	@Override
	public RefactoringProcessor getProcessor() {
		return processor;
	}
}
