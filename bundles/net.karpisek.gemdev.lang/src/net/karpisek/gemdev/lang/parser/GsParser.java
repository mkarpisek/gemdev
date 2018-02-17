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
// $ANTLR 3.1.1 D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g 2009-10-04 15:01:21

package net.karpisek.gemdev.lang.parser;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.RewriteEarlyExitException;
import org.antlr.runtime.tree.RewriteRuleSubtreeStream;
import org.antlr.runtime.tree.RewriteRuleTokenStream;
import org.antlr.runtime.tree.TreeAdaptor;

public class GsParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "UNARY_METHOD", "BINARY_METHOD", "KEYWORD_METHOD", "PRIMITIVE", "PARAMETERS", "TEMPORARIES", "STATEMENTS", "ARRAY", "BLOCK", "EXPR", "UNARY_MESSAGE", "BINARY_MESSAGE", "KEYWORD_MESSAGE", "PARAMETER", "BLOCK_PARAMETER", "KEYWORD", "INT_LITERAL", "FLOAT_LITERAL", "ID", "COLON", "LT", "GT", "BAR", "DOT", "RETURN", "ASSIGN", "NIL", "TRUE", "FALSE", "SELF", "SUPER", "THIS_CONTEXT", "MINUS", "CHARACTER_LITERAL", "STRING_LITERAL", "SYMBOL_LITERAL", "LPAREN", "RPAREN", "LBRACKET", "COMMA", "RBRACKET", "LBRACE", "RBRACE", "AMPERSAND", "BINARY_SELECTOR", "PLUS", "LETTERS", "DIGITS", "EXPONENT", "INT_OR_FLOAT", "BINARY_SELECTOR_CHAR", "COMMENT", "NEWLINE", "WS", "'primitive:'", "'protected'", "'unprotected'", "'#'", "';'"
	};
	public static final int EXPONENT=52;
	public static final int LT=24;
	public static final int BINARY_SELECTOR=48;
	public static final int T__62=62;
	public static final int PARAMETERS=8;
	public static final int DIGITS=51;
	public static final int LBRACE=45;
	public static final int UNARY_MESSAGE=14;
	public static final int KEYWORD_METHOD=6;
	public static final int T__61=61;
	public static final int ID=22;
	public static final int EOF=-1;
	public static final int T__60=60;
	public static final int PRIMITIVE=7;
	public static final int INT_OR_FLOAT=53;
	public static final int LPAREN=40;
	public static final int LBRACKET=42;
	public static final int RPAREN=41;
	public static final int T__58=58;
	public static final int BINARY_SELECTOR_CHAR=54;
	public static final int EXPR=13;
	public static final int UNARY_METHOD=4;
	public static final int STRING_LITERAL=38;
	public static final int SYMBOL_LITERAL=39;
	public static final int COMMA=43;
	public static final int T__59=59;
	public static final int PARAMETER=17;
	public static final int RETURN=28;
	public static final int BINARY_MESSAGE=15;
	public static final int PLUS=49;
	public static final int SUPER=34;
	public static final int RBRACKET=44;
	public static final int COMMENT=55;
	public static final int DOT=27;
	public static final int KEYWORD_MESSAGE=16;
	public static final int ARRAY=11;
	public static final int INT_LITERAL=20;
	public static final int KEYWORD=19;
	public static final int RBRACE=46;
	public static final int AMPERSAND=47;
	public static final int CHARACTER_LITERAL=37;
	public static final int LETTERS=50;
	public static final int MINUS=36;
	public static final int TRUE=31;
	public static final int TEMPORARIES=9;
	public static final int FLOAT_LITERAL=21;
	public static final int COLON=23;
	public static final int WS=57;
	public static final int NEWLINE=56;
	public static final int NIL=30;
	public static final int THIS_CONTEXT=35;
	public static final int BINARY_METHOD=5;
	public static final int BLOCK=12;
	public static final int ASSIGN=29;
	public static final int STATEMENTS=10;
	public static final int GT=25;
	public static final int FALSE=32;
	public static final int BLOCK_PARAMETER=18;
	public static final int SELF=33;
	public static final int BAR=26;

	// delegates
	// delegators


	public GsParser(final TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public GsParser(final TokenStream input, final RecognizerSharedState state) {
		super(input, state);

	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(final TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}

	@Override
	public String[] getTokenNames() { return GsParser.tokenNames; }
	@Override
	public String getGrammarFileName() { return "D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g"; }


	private final List<SyntaxError> errors = new LinkedList<SyntaxError>();
	@Override
	public void displayRecognitionError(final String[] tokenNames,
		final RecognitionException e)
	{
		final String hdr = getErrorHeader(e);
		final String msg = getErrorMessage(e, tokenNames);
		final Token t = e.token;
		int length = 1;
		if(t != null && t.getText() != null){
			length = t.getText().length();
		}

		errors.add(
			new SyntaxError(
				hdr+" "+msg,
				e.line,
				e.charPositionInLine,
				length
			)
		);
	}
	public List<SyntaxError> getSyntaxErrors(){
		return errors;
	}


	public static class method_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "method"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:72:1: method : m= methodHeader b= methodBody -> ^( $m $b) ;
	public final GsParser.method_return method() throws RecognitionException {
		final GsParser.method_return retval = new GsParser.method_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.methodHeader_return m = null;

		GsParser.methodBody_return b = null;


		final RewriteRuleSubtreeStream stream_methodHeader=new RewriteRuleSubtreeStream(adaptor,"rule methodHeader");
		final RewriteRuleSubtreeStream stream_methodBody=new RewriteRuleSubtreeStream(adaptor,"rule methodBody");
		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:72:7: (m= methodHeader b= methodBody -> ^( $m $b) )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:72:9: m= methodHeader b= methodBody
			{
				pushFollow(FOLLOW_methodHeader_in_method180);
				m=methodHeader();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_methodHeader.add(m.getTree());
				}
				pushFollow(FOLLOW_methodBody_in_method184);
				b=methodBody();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_methodBody.add(b.getTree());
				}


				// AST REWRITE
				// elements: m, b
				// token labels:
				// rule labels: retval, b, m
				// token list labels:
				// rule list labels:
				if ( state.backtracking==0 ) {
					retval.tree = root_0;
					final RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);
					final RewriteRuleSubtreeStream stream_b=new RewriteRuleSubtreeStream(adaptor,"token b",b!=null?b.tree:null);
					final RewriteRuleSubtreeStream stream_m=new RewriteRuleSubtreeStream(adaptor,"token m",m!=null?m.tree:null);

					root_0 = (GsTree)adaptor.nil();
					// 72:43: -> ^( $m $b)
					{
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:72:46: ^( $m $b)
						{
							GsTree root_1 = (GsTree)adaptor.nil();
							root_1 = (GsTree)adaptor.becomeRoot(stream_m.nextNode(), root_1);

							adaptor.addChild(root_1, stream_b.nextTree());

							adaptor.addChild(root_0, root_1);
						}

					}

					retval.tree = root_0;}
			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "method"

	public static class methodHeader_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "methodHeader"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:74:1: methodHeader : ( unaryMethodHeader -> ^( UNARY_METHOD ^( PARAMETERS unaryMethodHeader ) ) | binaryMethodHeader -> ^( BINARY_METHOD ^( PARAMETERS binaryMethodHeader ) ) | keywordMethodHeader -> ^( KEYWORD_METHOD ^( PARAMETERS keywordMethodHeader ) ) );
	public final GsParser.methodHeader_return methodHeader() throws RecognitionException {
		final GsParser.methodHeader_return retval = new GsParser.methodHeader_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.unaryMethodHeader_return unaryMethodHeader1 = null;

		GsParser.binaryMethodHeader_return binaryMethodHeader2 = null;

		GsParser.keywordMethodHeader_return keywordMethodHeader3 = null;


		final RewriteRuleSubtreeStream stream_keywordMethodHeader=new RewriteRuleSubtreeStream(adaptor,"rule keywordMethodHeader");
		final RewriteRuleSubtreeStream stream_unaryMethodHeader=new RewriteRuleSubtreeStream(adaptor,"rule unaryMethodHeader");
		final RewriteRuleSubtreeStream stream_binaryMethodHeader=new RewriteRuleSubtreeStream(adaptor,"rule binaryMethodHeader");
		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:75:3: ( unaryMethodHeader -> ^( UNARY_METHOD ^( PARAMETERS unaryMethodHeader ) ) | binaryMethodHeader -> ^( BINARY_METHOD ^( PARAMETERS binaryMethodHeader ) ) | keywordMethodHeader -> ^( KEYWORD_METHOD ^( PARAMETERS keywordMethodHeader ) ) )
			int alt1=3;
			final int LA1_0 = input.LA(1);

			if ( (LA1_0==ID) ) {
				final int LA1_1 = input.LA(2);

				if ( (LA1_1==COLON) ) {
					alt1=3;
				}
				else if ( (LA1_1==EOF||(LA1_1>=INT_LITERAL && LA1_1<=ID)||LA1_1==LT||LA1_1==BAR||LA1_1==RETURN||(LA1_1>=NIL && LA1_1<=LPAREN)||LA1_1==LBRACKET||LA1_1==LBRACE||LA1_1==61) ) {
					alt1=1;
				}
				else {
					if (state.backtracking>0) {state.failed=true; return retval;}
					final NoViableAltException nvae =
						new NoViableAltException("", 1, 1, input);

					throw nvae;
				}
			}
			else if ( ((LA1_0>=LT && LA1_0<=BAR)||LA1_0==MINUS||LA1_0==COMMA||(LA1_0>=AMPERSAND && LA1_0<=PLUS)) ) {
				alt1=2;
			}
			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				final NoViableAltException nvae =
					new NoViableAltException("", 1, 0, input);

				throw nvae;
			}
			switch (alt1) {
				case 1 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:75:5: unaryMethodHeader
				{
					pushFollow(FOLLOW_unaryMethodHeader_in_methodHeader211);
					unaryMethodHeader1=unaryMethodHeader();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						stream_unaryMethodHeader.add(unaryMethodHeader1.getTree());
					}


					// AST REWRITE
					// elements: unaryMethodHeader
					// token labels:
					// rule labels: retval
					// token list labels:
					// rule list labels:
					if ( state.backtracking==0 ) {
						retval.tree = root_0;
						final RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

						root_0 = (GsTree)adaptor.nil();
						// 75:43: -> ^( UNARY_METHOD ^( PARAMETERS unaryMethodHeader ) )
						{
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:75:46: ^( UNARY_METHOD ^( PARAMETERS unaryMethodHeader ) )
							{
								GsTree root_1 = (GsTree)adaptor.nil();
								root_1 = (GsTree)adaptor.becomeRoot(adaptor.create(UNARY_METHOD, "UNARY_METHOD"), root_1);

								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:75:61: ^( PARAMETERS unaryMethodHeader )
								{
									GsTree root_2 = (GsTree)adaptor.nil();
									root_2 = (GsTree)adaptor.becomeRoot(adaptor.create(PARAMETERS, "PARAMETERS"), root_2);

									adaptor.addChild(root_2, stream_unaryMethodHeader.nextTree());

									adaptor.addChild(root_1, root_2);
								}

								adaptor.addChild(root_0, root_1);
							}

						}

						retval.tree = root_0;}
				}
				break;
				case 2 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:76:5: binaryMethodHeader
				{
					pushFollow(FOLLOW_binaryMethodHeader_in_methodHeader249);
					binaryMethodHeader2=binaryMethodHeader();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						stream_binaryMethodHeader.add(binaryMethodHeader2.getTree());
					}


					// AST REWRITE
					// elements: binaryMethodHeader
					// token labels:
					// rule labels: retval
					// token list labels:
					// rule list labels:
					if ( state.backtracking==0 ) {
						retval.tree = root_0;
						final RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

						root_0 = (GsTree)adaptor.nil();
						// 76:43: -> ^( BINARY_METHOD ^( PARAMETERS binaryMethodHeader ) )
						{
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:76:46: ^( BINARY_METHOD ^( PARAMETERS binaryMethodHeader ) )
							{
								GsTree root_1 = (GsTree)adaptor.nil();
								root_1 = (GsTree)adaptor.becomeRoot(adaptor.create(BINARY_METHOD, "BINARY_METHOD"), root_1);

								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:76:62: ^( PARAMETERS binaryMethodHeader )
								{
									GsTree root_2 = (GsTree)adaptor.nil();
									root_2 = (GsTree)adaptor.becomeRoot(adaptor.create(PARAMETERS, "PARAMETERS"), root_2);

									adaptor.addChild(root_2, stream_binaryMethodHeader.nextTree());

									adaptor.addChild(root_1, root_2);
								}

								adaptor.addChild(root_0, root_1);
							}

						}

						retval.tree = root_0;}
				}
				break;
				case 3 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:77:5: keywordMethodHeader
				{
					pushFollow(FOLLOW_keywordMethodHeader_in_methodHeader286);
					keywordMethodHeader3=keywordMethodHeader();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						stream_keywordMethodHeader.add(keywordMethodHeader3.getTree());
					}


					// AST REWRITE
					// elements: keywordMethodHeader
					// token labels:
					// rule labels: retval
					// token list labels:
					// rule list labels:
					if ( state.backtracking==0 ) {
						retval.tree = root_0;
						final RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

						root_0 = (GsTree)adaptor.nil();
						// 77:43: -> ^( KEYWORD_METHOD ^( PARAMETERS keywordMethodHeader ) )
						{
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:77:46: ^( KEYWORD_METHOD ^( PARAMETERS keywordMethodHeader ) )
							{
								GsTree root_1 = (GsTree)adaptor.nil();
								root_1 = (GsTree)adaptor.becomeRoot(adaptor.create(KEYWORD_METHOD, "KEYWORD_METHOD"), root_1);

								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:77:63: ^( PARAMETERS keywordMethodHeader )
								{
									GsTree root_2 = (GsTree)adaptor.nil();
									root_2 = (GsTree)adaptor.becomeRoot(adaptor.create(PARAMETERS, "PARAMETERS"), root_2);

									adaptor.addChild(root_2, stream_keywordMethodHeader.nextTree());

									adaptor.addChild(root_1, root_2);
								}

								adaptor.addChild(root_0, root_1);
							}

						}

						retval.tree = root_0;}
				}
				break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "methodHeader"

	public static class unaryMethodHeader_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "unaryMethodHeader"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:79:1: unaryMethodHeader : ID ;
	public final GsParser.unaryMethodHeader_return unaryMethodHeader() throws RecognitionException {
		final GsParser.unaryMethodHeader_return retval = new GsParser.unaryMethodHeader_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token ID4=null;

		GsTree ID4_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:79:18: ( ID )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:79:20: ID
			{
				root_0 = (GsTree)adaptor.nil();

				ID4=(Token)match(input,ID,FOLLOW_ID_in_unaryMethodHeader332); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					ID4_tree = (GsTree)adaptor.create(ID4);
					adaptor.addChild(root_0, ID4_tree);
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "unaryMethodHeader"

	public static class binaryMethodHeader_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "binaryMethodHeader"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:80:1: binaryMethodHeader : binarySelector ID ;
	public final GsParser.binaryMethodHeader_return binaryMethodHeader() throws RecognitionException {
		final GsParser.binaryMethodHeader_return retval = new GsParser.binaryMethodHeader_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token ID6=null;
		GsParser.binarySelector_return binarySelector5 = null;


		GsTree ID6_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:80:19: ( binarySelector ID )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:80:21: binarySelector ID
			{
				root_0 = (GsTree)adaptor.nil();

				pushFollow(FOLLOW_binarySelector_in_binaryMethodHeader338);
				binarySelector5=binarySelector();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, binarySelector5.getTree());
				}
				ID6=(Token)match(input,ID,FOLLOW_ID_in_binaryMethodHeader340); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					ID6_tree = (GsTree)adaptor.create(ID6);
					adaptor.addChild(root_0, ID6_tree);
				}
				if ( state.backtracking==0 ) {
					ID6.setType(PARAMETER);
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "binaryMethodHeader"

	public static class keywordMethodHeader_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "keywordMethodHeader"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:81:1: keywordMethodHeader : ( keyword ID )+ ;
	public final GsParser.keywordMethodHeader_return keywordMethodHeader() throws RecognitionException {
		final GsParser.keywordMethodHeader_return retval = new GsParser.keywordMethodHeader_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token ID8=null;
		GsParser.keyword_return keyword7 = null;


		GsTree ID8_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:81:20: ( ( keyword ID )+ )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:81:22: ( keyword ID )+
			{
				root_0 = (GsTree)adaptor.nil();

				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:81:22: ( keyword ID )+
				int cnt2=0;
				loop2:
					do {
						int alt2=2;
						final int LA2_0 = input.LA(1);

						if ( (LA2_0==ID) ) {
							final int LA2_2 = input.LA(2);

							if ( (LA2_2==COLON) ) {
								alt2=1;
							}


						}


						switch (alt2) {
							case 1 :
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:81:23: keyword ID
								{
									pushFollow(FOLLOW_keyword_in_keywordMethodHeader352);
									keyword7=keyword();

									state._fsp--;
									if (state.failed) {
										return retval;
									}
									if ( state.backtracking==0 ) {
										adaptor.addChild(root_0, keyword7.getTree());
									}
									ID8=(Token)match(input,ID,FOLLOW_ID_in_keywordMethodHeader354); if (state.failed) {
										return retval;
									}
									if ( state.backtracking==0 ) {
										ID8_tree = (GsTree)adaptor.create(ID8);
										adaptor.addChild(root_0, ID8_tree);
									}
									if ( state.backtracking==0 ) {
										ID8.setType(PARAMETER);
									}

								}
								break;

							default :
								if ( cnt2 >= 1 ) {
									break loop2;
								}
								if (state.backtracking>0) {state.failed=true; return retval;}
								final EarlyExitException eee =
									new EarlyExitException(2, input);
								throw eee;
						}
						cnt2++;
					} while (true);


			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "keywordMethodHeader"

	public static class keyword_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "keyword"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:83:1: keyword : ID COLON ;
	public final GsParser.keyword_return keyword() throws RecognitionException {
		final GsParser.keyword_return retval = new GsParser.keyword_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token ID9=null;
		Token COLON10=null;

		GsTree ID9_tree=null;
		final GsTree COLON10_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:83:8: ( ID COLON )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:83:10: ID COLON
			{
				root_0 = (GsTree)adaptor.nil();

				ID9=(Token)match(input,ID,FOLLOW_ID_in_keyword373); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					ID9_tree = (GsTree)adaptor.create(ID9);
					adaptor.addChild(root_0, ID9_tree);
				}
				COLON10=(Token)match(input,COLON,FOLLOW_COLON_in_keyword375); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					ID9.setType(KEYWORD);
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "keyword"

	public static class methodBody_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "methodBody"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:85:1: methodBody : ( primitive )? temporaries statements ;
	public final GsParser.methodBody_return methodBody() throws RecognitionException {
		final GsParser.methodBody_return retval = new GsParser.methodBody_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.primitive_return primitive11 = null;

		GsParser.temporaries_return temporaries12 = null;

		GsParser.statements_return statements13 = null;



		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:85:11: ( ( primitive )? temporaries statements )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:85:13: ( primitive )? temporaries statements
			{
				root_0 = (GsTree)adaptor.nil();

				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:85:13: ( primitive )?
				int alt3=2;
				final int LA3_0 = input.LA(1);

				if ( (LA3_0==LT) ) {
					alt3=1;
				}
				switch (alt3) {
					case 1 :
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:0:0: primitive
					{
						pushFollow(FOLLOW_primitive_in_methodBody385);
						primitive11=primitive();

						state._fsp--;
						if (state.failed) {
							return retval;
						}
						if ( state.backtracking==0 ) {
							adaptor.addChild(root_0, primitive11.getTree());
						}

					}
					break;

				}

				pushFollow(FOLLOW_temporaries_in_methodBody388);
				temporaries12=temporaries();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, temporaries12.getTree());
				}
				pushFollow(FOLLOW_statements_in_methodBody390);
				statements13=statements();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, statements13.getTree());
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "methodBody"

	public static class primitive_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "primitive"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:87:1: primitive : LT ( primitiveModifier )? ( 'primitive:' INT_LITERAL )? GT -> ^( PRIMITIVE ( primitiveModifier )? ( INT_LITERAL )? ) ;
	public final GsParser.primitive_return primitive() throws RecognitionException {
		final GsParser.primitive_return retval = new GsParser.primitive_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token LT14=null;
		Token string_literal16=null;
		Token INT_LITERAL17=null;
		Token GT18=null;
		GsParser.primitiveModifier_return primitiveModifier15 = null;


		final GsTree LT14_tree=null;
		final GsTree string_literal16_tree=null;
		final GsTree INT_LITERAL17_tree=null;
		final GsTree GT18_tree=null;
		final RewriteRuleTokenStream stream_GT=new RewriteRuleTokenStream(adaptor,"token GT");
		final RewriteRuleTokenStream stream_LT=new RewriteRuleTokenStream(adaptor,"token LT");
		final RewriteRuleTokenStream stream_INT_LITERAL=new RewriteRuleTokenStream(adaptor,"token INT_LITERAL");
		final RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
		final RewriteRuleSubtreeStream stream_primitiveModifier=new RewriteRuleSubtreeStream(adaptor,"rule primitiveModifier");
		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:87:10: ( LT ( primitiveModifier )? ( 'primitive:' INT_LITERAL )? GT -> ^( PRIMITIVE ( primitiveModifier )? ( INT_LITERAL )? ) )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:87:12: LT ( primitiveModifier )? ( 'primitive:' INT_LITERAL )? GT
			{
				LT14=(Token)match(input,LT,FOLLOW_LT_in_primitive397); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_LT.add(LT14);
				}

				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:87:16: ( primitiveModifier )?
				int alt4=2;
				final int LA4_0 = input.LA(1);

				if ( ((LA4_0>=59 && LA4_0<=60)) ) {
					alt4=1;
				}
				switch (alt4) {
					case 1 :
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:0:0: primitiveModifier
					{
						pushFollow(FOLLOW_primitiveModifier_in_primitive400);
						primitiveModifier15=primitiveModifier();

						state._fsp--;
						if (state.failed) {
							return retval;
						}
						if ( state.backtracking==0 ) {
							stream_primitiveModifier.add(primitiveModifier15.getTree());
						}

					}
					break;

				}

				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:87:35: ( 'primitive:' INT_LITERAL )?
				int alt5=2;
				final int LA5_0 = input.LA(1);

				if ( (LA5_0==58) ) {
					alt5=1;
				}
				switch (alt5) {
					case 1 :
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:87:36: 'primitive:' INT_LITERAL
					{
						string_literal16=(Token)match(input,58,FOLLOW_58_in_primitive404); if (state.failed) {
							return retval;
						}
						if ( state.backtracking==0 ) {
							stream_58.add(string_literal16);
						}

						INT_LITERAL17=(Token)match(input,INT_LITERAL,FOLLOW_INT_LITERAL_in_primitive407); if (state.failed) {
							return retval;
						}
						if ( state.backtracking==0 ) {
							stream_INT_LITERAL.add(INT_LITERAL17);
						}


					}
					break;

				}

				GT18=(Token)match(input,GT,FOLLOW_GT_in_primitive412); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_GT.add(GT18);
				}



				// AST REWRITE
				// elements: primitiveModifier, INT_LITERAL
				// token labels:
				// rule labels: retval
				// token list labels:
				// rule list labels:
				if ( state.backtracking==0 ) {
					retval.tree = root_0;
					final RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

					root_0 = (GsTree)adaptor.nil();
					// 87:71: -> ^( PRIMITIVE ( primitiveModifier )? ( INT_LITERAL )? )
					{
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:87:74: ^( PRIMITIVE ( primitiveModifier )? ( INT_LITERAL )? )
						{
							GsTree root_1 = (GsTree)adaptor.nil();
							root_1 = (GsTree)adaptor.becomeRoot(adaptor.create(PRIMITIVE, "PRIMITIVE"), root_1);

							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:87:86: ( primitiveModifier )?
							if ( stream_primitiveModifier.hasNext() ) {
								adaptor.addChild(root_1, stream_primitiveModifier.nextTree());

							}
							stream_primitiveModifier.reset();
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:87:105: ( INT_LITERAL )?
							if ( stream_INT_LITERAL.hasNext() ) {
								adaptor.addChild(root_1, stream_INT_LITERAL.nextNode());

							}
							stream_INT_LITERAL.reset();

							adaptor.addChild(root_0, root_1);
						}

					}

					retval.tree = root_0;}
			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "primitive"

	public static class primitiveModifier_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "primitiveModifier"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:88:1: primitiveModifier : ( 'protected' | 'unprotected' );
	public final GsParser.primitiveModifier_return primitiveModifier() throws RecognitionException {
		final GsParser.primitiveModifier_return retval = new GsParser.primitiveModifier_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token set19=null;

		final GsTree set19_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:88:18: ( 'protected' | 'unprotected' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:
			{
				root_0 = (GsTree)adaptor.nil();

				set19=input.LT(1);
				if ( (input.LA(1)>=59 && input.LA(1)<=60) ) {
					input.consume();
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, adaptor.create(set19));
					}
					state.errorRecovery=false;state.failed=false;
				}
				else {
					if (state.backtracking>0) {state.failed=true; return retval;}
					final MismatchedSetException mse = new MismatchedSetException(null,input);
					throw mse;
				}


			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "primitiveModifier"

	public static class temporaries_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "temporaries"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:90:1: temporaries : ( BAR ( ID )* BAR )? -> ^( TEMPORARIES ( ID )* ) ;
	public final GsParser.temporaries_return temporaries() throws RecognitionException {
		final GsParser.temporaries_return retval = new GsParser.temporaries_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token BAR20=null;
		Token ID21=null;
		Token BAR22=null;

		final GsTree BAR20_tree=null;
		final GsTree ID21_tree=null;
		final GsTree BAR22_tree=null;
		final RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		final RewriteRuleTokenStream stream_BAR=new RewriteRuleTokenStream(adaptor,"token BAR");

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:90:12: ( ( BAR ( ID )* BAR )? -> ^( TEMPORARIES ( ID )* ) )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:90:14: ( BAR ( ID )* BAR )?
			{
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:90:14: ( BAR ( ID )* BAR )?
				int alt7=2;
				final int LA7_0 = input.LA(1);

				if ( (LA7_0==BAR) ) {
					alt7=1;
				}
				switch (alt7) {
					case 1 :
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:90:15: BAR ( ID )* BAR
					{
						BAR20=(Token)match(input,BAR,FOLLOW_BAR_in_temporaries443); if (state.failed) {
							return retval;
						}
						if ( state.backtracking==0 ) {
							stream_BAR.add(BAR20);
						}

						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:90:19: ( ID )*
						loop6:
							do {
								int alt6=2;
								final int LA6_0 = input.LA(1);

								if ( (LA6_0==ID) ) {
									alt6=1;
								}


								switch (alt6) {
									case 1 :
										// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:0:0: ID
										{
											ID21=(Token)match(input,ID,FOLLOW_ID_in_temporaries445); if (state.failed) {
												return retval;
											}
											if ( state.backtracking==0 ) {
												stream_ID.add(ID21);
											}


										}
										break;

									default :
										break loop6;
								}
							} while (true);

						BAR22=(Token)match(input,BAR,FOLLOW_BAR_in_temporaries448); if (state.failed) {
							return retval;
						}
						if ( state.backtracking==0 ) {
							stream_BAR.add(BAR22);
						}


					}
					break;

				}



				// AST REWRITE
				// elements: ID
				// token labels:
				// rule labels: retval
				// token list labels:
				// rule list labels:
				if ( state.backtracking==0 ) {
					retval.tree = root_0;
					final RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

					root_0 = (GsTree)adaptor.nil();
					// 90:43: -> ^( TEMPORARIES ( ID )* )
					{
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:90:46: ^( TEMPORARIES ( ID )* )
						{
							GsTree root_1 = (GsTree)adaptor.nil();
							root_1 = (GsTree)adaptor.becomeRoot(adaptor.create(TEMPORARIES, "TEMPORARIES"), root_1);

							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:90:60: ( ID )*
							while ( stream_ID.hasNext() ) {
								adaptor.addChild(root_1, stream_ID.nextNode());

							}
							stream_ID.reset();

							adaptor.addChild(root_0, root_1);
						}

					}

					retval.tree = root_0;}
			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "temporaries"

	public static class statements_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "statements"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:92:1: statements : ( ( statement ( DOT statement )* ( DOT finalStatement )? ( DOT )? )? -> ^( STATEMENTS ( statement )* ( finalStatement )? ) | finalStatement ( DOT )? );
	public final GsParser.statements_return statements() throws RecognitionException {
		final GsParser.statements_return retval = new GsParser.statements_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token DOT24=null;
		Token DOT26=null;
		Token DOT28=null;
		Token DOT30=null;
		GsParser.statement_return statement23 = null;

		GsParser.statement_return statement25 = null;

		GsParser.finalStatement_return finalStatement27 = null;

		GsParser.finalStatement_return finalStatement29 = null;


		final GsTree DOT24_tree=null;
		final GsTree DOT26_tree=null;
		final GsTree DOT28_tree=null;
		final GsTree DOT30_tree=null;
		final RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
		final RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
		final RewriteRuleSubtreeStream stream_finalStatement=new RewriteRuleSubtreeStream(adaptor,"rule finalStatement");
		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:93:3: ( ( statement ( DOT statement )* ( DOT finalStatement )? ( DOT )? )? -> ^( STATEMENTS ( statement )* ( finalStatement )? ) | finalStatement ( DOT )? )
			int alt13=2;
			final int LA13_0 = input.LA(1);

			if ( (LA13_0==EOF||(LA13_0>=INT_LITERAL && LA13_0<=ID)||(LA13_0>=NIL && LA13_0<=LPAREN)||LA13_0==LBRACKET||(LA13_0>=RBRACKET && LA13_0<=LBRACE)||LA13_0==61) ) {
				alt13=1;
			}
			else if ( (LA13_0==RETURN) ) {
				alt13=2;
			}
			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				final NoViableAltException nvae =
					new NoViableAltException("", 13, 0, input);

				throw nvae;
			}
			switch (alt13) {
				case 1 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:93:5: ( statement ( DOT statement )* ( DOT finalStatement )? ( DOT )? )?
				{
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:93:5: ( statement ( DOT statement )* ( DOT finalStatement )? ( DOT )? )?
					int alt11=2;
					final int LA11_0 = input.LA(1);

					if ( ((LA11_0>=INT_LITERAL && LA11_0<=ID)||(LA11_0>=NIL && LA11_0<=LPAREN)||LA11_0==LBRACKET||LA11_0==LBRACE||LA11_0==61) ) {
						alt11=1;
					}
					switch (alt11) {
						case 1 :
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:93:6: statement ( DOT statement )* ( DOT finalStatement )? ( DOT )?
						{
							pushFollow(FOLLOW_statement_in_statements484);
							statement23=statement();

							state._fsp--;
							if (state.failed) {
								return retval;
							}
							if ( state.backtracking==0 ) {
								stream_statement.add(statement23.getTree());
							}
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:93:16: ( DOT statement )*
							loop8:
								do {
									int alt8=2;
									final int LA8_0 = input.LA(1);

									if ( (LA8_0==DOT) ) {
										final int LA8_1 = input.LA(2);

										if ( ((LA8_1>=INT_LITERAL && LA8_1<=ID)||(LA8_1>=NIL && LA8_1<=LPAREN)||LA8_1==LBRACKET||LA8_1==LBRACE||LA8_1==61) ) {
											alt8=1;
										}


									}


									switch (alt8) {
										case 1 :
											// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:93:17: DOT statement
											{
												DOT24=(Token)match(input,DOT,FOLLOW_DOT_in_statements487); if (state.failed) {
													return retval;
												}
												if ( state.backtracking==0 ) {
													stream_DOT.add(DOT24);
												}

												pushFollow(FOLLOW_statement_in_statements490);
												statement25=statement();

												state._fsp--;
												if (state.failed) {
													return retval;
												}
												if ( state.backtracking==0 ) {
													stream_statement.add(statement25.getTree());
												}

											}
											break;

										default :
											break loop8;
									}
								} while (true);

							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:93:34: ( DOT finalStatement )?
							int alt9=2;
							final int LA9_0 = input.LA(1);

							if ( (LA9_0==DOT) ) {
								final int LA9_1 = input.LA(2);

								if ( (LA9_1==RETURN) ) {
									alt9=1;
								}
							}
							switch (alt9) {
								case 1 :
									// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:93:35: DOT finalStatement
									{
										DOT26=(Token)match(input,DOT,FOLLOW_DOT_in_statements495); if (state.failed) {
											return retval;
										}
										if ( state.backtracking==0 ) {
											stream_DOT.add(DOT26);
										}

										pushFollow(FOLLOW_finalStatement_in_statements498);
										finalStatement27=finalStatement();

										state._fsp--;
										if (state.failed) {
											return retval;
										}
										if ( state.backtracking==0 ) {
											stream_finalStatement.add(finalStatement27.getTree());
										}

									}
									break;

							}

							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:93:57: ( DOT )?
							int alt10=2;
							final int LA10_0 = input.LA(1);

							if ( (LA10_0==DOT) ) {
								alt10=1;
							}
							switch (alt10) {
								case 1 :
									// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:93:58: DOT
									{
										DOT28=(Token)match(input,DOT,FOLLOW_DOT_in_statements503); if (state.failed) {
											return retval;
										}
										if ( state.backtracking==0 ) {
											stream_DOT.add(DOT28);
										}


									}
									break;

							}


						}
						break;

					}



					// AST REWRITE
					// elements: finalStatement, statement
					// token labels:
					// rule labels: retval
					// token list labels:
					// rule list labels:
					if ( state.backtracking==0 ) {
						retval.tree = root_0;
						final RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

						root_0 = (GsTree)adaptor.nil();
						// 93:69: -> ^( STATEMENTS ( statement )* ( finalStatement )? )
						{
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:93:72: ^( STATEMENTS ( statement )* ( finalStatement )? )
							{
								GsTree root_1 = (GsTree)adaptor.nil();
								root_1 = (GsTree)adaptor.becomeRoot(adaptor.create(STATEMENTS, "STATEMENTS"), root_1);

								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:93:85: ( statement )*
								while ( stream_statement.hasNext() ) {
									adaptor.addChild(root_1, stream_statement.nextTree());

								}
								stream_statement.reset();
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:93:96: ( finalStatement )?
								if ( stream_finalStatement.hasNext() ) {
									adaptor.addChild(root_1, stream_finalStatement.nextTree());

								}
								stream_finalStatement.reset();

								adaptor.addChild(root_0, root_1);
							}

						}

						retval.tree = root_0;}
				}
				break;
				case 2 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:94:5: finalStatement ( DOT )?
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_finalStatement_in_statements528);
					finalStatement29=finalStatement();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, finalStatement29.getTree());
					}
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:94:19: ( DOT )?
					int alt12=2;
					final int LA12_0 = input.LA(1);

					if ( (LA12_0==DOT) ) {
						alt12=1;
					}
					switch (alt12) {
						case 1 :
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:94:20: DOT
						{
							DOT30=(Token)match(input,DOT,FOLLOW_DOT_in_statements530); if (state.failed) {
								return retval;
							}

						}
						break;

					}


				}
				break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "statements"

	public static class statement_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "statement"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:97:1: statement : ( assignment )* expr ;
	public final GsParser.statement_return statement() throws RecognitionException {
		final GsParser.statement_return retval = new GsParser.statement_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.assignment_return assignment31 = null;

		GsParser.expr_return expr32 = null;



		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:97:10: ( ( assignment )* expr )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:97:12: ( assignment )* expr
			{
				root_0 = (GsTree)adaptor.nil();

				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:97:12: ( assignment )*
				loop14:
					do {
						int alt14=2;
						final int LA14_0 = input.LA(1);

						if ( (LA14_0==ID) ) {
							final int LA14_2 = input.LA(2);

							if ( (LA14_2==ASSIGN) ) {
								alt14=1;
							}


						}


						switch (alt14) {
							case 1 :
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:97:13: assignment
								{
									pushFollow(FOLLOW_assignment_in_statement586);
									assignment31=assignment();

									state._fsp--;
									if (state.failed) {
										return retval;
									}
									if ( state.backtracking==0 ) {
										adaptor.addChild(root_0, assignment31.getTree());
									}

								}
								break;

							default :
								break loop14;
						}
					} while (true);

				pushFollow(FOLLOW_expr_in_statement590);
				expr32=expr();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, expr32.getTree());
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "statement"

	public static class finalStatement_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "finalStatement"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:98:1: finalStatement : RETURN statement ;
	public final GsParser.finalStatement_return finalStatement() throws RecognitionException {
		final GsParser.finalStatement_return retval = new GsParser.finalStatement_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token RETURN33=null;
		GsParser.statement_return statement34 = null;


		GsTree RETURN33_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:98:15: ( RETURN statement )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:98:17: RETURN statement
			{
				root_0 = (GsTree)adaptor.nil();

				RETURN33=(Token)match(input,RETURN,FOLLOW_RETURN_in_finalStatement596); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					RETURN33_tree = (GsTree)adaptor.create(RETURN33);
					root_0 = (GsTree)adaptor.becomeRoot(RETURN33_tree, root_0);
				}
				pushFollow(FOLLOW_statement_in_finalStatement599);
				statement34=statement();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, statement34.getTree());
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "finalStatement"

	public static class assignment_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "assignment"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:100:1: assignment : ID ASSIGN ;
	public final GsParser.assignment_return assignment() throws RecognitionException {
		final GsParser.assignment_return retval = new GsParser.assignment_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token ID35=null;
		Token ASSIGN36=null;

		GsTree ID35_tree=null;
		GsTree ASSIGN36_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:101:3: ( ID ASSIGN )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:101:5: ID ASSIGN
			{
				root_0 = (GsTree)adaptor.nil();

				ID35=(Token)match(input,ID,FOLLOW_ID_in_assignment609); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					ID35_tree = (GsTree)adaptor.create(ID35);
					adaptor.addChild(root_0, ID35_tree);
				}
				ASSIGN36=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_assignment611); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					ASSIGN36_tree = (GsTree)adaptor.create(ASSIGN36);
					root_0 = (GsTree)adaptor.becomeRoot(ASSIGN36_tree, root_0);
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "assignment"

	public static class expr_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "expr"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:104:1: expr : operand ( messageChain ( cascadedMessage )* )? -> ^( EXPR operand ( messageChain ( cascadedMessage )* )? ) ;
	public final GsParser.expr_return expr() throws RecognitionException {
		final GsParser.expr_return retval = new GsParser.expr_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.operand_return operand37 = null;

		GsParser.messageChain_return messageChain38 = null;

		GsParser.cascadedMessage_return cascadedMessage39 = null;


		final RewriteRuleSubtreeStream stream_messageChain=new RewriteRuleSubtreeStream(adaptor,"rule messageChain");
		final RewriteRuleSubtreeStream stream_cascadedMessage=new RewriteRuleSubtreeStream(adaptor,"rule cascadedMessage");
		final RewriteRuleSubtreeStream stream_operand=new RewriteRuleSubtreeStream(adaptor,"rule operand");
		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:104:5: ( operand ( messageChain ( cascadedMessage )* )? -> ^( EXPR operand ( messageChain ( cascadedMessage )* )? ) )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:104:7: operand ( messageChain ( cascadedMessage )* )?
			{
				pushFollow(FOLLOW_operand_in_expr622);
				operand37=operand();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_operand.add(operand37.getTree());
				}
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:104:15: ( messageChain ( cascadedMessage )* )?
				int alt16=2;
				final int LA16_0 = input.LA(1);

				if ( (LA16_0==ID||(LA16_0>=LT && LA16_0<=BAR)||LA16_0==MINUS||LA16_0==COMMA||(LA16_0>=AMPERSAND && LA16_0<=PLUS)) ) {
					alt16=1;
				}
				switch (alt16) {
					case 1 :
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:104:16: messageChain ( cascadedMessage )*
					{
						pushFollow(FOLLOW_messageChain_in_expr625);
						messageChain38=messageChain();

						state._fsp--;
						if (state.failed) {
							return retval;
						}
						if ( state.backtracking==0 ) {
							stream_messageChain.add(messageChain38.getTree());
						}
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:104:29: ( cascadedMessage )*
						loop15:
							do {
								int alt15=2;
								final int LA15_0 = input.LA(1);

								if ( (LA15_0==62) ) {
									alt15=1;
								}


								switch (alt15) {
									case 1 :
										// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:104:30: cascadedMessage
										{
											pushFollow(FOLLOW_cascadedMessage_in_expr628);
											cascadedMessage39=cascadedMessage();

											state._fsp--;
											if (state.failed) {
												return retval;
											}
											if ( state.backtracking==0 ) {
												stream_cascadedMessage.add(cascadedMessage39.getTree());
											}

										}
										break;

									default :
										break loop15;
								}
							} while (true);


					}
					break;

				}



				// AST REWRITE
				// elements: cascadedMessage, operand, messageChain
				// token labels:
				// rule labels: retval
				// token list labels:
				// rule list labels:
				if ( state.backtracking==0 ) {
					retval.tree = root_0;
					final RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

					root_0 = (GsTree)adaptor.nil();
					// 104:50: -> ^( EXPR operand ( messageChain ( cascadedMessage )* )? )
					{
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:104:53: ^( EXPR operand ( messageChain ( cascadedMessage )* )? )
						{
							GsTree root_1 = (GsTree)adaptor.nil();
							root_1 = (GsTree)adaptor.becomeRoot(adaptor.create(EXPR, "EXPR"), root_1);

							adaptor.addChild(root_1, stream_operand.nextTree());
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:104:68: ( messageChain ( cascadedMessage )* )?
							if ( stream_cascadedMessage.hasNext()||stream_messageChain.hasNext() ) {
								adaptor.addChild(root_1, stream_messageChain.nextTree());
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:104:82: ( cascadedMessage )*
								while ( stream_cascadedMessage.hasNext() ) {
									adaptor.addChild(root_1, stream_cascadedMessage.nextTree());

								}
								stream_cascadedMessage.reset();

							}
							stream_cascadedMessage.reset();
							stream_messageChain.reset();

							adaptor.addChild(root_0, root_1);
						}

					}

					retval.tree = root_0;}
			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "expr"

	public static class operand_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "operand"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:105:1: operand : ( literal | ID | nestedExpr | arrayBuilder );
	public final GsParser.operand_return operand() throws RecognitionException {
		final GsParser.operand_return retval = new GsParser.operand_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token ID41=null;
		GsParser.literal_return literal40 = null;

		GsParser.nestedExpr_return nestedExpr42 = null;

		GsParser.arrayBuilder_return arrayBuilder43 = null;


		GsTree ID41_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:106:3: ( literal | ID | nestedExpr | arrayBuilder )
			int alt17=4;
			switch ( input.LA(1) ) {
				case INT_LITERAL:
				case FLOAT_LITERAL:
				case NIL:
				case TRUE:
				case FALSE:
				case SELF:
				case SUPER:
				case THIS_CONTEXT:
				case MINUS:
				case CHARACTER_LITERAL:
				case STRING_LITERAL:
				case SYMBOL_LITERAL:
				case LBRACKET:
				case LBRACE:
				{
					alt17=1;
				}
				break;
				case 61:
				{
					final int LA17_2 = input.LA(2);

					if ( (LA17_2==LBRACKET) ) {
						alt17=4;
					}
					else if ( (LA17_2==LPAREN) ) {
						alt17=1;
					}
					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						final NoViableAltException nvae =
							new NoViableAltException("", 17, 2, input);

						throw nvae;
					}
				}
				break;
				case ID:
				{
					alt17=2;
				}
				break;
				case LPAREN:
				{
					alt17=3;
				}
				break;
				default:
					if (state.backtracking>0) {state.failed=true; return retval;}
					final NoViableAltException nvae =
						new NoViableAltException("", 17, 0, input);

					throw nvae;
			}

			switch (alt17) {
				case 1 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:106:5: literal
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_literal_in_operand659);
					literal40=literal();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, literal40.getTree());
					}

				}
				break;
				case 2 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:107:5: ID
				{
					root_0 = (GsTree)adaptor.nil();

					ID41=(Token)match(input,ID,FOLLOW_ID_in_operand665); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						ID41_tree = (GsTree)adaptor.create(ID41);
						adaptor.addChild(root_0, ID41_tree);
					}

				}
				break;
				case 3 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:108:5: nestedExpr
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_nestedExpr_in_operand671);
					nestedExpr42=nestedExpr();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, nestedExpr42.getTree());
					}

				}
				break;
				case 4 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:109:5: arrayBuilder
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_arrayBuilder_in_operand678);
					arrayBuilder43=arrayBuilder();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, arrayBuilder43.getTree());
					}

				}
				break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "operand"

	public static class literal_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "literal"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:112:1: literal : ( ( NIL | TRUE | FALSE | SELF | SUPER | THIS_CONTEXT ) | ( MINUS )? INT_LITERAL | ( MINUS )? FLOAT_LITERAL | CHARACTER_LITERAL | STRING_LITERAL | SYMBOL_LITERAL | arrayLiteral | block | selectionBlock );
	public final GsParser.literal_return literal() throws RecognitionException {
		final GsParser.literal_return retval = new GsParser.literal_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token set44=null;
		Token MINUS45=null;
		Token INT_LITERAL46=null;
		Token MINUS47=null;
		Token FLOAT_LITERAL48=null;
		Token CHARACTER_LITERAL49=null;
		Token STRING_LITERAL50=null;
		Token SYMBOL_LITERAL51=null;
		GsParser.arrayLiteral_return arrayLiteral52 = null;

		GsParser.block_return block53 = null;

		GsParser.selectionBlock_return selectionBlock54 = null;


		final GsTree set44_tree=null;
		GsTree MINUS45_tree=null;
		GsTree INT_LITERAL46_tree=null;
		GsTree MINUS47_tree=null;
		GsTree FLOAT_LITERAL48_tree=null;
		GsTree CHARACTER_LITERAL49_tree=null;
		GsTree STRING_LITERAL50_tree=null;
		GsTree SYMBOL_LITERAL51_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:113:3: ( ( NIL | TRUE | FALSE | SELF | SUPER | THIS_CONTEXT ) | ( MINUS )? INT_LITERAL | ( MINUS )? FLOAT_LITERAL | CHARACTER_LITERAL | STRING_LITERAL | SYMBOL_LITERAL | arrayLiteral | block | selectionBlock )
			int alt20=9;
			alt20 = dfa20.predict(input);
			switch (alt20) {
				case 1 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:113:5: ( NIL | TRUE | FALSE | SELF | SUPER | THIS_CONTEXT )
				{
					root_0 = (GsTree)adaptor.nil();

					set44=input.LT(1);
					if ( (input.LA(1)>=NIL && input.LA(1)<=THIS_CONTEXT) ) {
						input.consume();
						if ( state.backtracking==0 ) {
							adaptor.addChild(root_0, adaptor.create(set44));
						}
						state.errorRecovery=false;state.failed=false;
					}
					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						final MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}


				}
				break;
				case 2 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:114:5: ( MINUS )? INT_LITERAL
				{
					root_0 = (GsTree)adaptor.nil();

					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:114:10: ( MINUS )?
					int alt18=2;
					final int LA18_0 = input.LA(1);

					if ( (LA18_0==MINUS) ) {
						alt18=1;
					}
					switch (alt18) {
						case 1 :
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:0:0: MINUS
						{
							MINUS45=(Token)match(input,MINUS,FOLLOW_MINUS_in_literal712); if (state.failed) {
								return retval;
							}
							if ( state.backtracking==0 ) {
								MINUS45_tree = (GsTree)adaptor.create(MINUS45);
								root_0 = (GsTree)adaptor.becomeRoot(MINUS45_tree, root_0);
							}

						}
						break;

					}

					INT_LITERAL46=(Token)match(input,INT_LITERAL,FOLLOW_INT_LITERAL_in_literal716); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						INT_LITERAL46_tree = (GsTree)adaptor.create(INT_LITERAL46);
						adaptor.addChild(root_0, INT_LITERAL46_tree);
					}

				}
				break;
				case 3 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:115:5: ( MINUS )? FLOAT_LITERAL
				{
					root_0 = (GsTree)adaptor.nil();

					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:115:10: ( MINUS )?
					int alt19=2;
					final int LA19_0 = input.LA(1);

					if ( (LA19_0==MINUS) ) {
						alt19=1;
					}
					switch (alt19) {
						case 1 :
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:0:0: MINUS
						{
							MINUS47=(Token)match(input,MINUS,FOLLOW_MINUS_in_literal722); if (state.failed) {
								return retval;
							}
							if ( state.backtracking==0 ) {
								MINUS47_tree = (GsTree)adaptor.create(MINUS47);
								root_0 = (GsTree)adaptor.becomeRoot(MINUS47_tree, root_0);
							}

						}
						break;

					}

					FLOAT_LITERAL48=(Token)match(input,FLOAT_LITERAL,FOLLOW_FLOAT_LITERAL_in_literal726); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						FLOAT_LITERAL48_tree = (GsTree)adaptor.create(FLOAT_LITERAL48);
						adaptor.addChild(root_0, FLOAT_LITERAL48_tree);
					}

				}
				break;
				case 4 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:116:5: CHARACTER_LITERAL
				{
					root_0 = (GsTree)adaptor.nil();

					CHARACTER_LITERAL49=(Token)match(input,CHARACTER_LITERAL,FOLLOW_CHARACTER_LITERAL_in_literal732); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						CHARACTER_LITERAL49_tree = (GsTree)adaptor.create(CHARACTER_LITERAL49);
						adaptor.addChild(root_0, CHARACTER_LITERAL49_tree);
					}

				}
				break;
				case 5 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:117:5: STRING_LITERAL
				{
					root_0 = (GsTree)adaptor.nil();

					STRING_LITERAL50=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_literal738); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						STRING_LITERAL50_tree = (GsTree)adaptor.create(STRING_LITERAL50);
						adaptor.addChild(root_0, STRING_LITERAL50_tree);
					}

				}
				break;
				case 6 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:118:5: SYMBOL_LITERAL
				{
					root_0 = (GsTree)adaptor.nil();

					SYMBOL_LITERAL51=(Token)match(input,SYMBOL_LITERAL,FOLLOW_SYMBOL_LITERAL_in_literal744); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						SYMBOL_LITERAL51_tree = (GsTree)adaptor.create(SYMBOL_LITERAL51);
						adaptor.addChild(root_0, SYMBOL_LITERAL51_tree);
					}

				}
				break;
				case 7 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:119:5: arrayLiteral
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_arrayLiteral_in_literal750);
					arrayLiteral52=arrayLiteral();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, arrayLiteral52.getTree());
					}

				}
				break;
				case 8 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:120:5: block
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_block_in_literal756);
					block53=block();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, block53.getTree());
					}

				}
				break;
				case 9 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:121:5: selectionBlock
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_selectionBlock_in_literal762);
					selectionBlock54=selectionBlock();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, selectionBlock54.getTree());
					}

				}
				break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "literal"

	public static class arrayLiteral_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "arrayLiteral"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:125:1: arrayLiteral : '#' array ;
	public final GsParser.arrayLiteral_return arrayLiteral() throws RecognitionException {
		final GsParser.arrayLiteral_return retval = new GsParser.arrayLiteral_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token char_literal55=null;
		GsParser.array_return array56 = null;


		final GsTree char_literal55_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:125:13: ( '#' array )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:125:15: '#' array
			{
				root_0 = (GsTree)adaptor.nil();

				char_literal55=(Token)match(input,61,FOLLOW_61_in_arrayLiteral775); if (state.failed) {
					return retval;
				}
				pushFollow(FOLLOW_array_in_arrayLiteral778);
				array56=array();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, array56.getTree());
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "arrayLiteral"

	public static class array_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "array"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:126:1: array : LPAREN ( arrayItem )* RPAREN -> ^( ARRAY ( arrayItem )* ) ;
	public final GsParser.array_return array() throws RecognitionException {
		final GsParser.array_return retval = new GsParser.array_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token LPAREN57=null;
		Token RPAREN59=null;
		GsParser.arrayItem_return arrayItem58 = null;


		final GsTree LPAREN57_tree=null;
		final GsTree RPAREN59_tree=null;
		final RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
		final RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
		final RewriteRuleSubtreeStream stream_arrayItem=new RewriteRuleSubtreeStream(adaptor,"rule arrayItem");
		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:126:6: ( LPAREN ( arrayItem )* RPAREN -> ^( ARRAY ( arrayItem )* ) )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:126:8: LPAREN ( arrayItem )* RPAREN
			{
				LPAREN57=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_array784); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_LPAREN.add(LPAREN57);
				}

				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:126:16: ( arrayItem )*
				loop21:
					do {
						int alt21=2;
						final int LA21_0 = input.LA(1);

						if ( (LA21_0==INT_LITERAL||LA21_0==ID||(LA21_0>=NIL && LA21_0<=LPAREN)||LA21_0==61) ) {
							alt21=1;
						}


						switch (alt21) {
							case 1 :
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:126:17: arrayItem
							{
								pushFollow(FOLLOW_arrayItem_in_array788);
								arrayItem58=arrayItem();

								state._fsp--;
								if (state.failed) {
									return retval;
								}
								if ( state.backtracking==0 ) {
									stream_arrayItem.add(arrayItem58.getTree());
								}

							}
							break;

							default :
								break loop21;
						}
					} while (true);

				RPAREN59=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_array792); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_RPAREN.add(RPAREN59);
				}



				// AST REWRITE
				// elements: arrayItem
				// token labels:
				// rule labels: retval
				// token list labels:
				// rule list labels:
				if ( state.backtracking==0 ) {
					retval.tree = root_0;
					final RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

					root_0 = (GsTree)adaptor.nil();
					// 126:41: -> ^( ARRAY ( arrayItem )* )
					{
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:126:44: ^( ARRAY ( arrayItem )* )
						{
							GsTree root_1 = (GsTree)adaptor.nil();
							root_1 = (GsTree)adaptor.becomeRoot(adaptor.create(ARRAY, "ARRAY"), root_1);

							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:126:52: ( arrayItem )*
							while ( stream_arrayItem.hasNext() ) {
								adaptor.addChild(root_1, stream_arrayItem.nextTree());

							}
							stream_arrayItem.reset();

							adaptor.addChild(root_0, root_1);
						}

					}

					retval.tree = root_0;}
			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "array"

	public static class arrayItem_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "arrayItem"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:127:1: arrayItem : ( array | arrayLiteral | ( MINUS )? INT_LITERAL | ID | SYMBOL_LITERAL | STRING_LITERAL | CHARACTER_LITERAL | ( NIL | TRUE | FALSE | SELF | SUPER | THIS_CONTEXT ) );
	public final GsParser.arrayItem_return arrayItem() throws RecognitionException {
		final GsParser.arrayItem_return retval = new GsParser.arrayItem_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token MINUS62=null;
		Token INT_LITERAL63=null;
		Token ID64=null;
		Token SYMBOL_LITERAL65=null;
		Token STRING_LITERAL66=null;
		Token CHARACTER_LITERAL67=null;
		Token set68=null;
		GsParser.array_return array60 = null;

		GsParser.arrayLiteral_return arrayLiteral61 = null;


		GsTree MINUS62_tree=null;
		GsTree INT_LITERAL63_tree=null;
		GsTree ID64_tree=null;
		GsTree SYMBOL_LITERAL65_tree=null;
		GsTree STRING_LITERAL66_tree=null;
		GsTree CHARACTER_LITERAL67_tree=null;
		final GsTree set68_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:128:3: ( array | arrayLiteral | ( MINUS )? INT_LITERAL | ID | SYMBOL_LITERAL | STRING_LITERAL | CHARACTER_LITERAL | ( NIL | TRUE | FALSE | SELF | SUPER | THIS_CONTEXT ) )
			int alt23=8;
			switch ( input.LA(1) ) {
				case LPAREN:
				{
					alt23=1;
				}
				break;
				case 61:
				{
					alt23=2;
				}
				break;
				case INT_LITERAL:
				case MINUS:
				{
					alt23=3;
				}
				break;
				case ID:
				{
					alt23=4;
				}
				break;
				case SYMBOL_LITERAL:
				{
					alt23=5;
				}
				break;
				case STRING_LITERAL:
				{
					alt23=6;
				}
				break;
				case CHARACTER_LITERAL:
				{
					alt23=7;
				}
				break;
				case NIL:
				case TRUE:
				case FALSE:
				case SELF:
				case SUPER:
				case THIS_CONTEXT:
				{
					alt23=8;
				}
				break;
				default:
					if (state.backtracking>0) {state.failed=true; return retval;}
					final NoViableAltException nvae =
						new NoViableAltException("", 23, 0, input);

					throw nvae;
			}

			switch (alt23) {
				case 1 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:128:5: array
					{
						root_0 = (GsTree)adaptor.nil();

						pushFollow(FOLLOW_array_in_arrayItem815);
						array60=array();

						state._fsp--;
						if (state.failed) {
							return retval;
						}
						if ( state.backtracking==0 ) {
							adaptor.addChild(root_0, array60.getTree());
						}

					}
					break;
				case 2 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:129:5: arrayLiteral
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_arrayLiteral_in_arrayItem821);
					arrayLiteral61=arrayLiteral();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, arrayLiteral61.getTree());
					}

				}
				break;
				case 3 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:130:5: ( MINUS )? INT_LITERAL
				{
					root_0 = (GsTree)adaptor.nil();

					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:130:10: ( MINUS )?
					int alt22=2;
					final int LA22_0 = input.LA(1);

					if ( (LA22_0==MINUS) ) {
						alt22=1;
					}
					switch (alt22) {
						case 1 :
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:0:0: MINUS
						{
							MINUS62=(Token)match(input,MINUS,FOLLOW_MINUS_in_arrayItem827); if (state.failed) {
								return retval;
							}
							if ( state.backtracking==0 ) {
								MINUS62_tree = (GsTree)adaptor.create(MINUS62);
								root_0 = (GsTree)adaptor.becomeRoot(MINUS62_tree, root_0);
							}

						}
						break;

					}

					INT_LITERAL63=(Token)match(input,INT_LITERAL,FOLLOW_INT_LITERAL_in_arrayItem831); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						INT_LITERAL63_tree = (GsTree)adaptor.create(INT_LITERAL63);
						adaptor.addChild(root_0, INT_LITERAL63_tree);
					}

				}
				break;
				case 4 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:131:5: ID
				{
					root_0 = (GsTree)adaptor.nil();

					ID64=(Token)match(input,ID,FOLLOW_ID_in_arrayItem837); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						ID64_tree = (GsTree)adaptor.create(ID64);
						adaptor.addChild(root_0, ID64_tree);
					}

				}
				break;
				case 5 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:132:5: SYMBOL_LITERAL
				{
					root_0 = (GsTree)adaptor.nil();

					SYMBOL_LITERAL65=(Token)match(input,SYMBOL_LITERAL,FOLLOW_SYMBOL_LITERAL_in_arrayItem843); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						SYMBOL_LITERAL65_tree = (GsTree)adaptor.create(SYMBOL_LITERAL65);
						adaptor.addChild(root_0, SYMBOL_LITERAL65_tree);
					}

				}
				break;
				case 6 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:133:5: STRING_LITERAL
				{
					root_0 = (GsTree)adaptor.nil();

					STRING_LITERAL66=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_arrayItem849); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						STRING_LITERAL66_tree = (GsTree)adaptor.create(STRING_LITERAL66);
						adaptor.addChild(root_0, STRING_LITERAL66_tree);
					}

				}
				break;
				case 7 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:134:5: CHARACTER_LITERAL
				{
					root_0 = (GsTree)adaptor.nil();

					CHARACTER_LITERAL67=(Token)match(input,CHARACTER_LITERAL,FOLLOW_CHARACTER_LITERAL_in_arrayItem855); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						CHARACTER_LITERAL67_tree = (GsTree)adaptor.create(CHARACTER_LITERAL67);
						adaptor.addChild(root_0, CHARACTER_LITERAL67_tree);
					}

				}
				break;
				case 8 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:135:5: ( NIL | TRUE | FALSE | SELF | SUPER | THIS_CONTEXT )
				{
					root_0 = (GsTree)adaptor.nil();

					set68=input.LT(1);
					if ( (input.LA(1)>=NIL && input.LA(1)<=THIS_CONTEXT) ) {
						input.consume();
						if ( state.backtracking==0 ) {
							adaptor.addChild(root_0, adaptor.create(set68));
						}
						state.errorRecovery=false;state.failed=false;
					}
					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						final MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}


				}
				break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "arrayItem"

	public static class arrayBuilder_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "arrayBuilder"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:138:1: arrayBuilder : '#' LBRACKET ( exprA ( COMMA exprA )* )? ( COMMA )? RBRACKET -> ^( ARRAY ( exprA )* ) ;
	public final GsParser.arrayBuilder_return arrayBuilder() throws RecognitionException {
		final GsParser.arrayBuilder_return retval = new GsParser.arrayBuilder_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token char_literal69=null;
		Token LBRACKET70=null;
		Token COMMA72=null;
		Token COMMA74=null;
		Token RBRACKET75=null;
		GsParser.exprA_return exprA71 = null;

		GsParser.exprA_return exprA73 = null;


		final GsTree char_literal69_tree=null;
		final GsTree LBRACKET70_tree=null;
		final GsTree COMMA72_tree=null;
		final GsTree COMMA74_tree=null;
		final GsTree RBRACKET75_tree=null;
		final RewriteRuleTokenStream stream_LBRACKET=new RewriteRuleTokenStream(adaptor,"token LBRACKET");
		final RewriteRuleTokenStream stream_RBRACKET=new RewriteRuleTokenStream(adaptor,"token RBRACKET");
		final RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
		final RewriteRuleTokenStream stream_61=new RewriteRuleTokenStream(adaptor,"token 61");
		final RewriteRuleSubtreeStream stream_exprA=new RewriteRuleSubtreeStream(adaptor,"rule exprA");
		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:138:13: ( '#' LBRACKET ( exprA ( COMMA exprA )* )? ( COMMA )? RBRACKET -> ^( ARRAY ( exprA )* ) )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:138:15: '#' LBRACKET ( exprA ( COMMA exprA )* )? ( COMMA )? RBRACKET
			{
				char_literal69=(Token)match(input,61,FOLLOW_61_in_arrayBuilder885); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_61.add(char_literal69);
				}

				LBRACKET70=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_arrayBuilder887); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_LBRACKET.add(LBRACKET70);
				}

				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:138:28: ( exprA ( COMMA exprA )* )?
				int alt25=2;
				final int LA25_0 = input.LA(1);

				if ( ((LA25_0>=INT_LITERAL && LA25_0<=ID)||(LA25_0>=NIL && LA25_0<=LPAREN)||LA25_0==LBRACKET||LA25_0==LBRACE||LA25_0==61) ) {
					alt25=1;
				}
				switch (alt25) {
					case 1 :
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:138:29: exprA ( COMMA exprA )*
					{
						pushFollow(FOLLOW_exprA_in_arrayBuilder890);
						exprA71=exprA();

						state._fsp--;
						if (state.failed) {
							return retval;
						}
						if ( state.backtracking==0 ) {
							stream_exprA.add(exprA71.getTree());
						}
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:138:35: ( COMMA exprA )*
						loop24:
							do {
								int alt24=2;
								final int LA24_0 = input.LA(1);

								if ( (LA24_0==COMMA) ) {
									final int LA24_1 = input.LA(2);

									if ( ((LA24_1>=INT_LITERAL && LA24_1<=ID)||(LA24_1>=NIL && LA24_1<=LPAREN)||LA24_1==LBRACKET||LA24_1==LBRACE||LA24_1==61) ) {
										alt24=1;
									}


								}


								switch (alt24) {
									case 1 :
										// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:138:36: COMMA exprA
										{
											COMMA72=(Token)match(input,COMMA,FOLLOW_COMMA_in_arrayBuilder893); if (state.failed) {
												return retval;
											}
											if ( state.backtracking==0 ) {
												stream_COMMA.add(COMMA72);
											}

											pushFollow(FOLLOW_exprA_in_arrayBuilder895);
											exprA73=exprA();

											state._fsp--;
											if (state.failed) {
												return retval;
											}
											if ( state.backtracking==0 ) {
												stream_exprA.add(exprA73.getTree());
											}

										}
										break;

									default :
										break loop24;
								}
							} while (true);


					}
					break;

				}

				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:138:52: ( COMMA )?
				int alt26=2;
				final int LA26_0 = input.LA(1);

				if ( (LA26_0==COMMA) ) {
					alt26=1;
				}
				switch (alt26) {
					case 1 :
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:0:0: COMMA
					{
						COMMA74=(Token)match(input,COMMA,FOLLOW_COMMA_in_arrayBuilder901); if (state.failed) {
							return retval;
						}
						if ( state.backtracking==0 ) {
							stream_COMMA.add(COMMA74);
						}


					}
					break;

				}

				RBRACKET75=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_arrayBuilder904); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_RBRACKET.add(RBRACKET75);
				}



				// AST REWRITE
				// elements: exprA
				// token labels:
				// rule labels: retval
				// token list labels:
				// rule list labels:
				if ( state.backtracking==0 ) {
					retval.tree = root_0;
					final RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

					root_0 = (GsTree)adaptor.nil();
					// 138:68: -> ^( ARRAY ( exprA )* )
					{
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:138:71: ^( ARRAY ( exprA )* )
						{
							GsTree root_1 = (GsTree)adaptor.nil();
							root_1 = (GsTree)adaptor.becomeRoot(adaptor.create(ARRAY, "ARRAY"), root_1);

							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:138:79: ( exprA )*
							while ( stream_exprA.hasNext() ) {
								adaptor.addChild(root_1, stream_exprA.nextTree());

							}
							stream_exprA.reset();

							adaptor.addChild(root_0, root_1);
						}

					}

					retval.tree = root_0;}
			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "arrayBuilder"

	public static class exprA_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "exprA"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:139:1: exprA : operand ( messageChainA ( cascadedMessageA )* )? ;
	public final GsParser.exprA_return exprA() throws RecognitionException {
		final GsParser.exprA_return retval = new GsParser.exprA_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.operand_return operand76 = null;

		GsParser.messageChainA_return messageChainA77 = null;

		GsParser.cascadedMessageA_return cascadedMessageA78 = null;



		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:139:6: ( operand ( messageChainA ( cascadedMessageA )* )? )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:139:8: operand ( messageChainA ( cascadedMessageA )* )?
			{
				root_0 = (GsTree)adaptor.nil();

				pushFollow(FOLLOW_operand_in_exprA919);
				operand76=operand();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, operand76.getTree());
				}
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:139:16: ( messageChainA ( cascadedMessageA )* )?
				int alt28=2;
				final int LA28_0 = input.LA(1);

				if ( (LA28_0==ID||(LA28_0>=LT && LA28_0<=BAR)||LA28_0==MINUS||(LA28_0>=AMPERSAND && LA28_0<=PLUS)) ) {
					alt28=1;
				}
				switch (alt28) {
					case 1 :
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:139:17: messageChainA ( cascadedMessageA )*
					{
						pushFollow(FOLLOW_messageChainA_in_exprA922);
						messageChainA77=messageChainA();

						state._fsp--;
						if (state.failed) {
							return retval;
						}
						if ( state.backtracking==0 ) {
							adaptor.addChild(root_0, messageChainA77.getTree());
						}
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:139:31: ( cascadedMessageA )*
						loop27:
							do {
								int alt27=2;
								final int LA27_0 = input.LA(1);

								if ( (LA27_0==62) ) {
									alt27=1;
								}


								switch (alt27) {
									case 1 :
										// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:139:32: cascadedMessageA
										{
											pushFollow(FOLLOW_cascadedMessageA_in_exprA925);
											cascadedMessageA78=cascadedMessageA();

											state._fsp--;
											if (state.failed) {
												return retval;
											}
											if ( state.backtracking==0 ) {
												adaptor.addChild(root_0, cascadedMessageA78.getTree());
											}

										}
										break;

									default :
										break loop27;
								}
							} while (true);


					}
					break;

				}


			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "exprA"

	public static class block_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "block"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:142:1: block : LBRACKET parameters temporaries statements RBRACKET -> ^( BLOCK parameters temporaries statements ) ;
	public final GsParser.block_return block() throws RecognitionException {
		final GsParser.block_return retval = new GsParser.block_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token LBRACKET79=null;
		Token RBRACKET83=null;
		GsParser.parameters_return parameters80 = null;

		GsParser.temporaries_return temporaries81 = null;

		GsParser.statements_return statements82 = null;


		final GsTree LBRACKET79_tree=null;
		final GsTree RBRACKET83_tree=null;
		final RewriteRuleTokenStream stream_LBRACKET=new RewriteRuleTokenStream(adaptor,"token LBRACKET");
		final RewriteRuleTokenStream stream_RBRACKET=new RewriteRuleTokenStream(adaptor,"token RBRACKET");
		final RewriteRuleSubtreeStream stream_statements=new RewriteRuleSubtreeStream(adaptor,"rule statements");
		final RewriteRuleSubtreeStream stream_temporaries=new RewriteRuleSubtreeStream(adaptor,"rule temporaries");
		final RewriteRuleSubtreeStream stream_parameters=new RewriteRuleSubtreeStream(adaptor,"rule parameters");
		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:142:6: ( LBRACKET parameters temporaries statements RBRACKET -> ^( BLOCK parameters temporaries statements ) )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:142:8: LBRACKET parameters temporaries statements RBRACKET
			{
				LBRACKET79=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_block937); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_LBRACKET.add(LBRACKET79);
				}

				pushFollow(FOLLOW_parameters_in_block940);
				parameters80=parameters();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_parameters.add(parameters80.getTree());
				}
				pushFollow(FOLLOW_temporaries_in_block942);
				temporaries81=temporaries();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_temporaries.add(temporaries81.getTree());
				}
				pushFollow(FOLLOW_statements_in_block944);
				statements82=statements();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_statements.add(statements82.getTree());
				}
				RBRACKET83=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_block946); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_RBRACKET.add(RBRACKET83);
				}



				// AST REWRITE
				// elements: parameters, temporaries, statements
				// token labels:
					// rule labels: retval
				// token list labels:
				// rule list labels:
				if ( state.backtracking==0 ) {
					retval.tree = root_0;
					final RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

					root_0 = (GsTree)adaptor.nil();
					// 142:67: -> ^( BLOCK parameters temporaries statements )
					{
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:142:70: ^( BLOCK parameters temporaries statements )
						{
							GsTree root_1 = (GsTree)adaptor.nil();
							root_1 = (GsTree)adaptor.becomeRoot(adaptor.create(BLOCK, "BLOCK"), root_1);

							adaptor.addChild(root_1, stream_parameters.nextTree());
							adaptor.addChild(root_1, stream_temporaries.nextTree());
							adaptor.addChild(root_1, stream_statements.nextTree());

							adaptor.addChild(root_0, root_1);
						}

					}

					retval.tree = root_0;}
			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "block"

	public static class parameters_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "parameters"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:143:1: parameters : ( ( blockParameter )+ BAR -> ^( PARAMETERS ( blockParameter )+ ) | -> ^( PARAMETERS ) );
	public final GsParser.parameters_return parameters() throws RecognitionException {
		final GsParser.parameters_return retval = new GsParser.parameters_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token BAR85=null;
		GsParser.blockParameter_return blockParameter84 = null;


		final GsTree BAR85_tree=null;
		final RewriteRuleTokenStream stream_BAR=new RewriteRuleTokenStream(adaptor,"token BAR");
		final RewriteRuleSubtreeStream stream_blockParameter=new RewriteRuleSubtreeStream(adaptor,"rule blockParameter");
		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:144:3: ( ( blockParameter )+ BAR -> ^( PARAMETERS ( blockParameter )+ ) | -> ^( PARAMETERS ) )
			int alt30=2;
			final int LA30_0 = input.LA(1);

			if ( (LA30_0==COLON) ) {
				alt30=1;
			}
			else if ( (LA30_0==EOF||(LA30_0>=INT_LITERAL && LA30_0<=ID)||LA30_0==BAR||LA30_0==RETURN||(LA30_0>=NIL && LA30_0<=LPAREN)||LA30_0==LBRACKET||(LA30_0>=RBRACKET && LA30_0<=LBRACE)||LA30_0==61) ) {
				alt30=2;
			}
			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				final NoViableAltException nvae =
					new NoViableAltException("", 30, 0, input);

				throw nvae;
			}
			switch (alt30) {
				case 1 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:144:5: ( blockParameter )+ BAR
				{
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:144:5: ( blockParameter )+
					int cnt29=0;
					loop29:
						do {
							int alt29=2;
							final int LA29_0 = input.LA(1);

							if ( (LA29_0==COLON) ) {
								alt29=1;
							}


							switch (alt29) {
								case 1 :
									// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:144:6: blockParameter
									{
										pushFollow(FOLLOW_blockParameter_in_parameters974);
										blockParameter84=blockParameter();

										state._fsp--;
										if (state.failed) {
											return retval;
										}
										if ( state.backtracking==0 ) {
											stream_blockParameter.add(blockParameter84.getTree());
										}

									}
									break;

								default :
									if ( cnt29 >= 1 ) {
										break loop29;
									}
									if (state.backtracking>0) {state.failed=true; return retval;}
									final EarlyExitException eee =
										new EarlyExitException(29, input);
									throw eee;
							}
							cnt29++;
						} while (true);

					BAR85=(Token)match(input,BAR,FOLLOW_BAR_in_parameters978); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						stream_BAR.add(BAR85);
					}



					// AST REWRITE
					// elements: blockParameter
					// token labels:
					// rule labels: retval
					// token list labels:
					// rule list labels:
					if ( state.backtracking==0 ) {
						retval.tree = root_0;
						final RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

						root_0 = (GsTree)adaptor.nil();
						// 144:55: -> ^( PARAMETERS ( blockParameter )+ )
						{
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:144:58: ^( PARAMETERS ( blockParameter )+ )
							{
								GsTree root_1 = (GsTree)adaptor.nil();
								root_1 = (GsTree)adaptor.becomeRoot(adaptor.create(PARAMETERS, "PARAMETERS"), root_1);

								if ( !(stream_blockParameter.hasNext()) ) {
									throw new RewriteEarlyExitException();
								}
								while ( stream_blockParameter.hasNext() ) {
									adaptor.addChild(root_1, stream_blockParameter.nextTree());

								}
								stream_blockParameter.reset();

								adaptor.addChild(root_0, root_1);
							}

						}

						retval.tree = root_0;}
				}
				break;
				case 2 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:145:55:
				{

					// AST REWRITE
					// elements:
					// token labels:
					// rule labels: retval
					// token list labels:
					// rule list labels:
					if ( state.backtracking==0 ) {
						retval.tree = root_0;
						final RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

						root_0 = (GsTree)adaptor.nil();
						// 145:55: -> ^( PARAMETERS )
						{
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:145:58: ^( PARAMETERS )
							{
								GsTree root_1 = (GsTree)adaptor.nil();
								root_1 = (GsTree)adaptor.becomeRoot(adaptor.create(PARAMETERS, "PARAMETERS"), root_1);

								adaptor.addChild(root_0, root_1);
							}

						}

						retval.tree = root_0;}
				}
				break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "parameters"

	public static class blockParameter_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "blockParameter"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:147:1: blockParameter : COLON ID ;
	public final GsParser.blockParameter_return blockParameter() throws RecognitionException {
		final GsParser.blockParameter_return retval = new GsParser.blockParameter_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token COLON86=null;
		Token ID87=null;

		final GsTree COLON86_tree=null;
		GsTree ID87_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:147:15: ( COLON ID )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:147:17: COLON ID
			{
				root_0 = (GsTree)adaptor.nil();

				COLON86=(Token)match(input,COLON,FOLLOW_COLON_in_blockParameter1085); if (state.failed) {
					return retval;
				}
				ID87=(Token)match(input,ID,FOLLOW_ID_in_blockParameter1088); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					ID87_tree = (GsTree)adaptor.create(ID87);
					adaptor.addChild(root_0, ID87_tree);
				}
				if ( state.backtracking==0 ) {
					ID87.setType(BLOCK_PARAMETER);
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "blockParameter"

	public static class selectionBlock_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "selectionBlock"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:151:1: selectionBlock : LBRACE blockParameter BAR predicate RBRACE -> ^( BLOCK ^( PARAMETERS blockParameter ) ^( TEMPORARIES ) ^( STATEMENTS predicate ) ) ;
	public final GsParser.selectionBlock_return selectionBlock() throws RecognitionException {
		final GsParser.selectionBlock_return retval = new GsParser.selectionBlock_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token LBRACE88=null;
		Token BAR90=null;
		Token RBRACE92=null;
		GsParser.blockParameter_return blockParameter89 = null;

		GsParser.predicate_return predicate91 = null;


		final GsTree LBRACE88_tree=null;
		final GsTree BAR90_tree=null;
		final GsTree RBRACE92_tree=null;
		final RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
		final RewriteRuleTokenStream stream_BAR=new RewriteRuleTokenStream(adaptor,"token BAR");
		final RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
		final RewriteRuleSubtreeStream stream_blockParameter=new RewriteRuleSubtreeStream(adaptor,"rule blockParameter");
		final RewriteRuleSubtreeStream stream_predicate=new RewriteRuleSubtreeStream(adaptor,"rule predicate");
		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:151:15: ( LBRACE blockParameter BAR predicate RBRACE -> ^( BLOCK ^( PARAMETERS blockParameter ) ^( TEMPORARIES ) ^( STATEMENTS predicate ) ) )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:151:17: LBRACE blockParameter BAR predicate RBRACE
			{
				LBRACE88=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_selectionBlock1099); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_LBRACE.add(LBRACE88);
				}

				pushFollow(FOLLOW_blockParameter_in_selectionBlock1102);
				blockParameter89=blockParameter();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_blockParameter.add(blockParameter89.getTree());
				}
				BAR90=(Token)match(input,BAR,FOLLOW_BAR_in_selectionBlock1104); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_BAR.add(BAR90);
				}

				pushFollow(FOLLOW_predicate_in_selectionBlock1107);
				predicate91=predicate();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_predicate.add(predicate91.getTree());
				}
				RBRACE92=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_selectionBlock1109); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_RBRACE.add(RBRACE92);
				}



				// AST REWRITE
				// elements: predicate, blockParameter
				// token labels:
					// rule labels: retval
				// token list labels:
				// rule list labels:
				if ( state.backtracking==0 ) {
					retval.tree = root_0;
					final RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

					root_0 = (GsTree)adaptor.nil();
					// 151:63: -> ^( BLOCK ^( PARAMETERS blockParameter ) ^( TEMPORARIES ) ^( STATEMENTS predicate ) )
					{
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:151:66: ^( BLOCK ^( PARAMETERS blockParameter ) ^( TEMPORARIES ) ^( STATEMENTS predicate ) )
						{
							GsTree root_1 = (GsTree)adaptor.nil();
							root_1 = (GsTree)adaptor.becomeRoot(adaptor.create(BLOCK, "BLOCK"), root_1);

							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:151:74: ^( PARAMETERS blockParameter )
							{
								GsTree root_2 = (GsTree)adaptor.nil();
								root_2 = (GsTree)adaptor.becomeRoot(adaptor.create(PARAMETERS, "PARAMETERS"), root_2);

								adaptor.addChild(root_2, stream_blockParameter.nextTree());

								adaptor.addChild(root_1, root_2);
							}
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:151:103: ^( TEMPORARIES )
							{
								GsTree root_2 = (GsTree)adaptor.nil();
								root_2 = (GsTree)adaptor.becomeRoot(adaptor.create(TEMPORARIES, "TEMPORARIES"), root_2);

								adaptor.addChild(root_1, root_2);
							}
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:151:118: ^( STATEMENTS predicate )
							{
								GsTree root_2 = (GsTree)adaptor.nil();
								root_2 = (GsTree)adaptor.becomeRoot(adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

								adaptor.addChild(root_2, stream_predicate.nextTree());

								adaptor.addChild(root_1, root_2);
							}

							adaptor.addChild(root_0, root_1);
						}

					}

					retval.tree = root_0;}
			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "selectionBlock"

	public static class predicate_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "predicate"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:152:1: predicate : ( anyTerm ( AMPERSAND term )* | parenTerm ( AMPERSAND term )* );
	public final GsParser.predicate_return predicate() throws RecognitionException {
		final GsParser.predicate_return retval = new GsParser.predicate_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token AMPERSAND94=null;
		Token AMPERSAND97=null;
		GsParser.anyTerm_return anyTerm93 = null;

		GsParser.term_return term95 = null;

		GsParser.parenTerm_return parenTerm96 = null;

		GsParser.term_return term98 = null;


		GsTree AMPERSAND94_tree=null;
		GsTree AMPERSAND97_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:153:3: ( anyTerm ( AMPERSAND term )* | parenTerm ( AMPERSAND term )* )
			int alt33=2;
			final int LA33_0 = input.LA(1);

			if ( ((LA33_0>=INT_LITERAL && LA33_0<=ID)||(LA33_0>=NIL && LA33_0<=SYMBOL_LITERAL)||LA33_0==LBRACKET||LA33_0==LBRACE||LA33_0==61) ) {
				alt33=1;
			}
			else if ( (LA33_0==LPAREN) ) {
				alt33=2;
			}
			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				final NoViableAltException nvae =
					new NoViableAltException("", 33, 0, input);

				throw nvae;
			}
			switch (alt33) {
				case 1 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:153:4: anyTerm ( AMPERSAND term )*
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_anyTerm_in_predicate1140);
					anyTerm93=anyTerm();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, anyTerm93.getTree());
					}
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:153:12: ( AMPERSAND term )*
					loop31:
						do {
							int alt31=2;
							final int LA31_0 = input.LA(1);

							if ( (LA31_0==AMPERSAND) ) {
								alt31=1;
							}


							switch (alt31) {
								case 1 :
									// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:153:13: AMPERSAND term
									{
										AMPERSAND94=(Token)match(input,AMPERSAND,FOLLOW_AMPERSAND_in_predicate1143); if (state.failed) {
											return retval;
										}
										if ( state.backtracking==0 ) {
											AMPERSAND94_tree = (GsTree)adaptor.create(AMPERSAND94);
											adaptor.addChild(root_0, AMPERSAND94_tree);
										}
										pushFollow(FOLLOW_term_in_predicate1145);
										term95=term();

										state._fsp--;
										if (state.failed) {
											return retval;
										}
										if ( state.backtracking==0 ) {
											adaptor.addChild(root_0, term95.getTree());
										}

									}
									break;

								default :
									break loop31;
							}
						} while (true);


				}
				break;
				case 2 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:154:4: parenTerm ( AMPERSAND term )*
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_parenTerm_in_predicate1152);
					parenTerm96=parenTerm();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, parenTerm96.getTree());
					}
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:154:14: ( AMPERSAND term )*
					loop32:
						do {
							int alt32=2;
							final int LA32_0 = input.LA(1);

							if ( (LA32_0==AMPERSAND) ) {
								alt32=1;
							}


							switch (alt32) {
								case 1 :
									// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:154:15: AMPERSAND term
									{
										AMPERSAND97=(Token)match(input,AMPERSAND,FOLLOW_AMPERSAND_in_predicate1155); if (state.failed) {
											return retval;
										}
										if ( state.backtracking==0 ) {
											AMPERSAND97_tree = (GsTree)adaptor.create(AMPERSAND97);
											adaptor.addChild(root_0, AMPERSAND97_tree);
										}
										pushFollow(FOLLOW_term_in_predicate1157);
										term98=term();

										state._fsp--;
										if (state.failed) {
											return retval;
										}
										if ( state.backtracking==0 ) {
											adaptor.addChild(root_0, term98.getTree());
										}

									}
									break;

								default :
									break loop32;
							}
						} while (true);


				}
				break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "predicate"

	public static class term_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "term"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:157:1: term : ( parenTerm | selectionBlockOperand );
	public final GsParser.term_return term() throws RecognitionException {
		final GsParser.term_return retval = new GsParser.term_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.parenTerm_return parenTerm99 = null;

		GsParser.selectionBlockOperand_return selectionBlockOperand100 = null;



		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:157:5: ( parenTerm | selectionBlockOperand )
			int alt34=2;
			final int LA34_0 = input.LA(1);

			if ( (LA34_0==LPAREN) ) {
				alt34=1;
			}
			else if ( ((LA34_0>=INT_LITERAL && LA34_0<=ID)||(LA34_0>=NIL && LA34_0<=SYMBOL_LITERAL)||LA34_0==LBRACKET||LA34_0==LBRACE||LA34_0==61) ) {
				alt34=2;
			}
			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				final NoViableAltException nvae =
					new NoViableAltException("", 34, 0, input);

				throw nvae;
			}
			switch (alt34) {
				case 1 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:157:7: parenTerm
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_parenTerm_in_term1171);
					parenTerm99=parenTerm();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, parenTerm99.getTree());
					}

				}
				break;
				case 2 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:157:17: selectionBlockOperand
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_selectionBlockOperand_in_term1173);
					selectionBlockOperand100=selectionBlockOperand();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, selectionBlockOperand100.getTree());
					}

				}
				break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "term"

	public static class anyTerm_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "anyTerm"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:159:1: anyTerm : selectionBlockOperand ( binarySelector selectionBlockOperand )? ;
	public final GsParser.anyTerm_return anyTerm() throws RecognitionException {
		final GsParser.anyTerm_return retval = new GsParser.anyTerm_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.selectionBlockOperand_return selectionBlockOperand101 = null;

		GsParser.binarySelector_return binarySelector102 = null;

		GsParser.selectionBlockOperand_return selectionBlockOperand103 = null;



		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:159:8: ( selectionBlockOperand ( binarySelector selectionBlockOperand )? )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:159:10: selectionBlockOperand ( binarySelector selectionBlockOperand )?
			{
				root_0 = (GsTree)adaptor.nil();

				pushFollow(FOLLOW_selectionBlockOperand_in_anyTerm1180);
				selectionBlockOperand101=selectionBlockOperand();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, selectionBlockOperand101.getTree());
				}
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:159:32: ( binarySelector selectionBlockOperand )?
				int alt35=2;
				final int LA35_0 = input.LA(1);

				if ( (LA35_0==AMPERSAND) ) {
					final int LA35_1 = input.LA(2);

					if ( (synpred62_Gs()) ) {
						alt35=1;
					}
				}
				else if ( ((LA35_0>=LT && LA35_0<=BAR)||LA35_0==MINUS||LA35_0==COMMA||(LA35_0>=BINARY_SELECTOR && LA35_0<=PLUS)) ) {
					alt35=1;
				}
				switch (alt35) {
					case 1 :
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:159:33: binarySelector selectionBlockOperand
					{
						pushFollow(FOLLOW_binarySelector_in_anyTerm1183);
						binarySelector102=binarySelector();

						state._fsp--;
						if (state.failed) {
							return retval;
						}
						if ( state.backtracking==0 ) {
							adaptor.addChild(root_0, binarySelector102.getTree());
						}
						pushFollow(FOLLOW_selectionBlockOperand_in_anyTerm1185);
						selectionBlockOperand103=selectionBlockOperand();

						state._fsp--;
						if (state.failed) {
							return retval;
						}
						if ( state.backtracking==0 ) {
							adaptor.addChild(root_0, selectionBlockOperand103.getTree());
						}

					}
					break;

				}


			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "anyTerm"

	public static class parenTerm_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "parenTerm"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:160:1: parenTerm : LPAREN anyTerm RPAREN ;
	public final GsParser.parenTerm_return parenTerm() throws RecognitionException {
		final GsParser.parenTerm_return retval = new GsParser.parenTerm_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token LPAREN104=null;
		Token RPAREN106=null;
		GsParser.anyTerm_return anyTerm105 = null;


		final GsTree LPAREN104_tree=null;
		final GsTree RPAREN106_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:160:10: ( LPAREN anyTerm RPAREN )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:160:12: LPAREN anyTerm RPAREN
			{
				root_0 = (GsTree)adaptor.nil();

				LPAREN104=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_parenTerm1193); if (state.failed) {
					return retval;
				}
				pushFollow(FOLLOW_anyTerm_in_parenTerm1196);
				anyTerm105=anyTerm();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, anyTerm105.getTree());
				}
				RPAREN106=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_parenTerm1198); if (state.failed) {
					return retval;
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "parenTerm"

	public static class selectionBlockOperand_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "selectionBlockOperand"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:162:1: selectionBlockOperand : ( ID ( DOT ID )* | literal );
	public final GsParser.selectionBlockOperand_return selectionBlockOperand() throws RecognitionException {
		final GsParser.selectionBlockOperand_return retval = new GsParser.selectionBlockOperand_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token ID107=null;
		Token DOT108=null;
		Token ID109=null;
		GsParser.literal_return literal110 = null;


		GsTree ID107_tree=null;
		GsTree DOT108_tree=null;
		GsTree ID109_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:162:22: ( ID ( DOT ID )* | literal )
			int alt37=2;
			final int LA37_0 = input.LA(1);

			if ( (LA37_0==ID) ) {
				alt37=1;
			}
			else if ( ((LA37_0>=INT_LITERAL && LA37_0<=FLOAT_LITERAL)||(LA37_0>=NIL && LA37_0<=SYMBOL_LITERAL)||LA37_0==LBRACKET||LA37_0==LBRACE||LA37_0==61) ) {
				alt37=2;
			}
			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				final NoViableAltException nvae =
					new NoViableAltException("", 37, 0, input);

				throw nvae;
			}
			switch (alt37) {
				case 1 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:162:24: ID ( DOT ID )*
				{
					root_0 = (GsTree)adaptor.nil();

					ID107=(Token)match(input,ID,FOLLOW_ID_in_selectionBlockOperand1206); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						ID107_tree = (GsTree)adaptor.create(ID107);
						adaptor.addChild(root_0, ID107_tree);
					}
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:162:26: ( DOT ID )*
					loop36:
						do {
							int alt36=2;
							final int LA36_0 = input.LA(1);

							if ( (LA36_0==DOT) ) {
								alt36=1;
							}


							switch (alt36) {
								case 1 :
									// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:162:27: DOT ID
									{
										DOT108=(Token)match(input,DOT,FOLLOW_DOT_in_selectionBlockOperand1208); if (state.failed) {
											return retval;
										}
										if ( state.backtracking==0 ) {
											DOT108_tree = (GsTree)adaptor.create(DOT108);
											adaptor.addChild(root_0, DOT108_tree);
										}
										ID109=(Token)match(input,ID,FOLLOW_ID_in_selectionBlockOperand1210); if (state.failed) {
											return retval;
										}
										if ( state.backtracking==0 ) {
											ID109_tree = (GsTree)adaptor.create(ID109);
											adaptor.addChild(root_0, ID109_tree);
										}

									}
									break;

								default :
									break loop36;
							}
						} while (true);


				}
				break;
				case 2 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:162:38: literal
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_literal_in_selectionBlockOperand1216);
					literal110=literal();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, literal110.getTree());
					}

				}
				break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "selectionBlockOperand"

	public static class nestedExpr_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "nestedExpr"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:164:1: nestedExpr : '(' statement ')' ;
	public final GsParser.nestedExpr_return nestedExpr() throws RecognitionException {
		final GsParser.nestedExpr_return retval = new GsParser.nestedExpr_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token char_literal111=null;
		Token char_literal113=null;
		GsParser.statement_return statement112 = null;


		GsTree char_literal111_tree=null;
		GsTree char_literal113_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:164:11: ( '(' statement ')' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:164:13: '(' statement ')'
			{
				root_0 = (GsTree)adaptor.nil();

				char_literal111=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_nestedExpr1223); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					char_literal111_tree = (GsTree)adaptor.create(char_literal111);
					adaptor.addChild(root_0, char_literal111_tree);
				}
				pushFollow(FOLLOW_statement_in_nestedExpr1225);
				statement112=statement();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, statement112.getTree());
				}
				char_literal113=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_nestedExpr1227); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					char_literal113_tree = (GsTree)adaptor.create(char_literal113);
					adaptor.addChild(root_0, char_literal113_tree);
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "nestedExpr"

	public static class messageChain_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "messageChain"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:166:1: messageChain : ( unaryMessage unaryMessageChain binaryMessageChain ( keywordMessage )? | binaryMessage binaryMessageChain ( keywordMessage )? | keywordMessage );
	public final GsParser.messageChain_return messageChain() throws RecognitionException {
		final GsParser.messageChain_return retval = new GsParser.messageChain_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.unaryMessage_return unaryMessage114 = null;

		GsParser.unaryMessageChain_return unaryMessageChain115 = null;

		GsParser.binaryMessageChain_return binaryMessageChain116 = null;

		GsParser.keywordMessage_return keywordMessage117 = null;

		GsParser.binaryMessage_return binaryMessage118 = null;

		GsParser.binaryMessageChain_return binaryMessageChain119 = null;

		GsParser.keywordMessage_return keywordMessage120 = null;

		GsParser.keywordMessage_return keywordMessage121 = null;



		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:167:3: ( unaryMessage unaryMessageChain binaryMessageChain ( keywordMessage )? | binaryMessage binaryMessageChain ( keywordMessage )? | keywordMessage )
			int alt40=3;
			final int LA40_0 = input.LA(1);

			if ( (LA40_0==ID) ) {
				final int LA40_1 = input.LA(2);

				if ( (LA40_1==COLON) ) {
					alt40=3;
				}
				else if ( (LA40_1==EOF||LA40_1==ID||(LA40_1>=LT && LA40_1<=DOT)||LA40_1==MINUS||LA40_1==RPAREN||(LA40_1>=COMMA && LA40_1<=RBRACKET)||(LA40_1>=AMPERSAND && LA40_1<=PLUS)||LA40_1==62) ) {
					alt40=1;
				}
				else {
					if (state.backtracking>0) {state.failed=true; return retval;}
					final NoViableAltException nvae =
						new NoViableAltException("", 40, 1, input);

					throw nvae;
				}
			}
			else if ( ((LA40_0>=LT && LA40_0<=BAR)||LA40_0==MINUS||LA40_0==COMMA||(LA40_0>=AMPERSAND && LA40_0<=PLUS)) ) {
				alt40=2;
			}
			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				final NoViableAltException nvae =
					new NoViableAltException("", 40, 0, input);

				throw nvae;
			}
			switch (alt40) {
				case 1 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:167:5: unaryMessage unaryMessageChain binaryMessageChain ( keywordMessage )?
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_unaryMessage_in_messageChain1239);
					unaryMessage114=unaryMessage();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, unaryMessage114.getTree());
					}
					pushFollow(FOLLOW_unaryMessageChain_in_messageChain1241);
					unaryMessageChain115=unaryMessageChain();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, unaryMessageChain115.getTree());
					}
					pushFollow(FOLLOW_binaryMessageChain_in_messageChain1243);
					binaryMessageChain116=binaryMessageChain();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, binaryMessageChain116.getTree());
					}
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:167:55: ( keywordMessage )?
						int alt38=2;
					final int LA38_0 = input.LA(1);

					if ( (LA38_0==ID) ) {
						alt38=1;
					}
					switch (alt38) {
						case 1 :
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:167:56: keywordMessage
						{
							pushFollow(FOLLOW_keywordMessage_in_messageChain1246);
							keywordMessage117=keywordMessage();

							state._fsp--;
							if (state.failed) {
								return retval;
							}
							if ( state.backtracking==0 ) {
								adaptor.addChild(root_0, keywordMessage117.getTree());
							}

						}
						break;

					}


				}
				break;
				case 2 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:168:5: binaryMessage binaryMessageChain ( keywordMessage )?
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_binaryMessage_in_messageChain1254);
					binaryMessage118=binaryMessage();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, binaryMessage118.getTree());
					}
					pushFollow(FOLLOW_binaryMessageChain_in_messageChain1256);
					binaryMessageChain119=binaryMessageChain();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, binaryMessageChain119.getTree());
					}
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:168:38: ( keywordMessage )?
						int alt39=2;
					final int LA39_0 = input.LA(1);

					if ( (LA39_0==ID) ) {
						alt39=1;
					}
					switch (alt39) {
						case 1 :
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:168:39: keywordMessage
						{
							pushFollow(FOLLOW_keywordMessage_in_messageChain1259);
							keywordMessage120=keywordMessage();

							state._fsp--;
							if (state.failed) {
								return retval;
							}
							if ( state.backtracking==0 ) {
								adaptor.addChild(root_0, keywordMessage120.getTree());
							}

						}
						break;

					}


				}
				break;
				case 3 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:169:5: keywordMessage
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_keywordMessage_in_messageChain1267);
					keywordMessage121=keywordMessage();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, keywordMessage121.getTree());
					}

				}
				break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "messageChain"

	public static class unaryMessage_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "unaryMessage"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:171:1: unaryMessage : ID ;
	public final GsParser.unaryMessage_return unaryMessage() throws RecognitionException {
		final GsParser.unaryMessage_return retval = new GsParser.unaryMessage_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token ID122=null;

		GsTree ID122_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:171:13: ( ID )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:171:15: ID
			{
				root_0 = (GsTree)adaptor.nil();

				ID122=(Token)match(input,ID,FOLLOW_ID_in_unaryMessage1276); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					ID122_tree = (GsTree)adaptor.create(ID122);
					adaptor.addChild(root_0, ID122_tree);
				}
				if ( state.backtracking==0 ) {
					ID122.setType(UNARY_MESSAGE);
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "unaryMessage"

	public static class unaryMessageChain_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "unaryMessageChain"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:172:1: unaryMessageChain : ( unaryMessage )* ;
	public final GsParser.unaryMessageChain_return unaryMessageChain() throws RecognitionException {
		final GsParser.unaryMessageChain_return retval = new GsParser.unaryMessageChain_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.unaryMessage_return unaryMessage123 = null;



		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:172:18: ( ( unaryMessage )* )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:172:20: ( unaryMessage )*
			{
				root_0 = (GsTree)adaptor.nil();

				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:172:20: ( unaryMessage )*
				loop41:
					do {
						int alt41=2;
						final int LA41_0 = input.LA(1);

						if ( (LA41_0==ID) ) {
							final int LA41_2 = input.LA(2);

							if ( (LA41_2==EOF||LA41_2==ID||(LA41_2>=LT && LA41_2<=DOT)||LA41_2==MINUS||LA41_2==RPAREN||(LA41_2>=COMMA && LA41_2<=RBRACKET)||(LA41_2>=AMPERSAND && LA41_2<=PLUS)||LA41_2==62) ) {
								alt41=1;
							}


						}


						switch (alt41) {
							case 1 :
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:172:21: unaryMessage
								{
									pushFollow(FOLLOW_unaryMessage_in_unaryMessageChain1314);
									unaryMessage123=unaryMessage();

									state._fsp--;
									if (state.failed) {
										return retval;
									}
									if ( state.backtracking==0 ) {
										adaptor.addChild(root_0, unaryMessage123.getTree());
									}

								}
								break;

							default :
								break loop41;
						}
					} while (true);


			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "unaryMessageChain"

	public static class binaryMessageOperand_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "binaryMessageOperand"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:174:1: binaryMessageOperand : operand unaryMessageChain ;
	public final GsParser.binaryMessageOperand_return binaryMessageOperand() throws RecognitionException {
		final GsParser.binaryMessageOperand_return retval = new GsParser.binaryMessageOperand_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.operand_return operand124 = null;

		GsParser.unaryMessageChain_return unaryMessageChain125 = null;



		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:174:21: ( operand unaryMessageChain )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:174:23: operand unaryMessageChain
			{
				root_0 = (GsTree)adaptor.nil();

				pushFollow(FOLLOW_operand_in_binaryMessageOperand1323);
				operand124=operand();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, operand124.getTree());
				}
				pushFollow(FOLLOW_unaryMessageChain_in_binaryMessageOperand1325);
				unaryMessageChain125=unaryMessageChain();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, unaryMessageChain125.getTree());
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "binaryMessageOperand"

	public static class binaryMessage_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "binaryMessage"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:175:1: binaryMessage : binarySelector binaryMessageOperand -> ^( BINARY_MESSAGE binarySelector binaryMessageOperand ) ;
	public final GsParser.binaryMessage_return binaryMessage() throws RecognitionException {
		final GsParser.binaryMessage_return retval = new GsParser.binaryMessage_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.binarySelector_return binarySelector126 = null;

		GsParser.binaryMessageOperand_return binaryMessageOperand127 = null;


		final RewriteRuleSubtreeStream stream_binarySelector=new RewriteRuleSubtreeStream(adaptor,"rule binarySelector");
		final RewriteRuleSubtreeStream stream_binaryMessageOperand=new RewriteRuleSubtreeStream(adaptor,"rule binaryMessageOperand");
		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:175:14: ( binarySelector binaryMessageOperand -> ^( BINARY_MESSAGE binarySelector binaryMessageOperand ) )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:175:16: binarySelector binaryMessageOperand
			{
				pushFollow(FOLLOW_binarySelector_in_binaryMessage1331);
				binarySelector126=binarySelector();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_binarySelector.add(binarySelector126.getTree());
				}
				pushFollow(FOLLOW_binaryMessageOperand_in_binaryMessage1333);
				binaryMessageOperand127=binaryMessageOperand();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_binaryMessageOperand.add(binaryMessageOperand127.getTree());
				}


				// AST REWRITE
				// elements: binarySelector, binaryMessageOperand
				// token labels:
				// rule labels: retval
				// token list labels:
				// rule list labels:
				if ( state.backtracking==0 ) {
					retval.tree = root_0;
					final RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

					root_0 = (GsTree)adaptor.nil();
					// 175:56: -> ^( BINARY_MESSAGE binarySelector binaryMessageOperand )
					{
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:175:59: ^( BINARY_MESSAGE binarySelector binaryMessageOperand )
						{
							GsTree root_1 = (GsTree)adaptor.nil();
							root_1 = (GsTree)adaptor.becomeRoot(adaptor.create(BINARY_MESSAGE, "BINARY_MESSAGE"), root_1);

							adaptor.addChild(root_1, stream_binarySelector.nextTree());
							adaptor.addChild(root_1, stream_binaryMessageOperand.nextTree());

							adaptor.addChild(root_0, root_1);
						}

					}

					retval.tree = root_0;}
			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "binaryMessage"

	public static class binaryMessageChain_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "binaryMessageChain"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:176:1: binaryMessageChain : ( binaryMessage )* ;
	public final GsParser.binaryMessageChain_return binaryMessageChain() throws RecognitionException {
		final GsParser.binaryMessageChain_return retval = new GsParser.binaryMessageChain_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.binaryMessage_return binaryMessage128 = null;



		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:176:19: ( ( binaryMessage )* )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:176:21: ( binaryMessage )*
			{
				root_0 = (GsTree)adaptor.nil();

				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:176:21: ( binaryMessage )*
				loop42:
					do {
						int alt42=2;
						final int LA42_0 = input.LA(1);

						if ( ((LA42_0>=LT && LA42_0<=BAR)||LA42_0==MINUS||LA42_0==COMMA||(LA42_0>=AMPERSAND && LA42_0<=PLUS)) ) {
							alt42=1;
						}


						switch (alt42) {
							case 1 :
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:176:22: binaryMessage
							{
								pushFollow(FOLLOW_binaryMessage_in_binaryMessageChain1354);
								binaryMessage128=binaryMessage();

								state._fsp--;
								if (state.failed) {
									return retval;
								}
								if ( state.backtracking==0 ) {
									adaptor.addChild(root_0, binaryMessage128.getTree());
								}

							}
							break;

							default :
								break loop42;
						}
					} while (true);


			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "binaryMessageChain"

	public static class keywordMessageArgument_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "keywordMessageArgument"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:178:1: keywordMessageArgument : binaryMessageOperand binaryMessageChain ;
	public final GsParser.keywordMessageArgument_return keywordMessageArgument() throws RecognitionException {
		final GsParser.keywordMessageArgument_return retval = new GsParser.keywordMessageArgument_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.binaryMessageOperand_return binaryMessageOperand129 = null;

		GsParser.binaryMessageChain_return binaryMessageChain130 = null;



		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:178:23: ( binaryMessageOperand binaryMessageChain )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:178:25: binaryMessageOperand binaryMessageChain
			{
				root_0 = (GsTree)adaptor.nil();

				pushFollow(FOLLOW_binaryMessageOperand_in_keywordMessageArgument1363);
				binaryMessageOperand129=binaryMessageOperand();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, binaryMessageOperand129.getTree());
				}
				pushFollow(FOLLOW_binaryMessageChain_in_keywordMessageArgument1365);
				binaryMessageChain130=binaryMessageChain();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, binaryMessageChain130.getTree());
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "keywordMessageArgument"

	public static class keywordMessageSegment_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "keywordMessageSegment"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:179:1: keywordMessageSegment : keyword keywordMessageArgument ;
	public final GsParser.keywordMessageSegment_return keywordMessageSegment() throws RecognitionException {
		final GsParser.keywordMessageSegment_return retval = new GsParser.keywordMessageSegment_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.keyword_return keyword131 = null;

		GsParser.keywordMessageArgument_return keywordMessageArgument132 = null;



		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:179:22: ( keyword keywordMessageArgument )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:179:24: keyword keywordMessageArgument
			{
				root_0 = (GsTree)adaptor.nil();

				pushFollow(FOLLOW_keyword_in_keywordMessageSegment1371);
				keyword131=keyword();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, keyword131.getTree());
				}
				pushFollow(FOLLOW_keywordMessageArgument_in_keywordMessageSegment1373);
				keywordMessageArgument132=keywordMessageArgument();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, keywordMessageArgument132.getTree());
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "keywordMessageSegment"

	public static class keywordMessage_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "keywordMessage"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:180:1: keywordMessage : ( keywordMessageSegment )+ -> ^( KEYWORD_MESSAGE ( keywordMessageSegment )+ ) ;
	public final GsParser.keywordMessage_return keywordMessage() throws RecognitionException {
		final GsParser.keywordMessage_return retval = new GsParser.keywordMessage_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.keywordMessageSegment_return keywordMessageSegment133 = null;


		final RewriteRuleSubtreeStream stream_keywordMessageSegment=new RewriteRuleSubtreeStream(adaptor,"rule keywordMessageSegment");
		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:180:15: ( ( keywordMessageSegment )+ -> ^( KEYWORD_MESSAGE ( keywordMessageSegment )+ ) )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:180:17: ( keywordMessageSegment )+
			{
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:180:17: ( keywordMessageSegment )+
				int cnt43=0;
				loop43:
					do {
						int alt43=2;
						final int LA43_0 = input.LA(1);

						if ( (LA43_0==ID) ) {
							alt43=1;
						}


						switch (alt43) {
							case 1 :
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:180:18: keywordMessageSegment
							{
								pushFollow(FOLLOW_keywordMessageSegment_in_keywordMessage1380);
								keywordMessageSegment133=keywordMessageSegment();

								state._fsp--;
								if (state.failed) {
									return retval;
								}
								if ( state.backtracking==0 ) {
									stream_keywordMessageSegment.add(keywordMessageSegment133.getTree());
								}

							}
							break;

							default :
								if ( cnt43 >= 1 ) {
									break loop43;
								}
								if (state.backtracking>0) {state.failed=true; return retval;}
								final EarlyExitException eee =
									new EarlyExitException(43, input);
								throw eee;
						}
						cnt43++;
					} while (true);



				// AST REWRITE
				// elements: keywordMessageSegment
				// token labels:
				// rule labels: retval
				// token list labels:
				// rule list labels:
				if ( state.backtracking==0 ) {
					retval.tree = root_0;
					final RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

					root_0 = (GsTree)adaptor.nil();
					// 180:45: -> ^( KEYWORD_MESSAGE ( keywordMessageSegment )+ )
					{
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:180:48: ^( KEYWORD_MESSAGE ( keywordMessageSegment )+ )
						{
							GsTree root_1 = (GsTree)adaptor.nil();
							root_1 = (GsTree)adaptor.becomeRoot(adaptor.create(KEYWORD_MESSAGE, "KEYWORD_MESSAGE"), root_1);

							if ( !(stream_keywordMessageSegment.hasNext()) ) {
								throw new RewriteEarlyExitException();
							}
							while ( stream_keywordMessageSegment.hasNext() ) {
								adaptor.addChild(root_1, stream_keywordMessageSegment.nextTree());

							}
							stream_keywordMessageSegment.reset();

							adaptor.addChild(root_0, root_1);
						}

					}

					retval.tree = root_0;}
			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "keywordMessage"

	public static class cascadedMessage_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "cascadedMessage"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:182:1: cascadedMessage : ';' messageChain ;
	public final GsParser.cascadedMessage_return cascadedMessage() throws RecognitionException {
		final GsParser.cascadedMessage_return retval = new GsParser.cascadedMessage_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token char_literal134=null;
		GsParser.messageChain_return messageChain135 = null;


		GsTree char_literal134_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:182:16: ( ';' messageChain )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:182:18: ';' messageChain
			{
				root_0 = (GsTree)adaptor.nil();

				char_literal134=(Token)match(input,62,FOLLOW_62_in_cascadedMessage1401); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					char_literal134_tree = (GsTree)adaptor.create(char_literal134);
					adaptor.addChild(root_0, char_literal134_tree);
				}
				pushFollow(FOLLOW_messageChain_in_cascadedMessage1403);
				messageChain135=messageChain();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, messageChain135.getTree());
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "cascadedMessage"

	public static class binarySelector_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "binarySelector"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:184:1: binarySelector : ( BINARY_SELECTOR | PLUS | MINUS | AMPERSAND | BAR | LT | GT | COMMA );
	public final GsParser.binarySelector_return binarySelector() throws RecognitionException {
		final GsParser.binarySelector_return retval = new GsParser.binarySelector_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token BINARY_SELECTOR136=null;
		Token PLUS137=null;
		Token MINUS138=null;
		Token AMPERSAND139=null;
		Token BAR140=null;
		Token LT141=null;
		Token GT142=null;
		Token COMMA143=null;

		GsTree BINARY_SELECTOR136_tree=null;
		GsTree PLUS137_tree=null;
		GsTree MINUS138_tree=null;
		GsTree AMPERSAND139_tree=null;
		GsTree BAR140_tree=null;
		GsTree LT141_tree=null;
		GsTree GT142_tree=null;
		GsTree COMMA143_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:185:3: ( BINARY_SELECTOR | PLUS | MINUS | AMPERSAND | BAR | LT | GT | COMMA )
			int alt44=8;
			switch ( input.LA(1) ) {
				case BINARY_SELECTOR:
				{
					alt44=1;
				}
				break;
				case PLUS:
				{
					alt44=2;
				}
				break;
				case MINUS:
				{
					alt44=3;
				}
				break;
				case AMPERSAND:
				{
					alt44=4;
				}
				break;
				case BAR:
				{
					alt44=5;
				}
				break;
				case LT:
				{
					alt44=6;
				}
				break;
				case GT:
				{
					alt44=7;
				}
				break;
				case COMMA:
				{
					alt44=8;
				}
				break;
				default:
					if (state.backtracking>0) {state.failed=true; return retval;}
					final NoViableAltException nvae =
						new NoViableAltException("", 44, 0, input);

					throw nvae;
			}

			switch (alt44) {
				case 1 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:185:5: BINARY_SELECTOR
					{
						root_0 = (GsTree)adaptor.nil();

						BINARY_SELECTOR136=(Token)match(input,BINARY_SELECTOR,FOLLOW_BINARY_SELECTOR_in_binarySelector1415); if (state.failed) {
							return retval;
						}
						if ( state.backtracking==0 ) {
							BINARY_SELECTOR136_tree = (GsTree)adaptor.create(BINARY_SELECTOR136);
							root_0 = (GsTree)adaptor.becomeRoot(BINARY_SELECTOR136_tree, root_0);
						}

					}
					break;
				case 2 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:186:5: PLUS
				{
					root_0 = (GsTree)adaptor.nil();

					PLUS137=(Token)match(input,PLUS,FOLLOW_PLUS_in_binarySelector1422); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						PLUS137_tree = (GsTree)adaptor.create(PLUS137);
						root_0 = (GsTree)adaptor.becomeRoot(PLUS137_tree, root_0);
					}
					if ( state.backtracking==0 ) {
						PLUS137.setType(BINARY_SELECTOR);
					}

				}
				break;
				case 3 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:187:5: MINUS
				{
					root_0 = (GsTree)adaptor.nil();

					MINUS138=(Token)match(input,MINUS,FOLLOW_MINUS_in_binarySelector1433); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						MINUS138_tree = (GsTree)adaptor.create(MINUS138);
						root_0 = (GsTree)adaptor.becomeRoot(MINUS138_tree, root_0);
					}
					if ( state.backtracking==0 ) {
						MINUS138.setType(BINARY_SELECTOR);
					}

				}
				break;
				case 4 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:188:5: AMPERSAND
				{
					root_0 = (GsTree)adaptor.nil();

					AMPERSAND139=(Token)match(input,AMPERSAND,FOLLOW_AMPERSAND_in_binarySelector1442); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						AMPERSAND139_tree = (GsTree)adaptor.create(AMPERSAND139);
						root_0 = (GsTree)adaptor.becomeRoot(AMPERSAND139_tree, root_0);
					}
					if ( state.backtracking==0 ) {
						AMPERSAND139.setType(BINARY_SELECTOR);
					}

				}
				break;
				case 5 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:189:5: BAR
				{
					root_0 = (GsTree)adaptor.nil();

					BAR140=(Token)match(input,BAR,FOLLOW_BAR_in_binarySelector1451); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						BAR140_tree = (GsTree)adaptor.create(BAR140);
						root_0 = (GsTree)adaptor.becomeRoot(BAR140_tree, root_0);
					}
					if ( state.backtracking==0 ) {
						BAR140.setType(BINARY_SELECTOR);
					}

				}
				break;
				case 6 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:190:5: LT
				{
					root_0 = (GsTree)adaptor.nil();

					LT141=(Token)match(input,LT,FOLLOW_LT_in_binarySelector1462); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						LT141_tree = (GsTree)adaptor.create(LT141);
						root_0 = (GsTree)adaptor.becomeRoot(LT141_tree, root_0);
					}
					if ( state.backtracking==0 ) {
						LT141.setType(BINARY_SELECTOR);
					}

				}
				break;
				case 7 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:191:5: GT
				{
					root_0 = (GsTree)adaptor.nil();

					GT142=(Token)match(input,GT,FOLLOW_GT_in_binarySelector1471); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						GT142_tree = (GsTree)adaptor.create(GT142);
						root_0 = (GsTree)adaptor.becomeRoot(GT142_tree, root_0);
					}
					if ( state.backtracking==0 ) {
						GT142.setType(BINARY_SELECTOR);
					}

				}
				break;
				case 8 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:192:5: COMMA
				{
					root_0 = (GsTree)adaptor.nil();

					COMMA143=(Token)match(input,COMMA,FOLLOW_COMMA_in_binarySelector1480); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						COMMA143_tree = (GsTree)adaptor.create(COMMA143);
						root_0 = (GsTree)adaptor.becomeRoot(COMMA143_tree, root_0);
					}
					if ( state.backtracking==0 ) {
						COMMA143.setType(BINARY_SELECTOR);
					}

				}
				break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "binarySelector"

	public static class messageChainA_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "messageChainA"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:196:1: messageChainA : ( unaryMessageA unaryMessageChainA binaryMessageChainA ( keywordMessageA )? | binaryMessageA binaryMessageChainA ( keywordMessageA )? | keywordMessageA );
	public final GsParser.messageChainA_return messageChainA() throws RecognitionException {
		final GsParser.messageChainA_return retval = new GsParser.messageChainA_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.unaryMessageA_return unaryMessageA144 = null;

		GsParser.unaryMessageChainA_return unaryMessageChainA145 = null;

		GsParser.binaryMessageChainA_return binaryMessageChainA146 = null;

		GsParser.keywordMessageA_return keywordMessageA147 = null;

		GsParser.binaryMessageA_return binaryMessageA148 = null;

		GsParser.binaryMessageChainA_return binaryMessageChainA149 = null;

		GsParser.keywordMessageA_return keywordMessageA150 = null;

		GsParser.keywordMessageA_return keywordMessageA151 = null;



		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:197:3: ( unaryMessageA unaryMessageChainA binaryMessageChainA ( keywordMessageA )? | binaryMessageA binaryMessageChainA ( keywordMessageA )? | keywordMessageA )
			int alt47=3;
			final int LA47_0 = input.LA(1);

			if ( (LA47_0==ID) ) {
				final int LA47_1 = input.LA(2);

				if ( (LA47_1==COLON) ) {
					alt47=3;
				}
				else if ( (LA47_1==EOF||LA47_1==ID||(LA47_1>=LT && LA47_1<=BAR)||LA47_1==MINUS||(LA47_1>=COMMA && LA47_1<=RBRACKET)||(LA47_1>=AMPERSAND && LA47_1<=PLUS)||LA47_1==62) ) {
					alt47=1;
				}
				else {
					if (state.backtracking>0) {state.failed=true; return retval;}
					final NoViableAltException nvae =
						new NoViableAltException("", 47, 1, input);

					throw nvae;
				}
			}
			else if ( ((LA47_0>=LT && LA47_0<=BAR)||LA47_0==MINUS||(LA47_0>=AMPERSAND && LA47_0<=PLUS)) ) {
				alt47=2;
			}
			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				final NoViableAltException nvae =
					new NoViableAltException("", 47, 0, input);

				throw nvae;
			}
			switch (alt47) {
				case 1 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:197:5: unaryMessageA unaryMessageChainA binaryMessageChainA ( keywordMessageA )?
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_unaryMessageA_in_messageChainA1501);
					unaryMessageA144=unaryMessageA();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, unaryMessageA144.getTree());
					}
					pushFollow(FOLLOW_unaryMessageChainA_in_messageChainA1503);
					unaryMessageChainA145=unaryMessageChainA();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, unaryMessageChainA145.getTree());
					}
					pushFollow(FOLLOW_binaryMessageChainA_in_messageChainA1505);
					binaryMessageChainA146=binaryMessageChainA();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, binaryMessageChainA146.getTree());
					}
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:197:58: ( keywordMessageA )?
						int alt45=2;
					final int LA45_0 = input.LA(1);

					if ( (LA45_0==ID) ) {
						alt45=1;
					}
					switch (alt45) {
						case 1 :
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:197:59: keywordMessageA
						{
							pushFollow(FOLLOW_keywordMessageA_in_messageChainA1508);
							keywordMessageA147=keywordMessageA();

							state._fsp--;
							if (state.failed) {
								return retval;
							}
							if ( state.backtracking==0 ) {
								adaptor.addChild(root_0, keywordMessageA147.getTree());
							}

						}
						break;

					}


				}
				break;
				case 2 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:198:5: binaryMessageA binaryMessageChainA ( keywordMessageA )?
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_binaryMessageA_in_messageChainA1516);
					binaryMessageA148=binaryMessageA();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, binaryMessageA148.getTree());
					}
					pushFollow(FOLLOW_binaryMessageChainA_in_messageChainA1518);
					binaryMessageChainA149=binaryMessageChainA();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, binaryMessageChainA149.getTree());
					}
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:198:40: ( keywordMessageA )?
						int alt46=2;
					final int LA46_0 = input.LA(1);

					if ( (LA46_0==ID) ) {
						alt46=1;
					}
					switch (alt46) {
						case 1 :
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:198:41: keywordMessageA
						{
							pushFollow(FOLLOW_keywordMessageA_in_messageChainA1521);
							keywordMessageA150=keywordMessageA();

							state._fsp--;
							if (state.failed) {
								return retval;
							}
							if ( state.backtracking==0 ) {
								adaptor.addChild(root_0, keywordMessageA150.getTree());
							}

						}
						break;

					}


				}
				break;
				case 3 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:199:5: keywordMessageA
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_keywordMessageA_in_messageChainA1529);
					keywordMessageA151=keywordMessageA();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, keywordMessageA151.getTree());
					}

				}
				break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "messageChainA"

	public static class unaryMessageA_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "unaryMessageA"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:201:1: unaryMessageA : ID ;
	public final GsParser.unaryMessageA_return unaryMessageA() throws RecognitionException {
		final GsParser.unaryMessageA_return retval = new GsParser.unaryMessageA_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token ID152=null;

		GsTree ID152_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:201:14: ( ID )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:201:16: ID
			{
				root_0 = (GsTree)adaptor.nil();

				ID152=(Token)match(input,ID,FOLLOW_ID_in_unaryMessageA1538); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					ID152_tree = (GsTree)adaptor.create(ID152);
					adaptor.addChild(root_0, ID152_tree);
				}
				if ( state.backtracking==0 ) {
					ID152.setType(UNARY_MESSAGE);
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "unaryMessageA"

	public static class unaryMessageChainA_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "unaryMessageChainA"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:202:1: unaryMessageChainA : ( unaryMessageA )* ;
	public final GsParser.unaryMessageChainA_return unaryMessageChainA() throws RecognitionException {
		final GsParser.unaryMessageChainA_return retval = new GsParser.unaryMessageChainA_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.unaryMessageA_return unaryMessageA153 = null;



		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:202:19: ( ( unaryMessageA )* )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:202:21: ( unaryMessageA )*
			{
				root_0 = (GsTree)adaptor.nil();

				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:202:21: ( unaryMessageA )*
				loop48:
					do {
						int alt48=2;
						final int LA48_0 = input.LA(1);

						if ( (LA48_0==ID) ) {
							final int LA48_2 = input.LA(2);

							if ( (LA48_2==EOF||LA48_2==ID||(LA48_2>=LT && LA48_2<=BAR)||LA48_2==MINUS||(LA48_2>=COMMA && LA48_2<=RBRACKET)||(LA48_2>=AMPERSAND && LA48_2<=PLUS)||LA48_2==62) ) {
								alt48=1;
							}


						}


						switch (alt48) {
							case 1 :
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:202:22: unaryMessageA
								{
									pushFollow(FOLLOW_unaryMessageA_in_unaryMessageChainA1576);
									unaryMessageA153=unaryMessageA();

									state._fsp--;
									if (state.failed) {
										return retval;
									}
									if ( state.backtracking==0 ) {
										adaptor.addChild(root_0, unaryMessageA153.getTree());
									}

								}
								break;

							default :
								break loop48;
						}
					} while (true);


			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "unaryMessageChainA"

	public static class binaryMessageOperandA_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "binaryMessageOperandA"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:204:1: binaryMessageOperandA : operand unaryMessageChainA ;
	public final GsParser.binaryMessageOperandA_return binaryMessageOperandA() throws RecognitionException {
		final GsParser.binaryMessageOperandA_return retval = new GsParser.binaryMessageOperandA_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.operand_return operand154 = null;

		GsParser.unaryMessageChainA_return unaryMessageChainA155 = null;



		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:204:22: ( operand unaryMessageChainA )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:204:24: operand unaryMessageChainA
			{
				root_0 = (GsTree)adaptor.nil();

				pushFollow(FOLLOW_operand_in_binaryMessageOperandA1585);
				operand154=operand();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, operand154.getTree());
				}
				pushFollow(FOLLOW_unaryMessageChainA_in_binaryMessageOperandA1587);
				unaryMessageChainA155=unaryMessageChainA();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, unaryMessageChainA155.getTree());
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "binaryMessageOperandA"

	public static class binaryMessageA_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "binaryMessageA"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:205:1: binaryMessageA : binarySelectorA binaryMessageOperandA -> ^( BINARY_MESSAGE binarySelectorA binaryMessageOperandA ) ;
	public final GsParser.binaryMessageA_return binaryMessageA() throws RecognitionException {
		final GsParser.binaryMessageA_return retval = new GsParser.binaryMessageA_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.binarySelectorA_return binarySelectorA156 = null;

		GsParser.binaryMessageOperandA_return binaryMessageOperandA157 = null;


		final RewriteRuleSubtreeStream stream_binaryMessageOperandA=new RewriteRuleSubtreeStream(adaptor,"rule binaryMessageOperandA");
		final RewriteRuleSubtreeStream stream_binarySelectorA=new RewriteRuleSubtreeStream(adaptor,"rule binarySelectorA");
		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:205:15: ( binarySelectorA binaryMessageOperandA -> ^( BINARY_MESSAGE binarySelectorA binaryMessageOperandA ) )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:205:17: binarySelectorA binaryMessageOperandA
			{
				pushFollow(FOLLOW_binarySelectorA_in_binaryMessageA1593);
				binarySelectorA156=binarySelectorA();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_binarySelectorA.add(binarySelectorA156.getTree());
				}
				pushFollow(FOLLOW_binaryMessageOperandA_in_binaryMessageA1595);
				binaryMessageOperandA157=binaryMessageOperandA();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					stream_binaryMessageOperandA.add(binaryMessageOperandA157.getTree());
				}


				// AST REWRITE
				// elements: binarySelectorA, binaryMessageOperandA
				// token labels:
				// rule labels: retval
				// token list labels:
				// rule list labels:
				if ( state.backtracking==0 ) {
					retval.tree = root_0;
					final RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

					root_0 = (GsTree)adaptor.nil();
					// 205:59: -> ^( BINARY_MESSAGE binarySelectorA binaryMessageOperandA )
					{
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:205:62: ^( BINARY_MESSAGE binarySelectorA binaryMessageOperandA )
						{
							GsTree root_1 = (GsTree)adaptor.nil();
							root_1 = (GsTree)adaptor.becomeRoot(adaptor.create(BINARY_MESSAGE, "BINARY_MESSAGE"), root_1);

							adaptor.addChild(root_1, stream_binarySelectorA.nextTree());
							adaptor.addChild(root_1, stream_binaryMessageOperandA.nextTree());

							adaptor.addChild(root_0, root_1);
						}

					}

					retval.tree = root_0;}
			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "binaryMessageA"

	public static class binaryMessageChainA_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "binaryMessageChainA"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:206:1: binaryMessageChainA : ( binaryMessageA )* ;
	public final GsParser.binaryMessageChainA_return binaryMessageChainA() throws RecognitionException {
		final GsParser.binaryMessageChainA_return retval = new GsParser.binaryMessageChainA_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.binaryMessageA_return binaryMessageA158 = null;



		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:206:20: ( ( binaryMessageA )* )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:206:22: ( binaryMessageA )*
			{
				root_0 = (GsTree)adaptor.nil();

				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:206:22: ( binaryMessageA )*
				loop49:
					do {
						int alt49=2;
						final int LA49_0 = input.LA(1);

						if ( ((LA49_0>=LT && LA49_0<=BAR)||LA49_0==MINUS||(LA49_0>=AMPERSAND && LA49_0<=PLUS)) ) {
							alt49=1;
						}


						switch (alt49) {
							case 1 :
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:206:23: binaryMessageA
							{
								pushFollow(FOLLOW_binaryMessageA_in_binaryMessageChainA1616);
								binaryMessageA158=binaryMessageA();

								state._fsp--;
								if (state.failed) {
									return retval;
								}
								if ( state.backtracking==0 ) {
									adaptor.addChild(root_0, binaryMessageA158.getTree());
								}

							}
							break;

							default :
								break loop49;
						}
					} while (true);


			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "binaryMessageChainA"

	public static class keywordMessageArgumentA_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "keywordMessageArgumentA"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:208:1: keywordMessageArgumentA : binaryMessageOperandA binaryMessageChainA ;
	public final GsParser.keywordMessageArgumentA_return keywordMessageArgumentA() throws RecognitionException {
		final GsParser.keywordMessageArgumentA_return retval = new GsParser.keywordMessageArgumentA_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.binaryMessageOperandA_return binaryMessageOperandA159 = null;

		GsParser.binaryMessageChainA_return binaryMessageChainA160 = null;



		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:208:24: ( binaryMessageOperandA binaryMessageChainA )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:208:26: binaryMessageOperandA binaryMessageChainA
			{
				root_0 = (GsTree)adaptor.nil();

				pushFollow(FOLLOW_binaryMessageOperandA_in_keywordMessageArgumentA1625);
				binaryMessageOperandA159=binaryMessageOperandA();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, binaryMessageOperandA159.getTree());
				}
				pushFollow(FOLLOW_binaryMessageChainA_in_keywordMessageArgumentA1627);
				binaryMessageChainA160=binaryMessageChainA();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, binaryMessageChainA160.getTree());
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "keywordMessageArgumentA"

	public static class keywordMessageSegmentA_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "keywordMessageSegmentA"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:209:1: keywordMessageSegmentA : keyword keywordMessageArgumentA ;
	public final GsParser.keywordMessageSegmentA_return keywordMessageSegmentA() throws RecognitionException {
		final GsParser.keywordMessageSegmentA_return retval = new GsParser.keywordMessageSegmentA_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.keyword_return keyword161 = null;

		GsParser.keywordMessageArgumentA_return keywordMessageArgumentA162 = null;



		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:209:23: ( keyword keywordMessageArgumentA )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:209:25: keyword keywordMessageArgumentA
			{
				root_0 = (GsTree)adaptor.nil();

				pushFollow(FOLLOW_keyword_in_keywordMessageSegmentA1633);
				keyword161=keyword();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, keyword161.getTree());
				}
				pushFollow(FOLLOW_keywordMessageArgumentA_in_keywordMessageSegmentA1635);
				keywordMessageArgumentA162=keywordMessageArgumentA();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, keywordMessageArgumentA162.getTree());
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "keywordMessageSegmentA"

	public static class keywordMessageA_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "keywordMessageA"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:210:1: keywordMessageA : ( keywordMessageSegmentA )+ -> ^( KEYWORD_MESSAGE ( keywordMessageSegmentA )+ ) ;
	public final GsParser.keywordMessageA_return keywordMessageA() throws RecognitionException {
		final GsParser.keywordMessageA_return retval = new GsParser.keywordMessageA_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.keywordMessageSegmentA_return keywordMessageSegmentA163 = null;


		final RewriteRuleSubtreeStream stream_keywordMessageSegmentA=new RewriteRuleSubtreeStream(adaptor,"rule keywordMessageSegmentA");
		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:210:16: ( ( keywordMessageSegmentA )+ -> ^( KEYWORD_MESSAGE ( keywordMessageSegmentA )+ ) )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:210:18: ( keywordMessageSegmentA )+
			{
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:210:18: ( keywordMessageSegmentA )+
				int cnt50=0;
				loop50:
					do {
						int alt50=2;
						final int LA50_0 = input.LA(1);

						if ( (LA50_0==ID) ) {
							alt50=1;
						}


						switch (alt50) {
							case 1 :
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:210:19: keywordMessageSegmentA
							{
								pushFollow(FOLLOW_keywordMessageSegmentA_in_keywordMessageA1642);
								keywordMessageSegmentA163=keywordMessageSegmentA();

								state._fsp--;
								if (state.failed) {
									return retval;
								}
								if ( state.backtracking==0 ) {
									stream_keywordMessageSegmentA.add(keywordMessageSegmentA163.getTree());
								}

							}
							break;

							default :
								if ( cnt50 >= 1 ) {
									break loop50;
								}
								if (state.backtracking>0) {state.failed=true; return retval;}
								final EarlyExitException eee =
									new EarlyExitException(50, input);
								throw eee;
						}
						cnt50++;
					} while (true);



				// AST REWRITE
				// elements: keywordMessageSegmentA
				// token labels:
				// rule labels: retval
				// token list labels:
				// rule list labels:
				if ( state.backtracking==0 ) {
					retval.tree = root_0;
					final RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

					root_0 = (GsTree)adaptor.nil();
					// 210:47: -> ^( KEYWORD_MESSAGE ( keywordMessageSegmentA )+ )
					{
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:210:50: ^( KEYWORD_MESSAGE ( keywordMessageSegmentA )+ )
						{
							GsTree root_1 = (GsTree)adaptor.nil();
							root_1 = (GsTree)adaptor.becomeRoot(adaptor.create(KEYWORD_MESSAGE, "KEYWORD_MESSAGE"), root_1);

							if ( !(stream_keywordMessageSegmentA.hasNext()) ) {
								throw new RewriteEarlyExitException();
							}
							while ( stream_keywordMessageSegmentA.hasNext() ) {
								adaptor.addChild(root_1, stream_keywordMessageSegmentA.nextTree());

							}
							stream_keywordMessageSegmentA.reset();

							adaptor.addChild(root_0, root_1);
						}

					}

					retval.tree = root_0;}
			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "keywordMessageA"

	public static class cascadedMessageA_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "cascadedMessageA"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:212:1: cascadedMessageA : ';' messageChainA ;
	public final GsParser.cascadedMessageA_return cascadedMessageA() throws RecognitionException {
		final GsParser.cascadedMessageA_return retval = new GsParser.cascadedMessageA_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token char_literal164=null;
		GsParser.messageChainA_return messageChainA165 = null;


		GsTree char_literal164_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:212:17: ( ';' messageChainA )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:212:19: ';' messageChainA
			{
				root_0 = (GsTree)adaptor.nil();

				char_literal164=(Token)match(input,62,FOLLOW_62_in_cascadedMessageA1663); if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					char_literal164_tree = (GsTree)adaptor.create(char_literal164);
					adaptor.addChild(root_0, char_literal164_tree);
				}
				pushFollow(FOLLOW_messageChainA_in_cascadedMessageA1665);
				messageChainA165=messageChainA();

				state._fsp--;
				if (state.failed) {
					return retval;
				}
				if ( state.backtracking==0 ) {
					adaptor.addChild(root_0, messageChainA165.getTree());
				}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "cascadedMessageA"

	public static class binarySelectorA_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "binarySelectorA"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:214:1: binarySelectorA : ( BINARY_SELECTOR | PLUS | MINUS | AMPERSAND | BAR | LT | GT );
	public final GsParser.binarySelectorA_return binarySelectorA() throws RecognitionException {
		final GsParser.binarySelectorA_return retval = new GsParser.binarySelectorA_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		Token BINARY_SELECTOR166=null;
		Token PLUS167=null;
		Token MINUS168=null;
		Token AMPERSAND169=null;
		Token BAR170=null;
		Token LT171=null;
		Token GT172=null;

		GsTree BINARY_SELECTOR166_tree=null;
		GsTree PLUS167_tree=null;
		GsTree MINUS168_tree=null;
		GsTree AMPERSAND169_tree=null;
		GsTree BAR170_tree=null;
		GsTree LT171_tree=null;
		GsTree GT172_tree=null;

		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:215:3: ( BINARY_SELECTOR | PLUS | MINUS | AMPERSAND | BAR | LT | GT )
			int alt51=7;
			switch ( input.LA(1) ) {
				case BINARY_SELECTOR:
				{
					alt51=1;
				}
				break;
				case PLUS:
				{
					alt51=2;
				}
				break;
				case MINUS:
				{
					alt51=3;
				}
				break;
				case AMPERSAND:
				{
					alt51=4;
				}
				break;
				case BAR:
				{
					alt51=5;
				}
				break;
				case LT:
				{
					alt51=6;
				}
				break;
				case GT:
				{
					alt51=7;
				}
				break;
				default:
					if (state.backtracking>0) {state.failed=true; return retval;}
					final NoViableAltException nvae =
						new NoViableAltException("", 51, 0, input);

					throw nvae;
			}

			switch (alt51) {
				case 1 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:215:5: BINARY_SELECTOR
					{
						root_0 = (GsTree)adaptor.nil();

						BINARY_SELECTOR166=(Token)match(input,BINARY_SELECTOR,FOLLOW_BINARY_SELECTOR_in_binarySelectorA1677); if (state.failed) {
							return retval;
						}
						if ( state.backtracking==0 ) {
							BINARY_SELECTOR166_tree = (GsTree)adaptor.create(BINARY_SELECTOR166);
							root_0 = (GsTree)adaptor.becomeRoot(BINARY_SELECTOR166_tree, root_0);
						}

					}
					break;
				case 2 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:216:5: PLUS
				{
					root_0 = (GsTree)adaptor.nil();

					PLUS167=(Token)match(input,PLUS,FOLLOW_PLUS_in_binarySelectorA1684); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						PLUS167_tree = (GsTree)adaptor.create(PLUS167);
						root_0 = (GsTree)adaptor.becomeRoot(PLUS167_tree, root_0);
					}
					if ( state.backtracking==0 ) {
						PLUS167.setType(BINARY_SELECTOR);
					}

				}
				break;
				case 3 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:217:5: MINUS
				{
					root_0 = (GsTree)adaptor.nil();

					MINUS168=(Token)match(input,MINUS,FOLLOW_MINUS_in_binarySelectorA1693); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						MINUS168_tree = (GsTree)adaptor.create(MINUS168);
						root_0 = (GsTree)adaptor.becomeRoot(MINUS168_tree, root_0);
					}
					if ( state.backtracking==0 ) {
						MINUS168.setType(BINARY_SELECTOR);
					}

				}
				break;
				case 4 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:218:5: AMPERSAND
				{
					root_0 = (GsTree)adaptor.nil();

					AMPERSAND169=(Token)match(input,AMPERSAND,FOLLOW_AMPERSAND_in_binarySelectorA1702); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						AMPERSAND169_tree = (GsTree)adaptor.create(AMPERSAND169);
						root_0 = (GsTree)adaptor.becomeRoot(AMPERSAND169_tree, root_0);
					}
					if ( state.backtracking==0 ) {
						AMPERSAND169.setType(BINARY_SELECTOR);
					}

				}
				break;
				case 5 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:219:5: BAR
				{
					root_0 = (GsTree)adaptor.nil();

					BAR170=(Token)match(input,BAR,FOLLOW_BAR_in_binarySelectorA1711); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						BAR170_tree = (GsTree)adaptor.create(BAR170);
						root_0 = (GsTree)adaptor.becomeRoot(BAR170_tree, root_0);
					}
					if ( state.backtracking==0 ) {
						BAR170.setType(BINARY_SELECTOR);
					}

				}
				break;
				case 6 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:220:5: LT
				{
					root_0 = (GsTree)adaptor.nil();

					LT171=(Token)match(input,LT,FOLLOW_LT_in_binarySelectorA1720); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						LT171_tree = (GsTree)adaptor.create(LT171);
						root_0 = (GsTree)adaptor.becomeRoot(LT171_tree, root_0);
					}
					if ( state.backtracking==0 ) {
						LT171.setType(BINARY_SELECTOR);
					}

				}
				break;
				case 7 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:221:5: GT
				{
					root_0 = (GsTree)adaptor.nil();

					GT172=(Token)match(input,GT,FOLLOW_GT_in_binarySelectorA1729); if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						GT172_tree = (GsTree)adaptor.create(GT172);
						root_0 = (GsTree)adaptor.becomeRoot(GT172_tree, root_0);
					}
					if ( state.backtracking==0 ) {
						GT172.setType(BINARY_SELECTOR);
					}

				}
				break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "binarySelectorA"

	public static class selector_return extends ParserRuleReturnScope {
		GsTree tree;
		@Override
		public Object getTree() { return tree; }
	}

	// $ANTLR start "selector"
	// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:224:1: selector : ( unaryMessage | binarySelector | ( keyword )+ -> ^( KEYWORD_MESSAGE ( keyword )+ ) );
	public final GsParser.selector_return selector() throws RecognitionException {
		final GsParser.selector_return retval = new GsParser.selector_return();
		retval.start = input.LT(1);

		GsTree root_0 = null;

		GsParser.unaryMessage_return unaryMessage173 = null;

		GsParser.binarySelector_return binarySelector174 = null;

		GsParser.keyword_return keyword175 = null;


		final RewriteRuleSubtreeStream stream_keyword=new RewriteRuleSubtreeStream(adaptor,"rule keyword");
		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:225:3: ( unaryMessage | binarySelector | ( keyword )+ -> ^( KEYWORD_MESSAGE ( keyword )+ ) )
			int alt53=3;
			final int LA53_0 = input.LA(1);

			if ( (LA53_0==ID) ) {
				final int LA53_1 = input.LA(2);

				if ( (LA53_1==COLON) ) {
					alt53=3;
				}
				else if ( (LA53_1==EOF) ) {
					alt53=1;
				}
				else {
					if (state.backtracking>0) {state.failed=true; return retval;}
					final NoViableAltException nvae =
						new NoViableAltException("", 53, 1, input);

					throw nvae;
				}
			}
			else if ( ((LA53_0>=LT && LA53_0<=BAR)||LA53_0==MINUS||LA53_0==COMMA||(LA53_0>=AMPERSAND && LA53_0<=PLUS)) ) {
				alt53=2;
			}
			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				final NoViableAltException nvae =
					new NoViableAltException("", 53, 0, input);

				throw nvae;
			}
			switch (alt53) {
				case 1 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:225:5: unaryMessage
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_unaryMessage_in_selector1751);
					unaryMessage173=unaryMessage();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, unaryMessage173.getTree());
					}

				}
				break;
				case 2 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:226:5: binarySelector
				{
					root_0 = (GsTree)adaptor.nil();

					pushFollow(FOLLOW_binarySelector_in_selector1757);
					binarySelector174=binarySelector();

					state._fsp--;
					if (state.failed) {
						return retval;
					}
					if ( state.backtracking==0 ) {
						adaptor.addChild(root_0, binarySelector174.getTree());
					}

				}
				break;
				case 3 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:227:5: ( keyword )+
				{
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:227:5: ( keyword )+
					int cnt52=0;
					loop52:
						do {
							int alt52=2;
							final int LA52_0 = input.LA(1);

							if ( (LA52_0==ID) ) {
								alt52=1;
							}


							switch (alt52) {
								case 1 :
									// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:227:6: keyword
									{
										pushFollow(FOLLOW_keyword_in_selector1764);
										keyword175=keyword();

										state._fsp--;
										if (state.failed) {
											return retval;
										}
										if ( state.backtracking==0 ) {
											stream_keyword.add(keyword175.getTree());
										}

									}
									break;

								default :
									if ( cnt52 >= 1 ) {
										break loop52;
									}
									if (state.backtracking>0) {state.failed=true; return retval;}
									final EarlyExitException eee =
										new EarlyExitException(52, input);
									throw eee;
							}
							cnt52++;
						} while (true);



					// AST REWRITE
					// elements: keyword
					// token labels:
					// rule labels: retval
					// token list labels:
					// rule list labels:
					if ( state.backtracking==0 ) {
						retval.tree = root_0;
						final RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

						root_0 = (GsTree)adaptor.nil();
						// 227:16: -> ^( KEYWORD_MESSAGE ( keyword )+ )
						{
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:227:19: ^( KEYWORD_MESSAGE ( keyword )+ )
							{
								GsTree root_1 = (GsTree)adaptor.nil();
								root_1 = (GsTree)adaptor.becomeRoot(adaptor.create(KEYWORD_MESSAGE, "KEYWORD_MESSAGE"), root_1);

								if ( !(stream_keyword.hasNext()) ) {
									throw new RewriteEarlyExitException();
								}
								while ( stream_keyword.hasNext() ) {
									adaptor.addChild(root_1, stream_keyword.nextTree());

								}
								stream_keyword.reset();

								adaptor.addChild(root_0, root_1);
							}

						}

						retval.tree = root_0;}
				}
				break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {

				retval.tree = (GsTree)adaptor.rulePostProcessing(root_0);
				adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (final RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (GsTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

		}
		finally {
		}
		return retval;
	}
	// $ANTLR end "selector"

	// $ANTLR start synpred62_Gs
	public final void synpred62_Gs_fragment() throws RecognitionException {
		// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:159:33: ( binarySelector selectionBlockOperand )
		// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:159:33: binarySelector selectionBlockOperand
		{
			pushFollow(FOLLOW_binarySelector_in_synpred62_Gs1183);
			binarySelector();

			state._fsp--;
			if (state.failed) {
				return ;
			}
			pushFollow(FOLLOW_selectionBlockOperand_in_synpred62_Gs1185);
			selectionBlockOperand();

			state._fsp--;
			if (state.failed) {
				return ;
			}

		}
	}
	// $ANTLR end synpred62_Gs

	// Delegated rules

	public final boolean synpred62_Gs() {
		state.backtracking++;
		final int start = input.mark();
		try {
			synpred62_Gs_fragment(); // can never throw exception
		} catch (final RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		final boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}


	protected DFA20 dfa20 = new DFA20(this);
	static final String DFA20_eotS =
		"\13\uffff";
	static final String DFA20_eofS =
		"\13\uffff";
	static final String DFA20_minS =
		"\1\24\1\uffff\1\24\10\uffff";
	static final String DFA20_maxS =
		"\1\75\1\uffff\1\25\10\uffff";
	static final String DFA20_acceptS =
		"\1\uffff\1\1\1\uffff\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11";
	static final String DFA20_specialS =
		"\13\uffff}>";
	static final String[] DFA20_transitionS = {
		"\1\3\1\4\10\uffff\6\1\1\2\1\5\1\6\1\7\2\uffff\1\11\2\uffff"+
		"\1\12\17\uffff\1\10",
		"",
		"\1\3\1\4",
		"",
		"",
		"",
		"",
		"",
		"",
		"",
		""
	};

	static final short[] DFA20_eot = DFA.unpackEncodedString(DFA20_eotS);
	static final short[] DFA20_eof = DFA.unpackEncodedString(DFA20_eofS);
	static final char[] DFA20_min = DFA.unpackEncodedStringToUnsignedChars(DFA20_minS);
	static final char[] DFA20_max = DFA.unpackEncodedStringToUnsignedChars(DFA20_maxS);
	static final short[] DFA20_accept = DFA.unpackEncodedString(DFA20_acceptS);
	static final short[] DFA20_special = DFA.unpackEncodedString(DFA20_specialS);
	static final short[][] DFA20_transition;

	static {
		final int numStates = DFA20_transitionS.length;
		DFA20_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA20_transition[i] = DFA.unpackEncodedString(DFA20_transitionS[i]);
		}
	}

	class DFA20 extends DFA {

		public DFA20(final BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 20;
			this.eot = DFA20_eot;
			this.eof = DFA20_eof;
			this.min = DFA20_min;
			this.max = DFA20_max;
			this.accept = DFA20_accept;
			this.special = DFA20_special;
			this.transition = DFA20_transition;
		}
		@Override
		public String getDescription() {
			return "112:1: literal : ( ( NIL | TRUE | FALSE | SELF | SUPER | THIS_CONTEXT ) | ( MINUS )? INT_LITERAL | ( MINUS )? FLOAT_LITERAL | CHARACTER_LITERAL | STRING_LITERAL | SYMBOL_LITERAL | arrayLiteral | block | selectionBlock );";
		}
	}


	public static final BitSet FOLLOW_methodHeader_in_method180 = new BitSet(new long[]{0x200025FFD5700000L});
	public static final BitSet FOLLOW_methodBody_in_method184 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unaryMethodHeader_in_methodHeader211 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_binaryMethodHeader_in_methodHeader249 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_keywordMethodHeader_in_methodHeader286 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_unaryMethodHeader332 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_binarySelector_in_binaryMethodHeader338 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_ID_in_binaryMethodHeader340 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_keyword_in_keywordMethodHeader352 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_ID_in_keywordMethodHeader354 = new BitSet(new long[]{0x0000000000400002L});
	public static final BitSet FOLLOW_ID_in_keyword373 = new BitSet(new long[]{0x0000000000800000L});
	public static final BitSet FOLLOW_COLON_in_keyword375 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_primitive_in_methodBody385 = new BitSet(new long[]{0x200025FFD4700000L});
	public static final BitSet FOLLOW_temporaries_in_methodBody388 = new BitSet(new long[]{0x200025FFD0700000L});
	public static final BitSet FOLLOW_statements_in_methodBody390 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LT_in_primitive397 = new BitSet(new long[]{0x1C00000002000000L});
	public static final BitSet FOLLOW_primitiveModifier_in_primitive400 = new BitSet(new long[]{0x0400000002000000L});
	public static final BitSet FOLLOW_58_in_primitive404 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_INT_LITERAL_in_primitive407 = new BitSet(new long[]{0x0000000002000000L});
	public static final BitSet FOLLOW_GT_in_primitive412 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_primitiveModifier0 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_BAR_in_temporaries443 = new BitSet(new long[]{0x0000000004400000L});
	public static final BitSet FOLLOW_ID_in_temporaries445 = new BitSet(new long[]{0x0000000004400000L});
	public static final BitSet FOLLOW_BAR_in_temporaries448 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_statement_in_statements484 = new BitSet(new long[]{0x0000000008000002L});
	public static final BitSet FOLLOW_DOT_in_statements487 = new BitSet(new long[]{0x200025FFC0700000L});
	public static final BitSet FOLLOW_statement_in_statements490 = new BitSet(new long[]{0x0000000008000002L});
	public static final BitSet FOLLOW_DOT_in_statements495 = new BitSet(new long[]{0x200025FFD8700000L});
	public static final BitSet FOLLOW_finalStatement_in_statements498 = new BitSet(new long[]{0x0000000008000002L});
	public static final BitSet FOLLOW_DOT_in_statements503 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_finalStatement_in_statements528 = new BitSet(new long[]{0x0000000008000002L});
	public static final BitSet FOLLOW_DOT_in_statements530 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_assignment_in_statement586 = new BitSet(new long[]{0x200025FFC0700000L});
	public static final BitSet FOLLOW_expr_in_statement590 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_RETURN_in_finalStatement596 = new BitSet(new long[]{0x200025FFC0700000L});
	public static final BitSet FOLLOW_statement_in_finalStatement599 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_assignment609 = new BitSet(new long[]{0x0000000020000000L});
	public static final BitSet FOLLOW_ASSIGN_in_assignment611 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_operand_in_expr622 = new BitSet(new long[]{0x2003ADFFC7700002L});
	public static final BitSet FOLLOW_messageChain_in_expr625 = new BitSet(new long[]{0x4000000000000002L});
	public static final BitSet FOLLOW_cascadedMessage_in_expr628 = new BitSet(new long[]{0x4000000000000002L});
	public static final BitSet FOLLOW_literal_in_operand659 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_operand665 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_nestedExpr_in_operand671 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_arrayBuilder_in_operand678 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_literal694 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_MINUS_in_literal712 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_INT_LITERAL_in_literal716 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_MINUS_in_literal722 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_FLOAT_LITERAL_in_literal726 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_CHARACTER_LITERAL_in_literal732 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_LITERAL_in_literal738 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_SYMBOL_LITERAL_in_literal744 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_arrayLiteral_in_literal750 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_block_in_literal756 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_selectionBlock_in_literal762 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_61_in_arrayLiteral775 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_array_in_arrayLiteral778 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LPAREN_in_array784 = new BitSet(new long[]{0x200003FFC0500000L});
	public static final BitSet FOLLOW_arrayItem_in_array788 = new BitSet(new long[]{0x200003FFC0500000L});
	public static final BitSet FOLLOW_RPAREN_in_array792 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_array_in_arrayItem815 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_arrayLiteral_in_arrayItem821 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_MINUS_in_arrayItem827 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_INT_LITERAL_in_arrayItem831 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_arrayItem837 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_SYMBOL_LITERAL_in_arrayItem843 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_LITERAL_in_arrayItem849 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_CHARACTER_LITERAL_in_arrayItem855 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_arrayItem861 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_61_in_arrayBuilder885 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_LBRACKET_in_arrayBuilder887 = new BitSet(new long[]{0x20003DFFC0700000L});
	public static final BitSet FOLLOW_exprA_in_arrayBuilder890 = new BitSet(new long[]{0x0000180000000000L});
	public static final BitSet FOLLOW_COMMA_in_arrayBuilder893 = new BitSet(new long[]{0x200025FFC0700000L});
	public static final BitSet FOLLOW_exprA_in_arrayBuilder895 = new BitSet(new long[]{0x0000180000000000L});
	public static final BitSet FOLLOW_COMMA_in_arrayBuilder901 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_RBRACKET_in_arrayBuilder904 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_operand_in_exprA919 = new BitSet(new long[]{0x2003A5FFC7700002L});
	public static final BitSet FOLLOW_messageChainA_in_exprA922 = new BitSet(new long[]{0x4000000000000002L});
	public static final BitSet FOLLOW_cascadedMessageA_in_exprA925 = new BitSet(new long[]{0x4000000000000002L});
	public static final BitSet FOLLOW_LBRACKET_in_block937 = new BitSet(new long[]{0x200035FFD4F00000L});
	public static final BitSet FOLLOW_parameters_in_block940 = new BitSet(new long[]{0x200035FFD4700000L});
	public static final BitSet FOLLOW_temporaries_in_block942 = new BitSet(new long[]{0x200035FFD0700000L});
	public static final BitSet FOLLOW_statements_in_block944 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_RBRACKET_in_block946 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_blockParameter_in_parameters974 = new BitSet(new long[]{0x0000000004800000L});
	public static final BitSet FOLLOW_BAR_in_parameters978 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_COLON_in_blockParameter1085 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_ID_in_blockParameter1088 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LBRACE_in_selectionBlock1099 = new BitSet(new long[]{0x0000000000800000L});
	public static final BitSet FOLLOW_blockParameter_in_selectionBlock1102 = new BitSet(new long[]{0x0000000004000000L});
	public static final BitSet FOLLOW_BAR_in_selectionBlock1104 = new BitSet(new long[]{0x200025FFC0700000L});
	public static final BitSet FOLLOW_predicate_in_selectionBlock1107 = new BitSet(new long[]{0x0000400000000000L});
	public static final BitSet FOLLOW_RBRACE_in_selectionBlock1109 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_anyTerm_in_predicate1140 = new BitSet(new long[]{0x0000800000000002L});
	public static final BitSet FOLLOW_AMPERSAND_in_predicate1143 = new BitSet(new long[]{0x200025FFC0700000L});
	public static final BitSet FOLLOW_term_in_predicate1145 = new BitSet(new long[]{0x0000800000000002L});
	public static final BitSet FOLLOW_parenTerm_in_predicate1152 = new BitSet(new long[]{0x0000800000000002L});
	public static final BitSet FOLLOW_AMPERSAND_in_predicate1155 = new BitSet(new long[]{0x200025FFC0700000L});
	public static final BitSet FOLLOW_term_in_predicate1157 = new BitSet(new long[]{0x0000800000000002L});
	public static final BitSet FOLLOW_parenTerm_in_term1171 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_selectionBlockOperand_in_term1173 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_selectionBlockOperand_in_anyTerm1180 = new BitSet(new long[]{0x0003881007000002L});
	public static final BitSet FOLLOW_binarySelector_in_anyTerm1183 = new BitSet(new long[]{0x200024FFC0700000L});
	public static final BitSet FOLLOW_selectionBlockOperand_in_anyTerm1185 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LPAREN_in_parenTerm1193 = new BitSet(new long[]{0x200024FFC0700000L});
	public static final BitSet FOLLOW_anyTerm_in_parenTerm1196 = new BitSet(new long[]{0x0000020000000000L});
	public static final BitSet FOLLOW_RPAREN_in_parenTerm1198 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_selectionBlockOperand1206 = new BitSet(new long[]{0x0000000008000002L});
	public static final BitSet FOLLOW_DOT_in_selectionBlockOperand1208 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_ID_in_selectionBlockOperand1210 = new BitSet(new long[]{0x0000000008000002L});
	public static final BitSet FOLLOW_literal_in_selectionBlockOperand1216 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LPAREN_in_nestedExpr1223 = new BitSet(new long[]{0x200025FFC0700000L});
	public static final BitSet FOLLOW_statement_in_nestedExpr1225 = new BitSet(new long[]{0x0000020000000000L});
	public static final BitSet FOLLOW_RPAREN_in_nestedExpr1227 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unaryMessage_in_messageChain1239 = new BitSet(new long[]{0x2003ADFFC7700000L});
	public static final BitSet FOLLOW_unaryMessageChain_in_messageChain1241 = new BitSet(new long[]{0x2003ADFFC7700000L});
	public static final BitSet FOLLOW_binaryMessageChain_in_messageChain1243 = new BitSet(new long[]{0x2003ADFFC7700002L});
	public static final BitSet FOLLOW_keywordMessage_in_messageChain1246 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_binaryMessage_in_messageChain1254 = new BitSet(new long[]{0x2003ADFFC7700000L});
	public static final BitSet FOLLOW_binaryMessageChain_in_messageChain1256 = new BitSet(new long[]{0x2003ADFFC7700002L});
	public static final BitSet FOLLOW_keywordMessage_in_messageChain1259 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_keywordMessage_in_messageChain1267 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_unaryMessage1276 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unaryMessage_in_unaryMessageChain1314 = new BitSet(new long[]{0x0000000000400002L});
	public static final BitSet FOLLOW_operand_in_binaryMessageOperand1323 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_unaryMessageChain_in_binaryMessageOperand1325 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_binarySelector_in_binaryMessage1331 = new BitSet(new long[]{0x2003ADFFC7700000L});
	public static final BitSet FOLLOW_binaryMessageOperand_in_binaryMessage1333 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_binaryMessage_in_binaryMessageChain1354 = new BitSet(new long[]{0x0003881007000002L});
	public static final BitSet FOLLOW_binaryMessageOperand_in_keywordMessageArgument1363 = new BitSet(new long[]{0x0003881007000000L});
	public static final BitSet FOLLOW_binaryMessageChain_in_keywordMessageArgument1365 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_keyword_in_keywordMessageSegment1371 = new BitSet(new long[]{0x2003ADFFC7700000L});
	public static final BitSet FOLLOW_keywordMessageArgument_in_keywordMessageSegment1373 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_keywordMessageSegment_in_keywordMessage1380 = new BitSet(new long[]{0x2003ADFFC7700002L});
	public static final BitSet FOLLOW_62_in_cascadedMessage1401 = new BitSet(new long[]{0x2003ADFFC7700000L});
	public static final BitSet FOLLOW_messageChain_in_cascadedMessage1403 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_BINARY_SELECTOR_in_binarySelector1415 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PLUS_in_binarySelector1422 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_MINUS_in_binarySelector1433 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_AMPERSAND_in_binarySelector1442 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_BAR_in_binarySelector1451 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LT_in_binarySelector1462 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_GT_in_binarySelector1471 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_COMMA_in_binarySelector1480 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unaryMessageA_in_messageChainA1501 = new BitSet(new long[]{0x2003A5FFC7700000L});
	public static final BitSet FOLLOW_unaryMessageChainA_in_messageChainA1503 = new BitSet(new long[]{0x2003A5FFC7700000L});
	public static final BitSet FOLLOW_binaryMessageChainA_in_messageChainA1505 = new BitSet(new long[]{0x2003A5FFC7700002L});
	public static final BitSet FOLLOW_keywordMessageA_in_messageChainA1508 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_binaryMessageA_in_messageChainA1516 = new BitSet(new long[]{0x2003A5FFC7700000L});
	public static final BitSet FOLLOW_binaryMessageChainA_in_messageChainA1518 = new BitSet(new long[]{0x2003A5FFC7700002L});
	public static final BitSet FOLLOW_keywordMessageA_in_messageChainA1521 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_keywordMessageA_in_messageChainA1529 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_unaryMessageA1538 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unaryMessageA_in_unaryMessageChainA1576 = new BitSet(new long[]{0x0000000000400002L});
	public static final BitSet FOLLOW_operand_in_binaryMessageOperandA1585 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_unaryMessageChainA_in_binaryMessageOperandA1587 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_binarySelectorA_in_binaryMessageA1593 = new BitSet(new long[]{0x2003A5FFC7700000L});
	public static final BitSet FOLLOW_binaryMessageOperandA_in_binaryMessageA1595 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_binaryMessageA_in_binaryMessageChainA1616 = new BitSet(new long[]{0x0003801007000002L});
	public static final BitSet FOLLOW_binaryMessageOperandA_in_keywordMessageArgumentA1625 = new BitSet(new long[]{0x0003801007000000L});
	public static final BitSet FOLLOW_binaryMessageChainA_in_keywordMessageArgumentA1627 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_keyword_in_keywordMessageSegmentA1633 = new BitSet(new long[]{0x2003A5FFC7700000L});
	public static final BitSet FOLLOW_keywordMessageArgumentA_in_keywordMessageSegmentA1635 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_keywordMessageSegmentA_in_keywordMessageA1642 = new BitSet(new long[]{0x2003A5FFC7700002L});
	public static final BitSet FOLLOW_62_in_cascadedMessageA1663 = new BitSet(new long[]{0x2003A5FFC7700000L});
	public static final BitSet FOLLOW_messageChainA_in_cascadedMessageA1665 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_BINARY_SELECTOR_in_binarySelectorA1677 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PLUS_in_binarySelectorA1684 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_MINUS_in_binarySelectorA1693 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_AMPERSAND_in_binarySelectorA1702 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_BAR_in_binarySelectorA1711 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LT_in_binarySelectorA1720 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_GT_in_binarySelectorA1729 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unaryMessage_in_selector1751 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_binarySelector_in_selector1757 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_keyword_in_selector1764 = new BitSet(new long[]{0x0000000000400002L});
	public static final BitSet FOLLOW_binarySelector_in_synpred62_Gs1183 = new BitSet(new long[]{0x200024FFC0700000L});
	public static final BitSet FOLLOW_selectionBlockOperand_in_synpred62_Gs1185 = new BitSet(new long[]{0x0000000000000002L});

}