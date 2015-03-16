// $ANTLR 3.4 /Users/jiwon/workspace/socialite/grammar/SociaLite.g 2015-03-14 03:39:40

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "APPROX", "BODY", "CHAR", "CLEAR", "COL_DECL", "COL_DECLS", "COMMENT", "COMPOUND_EXPR", "CONCURRENT", "DECL", "DOT_END", "DOT_ID", "DROP", "ESC_SEQ", "EXPONENT", "EXPR", "FALSE", "FLOAT", "FUNC", "FUNCTION", "FUNC_ARG", "GROUP_BY", "HEAD", "HEX_DIGIT", "ID", "INDEX", "INDEX_BY", "INT", "ITER", "ITER_DECL", "KIND1", "KIND2", "MULTISET", "MULTI_ASSIGN", "NOT", "OCTAL_ESC", "OPT", "OPTION", "ORDER_BY", "PREDEFINED", "PREDICATE", "PROG", "QUERY", "RANGE", "RULE", "RULES", "SORT_BY", "SORT_ORDER", "STRING", "TABLE_OPT", "TERM", "TRUE", "T_FLOAT", "T_INT", "T_STR", "T_UTF8", "T_VAR", "UNICODE_ESC", "UTF8", "WS", "'!='", "'$'", "'('", "')'", "'*'", "'+'", "','", "'-'", "'..'", "'/'", "':'", "':-'", "';'", "'<'", "'<='", "'='", "'=='", "'>'", "'>='", "'?-'", "'Object'", "'String'", "'['", "']'", "'clear'", "'concurrent'", "'double'", "'drop'", "'float'", "'groupby'", "'indexby'", "'int'", "'long'", "'mod'", "'multiset'", "'orderby'", "'predefined'", "'sortby'"
    };

    public static final int EOF=-1;
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
    public static final int T__100=100;
    public static final int T__101=101;
    public static final int APPROX=4;
    public static final int BODY=5;
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
    public static final int FALSE=20;
    public static final int FLOAT=21;
    public static final int FUNC=22;
    public static final int FUNCTION=23;
    public static final int FUNC_ARG=24;
    public static final int GROUP_BY=25;
    public static final int HEAD=26;
    public static final int HEX_DIGIT=27;
    public static final int ID=28;
    public static final int INDEX=29;
    public static final int INDEX_BY=30;
    public static final int INT=31;
    public static final int ITER=32;
    public static final int ITER_DECL=33;
    public static final int KIND1=34;
    public static final int KIND2=35;
    public static final int MULTISET=36;
    public static final int MULTI_ASSIGN=37;
    public static final int NOT=38;
    public static final int OCTAL_ESC=39;
    public static final int OPT=40;
    public static final int OPTION=41;
    public static final int ORDER_BY=42;
    public static final int PREDEFINED=43;
    public static final int PREDICATE=44;
    public static final int PROG=45;
    public static final int QUERY=46;
    public static final int RANGE=47;
    public static final int RULE=48;
    public static final int RULES=49;
    public static final int SORT_BY=50;
    public static final int SORT_ORDER=51;
    public static final int STRING=52;
    public static final int TABLE_OPT=53;
    public static final int TERM=54;
    public static final int TRUE=55;
    public static final int T_FLOAT=56;
    public static final int T_INT=57;
    public static final int T_STR=58;
    public static final int T_UTF8=59;
    public static final int T_VAR=60;
    public static final int UNICODE_ESC=61;
    public static final int UTF8=62;
    public static final int WS=63;

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
    public String getGrammarFileName() { return "/Users/jiwon/workspace/socialite/grammar/SociaLite.g"; }


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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:88:1: prog : ( stat )+ EOF ;
    public final SociaLiteParser.prog_return prog() throws RecognitionException {
        SociaLiteParser.prog_return retval = new SociaLiteParser.prog_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token EOF2=null;
        SociaLiteParser.stat_return stat1 =null;


        CommonTree EOF2_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:88:6: ( ( stat )+ EOF )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:88:7: ( stat )+ EOF
            {
            root_0 = (CommonTree)adaptor.nil();


            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:88:7: ( stat )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==ID||LA1_0==83||LA1_0==88||LA1_0==91) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:88:7: stat
            	    {
            	    pushFollow(FOLLOW_stat_in_prog360);
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


            EOF2=(Token)match(input,EOF,FOLLOW_EOF_in_prog363); 
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:89:1: stat : ( table_decl | rule | query | table_stmt );
    public final SociaLiteParser.stat_return stat() throws RecognitionException {
        SociaLiteParser.stat_return retval = new SociaLiteParser.stat_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SociaLiteParser.table_decl_return table_decl3 =null;

        SociaLiteParser.rule_return rule4 =null;

        SociaLiteParser.query_return query5 =null;

        SociaLiteParser.table_stmt_return table_stmt6 =null;



        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:89:6: ( table_decl | rule | query | table_stmt )
            int alt2=4;
            switch ( input.LA(1) ) {
            case ID:
                {
                int LA2_1 = input.LA(2);

                if ( (LA2_1==66) ) {
                    switch ( input.LA(3) ) {
                    case 84:
                    case 85:
                    case 90:
                    case 92:
                    case 95:
                    case 96:
                        {
                        alt2=1;
                        }
                        break;
                    case ID:
                        {
                        int LA2_6 = input.LA(4);

                        if ( (LA2_6==ID||LA2_6==86) ) {
                            alt2=1;
                        }
                        else if ( (LA2_6==DOT_ID||(LA2_6 >= 67 && LA2_6 <= 71)||LA2_6==73||LA2_6==97) ) {
                            alt2=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 2, 6, input);

                            throw nvae;

                        }
                        }
                        break;
                    case FLOAT:
                    case INT:
                    case STRING:
                    case UTF8:
                    case 65:
                    case 66:
                    case 71:
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
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;

                }
                }
                break;
            case 83:
                {
                alt2=3;
                }
                break;
            case 88:
            case 91:
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:89:7: table_decl
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_table_decl_in_stat369);
                    table_decl3=table_decl();

                    state._fsp--;

                    adaptor.addChild(root_0, table_decl3.getTree());

                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:90:3: rule
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_rule_in_stat373);
                    rule4=rule();

                    state._fsp--;

                    adaptor.addChild(root_0, rule4.getTree());

                    }
                    break;
                case 3 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:91:3: query
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_query_in_stat377);
                    query5=query();

                    state._fsp--;

                    adaptor.addChild(root_0, query5.getTree());

                    }
                    break;
                case 4 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:92:3: table_stmt
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_table_stmt_in_stat381);
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:94:1: table_stmt : ( 'clear' ID DOT_END -> ^( CLEAR ID ) | 'drop' ID DOT_END -> ^( DROP ( ID )? ) );
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
        RewriteRuleTokenStream stream_91=new RewriteRuleTokenStream(adaptor,"token 91");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_DOT_END=new RewriteRuleTokenStream(adaptor,"token DOT_END");
        RewriteRuleTokenStream stream_88=new RewriteRuleTokenStream(adaptor,"token 88");

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:94:12: ( 'clear' ID DOT_END -> ^( CLEAR ID ) | 'drop' ID DOT_END -> ^( DROP ( ID )? ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==88) ) {
                alt3=1;
            }
            else if ( (LA3_0==91) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;

            }
            switch (alt3) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:94:14: 'clear' ID DOT_END
                    {
                    string_literal7=(Token)match(input,88,FOLLOW_88_in_table_stmt390);  
                    stream_88.add(string_literal7);


                    ID8=(Token)match(input,ID,FOLLOW_ID_in_table_stmt392);  
                    stream_ID.add(ID8);


                    DOT_END9=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_table_stmt394);  
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
                    // 94:33: -> ^( CLEAR ID )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:94:36: ^( CLEAR ID )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:95:3: 'drop' ID DOT_END
                    {
                    string_literal10=(Token)match(input,91,FOLLOW_91_in_table_stmt407);  
                    stream_91.add(string_literal10);


                    ID11=(Token)match(input,ID,FOLLOW_ID_in_table_stmt409);  
                    stream_ID.add(ID11);


                    DOT_END12=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_table_stmt411);  
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
                    // 95:21: -> ^( DROP ( ID )? )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:95:24: ^( DROP ( ID )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(DROP, "DROP")
                        , root_1);

                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:95:31: ( ID )?
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:98:1: query : '?-' predicate DOT_END -> ^( QUERY predicate ) ;
    public final SociaLiteParser.query_return query() throws RecognitionException {
        SociaLiteParser.query_return retval = new SociaLiteParser.query_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token string_literal13=null;
        Token DOT_END15=null;
        SociaLiteParser.predicate_return predicate14 =null;


        CommonTree string_literal13_tree=null;
        CommonTree DOT_END15_tree=null;
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_DOT_END=new RewriteRuleTokenStream(adaptor,"token DOT_END");
        RewriteRuleSubtreeStream stream_predicate=new RewriteRuleSubtreeStream(adaptor,"rule predicate");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:98:7: ( '?-' predicate DOT_END -> ^( QUERY predicate ) )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:98:8: '?-' predicate DOT_END
            {
            string_literal13=(Token)match(input,83,FOLLOW_83_in_query429);  
            stream_83.add(string_literal13);


            pushFollow(FOLLOW_predicate_in_query431);
            predicate14=predicate();

            state._fsp--;

            stream_predicate.add(predicate14.getTree());

            DOT_END15=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_query433);  
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
            // 98:31: -> ^( QUERY predicate )
            {
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:98:34: ^( QUERY predicate )
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:101:1: rule : head ':-' litlist ( ';' ':-' litlist )* DOT_END -> ( ^( RULE head litlist ) )+ DOT_END ;
    public final SociaLiteParser.rule_return rule() throws RecognitionException {
        SociaLiteParser.rule_return retval = new SociaLiteParser.rule_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token string_literal17=null;
        Token char_literal19=null;
        Token string_literal20=null;
        Token DOT_END22=null;
        SociaLiteParser.head_return head16 =null;

        SociaLiteParser.litlist_return litlist18 =null;

        SociaLiteParser.litlist_return litlist21 =null;


        CommonTree string_literal17_tree=null;
        CommonTree char_literal19_tree=null;
        CommonTree string_literal20_tree=null;
        CommonTree DOT_END22_tree=null;
        RewriteRuleTokenStream stream_DOT_END=new RewriteRuleTokenStream(adaptor,"token DOT_END");
        RewriteRuleTokenStream stream_75=new RewriteRuleTokenStream(adaptor,"token 75");
        RewriteRuleTokenStream stream_76=new RewriteRuleTokenStream(adaptor,"token 76");
        RewriteRuleSubtreeStream stream_litlist=new RewriteRuleSubtreeStream(adaptor,"rule litlist");
        RewriteRuleSubtreeStream stream_head=new RewriteRuleSubtreeStream(adaptor,"rule head");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:101:9: ( head ':-' litlist ( ';' ':-' litlist )* DOT_END -> ( ^( RULE head litlist ) )+ DOT_END )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:101:11: head ':-' litlist ( ';' ':-' litlist )* DOT_END
            {
            pushFollow(FOLLOW_head_in_rule454);
            head16=head();

            state._fsp--;

            stream_head.add(head16.getTree());

            string_literal17=(Token)match(input,75,FOLLOW_75_in_rule456);  
            stream_75.add(string_literal17);


            pushFollow(FOLLOW_litlist_in_rule458);
            litlist18=litlist();

            state._fsp--;

            stream_litlist.add(litlist18.getTree());

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:101:29: ( ';' ':-' litlist )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==76) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:101:30: ';' ':-' litlist
            	    {
            	    char_literal19=(Token)match(input,76,FOLLOW_76_in_rule461);  
            	    stream_76.add(char_literal19);


            	    string_literal20=(Token)match(input,75,FOLLOW_75_in_rule463);  
            	    stream_75.add(string_literal20);


            	    pushFollow(FOLLOW_litlist_in_rule465);
            	    litlist21=litlist();

            	    state._fsp--;

            	    stream_litlist.add(litlist21.getTree());

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            DOT_END22=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_rule469);  
            stream_DOT_END.add(DOT_END22);


            // AST REWRITE
            // elements: DOT_END, head, litlist
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 102:9: -> ( ^( RULE head litlist ) )+ DOT_END
            {
                if ( !(stream_head.hasNext()||stream_litlist.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_head.hasNext()||stream_litlist.hasNext() ) {
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:102:12: ^( RULE head litlist )
                    {
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    root_1 = (CommonTree)adaptor.becomeRoot(
                    (CommonTree)adaptor.create(RULE, "RULE")
                    , root_1);

                    adaptor.addChild(root_1, stream_head.nextTree());

                    adaptor.addChild(root_1, stream_litlist.nextTree());

                    adaptor.addChild(root_0, root_1);
                    }

                }
                stream_head.reset();
                stream_litlist.reset();

                adaptor.addChild(root_0, 
                stream_DOT_END.nextNode()
                );

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


    public static class head_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "head"
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:104:1: head : predicate ;
    public final SociaLiteParser.head_return head() throws RecognitionException {
        SociaLiteParser.head_return retval = new SociaLiteParser.head_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SociaLiteParser.predicate_return predicate23 =null;



        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:104:6: ( predicate )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:104:8: predicate
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_predicate_in_head506);
            predicate23=predicate();

            state._fsp--;

            adaptor.addChild(root_0, predicate23.getTree());

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
    // $ANTLR end "head"


    public static class litlist_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "litlist"
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:106:1: litlist : literal ( ',' ! literal )* ;
    public final SociaLiteParser.litlist_return litlist() throws RecognitionException {
        SociaLiteParser.litlist_return retval = new SociaLiteParser.litlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal25=null;
        SociaLiteParser.literal_return literal24 =null;

        SociaLiteParser.literal_return literal26 =null;


        CommonTree char_literal25_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:106:9: ( literal ( ',' ! literal )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:106:10: literal ( ',' ! literal )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_literal_in_litlist513);
            literal24=literal();

            state._fsp--;

            adaptor.addChild(root_0, literal24.getTree());

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:106:18: ( ',' ! literal )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==70) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:106:19: ',' ! literal
            	    {
            	    char_literal25=(Token)match(input,70,FOLLOW_70_in_litlist516); 

            	    pushFollow(FOLLOW_literal_in_litlist519);
            	    literal26=literal();

            	    state._fsp--;

            	    adaptor.addChild(root_0, literal26.getTree());

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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:108:1: literal : ( predicate -> ^( PREDICATE predicate ) | NOT predicate -> ^( PREDICATE NOT predicate ) | expr -> ^( EXPR expr ) );
    public final SociaLiteParser.literal_return literal() throws RecognitionException {
        SociaLiteParser.literal_return retval = new SociaLiteParser.literal_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token NOT28=null;
        SociaLiteParser.predicate_return predicate27 =null;

        SociaLiteParser.predicate_return predicate29 =null;

        SociaLiteParser.expr_return expr30 =null;


        CommonTree NOT28_tree=null;
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleSubtreeStream stream_predicate=new RewriteRuleSubtreeStream(adaptor,"rule predicate");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:108:9: ( predicate -> ^( PREDICATE predicate ) | NOT predicate -> ^( PREDICATE NOT predicate ) | expr -> ^( EXPR expr ) )
            int alt6=3;
            switch ( input.LA(1) ) {
            case ID:
                {
                int LA6_1 = input.LA(2);

                if ( (LA6_1==66) ) {
                    alt6=1;
                }
                else if ( (LA6_1==DOT_ID||LA6_1==64||(LA6_1 >= 68 && LA6_1 <= 69)||LA6_1==71||LA6_1==73||(LA6_1 >= 77 && LA6_1 <= 82)||LA6_1==97) ) {
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
            case 65:
            case 66:
            case 71:
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:108:10: predicate
                    {
                    pushFollow(FOLLOW_predicate_in_literal528);
                    predicate27=predicate();

                    state._fsp--;

                    stream_predicate.add(predicate27.getTree());

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
                    // 108:20: -> ^( PREDICATE predicate )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:108:23: ^( PREDICATE predicate )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:109:4: NOT predicate
                    {
                    NOT28=(Token)match(input,NOT,FOLLOW_NOT_in_literal542);  
                    stream_NOT.add(NOT28);


                    pushFollow(FOLLOW_predicate_in_literal544);
                    predicate29=predicate();

                    state._fsp--;

                    stream_predicate.add(predicate29.getTree());

                    // AST REWRITE
                    // elements: NOT, predicate
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 109:18: -> ^( PREDICATE NOT predicate )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:109:21: ^( PREDICATE NOT predicate )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:110:3: expr
                    {
                    pushFollow(FOLLOW_expr_in_literal558);
                    expr30=expr();

                    state._fsp--;

                    stream_expr.add(expr30.getTree());

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
                    // 110:8: -> ^( EXPR expr )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:110:11: ^( EXPR expr )
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:112:1: expr : ( simpleExpr ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^) simpleExpr | varlist '=' ( cast )? function -> ^( MULTI_ASSIGN varlist function ( cast )? ) );
    public final SociaLiteParser.expr_return expr() throws RecognitionException {
        SociaLiteParser.expr_return retval = new SociaLiteParser.expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal32=null;
        Token char_literal33=null;
        Token char_literal34=null;
        Token string_literal35=null;
        Token string_literal36=null;
        Token string_literal37=null;
        Token string_literal38=null;
        Token char_literal41=null;
        SociaLiteParser.simpleExpr_return simpleExpr31 =null;

        SociaLiteParser.simpleExpr_return simpleExpr39 =null;

        SociaLiteParser.varlist_return varlist40 =null;

        SociaLiteParser.cast_return cast42 =null;

        SociaLiteParser.function_return function43 =null;


        CommonTree char_literal32_tree=null;
        CommonTree char_literal33_tree=null;
        CommonTree char_literal34_tree=null;
        CommonTree string_literal35_tree=null;
        CommonTree string_literal36_tree=null;
        CommonTree string_literal37_tree=null;
        CommonTree string_literal38_tree=null;
        CommonTree char_literal41_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleSubtreeStream stream_varlist=new RewriteRuleSubtreeStream(adaptor,"rule varlist");
        RewriteRuleSubtreeStream stream_cast=new RewriteRuleSubtreeStream(adaptor,"rule cast");
        RewriteRuleSubtreeStream stream_function=new RewriteRuleSubtreeStream(adaptor,"rule function");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:112:5: ( simpleExpr ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^) simpleExpr | varlist '=' ( cast )? function -> ^( MULTI_ASSIGN varlist function ( cast )? ) )
            int alt9=2;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:112:7: simpleExpr ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^) simpleExpr
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_simpleExpr_in_expr575);
                    simpleExpr31=simpleExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, simpleExpr31.getTree());

                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:112:19: ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^)
                    int alt7=7;
                    switch ( input.LA(1) ) {
                    case 77:
                        {
                        alt7=1;
                        }
                        break;
                    case 81:
                        {
                        alt7=2;
                        }
                        break;
                    case 79:
                        {
                        alt7=3;
                        }
                        break;
                    case 64:
                        {
                        alt7=4;
                        }
                        break;
                    case 80:
                        {
                        alt7=5;
                        }
                        break;
                    case 82:
                        {
                        alt7=6;
                        }
                        break;
                    case 78:
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
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:112:20: '<' ^
                            {
                            char_literal32=(Token)match(input,77,FOLLOW_77_in_expr579); 
                            char_literal32_tree = 
                            (CommonTree)adaptor.create(char_literal32)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(char_literal32_tree, root_0);


                            }
                            break;
                        case 2 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:112:27: '>' ^
                            {
                            char_literal33=(Token)match(input,81,FOLLOW_81_in_expr584); 
                            char_literal33_tree = 
                            (CommonTree)adaptor.create(char_literal33)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(char_literal33_tree, root_0);


                            }
                            break;
                        case 3 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:112:34: '=' ^
                            {
                            char_literal34=(Token)match(input,79,FOLLOW_79_in_expr589); 
                            char_literal34_tree = 
                            (CommonTree)adaptor.create(char_literal34)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(char_literal34_tree, root_0);


                            }
                            break;
                        case 4 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:112:40: '!=' ^
                            {
                            string_literal35=(Token)match(input,64,FOLLOW_64_in_expr593); 
                            string_literal35_tree = 
                            (CommonTree)adaptor.create(string_literal35)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(string_literal35_tree, root_0);


                            }
                            break;
                        case 5 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:112:48: '==' ^
                            {
                            string_literal36=(Token)match(input,80,FOLLOW_80_in_expr598); 
                            string_literal36_tree = 
                            (CommonTree)adaptor.create(string_literal36)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(string_literal36_tree, root_0);


                            }
                            break;
                        case 6 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:112:56: '>=' ^
                            {
                            string_literal37=(Token)match(input,82,FOLLOW_82_in_expr603); 
                            string_literal37_tree = 
                            (CommonTree)adaptor.create(string_literal37)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(string_literal37_tree, root_0);


                            }
                            break;
                        case 7 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:112:64: '<=' ^
                            {
                            string_literal38=(Token)match(input,78,FOLLOW_78_in_expr608); 
                            string_literal38_tree = 
                            (CommonTree)adaptor.create(string_literal38)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(string_literal38_tree, root_0);


                            }
                            break;

                    }


                    pushFollow(FOLLOW_simpleExpr_in_expr613);
                    simpleExpr39=simpleExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, simpleExpr39.getTree());

                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:113:4: varlist '=' ( cast )? function
                    {
                    pushFollow(FOLLOW_varlist_in_expr618);
                    varlist40=varlist();

                    state._fsp--;

                    stream_varlist.add(varlist40.getTree());

                    char_literal41=(Token)match(input,79,FOLLOW_79_in_expr620);  
                    stream_79.add(char_literal41);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:113:16: ( cast )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==66) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:113:16: cast
                            {
                            pushFollow(FOLLOW_cast_in_expr622);
                            cast42=cast();

                            state._fsp--;

                            stream_cast.add(cast42.getTree());

                            }
                            break;

                    }


                    pushFollow(FOLLOW_function_in_expr625);
                    function43=function();

                    state._fsp--;

                    stream_function.add(function43.getTree());

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
                    // 113:31: -> ^( MULTI_ASSIGN varlist function ( cast )? )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:113:34: ^( MULTI_ASSIGN varlist function ( cast )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(MULTI_ASSIGN, "MULTI_ASSIGN")
                        , root_1);

                        adaptor.addChild(root_1, stream_varlist.nextTree());

                        adaptor.addChild(root_1, stream_function.nextTree());

                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:113:66: ( cast )?
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:115:1: simpleExpr : multExpr ( ( '+' ^| '-' ^) multExpr )* ;
    public final SociaLiteParser.simpleExpr_return simpleExpr() throws RecognitionException {
        SociaLiteParser.simpleExpr_return retval = new SociaLiteParser.simpleExpr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal45=null;
        Token char_literal46=null;
        SociaLiteParser.multExpr_return multExpr44 =null;

        SociaLiteParser.multExpr_return multExpr47 =null;


        CommonTree char_literal45_tree=null;
        CommonTree char_literal46_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:115:11: ( multExpr ( ( '+' ^| '-' ^) multExpr )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:115:13: multExpr ( ( '+' ^| '-' ^) multExpr )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_multExpr_in_simpleExpr647);
            multExpr44=multExpr();

            state._fsp--;

            adaptor.addChild(root_0, multExpr44.getTree());

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:115:23: ( ( '+' ^| '-' ^) multExpr )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==69||LA11_0==71) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:115:24: ( '+' ^| '-' ^) multExpr
            	    {
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:115:24: ( '+' ^| '-' ^)
            	    int alt10=2;
            	    int LA10_0 = input.LA(1);

            	    if ( (LA10_0==69) ) {
            	        alt10=1;
            	    }
            	    else if ( (LA10_0==71) ) {
            	        alt10=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 10, 0, input);

            	        throw nvae;

            	    }
            	    switch (alt10) {
            	        case 1 :
            	            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:115:25: '+' ^
            	            {
            	            char_literal45=(Token)match(input,69,FOLLOW_69_in_simpleExpr652); 
            	            char_literal45_tree = 
            	            (CommonTree)adaptor.create(char_literal45)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal45_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:115:30: '-' ^
            	            {
            	            char_literal46=(Token)match(input,71,FOLLOW_71_in_simpleExpr655); 
            	            char_literal46_tree = 
            	            (CommonTree)adaptor.create(char_literal46)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal46_tree, root_0);


            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_multExpr_in_simpleExpr659);
            	    multExpr47=multExpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, multExpr47.getTree());

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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:117:1: multExpr : exprValue ( ( '*' ^| '/' ^| 'mod' ^) exprValue )* ;
    public final SociaLiteParser.multExpr_return multExpr() throws RecognitionException {
        SociaLiteParser.multExpr_return retval = new SociaLiteParser.multExpr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal49=null;
        Token char_literal50=null;
        Token string_literal51=null;
        SociaLiteParser.exprValue_return exprValue48 =null;

        SociaLiteParser.exprValue_return exprValue52 =null;


        CommonTree char_literal49_tree=null;
        CommonTree char_literal50_tree=null;
        CommonTree string_literal51_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:117:9: ( exprValue ( ( '*' ^| '/' ^| 'mod' ^) exprValue )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:117:11: exprValue ( ( '*' ^| '/' ^| 'mod' ^) exprValue )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_exprValue_in_multExpr670);
            exprValue48=exprValue();

            state._fsp--;

            adaptor.addChild(root_0, exprValue48.getTree());

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:117:21: ( ( '*' ^| '/' ^| 'mod' ^) exprValue )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==68||LA13_0==73||LA13_0==97) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:117:22: ( '*' ^| '/' ^| 'mod' ^) exprValue
            	    {
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:117:22: ( '*' ^| '/' ^| 'mod' ^)
            	    int alt12=3;
            	    switch ( input.LA(1) ) {
            	    case 68:
            	        {
            	        alt12=1;
            	        }
            	        break;
            	    case 73:
            	        {
            	        alt12=2;
            	        }
            	        break;
            	    case 97:
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
            	            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:117:23: '*' ^
            	            {
            	            char_literal49=(Token)match(input,68,FOLLOW_68_in_multExpr674); 
            	            char_literal49_tree = 
            	            (CommonTree)adaptor.create(char_literal49)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal49_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:117:28: '/' ^
            	            {
            	            char_literal50=(Token)match(input,73,FOLLOW_73_in_multExpr677); 
            	            char_literal50_tree = 
            	            (CommonTree)adaptor.create(char_literal50)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal50_tree, root_0);


            	            }
            	            break;
            	        case 3 :
            	            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:117:33: 'mod' ^
            	            {
            	            string_literal51=(Token)match(input,97,FOLLOW_97_in_multExpr680); 
            	            string_literal51_tree = 
            	            (CommonTree)adaptor.create(string_literal51)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(string_literal51_tree, root_0);


            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_exprValue_in_multExpr684);
            	    exprValue52=exprValue();

            	    state._fsp--;

            	    adaptor.addChild(root_0, exprValue52.getTree());

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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:118:1: exprValue : ( (neg= '-' )? ( cast )? term -> ^( TERM term ( $neg)? ( cast )? ) | ( cast )? function -> ^( FUNCTION function ( cast )? ) | ( cast )? compExpr -> ^( COMPOUND_EXPR compExpr ( cast )? ) );
    public final SociaLiteParser.exprValue_return exprValue() throws RecognitionException {
        SociaLiteParser.exprValue_return retval = new SociaLiteParser.exprValue_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token neg=null;
        SociaLiteParser.cast_return cast53 =null;

        SociaLiteParser.term_return term54 =null;

        SociaLiteParser.cast_return cast55 =null;

        SociaLiteParser.function_return function56 =null;

        SociaLiteParser.cast_return cast57 =null;

        SociaLiteParser.compExpr_return compExpr58 =null;


        CommonTree neg_tree=null;
        RewriteRuleTokenStream stream_71=new RewriteRuleTokenStream(adaptor,"token 71");
        RewriteRuleSubtreeStream stream_cast=new RewriteRuleSubtreeStream(adaptor,"rule cast");
        RewriteRuleSubtreeStream stream_term=new RewriteRuleSubtreeStream(adaptor,"rule term");
        RewriteRuleSubtreeStream stream_compExpr=new RewriteRuleSubtreeStream(adaptor,"rule compExpr");
        RewriteRuleSubtreeStream stream_function=new RewriteRuleSubtreeStream(adaptor,"rule function");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:118:10: ( (neg= '-' )? ( cast )? term -> ^( TERM term ( $neg)? ( cast )? ) | ( cast )? function -> ^( FUNCTION function ( cast )? ) | ( cast )? compExpr -> ^( COMPOUND_EXPR compExpr ( cast )? ) )
            int alt18=3;
            switch ( input.LA(1) ) {
            case FLOAT:
            case ID:
            case INT:
            case STRING:
            case UTF8:
            case 71:
                {
                alt18=1;
                }
                break;
            case 66:
                {
                switch ( input.LA(2) ) {
                case 95:
                    {
                    int LA18_4 = input.LA(3);

                    if ( (LA18_4==86) ) {
                        int LA18_12 = input.LA(4);

                        if ( (LA18_12==87) ) {
                            int LA18_21 = input.LA(5);

                            if ( (LA18_21==67) ) {
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
                                case 65:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 66:
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
                    else if ( (LA18_4==67) ) {
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
                        case 65:
                            {
                            alt18=2;
                            }
                            break;
                        case 66:
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
                case 96:
                    {
                    int LA18_5 = input.LA(3);

                    if ( (LA18_5==86) ) {
                        int LA18_14 = input.LA(4);

                        if ( (LA18_14==87) ) {
                            int LA18_22 = input.LA(5);

                            if ( (LA18_22==67) ) {
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
                                case 65:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 66:
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
                    else if ( (LA18_5==67) ) {
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
                        case 65:
                            {
                            alt18=2;
                            }
                            break;
                        case 66:
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
                case 92:
                    {
                    int LA18_6 = input.LA(3);

                    if ( (LA18_6==86) ) {
                        int LA18_15 = input.LA(4);

                        if ( (LA18_15==87) ) {
                            int LA18_23 = input.LA(5);

                            if ( (LA18_23==67) ) {
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
                                case 65:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 66:
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
                    else if ( (LA18_6==67) ) {
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
                        case 65:
                            {
                            alt18=2;
                            }
                            break;
                        case 66:
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
                case 90:
                    {
                    int LA18_7 = input.LA(3);

                    if ( (LA18_7==86) ) {
                        int LA18_16 = input.LA(4);

                        if ( (LA18_16==87) ) {
                            int LA18_24 = input.LA(5);

                            if ( (LA18_24==67) ) {
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
                                case 65:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 66:
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
                    else if ( (LA18_7==67) ) {
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
                        case 65:
                            {
                            alt18=2;
                            }
                            break;
                        case 66:
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
                case 85:
                    {
                    int LA18_8 = input.LA(3);

                    if ( (LA18_8==86) ) {
                        int LA18_17 = input.LA(4);

                        if ( (LA18_17==87) ) {
                            int LA18_25 = input.LA(5);

                            if ( (LA18_25==67) ) {
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
                                case 65:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 66:
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
                    else if ( (LA18_8==67) ) {
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
                        case 65:
                            {
                            alt18=2;
                            }
                            break;
                        case 66:
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
                case 84:
                    {
                    int LA18_9 = input.LA(3);

                    if ( (LA18_9==86) ) {
                        int LA18_18 = input.LA(4);

                        if ( (LA18_18==87) ) {
                            int LA18_26 = input.LA(5);

                            if ( (LA18_26==67) ) {
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
                                case 65:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 66:
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
                    else if ( (LA18_9==67) ) {
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
                        case 65:
                            {
                            alt18=2;
                            }
                            break;
                        case 66:
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
                    case 86:
                        {
                        int LA18_19 = input.LA(4);

                        if ( (LA18_19==87) ) {
                            int LA18_27 = input.LA(5);

                            if ( (LA18_27==67) ) {
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
                                case 65:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 66:
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
                    case 67:
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
                        case 65:
                            {
                            alt18=2;
                            }
                            break;
                        case DOT_END:
                        case 64:
                        case 66:
                        case 67:
                        case 68:
                        case 69:
                        case 70:
                        case 71:
                        case 73:
                        case 76:
                        case 77:
                        case 78:
                        case 79:
                        case 80:
                        case 81:
                        case 82:
                        case 97:
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
                    case 68:
                    case 69:
                    case 71:
                    case 73:
                    case 97:
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
                case 65:
                case 66:
                case 71:
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
            case 65:
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:118:12: (neg= '-' )? ( cast )? term
                    {
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:118:12: (neg= '-' )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==71) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:118:13: neg= '-'
                            {
                            neg=(Token)match(input,71,FOLLOW_71_in_exprValue695);  
                            stream_71.add(neg);


                            }
                            break;

                    }


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:118:23: ( cast )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==66) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:118:23: cast
                            {
                            pushFollow(FOLLOW_cast_in_exprValue699);
                            cast53=cast();

                            state._fsp--;

                            stream_cast.add(cast53.getTree());

                            }
                            break;

                    }


                    pushFollow(FOLLOW_term_in_exprValue702);
                    term54=term();

                    state._fsp--;

                    stream_term.add(term54.getTree());

                    // AST REWRITE
                    // elements: cast, neg, term
                    // token labels: neg
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_neg=new RewriteRuleTokenStream(adaptor,"token neg",neg);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 118:34: -> ^( TERM term ( $neg)? ( cast )? )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:118:37: ^( TERM term ( $neg)? ( cast )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(TERM, "TERM")
                        , root_1);

                        adaptor.addChild(root_1, stream_term.nextTree());

                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:118:50: ( $neg)?
                        if ( stream_neg.hasNext() ) {
                            adaptor.addChild(root_1, stream_neg.nextNode());

                        }
                        stream_neg.reset();

                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:118:55: ( cast )?
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:119:4: ( cast )? function
                    {
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:119:4: ( cast )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==66) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:119:4: cast
                            {
                            pushFollow(FOLLOW_cast_in_exprValue722);
                            cast55=cast();

                            state._fsp--;

                            stream_cast.add(cast55.getTree());

                            }
                            break;

                    }


                    pushFollow(FOLLOW_function_in_exprValue725);
                    function56=function();

                    state._fsp--;

                    stream_function.add(function56.getTree());

                    // AST REWRITE
                    // elements: function, cast
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 119:19: -> ^( FUNCTION function ( cast )? )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:119:22: ^( FUNCTION function ( cast )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(FUNCTION, "FUNCTION")
                        , root_1);

                        adaptor.addChild(root_1, stream_function.nextTree());

                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:119:42: ( cast )?
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:120:4: ( cast )? compExpr
                    {
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:120:4: ( cast )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==66) ) {
                        int LA17_1 = input.LA(2);

                        if ( ((LA17_1 >= 84 && LA17_1 <= 85)||LA17_1==90||LA17_1==92||(LA17_1 >= 95 && LA17_1 <= 96)) ) {
                            alt17=1;
                        }
                        else if ( (LA17_1==ID) ) {
                            int LA17_3 = input.LA(3);

                            if ( (LA17_3==86) ) {
                                alt17=1;
                            }
                            else if ( (LA17_3==67) ) {
                                int LA17_5 = input.LA(4);

                                if ( (LA17_5==66) ) {
                                    alt17=1;
                                }
                            }
                        }
                    }
                    switch (alt17) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:120:4: cast
                            {
                            pushFollow(FOLLOW_cast_in_exprValue741);
                            cast57=cast();

                            state._fsp--;

                            stream_cast.add(cast57.getTree());

                            }
                            break;

                    }


                    pushFollow(FOLLOW_compExpr_in_exprValue744);
                    compExpr58=compExpr();

                    state._fsp--;

                    stream_compExpr.add(compExpr58.getTree());

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
                    // 120:19: -> ^( COMPOUND_EXPR compExpr ( cast )? )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:120:22: ^( COMPOUND_EXPR compExpr ( cast )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(COMPOUND_EXPR, "COMPOUND_EXPR")
                        , root_1);

                        adaptor.addChild(root_1, stream_compExpr.nextTree());

                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:120:47: ( cast )?
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:122:1: compExpr : '(' ! simpleExpr ')' !;
    public final SociaLiteParser.compExpr_return compExpr() throws RecognitionException {
        SociaLiteParser.compExpr_return retval = new SociaLiteParser.compExpr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal59=null;
        Token char_literal61=null;
        SociaLiteParser.simpleExpr_return simpleExpr60 =null;


        CommonTree char_literal59_tree=null;
        CommonTree char_literal61_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:122:9: ( '(' ! simpleExpr ')' !)
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:122:11: '(' ! simpleExpr ')' !
            {
            root_0 = (CommonTree)adaptor.nil();


            char_literal59=(Token)match(input,66,FOLLOW_66_in_compExpr763); 

            pushFollow(FOLLOW_simpleExpr_in_compExpr766);
            simpleExpr60=simpleExpr();

            state._fsp--;

            adaptor.addChild(root_0, simpleExpr60.getTree());

            char_literal61=(Token)match(input,67,FOLLOW_67_in_compExpr768); 

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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:123:1: cast : '(' type ')' -> type ;
    public final SociaLiteParser.cast_return cast() throws RecognitionException {
        SociaLiteParser.cast_return retval = new SociaLiteParser.cast_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal62=null;
        Token char_literal64=null;
        SociaLiteParser.type_return type63 =null;


        CommonTree char_literal62_tree=null;
        CommonTree char_literal64_tree=null;
        RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:123:5: ( '(' type ')' -> type )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:123:7: '(' type ')'
            {
            char_literal62=(Token)match(input,66,FOLLOW_66_in_cast777);  
            stream_66.add(char_literal62);


            pushFollow(FOLLOW_type_in_cast779);
            type63=type();

            state._fsp--;

            stream_type.add(type63.getTree());

            char_literal64=(Token)match(input,67,FOLLOW_67_in_cast781);  
            stream_67.add(char_literal64);


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
            // 123:20: -> type
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:124:1: varlist : '(' !e1= dotname ( ',' !e2= dotname )+ ')' !;
    public final SociaLiteParser.varlist_return varlist() throws RecognitionException {
        SociaLiteParser.varlist_return retval = new SociaLiteParser.varlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal65=null;
        Token char_literal66=null;
        Token char_literal67=null;
        SociaLiteParser.dotname_return e1 =null;

        SociaLiteParser.dotname_return e2 =null;


        CommonTree char_literal65_tree=null;
        CommonTree char_literal66_tree=null;
        CommonTree char_literal67_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:124:8: ( '(' !e1= dotname ( ',' !e2= dotname )+ ')' !)
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:124:10: '(' !e1= dotname ( ',' !e2= dotname )+ ')' !
            {
            root_0 = (CommonTree)adaptor.nil();


            char_literal65=(Token)match(input,66,FOLLOW_66_in_varlist793); 

            pushFollow(FOLLOW_dotname_in_varlist798);
            e1=dotname();

            state._fsp--;

            adaptor.addChild(root_0, e1.getTree());

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:124:27: ( ',' !e2= dotname )+
            int cnt19=0;
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==70) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:124:28: ',' !e2= dotname
            	    {
            	    char_literal66=(Token)match(input,70,FOLLOW_70_in_varlist802); 

            	    pushFollow(FOLLOW_dotname_in_varlist807);
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


            char_literal67=(Token)match(input,67,FOLLOW_67_in_varlist811); 

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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:125:1: function : '$' dotname '(' ( fparamlist )? ')' -> ^( FUNC dotname ( fparamlist )? ) ;
    public final SociaLiteParser.function_return function() throws RecognitionException {
        SociaLiteParser.function_return retval = new SociaLiteParser.function_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal68=null;
        Token char_literal70=null;
        Token char_literal72=null;
        SociaLiteParser.dotname_return dotname69 =null;

        SociaLiteParser.fparamlist_return fparamlist71 =null;


        CommonTree char_literal68_tree=null;
        CommonTree char_literal70_tree=null;
        CommonTree char_literal72_tree=null;
        RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleSubtreeStream stream_fparamlist=new RewriteRuleSubtreeStream(adaptor,"rule fparamlist");
        RewriteRuleSubtreeStream stream_dotname=new RewriteRuleSubtreeStream(adaptor,"rule dotname");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:125:9: ( '$' dotname '(' ( fparamlist )? ')' -> ^( FUNC dotname ( fparamlist )? ) )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:125:11: '$' dotname '(' ( fparamlist )? ')'
            {
            char_literal68=(Token)match(input,65,FOLLOW_65_in_function819);  
            stream_65.add(char_literal68);


            pushFollow(FOLLOW_dotname_in_function821);
            dotname69=dotname();

            state._fsp--;

            stream_dotname.add(dotname69.getTree());

            char_literal70=(Token)match(input,66,FOLLOW_66_in_function823);  
            stream_66.add(char_literal70);


            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:125:27: ( fparamlist )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==FLOAT||LA20_0==ID||LA20_0==INT||LA20_0==STRING||LA20_0==UTF8||(LA20_0 >= 65 && LA20_0 <= 66)||LA20_0==71) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:125:27: fparamlist
                    {
                    pushFollow(FOLLOW_fparamlist_in_function825);
                    fparamlist71=fparamlist();

                    state._fsp--;

                    stream_fparamlist.add(fparamlist71.getTree());

                    }
                    break;

            }


            char_literal72=(Token)match(input,67,FOLLOW_67_in_function828);  
            stream_67.add(char_literal72);


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
            // 125:43: -> ^( FUNC dotname ( fparamlist )? )
            {
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:125:46: ^( FUNC dotname ( fparamlist )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(FUNC, "FUNC")
                , root_1);

                adaptor.addChild(root_1, stream_dotname.nextTree());

                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:125:61: ( fparamlist )?
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:128:1: predicate : ID '(' paramlist ')' -> ID paramlist ;
    public final SociaLiteParser.predicate_return predicate() throws RecognitionException {
        SociaLiteParser.predicate_return retval = new SociaLiteParser.predicate_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token ID73=null;
        Token char_literal74=null;
        Token char_literal76=null;
        SociaLiteParser.paramlist_return paramlist75 =null;


        CommonTree ID73_tree=null;
        CommonTree char_literal74_tree=null;
        CommonTree char_literal76_tree=null;
        RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_paramlist=new RewriteRuleSubtreeStream(adaptor,"rule paramlist");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:128:11: ( ID '(' paramlist ')' -> ID paramlist )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:128:12: ID '(' paramlist ')'
            {
            ID73=(Token)match(input,ID,FOLLOW_ID_in_predicate848);  
            stream_ID.add(ID73);


            char_literal74=(Token)match(input,66,FOLLOW_66_in_predicate850);  
            stream_66.add(char_literal74);


            pushFollow(FOLLOW_paramlist_in_predicate852);
            paramlist75=paramlist();

            state._fsp--;

            stream_paramlist.add(paramlist75.getTree());

            char_literal76=(Token)match(input,67,FOLLOW_67_in_predicate854);  
            stream_67.add(char_literal76);


            // AST REWRITE
            // elements: ID, paramlist
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 128:33: -> ID paramlist
            {
                adaptor.addChild(root_0, 
                stream_ID.nextNode()
                );

                adaptor.addChild(root_0, stream_paramlist.nextTree());

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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:130:1: paramlist : param ( ',' ! param )* ;
    public final SociaLiteParser.paramlist_return paramlist() throws RecognitionException {
        SociaLiteParser.paramlist_return retval = new SociaLiteParser.paramlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal78=null;
        SociaLiteParser.param_return param77 =null;

        SociaLiteParser.param_return param79 =null;


        CommonTree char_literal78_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:130:11: ( param ( ',' ! param )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:130:12: param ( ',' ! param )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_param_in_paramlist867);
            param77=param();

            state._fsp--;

            adaptor.addChild(root_0, param77.getTree());

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:130:18: ( ',' ! param )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==70) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:130:19: ',' ! param
            	    {
            	    char_literal78=(Token)match(input,70,FOLLOW_70_in_paramlist870); 

            	    pushFollow(FOLLOW_param_in_paramlist873);
            	    param79=param();

            	    state._fsp--;

            	    adaptor.addChild(root_0, param79.getTree());

            	    }
            	    break;

            	default :
            	    break loop21;
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:131:1: fparamlist : fparam ( ',' ! fparam )* ;
    public final SociaLiteParser.fparamlist_return fparamlist() throws RecognitionException {
        SociaLiteParser.fparamlist_return retval = new SociaLiteParser.fparamlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal81=null;
        SociaLiteParser.fparam_return fparam80 =null;

        SociaLiteParser.fparam_return fparam82 =null;


        CommonTree char_literal81_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:131:12: ( fparam ( ',' ! fparam )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:131:13: fparam ( ',' ! fparam )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_fparam_in_fparamlist881);
            fparam80=fparam();

            state._fsp--;

            adaptor.addChild(root_0, fparam80.getTree());

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:131:20: ( ',' ! fparam )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==70) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:131:21: ',' ! fparam
            	    {
            	    char_literal81=(Token)match(input,70,FOLLOW_70_in_fparamlist884); 

            	    pushFollow(FOLLOW_fparam_in_fparamlist887);
            	    fparam82=fparam();

            	    state._fsp--;

            	    adaptor.addChild(root_0, fparam82.getTree());

            	    }
            	    break;

            	default :
            	    break loop22;
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:133:1: param : simpleExpr ;
    public final SociaLiteParser.param_return param() throws RecognitionException {
        SociaLiteParser.param_return retval = new SociaLiteParser.param_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SociaLiteParser.simpleExpr_return simpleExpr83 =null;



        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:133:7: ( simpleExpr )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:133:8: simpleExpr
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_simpleExpr_in_param896);
            simpleExpr83=simpleExpr();

            state._fsp--;

            adaptor.addChild(root_0, simpleExpr83.getTree());

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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:134:1: fparam : simpleExpr ;
    public final SociaLiteParser.fparam_return fparam() throws RecognitionException {
        SociaLiteParser.fparam_return retval = new SociaLiteParser.fparam_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SociaLiteParser.simpleExpr_return simpleExpr84 =null;



        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:134:8: ( simpleExpr )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:134:9: simpleExpr
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_simpleExpr_in_fparam902);
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
    // $ANTLR end "fparam"


    public static class term_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "term"
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:136:1: term : ( INT -> ^( T_INT INT ) | FLOAT -> ^( T_FLOAT FLOAT ) | STRING -> ^( T_STR STRING ) | UTF8 -> ^( T_UTF8 UTF8 ) | dotname -> ^( T_VAR dotname ) );
    public final SociaLiteParser.term_return term() throws RecognitionException {
        SociaLiteParser.term_return retval = new SociaLiteParser.term_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token INT85=null;
        Token FLOAT86=null;
        Token STRING87=null;
        Token UTF888=null;
        SociaLiteParser.dotname_return dotname89 =null;


        CommonTree INT85_tree=null;
        CommonTree FLOAT86_tree=null;
        CommonTree STRING87_tree=null;
        CommonTree UTF888_tree=null;
        RewriteRuleTokenStream stream_FLOAT=new RewriteRuleTokenStream(adaptor,"token FLOAT");
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleTokenStream stream_UTF8=new RewriteRuleTokenStream(adaptor,"token UTF8");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_dotname=new RewriteRuleSubtreeStream(adaptor,"rule dotname");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:136:5: ( INT -> ^( T_INT INT ) | FLOAT -> ^( T_FLOAT FLOAT ) | STRING -> ^( T_STR STRING ) | UTF8 -> ^( T_UTF8 UTF8 ) | dotname -> ^( T_VAR dotname ) )
            int alt23=5;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt23=1;
                }
                break;
            case FLOAT:
                {
                alt23=2;
                }
                break;
            case STRING:
                {
                alt23=3;
                }
                break;
            case UTF8:
                {
                alt23=4;
                }
                break;
            case ID:
                {
                alt23=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;

            }

            switch (alt23) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:136:7: INT
                    {
                    INT85=(Token)match(input,INT,FOLLOW_INT_in_term909);  
                    stream_INT.add(INT85);


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
                    // 136:11: -> ^( T_INT INT )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:136:14: ^( T_INT INT )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:137:3: FLOAT
                    {
                    FLOAT86=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_term922);  
                    stream_FLOAT.add(FLOAT86);


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
                    // 137:9: -> ^( T_FLOAT FLOAT )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:137:12: ^( T_FLOAT FLOAT )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:138:3: STRING
                    {
                    STRING87=(Token)match(input,STRING,FOLLOW_STRING_in_term934);  
                    stream_STRING.add(STRING87);


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
                    // 138:10: -> ^( T_STR STRING )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:138:13: ^( T_STR STRING )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:139:3: UTF8
                    {
                    UTF888=(Token)match(input,UTF8,FOLLOW_UTF8_in_term946);  
                    stream_UTF8.add(UTF888);


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
                    // 139:8: -> ^( T_UTF8 UTF8 )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:139:11: ^( T_UTF8 UTF8 )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:140:3: dotname
                    {
                    pushFollow(FOLLOW_dotname_in_term959);
                    dotname89=dotname();

                    state._fsp--;

                    stream_dotname.add(dotname89.getTree());

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
                    // 140:11: -> ^( T_VAR dotname )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:140:14: ^( T_VAR dotname )
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:142:1: table_decl : ID '(' decls ')' ( table_opts )? DOT_END -> ^( DECL ID decls ^( OPTION ( table_opts )? ) ) ;
    public final SociaLiteParser.table_decl_return table_decl() throws RecognitionException {
        SociaLiteParser.table_decl_return retval = new SociaLiteParser.table_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token ID90=null;
        Token char_literal91=null;
        Token char_literal93=null;
        Token DOT_END95=null;
        SociaLiteParser.decls_return decls92 =null;

        SociaLiteParser.table_opts_return table_opts94 =null;


        CommonTree ID90_tree=null;
        CommonTree char_literal91_tree=null;
        CommonTree char_literal93_tree=null;
        CommonTree DOT_END95_tree=null;
        RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_DOT_END=new RewriteRuleTokenStream(adaptor,"token DOT_END");
        RewriteRuleSubtreeStream stream_table_opts=new RewriteRuleSubtreeStream(adaptor,"rule table_opts");
        RewriteRuleSubtreeStream stream_decls=new RewriteRuleSubtreeStream(adaptor,"rule decls");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:142:11: ( ID '(' decls ')' ( table_opts )? DOT_END -> ^( DECL ID decls ^( OPTION ( table_opts )? ) ) )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:142:13: ID '(' decls ')' ( table_opts )? DOT_END
            {
            ID90=(Token)match(input,ID,FOLLOW_ID_in_table_decl975);  
            stream_ID.add(ID90);


            char_literal91=(Token)match(input,66,FOLLOW_66_in_table_decl977);  
            stream_66.add(char_literal91);


            pushFollow(FOLLOW_decls_in_table_decl979);
            decls92=decls();

            state._fsp--;

            stream_decls.add(decls92.getTree());

            char_literal93=(Token)match(input,67,FOLLOW_67_in_table_decl981);  
            stream_67.add(char_literal93);


            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:142:30: ( table_opts )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==89||(LA24_0 >= 93 && LA24_0 <= 94)||(LA24_0 >= 98 && LA24_0 <= 101)) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:142:30: table_opts
                    {
                    pushFollow(FOLLOW_table_opts_in_table_decl983);
                    table_opts94=table_opts();

                    state._fsp--;

                    stream_table_opts.add(table_opts94.getTree());

                    }
                    break;

            }


            DOT_END95=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_table_decl986);  
            stream_DOT_END.add(DOT_END95);


            // AST REWRITE
            // elements: ID, table_opts, decls
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 142:51: -> ^( DECL ID decls ^( OPTION ( table_opts )? ) )
            {
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:142:54: ^( DECL ID decls ^( OPTION ( table_opts )? ) )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(DECL, "DECL")
                , root_1);

                adaptor.addChild(root_1, 
                stream_ID.nextNode()
                );

                adaptor.addChild(root_1, stream_decls.nextTree());

                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:142:70: ^( OPTION ( table_opts )? )
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(OPTION, "OPTION")
                , root_2);

                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:142:79: ( table_opts )?
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:144:1: table_opts : t_opt ( ',' ! t_opt )* ;
    public final SociaLiteParser.table_opts_return table_opts() throws RecognitionException {
        SociaLiteParser.table_opts_return retval = new SociaLiteParser.table_opts_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal97=null;
        SociaLiteParser.t_opt_return t_opt96 =null;

        SociaLiteParser.t_opt_return t_opt98 =null;


        CommonTree char_literal97_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:144:11: ( t_opt ( ',' ! t_opt )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:144:13: t_opt ( ',' ! t_opt )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_t_opt_in_table_opts1019);
            t_opt96=t_opt();

            state._fsp--;

            adaptor.addChild(root_0, t_opt96.getTree());

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:144:19: ( ',' ! t_opt )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==70) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:144:20: ',' ! t_opt
            	    {
            	    char_literal97=(Token)match(input,70,FOLLOW_70_in_table_opts1022); 

            	    pushFollow(FOLLOW_t_opt_in_table_opts1025);
            	    t_opt98=t_opt();

            	    state._fsp--;

            	    adaptor.addChild(root_0, t_opt98.getTree());

            	    }
            	    break;

            	default :
            	    break loop25;
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:146:1: t_opt : ( 'sortby' col= ID (order= SORT_ORDER )? -> ^( SORT_BY $col ( $order)? ) | 'orderby' ID -> ^( ORDER_BY ID ) | 'indexby' ID -> ^( INDEX_BY ID ) | 'groupby' '(' INT ')' -> ^( GROUP_BY INT ) | 'predefined' -> PREDEFINED | 'concurrent' -> CONCURRENT | 'multiset' -> MULTISET );
    public final SociaLiteParser.t_opt_return t_opt() throws RecognitionException {
        SociaLiteParser.t_opt_return retval = new SociaLiteParser.t_opt_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token col=null;
        Token order=null;
        Token string_literal99=null;
        Token string_literal100=null;
        Token ID101=null;
        Token string_literal102=null;
        Token ID103=null;
        Token string_literal104=null;
        Token char_literal105=null;
        Token INT106=null;
        Token char_literal107=null;
        Token string_literal108=null;
        Token string_literal109=null;
        Token string_literal110=null;

        CommonTree col_tree=null;
        CommonTree order_tree=null;
        CommonTree string_literal99_tree=null;
        CommonTree string_literal100_tree=null;
        CommonTree ID101_tree=null;
        CommonTree string_literal102_tree=null;
        CommonTree ID103_tree=null;
        CommonTree string_literal104_tree=null;
        CommonTree char_literal105_tree=null;
        CommonTree INT106_tree=null;
        CommonTree char_literal107_tree=null;
        CommonTree string_literal108_tree=null;
        CommonTree string_literal109_tree=null;
        CommonTree string_literal110_tree=null;
        RewriteRuleTokenStream stream_98=new RewriteRuleTokenStream(adaptor,"token 98");
        RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleTokenStream stream_94=new RewriteRuleTokenStream(adaptor,"token 94");
        RewriteRuleTokenStream stream_93=new RewriteRuleTokenStream(adaptor,"token 93");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_SORT_ORDER=new RewriteRuleTokenStream(adaptor,"token SORT_ORDER");
        RewriteRuleTokenStream stream_99=new RewriteRuleTokenStream(adaptor,"token 99");
        RewriteRuleTokenStream stream_101=new RewriteRuleTokenStream(adaptor,"token 101");
        RewriteRuleTokenStream stream_100=new RewriteRuleTokenStream(adaptor,"token 100");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:146:7: ( 'sortby' col= ID (order= SORT_ORDER )? -> ^( SORT_BY $col ( $order)? ) | 'orderby' ID -> ^( ORDER_BY ID ) | 'indexby' ID -> ^( INDEX_BY ID ) | 'groupby' '(' INT ')' -> ^( GROUP_BY INT ) | 'predefined' -> PREDEFINED | 'concurrent' -> CONCURRENT | 'multiset' -> MULTISET )
            int alt27=7;
            switch ( input.LA(1) ) {
            case 101:
                {
                alt27=1;
                }
                break;
            case 99:
                {
                alt27=2;
                }
                break;
            case 94:
                {
                alt27=3;
                }
                break;
            case 93:
                {
                alt27=4;
                }
                break;
            case 100:
                {
                alt27=5;
                }
                break;
            case 89:
                {
                alt27=6;
                }
                break;
            case 98:
                {
                alt27=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;

            }

            switch (alt27) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:146:9: 'sortby' col= ID (order= SORT_ORDER )?
                    {
                    string_literal99=(Token)match(input,101,FOLLOW_101_in_t_opt1036);  
                    stream_101.add(string_literal99);


                    col=(Token)match(input,ID,FOLLOW_ID_in_t_opt1040);  
                    stream_ID.add(col);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:146:25: (order= SORT_ORDER )?
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==SORT_ORDER) ) {
                        alt26=1;
                    }
                    switch (alt26) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:146:26: order= SORT_ORDER
                            {
                            order=(Token)match(input,SORT_ORDER,FOLLOW_SORT_ORDER_in_t_opt1045);  
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
                    // 146:45: -> ^( SORT_BY $col ( $order)? )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:146:48: ^( SORT_BY $col ( $order)? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(SORT_BY, "SORT_BY")
                        , root_1);

                        adaptor.addChild(root_1, stream_col.nextNode());

                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:146:64: ( $order)?
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:147:3: 'orderby' ID
                    {
                    string_literal100=(Token)match(input,99,FOLLOW_99_in_t_opt1064);  
                    stream_99.add(string_literal100);


                    ID101=(Token)match(input,ID,FOLLOW_ID_in_t_opt1066);  
                    stream_ID.add(ID101);


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
                    // 147:16: -> ^( ORDER_BY ID )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:147:19: ^( ORDER_BY ID )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:148:3: 'indexby' ID
                    {
                    string_literal102=(Token)match(input,94,FOLLOW_94_in_t_opt1078);  
                    stream_94.add(string_literal102);


                    ID103=(Token)match(input,ID,FOLLOW_ID_in_t_opt1080);  
                    stream_ID.add(ID103);


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
                    // 148:16: -> ^( INDEX_BY ID )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:148:19: ^( INDEX_BY ID )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:149:3: 'groupby' '(' INT ')'
                    {
                    string_literal104=(Token)match(input,93,FOLLOW_93_in_t_opt1092);  
                    stream_93.add(string_literal104);


                    char_literal105=(Token)match(input,66,FOLLOW_66_in_t_opt1094);  
                    stream_66.add(char_literal105);


                    INT106=(Token)match(input,INT,FOLLOW_INT_in_t_opt1096);  
                    stream_INT.add(INT106);


                    char_literal107=(Token)match(input,67,FOLLOW_67_in_t_opt1098);  
                    stream_67.add(char_literal107);


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
                    // 149:25: -> ^( GROUP_BY INT )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:149:28: ^( GROUP_BY INT )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:150:3: 'predefined'
                    {
                    string_literal108=(Token)match(input,100,FOLLOW_100_in_t_opt1110);  
                    stream_100.add(string_literal108);


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
                    // 150:16: -> PREDEFINED
                    {
                        adaptor.addChild(root_0, 
                        (CommonTree)adaptor.create(PREDEFINED, "PREDEFINED")
                        );

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 6 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:151:3: 'concurrent'
                    {
                    string_literal109=(Token)match(input,89,FOLLOW_89_in_t_opt1118);  
                    stream_89.add(string_literal109);


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
                    // 151:16: -> CONCURRENT
                    {
                        adaptor.addChild(root_0, 
                        (CommonTree)adaptor.create(CONCURRENT, "CONCURRENT")
                        );

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 7 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:152:3: 'multiset'
                    {
                    string_literal110=(Token)match(input,98,FOLLOW_98_in_t_opt1126);  
                    stream_98.add(string_literal110);


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
                    // 152:14: -> MULTISET
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:156:1: decls : col_decls ( ',' '(' decls ')' )? -> ^( COL_DECLS col_decls ^( DECL ( decls )? ) ) ;
    public final SociaLiteParser.decls_return decls() throws RecognitionException {
        SociaLiteParser.decls_return retval = new SociaLiteParser.decls_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal112=null;
        Token char_literal113=null;
        Token char_literal115=null;
        SociaLiteParser.col_decls_return col_decls111 =null;

        SociaLiteParser.decls_return decls114 =null;


        CommonTree char_literal112_tree=null;
        CommonTree char_literal113_tree=null;
        CommonTree char_literal115_tree=null;
        RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_70=new RewriteRuleTokenStream(adaptor,"token 70");
        RewriteRuleSubtreeStream stream_col_decls=new RewriteRuleSubtreeStream(adaptor,"rule col_decls");
        RewriteRuleSubtreeStream stream_decls=new RewriteRuleSubtreeStream(adaptor,"rule decls");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:156:7: ( col_decls ( ',' '(' decls ')' )? -> ^( COL_DECLS col_decls ^( DECL ( decls )? ) ) )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:156:9: col_decls ( ',' '(' decls ')' )?
            {
            pushFollow(FOLLOW_col_decls_in_decls1149);
            col_decls111=col_decls();

            state._fsp--;

            stream_col_decls.add(col_decls111.getTree());

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:156:19: ( ',' '(' decls ')' )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==70) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:156:20: ',' '(' decls ')'
                    {
                    char_literal112=(Token)match(input,70,FOLLOW_70_in_decls1152);  
                    stream_70.add(char_literal112);


                    char_literal113=(Token)match(input,66,FOLLOW_66_in_decls1154);  
                    stream_66.add(char_literal113);


                    pushFollow(FOLLOW_decls_in_decls1156);
                    decls114=decls();

                    state._fsp--;

                    stream_decls.add(decls114.getTree());

                    char_literal115=(Token)match(input,67,FOLLOW_67_in_decls1158);  
                    stream_67.add(char_literal115);


                    }
                    break;

            }


            // AST REWRITE
            // elements: col_decls, decls
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 156:40: -> ^( COL_DECLS col_decls ^( DECL ( decls )? ) )
            {
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:156:43: ^( COL_DECLS col_decls ^( DECL ( decls )? ) )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(COL_DECLS, "COL_DECLS")
                , root_1);

                adaptor.addChild(root_1, stream_col_decls.nextTree());

                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:156:65: ^( DECL ( decls )? )
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(DECL, "DECL")
                , root_2);

                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:156:72: ( decls )?
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:158:1: col_decls : col_decl ( ',' ! col_decl )* ;
    public final SociaLiteParser.col_decls_return col_decls() throws RecognitionException {
        SociaLiteParser.col_decls_return retval = new SociaLiteParser.col_decls_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal117=null;
        SociaLiteParser.col_decl_return col_decl116 =null;

        SociaLiteParser.col_decl_return col_decl118 =null;


        CommonTree char_literal117_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:158:10: ( col_decl ( ',' ! col_decl )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:158:12: col_decl ( ',' ! col_decl )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_col_decl_in_col_decls1184);
            col_decl116=col_decl();

            state._fsp--;

            adaptor.addChild(root_0, col_decl116.getTree());

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:158:21: ( ',' ! col_decl )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==70) ) {
                    int LA29_1 = input.LA(2);

                    if ( (LA29_1==ID||(LA29_1 >= 84 && LA29_1 <= 85)||LA29_1==90||LA29_1==92||(LA29_1 >= 95 && LA29_1 <= 96)) ) {
                        alt29=1;
                    }


                }


                switch (alt29) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:158:22: ',' ! col_decl
            	    {
            	    char_literal117=(Token)match(input,70,FOLLOW_70_in_col_decls1187); 

            	    pushFollow(FOLLOW_col_decl_in_col_decls1190);
            	    col_decl118=col_decl();

            	    state._fsp--;

            	    adaptor.addChild(root_0, col_decl118.getTree());

            	    }
            	    break;

            	default :
            	    break loop29;
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:160:1: col_decl : type ID ( ':' col_opt )? -> ^( COL_DECL type ID ( col_opt )? ) ;
    public final SociaLiteParser.col_decl_return col_decl() throws RecognitionException {
        SociaLiteParser.col_decl_return retval = new SociaLiteParser.col_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token ID120=null;
        Token char_literal121=null;
        SociaLiteParser.type_return type119 =null;

        SociaLiteParser.col_opt_return col_opt122 =null;


        CommonTree ID120_tree=null;
        CommonTree char_literal121_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_74=new RewriteRuleTokenStream(adaptor,"token 74");
        RewriteRuleSubtreeStream stream_col_opt=new RewriteRuleSubtreeStream(adaptor,"rule col_opt");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:160:9: ( type ID ( ':' col_opt )? -> ^( COL_DECL type ID ( col_opt )? ) )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:160:11: type ID ( ':' col_opt )?
            {
            pushFollow(FOLLOW_type_in_col_decl1200);
            type119=type();

            state._fsp--;

            stream_type.add(type119.getTree());

            ID120=(Token)match(input,ID,FOLLOW_ID_in_col_decl1202);  
            stream_ID.add(ID120);


            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:160:19: ( ':' col_opt )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==74) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:160:20: ':' col_opt
                    {
                    char_literal121=(Token)match(input,74,FOLLOW_74_in_col_decl1205);  
                    stream_74.add(char_literal121);


                    pushFollow(FOLLOW_col_opt_in_col_decl1207);
                    col_opt122=col_opt();

                    state._fsp--;

                    stream_col_opt.add(col_opt122.getTree());

                    }
                    break;

            }


            // AST REWRITE
            // elements: ID, type, col_opt
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 160:33: -> ^( COL_DECL type ID ( col_opt )? )
            {
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:160:36: ^( COL_DECL type ID ( col_opt )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(COL_DECL, "COL_DECL")
                , root_1);

                adaptor.addChild(root_1, stream_type.nextTree());

                adaptor.addChild(root_1, 
                stream_ID.nextNode()
                );

                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:160:55: ( col_opt )?
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:162:1: col_opt : (i1= INT '..' i2= INT -> ^( RANGE $i1 $i2) | ITER_DECL -> ITER );
    public final SociaLiteParser.col_opt_return col_opt() throws RecognitionException {
        SociaLiteParser.col_opt_return retval = new SociaLiteParser.col_opt_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token i1=null;
        Token i2=null;
        Token string_literal123=null;
        Token ITER_DECL124=null;

        CommonTree i1_tree=null;
        CommonTree i2_tree=null;
        CommonTree string_literal123_tree=null;
        CommonTree ITER_DECL124_tree=null;
        RewriteRuleTokenStream stream_ITER_DECL=new RewriteRuleTokenStream(adaptor,"token ITER_DECL");
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleTokenStream stream_72=new RewriteRuleTokenStream(adaptor,"token 72");

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:162:9: (i1= INT '..' i2= INT -> ^( RANGE $i1 $i2) | ITER_DECL -> ITER )
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==INT) ) {
                alt31=1;
            }
            else if ( (LA31_0==ITER_DECL) ) {
                alt31=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;

            }
            switch (alt31) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:162:12: i1= INT '..' i2= INT
                    {
                    i1=(Token)match(input,INT,FOLLOW_INT_in_col_opt1233);  
                    stream_INT.add(i1);


                    string_literal123=(Token)match(input,72,FOLLOW_72_in_col_opt1235);  
                    stream_72.add(string_literal123);


                    i2=(Token)match(input,INT,FOLLOW_INT_in_col_opt1239);  
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
                    // 162:31: -> ^( RANGE $i1 $i2)
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:162:34: ^( RANGE $i1 $i2)
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:163:4: ITER_DECL
                    {
                    ITER_DECL124=(Token)match(input,ITER_DECL,FOLLOW_ITER_DECL_in_col_opt1256);  
                    stream_ITER_DECL.add(ITER_DECL124);


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
                    // 163:14: -> ITER
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:166:1: type : ( 'int' ( '[' ']' )? | 'long' ( '[' ']' )? | 'float' ( '[' ']' )? | 'double' ( '[' ']' )? | 'String' ( '[' ']' )? | 'Object' ( '[' ']' )? | ID ( '[' ']' )? );
    public final SociaLiteParser.type_return type() throws RecognitionException {
        SociaLiteParser.type_return retval = new SociaLiteParser.type_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token string_literal125=null;
        Token char_literal126=null;
        Token char_literal127=null;
        Token string_literal128=null;
        Token char_literal129=null;
        Token char_literal130=null;
        Token string_literal131=null;
        Token char_literal132=null;
        Token char_literal133=null;
        Token string_literal134=null;
        Token char_literal135=null;
        Token char_literal136=null;
        Token string_literal137=null;
        Token char_literal138=null;
        Token char_literal139=null;
        Token string_literal140=null;
        Token char_literal141=null;
        Token char_literal142=null;
        Token ID143=null;
        Token char_literal144=null;
        Token char_literal145=null;

        CommonTree string_literal125_tree=null;
        CommonTree char_literal126_tree=null;
        CommonTree char_literal127_tree=null;
        CommonTree string_literal128_tree=null;
        CommonTree char_literal129_tree=null;
        CommonTree char_literal130_tree=null;
        CommonTree string_literal131_tree=null;
        CommonTree char_literal132_tree=null;
        CommonTree char_literal133_tree=null;
        CommonTree string_literal134_tree=null;
        CommonTree char_literal135_tree=null;
        CommonTree char_literal136_tree=null;
        CommonTree string_literal137_tree=null;
        CommonTree char_literal138_tree=null;
        CommonTree char_literal139_tree=null;
        CommonTree string_literal140_tree=null;
        CommonTree char_literal141_tree=null;
        CommonTree char_literal142_tree=null;
        CommonTree ID143_tree=null;
        CommonTree char_literal144_tree=null;
        CommonTree char_literal145_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:166:5: ( 'int' ( '[' ']' )? | 'long' ( '[' ']' )? | 'float' ( '[' ']' )? | 'double' ( '[' ']' )? | 'String' ( '[' ']' )? | 'Object' ( '[' ']' )? | ID ( '[' ']' )? )
            int alt39=7;
            switch ( input.LA(1) ) {
            case 95:
                {
                alt39=1;
                }
                break;
            case 96:
                {
                alt39=2;
                }
                break;
            case 92:
                {
                alt39=3;
                }
                break;
            case 90:
                {
                alt39=4;
                }
                break;
            case 85:
                {
                alt39=5;
                }
                break;
            case 84:
                {
                alt39=6;
                }
                break;
            case ID:
                {
                alt39=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 39, 0, input);

                throw nvae;

            }

            switch (alt39) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:166:7: 'int' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal125=(Token)match(input,95,FOLLOW_95_in_type1269); 
                    string_literal125_tree = 
                    (CommonTree)adaptor.create(string_literal125)
                    ;
                    adaptor.addChild(root_0, string_literal125_tree);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:166:13: ( '[' ']' )?
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==86) ) {
                        alt32=1;
                    }
                    switch (alt32) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:166:14: '[' ']'
                            {
                            char_literal126=(Token)match(input,86,FOLLOW_86_in_type1272); 
                            char_literal126_tree = 
                            (CommonTree)adaptor.create(char_literal126)
                            ;
                            adaptor.addChild(root_0, char_literal126_tree);


                            char_literal127=(Token)match(input,87,FOLLOW_87_in_type1274); 
                            char_literal127_tree = 
                            (CommonTree)adaptor.create(char_literal127)
                            ;
                            adaptor.addChild(root_0, char_literal127_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:167:3: 'long' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal128=(Token)match(input,96,FOLLOW_96_in_type1280); 
                    string_literal128_tree = 
                    (CommonTree)adaptor.create(string_literal128)
                    ;
                    adaptor.addChild(root_0, string_literal128_tree);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:167:10: ( '[' ']' )?
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==86) ) {
                        alt33=1;
                    }
                    switch (alt33) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:167:11: '[' ']'
                            {
                            char_literal129=(Token)match(input,86,FOLLOW_86_in_type1283); 
                            char_literal129_tree = 
                            (CommonTree)adaptor.create(char_literal129)
                            ;
                            adaptor.addChild(root_0, char_literal129_tree);


                            char_literal130=(Token)match(input,87,FOLLOW_87_in_type1285); 
                            char_literal130_tree = 
                            (CommonTree)adaptor.create(char_literal130)
                            ;
                            adaptor.addChild(root_0, char_literal130_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:168:3: 'float' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal131=(Token)match(input,92,FOLLOW_92_in_type1293); 
                    string_literal131_tree = 
                    (CommonTree)adaptor.create(string_literal131)
                    ;
                    adaptor.addChild(root_0, string_literal131_tree);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:168:11: ( '[' ']' )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==86) ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:168:12: '[' ']'
                            {
                            char_literal132=(Token)match(input,86,FOLLOW_86_in_type1296); 
                            char_literal132_tree = 
                            (CommonTree)adaptor.create(char_literal132)
                            ;
                            adaptor.addChild(root_0, char_literal132_tree);


                            char_literal133=(Token)match(input,87,FOLLOW_87_in_type1298); 
                            char_literal133_tree = 
                            (CommonTree)adaptor.create(char_literal133)
                            ;
                            adaptor.addChild(root_0, char_literal133_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:169:3: 'double' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal134=(Token)match(input,90,FOLLOW_90_in_type1306); 
                    string_literal134_tree = 
                    (CommonTree)adaptor.create(string_literal134)
                    ;
                    adaptor.addChild(root_0, string_literal134_tree);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:169:12: ( '[' ']' )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==86) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:169:13: '[' ']'
                            {
                            char_literal135=(Token)match(input,86,FOLLOW_86_in_type1309); 
                            char_literal135_tree = 
                            (CommonTree)adaptor.create(char_literal135)
                            ;
                            adaptor.addChild(root_0, char_literal135_tree);


                            char_literal136=(Token)match(input,87,FOLLOW_87_in_type1311); 
                            char_literal136_tree = 
                            (CommonTree)adaptor.create(char_literal136)
                            ;
                            adaptor.addChild(root_0, char_literal136_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:170:3: 'String' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal137=(Token)match(input,85,FOLLOW_85_in_type1319); 
                    string_literal137_tree = 
                    (CommonTree)adaptor.create(string_literal137)
                    ;
                    adaptor.addChild(root_0, string_literal137_tree);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:170:12: ( '[' ']' )?
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==86) ) {
                        alt36=1;
                    }
                    switch (alt36) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:170:13: '[' ']'
                            {
                            char_literal138=(Token)match(input,86,FOLLOW_86_in_type1322); 
                            char_literal138_tree = 
                            (CommonTree)adaptor.create(char_literal138)
                            ;
                            adaptor.addChild(root_0, char_literal138_tree);


                            char_literal139=(Token)match(input,87,FOLLOW_87_in_type1324); 
                            char_literal139_tree = 
                            (CommonTree)adaptor.create(char_literal139)
                            ;
                            adaptor.addChild(root_0, char_literal139_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 6 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:171:3: 'Object' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal140=(Token)match(input,84,FOLLOW_84_in_type1332); 
                    string_literal140_tree = 
                    (CommonTree)adaptor.create(string_literal140)
                    ;
                    adaptor.addChild(root_0, string_literal140_tree);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:171:12: ( '[' ']' )?
                    int alt37=2;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0==86) ) {
                        alt37=1;
                    }
                    switch (alt37) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:171:13: '[' ']'
                            {
                            char_literal141=(Token)match(input,86,FOLLOW_86_in_type1335); 
                            char_literal141_tree = 
                            (CommonTree)adaptor.create(char_literal141)
                            ;
                            adaptor.addChild(root_0, char_literal141_tree);


                            char_literal142=(Token)match(input,87,FOLLOW_87_in_type1337); 
                            char_literal142_tree = 
                            (CommonTree)adaptor.create(char_literal142)
                            ;
                            adaptor.addChild(root_0, char_literal142_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 7 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:172:4: ID ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    ID143=(Token)match(input,ID,FOLLOW_ID_in_type1344); 
                    ID143_tree = 
                    (CommonTree)adaptor.create(ID143)
                    ;
                    adaptor.addChild(root_0, ID143_tree);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:172:7: ( '[' ']' )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==86) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:172:8: '[' ']'
                            {
                            char_literal144=(Token)match(input,86,FOLLOW_86_in_type1347); 
                            char_literal144_tree = 
                            (CommonTree)adaptor.create(char_literal144)
                            ;
                            adaptor.addChild(root_0, char_literal144_tree);


                            char_literal145=(Token)match(input,87,FOLLOW_87_in_type1349); 
                            char_literal145_tree = 
                            (CommonTree)adaptor.create(char_literal145)
                            ;
                            adaptor.addChild(root_0, char_literal145_tree);


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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:174:1: dotname : ID ( DOT_ID )* ;
    public final SociaLiteParser.dotname_return dotname() throws RecognitionException {
        SociaLiteParser.dotname_return retval = new SociaLiteParser.dotname_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token ID146=null;
        Token DOT_ID147=null;

        CommonTree ID146_tree=null;
        CommonTree DOT_ID147_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:174:9: ( ID ( DOT_ID )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:174:10: ID ( DOT_ID )*
            {
            root_0 = (CommonTree)adaptor.nil();


            ID146=(Token)match(input,ID,FOLLOW_ID_in_dotname1361); 
            ID146_tree = 
            (CommonTree)adaptor.create(ID146)
            ;
            adaptor.addChild(root_0, ID146_tree);


            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:174:13: ( DOT_ID )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==DOT_ID) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:174:14: DOT_ID
            	    {
            	    DOT_ID147=(Token)match(input,DOT_ID,FOLLOW_DOT_ID_in_dotname1364); 
            	    DOT_ID147_tree = 
            	    (CommonTree)adaptor.create(DOT_ID147)
            	    ;
            	    adaptor.addChild(root_0, DOT_ID147_tree);


            	    }
            	    break;

            	default :
            	    break loop40;
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
        "\1\25\1\uffff\1\25\2\17\1\uffff";
    static final String DFA9_maxS =
        "\1\107\1\uffff\1\140\2\141\1\uffff";
    static final String DFA9_acceptS =
        "\1\uffff\1\1\3\uffff\1\2";
    static final String DFA9_specialS =
        "\6\uffff}>";
    static final String[] DFA9_transitionS = {
            "\1\1\6\uffff\1\1\2\uffff\1\1\24\uffff\1\1\11\uffff\1\1\2\uffff"+
            "\1\1\1\2\4\uffff\1\1",
            "",
            "\1\1\6\uffff\1\3\2\uffff\1\1\24\uffff\1\1\11\uffff\1\1\2\uffff"+
            "\2\1\4\uffff\1\1\14\uffff\2\1\4\uffff\1\1\1\uffff\1\1\2\uffff"+
            "\2\1",
            "\1\4\63\uffff\3\1\1\5\1\1\1\uffff\1\1\14\uffff\1\1\12\uffff"+
            "\1\1",
            "\1\4\63\uffff\3\1\1\5\1\1\1\uffff\1\1\27\uffff\1\1",
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
            return "112:1: expr : ( simpleExpr ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^) simpleExpr | varlist '=' ( cast )? function -> ^( MULTI_ASSIGN varlist function ( cast )? ) );";
        }
    }
 

    public static final BitSet FOLLOW_stat_in_prog360 = new BitSet(new long[]{0x0000000010000000L,0x0000000009080000L});
    public static final BitSet FOLLOW_EOF_in_prog363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_decl_in_stat369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule_in_stat373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_query_in_stat377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_stmt_in_stat381 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_table_stmt390 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_ID_in_table_stmt392 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_DOT_END_in_table_stmt394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_91_in_table_stmt407 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_ID_in_table_stmt409 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_DOT_END_in_table_stmt411 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_query429 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_predicate_in_query431 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_DOT_END_in_query433 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_head_in_rule454 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_rule456 = new BitSet(new long[]{0x4010004090200000L,0x0000000000000086L});
    public static final BitSet FOLLOW_litlist_in_rule458 = new BitSet(new long[]{0x0000000000004000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_rule461 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_rule463 = new BitSet(new long[]{0x4010004090200000L,0x0000000000000086L});
    public static final BitSet FOLLOW_litlist_in_rule465 = new BitSet(new long[]{0x0000000000004000L,0x0000000000001000L});
    public static final BitSet FOLLOW_DOT_END_in_rule469 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_predicate_in_head506 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_litlist513 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_litlist516 = new BitSet(new long[]{0x4010004090200000L,0x0000000000000086L});
    public static final BitSet FOLLOW_literal_in_litlist519 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_predicate_in_literal528 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_literal542 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_predicate_in_literal544 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_literal558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleExpr_in_expr575 = new BitSet(new long[]{0x0000000000000000L,0x000000000007E001L});
    public static final BitSet FOLLOW_77_in_expr579 = new BitSet(new long[]{0x4010000090200000L,0x0000000000000086L});
    public static final BitSet FOLLOW_81_in_expr584 = new BitSet(new long[]{0x4010000090200000L,0x0000000000000086L});
    public static final BitSet FOLLOW_79_in_expr589 = new BitSet(new long[]{0x4010000090200000L,0x0000000000000086L});
    public static final BitSet FOLLOW_64_in_expr593 = new BitSet(new long[]{0x4010000090200000L,0x0000000000000086L});
    public static final BitSet FOLLOW_80_in_expr598 = new BitSet(new long[]{0x4010000090200000L,0x0000000000000086L});
    public static final BitSet FOLLOW_82_in_expr603 = new BitSet(new long[]{0x4010000090200000L,0x0000000000000086L});
    public static final BitSet FOLLOW_78_in_expr608 = new BitSet(new long[]{0x4010000090200000L,0x0000000000000086L});
    public static final BitSet FOLLOW_simpleExpr_in_expr613 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varlist_in_expr618 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_expr620 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000006L});
    public static final BitSet FOLLOW_cast_in_expr622 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_function_in_expr625 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multExpr_in_simpleExpr647 = new BitSet(new long[]{0x0000000000000002L,0x00000000000000A0L});
    public static final BitSet FOLLOW_69_in_simpleExpr652 = new BitSet(new long[]{0x4010000090200000L,0x0000000000000086L});
    public static final BitSet FOLLOW_71_in_simpleExpr655 = new BitSet(new long[]{0x4010000090200000L,0x0000000000000086L});
    public static final BitSet FOLLOW_multExpr_in_simpleExpr659 = new BitSet(new long[]{0x0000000000000002L,0x00000000000000A0L});
    public static final BitSet FOLLOW_exprValue_in_multExpr670 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000210L});
    public static final BitSet FOLLOW_68_in_multExpr674 = new BitSet(new long[]{0x4010000090200000L,0x0000000000000086L});
    public static final BitSet FOLLOW_73_in_multExpr677 = new BitSet(new long[]{0x4010000090200000L,0x0000000000000086L});
    public static final BitSet FOLLOW_97_in_multExpr680 = new BitSet(new long[]{0x4010000090200000L,0x0000000000000086L});
    public static final BitSet FOLLOW_exprValue_in_multExpr684 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000210L});
    public static final BitSet FOLLOW_71_in_exprValue695 = new BitSet(new long[]{0x4010000090200000L,0x0000000000000004L});
    public static final BitSet FOLLOW_cast_in_exprValue699 = new BitSet(new long[]{0x4010000090200000L});
    public static final BitSet FOLLOW_term_in_exprValue702 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cast_in_exprValue722 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_function_in_exprValue725 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cast_in_exprValue741 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_compExpr_in_exprValue744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_compExpr763 = new BitSet(new long[]{0x4010000090200000L,0x0000000000000086L});
    public static final BitSet FOLLOW_simpleExpr_in_compExpr766 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_compExpr768 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_cast777 = new BitSet(new long[]{0x0000000010000000L,0x0000000194300000L});
    public static final BitSet FOLLOW_type_in_cast779 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_cast781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_varlist793 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_dotname_in_varlist798 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_varlist802 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_dotname_in_varlist807 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000048L});
    public static final BitSet FOLLOW_67_in_varlist811 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_function819 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_dotname_in_function821 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_function823 = new BitSet(new long[]{0x4010000090200000L,0x000000000000008EL});
    public static final BitSet FOLLOW_fparamlist_in_function825 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_function828 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_predicate848 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_predicate850 = new BitSet(new long[]{0x4010000090200000L,0x0000000000000086L});
    public static final BitSet FOLLOW_paramlist_in_predicate852 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_predicate854 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_param_in_paramlist867 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_paramlist870 = new BitSet(new long[]{0x4010000090200000L,0x0000000000000086L});
    public static final BitSet FOLLOW_param_in_paramlist873 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_fparam_in_fparamlist881 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_fparamlist884 = new BitSet(new long[]{0x4010000090200000L,0x0000000000000086L});
    public static final BitSet FOLLOW_fparam_in_fparamlist887 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_simpleExpr_in_param896 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleExpr_in_fparam902 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_term909 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_term922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_term934 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UTF8_in_term946 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dotname_in_term959 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_table_decl975 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_table_decl977 = new BitSet(new long[]{0x0000000010000000L,0x0000000194300000L});
    public static final BitSet FOLLOW_decls_in_table_decl979 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_table_decl981 = new BitSet(new long[]{0x0000000000004000L,0x0000003C62000000L});
    public static final BitSet FOLLOW_table_opts_in_table_decl983 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_DOT_END_in_table_decl986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_t_opt_in_table_opts1019 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_table_opts1022 = new BitSet(new long[]{0x0000000000000000L,0x0000003C62000000L});
    public static final BitSet FOLLOW_t_opt_in_table_opts1025 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_101_in_t_opt1036 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_ID_in_t_opt1040 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_SORT_ORDER_in_t_opt1045 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_t_opt1064 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_ID_in_t_opt1066 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_t_opt1078 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_ID_in_t_opt1080 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_t_opt1092 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_t_opt1094 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_INT_in_t_opt1096 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_t_opt1098 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_100_in_t_opt1110 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_t_opt1118 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_t_opt1126 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_col_decls_in_decls1149 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_decls1152 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_decls1154 = new BitSet(new long[]{0x0000000010000000L,0x0000000194300000L});
    public static final BitSet FOLLOW_decls_in_decls1156 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_decls1158 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_col_decl_in_col_decls1184 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_col_decls1187 = new BitSet(new long[]{0x0000000010000000L,0x0000000194300000L});
    public static final BitSet FOLLOW_col_decl_in_col_decls1190 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_type_in_col_decl1200 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_ID_in_col_decl1202 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_col_decl1205 = new BitSet(new long[]{0x0000000280000000L});
    public static final BitSet FOLLOW_col_opt_in_col_decl1207 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_col_opt1233 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_col_opt1235 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_INT_in_col_opt1239 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ITER_DECL_in_col_opt1256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_95_in_type1269 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1272 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_type1274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_type1280 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1283 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_type1285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_type1293 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1296 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_type1298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_type1306 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1309 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_type1311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_85_in_type1319 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1322 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_type1324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_type1332 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1335 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_type1337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_type1344 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1347 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_type1349 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_dotname1361 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_DOT_ID_in_dotname1364 = new BitSet(new long[]{0x0000000000008002L});

}