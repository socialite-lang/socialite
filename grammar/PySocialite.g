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
    import socialite.util.SociaLiteException;
    import pysocialite.Format;
    import org.apache.commons.lang3.StringUtils;
}
@lexer::members{
    static String socialiteMod="socialiteModule";
    public static void setSocialiteModule(String mod) { socialiteMod = mod; }

    static String socialiteVar() { return socialiteMod+".engine"; /* see SociaLite.py */ }
    static String stringifyPythonVarFunc() { return socialiteMod+".passVars"; /* see SociaLite.py */ }
    static String tableIterClass() { return socialiteMod+".TableIterator"; /* see SociaLite.py */ }
    static String quoteStr() { return "\"\"\"";}
    static String singleQuote() { return "'";}

    ArrayList<String> varnames = new ArrayList<String>();
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
    String quote(String s) { 
        if (s.indexOf('\n') < 0 && s.indexOf(singleQuote()) < 0) {
            return singleQuote()+escape(s)+singleQuote();
        }
        return quoteStr()+escape(s)+quoteStr();
    }

    int skipTo(String src, int offset, String to) {
        int newOffset = src.indexOf(to, offset);
        if (newOffset<0) {
            throw new SociaLiteException("EOL while scanning string literal");
        }
        return newOffset;
    }

    boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
    boolean isValidVarNameChar1(char c) {
        return c=='_' || StringUtils.isAlpha(String.valueOf(c));
    }
    boolean isValidVarNameChar(char c) {
        return c=='_' || StringUtils.isAlpha(String.valueOf(c)) || isDigit(c);
    }
    boolean isWhitespace(char c) {
        return StringUtils.isWhitespace(String.valueOf(c));
    }
    boolean isEndOfVarChar(char c) {
        if (Character.isWhitespace(c)) return true;
        if (c==')' || c==',' || c==';' ||
            c=='+' || c=='-' || c=='*' || c=='/' ||
            c=='=' || c=='<' || c=='>' || c==Format.percent()) {
            return true;
        }
        return false;
    }

    // returns beginning and ending indices of variable name.
    // indices are inclusive.
    int[] findPythonVars(String query, int offset) {
        int[] span = new int[2];
        int i=offset;
        while (i<query.length()) {
            if (query.charAt(i) == '\"') {
                i = skipTo(query, i+1, "\"")+1;
                continue;
            }
            if (query.charAt(i) == '$') {
                int j=i+1;
                if (!isValidVarNameChar1(query.charAt(j))) {
                    String s=query.substring(j,j+1);
                    String msg="Invalid variable name:$"+s+"... "+ ", name cannot start with "+s;
                    throw new SociaLiteException(msg);
                }
                boolean varFound=false;
                while (j<query.length()) {
                    char c=query.charAt(j);
                    if (c == '(') {
                        break; // function, will be handled by the parser
                    } if (c == '.') {
                        if (j+1<query.length()) {
                            if (isWhitespace(query.charAt(j+1))) {
                                varFound=true; // if next char is whitespace, consider it python var
                                break;
                            } else { // if next char is anything but whitespace, it is not python var
                                break;
                            }
                        } else { // if it is end of string, consider it python var
                            varFound=true;
                            break;
                        }
                    } else if (isEndOfVarChar(c)) {
                        varFound=true;
                        break;
                    } else if (isValidVarNameChar(c)) {
                        j+=1;
                    } else {
                        String s=query.substring(i,j+1), ch=query.substring(j, j+1);
                        String msg="Invalid variable name:"+s+", unexpected character:"+ch;
                        throw new SociaLiteException(msg);
                    }
                }
                if (j==query.length()) varFound=true;

                if (varFound) {
                    span[0] = i; span[1] = j-1;
                    return span;   
                } else { i = j; }
            } else {
                i++;
            }
        }
        return null; // not found!
    }

    String quoteAndSubstPythonVars(String query) {
        varnames.clear();
        if (query.indexOf('$')<0) return quote(query);

        String result = "";
        int offset=0;
        while (true) {
            int[] span = findPythonVars(query, offset);
            if (span==null) {
                result += query.substring(offset);
                break;
            }

            String var = query.substring(span[0]+1, span[1]+1); // excluding $ prefix
            varnames.add(var);
            result += query.substring(offset, span[0]);
            result += Format.str();

            offset = span[1]+1;
        }
        result = quote(result);

        if (varnames.size()>0) {
            result += Format.percent()+"("+ stringifyPythonVarFunc()+"(";
            boolean first = true;
            for (String var:varnames) {
                if (!first) result += ", ";
                result += var;
                first = false;
            }
            result += "))";
        }

        return result;
    }

    String tableIter(String query) {
        query = "?-"+query+".";
        query = quoteAndSubstPythonVars(query);
        return tableIterClass()+"("+socialiteVar()+", "+query+")";
    }
    String runQuery(String query) {
        query = quoteAndSubstPythonVars(query);
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
