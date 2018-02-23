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
package net.karpisek.gemdev.lang;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.RewriteCardinalityException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import net.karpisek.gemdev.lang.lexer.GsLexer;
import net.karpisek.gemdev.lang.model.Message;
import net.karpisek.gemdev.lang.model.Message.Type;
import net.karpisek.gemdev.lang.model.MethodModel;
import net.karpisek.gemdev.lang.parser.GsErrorNode;
import net.karpisek.gemdev.lang.parser.GsParser;
import net.karpisek.gemdev.lang.parser.GsParser.method_return;
import net.karpisek.gemdev.lang.parser.GsTree;
import net.karpisek.gemdev.lang.parser.SyntaxError;

/**
 * Public interface for parser. Clients should use provided methods from this class and not create parser directly.
 */
public class ParserUtils {
	public static class ParsedSelector {
		private final Message.Type type;
		private final List<String> selectorParts;

		public ParsedSelector(final Message.Type type, final List<String> selectorParts) {
			this.type = type;
			this.selectorParts = selectorParts;
		}

		@Override
		public boolean equals(final Object obj) {
			if (obj instanceof ParsedSelector) {
				final ParsedSelector other = (ParsedSelector) obj;
				return Objects.equal(type, other.type) && Objects.equal(selectorParts, other.selectorParts);
			}
			return false;
		}

		public List<String> getSelectorParts() {
			return selectorParts;
		}

		public Message.Type getType() {
			return type;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(type, selectorParts);
		}

	}

	public static class Result {
		private final GsTree ast;
		private final List<SyntaxError> syntaxErrors;

		public Result(final GsTree ast, final List<SyntaxError> syntaxErrors) {
			this.ast = ast;
			this.syntaxErrors = syntaxErrors;
		}

		public GsTree getAst() {
			return ast;
		}

		public List<SyntaxError> getSyntaxErrors() {
			return syntaxErrors;
		}
	}

	/**
	 * Builds model of method from source code.
	 * 
	 * @param sourceCode of smalltalk method.
	 * @return model of method
	 * @throws IOException
	 * @throws RecognitionException
	 */
	public static MethodModel build(final String sourceCode) throws IOException {
		final Result result = parse(sourceCode);
		return new MethodModel(result.getAst(), result.getSyntaxErrors());
	}

	/**
	 * Creates AST of source code.
	 * 
	 * @param sourceCode of smalltalk method.
	 * @return
	 * @throws IOException
	 * @throws RecognitionException
	 */
	public static Result parse(final String sourceCode) throws IOException {
		final ANTLRReaderStream input = new ANTLRReaderStream(new StringReader(sourceCode));
		final GsLexer lexer = new GsLexer(input);
		final CommonTokenStream tokens = new CommonTokenStream(lexer);
		final GsParser parser = new GsParser(tokens);
		parser.setTreeAdaptor(new CommonTreeAdaptor() {
			@Override
			public Object create(final Token token) {
				return new GsTree(token);
			}

			@Override
			public Object errorNode(final TokenStream input, final Token start, final Token stop, final RecognitionException e) {
				return new GsErrorNode(input, start, stop, e);
			}
		});
		method_return methodResult;
		try {
			methodResult = parser.method();
			fillOffsets(new Document(sourceCode), (GsTree) methodResult.getTree());
		} catch (final RecognitionException e) {
			final String hdr = parser.getErrorHeader(e);
			final String msg = parser.getErrorMessage(e, GsParser.tokenNames);

			final Token t = e.token;
			int length = 1;
			if (t != null && t.getText() != null) {
				length = t.getText().length();
			}

			final SyntaxError error = new SyntaxError(hdr + " " + msg, //$NON-NLS-1$
					e.line, e.charPositionInLine, length);

			return new Result(null, Lists.newArrayList(error));
		} catch (final RewriteCardinalityException e) {
			return new Result(null, Lists.newArrayList(new SyntaxError(e.getMessage(), 0, 0, 1)));
		}

		return new Result((GsTree) methodResult.getTree(), parser.getSyntaxErrors());
	}

	/**
	 * Performs analysis of message selector.
	 * 
	 * @param selector of message (method name)
	 * @return parsed selector or null on parsing error
	 */
	public static ParsedSelector parseSelector(final String selector) {
		try {
			final ANTLRReaderStream input = new ANTLRReaderStream(new StringReader(selector));
			final GsLexer lexer = new GsLexer(input);
			final CommonTokenStream tokens = new CommonTokenStream(lexer);
			final List<?> tokenList = tokens.getTokens();
			if (tokenList.isEmpty()) {
				return null;
			}
			if (tokenList.size() == 1) {
				final CommonToken t = (CommonToken) tokenList.get(0);
				if (t.getType() == GsLexer.ID) {
					return new ParsedSelector(Type.UNARY, Lists.newArrayList(t.getText()));
				}
				if (t.getType() == GsLexer.BINARY_SELECTOR) {
					return new ParsedSelector(Type.BINARY, Lists.newArrayList(t.getText()));
				}
				return null;
			}
			final List<String> selectorParts = Lists.newLinkedList();
			if ((tokenList.size() % 2) != 0) {
				return null;
			}
			for (int i = 0; i < tokenList.size(); i += 2) {
				final CommonToken t1 = (CommonToken) tokenList.get(i);
				final CommonToken t2 = (CommonToken) tokenList.get(i + 1);

				if (t1.getType() != GsLexer.ID || t2.getType() != GsLexer.COLON) {
					return null;
				}
				selectorParts.add(t1.getText());
			}
			return new ParsedSelector(Type.KEYWORD, selectorParts);
		}

		catch (final IOException e1) {
			return null;
		}
	}

	private static void fillOffsets(final IDocument document, final GsTree tree) {
		try {
			if (tree.getToken().getLine() > 0) {

				tree.setOffset(document.getLineOffset(tree.getToken().getLine() - 1) + tree.getCharPositionInLine());
			}
			final List<?> children = tree.getChildren();
			if (children != null) {
				for (final Object o : children) {
					fillOffsets(document, (GsTree) o);
				}
			}
		} catch (final BadLocationException e) {
			LangPlugin.getDefault().logError(e);
		}
	}
}
