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

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelExtension;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;

import com.google.common.collect.Maps;

import net.karpisek.gemdev.core.analysis.IMethodContext;
import net.karpisek.gemdev.core.analysis.NullMethodContext;
import net.karpisek.gemdev.lang.model.Element;
import net.karpisek.gemdev.lang.model.MethodModel;
import net.karpisek.gemdev.lang.parser.GsTree;
import net.karpisek.gemdev.ui.preferences.GsPreferences;

/**
 * Source viewer specialised for smalltalk source code editing.
 */
public class GsSourceViewer extends SourceViewer implements IPropertyChangeListener, IGsSourceViewerListener {
	public static final String OCCURENCE_ANNOTATION_ID = "net.karpisek.gemdev.ui.occurencesAnnotationType"; //$NON-NLS-1$

	private Map<Annotation, Position> annotations;
	private ListenerList listeners;

	private IMethodContext context;
	private MethodModel model;
	private Element selectedElement;

	protected BracketsPainter bracketsPainter;

	/**
	 * Constructor for use in editor parts.
	 */
	public GsSourceViewer(final Composite parent, final IVerticalRuler verticalRuler, final IOverviewRuler overviewRuler, final boolean showAnnotationsOverview,
			final int styles) {
		super(parent, verticalRuler, overviewRuler, showAnnotationsOverview, styles);
		context = new NullMethodContext();
		bracketsPainter = new BracketsPainter(this);
	}

	/**
	 * Constructor for stand alone creating of viewer. Performs configuration and setting of contents.
	 */
	public GsSourceViewer(final Composite parent, final String initialText) {
		super(parent, null, SWT.V_SCROLL | SWT.H_SCROLL);
		configure(new Configuration(GsPreferences.loadStyles()));
		setDocument(new Document(initialText));
		context = new NullMethodContext();
		bracketsPainter = new BracketsPainter(this);
	}

	public void addModelListener(final IGsSourceViewerListener listener) {
		listeners.add(listener);
	}

	public IMethodContext getContext() {
		return context;
	}

	public MethodModel getModel() {
		return model;
	}

	@Override
	public void modelChanged(final MethodModel oldModel, final MethodModel newModel) {
		removeOccurenceAnnotations(getAnnotationModel());
	}

	@Override
	public void propertyChange(final PropertyChangeEvent event) {
		if (event.getProperty().equals(GsPreferences.SYNTAX_COLORING)) {
			reconfigure(new Configuration(GsPreferences.loadStyles()));
		}
	}

	/**
	 * Change setup of presentation for this viewer according to new configuration.
	 * 
	 * @param configuration which should be used
	 */
	public void reconfigure(final Configuration configuration) {
		unconfigure(); // remove old configuration
		configure(configuration); // configure it again
		invalidateTextPresentation();
		refresh();
	}

	public void removeListener(final IGsSourceViewerListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void selectedElementChanged(final MethodModel currentModel, final Element selectedElement) {
		final IAnnotationModel annotationModel = getAnnotationModel();
		if (annotationModel == null) {
			return;
		}

		removeOccurenceAnnotations(annotationModel);
		if (currentModel != null && selectedElement != null) {
			addOccurenceAnnotations(annotationModel, selectedElement.getOccurences());
		}
	}

	public void setContext(final IMethodContext context) {
		this.context = context;
	}

	public void setModel(final MethodModel newModel) {
		final MethodModel oldModel = model;
		model = newModel;

		for (final Object o : listeners.getListeners()) {
			((IGsSourceViewerListener) o).modelChanged(oldModel, newModel);
		}
	}

	private void addOccurenceAnnotations(final IAnnotationModel am, final List<GsTree> occurences) {
		if (am == null) {
			return;
		}

		for (final GsTree t : occurences) {
			final Annotation a = new Annotation(OCCURENCE_ANNOTATION_ID, false, null);
			annotations.put(a, new Position(t.getOffset(), t.getLength()));
		}
		((IAnnotationModelExtension) am).replaceAnnotations(null, annotations);
	}

	private void handleCursorMoved() {
		final int offset = ((ITextSelection) getSelection()).getOffset();
		bracketsPainter.refresh(offset);
	}

	private void removeOccurenceAnnotations(final IAnnotationModel am) {
		if (am == null) {
			return;
		}

		((IAnnotationModelExtension) am).replaceAnnotations(annotations.keySet().toArray(new Annotation[annotations.size()]), null);
		annotations.clear();
	}

	@Override
	protected void createControl(final Composite parent, final int styles) {
		super.createControl(parent, styles);

		listeners = new ListenerList();
		annotations = Maps.newHashMap();

		GsPreferences.getPluginPreferences().addPropertyChangeListener(this);
		getTextWidget().addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(final DisposeEvent e) {
				GsPreferences.getPluginPreferences().removePropertyChangeListener(GsSourceViewer.this);
			}
		});
		getTextWidget().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(final MouseEvent e) {
				handleCursorMoved();
			}
		});
		getTextWidget().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				handleCursorMoved();
			}
		});
		addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
				Element newSelectedElement = null;
				final MethodModel currentModel = model;
				if (currentModel != null) {
					final int offset = ((ITextSelection) event.getSelection()).getOffset();
					newSelectedElement = currentModel.getElement(offset);
				}

				if (newSelectedElement != selectedElement) {
					selectedElement = newSelectedElement;
					for (final Object o : listeners.getListeners()) {
						((IGsSourceViewerListener) o).selectedElementChanged(currentModel, newSelectedElement);
					}
				}
			}
		});
		addModelListener(this);
		getTextWidget().setFont(JFaceResources.getTextFont());
	}
}
