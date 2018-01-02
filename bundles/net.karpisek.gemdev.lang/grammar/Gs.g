grammar Gs;

options {
  language = Java;
  output = AST;
  ASTLabelType=GsTree;
  backtrack=true;
}

tokens{
  UNARY_METHOD;
  BINARY_METHOD;
  KEYWORD_METHOD;
  
  PRIMITIVE;
  
  PARAMETERS;
  TEMPORARIES;
  STATEMENTS;
  ARRAY;
  BLOCK;
  EXPR;  
  
  UNARY_MESSAGE;
  BINARY_MESSAGE;
  KEYWORD_MESSAGE;
  
  PARAMETER;
  BLOCK_PARAMETER;
  KEYWORD;
  
  INT_LITERAL;
  FLOAT_LITERAL;
}

@lexer::header{
  package net.karpisek.gemdev.lang.lexer;
}
@parser::header{
  package net.karpisek.gemdev.lang.parser;
  import java.util.LinkedList;
}

@members{
  private List<SyntaxError> errors = new LinkedList<SyntaxError>();
  @Override
  public void displayRecognitionError(final String[] tokenNames,
    final RecognitionException e)
  {
    String hdr = getErrorHeader(e);
    String msg = getErrorMessage(e, tokenNames);
    Token t = e.token;
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
}

method: m=methodHeader b=methodBody       -> ^($m $b);

methodHeader 
  : unaryMethodHeader                     -> ^(UNARY_METHOD ^(PARAMETERS unaryMethodHeader))
  | binaryMethodHeader                    -> ^(BINARY_METHOD ^(PARAMETERS binaryMethodHeader))
  | keywordMethodHeader                   -> ^(KEYWORD_METHOD ^(PARAMETERS keywordMethodHeader)) 
  ;      
unaryMethodHeader: ID;
binaryMethodHeader: binarySelector ID    {$ID.setType(PARAMETER);};
keywordMethodHeader: (keyword ID {$ID.setType(PARAMETER);})+        ;

keyword: ID COLON! {$ID.setType(KEYWORD);};

methodBody: primitive? temporaries statements;
//TODO: here should be DIGITS instead NUMBER  
primitive: LT! primitiveModifier? ('primitive:'! INT_LITERAL)?  GT!   -> ^(PRIMITIVE primitiveModifier? INT_LITERAL?);
primitiveModifier: 'protected'|'unprotected';

temporaries: (BAR ID* BAR)?               -> ^(TEMPORARIES ID*);

statements
  : (statement (DOT! statement)* (DOT! finalStatement)? (DOT)?)?    -> ^(STATEMENTS statement* finalStatement?)
  | finalStatement(DOT!)?                                        
  ;
  
statement: (assignment)* expr;
finalStatement: RETURN^ statement;

assignment
  : ID ASSIGN^
  ;

expr: operand (messageChain (cascadedMessage)*)? -> ^(EXPR operand (messageChain (cascadedMessage)*)?);
operand
  : literal
  | ID
  | nestedExpr 
  | arrayBuilder
  ;
  
literal 
  : (NIL|TRUE|FALSE|SELF|SUPER|THIS_CONTEXT)
  | MINUS^? INT_LITERAL
  | MINUS^? FLOAT_LITERAL
  | CHARACTER_LITERAL
  | STRING_LITERAL
  | SYMBOL_LITERAL
  | arrayLiteral
  | block
  | selectionBlock
  ;
  
//--------- arrays ---------- 
arrayLiteral: '#'! array;
array: LPAREN! (arrayItem)* RPAREN!     -> ^(ARRAY arrayItem*);
arrayItem
  : array
  | arrayLiteral
  | MINUS^? INT_LITERAL
  | ID
  | SYMBOL_LITERAL
  | STRING_LITERAL
  | CHARACTER_LITERAL
  | (NIL|TRUE|FALSE|SELF|SUPER|THIS_CONTEXT)
  ;
  
arrayBuilder: '#' LBRACKET (exprA (COMMA exprA)*)? COMMA? RBRACKET -> ^(ARRAY exprA*);
exprA: operand (messageChainA (cascadedMessageA)*)?;

//--------- block ----------  
block: LBRACKET! parameters temporaries statements RBRACKET!      -> ^(BLOCK parameters temporaries statements);
parameters
  : (blockParameter)+ BAR                             -> ^(PARAMETERS blockParameter+)
  |                                                   -> ^(PARAMETERS)
  ; 
blockParameter: COLON! ID {$ID.setType(BLOCK_PARAMETER);};


//--------- selection block ----------
selectionBlock: LBRACE! blockParameter BAR! predicate RBRACE! -> ^(BLOCK ^(PARAMETERS blockParameter) ^(TEMPORARIES) ^(STATEMENTS predicate));
predicate
  :anyTerm (AMPERSAND term)*
  |parenTerm (AMPERSAND term)*
  ;
  
term: parenTerm|selectionBlockOperand;
//TODO: operator should be only '='|'=='|'<'|'>'|'<='|'>='|'~='|'~~' not full BINARY_SELECTOR
anyTerm: selectionBlockOperand (binarySelector selectionBlockOperand)?;
parenTerm: LPAREN! anyTerm RPAREN!;

selectionBlockOperand: ID(DOT ID)* | literal;
//--------- messages ---------- 
nestedExpr: '(' statement ')';
  
messageChain
  : unaryMessage unaryMessageChain binaryMessageChain (keywordMessage)?
  | binaryMessage binaryMessageChain (keywordMessage)?
  | keywordMessage
  ;
unaryMessage: ID                              {$ID.setType(UNARY_MESSAGE);};
unaryMessageChain: (unaryMessage)*;

binaryMessageOperand: operand unaryMessageChain;
binaryMessage: binarySelector binaryMessageOperand     -> ^(BINARY_MESSAGE binarySelector binaryMessageOperand);
binaryMessageChain: (binaryMessage)*;

keywordMessageArgument: binaryMessageOperand binaryMessageChain;
keywordMessageSegment: keyword keywordMessageArgument;
keywordMessage: (keywordMessageSegment)+    -> ^(KEYWORD_MESSAGE keywordMessageSegment+);

cascadedMessage: ';' messageChain;
  
binarySelector
  : BINARY_SELECTOR^
  | PLUS^ {$PLUS.setType(BINARY_SELECTOR);}  
  | MINUS^ {$MINUS.setType(BINARY_SELECTOR);}
  | AMPERSAND^ {$AMPERSAND.setType(BINARY_SELECTOR);}
  | BAR^ {$BAR.setType(BINARY_SELECTOR);}  
  | LT^ {$LT.setType(BINARY_SELECTOR);}
  | GT^ {$GT.setType(BINARY_SELECTOR);}
  | COMMA^ {$COMMA.setType(BINARY_SELECTOR);}
  ;  
  
//--------- messages A---------- 
messageChainA
  : unaryMessageA unaryMessageChainA binaryMessageChainA (keywordMessageA)?
  | binaryMessageA binaryMessageChainA (keywordMessageA)?
  | keywordMessageA
  ;
unaryMessageA: ID                              {$ID.setType(UNARY_MESSAGE);};
unaryMessageChainA: (unaryMessageA)*;

binaryMessageOperandA: operand unaryMessageChainA;
binaryMessageA: binarySelectorA binaryMessageOperandA     -> ^(BINARY_MESSAGE binarySelectorA binaryMessageOperandA);
binaryMessageChainA: (binaryMessageA)*;

keywordMessageArgumentA: binaryMessageOperandA binaryMessageChainA;
keywordMessageSegmentA: keyword keywordMessageArgumentA;
keywordMessageA: (keywordMessageSegmentA)+    -> ^(KEYWORD_MESSAGE keywordMessageSegmentA+);

cascadedMessageA: ';' messageChainA;
  
binarySelectorA
  : BINARY_SELECTOR^
  | PLUS^ {$PLUS.setType(BINARY_SELECTOR);}
  | MINUS^ {$MINUS.setType(BINARY_SELECTOR);}
  | AMPERSAND^ {$AMPERSAND.setType(BINARY_SELECTOR);}
  | BAR^ {$BAR.setType(BINARY_SELECTOR);}
  | LT^ {$LT.setType(BINARY_SELECTOR);}
  | GT^ {$GT.setType(BINARY_SELECTOR);}  
  ;  
  
selector
  : unaryMessage
  | binarySelector
  | (keyword)+ -> ^(KEYWORD_MESSAGE keyword+)
  ;  
  
NIL: 'nil';
TRUE: 'true';
FALSE: 'false';
SELF: 'self';
SUPER: 'super';
THIS_CONTEXT: 'thisContext';

ID
  : LETTERS ('_'|LETTERS|DIGITS)*
  | '_' ('_'|LETTERS|DIGITS)+
  ;  
STRING_LITERAL: '\''(~('\'') | ('\'' '\''))* '\'';
SYMBOL_LITERAL: '#'(STRING_LITERAL|(ID COLON)+|ID|BINARY_SELECTOR);

INT_OR_FLOAT
  : (DIGITS DOT DIGITS EXPONENT?) => DIGITS DOT DIGITS EXPONENT? {$type = FLOAT_LITERAL;}
  | (DIGITS) =>  DIGITS {$type = INT_LITERAL;}
  ;
fragment LETTERS: ('a'..'z'|'A'..'Z');
fragment DIGITS: '0'..'9'+;
fragment EXPONENT: ('e' | 'd' | 'q') (MINUS? '0'..'9'+);

CHARACTER_LITERAL: '$' ('\u0003'..'\uFFFF');
DOT: '.';
PLUS: '+';
MINUS: '-';
BAR: '|';
AMPERSAND: '&';
COLON: ':';
RETURN: '^';
ASSIGN: (':' '=') | '_';
LPAREN: '(';
RPAREN: ')';
LBRACKET: '[';
RBRACKET: ']';
LBRACE: '{';
RBRACE: '}';
LT: '<';
GT: '>';
COMMA: ',';
BINARY_SELECTOR
  : BINARY_SELECTOR_CHAR (BINARY_SELECTOR_CHAR)?
  | (LT|GT|PLUS|MINUS|BAR|COMMA|AMPERSAND) (LT|GT|PLUS|MINUS|BAR|COMMA|AMPERSAND | BINARY_SELECTOR_CHAR)
  ;
fragment BINARY_SELECTOR_CHAR: '~' | '!' | '@' | '%' | '*' | '=' | '\\' | '?' | '/';
COMMENT: '"'(~('"') | ('"' '"'))* '"' {$channel=HIDDEN;};
NEWLINE:'\r'? '\n' {$channel=HIDDEN;};
WS:(' '|'\t'|'\n'|'\r')+ {$channel=HIDDEN;};
