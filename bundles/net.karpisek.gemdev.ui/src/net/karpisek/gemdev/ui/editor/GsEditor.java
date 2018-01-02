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

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;

import com.google.common.collect.Sets;

import net.karpisek.gemdev.core.analysis.IMethodContext;
import net.karpisek.gemdev.core.analysis.UndeclaredIdentifiersAnalysis;
import net.karpisek.gemdev.core.analysis.UnimplementedMessagesAnalysis;
import net.karpisek.gemdev.core.analysis.UnusedLocalIdentifierssAnalysis;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.db.ISessionListener;
import net.karpisek.gemdev.core.db.ISessionManagerListener;
import net.karpisek.gemdev.lang.model.Element;
import net.karpisek.gemdev.lang.model.MethodModel;
import net.karpisek.gemdev.lang.parser.SyntaxError;
import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.GuiUtils;
import net.karpisek.gemdev.ui.ICommands;
import net.karpisek.gemdev.ui.editor.rewriters.ToggleCommentHandler;
import net.karpisek.gemdev.ui.preferences.GsPreferences;

/**
 * Base implementation of editor for editing GS source code.
 */
public class GsEditor extends AbstractDecoratedTextEditor
		implements ISessionManagerListener, ISessionListener, IPropertyChangeListener, IGsSourceViewerListener {
	@Override
	public void addedClasses(final List<DbClass> classes) {
		refreshContext();
	}

	@Override
	public void addedMethodReferences(final List<MethodReference> allAdded) {
		refreshContext();
	}

	@Override
	public void createPartControl(final Composite parent) {
		super.createPartControl(parent);

		GuiUtils.activateContext(getSite());
		GuiUtils.activateHandlers(getSite(), ICommands.TOGGLE_COMMENT, new ToggleCommentHandler());

		GemDevUiPlugin.getSessionManager().addListener(this);
		GsPreferences.getPluginPreferences().addPropertyChangeListener(this);
	}

	@Override
	public void dispose() {
		super.dispose();
		GsPreferences.getPluginPreferences().removePropertyChangeListener(this);
		GemDevUiPlugin.getSessionManager().removeListener(this);
	}

	public GsSourceViewer getGsSourceViewer() {
		return (GsSourceViewer) getSourceViewer();
	}

	@Override
	public void changedDefinitionOfClasses(final Map<DbClass, DbClass> classes) {
		refreshContext();
	}

	@Override
	public void modelChanged(final MethodModel oldModel, final MethodModel newModel) {
		final IFile file = ((FileEditorInput) getEditorInput()).getFile();
		final IMethodContext context = getGsSourceViewer().getContext();

		removeMarkers(file);

		final Preferences p = GsPreferences.getPluginPreferences();
		if (p.getBoolean(GsPreferences.ENABLE_SYNTAX_ERROR_ANALYSIS)) {
			addSyntaxErrorMarker(file, getSourceViewer().getDocument(), newModel);
		}
		if (p.getBoolean(GsPreferences.ENABLE_UNDECLARED_IDENTIFIERS_ANALYSIS)) {
			new UndeclaredIdentifiersAnalysis(context, newModel).createProblemMarkers(file);
		}
		if (p.getBoolean(GsPreferences.ENABLE_UNUSED_LOCAL_IDENTIFIERS_ANALYSIS)) {
			new UnusedLocalIdentifierssAnalysis(context, newModel).createProblemMarkers(file);
		}

		if (p.getBoolean(GsPreferences.ENABLE_UNIMPLEMENTED_MESSAGES_ANALYSIS)) {
			new UnimplementedMessagesAnalysis(context, newModel).createProblemMarkers(file);
		}
	}

	@Override
	public void propertyChange(final PropertyChangeEvent event) {
		final Set<String> keys = Sets.newHashSet(GsPreferences.ENABLE_SYNTAX_ERROR_ANALYSIS, GsPreferences.ENABLE_UNDECLARED_IDENTIFIERS_ANALYSIS,
				GsPreferences.ENABLE_UNUSED_LOCAL_IDENTIFIERS_ANALYSIS, GsPreferences.ENABLE_UNIMPLEMENTED_MESSAGES_ANALYSIS);
		final MethodModel model = getGsSourceViewer().getModel();

		if (model != null && keys.contains(event.getProperty())) {
			modelChanged(null, model);
		}
	}

	@Override
	public void removedClasses(final List<DbClass> classes) {
		refreshContext();
	}

	@Override
	public void removedMethodReferences(final List<MethodReference> allRemoved) {
		refreshContext();
	}

	@Override
	public void selectedElementChanged(final MethodModel currentModel, final Element selectedElement) {
	}

	@Override
	public void sessionClosed(final ISession session) {
	}

	@Override
	public void sessionOpened(final ISession session) {
	}

	protected void addSyntaxErrorMarker(final IResource res, final IDocument doc, final MethodModel model) {
		for (final SyntaxError error : model.getSyntaxErrors()) {
			try {
				final IMarker m = res.createMarker(IMarker.PROBLEM);

				m.setAttribute(IMarker.LINE_NUMBER, error.getLine() - 1);
				m.setAttribute(IMarker.MESSAGE, error.getMessage());
				m.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
				m.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);

				final int offset = doc.getLineOffset(error.getLine() - 1) + error.getColumn();
				m.setAttribute(IMarker.CHAR_START, offset);
				m.setAttribute(IMarker.CHAR_END, offset + error.getLength());
			} catch (final CoreException e) {
				GemDevUiPlugin.getDefault().logError(e);
			} catch (final BadLocationException e) {
				GemDevUiPlugin.getDefault().logError(e);
			}
		}
	}

	@Override
	protected ISourceViewer createSourceViewer(final Composite parent, final IVerticalRuler ruler, final int styles) {
		fAnnotationAccess = getAnnotationAccess();
		fOverviewRuler = createOverviewRuler(getSharedColors());

		final GsSourceViewer viewer = new GsSourceViewer(parent, ruler, getOverviewRuler(), isOverviewRulerVisible(), styles);
		viewer.addModelListener(this);
		// ensure decoration support has been created and configured.
		getSourceViewerDecorationSupport(viewer);

		return viewer;
	}

	@Override
	protected void initializeEditor() {
		super.initializeEditor();

		setSourceViewerConfiguration(new Configuration(GsPreferences.loadStyles()));
	}

	protected void refreshContext() {
		// do nothing by default
	}

	protected void removeMarkers(final IResource res) {
		try {
			res.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		} catch (final CoreException e) {
			GemDevUiPlugin.getDefault().logError(e);
		}
	}
}
