// $ANTLR 3.4 /x/jiwon/workspace/socialite/grammar/SociaLite.g 2014-05-07 21:24:51

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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class SociaLiteParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "APPROX", "BODY", "CHAR", "CLEAR", "COL_DECL", "COL_DECLS", "COMMENT", "COMPOUND_EXPR", "DECL", "DOT_END", "DOT_ID", "DROP", "ESC_SEQ", "EXPONENT", "EXPR", "FALSE", "FLOAT", "FUNC", "FUNCTION", "FUNC_ARG", "GROUP_BY", "HEAD", "HEX_DIGIT", "ID", "INDEX", "INDEX_BY", "INT", "ITER", "ITER_DECL", "KIND1", "KIND2", "MULTISET", "MULTI_ASSIGN", "NOT", "OCTAL_ESC", "OPT", "OPTION", "ORDER_BY", "PREDEFINED", "PREDICATE", "PROG", "QUERY", "RANGE", "RULE", "SIZE", "SORT_BY", "SORT_ORDER", "STRING", "TABLE_OPT", "TERM", "TRUE", "T_FLOAT", "T_INT", "T_STR", "T_UTF8", "T_VAR", "UNICODE_ESC", "UTF8", "WS", "'!='", "'$'", "'('", "')'", "'*'", "'+'", "','", "'-'", "'..'", "'/'", "':'", "':-'", "';'", "'<'", "'<='", "'='", "'=='", "'>'", "'>='", "'?-'", "'Object'", "'String'", "'['", "']'", "'clear'", "'double'", "'drop'", "'float'", "'groupby'", "'indexby'", "'int'", "'long'", "'mod'", "'multiset'", "'orderby'", "'predefined'", "'sortby'"
    };

    public static final int EOF=-1;
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
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int T__99=99;
    public static final int APPROX=4;
    public static final int BODY=5;
    public static final int CHAR=6;
    public static final int CLEAR=7;
    public static final int COL_DECL=8;
    public static final int COL_DECLS=9;
    public static final int COMMENT=10;
    public static final int COMPOUND_EXPR=11;
    public static final int DECL=12;
    public static final int DOT_END=13;
    public static final int DOT_ID=14;
    public static final int DROP=15;
    public static final int ESC_SEQ=16;
    public static final int EXPONENT=17;
    public static final int EXPR=18;
    public static final int FALSE=19;
    public static final int FLOAT=20;
    public static final int FUNC=21;
    public static final int FUNCTION=22;
    public static final int FUNC_ARG=23;
    public static final int GROUP_BY=24;
    public static final int HEAD=25;
    public static final int HEX_DIGIT=26;
    public static final int ID=27;
    public static final int INDEX=28;
    public static final int INDEX_BY=29;
    public static final int INT=30;
    public static final int ITER=31;
    public static final int ITER_DECL=32;
    public static final int KIND1=33;
    public static final int KIND2=34;
    public static final int MULTISET=35;
    public static final int MULTI_ASSIGN=36;
    public static final int NOT=37;
    public static final int OCTAL_ESC=38;
    public static final int OPT=39;
    public static final int OPTION=40;
    public static final int ORDER_BY=41;
    public static final int PREDEFINED=42;
    public static final int PREDICATE=43;
    public static final int PROG=44;
    public static final int QUERY=45;
    public static final int RANGE=46;
    public static final int RULE=47;
    public static final int SIZE=48;
    public static final int SORT_BY=49;
    public static final int SORT_ORDER=50;
    public static final int STRING=51;
    public static final int TABLE_OPT=52;
    public static final int TERM=53;
    public static final int TRUE=54;
    public static final int T_FLOAT=55;
    public static final int T_INT=56;
    public static final int T_STR=57;
    public static final int T_UTF8=58;
    public static final int T_VAR=59;
    public static final int UNICODE_ESC=60;
    public static final int UTF8=61;
    public static final int WS=62;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public SociaLiteParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public SociaLiteParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return SociaLiteParser.tokenNames; }
    public String getGrammarFileName() { return "/x/jiwon/workspace/socialite/grammar/SociaLite.g"; }


        public socialite.parser.Parser parser;
        public HashMap<String, TableDecl> tableDeclMap = new HashMap<String, TableDecl>();
        Set<Function> funcsInRule = new LinkedHashSet<Function>();
        
        public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
    //        String hdr = getErrorHeader(e);
            String msg = getErrorMessage(e, tokenNames);
            throw new ParseException(parser, e, msg);
        }


    public static class prog_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "prog"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:87:1: prog : ( stat )+ EOF ;
    public final SociaLiteParser.prog_return prog() throws RecognitionException {
        SociaLiteParser.prog_return retval = new SociaLiteParser.prog_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token EOF2=null;
        SociaLiteParser.stat_return stat1 =null;


        CommonTree EOF2_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:87:6: ( ( stat )+ EOF )
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:87:7: ( stat )+ EOF
            {
            root_0 = (CommonTree)adaptor.nil();


            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:87:7: ( stat )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==ID||LA1_0==82||LA1_0==87||LA1_0==89) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:87:7: stat
            	    {
            	    pushFollow(FOLLOW_stat_in_prog353);
            	    stat1=stat();

            	    state._fsp--;

            	    adaptor.addChild(root_0, stat1.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            EOF2=(Token)match(input,EOF,FOLLOW_EOF_in_prog356); 
            EOF2_tree = 
            (CommonTree)adaptor.create(EOF2)
            ;
            adaptor.addChild(root_0, EOF2_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "prog"


    public static class stat_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "stat"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:88:1: stat : ( table_decl | rule | query | table_stmt );
    public final SociaLiteParser.stat_return stat() throws RecognitionException {
        SociaLiteParser.stat_return retval = new SociaLiteParser.stat_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SociaLiteParser.table_decl_return table_decl3 =null;

        SociaLiteParser.rule_return rule4 =null;

        SociaLiteParser.query_return query5 =null;

        SociaLiteParser.table_stmt_return table_stmt6 =null;



        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:88:6: ( table_decl | rule | query | table_stmt )
            int alt2=4;
            switch ( input.LA(1) ) {
            case ID:
                {
                int LA2_1 = input.LA(2);

                if ( (LA2_1==85) ) {
                    switch ( input.LA(3) ) {
                    case 83:
                    case 84:
                    case 88:
                    case 90:
                    case 93:
                    case 94:
                        {
                        alt2=1;
                        }
                        break;
                    case ID:
                        {
                        int LA2_7 = input.LA(4);

                        if ( (LA2_7==ID||LA2_7==85) ) {
                            alt2=1;
                        }
                        else if ( (LA2_7==DOT_ID||(LA2_7 >= 67 && LA2_7 <= 68)||LA2_7==70||LA2_7==72||LA2_7==86||LA2_7==95) ) {
                            alt2=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 2, 7, input);

                            throw nvae;

                        }
                        }
                        break;
                    case FLOAT:
                    case INT:
                    case STRING:
                    case UTF8:
                    case 64:
                    case 65:
                    case 70:
                        {
                        alt2=2;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 4, input);

                        throw nvae;

                    }

                }
                else if ( (LA2_1==65) ) {
                    switch ( input.LA(3) ) {
                    case 65:
                        {
                        switch ( input.LA(4) ) {
                        case 93:
                            {
                            switch ( input.LA(5) ) {
                            case 85:
                                {
                                int LA2_18 = input.LA(6);

                                if ( (LA2_18==86) ) {
                                    int LA2_25 = input.LA(7);

                                    if ( (LA2_25==ID) ) {
                                        alt2=1;
                                    }
                                    else if ( (LA2_25==66) ) {
                                        alt2=2;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 2, 25, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 2, 18, input);

                                    throw nvae;

                                }
                                }
                                break;
                            case ID:
                                {
                                alt2=1;
                                }
                                break;
                            case 66:
                                {
                                alt2=2;
                                }
                                break;
                            default:
                                NoViableAltException nvae =
                                    new NoViableAltException("", 2, 11, input);

                                throw nvae;

                            }

                            }
                            break;
                        case 94:
                            {
                            switch ( input.LA(5) ) {
                            case 85:
                                {
                                int LA2_19 = input.LA(6);

                                if ( (LA2_19==86) ) {
                                    int LA2_26 = input.LA(7);

                                    if ( (LA2_26==ID) ) {
                                        alt2=1;
                                    }
                                    else if ( (LA2_26==66) ) {
                                        alt2=2;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 2, 26, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 2, 19, input);

                                    throw nvae;

                                }
                                }
                                break;
                            case ID:
                                {
                                alt2=1;
                                }
                                break;
                            case 66:
                                {
                                alt2=2;
                                }
                                break;
                            default:
                                NoViableAltException nvae =
                                    new NoViableAltException("", 2, 12, input);

                                throw nvae;

                            }

                            }
                            break;
                        case 90:
                            {
                            switch ( input.LA(5) ) {
                            case 85:
                                {
                                int LA2_20 = input.LA(6);

                                if ( (LA2_20==86) ) {
                                    int LA2_27 = input.LA(7);

                                    if ( (LA2_27==ID) ) {
                                        alt2=1;
                                    }
                                    else if ( (LA2_27==66) ) {
                                        alt2=2;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 2, 27, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 2, 20, input);

                                    throw nvae;

                                }
                                }
                                break;
                            case ID:
                                {
                                alt2=1;
                                }
                                break;
                            case 66:
                                {
                                alt2=2;
                                }
                                break;
                            default:
                                NoViableAltException nvae =
                                    new NoViableAltException("", 2, 13, input);

                                throw nvae;

                            }

                            }
                            break;
                        case 88:
                            {
                            switch ( input.LA(5) ) {
                            case 85:
                                {
                                int LA2_21 = input.LA(6);

                                if ( (LA2_21==86) ) {
                                    int LA2_28 = input.LA(7);

                                    if ( (LA2_28==ID) ) {
                                        alt2=1;
                                    }
                                    else if ( (LA2_28==66) ) {
                                        alt2=2;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 2, 28, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 2, 21, input);

                                    throw nvae;

                                }
                                }
                                break;
                            case ID:
                                {
                                alt2=1;
                                }
                                break;
                            case 66:
                                {
                                alt2=2;
                                }
                                break;
                            default:
                                NoViableAltException nvae =
                                    new NoViableAltException("", 2, 14, input);

                                throw nvae;

                            }

                            }
                            break;
                        case 84:
                            {
                            switch ( input.LA(5) ) {
                            case 85:
                                {
                                int LA2_22 = input.LA(6);

                                if ( (LA2_22==86) ) {
                                    int LA2_29 = input.LA(7);

                                    if ( (LA2_29==ID) ) {
                                        alt2=1;
                                    }
                                    else if ( (LA2_29==66) ) {
                                        alt2=2;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 2, 29, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 2, 22, input);

                                    throw nvae;

                                }
                                }
                                break;
                            case ID:
                                {
                                alt2=1;
                                }
                                break;
                            case 66:
                                {
                                alt2=2;
                                }
                                break;
                            default:
                                NoViableAltException nvae =
                                    new NoViableAltException("", 2, 15, input);

                                throw nvae;

                            }

                            }
                            break;
                        case 83:
                            {
                            switch ( input.LA(5) ) {
                            case 85:
                                {
                                int LA2_23 = input.LA(6);

                                if ( (LA2_23==86) ) {
                                    int LA2_30 = input.LA(7);

                                    if ( (LA2_30==ID) ) {
                                        alt2=1;
                                    }
                                    else if ( (LA2_30==66) ) {
                                        alt2=2;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 2, 30, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 2, 23, input);

                                    throw nvae;

                                }
                                }
                                break;
                            case ID:
                                {
                                alt2=1;
                                }
                                break;
                            case 66:
                                {
                                alt2=2;
                                }
                                break;
                            default:
                                NoViableAltException nvae =
                                    new NoViableAltException("", 2, 16, input);

                                throw nvae;

                            }

                            }
                            break;
                        case ID:
                            {
                            switch ( input.LA(5) ) {
                            case 85:
                                {
                                int LA2_24 = input.LA(6);

                                if ( (LA2_24==86) ) {
                                    int LA2_31 = input.LA(7);

                                    if ( (LA2_31==ID) ) {
                                        alt2=1;
                                    }
                                    else if ( (LA2_31==66) ) {
                                        alt2=2;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 2, 31, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 2, 24, input);

                                    throw nvae;

                                }
                                }
                                break;
                            case ID:
                                {
                                alt2=1;
                                }
                                break;
                            case DOT_ID:
                            case 66:
                            case 67:
                            case 68:
                            case 70:
                            case 72:
                            case 95:
                                {
                                alt2=2;
                                }
                                break;
                            default:
                                NoViableAltException nvae =
                                    new NoViableAltException("", 2, 17, input);

                                throw nvae;

                            }

                            }
                            break;
                        case FLOAT:
                        case INT:
                        case STRING:
                        case UTF8:
                        case 64:
                        case 65:
                        case 70:
                            {
                            alt2=2;
                            }
                            break;
                        default:
                            NoViableAltException nvae =
                                new NoViableAltException("", 2, 9, input);

                            throw nvae;

                        }

                        }
                        break;
                    case 83:
                    case 84:
                    case 88:
                    case 90:
                    case 93:
                    case 94:
                        {
                        alt2=1;
                        }
                        break;
                    case ID:
                        {
                        int LA2_10 = input.LA(4);

                        if ( (LA2_10==ID||LA2_10==85) ) {
                            alt2=1;
                        }
                        else if ( (LA2_10==DOT_ID||(LA2_10 >= 66 && LA2_10 <= 70)||LA2_10==72||LA2_10==95) ) {
                            alt2=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 2, 10, input);

                            throw nvae;

                        }
                        }
                        break;
                    case FLOAT:
                    case INT:
                    case STRING:
                    case UTF8:
                    case 64:
                    case 66:
                    case 70:
                        {
                        alt2=2;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 5, input);

                        throw nvae;

                    }

                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;

                }
                }
                break;
            case 82:
                {
                alt2=3;
                }
                break;
            case 87:
            case 89:
                {
                alt2=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;

            }

            switch (alt2) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:88:7: table_decl
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_table_decl_in_stat362);
                    table_decl3=table_decl();

                    state._fsp--;

                    adaptor.addChild(root_0, table_decl3.getTree());

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:89:3: rule
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_rule_in_stat366);
                    rule4=rule();

                    state._fsp--;

                    adaptor.addChild(root_0, rule4.getTree());

                    }
                    break;
                case 3 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:90:3: query
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_query_in_stat370);
                    query5=query();

                    state._fsp--;

                    adaptor.addChild(root_0, query5.getTree());

                    }
                    break;
                case 4 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:91:3: table_stmt
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_table_stmt_in_stat374);
                    table_stmt6=table_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, table_stmt6.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "stat"


    public static class table_stmt_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "table_stmt"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:93:1: table_stmt : ( 'clear' ID DOT_END -> ^( CLEAR ID ) | 'drop' ID DOT_END -> ^( DROP ( ID )? ) );
    public final SociaLiteParser.table_stmt_return table_stmt() throws RecognitionException {
        SociaLiteParser.table_stmt_return retval = new SociaLiteParser.table_stmt_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token string_literal7=null;
        Token ID8=null;
        Token DOT_END9=null;
        Token string_literal10=null;
        Token ID11=null;
        Token DOT_END12=null;

        CommonTree string_literal7_tree=null;
        CommonTree ID8_tree=null;
        CommonTree DOT_END9_tree=null;
        CommonTree string_literal10_tree=null;
        CommonTree ID11_tree=null;
        CommonTree DOT_END12_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_DOT_END=new RewriteRuleTokenStream(adaptor,"token DOT_END");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:93:12: ( 'clear' ID DOT_END -> ^( CLEAR ID ) | 'drop' ID DOT_END -> ^( DROP ( ID )? ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==87) ) {
                alt3=1;
            }
            else if ( (LA3_0==89) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;

            }
            switch (alt3) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:93:14: 'clear' ID DOT_END
                    {
                    string_literal7=(Token)match(input,87,FOLLOW_87_in_table_stmt383);  
                    stream_87.add(string_literal7);


                    ID8=(Token)match(input,ID,FOLLOW_ID_in_table_stmt385);  
                    stream_ID.add(ID8);


                    DOT_END9=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_table_stmt387);  
                    stream_DOT_END.add(DOT_END9);


                    // AST REWRITE
                    // elements: ID
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 93:33: -> ^( CLEAR ID )
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:93:36: ^( CLEAR ID )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(CLEAR, "CLEAR")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_ID.nextNode()
                        );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:94:3: 'drop' ID DOT_END
                    {
                    string_literal10=(Token)match(input,89,FOLLOW_89_in_table_stmt400);  
                    stream_89.add(string_literal10);


                    ID11=(Token)match(input,ID,FOLLOW_ID_in_table_stmt402);  
                    stream_ID.add(ID11);


                    DOT_END12=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_table_stmt404);  
                    stream_DOT_END.add(DOT_END12);


                    // AST REWRITE
                    // elements: ID
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 94:21: -> ^( DROP ( ID )? )
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:94:24: ^( DROP ( ID )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(DROP, "DROP")
                        , root_1);

                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:94:31: ( ID )?
                        if ( stream_ID.hasNext() ) {
                            adaptor.addChild(root_1, 
                            stream_ID.nextNode()
                            );

                        }
                        stream_ID.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "table_stmt"


    public static class query_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "query"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:97:1: query : '?-' predicate DOT_END -> ^( QUERY predicate ) ;
    public final SociaLiteParser.query_return query() throws RecognitionException {
        SociaLiteParser.query_return retval = new SociaLiteParser.query_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token string_literal13=null;
        Token DOT_END15=null;
        SociaLiteParser.predicate_return predicate14 =null;


        CommonTree string_literal13_tree=null;
        CommonTree DOT_END15_tree=null;
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleTokenStream stream_DOT_END=new RewriteRuleTokenStream(adaptor,"token DOT_END");
        RewriteRuleSubtreeStream stream_predicate=new RewriteRuleSubtreeStream(adaptor,"rule predicate");
        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:97:7: ( '?-' predicate DOT_END -> ^( QUERY predicate ) )
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:97:8: '?-' predicate DOT_END
            {
            string_literal13=(Token)match(input,82,FOLLOW_82_in_query422);  
            stream_82.add(string_literal13);


            pushFollow(FOLLOW_predicate_in_query424);
            predicate14=predicate();

            state._fsp--;

            stream_predicate.add(predicate14.getTree());

            DOT_END15=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_query426);  
            stream_DOT_END.add(DOT_END15);


            // AST REWRITE
            // elements: predicate
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 97:31: -> ^( QUERY predicate )
            {
                // /x/jiwon/workspace/socialite/grammar/SociaLite.g:97:34: ^( QUERY predicate )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(QUERY, "QUERY")
                , root_1);

                adaptor.addChild(root_1, stream_predicate.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "query"


    public static class rule_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "rule"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:100:1: rule : predicate ':-' body1= litlist ( DOT_END | ( ';' ':-' body2= litlist DOT_END ) ) -> ^( RULE ^( HEAD predicate ) ^( BODY $body1) ^( BODY ( $body2)? ) ) ;
    public final SociaLiteParser.rule_return rule() throws RecognitionException {
        SociaLiteParser.rule_return retval = new SociaLiteParser.rule_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token string_literal17=null;
        Token DOT_END18=null;
        Token char_literal19=null;
        Token string_literal20=null;
        Token DOT_END21=null;
        SociaLiteParser.litlist_return body1 =null;

        SociaLiteParser.litlist_return body2 =null;

        SociaLiteParser.predicate_return predicate16 =null;


        CommonTree string_literal17_tree=null;
        CommonTree DOT_END18_tree=null;
        CommonTree char_literal19_tree=null;
        CommonTree string_literal20_tree=null;
        CommonTree DOT_END21_tree=null;
        RewriteRuleTokenStream stream_DOT_END=new RewriteRuleTokenStream(adaptor,"token DOT_END");
        RewriteRuleTokenStream stream_74=new RewriteRuleTokenStream(adaptor,"token 74");
        RewriteRuleTokenStream stream_75=new RewriteRuleTokenStream(adaptor,"token 75");
        RewriteRuleSubtreeStream stream_predicate=new RewriteRuleSubtreeStream(adaptor,"rule predicate");
        RewriteRuleSubtreeStream stream_litlist=new RewriteRuleSubtreeStream(adaptor,"rule litlist");
        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:100:9: ( predicate ':-' body1= litlist ( DOT_END | ( ';' ':-' body2= litlist DOT_END ) ) -> ^( RULE ^( HEAD predicate ) ^( BODY $body1) ^( BODY ( $body2)? ) ) )
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:100:11: predicate ':-' body1= litlist ( DOT_END | ( ';' ':-' body2= litlist DOT_END ) )
            {
            pushFollow(FOLLOW_predicate_in_rule447);
            predicate16=predicate();

            state._fsp--;

            stream_predicate.add(predicate16.getTree());

            string_literal17=(Token)match(input,74,FOLLOW_74_in_rule449);  
            stream_74.add(string_literal17);


            pushFollow(FOLLOW_litlist_in_rule453);
            body1=litlist();

            state._fsp--;

            stream_litlist.add(body1.getTree());

            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:101:17: ( DOT_END | ( ';' ':-' body2= litlist DOT_END ) )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==DOT_END) ) {
                alt4=1;
            }
            else if ( (LA4_0==75) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }
            switch (alt4) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:101:18: DOT_END
                    {
                    DOT_END18=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_rule473);  
                    stream_DOT_END.add(DOT_END18);


                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:101:28: ( ';' ':-' body2= litlist DOT_END )
                    {
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:101:28: ( ';' ':-' body2= litlist DOT_END )
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:101:29: ';' ':-' body2= litlist DOT_END
                    {
                    char_literal19=(Token)match(input,75,FOLLOW_75_in_rule478);  
                    stream_75.add(char_literal19);


                    string_literal20=(Token)match(input,74,FOLLOW_74_in_rule480);  
                    stream_74.add(string_literal20);


                    pushFollow(FOLLOW_litlist_in_rule484);
                    body2=litlist();

                    state._fsp--;

                    stream_litlist.add(body2.getTree());

                    DOT_END21=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_rule486);  
                    stream_DOT_END.add(DOT_END21);


                    }


                    }
                    break;

            }


            // AST REWRITE
            // elements: body2, predicate, body1
            // token labels: 
            // rule labels: body2, retval, body1
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_body2=new RewriteRuleSubtreeStream(adaptor,"rule body2",body2!=null?body2.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_body1=new RewriteRuleSubtreeStream(adaptor,"rule body1",body1!=null?body1.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 102:11: -> ^( RULE ^( HEAD predicate ) ^( BODY $body1) ^( BODY ( $body2)? ) )
            {
                // /x/jiwon/workspace/socialite/grammar/SociaLite.g:102:14: ^( RULE ^( HEAD predicate ) ^( BODY $body1) ^( BODY ( $body2)? ) )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(RULE, "RULE")
                , root_1);

                // /x/jiwon/workspace/socialite/grammar/SociaLite.g:102:21: ^( HEAD predicate )
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(HEAD, "HEAD")
                , root_2);

                adaptor.addChild(root_2, stream_predicate.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                // /x/jiwon/workspace/socialite/grammar/SociaLite.g:102:39: ^( BODY $body1)
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(BODY, "BODY")
                , root_2);

                adaptor.addChild(root_2, stream_body1.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                // /x/jiwon/workspace/socialite/grammar/SociaLite.g:102:54: ^( BODY ( $body2)? )
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(BODY, "BODY")
                , root_2);

                // /x/jiwon/workspace/socialite/grammar/SociaLite.g:102:63: ( $body2)?
                if ( stream_body2.hasNext() ) {
                    adaptor.addChild(root_2, stream_body2.nextTree());

                }
                stream_body2.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "rule"


    public static class litlist_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "litlist"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:104:1: litlist : literal ( ',' ! literal )* ;
    public final SociaLiteParser.litlist_return litlist() throws RecognitionException {
        SociaLiteParser.litlist_return retval = new SociaLiteParser.litlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal23=null;
        SociaLiteParser.literal_return literal22 =null;

        SociaLiteParser.literal_return literal24 =null;


        CommonTree char_literal23_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:104:9: ( literal ( ',' ! literal )* )
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:104:10: literal ( ',' ! literal )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_literal_in_litlist534);
            literal22=literal();

            state._fsp--;

            adaptor.addChild(root_0, literal22.getTree());

            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:104:18: ( ',' ! literal )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==69) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:104:19: ',' ! literal
            	    {
            	    char_literal23=(Token)match(input,69,FOLLOW_69_in_litlist537); 

            	    pushFollow(FOLLOW_literal_in_litlist540);
            	    literal24=literal();

            	    state._fsp--;

            	    adaptor.addChild(root_0, literal24.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "litlist"


    public static class literal_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "literal"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:106:1: literal : ( predicate -> ^( PREDICATE predicate ) | NOT predicate -> ^( PREDICATE NOT predicate ) | expr -> ^( EXPR expr ) );
    public final SociaLiteParser.literal_return literal() throws RecognitionException {
        SociaLiteParser.literal_return retval = new SociaLiteParser.literal_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token NOT26=null;
        SociaLiteParser.predicate_return predicate25 =null;

        SociaLiteParser.predicate_return predicate27 =null;

        SociaLiteParser.expr_return expr28 =null;


        CommonTree NOT26_tree=null;
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleSubtreeStream stream_predicate=new RewriteRuleSubtreeStream(adaptor,"rule predicate");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:106:9: ( predicate -> ^( PREDICATE predicate ) | NOT predicate -> ^( PREDICATE NOT predicate ) | expr -> ^( EXPR expr ) )
            int alt6=3;
            switch ( input.LA(1) ) {
            case ID:
                {
                int LA6_1 = input.LA(2);

                if ( (LA6_1==65||LA6_1==85) ) {
                    alt6=1;
                }
                else if ( (LA6_1==DOT_ID||LA6_1==63||(LA6_1 >= 67 && LA6_1 <= 68)||LA6_1==70||LA6_1==72||(LA6_1 >= 76 && LA6_1 <= 81)||LA6_1==95) ) {
                    alt6=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 1, input);

                    throw nvae;

                }
                }
                break;
            case NOT:
                {
                alt6=2;
                }
                break;
            case FLOAT:
            case INT:
            case STRING:
            case UTF8:
            case 64:
            case 65:
            case 70:
                {
                alt6=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;

            }

            switch (alt6) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:106:10: predicate
                    {
                    pushFollow(FOLLOW_predicate_in_literal549);
                    predicate25=predicate();

                    state._fsp--;

                    stream_predicate.add(predicate25.getTree());

                    // AST REWRITE
                    // elements: predicate
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 106:20: -> ^( PREDICATE predicate )
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:106:23: ^( PREDICATE predicate )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(PREDICATE, "PREDICATE")
                        , root_1);

                        adaptor.addChild(root_1, stream_predicate.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:107:4: NOT predicate
                    {
                    NOT26=(Token)match(input,NOT,FOLLOW_NOT_in_literal563);  
                    stream_NOT.add(NOT26);


                    pushFollow(FOLLOW_predicate_in_literal565);
                    predicate27=predicate();

                    state._fsp--;

                    stream_predicate.add(predicate27.getTree());

                    // AST REWRITE
                    // elements: predicate, NOT
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 107:18: -> ^( PREDICATE NOT predicate )
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:107:21: ^( PREDICATE NOT predicate )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(PREDICATE, "PREDICATE")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_NOT.nextNode()
                        );

                        adaptor.addChild(root_1, stream_predicate.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 3 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:108:3: expr
                    {
                    pushFollow(FOLLOW_expr_in_literal579);
                    expr28=expr();

                    state._fsp--;

                    stream_expr.add(expr28.getTree());

                    // AST REWRITE
                    // elements: expr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 108:8: -> ^( EXPR expr )
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:108:11: ^( EXPR expr )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(EXPR, "EXPR")
                        , root_1);

                        adaptor.addChild(root_1, stream_expr.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "literal"


    public static class expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "expr"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:110:1: expr : ( simpleExpr ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^) simpleExpr | varlist '=' ( cast )? function -> ^( MULTI_ASSIGN varlist function ( cast )? ) );
    public final SociaLiteParser.expr_return expr() throws RecognitionException {
        SociaLiteParser.expr_return retval = new SociaLiteParser.expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal30=null;
        Token char_literal31=null;
        Token char_literal32=null;
        Token string_literal33=null;
        Token string_literal34=null;
        Token string_literal35=null;
        Token string_literal36=null;
        Token char_literal39=null;
        SociaLiteParser.simpleExpr_return simpleExpr29 =null;

        SociaLiteParser.simpleExpr_return simpleExpr37 =null;

        SociaLiteParser.varlist_return varlist38 =null;

        SociaLiteParser.cast_return cast40 =null;

        SociaLiteParser.function_return function41 =null;


        CommonTree char_literal30_tree=null;
        CommonTree char_literal31_tree=null;
        CommonTree char_literal32_tree=null;
        CommonTree string_literal33_tree=null;
        CommonTree string_literal34_tree=null;
        CommonTree string_literal35_tree=null;
        CommonTree string_literal36_tree=null;
        CommonTree char_literal39_tree=null;
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleSubtreeStream stream_varlist=new RewriteRuleSubtreeStream(adaptor,"rule varlist");
        RewriteRuleSubtreeStream stream_cast=new RewriteRuleSubtreeStream(adaptor,"rule cast");
        RewriteRuleSubtreeStream stream_function=new RewriteRuleSubtreeStream(adaptor,"rule function");
        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:110:5: ( simpleExpr ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^) simpleExpr | varlist '=' ( cast )? function -> ^( MULTI_ASSIGN varlist function ( cast )? ) )
            int alt9=2;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:110:7: simpleExpr ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^) simpleExpr
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_simpleExpr_in_expr596);
                    simpleExpr29=simpleExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, simpleExpr29.getTree());

                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:110:19: ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^)
                    int alt7=7;
                    switch ( input.LA(1) ) {
                    case 76:
                        {
                        alt7=1;
                        }
                        break;
                    case 80:
                        {
                        alt7=2;
                        }
                        break;
                    case 78:
                        {
                        alt7=3;
                        }
                        break;
                    case 63:
                        {
                        alt7=4;
                        }
                        break;
                    case 79:
                        {
                        alt7=5;
                        }
                        break;
                    case 81:
                        {
                        alt7=6;
                        }
                        break;
                    case 77:
                        {
                        alt7=7;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 7, 0, input);

                        throw nvae;

                    }

                    switch (alt7) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:110:20: '<' ^
                            {
                            char_literal30=(Token)match(input,76,FOLLOW_76_in_expr600); 
                            char_literal30_tree = 
                            (CommonTree)adaptor.create(char_literal30)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(char_literal30_tree, root_0);


                            }
                            break;
                        case 2 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:110:27: '>' ^
                            {
                            char_literal31=(Token)match(input,80,FOLLOW_80_in_expr605); 
                            char_literal31_tree = 
                            (CommonTree)adaptor.create(char_literal31)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(char_literal31_tree, root_0);


                            }
                            break;
                        case 3 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:110:34: '=' ^
                            {
                            char_literal32=(Token)match(input,78,FOLLOW_78_in_expr610); 
                            char_literal32_tree = 
                            (CommonTree)adaptor.create(char_literal32)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(char_literal32_tree, root_0);


                            }
                            break;
                        case 4 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:110:40: '!=' ^
                            {
                            string_literal33=(Token)match(input,63,FOLLOW_63_in_expr614); 
                            string_literal33_tree = 
                            (CommonTree)adaptor.create(string_literal33)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(string_literal33_tree, root_0);


                            }
                            break;
                        case 5 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:110:48: '==' ^
                            {
                            string_literal34=(Token)match(input,79,FOLLOW_79_in_expr619); 
                            string_literal34_tree = 
                            (CommonTree)adaptor.create(string_literal34)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(string_literal34_tree, root_0);


                            }
                            break;
                        case 6 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:110:56: '>=' ^
                            {
                            string_literal35=(Token)match(input,81,FOLLOW_81_in_expr624); 
                            string_literal35_tree = 
                            (CommonTree)adaptor.create(string_literal35)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(string_literal35_tree, root_0);


                            }
                            break;
                        case 7 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:110:64: '<=' ^
                            {
                            string_literal36=(Token)match(input,77,FOLLOW_77_in_expr629); 
                            string_literal36_tree = 
                            (CommonTree)adaptor.create(string_literal36)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(string_literal36_tree, root_0);


                            }
                            break;

                    }


                    pushFollow(FOLLOW_simpleExpr_in_expr634);
                    simpleExpr37=simpleExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, simpleExpr37.getTree());

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:111:4: varlist '=' ( cast )? function
                    {
                    pushFollow(FOLLOW_varlist_in_expr639);
                    varlist38=varlist();

                    state._fsp--;

                    stream_varlist.add(varlist38.getTree());

                    char_literal39=(Token)match(input,78,FOLLOW_78_in_expr641);  
                    stream_78.add(char_literal39);


                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:111:16: ( cast )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==65) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:111:16: cast
                            {
                            pushFollow(FOLLOW_cast_in_expr643);
                            cast40=cast();

                            state._fsp--;

                            stream_cast.add(cast40.getTree());

                            }
                            break;

                    }


                    pushFollow(FOLLOW_function_in_expr646);
                    function41=function();

                    state._fsp--;

                    stream_function.add(function41.getTree());

                    // AST REWRITE
                    // elements: cast, varlist, function
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 111:31: -> ^( MULTI_ASSIGN varlist function ( cast )? )
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:111:34: ^( MULTI_ASSIGN varlist function ( cast )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(MULTI_ASSIGN, "MULTI_ASSIGN")
                        , root_1);

                        adaptor.addChild(root_1, stream_varlist.nextTree());

                        adaptor.addChild(root_1, stream_function.nextTree());

                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:111:66: ( cast )?
                        if ( stream_cast.hasNext() ) {
                            adaptor.addChild(root_1, stream_cast.nextTree());

                        }
                        stream_cast.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "expr"


    public static class simpleExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "simpleExpr"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:113:1: simpleExpr : multExpr ( ( '+' ^| '-' ^) multExpr )* ;
    public final SociaLiteParser.simpleExpr_return simpleExpr() throws RecognitionException {
        SociaLiteParser.simpleExpr_return retval = new SociaLiteParser.simpleExpr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal43=null;
        Token char_literal44=null;
        SociaLiteParser.multExpr_return multExpr42 =null;

        SociaLiteParser.multExpr_return multExpr45 =null;


        CommonTree char_literal43_tree=null;
        CommonTree char_literal44_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:113:11: ( multExpr ( ( '+' ^| '-' ^) multExpr )* )
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:113:13: multExpr ( ( '+' ^| '-' ^) multExpr )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_multExpr_in_simpleExpr668);
            multExpr42=multExpr();

            state._fsp--;

            adaptor.addChild(root_0, multExpr42.getTree());

            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:113:23: ( ( '+' ^| '-' ^) multExpr )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==68||LA11_0==70) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:113:24: ( '+' ^| '-' ^) multExpr
            	    {
            	    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:113:24: ( '+' ^| '-' ^)
            	    int alt10=2;
            	    int LA10_0 = input.LA(1);

            	    if ( (LA10_0==68) ) {
            	        alt10=1;
            	    }
            	    else if ( (LA10_0==70) ) {
            	        alt10=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 10, 0, input);

            	        throw nvae;

            	    }
            	    switch (alt10) {
            	        case 1 :
            	            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:113:25: '+' ^
            	            {
            	            char_literal43=(Token)match(input,68,FOLLOW_68_in_simpleExpr673); 
            	            char_literal43_tree = 
            	            (CommonTree)adaptor.create(char_literal43)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal43_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:113:30: '-' ^
            	            {
            	            char_literal44=(Token)match(input,70,FOLLOW_70_in_simpleExpr676); 
            	            char_literal44_tree = 
            	            (CommonTree)adaptor.create(char_literal44)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal44_tree, root_0);


            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_multExpr_in_simpleExpr680);
            	    multExpr45=multExpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, multExpr45.getTree());

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "simpleExpr"


    public static class multExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "multExpr"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:115:1: multExpr : exprValue ( ( '*' ^| '/' ^| 'mod' ^) exprValue )* ;
    public final SociaLiteParser.multExpr_return multExpr() throws RecognitionException {
        SociaLiteParser.multExpr_return retval = new SociaLiteParser.multExpr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal47=null;
        Token char_literal48=null;
        Token string_literal49=null;
        SociaLiteParser.exprValue_return exprValue46 =null;

        SociaLiteParser.exprValue_return exprValue50 =null;


        CommonTree char_literal47_tree=null;
        CommonTree char_literal48_tree=null;
        CommonTree string_literal49_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:115:9: ( exprValue ( ( '*' ^| '/' ^| 'mod' ^) exprValue )* )
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:115:11: exprValue ( ( '*' ^| '/' ^| 'mod' ^) exprValue )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_exprValue_in_multExpr691);
            exprValue46=exprValue();

            state._fsp--;

            adaptor.addChild(root_0, exprValue46.getTree());

            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:115:21: ( ( '*' ^| '/' ^| 'mod' ^) exprValue )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==67||LA13_0==72||LA13_0==95) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:115:22: ( '*' ^| '/' ^| 'mod' ^) exprValue
            	    {
            	    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:115:22: ( '*' ^| '/' ^| 'mod' ^)
            	    int alt12=3;
            	    switch ( input.LA(1) ) {
            	    case 67:
            	        {
            	        alt12=1;
            	        }
            	        break;
            	    case 72:
            	        {
            	        alt12=2;
            	        }
            	        break;
            	    case 95:
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
            	            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:115:23: '*' ^
            	            {
            	            char_literal47=(Token)match(input,67,FOLLOW_67_in_multExpr695); 
            	            char_literal47_tree = 
            	            (CommonTree)adaptor.create(char_literal47)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal47_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:115:28: '/' ^
            	            {
            	            char_literal48=(Token)match(input,72,FOLLOW_72_in_multExpr698); 
            	            char_literal48_tree = 
            	            (CommonTree)adaptor.create(char_literal48)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal48_tree, root_0);


            	            }
            	            break;
            	        case 3 :
            	            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:115:33: 'mod' ^
            	            {
            	            string_literal49=(Token)match(input,95,FOLLOW_95_in_multExpr701); 
            	            string_literal49_tree = 
            	            (CommonTree)adaptor.create(string_literal49)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(string_literal49_tree, root_0);


            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_exprValue_in_multExpr705);
            	    exprValue50=exprValue();

            	    state._fsp--;

            	    adaptor.addChild(root_0, exprValue50.getTree());

            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "multExpr"


    public static class exprValue_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "exprValue"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:116:1: exprValue : ( (neg= '-' )? ( cast )? term -> ^( TERM term ( $neg)? ( cast )? ) | ( cast )? function -> ^( FUNCTION function ( cast )? ) | ( cast )? compExpr -> ^( COMPOUND_EXPR compExpr ( cast )? ) );
    public final SociaLiteParser.exprValue_return exprValue() throws RecognitionException {
        SociaLiteParser.exprValue_return retval = new SociaLiteParser.exprValue_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token neg=null;
        SociaLiteParser.cast_return cast51 =null;

        SociaLiteParser.term_return term52 =null;

        SociaLiteParser.cast_return cast53 =null;

        SociaLiteParser.function_return function54 =null;

        SociaLiteParser.cast_return cast55 =null;

        SociaLiteParser.compExpr_return compExpr56 =null;


        CommonTree neg_tree=null;
        RewriteRuleTokenStream stream_70=new RewriteRuleTokenStream(adaptor,"token 70");
        RewriteRuleSubtreeStream stream_cast=new RewriteRuleSubtreeStream(adaptor,"rule cast");
        RewriteRuleSubtreeStream stream_term=new RewriteRuleSubtreeStream(adaptor,"rule term");
        RewriteRuleSubtreeStream stream_compExpr=new RewriteRuleSubtreeStream(adaptor,"rule compExpr");
        RewriteRuleSubtreeStream stream_function=new RewriteRuleSubtreeStream(adaptor,"rule function");
        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:116:10: ( (neg= '-' )? ( cast )? term -> ^( TERM term ( $neg)? ( cast )? ) | ( cast )? function -> ^( FUNCTION function ( cast )? ) | ( cast )? compExpr -> ^( COMPOUND_EXPR compExpr ( cast )? ) )
            int alt18=3;
            switch ( input.LA(1) ) {
            case FLOAT:
            case ID:
            case INT:
            case STRING:
            case UTF8:
            case 70:
                {
                alt18=1;
                }
                break;
            case 65:
                {
                switch ( input.LA(2) ) {
                case 93:
                    {
                    int LA18_4 = input.LA(3);

                    if ( (LA18_4==85) ) {
                        int LA18_12 = input.LA(4);

                        if ( (LA18_12==86) ) {
                            int LA18_21 = input.LA(5);

                            if ( (LA18_21==66) ) {
                                switch ( input.LA(6) ) {
                                case FLOAT:
                                case ID:
                                case INT:
                                case STRING:
                                case UTF8:
                                    {
                                    alt18=1;
                                    }
                                    break;
                                case 64:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 65:
                                    {
                                    alt18=3;
                                    }
                                    break;
                                default:
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 18, 13, input);

                                    throw nvae;

                                }

                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 18, 21, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 18, 12, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA18_4==66) ) {
                        switch ( input.LA(4) ) {
                        case FLOAT:
                        case ID:
                        case INT:
                        case STRING:
                        case UTF8:
                            {
                            alt18=1;
                            }
                            break;
                        case 64:
                            {
                            alt18=2;
                            }
                            break;
                        case 65:
                            {
                            alt18=3;
                            }
                            break;
                        default:
                            NoViableAltException nvae =
                                new NoViableAltException("", 18, 13, input);

                            throw nvae;

                        }

                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 18, 4, input);

                        throw nvae;

                    }
                    }
                    break;
                case 94:
                    {
                    int LA18_5 = input.LA(3);

                    if ( (LA18_5==85) ) {
                        int LA18_14 = input.LA(4);

                        if ( (LA18_14==86) ) {
                            int LA18_22 = input.LA(5);

                            if ( (LA18_22==66) ) {
                                switch ( input.LA(6) ) {
                                case FLOAT:
                                case ID:
                                case INT:
                                case STRING:
                                case UTF8:
                                    {
                                    alt18=1;
                                    }
                                    break;
                                case 64:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 65:
                                    {
                                    alt18=3;
                                    }
                                    break;
                                default:
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 18, 13, input);

                                    throw nvae;

                                }

                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 18, 22, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 18, 14, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA18_5==66) ) {
                        switch ( input.LA(4) ) {
                        case FLOAT:
                        case ID:
                        case INT:
                        case STRING:
                        case UTF8:
                            {
                            alt18=1;
                            }
                            break;
                        case 64:
                            {
                            alt18=2;
                            }
                            break;
                        case 65:
                            {
                            alt18=3;
                            }
                            break;
                        default:
                            NoViableAltException nvae =
                                new NoViableAltException("", 18, 13, input);

                            throw nvae;

                        }

                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 18, 5, input);

                        throw nvae;

                    }
                    }
                    break;
                case 90:
                    {
                    int LA18_6 = input.LA(3);

                    if ( (LA18_6==85) ) {
                        int LA18_15 = input.LA(4);

                        if ( (LA18_15==86) ) {
                            int LA18_23 = input.LA(5);

                            if ( (LA18_23==66) ) {
                                switch ( input.LA(6) ) {
                                case FLOAT:
                                case ID:
                                case INT:
                                case STRING:
                                case UTF8:
                                    {
                                    alt18=1;
                                    }
                                    break;
                                case 64:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 65:
                                    {
                                    alt18=3;
                                    }
                                    break;
                                default:
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 18, 13, input);

                                    throw nvae;

                                }

                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 18, 23, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 18, 15, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA18_6==66) ) {
                        switch ( input.LA(4) ) {
                        case FLOAT:
                        case ID:
                        case INT:
                        case STRING:
                        case UTF8:
                            {
                            alt18=1;
                            }
                            break;
                        case 64:
                            {
                            alt18=2;
                            }
                            break;
                        case 65:
                            {
                            alt18=3;
                            }
                            break;
                        default:
                            NoViableAltException nvae =
                                new NoViableAltException("", 18, 13, input);

                            throw nvae;

                        }

                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 18, 6, input);

                        throw nvae;

                    }
                    }
                    break;
                case 88:
                    {
                    int LA18_7 = input.LA(3);

                    if ( (LA18_7==85) ) {
                        int LA18_16 = input.LA(4);

                        if ( (LA18_16==86) ) {
                            int LA18_24 = input.LA(5);

                            if ( (LA18_24==66) ) {
                                switch ( input.LA(6) ) {
                                case FLOAT:
                                case ID:
                                case INT:
                                case STRING:
                                case UTF8:
                                    {
                                    alt18=1;
                                    }
                                    break;
                                case 64:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 65:
                                    {
                                    alt18=3;
                                    }
                                    break;
                                default:
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 18, 13, input);

                                    throw nvae;

                                }

                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 18, 24, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 18, 16, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA18_7==66) ) {
                        switch ( input.LA(4) ) {
                        case FLOAT:
                        case ID:
                        case INT:
                        case STRING:
                        case UTF8:
                            {
                            alt18=1;
                            }
                            break;
                        case 64:
                            {
                            alt18=2;
                            }
                            break;
                        case 65:
                            {
                            alt18=3;
                            }
                            break;
                        default:
                            NoViableAltException nvae =
                                new NoViableAltException("", 18, 13, input);

                            throw nvae;

                        }

                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 18, 7, input);

                        throw nvae;

                    }
                    }
                    break;
                case 84:
                    {
                    int LA18_8 = input.LA(3);

                    if ( (LA18_8==85) ) {
                        int LA18_17 = input.LA(4);

                        if ( (LA18_17==86) ) {
                            int LA18_25 = input.LA(5);

                            if ( (LA18_25==66) ) {
                                switch ( input.LA(6) ) {
                                case FLOAT:
                                case ID:
                                case INT:
                                case STRING:
                                case UTF8:
                                    {
                                    alt18=1;
                                    }
                                    break;
                                case 64:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 65:
                                    {
                                    alt18=3;
                                    }
                                    break;
                                default:
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 18, 13, input);

                                    throw nvae;

                                }

                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 18, 25, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 18, 17, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA18_8==66) ) {
                        switch ( input.LA(4) ) {
                        case FLOAT:
                        case ID:
                        case INT:
                        case STRING:
                        case UTF8:
                            {
                            alt18=1;
                            }
                            break;
                        case 64:
                            {
                            alt18=2;
                            }
                            break;
                        case 65:
                            {
                            alt18=3;
                            }
                            break;
                        default:
                            NoViableAltException nvae =
                                new NoViableAltException("", 18, 13, input);

                            throw nvae;

                        }

                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 18, 8, input);

                        throw nvae;

                    }
                    }
                    break;
                case 83:
                    {
                    int LA18_9 = input.LA(3);

                    if ( (LA18_9==85) ) {
                        int LA18_18 = input.LA(4);

                        if ( (LA18_18==86) ) {
                            int LA18_26 = input.LA(5);

                            if ( (LA18_26==66) ) {
                                switch ( input.LA(6) ) {
                                case FLOAT:
                                case ID:
                                case INT:
                                case STRING:
                                case UTF8:
                                    {
                                    alt18=1;
                                    }
                                    break;
                                case 64:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 65:
                                    {
                                    alt18=3;
                                    }
                                    break;
                                default:
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 18, 13, input);

                                    throw nvae;

                                }

                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 18, 26, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 18, 18, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA18_9==66) ) {
                        switch ( input.LA(4) ) {
                        case FLOAT:
                        case ID:
                        case INT:
                        case STRING:
                        case UTF8:
                            {
                            alt18=1;
                            }
                            break;
                        case 64:
                            {
                            alt18=2;
                            }
                            break;
                        case 65:
                            {
                            alt18=3;
                            }
                            break;
                        default:
                            NoViableAltException nvae =
                                new NoViableAltException("", 18, 13, input);

                            throw nvae;

                        }

                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 18, 9, input);

                        throw nvae;

                    }
                    }
                    break;
                case ID:
                    {
                    switch ( input.LA(3) ) {
                    case 85:
                        {
                        int LA18_19 = input.LA(4);

                        if ( (LA18_19==86) ) {
                            int LA18_27 = input.LA(5);

                            if ( (LA18_27==66) ) {
                                switch ( input.LA(6) ) {
                                case FLOAT:
                                case ID:
                                case INT:
                                case STRING:
                                case UTF8:
                                    {
                                    alt18=1;
                                    }
                                    break;
                                case 64:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 65:
                                    {
                                    alt18=3;
                                    }
                                    break;
                                default:
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 18, 13, input);

                                    throw nvae;

                                }

                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 18, 27, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 18, 19, input);

                            throw nvae;

                        }
                        }
                        break;
                    case 66:
                        {
                        switch ( input.LA(4) ) {
                        case FLOAT:
                        case ID:
                        case INT:
                        case STRING:
                        case UTF8:
                            {
                            alt18=1;
                            }
                            break;
                        case 64:
                            {
                            alt18=2;
                            }
                            break;
                        case DOT_END:
                        case 63:
                        case 65:
                        case 66:
                        case 67:
                        case 68:
                        case 69:
                        case 70:
                        case 72:
                        case 75:
                        case 76:
                        case 77:
                        case 78:
                        case 79:
                        case 80:
                        case 81:
                        case 86:
                        case 95:
                            {
                            alt18=3;
                            }
                            break;
                        default:
                            NoViableAltException nvae =
                                new NoViableAltException("", 18, 20, input);

                            throw nvae;

                        }

                        }
                        break;
                    case DOT_ID:
                    case 67:
                    case 68:
                    case 70:
                    case 72:
                    case 95:
                        {
                        alt18=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 18, 10, input);

                        throw nvae;

                    }

                    }
                    break;
                case FLOAT:
                case INT:
                case STRING:
                case UTF8:
                case 64:
                case 65:
                case 70:
                    {
                    alt18=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 2, input);

                    throw nvae;

                }

                }
                break;
            case 64:
                {
                alt18=2;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;

            }

            switch (alt18) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:116:12: (neg= '-' )? ( cast )? term
                    {
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:116:12: (neg= '-' )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==70) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:116:13: neg= '-'
                            {
                            neg=(Token)match(input,70,FOLLOW_70_in_exprValue716);  
                            stream_70.add(neg);


                            }
                            break;

                    }


                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:116:23: ( cast )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==65) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:116:23: cast
                            {
                            pushFollow(FOLLOW_cast_in_exprValue720);
                            cast51=cast();

                            state._fsp--;

                            stream_cast.add(cast51.getTree());

                            }
                            break;

                    }


                    pushFollow(FOLLOW_term_in_exprValue723);
                    term52=term();

                    state._fsp--;

                    stream_term.add(term52.getTree());

                    // AST REWRITE
                    // elements: term, neg, cast
                    // token labels: neg
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_neg=new RewriteRuleTokenStream(adaptor,"token neg",neg);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 116:34: -> ^( TERM term ( $neg)? ( cast )? )
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:116:37: ^( TERM term ( $neg)? ( cast )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(TERM, "TERM")
                        , root_1);

                        adaptor.addChild(root_1, stream_term.nextTree());

                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:116:50: ( $neg)?
                        if ( stream_neg.hasNext() ) {
                            adaptor.addChild(root_1, stream_neg.nextNode());

                        }
                        stream_neg.reset();

                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:116:55: ( cast )?
                        if ( stream_cast.hasNext() ) {
                            adaptor.addChild(root_1, stream_cast.nextTree());

                        }
                        stream_cast.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:117:4: ( cast )? function
                    {
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:117:4: ( cast )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==65) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:117:4: cast
                            {
                            pushFollow(FOLLOW_cast_in_exprValue743);
                            cast53=cast();

                            state._fsp--;

                            stream_cast.add(cast53.getTree());

                            }
                            break;

                    }


                    pushFollow(FOLLOW_function_in_exprValue746);
                    function54=function();

                    state._fsp--;

                    stream_function.add(function54.getTree());

                    // AST REWRITE
                    // elements: cast, function
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 117:19: -> ^( FUNCTION function ( cast )? )
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:117:22: ^( FUNCTION function ( cast )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(FUNCTION, "FUNCTION")
                        , root_1);

                        adaptor.addChild(root_1, stream_function.nextTree());

                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:117:42: ( cast )?
                        if ( stream_cast.hasNext() ) {
                            adaptor.addChild(root_1, stream_cast.nextTree());

                        }
                        stream_cast.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 3 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:118:4: ( cast )? compExpr
                    {
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:118:4: ( cast )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==65) ) {
                        int LA17_1 = input.LA(2);

                        if ( ((LA17_1 >= 83 && LA17_1 <= 84)||LA17_1==88||LA17_1==90||(LA17_1 >= 93 && LA17_1 <= 94)) ) {
                            alt17=1;
                        }
                        else if ( (LA17_1==ID) ) {
                            int LA17_3 = input.LA(3);

                            if ( (LA17_3==85) ) {
                                alt17=1;
                            }
                            else if ( (LA17_3==66) ) {
                                int LA17_5 = input.LA(4);

                                if ( (LA17_5==65) ) {
                                    alt17=1;
                                }
                            }
                        }
                    }
                    switch (alt17) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:118:4: cast
                            {
                            pushFollow(FOLLOW_cast_in_exprValue762);
                            cast55=cast();

                            state._fsp--;

                            stream_cast.add(cast55.getTree());

                            }
                            break;

                    }


                    pushFollow(FOLLOW_compExpr_in_exprValue765);
                    compExpr56=compExpr();

                    state._fsp--;

                    stream_compExpr.add(compExpr56.getTree());

                    // AST REWRITE
                    // elements: cast, compExpr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 118:19: -> ^( COMPOUND_EXPR compExpr ( cast )? )
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:118:22: ^( COMPOUND_EXPR compExpr ( cast )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(COMPOUND_EXPR, "COMPOUND_EXPR")
                        , root_1);

                        adaptor.addChild(root_1, stream_compExpr.nextTree());

                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:118:47: ( cast )?
                        if ( stream_cast.hasNext() ) {
                            adaptor.addChild(root_1, stream_cast.nextTree());

                        }
                        stream_cast.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "exprValue"


    public static class compExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "compExpr"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:120:1: compExpr : '(' ! simpleExpr ')' !;
    public final SociaLiteParser.compExpr_return compExpr() throws RecognitionException {
        SociaLiteParser.compExpr_return retval = new SociaLiteParser.compExpr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal57=null;
        Token char_literal59=null;
        SociaLiteParser.simpleExpr_return simpleExpr58 =null;


        CommonTree char_literal57_tree=null;
        CommonTree char_literal59_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:120:9: ( '(' ! simpleExpr ')' !)
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:120:11: '(' ! simpleExpr ')' !
            {
            root_0 = (CommonTree)adaptor.nil();


            char_literal57=(Token)match(input,65,FOLLOW_65_in_compExpr784); 

            pushFollow(FOLLOW_simpleExpr_in_compExpr787);
            simpleExpr58=simpleExpr();

            state._fsp--;

            adaptor.addChild(root_0, simpleExpr58.getTree());

            char_literal59=(Token)match(input,66,FOLLOW_66_in_compExpr789); 

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "compExpr"


    public static class cast_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "cast"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:121:1: cast : '(' type ')' -> type ;
    public final SociaLiteParser.cast_return cast() throws RecognitionException {
        SociaLiteParser.cast_return retval = new SociaLiteParser.cast_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal60=null;
        Token char_literal62=null;
        SociaLiteParser.type_return type61 =null;


        CommonTree char_literal60_tree=null;
        CommonTree char_literal62_tree=null;
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:121:5: ( '(' type ')' -> type )
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:121:7: '(' type ')'
            {
            char_literal60=(Token)match(input,65,FOLLOW_65_in_cast798);  
            stream_65.add(char_literal60);


            pushFollow(FOLLOW_type_in_cast800);
            type61=type();

            state._fsp--;

            stream_type.add(type61.getTree());

            char_literal62=(Token)match(input,66,FOLLOW_66_in_cast802);  
            stream_66.add(char_literal62);


            // AST REWRITE
            // elements: type
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 121:20: -> type
            {
                adaptor.addChild(root_0, stream_type.nextTree());

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "cast"


    public static class varlist_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "varlist"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:122:1: varlist : '(' !e1= dotname ( ',' !e2= dotname )+ ')' !;
    public final SociaLiteParser.varlist_return varlist() throws RecognitionException {
        SociaLiteParser.varlist_return retval = new SociaLiteParser.varlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal63=null;
        Token char_literal64=null;
        Token char_literal65=null;
        SociaLiteParser.dotname_return e1 =null;

        SociaLiteParser.dotname_return e2 =null;


        CommonTree char_literal63_tree=null;
        CommonTree char_literal64_tree=null;
        CommonTree char_literal65_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:122:8: ( '(' !e1= dotname ( ',' !e2= dotname )+ ')' !)
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:122:10: '(' !e1= dotname ( ',' !e2= dotname )+ ')' !
            {
            root_0 = (CommonTree)adaptor.nil();


            char_literal63=(Token)match(input,65,FOLLOW_65_in_varlist814); 

            pushFollow(FOLLOW_dotname_in_varlist819);
            e1=dotname();

            state._fsp--;

            adaptor.addChild(root_0, e1.getTree());

            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:122:27: ( ',' !e2= dotname )+
            int cnt19=0;
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==69) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:122:28: ',' !e2= dotname
            	    {
            	    char_literal64=(Token)match(input,69,FOLLOW_69_in_varlist823); 

            	    pushFollow(FOLLOW_dotname_in_varlist828);
            	    e2=dotname();

            	    state._fsp--;

            	    adaptor.addChild(root_0, e2.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt19 >= 1 ) break loop19;
                        EarlyExitException eee =
                            new EarlyExitException(19, input);
                        throw eee;
                }
                cnt19++;
            } while (true);


            char_literal65=(Token)match(input,66,FOLLOW_66_in_varlist832); 

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "varlist"


    public static class function_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "function"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:123:1: function : '$' dotname '(' ( fparamlist )? ')' -> ^( FUNC dotname ( fparamlist )? ) ;
    public final SociaLiteParser.function_return function() throws RecognitionException {
        SociaLiteParser.function_return retval = new SociaLiteParser.function_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal66=null;
        Token char_literal68=null;
        Token char_literal70=null;
        SociaLiteParser.dotname_return dotname67 =null;

        SociaLiteParser.fparamlist_return fparamlist69 =null;


        CommonTree char_literal66_tree=null;
        CommonTree char_literal68_tree=null;
        CommonTree char_literal70_tree=null;
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleSubtreeStream stream_fparamlist=new RewriteRuleSubtreeStream(adaptor,"rule fparamlist");
        RewriteRuleSubtreeStream stream_dotname=new RewriteRuleSubtreeStream(adaptor,"rule dotname");
        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:123:9: ( '$' dotname '(' ( fparamlist )? ')' -> ^( FUNC dotname ( fparamlist )? ) )
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:123:11: '$' dotname '(' ( fparamlist )? ')'
            {
            char_literal66=(Token)match(input,64,FOLLOW_64_in_function840);  
            stream_64.add(char_literal66);


            pushFollow(FOLLOW_dotname_in_function842);
            dotname67=dotname();

            state._fsp--;

            stream_dotname.add(dotname67.getTree());

            char_literal68=(Token)match(input,65,FOLLOW_65_in_function844);  
            stream_65.add(char_literal68);


            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:123:27: ( fparamlist )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==FLOAT||LA20_0==ID||LA20_0==INT||LA20_0==STRING||LA20_0==UTF8||(LA20_0 >= 64 && LA20_0 <= 65)||LA20_0==70) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:123:27: fparamlist
                    {
                    pushFollow(FOLLOW_fparamlist_in_function846);
                    fparamlist69=fparamlist();

                    state._fsp--;

                    stream_fparamlist.add(fparamlist69.getTree());

                    }
                    break;

            }


            char_literal70=(Token)match(input,66,FOLLOW_66_in_function849);  
            stream_66.add(char_literal70);


            // AST REWRITE
            // elements: dotname, fparamlist
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 123:43: -> ^( FUNC dotname ( fparamlist )? )
            {
                // /x/jiwon/workspace/socialite/grammar/SociaLite.g:123:46: ^( FUNC dotname ( fparamlist )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(FUNC, "FUNC")
                , root_1);

                adaptor.addChild(root_1, stream_dotname.nextTree());

                // /x/jiwon/workspace/socialite/grammar/SociaLite.g:123:61: ( fparamlist )?
                if ( stream_fparamlist.hasNext() ) {
                    adaptor.addChild(root_1, stream_fparamlist.nextTree());

                }
                stream_fparamlist.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "function"


    public static class predicate_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "predicate"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:125:1: predicate : ID ( '[' param ']' )? '(' ( paramlist )? ')' -> ID ^( INDEX ( param )? ) ( paramlist )? ;
    public final SociaLiteParser.predicate_return predicate() throws RecognitionException {
        SociaLiteParser.predicate_return retval = new SociaLiteParser.predicate_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token ID71=null;
        Token char_literal72=null;
        Token char_literal74=null;
        Token char_literal75=null;
        Token char_literal77=null;
        SociaLiteParser.param_return param73 =null;

        SociaLiteParser.paramlist_return paramlist76 =null;


        CommonTree ID71_tree=null;
        CommonTree char_literal72_tree=null;
        CommonTree char_literal74_tree=null;
        CommonTree char_literal75_tree=null;
        CommonTree char_literal77_tree=null;
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_85=new RewriteRuleTokenStream(adaptor,"token 85");
        RewriteRuleSubtreeStream stream_param=new RewriteRuleSubtreeStream(adaptor,"rule param");
        RewriteRuleSubtreeStream stream_paramlist=new RewriteRuleSubtreeStream(adaptor,"rule paramlist");
        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:125:11: ( ID ( '[' param ']' )? '(' ( paramlist )? ')' -> ID ^( INDEX ( param )? ) ( paramlist )? )
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:125:12: ID ( '[' param ']' )? '(' ( paramlist )? ')'
            {
            ID71=(Token)match(input,ID,FOLLOW_ID_in_predicate868);  
            stream_ID.add(ID71);


            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:125:15: ( '[' param ']' )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==85) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:125:16: '[' param ']'
                    {
                    char_literal72=(Token)match(input,85,FOLLOW_85_in_predicate871);  
                    stream_85.add(char_literal72);


                    pushFollow(FOLLOW_param_in_predicate873);
                    param73=param();

                    state._fsp--;

                    stream_param.add(param73.getTree());

                    char_literal74=(Token)match(input,86,FOLLOW_86_in_predicate875);  
                    stream_86.add(char_literal74);


                    }
                    break;

            }


            char_literal75=(Token)match(input,65,FOLLOW_65_in_predicate879);  
            stream_65.add(char_literal75);


            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:125:36: ( paramlist )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==FLOAT||LA22_0==ID||LA22_0==INT||LA22_0==STRING||LA22_0==UTF8||(LA22_0 >= 64 && LA22_0 <= 65)||LA22_0==70) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:125:36: paramlist
                    {
                    pushFollow(FOLLOW_paramlist_in_predicate881);
                    paramlist76=paramlist();

                    state._fsp--;

                    stream_paramlist.add(paramlist76.getTree());

                    }
                    break;

            }


            char_literal77=(Token)match(input,66,FOLLOW_66_in_predicate884);  
            stream_66.add(char_literal77);


            // AST REWRITE
            // elements: ID, param, paramlist
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 125:51: -> ID ^( INDEX ( param )? ) ( paramlist )?
            {
                adaptor.addChild(root_0, 
                stream_ID.nextNode()
                );

                // /x/jiwon/workspace/socialite/grammar/SociaLite.g:125:57: ^( INDEX ( param )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(INDEX, "INDEX")
                , root_1);

                // /x/jiwon/workspace/socialite/grammar/SociaLite.g:125:65: ( param )?
                if ( stream_param.hasNext() ) {
                    adaptor.addChild(root_1, stream_param.nextTree());

                }
                stream_param.reset();

                adaptor.addChild(root_0, root_1);
                }

                // /x/jiwon/workspace/socialite/grammar/SociaLite.g:125:73: ( paramlist )?
                if ( stream_paramlist.hasNext() ) {
                    adaptor.addChild(root_0, stream_paramlist.nextTree());

                }
                stream_paramlist.reset();

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "predicate"


    public static class paramlist_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "paramlist"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:126:1: paramlist : param ( ',' ! param )* ;
    public final SociaLiteParser.paramlist_return paramlist() throws RecognitionException {
        SociaLiteParser.paramlist_return retval = new SociaLiteParser.paramlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal79=null;
        SociaLiteParser.param_return param78 =null;

        SociaLiteParser.param_return param80 =null;


        CommonTree char_literal79_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:126:11: ( param ( ',' ! param )* )
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:126:12: param ( ',' ! param )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_param_in_paramlist904);
            param78=param();

            state._fsp--;

            adaptor.addChild(root_0, param78.getTree());

            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:126:18: ( ',' ! param )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==69) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:126:19: ',' ! param
            	    {
            	    char_literal79=(Token)match(input,69,FOLLOW_69_in_paramlist907); 

            	    pushFollow(FOLLOW_param_in_paramlist910);
            	    param80=param();

            	    state._fsp--;

            	    adaptor.addChild(root_0, param80.getTree());

            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "paramlist"


    public static class fparamlist_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "fparamlist"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:127:1: fparamlist : fparam ( ',' ! fparam )* ;
    public final SociaLiteParser.fparamlist_return fparamlist() throws RecognitionException {
        SociaLiteParser.fparamlist_return retval = new SociaLiteParser.fparamlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal82=null;
        SociaLiteParser.fparam_return fparam81 =null;

        SociaLiteParser.fparam_return fparam83 =null;


        CommonTree char_literal82_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:127:12: ( fparam ( ',' ! fparam )* )
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:127:13: fparam ( ',' ! fparam )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_fparam_in_fparamlist918);
            fparam81=fparam();

            state._fsp--;

            adaptor.addChild(root_0, fparam81.getTree());

            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:127:20: ( ',' ! fparam )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==69) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:127:21: ',' ! fparam
            	    {
            	    char_literal82=(Token)match(input,69,FOLLOW_69_in_fparamlist921); 

            	    pushFollow(FOLLOW_fparam_in_fparamlist924);
            	    fparam83=fparam();

            	    state._fsp--;

            	    adaptor.addChild(root_0, fparam83.getTree());

            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "fparamlist"


    public static class param_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "param"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:129:1: param : simpleExpr ;
    public final SociaLiteParser.param_return param() throws RecognitionException {
        SociaLiteParser.param_return retval = new SociaLiteParser.param_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SociaLiteParser.simpleExpr_return simpleExpr84 =null;



        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:129:7: ( simpleExpr )
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:129:8: simpleExpr
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_simpleExpr_in_param933);
            simpleExpr84=simpleExpr();

            state._fsp--;

            adaptor.addChild(root_0, simpleExpr84.getTree());

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "param"


    public static class fparam_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "fparam"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:130:1: fparam : simpleExpr ;
    public final SociaLiteParser.fparam_return fparam() throws RecognitionException {
        SociaLiteParser.fparam_return retval = new SociaLiteParser.fparam_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SociaLiteParser.simpleExpr_return simpleExpr85 =null;



        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:130:8: ( simpleExpr )
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:130:9: simpleExpr
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_simpleExpr_in_fparam939);
            simpleExpr85=simpleExpr();

            state._fsp--;

            adaptor.addChild(root_0, simpleExpr85.getTree());

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "fparam"


    public static class term_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "term"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:132:1: term : ( INT -> ^( T_INT INT ) | FLOAT -> ^( T_FLOAT FLOAT ) | STRING -> ^( T_STR STRING ) | UTF8 -> ^( T_UTF8 UTF8 ) | dotname -> ^( T_VAR dotname ) );
    public final SociaLiteParser.term_return term() throws RecognitionException {
        SociaLiteParser.term_return retval = new SociaLiteParser.term_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token INT86=null;
        Token FLOAT87=null;
        Token STRING88=null;
        Token UTF889=null;
        SociaLiteParser.dotname_return dotname90 =null;


        CommonTree INT86_tree=null;
        CommonTree FLOAT87_tree=null;
        CommonTree STRING88_tree=null;
        CommonTree UTF889_tree=null;
        RewriteRuleTokenStream stream_FLOAT=new RewriteRuleTokenStream(adaptor,"token FLOAT");
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleTokenStream stream_UTF8=new RewriteRuleTokenStream(adaptor,"token UTF8");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_dotname=new RewriteRuleSubtreeStream(adaptor,"rule dotname");
        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:132:5: ( INT -> ^( T_INT INT ) | FLOAT -> ^( T_FLOAT FLOAT ) | STRING -> ^( T_STR STRING ) | UTF8 -> ^( T_UTF8 UTF8 ) | dotname -> ^( T_VAR dotname ) )
            int alt25=5;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt25=1;
                }
                break;
            case FLOAT:
                {
                alt25=2;
                }
                break;
            case STRING:
                {
                alt25=3;
                }
                break;
            case UTF8:
                {
                alt25=4;
                }
                break;
            case ID:
                {
                alt25=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;

            }

            switch (alt25) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:132:7: INT
                    {
                    INT86=(Token)match(input,INT,FOLLOW_INT_in_term946);  
                    stream_INT.add(INT86);


                    // AST REWRITE
                    // elements: INT
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 132:11: -> ^( T_INT INT )
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:132:14: ^( T_INT INT )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(T_INT, "T_INT")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_INT.nextNode()
                        );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:133:3: FLOAT
                    {
                    FLOAT87=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_term959);  
                    stream_FLOAT.add(FLOAT87);


                    // AST REWRITE
                    // elements: FLOAT
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 133:9: -> ^( T_FLOAT FLOAT )
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:133:12: ^( T_FLOAT FLOAT )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(T_FLOAT, "T_FLOAT")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_FLOAT.nextNode()
                        );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 3 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:134:3: STRING
                    {
                    STRING88=(Token)match(input,STRING,FOLLOW_STRING_in_term971);  
                    stream_STRING.add(STRING88);


                    // AST REWRITE
                    // elements: STRING
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 134:10: -> ^( T_STR STRING )
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:134:13: ^( T_STR STRING )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(T_STR, "T_STR")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_STRING.nextNode()
                        );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 4 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:135:3: UTF8
                    {
                    UTF889=(Token)match(input,UTF8,FOLLOW_UTF8_in_term983);  
                    stream_UTF8.add(UTF889);


                    // AST REWRITE
                    // elements: UTF8
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 135:8: -> ^( T_UTF8 UTF8 )
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:135:11: ^( T_UTF8 UTF8 )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(T_UTF8, "T_UTF8")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_UTF8.nextNode()
                        );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 5 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:136:3: dotname
                    {
                    pushFollow(FOLLOW_dotname_in_term996);
                    dotname90=dotname();

                    state._fsp--;

                    stream_dotname.add(dotname90.getTree());

                    // AST REWRITE
                    // elements: dotname
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 136:11: -> ^( T_VAR dotname )
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:136:14: ^( T_VAR dotname )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(T_VAR, "T_VAR")
                        , root_1);

                        adaptor.addChild(root_1, stream_dotname.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "term"


    public static class table_decl_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "table_decl"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:138:1: table_decl : ( ID ( '[' col_decl ']' )? '(' decls ')' ( table_opts )? DOT_END -> ^( DECL KIND1 ID ^( INDEX ( col_decl )? ) decls ^( OPTION ( table_opts )? ) ) | ID ( '[' col_decl ']' )? '(' '(' decls ')' ')' ( table_opts )? DOT_END -> ^( DECL KIND2 ID ^( INDEX ( col_decl )? ) decls ^( OPTION ( table_opts )? ) ) );
    public final SociaLiteParser.table_decl_return table_decl() throws RecognitionException {
        SociaLiteParser.table_decl_return retval = new SociaLiteParser.table_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token ID91=null;
        Token char_literal92=null;
        Token char_literal94=null;
        Token char_literal95=null;
        Token char_literal97=null;
        Token DOT_END99=null;
        Token ID100=null;
        Token char_literal101=null;
        Token char_literal103=null;
        Token char_literal104=null;
        Token char_literal105=null;
        Token char_literal107=null;
        Token char_literal108=null;
        Token DOT_END110=null;
        SociaLiteParser.col_decl_return col_decl93 =null;

        SociaLiteParser.decls_return decls96 =null;

        SociaLiteParser.table_opts_return table_opts98 =null;

        SociaLiteParser.col_decl_return col_decl102 =null;

        SociaLiteParser.decls_return decls106 =null;

        SociaLiteParser.table_opts_return table_opts109 =null;


        CommonTree ID91_tree=null;
        CommonTree char_literal92_tree=null;
        CommonTree char_literal94_tree=null;
        CommonTree char_literal95_tree=null;
        CommonTree char_literal97_tree=null;
        CommonTree DOT_END99_tree=null;
        CommonTree ID100_tree=null;
        CommonTree char_literal101_tree=null;
        CommonTree char_literal103_tree=null;
        CommonTree char_literal104_tree=null;
        CommonTree char_literal105_tree=null;
        CommonTree char_literal107_tree=null;
        CommonTree char_literal108_tree=null;
        CommonTree DOT_END110_tree=null;
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleTokenStream stream_DOT_END=new RewriteRuleTokenStream(adaptor,"token DOT_END");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_85=new RewriteRuleTokenStream(adaptor,"token 85");
        RewriteRuleSubtreeStream stream_table_opts=new RewriteRuleSubtreeStream(adaptor,"rule table_opts");
        RewriteRuleSubtreeStream stream_col_decl=new RewriteRuleSubtreeStream(adaptor,"rule col_decl");
        RewriteRuleSubtreeStream stream_decls=new RewriteRuleSubtreeStream(adaptor,"rule decls");
        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:138:11: ( ID ( '[' col_decl ']' )? '(' decls ')' ( table_opts )? DOT_END -> ^( DECL KIND1 ID ^( INDEX ( col_decl )? ) decls ^( OPTION ( table_opts )? ) ) | ID ( '[' col_decl ']' )? '(' '(' decls ')' ')' ( table_opts )? DOT_END -> ^( DECL KIND2 ID ^( INDEX ( col_decl )? ) decls ^( OPTION ( table_opts )? ) ) )
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==ID) ) {
                int LA30_1 = input.LA(2);

                if ( (LA30_1==85) ) {
                    switch ( input.LA(3) ) {
                    case 93:
                        {
                        int LA30_4 = input.LA(4);

                        if ( (LA30_4==85) ) {
                            int LA30_13 = input.LA(5);

                            if ( (LA30_13==86) ) {
                                int LA30_21 = input.LA(6);

                                if ( (LA30_21==ID) ) {
                                    int LA30_14 = input.LA(7);

                                    if ( (LA30_14==73) ) {
                                        int LA30_22 = input.LA(8);

                                        if ( (LA30_22==INT) ) {
                                            int LA30_30 = input.LA(9);

                                            if ( (LA30_30==71) ) {
                                                int LA30_32 = input.LA(10);

                                                if ( (LA30_32==INT) ) {
                                                    int LA30_33 = input.LA(11);

                                                    if ( (LA30_33==86) ) {
                                                        int LA30_23 = input.LA(12);

                                                        if ( (LA30_23==65) ) {
                                                            int LA30_3 = input.LA(13);

                                                            if ( (LA30_3==65) ) {
                                                                alt30=2;
                                                            }
                                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                                alt30=1;
                                                            }
                                                            else {
                                                                NoViableAltException nvae =
                                                                    new NoViableAltException("", 30, 3, input);

                                                                throw nvae;

                                                            }
                                                        }
                                                        else {
                                                            NoViableAltException nvae =
                                                                new NoViableAltException("", 30, 23, input);

                                                            throw nvae;

                                                        }
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 33, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 32, input);

                                                    throw nvae;

                                                }
                                            }
                                            else if ( (LA30_30==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 30, input);

                                                throw nvae;

                                            }
                                        }
                                        else if ( (LA30_22==ITER_DECL) ) {
                                            int LA30_31 = input.LA(9);

                                            if ( (LA30_31==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 31, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 22, input);

                                            throw nvae;

                                        }
                                    }
                                    else if ( (LA30_14==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 14, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 21, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 30, 13, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA30_4==ID) ) {
                            int LA30_14 = input.LA(5);

                            if ( (LA30_14==73) ) {
                                int LA30_22 = input.LA(6);

                                if ( (LA30_22==INT) ) {
                                    int LA30_30 = input.LA(7);

                                    if ( (LA30_30==71) ) {
                                        int LA30_32 = input.LA(8);

                                        if ( (LA30_32==INT) ) {
                                            int LA30_33 = input.LA(9);

                                            if ( (LA30_33==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 33, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 32, input);

                                            throw nvae;

                                        }
                                    }
                                    else if ( (LA30_30==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 30, input);

                                        throw nvae;

                                    }
                                }
                                else if ( (LA30_22==ITER_DECL) ) {
                                    int LA30_31 = input.LA(7);

                                    if ( (LA30_31==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 31, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 22, input);

                                    throw nvae;

                                }
                            }
                            else if ( (LA30_14==86) ) {
                                int LA30_23 = input.LA(6);

                                if ( (LA30_23==65) ) {
                                    int LA30_3 = input.LA(7);

                                    if ( (LA30_3==65) ) {
                                        alt30=2;
                                    }
                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                        alt30=1;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 3, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 23, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 30, 14, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 30, 4, input);

                            throw nvae;

                        }
                        }
                        break;
                    case 94:
                        {
                        int LA30_5 = input.LA(4);

                        if ( (LA30_5==85) ) {
                            int LA30_15 = input.LA(5);

                            if ( (LA30_15==86) ) {
                                int LA30_24 = input.LA(6);

                                if ( (LA30_24==ID) ) {
                                    int LA30_14 = input.LA(7);

                                    if ( (LA30_14==73) ) {
                                        int LA30_22 = input.LA(8);

                                        if ( (LA30_22==INT) ) {
                                            int LA30_30 = input.LA(9);

                                            if ( (LA30_30==71) ) {
                                                int LA30_32 = input.LA(10);

                                                if ( (LA30_32==INT) ) {
                                                    int LA30_33 = input.LA(11);

                                                    if ( (LA30_33==86) ) {
                                                        int LA30_23 = input.LA(12);

                                                        if ( (LA30_23==65) ) {
                                                            int LA30_3 = input.LA(13);

                                                            if ( (LA30_3==65) ) {
                                                                alt30=2;
                                                            }
                                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                                alt30=1;
                                                            }
                                                            else {
                                                                NoViableAltException nvae =
                                                                    new NoViableAltException("", 30, 3, input);

                                                                throw nvae;

                                                            }
                                                        }
                                                        else {
                                                            NoViableAltException nvae =
                                                                new NoViableAltException("", 30, 23, input);

                                                            throw nvae;

                                                        }
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 33, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 32, input);

                                                    throw nvae;

                                                }
                                            }
                                            else if ( (LA30_30==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 30, input);

                                                throw nvae;

                                            }
                                        }
                                        else if ( (LA30_22==ITER_DECL) ) {
                                            int LA30_31 = input.LA(9);

                                            if ( (LA30_31==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 31, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 22, input);

                                            throw nvae;

                                        }
                                    }
                                    else if ( (LA30_14==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 14, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 24, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 30, 15, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA30_5==ID) ) {
                            int LA30_14 = input.LA(5);

                            if ( (LA30_14==73) ) {
                                int LA30_22 = input.LA(6);

                                if ( (LA30_22==INT) ) {
                                    int LA30_30 = input.LA(7);

                                    if ( (LA30_30==71) ) {
                                        int LA30_32 = input.LA(8);

                                        if ( (LA30_32==INT) ) {
                                            int LA30_33 = input.LA(9);

                                            if ( (LA30_33==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 33, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 32, input);

                                            throw nvae;

                                        }
                                    }
                                    else if ( (LA30_30==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 30, input);

                                        throw nvae;

                                    }
                                }
                                else if ( (LA30_22==ITER_DECL) ) {
                                    int LA30_31 = input.LA(7);

                                    if ( (LA30_31==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 31, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 22, input);

                                    throw nvae;

                                }
                            }
                            else if ( (LA30_14==86) ) {
                                int LA30_23 = input.LA(6);

                                if ( (LA30_23==65) ) {
                                    int LA30_3 = input.LA(7);

                                    if ( (LA30_3==65) ) {
                                        alt30=2;
                                    }
                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                        alt30=1;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 3, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 23, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 30, 14, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 30, 5, input);

                            throw nvae;

                        }
                        }
                        break;
                    case 90:
                        {
                        int LA30_6 = input.LA(4);

                        if ( (LA30_6==85) ) {
                            int LA30_16 = input.LA(5);

                            if ( (LA30_16==86) ) {
                                int LA30_25 = input.LA(6);

                                if ( (LA30_25==ID) ) {
                                    int LA30_14 = input.LA(7);

                                    if ( (LA30_14==73) ) {
                                        int LA30_22 = input.LA(8);

                                        if ( (LA30_22==INT) ) {
                                            int LA30_30 = input.LA(9);

                                            if ( (LA30_30==71) ) {
                                                int LA30_32 = input.LA(10);

                                                if ( (LA30_32==INT) ) {
                                                    int LA30_33 = input.LA(11);

                                                    if ( (LA30_33==86) ) {
                                                        int LA30_23 = input.LA(12);

                                                        if ( (LA30_23==65) ) {
                                                            int LA30_3 = input.LA(13);

                                                            if ( (LA30_3==65) ) {
                                                                alt30=2;
                                                            }
                                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                                alt30=1;
                                                            }
                                                            else {
                                                                NoViableAltException nvae =
                                                                    new NoViableAltException("", 30, 3, input);

                                                                throw nvae;

                                                            }
                                                        }
                                                        else {
                                                            NoViableAltException nvae =
                                                                new NoViableAltException("", 30, 23, input);

                                                            throw nvae;

                                                        }
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 33, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 32, input);

                                                    throw nvae;

                                                }
                                            }
                                            else if ( (LA30_30==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 30, input);

                                                throw nvae;

                                            }
                                        }
                                        else if ( (LA30_22==ITER_DECL) ) {
                                            int LA30_31 = input.LA(9);

                                            if ( (LA30_31==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 31, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 22, input);

                                            throw nvae;

                                        }
                                    }
                                    else if ( (LA30_14==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 14, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 25, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 30, 16, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA30_6==ID) ) {
                            int LA30_14 = input.LA(5);

                            if ( (LA30_14==73) ) {
                                int LA30_22 = input.LA(6);

                                if ( (LA30_22==INT) ) {
                                    int LA30_30 = input.LA(7);

                                    if ( (LA30_30==71) ) {
                                        int LA30_32 = input.LA(8);

                                        if ( (LA30_32==INT) ) {
                                            int LA30_33 = input.LA(9);

                                            if ( (LA30_33==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 33, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 32, input);

                                            throw nvae;

                                        }
                                    }
                                    else if ( (LA30_30==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 30, input);

                                        throw nvae;

                                    }
                                }
                                else if ( (LA30_22==ITER_DECL) ) {
                                    int LA30_31 = input.LA(7);

                                    if ( (LA30_31==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 31, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 22, input);

                                    throw nvae;

                                }
                            }
                            else if ( (LA30_14==86) ) {
                                int LA30_23 = input.LA(6);

                                if ( (LA30_23==65) ) {
                                    int LA30_3 = input.LA(7);

                                    if ( (LA30_3==65) ) {
                                        alt30=2;
                                    }
                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                        alt30=1;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 3, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 23, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 30, 14, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 30, 6, input);

                            throw nvae;

                        }
                        }
                        break;
                    case 88:
                        {
                        int LA30_7 = input.LA(4);

                        if ( (LA30_7==85) ) {
                            int LA30_17 = input.LA(5);

                            if ( (LA30_17==86) ) {
                                int LA30_26 = input.LA(6);

                                if ( (LA30_26==ID) ) {
                                    int LA30_14 = input.LA(7);

                                    if ( (LA30_14==73) ) {
                                        int LA30_22 = input.LA(8);

                                        if ( (LA30_22==INT) ) {
                                            int LA30_30 = input.LA(9);

                                            if ( (LA30_30==71) ) {
                                                int LA30_32 = input.LA(10);

                                                if ( (LA30_32==INT) ) {
                                                    int LA30_33 = input.LA(11);

                                                    if ( (LA30_33==86) ) {
                                                        int LA30_23 = input.LA(12);

                                                        if ( (LA30_23==65) ) {
                                                            int LA30_3 = input.LA(13);

                                                            if ( (LA30_3==65) ) {
                                                                alt30=2;
                                                            }
                                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                                alt30=1;
                                                            }
                                                            else {
                                                                NoViableAltException nvae =
                                                                    new NoViableAltException("", 30, 3, input);

                                                                throw nvae;

                                                            }
                                                        }
                                                        else {
                                                            NoViableAltException nvae =
                                                                new NoViableAltException("", 30, 23, input);

                                                            throw nvae;

                                                        }
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 33, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 32, input);

                                                    throw nvae;

                                                }
                                            }
                                            else if ( (LA30_30==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 30, input);

                                                throw nvae;

                                            }
                                        }
                                        else if ( (LA30_22==ITER_DECL) ) {
                                            int LA30_31 = input.LA(9);

                                            if ( (LA30_31==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 31, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 22, input);

                                            throw nvae;

                                        }
                                    }
                                    else if ( (LA30_14==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 14, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 26, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 30, 17, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA30_7==ID) ) {
                            int LA30_14 = input.LA(5);

                            if ( (LA30_14==73) ) {
                                int LA30_22 = input.LA(6);

                                if ( (LA30_22==INT) ) {
                                    int LA30_30 = input.LA(7);

                                    if ( (LA30_30==71) ) {
                                        int LA30_32 = input.LA(8);

                                        if ( (LA30_32==INT) ) {
                                            int LA30_33 = input.LA(9);

                                            if ( (LA30_33==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 33, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 32, input);

                                            throw nvae;

                                        }
                                    }
                                    else if ( (LA30_30==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 30, input);

                                        throw nvae;

                                    }
                                }
                                else if ( (LA30_22==ITER_DECL) ) {
                                    int LA30_31 = input.LA(7);

                                    if ( (LA30_31==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 31, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 22, input);

                                    throw nvae;

                                }
                            }
                            else if ( (LA30_14==86) ) {
                                int LA30_23 = input.LA(6);

                                if ( (LA30_23==65) ) {
                                    int LA30_3 = input.LA(7);

                                    if ( (LA30_3==65) ) {
                                        alt30=2;
                                    }
                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                        alt30=1;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 3, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 23, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 30, 14, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 30, 7, input);

                            throw nvae;

                        }
                        }
                        break;
                    case 84:
                        {
                        int LA30_8 = input.LA(4);

                        if ( (LA30_8==85) ) {
                            int LA30_18 = input.LA(5);

                            if ( (LA30_18==86) ) {
                                int LA30_27 = input.LA(6);

                                if ( (LA30_27==ID) ) {
                                    int LA30_14 = input.LA(7);

                                    if ( (LA30_14==73) ) {
                                        int LA30_22 = input.LA(8);

                                        if ( (LA30_22==INT) ) {
                                            int LA30_30 = input.LA(9);

                                            if ( (LA30_30==71) ) {
                                                int LA30_32 = input.LA(10);

                                                if ( (LA30_32==INT) ) {
                                                    int LA30_33 = input.LA(11);

                                                    if ( (LA30_33==86) ) {
                                                        int LA30_23 = input.LA(12);

                                                        if ( (LA30_23==65) ) {
                                                            int LA30_3 = input.LA(13);

                                                            if ( (LA30_3==65) ) {
                                                                alt30=2;
                                                            }
                                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                                alt30=1;
                                                            }
                                                            else {
                                                                NoViableAltException nvae =
                                                                    new NoViableAltException("", 30, 3, input);

                                                                throw nvae;

                                                            }
                                                        }
                                                        else {
                                                            NoViableAltException nvae =
                                                                new NoViableAltException("", 30, 23, input);

                                                            throw nvae;

                                                        }
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 33, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 32, input);

                                                    throw nvae;

                                                }
                                            }
                                            else if ( (LA30_30==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 30, input);

                                                throw nvae;

                                            }
                                        }
                                        else if ( (LA30_22==ITER_DECL) ) {
                                            int LA30_31 = input.LA(9);

                                            if ( (LA30_31==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 31, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 22, input);

                                            throw nvae;

                                        }
                                    }
                                    else if ( (LA30_14==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 14, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 27, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 30, 18, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA30_8==ID) ) {
                            int LA30_14 = input.LA(5);

                            if ( (LA30_14==73) ) {
                                int LA30_22 = input.LA(6);

                                if ( (LA30_22==INT) ) {
                                    int LA30_30 = input.LA(7);

                                    if ( (LA30_30==71) ) {
                                        int LA30_32 = input.LA(8);

                                        if ( (LA30_32==INT) ) {
                                            int LA30_33 = input.LA(9);

                                            if ( (LA30_33==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 33, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 32, input);

                                            throw nvae;

                                        }
                                    }
                                    else if ( (LA30_30==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 30, input);

                                        throw nvae;

                                    }
                                }
                                else if ( (LA30_22==ITER_DECL) ) {
                                    int LA30_31 = input.LA(7);

                                    if ( (LA30_31==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 31, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 22, input);

                                    throw nvae;

                                }
                            }
                            else if ( (LA30_14==86) ) {
                                int LA30_23 = input.LA(6);

                                if ( (LA30_23==65) ) {
                                    int LA30_3 = input.LA(7);

                                    if ( (LA30_3==65) ) {
                                        alt30=2;
                                    }
                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                        alt30=1;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 3, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 23, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 30, 14, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 30, 8, input);

                            throw nvae;

                        }
                        }
                        break;
                    case 83:
                        {
                        int LA30_9 = input.LA(4);

                        if ( (LA30_9==85) ) {
                            int LA30_19 = input.LA(5);

                            if ( (LA30_19==86) ) {
                                int LA30_28 = input.LA(6);

                                if ( (LA30_28==ID) ) {
                                    int LA30_14 = input.LA(7);

                                    if ( (LA30_14==73) ) {
                                        int LA30_22 = input.LA(8);

                                        if ( (LA30_22==INT) ) {
                                            int LA30_30 = input.LA(9);

                                            if ( (LA30_30==71) ) {
                                                int LA30_32 = input.LA(10);

                                                if ( (LA30_32==INT) ) {
                                                    int LA30_33 = input.LA(11);

                                                    if ( (LA30_33==86) ) {
                                                        int LA30_23 = input.LA(12);

                                                        if ( (LA30_23==65) ) {
                                                            int LA30_3 = input.LA(13);

                                                            if ( (LA30_3==65) ) {
                                                                alt30=2;
                                                            }
                                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                                alt30=1;
                                                            }
                                                            else {
                                                                NoViableAltException nvae =
                                                                    new NoViableAltException("", 30, 3, input);

                                                                throw nvae;

                                                            }
                                                        }
                                                        else {
                                                            NoViableAltException nvae =
                                                                new NoViableAltException("", 30, 23, input);

                                                            throw nvae;

                                                        }
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 33, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 32, input);

                                                    throw nvae;

                                                }
                                            }
                                            else if ( (LA30_30==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 30, input);

                                                throw nvae;

                                            }
                                        }
                                        else if ( (LA30_22==ITER_DECL) ) {
                                            int LA30_31 = input.LA(9);

                                            if ( (LA30_31==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 31, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 22, input);

                                            throw nvae;

                                        }
                                    }
                                    else if ( (LA30_14==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 14, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 28, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 30, 19, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA30_9==ID) ) {
                            int LA30_14 = input.LA(5);

                            if ( (LA30_14==73) ) {
                                int LA30_22 = input.LA(6);

                                if ( (LA30_22==INT) ) {
                                    int LA30_30 = input.LA(7);

                                    if ( (LA30_30==71) ) {
                                        int LA30_32 = input.LA(8);

                                        if ( (LA30_32==INT) ) {
                                            int LA30_33 = input.LA(9);

                                            if ( (LA30_33==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 33, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 32, input);

                                            throw nvae;

                                        }
                                    }
                                    else if ( (LA30_30==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 30, input);

                                        throw nvae;

                                    }
                                }
                                else if ( (LA30_22==ITER_DECL) ) {
                                    int LA30_31 = input.LA(7);

                                    if ( (LA30_31==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 31, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 22, input);

                                    throw nvae;

                                }
                            }
                            else if ( (LA30_14==86) ) {
                                int LA30_23 = input.LA(6);

                                if ( (LA30_23==65) ) {
                                    int LA30_3 = input.LA(7);

                                    if ( (LA30_3==65) ) {
                                        alt30=2;
                                    }
                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                        alt30=1;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 3, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 23, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 30, 14, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 30, 9, input);

                            throw nvae;

                        }
                        }
                        break;
                    case ID:
                        {
                        int LA30_10 = input.LA(4);

                        if ( (LA30_10==85) ) {
                            int LA30_20 = input.LA(5);

                            if ( (LA30_20==86) ) {
                                int LA30_29 = input.LA(6);

                                if ( (LA30_29==ID) ) {
                                    int LA30_14 = input.LA(7);

                                    if ( (LA30_14==73) ) {
                                        int LA30_22 = input.LA(8);

                                        if ( (LA30_22==INT) ) {
                                            int LA30_30 = input.LA(9);

                                            if ( (LA30_30==71) ) {
                                                int LA30_32 = input.LA(10);

                                                if ( (LA30_32==INT) ) {
                                                    int LA30_33 = input.LA(11);

                                                    if ( (LA30_33==86) ) {
                                                        int LA30_23 = input.LA(12);

                                                        if ( (LA30_23==65) ) {
                                                            int LA30_3 = input.LA(13);

                                                            if ( (LA30_3==65) ) {
                                                                alt30=2;
                                                            }
                                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                                alt30=1;
                                                            }
                                                            else {
                                                                NoViableAltException nvae =
                                                                    new NoViableAltException("", 30, 3, input);

                                                                throw nvae;

                                                            }
                                                        }
                                                        else {
                                                            NoViableAltException nvae =
                                                                new NoViableAltException("", 30, 23, input);

                                                            throw nvae;

                                                        }
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 33, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 32, input);

                                                    throw nvae;

                                                }
                                            }
                                            else if ( (LA30_30==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 30, input);

                                                throw nvae;

                                            }
                                        }
                                        else if ( (LA30_22==ITER_DECL) ) {
                                            int LA30_31 = input.LA(9);

                                            if ( (LA30_31==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 31, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 22, input);

                                            throw nvae;

                                        }
                                    }
                                    else if ( (LA30_14==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 14, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 29, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 30, 20, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA30_10==ID) ) {
                            int LA30_14 = input.LA(5);

                            if ( (LA30_14==73) ) {
                                int LA30_22 = input.LA(6);

                                if ( (LA30_22==INT) ) {
                                    int LA30_30 = input.LA(7);

                                    if ( (LA30_30==71) ) {
                                        int LA30_32 = input.LA(8);

                                        if ( (LA30_32==INT) ) {
                                            int LA30_33 = input.LA(9);

                                            if ( (LA30_33==86) ) {
                                                int LA30_23 = input.LA(10);

                                                if ( (LA30_23==65) ) {
                                                    int LA30_3 = input.LA(11);

                                                    if ( (LA30_3==65) ) {
                                                        alt30=2;
                                                    }
                                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                        alt30=1;
                                                    }
                                                    else {
                                                        NoViableAltException nvae =
                                                            new NoViableAltException("", 30, 3, input);

                                                        throw nvae;

                                                    }
                                                }
                                                else {
                                                    NoViableAltException nvae =
                                                        new NoViableAltException("", 30, 23, input);

                                                    throw nvae;

                                                }
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 33, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 32, input);

                                            throw nvae;

                                        }
                                    }
                                    else if ( (LA30_30==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 30, input);

                                        throw nvae;

                                    }
                                }
                                else if ( (LA30_22==ITER_DECL) ) {
                                    int LA30_31 = input.LA(7);

                                    if ( (LA30_31==86) ) {
                                        int LA30_23 = input.LA(8);

                                        if ( (LA30_23==65) ) {
                                            int LA30_3 = input.LA(9);

                                            if ( (LA30_3==65) ) {
                                                alt30=2;
                                            }
                                            else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                                alt30=1;
                                            }
                                            else {
                                                NoViableAltException nvae =
                                                    new NoViableAltException("", 30, 3, input);

                                                throw nvae;

                                            }
                                        }
                                        else {
                                            NoViableAltException nvae =
                                                new NoViableAltException("", 30, 23, input);

                                            throw nvae;

                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 31, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 22, input);

                                    throw nvae;

                                }
                            }
                            else if ( (LA30_14==86) ) {
                                int LA30_23 = input.LA(6);

                                if ( (LA30_23==65) ) {
                                    int LA30_3 = input.LA(7);

                                    if ( (LA30_3==65) ) {
                                        alt30=2;
                                    }
                                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                                        alt30=1;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 30, 3, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 30, 23, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 30, 14, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 30, 10, input);

                            throw nvae;

                        }
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 30, 2, input);

                        throw nvae;

                    }

                }
                else if ( (LA30_1==65) ) {
                    int LA30_3 = input.LA(3);

                    if ( (LA30_3==65) ) {
                        alt30=2;
                    }
                    else if ( (LA30_3==ID||(LA30_3 >= 83 && LA30_3 <= 84)||LA30_3==88||LA30_3==90||(LA30_3 >= 93 && LA30_3 <= 94)) ) {
                        alt30=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 30, 3, input);

                        throw nvae;

                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 30, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;

            }
            switch (alt30) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:138:16: ID ( '[' col_decl ']' )? '(' decls ')' ( table_opts )? DOT_END
                    {
                    ID91=(Token)match(input,ID,FOLLOW_ID_in_table_decl1015);  
                    stream_ID.add(ID91);


                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:138:20: ( '[' col_decl ']' )?
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==85) ) {
                        alt26=1;
                    }
                    switch (alt26) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:138:21: '[' col_decl ']'
                            {
                            char_literal92=(Token)match(input,85,FOLLOW_85_in_table_decl1019);  
                            stream_85.add(char_literal92);


                            pushFollow(FOLLOW_col_decl_in_table_decl1021);
                            col_decl93=col_decl();

                            state._fsp--;

                            stream_col_decl.add(col_decl93.getTree());

                            char_literal94=(Token)match(input,86,FOLLOW_86_in_table_decl1023);  
                            stream_86.add(char_literal94);


                            }
                            break;

                    }


                    char_literal95=(Token)match(input,65,FOLLOW_65_in_table_decl1028);  
                    stream_65.add(char_literal95);


                    pushFollow(FOLLOW_decls_in_table_decl1030);
                    decls96=decls();

                    state._fsp--;

                    stream_decls.add(decls96.getTree());

                    char_literal97=(Token)match(input,66,FOLLOW_66_in_table_decl1031);  
                    stream_66.add(char_literal97);


                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:138:54: ( table_opts )?
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( ((LA27_0 >= 91 && LA27_0 <= 92)||(LA27_0 >= 96 && LA27_0 <= 99)) ) {
                        alt27=1;
                    }
                    switch (alt27) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:138:54: table_opts
                            {
                            pushFollow(FOLLOW_table_opts_in_table_decl1033);
                            table_opts98=table_opts();

                            state._fsp--;

                            stream_table_opts.add(table_opts98.getTree());

                            }
                            break;

                    }


                    DOT_END99=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_table_decl1036);  
                    stream_DOT_END.add(DOT_END99);


                    // AST REWRITE
                    // elements: decls, table_opts, col_decl, ID
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 138:75: -> ^( DECL KIND1 ID ^( INDEX ( col_decl )? ) decls ^( OPTION ( table_opts )? ) )
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:138:78: ^( DECL KIND1 ID ^( INDEX ( col_decl )? ) decls ^( OPTION ( table_opts )? ) )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(DECL, "DECL")
                        , root_1);

                        adaptor.addChild(root_1, 
                        (CommonTree)adaptor.create(KIND1, "KIND1")
                        );

                        adaptor.addChild(root_1, 
                        stream_ID.nextNode()
                        );

                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:138:95: ^( INDEX ( col_decl )? )
                        {
                        CommonTree root_2 = (CommonTree)adaptor.nil();
                        root_2 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(INDEX, "INDEX")
                        , root_2);

                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:138:103: ( col_decl )?
                        if ( stream_col_decl.hasNext() ) {
                            adaptor.addChild(root_2, stream_col_decl.nextTree());

                        }
                        stream_col_decl.reset();

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_1, stream_decls.nextTree());

                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:138:120: ^( OPTION ( table_opts )? )
                        {
                        CommonTree root_2 = (CommonTree)adaptor.nil();
                        root_2 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(OPTION, "OPTION")
                        , root_2);

                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:138:129: ( table_opts )?
                        if ( stream_table_opts.hasNext() ) {
                            adaptor.addChild(root_2, stream_table_opts.nextTree());

                        }
                        stream_table_opts.reset();

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:139:3: ID ( '[' col_decl ']' )? '(' '(' decls ')' ')' ( table_opts )? DOT_END
                    {
                    ID100=(Token)match(input,ID,FOLLOW_ID_in_table_decl1068);  
                    stream_ID.add(ID100);


                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:139:6: ( '[' col_decl ']' )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==85) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:139:7: '[' col_decl ']'
                            {
                            char_literal101=(Token)match(input,85,FOLLOW_85_in_table_decl1071);  
                            stream_85.add(char_literal101);


                            pushFollow(FOLLOW_col_decl_in_table_decl1073);
                            col_decl102=col_decl();

                            state._fsp--;

                            stream_col_decl.add(col_decl102.getTree());

                            char_literal103=(Token)match(input,86,FOLLOW_86_in_table_decl1075);  
                            stream_86.add(char_literal103);


                            }
                            break;

                    }


                    char_literal104=(Token)match(input,65,FOLLOW_65_in_table_decl1079);  
                    stream_65.add(char_literal104);


                    char_literal105=(Token)match(input,65,FOLLOW_65_in_table_decl1081);  
                    stream_65.add(char_literal105);


                    pushFollow(FOLLOW_decls_in_table_decl1083);
                    decls106=decls();

                    state._fsp--;

                    stream_decls.add(decls106.getTree());

                    char_literal107=(Token)match(input,66,FOLLOW_66_in_table_decl1084);  
                    stream_66.add(char_literal107);


                    char_literal108=(Token)match(input,66,FOLLOW_66_in_table_decl1086);  
                    stream_66.add(char_literal108);


                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:139:47: ( table_opts )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( ((LA29_0 >= 91 && LA29_0 <= 92)||(LA29_0 >= 96 && LA29_0 <= 99)) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:139:47: table_opts
                            {
                            pushFollow(FOLLOW_table_opts_in_table_decl1088);
                            table_opts109=table_opts();

                            state._fsp--;

                            stream_table_opts.add(table_opts109.getTree());

                            }
                            break;

                    }


                    DOT_END110=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_table_decl1091);  
                    stream_DOT_END.add(DOT_END110);


                    // AST REWRITE
                    // elements: col_decl, ID, table_opts, decls
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 139:68: -> ^( DECL KIND2 ID ^( INDEX ( col_decl )? ) decls ^( OPTION ( table_opts )? ) )
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:139:71: ^( DECL KIND2 ID ^( INDEX ( col_decl )? ) decls ^( OPTION ( table_opts )? ) )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(DECL, "DECL")
                        , root_1);

                        adaptor.addChild(root_1, 
                        (CommonTree)adaptor.create(KIND2, "KIND2")
                        );

                        adaptor.addChild(root_1, 
                        stream_ID.nextNode()
                        );

                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:139:88: ^( INDEX ( col_decl )? )
                        {
                        CommonTree root_2 = (CommonTree)adaptor.nil();
                        root_2 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(INDEX, "INDEX")
                        , root_2);

                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:139:96: ( col_decl )?
                        if ( stream_col_decl.hasNext() ) {
                            adaptor.addChild(root_2, stream_col_decl.nextTree());

                        }
                        stream_col_decl.reset();

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_1, stream_decls.nextTree());

                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:139:113: ^( OPTION ( table_opts )? )
                        {
                        CommonTree root_2 = (CommonTree)adaptor.nil();
                        root_2 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(OPTION, "OPTION")
                        , root_2);

                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:139:122: ( table_opts )?
                        if ( stream_table_opts.hasNext() ) {
                            adaptor.addChild(root_2, stream_table_opts.nextTree());

                        }
                        stream_table_opts.reset();

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "table_decl"


    public static class table_opts_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "table_opts"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:141:1: table_opts : t_opt ( ',' ! t_opt )* ;
    public final SociaLiteParser.table_opts_return table_opts() throws RecognitionException {
        SociaLiteParser.table_opts_return retval = new SociaLiteParser.table_opts_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal112=null;
        SociaLiteParser.t_opt_return t_opt111 =null;

        SociaLiteParser.t_opt_return t_opt113 =null;


        CommonTree char_literal112_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:141:12: ( t_opt ( ',' ! t_opt )* )
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:141:14: t_opt ( ',' ! t_opt )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_t_opt_in_table_opts1129);
            t_opt111=t_opt();

            state._fsp--;

            adaptor.addChild(root_0, t_opt111.getTree());

            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:141:20: ( ',' ! t_opt )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==69) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:141:21: ',' ! t_opt
            	    {
            	    char_literal112=(Token)match(input,69,FOLLOW_69_in_table_opts1132); 

            	    pushFollow(FOLLOW_t_opt_in_table_opts1135);
            	    t_opt113=t_opt();

            	    state._fsp--;

            	    adaptor.addChild(root_0, t_opt113.getTree());

            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "table_opts"


    public static class t_opt_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "t_opt"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:143:1: t_opt : ( 'sortby' col= ID (order= SORT_ORDER )? -> ^( SORT_BY $col ( $order)? ) | 'orderby' ID -> ^( ORDER_BY ID ) | 'indexby' ID -> ^( INDEX_BY ID ) | 'groupby' '(' INT ')' -> ^( GROUP_BY INT ) | 'predefined' -> PREDEFINED | 'multiset' -> MULTISET );
    public final SociaLiteParser.t_opt_return t_opt() throws RecognitionException {
        SociaLiteParser.t_opt_return retval = new SociaLiteParser.t_opt_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token col=null;
        Token order=null;
        Token string_literal114=null;
        Token string_literal115=null;
        Token ID116=null;
        Token string_literal117=null;
        Token ID118=null;
        Token string_literal119=null;
        Token char_literal120=null;
        Token INT121=null;
        Token char_literal122=null;
        Token string_literal123=null;
        Token string_literal124=null;

        CommonTree col_tree=null;
        CommonTree order_tree=null;
        CommonTree string_literal114_tree=null;
        CommonTree string_literal115_tree=null;
        CommonTree ID116_tree=null;
        CommonTree string_literal117_tree=null;
        CommonTree ID118_tree=null;
        CommonTree string_literal119_tree=null;
        CommonTree char_literal120_tree=null;
        CommonTree INT121_tree=null;
        CommonTree char_literal122_tree=null;
        CommonTree string_literal123_tree=null;
        CommonTree string_literal124_tree=null;
        RewriteRuleTokenStream stream_98=new RewriteRuleTokenStream(adaptor,"token 98");
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_97=new RewriteRuleTokenStream(adaptor,"token 97");
        RewriteRuleTokenStream stream_96=new RewriteRuleTokenStream(adaptor,"token 96");
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleTokenStream stream_92=new RewriteRuleTokenStream(adaptor,"token 92");
        RewriteRuleTokenStream stream_91=new RewriteRuleTokenStream(adaptor,"token 91");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleTokenStream stream_SORT_ORDER=new RewriteRuleTokenStream(adaptor,"token SORT_ORDER");
        RewriteRuleTokenStream stream_99=new RewriteRuleTokenStream(adaptor,"token 99");

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:143:7: ( 'sortby' col= ID (order= SORT_ORDER )? -> ^( SORT_BY $col ( $order)? ) | 'orderby' ID -> ^( ORDER_BY ID ) | 'indexby' ID -> ^( INDEX_BY ID ) | 'groupby' '(' INT ')' -> ^( GROUP_BY INT ) | 'predefined' -> PREDEFINED | 'multiset' -> MULTISET )
            int alt33=6;
            switch ( input.LA(1) ) {
            case 99:
                {
                alt33=1;
                }
                break;
            case 97:
                {
                alt33=2;
                }
                break;
            case 92:
                {
                alt33=3;
                }
                break;
            case 91:
                {
                alt33=4;
                }
                break;
            case 98:
                {
                alt33=5;
                }
                break;
            case 96:
                {
                alt33=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 33, 0, input);

                throw nvae;

            }

            switch (alt33) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:143:9: 'sortby' col= ID (order= SORT_ORDER )?
                    {
                    string_literal114=(Token)match(input,99,FOLLOW_99_in_t_opt1146);  
                    stream_99.add(string_literal114);


                    col=(Token)match(input,ID,FOLLOW_ID_in_t_opt1150);  
                    stream_ID.add(col);


                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:143:25: (order= SORT_ORDER )?
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==SORT_ORDER) ) {
                        alt32=1;
                    }
                    switch (alt32) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:143:26: order= SORT_ORDER
                            {
                            order=(Token)match(input,SORT_ORDER,FOLLOW_SORT_ORDER_in_t_opt1155);  
                            stream_SORT_ORDER.add(order);


                            }
                            break;

                    }


                    // AST REWRITE
                    // elements: order, col
                    // token labels: col, order
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_col=new RewriteRuleTokenStream(adaptor,"token col",col);
                    RewriteRuleTokenStream stream_order=new RewriteRuleTokenStream(adaptor,"token order",order);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 143:45: -> ^( SORT_BY $col ( $order)? )
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:143:48: ^( SORT_BY $col ( $order)? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(SORT_BY, "SORT_BY")
                        , root_1);

                        adaptor.addChild(root_1, stream_col.nextNode());

                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:143:64: ( $order)?
                        if ( stream_order.hasNext() ) {
                            adaptor.addChild(root_1, stream_order.nextNode());

                        }
                        stream_order.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:144:3: 'orderby' ID
                    {
                    string_literal115=(Token)match(input,97,FOLLOW_97_in_t_opt1174);  
                    stream_97.add(string_literal115);


                    ID116=(Token)match(input,ID,FOLLOW_ID_in_t_opt1176);  
                    stream_ID.add(ID116);


                    // AST REWRITE
                    // elements: ID
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 144:16: -> ^( ORDER_BY ID )
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:144:19: ^( ORDER_BY ID )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(ORDER_BY, "ORDER_BY")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_ID.nextNode()
                        );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 3 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:145:3: 'indexby' ID
                    {
                    string_literal117=(Token)match(input,92,FOLLOW_92_in_t_opt1188);  
                    stream_92.add(string_literal117);


                    ID118=(Token)match(input,ID,FOLLOW_ID_in_t_opt1190);  
                    stream_ID.add(ID118);


                    // AST REWRITE
                    // elements: ID
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 145:16: -> ^( INDEX_BY ID )
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:145:19: ^( INDEX_BY ID )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(INDEX_BY, "INDEX_BY")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_ID.nextNode()
                        );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 4 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:146:3: 'groupby' '(' INT ')'
                    {
                    string_literal119=(Token)match(input,91,FOLLOW_91_in_t_opt1202);  
                    stream_91.add(string_literal119);


                    char_literal120=(Token)match(input,65,FOLLOW_65_in_t_opt1204);  
                    stream_65.add(char_literal120);


                    INT121=(Token)match(input,INT,FOLLOW_INT_in_t_opt1206);  
                    stream_INT.add(INT121);


                    char_literal122=(Token)match(input,66,FOLLOW_66_in_t_opt1208);  
                    stream_66.add(char_literal122);


                    // AST REWRITE
                    // elements: INT
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 146:25: -> ^( GROUP_BY INT )
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:146:28: ^( GROUP_BY INT )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(GROUP_BY, "GROUP_BY")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_INT.nextNode()
                        );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 5 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:147:3: 'predefined'
                    {
                    string_literal123=(Token)match(input,98,FOLLOW_98_in_t_opt1220);  
                    stream_98.add(string_literal123);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 147:16: -> PREDEFINED
                    {
                        adaptor.addChild(root_0, 
                        (CommonTree)adaptor.create(PREDEFINED, "PREDEFINED")
                        );

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 6 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:148:3: 'multiset'
                    {
                    string_literal124=(Token)match(input,96,FOLLOW_96_in_t_opt1228);  
                    stream_96.add(string_literal124);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 148:14: -> MULTISET
                    {
                        adaptor.addChild(root_0, 
                        (CommonTree)adaptor.create(MULTISET, "MULTISET")
                        );

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "t_opt"


    public static class decls_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "decls"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:152:1: decls : col_decls ( ',' '(' decls ')' )? -> ^( COL_DECLS col_decls ^( DECL ( decls )? ) ) ;
    public final SociaLiteParser.decls_return decls() throws RecognitionException {
        SociaLiteParser.decls_return retval = new SociaLiteParser.decls_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal126=null;
        Token char_literal127=null;
        Token char_literal129=null;
        SociaLiteParser.col_decls_return col_decls125 =null;

        SociaLiteParser.decls_return decls128 =null;


        CommonTree char_literal126_tree=null;
        CommonTree char_literal127_tree=null;
        CommonTree char_literal129_tree=null;
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_69=new RewriteRuleTokenStream(adaptor,"token 69");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleSubtreeStream stream_col_decls=new RewriteRuleSubtreeStream(adaptor,"rule col_decls");
        RewriteRuleSubtreeStream stream_decls=new RewriteRuleSubtreeStream(adaptor,"rule decls");
        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:152:7: ( col_decls ( ',' '(' decls ')' )? -> ^( COL_DECLS col_decls ^( DECL ( decls )? ) ) )
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:152:8: col_decls ( ',' '(' decls ')' )?
            {
            pushFollow(FOLLOW_col_decls_in_decls1249);
            col_decls125=col_decls();

            state._fsp--;

            stream_col_decls.add(col_decls125.getTree());

            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:152:18: ( ',' '(' decls ')' )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==69) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:152:19: ',' '(' decls ')'
                    {
                    char_literal126=(Token)match(input,69,FOLLOW_69_in_decls1252);  
                    stream_69.add(char_literal126);


                    char_literal127=(Token)match(input,65,FOLLOW_65_in_decls1254);  
                    stream_65.add(char_literal127);


                    pushFollow(FOLLOW_decls_in_decls1256);
                    decls128=decls();

                    state._fsp--;

                    stream_decls.add(decls128.getTree());

                    char_literal129=(Token)match(input,66,FOLLOW_66_in_decls1258);  
                    stream_66.add(char_literal129);


                    }
                    break;

            }


            // AST REWRITE
            // elements: decls, col_decls
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 152:39: -> ^( COL_DECLS col_decls ^( DECL ( decls )? ) )
            {
                // /x/jiwon/workspace/socialite/grammar/SociaLite.g:152:42: ^( COL_DECLS col_decls ^( DECL ( decls )? ) )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(COL_DECLS, "COL_DECLS")
                , root_1);

                adaptor.addChild(root_1, stream_col_decls.nextTree());

                // /x/jiwon/workspace/socialite/grammar/SociaLite.g:152:64: ^( DECL ( decls )? )
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(DECL, "DECL")
                , root_2);

                // /x/jiwon/workspace/socialite/grammar/SociaLite.g:152:71: ( decls )?
                if ( stream_decls.hasNext() ) {
                    adaptor.addChild(root_2, stream_decls.nextTree());

                }
                stream_decls.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "decls"


    public static class col_decls_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "col_decls"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:154:1: col_decls : col_decl ( ',' ! col_decl )* ;
    public final SociaLiteParser.col_decls_return col_decls() throws RecognitionException {
        SociaLiteParser.col_decls_return retval = new SociaLiteParser.col_decls_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal131=null;
        SociaLiteParser.col_decl_return col_decl130 =null;

        SociaLiteParser.col_decl_return col_decl132 =null;


        CommonTree char_literal131_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:154:11: ( col_decl ( ',' ! col_decl )* )
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:154:12: col_decl ( ',' ! col_decl )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_col_decl_in_col_decls1284);
            col_decl130=col_decl();

            state._fsp--;

            adaptor.addChild(root_0, col_decl130.getTree());

            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:154:21: ( ',' ! col_decl )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==69) ) {
                    int LA35_1 = input.LA(2);

                    if ( (LA35_1==ID||(LA35_1 >= 83 && LA35_1 <= 84)||LA35_1==88||LA35_1==90||(LA35_1 >= 93 && LA35_1 <= 94)) ) {
                        alt35=1;
                    }


                }


                switch (alt35) {
            	case 1 :
            	    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:154:22: ',' ! col_decl
            	    {
            	    char_literal131=(Token)match(input,69,FOLLOW_69_in_col_decls1287); 

            	    pushFollow(FOLLOW_col_decl_in_col_decls1290);
            	    col_decl132=col_decl();

            	    state._fsp--;

            	    adaptor.addChild(root_0, col_decl132.getTree());

            	    }
            	    break;

            	default :
            	    break loop35;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "col_decls"


    public static class col_decl_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "col_decl"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:156:1: col_decl : type ID ( ':' col_opt )? -> ^( COL_DECL type ID ( col_opt )? ) ;
    public final SociaLiteParser.col_decl_return col_decl() throws RecognitionException {
        SociaLiteParser.col_decl_return retval = new SociaLiteParser.col_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token ID134=null;
        Token char_literal135=null;
        SociaLiteParser.type_return type133 =null;

        SociaLiteParser.col_opt_return col_opt136 =null;


        CommonTree ID134_tree=null;
        CommonTree char_literal135_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_73=new RewriteRuleTokenStream(adaptor,"token 73");
        RewriteRuleSubtreeStream stream_col_opt=new RewriteRuleSubtreeStream(adaptor,"rule col_opt");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:156:10: ( type ID ( ':' col_opt )? -> ^( COL_DECL type ID ( col_opt )? ) )
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:156:12: type ID ( ':' col_opt )?
            {
            pushFollow(FOLLOW_type_in_col_decl1301);
            type133=type();

            state._fsp--;

            stream_type.add(type133.getTree());

            ID134=(Token)match(input,ID,FOLLOW_ID_in_col_decl1303);  
            stream_ID.add(ID134);


            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:156:20: ( ':' col_opt )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==73) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:156:21: ':' col_opt
                    {
                    char_literal135=(Token)match(input,73,FOLLOW_73_in_col_decl1306);  
                    stream_73.add(char_literal135);


                    pushFollow(FOLLOW_col_opt_in_col_decl1308);
                    col_opt136=col_opt();

                    state._fsp--;

                    stream_col_opt.add(col_opt136.getTree());

                    }
                    break;

            }


            // AST REWRITE
            // elements: type, ID, col_opt
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 156:34: -> ^( COL_DECL type ID ( col_opt )? )
            {
                // /x/jiwon/workspace/socialite/grammar/SociaLite.g:156:37: ^( COL_DECL type ID ( col_opt )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(COL_DECL, "COL_DECL")
                , root_1);

                adaptor.addChild(root_1, stream_type.nextTree());

                adaptor.addChild(root_1, 
                stream_ID.nextNode()
                );

                // /x/jiwon/workspace/socialite/grammar/SociaLite.g:156:56: ( col_opt )?
                if ( stream_col_opt.hasNext() ) {
                    adaptor.addChild(root_1, stream_col_opt.nextTree());

                }
                stream_col_opt.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "col_decl"


    public static class col_opt_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "col_opt"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:158:1: col_opt : (i1= INT '..' i2= INT -> ^( RANGE $i1 $i2) |i1= INT -> ^( SIZE $i1) | ITER_DECL -> ITER );
    public final SociaLiteParser.col_opt_return col_opt() throws RecognitionException {
        SociaLiteParser.col_opt_return retval = new SociaLiteParser.col_opt_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token i1=null;
        Token i2=null;
        Token string_literal137=null;
        Token ITER_DECL138=null;

        CommonTree i1_tree=null;
        CommonTree i2_tree=null;
        CommonTree string_literal137_tree=null;
        CommonTree ITER_DECL138_tree=null;
        RewriteRuleTokenStream stream_ITER_DECL=new RewriteRuleTokenStream(adaptor,"token ITER_DECL");
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleTokenStream stream_71=new RewriteRuleTokenStream(adaptor,"token 71");

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:158:9: (i1= INT '..' i2= INT -> ^( RANGE $i1 $i2) |i1= INT -> ^( SIZE $i1) | ITER_DECL -> ITER )
            int alt37=3;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==INT) ) {
                int LA37_1 = input.LA(2);

                if ( (LA37_1==71) ) {
                    alt37=1;
                }
                else if ( (LA37_1==66||LA37_1==69||LA37_1==86) ) {
                    alt37=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 37, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA37_0==ITER_DECL) ) {
                alt37=3;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 37, 0, input);

                throw nvae;

            }
            switch (alt37) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:158:12: i1= INT '..' i2= INT
                    {
                    i1=(Token)match(input,INT,FOLLOW_INT_in_col_opt1334);  
                    stream_INT.add(i1);


                    string_literal137=(Token)match(input,71,FOLLOW_71_in_col_opt1336);  
                    stream_71.add(string_literal137);


                    i2=(Token)match(input,INT,FOLLOW_INT_in_col_opt1340);  
                    stream_INT.add(i2);


                    // AST REWRITE
                    // elements: i1, i2
                    // token labels: i2, i1
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_i2=new RewriteRuleTokenStream(adaptor,"token i2",i2);
                    RewriteRuleTokenStream stream_i1=new RewriteRuleTokenStream(adaptor,"token i1",i1);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 158:31: -> ^( RANGE $i1 $i2)
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:158:34: ^( RANGE $i1 $i2)
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(RANGE, "RANGE")
                        , root_1);

                        adaptor.addChild(root_1, stream_i1.nextNode());

                        adaptor.addChild(root_1, stream_i2.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:159:5: i1= INT
                    {
                    i1=(Token)match(input,INT,FOLLOW_INT_in_col_opt1360);  
                    stream_INT.add(i1);


                    // AST REWRITE
                    // elements: i1
                    // token labels: i1
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_i1=new RewriteRuleTokenStream(adaptor,"token i1",i1);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 159:12: -> ^( SIZE $i1)
                    {
                        // /x/jiwon/workspace/socialite/grammar/SociaLite.g:159:15: ^( SIZE $i1)
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(SIZE, "SIZE")
                        , root_1);

                        adaptor.addChild(root_1, stream_i1.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 3 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:160:4: ITER_DECL
                    {
                    ITER_DECL138=(Token)match(input,ITER_DECL,FOLLOW_ITER_DECL_in_col_opt1374);  
                    stream_ITER_DECL.add(ITER_DECL138);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 160:14: -> ITER
                    {
                        adaptor.addChild(root_0, 
                        (CommonTree)adaptor.create(ITER, "ITER")
                        );

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "col_opt"


    public static class type_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "type"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:163:1: type : ( 'int' ( '[' ']' )? | 'long' ( '[' ']' )? | 'float' ( '[' ']' )? | 'double' ( '[' ']' )? | 'String' ( '[' ']' )? | 'Object' ( '[' ']' )? | ID ( '[' ']' )? );
    public final SociaLiteParser.type_return type() throws RecognitionException {
        SociaLiteParser.type_return retval = new SociaLiteParser.type_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token string_literal139=null;
        Token char_literal140=null;
        Token char_literal141=null;
        Token string_literal142=null;
        Token char_literal143=null;
        Token char_literal144=null;
        Token string_literal145=null;
        Token char_literal146=null;
        Token char_literal147=null;
        Token string_literal148=null;
        Token char_literal149=null;
        Token char_literal150=null;
        Token string_literal151=null;
        Token char_literal152=null;
        Token char_literal153=null;
        Token string_literal154=null;
        Token char_literal155=null;
        Token char_literal156=null;
        Token ID157=null;
        Token char_literal158=null;
        Token char_literal159=null;

        CommonTree string_literal139_tree=null;
        CommonTree char_literal140_tree=null;
        CommonTree char_literal141_tree=null;
        CommonTree string_literal142_tree=null;
        CommonTree char_literal143_tree=null;
        CommonTree char_literal144_tree=null;
        CommonTree string_literal145_tree=null;
        CommonTree char_literal146_tree=null;
        CommonTree char_literal147_tree=null;
        CommonTree string_literal148_tree=null;
        CommonTree char_literal149_tree=null;
        CommonTree char_literal150_tree=null;
        CommonTree string_literal151_tree=null;
        CommonTree char_literal152_tree=null;
        CommonTree char_literal153_tree=null;
        CommonTree string_literal154_tree=null;
        CommonTree char_literal155_tree=null;
        CommonTree char_literal156_tree=null;
        CommonTree ID157_tree=null;
        CommonTree char_literal158_tree=null;
        CommonTree char_literal159_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:163:5: ( 'int' ( '[' ']' )? | 'long' ( '[' ']' )? | 'float' ( '[' ']' )? | 'double' ( '[' ']' )? | 'String' ( '[' ']' )? | 'Object' ( '[' ']' )? | ID ( '[' ']' )? )
            int alt45=7;
            switch ( input.LA(1) ) {
            case 93:
                {
                alt45=1;
                }
                break;
            case 94:
                {
                alt45=2;
                }
                break;
            case 90:
                {
                alt45=3;
                }
                break;
            case 88:
                {
                alt45=4;
                }
                break;
            case 84:
                {
                alt45=5;
                }
                break;
            case 83:
                {
                alt45=6;
                }
                break;
            case ID:
                {
                alt45=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;

            }

            switch (alt45) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:163:7: 'int' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal139=(Token)match(input,93,FOLLOW_93_in_type1387); 
                    string_literal139_tree = 
                    (CommonTree)adaptor.create(string_literal139)
                    ;
                    adaptor.addChild(root_0, string_literal139_tree);


                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:163:13: ( '[' ']' )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==85) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:163:14: '[' ']'
                            {
                            char_literal140=(Token)match(input,85,FOLLOW_85_in_type1390); 
                            char_literal140_tree = 
                            (CommonTree)adaptor.create(char_literal140)
                            ;
                            adaptor.addChild(root_0, char_literal140_tree);


                            char_literal141=(Token)match(input,86,FOLLOW_86_in_type1392); 
                            char_literal141_tree = 
                            (CommonTree)adaptor.create(char_literal141)
                            ;
                            adaptor.addChild(root_0, char_literal141_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:164:3: 'long' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal142=(Token)match(input,94,FOLLOW_94_in_type1398); 
                    string_literal142_tree = 
                    (CommonTree)adaptor.create(string_literal142)
                    ;
                    adaptor.addChild(root_0, string_literal142_tree);


                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:164:10: ( '[' ']' )?
                    int alt39=2;
                    int LA39_0 = input.LA(1);

                    if ( (LA39_0==85) ) {
                        alt39=1;
                    }
                    switch (alt39) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:164:11: '[' ']'
                            {
                            char_literal143=(Token)match(input,85,FOLLOW_85_in_type1401); 
                            char_literal143_tree = 
                            (CommonTree)adaptor.create(char_literal143)
                            ;
                            adaptor.addChild(root_0, char_literal143_tree);


                            char_literal144=(Token)match(input,86,FOLLOW_86_in_type1403); 
                            char_literal144_tree = 
                            (CommonTree)adaptor.create(char_literal144)
                            ;
                            adaptor.addChild(root_0, char_literal144_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:165:3: 'float' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal145=(Token)match(input,90,FOLLOW_90_in_type1411); 
                    string_literal145_tree = 
                    (CommonTree)adaptor.create(string_literal145)
                    ;
                    adaptor.addChild(root_0, string_literal145_tree);


                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:165:11: ( '[' ']' )?
                    int alt40=2;
                    int LA40_0 = input.LA(1);

                    if ( (LA40_0==85) ) {
                        alt40=1;
                    }
                    switch (alt40) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:165:12: '[' ']'
                            {
                            char_literal146=(Token)match(input,85,FOLLOW_85_in_type1414); 
                            char_literal146_tree = 
                            (CommonTree)adaptor.create(char_literal146)
                            ;
                            adaptor.addChild(root_0, char_literal146_tree);


                            char_literal147=(Token)match(input,86,FOLLOW_86_in_type1416); 
                            char_literal147_tree = 
                            (CommonTree)adaptor.create(char_literal147)
                            ;
                            adaptor.addChild(root_0, char_literal147_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:166:3: 'double' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal148=(Token)match(input,88,FOLLOW_88_in_type1424); 
                    string_literal148_tree = 
                    (CommonTree)adaptor.create(string_literal148)
                    ;
                    adaptor.addChild(root_0, string_literal148_tree);


                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:166:12: ( '[' ']' )?
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==85) ) {
                        alt41=1;
                    }
                    switch (alt41) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:166:13: '[' ']'
                            {
                            char_literal149=(Token)match(input,85,FOLLOW_85_in_type1427); 
                            char_literal149_tree = 
                            (CommonTree)adaptor.create(char_literal149)
                            ;
                            adaptor.addChild(root_0, char_literal149_tree);


                            char_literal150=(Token)match(input,86,FOLLOW_86_in_type1429); 
                            char_literal150_tree = 
                            (CommonTree)adaptor.create(char_literal150)
                            ;
                            adaptor.addChild(root_0, char_literal150_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:167:3: 'String' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal151=(Token)match(input,84,FOLLOW_84_in_type1437); 
                    string_literal151_tree = 
                    (CommonTree)adaptor.create(string_literal151)
                    ;
                    adaptor.addChild(root_0, string_literal151_tree);


                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:167:12: ( '[' ']' )?
                    int alt42=2;
                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==85) ) {
                        alt42=1;
                    }
                    switch (alt42) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:167:13: '[' ']'
                            {
                            char_literal152=(Token)match(input,85,FOLLOW_85_in_type1440); 
                            char_literal152_tree = 
                            (CommonTree)adaptor.create(char_literal152)
                            ;
                            adaptor.addChild(root_0, char_literal152_tree);


                            char_literal153=(Token)match(input,86,FOLLOW_86_in_type1442); 
                            char_literal153_tree = 
                            (CommonTree)adaptor.create(char_literal153)
                            ;
                            adaptor.addChild(root_0, char_literal153_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 6 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:168:3: 'Object' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal154=(Token)match(input,83,FOLLOW_83_in_type1450); 
                    string_literal154_tree = 
                    (CommonTree)adaptor.create(string_literal154)
                    ;
                    adaptor.addChild(root_0, string_literal154_tree);


                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:168:12: ( '[' ']' )?
                    int alt43=2;
                    int LA43_0 = input.LA(1);

                    if ( (LA43_0==85) ) {
                        alt43=1;
                    }
                    switch (alt43) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:168:13: '[' ']'
                            {
                            char_literal155=(Token)match(input,85,FOLLOW_85_in_type1453); 
                            char_literal155_tree = 
                            (CommonTree)adaptor.create(char_literal155)
                            ;
                            adaptor.addChild(root_0, char_literal155_tree);


                            char_literal156=(Token)match(input,86,FOLLOW_86_in_type1455); 
                            char_literal156_tree = 
                            (CommonTree)adaptor.create(char_literal156)
                            ;
                            adaptor.addChild(root_0, char_literal156_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 7 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:169:4: ID ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    ID157=(Token)match(input,ID,FOLLOW_ID_in_type1462); 
                    ID157_tree = 
                    (CommonTree)adaptor.create(ID157)
                    ;
                    adaptor.addChild(root_0, ID157_tree);


                    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:169:7: ( '[' ']' )?
                    int alt44=2;
                    int LA44_0 = input.LA(1);

                    if ( (LA44_0==85) ) {
                        alt44=1;
                    }
                    switch (alt44) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:169:8: '[' ']'
                            {
                            char_literal158=(Token)match(input,85,FOLLOW_85_in_type1465); 
                            char_literal158_tree = 
                            (CommonTree)adaptor.create(char_literal158)
                            ;
                            adaptor.addChild(root_0, char_literal158_tree);


                            char_literal159=(Token)match(input,86,FOLLOW_86_in_type1467); 
                            char_literal159_tree = 
                            (CommonTree)adaptor.create(char_literal159)
                            ;
                            adaptor.addChild(root_0, char_literal159_tree);


                            }
                            break;

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "type"


    public static class dotname_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "dotname"
    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:171:1: dotname : ID ( DOT_ID )* ;
    public final SociaLiteParser.dotname_return dotname() throws RecognitionException {
        SociaLiteParser.dotname_return retval = new SociaLiteParser.dotname_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token ID160=null;
        Token DOT_ID161=null;

        CommonTree ID160_tree=null;
        CommonTree DOT_ID161_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:171:9: ( ID ( DOT_ID )* )
            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:171:10: ID ( DOT_ID )*
            {
            root_0 = (CommonTree)adaptor.nil();


            ID160=(Token)match(input,ID,FOLLOW_ID_in_dotname1479); 
            ID160_tree = 
            (CommonTree)adaptor.create(ID160)
            ;
            adaptor.addChild(root_0, ID160_tree);


            // /x/jiwon/workspace/socialite/grammar/SociaLite.g:171:13: ( DOT_ID )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==DOT_ID) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // /x/jiwon/workspace/socialite/grammar/SociaLite.g:171:14: DOT_ID
            	    {
            	    DOT_ID161=(Token)match(input,DOT_ID,FOLLOW_DOT_ID_in_dotname1482); 
            	    DOT_ID161_tree = 
            	    (CommonTree)adaptor.create(DOT_ID161)
            	    ;
            	    adaptor.addChild(root_0, DOT_ID161_tree);


            	    }
            	    break;

            	default :
            	    break loop46;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "dotname"

    // Delegated rules


    protected DFA9 dfa9 = new DFA9(this);
    static final String DFA9_eotS =
        "\6\uffff";
    static final String DFA9_eofS =
        "\6\uffff";
    static final String DFA9_minS =
        "\1\24\1\uffff\1\24\2\16\1\uffff";
    static final String DFA9_maxS =
        "\1\106\1\uffff\1\136\2\137\1\uffff";
    static final String DFA9_acceptS =
        "\1\uffff\1\1\3\uffff\1\2";
    static final String DFA9_specialS =
        "\6\uffff}>";
    static final String[] DFA9_transitionS = {
            "\1\1\6\uffff\1\1\2\uffff\1\1\24\uffff\1\1\11\uffff\1\1\2\uffff"+
            "\1\1\1\2\4\uffff\1\1",
            "",
            "\1\1\6\uffff\1\3\2\uffff\1\1\24\uffff\1\1\11\uffff\1\1\2\uffff"+
            "\2\1\4\uffff\1\1\14\uffff\2\1\3\uffff\1\1\1\uffff\1\1\2\uffff"+
            "\2\1",
            "\1\4\63\uffff\3\1\1\5\1\1\1\uffff\1\1\14\uffff\1\1\11\uffff"+
            "\1\1",
            "\1\4\63\uffff\3\1\1\5\1\1\1\uffff\1\1\26\uffff\1\1",
            ""
    };

    static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
    static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
    static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
    static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
    static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
    static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
    static final short[][] DFA9_transition;

    static {
        int numStates = DFA9_transitionS.length;
        DFA9_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
        }
    }

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = DFA9_eot;
            this.eof = DFA9_eof;
            this.min = DFA9_min;
            this.max = DFA9_max;
            this.accept = DFA9_accept;
            this.special = DFA9_special;
            this.transition = DFA9_transition;
        }
        public String getDescription() {
            return "110:1: expr : ( simpleExpr ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^) simpleExpr | varlist '=' ( cast )? function -> ^( MULTI_ASSIGN varlist function ( cast )? ) );";
        }
    }
 

    public static final BitSet FOLLOW_stat_in_prog353 = new BitSet(new long[]{0x0000000008000000L,0x0000000002840000L});
    public static final BitSet FOLLOW_EOF_in_prog356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_decl_in_stat362 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule_in_stat366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_query_in_stat370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_stmt_in_stat374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_87_in_table_stmt383 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_ID_in_table_stmt385 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_DOT_END_in_table_stmt387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_table_stmt400 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_ID_in_table_stmt402 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_DOT_END_in_table_stmt404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_query422 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_predicate_in_query424 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_DOT_END_in_query426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_predicate_in_rule447 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_rule449 = new BitSet(new long[]{0x2008002048100000L,0x0000000000000043L});
    public static final BitSet FOLLOW_litlist_in_rule453 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000800L});
    public static final BitSet FOLLOW_DOT_END_in_rule473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_rule478 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_rule480 = new BitSet(new long[]{0x2008002048100000L,0x0000000000000043L});
    public static final BitSet FOLLOW_litlist_in_rule484 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_DOT_END_in_rule486 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_litlist534 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_litlist537 = new BitSet(new long[]{0x2008002048100000L,0x0000000000000043L});
    public static final BitSet FOLLOW_literal_in_litlist540 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_predicate_in_literal549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_literal563 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_predicate_in_literal565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_literal579 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleExpr_in_expr596 = new BitSet(new long[]{0x8000000000000000L,0x000000000003F000L});
    public static final BitSet FOLLOW_76_in_expr600 = new BitSet(new long[]{0x2008000048100000L,0x0000000000000043L});
    public static final BitSet FOLLOW_80_in_expr605 = new BitSet(new long[]{0x2008000048100000L,0x0000000000000043L});
    public static final BitSet FOLLOW_78_in_expr610 = new BitSet(new long[]{0x2008000048100000L,0x0000000000000043L});
    public static final BitSet FOLLOW_63_in_expr614 = new BitSet(new long[]{0x2008000048100000L,0x0000000000000043L});
    public static final BitSet FOLLOW_79_in_expr619 = new BitSet(new long[]{0x2008000048100000L,0x0000000000000043L});
    public static final BitSet FOLLOW_81_in_expr624 = new BitSet(new long[]{0x2008000048100000L,0x0000000000000043L});
    public static final BitSet FOLLOW_77_in_expr629 = new BitSet(new long[]{0x2008000048100000L,0x0000000000000043L});
    public static final BitSet FOLLOW_simpleExpr_in_expr634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varlist_in_expr639 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_expr641 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_cast_in_expr643 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_function_in_expr646 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multExpr_in_simpleExpr668 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000050L});
    public static final BitSet FOLLOW_68_in_simpleExpr673 = new BitSet(new long[]{0x2008000048100000L,0x0000000000000043L});
    public static final BitSet FOLLOW_70_in_simpleExpr676 = new BitSet(new long[]{0x2008000048100000L,0x0000000000000043L});
    public static final BitSet FOLLOW_multExpr_in_simpleExpr680 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000050L});
    public static final BitSet FOLLOW_exprValue_in_multExpr691 = new BitSet(new long[]{0x0000000000000002L,0x0000000080000108L});
    public static final BitSet FOLLOW_67_in_multExpr695 = new BitSet(new long[]{0x2008000048100000L,0x0000000000000043L});
    public static final BitSet FOLLOW_72_in_multExpr698 = new BitSet(new long[]{0x2008000048100000L,0x0000000000000043L});
    public static final BitSet FOLLOW_95_in_multExpr701 = new BitSet(new long[]{0x2008000048100000L,0x0000000000000043L});
    public static final BitSet FOLLOW_exprValue_in_multExpr705 = new BitSet(new long[]{0x0000000000000002L,0x0000000080000108L});
    public static final BitSet FOLLOW_70_in_exprValue716 = new BitSet(new long[]{0x2008000048100000L,0x0000000000000002L});
    public static final BitSet FOLLOW_cast_in_exprValue720 = new BitSet(new long[]{0x2008000048100000L});
    public static final BitSet FOLLOW_term_in_exprValue723 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cast_in_exprValue743 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_function_in_exprValue746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cast_in_exprValue762 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_compExpr_in_exprValue765 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_compExpr784 = new BitSet(new long[]{0x2008000048100000L,0x0000000000000043L});
    public static final BitSet FOLLOW_simpleExpr_in_compExpr787 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_compExpr789 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_cast798 = new BitSet(new long[]{0x0000000008000000L,0x0000000065180000L});
    public static final BitSet FOLLOW_type_in_cast800 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_cast802 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_varlist814 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_dotname_in_varlist819 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_varlist823 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_dotname_in_varlist828 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_66_in_varlist832 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_64_in_function840 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_dotname_in_function842 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_function844 = new BitSet(new long[]{0x2008000048100000L,0x0000000000000047L});
    public static final BitSet FOLLOW_fparamlist_in_function846 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_function849 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_predicate868 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200002L});
    public static final BitSet FOLLOW_85_in_predicate871 = new BitSet(new long[]{0x2008000048100000L,0x0000000000000043L});
    public static final BitSet FOLLOW_param_in_predicate873 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_predicate875 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_predicate879 = new BitSet(new long[]{0x2008000048100000L,0x0000000000000047L});
    public static final BitSet FOLLOW_paramlist_in_predicate881 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_predicate884 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_param_in_paramlist904 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_paramlist907 = new BitSet(new long[]{0x2008000048100000L,0x0000000000000043L});
    public static final BitSet FOLLOW_param_in_paramlist910 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_fparam_in_fparamlist918 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_fparamlist921 = new BitSet(new long[]{0x2008000048100000L,0x0000000000000043L});
    public static final BitSet FOLLOW_fparam_in_fparamlist924 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_simpleExpr_in_param933 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleExpr_in_fparam939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_term946 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_term959 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_term971 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UTF8_in_term983 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dotname_in_term996 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_table_decl1015 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200002L});
    public static final BitSet FOLLOW_85_in_table_decl1019 = new BitSet(new long[]{0x0000000008000000L,0x0000000065180000L});
    public static final BitSet FOLLOW_col_decl_in_table_decl1021 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_table_decl1023 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_table_decl1028 = new BitSet(new long[]{0x0000000008000000L,0x0000000065180000L});
    public static final BitSet FOLLOW_decls_in_table_decl1030 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_table_decl1031 = new BitSet(new long[]{0x0000000000002000L,0x0000000F18000000L});
    public static final BitSet FOLLOW_table_opts_in_table_decl1033 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_DOT_END_in_table_decl1036 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_table_decl1068 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200002L});
    public static final BitSet FOLLOW_85_in_table_decl1071 = new BitSet(new long[]{0x0000000008000000L,0x0000000065180000L});
    public static final BitSet FOLLOW_col_decl_in_table_decl1073 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_table_decl1075 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_table_decl1079 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_table_decl1081 = new BitSet(new long[]{0x0000000008000000L,0x0000000065180000L});
    public static final BitSet FOLLOW_decls_in_table_decl1083 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_table_decl1084 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_table_decl1086 = new BitSet(new long[]{0x0000000000002000L,0x0000000F18000000L});
    public static final BitSet FOLLOW_table_opts_in_table_decl1088 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_DOT_END_in_table_decl1091 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_t_opt_in_table_opts1129 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_table_opts1132 = new BitSet(new long[]{0x0000000000000000L,0x0000000F18000000L});
    public static final BitSet FOLLOW_t_opt_in_table_opts1135 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_99_in_t_opt1146 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_ID_in_t_opt1150 = new BitSet(new long[]{0x0004000000000002L});
    public static final BitSet FOLLOW_SORT_ORDER_in_t_opt1155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_97_in_t_opt1174 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_ID_in_t_opt1176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_t_opt1188 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_ID_in_t_opt1190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_91_in_t_opt1202 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_t_opt1204 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_INT_in_t_opt1206 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_t_opt1208 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_t_opt1220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_t_opt1228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_col_decls_in_decls1249 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_decls1252 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_decls1254 = new BitSet(new long[]{0x0000000008000000L,0x0000000065180000L});
    public static final BitSet FOLLOW_decls_in_decls1256 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_decls1258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_col_decl_in_col_decls1284 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_col_decls1287 = new BitSet(new long[]{0x0000000008000000L,0x0000000065180000L});
    public static final BitSet FOLLOW_col_decl_in_col_decls1290 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_type_in_col_decl1301 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_ID_in_col_decl1303 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_col_decl1306 = new BitSet(new long[]{0x0000000140000000L});
    public static final BitSet FOLLOW_col_opt_in_col_decl1308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_col_opt1334 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_col_opt1336 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_INT_in_col_opt1340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_col_opt1360 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ITER_DECL_in_col_opt1374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_type1387 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type1390 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1392 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_type1398 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type1401 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1403 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_type1411 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type1414 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_type1424 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type1427 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_type1437 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type1440 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1442 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_type1450 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type1453 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_type1462 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type1465 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1467 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_dotname1479 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_DOT_ID_in_dotname1482 = new BitSet(new long[]{0x0000000000004002L});

}