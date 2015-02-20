// $ANTLR 3.4 /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g 2015-02-18 14:46:31

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "APPROX", "BODY", "CHAR", "CLEAR", "COL_DECL", "COL_DECLS", "COMMENT", "COMPOUND_EXPR", "CONCURRENT", "DECL", "DOT_END", "DOT_ID", "DROP", "ESC_SEQ", "EXPONENT", "EXPR", "FALSE", "FLOAT", "FUNC", "FUNCTION", "FUNC_ARG", "GROUP_BY", "HEAD", "HEX_DIGIT", "ID", "INDEX", "INDEX_BY", "INT", "ITER", "ITER_DECL", "KIND1", "KIND2", "MULTISET", "MULTI_ASSIGN", "NOT", "OCTAL_ESC", "OPT", "OPTION", "ORDER_BY", "PREDEFINED", "PREDICATE", "PROG", "QUERY", "RANGE", "RULE", "SORT_BY", "SORT_ORDER", "STRING", "TABLE_OPT", "TERM", "TRUE", "T_FLOAT", "T_INT", "T_STR", "T_UTF8", "T_VAR", "UNICODE_ESC", "UTF8", "WS", "'!='", "'$'", "'('", "')'", "'*'", "'+'", "','", "'-'", "'..'", "'/'", "':'", "':-'", "';'", "'<'", "'<='", "'='", "'=='", "'>'", "'>='", "'?-'", "'Object'", "'String'", "'['", "']'", "'clear'", "'concurrent'", "'double'", "'drop'", "'float'", "'groupby'", "'indexby'", "'int'", "'long'", "'mod'", "'multiset'", "'orderby'", "'predefined'", "'sortby'"
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
    public static final int T__100=100;
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
    public String getGrammarFileName() { return "/Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g"; }


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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:87:1: prog : ( stat )+ EOF ;
    public final SociaLiteParser.prog_return prog() throws RecognitionException {
        SociaLiteParser.prog_return retval = new SociaLiteParser.prog_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token EOF2=null;
        SociaLiteParser.stat_return stat1 =null;


        CommonTree EOF2_tree=null;

        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:87:6: ( ( stat )+ EOF )
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:87:7: ( stat )+ EOF
            {
            root_0 = (CommonTree)adaptor.nil();


            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:87:7: ( stat )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==ID||LA1_0==82||LA1_0==87||LA1_0==90) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:87:7: stat
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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:88:1: stat : ( table_decl | rule | query | table_stmt );
    public final SociaLiteParser.stat_return stat() throws RecognitionException {
        SociaLiteParser.stat_return retval = new SociaLiteParser.stat_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SociaLiteParser.table_decl_return table_decl3 =null;

        SociaLiteParser.rule_return rule4 =null;

        SociaLiteParser.query_return query5 =null;

        SociaLiteParser.table_stmt_return table_stmt6 =null;



        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:88:6: ( table_decl | rule | query | table_stmt )
            int alt2=4;
            switch ( input.LA(1) ) {
            case ID:
                {
                int LA2_1 = input.LA(2);

                if ( (LA2_1==65) ) {
                    switch ( input.LA(3) ) {
                    case 83:
                    case 84:
                    case 89:
                    case 91:
                    case 94:
                    case 95:
                        {
                        alt2=1;
                        }
                        break;
                    case ID:
                        {
                        int LA2_6 = input.LA(4);

                        if ( (LA2_6==ID||LA2_6==85) ) {
                            alt2=1;
                        }
                        else if ( (LA2_6==DOT_ID||(LA2_6 >= 66 && LA2_6 <= 70)||LA2_6==72||LA2_6==96) ) {
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
            case 90:
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:88:7: table_decl
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_table_decl_in_stat362);
                    table_decl3=table_decl();

                    state._fsp--;

                    adaptor.addChild(root_0, table_decl3.getTree());

                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:89:3: rule
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_rule_in_stat366);
                    rule4=rule();

                    state._fsp--;

                    adaptor.addChild(root_0, rule4.getTree());

                    }
                    break;
                case 3 :
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:90:3: query
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_query_in_stat370);
                    query5=query();

                    state._fsp--;

                    adaptor.addChild(root_0, query5.getTree());

                    }
                    break;
                case 4 :
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:91:3: table_stmt
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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:93:1: table_stmt : ( 'clear' ID DOT_END -> ^( CLEAR ID ) | 'drop' ID DOT_END -> ^( DROP ( ID )? ) );
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
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_DOT_END=new RewriteRuleTokenStream(adaptor,"token DOT_END");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");

        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:93:12: ( 'clear' ID DOT_END -> ^( CLEAR ID ) | 'drop' ID DOT_END -> ^( DROP ( ID )? ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==87) ) {
                alt3=1;
            }
            else if ( (LA3_0==90) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;

            }
            switch (alt3) {
                case 1 :
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:93:14: 'clear' ID DOT_END
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
                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:93:36: ^( CLEAR ID )
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:94:3: 'drop' ID DOT_END
                    {
                    string_literal10=(Token)match(input,90,FOLLOW_90_in_table_stmt400);  
                    stream_90.add(string_literal10);


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
                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:94:24: ^( DROP ( ID )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(DROP, "DROP")
                        , root_1);

                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:94:31: ( ID )?
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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:97:1: query : '?-' predicate DOT_END -> ^( QUERY predicate ) ;
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
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:97:7: ( '?-' predicate DOT_END -> ^( QUERY predicate ) )
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:97:8: '?-' predicate DOT_END
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
                // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:97:34: ^( QUERY predicate )
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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:100:1: rule : head ':-' body1= litlist ( DOT_END | ( ';' ':-' body2= litlist DOT_END ) ) -> ^( RULE ^( HEAD head ) ^( BODY $body1) ^( BODY ( $body2)? ) ) ;
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

        SociaLiteParser.head_return head16 =null;


        CommonTree string_literal17_tree=null;
        CommonTree DOT_END18_tree=null;
        CommonTree char_literal19_tree=null;
        CommonTree string_literal20_tree=null;
        CommonTree DOT_END21_tree=null;
        RewriteRuleTokenStream stream_DOT_END=new RewriteRuleTokenStream(adaptor,"token DOT_END");
        RewriteRuleTokenStream stream_74=new RewriteRuleTokenStream(adaptor,"token 74");
        RewriteRuleTokenStream stream_75=new RewriteRuleTokenStream(adaptor,"token 75");
        RewriteRuleSubtreeStream stream_litlist=new RewriteRuleSubtreeStream(adaptor,"rule litlist");
        RewriteRuleSubtreeStream stream_head=new RewriteRuleSubtreeStream(adaptor,"rule head");
        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:100:9: ( head ':-' body1= litlist ( DOT_END | ( ';' ':-' body2= litlist DOT_END ) ) -> ^( RULE ^( HEAD head ) ^( BODY $body1) ^( BODY ( $body2)? ) ) )
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:100:11: head ':-' body1= litlist ( DOT_END | ( ';' ':-' body2= litlist DOT_END ) )
            {
            pushFollow(FOLLOW_head_in_rule447);
            head16=head();

            state._fsp--;

            stream_head.add(head16.getTree());

            string_literal17=(Token)match(input,74,FOLLOW_74_in_rule449);  
            stream_74.add(string_literal17);


            pushFollow(FOLLOW_litlist_in_rule453);
            body1=litlist();

            state._fsp--;

            stream_litlist.add(body1.getTree());

            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:101:17: ( DOT_END | ( ';' ':-' body2= litlist DOT_END ) )
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:101:18: DOT_END
                    {
                    DOT_END18=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_rule473);  
                    stream_DOT_END.add(DOT_END18);


                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:101:28: ( ';' ':-' body2= litlist DOT_END )
                    {
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:101:28: ( ';' ':-' body2= litlist DOT_END )
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:101:29: ';' ':-' body2= litlist DOT_END
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
            // elements: body1, body2, head
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
            // 102:11: -> ^( RULE ^( HEAD head ) ^( BODY $body1) ^( BODY ( $body2)? ) )
            {
                // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:102:14: ^( RULE ^( HEAD head ) ^( BODY $body1) ^( BODY ( $body2)? ) )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(RULE, "RULE")
                , root_1);

                // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:102:21: ^( HEAD head )
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(HEAD, "HEAD")
                , root_2);

                adaptor.addChild(root_2, stream_head.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:102:34: ^( BODY $body1)
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(BODY, "BODY")
                , root_2);

                adaptor.addChild(root_2, stream_body1.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:102:49: ^( BODY ( $body2)? )
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(BODY, "BODY")
                , root_2);

                // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:102:58: ( $body2)?
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


    public static class head_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "head"
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:104:1: head : predicate ;
    public final SociaLiteParser.head_return head() throws RecognitionException {
        SociaLiteParser.head_return retval = new SociaLiteParser.head_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SociaLiteParser.predicate_return predicate22 =null;



        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:104:6: ( predicate )
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:104:8: predicate
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_predicate_in_head535);
            predicate22=predicate();

            state._fsp--;

            adaptor.addChild(root_0, predicate22.getTree());

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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:106:1: litlist : literal ( ',' ! literal )* ;
    public final SociaLiteParser.litlist_return litlist() throws RecognitionException {
        SociaLiteParser.litlist_return retval = new SociaLiteParser.litlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal24=null;
        SociaLiteParser.literal_return literal23 =null;

        SociaLiteParser.literal_return literal25 =null;


        CommonTree char_literal24_tree=null;

        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:106:9: ( literal ( ',' ! literal )* )
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:106:10: literal ( ',' ! literal )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_literal_in_litlist542);
            literal23=literal();

            state._fsp--;

            adaptor.addChild(root_0, literal23.getTree());

            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:106:18: ( ',' ! literal )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==69) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:106:19: ',' ! literal
            	    {
            	    char_literal24=(Token)match(input,69,FOLLOW_69_in_litlist545); 

            	    pushFollow(FOLLOW_literal_in_litlist548);
            	    literal25=literal();

            	    state._fsp--;

            	    adaptor.addChild(root_0, literal25.getTree());

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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:108:1: literal : ( predicate -> ^( PREDICATE predicate ) | NOT predicate -> ^( PREDICATE NOT predicate ) | expr -> ^( EXPR expr ) );
    public final SociaLiteParser.literal_return literal() throws RecognitionException {
        SociaLiteParser.literal_return retval = new SociaLiteParser.literal_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token NOT27=null;
        SociaLiteParser.predicate_return predicate26 =null;

        SociaLiteParser.predicate_return predicate28 =null;

        SociaLiteParser.expr_return expr29 =null;


        CommonTree NOT27_tree=null;
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleSubtreeStream stream_predicate=new RewriteRuleSubtreeStream(adaptor,"rule predicate");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:108:9: ( predicate -> ^( PREDICATE predicate ) | NOT predicate -> ^( PREDICATE NOT predicate ) | expr -> ^( EXPR expr ) )
            int alt6=3;
            switch ( input.LA(1) ) {
            case ID:
                {
                int LA6_1 = input.LA(2);

                if ( (LA6_1==65) ) {
                    alt6=1;
                }
                else if ( (LA6_1==DOT_ID||LA6_1==63||(LA6_1 >= 67 && LA6_1 <= 68)||LA6_1==70||LA6_1==72||(LA6_1 >= 76 && LA6_1 <= 81)||LA6_1==96) ) {
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:108:10: predicate
                    {
                    pushFollow(FOLLOW_predicate_in_literal557);
                    predicate26=predicate();

                    state._fsp--;

                    stream_predicate.add(predicate26.getTree());

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
                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:108:23: ^( PREDICATE predicate )
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:109:4: NOT predicate
                    {
                    NOT27=(Token)match(input,NOT,FOLLOW_NOT_in_literal571);  
                    stream_NOT.add(NOT27);


                    pushFollow(FOLLOW_predicate_in_literal573);
                    predicate28=predicate();

                    state._fsp--;

                    stream_predicate.add(predicate28.getTree());

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
                    // 109:18: -> ^( PREDICATE NOT predicate )
                    {
                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:109:21: ^( PREDICATE NOT predicate )
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:110:3: expr
                    {
                    pushFollow(FOLLOW_expr_in_literal587);
                    expr29=expr();

                    state._fsp--;

                    stream_expr.add(expr29.getTree());

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
                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:110:11: ^( EXPR expr )
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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:112:1: expr : ( simpleExpr ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^) simpleExpr | varlist '=' ( cast )? function -> ^( MULTI_ASSIGN varlist function ( cast )? ) );
    public final SociaLiteParser.expr_return expr() throws RecognitionException {
        SociaLiteParser.expr_return retval = new SociaLiteParser.expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal31=null;
        Token char_literal32=null;
        Token char_literal33=null;
        Token string_literal34=null;
        Token string_literal35=null;
        Token string_literal36=null;
        Token string_literal37=null;
        Token char_literal40=null;
        SociaLiteParser.simpleExpr_return simpleExpr30 =null;

        SociaLiteParser.simpleExpr_return simpleExpr38 =null;

        SociaLiteParser.varlist_return varlist39 =null;

        SociaLiteParser.cast_return cast41 =null;

        SociaLiteParser.function_return function42 =null;


        CommonTree char_literal31_tree=null;
        CommonTree char_literal32_tree=null;
        CommonTree char_literal33_tree=null;
        CommonTree string_literal34_tree=null;
        CommonTree string_literal35_tree=null;
        CommonTree string_literal36_tree=null;
        CommonTree string_literal37_tree=null;
        CommonTree char_literal40_tree=null;
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleSubtreeStream stream_varlist=new RewriteRuleSubtreeStream(adaptor,"rule varlist");
        RewriteRuleSubtreeStream stream_cast=new RewriteRuleSubtreeStream(adaptor,"rule cast");
        RewriteRuleSubtreeStream stream_function=new RewriteRuleSubtreeStream(adaptor,"rule function");
        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:112:5: ( simpleExpr ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^) simpleExpr | varlist '=' ( cast )? function -> ^( MULTI_ASSIGN varlist function ( cast )? ) )
            int alt9=2;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:112:7: simpleExpr ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^) simpleExpr
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_simpleExpr_in_expr604);
                    simpleExpr30=simpleExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, simpleExpr30.getTree());

                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:112:19: ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^)
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
                            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:112:20: '<' ^
                            {
                            char_literal31=(Token)match(input,76,FOLLOW_76_in_expr608); 
                            char_literal31_tree = 
                            (CommonTree)adaptor.create(char_literal31)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(char_literal31_tree, root_0);


                            }
                            break;
                        case 2 :
                            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:112:27: '>' ^
                            {
                            char_literal32=(Token)match(input,80,FOLLOW_80_in_expr613); 
                            char_literal32_tree = 
                            (CommonTree)adaptor.create(char_literal32)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(char_literal32_tree, root_0);


                            }
                            break;
                        case 3 :
                            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:112:34: '=' ^
                            {
                            char_literal33=(Token)match(input,78,FOLLOW_78_in_expr618); 
                            char_literal33_tree = 
                            (CommonTree)adaptor.create(char_literal33)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(char_literal33_tree, root_0);


                            }
                            break;
                        case 4 :
                            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:112:40: '!=' ^
                            {
                            string_literal34=(Token)match(input,63,FOLLOW_63_in_expr622); 
                            string_literal34_tree = 
                            (CommonTree)adaptor.create(string_literal34)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(string_literal34_tree, root_0);


                            }
                            break;
                        case 5 :
                            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:112:48: '==' ^
                            {
                            string_literal35=(Token)match(input,79,FOLLOW_79_in_expr627); 
                            string_literal35_tree = 
                            (CommonTree)adaptor.create(string_literal35)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(string_literal35_tree, root_0);


                            }
                            break;
                        case 6 :
                            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:112:56: '>=' ^
                            {
                            string_literal36=(Token)match(input,81,FOLLOW_81_in_expr632); 
                            string_literal36_tree = 
                            (CommonTree)adaptor.create(string_literal36)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(string_literal36_tree, root_0);


                            }
                            break;
                        case 7 :
                            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:112:64: '<=' ^
                            {
                            string_literal37=(Token)match(input,77,FOLLOW_77_in_expr637); 
                            string_literal37_tree = 
                            (CommonTree)adaptor.create(string_literal37)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(string_literal37_tree, root_0);


                            }
                            break;

                    }


                    pushFollow(FOLLOW_simpleExpr_in_expr642);
                    simpleExpr38=simpleExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, simpleExpr38.getTree());

                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:113:4: varlist '=' ( cast )? function
                    {
                    pushFollow(FOLLOW_varlist_in_expr647);
                    varlist39=varlist();

                    state._fsp--;

                    stream_varlist.add(varlist39.getTree());

                    char_literal40=(Token)match(input,78,FOLLOW_78_in_expr649);  
                    stream_78.add(char_literal40);


                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:113:16: ( cast )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==65) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:113:16: cast
                            {
                            pushFollow(FOLLOW_cast_in_expr651);
                            cast41=cast();

                            state._fsp--;

                            stream_cast.add(cast41.getTree());

                            }
                            break;

                    }


                    pushFollow(FOLLOW_function_in_expr654);
                    function42=function();

                    state._fsp--;

                    stream_function.add(function42.getTree());

                    // AST REWRITE
                    // elements: cast, function, varlist
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
                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:113:34: ^( MULTI_ASSIGN varlist function ( cast )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(MULTI_ASSIGN, "MULTI_ASSIGN")
                        , root_1);

                        adaptor.addChild(root_1, stream_varlist.nextTree());

                        adaptor.addChild(root_1, stream_function.nextTree());

                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:113:66: ( cast )?
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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:115:1: simpleExpr : multExpr ( ( '+' ^| '-' ^) multExpr )* ;
    public final SociaLiteParser.simpleExpr_return simpleExpr() throws RecognitionException {
        SociaLiteParser.simpleExpr_return retval = new SociaLiteParser.simpleExpr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal44=null;
        Token char_literal45=null;
        SociaLiteParser.multExpr_return multExpr43 =null;

        SociaLiteParser.multExpr_return multExpr46 =null;


        CommonTree char_literal44_tree=null;
        CommonTree char_literal45_tree=null;

        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:115:11: ( multExpr ( ( '+' ^| '-' ^) multExpr )* )
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:115:13: multExpr ( ( '+' ^| '-' ^) multExpr )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_multExpr_in_simpleExpr676);
            multExpr43=multExpr();

            state._fsp--;

            adaptor.addChild(root_0, multExpr43.getTree());

            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:115:23: ( ( '+' ^| '-' ^) multExpr )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==68||LA11_0==70) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:115:24: ( '+' ^| '-' ^) multExpr
            	    {
            	    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:115:24: ( '+' ^| '-' ^)
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
            	            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:115:25: '+' ^
            	            {
            	            char_literal44=(Token)match(input,68,FOLLOW_68_in_simpleExpr681); 
            	            char_literal44_tree = 
            	            (CommonTree)adaptor.create(char_literal44)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal44_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:115:30: '-' ^
            	            {
            	            char_literal45=(Token)match(input,70,FOLLOW_70_in_simpleExpr684); 
            	            char_literal45_tree = 
            	            (CommonTree)adaptor.create(char_literal45)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal45_tree, root_0);


            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_multExpr_in_simpleExpr688);
            	    multExpr46=multExpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, multExpr46.getTree());

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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:117:1: multExpr : exprValue ( ( '*' ^| '/' ^| 'mod' ^) exprValue )* ;
    public final SociaLiteParser.multExpr_return multExpr() throws RecognitionException {
        SociaLiteParser.multExpr_return retval = new SociaLiteParser.multExpr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal48=null;
        Token char_literal49=null;
        Token string_literal50=null;
        SociaLiteParser.exprValue_return exprValue47 =null;

        SociaLiteParser.exprValue_return exprValue51 =null;


        CommonTree char_literal48_tree=null;
        CommonTree char_literal49_tree=null;
        CommonTree string_literal50_tree=null;

        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:117:9: ( exprValue ( ( '*' ^| '/' ^| 'mod' ^) exprValue )* )
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:117:11: exprValue ( ( '*' ^| '/' ^| 'mod' ^) exprValue )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_exprValue_in_multExpr699);
            exprValue47=exprValue();

            state._fsp--;

            adaptor.addChild(root_0, exprValue47.getTree());

            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:117:21: ( ( '*' ^| '/' ^| 'mod' ^) exprValue )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==67||LA13_0==72||LA13_0==96) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:117:22: ( '*' ^| '/' ^| 'mod' ^) exprValue
            	    {
            	    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:117:22: ( '*' ^| '/' ^| 'mod' ^)
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
            	    case 96:
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
            	            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:117:23: '*' ^
            	            {
            	            char_literal48=(Token)match(input,67,FOLLOW_67_in_multExpr703); 
            	            char_literal48_tree = 
            	            (CommonTree)adaptor.create(char_literal48)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal48_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:117:28: '/' ^
            	            {
            	            char_literal49=(Token)match(input,72,FOLLOW_72_in_multExpr706); 
            	            char_literal49_tree = 
            	            (CommonTree)adaptor.create(char_literal49)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal49_tree, root_0);


            	            }
            	            break;
            	        case 3 :
            	            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:117:33: 'mod' ^
            	            {
            	            string_literal50=(Token)match(input,96,FOLLOW_96_in_multExpr709); 
            	            string_literal50_tree = 
            	            (CommonTree)adaptor.create(string_literal50)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(string_literal50_tree, root_0);


            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_exprValue_in_multExpr713);
            	    exprValue51=exprValue();

            	    state._fsp--;

            	    adaptor.addChild(root_0, exprValue51.getTree());

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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:118:1: exprValue : ( (neg= '-' )? ( cast )? term -> ^( TERM term ( $neg)? ( cast )? ) | ( cast )? function -> ^( FUNCTION function ( cast )? ) | ( cast )? compExpr -> ^( COMPOUND_EXPR compExpr ( cast )? ) );
    public final SociaLiteParser.exprValue_return exprValue() throws RecognitionException {
        SociaLiteParser.exprValue_return retval = new SociaLiteParser.exprValue_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token neg=null;
        SociaLiteParser.cast_return cast52 =null;

        SociaLiteParser.term_return term53 =null;

        SociaLiteParser.cast_return cast54 =null;

        SociaLiteParser.function_return function55 =null;

        SociaLiteParser.cast_return cast56 =null;

        SociaLiteParser.compExpr_return compExpr57 =null;


        CommonTree neg_tree=null;
        RewriteRuleTokenStream stream_70=new RewriteRuleTokenStream(adaptor,"token 70");
        RewriteRuleSubtreeStream stream_cast=new RewriteRuleSubtreeStream(adaptor,"rule cast");
        RewriteRuleSubtreeStream stream_term=new RewriteRuleSubtreeStream(adaptor,"rule term");
        RewriteRuleSubtreeStream stream_compExpr=new RewriteRuleSubtreeStream(adaptor,"rule compExpr");
        RewriteRuleSubtreeStream stream_function=new RewriteRuleSubtreeStream(adaptor,"rule function");
        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:118:10: ( (neg= '-' )? ( cast )? term -> ^( TERM term ( $neg)? ( cast )? ) | ( cast )? function -> ^( FUNCTION function ( cast )? ) | ( cast )? compExpr -> ^( COMPOUND_EXPR compExpr ( cast )? ) )
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
                case 94:
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
                case 95:
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
                case 91:
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
                case 89:
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
                        case 96:
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
                    case 96:
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:118:12: (neg= '-' )? ( cast )? term
                    {
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:118:12: (neg= '-' )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==70) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:118:13: neg= '-'
                            {
                            neg=(Token)match(input,70,FOLLOW_70_in_exprValue724);  
                            stream_70.add(neg);


                            }
                            break;

                    }


                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:118:23: ( cast )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==65) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:118:23: cast
                            {
                            pushFollow(FOLLOW_cast_in_exprValue728);
                            cast52=cast();

                            state._fsp--;

                            stream_cast.add(cast52.getTree());

                            }
                            break;

                    }


                    pushFollow(FOLLOW_term_in_exprValue731);
                    term53=term();

                    state._fsp--;

                    stream_term.add(term53.getTree());

                    // AST REWRITE
                    // elements: term, cast, neg
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
                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:118:37: ^( TERM term ( $neg)? ( cast )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(TERM, "TERM")
                        , root_1);

                        adaptor.addChild(root_1, stream_term.nextTree());

                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:118:50: ( $neg)?
                        if ( stream_neg.hasNext() ) {
                            adaptor.addChild(root_1, stream_neg.nextNode());

                        }
                        stream_neg.reset();

                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:118:55: ( cast )?
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:119:4: ( cast )? function
                    {
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:119:4: ( cast )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==65) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:119:4: cast
                            {
                            pushFollow(FOLLOW_cast_in_exprValue751);
                            cast54=cast();

                            state._fsp--;

                            stream_cast.add(cast54.getTree());

                            }
                            break;

                    }


                    pushFollow(FOLLOW_function_in_exprValue754);
                    function55=function();

                    state._fsp--;

                    stream_function.add(function55.getTree());

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
                    // 119:19: -> ^( FUNCTION function ( cast )? )
                    {
                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:119:22: ^( FUNCTION function ( cast )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(FUNCTION, "FUNCTION")
                        , root_1);

                        adaptor.addChild(root_1, stream_function.nextTree());

                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:119:42: ( cast )?
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:120:4: ( cast )? compExpr
                    {
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:120:4: ( cast )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==65) ) {
                        int LA17_1 = input.LA(2);

                        if ( ((LA17_1 >= 83 && LA17_1 <= 84)||LA17_1==89||LA17_1==91||(LA17_1 >= 94 && LA17_1 <= 95)) ) {
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
                            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:120:4: cast
                            {
                            pushFollow(FOLLOW_cast_in_exprValue770);
                            cast56=cast();

                            state._fsp--;

                            stream_cast.add(cast56.getTree());

                            }
                            break;

                    }


                    pushFollow(FOLLOW_compExpr_in_exprValue773);
                    compExpr57=compExpr();

                    state._fsp--;

                    stream_compExpr.add(compExpr57.getTree());

                    // AST REWRITE
                    // elements: compExpr, cast
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
                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:120:22: ^( COMPOUND_EXPR compExpr ( cast )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(COMPOUND_EXPR, "COMPOUND_EXPR")
                        , root_1);

                        adaptor.addChild(root_1, stream_compExpr.nextTree());

                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:120:47: ( cast )?
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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:122:1: compExpr : '(' ! simpleExpr ')' !;
    public final SociaLiteParser.compExpr_return compExpr() throws RecognitionException {
        SociaLiteParser.compExpr_return retval = new SociaLiteParser.compExpr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal58=null;
        Token char_literal60=null;
        SociaLiteParser.simpleExpr_return simpleExpr59 =null;


        CommonTree char_literal58_tree=null;
        CommonTree char_literal60_tree=null;

        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:122:9: ( '(' ! simpleExpr ')' !)
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:122:11: '(' ! simpleExpr ')' !
            {
            root_0 = (CommonTree)adaptor.nil();


            char_literal58=(Token)match(input,65,FOLLOW_65_in_compExpr792); 

            pushFollow(FOLLOW_simpleExpr_in_compExpr795);
            simpleExpr59=simpleExpr();

            state._fsp--;

            adaptor.addChild(root_0, simpleExpr59.getTree());

            char_literal60=(Token)match(input,66,FOLLOW_66_in_compExpr797); 

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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:123:1: cast : '(' type ')' -> type ;
    public final SociaLiteParser.cast_return cast() throws RecognitionException {
        SociaLiteParser.cast_return retval = new SociaLiteParser.cast_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal61=null;
        Token char_literal63=null;
        SociaLiteParser.type_return type62 =null;


        CommonTree char_literal61_tree=null;
        CommonTree char_literal63_tree=null;
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:123:5: ( '(' type ')' -> type )
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:123:7: '(' type ')'
            {
            char_literal61=(Token)match(input,65,FOLLOW_65_in_cast806);  
            stream_65.add(char_literal61);


            pushFollow(FOLLOW_type_in_cast808);
            type62=type();

            state._fsp--;

            stream_type.add(type62.getTree());

            char_literal63=(Token)match(input,66,FOLLOW_66_in_cast810);  
            stream_66.add(char_literal63);


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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:124:1: varlist : '(' !e1= dotname ( ',' !e2= dotname )+ ')' !;
    public final SociaLiteParser.varlist_return varlist() throws RecognitionException {
        SociaLiteParser.varlist_return retval = new SociaLiteParser.varlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal64=null;
        Token char_literal65=null;
        Token char_literal66=null;
        SociaLiteParser.dotname_return e1 =null;

        SociaLiteParser.dotname_return e2 =null;


        CommonTree char_literal64_tree=null;
        CommonTree char_literal65_tree=null;
        CommonTree char_literal66_tree=null;

        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:124:8: ( '(' !e1= dotname ( ',' !e2= dotname )+ ')' !)
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:124:10: '(' !e1= dotname ( ',' !e2= dotname )+ ')' !
            {
            root_0 = (CommonTree)adaptor.nil();


            char_literal64=(Token)match(input,65,FOLLOW_65_in_varlist822); 

            pushFollow(FOLLOW_dotname_in_varlist827);
            e1=dotname();

            state._fsp--;

            adaptor.addChild(root_0, e1.getTree());

            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:124:27: ( ',' !e2= dotname )+
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
            	    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:124:28: ',' !e2= dotname
            	    {
            	    char_literal65=(Token)match(input,69,FOLLOW_69_in_varlist831); 

            	    pushFollow(FOLLOW_dotname_in_varlist836);
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


            char_literal66=(Token)match(input,66,FOLLOW_66_in_varlist840); 

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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:125:1: function : '$' dotname '(' ( fparamlist )? ')' -> ^( FUNC dotname ( fparamlist )? ) ;
    public final SociaLiteParser.function_return function() throws RecognitionException {
        SociaLiteParser.function_return retval = new SociaLiteParser.function_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal67=null;
        Token char_literal69=null;
        Token char_literal71=null;
        SociaLiteParser.dotname_return dotname68 =null;

        SociaLiteParser.fparamlist_return fparamlist70 =null;


        CommonTree char_literal67_tree=null;
        CommonTree char_literal69_tree=null;
        CommonTree char_literal71_tree=null;
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleSubtreeStream stream_fparamlist=new RewriteRuleSubtreeStream(adaptor,"rule fparamlist");
        RewriteRuleSubtreeStream stream_dotname=new RewriteRuleSubtreeStream(adaptor,"rule dotname");
        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:125:9: ( '$' dotname '(' ( fparamlist )? ')' -> ^( FUNC dotname ( fparamlist )? ) )
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:125:11: '$' dotname '(' ( fparamlist )? ')'
            {
            char_literal67=(Token)match(input,64,FOLLOW_64_in_function848);  
            stream_64.add(char_literal67);


            pushFollow(FOLLOW_dotname_in_function850);
            dotname68=dotname();

            state._fsp--;

            stream_dotname.add(dotname68.getTree());

            char_literal69=(Token)match(input,65,FOLLOW_65_in_function852);  
            stream_65.add(char_literal69);


            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:125:27: ( fparamlist )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==FLOAT||LA20_0==ID||LA20_0==INT||LA20_0==STRING||LA20_0==UTF8||(LA20_0 >= 64 && LA20_0 <= 65)||LA20_0==70) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:125:27: fparamlist
                    {
                    pushFollow(FOLLOW_fparamlist_in_function854);
                    fparamlist70=fparamlist();

                    state._fsp--;

                    stream_fparamlist.add(fparamlist70.getTree());

                    }
                    break;

            }


            char_literal71=(Token)match(input,66,FOLLOW_66_in_function857);  
            stream_66.add(char_literal71);


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
                // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:125:46: ^( FUNC dotname ( fparamlist )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(FUNC, "FUNC")
                , root_1);

                adaptor.addChild(root_1, stream_dotname.nextTree());

                // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:125:61: ( fparamlist )?
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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:128:1: predicate : ID '(' paramlist ')' -> ID paramlist ;
    public final SociaLiteParser.predicate_return predicate() throws RecognitionException {
        SociaLiteParser.predicate_return retval = new SociaLiteParser.predicate_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token ID72=null;
        Token char_literal73=null;
        Token char_literal75=null;
        SociaLiteParser.paramlist_return paramlist74 =null;


        CommonTree ID72_tree=null;
        CommonTree char_literal73_tree=null;
        CommonTree char_literal75_tree=null;
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleSubtreeStream stream_paramlist=new RewriteRuleSubtreeStream(adaptor,"rule paramlist");
        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:128:11: ( ID '(' paramlist ')' -> ID paramlist )
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:128:12: ID '(' paramlist ')'
            {
            ID72=(Token)match(input,ID,FOLLOW_ID_in_predicate877);  
            stream_ID.add(ID72);


            char_literal73=(Token)match(input,65,FOLLOW_65_in_predicate879);  
            stream_65.add(char_literal73);


            pushFollow(FOLLOW_paramlist_in_predicate881);
            paramlist74=paramlist();

            state._fsp--;

            stream_paramlist.add(paramlist74.getTree());

            char_literal75=(Token)match(input,66,FOLLOW_66_in_predicate883);  
            stream_66.add(char_literal75);


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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:130:1: paramlist : param ( ',' ! param )* ;
    public final SociaLiteParser.paramlist_return paramlist() throws RecognitionException {
        SociaLiteParser.paramlist_return retval = new SociaLiteParser.paramlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal77=null;
        SociaLiteParser.param_return param76 =null;

        SociaLiteParser.param_return param78 =null;


        CommonTree char_literal77_tree=null;

        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:130:11: ( param ( ',' ! param )* )
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:130:12: param ( ',' ! param )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_param_in_paramlist896);
            param76=param();

            state._fsp--;

            adaptor.addChild(root_0, param76.getTree());

            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:130:18: ( ',' ! param )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==69) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:130:19: ',' ! param
            	    {
            	    char_literal77=(Token)match(input,69,FOLLOW_69_in_paramlist899); 

            	    pushFollow(FOLLOW_param_in_paramlist902);
            	    param78=param();

            	    state._fsp--;

            	    adaptor.addChild(root_0, param78.getTree());

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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:131:1: fparamlist : fparam ( ',' ! fparam )* ;
    public final SociaLiteParser.fparamlist_return fparamlist() throws RecognitionException {
        SociaLiteParser.fparamlist_return retval = new SociaLiteParser.fparamlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal80=null;
        SociaLiteParser.fparam_return fparam79 =null;

        SociaLiteParser.fparam_return fparam81 =null;


        CommonTree char_literal80_tree=null;

        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:131:12: ( fparam ( ',' ! fparam )* )
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:131:13: fparam ( ',' ! fparam )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_fparam_in_fparamlist910);
            fparam79=fparam();

            state._fsp--;

            adaptor.addChild(root_0, fparam79.getTree());

            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:131:20: ( ',' ! fparam )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==69) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:131:21: ',' ! fparam
            	    {
            	    char_literal80=(Token)match(input,69,FOLLOW_69_in_fparamlist913); 

            	    pushFollow(FOLLOW_fparam_in_fparamlist916);
            	    fparam81=fparam();

            	    state._fsp--;

            	    adaptor.addChild(root_0, fparam81.getTree());

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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:133:1: param : simpleExpr ;
    public final SociaLiteParser.param_return param() throws RecognitionException {
        SociaLiteParser.param_return retval = new SociaLiteParser.param_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SociaLiteParser.simpleExpr_return simpleExpr82 =null;



        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:133:7: ( simpleExpr )
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:133:8: simpleExpr
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_simpleExpr_in_param925);
            simpleExpr82=simpleExpr();

            state._fsp--;

            adaptor.addChild(root_0, simpleExpr82.getTree());

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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:134:1: fparam : simpleExpr ;
    public final SociaLiteParser.fparam_return fparam() throws RecognitionException {
        SociaLiteParser.fparam_return retval = new SociaLiteParser.fparam_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SociaLiteParser.simpleExpr_return simpleExpr83 =null;



        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:134:8: ( simpleExpr )
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:134:9: simpleExpr
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_simpleExpr_in_fparam931);
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
    // $ANTLR end "fparam"


    public static class term_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "term"
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:136:1: term : ( INT -> ^( T_INT INT ) | FLOAT -> ^( T_FLOAT FLOAT ) | STRING -> ^( T_STR STRING ) | UTF8 -> ^( T_UTF8 UTF8 ) | dotname -> ^( T_VAR dotname ) );
    public final SociaLiteParser.term_return term() throws RecognitionException {
        SociaLiteParser.term_return retval = new SociaLiteParser.term_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token INT84=null;
        Token FLOAT85=null;
        Token STRING86=null;
        Token UTF887=null;
        SociaLiteParser.dotname_return dotname88 =null;


        CommonTree INT84_tree=null;
        CommonTree FLOAT85_tree=null;
        CommonTree STRING86_tree=null;
        CommonTree UTF887_tree=null;
        RewriteRuleTokenStream stream_FLOAT=new RewriteRuleTokenStream(adaptor,"token FLOAT");
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleTokenStream stream_UTF8=new RewriteRuleTokenStream(adaptor,"token UTF8");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_dotname=new RewriteRuleSubtreeStream(adaptor,"rule dotname");
        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:136:5: ( INT -> ^( T_INT INT ) | FLOAT -> ^( T_FLOAT FLOAT ) | STRING -> ^( T_STR STRING ) | UTF8 -> ^( T_UTF8 UTF8 ) | dotname -> ^( T_VAR dotname ) )
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:136:7: INT
                    {
                    INT84=(Token)match(input,INT,FOLLOW_INT_in_term938);  
                    stream_INT.add(INT84);


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
                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:136:14: ^( T_INT INT )
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:137:3: FLOAT
                    {
                    FLOAT85=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_term951);  
                    stream_FLOAT.add(FLOAT85);


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
                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:137:12: ^( T_FLOAT FLOAT )
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:138:3: STRING
                    {
                    STRING86=(Token)match(input,STRING,FOLLOW_STRING_in_term963);  
                    stream_STRING.add(STRING86);


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
                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:138:13: ^( T_STR STRING )
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:139:3: UTF8
                    {
                    UTF887=(Token)match(input,UTF8,FOLLOW_UTF8_in_term975);  
                    stream_UTF8.add(UTF887);


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
                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:139:11: ^( T_UTF8 UTF8 )
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:140:3: dotname
                    {
                    pushFollow(FOLLOW_dotname_in_term988);
                    dotname88=dotname();

                    state._fsp--;

                    stream_dotname.add(dotname88.getTree());

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
                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:140:14: ^( T_VAR dotname )
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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:142:1: table_decl : ID '(' decls ')' ( table_opts )? DOT_END -> ^( DECL ID decls ^( OPTION ( table_opts )? ) ) ;
    public final SociaLiteParser.table_decl_return table_decl() throws RecognitionException {
        SociaLiteParser.table_decl_return retval = new SociaLiteParser.table_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token ID89=null;
        Token char_literal90=null;
        Token char_literal92=null;
        Token DOT_END94=null;
        SociaLiteParser.decls_return decls91 =null;

        SociaLiteParser.table_opts_return table_opts93 =null;


        CommonTree ID89_tree=null;
        CommonTree char_literal90_tree=null;
        CommonTree char_literal92_tree=null;
        CommonTree DOT_END94_tree=null;
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleTokenStream stream_DOT_END=new RewriteRuleTokenStream(adaptor,"token DOT_END");
        RewriteRuleSubtreeStream stream_table_opts=new RewriteRuleSubtreeStream(adaptor,"rule table_opts");
        RewriteRuleSubtreeStream stream_decls=new RewriteRuleSubtreeStream(adaptor,"rule decls");
        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:142:11: ( ID '(' decls ')' ( table_opts )? DOT_END -> ^( DECL ID decls ^( OPTION ( table_opts )? ) ) )
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:142:16: ID '(' decls ')' ( table_opts )? DOT_END
            {
            ID89=(Token)match(input,ID,FOLLOW_ID_in_table_decl1007);  
            stream_ID.add(ID89);


            char_literal90=(Token)match(input,65,FOLLOW_65_in_table_decl1010);  
            stream_65.add(char_literal90);


            pushFollow(FOLLOW_decls_in_table_decl1012);
            decls91=decls();

            state._fsp--;

            stream_decls.add(decls91.getTree());

            char_literal92=(Token)match(input,66,FOLLOW_66_in_table_decl1014);  
            stream_66.add(char_literal92);


            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:142:34: ( table_opts )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==88||(LA24_0 >= 92 && LA24_0 <= 93)||(LA24_0 >= 97 && LA24_0 <= 100)) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:142:34: table_opts
                    {
                    pushFollow(FOLLOW_table_opts_in_table_decl1016);
                    table_opts93=table_opts();

                    state._fsp--;

                    stream_table_opts.add(table_opts93.getTree());

                    }
                    break;

            }


            DOT_END94=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_table_decl1019);  
            stream_DOT_END.add(DOT_END94);


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
            // 142:55: -> ^( DECL ID decls ^( OPTION ( table_opts )? ) )
            {
                // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:142:58: ^( DECL ID decls ^( OPTION ( table_opts )? ) )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(DECL, "DECL")
                , root_1);

                adaptor.addChild(root_1, 
                stream_ID.nextNode()
                );

                adaptor.addChild(root_1, stream_decls.nextTree());

                // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:142:75: ^( OPTION ( table_opts )? )
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(OPTION, "OPTION")
                , root_2);

                // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:142:84: ( table_opts )?
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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:144:1: table_opts : t_opt ( ',' ! t_opt )* ;
    public final SociaLiteParser.table_opts_return table_opts() throws RecognitionException {
        SociaLiteParser.table_opts_return retval = new SociaLiteParser.table_opts_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal96=null;
        SociaLiteParser.t_opt_return t_opt95 =null;

        SociaLiteParser.t_opt_return t_opt97 =null;


        CommonTree char_literal96_tree=null;

        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:144:11: ( t_opt ( ',' ! t_opt )* )
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:144:13: t_opt ( ',' ! t_opt )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_t_opt_in_table_opts1053);
            t_opt95=t_opt();

            state._fsp--;

            adaptor.addChild(root_0, t_opt95.getTree());

            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:144:19: ( ',' ! t_opt )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==69) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:144:20: ',' ! t_opt
            	    {
            	    char_literal96=(Token)match(input,69,FOLLOW_69_in_table_opts1056); 

            	    pushFollow(FOLLOW_t_opt_in_table_opts1059);
            	    t_opt97=t_opt();

            	    state._fsp--;

            	    adaptor.addChild(root_0, t_opt97.getTree());

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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:146:1: t_opt : ( 'sortby' col= ID (order= SORT_ORDER )? -> ^( SORT_BY $col ( $order)? ) | 'orderby' ID -> ^( ORDER_BY ID ) | 'indexby' ID -> ^( INDEX_BY ID ) | 'groupby' '(' INT ')' -> ^( GROUP_BY INT ) | 'predefined' -> PREDEFINED | 'concurrent' -> CONCURRENT | 'multiset' -> MULTISET );
    public final SociaLiteParser.t_opt_return t_opt() throws RecognitionException {
        SociaLiteParser.t_opt_return retval = new SociaLiteParser.t_opt_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token col=null;
        Token order=null;
        Token string_literal98=null;
        Token string_literal99=null;
        Token ID100=null;
        Token string_literal101=null;
        Token ID102=null;
        Token string_literal103=null;
        Token char_literal104=null;
        Token INT105=null;
        Token char_literal106=null;
        Token string_literal107=null;
        Token string_literal108=null;
        Token string_literal109=null;

        CommonTree col_tree=null;
        CommonTree order_tree=null;
        CommonTree string_literal98_tree=null;
        CommonTree string_literal99_tree=null;
        CommonTree ID100_tree=null;
        CommonTree string_literal101_tree=null;
        CommonTree ID102_tree=null;
        CommonTree string_literal103_tree=null;
        CommonTree char_literal104_tree=null;
        CommonTree INT105_tree=null;
        CommonTree char_literal106_tree=null;
        CommonTree string_literal107_tree=null;
        CommonTree string_literal108_tree=null;
        CommonTree string_literal109_tree=null;
        RewriteRuleTokenStream stream_98=new RewriteRuleTokenStream(adaptor,"token 98");
        RewriteRuleTokenStream stream_97=new RewriteRuleTokenStream(adaptor,"token 97");
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleTokenStream stream_93=new RewriteRuleTokenStream(adaptor,"token 93");
        RewriteRuleTokenStream stream_92=new RewriteRuleTokenStream(adaptor,"token 92");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleTokenStream stream_SORT_ORDER=new RewriteRuleTokenStream(adaptor,"token SORT_ORDER");
        RewriteRuleTokenStream stream_99=new RewriteRuleTokenStream(adaptor,"token 99");
        RewriteRuleTokenStream stream_88=new RewriteRuleTokenStream(adaptor,"token 88");
        RewriteRuleTokenStream stream_100=new RewriteRuleTokenStream(adaptor,"token 100");

        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:146:7: ( 'sortby' col= ID (order= SORT_ORDER )? -> ^( SORT_BY $col ( $order)? ) | 'orderby' ID -> ^( ORDER_BY ID ) | 'indexby' ID -> ^( INDEX_BY ID ) | 'groupby' '(' INT ')' -> ^( GROUP_BY INT ) | 'predefined' -> PREDEFINED | 'concurrent' -> CONCURRENT | 'multiset' -> MULTISET )
            int alt27=7;
            switch ( input.LA(1) ) {
            case 100:
                {
                alt27=1;
                }
                break;
            case 98:
                {
                alt27=2;
                }
                break;
            case 93:
                {
                alt27=3;
                }
                break;
            case 92:
                {
                alt27=4;
                }
                break;
            case 99:
                {
                alt27=5;
                }
                break;
            case 88:
                {
                alt27=6;
                }
                break;
            case 97:
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:146:9: 'sortby' col= ID (order= SORT_ORDER )?
                    {
                    string_literal98=(Token)match(input,100,FOLLOW_100_in_t_opt1070);  
                    stream_100.add(string_literal98);


                    col=(Token)match(input,ID,FOLLOW_ID_in_t_opt1074);  
                    stream_ID.add(col);


                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:146:25: (order= SORT_ORDER )?
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==SORT_ORDER) ) {
                        alt26=1;
                    }
                    switch (alt26) {
                        case 1 :
                            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:146:26: order= SORT_ORDER
                            {
                            order=(Token)match(input,SORT_ORDER,FOLLOW_SORT_ORDER_in_t_opt1079);  
                            stream_SORT_ORDER.add(order);


                            }
                            break;

                    }


                    // AST REWRITE
                    // elements: col, order
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
                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:146:48: ^( SORT_BY $col ( $order)? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(SORT_BY, "SORT_BY")
                        , root_1);

                        adaptor.addChild(root_1, stream_col.nextNode());

                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:146:64: ( $order)?
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:147:3: 'orderby' ID
                    {
                    string_literal99=(Token)match(input,98,FOLLOW_98_in_t_opt1098);  
                    stream_98.add(string_literal99);


                    ID100=(Token)match(input,ID,FOLLOW_ID_in_t_opt1100);  
                    stream_ID.add(ID100);


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
                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:147:19: ^( ORDER_BY ID )
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:148:3: 'indexby' ID
                    {
                    string_literal101=(Token)match(input,93,FOLLOW_93_in_t_opt1112);  
                    stream_93.add(string_literal101);


                    ID102=(Token)match(input,ID,FOLLOW_ID_in_t_opt1114);  
                    stream_ID.add(ID102);


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
                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:148:19: ^( INDEX_BY ID )
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:149:3: 'groupby' '(' INT ')'
                    {
                    string_literal103=(Token)match(input,92,FOLLOW_92_in_t_opt1126);  
                    stream_92.add(string_literal103);


                    char_literal104=(Token)match(input,65,FOLLOW_65_in_t_opt1128);  
                    stream_65.add(char_literal104);


                    INT105=(Token)match(input,INT,FOLLOW_INT_in_t_opt1130);  
                    stream_INT.add(INT105);


                    char_literal106=(Token)match(input,66,FOLLOW_66_in_t_opt1132);  
                    stream_66.add(char_literal106);


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
                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:149:28: ^( GROUP_BY INT )
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:150:3: 'predefined'
                    {
                    string_literal107=(Token)match(input,99,FOLLOW_99_in_t_opt1144);  
                    stream_99.add(string_literal107);


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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:151:3: 'concurrent'
                    {
                    string_literal108=(Token)match(input,88,FOLLOW_88_in_t_opt1152);  
                    stream_88.add(string_literal108);


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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:152:3: 'multiset'
                    {
                    string_literal109=(Token)match(input,97,FOLLOW_97_in_t_opt1160);  
                    stream_97.add(string_literal109);


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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:156:1: decls : col_decls ( ',' '(' decls ')' )? -> ^( COL_DECLS col_decls ^( DECL ( decls )? ) ) ;
    public final SociaLiteParser.decls_return decls() throws RecognitionException {
        SociaLiteParser.decls_return retval = new SociaLiteParser.decls_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal111=null;
        Token char_literal112=null;
        Token char_literal114=null;
        SociaLiteParser.col_decls_return col_decls110 =null;

        SociaLiteParser.decls_return decls113 =null;


        CommonTree char_literal111_tree=null;
        CommonTree char_literal112_tree=null;
        CommonTree char_literal114_tree=null;
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_69=new RewriteRuleTokenStream(adaptor,"token 69");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleSubtreeStream stream_col_decls=new RewriteRuleSubtreeStream(adaptor,"rule col_decls");
        RewriteRuleSubtreeStream stream_decls=new RewriteRuleSubtreeStream(adaptor,"rule decls");
        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:156:7: ( col_decls ( ',' '(' decls ')' )? -> ^( COL_DECLS col_decls ^( DECL ( decls )? ) ) )
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:156:9: col_decls ( ',' '(' decls ')' )?
            {
            pushFollow(FOLLOW_col_decls_in_decls1183);
            col_decls110=col_decls();

            state._fsp--;

            stream_col_decls.add(col_decls110.getTree());

            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:156:19: ( ',' '(' decls ')' )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==69) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:156:20: ',' '(' decls ')'
                    {
                    char_literal111=(Token)match(input,69,FOLLOW_69_in_decls1186);  
                    stream_69.add(char_literal111);


                    char_literal112=(Token)match(input,65,FOLLOW_65_in_decls1188);  
                    stream_65.add(char_literal112);


                    pushFollow(FOLLOW_decls_in_decls1190);
                    decls113=decls();

                    state._fsp--;

                    stream_decls.add(decls113.getTree());

                    char_literal114=(Token)match(input,66,FOLLOW_66_in_decls1192);  
                    stream_66.add(char_literal114);


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
            // 156:40: -> ^( COL_DECLS col_decls ^( DECL ( decls )? ) )
            {
                // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:156:43: ^( COL_DECLS col_decls ^( DECL ( decls )? ) )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(COL_DECLS, "COL_DECLS")
                , root_1);

                adaptor.addChild(root_1, stream_col_decls.nextTree());

                // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:156:65: ^( DECL ( decls )? )
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(DECL, "DECL")
                , root_2);

                // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:156:72: ( decls )?
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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:158:1: col_decls : col_decl ( ',' ! col_decl )* ;
    public final SociaLiteParser.col_decls_return col_decls() throws RecognitionException {
        SociaLiteParser.col_decls_return retval = new SociaLiteParser.col_decls_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal116=null;
        SociaLiteParser.col_decl_return col_decl115 =null;

        SociaLiteParser.col_decl_return col_decl117 =null;


        CommonTree char_literal116_tree=null;

        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:158:10: ( col_decl ( ',' ! col_decl )* )
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:158:12: col_decl ( ',' ! col_decl )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_col_decl_in_col_decls1218);
            col_decl115=col_decl();

            state._fsp--;

            adaptor.addChild(root_0, col_decl115.getTree());

            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:158:21: ( ',' ! col_decl )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==69) ) {
                    int LA29_1 = input.LA(2);

                    if ( (LA29_1==ID||(LA29_1 >= 83 && LA29_1 <= 84)||LA29_1==89||LA29_1==91||(LA29_1 >= 94 && LA29_1 <= 95)) ) {
                        alt29=1;
                    }


                }


                switch (alt29) {
            	case 1 :
            	    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:158:22: ',' ! col_decl
            	    {
            	    char_literal116=(Token)match(input,69,FOLLOW_69_in_col_decls1221); 

            	    pushFollow(FOLLOW_col_decl_in_col_decls1224);
            	    col_decl117=col_decl();

            	    state._fsp--;

            	    adaptor.addChild(root_0, col_decl117.getTree());

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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:160:1: col_decl : type ID ( ':' col_opt )? -> ^( COL_DECL type ID ( col_opt )? ) ;
    public final SociaLiteParser.col_decl_return col_decl() throws RecognitionException {
        SociaLiteParser.col_decl_return retval = new SociaLiteParser.col_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token ID119=null;
        Token char_literal120=null;
        SociaLiteParser.type_return type118 =null;

        SociaLiteParser.col_opt_return col_opt121 =null;


        CommonTree ID119_tree=null;
        CommonTree char_literal120_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_73=new RewriteRuleTokenStream(adaptor,"token 73");
        RewriteRuleSubtreeStream stream_col_opt=new RewriteRuleSubtreeStream(adaptor,"rule col_opt");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:160:9: ( type ID ( ':' col_opt )? -> ^( COL_DECL type ID ( col_opt )? ) )
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:160:11: type ID ( ':' col_opt )?
            {
            pushFollow(FOLLOW_type_in_col_decl1234);
            type118=type();

            state._fsp--;

            stream_type.add(type118.getTree());

            ID119=(Token)match(input,ID,FOLLOW_ID_in_col_decl1236);  
            stream_ID.add(ID119);


            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:160:19: ( ':' col_opt )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==73) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:160:20: ':' col_opt
                    {
                    char_literal120=(Token)match(input,73,FOLLOW_73_in_col_decl1239);  
                    stream_73.add(char_literal120);


                    pushFollow(FOLLOW_col_opt_in_col_decl1241);
                    col_opt121=col_opt();

                    state._fsp--;

                    stream_col_opt.add(col_opt121.getTree());

                    }
                    break;

            }


            // AST REWRITE
            // elements: ID, col_opt, type
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
                // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:160:36: ^( COL_DECL type ID ( col_opt )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(COL_DECL, "COL_DECL")
                , root_1);

                adaptor.addChild(root_1, stream_type.nextTree());

                adaptor.addChild(root_1, 
                stream_ID.nextNode()
                );

                // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:160:55: ( col_opt )?
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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:162:1: col_opt : (i1= INT '..' i2= INT -> ^( RANGE $i1 $i2) | ITER_DECL -> ITER );
    public final SociaLiteParser.col_opt_return col_opt() throws RecognitionException {
        SociaLiteParser.col_opt_return retval = new SociaLiteParser.col_opt_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token i1=null;
        Token i2=null;
        Token string_literal122=null;
        Token ITER_DECL123=null;

        CommonTree i1_tree=null;
        CommonTree i2_tree=null;
        CommonTree string_literal122_tree=null;
        CommonTree ITER_DECL123_tree=null;
        RewriteRuleTokenStream stream_ITER_DECL=new RewriteRuleTokenStream(adaptor,"token ITER_DECL");
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleTokenStream stream_71=new RewriteRuleTokenStream(adaptor,"token 71");

        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:162:9: (i1= INT '..' i2= INT -> ^( RANGE $i1 $i2) | ITER_DECL -> ITER )
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:162:12: i1= INT '..' i2= INT
                    {
                    i1=(Token)match(input,INT,FOLLOW_INT_in_col_opt1267);  
                    stream_INT.add(i1);


                    string_literal122=(Token)match(input,71,FOLLOW_71_in_col_opt1269);  
                    stream_71.add(string_literal122);


                    i2=(Token)match(input,INT,FOLLOW_INT_in_col_opt1273);  
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
                        // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:162:34: ^( RANGE $i1 $i2)
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:163:4: ITER_DECL
                    {
                    ITER_DECL123=(Token)match(input,ITER_DECL,FOLLOW_ITER_DECL_in_col_opt1290);  
                    stream_ITER_DECL.add(ITER_DECL123);


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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:166:1: type : ( 'int' ( '[' ']' )? | 'long' ( '[' ']' )? | 'float' ( '[' ']' )? | 'double' ( '[' ']' )? | 'String' ( '[' ']' )? | 'Object' ( '[' ']' )? | ID ( '[' ']' )? );
    public final SociaLiteParser.type_return type() throws RecognitionException {
        SociaLiteParser.type_return retval = new SociaLiteParser.type_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token string_literal124=null;
        Token char_literal125=null;
        Token char_literal126=null;
        Token string_literal127=null;
        Token char_literal128=null;
        Token char_literal129=null;
        Token string_literal130=null;
        Token char_literal131=null;
        Token char_literal132=null;
        Token string_literal133=null;
        Token char_literal134=null;
        Token char_literal135=null;
        Token string_literal136=null;
        Token char_literal137=null;
        Token char_literal138=null;
        Token string_literal139=null;
        Token char_literal140=null;
        Token char_literal141=null;
        Token ID142=null;
        Token char_literal143=null;
        Token char_literal144=null;

        CommonTree string_literal124_tree=null;
        CommonTree char_literal125_tree=null;
        CommonTree char_literal126_tree=null;
        CommonTree string_literal127_tree=null;
        CommonTree char_literal128_tree=null;
        CommonTree char_literal129_tree=null;
        CommonTree string_literal130_tree=null;
        CommonTree char_literal131_tree=null;
        CommonTree char_literal132_tree=null;
        CommonTree string_literal133_tree=null;
        CommonTree char_literal134_tree=null;
        CommonTree char_literal135_tree=null;
        CommonTree string_literal136_tree=null;
        CommonTree char_literal137_tree=null;
        CommonTree char_literal138_tree=null;
        CommonTree string_literal139_tree=null;
        CommonTree char_literal140_tree=null;
        CommonTree char_literal141_tree=null;
        CommonTree ID142_tree=null;
        CommonTree char_literal143_tree=null;
        CommonTree char_literal144_tree=null;

        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:166:5: ( 'int' ( '[' ']' )? | 'long' ( '[' ']' )? | 'float' ( '[' ']' )? | 'double' ( '[' ']' )? | 'String' ( '[' ']' )? | 'Object' ( '[' ']' )? | ID ( '[' ']' )? )
            int alt39=7;
            switch ( input.LA(1) ) {
            case 94:
                {
                alt39=1;
                }
                break;
            case 95:
                {
                alt39=2;
                }
                break;
            case 91:
                {
                alt39=3;
                }
                break;
            case 89:
                {
                alt39=4;
                }
                break;
            case 84:
                {
                alt39=5;
                }
                break;
            case 83:
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
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:166:7: 'int' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal124=(Token)match(input,94,FOLLOW_94_in_type1303); 
                    string_literal124_tree = 
                    (CommonTree)adaptor.create(string_literal124)
                    ;
                    adaptor.addChild(root_0, string_literal124_tree);


                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:166:13: ( '[' ']' )?
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==85) ) {
                        alt32=1;
                    }
                    switch (alt32) {
                        case 1 :
                            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:166:14: '[' ']'
                            {
                            char_literal125=(Token)match(input,85,FOLLOW_85_in_type1306); 
                            char_literal125_tree = 
                            (CommonTree)adaptor.create(char_literal125)
                            ;
                            adaptor.addChild(root_0, char_literal125_tree);


                            char_literal126=(Token)match(input,86,FOLLOW_86_in_type1308); 
                            char_literal126_tree = 
                            (CommonTree)adaptor.create(char_literal126)
                            ;
                            adaptor.addChild(root_0, char_literal126_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:167:3: 'long' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal127=(Token)match(input,95,FOLLOW_95_in_type1314); 
                    string_literal127_tree = 
                    (CommonTree)adaptor.create(string_literal127)
                    ;
                    adaptor.addChild(root_0, string_literal127_tree);


                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:167:10: ( '[' ']' )?
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==85) ) {
                        alt33=1;
                    }
                    switch (alt33) {
                        case 1 :
                            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:167:11: '[' ']'
                            {
                            char_literal128=(Token)match(input,85,FOLLOW_85_in_type1317); 
                            char_literal128_tree = 
                            (CommonTree)adaptor.create(char_literal128)
                            ;
                            adaptor.addChild(root_0, char_literal128_tree);


                            char_literal129=(Token)match(input,86,FOLLOW_86_in_type1319); 
                            char_literal129_tree = 
                            (CommonTree)adaptor.create(char_literal129)
                            ;
                            adaptor.addChild(root_0, char_literal129_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:168:3: 'float' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal130=(Token)match(input,91,FOLLOW_91_in_type1327); 
                    string_literal130_tree = 
                    (CommonTree)adaptor.create(string_literal130)
                    ;
                    adaptor.addChild(root_0, string_literal130_tree);


                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:168:11: ( '[' ']' )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==85) ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:168:12: '[' ']'
                            {
                            char_literal131=(Token)match(input,85,FOLLOW_85_in_type1330); 
                            char_literal131_tree = 
                            (CommonTree)adaptor.create(char_literal131)
                            ;
                            adaptor.addChild(root_0, char_literal131_tree);


                            char_literal132=(Token)match(input,86,FOLLOW_86_in_type1332); 
                            char_literal132_tree = 
                            (CommonTree)adaptor.create(char_literal132)
                            ;
                            adaptor.addChild(root_0, char_literal132_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:169:3: 'double' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal133=(Token)match(input,89,FOLLOW_89_in_type1340); 
                    string_literal133_tree = 
                    (CommonTree)adaptor.create(string_literal133)
                    ;
                    adaptor.addChild(root_0, string_literal133_tree);


                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:169:12: ( '[' ']' )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==85) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:169:13: '[' ']'
                            {
                            char_literal134=(Token)match(input,85,FOLLOW_85_in_type1343); 
                            char_literal134_tree = 
                            (CommonTree)adaptor.create(char_literal134)
                            ;
                            adaptor.addChild(root_0, char_literal134_tree);


                            char_literal135=(Token)match(input,86,FOLLOW_86_in_type1345); 
                            char_literal135_tree = 
                            (CommonTree)adaptor.create(char_literal135)
                            ;
                            adaptor.addChild(root_0, char_literal135_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:170:3: 'String' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal136=(Token)match(input,84,FOLLOW_84_in_type1353); 
                    string_literal136_tree = 
                    (CommonTree)adaptor.create(string_literal136)
                    ;
                    adaptor.addChild(root_0, string_literal136_tree);


                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:170:12: ( '[' ']' )?
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==85) ) {
                        alt36=1;
                    }
                    switch (alt36) {
                        case 1 :
                            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:170:13: '[' ']'
                            {
                            char_literal137=(Token)match(input,85,FOLLOW_85_in_type1356); 
                            char_literal137_tree = 
                            (CommonTree)adaptor.create(char_literal137)
                            ;
                            adaptor.addChild(root_0, char_literal137_tree);


                            char_literal138=(Token)match(input,86,FOLLOW_86_in_type1358); 
                            char_literal138_tree = 
                            (CommonTree)adaptor.create(char_literal138)
                            ;
                            adaptor.addChild(root_0, char_literal138_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 6 :
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:171:3: 'Object' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal139=(Token)match(input,83,FOLLOW_83_in_type1366); 
                    string_literal139_tree = 
                    (CommonTree)adaptor.create(string_literal139)
                    ;
                    adaptor.addChild(root_0, string_literal139_tree);


                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:171:12: ( '[' ']' )?
                    int alt37=2;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0==85) ) {
                        alt37=1;
                    }
                    switch (alt37) {
                        case 1 :
                            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:171:13: '[' ']'
                            {
                            char_literal140=(Token)match(input,85,FOLLOW_85_in_type1369); 
                            char_literal140_tree = 
                            (CommonTree)adaptor.create(char_literal140)
                            ;
                            adaptor.addChild(root_0, char_literal140_tree);


                            char_literal141=(Token)match(input,86,FOLLOW_86_in_type1371); 
                            char_literal141_tree = 
                            (CommonTree)adaptor.create(char_literal141)
                            ;
                            adaptor.addChild(root_0, char_literal141_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 7 :
                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:172:4: ID ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    ID142=(Token)match(input,ID,FOLLOW_ID_in_type1378); 
                    ID142_tree = 
                    (CommonTree)adaptor.create(ID142)
                    ;
                    adaptor.addChild(root_0, ID142_tree);


                    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:172:7: ( '[' ']' )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==85) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:172:8: '[' ']'
                            {
                            char_literal143=(Token)match(input,85,FOLLOW_85_in_type1381); 
                            char_literal143_tree = 
                            (CommonTree)adaptor.create(char_literal143)
                            ;
                            adaptor.addChild(root_0, char_literal143_tree);


                            char_literal144=(Token)match(input,86,FOLLOW_86_in_type1383); 
                            char_literal144_tree = 
                            (CommonTree)adaptor.create(char_literal144)
                            ;
                            adaptor.addChild(root_0, char_literal144_tree);


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
    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:174:1: dotname : ID ( DOT_ID )* ;
    public final SociaLiteParser.dotname_return dotname() throws RecognitionException {
        SociaLiteParser.dotname_return retval = new SociaLiteParser.dotname_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token ID145=null;
        Token DOT_ID146=null;

        CommonTree ID145_tree=null;
        CommonTree DOT_ID146_tree=null;

        try {
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:174:9: ( ID ( DOT_ID )* )
            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:174:10: ID ( DOT_ID )*
            {
            root_0 = (CommonTree)adaptor.nil();


            ID145=(Token)match(input,ID,FOLLOW_ID_in_dotname1395); 
            ID145_tree = 
            (CommonTree)adaptor.create(ID145)
            ;
            adaptor.addChild(root_0, ID145_tree);


            // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:174:13: ( DOT_ID )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==DOT_ID) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // /Users/jiwon/workspace/linkedin_socialite_backup/grammar/SociaLite.g:174:14: DOT_ID
            	    {
            	    DOT_ID146=(Token)match(input,DOT_ID,FOLLOW_DOT_ID_in_dotname1398); 
            	    DOT_ID146_tree = 
            	    (CommonTree)adaptor.create(DOT_ID146)
            	    ;
            	    adaptor.addChild(root_0, DOT_ID146_tree);


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
        "\1\106\1\uffff\1\137\2\140\1\uffff";
    static final String DFA9_acceptS =
        "\1\uffff\1\1\3\uffff\1\2";
    static final String DFA9_specialS =
        "\6\uffff}>";
    static final String[] DFA9_transitionS = {
            "\1\1\6\uffff\1\1\2\uffff\1\1\23\uffff\1\1\11\uffff\1\1\2\uffff"+
            "\1\1\1\2\4\uffff\1\1",
            "",
            "\1\1\6\uffff\1\3\2\uffff\1\1\23\uffff\1\1\11\uffff\1\1\2\uffff"+
            "\2\1\4\uffff\1\1\14\uffff\2\1\4\uffff\1\1\1\uffff\1\1\2\uffff"+
            "\2\1",
            "\1\4\62\uffff\3\1\1\5\1\1\1\uffff\1\1\14\uffff\1\1\12\uffff"+
            "\1\1",
            "\1\4\62\uffff\3\1\1\5\1\1\1\uffff\1\1\27\uffff\1\1",
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
 

    public static final BitSet FOLLOW_stat_in_prog353 = new BitSet(new long[]{0x0000000010000000L,0x0000000004840000L});
    public static final BitSet FOLLOW_EOF_in_prog356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_decl_in_stat362 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule_in_stat366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_query_in_stat370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_stmt_in_stat374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_87_in_table_stmt383 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_ID_in_table_stmt385 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_DOT_END_in_table_stmt387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_table_stmt400 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_ID_in_table_stmt402 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_DOT_END_in_table_stmt404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_query422 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_predicate_in_query424 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_DOT_END_in_query426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_head_in_rule447 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_rule449 = new BitSet(new long[]{0x2008004090200000L,0x0000000000000043L});
    public static final BitSet FOLLOW_litlist_in_rule453 = new BitSet(new long[]{0x0000000000004000L,0x0000000000000800L});
    public static final BitSet FOLLOW_DOT_END_in_rule473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_rule478 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_rule480 = new BitSet(new long[]{0x2008004090200000L,0x0000000000000043L});
    public static final BitSet FOLLOW_litlist_in_rule484 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_DOT_END_in_rule486 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_predicate_in_head535 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_litlist542 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_litlist545 = new BitSet(new long[]{0x2008004090200000L,0x0000000000000043L});
    public static final BitSet FOLLOW_literal_in_litlist548 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_predicate_in_literal557 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_literal571 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_predicate_in_literal573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_literal587 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleExpr_in_expr604 = new BitSet(new long[]{0x8000000000000000L,0x000000000003F000L});
    public static final BitSet FOLLOW_76_in_expr608 = new BitSet(new long[]{0x2008000090200000L,0x0000000000000043L});
    public static final BitSet FOLLOW_80_in_expr613 = new BitSet(new long[]{0x2008000090200000L,0x0000000000000043L});
    public static final BitSet FOLLOW_78_in_expr618 = new BitSet(new long[]{0x2008000090200000L,0x0000000000000043L});
    public static final BitSet FOLLOW_63_in_expr622 = new BitSet(new long[]{0x2008000090200000L,0x0000000000000043L});
    public static final BitSet FOLLOW_79_in_expr627 = new BitSet(new long[]{0x2008000090200000L,0x0000000000000043L});
    public static final BitSet FOLLOW_81_in_expr632 = new BitSet(new long[]{0x2008000090200000L,0x0000000000000043L});
    public static final BitSet FOLLOW_77_in_expr637 = new BitSet(new long[]{0x2008000090200000L,0x0000000000000043L});
    public static final BitSet FOLLOW_simpleExpr_in_expr642 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varlist_in_expr647 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_expr649 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_cast_in_expr651 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_function_in_expr654 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multExpr_in_simpleExpr676 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000050L});
    public static final BitSet FOLLOW_68_in_simpleExpr681 = new BitSet(new long[]{0x2008000090200000L,0x0000000000000043L});
    public static final BitSet FOLLOW_70_in_simpleExpr684 = new BitSet(new long[]{0x2008000090200000L,0x0000000000000043L});
    public static final BitSet FOLLOW_multExpr_in_simpleExpr688 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000050L});
    public static final BitSet FOLLOW_exprValue_in_multExpr699 = new BitSet(new long[]{0x0000000000000002L,0x0000000100000108L});
    public static final BitSet FOLLOW_67_in_multExpr703 = new BitSet(new long[]{0x2008000090200000L,0x0000000000000043L});
    public static final BitSet FOLLOW_72_in_multExpr706 = new BitSet(new long[]{0x2008000090200000L,0x0000000000000043L});
    public static final BitSet FOLLOW_96_in_multExpr709 = new BitSet(new long[]{0x2008000090200000L,0x0000000000000043L});
    public static final BitSet FOLLOW_exprValue_in_multExpr713 = new BitSet(new long[]{0x0000000000000002L,0x0000000100000108L});
    public static final BitSet FOLLOW_70_in_exprValue724 = new BitSet(new long[]{0x2008000090200000L,0x0000000000000002L});
    public static final BitSet FOLLOW_cast_in_exprValue728 = new BitSet(new long[]{0x2008000090200000L});
    public static final BitSet FOLLOW_term_in_exprValue731 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cast_in_exprValue751 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_function_in_exprValue754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cast_in_exprValue770 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_compExpr_in_exprValue773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_compExpr792 = new BitSet(new long[]{0x2008000090200000L,0x0000000000000043L});
    public static final BitSet FOLLOW_simpleExpr_in_compExpr795 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_compExpr797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_cast806 = new BitSet(new long[]{0x0000000010000000L,0x00000000CA180000L});
    public static final BitSet FOLLOW_type_in_cast808 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_cast810 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_varlist822 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_dotname_in_varlist827 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_varlist831 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_dotname_in_varlist836 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_66_in_varlist840 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_64_in_function848 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_dotname_in_function850 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_function852 = new BitSet(new long[]{0x2008000090200000L,0x0000000000000047L});
    public static final BitSet FOLLOW_fparamlist_in_function854 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_function857 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_predicate877 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_predicate879 = new BitSet(new long[]{0x2008000090200000L,0x0000000000000043L});
    public static final BitSet FOLLOW_paramlist_in_predicate881 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_predicate883 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_param_in_paramlist896 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_paramlist899 = new BitSet(new long[]{0x2008000090200000L,0x0000000000000043L});
    public static final BitSet FOLLOW_param_in_paramlist902 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_fparam_in_fparamlist910 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_fparamlist913 = new BitSet(new long[]{0x2008000090200000L,0x0000000000000043L});
    public static final BitSet FOLLOW_fparam_in_fparamlist916 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_simpleExpr_in_param925 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleExpr_in_fparam931 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_term938 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_term951 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_term963 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UTF8_in_term975 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dotname_in_term988 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_table_decl1007 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_table_decl1010 = new BitSet(new long[]{0x0000000010000000L,0x00000000CA180000L});
    public static final BitSet FOLLOW_decls_in_table_decl1012 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_table_decl1014 = new BitSet(new long[]{0x0000000000004000L,0x0000001E31000000L});
    public static final BitSet FOLLOW_table_opts_in_table_decl1016 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_DOT_END_in_table_decl1019 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_t_opt_in_table_opts1053 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_table_opts1056 = new BitSet(new long[]{0x0000000000000000L,0x0000001E31000000L});
    public static final BitSet FOLLOW_t_opt_in_table_opts1059 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_100_in_t_opt1070 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_ID_in_t_opt1074 = new BitSet(new long[]{0x0004000000000002L});
    public static final BitSet FOLLOW_SORT_ORDER_in_t_opt1079 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_t_opt1098 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_ID_in_t_opt1100 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_t_opt1112 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_ID_in_t_opt1114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_t_opt1126 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_t_opt1128 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_INT_in_t_opt1130 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_t_opt1132 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_t_opt1144 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_t_opt1152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_97_in_t_opt1160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_col_decls_in_decls1183 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_decls1186 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_decls1188 = new BitSet(new long[]{0x0000000010000000L,0x00000000CA180000L});
    public static final BitSet FOLLOW_decls_in_decls1190 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_decls1192 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_col_decl_in_col_decls1218 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_col_decls1221 = new BitSet(new long[]{0x0000000010000000L,0x00000000CA180000L});
    public static final BitSet FOLLOW_col_decl_in_col_decls1224 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_type_in_col_decl1234 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_ID_in_col_decl1236 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_col_decl1239 = new BitSet(new long[]{0x0000000280000000L});
    public static final BitSet FOLLOW_col_opt_in_col_decl1241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_col_opt1267 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_col_opt1269 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_INT_in_col_opt1273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ITER_DECL_in_col_opt1290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_type1303 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type1306 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_95_in_type1314 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type1317 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_91_in_type1327 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type1330 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_type1340 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type1343 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_type1353 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type1356 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_type1366 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type1369 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_type1378 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type1381 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type1383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_dotname1395 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_DOT_ID_in_dotname1398 = new BitSet(new long[]{0x0000000000008002L});

}