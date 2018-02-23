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

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.DocumentChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;

import net.karpisek.gemdev.lang.model.Element;
import net.karpisek.gemdev.lang.model.LocalIdentifier;
import net.karpisek.gemdev.lang.parser.GsTree;
import net.karpisek.gemdev.ui.Messages;

/**

 */
public class RenameProcessor extends RefactoringProcessor {
	private final RenameInfo info;

	public RenameProcessor(final RenameInfo info) {
		this.info = info;
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException {
		final CompositeChange result = new CompositeChange(getProcessorName());
		result.add(createRenameChange(pm, result));
		return result;
	}

	@Override
	public Object[] getElements() {
		return new Object[] { info.getSelectedElement() };
	}

	@Override
	public String getIdentifier() {
		return getClass().getName();
	}

	@Override
	public String getProcessorName() {
		return info.getDescription();
	}

	@Override
	public RefactoringStatus checkFinalConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws CoreException {
		final RefactoringStatus result = new RefactoringStatus();
		return result;
	}

	@Override
	public RefactoringStatus checkInitialConditions(final IProgressMonitor pm) throws CoreException {
		final RefactoringStatus result = new RefactoringStatus();
		if (info.getDocument() == null) {
			result.addFatalError(Messages.RENAME_REFACTORING_NO_INPUT_DOCUMENT_FOUND_ERROR);
		}
		if (info.getModel() == null) {
			result.addFatalError(Messages.RENAME_REFACTORING_NO_METHOD_MODEL_FOUND_ERROR);
		}
		final Element element = info.getSelectedElement();
		if (element == null || !(element instanceof LocalIdentifier)) {
			result.addFatalError(Messages.RENAME_REFACTORING_WRONG_ELEMENT_SELECTED_MESSAGE);
		}

		return result;
	}

	@Override
	public boolean isApplicable() throws CoreException {
		return true;
	}

	@Override
	public RefactoringParticipant[] loadParticipants(final RefactoringStatus status, final SharableParticipants sharedParticipants) throws CoreException {
		return new RefactoringParticipant[0];
	}

	TextChange createRenameChange(final IProgressMonitor pm, final CompositeChange rootChange) {
		final DocumentChange result = new DocumentChange(info.getModel().getSelector(), info.getDocument());

		final MultiTextEdit textEditRoot = new MultiTextEdit();

		final List<GsTree> occurences = info.getSelectedElement().getOccurences();

		for (final GsTree element : occurences) {
			final ReplaceEdit edit = new ReplaceEdit(element.getOffset(), element.getLength(), info.getNewName());
			textEditRoot.addChild(edit);
		}

		result.setEdit(textEditRoot);
		return result;
	}

}
