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
package net.karpisek.gemdev.ui.preferences;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.google.common.collect.Maps;

import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.editor.Configuration;
import net.karpisek.gemdev.ui.editor.GsSourceViewer;
import net.karpisek.gemdev.ui.preferences.SyntaxColoringStyle.FontModifier;
import net.karpisek.gemdev.ui.preferences.SyntaxColoringStyle.Name;

/**
 * Page allows to define different font and color styles of syntax.
 */
public class SyntaxColoringPage extends PreferencePage implements IWorkbenchPreferencePage {
	private static class ColorSelectorWithLabel extends Composite {
		private final CLabel labelControl;
		private final ColorSelector selector;

		public ColorSelectorWithLabel(final Composite parent, final String label) {
			super(parent, SWT.NONE);
			this.setLayout(new FillLayout());
			labelControl = new CLabel(this, SWT.NONE);
			labelControl.setText(label);
			selector = new ColorSelector(this);
		}

		public void addListener(final IPropertyChangeListener listener) {
			selector.addListener(listener);
		}

		public void setColorValue(final RGB color) {
			selector.setColorValue(color);
			selector.getButton().setBackground(GemDevUiPlugin.getDefault().getColor(color));
		}

		@Override
		public void setEnabled(final boolean enabled) {
			labelControl.setEnabled(enabled);
			selector.setEnabled(enabled);
		}
	}
	private SyntaxColoringStyleSet styles;
	private ListViewer stylesList;
	private GsSourceViewer previewViewer;
	private Map<FontModifier, Button> fontModifierCheckboxes;
	private ColorSelectorWithLabel colorSelector;

	private Button enableChackbox;

	public SyntaxColoringPage() {
	}

	public SyntaxColoringPage(final String title) {
		super(title);
	}

	public SyntaxColoringPage(final String title, final ImageDescriptor image) {
		super(title, image);
	}

	@Override
	public void init(final IWorkbench workbench) {
	}

	@Override
	public boolean performOk() {
		GsPreferences.saveStyles(styles);
		return true;
	}

	private void createCheckbox(final Composite parent, final FontModifier modifier) {
		final Button button = createCheckbox(parent, modifier.toString(), new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				getSelectedStyle().setFontModifier(modifier, ((Button) e.getSource()).getSelection());
				refreshPreview();
			}
		});
		fontModifierCheckboxes.put(modifier, button);
	}

	private Button createCheckbox(final Composite parent, final String label, final SelectionListener listener) {
		final Button button = new Button(parent, SWT.CHECK);
		button.setText(label);
		button.addSelectionListener(listener);
		return button;
	}

	private void createPreviewControls(final Composite parent) {
		final String previewText = GemDevUiPlugin.getDefault().getFileContents("/resources/syntaxColoringPreview.txt"); //$NON-NLS-1$

		new Label(parent, SWT.NONE).setText(Messages.PREFERENCES_PREVIEW + ":"); //$NON-NLS-1$
		previewViewer = new GsSourceViewer(parent, previewText);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).span(2, 1).applyTo(previewViewer.getControl());
	}

	private void createStyleDetailsControls(final Composite parent) {
		enableChackbox = createCheckbox(parent, Messages.PREFERENCES_ENABLE, new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final boolean enabled = ((Button) e.getSource()).getSelection();
				getSelectedStyle().setEnabled(enabled);
				colorSelector.setEnabled(enabled);
				for (final Button cb : fontModifierCheckboxes.values()) {
					cb.setEnabled(enabled);
				}
				refreshPreview();
			}
		});

		new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);

		colorSelector = new ColorSelectorWithLabel(parent, Messages.PREFERENCES_COLOR + ":"); //$NON-NLS-1$
		colorSelector.addListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent event) {
				getSelectedStyle().setColor((RGB) event.getNewValue());
				refreshPreview();
			}
		});

		for (final FontModifier modifier : FontModifier.values()) {
			createCheckbox(parent, modifier);
		}
	}

	private void refreshPreview() {
		previewViewer.reconfigure(new Configuration(styles));
	}

	private void setInput(final SyntaxColoringStyleSet styles) {
		final SyntaxColoringStyle oldSelectedStyle = getSelectedStyle();
		final SyntaxColoringStyle newSelectedStyle = styles.getStyle(oldSelectedStyle == null ? Name.DEFAULT : oldSelectedStyle.getName());

		this.styles = styles;
		stylesList.setInput(styles.asList());
		stylesList.setSelection(new StructuredSelection(newSelectedStyle));
		refreshPreview();
	}

	private void styleSelected(final SyntaxColoringStyle selectedStyle) {
		enableChackbox.setSelection(selectedStyle.isEnabled());
		colorSelector.setEnabled(selectedStyle.isEnabled());
		colorSelector.setColorValue(selectedStyle.getColor());
		for (final Entry<FontModifier, Button> entry : fontModifierCheckboxes.entrySet()) {
			entry.getValue().setEnabled(selectedStyle.isEnabled());
			entry.getValue().setSelection(selectedStyle.getFontModifier(entry.getKey()));
		}
	}

	@Override
	protected Control createContents(final Composite parent) {
		fontModifierCheckboxes = Maps.newHashMap();

		final GridLayoutFactory f = GridLayoutFactory.swtDefaults();
		final Composite container = new Composite(parent, SWT.NONE);

		new Label(container, SWT.NONE).setText(Messages.PREFERENCES_ELEMENT + ":"); //$NON-NLS-1$
		final Composite c1 = new Composite(container, SWT.NONE);
		GridDataFactory.fillDefaults().hint(SWT.DEFAULT, 180).grab(true, false).align(SWT.FILL, SWT.FILL).applyTo(c1);

		stylesList = new ListViewer(c1, SWT.BORDER);
		stylesList.setContentProvider(new ArrayContentProvider());
		stylesList.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof SyntaxColoringStyle) {
					return ((SyntaxColoringStyle) element).getName().toString();
				}
				return super.getText(element);
			}
		});
		stylesList.setSorter(new ViewerSorter());
		stylesList.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
				final IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				styleSelected((SyntaxColoringStyle) selection.getFirstElement());
			}
		});
		GridDataFactory.fillDefaults().hint(150, SWT.DEFAULT).grab(false, true).align(SWT.FILL, SWT.FILL).applyTo(stylesList.getControl());

		final Composite c12 = new Composite(c1, SWT.NONE);
		GridDataFactory.fillDefaults().grab(false, true).align(SWT.FILL, SWT.FILL).applyTo(c12);
		createStyleDetailsControls(c12);

		f.generateLayout(c12);
		f.numColumns(2);
		f.generateLayout(c1);

		createPreviewControls(container);

		f.numColumns(1);
		f.generateLayout(container);

		setInput(GsPreferences.loadStyles());

		return container;
	}

	protected SyntaxColoringStyle getSelectedStyle() {
		final IStructuredSelection selection = (IStructuredSelection) stylesList.getSelection();
		return (SyntaxColoringStyle) selection.getFirstElement();
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();

		setInput(SyntaxColoringStyleSet.createDefault());
	}
}
