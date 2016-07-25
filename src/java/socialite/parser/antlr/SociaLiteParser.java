// $ANTLR 3.4 /Users/jiwon/workspace/socialite/grammar/SociaLite.g 2016-07-24 11:14:24

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "APPROX", "BIT", "CHAR", "CLEAR", "COL_DECL", "COL_DECLS", "COMMENT", "COMPOUND_EXPR", "CONCURRENT", "DECL", "DOT_END", "DOT_ID", "DROP", "ESC_SEQ", "EXPONENT", "EXPR", "FLOAT", "FUNC", "FUNCTION", "GROUP_BY", "HEX_DIGIT", "ID", "INDEX", "INDEX_BY", "INT", "ITER", "ITER_DECL", "MULTISET", "MULTI_ASSIGN", "NOT", "OCTAL_ESC", "OPTION", "ORDER_BY", "PREDEFINED", "PREDICATE", "PROG", "QUERY", "RANGE", "RULE", "SHARD_BY", "SORT_BY", "SORT_ORDER", "STRING", "TERM", "T_FLOAT", "T_INT", "T_STR", "T_UTF8", "T_VAR", "UNICODE_ESC", "UTF8", "WS", "'!='", "'$'", "'('", "')'", "'*'", "'+'", "','", "'-'", "'..'", "'/'", "':'", "':-'", "';'", "'<'", "'<='", "'='", "'=='", "'>'", "'>='", "'?-'", "'Object'", "'String'", "'['", "']'", "'bit'", "'clear'", "'concurrent'", "'double'", "'drop'", "'float'", "'groupby'", "'indexby'", "'int'", "'long'", "'mod'", "'multiset'", "'orderby'", "'predefined'", "'shardby'", "'sortby'"
    };

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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:80:1: prog : ( stat )+ EOF ;
    public final SociaLiteParser.prog_return prog() throws RecognitionException {
        SociaLiteParser.prog_return retval = new SociaLiteParser.prog_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token EOF2=null;
        SociaLiteParser.stat_return stat1 =null;


        CommonTree EOF2_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:80:6: ( ( stat )+ EOF )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:80:7: ( stat )+ EOF
            {
            root_0 = (CommonTree)adaptor.nil();


            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:80:7: ( stat )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==ID||LA1_0==75||LA1_0==81||LA1_0==84) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:80:7: stat
            	    {
            	    pushFollow(FOLLOW_stat_in_prog304);
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


            EOF2=(Token)match(input,EOF,FOLLOW_EOF_in_prog307); 
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:81:1: stat : ( table_decl | rule | query | table_stmt );
    public final SociaLiteParser.stat_return stat() throws RecognitionException {
        SociaLiteParser.stat_return retval = new SociaLiteParser.stat_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SociaLiteParser.table_decl_return table_decl3 =null;

        SociaLiteParser.rule_return rule4 =null;

        SociaLiteParser.query_return query5 =null;

        SociaLiteParser.table_stmt_return table_stmt6 =null;



        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:81:6: ( table_decl | rule | query | table_stmt )
            int alt2=4;
            switch ( input.LA(1) ) {
            case ID:
                {
                int LA2_1 = input.LA(2);

                if ( (LA2_1==58) ) {
                    switch ( input.LA(3) ) {
                    case 76:
                    case 77:
                    case 83:
                    case 85:
                    case 88:
                    case 89:
                        {
                        alt2=1;
                        }
                        break;
                    case ID:
                        {
                        int LA2_6 = input.LA(4);

                        if ( (LA2_6==ID||LA2_6==78) ) {
                            alt2=1;
                        }
                        else if ( (LA2_6==DOT_ID||(LA2_6 >= 59 && LA2_6 <= 63)||LA2_6==65||LA2_6==90) ) {
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
                    case 57:
                    case 58:
                    case 63:
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
            case 75:
                {
                alt2=3;
                }
                break;
            case 81:
            case 84:
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:81:7: table_decl
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_table_decl_in_stat313);
                    table_decl3=table_decl();

                    state._fsp--;

                    adaptor.addChild(root_0, table_decl3.getTree());

                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:82:3: rule
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_rule_in_stat317);
                    rule4=rule();

                    state._fsp--;

                    adaptor.addChild(root_0, rule4.getTree());

                    }
                    break;
                case 3 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:83:3: query
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_query_in_stat321);
                    query5=query();

                    state._fsp--;

                    adaptor.addChild(root_0, query5.getTree());

                    }
                    break;
                case 4 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:84:3: table_stmt
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_table_stmt_in_stat325);
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:86:1: table_stmt : ( 'clear' ID DOT_END -> ^( CLEAR ID ) | 'drop' ID DOT_END -> ^( DROP ( ID )? ) );
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
        RewriteRuleTokenStream stream_DOT_END=new RewriteRuleTokenStream(adaptor,"token DOT_END");
        RewriteRuleTokenStream stream_81=new RewriteRuleTokenStream(adaptor,"token 81");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:86:12: ( 'clear' ID DOT_END -> ^( CLEAR ID ) | 'drop' ID DOT_END -> ^( DROP ( ID )? ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==81) ) {
                alt3=1;
            }
            else if ( (LA3_0==84) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;

            }
            switch (alt3) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:86:14: 'clear' ID DOT_END
                    {
                    string_literal7=(Token)match(input,81,FOLLOW_81_in_table_stmt334);  
                    stream_81.add(string_literal7);


                    ID8=(Token)match(input,ID,FOLLOW_ID_in_table_stmt336);  
                    stream_ID.add(ID8);


                    DOT_END9=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_table_stmt338);  
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
                    // 86:33: -> ^( CLEAR ID )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:86:36: ^( CLEAR ID )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:87:3: 'drop' ID DOT_END
                    {
                    string_literal10=(Token)match(input,84,FOLLOW_84_in_table_stmt351);  
                    stream_84.add(string_literal10);


                    ID11=(Token)match(input,ID,FOLLOW_ID_in_table_stmt353);  
                    stream_ID.add(ID11);


                    DOT_END12=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_table_stmt355);  
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
                    // 87:21: -> ^( DROP ( ID )? )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:87:24: ^( DROP ( ID )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(DROP, "DROP")
                        , root_1);

                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:87:31: ( ID )?
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:90:1: query : '?-' predicate DOT_END -> ^( QUERY predicate ) ;
    public final SociaLiteParser.query_return query() throws RecognitionException {
        SociaLiteParser.query_return retval = new SociaLiteParser.query_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token string_literal13=null;
        Token DOT_END15=null;
        SociaLiteParser.predicate_return predicate14 =null;


        CommonTree string_literal13_tree=null;
        CommonTree DOT_END15_tree=null;
        RewriteRuleTokenStream stream_DOT_END=new RewriteRuleTokenStream(adaptor,"token DOT_END");
        RewriteRuleTokenStream stream_75=new RewriteRuleTokenStream(adaptor,"token 75");
        RewriteRuleSubtreeStream stream_predicate=new RewriteRuleSubtreeStream(adaptor,"rule predicate");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:90:7: ( '?-' predicate DOT_END -> ^( QUERY predicate ) )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:90:8: '?-' predicate DOT_END
            {
            string_literal13=(Token)match(input,75,FOLLOW_75_in_query373);  
            stream_75.add(string_literal13);


            pushFollow(FOLLOW_predicate_in_query375);
            predicate14=predicate();

            state._fsp--;

            stream_predicate.add(predicate14.getTree());

            DOT_END15=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_query377);  
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
            // 90:31: -> ^( QUERY predicate )
            {
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:90:34: ^( QUERY predicate )
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:93:1: rule : head ':-' litlist ( ';' ':-' litlist )* DOT_END -> ( ^( RULE head litlist ) )+ DOT_END ;
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
        RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
        RewriteRuleTokenStream stream_68=new RewriteRuleTokenStream(adaptor,"token 68");
        RewriteRuleTokenStream stream_DOT_END=new RewriteRuleTokenStream(adaptor,"token DOT_END");
        RewriteRuleSubtreeStream stream_head=new RewriteRuleSubtreeStream(adaptor,"rule head");
        RewriteRuleSubtreeStream stream_litlist=new RewriteRuleSubtreeStream(adaptor,"rule litlist");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:93:9: ( head ':-' litlist ( ';' ':-' litlist )* DOT_END -> ( ^( RULE head litlist ) )+ DOT_END )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:93:11: head ':-' litlist ( ';' ':-' litlist )* DOT_END
            {
            pushFollow(FOLLOW_head_in_rule398);
            head16=head();

            state._fsp--;

            stream_head.add(head16.getTree());

            string_literal17=(Token)match(input,67,FOLLOW_67_in_rule400);  
            stream_67.add(string_literal17);


            pushFollow(FOLLOW_litlist_in_rule402);
            litlist18=litlist();

            state._fsp--;

            stream_litlist.add(litlist18.getTree());

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:93:29: ( ';' ':-' litlist )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==68) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:93:30: ';' ':-' litlist
            	    {
            	    char_literal19=(Token)match(input,68,FOLLOW_68_in_rule405);  
            	    stream_68.add(char_literal19);


            	    string_literal20=(Token)match(input,67,FOLLOW_67_in_rule407);  
            	    stream_67.add(string_literal20);


            	    pushFollow(FOLLOW_litlist_in_rule409);
            	    litlist21=litlist();

            	    state._fsp--;

            	    stream_litlist.add(litlist21.getTree());

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            DOT_END22=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_rule413);  
            stream_DOT_END.add(DOT_END22);


            // AST REWRITE
            // elements: litlist, DOT_END, head
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 94:9: -> ( ^( RULE head litlist ) )+ DOT_END
            {
                if ( !(stream_litlist.hasNext()||stream_head.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_litlist.hasNext()||stream_head.hasNext() ) {
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:94:12: ^( RULE head litlist )
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
                stream_litlist.reset();
                stream_head.reset();

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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:96:1: head : predicate ;
    public final SociaLiteParser.head_return head() throws RecognitionException {
        SociaLiteParser.head_return retval = new SociaLiteParser.head_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SociaLiteParser.predicate_return predicate23 =null;



        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:96:6: ( predicate )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:96:8: predicate
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_predicate_in_head450);
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:98:1: litlist : literal ( ',' ! literal )* ;
    public final SociaLiteParser.litlist_return litlist() throws RecognitionException {
        SociaLiteParser.litlist_return retval = new SociaLiteParser.litlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal25=null;
        SociaLiteParser.literal_return literal24 =null;

        SociaLiteParser.literal_return literal26 =null;


        CommonTree char_literal25_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:98:9: ( literal ( ',' ! literal )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:98:10: literal ( ',' ! literal )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_literal_in_litlist457);
            literal24=literal();

            state._fsp--;

            adaptor.addChild(root_0, literal24.getTree());

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:98:18: ( ',' ! literal )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==62) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:98:19: ',' ! literal
            	    {
            	    char_literal25=(Token)match(input,62,FOLLOW_62_in_litlist460); 

            	    pushFollow(FOLLOW_literal_in_litlist463);
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:100:1: literal : ( predicate -> ^( PREDICATE predicate ) | NOT predicate -> ^( PREDICATE NOT predicate ) | expr -> ^( EXPR expr ) );
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
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:100:9: ( predicate -> ^( PREDICATE predicate ) | NOT predicate -> ^( PREDICATE NOT predicate ) | expr -> ^( EXPR expr ) )
            int alt6=3;
            switch ( input.LA(1) ) {
            case ID:
                {
                int LA6_1 = input.LA(2);

                if ( (LA6_1==58) ) {
                    alt6=1;
                }
                else if ( (LA6_1==DOT_ID||LA6_1==56||(LA6_1 >= 60 && LA6_1 <= 61)||LA6_1==63||LA6_1==65||(LA6_1 >= 69 && LA6_1 <= 74)||LA6_1==90) ) {
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
            case 57:
            case 58:
            case 63:
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:100:10: predicate
                    {
                    pushFollow(FOLLOW_predicate_in_literal472);
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
                    // 100:20: -> ^( PREDICATE predicate )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:100:23: ^( PREDICATE predicate )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:101:4: NOT predicate
                    {
                    NOT28=(Token)match(input,NOT,FOLLOW_NOT_in_literal486);  
                    stream_NOT.add(NOT28);


                    pushFollow(FOLLOW_predicate_in_literal488);
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
                    // 101:18: -> ^( PREDICATE NOT predicate )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:101:21: ^( PREDICATE NOT predicate )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:102:3: expr
                    {
                    pushFollow(FOLLOW_expr_in_literal502);
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
                    // 102:8: -> ^( EXPR expr )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:102:11: ^( EXPR expr )
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:104:1: expr : ( simpleExpr ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^) simpleExpr | varlist '=' ( cast )? function -> ^( MULTI_ASSIGN varlist function ( cast )? ) );
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
        RewriteRuleTokenStream stream_71=new RewriteRuleTokenStream(adaptor,"token 71");
        RewriteRuleSubtreeStream stream_cast=new RewriteRuleSubtreeStream(adaptor,"rule cast");
        RewriteRuleSubtreeStream stream_function=new RewriteRuleSubtreeStream(adaptor,"rule function");
        RewriteRuleSubtreeStream stream_varlist=new RewriteRuleSubtreeStream(adaptor,"rule varlist");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:104:5: ( simpleExpr ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^) simpleExpr | varlist '=' ( cast )? function -> ^( MULTI_ASSIGN varlist function ( cast )? ) )
            int alt9=2;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:104:7: simpleExpr ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^) simpleExpr
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_simpleExpr_in_expr519);
                    simpleExpr31=simpleExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, simpleExpr31.getTree());

                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:104:19: ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^)
                    int alt7=7;
                    switch ( input.LA(1) ) {
                    case 69:
                        {
                        alt7=1;
                        }
                        break;
                    case 73:
                        {
                        alt7=2;
                        }
                        break;
                    case 71:
                        {
                        alt7=3;
                        }
                        break;
                    case 56:
                        {
                        alt7=4;
                        }
                        break;
                    case 72:
                        {
                        alt7=5;
                        }
                        break;
                    case 74:
                        {
                        alt7=6;
                        }
                        break;
                    case 70:
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
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:104:20: '<' ^
                            {
                            char_literal32=(Token)match(input,69,FOLLOW_69_in_expr523); 
                            char_literal32_tree = 
                            (CommonTree)adaptor.create(char_literal32)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(char_literal32_tree, root_0);


                            }
                            break;
                        case 2 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:104:27: '>' ^
                            {
                            char_literal33=(Token)match(input,73,FOLLOW_73_in_expr528); 
                            char_literal33_tree = 
                            (CommonTree)adaptor.create(char_literal33)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(char_literal33_tree, root_0);


                            }
                            break;
                        case 3 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:104:34: '=' ^
                            {
                            char_literal34=(Token)match(input,71,FOLLOW_71_in_expr533); 
                            char_literal34_tree = 
                            (CommonTree)adaptor.create(char_literal34)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(char_literal34_tree, root_0);


                            }
                            break;
                        case 4 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:104:40: '!=' ^
                            {
                            string_literal35=(Token)match(input,56,FOLLOW_56_in_expr537); 
                            string_literal35_tree = 
                            (CommonTree)adaptor.create(string_literal35)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(string_literal35_tree, root_0);


                            }
                            break;
                        case 5 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:104:48: '==' ^
                            {
                            string_literal36=(Token)match(input,72,FOLLOW_72_in_expr542); 
                            string_literal36_tree = 
                            (CommonTree)adaptor.create(string_literal36)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(string_literal36_tree, root_0);


                            }
                            break;
                        case 6 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:104:56: '>=' ^
                            {
                            string_literal37=(Token)match(input,74,FOLLOW_74_in_expr547); 
                            string_literal37_tree = 
                            (CommonTree)adaptor.create(string_literal37)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(string_literal37_tree, root_0);


                            }
                            break;
                        case 7 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:104:64: '<=' ^
                            {
                            string_literal38=(Token)match(input,70,FOLLOW_70_in_expr552); 
                            string_literal38_tree = 
                            (CommonTree)adaptor.create(string_literal38)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(string_literal38_tree, root_0);


                            }
                            break;

                    }


                    pushFollow(FOLLOW_simpleExpr_in_expr557);
                    simpleExpr39=simpleExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, simpleExpr39.getTree());

                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:105:4: varlist '=' ( cast )? function
                    {
                    pushFollow(FOLLOW_varlist_in_expr562);
                    varlist40=varlist();

                    state._fsp--;

                    stream_varlist.add(varlist40.getTree());

                    char_literal41=(Token)match(input,71,FOLLOW_71_in_expr564);  
                    stream_71.add(char_literal41);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:105:16: ( cast )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==58) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:105:16: cast
                            {
                            pushFollow(FOLLOW_cast_in_expr566);
                            cast42=cast();

                            state._fsp--;

                            stream_cast.add(cast42.getTree());

                            }
                            break;

                    }


                    pushFollow(FOLLOW_function_in_expr569);
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
                    // 105:31: -> ^( MULTI_ASSIGN varlist function ( cast )? )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:105:34: ^( MULTI_ASSIGN varlist function ( cast )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(MULTI_ASSIGN, "MULTI_ASSIGN")
                        , root_1);

                        adaptor.addChild(root_1, stream_varlist.nextTree());

                        adaptor.addChild(root_1, stream_function.nextTree());

                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:105:66: ( cast )?
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:107:1: simpleExpr : multExpr ( ( '+' ^| '-' ^) multExpr )* ;
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
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:107:11: ( multExpr ( ( '+' ^| '-' ^) multExpr )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:107:13: multExpr ( ( '+' ^| '-' ^) multExpr )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_multExpr_in_simpleExpr591);
            multExpr44=multExpr();

            state._fsp--;

            adaptor.addChild(root_0, multExpr44.getTree());

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:107:23: ( ( '+' ^| '-' ^) multExpr )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==61||LA11_0==63) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:107:24: ( '+' ^| '-' ^) multExpr
            	    {
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:107:24: ( '+' ^| '-' ^)
            	    int alt10=2;
            	    int LA10_0 = input.LA(1);

            	    if ( (LA10_0==61) ) {
            	        alt10=1;
            	    }
            	    else if ( (LA10_0==63) ) {
            	        alt10=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 10, 0, input);

            	        throw nvae;

            	    }
            	    switch (alt10) {
            	        case 1 :
            	            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:107:25: '+' ^
            	            {
            	            char_literal45=(Token)match(input,61,FOLLOW_61_in_simpleExpr596); 
            	            char_literal45_tree = 
            	            (CommonTree)adaptor.create(char_literal45)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal45_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:107:30: '-' ^
            	            {
            	            char_literal46=(Token)match(input,63,FOLLOW_63_in_simpleExpr599); 
            	            char_literal46_tree = 
            	            (CommonTree)adaptor.create(char_literal46)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal46_tree, root_0);


            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_multExpr_in_simpleExpr603);
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:109:1: multExpr : exprValue ( ( '*' ^| '/' ^| 'mod' ^) exprValue )* ;
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
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:109:9: ( exprValue ( ( '*' ^| '/' ^| 'mod' ^) exprValue )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:109:11: exprValue ( ( '*' ^| '/' ^| 'mod' ^) exprValue )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_exprValue_in_multExpr614);
            exprValue48=exprValue();

            state._fsp--;

            adaptor.addChild(root_0, exprValue48.getTree());

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:109:21: ( ( '*' ^| '/' ^| 'mod' ^) exprValue )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==60||LA13_0==65||LA13_0==90) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:109:22: ( '*' ^| '/' ^| 'mod' ^) exprValue
            	    {
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:109:22: ( '*' ^| '/' ^| 'mod' ^)
            	    int alt12=3;
            	    switch ( input.LA(1) ) {
            	    case 60:
            	        {
            	        alt12=1;
            	        }
            	        break;
            	    case 65:
            	        {
            	        alt12=2;
            	        }
            	        break;
            	    case 90:
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
            	            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:109:23: '*' ^
            	            {
            	            char_literal49=(Token)match(input,60,FOLLOW_60_in_multExpr618); 
            	            char_literal49_tree = 
            	            (CommonTree)adaptor.create(char_literal49)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal49_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:109:28: '/' ^
            	            {
            	            char_literal50=(Token)match(input,65,FOLLOW_65_in_multExpr621); 
            	            char_literal50_tree = 
            	            (CommonTree)adaptor.create(char_literal50)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal50_tree, root_0);


            	            }
            	            break;
            	        case 3 :
            	            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:109:33: 'mod' ^
            	            {
            	            string_literal51=(Token)match(input,90,FOLLOW_90_in_multExpr624); 
            	            string_literal51_tree = 
            	            (CommonTree)adaptor.create(string_literal51)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(string_literal51_tree, root_0);


            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_exprValue_in_multExpr628);
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:110:1: exprValue : ( (neg= '-' )? ( cast )? term -> ^( TERM term ( $neg)? ( cast )? ) | ( cast )? function -> ^( FUNCTION function ( cast )? ) | ( cast )? compExpr -> ^( COMPOUND_EXPR compExpr ( cast )? ) );
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
        RewriteRuleTokenStream stream_63=new RewriteRuleTokenStream(adaptor,"token 63");
        RewriteRuleSubtreeStream stream_cast=new RewriteRuleSubtreeStream(adaptor,"rule cast");
        RewriteRuleSubtreeStream stream_function=new RewriteRuleSubtreeStream(adaptor,"rule function");
        RewriteRuleSubtreeStream stream_compExpr=new RewriteRuleSubtreeStream(adaptor,"rule compExpr");
        RewriteRuleSubtreeStream stream_term=new RewriteRuleSubtreeStream(adaptor,"rule term");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:110:10: ( (neg= '-' )? ( cast )? term -> ^( TERM term ( $neg)? ( cast )? ) | ( cast )? function -> ^( FUNCTION function ( cast )? ) | ( cast )? compExpr -> ^( COMPOUND_EXPR compExpr ( cast )? ) )
            int alt18=3;
            switch ( input.LA(1) ) {
            case FLOAT:
            case ID:
            case INT:
            case STRING:
            case UTF8:
            case 63:
                {
                alt18=1;
                }
                break;
            case 58:
                {
                switch ( input.LA(2) ) {
                case 88:
                    {
                    int LA18_4 = input.LA(3);

                    if ( (LA18_4==78) ) {
                        int LA18_12 = input.LA(4);

                        if ( (LA18_12==79) ) {
                            int LA18_21 = input.LA(5);

                            if ( (LA18_21==59) ) {
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
                                case 57:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 58:
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
                    else if ( (LA18_4==59) ) {
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
                        case 57:
                            {
                            alt18=2;
                            }
                            break;
                        case 58:
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
                case 89:
                    {
                    int LA18_5 = input.LA(3);

                    if ( (LA18_5==78) ) {
                        int LA18_14 = input.LA(4);

                        if ( (LA18_14==79) ) {
                            int LA18_22 = input.LA(5);

                            if ( (LA18_22==59) ) {
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
                                case 57:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 58:
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
                    else if ( (LA18_5==59) ) {
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
                        case 57:
                            {
                            alt18=2;
                            }
                            break;
                        case 58:
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
                case 85:
                    {
                    int LA18_6 = input.LA(3);

                    if ( (LA18_6==78) ) {
                        int LA18_15 = input.LA(4);

                        if ( (LA18_15==79) ) {
                            int LA18_23 = input.LA(5);

                            if ( (LA18_23==59) ) {
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
                                case 57:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 58:
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
                    else if ( (LA18_6==59) ) {
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
                        case 57:
                            {
                            alt18=2;
                            }
                            break;
                        case 58:
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
                case 83:
                    {
                    int LA18_7 = input.LA(3);

                    if ( (LA18_7==78) ) {
                        int LA18_16 = input.LA(4);

                        if ( (LA18_16==79) ) {
                            int LA18_24 = input.LA(5);

                            if ( (LA18_24==59) ) {
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
                                case 57:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 58:
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
                    else if ( (LA18_7==59) ) {
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
                        case 57:
                            {
                            alt18=2;
                            }
                            break;
                        case 58:
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
                case 77:
                    {
                    int LA18_8 = input.LA(3);

                    if ( (LA18_8==78) ) {
                        int LA18_17 = input.LA(4);

                        if ( (LA18_17==79) ) {
                            int LA18_25 = input.LA(5);

                            if ( (LA18_25==59) ) {
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
                                case 57:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 58:
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
                    else if ( (LA18_8==59) ) {
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
                        case 57:
                            {
                            alt18=2;
                            }
                            break;
                        case 58:
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
                case 76:
                    {
                    int LA18_9 = input.LA(3);

                    if ( (LA18_9==78) ) {
                        int LA18_18 = input.LA(4);

                        if ( (LA18_18==79) ) {
                            int LA18_26 = input.LA(5);

                            if ( (LA18_26==59) ) {
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
                                case 57:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 58:
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
                    else if ( (LA18_9==59) ) {
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
                        case 57:
                            {
                            alt18=2;
                            }
                            break;
                        case 58:
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
                    case 78:
                        {
                        int LA18_19 = input.LA(4);

                        if ( (LA18_19==79) ) {
                            int LA18_27 = input.LA(5);

                            if ( (LA18_27==59) ) {
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
                                case 57:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 58:
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
                    case 59:
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
                        case 57:
                            {
                            alt18=2;
                            }
                            break;
                        case DOT_END:
                        case 56:
                        case 58:
                        case 59:
                        case 60:
                        case 61:
                        case 62:
                        case 63:
                        case 65:
                        case 68:
                        case 69:
                        case 70:
                        case 71:
                        case 72:
                        case 73:
                        case 74:
                        case 90:
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
                    case 60:
                    case 61:
                    case 63:
                    case 65:
                    case 90:
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
                case 57:
                case 58:
                case 63:
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
            case 57:
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:110:12: (neg= '-' )? ( cast )? term
                    {
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:110:12: (neg= '-' )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==63) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:110:13: neg= '-'
                            {
                            neg=(Token)match(input,63,FOLLOW_63_in_exprValue639);  
                            stream_63.add(neg);


                            }
                            break;

                    }


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:110:23: ( cast )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==58) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:110:23: cast
                            {
                            pushFollow(FOLLOW_cast_in_exprValue643);
                            cast53=cast();

                            state._fsp--;

                            stream_cast.add(cast53.getTree());

                            }
                            break;

                    }


                    pushFollow(FOLLOW_term_in_exprValue646);
                    term54=term();

                    state._fsp--;

                    stream_term.add(term54.getTree());

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
                    // 110:34: -> ^( TERM term ( $neg)? ( cast )? )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:110:37: ^( TERM term ( $neg)? ( cast )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(TERM, "TERM")
                        , root_1);

                        adaptor.addChild(root_1, stream_term.nextTree());

                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:110:50: ( $neg)?
                        if ( stream_neg.hasNext() ) {
                            adaptor.addChild(root_1, stream_neg.nextNode());

                        }
                        stream_neg.reset();

                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:110:55: ( cast )?
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:111:4: ( cast )? function
                    {
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:111:4: ( cast )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==58) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:111:4: cast
                            {
                            pushFollow(FOLLOW_cast_in_exprValue666);
                            cast55=cast();

                            state._fsp--;

                            stream_cast.add(cast55.getTree());

                            }
                            break;

                    }


                    pushFollow(FOLLOW_function_in_exprValue669);
                    function56=function();

                    state._fsp--;

                    stream_function.add(function56.getTree());

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
                    // 111:19: -> ^( FUNCTION function ( cast )? )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:111:22: ^( FUNCTION function ( cast )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(FUNCTION, "FUNCTION")
                        , root_1);

                        adaptor.addChild(root_1, stream_function.nextTree());

                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:111:42: ( cast )?
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:112:4: ( cast )? compExpr
                    {
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:112:4: ( cast )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==58) ) {
                        int LA17_1 = input.LA(2);

                        if ( ((LA17_1 >= 76 && LA17_1 <= 77)||LA17_1==83||LA17_1==85||(LA17_1 >= 88 && LA17_1 <= 89)) ) {
                            alt17=1;
                        }
                        else if ( (LA17_1==ID) ) {
                            int LA17_3 = input.LA(3);

                            if ( (LA17_3==78) ) {
                                alt17=1;
                            }
                            else if ( (LA17_3==59) ) {
                                int LA17_5 = input.LA(4);

                                if ( (LA17_5==58) ) {
                                    alt17=1;
                                }
                            }
                        }
                    }
                    switch (alt17) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:112:4: cast
                            {
                            pushFollow(FOLLOW_cast_in_exprValue685);
                            cast57=cast();

                            state._fsp--;

                            stream_cast.add(cast57.getTree());

                            }
                            break;

                    }


                    pushFollow(FOLLOW_compExpr_in_exprValue688);
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
                    // 112:19: -> ^( COMPOUND_EXPR compExpr ( cast )? )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:112:22: ^( COMPOUND_EXPR compExpr ( cast )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(COMPOUND_EXPR, "COMPOUND_EXPR")
                        , root_1);

                        adaptor.addChild(root_1, stream_compExpr.nextTree());

                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:112:47: ( cast )?
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:114:1: compExpr : '(' ! simpleExpr ')' !;
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
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:114:9: ( '(' ! simpleExpr ')' !)
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:114:11: '(' ! simpleExpr ')' !
            {
            root_0 = (CommonTree)adaptor.nil();


            char_literal59=(Token)match(input,58,FOLLOW_58_in_compExpr707); 

            pushFollow(FOLLOW_simpleExpr_in_compExpr710);
            simpleExpr60=simpleExpr();

            state._fsp--;

            adaptor.addChild(root_0, simpleExpr60.getTree());

            char_literal61=(Token)match(input,59,FOLLOW_59_in_compExpr712); 

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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:115:1: cast : '(' type ')' -> type ;
    public final SociaLiteParser.cast_return cast() throws RecognitionException {
        SociaLiteParser.cast_return retval = new SociaLiteParser.cast_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal62=null;
        Token char_literal64=null;
        SociaLiteParser.type_return type63 =null;


        CommonTree char_literal62_tree=null;
        CommonTree char_literal64_tree=null;
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:115:5: ( '(' type ')' -> type )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:115:7: '(' type ')'
            {
            char_literal62=(Token)match(input,58,FOLLOW_58_in_cast721);  
            stream_58.add(char_literal62);


            pushFollow(FOLLOW_type_in_cast723);
            type63=type();

            state._fsp--;

            stream_type.add(type63.getTree());

            char_literal64=(Token)match(input,59,FOLLOW_59_in_cast725);  
            stream_59.add(char_literal64);


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
            // 115:20: -> type
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:116:1: varlist : '(' !e1= dotname ( ',' !e2= dotname )+ ')' !;
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
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:116:8: ( '(' !e1= dotname ( ',' !e2= dotname )+ ')' !)
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:116:10: '(' !e1= dotname ( ',' !e2= dotname )+ ')' !
            {
            root_0 = (CommonTree)adaptor.nil();


            char_literal65=(Token)match(input,58,FOLLOW_58_in_varlist737); 

            pushFollow(FOLLOW_dotname_in_varlist742);
            e1=dotname();

            state._fsp--;

            adaptor.addChild(root_0, e1.getTree());

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:116:27: ( ',' !e2= dotname )+
            int cnt19=0;
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==62) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:116:28: ',' !e2= dotname
            	    {
            	    char_literal66=(Token)match(input,62,FOLLOW_62_in_varlist746); 

            	    pushFollow(FOLLOW_dotname_in_varlist751);
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


            char_literal67=(Token)match(input,59,FOLLOW_59_in_varlist755); 

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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:117:1: function : '$' dotname '(' ( fparamlist )? ')' -> ^( FUNC dotname ( fparamlist )? ) ;
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
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleSubtreeStream stream_fparamlist=new RewriteRuleSubtreeStream(adaptor,"rule fparamlist");
        RewriteRuleSubtreeStream stream_dotname=new RewriteRuleSubtreeStream(adaptor,"rule dotname");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:117:9: ( '$' dotname '(' ( fparamlist )? ')' -> ^( FUNC dotname ( fparamlist )? ) )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:117:11: '$' dotname '(' ( fparamlist )? ')'
            {
            char_literal68=(Token)match(input,57,FOLLOW_57_in_function763);  
            stream_57.add(char_literal68);


            pushFollow(FOLLOW_dotname_in_function765);
            dotname69=dotname();

            state._fsp--;

            stream_dotname.add(dotname69.getTree());

            char_literal70=(Token)match(input,58,FOLLOW_58_in_function767);  
            stream_58.add(char_literal70);


            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:117:27: ( fparamlist )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==FLOAT||LA20_0==ID||LA20_0==INT||LA20_0==STRING||LA20_0==UTF8||(LA20_0 >= 57 && LA20_0 <= 58)||LA20_0==63) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:117:27: fparamlist
                    {
                    pushFollow(FOLLOW_fparamlist_in_function769);
                    fparamlist71=fparamlist();

                    state._fsp--;

                    stream_fparamlist.add(fparamlist71.getTree());

                    }
                    break;

            }


            char_literal72=(Token)match(input,59,FOLLOW_59_in_function772);  
            stream_59.add(char_literal72);


            // AST REWRITE
            // elements: fparamlist, dotname
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 117:43: -> ^( FUNC dotname ( fparamlist )? )
            {
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:117:46: ^( FUNC dotname ( fparamlist )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(FUNC, "FUNC")
                , root_1);

                adaptor.addChild(root_1, stream_dotname.nextTree());

                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:117:61: ( fparamlist )?
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:120:1: predicate : ID '(' paramlist ')' -> ID paramlist ;
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
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_paramlist=new RewriteRuleSubtreeStream(adaptor,"rule paramlist");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:120:11: ( ID '(' paramlist ')' -> ID paramlist )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:120:12: ID '(' paramlist ')'
            {
            ID73=(Token)match(input,ID,FOLLOW_ID_in_predicate792);  
            stream_ID.add(ID73);


            char_literal74=(Token)match(input,58,FOLLOW_58_in_predicate794);  
            stream_58.add(char_literal74);


            pushFollow(FOLLOW_paramlist_in_predicate796);
            paramlist75=paramlist();

            state._fsp--;

            stream_paramlist.add(paramlist75.getTree());

            char_literal76=(Token)match(input,59,FOLLOW_59_in_predicate798);  
            stream_59.add(char_literal76);


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
            // 120:33: -> ID paramlist
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:122:1: paramlist : param ( ',' ! param )* ;
    public final SociaLiteParser.paramlist_return paramlist() throws RecognitionException {
        SociaLiteParser.paramlist_return retval = new SociaLiteParser.paramlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal78=null;
        SociaLiteParser.param_return param77 =null;

        SociaLiteParser.param_return param79 =null;


        CommonTree char_literal78_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:122:11: ( param ( ',' ! param )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:122:12: param ( ',' ! param )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_param_in_paramlist811);
            param77=param();

            state._fsp--;

            adaptor.addChild(root_0, param77.getTree());

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:122:18: ( ',' ! param )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==62) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:122:19: ',' ! param
            	    {
            	    char_literal78=(Token)match(input,62,FOLLOW_62_in_paramlist814); 

            	    pushFollow(FOLLOW_param_in_paramlist817);
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:123:1: fparamlist : fparam ( ',' ! fparam )* ;
    public final SociaLiteParser.fparamlist_return fparamlist() throws RecognitionException {
        SociaLiteParser.fparamlist_return retval = new SociaLiteParser.fparamlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal81=null;
        SociaLiteParser.fparam_return fparam80 =null;

        SociaLiteParser.fparam_return fparam82 =null;


        CommonTree char_literal81_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:123:12: ( fparam ( ',' ! fparam )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:123:13: fparam ( ',' ! fparam )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_fparam_in_fparamlist825);
            fparam80=fparam();

            state._fsp--;

            adaptor.addChild(root_0, fparam80.getTree());

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:123:20: ( ',' ! fparam )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==62) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:123:21: ',' ! fparam
            	    {
            	    char_literal81=(Token)match(input,62,FOLLOW_62_in_fparamlist828); 

            	    pushFollow(FOLLOW_fparam_in_fparamlist831);
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:125:1: param : simpleExpr ;
    public final SociaLiteParser.param_return param() throws RecognitionException {
        SociaLiteParser.param_return retval = new SociaLiteParser.param_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SociaLiteParser.simpleExpr_return simpleExpr83 =null;



        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:125:7: ( simpleExpr )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:125:8: simpleExpr
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_simpleExpr_in_param840);
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:126:1: fparam : simpleExpr ;
    public final SociaLiteParser.fparam_return fparam() throws RecognitionException {
        SociaLiteParser.fparam_return retval = new SociaLiteParser.fparam_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SociaLiteParser.simpleExpr_return simpleExpr84 =null;



        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:126:8: ( simpleExpr )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:126:9: simpleExpr
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_simpleExpr_in_fparam846);
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:128:1: term : ( INT -> ^( T_INT INT ) | FLOAT -> ^( T_FLOAT FLOAT ) | STRING -> ^( T_STR STRING ) | UTF8 -> ^( T_UTF8 UTF8 ) | dotname -> ^( T_VAR dotname ) );
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
        RewriteRuleTokenStream stream_UTF8=new RewriteRuleTokenStream(adaptor,"token UTF8");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleSubtreeStream stream_dotname=new RewriteRuleSubtreeStream(adaptor,"rule dotname");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:128:5: ( INT -> ^( T_INT INT ) | FLOAT -> ^( T_FLOAT FLOAT ) | STRING -> ^( T_STR STRING ) | UTF8 -> ^( T_UTF8 UTF8 ) | dotname -> ^( T_VAR dotname ) )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:128:7: INT
                    {
                    INT85=(Token)match(input,INT,FOLLOW_INT_in_term853);  
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
                    // 128:11: -> ^( T_INT INT )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:128:14: ^( T_INT INT )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:129:3: FLOAT
                    {
                    FLOAT86=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_term866);  
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
                    // 129:9: -> ^( T_FLOAT FLOAT )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:129:12: ^( T_FLOAT FLOAT )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:130:3: STRING
                    {
                    STRING87=(Token)match(input,STRING,FOLLOW_STRING_in_term878);  
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
                    // 130:10: -> ^( T_STR STRING )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:130:13: ^( T_STR STRING )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:131:3: UTF8
                    {
                    UTF888=(Token)match(input,UTF8,FOLLOW_UTF8_in_term890);  
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
                    // 131:8: -> ^( T_UTF8 UTF8 )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:131:11: ^( T_UTF8 UTF8 )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:132:3: dotname
                    {
                    pushFollow(FOLLOW_dotname_in_term903);
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
                    // 132:11: -> ^( T_VAR dotname )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:132:14: ^( T_VAR dotname )
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:134:1: table_decl : ID '(' decls ')' ( table_opts )? DOT_END -> ^( DECL ID decls ^( OPTION ( table_opts )? ) ) ;
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
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleTokenStream stream_DOT_END=new RewriteRuleTokenStream(adaptor,"token DOT_END");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_table_opts=new RewriteRuleSubtreeStream(adaptor,"rule table_opts");
        RewriteRuleSubtreeStream stream_decls=new RewriteRuleSubtreeStream(adaptor,"rule decls");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:134:11: ( ID '(' decls ')' ( table_opts )? DOT_END -> ^( DECL ID decls ^( OPTION ( table_opts )? ) ) )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:134:13: ID '(' decls ')' ( table_opts )? DOT_END
            {
            ID90=(Token)match(input,ID,FOLLOW_ID_in_table_decl919);  
            stream_ID.add(ID90);


            char_literal91=(Token)match(input,58,FOLLOW_58_in_table_decl921);  
            stream_58.add(char_literal91);


            pushFollow(FOLLOW_decls_in_table_decl923);
            decls92=decls();

            state._fsp--;

            stream_decls.add(decls92.getTree());

            char_literal93=(Token)match(input,59,FOLLOW_59_in_table_decl925);  
            stream_59.add(char_literal93);


            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:134:30: ( table_opts )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==82||(LA24_0 >= 86 && LA24_0 <= 87)||(LA24_0 >= 91 && LA24_0 <= 95)) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:134:30: table_opts
                    {
                    pushFollow(FOLLOW_table_opts_in_table_decl927);
                    table_opts94=table_opts();

                    state._fsp--;

                    stream_table_opts.add(table_opts94.getTree());

                    }
                    break;

            }


            DOT_END95=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_table_decl930);  
            stream_DOT_END.add(DOT_END95);


            // AST REWRITE
            // elements: decls, ID, table_opts
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 134:51: -> ^( DECL ID decls ^( OPTION ( table_opts )? ) )
            {
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:134:54: ^( DECL ID decls ^( OPTION ( table_opts )? ) )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(DECL, "DECL")
                , root_1);

                adaptor.addChild(root_1, 
                stream_ID.nextNode()
                );

                adaptor.addChild(root_1, stream_decls.nextTree());

                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:134:70: ^( OPTION ( table_opts )? )
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(OPTION, "OPTION")
                , root_2);

                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:134:79: ( table_opts )?
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:136:1: table_opts : t_opt ( ',' ! t_opt )* ;
    public final SociaLiteParser.table_opts_return table_opts() throws RecognitionException {
        SociaLiteParser.table_opts_return retval = new SociaLiteParser.table_opts_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal97=null;
        SociaLiteParser.t_opt_return t_opt96 =null;

        SociaLiteParser.t_opt_return t_opt98 =null;


        CommonTree char_literal97_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:136:11: ( t_opt ( ',' ! t_opt )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:136:13: t_opt ( ',' ! t_opt )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_t_opt_in_table_opts963);
            t_opt96=t_opt();

            state._fsp--;

            adaptor.addChild(root_0, t_opt96.getTree());

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:136:19: ( ',' ! t_opt )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==62) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:136:20: ',' ! t_opt
            	    {
            	    char_literal97=(Token)match(input,62,FOLLOW_62_in_table_opts966); 

            	    pushFollow(FOLLOW_t_opt_in_table_opts969);
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:138:1: t_opt : ( 'sortby' col= ID (order= SORT_ORDER )? -> ^( SORT_BY $col ( $order)? ) | 'orderby' ID -> ^( ORDER_BY ID ) | 'indexby' ID -> ^( INDEX_BY ID ) | 'groupby' '(' INT ')' -> ^( GROUP_BY INT ) | 'shardby' ID -> ^( SHARD_BY ID ) | 'predefined' -> PREDEFINED | 'concurrent' -> CONCURRENT | 'multiset' -> MULTISET );
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
        Token ID109=null;
        Token string_literal110=null;
        Token string_literal111=null;
        Token string_literal112=null;

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
        CommonTree ID109_tree=null;
        CommonTree string_literal110_tree=null;
        CommonTree string_literal111_tree=null;
        CommonTree string_literal112_tree=null;
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleTokenStream stream_SORT_ORDER=new RewriteRuleTokenStream(adaptor,"token SORT_ORDER");
        RewriteRuleTokenStream stream_91=new RewriteRuleTokenStream(adaptor,"token 91");
        RewriteRuleTokenStream stream_92=new RewriteRuleTokenStream(adaptor,"token 92");
        RewriteRuleTokenStream stream_93=new RewriteRuleTokenStream(adaptor,"token 93");
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_94=new RewriteRuleTokenStream(adaptor,"token 94");
        RewriteRuleTokenStream stream_95=new RewriteRuleTokenStream(adaptor,"token 95");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:138:7: ( 'sortby' col= ID (order= SORT_ORDER )? -> ^( SORT_BY $col ( $order)? ) | 'orderby' ID -> ^( ORDER_BY ID ) | 'indexby' ID -> ^( INDEX_BY ID ) | 'groupby' '(' INT ')' -> ^( GROUP_BY INT ) | 'shardby' ID -> ^( SHARD_BY ID ) | 'predefined' -> PREDEFINED | 'concurrent' -> CONCURRENT | 'multiset' -> MULTISET )
            int alt27=8;
            switch ( input.LA(1) ) {
            case 95:
                {
                alt27=1;
                }
                break;
            case 92:
                {
                alt27=2;
                }
                break;
            case 87:
                {
                alt27=3;
                }
                break;
            case 86:
                {
                alt27=4;
                }
                break;
            case 94:
                {
                alt27=5;
                }
                break;
            case 93:
                {
                alt27=6;
                }
                break;
            case 82:
                {
                alt27=7;
                }
                break;
            case 91:
                {
                alt27=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;

            }

            switch (alt27) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:138:8: 'sortby' col= ID (order= SORT_ORDER )?
                    {
                    string_literal99=(Token)match(input,95,FOLLOW_95_in_t_opt979);  
                    stream_95.add(string_literal99);


                    col=(Token)match(input,ID,FOLLOW_ID_in_t_opt983);  
                    stream_ID.add(col);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:138:24: (order= SORT_ORDER )?
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==SORT_ORDER) ) {
                        alt26=1;
                    }
                    switch (alt26) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:138:25: order= SORT_ORDER
                            {
                            order=(Token)match(input,SORT_ORDER,FOLLOW_SORT_ORDER_in_t_opt988);  
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
                    // 138:44: -> ^( SORT_BY $col ( $order)? )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:138:47: ^( SORT_BY $col ( $order)? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(SORT_BY, "SORT_BY")
                        , root_1);

                        adaptor.addChild(root_1, stream_col.nextNode());

                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:138:63: ( $order)?
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:139:3: 'orderby' ID
                    {
                    string_literal100=(Token)match(input,92,FOLLOW_92_in_t_opt1007);  
                    stream_92.add(string_literal100);


                    ID101=(Token)match(input,ID,FOLLOW_ID_in_t_opt1009);  
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
                    // 139:16: -> ^( ORDER_BY ID )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:139:19: ^( ORDER_BY ID )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:140:3: 'indexby' ID
                    {
                    string_literal102=(Token)match(input,87,FOLLOW_87_in_t_opt1021);  
                    stream_87.add(string_literal102);


                    ID103=(Token)match(input,ID,FOLLOW_ID_in_t_opt1023);  
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
                    // 140:16: -> ^( INDEX_BY ID )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:140:19: ^( INDEX_BY ID )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:141:3: 'groupby' '(' INT ')'
                    {
                    string_literal104=(Token)match(input,86,FOLLOW_86_in_t_opt1035);  
                    stream_86.add(string_literal104);


                    char_literal105=(Token)match(input,58,FOLLOW_58_in_t_opt1037);  
                    stream_58.add(char_literal105);


                    INT106=(Token)match(input,INT,FOLLOW_INT_in_t_opt1039);  
                    stream_INT.add(INT106);


                    char_literal107=(Token)match(input,59,FOLLOW_59_in_t_opt1041);  
                    stream_59.add(char_literal107);


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
                    // 141:25: -> ^( GROUP_BY INT )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:141:28: ^( GROUP_BY INT )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:142:3: 'shardby' ID
                    {
                    string_literal108=(Token)match(input,94,FOLLOW_94_in_t_opt1053);  
                    stream_94.add(string_literal108);


                    ID109=(Token)match(input,ID,FOLLOW_ID_in_t_opt1055);  
                    stream_ID.add(ID109);


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
                    // 142:16: -> ^( SHARD_BY ID )
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:142:19: ^( SHARD_BY ID )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(SHARD_BY, "SHARD_BY")
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
                case 6 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:143:3: 'predefined'
                    {
                    string_literal110=(Token)match(input,93,FOLLOW_93_in_t_opt1068);  
                    stream_93.add(string_literal110);


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
                    // 143:16: -> PREDEFINED
                    {
                        adaptor.addChild(root_0, 
                        (CommonTree)adaptor.create(PREDEFINED, "PREDEFINED")
                        );

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 7 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:144:3: 'concurrent'
                    {
                    string_literal111=(Token)match(input,82,FOLLOW_82_in_t_opt1076);  
                    stream_82.add(string_literal111);


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
                    // 144:16: -> CONCURRENT
                    {
                        adaptor.addChild(root_0, 
                        (CommonTree)adaptor.create(CONCURRENT, "CONCURRENT")
                        );

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 8 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:145:3: 'multiset'
                    {
                    string_literal112=(Token)match(input,91,FOLLOW_91_in_t_opt1084);  
                    stream_91.add(string_literal112);


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
                    // 145:14: -> MULTISET
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:149:1: decls : col_decls ( ',' '(' decls ')' )? -> ^( COL_DECLS col_decls ^( DECL ( decls )? ) ) ;
    public final SociaLiteParser.decls_return decls() throws RecognitionException {
        SociaLiteParser.decls_return retval = new SociaLiteParser.decls_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal114=null;
        Token char_literal115=null;
        Token char_literal117=null;
        SociaLiteParser.col_decls_return col_decls113 =null;

        SociaLiteParser.decls_return decls116 =null;


        CommonTree char_literal114_tree=null;
        CommonTree char_literal115_tree=null;
        CommonTree char_literal117_tree=null;
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleTokenStream stream_62=new RewriteRuleTokenStream(adaptor,"token 62");
        RewriteRuleSubtreeStream stream_decls=new RewriteRuleSubtreeStream(adaptor,"rule decls");
        RewriteRuleSubtreeStream stream_col_decls=new RewriteRuleSubtreeStream(adaptor,"rule col_decls");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:149:7: ( col_decls ( ',' '(' decls ')' )? -> ^( COL_DECLS col_decls ^( DECL ( decls )? ) ) )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:149:9: col_decls ( ',' '(' decls ')' )?
            {
            pushFollow(FOLLOW_col_decls_in_decls1107);
            col_decls113=col_decls();

            state._fsp--;

            stream_col_decls.add(col_decls113.getTree());

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:149:19: ( ',' '(' decls ')' )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==62) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:149:20: ',' '(' decls ')'
                    {
                    char_literal114=(Token)match(input,62,FOLLOW_62_in_decls1110);  
                    stream_62.add(char_literal114);


                    char_literal115=(Token)match(input,58,FOLLOW_58_in_decls1112);  
                    stream_58.add(char_literal115);


                    pushFollow(FOLLOW_decls_in_decls1114);
                    decls116=decls();

                    state._fsp--;

                    stream_decls.add(decls116.getTree());

                    char_literal117=(Token)match(input,59,FOLLOW_59_in_decls1116);  
                    stream_59.add(char_literal117);


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
            // 149:40: -> ^( COL_DECLS col_decls ^( DECL ( decls )? ) )
            {
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:149:43: ^( COL_DECLS col_decls ^( DECL ( decls )? ) )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(COL_DECLS, "COL_DECLS")
                , root_1);

                adaptor.addChild(root_1, stream_col_decls.nextTree());

                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:149:65: ^( DECL ( decls )? )
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(DECL, "DECL")
                , root_2);

                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:149:72: ( decls )?
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:151:1: col_decls : col_decl ( ',' ! col_decl )* ;
    public final SociaLiteParser.col_decls_return col_decls() throws RecognitionException {
        SociaLiteParser.col_decls_return retval = new SociaLiteParser.col_decls_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal119=null;
        SociaLiteParser.col_decl_return col_decl118 =null;

        SociaLiteParser.col_decl_return col_decl120 =null;


        CommonTree char_literal119_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:151:10: ( col_decl ( ',' ! col_decl )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:151:12: col_decl ( ',' ! col_decl )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_col_decl_in_col_decls1142);
            col_decl118=col_decl();

            state._fsp--;

            adaptor.addChild(root_0, col_decl118.getTree());

            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:151:21: ( ',' ! col_decl )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==62) ) {
                    int LA29_1 = input.LA(2);

                    if ( (LA29_1==ID||(LA29_1 >= 76 && LA29_1 <= 77)||LA29_1==83||LA29_1==85||(LA29_1 >= 88 && LA29_1 <= 89)) ) {
                        alt29=1;
                    }


                }


                switch (alt29) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:151:22: ',' ! col_decl
            	    {
            	    char_literal119=(Token)match(input,62,FOLLOW_62_in_col_decls1145); 

            	    pushFollow(FOLLOW_col_decl_in_col_decls1148);
            	    col_decl120=col_decl();

            	    state._fsp--;

            	    adaptor.addChild(root_0, col_decl120.getTree());

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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:153:1: col_decl : type ID ( ':' col_opt )? -> ^( COL_DECL type ID ( col_opt )? ) ;
    public final SociaLiteParser.col_decl_return col_decl() throws RecognitionException {
        SociaLiteParser.col_decl_return retval = new SociaLiteParser.col_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token ID122=null;
        Token char_literal123=null;
        SociaLiteParser.type_return type121 =null;

        SociaLiteParser.col_opt_return col_opt124 =null;


        CommonTree ID122_tree=null;
        CommonTree char_literal123_tree=null;
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_col_opt=new RewriteRuleSubtreeStream(adaptor,"rule col_opt");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:153:9: ( type ID ( ':' col_opt )? -> ^( COL_DECL type ID ( col_opt )? ) )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:153:11: type ID ( ':' col_opt )?
            {
            pushFollow(FOLLOW_type_in_col_decl1158);
            type121=type();

            state._fsp--;

            stream_type.add(type121.getTree());

            ID122=(Token)match(input,ID,FOLLOW_ID_in_col_decl1160);  
            stream_ID.add(ID122);


            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:153:19: ( ':' col_opt )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==66) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:153:20: ':' col_opt
                    {
                    char_literal123=(Token)match(input,66,FOLLOW_66_in_col_decl1163);  
                    stream_66.add(char_literal123);


                    pushFollow(FOLLOW_col_opt_in_col_decl1165);
                    col_opt124=col_opt();

                    state._fsp--;

                    stream_col_opt.add(col_opt124.getTree());

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
            // 153:33: -> ^( COL_DECL type ID ( col_opt )? )
            {
                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:153:36: ^( COL_DECL type ID ( col_opt )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(COL_DECL, "COL_DECL")
                , root_1);

                adaptor.addChild(root_1, stream_type.nextTree());

                adaptor.addChild(root_1, 
                stream_ID.nextNode()
                );

                // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:153:55: ( col_opt )?
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:155:1: col_opt : (i1= INT '..' i2= INT -> ^( RANGE $i1 $i2) | ITER_DECL -> ITER |i1= INT 'bit' -> ^( BIT $i1) );
    public final SociaLiteParser.col_opt_return col_opt() throws RecognitionException {
        SociaLiteParser.col_opt_return retval = new SociaLiteParser.col_opt_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token i1=null;
        Token i2=null;
        Token string_literal125=null;
        Token ITER_DECL126=null;
        Token string_literal127=null;

        CommonTree i1_tree=null;
        CommonTree i2_tree=null;
        CommonTree string_literal125_tree=null;
        CommonTree ITER_DECL126_tree=null;
        CommonTree string_literal127_tree=null;
        RewriteRuleTokenStream stream_80=new RewriteRuleTokenStream(adaptor,"token 80");
        RewriteRuleTokenStream stream_ITER_DECL=new RewriteRuleTokenStream(adaptor,"token ITER_DECL");
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:155:9: (i1= INT '..' i2= INT -> ^( RANGE $i1 $i2) | ITER_DECL -> ITER |i1= INT 'bit' -> ^( BIT $i1) )
            int alt31=3;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==INT) ) {
                int LA31_1 = input.LA(2);

                if ( (LA31_1==64) ) {
                    alt31=1;
                }
                else if ( (LA31_1==80) ) {
                    alt31=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 31, 1, input);

                    throw nvae;

                }
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:155:11: i1= INT '..' i2= INT
                    {
                    i1=(Token)match(input,INT,FOLLOW_INT_in_col_opt1190);  
                    stream_INT.add(i1);


                    string_literal125=(Token)match(input,64,FOLLOW_64_in_col_opt1192);  
                    stream_64.add(string_literal125);


                    i2=(Token)match(input,INT,FOLLOW_INT_in_col_opt1196);  
                    stream_INT.add(i2);


                    // AST REWRITE
                    // elements: i1, i2
                    // token labels: i1, i2
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_i1=new RewriteRuleTokenStream(adaptor,"token i1",i1);
                    RewriteRuleTokenStream stream_i2=new RewriteRuleTokenStream(adaptor,"token i2",i2);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 155:30: -> ^( RANGE $i1 $i2)
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:155:33: ^( RANGE $i1 $i2)
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:156:4: ITER_DECL
                    {
                    ITER_DECL126=(Token)match(input,ITER_DECL,FOLLOW_ITER_DECL_in_col_opt1213);  
                    stream_ITER_DECL.add(ITER_DECL126);


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
                    // 156:14: -> ITER
                    {
                        adaptor.addChild(root_0, 
                        (CommonTree)adaptor.create(ITER, "ITER")
                        );

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 3 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:157:4: i1= INT 'bit'
                    {
                    i1=(Token)match(input,INT,FOLLOW_INT_in_col_opt1224);  
                    stream_INT.add(i1);


                    string_literal127=(Token)match(input,80,FOLLOW_80_in_col_opt1226);  
                    stream_80.add(string_literal127);


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
                    // 157:17: -> ^( BIT $i1)
                    {
                        // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:157:20: ^( BIT $i1)
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(BIT, "BIT")
                        , root_1);

                        adaptor.addChild(root_1, stream_i1.nextNode());

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
    // $ANTLR end "col_opt"


    public static class type_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "type"
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:160:1: type : ( 'int' ( '[' ']' )? | 'long' ( '[' ']' )? | 'float' ( '[' ']' )? | 'double' ( '[' ']' )? | 'String' ( '[' ']' )? | 'Object' ( '[' ']' )? | ID ( '[' ']' )? );
    public final SociaLiteParser.type_return type() throws RecognitionException {
        SociaLiteParser.type_return retval = new SociaLiteParser.type_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

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
        Token string_literal143=null;
        Token char_literal144=null;
        Token char_literal145=null;
        Token ID146=null;
        Token char_literal147=null;
        Token char_literal148=null;

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
        CommonTree string_literal143_tree=null;
        CommonTree char_literal144_tree=null;
        CommonTree char_literal145_tree=null;
        CommonTree ID146_tree=null;
        CommonTree char_literal147_tree=null;
        CommonTree char_literal148_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:160:5: ( 'int' ( '[' ']' )? | 'long' ( '[' ']' )? | 'float' ( '[' ']' )? | 'double' ( '[' ']' )? | 'String' ( '[' ']' )? | 'Object' ( '[' ']' )? | ID ( '[' ']' )? )
            int alt39=7;
            switch ( input.LA(1) ) {
            case 88:
                {
                alt39=1;
                }
                break;
            case 89:
                {
                alt39=2;
                }
                break;
            case 85:
                {
                alt39=3;
                }
                break;
            case 83:
                {
                alt39=4;
                }
                break;
            case 77:
                {
                alt39=5;
                }
                break;
            case 76:
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:160:7: 'int' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal128=(Token)match(input,88,FOLLOW_88_in_type1244); 
                    string_literal128_tree = 
                    (CommonTree)adaptor.create(string_literal128)
                    ;
                    adaptor.addChild(root_0, string_literal128_tree);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:160:13: ( '[' ']' )?
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==78) ) {
                        alt32=1;
                    }
                    switch (alt32) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:160:14: '[' ']'
                            {
                            char_literal129=(Token)match(input,78,FOLLOW_78_in_type1247); 
                            char_literal129_tree = 
                            (CommonTree)adaptor.create(char_literal129)
                            ;
                            adaptor.addChild(root_0, char_literal129_tree);


                            char_literal130=(Token)match(input,79,FOLLOW_79_in_type1249); 
                            char_literal130_tree = 
                            (CommonTree)adaptor.create(char_literal130)
                            ;
                            adaptor.addChild(root_0, char_literal130_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:161:3: 'long' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal131=(Token)match(input,89,FOLLOW_89_in_type1255); 
                    string_literal131_tree = 
                    (CommonTree)adaptor.create(string_literal131)
                    ;
                    adaptor.addChild(root_0, string_literal131_tree);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:161:10: ( '[' ']' )?
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==78) ) {
                        alt33=1;
                    }
                    switch (alt33) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:161:11: '[' ']'
                            {
                            char_literal132=(Token)match(input,78,FOLLOW_78_in_type1258); 
                            char_literal132_tree = 
                            (CommonTree)adaptor.create(char_literal132)
                            ;
                            adaptor.addChild(root_0, char_literal132_tree);


                            char_literal133=(Token)match(input,79,FOLLOW_79_in_type1260); 
                            char_literal133_tree = 
                            (CommonTree)adaptor.create(char_literal133)
                            ;
                            adaptor.addChild(root_0, char_literal133_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:162:3: 'float' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal134=(Token)match(input,85,FOLLOW_85_in_type1268); 
                    string_literal134_tree = 
                    (CommonTree)adaptor.create(string_literal134)
                    ;
                    adaptor.addChild(root_0, string_literal134_tree);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:162:11: ( '[' ']' )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==78) ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:162:12: '[' ']'
                            {
                            char_literal135=(Token)match(input,78,FOLLOW_78_in_type1271); 
                            char_literal135_tree = 
                            (CommonTree)adaptor.create(char_literal135)
                            ;
                            adaptor.addChild(root_0, char_literal135_tree);


                            char_literal136=(Token)match(input,79,FOLLOW_79_in_type1273); 
                            char_literal136_tree = 
                            (CommonTree)adaptor.create(char_literal136)
                            ;
                            adaptor.addChild(root_0, char_literal136_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:163:3: 'double' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal137=(Token)match(input,83,FOLLOW_83_in_type1281); 
                    string_literal137_tree = 
                    (CommonTree)adaptor.create(string_literal137)
                    ;
                    adaptor.addChild(root_0, string_literal137_tree);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:163:12: ( '[' ']' )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==78) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:163:13: '[' ']'
                            {
                            char_literal138=(Token)match(input,78,FOLLOW_78_in_type1284); 
                            char_literal138_tree = 
                            (CommonTree)adaptor.create(char_literal138)
                            ;
                            adaptor.addChild(root_0, char_literal138_tree);


                            char_literal139=(Token)match(input,79,FOLLOW_79_in_type1286); 
                            char_literal139_tree = 
                            (CommonTree)adaptor.create(char_literal139)
                            ;
                            adaptor.addChild(root_0, char_literal139_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:164:3: 'String' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal140=(Token)match(input,77,FOLLOW_77_in_type1294); 
                    string_literal140_tree = 
                    (CommonTree)adaptor.create(string_literal140)
                    ;
                    adaptor.addChild(root_0, string_literal140_tree);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:164:12: ( '[' ']' )?
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==78) ) {
                        alt36=1;
                    }
                    switch (alt36) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:164:13: '[' ']'
                            {
                            char_literal141=(Token)match(input,78,FOLLOW_78_in_type1297); 
                            char_literal141_tree = 
                            (CommonTree)adaptor.create(char_literal141)
                            ;
                            adaptor.addChild(root_0, char_literal141_tree);


                            char_literal142=(Token)match(input,79,FOLLOW_79_in_type1299); 
                            char_literal142_tree = 
                            (CommonTree)adaptor.create(char_literal142)
                            ;
                            adaptor.addChild(root_0, char_literal142_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 6 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:165:3: 'Object' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal143=(Token)match(input,76,FOLLOW_76_in_type1307); 
                    string_literal143_tree = 
                    (CommonTree)adaptor.create(string_literal143)
                    ;
                    adaptor.addChild(root_0, string_literal143_tree);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:165:12: ( '[' ']' )?
                    int alt37=2;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0==78) ) {
                        alt37=1;
                    }
                    switch (alt37) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:165:13: '[' ']'
                            {
                            char_literal144=(Token)match(input,78,FOLLOW_78_in_type1310); 
                            char_literal144_tree = 
                            (CommonTree)adaptor.create(char_literal144)
                            ;
                            adaptor.addChild(root_0, char_literal144_tree);


                            char_literal145=(Token)match(input,79,FOLLOW_79_in_type1312); 
                            char_literal145_tree = 
                            (CommonTree)adaptor.create(char_literal145)
                            ;
                            adaptor.addChild(root_0, char_literal145_tree);


                            }
                            break;

                    }


                    }
                    break;
                case 7 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:166:4: ID ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    ID146=(Token)match(input,ID,FOLLOW_ID_in_type1319); 
                    ID146_tree = 
                    (CommonTree)adaptor.create(ID146)
                    ;
                    adaptor.addChild(root_0, ID146_tree);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:166:7: ( '[' ']' )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==78) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:166:8: '[' ']'
                            {
                            char_literal147=(Token)match(input,78,FOLLOW_78_in_type1322); 
                            char_literal147_tree = 
                            (CommonTree)adaptor.create(char_literal147)
                            ;
                            adaptor.addChild(root_0, char_literal147_tree);


                            char_literal148=(Token)match(input,79,FOLLOW_79_in_type1324); 
                            char_literal148_tree = 
                            (CommonTree)adaptor.create(char_literal148)
                            ;
                            adaptor.addChild(root_0, char_literal148_tree);


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
    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:168:1: dotname : ID ( DOT_ID )* ;
    public final SociaLiteParser.dotname_return dotname() throws RecognitionException {
        SociaLiteParser.dotname_return retval = new SociaLiteParser.dotname_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token ID149=null;
        Token DOT_ID150=null;

        CommonTree ID149_tree=null;
        CommonTree DOT_ID150_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:168:9: ( ID ( DOT_ID )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:168:10: ID ( DOT_ID )*
            {
            root_0 = (CommonTree)adaptor.nil();


            ID149=(Token)match(input,ID,FOLLOW_ID_in_dotname1336); 
            ID149_tree = 
            (CommonTree)adaptor.create(ID149)
            ;
            adaptor.addChild(root_0, ID149_tree);


            // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:168:13: ( DOT_ID )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==DOT_ID) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLite.g:168:14: DOT_ID
            	    {
            	    DOT_ID150=(Token)match(input,DOT_ID,FOLLOW_DOT_ID_in_dotname1339); 
            	    DOT_ID150_tree = 
            	    (CommonTree)adaptor.create(DOT_ID150)
            	    ;
            	    adaptor.addChild(root_0, DOT_ID150_tree);


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
        "\1\24\1\uffff\1\24\2\17\1\uffff";
    static final String DFA9_maxS =
        "\1\77\1\uffff\1\131\2\132\1\uffff";
    static final String DFA9_acceptS =
        "\1\uffff\1\1\3\uffff\1\2";
    static final String DFA9_specialS =
        "\6\uffff}>";
    static final String[] DFA9_transitionS = {
            "\1\1\4\uffff\1\1\2\uffff\1\1\21\uffff\1\1\7\uffff\1\1\2\uffff"+
            "\1\1\1\2\4\uffff\1\1",
            "",
            "\1\1\4\uffff\1\3\2\uffff\1\1\21\uffff\1\1\7\uffff\1\1\2\uffff"+
            "\2\1\4\uffff\1\1\14\uffff\2\1\5\uffff\1\1\1\uffff\1\1\2\uffff"+
            "\2\1",
            "\1\4\53\uffff\3\1\1\5\1\1\1\uffff\1\1\14\uffff\1\1\13\uffff"+
            "\1\1",
            "\1\4\53\uffff\3\1\1\5\1\1\1\uffff\1\1\30\uffff\1\1",
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
            return "104:1: expr : ( simpleExpr ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^) simpleExpr | varlist '=' ( cast )? function -> ^( MULTI_ASSIGN varlist function ( cast )? ) );";
        }
    }
 

    public static final BitSet FOLLOW_stat_in_prog304 = new BitSet(new long[]{0x0000000002000000L,0x0000000000120800L});
    public static final BitSet FOLLOW_EOF_in_prog307 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_decl_in_stat313 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule_in_stat317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_query_in_stat321 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_stmt_in_stat325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_table_stmt334 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_ID_in_table_stmt336 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_DOT_END_in_table_stmt338 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_table_stmt351 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_ID_in_table_stmt353 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_DOT_END_in_table_stmt355 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_query373 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_predicate_in_query375 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_DOT_END_in_query377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_head_in_rule398 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_rule400 = new BitSet(new long[]{0x8640400212100000L});
    public static final BitSet FOLLOW_litlist_in_rule402 = new BitSet(new long[]{0x0000000000004000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_rule405 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_rule407 = new BitSet(new long[]{0x8640400212100000L});
    public static final BitSet FOLLOW_litlist_in_rule409 = new BitSet(new long[]{0x0000000000004000L,0x0000000000000010L});
    public static final BitSet FOLLOW_DOT_END_in_rule413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_predicate_in_head450 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_litlist457 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_62_in_litlist460 = new BitSet(new long[]{0x8640400212100000L});
    public static final BitSet FOLLOW_literal_in_litlist463 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_predicate_in_literal472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_literal486 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_predicate_in_literal488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_literal502 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleExpr_in_expr519 = new BitSet(new long[]{0x0100000000000000L,0x00000000000007E0L});
    public static final BitSet FOLLOW_69_in_expr523 = new BitSet(new long[]{0x8640400012100000L});
    public static final BitSet FOLLOW_73_in_expr528 = new BitSet(new long[]{0x8640400012100000L});
    public static final BitSet FOLLOW_71_in_expr533 = new BitSet(new long[]{0x8640400012100000L});
    public static final BitSet FOLLOW_56_in_expr537 = new BitSet(new long[]{0x8640400012100000L});
    public static final BitSet FOLLOW_72_in_expr542 = new BitSet(new long[]{0x8640400012100000L});
    public static final BitSet FOLLOW_74_in_expr547 = new BitSet(new long[]{0x8640400012100000L});
    public static final BitSet FOLLOW_70_in_expr552 = new BitSet(new long[]{0x8640400012100000L});
    public static final BitSet FOLLOW_simpleExpr_in_expr557 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varlist_in_expr562 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_expr564 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_cast_in_expr566 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_function_in_expr569 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multExpr_in_simpleExpr591 = new BitSet(new long[]{0xA000000000000002L});
    public static final BitSet FOLLOW_61_in_simpleExpr596 = new BitSet(new long[]{0x8640400012100000L});
    public static final BitSet FOLLOW_63_in_simpleExpr599 = new BitSet(new long[]{0x8640400012100000L});
    public static final BitSet FOLLOW_multExpr_in_simpleExpr603 = new BitSet(new long[]{0xA000000000000002L});
    public static final BitSet FOLLOW_exprValue_in_multExpr614 = new BitSet(new long[]{0x1000000000000002L,0x0000000004000002L});
    public static final BitSet FOLLOW_60_in_multExpr618 = new BitSet(new long[]{0x8640400012100000L});
    public static final BitSet FOLLOW_65_in_multExpr621 = new BitSet(new long[]{0x8640400012100000L});
    public static final BitSet FOLLOW_90_in_multExpr624 = new BitSet(new long[]{0x8640400012100000L});
    public static final BitSet FOLLOW_exprValue_in_multExpr628 = new BitSet(new long[]{0x1000000000000002L,0x0000000004000002L});
    public static final BitSet FOLLOW_63_in_exprValue639 = new BitSet(new long[]{0x0440400012100000L});
    public static final BitSet FOLLOW_cast_in_exprValue643 = new BitSet(new long[]{0x0040400012100000L});
    public static final BitSet FOLLOW_term_in_exprValue646 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cast_in_exprValue666 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_function_in_exprValue669 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cast_in_exprValue685 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_compExpr_in_exprValue688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_compExpr707 = new BitSet(new long[]{0x8640400012100000L});
    public static final BitSet FOLLOW_simpleExpr_in_compExpr710 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_compExpr712 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_cast721 = new BitSet(new long[]{0x0000000002000000L,0x0000000003283000L});
    public static final BitSet FOLLOW_type_in_cast723 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_cast725 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_varlist737 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_dotname_in_varlist742 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_62_in_varlist746 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_dotname_in_varlist751 = new BitSet(new long[]{0x4800000000000000L});
    public static final BitSet FOLLOW_59_in_varlist755 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_function763 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_dotname_in_function765 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_function767 = new BitSet(new long[]{0x8E40400012100000L});
    public static final BitSet FOLLOW_fparamlist_in_function769 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_function772 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_predicate792 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_predicate794 = new BitSet(new long[]{0x8640400012100000L});
    public static final BitSet FOLLOW_paramlist_in_predicate796 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_predicate798 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_param_in_paramlist811 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_62_in_paramlist814 = new BitSet(new long[]{0x8640400012100000L});
    public static final BitSet FOLLOW_param_in_paramlist817 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_fparam_in_fparamlist825 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_62_in_fparamlist828 = new BitSet(new long[]{0x8640400012100000L});
    public static final BitSet FOLLOW_fparam_in_fparamlist831 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_simpleExpr_in_param840 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleExpr_in_fparam846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_term853 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_term866 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_term878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UTF8_in_term890 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dotname_in_term903 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_table_decl919 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_table_decl921 = new BitSet(new long[]{0x0000000002000000L,0x0000000003283000L});
    public static final BitSet FOLLOW_decls_in_table_decl923 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_table_decl925 = new BitSet(new long[]{0x0000000000004000L,0x00000000F8C40000L});
    public static final BitSet FOLLOW_table_opts_in_table_decl927 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_DOT_END_in_table_decl930 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_t_opt_in_table_opts963 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_62_in_table_opts966 = new BitSet(new long[]{0x0000000000000000L,0x00000000F8C40000L});
    public static final BitSet FOLLOW_t_opt_in_table_opts969 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_95_in_t_opt979 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_ID_in_t_opt983 = new BitSet(new long[]{0x0000200000000002L});
    public static final BitSet FOLLOW_SORT_ORDER_in_t_opt988 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_t_opt1007 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_ID_in_t_opt1009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_87_in_t_opt1021 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_ID_in_t_opt1023 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_86_in_t_opt1035 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_t_opt1037 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_INT_in_t_opt1039 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_t_opt1041 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_t_opt1053 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_ID_in_t_opt1055 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_t_opt1068 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_t_opt1076 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_91_in_t_opt1084 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_col_decls_in_decls1107 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_62_in_decls1110 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_decls1112 = new BitSet(new long[]{0x0000000002000000L,0x0000000003283000L});
    public static final BitSet FOLLOW_decls_in_decls1114 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_decls1116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_col_decl_in_col_decls1142 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_62_in_col_decls1145 = new BitSet(new long[]{0x0000000002000000L,0x0000000003283000L});
    public static final BitSet FOLLOW_col_decl_in_col_decls1148 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_type_in_col_decl1158 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_ID_in_col_decl1160 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_col_decl1163 = new BitSet(new long[]{0x0000000050000000L});
    public static final BitSet FOLLOW_col_opt_in_col_decl1165 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_col_opt1190 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_col_opt1192 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_INT_in_col_opt1196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ITER_DECL_in_col_opt1213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_col_opt1224 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_col_opt1226 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_type1244 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_type1247 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_type1249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_type1255 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_type1258 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_type1260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_85_in_type1268 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_type1271 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_type1273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_type1281 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_type1284 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_type1286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_type1294 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_type1297 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_type1299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_type1307 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_type1310 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_type1312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_type1319 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_type1322 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_type1324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_dotname1336 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_DOT_ID_in_dotname1339 = new BitSet(new long[]{0x0000000000008002L});

}