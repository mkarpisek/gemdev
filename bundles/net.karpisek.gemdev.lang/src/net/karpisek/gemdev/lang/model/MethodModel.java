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
package net.karpisek.gemdev.lang.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.karpisek.gemdev.lang.parser.GsParser;
import net.karpisek.gemdev.lang.parser.GsTree;
import net.karpisek.gemdev.lang.parser.SyntaxError;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

/**
 * Model of smalltalk method.

 */
public class MethodModel{
	public enum Type{UNARY, BINARY,KEYWORD};

	private final GsTree ast;
	private final List<SyntaxError> syntaxErrors;

	private final Context context;
	private final List<Context> contexts;

	private Type type;
	private final List<String> selectorParts;

	private final List<Element> elements;
	private final Map<Integer, Element> elementsByOffset;


	public MethodModel(final GsTree ast, final List<SyntaxError> syntaxErrors){
		this.ast = ast;
		this.syntaxErrors = syntaxErrors;
		this.context = new Context(null);
		this.contexts = Lists.newLinkedList();
		this.contexts.add(context);

		this.selectorParts = Lists.newLinkedList();
		this.elements = Lists.newLinkedList();
		this.elementsByOffset = Maps.newHashMap();

		//traverse ast and create context hierarchy
		final VisitorData data = new VisitorData();
		visit(ast, context, data);
		createElements(data);
	}

	public boolean hasSyntaxError(){
		return !syntaxErrors.isEmpty();
	}

	public List<SyntaxError> getSyntaxErrors() {
		return syntaxErrors;
	}

	/**
	 * @return method context (root of context tree)
	 */
	public Context getContext(){
		return context;
	}

	/**
	 * @return all contexts for this method
	 */
	public List<Context> getContexts(){
		return Lists.newArrayList(contexts.iterator());
	}

	public Type getType() {
		return type;
	}

	public List<String> getSelectorParts(){
		return Lists.newArrayList(selectorParts.iterator());
	}

	public String getSelector(){
		final StringBuilder sb = new StringBuilder();
		for (final String part : selectorParts) {
			sb.append(part);
		}
		return sb.toString();
	}

	public Map<Integer, Element> getElements() {
		return elementsByOffset;
	}

	/**
	 * Element which occurs on offset of source code.
	 * @param offset of element
	 * @return element or null if nothing is found
	 */
	public Element getElement(final int offset){
		return elementsByOffset.get(offset);
	}

	public List<Identifier> getIdentifiers(){
		final List<Identifier> result = Lists.newLinkedList();
		for (final Element element : elements) {
			if(element instanceof Identifier){
				result.add((Identifier) element);
			}
		}
		return result;
	}

	public List<Identifier> getUndeclaredIdentifiers(){
		final List<Identifier> result = Lists.newLinkedList();
		for (final Element element : elements) {
			if(element instanceof Identifier && !(element instanceof LocalIdentifier)){
				result.add((Identifier) element);
			}
		}
		return result;
	}

	/**
	 * @param identifierType for which to filter result or null if all types should be returned
	 * @return list of local identifiers
	 */
	public List<LocalIdentifier> getLocalIdentifiers(final LocalIdentifier.Type identifierType){
		final List<LocalIdentifier> result = Lists.newLinkedList();
		for (final Element element : elements) {
			if(element instanceof LocalIdentifier && (identifierType == null || identifierType == ((LocalIdentifier)element).getType())){
				result.add((LocalIdentifier) element);
			}
		}
		return result;
	}

	public List<Symbol> getSymbols(){
		final List<Symbol> result = Lists.newLinkedList();
		for (final Element element : elements) {
			if(element instanceof Symbol){
				result.add((Symbol) element);
			}
		}
		return result;
	}

	/**
	 * @param messageType for which to filter result or null if all types should be returned
	 * @return list of messages
	 */
	public List<Message> getMessages(final Message.Type messageType){
		final List<Message> result = Lists.newLinkedList();
		for (final Element element : elements) {
			if(element instanceof Message && (messageType == null || messageType == ((Message)element).getType())){
				result.add((Message) element);
			}
		}
		return result;
	}

	/**
	 * Collect all occurences of element in method.
	 * @param offset of element
	 * @return list of occurences
	 */
	public List<GsTree> getElementOccurences(final int offset){
		final Element element = elementsByOffset.get(offset);
		if(element == null){
			return Collections.emptyList();
		}
		return element.getOccurences();
	}

	private void visit(final GsTree node, final Context currentContext, final VisitorData data) {
		if(node == null) {
			return;
		}

		switch(node.getType()){
			case GsParser.UNARY_METHOD:
				type = Type.UNARY;
				visitChildren(node, currentContext, data);
				break;
			case GsParser.BINARY_METHOD:
				type = Type.BINARY;
				visitChildren(node, currentContext, data);
				break;
			case GsParser.KEYWORD_METHOD:
				type = Type.KEYWORD;
				visitChildren(node, currentContext, data);
				break;
			case GsParser.PARAMETERS:
				if(currentContext == context){
					//method level parameters
					for (final Object o : getChildren(node)) {
						final CommonTree t = (CommonTree) o;
						if(t.getType() != GsParser.PARAMETER){
							//filter out parameters - there will remain selector parts
							String selectorPart = t.getText();
							if(node.getParent().getType() == GsParser.KEYWORD_METHOD){
								selectorPart += ":"; //$NON-NLS-1$
							}
							selectorParts.add(selectorPart);
						}
					}
				}
				visitChildren(node, currentContext, data);
				break;
			case GsParser.PARAMETER:
				//this is method level parameter
				currentContext.declareIdentifier(node);
				break;
			case GsParser.BLOCK_PARAMETER:
				currentContext.declareIdentifier(node);
				break;
			case GsParser.TEMPORARIES:
				for (final Object o : getChildren(node)) {
					currentContext.declareIdentifier((GsTree) o);
				}
				break;
			case GsParser.ID:
				if(node.getParent().getType() != GsParser.PARAMETERS){
					currentContext.referenceIdentifier(node);
				}
				break;
			case GsParser.BLOCK:
				final Context newContext = new Context(currentContext);
				contexts.add(newContext);
				visitChildren(node, newContext, data);
				break;

			case GsParser.UNARY_MESSAGE:
				visitUnaryMessage(node, currentContext, data);
				break;
			case GsParser.BINARY_MESSAGE:
				visitBinaryMessage(node, currentContext, data);
				break;
			case GsParser.KEYWORD_MESSAGE:
				visitKeywordMessage(node, currentContext, data);
				break;
			case GsParser.SYMBOL_LITERAL:
				//modify token position and text to remove leading '#' character from it
				final Token symbolToken = node.getToken();
				symbolToken.setCharPositionInLine(symbolToken.getCharPositionInLine()+1);
				symbolToken.setText(symbolToken.getText().substring(1));
				node.setOffset(node.getOffset()+1);
				data.symbols.put(symbolToken.getText(), node);
				break;
			default:{
				visitChildren(node, currentContext, data);
			}
		}
	}

	private void visitChildren(final GsTree node, final Context currentContext, final VisitorData data) {
		for (final Object o : getChildren(node)) {
			visit((GsTree) o, currentContext, data);
		}
	}

	private void visitUnaryMessage(final GsTree node, final Context currentContext, final VisitorData data) {
		data.getMessages(Message.Type.UNARY).put(node.getText(), Lists.newArrayList(node));
		visitChildren(node, currentContext, data);
	}

	private void visitBinaryMessage(final GsTree node, final Context currentContext, final VisitorData data) {
		final GsTree binarySelector = (GsTree) node.getChild(0);
		data.getMessages(Message.Type.BINARY).put(binarySelector.getText(), Lists.newArrayList(binarySelector));
		visitChildren(node, currentContext,data );
	}

	@SuppressWarnings("unchecked")
	private void visitKeywordMessage(final GsTree node, final Context currentContext, final VisitorData data) {
		final Iterator keywords = Iterators.filter(node.getChildren().iterator(), new Predicate<GsTree>(){
			@Override
			public boolean apply(final GsTree input) {
				return input.getType() == GsParser.KEYWORD;
			}
		});

		final List<GsTree> selectorParts = Lists.newArrayList(keywords);
		final StringBuilder sb = new StringBuilder();
		for (final GsTree part : selectorParts) {
			sb.append(part.getText()).append(":"); //$NON-NLS-1$
		}
		data.getMessages(Message.Type.KEYWORD).put(sb.toString(), selectorParts);

		visitChildren(node, currentContext, data);
	}

	private List<?> getChildren(final GsTree node){
		if(node == null || node.getChildren() == null) {
			return Collections.EMPTY_LIST;
		}
		return node.getChildren();
	}

	private void createElements(final VisitorData data) {
		createIdentifierElements(data);
		createSymbolElements(data);
		createMessageElements(data);

		//sort elements by first offset
		final Map<Element, Integer> elementsByFirstOffset = Maps.newHashMap();
		for (final Map.Entry<Integer, Element> entry : elementsByOffset.entrySet()) {
			final Integer offset = elementsByFirstOffset.get(entry.getValue());
			if(offset == null || offset.intValue() > entry.getKey().intValue()){
				elementsByFirstOffset.put(entry.getValue(), entry.getKey());
			}
		}
		Collections.sort(elements, new Comparator<Element>(){
			@Override
			public int compare(final Element o1, final Element o2) {
				return elementsByFirstOffset.get(o1).compareTo(elementsByFirstOffset.get(o2));
			}
		});
	}

	private void createIdentifierElements(final VisitorData data) {
		final Map<GsTree, List<GsTree>> declaredIdentifiers = Maps.newHashMap();
		final Multimap<String, GsTree> undeclaredIdentifiers =LinkedHashMultimap.create();

		//put there all existing declarations as keys
		for (final Context c : getContexts()) {
			for (final GsTree declaration : c.getIdentifierDeclarations()) {
				declaredIdentifiers.put(declaration, new LinkedList<GsTree>());
			}
		}

		for (final Context c : getContexts()) {
			for (final GsTree ref : c.getIdentifierReferences()) {
				final GsTree decl = c.resolve(ref.getText());

				if(decl == null){
					//identifier not declared in method -> collect them in map by name
					undeclaredIdentifiers.put(ref.getText(), ref);
				}
				else{
					//identifier declared in method -> collect by declaration token
					declaredIdentifiers.get(decl).add(ref);
				}
			}
		}

		for (final Entry<GsTree, List<GsTree>> entry : declaredIdentifiers.entrySet()) {
			LocalIdentifier.Type elementType = LocalIdentifier.Type.TMP_VAR;
			if(GsParser.PARAMETER == entry.getKey().getType()){
				elementType = LocalIdentifier.Type.METHOD_PARAMETER;
			}
			if(GsParser.BLOCK_PARAMETER == entry.getKey().getType()){
				elementType = LocalIdentifier.Type.BLOCK_PARAMETER;
			}

			addElement(new LocalIdentifier(elementType, entry.getKey(), entry.getValue(), this));
		}
		for (final Entry<String, Collection<GsTree>> entry : undeclaredIdentifiers.asMap().entrySet()) {
			final List<GsTree> refs = Lists.newLinkedList();
			refs.addAll(entry.getValue());

			addElement(new Identifier(entry.getKey(), refs , this));
		}
	}

	private void createSymbolElements(final VisitorData data) {
		for (final Entry<String, Collection<GsTree>> entry: data.symbols.asMap().entrySet()) {
			final List<GsTree> refs = Lists.newLinkedList();
			refs.addAll(entry.getValue());

			addElement(new Symbol(entry.getKey(), refs , this));
		}
	}

	private void createMessageElements(final VisitorData data) {
		for (final Message.Type t : Message.Type.values()) {
			for (final Entry<String, Collection<List<GsTree>>> entry: data.getMessages(t).asMap().entrySet()) {
				final List<MessageReference> refs = Lists.newLinkedList();
				for (final List<GsTree> parts : entry.getValue()) {
					final GsTree firstPart = parts.get(0);
					final GsTree parent = firstPart.getParentOfType(GsParser.EXPR);
					final GsTree rec = (GsTree) parent.getChild(0);
					final Element e = elementsByOffset.get(rec.getOffset());


					refs.add(new MessageReference(e, parts));
				}
				addElement(new Message(entry.getKey(), t, refs, this));
			}
		}
	}

	private void addElement(final Element element){
		elements.add(element);
		for (final Integer offset : element.getOffsets()) {
			elementsByOffset.put(offset, element);
		}
	}

	private static class VisitorData{
		private final Multimap<String, GsTree> symbols;
		private final Map<Message.Type, Multimap<String, List<GsTree>>> messages;

		public VisitorData(){
			this.symbols = LinkedHashMultimap.create();
			this.messages = Maps.newHashMap();
			for (final Message.Type t : Message.Type.values()) {
				final Multimap<String, List<GsTree>> map = LinkedHashMultimap.create();
				messages.put(t, map);
			}
		}

		public Multimap<String, GsTree> getSymbols() {
			return symbols;
		}

		public Multimap<String, List<GsTree>> getMessages(final Message.Type type){
			return messages.get(type);
		}
	}
}
