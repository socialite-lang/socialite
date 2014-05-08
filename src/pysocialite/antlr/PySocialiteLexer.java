// $ANTLR 3.4 /x/jiwon/workspace/socialite/grammar/PySocialite.g 2014-05-04 00:33:09

    package pysocialite.antlr;
    import org.python.core.PyObject;
    import org.python.core.PyString;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class PySocialiteLexer extends Lexer {
    public static final int EOF=-1;
    public static final int ANY_OTHER_CHAR=4;
    public static final int COMMENT=5;
    public static final int ESC_SEQ=6;
    public static final int EXPONENT=7;
    public static final int HEX_DIGIT=8;
    public static final int OCTAL_ESC=9;
    public static final int PROG=10;
    public static final int QUERY=11;
    public static final int STRING=12;
    public static final int UNICODE_ESC=13;
    public static final int WS=14;

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


    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public PySocialiteLexer() {} 
    public PySocialiteLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public PySocialiteLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "/x/jiwon/workspace/socialite/grammar/PySocialite.g"; }

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:75:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' | '/*' ( options {greedy=false; } : . )* '*/' | '#' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            int alt6=3;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='/') ) {
                int LA6_1 = input.LA(2);

                if ( (LA6_1=='/') ) {
                    alt6=1;
                }
                else if ( (LA6_1=='*') ) {
                    alt6=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA6_0=='#') ) {
                alt6=3;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;

            }
            switch (alt6) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:75:9: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
                    {
                    match("//"); 



                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:75:14: (~ ( '\\n' | '\\r' ) )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( ((LA1_0 >= '\u0000' && LA1_0 <= '\t')||(LA1_0 >= '\u000B' && LA1_0 <= '\f')||(LA1_0 >= '\u000E' && LA1_0 <= '\uFFFF')) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:
                    	    {
                    	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop1;
                        }
                    } while (true);


                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:75:28: ( '\\r' )?
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0=='\r') ) {
                        alt2=1;
                    }
                    switch (alt2) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:75:28: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }


                    match('\n'); 

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:76:9: '/*' ( options {greedy=false; } : . )* '*/'
                    {
                    match("/*"); 



                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:76:14: ( options {greedy=false; } : . )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0=='*') ) {
                            int LA3_1 = input.LA(2);

                            if ( (LA3_1=='/') ) {
                                alt3=2;
                            }
                            else if ( ((LA3_1 >= '\u0000' && LA3_1 <= '.')||(LA3_1 >= '0' && LA3_1 <= '\uFFFF')) ) {
                                alt3=1;
                            }


                        }
                        else if ( ((LA3_0 >= '\u0000' && LA3_0 <= ')')||(LA3_0 >= '+' && LA3_0 <= '\uFFFF')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:76:42: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);


                    match("*/"); 



                    }
                    break;
                case 3 :
                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:77:9: '#' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
                    {
                    match('#'); 

                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:77:14: (~ ( '\\n' | '\\r' ) )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( ((LA4_0 >= '\u0000' && LA4_0 <= '\t')||(LA4_0 >= '\u000B' && LA4_0 <= '\f')||(LA4_0 >= '\u000E' && LA4_0 <= '\uFFFF')) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:
                    	    {
                    	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);


                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:77:28: ( '\\r' )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0=='\r') ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:77:28: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }


                    match('\n'); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:80:5: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:
            {
            if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:88:5: ( '\"' ( ESC_SEQ |~ '\"' )* '\"' | 'r' '\"' ( options {greedy=false; } : . )* '\"' | '\\'' ( ESC_SEQ |~ '\\'' )* '\\'' | 'r' '\\'' ( options {greedy=false; } : . )* '\\'' | '\"\"\"' ( options {greedy=false; } : . )* '\"\"\"' )
            int alt12=5;
            switch ( input.LA(1) ) {
            case '\"':
                {
                int LA12_1 = input.LA(2);

                if ( (LA12_1=='\"') ) {
                    int LA12_4 = input.LA(3);

                    if ( (LA12_4=='\"') ) {
                        alt12=5;
                    }
                    else {
                        alt12=1;
                    }
                }
                else if ( ((LA12_1 >= '\u0000' && LA12_1 <= '!')||(LA12_1 >= '#' && LA12_1 <= '\uFFFF')) ) {
                    alt12=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 12, 1, input);

                    throw nvae;

                }
                }
                break;
            case 'r':
                {
                int LA12_2 = input.LA(2);

                if ( (LA12_2=='\"') ) {
                    alt12=2;
                }
                else if ( (LA12_2=='\'') ) {
                    alt12=4;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 12, 2, input);

                    throw nvae;

                }
                }
                break;
            case '\'':
                {
                alt12=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;

            }

            switch (alt12) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:88:8: '\"' ( ESC_SEQ |~ '\"' )* '\"'
                    {
                    match('\"'); 

                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:88:12: ( ESC_SEQ |~ '\"' )*
                    loop7:
                    do {
                        int alt7=3;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0=='\\') ) {
                            int LA7_2 = input.LA(2);

                            if ( (LA7_2=='\"') ) {
                                int LA7_4 = input.LA(3);

                                if ( ((LA7_4 >= '\u0000' && LA7_4 <= '\uFFFF')) ) {
                                    alt7=1;
                                }

                                else {
                                    alt7=2;
                                }


                            }
                            else if ( (LA7_2=='u') ) {
                                int LA7_5 = input.LA(3);

                                if ( ((LA7_5 >= '0' && LA7_5 <= '9')||(LA7_5 >= 'A' && LA7_5 <= 'F')||(LA7_5 >= 'a' && LA7_5 <= 'f')) ) {
                                    int LA7_10 = input.LA(4);

                                    if ( ((LA7_10 >= '0' && LA7_10 <= '9')||(LA7_10 >= 'A' && LA7_10 <= 'F')||(LA7_10 >= 'a' && LA7_10 <= 'f')) ) {
                                        int LA7_11 = input.LA(5);

                                        if ( ((LA7_11 >= '0' && LA7_11 <= '9')||(LA7_11 >= 'A' && LA7_11 <= 'F')||(LA7_11 >= 'a' && LA7_11 <= 'f')) ) {
                                            int LA7_12 = input.LA(6);

                                            if ( ((LA7_12 >= '0' && LA7_12 <= '9')||(LA7_12 >= 'A' && LA7_12 <= 'F')||(LA7_12 >= 'a' && LA7_12 <= 'f')) ) {
                                                alt7=1;
                                            }
                                            else if ( ((LA7_12 >= '\u0000' && LA7_12 <= '/')||(LA7_12 >= ':' && LA7_12 <= '@')||(LA7_12 >= 'G' && LA7_12 <= '`')||(LA7_12 >= 'g' && LA7_12 <= '\uFFFF')) ) {
                                                alt7=2;
                                            }


                                        }
                                        else if ( ((LA7_11 >= '\u0000' && LA7_11 <= '/')||(LA7_11 >= ':' && LA7_11 <= '@')||(LA7_11 >= 'G' && LA7_11 <= '`')||(LA7_11 >= 'g' && LA7_11 <= '\uFFFF')) ) {
                                            alt7=2;
                                        }


                                    }
                                    else if ( ((LA7_10 >= '\u0000' && LA7_10 <= '/')||(LA7_10 >= ':' && LA7_10 <= '@')||(LA7_10 >= 'G' && LA7_10 <= '`')||(LA7_10 >= 'g' && LA7_10 <= '\uFFFF')) ) {
                                        alt7=2;
                                    }


                                }
                                else if ( ((LA7_5 >= '\u0000' && LA7_5 <= '/')||(LA7_5 >= ':' && LA7_5 <= '@')||(LA7_5 >= 'G' && LA7_5 <= '`')||(LA7_5 >= 'g' && LA7_5 <= '\uFFFF')) ) {
                                    alt7=2;
                                }


                            }
                            else if ( ((LA7_2 >= '0' && LA7_2 <= '3')) ) {
                                alt7=1;
                            }
                            else if ( ((LA7_2 >= '4' && LA7_2 <= '7')) ) {
                                alt7=1;
                            }
                            else if ( (LA7_2=='\\') ) {
                                alt7=1;
                            }
                            else if ( (LA7_2=='\''||LA7_2=='b'||LA7_2=='f'||LA7_2=='n'||LA7_2=='r'||LA7_2=='t') ) {
                                alt7=1;
                            }
                            else if ( ((LA7_2 >= '\u0000' && LA7_2 <= '!')||(LA7_2 >= '#' && LA7_2 <= '&')||(LA7_2 >= '(' && LA7_2 <= '/')||(LA7_2 >= '8' && LA7_2 <= '[')||(LA7_2 >= ']' && LA7_2 <= 'a')||(LA7_2 >= 'c' && LA7_2 <= 'e')||(LA7_2 >= 'g' && LA7_2 <= 'm')||(LA7_2 >= 'o' && LA7_2 <= 'q')||LA7_2=='s'||(LA7_2 >= 'v' && LA7_2 <= '\uFFFF')) ) {
                                alt7=2;
                            }


                        }
                        else if ( ((LA7_0 >= '\u0000' && LA7_0 <= '!')||(LA7_0 >= '#' && LA7_0 <= '[')||(LA7_0 >= ']' && LA7_0 <= '\uFFFF')) ) {
                            alt7=2;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:88:14: ESC_SEQ
                    	    {
                    	    mESC_SEQ(); 


                    	    }
                    	    break;
                    	case 2 :
                    	    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:88:24: ~ '\"'
                    	    {
                    	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '\uFFFF') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);


                    match('\"'); 

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:89:8: 'r' '\"' ( options {greedy=false; } : . )* '\"'
                    {
                    match('r'); 

                    match('\"'); 

                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:89:16: ( options {greedy=false; } : . )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0=='\"') ) {
                            alt8=2;
                        }
                        else if ( ((LA8_0 >= '\u0000' && LA8_0 <= '!')||(LA8_0 >= '#' && LA8_0 <= '\uFFFF')) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:89:44: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop8;
                        }
                    } while (true);


                    match('\"'); 

                    }
                    break;
                case 3 :
                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:90:8: '\\'' ( ESC_SEQ |~ '\\'' )* '\\''
                    {
                    match('\''); 

                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:90:13: ( ESC_SEQ |~ '\\'' )*
                    loop9:
                    do {
                        int alt9=3;
                        int LA9_0 = input.LA(1);

                        if ( (LA9_0=='\\') ) {
                            int LA9_2 = input.LA(2);

                            if ( (LA9_2=='\'') ) {
                                int LA9_4 = input.LA(3);

                                if ( ((LA9_4 >= '\u0000' && LA9_4 <= '\uFFFF')) ) {
                                    alt9=1;
                                }

                                else {
                                    alt9=2;
                                }


                            }
                            else if ( (LA9_2=='u') ) {
                                int LA9_5 = input.LA(3);

                                if ( ((LA9_5 >= '0' && LA9_5 <= '9')||(LA9_5 >= 'A' && LA9_5 <= 'F')||(LA9_5 >= 'a' && LA9_5 <= 'f')) ) {
                                    int LA9_10 = input.LA(4);

                                    if ( ((LA9_10 >= '0' && LA9_10 <= '9')||(LA9_10 >= 'A' && LA9_10 <= 'F')||(LA9_10 >= 'a' && LA9_10 <= 'f')) ) {
                                        int LA9_11 = input.LA(5);

                                        if ( ((LA9_11 >= '0' && LA9_11 <= '9')||(LA9_11 >= 'A' && LA9_11 <= 'F')||(LA9_11 >= 'a' && LA9_11 <= 'f')) ) {
                                            int LA9_12 = input.LA(6);

                                            if ( ((LA9_12 >= '0' && LA9_12 <= '9')||(LA9_12 >= 'A' && LA9_12 <= 'F')||(LA9_12 >= 'a' && LA9_12 <= 'f')) ) {
                                                alt9=1;
                                            }
                                            else if ( ((LA9_12 >= '\u0000' && LA9_12 <= '/')||(LA9_12 >= ':' && LA9_12 <= '@')||(LA9_12 >= 'G' && LA9_12 <= '`')||(LA9_12 >= 'g' && LA9_12 <= '\uFFFF')) ) {
                                                alt9=2;
                                            }


                                        }
                                        else if ( ((LA9_11 >= '\u0000' && LA9_11 <= '/')||(LA9_11 >= ':' && LA9_11 <= '@')||(LA9_11 >= 'G' && LA9_11 <= '`')||(LA9_11 >= 'g' && LA9_11 <= '\uFFFF')) ) {
                                            alt9=2;
                                        }


                                    }
                                    else if ( ((LA9_10 >= '\u0000' && LA9_10 <= '/')||(LA9_10 >= ':' && LA9_10 <= '@')||(LA9_10 >= 'G' && LA9_10 <= '`')||(LA9_10 >= 'g' && LA9_10 <= '\uFFFF')) ) {
                                        alt9=2;
                                    }


                                }
                                else if ( ((LA9_5 >= '\u0000' && LA9_5 <= '/')||(LA9_5 >= ':' && LA9_5 <= '@')||(LA9_5 >= 'G' && LA9_5 <= '`')||(LA9_5 >= 'g' && LA9_5 <= '\uFFFF')) ) {
                                    alt9=2;
                                }


                            }
                            else if ( ((LA9_2 >= '0' && LA9_2 <= '3')) ) {
                                alt9=1;
                            }
                            else if ( ((LA9_2 >= '4' && LA9_2 <= '7')) ) {
                                alt9=1;
                            }
                            else if ( (LA9_2=='\\') ) {
                                alt9=1;
                            }
                            else if ( (LA9_2=='\"'||LA9_2=='b'||LA9_2=='f'||LA9_2=='n'||LA9_2=='r'||LA9_2=='t') ) {
                                alt9=1;
                            }
                            else if ( ((LA9_2 >= '\u0000' && LA9_2 <= '!')||(LA9_2 >= '#' && LA9_2 <= '&')||(LA9_2 >= '(' && LA9_2 <= '/')||(LA9_2 >= '8' && LA9_2 <= '[')||(LA9_2 >= ']' && LA9_2 <= 'a')||(LA9_2 >= 'c' && LA9_2 <= 'e')||(LA9_2 >= 'g' && LA9_2 <= 'm')||(LA9_2 >= 'o' && LA9_2 <= 'q')||LA9_2=='s'||(LA9_2 >= 'v' && LA9_2 <= '\uFFFF')) ) {
                                alt9=2;
                            }


                        }
                        else if ( ((LA9_0 >= '\u0000' && LA9_0 <= '&')||(LA9_0 >= '(' && LA9_0 <= '[')||(LA9_0 >= ']' && LA9_0 <= '\uFFFF')) ) {
                            alt9=2;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:90:15: ESC_SEQ
                    	    {
                    	    mESC_SEQ(); 


                    	    }
                    	    break;
                    	case 2 :
                    	    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:90:25: ~ '\\''
                    	    {
                    	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '&')||(input.LA(1) >= '(' && input.LA(1) <= '\uFFFF') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop9;
                        }
                    } while (true);


                    match('\''); 

                    }
                    break;
                case 4 :
                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:91:8: 'r' '\\'' ( options {greedy=false; } : . )* '\\''
                    {
                    match('r'); 

                    match('\''); 

                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:91:17: ( options {greedy=false; } : . )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0=='\'') ) {
                            alt10=2;
                        }
                        else if ( ((LA10_0 >= '\u0000' && LA10_0 <= '&')||(LA10_0 >= '(' && LA10_0 <= '\uFFFF')) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:91:45: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);


                    match('\''); 

                    }
                    break;
                case 5 :
                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:92:8: '\"\"\"' ( options {greedy=false; } : . )* '\"\"\"'
                    {
                    match("\"\"\""); 



                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:92:14: ( options {greedy=false; } : . )*
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( (LA11_0=='\"') ) {
                            int LA11_1 = input.LA(2);

                            if ( (LA11_1=='\"') ) {
                                int LA11_3 = input.LA(3);

                                if ( (LA11_3=='\"') ) {
                                    alt11=2;
                                }
                                else if ( ((LA11_3 >= '\u0000' && LA11_3 <= '!')||(LA11_3 >= '#' && LA11_3 <= '\uFFFF')) ) {
                                    alt11=1;
                                }


                            }
                            else if ( ((LA11_1 >= '\u0000' && LA11_1 <= '!')||(LA11_1 >= '#' && LA11_1 <= '\uFFFF')) ) {
                                alt11=1;
                            }


                        }
                        else if ( ((LA11_0 >= '\u0000' && LA11_0 <= '!')||(LA11_0 >= '#' && LA11_0 <= '\uFFFF')) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:92:42: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop11;
                        }
                    } while (true);


                    match("\"\"\""); 



                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "EXPONENT"
    public final void mEXPONENT() throws RecognitionException {
        try {
            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:97:10: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:97:12: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:97:22: ( '+' | '-' )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0=='+'||LA13_0=='-') ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;

            }


            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:97:33: ( '0' .. '9' )+
            int cnt14=0;
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0 >= '0' && LA14_0 <= '9')) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt14 >= 1 ) break loop14;
                        EarlyExitException eee =
                            new EarlyExitException(14, input);
                        throw eee;
                }
                cnt14++;
            } while (true);


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EXPONENT"

    // $ANTLR start "HEX_DIGIT"
    public final void mHEX_DIGIT() throws RecognitionException {
        try {
            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:100:11: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:
            {
            if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'F')||(input.LA(1) >= 'a' && input.LA(1) <= 'f') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "HEX_DIGIT"

    // $ANTLR start "ESC_SEQ"
    public final void mESC_SEQ() throws RecognitionException {
        try {
            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:104:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | UNICODE_ESC | OCTAL_ESC )
            int alt15=3;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='\\') ) {
                switch ( input.LA(2) ) {
                case '\"':
                case '\'':
                case '\\':
                case 'b':
                case 'f':
                case 'n':
                case 'r':
                case 't':
                    {
                    alt15=1;
                    }
                    break;
                case 'u':
                    {
                    alt15=2;
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
                    {
                    alt15=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 1, input);

                    throw nvae;

                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;

            }
            switch (alt15) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:104:9: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
                    {
                    match('\\'); 

                    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:105:9: UNICODE_ESC
                    {
                    mUNICODE_ESC(); 


                    }
                    break;
                case 3 :
                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:106:9: OCTAL_ESC
                    {
                    mOCTAL_ESC(); 


                    }
                    break;

            }

        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ESC_SEQ"

    // $ANTLR start "OCTAL_ESC"
    public final void mOCTAL_ESC() throws RecognitionException {
        try {
            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:111:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt16=3;
            int LA16_0 = input.LA(1);

            if ( (LA16_0=='\\') ) {
                int LA16_1 = input.LA(2);

                if ( ((LA16_1 >= '0' && LA16_1 <= '3')) ) {
                    int LA16_2 = input.LA(3);

                    if ( ((LA16_2 >= '0' && LA16_2 <= '7')) ) {
                        int LA16_4 = input.LA(4);

                        if ( ((LA16_4 >= '0' && LA16_4 <= '7')) ) {
                            alt16=1;
                        }
                        else {
                            alt16=2;
                        }
                    }
                    else {
                        alt16=3;
                    }
                }
                else if ( ((LA16_1 >= '4' && LA16_1 <= '7')) ) {
                    int LA16_3 = input.LA(3);

                    if ( ((LA16_3 >= '0' && LA16_3 <= '7')) ) {
                        alt16=2;
                    }
                    else {
                        alt16=3;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 16, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;

            }
            switch (alt16) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:111:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 

                    if ( (input.LA(1) >= '0' && input.LA(1) <= '3') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:112:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 

                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;
                case 3 :
                    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:113:9: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); 

                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;

            }

        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OCTAL_ESC"

    // $ANTLR start "UNICODE_ESC"
    public final void mUNICODE_ESC() throws RecognitionException {
        try {
            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:118:5: ( '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT )
            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:118:9: '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
            {
            match('\\'); 

            match('u'); 

            mHEX_DIGIT(); 


            mHEX_DIGIT(); 


            mHEX_DIGIT(); 


            mHEX_DIGIT(); 


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "UNICODE_ESC"

    // $ANTLR start "QUERY"
    public final void mQUERY() throws RecognitionException {
        try {
            int _type = QUERY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:121:5: ( '`' ( ESC_SEQ |~ ( '\\\\' | '`' ) )* '`' )
            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:121:7: '`' ( ESC_SEQ |~ ( '\\\\' | '`' ) )* '`'
            {
            match('`'); 

            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:121:11: ( ESC_SEQ |~ ( '\\\\' | '`' ) )*
            loop17:
            do {
                int alt17=3;
                int LA17_0 = input.LA(1);

                if ( (LA17_0=='\\') ) {
                    alt17=1;
                }
                else if ( ((LA17_0 >= '\u0000' && LA17_0 <= '[')||(LA17_0 >= ']' && LA17_0 <= '_')||(LA17_0 >= 'a' && LA17_0 <= '\uFFFF')) ) {
                    alt17=2;
                }


                switch (alt17) {
            	case 1 :
            	    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:121:12: ESC_SEQ
            	    {
            	    mESC_SEQ(); 


            	    }
            	    break;
            	case 2 :
            	    // /x/jiwon/workspace/socialite/grammar/PySocialite.g:121:22: ~ ( '\\\\' | '`' )
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= '_')||(input.LA(1) >= 'a' && input.LA(1) <= '\uFFFF') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);


            match('`'); 

             this.setText(transform(this.getText()));

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "QUERY"

    // $ANTLR start "ANY_OTHER_CHAR"
    public final void mANY_OTHER_CHAR() throws RecognitionException {
        try {
            int _type = ANY_OTHER_CHAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:125:2: ( . )
            // /x/jiwon/workspace/socialite/grammar/PySocialite.g:125:3: .
            {
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ANY_OTHER_CHAR"

    public void mTokens() throws RecognitionException {
        // /x/jiwon/workspace/socialite/grammar/PySocialite.g:1:8: ( COMMENT | WS | STRING | QUERY | ANY_OTHER_CHAR )
        int alt18=5;
        int LA18_0 = input.LA(1);

        if ( (LA18_0=='/') ) {
            int LA18_1 = input.LA(2);

            if ( (LA18_1=='*'||LA18_1=='/') ) {
                alt18=1;
            }
            else {
                alt18=5;
            }
        }
        else if ( (LA18_0=='#') ) {
            int LA18_2 = input.LA(2);

            if ( ((LA18_2 >= '\u0000' && LA18_2 <= '\uFFFF')) ) {
                alt18=1;
            }
            else {
                alt18=5;
            }
        }
        else if ( ((LA18_0 >= '\t' && LA18_0 <= '\n')||LA18_0=='\r'||LA18_0==' ') ) {
            alt18=2;
        }
        else if ( (LA18_0=='\"') ) {
            int LA18_4 = input.LA(2);

            if ( ((LA18_4 >= '\u0000' && LA18_4 <= '\uFFFF')) ) {
                alt18=3;
            }
            else {
                alt18=5;
            }
        }
        else if ( (LA18_0=='r') ) {
            int LA18_5 = input.LA(2);

            if ( (LA18_5=='\"'||LA18_5=='\'') ) {
                alt18=3;
            }
            else {
                alt18=5;
            }
        }
        else if ( (LA18_0=='\'') ) {
            int LA18_6 = input.LA(2);

            if ( ((LA18_6 >= '\u0000' && LA18_6 <= '\uFFFF')) ) {
                alt18=3;
            }
            else {
                alt18=5;
            }
        }
        else if ( (LA18_0=='`') ) {
            int LA18_7 = input.LA(2);

            if ( ((LA18_7 >= '\u0000' && LA18_7 <= '\uFFFF')) ) {
                alt18=4;
            }
            else {
                alt18=5;
            }
        }
        else if ( ((LA18_0 >= '\u0000' && LA18_0 <= '\b')||(LA18_0 >= '\u000B' && LA18_0 <= '\f')||(LA18_0 >= '\u000E' && LA18_0 <= '\u001F')||LA18_0=='!'||(LA18_0 >= '$' && LA18_0 <= '&')||(LA18_0 >= '(' && LA18_0 <= '.')||(LA18_0 >= '0' && LA18_0 <= '_')||(LA18_0 >= 'a' && LA18_0 <= 'q')||(LA18_0 >= 's' && LA18_0 <= '\uFFFF')) ) {
            alt18=5;
        }
        else {
            NoViableAltException nvae =
                new NoViableAltException("", 18, 0, input);

            throw nvae;

        }
        switch (alt18) {
            case 1 :
                // /x/jiwon/workspace/socialite/grammar/PySocialite.g:1:10: COMMENT
                {
                mCOMMENT(); 


                }
                break;
            case 2 :
                // /x/jiwon/workspace/socialite/grammar/PySocialite.g:1:18: WS
                {
                mWS(); 


                }
                break;
            case 3 :
                // /x/jiwon/workspace/socialite/grammar/PySocialite.g:1:21: STRING
                {
                mSTRING(); 


                }
                break;
            case 4 :
                // /x/jiwon/workspace/socialite/grammar/PySocialite.g:1:28: QUERY
                {
                mQUERY(); 


                }
                break;
            case 5 :
                // /x/jiwon/workspace/socialite/grammar/PySocialite.g:1:34: ANY_OTHER_CHAR
                {
                mANY_OTHER_CHAR(); 


                }
                break;

        }

    }


 

}