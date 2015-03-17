grammar SociaLite;
options {
    language=Java;
    output=AST;
    ASTLabelType=CommonTree;
}
tokens {
    PROG;
    DECL;
    COL_DECLS;
    OPTION;
    RULE;
    QUERY;
    PREDICATE;
    FUNCTION;
    FUNC;
    EXPR;
    COMPOUND_EXPR;    
    TERM;
    MULTI_ASSIGN;
    T_INT;
    T_FLOAT;
    T_STR;
    T_UTF8;
    T_VAR;
    INDEX;
    COL_DECL;
    CLEAR; // db clear
    DROP; // db drop
    SORT_BY;
    ORDER_BY;
    INDEX_BY;
    GROUP_BY;
    RANGE;
    ITER;
    PREDEFINED;
    CONCURRENT;
    APPROX;
    MULTISET;
}
@header {
    package socialite.parser.antlr;    
    import socialite.parser.Query;
    import socialite.parser.Variable;
    import socialite.parser.Predicate;
    import socialite.parser.Function;
    import socialite.parser.AggrFunction;
    import socialite.parser.Expr;
    import socialite.parser.Op;    
    import socialite.parser.CmpOp;
    import socialite.parser.BinOp;
    import socialite.parser.AssignOp;
    import socialite.parser.UnaryMinus;
    import socialite.parser.TypeCast;
    import socialite.parser.hash;
    import socialite.util.ParseException;
    import socialite.util.Assert;
    import java.util.List;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.Set;
    import java.util.LinkedHashSet;
}
@lexer::header{
    package socialite.parser.antlr;
}
@members {
    public socialite.parser.Parser parser;
    public HashMap<String, TableDecl> tableDeclMap = new HashMap<String, TableDecl>();
    Set<Function> funcsInRule = new LinkedHashSet<Function>();
    
    public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
//        String hdr = getErrorHeader(e);
        String msg = getErrorMessage(e, tokenNames);
        throw new ParseException(parser, e, msg);
    }
}
prog	:stat+ EOF;
stat	:table_decl
	|rule
	|query
	|table_stmt
	;
table_stmt	: 'clear' ID DOT_END -> ^(CLEAR ID) 
	|'drop' ID DOT_END -> ^(DROP ID?)
	;

query	:'?-' predicate DOT_END -> ^(QUERY predicate)
	;

rule    : head ':-' litlist (';' ':-' litlist)* DOT_END
        -> ^(RULE head litlist)+ DOT_END
        ;
head : predicate;

litlist	:literal (','! literal)*;

literal	:predicate -> ^(PREDICATE  predicate)
	| NOT predicate -> ^(PREDICATE NOT predicate)
	|expr -> ^(EXPR expr)
	; 
expr:	simpleExpr  ('<'^ | '>'^ | '='^| '!='^ | '=='^ | '>='^ | '<='^ ) simpleExpr
	| varlist '=' cast? function -> ^(MULTI_ASSIGN varlist function cast?)
	;	
simpleExpr:	multExpr  (('+'^|'-'^) multExpr)*
	;	
multExpr:	exprValue (('*'^|'/'^|'mod'^) exprValue)*;
exprValue:	(neg='-')? cast? term -> ^(TERM term $neg? cast?)
	| cast? function -> ^(FUNCTION function cast?)
	| cast? compExpr -> ^(COMPOUND_EXPR compExpr cast?)
	;
compExpr:	'('! simpleExpr ')'! ;	
cast:	'(' type ')' -> type ;	
varlist:	'('! e1=dotname  (','! e2=dotname)+ ')'! ;
function:	'$' dotname '(' fparamlist? ')' -> ^(FUNC dotname fparamlist?)
	;
//predicate	:ID ('[' param ']')? '(' paramlist? ')' -> ID ^(INDEX param?) paramlist?;
predicate	:ID '(' paramlist ')' -> ID paramlist;

paramlist	:param (','! param)*;
fparamlist	:fparam (','! fparam)*;

param	:simpleExpr;
fparam	:simpleExpr;

term:	INT -> ^(T_INT INT )
	|FLOAT -> ^(T_FLOAT FLOAT)
	|STRING -> ^(T_STR STRING)
	|UTF8 -> ^(T_UTF8 UTF8)	
	|dotname -> ^(T_VAR dotname)
	;
table_decl: ID '(' decls ')' table_opts? DOT_END  -> ^(DECL ID decls ^(OPTION table_opts?))
        ;
table_opts: t_opt (','! t_opt)*
	;
t_opt	: 'sortby' col=ID (order=SORT_ORDER)? -> ^(SORT_BY $col $order?)
	|'orderby' ID -> ^(ORDER_BY ID)
	|'indexby' ID -> ^(INDEX_BY ID)
	|'groupby' '(' INT ')' -> ^(GROUP_BY INT)
	|'predefined' -> PREDEFINED
	|'concurrent' -> CONCURRENT
	|'multiset' -> MULTISET
	;
SORT_ORDER: 'asc'|'desc'
	;
decls	: col_decls (',' '(' decls ')')? -> ^(COL_DECLS col_decls ^(DECL decls?))
	;	
col_decls: col_decl (','! col_decl)*
	;
col_decl: type ID (':' col_opt)?-> ^(COL_DECL type ID col_opt?)
	;
col_opt	:  i1=INT '..' i2=INT -> ^(RANGE $i1 $i2)
	| ITER_DECL -> ITER
	;

type:	'int' ('[' ']')?
	|'long' ('[' ']')?  
	|'float' ('[' ']')?  
	|'double' ('[' ']')?  
	|'String' ('[' ']')?  
	|'Object' ('[' ']')?
	| ID ('[' ']')?  
	;
dotname	:ID (DOT_ID)*
	;

ITER_DECL: ('iter' | 'iterator');
NOT:	('not' | '!');
ID  :	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;
DOT_ID:	'.'('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;

DOT_END: ({input.LA(2)=='\r'||input.LA(2)=='\n'||
           input.LA(2)==' '||input.LA(2)=='\t'|| input.LA(2)==EOF}?) '.'
        ;

//DOT_ID: ID
//        ({input.LA(1)=='.' && input.LA(2)!=EOF && input.LA(2)!='\n' &&
//          ((input.LA(2)>='a'&&input.LA(2)<='z')||
//           (input.LA(2)>='A'&&input.LA(2)<='Z')||input.LA(2)=='_')
//            }? => ('.' ID)+ {$type = DOT_ID;}
//          | {$type = ID;})
//	;
INT :	('0'..'9')+ ('l'|'L')?
    ;
FLOAT
    : ('0'..'9')+ 
    ( {input.LA(2) != '.' &&
       input.LA(2) != ' ' && input.LA(2) != '\t' && input.LA(2) != '\r' && input.LA(2) != '\n' &&
        input.LA(2) != EOF }? => '.' ('0'..'9')+ EXPONENT? ('f'|'F'|'d'|'D')? {$type = FLOAT;}
        |  {$type = INT;} )
    |  ('0'..'9')+ EXPONENT
    ;
/*FLOAT
    :   ('0'..'9')+ '.' ('0'..'9')+ EXPONENT?
    |   ('0'..'9')+ EXPONENT
    ;*/
//    |  '.' ('0'..'9')+ EXPONENT?

COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    |   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    | '#'  ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;

WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) {$channel=HIDDEN;}
    ;

UTF8
    :  'u' '"' ( ESC_SEQ | ~('\\'|'"') )* '"'
    ;

STRING
    :  '"' ( ESC_SEQ | ~('\\'|'"') )* '"'
    ;

CHAR:  '\'' ( ESC_SEQ | ~('\''|'\\') ) '\''
    ;

fragment
EXPONENT : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

fragment
HEX_DIGIT : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
ESC_SEQ
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UNICODE_ESC
    |   OCTAL_ESC
    ;

fragment
OCTAL_ESC
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UNICODE_ESC
    :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;
