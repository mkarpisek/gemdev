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
package net.karpisek.gemdev.team.model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.model.IBehavior;
import net.karpisek.gemdev.core.model.ICategory;
import net.karpisek.gemdev.core.model.IClass;
import net.karpisek.gemdev.core.model.IMetaclass;
import net.karpisek.gemdev.core.model.IMethod;
import net.karpisek.gemdev.team.WorkingCopy;
import net.karpisek.gemdev.utils.Pair;

/**
 * In memory representation of {@link IClass}. Intentionally is counted as isolated unit. File format is generally: <DbClass declaration> //class declaration is
 * mandatory <Instance methods> //0..N instance methods <Cnstance methods> //0..N class methods Details: DbClass: <className> //must be on first line of file
 * SuperClass: <SuperClass> //superclass name can be empty/nil or string InstVarNames: <ivar1>,...,<ivarN> //ivar names separated by comma, can be empty
 * ClassVarNames: <cvar1>,...,<cvarN> //cvar names separated by comma, can be empty DbCategory: <categoryName> //name of system category - must not be empty %
 * //symbol % in first column of line, ending class declaration InstanceMethod: <methodSelector> //must not be empty DbCategory: <categoryName> //name of method
 * protocol, must not be empty <sourceCode> % //symbol % in first column of line, ending method declaration ClassMethod: <methodSelector> //must not be empty
 * DbCategory: <categoryName> //name of method protocol, must not be empty <sourceCode> % //symbol % in first column of line, ending method declaration
 */
public class LocalClass extends LocalBehavior implements IClass {
	public static final String CLASS_NAME_PROPERTY = "Class"; //$NON-NLS-1$
	private static final String SUPER_CLASS_NAME_PROPERTY = "SuperClass"; //$NON-NLS-1$
	private static final String INSTANCE_VARIABLES_PROPERTY = "InstanceVariables"; //$NON-NLS-1$
	private static final String CLASS_VARIABLES_PROPERTY = "ClassVariables"; //$NON-NLS-1$
	private static final String CATEGORY_NAME_PROPERTY = "Category"; //$NON-NLS-1$
	private static final String END_MARKER = "%"; //$NON-NLS-1$

	private static final String INSTANCE_METHOD_PROPERTY = "InstanceMethod"; //$NON-NLS-1$
	private static final String CLASS_METHOD_PROPERTY = "ClassMethod"; //$NON-NLS-1$

	public static Pair<String, Boolean> getMethodReferenceProperty(final String className, final String line, final int lineNumber) throws IOException {
		String methodName = null;
		boolean instanceSide = true;

		final String instancePropertyKey = INSTANCE_METHOD_PROPERTY + ":"; //$NON-NLS-1$
		final String classPropertyKey = CLASS_METHOD_PROPERTY + ":"; //$NON-NLS-1$

		if (!Strings.isNullOrEmpty(line)) {
			if (line.startsWith(instancePropertyKey)) {
				methodName = getNonEmptyProperty(INSTANCE_METHOD_PROPERTY, line, lineNumber);
			} else {
				if (line.startsWith(classPropertyKey)) {
					methodName = getNonEmptyProperty(CLASS_METHOD_PROPERTY, line, lineNumber);
					instanceSide = false;
				}
			}
		}
		if (methodName == null) {
			throw new IOException(String.format("[Line %d] Property '%s' or '%s' not found on line '%s'", lineNumber, instancePropertyKey, classPropertyKey, //$NON-NLS-1$
					line == null ? "" : line)); //$NON-NLS-1$
		}
		return new Pair<String, Boolean>(methodName, instanceSide);
	}

	public static String getNonEmptyProperty(final String propertyName, final String line, final int lineNumber) throws IOException {
		final String propertyValue = getProperty(propertyName, line, lineNumber);
		if (Strings.isNullOrEmpty(propertyValue)) {
			throw new IOException(String.format("[Line %d] Property '%s' must not be empty", lineNumber, propertyName)); //$NON-NLS-1$
		}
		return propertyValue;
	}

	public static String getProperty(final String propertyName, final String line, final int lineNumber) throws IOException {
		final String propertyKey = propertyName + ":"; //$NON-NLS-1$
		if (Strings.isNullOrEmpty(line) || !line.startsWith(propertyKey)) {
			throw new IOException(String.format("[Line %d] Property '%s' not found on line '%s'", lineNumber, propertyName, line == null ? "" : line)); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return Strings.nullToEmpty(substringAfter(line, propertyKey)).trim();
	}

	public static List<String> getStringListProperty(final String propertyName, final String line, final int lineNumber) throws IOException {
		final String propertyValue = getProperty(propertyName, line, lineNumber);
		return Lists.newArrayList(Splitter.on(',').trimResults().omitEmptyStrings().split(propertyValue));
	}

	public static void checkIsEndMarker(final String endMarker, final String line, final int lineNumber) throws IOException {
		if (Strings.isNullOrEmpty(line) || !line.startsWith(endMarker) || !line.trim().equals(endMarker)) {
			throw new IOException(String.format("[Line %d] Expected end maker line (only '%s'), got '%s'", lineNumber, endMarker, line)); //$NON-NLS-1$
		}
	}

	public static LocalClass read(final WorkingCopy workingCopy, final Reader reader) throws IOException {
		try (final LineNumberReader in = new LineNumberReader(reader)) {
			final String first = in.readLine();
			final String className = getNonEmptyProperty(CLASS_NAME_PROPERTY, first, in.getLineNumber());

			final LocalClass c = new LocalClass(workingCopy, className);
			c.readClass(in);
			return c;
		}
	}

	private static String substringAfter(String str, String separator) {
		if (Strings.isNullOrEmpty(str) || Strings.isNullOrEmpty(separator)) {
			return "";
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	private final WorkingCopy workingCopy;

	private final String name;

	private final LocalMetaclass metaclass;
	private String superClassName;

	private List<String> instanceVariables;
	private List<String> classVariables;
	private String categoryName;

	public LocalClass(final WorkingCopy workingCopy, final String name) {
		this.workingCopy = workingCopy;

		this.name = name;
		this.metaclass = new LocalMetaclass(this);

		this.superClassName = ""; //$NON-NLS-1$
		this.instanceVariables = Lists.newLinkedList();
		this.classVariables = Lists.newLinkedList();
		this.categoryName = ""; //$NON-NLS-1$
	}

	/**
	 * Answers new local class with same definition as receiver including copies of its method.
	 */
	public LocalClass deepCopy() {
		final LocalClass classCopy = emptyCopy();

		for (final boolean instanceSide : new boolean[] { true, false }) {
			for (final IMethod method : getMethods(instanceSide)) {
				final LocalBehavior behavior = (LocalBehavior) classCopy.getBehavior(instanceSide);
				behavior.createMethod(method.getName(), method.getCategory().getName(), method.getSourceCode());
			}
		}
		return classCopy;
	}

	/**
	 * Answers new local class with same definition as receiver but without any method.
	 */
	public LocalClass emptyCopy() {
		final LocalClass copy = new LocalClass(workingCopy, getName());
		copy.superClassName = getSuperclassName();
		copy.instanceVariables = Lists.newArrayList(getInstanceVariables());
		copy.classVariables = Lists.newArrayList(getClassVariables());
		copy.categoryName = this.getCategoryName();
		return copy;
	}

	public IBehavior getBehavior(final boolean instanceSide) {
		if (instanceSide) {
			return getInstanceSide();
		}
		return getClassSide();
	}

	@Override
	public List<ICategory> getCategories(final boolean instanceSide) {
		return getBehavior(instanceSide).getCategories();
	}

	@Override
	public ICategory getCategory(final String name, final boolean instanceSide) {
		return getBehavior(instanceSide).getCategory(name);
	}

	@Override
	public String getCategoryName() {
		return categoryName;
	}

	@Override
	public String getClassName() {
		return name;
	}

	@Override
	public IMetaclass getClassSide() {
		return metaclass;
	}

	@Override
	public List<String> getClassVariables() {
		return classVariables;
	}

	@Override
	public IClass getInstanceSide() {
		return this;
	}

	@Override
	public List<String> getInstanceVariables() {
		return instanceVariables;
	}

	@Override
	public IMethod getMethod(final String name, final boolean instanceSide) {
		return getBehavior(instanceSide).getMethod(name);
	}

	@Override
	public List<IMethod> getMethods(final boolean instanceSide) {
		return getBehavior(instanceSide).getMethods();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getSuperclassName() {
		return superClassName;
	}

	public WorkingCopy getWorkingCopy() {
		return workingCopy;
	}

	@Override
	public boolean isInstanceSide() {
		return true;
	}

	public void setCategoryName(final String categoryName) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(categoryName), "DbCategory name must not be empty"); //$NON-NLS-1$

		this.categoryName = categoryName;
	}

	public void setSuperClassName(final String name) {
		this.superClassName = name;
	}

	@Override
	public String toString() {
		final StringWriter sw = new StringWriter();
		write(sw);
		return sw.toString();
	}

	/**
	 * Write receiver text representation in .gsc format.
	 * 
	 * @param writer to which class should be written
	 */
	public void write(final Writer writer) {
		final PrintWriter out = new PrintWriter(new BufferedWriter(writer));
		writeDeclaration(out);
		writeMethods(out, getInstanceSide().getMethods());
		writeMethods(out, getClassSide().getMethods());
		out.flush();
	}

	private void readClass(final LineNumberReader reader) throws IOException {
		superClassName = getNonEmptyProperty(SUPER_CLASS_NAME_PROPERTY, reader.readLine(), reader.getLineNumber());

		instanceVariables = getStringListProperty(INSTANCE_VARIABLES_PROPERTY, reader.readLine(), reader.getLineNumber());
		classVariables = getStringListProperty(CLASS_VARIABLES_PROPERTY, reader.readLine(), reader.getLineNumber());
		categoryName = getNonEmptyProperty(CATEGORY_NAME_PROPERTY, reader.readLine(), reader.getLineNumber());
		checkIsEndMarker(END_MARKER, reader.readLine(), reader.getLineNumber());

		readMethods(reader);
	}

	private void readMethods(final LineNumberReader reader) throws IOException {
		String line = ""; //$NON-NLS-1$
		while ((line = reader.readLine()) != null) {
			final Pair<String, Boolean> pair = getMethodReferenceProperty(getName(), line, reader.getLineNumber());
			final String methodCategory = getNonEmptyProperty(CATEGORY_NAME_PROPERTY, reader.readLine(), reader.getLineNumber());

			int lineCounter = 0;
			boolean stop = false;
			final String endline = System.lineSeparator();
			final StringBuilder sourceCodeBuilder = new StringBuilder();
			while (!stop) {
				line = reader.readLine();
				if (line == null) {
					throw new IOException(String.format("[Line %d] Premature end of method source code", reader.getLineNumber())); //$NON-NLS-1$
				}
				stop = line.equals(END_MARKER);
				if (!stop) {
					sourceCodeBuilder.append(line).append(endline);
					lineCounter++;
				}
			}
			if (lineCounter == 0) {
				throw new IOException(String.format("[Line %d] DbMethod source code is empty", reader.getLineNumber())); //$NON-NLS-1$
			}

			final LocalBehavior behavior = (LocalBehavior) getBehavior(pair.getValue());
			behavior.createMethod(pair.getKey(), methodCategory, sourceCodeBuilder.toString());
		}
	}

	private void writeDeclaration(final PrintWriter writer) {
		writeProperty(writer, CLASS_NAME_PROPERTY, getName());
		writeProperty(writer, SUPER_CLASS_NAME_PROPERTY, getSuperclassName());
		writeProperty(writer, INSTANCE_VARIABLES_PROPERTY, getInstanceVariables());
		writeProperty(writer, CLASS_VARIABLES_PROPERTY, getClassVariables());
		writeProperty(writer, CATEGORY_NAME_PROPERTY, getCategoryName());
		writeEndMarker(writer);
	}

	private void writeEndMarker(final PrintWriter writer) {
		writer.println(END_MARKER);
	}

	private void writeMethod(final PrintWriter writer, final IMethod method) {
		writeProperty(writer, method.isInstanceSide() ? INSTANCE_METHOD_PROPERTY : CLASS_METHOD_PROPERTY, method.getName());
		writeProperty(writer, CATEGORY_NAME_PROPERTY, method.getCategory().getName());

		final String sourceCode = method.getSourceCode();
		writer.print(sourceCode);

		// now the problem - source code can include ending newline or not - but end marker HAS to be on newline
		// so append newline if it is needed
		// otherwise not (because in that case we would be adding empty lines to source code)
		String unixLineSeparator = "\n";
		String windowsLineSeparator = "\r\n";
		if (!(sourceCode.endsWith(System.lineSeparator()) || sourceCode.endsWith(unixLineSeparator) || sourceCode.endsWith(windowsLineSeparator))) {
			writer.println();
		}
		writeEndMarker(writer);
	}

	private void writeMethods(final PrintWriter writer, final List<IMethod> methods) {
		Collections.sort(methods, new Comparator<IMethod>() {
			@Override
			public int compare(final IMethod o1, final IMethod o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		for (final IMethod m : methods) {
			writeMethod(writer, m);
		}
	}

	private void writeProperty(final PrintWriter writer, final String propertyName, final List<String> varNames) {
		writer.print(propertyName);
		writer.print(": "); //$NON-NLS-1$
		int counter = 0;
		for (final String varName : varNames) {
			if (counter > 0) {
				writer.print(","); //$NON-NLS-1$
			}
			writer.print(varName);
			counter++;
		}
		writer.println();
	}

	private void writeProperty(final PrintWriter writer, final String propertyName, final String propertyValue) {
		writer.print(propertyName);
		writer.print(": "); //$NON-NLS-1$
		writer.print(propertyValue);
		writer.println();
	}
}
