grammar PySocialite;
options {
    language=Java;
}
tokens {
    PROG;
}
@header {
    package pysocialite.antlr;    
    import java.util.List;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.Set;
    import java.util.LinkedHashSet;
}
@lexer::header{
    package pysocialite.antlr;
    import org.python.core.PyObject;
    import org.python.core.PyString;
}
@lexer::members{
    static String socialiteMod="socialiteModule";
    static PyObject substPythonVars=null;
    public static void setSocialiteModule(String mod) { socialiteMod = mod; }
    public static void setSubstFunc(PyObject subst) { substPythonVars = subst; }

    String socialiteVar() { return socialiteMod+".engine"; /* see SociaLite.py */ }
    String tableIterClass() { return socialiteMod+".TableIterator"; /* see SociaLite.py */ }

    boolean isTableIter(String query) {
        String q=query.trim();
        if (q.charAt(q.length()-1)=='.') return false;
        if (q.indexOf(":-")<0) return true;

        return true;
    }
    public String transform(String quoted) {
        String query = quoted.substring(1,quoted.length()-1);
        if (isTableIter(query)) {
            return tableIter(query);
        } else {
            return runQuery(query);
        }
    }
    String escape(String s) {
        s = s.replace("\"", "\\\"");
        return s;
    }
    String quote(String s) { return "\"\"\""+escape(s)+"\"\"\""; }
    String maybeSubstPythonVars(String query) {
        if (query.indexOf('$')>=0) {
            PyObject ret=substPythonVars.__call__(new PyString(query));
            query = (String)ret.__tojava__(String.class);
        }
        return query;
    }
    String tableIter(String query) {
        query = "?-"+query+".";
        query = quote(query);
        query = maybeSubstPythonVars(query);
        return socialiteMod+".TableIterator("+socialiteVar()+", "+query+")";
    }
    String runQuery(String query) {
        query = quote(query);
        query = maybeSubstPythonVars(query);
        return socialiteVar()+".run("+query+")";
    }
}
prog returns [String str]
@init{StringBuilder builder = new StringBuilder();}
    : (t=. {builder.append($t.text);})* EOF {$str = builder.toString();}
    ;
    
COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' 
    |   '/*' ( options {greedy=false;} : . )* '*/'
    |   '#'  ~('\n'|'\r')* '\r'? '\n'
    ;

WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) 
    ;

STRING
    :  '"' ( ESC_SEQ | ~'"' )* '"'
    |  'r' '"' ( options {greedy=false;} : . )* '"'
    |  '\'' ( ESC_SEQ | ~'\'' )* '\''
    |  'r' '\'' ( options {greedy=false;} : . )* '\''
    |  '"""' ( options {greedy=false;} : . )* '"""'
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

QUERY
    : '`' (ESC_SEQ | ~('\\'|'`'))*  '`'  { this.setText(transform(this.getText()));} 
    ;

ANY_OTHER_CHAR
	:.
	;
