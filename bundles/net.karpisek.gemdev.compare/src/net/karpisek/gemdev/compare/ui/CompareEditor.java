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
package net.karpisek.gemdev.compare.ui;

import java.util.List;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import net.karpisek.gemdev.compare.Messages;
import net.karpisek.gemdev.compare.model.CategoryDelta;
import net.karpisek.gemdev.compare.model.ClassDelta;
import net.karpisek.gemdev.compare.model.MethodDelta;
import net.karpisek.gemdev.ui.GuiUtils;

/**
 * Displays read-only delta of comparing two classes.
 */
public class CompareEditor extends EditorPart {
	public static final String EDITOR_ID = "net.karpisek.gemdev.compare.CompareEditor"; //$NON-NLS-1$

	private CLabel classHierarchyLabel;
	private TableViewer classesViewer;
	private TableViewer categoriesViewer;
	private TableViewer methodsViewer;
	private TextMergeViewer sourceCodeViewer;

	@Override
	public void createPartControl(final Composite parent) {
		GuiUtils.activateContext(getSite());

		final SashForm mainSf = new SashForm(parent, SWT.VERTICAL | SWT.SMOOTH);

		final SashForm browsingSf = new SashForm(mainSf, SWT.HORIZONTAL | SWT.SMOOTH);
		createClassesViewForm(browsingSf);
		createCategoriesViewForm(browsingSf);
		createMethodsViewForm(browsingSf);
		browsingSf.setWeights(new int[] { 30, 35, 35 });

		createSourceCodeDiffViewer(mainSf);

		mainSf.setWeights(new int[] { 40, 60 });

		final CompareEditorInput input = (CompareEditorInput) getEditorInput();

		if (!input.getDeltas().isEmpty()) {
			classesViewer.setInput(input.getDeltas());
			classesViewer.setSelection(new StructuredSelection(input.getDeltas().get(0)));
		} else {
			classHierarchyLabel.setText(Messages.NO_DIFFERENCES_FOUND_MESSAGE);
		}

		setPartName(input.getName());

		GuiUtils.activateContext(getSite());
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}

	public CompareEditorInput getCompareEditorInput() {
		return (CompareEditorInput) getEditorInput();
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void setFocus() {
	}

	private ViewForm createCategoriesViewForm(final Composite parent) {
		final ViewForm vf = new ViewForm(parent, SWT.BORDER);
		categoriesViewer = new TableViewer(vf, SWT.BORDER);
		categoriesViewer.setLabelProvider(new DeltaLabelProvider());
		categoriesViewer.setContentProvider(new ArrayContentProvider());
		categoriesViewer.setInput(new Object[0]);
		categoriesViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
				selectedCategoryChanged(event);
			}
		});
		vf.setContent(categoriesViewer.getControl());
		return vf;
	}

	private ViewForm createClassesViewForm(final Composite parent) {
		final ViewForm vf = new ViewForm(parent, SWT.BORDER);

		classHierarchyLabel = new CLabel(vf, SWT.LEFT);
		vf.setTopLeft(classHierarchyLabel);

		classesViewer = new TableViewer(vf, SWT.BORDER);
		classesViewer.setLabelProvider(new DeltaLabelProvider());
		classesViewer.setContentProvider(new ArrayContentProvider());
		classesViewer.setInput(new Object[0]);
		classesViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
				selectedClassChanged(event);
			}
		});

		vf.setContent(classesViewer.getControl());
		return vf;
	}

	private ViewForm createMethodsViewForm(final Composite parent) {
		final ViewForm vf = new ViewForm(parent, SWT.BORDER);

		methodsViewer = new TableViewer(vf, SWT.BORDER);
		methodsViewer.setLabelProvider(new DeltaLabelProvider());
		methodsViewer.setContentProvider(new ArrayContentProvider());
		methodsViewer.setInput(new Object[0]);
		methodsViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
				selectedMethodChanged(event);
			}
		});
		vf.setContent(methodsViewer.getControl());
		return vf;
	}

	private void createSourceCodeDiffViewer(final SashForm mainSf) {
		sourceCodeViewer = new TextMergeViewer(mainSf, new CompareConfiguration());
		sourceCodeViewer.setContentProvider(new IMergeViewerContentProvider() {
			@Override
			public void dispose() {

			}

			@Override
			public Object getAncestorContent(final Object input) {
				return null;
			}

			@Override
			public Image getAncestorImage(final Object input) {
				return null;
			}

			@Override
			public String getAncestorLabel(final Object input) {
				return null;
			}

			@Override
			public Object getLeftContent(final Object input) {
				if (input == null) {
					return new Document();
				}
				return new Document(((MethodDelta) input).getSourceCode());
			}

			@Override
			public Image getLeftImage(final Object input) {
				return null;
			}

			@Override
			public String getLeftLabel(final Object input) {
				if (input == null) {
					return ""; //$NON-NLS-1$
				}
				return (((MethodDelta) input).getSourceTitle());
			}

			@Override
			public Object getRightContent(final Object input) {
				if (input == null) {
					return new Document();
				}
				return new Document(((MethodDelta) input).getTargetCode());
			}

			@Override
			public Image getRightImage(final Object input) {
				return null;
			}

			@Override
			public String getRightLabel(final Object input) {
				if (input == null) {
					return ""; //$NON-NLS-1$
				}
				return (((MethodDelta) input).getTargetTitle());
			}

			@Override
			public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
			}

			@Override
			public boolean isLeftEditable(final Object input) {
				return false;
			}

			@Override
			public boolean isRightEditable(final Object input) {
				return false;
			}

			@Override
			public void saveLeftContent(final Object input, final byte[] bytes) {

			}

			@Override
			public void saveRightContent(final Object input, final byte[] bytes) {

			}

			@Override
			public boolean showAncestor(final Object input) {
				return false;
			}

		});
	}

	private Object getFirstSelectedElement(final SelectionChangedEvent event) {
		if (event.getSelection() instanceof IStructuredSelection) {
			return ((IStructuredSelection) event.getSelection()).getFirstElement();
		}
		return null;
	}

	private void selectedCategoryChanged(final SelectionChangedEvent event) {
		final CategoryDelta selection = (CategoryDelta) getFirstSelectedElement(event);
		if (selection == null) {
			methodsViewer.setInput(new Object[0]);
			return;
		}

		final List<MethodDelta> deltas = selection.getMethodDeltas();
		methodsViewer.setInput(deltas);
		if (deltas.size() > 0) {
			methodsViewer.setSelection(new StructuredSelection(deltas.get(0)));
		}
	}

	private void selectedClassChanged(final SelectionChangedEvent event) {
		final ClassDelta selection = (ClassDelta) getFirstSelectedElement(event);
		if (selection == null) {
			categoriesViewer.setInput(new Object[0]);
			return;
		}

		final List<CategoryDelta> deltas = selection.getCategoryDeltas();
		categoriesViewer.setInput(deltas);
		if (deltas.size() > 0) {
			categoriesViewer.setSelection(new StructuredSelection(deltas.get(0)));
		}
	}

	private void selectedMethodChanged(final SelectionChangedEvent event) {
		final MethodDelta selection = (MethodDelta) getFirstSelectedElement(event);
		if (event.getSelection().isEmpty()) {
			sourceCodeViewer.setInput(null);
			return;
		}

		sourceCodeViewer.setInput(selection);
	}
}