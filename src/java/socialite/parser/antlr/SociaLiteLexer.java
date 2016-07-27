// $ANTLR 3.4 /Users/jiwon/workspace/socialite/grammar/SociaLite.g 2016-07-24 11:14:24

    package socialite.parser.antlr;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class SociaLiteLexer extends Lexer {
    public static final int EOF=-1;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__59=59;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__73=73;
    public static final int T__74=74;
    public static final int T__75=75;
    public static final int T__76=76;
    public static final int T__77=77;
    public static final int T__78=78;
    public static final int T__79=79;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int T__84=84;
    public static final int T__85=85;
    public static final int T__86=86;
    public static final int T__87=87;
    public static final int T__88=88;
    public static final int T__89=89;
    public static final int T__90=90;
    public static final int T__91=91;
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__95=95;
    public static final int APPROX=4;
    public static final int BIT=5;
    public static final int CHAR=6;
    public static final int CLEAR=7;
    public static final int COL_DECL=8;
    public static final int COL_DECLS=9;
    public static final int COMMENT=10;
    public static final int COMPOUND_EXPR=11;
    public static final int CONCURRENT=12;
    public static final int DECL=13;
    public static final int DOT_END=14;
    public static final int DOT_ID=15;
    public static final int DROP=16;
    public static final int ESC_SEQ=17;
    public static final int EXPONENT=18;
    public static final int EXPR=19;
    public static final int FLOAT=20;
    public static final int FUNC=21;
    public static final int FUNCTION=22;
    public static final int GROUP_BY=23;
    public static final int HEX_DIGIT=24;
    public static final int ID=25;
    public static final int INDEX=26;
    public static final int INDEX_BY=27;
    public static final int INT=28;
    public static final int ITER=29;
    public static final int ITER_DECL=30;
    public static final int MULTISET=31;
    public static final int MULTI_ASSIGN=32;
    public static final int NOT=33;
    public static final int OCTAL_ESC=34;
    public static final int OPTION=35;
    public static final int ORDER_BY=36;
    public static final int PREDEFINED=37;
    public static final int PREDICATE=38;
    public static final int PROG=39;
    public static final int QUERY=40;
    public static final int RANGE=41;
    public static final int RULE=42;
    public static final int SHARD_BY=43;
    public static final int SORT_BY=44;
    public static final int SORT_ORDER=45;
    public static final int STRING=46;
    public static final int TERM=47;
    public static final int T_FLOAT=48;
    public static final int T_INT=49;
    public static final int T_STR=50;
    public static final int T_UTF8=51;
    public static final int T_VAR=52;
    public static final int UNICODE_ESC=53;
    public static final int UTF8=54;
    public static final int WS=55;

    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public SociaLiteLexer() {} 
    public SociaLiteLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public SociaLiteLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "/Users/jiwon/workspace/socialite/grammar/SociaLite.g"; }

    // $ANTLR start "T__56"
    public final void mT__56() throws RecognitionException {
        try {
            int _type = T__56;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:11:7: ( '!=' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:11:9: '!='
            {
            match("!="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__56"

    // $ANTLR start "T__57"
    public final void mT__57() throws RecognitionException {
        try {
            int _type = T__57;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:12:7: ( '$' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:12:9: '$'
            {
            match('$'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__57"

    // $ANTLR start "T__58"
    public final void mT__58() throws RecognitionException {
        try {
            int _type = T__58;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:13:7: ( '(' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:13:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__58"

    // $ANTLR start "T__59"
    public final void mT__59() throws RecognitionException {
        try {
            int _type = T__59;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:14:7: ( ')' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:14:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__59"

    // $ANTLR start "T__60"
    public final void mT__60() throws RecognitionException {
        try {
            int _type = T__60;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:15:7: ( '*' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:15:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__60"

    // $ANTLR start "T__61"
    public final void mT__61() throws RecognitionException {
        try {
            int _type = T__61;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:16:7: ( '+' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:16:9: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__61"

    // $ANTLR start "T__62"
    public final void mT__62() throws RecognitionException {
        try {
            int _type = T__62;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:17:7: ( ',' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:17:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__62"

    // $ANTLR start "T__63"
    public final void mT__63() throws RecognitionException {
        try {
            int _type = T__63;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:18:7: ( '-' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:18:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__63"

    // $ANTLR start "T__64"
    public final void mT__64() throws RecognitionException {
        try {
            int _type = T__64;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:19:7: ( '..' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:19:9: '..'
            {
            match(".."); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__64"

    // $ANTLR start "T__65"
    public final void mT__65() throws RecognitionException {
        try {
            int _type = T__65;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:20:7: ( '/' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:20:9: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__65"

    // $ANTLR start "T__66"
    public final void mT__66() throws RecognitionException {
        try {
            int _type = T__66;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:21:7: ( ':' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:21:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__66"

    // $ANTLR start "T__67"
    public final void mT__67() throws RecognitionException {
        try {
            int _type = T__67;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:22:7: ( ':-' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:22:9: ':-'
            {
            match(":-"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__67"

    // $ANTLR start "T__68"
    public final void mT__68() throws RecognitionException {
        try {
            int _type = T__68;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:23:7: ( ';' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:23:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__68"

    // $ANTLR start "T__69"
    public final void mT__69() throws RecognitionException {
        try {
            int _type = T__69;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:24:7: ( '<' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:24:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__69"

    // $ANTLR start "T__70"
    public final void mT__70() throws RecognitionException {
        try {
            int _type = T__70;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:25:7: ( '<=' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:25:9: '<='
            {
            match("<="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__70"

    // $ANTLR start "T__71"
    public final void mT__71() throws RecognitionException {
        try {
            int _type = T__71;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:26:7: ( '=' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:26:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__71"

    // $ANTLR start "T__72"
    public final void mT__72() throws RecognitionException {
        try {
            int _type = T__72;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:27:7: ( '==' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:27:9: '=='
            {
            match("=="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__72"

    // $ANTLR start "T__73"
    public final void mT__73() throws RecognitionException {
        try {
            int _type = T__73;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:28:7: ( '>' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:28:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__73"

    // $ANTLR start "T__74"
    public final void mT__74() throws RecognitionException {
        try {
            int _type = T__74;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:29:7: ( '>=' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:29:9: '>='
            {
            match(">="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__74"

    // $ANTLR start "T__75"
    public final void mT__75() throws RecognitionException {
        try {
            int _type = T__75;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:30:7: ( '?-' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:30:9: '?-'
            {
            match("?-"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__75"

    // $ANTLR start "T__76"
    public final void mT__76() throws RecognitionException {
        try {
            int _type = T__76;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:31:7: ( 'Object' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:31:9: 'Object'
            {
            match("Object"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__76"

    // $ANTLR start "T__77"
    public final void mT__77() throws RecognitionException {
        try {
            int _type = T__77;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:32:7: ( 'String' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:32:9: 'String'
            {
            match("String"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__77"

    // $ANTLR start "T__78"
    public final void mT__78() throws RecognitionException {
        try {
            int _type = T__78;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:33:7: ( '[' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:33:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__78"

    // $ANTLR start "T__79"
    public final void mT__79() throws RecognitionException {
        try {
            int _type = T__79;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:34:7: ( ']' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:34:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__79"

    // $ANTLR start "T__80"
    public final void mT__80() throws RecognitionException {
        try {
            int _type = T__80;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:35:7: ( 'bit' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:35:9: 'bit'
            {
            match("bit"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__80"

    // $ANTLR start "T__81"
    public final void mT__81() throws RecognitionException {
        try {
            int _type = T__81;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:36:7: ( 'clear' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:36:9: 'clear'
            {
            match("clear"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__81"

    // $ANTLR start "T__82"
    public final void mT__82() throws RecognitionException {
        try {
            int _type = T__82;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:37:7: ( 'concurrent' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:37:9: 'concurrent'
            {
            match("concurrent"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__82"

    // $ANTLR start "T__83"
    public final void mT__83() throws RecognitionException {
        try {
            int _type = T__83;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:38:7: ( 'double' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:38:9: 'double'
            {
            match("double"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__83"

    // $ANTLR start "T__84"
    public final void mT__84() throws RecognitionException {
        try {
            int _type = T__84;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:39:7: ( 'drop' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:39:9: 'drop'
            {
            match("drop"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__84"

    // $ANTLR start "T__85"
    public final void mT__85() throws RecognitionException {
        try {
            int _type = T__85;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:40:7: ( 'float' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:40:9: 'float'
            {
            match("float"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__85"

    // $ANTLR start "T__86"
    public final void mT__86() throws RecognitionException {
        try {
            int _type = T__86;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:41:7: ( 'groupby' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:41:9: 'groupby'
            {
            match("groupby"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__86"

    // $ANTLR start "T__87"
    public final void mT__87() throws RecognitionException {
        try {
            int _type = T__87;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:42:7: ( 'indexby' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:42:9: 'indexby'
            {
            match("indexby"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__87"

    // $ANTLR start "T__88"
    public final void mT__88() throws RecognitionException {
        try {
            int _type = T__88;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:43:7: ( 'int' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:43:9: 'int'
            {
            match("int"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__88"

    // $ANTLR start "T__89"
    public final void mT__89() throws RecognitionException {
        try {
            int _type = T__89;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:44:7: ( 'long' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:44:9: 'long'
            {
            match("long"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__89"

    // $ANTLR start "T__90"
    public final void mT__90() throws RecognitionException {
        try {
            int _type = T__90;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:45:7: ( 'mod' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:45:9: 'mod'
            {
            match("mod"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__90"

    // $ANTLR start "T__91"
    public final void mT__91() throws RecognitionException {
        try {
            int _type = T__91;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:46:7: ( 'multiset' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:46:9: 'multiset'
            {
            match("multiset"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__91"

    // $ANTLR start "T__92"
    public final void mT__92() throws RecognitionException {
        try {
            int _type = T__92;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:47:7: ( 'orderby' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:47:9: 'orderby'
            {
            match("orderby"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__92"

    // $ANTLR start "T__93"
    public final void mT__93() throws RecognitionException {
        try {
            int _type = T__93;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:48:7: ( 'predefined' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:48:9: 'predefined'
            {
            match("predefined"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__93"

    // $ANTLR start "T__94"
    public final void mT__94() throws RecognitionException {
        try {
            int _type = T__94;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:49:7: ( 'shardby' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:49:9: 'shardby'
            {
            match("shardby"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__94"

    // $ANTLR start "T__95"
    public final void mT__95() throws RecognitionException {
        try {
            int _type = T__95;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:50:7: ( 'sortby' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:50:9: 'sortby'
            {
            match("sortby"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__95"

    // $ANTLR start "SORT_ORDER"
    public final void mSORT_ORDER() throws RecognitionException {
        try {
            int _type = SORT_ORDER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:147:11: ( 'asc' | 'desc' )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='a') ) {
                alt1=1;
            }
            else if ( (LA1_0=='d') ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;

            }
            switch (alt1) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:147:13: 'asc'
                    {
                    match("asc"); 



                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:147:19: 'desc'
                    {
                    match("desc"); 



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
    // $ANTLR end "SORT_ORDER"

    // $ANTLR start "ITER_DECL"
    public final void mITER_DECL() throws RecognitionException {
        try {
            int _type = ITER_DECL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:171:10: ( ( 'iter' | 'iterator' ) )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:171:12: ( 'iter' | 'iterator' )
            {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:171:12: ( 'iter' | 'iterator' )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='i') ) {
                int LA2_1 = input.LA(2);

                if ( (LA2_1=='t') ) {
                    int LA2_2 = input.LA(3);

                    if ( (LA2_2=='e') ) {
                        int LA2_3 = input.LA(4);

                        if ( (LA2_3=='r') ) {
                            int LA2_4 = input.LA(5);

                            if ( (LA2_4=='a') ) {
                                alt2=2;
                            }
                            else {
                                alt2=1;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 2, 3, input);

                            throw nvae;

                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 2, input);

                        throw nvae;

                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;

            }
            switch (alt2) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:171:13: 'iter'
                    {
                    match("iter"); 



                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:171:22: 'iterator'
                    {
                    match("iterator"); 



                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ITER_DECL"

    // $ANTLR start "NOT"
    public final void mNOT() throws RecognitionException {
        try {
            int _type = NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:172:4: ( ( 'not' | '!' ) )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:172:6: ( 'not' | '!' )
            {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:172:6: ( 'not' | '!' )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='n') ) {
                alt3=1;
            }
            else if ( (LA3_0=='!') ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;

            }
            switch (alt3) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:172:7: 'not'
                    {
                    match("not"); 



                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:172:15: '!'
                    {
                    match('!'); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NOT"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:173:5: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:173:7: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:173:31: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0 >= '0' && LA4_0 <= '9')||(LA4_0 >= 'A' && LA4_0 <= 'Z')||LA4_0=='_'||(LA4_0 >= 'a' && LA4_0 <= 'z')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
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


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "DOT_ID"
    public final void mDOT_ID() throws RecognitionException {
        try {
            int _type = DOT_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:174:7: ( '.' ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:174:9: '.' ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            match('.'); 

            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:174:36: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0 >= '0' && LA5_0 <= '9')||(LA5_0 >= 'A' && LA5_0 <= 'Z')||LA5_0=='_'||(LA5_0 >= 'a' && LA5_0 <= 'z')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
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
            	    break loop5;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DOT_ID"

    // $ANTLR start "DOT_END"
    public final void mDOT_END() throws RecognitionException {
        try {
            int _type = DOT_END;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:176:8: ( ({...}?) '.' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:176:10: ({...}?) '.'
            {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:176:10: ({...}?)
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:176:11: {...}?
            {
            if ( !((input.LA(2)=='\r'||input.LA(2)=='\n'||
                       input.LA(2)==' '||input.LA(2)=='\t'|| input.LA(2)==EOF)) ) {
                throw new FailedPredicateException(input, "DOT_END", "input.LA(2)=='\\r'||input.LA(2)=='\\n'||\n           input.LA(2)==' '||input.LA(2)=='\\t'|| input.LA(2)==EOF");
            }

            }


            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DOT_END"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:187:5: ( ( '0' .. '9' )+ ( 'l' | 'L' )? )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:187:7: ( '0' .. '9' )+ ( 'l' | 'L' )?
            {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:187:7: ( '0' .. '9' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0 >= '0' && LA6_0 <= '9')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:
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
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:187:19: ( 'l' | 'L' )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='L'||LA7_0=='l') ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:
                    {
                    if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
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

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            int _type = FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:190:5: ( ( '0' .. '9' )+ ({...}? => '.' ( '0' .. '9' )+ ( EXPONENT )? ( 'f' | 'F' | 'd' | 'D' )? |) | ( '0' .. '9' )+ EXPONENT )
            int alt14=2;
            alt14 = dfa14.predict(input);
            switch (alt14) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:190:7: ( '0' .. '9' )+ ({...}? => '.' ( '0' .. '9' )+ ( EXPONENT )? ( 'f' | 'F' | 'd' | 'D' )? |)
                    {
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:190:7: ( '0' .. '9' )+
                    int cnt8=0;
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( ((LA8_0 >= '0' && LA8_0 <= '9')) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:
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
                    	    if ( cnt8 >= 1 ) break loop8;
                                EarlyExitException eee =
                                    new EarlyExitException(8, input);
                                throw eee;
                        }
                        cnt8++;
                    } while (true);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:191:5: ({...}? => '.' ( '0' .. '9' )+ ( EXPONENT )? ( 'f' | 'F' | 'd' | 'D' )? |)
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0=='.') && ((input.LA(2) != '.' &&
                           input.LA(2) != ' ' && input.LA(2) != '\t' && input.LA(2) != '\r' && input.LA(2) != '\n' &&
                            input.LA(2) != EOF ))) {
                        alt12=1;
                    }
                    else {
                        alt12=2;
                    }
                    switch (alt12) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:191:7: {...}? => '.' ( '0' .. '9' )+ ( EXPONENT )? ( 'f' | 'F' | 'd' | 'D' )?
                            {
                            if ( !((input.LA(2) != '.' &&
                                   input.LA(2) != ' ' && input.LA(2) != '\t' && input.LA(2) != '\r' && input.LA(2) != '\n' &&
                                    input.LA(2) != EOF )) ) {
                                throw new FailedPredicateException(input, "FLOAT", "input.LA(2) != '.' &&\n       input.LA(2) != ' ' && input.LA(2) != '\\t' && input.LA(2) != '\\r' && input.LA(2) != '\\n' &&\n        input.LA(2) != EOF ");
                            }

                            match('.'); 

                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:193:38: ( '0' .. '9' )+
                            int cnt9=0;
                            loop9:
                            do {
                                int alt9=2;
                                int LA9_0 = input.LA(1);

                                if ( ((LA9_0 >= '0' && LA9_0 <= '9')) ) {
                                    alt9=1;
                                }


                                switch (alt9) {
                            	case 1 :
                            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:
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
                            	    if ( cnt9 >= 1 ) break loop9;
                                        EarlyExitException eee =
                                            new EarlyExitException(9, input);
                                        throw eee;
                                }
                                cnt9++;
                            } while (true);


                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:193:50: ( EXPONENT )?
                            int alt10=2;
                            int LA10_0 = input.LA(1);

                            if ( (LA10_0=='E'||LA10_0=='e') ) {
                                alt10=1;
                            }
                            switch (alt10) {
                                case 1 :
                                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:193:50: EXPONENT
                                    {
                                    mEXPONENT(); 


                                    }
                                    break;

                            }


                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:193:60: ( 'f' | 'F' | 'd' | 'D' )?
                            int alt11=2;
                            int LA11_0 = input.LA(1);

                            if ( (LA11_0=='D'||LA11_0=='F'||LA11_0=='d'||LA11_0=='f') ) {
                                alt11=1;
                            }
                            switch (alt11) {
                                case 1 :
                                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:
                                    {
                                    if ( input.LA(1)=='D'||input.LA(1)=='F'||input.LA(1)=='d'||input.LA(1)=='f' ) {
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


                            _type = FLOAT;

                            }
                            break;
                        case 2 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:194:12: 
                            {
                            _type = INT;

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:195:8: ( '0' .. '9' )+ EXPONENT
                    {
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:195:8: ( '0' .. '9' )+
                    int cnt13=0;
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( ((LA13_0 >= '0' && LA13_0 <= '9')) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:
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
                    	    if ( cnt13 >= 1 ) break loop13;
                                EarlyExitException eee =
                                    new EarlyExitException(13, input);
                                throw eee;
                        }
                        cnt13++;
                    } while (true);


                    mEXPONENT(); 


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
    // $ANTLR end "FLOAT"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:204:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' | '/*' ( options {greedy=false; } : . )* '*/' | '#' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            int alt20=3;
            int LA20_0 = input.LA(1);

            if ( (LA20_0=='/') ) {
                int LA20_1 = input.LA(2);

                if ( (LA20_1=='/') ) {
                    alt20=1;
                }
                else if ( (LA20_1=='*') ) {
                    alt20=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 20, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA20_0=='#') ) {
                alt20=3;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;

            }
            switch (alt20) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:204:9: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
                    {
                    match("//"); 



                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:204:14: (~ ( '\\n' | '\\r' ) )*
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( ((LA15_0 >= '\u0000' && LA15_0 <= '\t')||(LA15_0 >= '\u000B' && LA15_0 <= '\f')||(LA15_0 >= '\u000E' && LA15_0 <= '\uFFFF')) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:
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
                    	    break loop15;
                        }
                    } while (true);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:204:28: ( '\\r' )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0=='\r') ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:204:28: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }


                    match('\n'); 

                    _channel=HIDDEN;

                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:205:9: '/*' ( options {greedy=false; } : . )* '*/'
                    {
                    match("/*"); 



                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:205:14: ( options {greedy=false; } : . )*
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( (LA17_0=='*') ) {
                            int LA17_1 = input.LA(2);

                            if ( (LA17_1=='/') ) {
                                alt17=2;
                            }
                            else if ( ((LA17_1 >= '\u0000' && LA17_1 <= '.')||(LA17_1 >= '0' && LA17_1 <= '\uFFFF')) ) {
                                alt17=1;
                            }


                        }
                        else if ( ((LA17_0 >= '\u0000' && LA17_0 <= ')')||(LA17_0 >= '+' && LA17_0 <= '\uFFFF')) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:205:42: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop17;
                        }
                    } while (true);


                    match("*/"); 



                    _channel=HIDDEN;

                    }
                    break;
                case 3 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:206:7: '#' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
                    {
                    match('#'); 

                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:206:12: (~ ( '\\n' | '\\r' ) )*
                    loop18:
                    do {
                        int alt18=2;
                        int LA18_0 = input.LA(1);

                        if ( ((LA18_0 >= '\u0000' && LA18_0 <= '\t')||(LA18_0 >= '\u000B' && LA18_0 <= '\f')||(LA18_0 >= '\u000E' && LA18_0 <= '\uFFFF')) ) {
                            alt18=1;
                        }


                        switch (alt18) {
                    	case 1 :
                    	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:
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
                    	    break loop18;
                        }
                    } while (true);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:206:26: ( '\\r' )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0=='\r') ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:206:26: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }


                    match('\n'); 

                    _channel=HIDDEN;

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
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:209:5: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:209:9: ( ' ' | '\\t' | '\\r' | '\\n' )
            {
            if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "UTF8"
    public final void mUTF8() throws RecognitionException {
        try {
            int _type = UTF8;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:217:5: ( 'u' '\"' ( ESC_SEQ |~ ( '\\\\' | '\"' ) )* '\"' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:217:8: 'u' '\"' ( ESC_SEQ |~ ( '\\\\' | '\"' ) )* '\"'
            {
            match('u'); 

            match('\"'); 

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:217:16: ( ESC_SEQ |~ ( '\\\\' | '\"' ) )*
            loop21:
            do {
                int alt21=3;
                int LA21_0 = input.LA(1);

                if ( (LA21_0=='\\') ) {
                    alt21=1;
                }
                else if ( ((LA21_0 >= '\u0000' && LA21_0 <= '!')||(LA21_0 >= '#' && LA21_0 <= '[')||(LA21_0 >= ']' && LA21_0 <= '\uFFFF')) ) {
                    alt21=2;
                }


                switch (alt21) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:217:18: ESC_SEQ
            	    {
            	    mESC_SEQ(); 


            	    }
            	    break;
            	case 2 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:217:28: ~ ( '\\\\' | '\"' )
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= '\uFFFF') ) {
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
            	    break loop21;
                }
            } while (true);


            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "UTF8"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:221:5: ( '\"' ( ESC_SEQ |~ ( '\\\\' | '\"' ) )* '\"' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:221:8: '\"' ( ESC_SEQ |~ ( '\\\\' | '\"' ) )* '\"'
            {
            match('\"'); 

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:221:12: ( ESC_SEQ |~ ( '\\\\' | '\"' ) )*
            loop22:
            do {
                int alt22=3;
                int LA22_0 = input.LA(1);

                if ( (LA22_0=='\\') ) {
                    alt22=1;
                }
                else if ( ((LA22_0 >= '\u0000' && LA22_0 <= '!')||(LA22_0 >= '#' && LA22_0 <= '[')||(LA22_0 >= ']' && LA22_0 <= '\uFFFF')) ) {
                    alt22=2;
                }


                switch (alt22) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:221:14: ESC_SEQ
            	    {
            	    mESC_SEQ(); 


            	    }
            	    break;
            	case 2 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:221:24: ~ ( '\\\\' | '\"' )
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= '\uFFFF') ) {
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
            	    break loop22;
                }
            } while (true);


            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "CHAR"
    public final void mCHAR() throws RecognitionException {
        try {
            int _type = CHAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:224:5: ( '\\'' ( ESC_SEQ |~ ( '\\'' | '\\\\' ) ) '\\'' )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:224:8: '\\'' ( ESC_SEQ |~ ( '\\'' | '\\\\' ) ) '\\''
            {
            match('\''); 

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:224:13: ( ESC_SEQ |~ ( '\\'' | '\\\\' ) )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0=='\\') ) {
                alt23=1;
            }
            else if ( ((LA23_0 >= '\u0000' && LA23_0 <= '&')||(LA23_0 >= '(' && LA23_0 <= '[')||(LA23_0 >= ']' && LA23_0 <= '\uFFFF')) ) {
                alt23=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;

            }
            switch (alt23) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:224:15: ESC_SEQ
                    {
                    mESC_SEQ(); 


                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:224:25: ~ ( '\\'' | '\\\\' )
                    {
                    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '&')||(input.LA(1) >= '(' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= '\uFFFF') ) {
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


            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CHAR"

    // $ANTLR start "EXPONENT"
    public final void mEXPONENT() throws RecognitionException {
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:229:10: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:229:12: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:229:22: ( '+' | '-' )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0=='+'||LA24_0=='-') ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:
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


            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:229:33: ( '0' .. '9' )+
            int cnt25=0;
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( ((LA25_0 >= '0' && LA25_0 <= '9')) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:
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
            	    if ( cnt25 >= 1 ) break loop25;
                        EarlyExitException eee =
                            new EarlyExitException(25, input);
                        throw eee;
                }
                cnt25++;
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
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:232:11: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:
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
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:236:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | UNICODE_ESC | OCTAL_ESC )
            int alt26=3;
            int LA26_0 = input.LA(1);

            if ( (LA26_0=='\\') ) {
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
                    alt26=1;
                    }
                    break;
                case 'u':
                    {
                    alt26=2;
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
                    alt26=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 26, 1, input);

                    throw nvae;

                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;

            }
            switch (alt26) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:236:9: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:237:9: UNICODE_ESC
                    {
                    mUNICODE_ESC(); 


                    }
                    break;
                case 3 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:238:9: OCTAL_ESC
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
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:243:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt27=3;
            int LA27_0 = input.LA(1);

            if ( (LA27_0=='\\') ) {
                int LA27_1 = input.LA(2);

                if ( ((LA27_1 >= '0' && LA27_1 <= '3')) ) {
                    int LA27_2 = input.LA(3);

                    if ( ((LA27_2 >= '0' && LA27_2 <= '7')) ) {
                        int LA27_4 = input.LA(4);

                        if ( ((LA27_4 >= '0' && LA27_4 <= '7')) ) {
                            alt27=1;
                        }
                        else {
                            alt27=2;
                        }
                    }
                    else {
                        alt27=3;
                    }
                }
                else if ( ((LA27_1 >= '4' && LA27_1 <= '7')) ) {
                    int LA27_3 = input.LA(3);

                    if ( ((LA27_3 >= '0' && LA27_3 <= '7')) ) {
                        alt27=2;
                    }
                    else {
                        alt27=3;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 27, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;

            }
            switch (alt27) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:243:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:244:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:245:9: '\\\\' ( '0' .. '7' )
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
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:250:5: ( '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:250:9: '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
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

    public void mTokens() throws RecognitionException {
        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:8: ( T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | SORT_ORDER | ITER_DECL | NOT | ID | DOT_ID | DOT_END | INT | FLOAT | COMMENT | WS | UTF8 | STRING | CHAR )
        int alt28=53;
        alt28 = dfa28.predict(input);
        switch (alt28) {
            case 1 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:10: T__56
                {
                mT__56(); 


                }
                break;
            case 2 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:16: T__57
                {
                mT__57(); 


                }
                break;
            case 3 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:22: T__58
                {
                mT__58(); 


                }
                break;
            case 4 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:28: T__59
                {
                mT__59(); 


                }
                break;
            case 5 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:34: T__60
                {
                mT__60(); 


                }
                break;
            case 6 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:40: T__61
                {
                mT__61(); 


                }
                break;
            case 7 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:46: T__62
                {
                mT__62(); 


                }
                break;
            case 8 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:52: T__63
                {
                mT__63(); 


                }
                break;
            case 9 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:58: T__64
                {
                mT__64(); 


                }
                break;
            case 10 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:64: T__65
                {
                mT__65(); 


                }
                break;
            case 11 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:70: T__66
                {
                mT__66(); 


                }
                break;
            case 12 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:76: T__67
                {
                mT__67(); 


                }
                break;
            case 13 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:82: T__68
                {
                mT__68(); 


                }
                break;
            case 14 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:88: T__69
                {
                mT__69(); 


                }
                break;
            case 15 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:94: T__70
                {
                mT__70(); 


                }
                break;
            case 16 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:100: T__71
                {
                mT__71(); 


                }
                break;
            case 17 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:106: T__72
                {
                mT__72(); 


                }
                break;
            case 18 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:112: T__73
                {
                mT__73(); 


                }
                break;
            case 19 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:118: T__74
                {
                mT__74(); 


                }
                break;
            case 20 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:124: T__75
                {
                mT__75(); 


                }
                break;
            case 21 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:130: T__76
                {
                mT__76(); 


                }
                break;
            case 22 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:136: T__77
                {
                mT__77(); 


                }
                break;
            case 23 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:142: T__78
                {
                mT__78(); 


                }
                break;
            case 24 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:148: T__79
                {
                mT__79(); 


                }
                break;
            case 25 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:154: T__80
                {
                mT__80(); 


                }
                break;
            case 26 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:160: T__81
                {
                mT__81(); 


                }
                break;
            case 27 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:166: T__82
                {
                mT__82(); 


                }
                break;
            case 28 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:172: T__83
                {
                mT__83(); 


                }
                break;
            case 29 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:178: T__84
                {
                mT__84(); 


                }
                break;
            case 30 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:184: T__85
                {
                mT__85(); 


                }
                break;
            case 31 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:190: T__86
                {
                mT__86(); 


                }
                break;
            case 32 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:196: T__87
                {
                mT__87(); 


                }
                break;
            case 33 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:202: T__88
                {
                mT__88(); 


                }
                break;
            case 34 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:208: T__89
                {
                mT__89(); 


                }
                break;
            case 35 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:214: T__90
                {
                mT__90(); 


                }
                break;
            case 36 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:220: T__91
                {
                mT__91(); 


                }
                break;
            case 37 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:226: T__92
                {
                mT__92(); 


                }
                break;
            case 38 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:232: T__93
                {
                mT__93(); 


                }
                break;
            case 39 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:238: T__94
                {
                mT__94(); 


                }
                break;
            case 40 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:244: T__95
                {
                mT__95(); 


                }
                break;
            case 41 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:250: SORT_ORDER
                {
                mSORT_ORDER(); 


                }
                break;
            case 42 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:261: ITER_DECL
                {
                mITER_DECL(); 


                }
                break;
            case 43 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:271: NOT
                {
                mNOT(); 


                }
                break;
            case 44 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:275: ID
                {
                mID(); 


                }
                break;
            case 45 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:278: DOT_ID
                {
                mDOT_ID(); 


                }
                break;
            case 46 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:285: DOT_END
                {
                mDOT_END(); 


                }
                break;
            case 47 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:293: INT
                {
                mINT(); 


                }
                break;
            case 48 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:297: FLOAT
                {
                mFLOAT(); 


                }
                break;
            case 49 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:303: COMMENT
                {
                mCOMMENT(); 


                }
                break;
            case 50 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:311: WS
                {
                mWS(); 


                }
                break;
            case 51 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:314: UTF8
                {
                mUTF8(); 


                }
                break;
            case 52 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:319: STRING
                {
                mSTRING(); 


                }
                break;
            case 53 :
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:1:326: CHAR
                {
                mCHAR(); 


                }
                break;

        }

    }


    protected DFA14 dfa14 = new DFA14(this);
    protected DFA28 dfa28 = new DFA28(this);
    static final String DFA14_eotS =
        "\1\uffff\1\2\2\uffff";
    static final String DFA14_eofS =
        "\4\uffff";
    static final String DFA14_minS =
        "\2\60\2\uffff";
    static final String DFA14_maxS =
        "\1\71\1\145\2\uffff";
    static final String DFA14_acceptS =
        "\2\uffff\1\1\1\2";
    static final String DFA14_specialS =
        "\4\uffff}>";
    static final String[] DFA14_transitionS = {
            "\12\1",
            "\12\1\13\uffff\1\3\37\uffff\1\3",
            "",
            ""
    };

    static final short[] DFA14_eot = DFA.unpackEncodedString(DFA14_eotS);
    static final short[] DFA14_eof = DFA.unpackEncodedString(DFA14_eofS);
    static final char[] DFA14_min = DFA.unpackEncodedStringToUnsignedChars(DFA14_minS);
    static final char[] DFA14_max = DFA.unpackEncodedStringToUnsignedChars(DFA14_maxS);
    static final short[] DFA14_accept = DFA.unpackEncodedString(DFA14_acceptS);
    static final short[] DFA14_special = DFA.unpackEncodedString(DFA14_specialS);
    static final short[][] DFA14_transition;

    static {
        int numStates = DFA14_transitionS.length;
        DFA14_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA14_transition[i] = DFA.unpackEncodedString(DFA14_transitionS[i]);
        }
    }

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = DFA14_eot;
            this.eof = DFA14_eof;
            this.min = DFA14_min;
            this.max = DFA14_max;
            this.accept = DFA14_accept;
            this.special = DFA14_special;
            this.transition = DFA14_transition;
        }
        public String getDescription() {
            return "189:1: FLOAT : ( ( '0' .. '9' )+ ({...}? => '.' ( '0' .. '9' )+ ( EXPONENT )? ( 'f' | 'F' | 'd' | 'D' )? |) | ( '0' .. '9' )+ EXPONENT );";
        }
    }
    static final String DFA28_eotS =
        "\1\uffff\1\52\7\uffff\1\55\1\56\1\60\1\uffff\1\62\1\64\1\66\1\uffff"+
        "\2\46\2\uffff\16\46\1\115\23\uffff\25\46\3\uffff\2\46\1\147\10\46"+
        "\1\160\2\46\1\163\5\46\1\171\1\52\2\46\1\uffff\3\46\1\177\1\171"+
        "\3\46\1\uffff\1\u0084\1\u0085\1\uffff\5\46\1\uffff\2\46\1\u008d"+
        "\2\46\1\uffff\1\u0090\3\46\2\uffff\5\46\1\u0099\1\u009a\1\uffff"+
        "\1\46\1\u009c\1\uffff\7\46\1\u00a4\2\uffff\1\46\1\uffff\1\u00a6"+
        "\1\u00a7\2\46\1\u00aa\1\46\1\u00ac\1\uffff\1\46\2\uffff\1\u0084"+
        "\1\u00ae\1\uffff\1\46\1\uffff\1\46\1\uffff\1\46\1\u00b2\1\u00b3"+
        "\2\uffff";
    static final String DFA28_eofS =
        "\u00b4\uffff";
    static final String DFA28_minS =
        "\1\11\1\75\7\uffff\1\56\1\52\1\55\1\uffff\3\75\1\uffff\1\142\1\164"+
        "\2\uffff\1\151\1\154\1\145\1\154\1\162\1\156\2\157\2\162\1\150\1"+
        "\163\1\157\1\42\1\56\23\uffff\1\152\1\162\1\164\1\145\1\156\1\165"+
        "\1\157\1\163\2\157\1\144\1\145\1\156\1\144\1\154\1\144\1\145\1\141"+
        "\1\162\1\143\1\164\3\uffff\1\145\1\151\1\60\1\141\1\143\1\142\1"+
        "\160\1\143\1\141\1\165\1\145\1\60\1\162\1\147\1\60\1\164\1\145\1"+
        "\144\1\162\1\164\2\60\1\143\1\156\1\uffff\1\162\1\165\1\154\2\60"+
        "\1\164\1\160\1\170\1\uffff\2\60\1\uffff\1\151\1\162\1\145\1\144"+
        "\1\142\1\uffff\1\164\1\147\1\60\1\162\1\145\1\uffff\1\60\2\142\1"+
        "\164\2\uffff\1\163\1\142\1\146\1\142\1\171\2\60\1\uffff\1\162\1"+
        "\60\1\uffff\2\171\1\157\1\145\1\171\1\151\1\171\1\60\2\uffff\1\145"+
        "\1\uffff\2\60\1\162\1\164\1\60\1\156\1\60\1\uffff\1\156\2\uffff"+
        "\2\60\1\uffff\1\145\1\uffff\1\164\1\uffff\1\144\2\60\2\uffff";
    static final String DFA28_maxS =
        "\1\172\1\75\7\uffff\1\172\1\57\1\55\1\uffff\3\75\1\uffff\1\142\1"+
        "\164\2\uffff\1\151\1\157\1\162\1\154\1\162\1\164\1\157\1\165\2\162"+
        "\1\157\1\163\1\157\1\42\1\145\23\uffff\1\152\1\162\1\164\1\145\1"+
        "\156\1\165\1\157\1\163\2\157\1\164\1\145\1\156\1\144\1\154\1\144"+
        "\1\145\1\141\1\162\1\143\1\164\3\uffff\1\145\1\151\1\172\1\141\1"+
        "\143\1\142\1\160\1\143\1\141\1\165\1\145\1\172\1\162\1\147\1\172"+
        "\1\164\1\145\1\144\1\162\1\164\2\172\1\143\1\156\1\uffff\1\162\1"+
        "\165\1\154\2\172\1\164\1\160\1\170\1\uffff\2\172\1\uffff\1\151\1"+
        "\162\1\145\1\144\1\142\1\uffff\1\164\1\147\1\172\1\162\1\145\1\uffff"+
        "\1\172\2\142\1\164\2\uffff\1\163\1\142\1\146\1\142\1\171\2\172\1"+
        "\uffff\1\162\1\172\1\uffff\2\171\1\157\1\145\1\171\1\151\1\171\1"+
        "\172\2\uffff\1\145\1\uffff\2\172\1\162\1\164\1\172\1\156\1\172\1"+
        "\uffff\1\156\2\uffff\2\172\1\uffff\1\145\1\uffff\1\164\1\uffff\1"+
        "\144\2\172\2\uffff";
    static final String DFA28_acceptS =
        "\2\uffff\1\2\1\3\1\4\1\5\1\6\1\7\1\10\3\uffff\1\15\3\uffff\1\24"+
        "\2\uffff\1\27\1\30\17\uffff\1\61\1\62\1\54\1\64\1\65\1\1\1\53\1"+
        "\11\1\55\1\56\1\12\1\14\1\13\1\17\1\16\1\21\1\20\1\23\1\22\25\uffff"+
        "\1\63\1\57\1\60\30\uffff\1\31\10\uffff\1\41\2\uffff\1\43\5\uffff"+
        "\1\51\5\uffff\1\35\4\uffff\1\52\1\42\7\uffff\1\32\2\uffff\1\36\10"+
        "\uffff\1\25\1\26\1\uffff\1\34\7\uffff\1\50\1\uffff\1\37\1\40\2\uffff"+
        "\1\45\1\uffff\1\47\1\uffff\1\44\3\uffff\1\33\1\46";
    static final String DFA28_specialS =
        "\u00b4\uffff}>";
    static final String[] DFA28_transitionS = {
            "\2\45\2\uffff\1\45\22\uffff\1\45\1\1\1\47\1\44\1\2\2\uffff\1"+
            "\50\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\12\43\1\13\1\14\1\15"+
            "\1\16\1\17\1\20\1\uffff\16\46\1\21\3\46\1\22\7\46\1\23\1\uffff"+
            "\1\24\1\uffff\1\46\1\uffff\1\40\1\25\1\26\1\27\1\46\1\30\1\31"+
            "\1\46\1\32\2\46\1\33\1\34\1\41\1\35\1\36\2\46\1\37\1\46\1\42"+
            "\5\46",
            "\1\51",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\53\22\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
            "\1\44\4\uffff\1\44",
            "\1\57",
            "",
            "\1\61",
            "\1\63",
            "\1\65",
            "",
            "\1\67",
            "\1\70",
            "",
            "",
            "\1\71",
            "\1\72\2\uffff\1\73",
            "\1\76\11\uffff\1\74\2\uffff\1\75",
            "\1\77",
            "\1\100",
            "\1\101\5\uffff\1\102",
            "\1\103",
            "\1\104\5\uffff\1\105",
            "\1\106",
            "\1\107",
            "\1\110\6\uffff\1\111",
            "\1\112",
            "\1\113",
            "\1\114",
            "\1\116\1\uffff\12\43\13\uffff\1\116\37\uffff\1\116",
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
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\117",
            "\1\120",
            "\1\121",
            "\1\122",
            "\1\123",
            "\1\124",
            "\1\125",
            "\1\126",
            "\1\127",
            "\1\130",
            "\1\131\17\uffff\1\132",
            "\1\133",
            "\1\134",
            "\1\135",
            "\1\136",
            "\1\137",
            "\1\140",
            "\1\141",
            "\1\142",
            "\1\143",
            "\1\144",
            "",
            "",
            "",
            "\1\145",
            "\1\146",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "\1\150",
            "\1\151",
            "\1\152",
            "\1\153",
            "\1\154",
            "\1\155",
            "\1\156",
            "\1\157",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "\1\161",
            "\1\162",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "\1\164",
            "\1\165",
            "\1\166",
            "\1\167",
            "\1\170",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "\1\172",
            "\1\173",
            "",
            "\1\174",
            "\1\175",
            "\1\176",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "\1\u0080",
            "\1\u0081",
            "\1\u0082",
            "",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\1\u0083\31\46",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "",
            "\1\u0086",
            "\1\u0087",
            "\1\u0088",
            "\1\u0089",
            "\1\u008a",
            "",
            "\1\u008b",
            "\1\u008c",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "\1\u008e",
            "\1\u008f",
            "",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "\1\u0091",
            "\1\u0092",
            "\1\u0093",
            "",
            "",
            "\1\u0094",
            "\1\u0095",
            "\1\u0096",
            "\1\u0097",
            "\1\u0098",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "",
            "\1\u009b",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "",
            "\1\u009d",
            "\1\u009e",
            "\1\u009f",
            "\1\u00a0",
            "\1\u00a1",
            "\1\u00a2",
            "\1\u00a3",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "",
            "",
            "\1\u00a5",
            "",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "\1\u00a8",
            "\1\u00a9",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "\1\u00ab",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "",
            "\1\u00ad",
            "",
            "",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "",
            "\1\u00af",
            "",
            "\1\u00b0",
            "",
            "\1\u00b1",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "\12\46\7\uffff\32\46\4\uffff\1\46\1\uffff\32\46",
            "",
            ""
    };

    static final short[] DFA28_eot = DFA.unpackEncodedString(DFA28_eotS);
    static final short[] DFA28_eof = DFA.unpackEncodedString(DFA28_eofS);
    static final char[] DFA28_min = DFA.unpackEncodedStringToUnsignedChars(DFA28_minS);
    static final char[] DFA28_max = DFA.unpackEncodedStringToUnsignedChars(DFA28_maxS);
    static final short[] DFA28_accept = DFA.unpackEncodedString(DFA28_acceptS);
    static final short[] DFA28_special = DFA.unpackEncodedString(DFA28_specialS);
    static final short[][] DFA28_transition;

    static {
        int numStates = DFA28_transitionS.length;
        DFA28_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA28_transition[i] = DFA.unpackEncodedString(DFA28_transitionS[i]);
        }
    }

    class DFA28 extends DFA {

        public DFA28(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 28;
            this.eot = DFA28_eot;
            this.eof = DFA28_eof;
            this.min = DFA28_min;
            this.max = DFA28_max;
            this.accept = DFA28_accept;
            this.special = DFA28_special;
            this.transition = DFA28_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | SORT_ORDER | ITER_DECL | NOT | ID | DOT_ID | DOT_END | INT | FLOAT | COMMENT | WS | UTF8 | STRING | CHAR );";
        }
    }
 

}