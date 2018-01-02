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

import java.io.IOException;
import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.templates.ContributionContextTypeRegistry;
import org.eclipse.ui.editors.text.templates.ContributionTemplateStore;
import org.osgi.framework.BundleContext;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.karpisek.gemdev.core.db.SessionFactory;
import net.karpisek.gemdev.core.db.SessionManager;
import net.karpisek.gemdev.ui.editor.contentassist.GsTemplateContextType;

/**
 * Main UI plugin for GemDev project.
 */
public class GemDevUiPlugin extends AbstractGsUiPlugin {
	public static final String PLUGIN_ID = "net.karpisek.ui.gemdev"; //$NON-NLS-1$

	public static final String CLASS_ICON = "class.gif"; //$NON-NLS-1$
	public static final String CLASS_DISABLED_ICON = "classDisabled.gif"; //$NON-NLS-1$
	public static final String CLASS_CATEGORY_ICON = "classCategory.gif"; //$NON-NLS-1$
	public static final String CLASS_METHOD_ICON = "classMethod.gif"; //$NON-NLS-1$
	public static final String INSTANCE_ICON = "instance.gif"; //$NON-NLS-1$
	public static final String INSTANCE_CATEGORY_ICON = "instanceCategory.gif"; //$NON-NLS-1$
	public static final String INSTANCE_METHOD_ICON = "instanceMethod.gif"; //$NON-NLS-1$
	public static final String FIND_CLASS_ICON = "opentype.gif"; //$NON-NLS-1$
	public static final String FIND_IMPLEMENTORS_ICON = "findImplementors.gif"; //$NON-NLS-1$
	public static final String FIND_SENDERS_ICON = "findSenders.gif"; //$NON-NLS-1$
	public static final String SELECTOR_ICON = "selector.gif"; //$NON-NLS-1$
	public static final String ABORT_TRANSACTION_ICON = "abortTransaction.gif"; //$NON-NLS-1$
	public static final String COMMIT_TRANSACTION_ICON = "commitTransaction.gif"; //$NON-NLS-1$
	public static final String REFRESH_ICON = "refresh.gif"; //$NON-NLS-1$
	public static final String HISTORY_ICON = "history.gif"; //$NON-NLS-1$

	public static final String UNDEFINED_OBJECT_INFO = "undefinedObject.gif"; //$NON-NLS-1$
	public static final String OBJECT_ICON = "object.gif"; //$NON-NLS-1$
	public static final String NUMBER_ICON = "number.gif"; //$NON-NLS-1$
	public static final String STRING_ICON = "string.gif"; //$NON-NLS-1$
	public static final String ABSTRACT_DICTIONARY_ICON = "abstractDictionary.gif"; //$NON-NLS-1$
	public static final String SEQUENCEABLE_COLLECTION_ICON = "sequenceableCollection.gif"; //$NON-NLS-1$
	public static final String UNORDERED_COLLECTION_ICON = "unorderedCollection.gif"; //$NON-NLS-1$

	public static final String FIND_ICON = "find.gif"; //$NON-NLS-1$
	public static final String NEW_ICON = "new.gif"; //$NON-NLS-1$
	public static final String DELETE_ICON = "delete.gif"; //$NON-NLS-1$

	public static final String TEMPLATE_ICON = "template.gif"; //$NON-NLS-1$

	public static final String CUSTOM_TEMPLATES_KEY = "net.karpisek.gemdev.ui.customtemplates";

	private static GemDevUiPlugin plugin;

	public static GemDevUiPlugin getDefault() {
		return plugin;
	}

	public static SessionManager getSessionManager() {
		return getDefault().sessionManager;
	}

	private SessionManager sessionManager;
	private final Map<RGB, Color> colors;

	/** The template store. */
	private TemplateStore fStore;

	/** The context type registry. */
	private ContributionContextTypeRegistry fRegistry;

	public GemDevUiPlugin() {
		super(PLUGIN_ID);
		colors = Maps.newHashMap();
	}

	public Color getColor(final RGB rgb) {
		Color color = colors.get(rgb);
		if (color == null) {
			color = new Color(PlatformUI.getWorkbench().getDisplay(), rgb);
			colors.put(rgb, color);
		}
		return color;
	}

	/**
	 * Returns this plug-in's context type registry.
	 *
	 * @return the context type registry for this plug-in instance
	 */
	public ContextTypeRegistry getContextTypeRegistry() {
		if (fRegistry == null) {
			// create an configure the contexts available in the template editor
			fRegistry = new ContributionContextTypeRegistry();
			fRegistry.addContextType(GsTemplateContextType.CONTEXT_TYPE);
		}
		return fRegistry;
	}

	/**
	 * Returns this plug-in's template store.
	 *
	 * @return the template store of this plug-in instance
	 */
	public TemplateStore getTemplateStore() {
		if (fStore == null) {
			fStore = new ContributionTemplateStore(getContextTypeRegistry(), GemDevUiPlugin.getDefault().getPreferenceStore(), CUSTOM_TEMPLATES_KEY);
			try {
				fStore.load();
			} catch (final IOException e) {
				logError(e);
			}
		}
		return fStore;
	}

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		sessionManager = new SessionManager(new SessionFactory());
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		for (final Map.Entry<RGB, Color> entry : colors.entrySet()) {
			entry.getValue().dispose();
		}
		colors.clear();

		sessionManager.closeAllSessions(new NullProgressMonitor());
		sessionManager = null;
		plugin = null;
		super.stop(context);
	}

	@Override
	protected void initializeImageRegistry(final ImageRegistry registry) {
		initializeImageRegistry(registry, Lists.newArrayList(CLASS_ICON, CLASS_DISABLED_ICON, CLASS_CATEGORY_ICON, CLASS_METHOD_ICON, INSTANCE_ICON,
				INSTANCE_CATEGORY_ICON, INSTANCE_METHOD_ICON, FIND_CLASS_ICON, FIND_IMPLEMENTORS_ICON, FIND_SENDERS_ICON, SELECTOR_ICON,

				ABORT_TRANSACTION_ICON, COMMIT_TRANSACTION_ICON, REFRESH_ICON, HISTORY_ICON,

				UNDEFINED_OBJECT_INFO, OBJECT_ICON, NUMBER_ICON, STRING_ICON, ABSTRACT_DICTIONARY_ICON, SEQUENCEABLE_COLLECTION_ICON, UNORDERED_COLLECTION_ICON,

				FIND_ICON, NEW_ICON, DELETE_ICON,

				TEMPLATE_ICON));
	}
}
