// $ANTLR 3.4 /Users/dyoung/opensource/socialite/grammar/SociaLite.g 2015-03-16 10:44:14

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "APPROX", "CHAR", "CLEAR", "COL_DECL", "COL_DECLS", "COMMENT", "COMPOUND_EXPR", "CONCURRENT", "DECL", "DOT_END", "DOT_ID", "DROP", "ESC_SEQ", "EXPONENT", "EXPR", "FLOAT", "FUNC", "FUNCTION", "GROUP_BY", "HEX_DIGIT", "ID", "INDEX", "INDEX_BY", "INT", "ITER", "ITER_DECL", "MULTISET", "MULTI_ASSIGN", "NOT", "OCTAL_ESC", "OPTION", "ORDER_BY", "PREDEFINED", "PREDICATE", "PROG", "QUERY", "RANGE", "RULE", "SORT_BY", "SORT_ORDER", "STRING", "TERM", "T_FLOAT", "T_INT", "T_STR", "T_UTF8", "T_VAR", "UNICODE_ESC", "UTF8", "WS", "'!='", "'$'", "'('", "')'", "'*'", "'+'", "','", "'-'", "'..'", "'/'", "':'", "':-'", "';'", "'<'", "'<='", "'='", "'=='", "'>'", "'>='", "'?-'", "'Object'", "'String'", "'['", "']'", "'clear'", "'concurrent'", "'double'", "'drop'", "'float'", "'groupby'", "'indexby'", "'int'", "'long'", "'mod'", "'multiset'", "'orderby'", "'predefined'", "'sortby'"
    };

    public static final int EOF=-1;
    public static final int T__54=54;
    public static final int T__55=55;
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
    public static final int APPROX=4;
    public static final int CHAR=5;
    public static final int CLEAR=6;
    public static final int COL_DECL=7;
    public static final int COL_DECLS=8;
    public static final int COMMENT=9;
    public static final int COMPOUND_EXPR=10;
    public static final int CONCURRENT=11;
    public static final int DECL=12;
    public static final int DOT_END=13;
    public static final int DOT_ID=14;
    public static final int DROP=15;
    public static final int ESC_SEQ=16;
    public static final int EXPONENT=17;
    public static final int EXPR=18;
    public static final int FLOAT=19;
    public static final int FUNC=20;
    public static final int FUNCTION=21;
    public static final int GROUP_BY=22;
    public static final int HEX_DIGIT=23;
    public static final int ID=24;
    public static final int INDEX=25;
    public static final int INDEX_BY=26;
    public static final int INT=27;
    public static final int ITER=28;
    public static final int ITER_DECL=29;
    public static final int MULTISET=30;
    public static final int MULTI_ASSIGN=31;
    public static final int NOT=32;
    public static final int OCTAL_ESC=33;
    public static final int OPTION=34;
    public static final int ORDER_BY=35;
    public static final int PREDEFINED=36;
    public static final int PREDICATE=37;
    public static final int PROG=38;
    public static final int QUERY=39;
    public static final int RANGE=40;
    public static final int RULE=41;
    public static final int SORT_BY=42;
    public static final int SORT_ORDER=43;
    public static final int STRING=44;
    public static final int TERM=45;
    public static final int T_FLOAT=46;
    public static final int T_INT=47;
    public static final int T_STR=48;
    public static final int T_UTF8=49;
    public static final int T_VAR=50;
    public static final int UNICODE_ESC=51;
    public static final int UTF8=52;
    public static final int WS=53;

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
    public String getGrammarFileName() { return "/Users/dyoung/opensource/socialite/grammar/SociaLite.g"; }


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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:78:1: prog : ( stat )+ EOF ;
    public final SociaLiteParser.prog_return prog() throws RecognitionException {
        SociaLiteParser.prog_return retval = new SociaLiteParser.prog_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token EOF2=null;
        SociaLiteParser.stat_return stat1 =null;


        CommonTree EOF2_tree=null;

        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:78:6: ( ( stat )+ EOF )
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:78:7: ( stat )+ EOF
            {
            root_0 = (CommonTree)adaptor.nil();


            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:78:7: ( stat )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==ID||LA1_0==73||LA1_0==78||LA1_0==81) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:78:7: stat
            	    {
            	    pushFollow(FOLLOW_stat_in_prog290);
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


            EOF2=(Token)match(input,EOF,FOLLOW_EOF_in_prog293); 
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:79:1: stat : ( table_decl | rule | query | table_stmt );
    public final SociaLiteParser.stat_return stat() throws RecognitionException {
        SociaLiteParser.stat_return retval = new SociaLiteParser.stat_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SociaLiteParser.table_decl_return table_decl3 =null;

        SociaLiteParser.rule_return rule4 =null;

        SociaLiteParser.query_return query5 =null;

        SociaLiteParser.table_stmt_return table_stmt6 =null;



        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:79:6: ( table_decl | rule | query | table_stmt )
            int alt2=4;
            switch ( input.LA(1) ) {
            case ID:
                {
                int LA2_1 = input.LA(2);

                if ( (LA2_1==56) ) {
                    switch ( input.LA(3) ) {
                    case 74:
                    case 75:
                    case 80:
                    case 82:
                    case 85:
                    case 86:
                        {
                        alt2=1;
                        }
                        break;
                    case ID:
                        {
                        int LA2_6 = input.LA(4);

                        if ( (LA2_6==ID||LA2_6==76) ) {
                            alt2=1;
                        }
                        else if ( (LA2_6==DOT_ID||(LA2_6 >= 57 && LA2_6 <= 61)||LA2_6==63||LA2_6==87) ) {
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
                    case 55:
                    case 56:
                    case 61:
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
            case 73:
                {
                alt2=3;
                }
                break;
            case 78:
            case 81:
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:79:7: table_decl
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_table_decl_in_stat299);
                    table_decl3=table_decl();

                    state._fsp--;

                    adaptor.addChild(root_0, table_decl3.getTree());

                    }
                    break;
                case 2 :
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:80:3: rule
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_rule_in_stat303);
                    rule4=rule();

                    state._fsp--;

                    adaptor.addChild(root_0, rule4.getTree());

                    }
                    break;
                case 3 :
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:81:3: query
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_query_in_stat307);
                    query5=query();

                    state._fsp--;

                    adaptor.addChild(root_0, query5.getTree());

                    }
                    break;
                case 4 :
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:82:3: table_stmt
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_table_stmt_in_stat311);
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:84:1: table_stmt : ( 'clear' ID DOT_END -> ^( CLEAR ID ) | 'drop' ID DOT_END -> ^( DROP ( ID )? ) );
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
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_81=new RewriteRuleTokenStream(adaptor,"token 81");
        RewriteRuleTokenStream stream_DOT_END=new RewriteRuleTokenStream(adaptor,"token DOT_END");

        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:84:12: ( 'clear' ID DOT_END -> ^( CLEAR ID ) | 'drop' ID DOT_END -> ^( DROP ( ID )? ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==78) ) {
                alt3=1;
            }
            else if ( (LA3_0==81) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;

            }
            switch (alt3) {
                case 1 :
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:84:14: 'clear' ID DOT_END
                    {
                    string_literal7=(Token)match(input,78,FOLLOW_78_in_table_stmt320);  
                    stream_78.add(string_literal7);


                    ID8=(Token)match(input,ID,FOLLOW_ID_in_table_stmt322);  
                    stream_ID.add(ID8);


                    DOT_END9=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_table_stmt324);  
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
                    // 84:33: -> ^( CLEAR ID )
                    {
                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:84:36: ^( CLEAR ID )
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:85:3: 'drop' ID DOT_END
                    {
                    string_literal10=(Token)match(input,81,FOLLOW_81_in_table_stmt337);  
                    stream_81.add(string_literal10);


                    ID11=(Token)match(input,ID,FOLLOW_ID_in_table_stmt339);  
                    stream_ID.add(ID11);


                    DOT_END12=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_table_stmt341);  
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
                    // 85:21: -> ^( DROP ( ID )? )
                    {
                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:85:24: ^( DROP ( ID )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(DROP, "DROP")
                        , root_1);

                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:85:31: ( ID )?
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:88:1: query : '?-' predicate DOT_END -> ^( QUERY predicate ) ;
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
        RewriteRuleTokenStream stream_73=new RewriteRuleTokenStream(adaptor,"token 73");
        RewriteRuleSubtreeStream stream_predicate=new RewriteRuleSubtreeStream(adaptor,"rule predicate");
        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:88:7: ( '?-' predicate DOT_END -> ^( QUERY predicate ) )
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:88:8: '?-' predicate DOT_END
            {
            string_literal13=(Token)match(input,73,FOLLOW_73_in_query359);  
            stream_73.add(string_literal13);


            pushFollow(FOLLOW_predicate_in_query361);
            predicate14=predicate();

            state._fsp--;

            stream_predicate.add(predicate14.getTree());

            DOT_END15=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_query363);  
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
            // 88:31: -> ^( QUERY predicate )
            {
                // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:88:34: ^( QUERY predicate )
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:91:1: rule : head ':-' litlist ( ';' ':-' litlist )* DOT_END -> ( ^( RULE head litlist ) )+ DOT_END ;
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
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleTokenStream stream_DOT_END=new RewriteRuleTokenStream(adaptor,"token DOT_END");
        RewriteRuleSubtreeStream stream_litlist=new RewriteRuleSubtreeStream(adaptor,"rule litlist");
        RewriteRuleSubtreeStream stream_head=new RewriteRuleSubtreeStream(adaptor,"rule head");
        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:91:9: ( head ':-' litlist ( ';' ':-' litlist )* DOT_END -> ( ^( RULE head litlist ) )+ DOT_END )
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:91:11: head ':-' litlist ( ';' ':-' litlist )* DOT_END
            {
            pushFollow(FOLLOW_head_in_rule384);
            head16=head();

            state._fsp--;

            stream_head.add(head16.getTree());

            string_literal17=(Token)match(input,65,FOLLOW_65_in_rule386);  
            stream_65.add(string_literal17);


            pushFollow(FOLLOW_litlist_in_rule388);
            litlist18=litlist();

            state._fsp--;

            stream_litlist.add(litlist18.getTree());

            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:91:29: ( ';' ':-' litlist )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==66) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:91:30: ';' ':-' litlist
            	    {
            	    char_literal19=(Token)match(input,66,FOLLOW_66_in_rule391);  
            	    stream_66.add(char_literal19);


            	    string_literal20=(Token)match(input,65,FOLLOW_65_in_rule393);  
            	    stream_65.add(string_literal20);


            	    pushFollow(FOLLOW_litlist_in_rule395);
            	    litlist21=litlist();

            	    state._fsp--;

            	    stream_litlist.add(litlist21.getTree());

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            DOT_END22=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_rule399);  
            stream_DOT_END.add(DOT_END22);


            // AST REWRITE
            // elements: DOT_END, litlist, head
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 92:9: -> ( ^( RULE head litlist ) )+ DOT_END
            {
                if ( !(stream_litlist.hasNext()||stream_head.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_litlist.hasNext()||stream_head.hasNext() ) {
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:92:12: ^( RULE head litlist )
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:94:1: head : predicate ;
    public final SociaLiteParser.head_return head() throws RecognitionException {
        SociaLiteParser.head_return retval = new SociaLiteParser.head_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SociaLiteParser.predicate_return predicate23 =null;



        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:94:6: ( predicate )
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:94:8: predicate
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_predicate_in_head436);
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:96:1: litlist : literal ( ',' ! literal )* ;
    public final SociaLiteParser.litlist_return litlist() throws RecognitionException {
        SociaLiteParser.litlist_return retval = new SociaLiteParser.litlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal25=null;
        SociaLiteParser.literal_return literal24 =null;

        SociaLiteParser.literal_return literal26 =null;


        CommonTree char_literal25_tree=null;

        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:96:9: ( literal ( ',' ! literal )* )
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:96:10: literal ( ',' ! literal )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_literal_in_litlist443);
            literal24=literal();

            state._fsp--;

            adaptor.addChild(root_0, literal24.getTree());

            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:96:18: ( ',' ! literal )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==60) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:96:19: ',' ! literal
            	    {
            	    char_literal25=(Token)match(input,60,FOLLOW_60_in_litlist446); 

            	    pushFollow(FOLLOW_literal_in_litlist449);
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:98:1: literal : ( predicate -> ^( PREDICATE predicate ) | NOT predicate -> ^( PREDICATE NOT predicate ) | expr -> ^( EXPR expr ) );
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
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:98:9: ( predicate -> ^( PREDICATE predicate ) | NOT predicate -> ^( PREDICATE NOT predicate ) | expr -> ^( EXPR expr ) )
            int alt6=3;
            switch ( input.LA(1) ) {
            case ID:
                {
                int LA6_1 = input.LA(2);

                if ( (LA6_1==56) ) {
                    alt6=1;
                }
                else if ( (LA6_1==DOT_ID||LA6_1==54||(LA6_1 >= 58 && LA6_1 <= 59)||LA6_1==61||LA6_1==63||(LA6_1 >= 67 && LA6_1 <= 72)||LA6_1==87) ) {
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
            case 55:
            case 56:
            case 61:
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:98:10: predicate
                    {
                    pushFollow(FOLLOW_predicate_in_literal458);
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
                    // 98:20: -> ^( PREDICATE predicate )
                    {
                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:98:23: ^( PREDICATE predicate )
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:99:4: NOT predicate
                    {
                    NOT28=(Token)match(input,NOT,FOLLOW_NOT_in_literal472);  
                    stream_NOT.add(NOT28);


                    pushFollow(FOLLOW_predicate_in_literal474);
                    predicate29=predicate();

                    state._fsp--;

                    stream_predicate.add(predicate29.getTree());

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
                    // 99:18: -> ^( PREDICATE NOT predicate )
                    {
                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:99:21: ^( PREDICATE NOT predicate )
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:100:3: expr
                    {
                    pushFollow(FOLLOW_expr_in_literal488);
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
                    // 100:8: -> ^( EXPR expr )
                    {
                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:100:11: ^( EXPR expr )
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:102:1: expr : ( simpleExpr ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^) simpleExpr | varlist '=' ( cast )? function -> ^( MULTI_ASSIGN varlist function ( cast )? ) );
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
        RewriteRuleTokenStream stream_69=new RewriteRuleTokenStream(adaptor,"token 69");
        RewriteRuleSubtreeStream stream_varlist=new RewriteRuleSubtreeStream(adaptor,"rule varlist");
        RewriteRuleSubtreeStream stream_cast=new RewriteRuleSubtreeStream(adaptor,"rule cast");
        RewriteRuleSubtreeStream stream_function=new RewriteRuleSubtreeStream(adaptor,"rule function");
        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:102:5: ( simpleExpr ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^) simpleExpr | varlist '=' ( cast )? function -> ^( MULTI_ASSIGN varlist function ( cast )? ) )
            int alt9=2;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:102:7: simpleExpr ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^) simpleExpr
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    pushFollow(FOLLOW_simpleExpr_in_expr505);
                    simpleExpr31=simpleExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, simpleExpr31.getTree());

                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:102:19: ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^)
                    int alt7=7;
                    switch ( input.LA(1) ) {
                    case 67:
                        {
                        alt7=1;
                        }
                        break;
                    case 71:
                        {
                        alt7=2;
                        }
                        break;
                    case 69:
                        {
                        alt7=3;
                        }
                        break;
                    case 54:
                        {
                        alt7=4;
                        }
                        break;
                    case 70:
                        {
                        alt7=5;
                        }
                        break;
                    case 72:
                        {
                        alt7=6;
                        }
                        break;
                    case 68:
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
                            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:102:20: '<' ^
                            {
                            char_literal32=(Token)match(input,67,FOLLOW_67_in_expr509); 
                            char_literal32_tree = 
                            (CommonTree)adaptor.create(char_literal32)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(char_literal32_tree, root_0);


                            }
                            break;
                        case 2 :
                            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:102:27: '>' ^
                            {
                            char_literal33=(Token)match(input,71,FOLLOW_71_in_expr514); 
                            char_literal33_tree = 
                            (CommonTree)adaptor.create(char_literal33)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(char_literal33_tree, root_0);


                            }
                            break;
                        case 3 :
                            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:102:34: '=' ^
                            {
                            char_literal34=(Token)match(input,69,FOLLOW_69_in_expr519); 
                            char_literal34_tree = 
                            (CommonTree)adaptor.create(char_literal34)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(char_literal34_tree, root_0);


                            }
                            break;
                        case 4 :
                            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:102:40: '!=' ^
                            {
                            string_literal35=(Token)match(input,54,FOLLOW_54_in_expr523); 
                            string_literal35_tree = 
                            (CommonTree)adaptor.create(string_literal35)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(string_literal35_tree, root_0);


                            }
                            break;
                        case 5 :
                            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:102:48: '==' ^
                            {
                            string_literal36=(Token)match(input,70,FOLLOW_70_in_expr528); 
                            string_literal36_tree = 
                            (CommonTree)adaptor.create(string_literal36)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(string_literal36_tree, root_0);


                            }
                            break;
                        case 6 :
                            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:102:56: '>=' ^
                            {
                            string_literal37=(Token)match(input,72,FOLLOW_72_in_expr533); 
                            string_literal37_tree = 
                            (CommonTree)adaptor.create(string_literal37)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(string_literal37_tree, root_0);


                            }
                            break;
                        case 7 :
                            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:102:64: '<=' ^
                            {
                            string_literal38=(Token)match(input,68,FOLLOW_68_in_expr538); 
                            string_literal38_tree = 
                            (CommonTree)adaptor.create(string_literal38)
                            ;
                            root_0 = (CommonTree)adaptor.becomeRoot(string_literal38_tree, root_0);


                            }
                            break;

                    }


                    pushFollow(FOLLOW_simpleExpr_in_expr543);
                    simpleExpr39=simpleExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, simpleExpr39.getTree());

                    }
                    break;
                case 2 :
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:103:4: varlist '=' ( cast )? function
                    {
                    pushFollow(FOLLOW_varlist_in_expr548);
                    varlist40=varlist();

                    state._fsp--;

                    stream_varlist.add(varlist40.getTree());

                    char_literal41=(Token)match(input,69,FOLLOW_69_in_expr550);  
                    stream_69.add(char_literal41);


                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:103:16: ( cast )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==56) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:103:16: cast
                            {
                            pushFollow(FOLLOW_cast_in_expr552);
                            cast42=cast();

                            state._fsp--;

                            stream_cast.add(cast42.getTree());

                            }
                            break;

                    }


                    pushFollow(FOLLOW_function_in_expr555);
                    function43=function();

                    state._fsp--;

                    stream_function.add(function43.getTree());

                    // AST REWRITE
                    // elements: function, cast, varlist
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 103:31: -> ^( MULTI_ASSIGN varlist function ( cast )? )
                    {
                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:103:34: ^( MULTI_ASSIGN varlist function ( cast )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(MULTI_ASSIGN, "MULTI_ASSIGN")
                        , root_1);

                        adaptor.addChild(root_1, stream_varlist.nextTree());

                        adaptor.addChild(root_1, stream_function.nextTree());

                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:103:66: ( cast )?
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:105:1: simpleExpr : multExpr ( ( '+' ^| '-' ^) multExpr )* ;
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
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:105:11: ( multExpr ( ( '+' ^| '-' ^) multExpr )* )
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:105:13: multExpr ( ( '+' ^| '-' ^) multExpr )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_multExpr_in_simpleExpr577);
            multExpr44=multExpr();

            state._fsp--;

            adaptor.addChild(root_0, multExpr44.getTree());

            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:105:23: ( ( '+' ^| '-' ^) multExpr )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==59||LA11_0==61) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:105:24: ( '+' ^| '-' ^) multExpr
            	    {
            	    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:105:24: ( '+' ^| '-' ^)
            	    int alt10=2;
            	    int LA10_0 = input.LA(1);

            	    if ( (LA10_0==59) ) {
            	        alt10=1;
            	    }
            	    else if ( (LA10_0==61) ) {
            	        alt10=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 10, 0, input);

            	        throw nvae;

            	    }
            	    switch (alt10) {
            	        case 1 :
            	            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:105:25: '+' ^
            	            {
            	            char_literal45=(Token)match(input,59,FOLLOW_59_in_simpleExpr582); 
            	            char_literal45_tree = 
            	            (CommonTree)adaptor.create(char_literal45)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal45_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:105:30: '-' ^
            	            {
            	            char_literal46=(Token)match(input,61,FOLLOW_61_in_simpleExpr585); 
            	            char_literal46_tree = 
            	            (CommonTree)adaptor.create(char_literal46)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal46_tree, root_0);


            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_multExpr_in_simpleExpr589);
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:107:1: multExpr : exprValue ( ( '*' ^| '/' ^| 'mod' ^) exprValue )* ;
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
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:107:9: ( exprValue ( ( '*' ^| '/' ^| 'mod' ^) exprValue )* )
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:107:11: exprValue ( ( '*' ^| '/' ^| 'mod' ^) exprValue )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_exprValue_in_multExpr600);
            exprValue48=exprValue();

            state._fsp--;

            adaptor.addChild(root_0, exprValue48.getTree());

            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:107:21: ( ( '*' ^| '/' ^| 'mod' ^) exprValue )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==58||LA13_0==63||LA13_0==87) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:107:22: ( '*' ^| '/' ^| 'mod' ^) exprValue
            	    {
            	    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:107:22: ( '*' ^| '/' ^| 'mod' ^)
            	    int alt12=3;
            	    switch ( input.LA(1) ) {
            	    case 58:
            	        {
            	        alt12=1;
            	        }
            	        break;
            	    case 63:
            	        {
            	        alt12=2;
            	        }
            	        break;
            	    case 87:
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
            	            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:107:23: '*' ^
            	            {
            	            char_literal49=(Token)match(input,58,FOLLOW_58_in_multExpr604); 
            	            char_literal49_tree = 
            	            (CommonTree)adaptor.create(char_literal49)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal49_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:107:28: '/' ^
            	            {
            	            char_literal50=(Token)match(input,63,FOLLOW_63_in_multExpr607); 
            	            char_literal50_tree = 
            	            (CommonTree)adaptor.create(char_literal50)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal50_tree, root_0);


            	            }
            	            break;
            	        case 3 :
            	            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:107:33: 'mod' ^
            	            {
            	            string_literal51=(Token)match(input,87,FOLLOW_87_in_multExpr610); 
            	            string_literal51_tree = 
            	            (CommonTree)adaptor.create(string_literal51)
            	            ;
            	            root_0 = (CommonTree)adaptor.becomeRoot(string_literal51_tree, root_0);


            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_exprValue_in_multExpr614);
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:108:1: exprValue : ( (neg= '-' )? ( cast )? term -> ^( TERM term ( $neg)? ( cast )? ) | ( cast )? function -> ^( FUNCTION function ( cast )? ) | ( cast )? compExpr -> ^( COMPOUND_EXPR compExpr ( cast )? ) );
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
        RewriteRuleTokenStream stream_61=new RewriteRuleTokenStream(adaptor,"token 61");
        RewriteRuleSubtreeStream stream_cast=new RewriteRuleSubtreeStream(adaptor,"rule cast");
        RewriteRuleSubtreeStream stream_term=new RewriteRuleSubtreeStream(adaptor,"rule term");
        RewriteRuleSubtreeStream stream_compExpr=new RewriteRuleSubtreeStream(adaptor,"rule compExpr");
        RewriteRuleSubtreeStream stream_function=new RewriteRuleSubtreeStream(adaptor,"rule function");
        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:108:10: ( (neg= '-' )? ( cast )? term -> ^( TERM term ( $neg)? ( cast )? ) | ( cast )? function -> ^( FUNCTION function ( cast )? ) | ( cast )? compExpr -> ^( COMPOUND_EXPR compExpr ( cast )? ) )
            int alt18=3;
            switch ( input.LA(1) ) {
            case FLOAT:
            case ID:
            case INT:
            case STRING:
            case UTF8:
            case 61:
                {
                alt18=1;
                }
                break;
            case 56:
                {
                switch ( input.LA(2) ) {
                case 85:
                    {
                    int LA18_4 = input.LA(3);

                    if ( (LA18_4==76) ) {
                        int LA18_12 = input.LA(4);

                        if ( (LA18_12==77) ) {
                            int LA18_21 = input.LA(5);

                            if ( (LA18_21==57) ) {
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
                                case 55:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 56:
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
                    else if ( (LA18_4==57) ) {
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
                        case 55:
                            {
                            alt18=2;
                            }
                            break;
                        case 56:
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
                case 86:
                    {
                    int LA18_5 = input.LA(3);

                    if ( (LA18_5==76) ) {
                        int LA18_14 = input.LA(4);

                        if ( (LA18_14==77) ) {
                            int LA18_22 = input.LA(5);

                            if ( (LA18_22==57) ) {
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
                                case 55:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 56:
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
                    else if ( (LA18_5==57) ) {
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
                        case 55:
                            {
                            alt18=2;
                            }
                            break;
                        case 56:
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
                case 82:
                    {
                    int LA18_6 = input.LA(3);

                    if ( (LA18_6==76) ) {
                        int LA18_15 = input.LA(4);

                        if ( (LA18_15==77) ) {
                            int LA18_23 = input.LA(5);

                            if ( (LA18_23==57) ) {
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
                                case 55:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 56:
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
                    else if ( (LA18_6==57) ) {
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
                        case 55:
                            {
                            alt18=2;
                            }
                            break;
                        case 56:
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
                case 80:
                    {
                    int LA18_7 = input.LA(3);

                    if ( (LA18_7==76) ) {
                        int LA18_16 = input.LA(4);

                        if ( (LA18_16==77) ) {
                            int LA18_24 = input.LA(5);

                            if ( (LA18_24==57) ) {
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
                                case 55:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 56:
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
                    else if ( (LA18_7==57) ) {
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
                        case 55:
                            {
                            alt18=2;
                            }
                            break;
                        case 56:
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
                case 75:
                    {
                    int LA18_8 = input.LA(3);

                    if ( (LA18_8==76) ) {
                        int LA18_17 = input.LA(4);

                        if ( (LA18_17==77) ) {
                            int LA18_25 = input.LA(5);

                            if ( (LA18_25==57) ) {
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
                                case 55:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 56:
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
                    else if ( (LA18_8==57) ) {
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
                        case 55:
                            {
                            alt18=2;
                            }
                            break;
                        case 56:
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
                case 74:
                    {
                    int LA18_9 = input.LA(3);

                    if ( (LA18_9==76) ) {
                        int LA18_18 = input.LA(4);

                        if ( (LA18_18==77) ) {
                            int LA18_26 = input.LA(5);

                            if ( (LA18_26==57) ) {
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
                                case 55:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 56:
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
                    else if ( (LA18_9==57) ) {
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
                        case 55:
                            {
                            alt18=2;
                            }
                            break;
                        case 56:
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
                    case 76:
                        {
                        int LA18_19 = input.LA(4);

                        if ( (LA18_19==77) ) {
                            int LA18_27 = input.LA(5);

                            if ( (LA18_27==57) ) {
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
                                case 55:
                                    {
                                    alt18=2;
                                    }
                                    break;
                                case 56:
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
                    case 57:
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
                        case 55:
                            {
                            alt18=2;
                            }
                            break;
                        case DOT_END:
                        case 54:
                        case 56:
                        case 57:
                        case 58:
                        case 59:
                        case 60:
                        case 61:
                        case 63:
                        case 66:
                        case 67:
                        case 68:
                        case 69:
                        case 70:
                        case 71:
                        case 72:
                        case 87:
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
                    case 58:
                    case 59:
                    case 61:
                    case 63:
                    case 87:
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
                case 55:
                case 56:
                case 61:
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
            case 55:
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:108:12: (neg= '-' )? ( cast )? term
                    {
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:108:12: (neg= '-' )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==61) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:108:13: neg= '-'
                            {
                            neg=(Token)match(input,61,FOLLOW_61_in_exprValue625);  
                            stream_61.add(neg);


                            }
                            break;

                    }


                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:108:23: ( cast )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==56) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:108:23: cast
                            {
                            pushFollow(FOLLOW_cast_in_exprValue629);
                            cast53=cast();

                            state._fsp--;

                            stream_cast.add(cast53.getTree());

                            }
                            break;

                    }


                    pushFollow(FOLLOW_term_in_exprValue632);
                    term54=term();

                    state._fsp--;

                    stream_term.add(term54.getTree());

                    // AST REWRITE
                    // elements: neg, cast, term
                    // token labels: neg
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_neg=new RewriteRuleTokenStream(adaptor,"token neg",neg);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 108:34: -> ^( TERM term ( $neg)? ( cast )? )
                    {
                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:108:37: ^( TERM term ( $neg)? ( cast )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(TERM, "TERM")
                        , root_1);

                        adaptor.addChild(root_1, stream_term.nextTree());

                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:108:50: ( $neg)?
                        if ( stream_neg.hasNext() ) {
                            adaptor.addChild(root_1, stream_neg.nextNode());

                        }
                        stream_neg.reset();

                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:108:55: ( cast )?
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:109:4: ( cast )? function
                    {
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:109:4: ( cast )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==56) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:109:4: cast
                            {
                            pushFollow(FOLLOW_cast_in_exprValue652);
                            cast55=cast();

                            state._fsp--;

                            stream_cast.add(cast55.getTree());

                            }
                            break;

                    }


                    pushFollow(FOLLOW_function_in_exprValue655);
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
                    // 109:19: -> ^( FUNCTION function ( cast )? )
                    {
                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:109:22: ^( FUNCTION function ( cast )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(FUNCTION, "FUNCTION")
                        , root_1);

                        adaptor.addChild(root_1, stream_function.nextTree());

                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:109:42: ( cast )?
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:110:4: ( cast )? compExpr
                    {
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:110:4: ( cast )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==56) ) {
                        int LA17_1 = input.LA(2);

                        if ( ((LA17_1 >= 74 && LA17_1 <= 75)||LA17_1==80||LA17_1==82||(LA17_1 >= 85 && LA17_1 <= 86)) ) {
                            alt17=1;
                        }
                        else if ( (LA17_1==ID) ) {
                            int LA17_3 = input.LA(3);

                            if ( (LA17_3==76) ) {
                                alt17=1;
                            }
                            else if ( (LA17_3==57) ) {
                                int LA17_5 = input.LA(4);

                                if ( (LA17_5==56) ) {
                                    alt17=1;
                                }
                            }
                        }
                    }
                    switch (alt17) {
                        case 1 :
                            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:110:4: cast
                            {
                            pushFollow(FOLLOW_cast_in_exprValue671);
                            cast57=cast();

                            state._fsp--;

                            stream_cast.add(cast57.getTree());

                            }
                            break;

                    }


                    pushFollow(FOLLOW_compExpr_in_exprValue674);
                    compExpr58=compExpr();

                    state._fsp--;

                    stream_compExpr.add(compExpr58.getTree());

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
                    // 110:19: -> ^( COMPOUND_EXPR compExpr ( cast )? )
                    {
                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:110:22: ^( COMPOUND_EXPR compExpr ( cast )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(COMPOUND_EXPR, "COMPOUND_EXPR")
                        , root_1);

                        adaptor.addChild(root_1, stream_compExpr.nextTree());

                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:110:47: ( cast )?
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:112:1: compExpr : '(' ! simpleExpr ')' !;
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
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:112:9: ( '(' ! simpleExpr ')' !)
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:112:11: '(' ! simpleExpr ')' !
            {
            root_0 = (CommonTree)adaptor.nil();


            char_literal59=(Token)match(input,56,FOLLOW_56_in_compExpr693); 

            pushFollow(FOLLOW_simpleExpr_in_compExpr696);
            simpleExpr60=simpleExpr();

            state._fsp--;

            adaptor.addChild(root_0, simpleExpr60.getTree());

            char_literal61=(Token)match(input,57,FOLLOW_57_in_compExpr698); 

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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:113:1: cast : '(' type ')' -> type ;
    public final SociaLiteParser.cast_return cast() throws RecognitionException {
        SociaLiteParser.cast_return retval = new SociaLiteParser.cast_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal62=null;
        Token char_literal64=null;
        SociaLiteParser.type_return type63 =null;


        CommonTree char_literal62_tree=null;
        CommonTree char_literal64_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:113:5: ( '(' type ')' -> type )
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:113:7: '(' type ')'
            {
            char_literal62=(Token)match(input,56,FOLLOW_56_in_cast707);  
            stream_56.add(char_literal62);


            pushFollow(FOLLOW_type_in_cast709);
            type63=type();

            state._fsp--;

            stream_type.add(type63.getTree());

            char_literal64=(Token)match(input,57,FOLLOW_57_in_cast711);  
            stream_57.add(char_literal64);


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
            // 113:20: -> type
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:114:1: varlist : '(' !e1= dotname ( ',' !e2= dotname )+ ')' !;
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
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:114:8: ( '(' !e1= dotname ( ',' !e2= dotname )+ ')' !)
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:114:10: '(' !e1= dotname ( ',' !e2= dotname )+ ')' !
            {
            root_0 = (CommonTree)adaptor.nil();


            char_literal65=(Token)match(input,56,FOLLOW_56_in_varlist723); 

            pushFollow(FOLLOW_dotname_in_varlist728);
            e1=dotname();

            state._fsp--;

            adaptor.addChild(root_0, e1.getTree());

            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:114:27: ( ',' !e2= dotname )+
            int cnt19=0;
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==60) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:114:28: ',' !e2= dotname
            	    {
            	    char_literal66=(Token)match(input,60,FOLLOW_60_in_varlist732); 

            	    pushFollow(FOLLOW_dotname_in_varlist737);
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


            char_literal67=(Token)match(input,57,FOLLOW_57_in_varlist741); 

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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:115:1: function : '$' dotname '(' ( fparamlist )? ')' -> ^( FUNC dotname ( fparamlist )? ) ;
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
        RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleSubtreeStream stream_fparamlist=new RewriteRuleSubtreeStream(adaptor,"rule fparamlist");
        RewriteRuleSubtreeStream stream_dotname=new RewriteRuleSubtreeStream(adaptor,"rule dotname");
        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:115:9: ( '$' dotname '(' ( fparamlist )? ')' -> ^( FUNC dotname ( fparamlist )? ) )
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:115:11: '$' dotname '(' ( fparamlist )? ')'
            {
            char_literal68=(Token)match(input,55,FOLLOW_55_in_function749);  
            stream_55.add(char_literal68);


            pushFollow(FOLLOW_dotname_in_function751);
            dotname69=dotname();

            state._fsp--;

            stream_dotname.add(dotname69.getTree());

            char_literal70=(Token)match(input,56,FOLLOW_56_in_function753);  
            stream_56.add(char_literal70);


            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:115:27: ( fparamlist )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==FLOAT||LA20_0==ID||LA20_0==INT||LA20_0==STRING||LA20_0==UTF8||(LA20_0 >= 55 && LA20_0 <= 56)||LA20_0==61) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:115:27: fparamlist
                    {
                    pushFollow(FOLLOW_fparamlist_in_function755);
                    fparamlist71=fparamlist();

                    state._fsp--;

                    stream_fparamlist.add(fparamlist71.getTree());

                    }
                    break;

            }


            char_literal72=(Token)match(input,57,FOLLOW_57_in_function758);  
            stream_57.add(char_literal72);


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
            // 115:43: -> ^( FUNC dotname ( fparamlist )? )
            {
                // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:115:46: ^( FUNC dotname ( fparamlist )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(FUNC, "FUNC")
                , root_1);

                adaptor.addChild(root_1, stream_dotname.nextTree());

                // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:115:61: ( fparamlist )?
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:118:1: predicate : ID '(' paramlist ')' -> ID paramlist ;
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
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_paramlist=new RewriteRuleSubtreeStream(adaptor,"rule paramlist");
        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:118:11: ( ID '(' paramlist ')' -> ID paramlist )
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:118:12: ID '(' paramlist ')'
            {
            ID73=(Token)match(input,ID,FOLLOW_ID_in_predicate778);  
            stream_ID.add(ID73);


            char_literal74=(Token)match(input,56,FOLLOW_56_in_predicate780);  
            stream_56.add(char_literal74);


            pushFollow(FOLLOW_paramlist_in_predicate782);
            paramlist75=paramlist();

            state._fsp--;

            stream_paramlist.add(paramlist75.getTree());

            char_literal76=(Token)match(input,57,FOLLOW_57_in_predicate784);  
            stream_57.add(char_literal76);


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
            // 118:33: -> ID paramlist
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:120:1: paramlist : param ( ',' ! param )* ;
    public final SociaLiteParser.paramlist_return paramlist() throws RecognitionException {
        SociaLiteParser.paramlist_return retval = new SociaLiteParser.paramlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal78=null;
        SociaLiteParser.param_return param77 =null;

        SociaLiteParser.param_return param79 =null;


        CommonTree char_literal78_tree=null;

        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:120:11: ( param ( ',' ! param )* )
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:120:12: param ( ',' ! param )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_param_in_paramlist797);
            param77=param();

            state._fsp--;

            adaptor.addChild(root_0, param77.getTree());

            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:120:18: ( ',' ! param )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==60) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:120:19: ',' ! param
            	    {
            	    char_literal78=(Token)match(input,60,FOLLOW_60_in_paramlist800); 

            	    pushFollow(FOLLOW_param_in_paramlist803);
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:121:1: fparamlist : fparam ( ',' ! fparam )* ;
    public final SociaLiteParser.fparamlist_return fparamlist() throws RecognitionException {
        SociaLiteParser.fparamlist_return retval = new SociaLiteParser.fparamlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal81=null;
        SociaLiteParser.fparam_return fparam80 =null;

        SociaLiteParser.fparam_return fparam82 =null;


        CommonTree char_literal81_tree=null;

        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:121:12: ( fparam ( ',' ! fparam )* )
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:121:13: fparam ( ',' ! fparam )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_fparam_in_fparamlist811);
            fparam80=fparam();

            state._fsp--;

            adaptor.addChild(root_0, fparam80.getTree());

            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:121:20: ( ',' ! fparam )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==60) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:121:21: ',' ! fparam
            	    {
            	    char_literal81=(Token)match(input,60,FOLLOW_60_in_fparamlist814); 

            	    pushFollow(FOLLOW_fparam_in_fparamlist817);
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:123:1: param : simpleExpr ;
    public final SociaLiteParser.param_return param() throws RecognitionException {
        SociaLiteParser.param_return retval = new SociaLiteParser.param_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SociaLiteParser.simpleExpr_return simpleExpr83 =null;



        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:123:7: ( simpleExpr )
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:123:8: simpleExpr
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_simpleExpr_in_param826);
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:124:1: fparam : simpleExpr ;
    public final SociaLiteParser.fparam_return fparam() throws RecognitionException {
        SociaLiteParser.fparam_return retval = new SociaLiteParser.fparam_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SociaLiteParser.simpleExpr_return simpleExpr84 =null;



        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:124:8: ( simpleExpr )
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:124:9: simpleExpr
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_simpleExpr_in_fparam832);
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:126:1: term : ( INT -> ^( T_INT INT ) | FLOAT -> ^( T_FLOAT FLOAT ) | STRING -> ^( T_STR STRING ) | UTF8 -> ^( T_UTF8 UTF8 ) | dotname -> ^( T_VAR dotname ) );
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
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:126:5: ( INT -> ^( T_INT INT ) | FLOAT -> ^( T_FLOAT FLOAT ) | STRING -> ^( T_STR STRING ) | UTF8 -> ^( T_UTF8 UTF8 ) | dotname -> ^( T_VAR dotname ) )
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:126:7: INT
                    {
                    INT85=(Token)match(input,INT,FOLLOW_INT_in_term839);  
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
                    // 126:11: -> ^( T_INT INT )
                    {
                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:126:14: ^( T_INT INT )
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:127:3: FLOAT
                    {
                    FLOAT86=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_term852);  
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
                    // 127:9: -> ^( T_FLOAT FLOAT )
                    {
                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:127:12: ^( T_FLOAT FLOAT )
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:128:3: STRING
                    {
                    STRING87=(Token)match(input,STRING,FOLLOW_STRING_in_term864);  
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
                    // 128:10: -> ^( T_STR STRING )
                    {
                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:128:13: ^( T_STR STRING )
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:129:3: UTF8
                    {
                    UTF888=(Token)match(input,UTF8,FOLLOW_UTF8_in_term876);  
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
                    // 129:8: -> ^( T_UTF8 UTF8 )
                    {
                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:129:11: ^( T_UTF8 UTF8 )
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:130:3: dotname
                    {
                    pushFollow(FOLLOW_dotname_in_term889);
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
                    // 130:11: -> ^( T_VAR dotname )
                    {
                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:130:14: ^( T_VAR dotname )
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:132:1: table_decl : ID '(' decls ')' ( table_opts )? DOT_END -> ^( DECL ID decls ^( OPTION ( table_opts )? ) ) ;
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
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_DOT_END=new RewriteRuleTokenStream(adaptor,"token DOT_END");
        RewriteRuleSubtreeStream stream_table_opts=new RewriteRuleSubtreeStream(adaptor,"rule table_opts");
        RewriteRuleSubtreeStream stream_decls=new RewriteRuleSubtreeStream(adaptor,"rule decls");
        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:132:11: ( ID '(' decls ')' ( table_opts )? DOT_END -> ^( DECL ID decls ^( OPTION ( table_opts )? ) ) )
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:132:13: ID '(' decls ')' ( table_opts )? DOT_END
            {
            ID90=(Token)match(input,ID,FOLLOW_ID_in_table_decl905);  
            stream_ID.add(ID90);


            char_literal91=(Token)match(input,56,FOLLOW_56_in_table_decl907);  
            stream_56.add(char_literal91);


            pushFollow(FOLLOW_decls_in_table_decl909);
            decls92=decls();

            state._fsp--;

            stream_decls.add(decls92.getTree());

            char_literal93=(Token)match(input,57,FOLLOW_57_in_table_decl911);  
            stream_57.add(char_literal93);


            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:132:30: ( table_opts )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==79||(LA24_0 >= 83 && LA24_0 <= 84)||(LA24_0 >= 88 && LA24_0 <= 91)) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:132:30: table_opts
                    {
                    pushFollow(FOLLOW_table_opts_in_table_decl913);
                    table_opts94=table_opts();

                    state._fsp--;

                    stream_table_opts.add(table_opts94.getTree());

                    }
                    break;

            }


            DOT_END95=(Token)match(input,DOT_END,FOLLOW_DOT_END_in_table_decl916);  
            stream_DOT_END.add(DOT_END95);


            // AST REWRITE
            // elements: table_opts, ID, decls
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 132:51: -> ^( DECL ID decls ^( OPTION ( table_opts )? ) )
            {
                // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:132:54: ^( DECL ID decls ^( OPTION ( table_opts )? ) )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(DECL, "DECL")
                , root_1);

                adaptor.addChild(root_1, 
                stream_ID.nextNode()
                );

                adaptor.addChild(root_1, stream_decls.nextTree());

                // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:132:70: ^( OPTION ( table_opts )? )
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(OPTION, "OPTION")
                , root_2);

                // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:132:79: ( table_opts )?
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:134:1: table_opts : t_opt ( ',' ! t_opt )* ;
    public final SociaLiteParser.table_opts_return table_opts() throws RecognitionException {
        SociaLiteParser.table_opts_return retval = new SociaLiteParser.table_opts_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal97=null;
        SociaLiteParser.t_opt_return t_opt96 =null;

        SociaLiteParser.t_opt_return t_opt98 =null;


        CommonTree char_literal97_tree=null;

        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:134:11: ( t_opt ( ',' ! t_opt )* )
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:134:13: t_opt ( ',' ! t_opt )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_t_opt_in_table_opts949);
            t_opt96=t_opt();

            state._fsp--;

            adaptor.addChild(root_0, t_opt96.getTree());

            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:134:19: ( ',' ! t_opt )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==60) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:134:20: ',' ! t_opt
            	    {
            	    char_literal97=(Token)match(input,60,FOLLOW_60_in_table_opts952); 

            	    pushFollow(FOLLOW_t_opt_in_table_opts955);
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:136:1: t_opt : ( 'sortby' col= ID (order= SORT_ORDER )? -> ^( SORT_BY $col ( $order)? ) | 'orderby' ID -> ^( ORDER_BY ID ) | 'indexby' ID -> ^( INDEX_BY ID ) | 'groupby' '(' INT ')' -> ^( GROUP_BY INT ) | 'predefined' -> PREDEFINED | 'concurrent' -> CONCURRENT | 'multiset' -> MULTISET );
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
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
        RewriteRuleTokenStream stream_91=new RewriteRuleTokenStream(adaptor,"token 91");
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_SORT_ORDER=new RewriteRuleTokenStream(adaptor,"token SORT_ORDER");
        RewriteRuleTokenStream stream_88=new RewriteRuleTokenStream(adaptor,"token 88");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");

        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:136:7: ( 'sortby' col= ID (order= SORT_ORDER )? -> ^( SORT_BY $col ( $order)? ) | 'orderby' ID -> ^( ORDER_BY ID ) | 'indexby' ID -> ^( INDEX_BY ID ) | 'groupby' '(' INT ')' -> ^( GROUP_BY INT ) | 'predefined' -> PREDEFINED | 'concurrent' -> CONCURRENT | 'multiset' -> MULTISET )
            int alt27=7;
            switch ( input.LA(1) ) {
            case 91:
                {
                alt27=1;
                }
                break;
            case 89:
                {
                alt27=2;
                }
                break;
            case 84:
                {
                alt27=3;
                }
                break;
            case 83:
                {
                alt27=4;
                }
                break;
            case 90:
                {
                alt27=5;
                }
                break;
            case 79:
                {
                alt27=6;
                }
                break;
            case 88:
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:136:9: 'sortby' col= ID (order= SORT_ORDER )?
                    {
                    string_literal99=(Token)match(input,91,FOLLOW_91_in_t_opt966);  
                    stream_91.add(string_literal99);


                    col=(Token)match(input,ID,FOLLOW_ID_in_t_opt970);  
                    stream_ID.add(col);


                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:136:25: (order= SORT_ORDER )?
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==SORT_ORDER) ) {
                        alt26=1;
                    }
                    switch (alt26) {
                        case 1 :
                            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:136:26: order= SORT_ORDER
                            {
                            order=(Token)match(input,SORT_ORDER,FOLLOW_SORT_ORDER_in_t_opt975);  
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
                    // 136:45: -> ^( SORT_BY $col ( $order)? )
                    {
                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:136:48: ^( SORT_BY $col ( $order)? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(
                        (CommonTree)adaptor.create(SORT_BY, "SORT_BY")
                        , root_1);

                        adaptor.addChild(root_1, stream_col.nextNode());

                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:136:64: ( $order)?
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:137:3: 'orderby' ID
                    {
                    string_literal100=(Token)match(input,89,FOLLOW_89_in_t_opt994);  
                    stream_89.add(string_literal100);


                    ID101=(Token)match(input,ID,FOLLOW_ID_in_t_opt996);  
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
                    // 137:16: -> ^( ORDER_BY ID )
                    {
                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:137:19: ^( ORDER_BY ID )
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:138:3: 'indexby' ID
                    {
                    string_literal102=(Token)match(input,84,FOLLOW_84_in_t_opt1008);  
                    stream_84.add(string_literal102);


                    ID103=(Token)match(input,ID,FOLLOW_ID_in_t_opt1010);  
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
                    // 138:16: -> ^( INDEX_BY ID )
                    {
                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:138:19: ^( INDEX_BY ID )
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:139:3: 'groupby' '(' INT ')'
                    {
                    string_literal104=(Token)match(input,83,FOLLOW_83_in_t_opt1022);  
                    stream_83.add(string_literal104);


                    char_literal105=(Token)match(input,56,FOLLOW_56_in_t_opt1024);  
                    stream_56.add(char_literal105);


                    INT106=(Token)match(input,INT,FOLLOW_INT_in_t_opt1026);  
                    stream_INT.add(INT106);


                    char_literal107=(Token)match(input,57,FOLLOW_57_in_t_opt1028);  
                    stream_57.add(char_literal107);


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
                    // 139:25: -> ^( GROUP_BY INT )
                    {
                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:139:28: ^( GROUP_BY INT )
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:140:3: 'predefined'
                    {
                    string_literal108=(Token)match(input,90,FOLLOW_90_in_t_opt1040);  
                    stream_90.add(string_literal108);


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
                    // 140:16: -> PREDEFINED
                    {
                        adaptor.addChild(root_0, 
                        (CommonTree)adaptor.create(PREDEFINED, "PREDEFINED")
                        );

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 6 :
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:141:3: 'concurrent'
                    {
                    string_literal109=(Token)match(input,79,FOLLOW_79_in_t_opt1048);  
                    stream_79.add(string_literal109);


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
                    // 141:16: -> CONCURRENT
                    {
                        adaptor.addChild(root_0, 
                        (CommonTree)adaptor.create(CONCURRENT, "CONCURRENT")
                        );

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 7 :
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:142:3: 'multiset'
                    {
                    string_literal110=(Token)match(input,88,FOLLOW_88_in_t_opt1056);  
                    stream_88.add(string_literal110);


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
                    // 142:14: -> MULTISET
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:146:1: decls : col_decls ( ',' '(' decls ')' )? -> ^( COL_DECLS col_decls ^( DECL ( decls )? ) ) ;
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
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
        RewriteRuleTokenStream stream_60=new RewriteRuleTokenStream(adaptor,"token 60");
        RewriteRuleSubtreeStream stream_col_decls=new RewriteRuleSubtreeStream(adaptor,"rule col_decls");
        RewriteRuleSubtreeStream stream_decls=new RewriteRuleSubtreeStream(adaptor,"rule decls");
        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:146:7: ( col_decls ( ',' '(' decls ')' )? -> ^( COL_DECLS col_decls ^( DECL ( decls )? ) ) )
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:146:9: col_decls ( ',' '(' decls ')' )?
            {
            pushFollow(FOLLOW_col_decls_in_decls1079);
            col_decls111=col_decls();

            state._fsp--;

            stream_col_decls.add(col_decls111.getTree());

            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:146:19: ( ',' '(' decls ')' )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==60) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:146:20: ',' '(' decls ')'
                    {
                    char_literal112=(Token)match(input,60,FOLLOW_60_in_decls1082);  
                    stream_60.add(char_literal112);


                    char_literal113=(Token)match(input,56,FOLLOW_56_in_decls1084);  
                    stream_56.add(char_literal113);


                    pushFollow(FOLLOW_decls_in_decls1086);
                    decls114=decls();

                    state._fsp--;

                    stream_decls.add(decls114.getTree());

                    char_literal115=(Token)match(input,57,FOLLOW_57_in_decls1088);  
                    stream_57.add(char_literal115);


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
            // 146:40: -> ^( COL_DECLS col_decls ^( DECL ( decls )? ) )
            {
                // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:146:43: ^( COL_DECLS col_decls ^( DECL ( decls )? ) )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(COL_DECLS, "COL_DECLS")
                , root_1);

                adaptor.addChild(root_1, stream_col_decls.nextTree());

                // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:146:65: ^( DECL ( decls )? )
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(DECL, "DECL")
                , root_2);

                // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:146:72: ( decls )?
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:148:1: col_decls : col_decl ( ',' ! col_decl )* ;
    public final SociaLiteParser.col_decls_return col_decls() throws RecognitionException {
        SociaLiteParser.col_decls_return retval = new SociaLiteParser.col_decls_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal117=null;
        SociaLiteParser.col_decl_return col_decl116 =null;

        SociaLiteParser.col_decl_return col_decl118 =null;


        CommonTree char_literal117_tree=null;

        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:148:10: ( col_decl ( ',' ! col_decl )* )
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:148:12: col_decl ( ',' ! col_decl )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_col_decl_in_col_decls1114);
            col_decl116=col_decl();

            state._fsp--;

            adaptor.addChild(root_0, col_decl116.getTree());

            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:148:21: ( ',' ! col_decl )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==60) ) {
                    int LA29_1 = input.LA(2);

                    if ( (LA29_1==ID||(LA29_1 >= 74 && LA29_1 <= 75)||LA29_1==80||LA29_1==82||(LA29_1 >= 85 && LA29_1 <= 86)) ) {
                        alt29=1;
                    }


                }


                switch (alt29) {
            	case 1 :
            	    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:148:22: ',' ! col_decl
            	    {
            	    char_literal117=(Token)match(input,60,FOLLOW_60_in_col_decls1117); 

            	    pushFollow(FOLLOW_col_decl_in_col_decls1120);
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:150:1: col_decl : type ID ( ':' col_opt )? -> ^( COL_DECL type ID ( col_opt )? ) ;
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
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleSubtreeStream stream_col_opt=new RewriteRuleSubtreeStream(adaptor,"rule col_opt");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:150:9: ( type ID ( ':' col_opt )? -> ^( COL_DECL type ID ( col_opt )? ) )
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:150:11: type ID ( ':' col_opt )?
            {
            pushFollow(FOLLOW_type_in_col_decl1130);
            type119=type();

            state._fsp--;

            stream_type.add(type119.getTree());

            ID120=(Token)match(input,ID,FOLLOW_ID_in_col_decl1132);  
            stream_ID.add(ID120);


            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:150:19: ( ':' col_opt )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==64) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:150:20: ':' col_opt
                    {
                    char_literal121=(Token)match(input,64,FOLLOW_64_in_col_decl1135);  
                    stream_64.add(char_literal121);


                    pushFollow(FOLLOW_col_opt_in_col_decl1137);
                    col_opt122=col_opt();

                    state._fsp--;

                    stream_col_opt.add(col_opt122.getTree());

                    }
                    break;

            }


            // AST REWRITE
            // elements: col_opt, type, ID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 150:33: -> ^( COL_DECL type ID ( col_opt )? )
            {
                // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:150:36: ^( COL_DECL type ID ( col_opt )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(COL_DECL, "COL_DECL")
                , root_1);

                adaptor.addChild(root_1, stream_type.nextTree());

                adaptor.addChild(root_1, 
                stream_ID.nextNode()
                );

                // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:150:55: ( col_opt )?
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:152:1: col_opt : (i1= INT '..' i2= INT -> ^( RANGE $i1 $i2) | ITER_DECL -> ITER );
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
        RewriteRuleTokenStream stream_62=new RewriteRuleTokenStream(adaptor,"token 62");

        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:152:9: (i1= INT '..' i2= INT -> ^( RANGE $i1 $i2) | ITER_DECL -> ITER )
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:152:12: i1= INT '..' i2= INT
                    {
                    i1=(Token)match(input,INT,FOLLOW_INT_in_col_opt1163);  
                    stream_INT.add(i1);


                    string_literal123=(Token)match(input,62,FOLLOW_62_in_col_opt1165);  
                    stream_62.add(string_literal123);


                    i2=(Token)match(input,INT,FOLLOW_INT_in_col_opt1169);  
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
                    // 152:31: -> ^( RANGE $i1 $i2)
                    {
                        // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:152:34: ^( RANGE $i1 $i2)
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:153:4: ITER_DECL
                    {
                    ITER_DECL124=(Token)match(input,ITER_DECL,FOLLOW_ITER_DECL_in_col_opt1186);  
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
                    // 153:14: -> ITER
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:156:1: type : ( 'int' ( '[' ']' )? | 'long' ( '[' ']' )? | 'float' ( '[' ']' )? | 'double' ( '[' ']' )? | 'String' ( '[' ']' )? | 'Object' ( '[' ']' )? | ID ( '[' ']' )? );
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
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:156:5: ( 'int' ( '[' ']' )? | 'long' ( '[' ']' )? | 'float' ( '[' ']' )? | 'double' ( '[' ']' )? | 'String' ( '[' ']' )? | 'Object' ( '[' ']' )? | ID ( '[' ']' )? )
            int alt39=7;
            switch ( input.LA(1) ) {
            case 85:
                {
                alt39=1;
                }
                break;
            case 86:
                {
                alt39=2;
                }
                break;
            case 82:
                {
                alt39=3;
                }
                break;
            case 80:
                {
                alt39=4;
                }
                break;
            case 75:
                {
                alt39=5;
                }
                break;
            case 74:
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:156:7: 'int' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal125=(Token)match(input,85,FOLLOW_85_in_type1199); 
                    string_literal125_tree = 
                    (CommonTree)adaptor.create(string_literal125)
                    ;
                    adaptor.addChild(root_0, string_literal125_tree);


                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:156:13: ( '[' ']' )?
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==76) ) {
                        alt32=1;
                    }
                    switch (alt32) {
                        case 1 :
                            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:156:14: '[' ']'
                            {
                            char_literal126=(Token)match(input,76,FOLLOW_76_in_type1202); 
                            char_literal126_tree = 
                            (CommonTree)adaptor.create(char_literal126)
                            ;
                            adaptor.addChild(root_0, char_literal126_tree);


                            char_literal127=(Token)match(input,77,FOLLOW_77_in_type1204); 
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:157:3: 'long' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal128=(Token)match(input,86,FOLLOW_86_in_type1210); 
                    string_literal128_tree = 
                    (CommonTree)adaptor.create(string_literal128)
                    ;
                    adaptor.addChild(root_0, string_literal128_tree);


                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:157:10: ( '[' ']' )?
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==76) ) {
                        alt33=1;
                    }
                    switch (alt33) {
                        case 1 :
                            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:157:11: '[' ']'
                            {
                            char_literal129=(Token)match(input,76,FOLLOW_76_in_type1213); 
                            char_literal129_tree = 
                            (CommonTree)adaptor.create(char_literal129)
                            ;
                            adaptor.addChild(root_0, char_literal129_tree);


                            char_literal130=(Token)match(input,77,FOLLOW_77_in_type1215); 
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:158:3: 'float' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal131=(Token)match(input,82,FOLLOW_82_in_type1223); 
                    string_literal131_tree = 
                    (CommonTree)adaptor.create(string_literal131)
                    ;
                    adaptor.addChild(root_0, string_literal131_tree);


                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:158:11: ( '[' ']' )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==76) ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:158:12: '[' ']'
                            {
                            char_literal132=(Token)match(input,76,FOLLOW_76_in_type1226); 
                            char_literal132_tree = 
                            (CommonTree)adaptor.create(char_literal132)
                            ;
                            adaptor.addChild(root_0, char_literal132_tree);


                            char_literal133=(Token)match(input,77,FOLLOW_77_in_type1228); 
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:159:3: 'double' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal134=(Token)match(input,80,FOLLOW_80_in_type1236); 
                    string_literal134_tree = 
                    (CommonTree)adaptor.create(string_literal134)
                    ;
                    adaptor.addChild(root_0, string_literal134_tree);


                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:159:12: ( '[' ']' )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==76) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:159:13: '[' ']'
                            {
                            char_literal135=(Token)match(input,76,FOLLOW_76_in_type1239); 
                            char_literal135_tree = 
                            (CommonTree)adaptor.create(char_literal135)
                            ;
                            adaptor.addChild(root_0, char_literal135_tree);


                            char_literal136=(Token)match(input,77,FOLLOW_77_in_type1241); 
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:160:3: 'String' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal137=(Token)match(input,75,FOLLOW_75_in_type1249); 
                    string_literal137_tree = 
                    (CommonTree)adaptor.create(string_literal137)
                    ;
                    adaptor.addChild(root_0, string_literal137_tree);


                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:160:12: ( '[' ']' )?
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==76) ) {
                        alt36=1;
                    }
                    switch (alt36) {
                        case 1 :
                            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:160:13: '[' ']'
                            {
                            char_literal138=(Token)match(input,76,FOLLOW_76_in_type1252); 
                            char_literal138_tree = 
                            (CommonTree)adaptor.create(char_literal138)
                            ;
                            adaptor.addChild(root_0, char_literal138_tree);


                            char_literal139=(Token)match(input,77,FOLLOW_77_in_type1254); 
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:161:3: 'Object' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    string_literal140=(Token)match(input,74,FOLLOW_74_in_type1262); 
                    string_literal140_tree = 
                    (CommonTree)adaptor.create(string_literal140)
                    ;
                    adaptor.addChild(root_0, string_literal140_tree);


                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:161:12: ( '[' ']' )?
                    int alt37=2;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0==76) ) {
                        alt37=1;
                    }
                    switch (alt37) {
                        case 1 :
                            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:161:13: '[' ']'
                            {
                            char_literal141=(Token)match(input,76,FOLLOW_76_in_type1265); 
                            char_literal141_tree = 
                            (CommonTree)adaptor.create(char_literal141)
                            ;
                            adaptor.addChild(root_0, char_literal141_tree);


                            char_literal142=(Token)match(input,77,FOLLOW_77_in_type1267); 
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
                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:162:4: ID ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    ID143=(Token)match(input,ID,FOLLOW_ID_in_type1274); 
                    ID143_tree = 
                    (CommonTree)adaptor.create(ID143)
                    ;
                    adaptor.addChild(root_0, ID143_tree);


                    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:162:7: ( '[' ']' )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==76) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:162:8: '[' ']'
                            {
                            char_literal144=(Token)match(input,76,FOLLOW_76_in_type1277); 
                            char_literal144_tree = 
                            (CommonTree)adaptor.create(char_literal144)
                            ;
                            adaptor.addChild(root_0, char_literal144_tree);


                            char_literal145=(Token)match(input,77,FOLLOW_77_in_type1279); 
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
    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:164:1: dotname : ID ( DOT_ID )* ;
    public final SociaLiteParser.dotname_return dotname() throws RecognitionException {
        SociaLiteParser.dotname_return retval = new SociaLiteParser.dotname_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token ID146=null;
        Token DOT_ID147=null;

        CommonTree ID146_tree=null;
        CommonTree DOT_ID147_tree=null;

        try {
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:164:9: ( ID ( DOT_ID )* )
            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:164:10: ID ( DOT_ID )*
            {
            root_0 = (CommonTree)adaptor.nil();


            ID146=(Token)match(input,ID,FOLLOW_ID_in_dotname1291); 
            ID146_tree = 
            (CommonTree)adaptor.create(ID146)
            ;
            adaptor.addChild(root_0, ID146_tree);


            // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:164:13: ( DOT_ID )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==DOT_ID) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // /Users/dyoung/opensource/socialite/grammar/SociaLite.g:164:14: DOT_ID
            	    {
            	    DOT_ID147=(Token)match(input,DOT_ID,FOLLOW_DOT_ID_in_dotname1294); 
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
        "\1\23\1\uffff\1\23\2\16\1\uffff";
    static final String DFA9_maxS =
        "\1\75\1\uffff\1\126\2\127\1\uffff";
    static final String DFA9_acceptS =
        "\1\uffff\1\1\3\uffff\1\2";
    static final String DFA9_specialS =
        "\6\uffff}>";
    static final String[] DFA9_transitionS = {
            "\1\1\4\uffff\1\1\2\uffff\1\1\20\uffff\1\1\7\uffff\1\1\2\uffff"+
            "\1\1\1\2\4\uffff\1\1",
            "",
            "\1\1\4\uffff\1\3\2\uffff\1\1\20\uffff\1\1\7\uffff\1\1\2\uffff"+
            "\2\1\4\uffff\1\1\14\uffff\2\1\4\uffff\1\1\1\uffff\1\1\2\uffff"+
            "\2\1",
            "\1\4\52\uffff\3\1\1\5\1\1\1\uffff\1\1\14\uffff\1\1\12\uffff"+
            "\1\1",
            "\1\4\52\uffff\3\1\1\5\1\1\1\uffff\1\1\27\uffff\1\1",
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
            return "102:1: expr : ( simpleExpr ( '<' ^| '>' ^| '=' ^| '!=' ^| '==' ^| '>=' ^| '<=' ^) simpleExpr | varlist '=' ( cast )? function -> ^( MULTI_ASSIGN varlist function ( cast )? ) );";
        }
    }
 

    public static final BitSet FOLLOW_stat_in_prog290 = new BitSet(new long[]{0x0000000001000000L,0x0000000000024200L});
    public static final BitSet FOLLOW_EOF_in_prog293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_decl_in_stat299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule_in_stat303 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_query_in_stat307 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_stmt_in_stat311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_table_stmt320 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_ID_in_table_stmt322 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_DOT_END_in_table_stmt324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_table_stmt337 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_ID_in_table_stmt339 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_DOT_END_in_table_stmt341 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_query359 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_predicate_in_query361 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_DOT_END_in_query363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_head_in_rule384 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_rule386 = new BitSet(new long[]{0x2190100109080000L});
    public static final BitSet FOLLOW_litlist_in_rule388 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_rule391 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_rule393 = new BitSet(new long[]{0x2190100109080000L});
    public static final BitSet FOLLOW_litlist_in_rule395 = new BitSet(new long[]{0x0000000000002000L,0x0000000000000004L});
    public static final BitSet FOLLOW_DOT_END_in_rule399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_predicate_in_head436 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_litlist443 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_60_in_litlist446 = new BitSet(new long[]{0x2190100109080000L});
    public static final BitSet FOLLOW_literal_in_litlist449 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_predicate_in_literal458 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_literal472 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_predicate_in_literal474 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_literal488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleExpr_in_expr505 = new BitSet(new long[]{0x0040000000000000L,0x00000000000001F8L});
    public static final BitSet FOLLOW_67_in_expr509 = new BitSet(new long[]{0x2190100009080000L});
    public static final BitSet FOLLOW_71_in_expr514 = new BitSet(new long[]{0x2190100009080000L});
    public static final BitSet FOLLOW_69_in_expr519 = new BitSet(new long[]{0x2190100009080000L});
    public static final BitSet FOLLOW_54_in_expr523 = new BitSet(new long[]{0x2190100009080000L});
    public static final BitSet FOLLOW_70_in_expr528 = new BitSet(new long[]{0x2190100009080000L});
    public static final BitSet FOLLOW_72_in_expr533 = new BitSet(new long[]{0x2190100009080000L});
    public static final BitSet FOLLOW_68_in_expr538 = new BitSet(new long[]{0x2190100009080000L});
    public static final BitSet FOLLOW_simpleExpr_in_expr543 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varlist_in_expr548 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_expr550 = new BitSet(new long[]{0x0180000000000000L});
    public static final BitSet FOLLOW_cast_in_expr552 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_function_in_expr555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multExpr_in_simpleExpr577 = new BitSet(new long[]{0x2800000000000002L});
    public static final BitSet FOLLOW_59_in_simpleExpr582 = new BitSet(new long[]{0x2190100009080000L});
    public static final BitSet FOLLOW_61_in_simpleExpr585 = new BitSet(new long[]{0x2190100009080000L});
    public static final BitSet FOLLOW_multExpr_in_simpleExpr589 = new BitSet(new long[]{0x2800000000000002L});
    public static final BitSet FOLLOW_exprValue_in_multExpr600 = new BitSet(new long[]{0x8400000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_58_in_multExpr604 = new BitSet(new long[]{0x2190100009080000L});
    public static final BitSet FOLLOW_63_in_multExpr607 = new BitSet(new long[]{0x2190100009080000L});
    public static final BitSet FOLLOW_87_in_multExpr610 = new BitSet(new long[]{0x2190100009080000L});
    public static final BitSet FOLLOW_exprValue_in_multExpr614 = new BitSet(new long[]{0x8400000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_61_in_exprValue625 = new BitSet(new long[]{0x0110100009080000L});
    public static final BitSet FOLLOW_cast_in_exprValue629 = new BitSet(new long[]{0x0010100009080000L});
    public static final BitSet FOLLOW_term_in_exprValue632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cast_in_exprValue652 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_function_in_exprValue655 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cast_in_exprValue671 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_compExpr_in_exprValue674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_compExpr693 = new BitSet(new long[]{0x2190100009080000L});
    public static final BitSet FOLLOW_simpleExpr_in_compExpr696 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_compExpr698 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_cast707 = new BitSet(new long[]{0x0000000001000000L,0x0000000000650C00L});
    public static final BitSet FOLLOW_type_in_cast709 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_cast711 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_varlist723 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_dotname_in_varlist728 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_varlist732 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_dotname_in_varlist737 = new BitSet(new long[]{0x1200000000000000L});
    public static final BitSet FOLLOW_57_in_varlist741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_function749 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_dotname_in_function751 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_function753 = new BitSet(new long[]{0x2390100009080000L});
    public static final BitSet FOLLOW_fparamlist_in_function755 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_function758 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_predicate778 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_predicate780 = new BitSet(new long[]{0x2190100009080000L});
    public static final BitSet FOLLOW_paramlist_in_predicate782 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_predicate784 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_param_in_paramlist797 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_60_in_paramlist800 = new BitSet(new long[]{0x2190100009080000L});
    public static final BitSet FOLLOW_param_in_paramlist803 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_fparam_in_fparamlist811 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_60_in_fparamlist814 = new BitSet(new long[]{0x2190100009080000L});
    public static final BitSet FOLLOW_fparam_in_fparamlist817 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_simpleExpr_in_param826 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleExpr_in_fparam832 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_term839 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_term852 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_term864 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UTF8_in_term876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dotname_in_term889 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_table_decl905 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_table_decl907 = new BitSet(new long[]{0x0000000001000000L,0x0000000000650C00L});
    public static final BitSet FOLLOW_decls_in_table_decl909 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_table_decl911 = new BitSet(new long[]{0x0000000000002000L,0x000000000F188000L});
    public static final BitSet FOLLOW_table_opts_in_table_decl913 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_DOT_END_in_table_decl916 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_t_opt_in_table_opts949 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_60_in_table_opts952 = new BitSet(new long[]{0x0000000000000000L,0x000000000F188000L});
    public static final BitSet FOLLOW_t_opt_in_table_opts955 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_91_in_t_opt966 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_ID_in_t_opt970 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_SORT_ORDER_in_t_opt975 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_t_opt994 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_ID_in_t_opt996 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_t_opt1008 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_ID_in_t_opt1010 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_t_opt1022 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_t_opt1024 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_INT_in_t_opt1026 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_t_opt1028 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_t_opt1040 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_t_opt1048 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_t_opt1056 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_col_decls_in_decls1079 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_60_in_decls1082 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_decls1084 = new BitSet(new long[]{0x0000000001000000L,0x0000000000650C00L});
    public static final BitSet FOLLOW_decls_in_decls1086 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_decls1088 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_col_decl_in_col_decls1114 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_60_in_col_decls1117 = new BitSet(new long[]{0x0000000001000000L,0x0000000000650C00L});
    public static final BitSet FOLLOW_col_decl_in_col_decls1120 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_type_in_col_decl1130 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_ID_in_col_decl1132 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_col_decl1135 = new BitSet(new long[]{0x0000000028000000L});
    public static final BitSet FOLLOW_col_opt_in_col_decl1137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_col_opt1163 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_62_in_col_opt1165 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_INT_in_col_opt1169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ITER_DECL_in_col_opt1186 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_85_in_type1199 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_type1202 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_type1204 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_86_in_type1210 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_type1213 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_type1215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_type1223 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_type1226 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_type1228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_type1236 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_type1239 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_type1241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_type1249 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_type1252 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_type1254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_type1262 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_type1265 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_type1267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_type1274 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_type1277 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_type1279 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_dotname1291 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_DOT_ID_in_dotname1294 = new BitSet(new long[]{0x0000000000004002L});

}