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
package net.karpisek.gemdev.ui.editor.contentassist;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.karpisek.gemdev.core.analysis.IMethodContext;
import net.karpisek.gemdev.core.db.DbBehavior;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.db.Selector;
import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.ui.GemDevUiPlugin;

/**
 * Creates runtime templates for smalltalk editor content assistant. Creates templates for
 * <li>classes if prefix starts with uppercase character
 * <li>methods if prefix starts with lowercase character
 */
public class TemplateFactory {
	private static class ClassTemplate extends Template implements TemplateWithImage {
		public ClassTemplate(final String name, final String description, final String pattern) {
			super(name, description, GsTemplateContextType.CONTEXT_TYPE, pattern, true);
		}

		@Override
		public String getImageId() {
			return GemDevUiPlugin.CLASS_ICON;
		}

		@Override
		public String toString() {
			return String.format("classTemplate(name=%s)", getName());
		}
	}

	private static class MessageTemplate extends Template implements TemplateWithImage {
		public MessageTemplate(final String name, final String description, final String pattern) {
			super(name, description, GsTemplateContextType.CONTEXT_TYPE, pattern, true);
		}

		@Override
		public String getImageId() {
			return GemDevUiPlugin.SELECTOR_ICON;
		}

		@Override
		public String toString() {
			return String.format("msgTemplate(name=%s)", getName());
		}
	}

	public List<Template> createClassTemplates(final ISession session, final TemplateContext context, final String prefix) {
		final List<Template> result = Lists.newLinkedList();
		for (final DbClass clazz : session.getCachedClasses()) {
			if (clazz.getName().toLowerCase().startsWith(prefix.toLowerCase())) {
				result.add(new ClassTemplate(clazz.getName(), "Class", clazz.getName()));
			}
		}
		Collections.sort(result, new Comparator<Template>() {
			@Override
			public int compare(final Template o1, final Template o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return result;
	}

	/**
	 * Looks on methodName and suggests template pattern which can be used for its expansion.
	 *
	 * @param methodName should be correct selector
	 * @return pattern or null if nothing couldn't be found
	 */
	public String createMessageTemplatePattern(final String methodName) {
		if (methodName.indexOf(':') != -1) {
			// keyword message
			final String[] parts = methodName.split(":");
			final StringBuilder sb = new StringBuilder(methodName.length() * 2);
			for (int i = 0; i < parts.length; i++) {
				if (i > 0) {
					sb.append(" ");
				}
				sb.append(parts[i]).append(": ${p").append(i + 1).append("}");
			}
			return sb.toString();
		}

		return methodName;
	}

	public List<Template> createMessageTemplates(final DbBehavior behavior, final TemplateContext context, final String prefix) {
		final List<Template> result = Lists.newLinkedList();
		for (final MethodReference m : getBindingTargetMethods(behavior, prefix)) {
			final String pattern = createMessageTemplatePattern(m.getMethodName());

			if (pattern != null) {
				result.add(new MessageTemplate(m.getMethodName(), m.getClassName(), pattern));
			}
		}
		Collections.sort(result, new Comparator<Template>() {
			@Override
			public int compare(final Template o1, final Template o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return result;
	}

	public List<Template> createMessageTemplates(final ISession system, final TemplateContext context, final String prefix) {
		final List<Template> result = Lists.newLinkedList();
		for (final Selector s : system.getCachedSelectors()) {
			final String name = s.getName();
			if (name.toLowerCase().startsWith(prefix.toLowerCase())) {
				final String pattern = createMessageTemplatePattern(name);

				if (pattern != null) {
					final List<MethodReference> methods = system.getCachedMethods(s);
					String description = methods.get(0).getClassName();
					if (methods.size() > 1) {
						description = description + ", ...";
					}
					result.add(new MessageTemplate(name, description, pattern));
				}
			}
		}
		Collections.sort(result, new Comparator<Template>() {
			@Override
			public int compare(final Template o1, final Template o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return result;
	}

	/**
	 * Main factory method which depending on provided prefix will heuristically decide what templates to create.
	 *
	 * @param methodContext which should be used for finding information about system
	 * @param context for templates
	 * @param prefix is string from text editor before point content assist was invoked
	 * @param receiverString
	 * @return list of possible templates
	 */
	public List<Template> createTemplates(final IMethodContext methodContext, final TemplateContext context, final String prefix, final String receiverString) {
		Preconditions.checkNotNull(methodContext);

		final List<Template> result = Lists.newLinkedList();

		if (!Strings.isNullOrEmpty(prefix) && Character.isUpperCase(prefix.charAt(0))) {
			result.addAll(createClassTemplates(methodContext.getSession(), context, prefix));
		} else {
			List<Template> messageTemplates = Lists.newLinkedList();

			final DbBehavior receiverBehavior = (DbBehavior) methodContext.resolve(receiverString);
			if (receiverBehavior != null) {
				messageTemplates = createMessageTemplates(receiverBehavior, context, prefix);
			} else {
				messageTemplates = createMessageTemplates(methodContext.getSession(), context, prefix);
			}
			result.addAll(messageTemplates);
		}

		return result;
	}

	public List<MethodReference> getBindingTargetMethods(final DbBehavior receiverBehavior, final String prefix) {
		Preconditions.checkNotNull(receiverBehavior);
		Preconditions.checkNotNull(prefix);

		final boolean doPrefixMatching = prefix.length() > 0;
		final String lowercasePrefix = prefix.toLowerCase();

		final Set<String> methodNames = Sets.newHashSet();
		final List<MethodReference> methods = Lists.newLinkedList();
		for (final MethodReference m : receiverBehavior.getCachedMethodsYouAreResponding()) {
			final String name = m.getMethodName();
			if (!methodNames.contains(name) && (!doPrefixMatching || name.toLowerCase().startsWith(lowercasePrefix))) {
				methodNames.add(name);
				methods.add(m);
			}
		}
		Collections.sort(methods, new Comparator<MethodReference>() {
			@Override
			public int compare(final MethodReference o1, final MethodReference o2) {
				return o1.getMethodName().compareTo(o2.getMethodName());
			}
		});
		return methods;
	}
}
