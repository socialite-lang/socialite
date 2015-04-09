// $ANTLR 3.4 /Users/jiwon/workspace/socialite/grammar/PySocialite.g 2015-03-29 00:41:20

    package pysocialite.antlr;    
    import java.util.List;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.Set;
    import java.util.LinkedHashSet;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class PySocialiteParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ANY_OTHER_CHAR", "COMMENT", "ESC_SEQ", "EXPONENT", "HEX_DIGIT", "OCTAL_ESC", "PROG", "QUERY", "STRING", "UNICODE_ESC", "WS"
    };

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

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public PySocialiteParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public PySocialiteParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() { return PySocialiteParser.tokenNames; }
    public String getGrammarFileName() { return "/Users/jiwon/workspace/socialite/grammar/PySocialite.g"; }



    // $ANTLR start "prog"
    // /Users/jiwon/workspace/socialite/grammar/PySocialite.g:195:1: prog returns [String str] : (t= . )* EOF ;
    public final String prog() throws RecognitionException {
        String str = null;


        Token t=null;

        StringBuilder builder = new StringBuilder();
        try {
            // /Users/jiwon/workspace/socialite/grammar/PySocialite.g:197:5: ( (t= . )* EOF )
            // /Users/jiwon/workspace/socialite/grammar/PySocialite.g:197:7: (t= . )* EOF
            {
            // /Users/jiwon/workspace/socialite/grammar/PySocialite.g:197:7: (t= . )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0 >= ANY_OTHER_CHAR && LA1_0 <= WS)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/PySocialite.g:197:8: t= .
            	    {
            	    t=(Token)input.LT(1);

            	    matchAny(input); 

            	    builder.append((t!=null?t.getText():null));

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            match(input,EOF,FOLLOW_EOF_in_prog73); 

            str = builder.toString();

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return str;
    }
    // $ANTLR end "prog"

    // Delegated rules


 

    public static final BitSet FOLLOW_EOF_in_prog73 = new BitSet(new long[]{0x0000000000000002L});

}