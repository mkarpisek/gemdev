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

package net.karpisek.gemdev.lang.lexer;


import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
public class GsLexer extends Lexer {
	public static final int EXPONENT=52;
	public static final int LT=24;
	public static final int BINARY_SELECTOR=48;
	public static final int T__62=62;
	public static final int PARAMETERS=8;
	public static final int DIGITS=51;
	public static final int LBRACE=45;
	public static final int KEYWORD_METHOD=6;
	public static final int UNARY_MESSAGE=14;
	public static final int ID=22;
	public static final int T__61=61;
	public static final int T__60=60;
	public static final int EOF=-1;
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
	public static final int MINUS=36;
	public static final int LETTERS=50;
	public static final int TRUE=31;
	public static final int TEMPORARIES=9;
	public static final int COLON=23;
	public static final int FLOAT_LITERAL=21;
	public static final int WS=57;
	public static final int THIS_CONTEXT=35;
	public static final int NIL=30;
	public static final int NEWLINE=56;
	public static final int BINARY_METHOD=5;
	public static final int BLOCK=12;
	public static final int ASSIGN=29;
	public static final int GT=25;
	public static final int STATEMENTS=10;
	public static final int FALSE=32;
	public static final int SELF=33;
	public static final int BLOCK_PARAMETER=18;
	public static final int BAR=26;

	// delegates
	// delegators

	public GsLexer() {}
	public GsLexer(final CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public GsLexer(final CharStream input, final RecognizerSharedState state) {
		super(input,state);

	}
	@Override
	public String getGrammarFileName() { return "D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g"; }

	// $ANTLR start "T__58"
	public final void mT__58() throws RecognitionException {
		try {
			final int _type = T__58;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:11:7: ( 'primitive:' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:11:9: 'primitive:'
			{
				match("primitive:"); if (state.failed) {
					return ;
				}


			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "T__58"

	// $ANTLR start "T__59"
	public final void mT__59() throws RecognitionException {
		try {
			final int _type = T__59;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:12:7: ( 'protected' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:12:9: 'protected'
			{
				match("protected"); if (state.failed) {
					return ;
				}


			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "T__59"

	// $ANTLR start "T__60"
	public final void mT__60() throws RecognitionException {
		try {
			final int _type = T__60;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:13:7: ( 'unprotected' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:13:9: 'unprotected'
			{
				match("unprotected"); if (state.failed) {
					return ;
				}


			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "T__60"

	// $ANTLR start "T__61"
	public final void mT__61() throws RecognitionException {
		try {
			final int _type = T__61;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:14:7: ( '#' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:14:9: '#'
			{
				match('#'); if (state.failed) {
					return ;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "T__61"

	// $ANTLR start "T__62"
	public final void mT__62() throws RecognitionException {
		try {
			final int _type = T__62;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:15:7: ( ';' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:15:9: ';'
			{
				match(';'); if (state.failed) {
					return ;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "T__62"

	// $ANTLR start "NIL"
	public final void mNIL() throws RecognitionException {
		try {
			final int _type = NIL;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:230:4: ( 'nil' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:230:6: 'nil'
			{
				match("nil"); if (state.failed) {
					return ;
				}


			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "NIL"

	// $ANTLR start "TRUE"
	public final void mTRUE() throws RecognitionException {
		try {
			final int _type = TRUE;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:231:5: ( 'true' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:231:7: 'true'
			{
				match("true"); if (state.failed) {
					return ;
				}


			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "TRUE"

	// $ANTLR start "FALSE"
	public final void mFALSE() throws RecognitionException {
		try {
			final int _type = FALSE;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:232:6: ( 'false' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:232:8: 'false'
			{
				match("false"); if (state.failed) {
					return ;
				}


			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "FALSE"

	// $ANTLR start "SELF"
	public final void mSELF() throws RecognitionException {
		try {
			final int _type = SELF;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:233:5: ( 'self' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:233:7: 'self'
			{
				match("self"); if (state.failed) {
					return ;
				}


			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "SELF"

	// $ANTLR start "SUPER"
	public final void mSUPER() throws RecognitionException {
		try {
			final int _type = SUPER;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:234:6: ( 'super' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:234:8: 'super'
			{
				match("super"); if (state.failed) {
					return ;
				}


			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "SUPER"

	// $ANTLR start "THIS_CONTEXT"
	public final void mTHIS_CONTEXT() throws RecognitionException {
		try {
			final int _type = THIS_CONTEXT;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:235:13: ( 'thisContext' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:235:15: 'thisContext'
			{
				match("thisContext"); if (state.failed) {
					return ;
				}


			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "THIS_CONTEXT"

	// $ANTLR start "ID"
	public final void mID() throws RecognitionException {
		try {
			final int _type = ID;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:238:3: ( LETTERS ( '_' | LETTERS | DIGITS )* | '_' ( '_' | LETTERS | DIGITS )+ )
			int alt3=2;
			final int LA3_0 = input.LA(1);

			if ( ((LA3_0>='A' && LA3_0<='Z')||(LA3_0>='a' && LA3_0<='z')) ) {
				alt3=1;
			}
			else if ( (LA3_0=='_') ) {
				alt3=2;
			}
			else {
				if (state.backtracking>0) {state.failed=true; return ;}
				final NoViableAltException nvae =
					new NoViableAltException("", 3, 0, input);

				throw nvae;
			}
			switch (alt3) {
				case 1 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:238:5: LETTERS ( '_' | LETTERS | DIGITS )*
				{
					mLETTERS(); if (state.failed) {
						return ;
					}
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:238:13: ( '_' | LETTERS | DIGITS )*
					loop1:
						do {
							int alt1=4;
							switch ( input.LA(1) ) {
								case '_':
								{
									alt1=1;
								}
								break;
								case 'A':
								case 'B':
								case 'C':
								case 'D':
								case 'E':
								case 'F':
								case 'G':
								case 'H':
								case 'I':
								case 'J':
								case 'K':
								case 'L':
								case 'M':
								case 'N':
								case 'O':
								case 'P':
								case 'Q':
								case 'R':
								case 'S':
								case 'T':
								case 'U':
								case 'V':
								case 'W':
								case 'X':
								case 'Y':
								case 'Z':
								case 'a':
								case 'b':
								case 'c':
								case 'd':
								case 'e':
								case 'f':
								case 'g':
								case 'h':
								case 'i':
								case 'j':
								case 'k':
								case 'l':
								case 'm':
								case 'n':
								case 'o':
								case 'p':
								case 'q':
								case 'r':
								case 's':
								case 't':
								case 'u':
								case 'v':
								case 'w':
								case 'x':
								case 'y':
								case 'z':
								{
									alt1=2;
								}
								break;
								case '0':
								case '1':
								case '2':
								case '3':
								case '4':
								case '5':
								case '6':
								case '7':
								case '8':
								case '9':
								{
									alt1=3;
								}
								break;

							}

							switch (alt1) {
								case 1 :
									// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:238:14: '_'
								{
									match('_'); if (state.failed) {
										return ;
									}

								}
								break;
								case 2 :
									// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:238:18: LETTERS
								{
									mLETTERS(); if (state.failed) {
										return ;
									}

								}
								break;
								case 3 :
									// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:238:26: DIGITS
								{
									mDIGITS(); if (state.failed) {
										return ;
									}

								}
								break;

								default :
									break loop1;
							}
						} while (true);


				}
				break;
				case 2 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:239:5: '_' ( '_' | LETTERS | DIGITS )+
				{
					match('_'); if (state.failed) {
						return ;
					}
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:239:9: ( '_' | LETTERS | DIGITS )+
					int cnt2=0;
					loop2:
						do {
							int alt2=4;
							switch ( input.LA(1) ) {
								case '_':
								{
									alt2=1;
								}
								break;
								case 'A':
								case 'B':
								case 'C':
								case 'D':
								case 'E':
								case 'F':
								case 'G':
								case 'H':
								case 'I':
								case 'J':
								case 'K':
								case 'L':
								case 'M':
								case 'N':
								case 'O':
								case 'P':
								case 'Q':
								case 'R':
								case 'S':
								case 'T':
								case 'U':
								case 'V':
								case 'W':
								case 'X':
								case 'Y':
								case 'Z':
								case 'a':
								case 'b':
								case 'c':
								case 'd':
								case 'e':
								case 'f':
								case 'g':
								case 'h':
								case 'i':
								case 'j':
								case 'k':
								case 'l':
								case 'm':
								case 'n':
								case 'o':
								case 'p':
								case 'q':
								case 'r':
								case 's':
								case 't':
								case 'u':
								case 'v':
								case 'w':
								case 'x':
								case 'y':
								case 'z':
								{
									alt2=2;
								}
								break;
								case '0':
								case '1':
								case '2':
								case '3':
								case '4':
								case '5':
								case '6':
								case '7':
								case '8':
								case '9':
								{
									alt2=3;
								}
								break;

							}

							switch (alt2) {
								case 1 :
									// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:239:10: '_'
								{
									match('_'); if (state.failed) {
										return ;
									}

								}
								break;
								case 2 :
									// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:239:14: LETTERS
								{
									mLETTERS(); if (state.failed) {
										return ;
									}

								}
								break;
								case 3 :
									// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:239:22: DIGITS
								{
									mDIGITS(); if (state.failed) {
										return ;
									}

								}
								break;

								default :
									if ( cnt2 >= 1 ) {
										break loop2;
									}
									if (state.backtracking>0) {state.failed=true; return ;}
									final EarlyExitException eee =
										new EarlyExitException(2, input);
									throw eee;
							}
							cnt2++;
						} while (true);


				}
				break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "ID"

	// $ANTLR start "STRING_LITERAL"
	public final void mSTRING_LITERAL() throws RecognitionException {
		try {
			final int _type = STRING_LITERAL;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:241:15: ( '\\'' (~ ( '\\'' ) | ( '\\'' '\\'' ) )* '\\'' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:241:17: '\\'' (~ ( '\\'' ) | ( '\\'' '\\'' ) )* '\\''
			{
				match('\''); if (state.failed) {
					return ;
				}
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:241:21: (~ ( '\\'' ) | ( '\\'' '\\'' ) )*
				loop4:
					do {
						int alt4=3;
						final int LA4_0 = input.LA(1);

						if ( (LA4_0=='\'') ) {
							final int LA4_1 = input.LA(2);

							if ( (LA4_1=='\'') ) {
								alt4=2;
							}


						}
						else if ( ((LA4_0>='\u0000' && LA4_0<='&')||(LA4_0>='(' && LA4_0<='\uFFFF')) ) {
							alt4=1;
						}


						switch (alt4) {
							case 1 :
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:241:22: ~ ( '\\'' )
							{
								if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='\uFFFF') ) {
									input.consume();
									state.failed=false;
								}
								else {
									if (state.backtracking>0) {state.failed=true; return ;}
									final MismatchedSetException mse = new MismatchedSetException(null,input);
									recover(mse);
									throw mse;}


							}
							break;
							case 2 :
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:241:32: ( '\\'' '\\'' )
							{
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:241:32: ( '\\'' '\\'' )
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:241:33: '\\'' '\\''
								{
									match('\''); if (state.failed) {
										return ;
									}
									match('\''); if (state.failed) {
										return ;
									}

								}


							}
							break;

							default :
								break loop4;
						}
					} while (true);

				match('\''); if (state.failed) {
					return ;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "STRING_LITERAL"

	// $ANTLR start "SYMBOL_LITERAL"
	public final void mSYMBOL_LITERAL() throws RecognitionException {
		try {
			final int _type = SYMBOL_LITERAL;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:242:15: ( '#' ( STRING_LITERAL | ( ID COLON )+ | ID | BINARY_SELECTOR ) )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:242:17: '#' ( STRING_LITERAL | ( ID COLON )+ | ID | BINARY_SELECTOR )
			{
				match('#'); if (state.failed) {
					return ;
				}
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:242:20: ( STRING_LITERAL | ( ID COLON )+ | ID | BINARY_SELECTOR )
				int alt6=4;
				alt6 = dfa6.predict(input);
				switch (alt6) {
					case 1 :
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:242:21: STRING_LITERAL
					{
						mSTRING_LITERAL(); if (state.failed) {
							return ;
						}

					}
					break;
					case 2 :
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:242:36: ( ID COLON )+
					{
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:242:36: ( ID COLON )+
						int cnt5=0;
						loop5:
							do {
								int alt5=2;
								final int LA5_0 = input.LA(1);

								if ( ((LA5_0>='A' && LA5_0<='Z')||LA5_0=='_'||(LA5_0>='a' && LA5_0<='z')) ) {
									alt5=1;
								}


								switch (alt5) {
									case 1 :
										// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:242:37: ID COLON
									{
										mID(); if (state.failed) {
											return ;
										}
										mCOLON(); if (state.failed) {
											return ;
										}

									}
									break;

									default :
										if ( cnt5 >= 1 ) {
											break loop5;
										}
										if (state.backtracking>0) {state.failed=true; return ;}
										final EarlyExitException eee =
											new EarlyExitException(5, input);
										throw eee;
								}
								cnt5++;
							} while (true);


					}
					break;
					case 3 :
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:242:48: ID
					{
						mID(); if (state.failed) {
							return ;
						}

					}
					break;
					case 4 :
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:242:51: BINARY_SELECTOR
					{
						mBINARY_SELECTOR(); if (state.failed) {
							return ;
						}

					}
					break;

				}


			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "SYMBOL_LITERAL"

	// $ANTLR start "INT_OR_FLOAT"
	public final void mINT_OR_FLOAT() throws RecognitionException {
		try {
			int _type = INT_OR_FLOAT;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:245:3: ( ( DIGITS DOT DIGITS ( EXPONENT )? )=> DIGITS DOT DIGITS ( EXPONENT )? | ( DIGITS )=> DIGITS )
			int alt8=2;
			alt8 = dfa8.predict(input);
			switch (alt8) {
				case 1 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:245:5: ( DIGITS DOT DIGITS ( EXPONENT )? )=> DIGITS DOT DIGITS ( EXPONENT )?
				{
					mDIGITS(); if (state.failed) {
						return ;
					}
					mDOT(); if (state.failed) {
						return ;
					}
					mDIGITS(); if (state.failed) {
						return ;
					}
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:245:56: ( EXPONENT )?
					int alt7=2;
					final int LA7_0 = input.LA(1);

					if ( ((LA7_0>='d' && LA7_0<='e')||LA7_0=='q') ) {
						alt7=1;
					}
					switch (alt7) {
						case 1 :
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:245:56: EXPONENT
						{
							mEXPONENT(); if (state.failed) {
								return ;
							}

						}
						break;

					}

					if ( state.backtracking==0 ) {
						_type = FLOAT_LITERAL;
					}

				}
				break;
				case 2 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:246:5: ( DIGITS )=> DIGITS
				{
					mDIGITS(); if (state.failed) {
						return ;
					}
					if ( state.backtracking==0 ) {
						_type = INT_LITERAL;
					}

				}
				break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "INT_OR_FLOAT"

	// $ANTLR start "LETTERS"
	public final void mLETTERS() throws RecognitionException {
		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:248:17: ( ( 'a' .. 'z' | 'A' .. 'Z' ) )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:248:19: ( 'a' .. 'z' | 'A' .. 'Z' )
			{
				if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
					input.consume();
					state.failed=false;
				}
				else {
					if (state.backtracking>0) {state.failed=true; return ;}
					final MismatchedSetException mse = new MismatchedSetException(null,input);
					recover(mse);
					throw mse;}


			}

		}
		finally {
		}
	}
	// $ANTLR end "LETTERS"

	// $ANTLR start "DIGITS"
	public final void mDIGITS() throws RecognitionException {
		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:249:16: ( ( '0' .. '9' )+ )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:249:18: ( '0' .. '9' )+
			{
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:249:18: ( '0' .. '9' )+
				int cnt9=0;
				loop9:
					do {
						int alt9=2;
						final int LA9_0 = input.LA(1);

						if ( ((LA9_0>='0' && LA9_0<='9')) ) {
							alt9=1;
						}


						switch (alt9) {
							case 1 :
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:249:18: '0' .. '9'
							{
								matchRange('0','9'); if (state.failed) {
									return ;
								}

							}
							break;

							default :
								if ( cnt9 >= 1 ) {
									break loop9;
								}
								if (state.backtracking>0) {state.failed=true; return ;}
								final EarlyExitException eee =
									new EarlyExitException(9, input);
								throw eee;
						}
						cnt9++;
					} while (true);


			}

		}
		finally {
		}
	}
	// $ANTLR end "DIGITS"

	// $ANTLR start "EXPONENT"
	public final void mEXPONENT() throws RecognitionException {
		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:250:18: ( ( 'e' | 'd' | 'q' ) ( ( MINUS )? ( '0' .. '9' )+ ) )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:250:20: ( 'e' | 'd' | 'q' ) ( ( MINUS )? ( '0' .. '9' )+ )
			{
				if ( (input.LA(1)>='d' && input.LA(1)<='e')||input.LA(1)=='q' ) {
					input.consume();
					state.failed=false;
				}
				else {
					if (state.backtracking>0) {state.failed=true; return ;}
					final MismatchedSetException mse = new MismatchedSetException(null,input);
					recover(mse);
					throw mse;}

				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:250:38: ( ( MINUS )? ( '0' .. '9' )+ )
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:250:39: ( MINUS )? ( '0' .. '9' )+
				{
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:250:39: ( MINUS )?
					int alt10=2;
					final int LA10_0 = input.LA(1);

					if ( (LA10_0=='-') ) {
						alt10=1;
					}
					switch (alt10) {
						case 1 :
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:250:39: MINUS
						{
							mMINUS(); if (state.failed) {
								return ;
							}

						}
						break;

					}

					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:250:46: ( '0' .. '9' )+
					int cnt11=0;
					loop11:
						do {
							int alt11=2;
							final int LA11_0 = input.LA(1);

							if ( ((LA11_0>='0' && LA11_0<='9')) ) {
								alt11=1;
							}


							switch (alt11) {
								case 1 :
									// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:250:46: '0' .. '9'
								{
									matchRange('0','9'); if (state.failed) {
										return ;
									}

								}
								break;

								default :
									if ( cnt11 >= 1 ) {
										break loop11;
									}
									if (state.backtracking>0) {state.failed=true; return ;}
									final EarlyExitException eee =
										new EarlyExitException(11, input);
									throw eee;
							}
							cnt11++;
						} while (true);


				}


			}

		}
		finally {
		}
	}
	// $ANTLR end "EXPONENT"

	// $ANTLR start "CHARACTER_LITERAL"
	public final void mCHARACTER_LITERAL() throws RecognitionException {
		try {
			final int _type = CHARACTER_LITERAL;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:252:18: ( '$' ( '\\u0003' .. '\\uFFFF' ) )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:252:20: '$' ( '\\u0003' .. '\\uFFFF' )
			{
				match('$'); if (state.failed) {
					return ;
				}
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:252:24: ( '\\u0003' .. '\\uFFFF' )
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:252:25: '\\u0003' .. '\\uFFFF'
				{
					matchRange('\u0003','\uFFFF'); if (state.failed) {
						return ;
					}

				}


			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "CHARACTER_LITERAL"

	// $ANTLR start "DOT"
	public final void mDOT() throws RecognitionException {
		try {
			final int _type = DOT;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:253:4: ( '.' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:253:6: '.'
			{
				match('.'); if (state.failed) {
					return ;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "DOT"

	// $ANTLR start "PLUS"
	public final void mPLUS() throws RecognitionException {
		try {
			final int _type = PLUS;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:254:5: ( '+' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:254:7: '+'
			{
				match('+'); if (state.failed) {
					return ;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "PLUS"

	// $ANTLR start "MINUS"
	public final void mMINUS() throws RecognitionException {
		try {
			final int _type = MINUS;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:255:6: ( '-' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:255:8: '-'
			{
				match('-'); if (state.failed) {
					return ;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "MINUS"

	// $ANTLR start "BAR"
	public final void mBAR() throws RecognitionException {
		try {
			final int _type = BAR;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:256:4: ( '|' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:256:6: '|'
			{
				match('|'); if (state.failed) {
					return ;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "BAR"

	// $ANTLR start "AMPERSAND"
	public final void mAMPERSAND() throws RecognitionException {
		try {
			final int _type = AMPERSAND;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:257:10: ( '&' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:257:12: '&'
			{
				match('&'); if (state.failed) {
					return ;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "AMPERSAND"

	// $ANTLR start "COLON"
	public final void mCOLON() throws RecognitionException {
		try {
			final int _type = COLON;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:258:6: ( ':' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:258:8: ':'
			{
				match(':'); if (state.failed) {
					return ;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "COLON"

	// $ANTLR start "RETURN"
	public final void mRETURN() throws RecognitionException {
		try {
			final int _type = RETURN;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:259:7: ( '^' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:259:9: '^'
			{
				match('^'); if (state.failed) {
					return ;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "RETURN"

	// $ANTLR start "ASSIGN"
	public final void mASSIGN() throws RecognitionException {
		try {
			final int _type = ASSIGN;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:260:7: ( ( ':' '=' ) | '_' )
			int alt12=2;
			final int LA12_0 = input.LA(1);

			if ( (LA12_0==':') ) {
				alt12=1;
			}
			else if ( (LA12_0=='_') ) {
				alt12=2;
			}
			else {
				if (state.backtracking>0) {state.failed=true; return ;}
				final NoViableAltException nvae =
					new NoViableAltException("", 12, 0, input);

				throw nvae;
			}
			switch (alt12) {
				case 1 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:260:9: ( ':' '=' )
				{
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:260:9: ( ':' '=' )
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:260:10: ':' '='
					{
						match(':'); if (state.failed) {
							return ;
						}
						match('='); if (state.failed) {
							return ;
						}

					}


				}
				break;
				case 2 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:260:21: '_'
				{
					match('_'); if (state.failed) {
						return ;
					}

				}
				break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "ASSIGN"

	// $ANTLR start "LPAREN"
	public final void mLPAREN() throws RecognitionException {
		try {
			final int _type = LPAREN;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:261:7: ( '(' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:261:9: '('
			{
				match('('); if (state.failed) {
					return ;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "LPAREN"

	// $ANTLR start "RPAREN"
	public final void mRPAREN() throws RecognitionException {
		try {
			final int _type = RPAREN;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:262:7: ( ')' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:262:9: ')'
			{
				match(')'); if (state.failed) {
					return ;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "RPAREN"

	// $ANTLR start "LBRACKET"
	public final void mLBRACKET() throws RecognitionException {
		try {
			final int _type = LBRACKET;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:263:9: ( '[' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:263:11: '['
			{
				match('['); if (state.failed) {
					return ;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "LBRACKET"

	// $ANTLR start "RBRACKET"
	public final void mRBRACKET() throws RecognitionException {
		try {
			final int _type = RBRACKET;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:264:9: ( ']' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:264:11: ']'
			{
				match(']'); if (state.failed) {
					return ;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "RBRACKET"

	// $ANTLR start "LBRACE"
	public final void mLBRACE() throws RecognitionException {
		try {
			final int _type = LBRACE;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:265:7: ( '{' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:265:9: '{'
			{
				match('{'); if (state.failed) {
					return ;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "LBRACE"

	// $ANTLR start "RBRACE"
	public final void mRBRACE() throws RecognitionException {
		try {
			final int _type = RBRACE;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:266:7: ( '}' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:266:9: '}'
			{
				match('}'); if (state.failed) {
					return ;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "RBRACE"

	// $ANTLR start "LT"
	public final void mLT() throws RecognitionException {
		try {
			final int _type = LT;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:267:3: ( '<' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:267:5: '<'
			{
				match('<'); if (state.failed) {
					return ;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "LT"

	// $ANTLR start "GT"
	public final void mGT() throws RecognitionException {
		try {
			final int _type = GT;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:268:3: ( '>' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:268:5: '>'
			{
				match('>'); if (state.failed) {
					return ;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "GT"

	// $ANTLR start "COMMA"
	public final void mCOMMA() throws RecognitionException {
		try {
			final int _type = COMMA;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:269:6: ( ',' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:269:8: ','
			{
				match(','); if (state.failed) {
					return ;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "COMMA"

	// $ANTLR start "BINARY_SELECTOR"
	public final void mBINARY_SELECTOR() throws RecognitionException {
		try {
			final int _type = BINARY_SELECTOR;
			final int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:271:3: ( BINARY_SELECTOR_CHAR ( BINARY_SELECTOR_CHAR )? | ( LT | GT | PLUS | MINUS | BAR | COMMA | AMPERSAND ) ( LT | GT | PLUS | MINUS | BAR | COMMA | AMPERSAND | BINARY_SELECTOR_CHAR ) )
			int alt14=2;
			final int LA14_0 = input.LA(1);

			if ( (LA14_0=='!'||LA14_0=='%'||LA14_0=='*'||LA14_0=='/'||LA14_0=='='||(LA14_0>='?' && LA14_0<='@')||LA14_0=='\\'||LA14_0=='~') ) {
				alt14=1;
			}
			else if ( (LA14_0=='&'||(LA14_0>='+' && LA14_0<='-')||LA14_0=='<'||LA14_0=='>'||LA14_0=='|') ) {
				alt14=2;
			}
			else {
				if (state.backtracking>0) {state.failed=true; return ;}
				final NoViableAltException nvae =
					new NoViableAltException("", 14, 0, input);

				throw nvae;
			}
			switch (alt14) {
				case 1 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:271:5: BINARY_SELECTOR_CHAR ( BINARY_SELECTOR_CHAR )?
				{
					mBINARY_SELECTOR_CHAR(); if (state.failed) {
						return ;
					}
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:271:26: ( BINARY_SELECTOR_CHAR )?
					int alt13=2;
					final int LA13_0 = input.LA(1);

					if ( (LA13_0=='!'||LA13_0=='%'||LA13_0=='*'||LA13_0=='/'||LA13_0=='='||(LA13_0>='?' && LA13_0<='@')||LA13_0=='\\'||LA13_0=='~') ) {
						alt13=1;
					}
					switch (alt13) {
						case 1 :
							// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:271:27: BINARY_SELECTOR_CHAR
						{
							mBINARY_SELECTOR_CHAR(); if (state.failed) {
								return ;
							}

						}
						break;

					}


				}
				break;
				case 2 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:272:5: ( LT | GT | PLUS | MINUS | BAR | COMMA | AMPERSAND ) ( LT | GT | PLUS | MINUS | BAR | COMMA | AMPERSAND | BINARY_SELECTOR_CHAR )
				{
					if ( input.LA(1)=='&'||(input.LA(1)>='+' && input.LA(1)<='-')||input.LA(1)=='<'||input.LA(1)=='>'||input.LA(1)=='|' ) {
						input.consume();
						state.failed=false;
					}
					else {
						if (state.backtracking>0) {state.failed=true; return ;}
						final MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;}

					if ( input.LA(1)=='!'||(input.LA(1)>='%' && input.LA(1)<='&')||(input.LA(1)>='*' && input.LA(1)<='-')||input.LA(1)=='/'||(input.LA(1)>='<' && input.LA(1)<='@')||input.LA(1)=='\\'||input.LA(1)=='|'||input.LA(1)=='~' ) {
						input.consume();
						state.failed=false;
					}
					else {
						if (state.backtracking>0) {state.failed=true; return ;}
						final MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;}


				}
				break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "BINARY_SELECTOR"

	// $ANTLR start "BINARY_SELECTOR_CHAR"
	public final void mBINARY_SELECTOR_CHAR() throws RecognitionException {
		try {
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:274:30: ( '~' | '!' | '@' | '%' | '*' | '=' | '\\\\' | '?' | '/' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:
			{
				if ( input.LA(1)=='!'||input.LA(1)=='%'||input.LA(1)=='*'||input.LA(1)=='/'||input.LA(1)=='='||(input.LA(1)>='?' && input.LA(1)<='@')||input.LA(1)=='\\'||input.LA(1)=='~' ) {
					input.consume();
					state.failed=false;
				}
				else {
					if (state.backtracking>0) {state.failed=true; return ;}
					final MismatchedSetException mse = new MismatchedSetException(null,input);
					recover(mse);
					throw mse;}


			}

		}
		finally {
		}
	}
	// $ANTLR end "BINARY_SELECTOR_CHAR"

	// $ANTLR start "COMMENT"
	public final void mCOMMENT() throws RecognitionException {
		try {
			final int _type = COMMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:275:8: ( '\"' (~ ( '\"' ) | ( '\"' '\"' ) )* '\"' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:275:10: '\"' (~ ( '\"' ) | ( '\"' '\"' ) )* '\"'
			{
				match('\"'); if (state.failed) {
					return ;
				}
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:275:13: (~ ( '\"' ) | ( '\"' '\"' ) )*
				loop15:
					do {
						int alt15=3;
						final int LA15_0 = input.LA(1);

						if ( (LA15_0=='\"') ) {
							final int LA15_1 = input.LA(2);

							if ( (LA15_1=='\"') ) {
								alt15=2;
							}


						}
						else if ( ((LA15_0>='\u0000' && LA15_0<='!')||(LA15_0>='#' && LA15_0<='\uFFFF')) ) {
							alt15=1;
						}


						switch (alt15) {
							case 1 :
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:275:14: ~ ( '\"' )
							{
								if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='\uFFFF') ) {
									input.consume();
									state.failed=false;
								}
								else {
									if (state.backtracking>0) {state.failed=true; return ;}
									final MismatchedSetException mse = new MismatchedSetException(null,input);
									recover(mse);
									throw mse;}


							}
							break;
							case 2 :
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:275:23: ( '\"' '\"' )
							{
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:275:23: ( '\"' '\"' )
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:275:24: '\"' '\"'
								{
									match('\"'); if (state.failed) {
										return ;
									}
									match('\"'); if (state.failed) {
										return ;
									}

								}


							}
							break;

							default :
								break loop15;
						}
					} while (true);

				match('\"'); if (state.failed) {
					return ;
				}
				if ( state.backtracking==0 ) {
					_channel=HIDDEN;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "COMMENT"

	// $ANTLR start "NEWLINE"
	public final void mNEWLINE() throws RecognitionException {
		try {
			final int _type = NEWLINE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:276:8: ( ( '\\r' )? '\\n' )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:276:9: ( '\\r' )? '\\n'
			{
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:276:9: ( '\\r' )?
				int alt16=2;
				final int LA16_0 = input.LA(1);

				if ( (LA16_0=='\r') ) {
					alt16=1;
				}
				switch (alt16) {
					case 1 :
						// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:276:9: '\\r'
					{
						match('\r'); if (state.failed) {
							return ;
						}

					}
					break;

				}

				match('\n'); if (state.failed) {
					return ;
				}
				if ( state.backtracking==0 ) {
					_channel=HIDDEN;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "NEWLINE"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			final int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:277:3: ( ( ' ' | '\\t' | '\\n' | '\\r' )+ )
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:277:4: ( ' ' | '\\t' | '\\n' | '\\r' )+
			{
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:277:4: ( ' ' | '\\t' | '\\n' | '\\r' )+
				int cnt17=0;
				loop17:
					do {
						int alt17=2;
						final int LA17_0 = input.LA(1);

						if ( ((LA17_0>='\t' && LA17_0<='\n')||LA17_0=='\r'||LA17_0==' ') ) {
							alt17=1;
						}


						switch (alt17) {
							case 1 :
								// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:
							{
								if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
									input.consume();
									state.failed=false;
								}
								else {
									if (state.backtracking>0) {state.failed=true; return ;}
									final MismatchedSetException mse = new MismatchedSetException(null,input);
									recover(mse);
									throw mse;}


							}
							break;

							default :
								if ( cnt17 >= 1 ) {
									break loop17;
								}
								if (state.backtracking>0) {state.failed=true; return ;}
								final EarlyExitException eee =
									new EarlyExitException(17, input);
								throw eee;
						}
						cnt17++;
					} while (true);

				if ( state.backtracking==0 ) {
					_channel=HIDDEN;
				}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
		}
	}
	// $ANTLR end "WS"

	@Override
	public void mTokens() throws RecognitionException {
		// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:8: ( T__58 | T__59 | T__60 | T__61 | T__62 | NIL | TRUE | FALSE | SELF | SUPER | THIS_CONTEXT | ID | STRING_LITERAL | SYMBOL_LITERAL | INT_OR_FLOAT | CHARACTER_LITERAL | DOT | PLUS | MINUS | BAR | AMPERSAND | COLON | RETURN | ASSIGN | LPAREN | RPAREN | LBRACKET | RBRACKET | LBRACE | RBRACE | LT | GT | COMMA | BINARY_SELECTOR | COMMENT | NEWLINE | WS )
		int alt18=37;
		alt18 = dfa18.predict(input);
		switch (alt18) {
			case 1 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:10: T__58
			{
				mT__58(); if (state.failed) {
					return ;
				}

			}
			break;
			case 2 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:16: T__59
			{
				mT__59(); if (state.failed) {
					return ;
				}

			}
			break;
			case 3 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:22: T__60
			{
				mT__60(); if (state.failed) {
					return ;
				}

			}
			break;
			case 4 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:28: T__61
			{
				mT__61(); if (state.failed) {
					return ;
				}

			}
			break;
			case 5 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:34: T__62
			{
				mT__62(); if (state.failed) {
					return ;
				}

			}
			break;
			case 6 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:40: NIL
			{
				mNIL(); if (state.failed) {
					return ;
				}

			}
			break;
			case 7 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:44: TRUE
			{
				mTRUE(); if (state.failed) {
					return ;
				}

			}
			break;
			case 8 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:49: FALSE
			{
				mFALSE(); if (state.failed) {
					return ;
				}

			}
			break;
			case 9 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:55: SELF
			{
				mSELF(); if (state.failed) {
					return ;
				}

			}
			break;
			case 10 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:60: SUPER
			{
				mSUPER(); if (state.failed) {
					return ;
				}

			}
			break;
			case 11 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:66: THIS_CONTEXT
			{
				mTHIS_CONTEXT(); if (state.failed) {
					return ;
				}

			}
			break;
			case 12 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:79: ID
			{
				mID(); if (state.failed) {
					return ;
				}

			}
			break;
			case 13 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:82: STRING_LITERAL
			{
				mSTRING_LITERAL(); if (state.failed) {
					return ;
				}

			}
			break;
			case 14 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:97: SYMBOL_LITERAL
			{
				mSYMBOL_LITERAL(); if (state.failed) {
					return ;
				}

			}
			break;
			case 15 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:112: INT_OR_FLOAT
			{
				mINT_OR_FLOAT(); if (state.failed) {
					return ;
				}

			}
			break;
			case 16 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:125: CHARACTER_LITERAL
			{
				mCHARACTER_LITERAL(); if (state.failed) {
					return ;
				}

			}
			break;
			case 17 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:143: DOT
			{
				mDOT(); if (state.failed) {
					return ;
				}

			}
			break;
			case 18 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:147: PLUS
			{
				mPLUS(); if (state.failed) {
					return ;
				}

			}
			break;
			case 19 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:152: MINUS
			{
				mMINUS(); if (state.failed) {
					return ;
				}

			}
			break;
			case 20 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:158: BAR
			{
				mBAR(); if (state.failed) {
					return ;
				}

			}
			break;
			case 21 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:162: AMPERSAND
			{
				mAMPERSAND(); if (state.failed) {
					return ;
				}

			}
			break;
			case 22 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:172: COLON
			{
				mCOLON(); if (state.failed) {
					return ;
				}

			}
			break;
			case 23 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:178: RETURN
			{
				mRETURN(); if (state.failed) {
					return ;
				}

			}
			break;
			case 24 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:185: ASSIGN
			{
				mASSIGN(); if (state.failed) {
					return ;
				}

			}
			break;
			case 25 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:192: LPAREN
			{
				mLPAREN(); if (state.failed) {
					return ;
				}

			}
			break;
			case 26 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:199: RPAREN
			{
				mRPAREN(); if (state.failed) {
					return ;
				}

			}
			break;
			case 27 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:206: LBRACKET
			{
				mLBRACKET(); if (state.failed) {
					return ;
				}

			}
			break;
			case 28 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:215: RBRACKET
			{
				mRBRACKET(); if (state.failed) {
					return ;
				}

			}
			break;
			case 29 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:224: LBRACE
			{
				mLBRACE(); if (state.failed) {
					return ;
				}

			}
			break;
			case 30 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:231: RBRACE
			{
				mRBRACE(); if (state.failed) {
					return ;
				}

			}
			break;
			case 31 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:238: LT
			{
				mLT(); if (state.failed) {
					return ;
				}

			}
			break;
			case 32 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:241: GT
			{
				mGT(); if (state.failed) {
					return ;
				}

			}
			break;
			case 33 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:244: COMMA
			{
				mCOMMA(); if (state.failed) {
					return ;
				}

			}
			break;
			case 34 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:250: BINARY_SELECTOR
			{
				mBINARY_SELECTOR(); if (state.failed) {
					return ;
				}

			}
			break;
			case 35 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:266: COMMENT
			{
				mCOMMENT(); if (state.failed) {
					return ;
				}

			}
			break;
			case 36 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:274: NEWLINE
			{
				mNEWLINE(); if (state.failed) {
					return ;
				}

			}
			break;
			case 37 :
				// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:1:282: WS
			{
				mWS(); if (state.failed) {
					return ;
				}

			}
			break;

		}

	}

	// $ANTLR start synpred1_Gs
	public final void synpred1_Gs_fragment() throws RecognitionException {
		// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:245:5: ( DIGITS DOT DIGITS ( EXPONENT )? )
		// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:245:6: DIGITS DOT DIGITS ( EXPONENT )?
		{
			mDIGITS(); if (state.failed) {
				return ;
			}
			mDOT(); if (state.failed) {
				return ;
			}
			mDIGITS(); if (state.failed) {
				return ;
			}
			// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:245:24: ( EXPONENT )?
			int alt19=2;
			final int LA19_0 = input.LA(1);

			if ( ((LA19_0>='d' && LA19_0<='e')||LA19_0=='q') ) {
				alt19=1;
			}
			switch (alt19) {
				case 1 :
					// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:245:24: EXPONENT
				{
					mEXPONENT(); if (state.failed) {
						return ;
					}

				}
				break;

			}


		}
	}
	// $ANTLR end synpred1_Gs

	// $ANTLR start synpred2_Gs
	public final void synpred2_Gs_fragment() throws RecognitionException {
		// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:246:5: ( DIGITS )
		// D:\\Dev\\eclipseWorkspaces\\gemdev\\net.karpisek.gemdev.lang\\grammar\\Gs.g:246:6: DIGITS
		{
			mDIGITS(); if (state.failed) {
				return ;
			}

		}
	}
	// $ANTLR end synpred2_Gs

	public final boolean synpred2_Gs() {
		state.backtracking++;
		final int start = input.mark();
		try {
			synpred2_Gs_fragment(); // can never throw exception
		} catch (final RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		final boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred1_Gs() {
		state.backtracking++;
		final int start = input.mark();
		try {
			synpred1_Gs_fragment(); // can never throw exception
		} catch (final RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		final boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}


	protected DFA6 dfa6 = new DFA6(this);
	protected DFA8 dfa8 = new DFA8(this);
	protected DFA18 dfa18 = new DFA18(this);
	static final String DFA6_eotS =
		"\2\uffff\1\5\3\uffff\3\5\1\uffff\3\5";
	static final String DFA6_eofS =
		"\15\uffff";
	static final String DFA6_minS =
		"\1\41\1\uffff\2\60\2\uffff\3\60\1\uffff\3\60";
	static final String DFA6_maxS =
		"\1\176\1\uffff\2\172\2\uffff\3\172\1\uffff\3\172";
	static final String DFA6_acceptS =
		"\1\uffff\1\1\2\uffff\1\4\1\3\3\uffff\1\2\3\uffff";
	static final String DFA6_specialS =
		"\15\uffff}>";
	static final String[] DFA6_transitionS = {
		"\1\4\3\uffff\2\4\1\1\2\uffff\4\4\1\uffff\1\4\14\uffff\5\4\32"+
		"\2\1\uffff\1\4\2\uffff\1\3\1\uffff\32\2\1\uffff\1\4\1\uffff"+
		"\1\4",
		"",
		"\12\10\1\11\6\uffff\32\7\4\uffff\1\6\1\uffff\32\7",
		"\12\14\7\uffff\32\13\4\uffff\1\12\1\uffff\32\13",
		"",
		"",
		"\12\10\1\11\6\uffff\32\7\4\uffff\1\6\1\uffff\32\7",
		"\12\10\1\11\6\uffff\32\7\4\uffff\1\6\1\uffff\32\7",
		"\12\10\1\11\6\uffff\32\7\4\uffff\1\6\1\uffff\32\7",
		"",
		"\12\14\1\11\6\uffff\32\13\4\uffff\1\12\1\uffff\32\13",
		"\12\14\1\11\6\uffff\32\13\4\uffff\1\12\1\uffff\32\13",
		"\12\14\1\11\6\uffff\32\13\4\uffff\1\12\1\uffff\32\13"
	};

	static final short[] DFA6_eot = DFA.unpackEncodedString(DFA6_eotS);
	static final short[] DFA6_eof = DFA.unpackEncodedString(DFA6_eofS);
	static final char[] DFA6_min = DFA.unpackEncodedStringToUnsignedChars(DFA6_minS);
	static final char[] DFA6_max = DFA.unpackEncodedStringToUnsignedChars(DFA6_maxS);
	static final short[] DFA6_accept = DFA.unpackEncodedString(DFA6_acceptS);
	static final short[] DFA6_special = DFA.unpackEncodedString(DFA6_specialS);
	static final short[][] DFA6_transition;

	static {
		final int numStates = DFA6_transitionS.length;
		DFA6_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA6_transition[i] = DFA.unpackEncodedString(DFA6_transitionS[i]);
		}
	}

	class DFA6 extends DFA {

		public DFA6(final BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 6;
			this.eot = DFA6_eot;
			this.eof = DFA6_eof;
			this.min = DFA6_min;
			this.max = DFA6_max;
			this.accept = DFA6_accept;
			this.special = DFA6_special;
			this.transition = DFA6_transition;
		}
		@Override
		public String getDescription() {
			return "242:20: ( STRING_LITERAL | ( ID COLON )+ | ID | BINARY_SELECTOR )";
		}
	}
	static final String DFA8_eotS =
		"\1\uffff\1\2\2\uffff";
	static final String DFA8_eofS =
		"\4\uffff";
	static final String DFA8_minS =
		"\1\60\1\56\2\uffff";
	static final String DFA8_maxS =
		"\2\71\2\uffff";
	static final String DFA8_acceptS =
		"\2\uffff\1\2\1\1";
	static final String DFA8_specialS =
		"\1\uffff\1\0\2\uffff}>";
	static final String[] DFA8_transitionS = {
		"\12\1",
		"\1\3\1\uffff\12\1",
		"",
		""
	};

	static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
	static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
	static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
	static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
	static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
	static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
	static final short[][] DFA8_transition;

	static {
		final int numStates = DFA8_transitionS.length;
		DFA8_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
		}
	}

	class DFA8 extends DFA {

		public DFA8(final BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 8;
			this.eot = DFA8_eot;
			this.eof = DFA8_eof;
			this.min = DFA8_min;
			this.max = DFA8_max;
			this.accept = DFA8_accept;
			this.special = DFA8_special;
			this.transition = DFA8_transition;
		}
		@Override
		public String getDescription() {
			return "244:1: INT_OR_FLOAT : ( ( DIGITS DOT DIGITS ( EXPONENT )? )=> DIGITS DOT DIGITS ( EXPONENT )? | ( DIGITS )=> DIGITS );";
		}
		@Override
		public int specialStateTransition(int s, final IntStream _input) throws NoViableAltException {
			final IntStream input = _input;
			final int _s = s;
			switch ( s ) {
				case 0 :
					final int LA8_1 = input.LA(1);


					final int index8_1 = input.index();
					input.rewind();
					s = -1;
					if ( (LA8_1=='.') && (synpred1_Gs())) {s = 3;}

					else if ( ((LA8_1>='0' && LA8_1<='9')) ) {s = 1;}
					else {
						s = 2;
					}


					input.seek(index8_1);
					if ( s>=0 ) {
						return s;
					}
					break;
			}
			if (state.backtracking>0) {state.failed=true; return -1;}
			final NoViableAltException nvae =
				new NoViableAltException(getDescription(), 8, _s, input);
			error(nvae);
			throw nvae;
		}
	}
	static final String DFA18_eotS =
		"\1\uffff\2\11\1\45\1\uffff\4\11\1\uffff\1\55\4\uffff\1\56\1\57"+
		"\1\60\1\61\1\62\7\uffff\1\63\1\64\1\65\2\uffff\1\42\1\66\1\uffff"+
		"\2\11\2\uffff\6\11\12\uffff\3\11\1\103\10\11\1\uffff\1\114\2\11"+
		"\1\117\4\11\1\uffff\1\11\1\125\1\uffff\1\126\4\11\2\uffff\11\11"+
		"\1\144\2\11\2\uffff\2\11\1\151\1\152\2\uffff";
	static final String DFA18_eofS =
		"\153\uffff";
	static final String DFA18_minS =
		"\1\11\1\162\1\156\1\41\1\uffff\1\151\1\150\1\141\1\145\1\uffff"+
		"\1\60\4\uffff\4\41\1\75\7\uffff\3\41\2\uffff\1\12\1\11\1\uffff\1"+
		"\151\1\160\2\uffff\1\154\1\165\1\151\2\154\1\160\12\uffff\1\155"+
		"\1\164\1\162\1\60\1\145\2\163\1\146\1\145\1\151\1\145\1\157\1\uffff"+
		"\1\60\1\103\1\145\1\60\1\162\1\164\1\143\1\164\1\uffff\1\157\1\60"+
		"\1\uffff\1\60\1\151\1\164\1\145\1\156\2\uffff\1\166\1\145\1\143"+
		"\1\164\1\145\1\144\1\164\1\145\1\72\1\60\1\145\1\170\2\uffff\1\144"+
		"\1\164\2\60\2\uffff";
	static final String DFA18_maxS =
		"\1\176\1\162\1\156\1\176\1\uffff\1\151\1\162\1\141\1\165\1\uffff"+
		"\1\172\4\uffff\4\176\1\75\7\uffff\3\176\2\uffff\1\12\1\40\1\uffff"+
		"\1\157\1\160\2\uffff\1\154\1\165\1\151\2\154\1\160\12\uffff\1\155"+
		"\1\164\1\162\1\172\1\145\2\163\1\146\1\145\1\151\1\145\1\157\1\uffff"+
		"\1\172\1\103\1\145\1\172\1\162\1\164\1\143\1\164\1\uffff\1\157\1"+
		"\172\1\uffff\1\172\1\151\1\164\1\145\1\156\2\uffff\1\166\1\145\1"+
		"\143\1\164\1\145\1\144\1\164\1\145\1\72\1\172\1\145\1\170\2\uffff"+
		"\1\144\1\164\2\172\2\uffff";
	static final String DFA18_acceptS =
		"\4\uffff\1\5\4\uffff\1\14\1\uffff\1\15\1\17\1\20\1\21\5\uffff\1"+
		"\27\1\31\1\32\1\33\1\34\1\35\1\36\3\uffff\1\42\1\43\2\uffff\1\45"+
		"\2\uffff\1\4\1\16\6\uffff\1\30\1\22\1\23\1\24\1\25\1\26\1\37\1\40"+
		"\1\41\1\44\14\uffff\1\6\10\uffff\1\7\2\uffff\1\11\5\uffff\1\10\1"+
		"\12\14\uffff\1\1\1\2\4\uffff\1\3\1\13";
	static final String DFA18_specialS =
		"\153\uffff}>";
	static final String[] DFA18_transitionS = {
		"\1\42\1\41\2\uffff\1\40\22\uffff\1\42\1\36\1\37\1\3\1\15\1"+
		"\36\1\22\1\13\1\25\1\26\1\36\1\17\1\35\1\20\1\16\1\36\12\14"+
		"\1\23\1\4\1\33\1\36\1\34\2\36\32\11\1\27\1\36\1\30\1\24\1\12"+
		"\1\uffff\5\11\1\7\7\11\1\5\1\11\1\1\2\11\1\10\1\6\1\2\5\11\1"+
		"\31\1\21\1\32\1\36",
		"\1\43",
		"\1\44",
		"\1\46\3\uffff\3\46\2\uffff\4\46\1\uffff\1\46\14\uffff\37\46"+
		"\1\uffff\1\46\2\uffff\1\46\1\uffff\32\46\1\uffff\1\46\1\uffff"+
		"\1\46",
		"",
		"\1\47",
		"\1\51\11\uffff\1\50",
		"\1\52",
		"\1\53\17\uffff\1\54",
		"",
		"\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
		"",
		"",
		"",
		"",
		"\1\36\3\uffff\2\36\3\uffff\4\36\1\uffff\1\36\14\uffff\5\36"+
		"\33\uffff\1\36\37\uffff\1\36\1\uffff\1\36",
		"\1\36\3\uffff\2\36\3\uffff\4\36\1\uffff\1\36\14\uffff\5\36"+
		"\33\uffff\1\36\37\uffff\1\36\1\uffff\1\36",
		"\1\36\3\uffff\2\36\3\uffff\4\36\1\uffff\1\36\14\uffff\5\36"+
		"\33\uffff\1\36\37\uffff\1\36\1\uffff\1\36",
		"\1\36\3\uffff\2\36\3\uffff\4\36\1\uffff\1\36\14\uffff\5\36"+
		"\33\uffff\1\36\37\uffff\1\36\1\uffff\1\36",
		"\1\55",
		"",
		"",
		"",
		"",
		"",
		"",
		"",
		"\1\36\3\uffff\2\36\3\uffff\4\36\1\uffff\1\36\14\uffff\5\36"+
		"\33\uffff\1\36\37\uffff\1\36\1\uffff\1\36",
		"\1\36\3\uffff\2\36\3\uffff\4\36\1\uffff\1\36\14\uffff\5\36"+
		"\33\uffff\1\36\37\uffff\1\36\1\uffff\1\36",
		"\1\36\3\uffff\2\36\3\uffff\4\36\1\uffff\1\36\14\uffff\5\36"+
		"\33\uffff\1\36\37\uffff\1\36\1\uffff\1\36",
		"",
		"",
		"\1\41",
		"\2\42\2\uffff\1\42\22\uffff\1\42",
		"",
		"\1\67\5\uffff\1\70",
		"\1\71",
		"",
		"",
		"\1\72",
		"\1\73",
		"\1\74",
		"\1\75",
		"\1\76",
		"\1\77",
		"",
		"",
		"",
		"",
		"",
		"",
		"",
		"",
		"",
		"",
		"\1\100",
		"\1\101",
		"\1\102",
		"\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
		"\1\104",
		"\1\105",
		"\1\106",
		"\1\107",
		"\1\110",
		"\1\111",
		"\1\112",
		"\1\113",
		"",
		"\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
		"\1\115",
		"\1\116",
		"\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
		"\1\120",
		"\1\121",
		"\1\122",
		"\1\123",
		"",
		"\1\124",
		"\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
		"",
		"\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
		"\1\127",
		"\1\130",
		"\1\131",
		"\1\132",
		"",
		"",
		"\1\133",
		"\1\134",
		"\1\135",
		"\1\136",
		"\1\137",
		"\1\140",
		"\1\141",
		"\1\142",
		"\1\143",
		"\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
		"\1\145",
		"\1\146",
		"",
		"",
		"\1\147",
		"\1\150",
		"\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
		"\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
		"",
		""
	};

	static final short[] DFA18_eot = DFA.unpackEncodedString(DFA18_eotS);
	static final short[] DFA18_eof = DFA.unpackEncodedString(DFA18_eofS);
	static final char[] DFA18_min = DFA.unpackEncodedStringToUnsignedChars(DFA18_minS);
	static final char[] DFA18_max = DFA.unpackEncodedStringToUnsignedChars(DFA18_maxS);
	static final short[] DFA18_accept = DFA.unpackEncodedString(DFA18_acceptS);
	static final short[] DFA18_special = DFA.unpackEncodedString(DFA18_specialS);
	static final short[][] DFA18_transition;

	static {
		final int numStates = DFA18_transitionS.length;
		DFA18_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA18_transition[i] = DFA.unpackEncodedString(DFA18_transitionS[i]);
		}
	}

	class DFA18 extends DFA {

		public DFA18(final BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 18;
			this.eot = DFA18_eot;
			this.eof = DFA18_eof;
			this.min = DFA18_min;
			this.max = DFA18_max;
			this.accept = DFA18_accept;
			this.special = DFA18_special;
			this.transition = DFA18_transition;
		}
		@Override
		public String getDescription() {
			return "1:1: Tokens : ( T__58 | T__59 | T__60 | T__61 | T__62 | NIL | TRUE | FALSE | SELF | SUPER | THIS_CONTEXT | ID | STRING_LITERAL | SYMBOL_LITERAL | INT_OR_FLOAT | CHARACTER_LITERAL | DOT | PLUS | MINUS | BAR | AMPERSAND | COLON | RETURN | ASSIGN | LPAREN | RPAREN | LBRACKET | RBRACKET | LBRACE | RBRACE | LT | GT | COMMA | BINARY_SELECTOR | COMMENT | NEWLINE | WS );";
		}
	}


}