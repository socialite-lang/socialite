// $ANTLR 3.4 /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g 2014-05-07 21:24:55

    package socialite.parser.antlr;    
    import socialite.parser.Query;
    import socialite.parser.Variable;
    import socialite.parser.Const;
    import socialite.parser.Predicate;
    import socialite.parser.Function;
    import socialite.parser.AggrFunction;
    import socialite.parser.Expr;
    import socialite.parser.Op;    
    import socialite.parser.CmpOp;
    import socialite.parser.BinOp;
    import socialite.parser.AssignOp;
    import socialite.parser.AssignDotVar;
    import socialite.parser.UnaryOp;    
    import socialite.parser.UnaryMinus;    
    import socialite.parser.TypeCast;
    import socialite.parser.hash;
    import socialite.parser.Parser;   
    import socialite.util.ParseException;
    import socialite.util.InternalException;
    import socialite.util.Assert;
    import java.util.List;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.Map;
    import java.util.Set;
    import java.util.LinkedHashSet;
    import socialite.type.Utf8;    
    import java.lang.reflect.Array;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class SociaLiteRule extends TreeParser {
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
    public TreeParser[] getDelegates() {
        return new TreeParser[] {};
    }

    // delegators


    public SociaLiteRule(TreeNodeStream input) {
        this(input, new RecognizerSharedState());
    }
    public SociaLiteRule(TreeNodeStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return SociaLiteRule.tokenNames; }
    public String getGrammarFileName() { return "/x/jiwon/workspace/socialite/grammar/SociaLiteRule.g"; }


        public Parser parser;
        public Map<String, TableDecl> tableDeclMap = new HashMap<String, TableDecl>();
        Set<Variable> dotVars = new LinkedHashSet<Variable>();
        List<AssignOp> tmpVarAssigns = new ArrayList<AssignOp>();
        int kind=0;
        public Parser getParser() { return parser; }
        public String maybeGetDuplicateColumnName(ColumnDecl d1, List<ColumnDecl> decls) {
            LinkedHashSet<String> names = new LinkedHashSet<String>(decls.size()+1);
            if (d1!=null) names.add(d1.name);
            if (decls==null) return null;
            for (ColumnDecl d:decls) {
                if (names.contains(d.name))
                    return d.name;
                names.add(d.name);
            }
            return null;
        }
        public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
    //        String hdr = getErrorHeader(e);
            String msg = getErrorMessage(e, tokenNames);
            throw new ParseException(parser, e, msg);
        }

        public void checkParamsInBodyP(Predicate bodyp, int line, int pos) {
            for (Object p:bodyp.getAllParams()) {
            }
        }
        public void checkParamsInHeadP(Predicate head, int line, int pos) {
            for (Object p:head.getAllParams()) {
            }
        }
        public boolean isSimpleIntValue(Object o) {
            return getSimpleIntValue(o) != null;
        }
        public Integer getSimpleIntValue(Object o) {
            if (o instanceof Const) {
                Const c=(Const)o;
                if (c.type.equals(Integer.class)||c.type.equals(int.class))
                    return (Integer)c.val;
            }

            if (o instanceof BinOp) {
                BinOp b = (BinOp)o;
                if (b.arg1 instanceof Const && b.arg2 instanceof Const) {
                    Integer arg1=(Integer)((Const)b.arg1).val;
                    Integer arg2=(Integer)((Const)b.arg2).val;
                    if (b.op.equals("+")) {
                        return arg1+arg2;
                    } else if (b.op.equals("-")) {
                        return arg1-arg2;
                    } else if (b.op.equals("mod")) {
                        return arg1%arg2;
                    } else if (b.op.equals("*")) {
                        return arg1*arg2;
                    } else if (b.op.equals("/")) {
                        return arg1/arg2;
                    }
                }
            }
            return null;
        }

        public Variable addTmpVarAssign(Object rhs) {
            Variable tmpVar = Variable.getTmpVar();
            try { tmpVarAssigns.add(new AssignOp(tmpVar, rhs));}
            catch (InternalException e) {}
            return tmpVar;
        }
        public boolean tmpVarAssignHasFunc() {
            for (AssignOp o:tmpVarAssigns) {
                if (o.arg2 instanceof Function) {
                    return true;
                }
            }
            return false;
        }


    public static class prog_return extends TreeRuleReturnScope {
        public List result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "prog"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:118:1: prog returns [List result] : ( stat )+ EOF ;
    public final SociaLiteRule.prog_return prog() throws RecognitionException {
        SociaLiteRule.prog_return retval = new SociaLiteRule.prog_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree EOF2=null;
        SociaLiteRule.stat_return stat1 =null;


        CommonTree EOF2_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:119:2: ( ( stat )+ EOF )
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:119:4: ( stat )+ EOF
            {
            root_0 = (CommonTree)adaptor.nil();


            retval.result = new ArrayList();

            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:120:2: ( stat )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==CLEAR||LA1_0==DECL||LA1_0==DROP||LA1_0==QUERY||LA1_0==RULE) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:120:3: stat
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_stat_in_prog65);
            	    stat1=stat();

            	    state._fsp--;

            	    adaptor.addChild(root_0, stat1.getTree());



            	    	    if ((stat1!=null?stat1.result:null) instanceof List)
            	    	        retval.result.addAll((List)((stat1!=null?stat1.result:null)));
            	    	    else
            	        	        retval.result.add((stat1!=null?stat1.result:null));

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


            _last = (CommonTree)input.LT(1);
            EOF2=(CommonTree)match(input,EOF,FOLLOW_EOF_in_prog77); 
            EOF2_tree = (CommonTree)adaptor.dupNode(EOF2);


            adaptor.addChild(root_0, EOF2_tree);


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "prog"


    public static class stat_return extends TreeRuleReturnScope {
        public Object result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "stat"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:127:1: stat returns [Object result] : ( table_decl | rule | query | table_stmt );
    public final SociaLiteRule.stat_return stat() throws RecognitionException {
        SociaLiteRule.stat_return retval = new SociaLiteRule.stat_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.table_decl_return table_decl3 =null;

        SociaLiteRule.rule_return rule4 =null;

        SociaLiteRule.query_return query5 =null;

        SociaLiteRule.table_stmt_return table_stmt6 =null;



        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:128:2: ( table_decl | rule | query | table_stmt )
            int alt2=4;
            switch ( input.LA(1) ) {
            case DECL:
                {
                alt2=1;
                }
                break;
            case RULE:
                {
                alt2=2;
                }
                break;
            case QUERY:
                {
                alt2=3;
                }
                break;
            case CLEAR:
            case DROP:
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
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:128:3: table_decl
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_table_decl_in_stat90);
                    table_decl3=table_decl();

                    state._fsp--;

                    adaptor.addChild(root_0, table_decl3.getTree());


                    retval.result = (table_decl3!=null?table_decl3.result:null);

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:129:3: rule
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_rule_in_stat96);
                    rule4=rule();

                    state._fsp--;

                    adaptor.addChild(root_0, rule4.getTree());


                    retval.result = (rule4!=null?rule4.result:null);

                    }
                    break;
                case 3 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:130:3: query
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_query_in_stat102);
                    query5=query();

                    state._fsp--;

                    adaptor.addChild(root_0, query5.getTree());


                    retval.result = (query5!=null?query5.result:null);

                    }
                    break;
                case 4 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:131:3: table_stmt
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_table_stmt_in_stat107);
                    table_stmt6=table_stmt();

                    state._fsp--;

                    adaptor.addChild(root_0, table_stmt6.getTree());


                    retval.result = (table_stmt6!=null?table_stmt6.result:null);

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "stat"


    public static class table_stmt_return extends TreeRuleReturnScope {
        public TableStmt result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "table_stmt"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:133:1: table_stmt returns [TableStmt result] : ( ^( CLEAR ID ) | ^( DROP ( ID )? ) );
    public final SociaLiteRule.table_stmt_return table_stmt() throws RecognitionException {
        SociaLiteRule.table_stmt_return retval = new SociaLiteRule.table_stmt_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree CLEAR7=null;
        CommonTree ID8=null;
        CommonTree DROP9=null;
        CommonTree ID10=null;

        CommonTree CLEAR7_tree=null;
        CommonTree ID8_tree=null;
        CommonTree DROP9_tree=null;
        CommonTree ID10_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:134:2: ( ^( CLEAR ID ) | ^( DROP ( ID )? ) )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==CLEAR) ) {
                alt4=1;
            }
            else if ( (LA4_0==DROP) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }
            switch (alt4) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:134:4: ^( CLEAR ID )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    CLEAR7=(CommonTree)match(input,CLEAR,FOLLOW_CLEAR_in_table_stmt124); 
                    CLEAR7_tree = (CommonTree)adaptor.dupNode(CLEAR7);


                    root_1 = (CommonTree)adaptor.becomeRoot(CLEAR7_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID8=(CommonTree)match(input,ID,FOLLOW_ID_in_table_stmt126); 
                    ID8_tree = (CommonTree)adaptor.dupNode(ID8);


                    adaptor.addChild(root_1, ID8_tree);


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }


                     retval.result = new ClearTable((ID8!=null?ID8.getText():null));

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:135:3: ^( DROP ( ID )? )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    DROP9=(CommonTree)match(input,DROP,FOLLOW_DROP_in_table_stmt135); 
                    DROP9_tree = (CommonTree)adaptor.dupNode(DROP9);


                    root_1 = (CommonTree)adaptor.becomeRoot(DROP9_tree, root_1);


                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:135:10: ( ID )?
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==ID) ) {
                            alt3=1;
                        }
                        switch (alt3) {
                            case 1 :
                                // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:135:10: ID
                                {
                                _last = (CommonTree)input.LT(1);
                                ID10=(CommonTree)match(input,ID,FOLLOW_ID_in_table_stmt137); 
                                ID10_tree = (CommonTree)adaptor.dupNode(ID10);


                                adaptor.addChild(root_1, ID10_tree);


                                }
                                break;

                        }


                        match(input, Token.UP, null); 
                    }
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }


                     retval.result = new DropTable((ID10!=null?ID10.getText():null));

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "table_stmt"


    public static class query_return extends TreeRuleReturnScope {
        public Query result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "query"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:137:1: query returns [Query result] : ^( QUERY predicate ) ;
    public final SociaLiteRule.query_return query() throws RecognitionException {
        SociaLiteRule.query_return retval = new SociaLiteRule.query_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree QUERY11=null;
        SociaLiteRule.predicate_return predicate12 =null;


        CommonTree QUERY11_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:138:2: ( ^( QUERY predicate ) )
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:138:3: ^( QUERY predicate )
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();
            _last = (CommonTree)input.LT(1);
            QUERY11=(CommonTree)match(input,QUERY,FOLLOW_QUERY_in_query155); 
            QUERY11_tree = (CommonTree)adaptor.dupNode(QUERY11);


            root_1 = (CommonTree)adaptor.becomeRoot(QUERY11_tree, root_1);


            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_predicate_in_query157);
            predicate12=predicate();

            state._fsp--;

            adaptor.addChild(root_1, predicate12.getTree());


            match(input, Token.UP, null); 
            adaptor.addChild(root_0, root_1);
            _last = _save_last_1;
            }


             
            	retval.result = new Query((predicate12!=null?predicate12.result:null));
            	Variable.nextRule();
            	Const.nextRule();
            	tmpVarAssigns.clear();
            	

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "query"


    public static class table_decl_return extends TreeRuleReturnScope {
        public TableDecl result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "table_decl"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:145:1: table_decl returns [TableDecl result] : ^( DECL ( KIND1 | KIND2 ) ID ^( INDEX ( col_decl )? ) decls ^( OPTION ( table_opts )? ) ) ;
    public final SociaLiteRule.table_decl_return table_decl() throws RecognitionException {
        SociaLiteRule.table_decl_return retval = new SociaLiteRule.table_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree DECL13=null;
        CommonTree KIND114=null;
        CommonTree KIND215=null;
        CommonTree ID16=null;
        CommonTree INDEX17=null;
        CommonTree OPTION20=null;
        SociaLiteRule.col_decl_return col_decl18 =null;

        SociaLiteRule.decls_return decls19 =null;

        SociaLiteRule.table_opts_return table_opts21 =null;


        CommonTree DECL13_tree=null;
        CommonTree KIND114_tree=null;
        CommonTree KIND215_tree=null;
        CommonTree ID16_tree=null;
        CommonTree INDEX17_tree=null;
        CommonTree OPTION20_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:146:2: ( ^( DECL ( KIND1 | KIND2 ) ID ^( INDEX ( col_decl )? ) decls ^( OPTION ( table_opts )? ) ) )
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:146:3: ^( DECL ( KIND1 | KIND2 ) ID ^( INDEX ( col_decl )? ) decls ^( OPTION ( table_opts )? ) )
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();
            _last = (CommonTree)input.LT(1);
            DECL13=(CommonTree)match(input,DECL,FOLLOW_DECL_in_table_decl174); 
            DECL13_tree = (CommonTree)adaptor.dupNode(DECL13);


            root_1 = (CommonTree)adaptor.becomeRoot(DECL13_tree, root_1);


            match(input, Token.DOWN, null); 
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:146:10: ( KIND1 | KIND2 )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==KIND1) ) {
                alt5=1;
            }
            else if ( (LA5_0==KIND2) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;

            }
            switch (alt5) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:146:11: KIND1
                    {
                    _last = (CommonTree)input.LT(1);
                    KIND114=(CommonTree)match(input,KIND1,FOLLOW_KIND1_in_table_decl177); 
                    KIND114_tree = (CommonTree)adaptor.dupNode(KIND114);


                    adaptor.addChild(root_1, KIND114_tree);


                    kind=1;

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:146:28: KIND2
                    {
                    _last = (CommonTree)input.LT(1);
                    KIND215=(CommonTree)match(input,KIND2,FOLLOW_KIND2_in_table_decl182); 
                    KIND215_tree = (CommonTree)adaptor.dupNode(KIND215);


                    adaptor.addChild(root_1, KIND215_tree);


                    kind=2;

                    }
                    break;

            }


            _last = (CommonTree)input.LT(1);
            ID16=(CommonTree)match(input,ID,FOLLOW_ID_in_table_decl188); 
            ID16_tree = (CommonTree)adaptor.dupNode(ID16);


            adaptor.addChild(root_1, ID16_tree);


            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_2 = _last;
            CommonTree _first_2 = null;
            CommonTree root_2 = (CommonTree)adaptor.nil();
            _last = (CommonTree)input.LT(1);
            INDEX17=(CommonTree)match(input,INDEX,FOLLOW_INDEX_in_table_decl191); 
            INDEX17_tree = (CommonTree)adaptor.dupNode(INDEX17);


            root_2 = (CommonTree)adaptor.becomeRoot(INDEX17_tree, root_2);


            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:146:57: ( col_decl )?
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==COL_DECL) ) {
                    alt6=1;
                }
                switch (alt6) {
                    case 1 :
                        // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:146:57: col_decl
                        {
                        _last = (CommonTree)input.LT(1);
                        pushFollow(FOLLOW_col_decl_in_table_decl193);
                        col_decl18=col_decl();

                        state._fsp--;

                        adaptor.addChild(root_2, col_decl18.getTree());


                        }
                        break;

                }


                match(input, Token.UP, null); 
            }
            adaptor.addChild(root_1, root_2);
            _last = _save_last_2;
            }


            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_decls_in_table_decl197);
            decls19=decls();

            state._fsp--;

            adaptor.addChild(root_1, decls19.getTree());


            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_2 = _last;
            CommonTree _first_2 = null;
            CommonTree root_2 = (CommonTree)adaptor.nil();
            _last = (CommonTree)input.LT(1);
            OPTION20=(CommonTree)match(input,OPTION,FOLLOW_OPTION_in_table_decl201); 
            OPTION20_tree = (CommonTree)adaptor.dupNode(OPTION20);


            root_2 = (CommonTree)adaptor.becomeRoot(OPTION20_tree, root_2);


            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:146:84: ( table_opts )?
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==GROUP_BY||LA7_0==INDEX_BY||LA7_0==MULTISET||(LA7_0 >= ORDER_BY && LA7_0 <= PREDEFINED)||LA7_0==SORT_BY) ) {
                    alt7=1;
                }
                switch (alt7) {
                    case 1 :
                        // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:146:84: table_opts
                        {
                        _last = (CommonTree)input.LT(1);
                        pushFollow(FOLLOW_table_opts_in_table_decl203);
                        table_opts21=table_opts();

                        state._fsp--;

                        adaptor.addChild(root_2, table_opts21.getTree());


                        }
                        break;

                }


                match(input, Token.UP, null); 
            }
            adaptor.addChild(root_1, root_2);
            _last = _save_last_2;
            }


            match(input, Token.UP, null); 
            adaptor.addChild(root_0, root_1);
            _last = _save_last_1;
            }



            	    if (kind==2 && (col_decl18!=null?col_decl18.result:null)==null) 
            	        throw new ParseException(getParser(), (ID16!=null?ID16.getLine():0)-1, (ID16!=null?ID16.getCharPositionInLine():0)+(ID16!=null?ID16.getText():null).length()+1, "Cannot have nested table without any other columns"); 
            	    String dupCol=maybeGetDuplicateColumnName((col_decl18!=null?col_decl18.result:null), (decls19!=null?decls19.result:null).getAllColDecls());
            	    if (dupCol!=null) 
            	        throw new ParseException(getParser(), (ID16!=null?ID16.getLine():0)-1, (ID16!=null?ID16.getCharPositionInLine():0),  "Duplicate column name "+dupCol+" in "+ID16);
            	    if (kind==1) retval.result = new TableDecl((ID16!=null?ID16.getText():null), (col_decl18!=null?col_decl18.result:null), (decls19!=null?decls19.result:null).colDecls, (decls19!=null?decls19.result:null).nestedTable);    
            	    else retval.result = new TableDecl((ID16!=null?ID16.getText():null), (col_decl18!=null?col_decl18.result:null), null, (decls19!=null?decls19.result:null));    
            	    if (retval.result.nestedTable!=null) {
            	        for (ColumnDecl d:retval.result.nestedTable.getAllColDecls()) {
            	            if (d.option() instanceof ColIter) {
            	                throw new ParseException(getParser(), (ID16!=null?ID16.getLine():0)-1, (ID16!=null?ID16.getCharPositionInLine():0)+(ID16!=null?ID16.getText():null).length()+1, "Iterator column cannot be nested."); 
            	            }
            	        }
            	    }
            	    try { retval.result.setOptions((table_opts21!=null?table_opts21.result:null)); }
            	    catch (ParseException e) {
            	        e.setLine((ID16!=null?ID16.getLine():0)-1); e.setPos(0);e.setParser(getParser());
            	        throw e;
            	    }
            	    if (tableDeclMap.containsKey((ID16!=null?ID16.getText():null))) {
            	        if (!retval.result.equals(tableDeclMap.get((ID16!=null?ID16.getText():null)))) {
            	            throw new ParseException(getParser(), (ID16!=null?ID16.getLine():0)-1, (ID16!=null?ID16.getCharPositionInLine():0), 
            	                        (ID16!=null?ID16.getText():null)+" was previously declared with different signature.");	 
            	        }
            	        retval.result =null;
            	    } else { tableDeclMap.put((ID16!=null?ID16.getText():null), retval.result); }
            	

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "table_decl"


    public static class table_opts_return extends TreeRuleReturnScope {
        public List<TableOpt> result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "table_opts"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:177:1: table_opts returns [List<TableOpt> result] : opt1= t_opt (opt2= t_opt )* ;
    public final SociaLiteRule.table_opts_return table_opts() throws RecognitionException {
        SociaLiteRule.table_opts_return retval = new SociaLiteRule.table_opts_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.t_opt_return opt1 =null;

        SociaLiteRule.t_opt_return opt2 =null;



        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:178:2: (opt1= t_opt (opt2= t_opt )* )
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:178:3: opt1= t_opt (opt2= t_opt )*
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_t_opt_in_table_opts229);
            opt1=t_opt();

            state._fsp--;

            adaptor.addChild(root_0, opt1.getTree());


            retval.result = new ArrayList<TableOpt>(); retval.result.add((opt1!=null?opt1.result:null));

            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:179:2: (opt2= t_opt )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==GROUP_BY||LA8_0==INDEX_BY||LA8_0==MULTISET||(LA8_0 >= ORDER_BY && LA8_0 <= PREDEFINED)||LA8_0==SORT_BY) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:179:3: opt2= t_opt
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_t_opt_in_table_opts237);
            	    opt2=t_opt();

            	    state._fsp--;

            	    adaptor.addChild(root_0, opt2.getTree());


            	    retval.result.add((opt2!=null?opt2.result:null));

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "table_opts"


    public static class t_opt_return extends TreeRuleReturnScope {
        public TableOpt result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "t_opt"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:181:1: t_opt returns [TableOpt result] : ( ^( SORT_BY col= ID (order= SORT_ORDER )? ) | ^( ORDER_BY ID ) | ^( INDEX_BY ID ) | ^( GROUP_BY INT ) | PREDEFINED | MULTISET );
    public final SociaLiteRule.t_opt_return t_opt() throws RecognitionException {
        SociaLiteRule.t_opt_return retval = new SociaLiteRule.t_opt_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree col=null;
        CommonTree order=null;
        CommonTree SORT_BY22=null;
        CommonTree ORDER_BY23=null;
        CommonTree ID24=null;
        CommonTree INDEX_BY25=null;
        CommonTree ID26=null;
        CommonTree GROUP_BY27=null;
        CommonTree INT28=null;
        CommonTree PREDEFINED29=null;
        CommonTree MULTISET30=null;

        CommonTree col_tree=null;
        CommonTree order_tree=null;
        CommonTree SORT_BY22_tree=null;
        CommonTree ORDER_BY23_tree=null;
        CommonTree ID24_tree=null;
        CommonTree INDEX_BY25_tree=null;
        CommonTree ID26_tree=null;
        CommonTree GROUP_BY27_tree=null;
        CommonTree INT28_tree=null;
        CommonTree PREDEFINED29_tree=null;
        CommonTree MULTISET30_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:182:2: ( ^( SORT_BY col= ID (order= SORT_ORDER )? ) | ^( ORDER_BY ID ) | ^( INDEX_BY ID ) | ^( GROUP_BY INT ) | PREDEFINED | MULTISET )
            int alt10=6;
            switch ( input.LA(1) ) {
            case SORT_BY:
                {
                alt10=1;
                }
                break;
            case ORDER_BY:
                {
                alt10=2;
                }
                break;
            case INDEX_BY:
                {
                alt10=3;
                }
                break;
            case GROUP_BY:
                {
                alt10=4;
                }
                break;
            case PREDEFINED:
                {
                alt10=5;
                }
                break;
            case MULTISET:
                {
                alt10=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;

            }

            switch (alt10) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:182:3: ^( SORT_BY col= ID (order= SORT_ORDER )? )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    SORT_BY22=(CommonTree)match(input,SORT_BY,FOLLOW_SORT_BY_in_t_opt256); 
                    SORT_BY22_tree = (CommonTree)adaptor.dupNode(SORT_BY22);


                    root_1 = (CommonTree)adaptor.becomeRoot(SORT_BY22_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    col=(CommonTree)match(input,ID,FOLLOW_ID_in_t_opt260); 
                    col_tree = (CommonTree)adaptor.dupNode(col);


                    adaptor.addChild(root_1, col_tree);


                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:182:20: (order= SORT_ORDER )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==SORT_ORDER) ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:182:21: order= SORT_ORDER
                            {
                            _last = (CommonTree)input.LT(1);
                            order=(CommonTree)match(input,SORT_ORDER,FOLLOW_SORT_ORDER_in_t_opt265); 
                            order_tree = (CommonTree)adaptor.dupNode(order);


                            adaptor.addChild(root_1, order_tree);


                            }
                            break;

                    }


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }



                    	    if ((order!=null?order.getText():null) == null)  retval.result = new SortBy((col!=null?col.getText():null));
                    	    else if ((order!=null?order.getText():null).equals("asc"))  retval.result = new SortBy((col!=null?col.getText():null));
                    	    else  retval.result = new SortBy((col!=null?col.getText():null), false);
                    	

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:187:3: ^( ORDER_BY ID )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    ORDER_BY23=(CommonTree)match(input,ORDER_BY,FOLLOW_ORDER_BY_in_t_opt275); 
                    ORDER_BY23_tree = (CommonTree)adaptor.dupNode(ORDER_BY23);


                    root_1 = (CommonTree)adaptor.becomeRoot(ORDER_BY23_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID24=(CommonTree)match(input,ID,FOLLOW_ID_in_t_opt277); 
                    ID24_tree = (CommonTree)adaptor.dupNode(ID24);


                    adaptor.addChild(root_1, ID24_tree);


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }


                    retval.result = new OrderBy((ID24!=null?ID24.getText():null));

                    }
                    break;
                case 3 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:188:3: ^( INDEX_BY ID )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    INDEX_BY25=(CommonTree)match(input,INDEX_BY,FOLLOW_INDEX_BY_in_t_opt285); 
                    INDEX_BY25_tree = (CommonTree)adaptor.dupNode(INDEX_BY25);


                    root_1 = (CommonTree)adaptor.becomeRoot(INDEX_BY25_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID26=(CommonTree)match(input,ID,FOLLOW_ID_in_t_opt287); 
                    ID26_tree = (CommonTree)adaptor.dupNode(ID26);


                    adaptor.addChild(root_1, ID26_tree);


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }


                    retval.result = new IndexBy((ID26!=null?ID26.getText():null));

                    }
                    break;
                case 4 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:189:3: ^( GROUP_BY INT )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    GROUP_BY27=(CommonTree)match(input,GROUP_BY,FOLLOW_GROUP_BY_in_t_opt295); 
                    GROUP_BY27_tree = (CommonTree)adaptor.dupNode(GROUP_BY27);


                    root_1 = (CommonTree)adaptor.becomeRoot(GROUP_BY27_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    INT28=(CommonTree)match(input,INT,FOLLOW_INT_in_t_opt297); 
                    INT28_tree = (CommonTree)adaptor.dupNode(INT28);


                    adaptor.addChild(root_1, INT28_tree);


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }


                     retval.result = new GroupBy(Integer.parseInt((INT28!=null?INT28.getText():null)));

                    }
                    break;
                case 5 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:190:4: PREDEFINED
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    PREDEFINED29=(CommonTree)match(input,PREDEFINED,FOLLOW_PREDEFINED_in_t_opt305); 
                    PREDEFINED29_tree = (CommonTree)adaptor.dupNode(PREDEFINED29);


                    adaptor.addChild(root_0, PREDEFINED29_tree);


                    retval.result = new Predefined(); 

                    }
                    break;
                case 6 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:191:4: MULTISET
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    MULTISET30=(CommonTree)match(input,MULTISET,FOLLOW_MULTISET_in_t_opt312); 
                    MULTISET30_tree = (CommonTree)adaptor.dupNode(MULTISET30);


                    adaptor.addChild(root_0, MULTISET30_tree);


                    retval.result = new MultiSet();

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "t_opt"


    public static class decls_return extends TreeRuleReturnScope {
        public NestedTableDecl result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "decls"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:193:1: decls returns [NestedTableDecl result] : ^( COL_DECLS col_decls ^( DECL (nested= decls )? ) ) ;
    public final SociaLiteRule.decls_return decls() throws RecognitionException {
        SociaLiteRule.decls_return retval = new SociaLiteRule.decls_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree COL_DECLS31=null;
        CommonTree DECL33=null;
        SociaLiteRule.decls_return nested =null;

        SociaLiteRule.col_decls_return col_decls32 =null;


        CommonTree COL_DECLS31_tree=null;
        CommonTree DECL33_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:194:2: ( ^( COL_DECLS col_decls ^( DECL (nested= decls )? ) ) )
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:194:3: ^( COL_DECLS col_decls ^( DECL (nested= decls )? ) )
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();
            _last = (CommonTree)input.LT(1);
            COL_DECLS31=(CommonTree)match(input,COL_DECLS,FOLLOW_COL_DECLS_in_decls329); 
            COL_DECLS31_tree = (CommonTree)adaptor.dupNode(COL_DECLS31);


            root_1 = (CommonTree)adaptor.becomeRoot(COL_DECLS31_tree, root_1);


            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_col_decls_in_decls331);
            col_decls32=col_decls();

            state._fsp--;

            adaptor.addChild(root_1, col_decls32.getTree());


            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_2 = _last;
            CommonTree _first_2 = null;
            CommonTree root_2 = (CommonTree)adaptor.nil();
            _last = (CommonTree)input.LT(1);
            DECL33=(CommonTree)match(input,DECL,FOLLOW_DECL_in_decls334); 
            DECL33_tree = (CommonTree)adaptor.dupNode(DECL33);


            root_2 = (CommonTree)adaptor.becomeRoot(DECL33_tree, root_2);


            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:194:38: (nested= decls )?
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==COL_DECLS) ) {
                    alt11=1;
                }
                switch (alt11) {
                    case 1 :
                        // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:194:38: nested= decls
                        {
                        _last = (CommonTree)input.LT(1);
                        pushFollow(FOLLOW_decls_in_decls338);
                        nested=decls();

                        state._fsp--;

                        adaptor.addChild(root_2, nested.getTree());


                        }
                        break;

                }


                match(input, Token.UP, null); 
            }
            adaptor.addChild(root_1, root_2);
            _last = _save_last_2;
            }


            match(input, Token.UP, null); 
            adaptor.addChild(root_0, root_1);
            _last = _save_last_1;
            }


             retval.result = new NestedTableDecl((col_decls32!=null?col_decls32.result:null), (nested!=null?nested.result:null));
            	

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "decls"


    public static class col_decls_return extends TreeRuleReturnScope {
        public List<ColumnDecl> result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "col_decls"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:197:1: col_decls returns [List<ColumnDecl> result] : d1= col_decl (d2= col_decl )* ;
    public final SociaLiteRule.col_decls_return col_decls() throws RecognitionException {
        SociaLiteRule.col_decls_return retval = new SociaLiteRule.col_decls_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.col_decl_return d1 =null;

        SociaLiteRule.col_decl_return d2 =null;



        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:198:2: (d1= col_decl (d2= col_decl )* )
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:198:3: d1= col_decl (d2= col_decl )*
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_col_decl_in_col_decls358);
            d1=col_decl();

            state._fsp--;

            adaptor.addChild(root_0, d1.getTree());


            retval.result = new ArrayList<ColumnDecl>(); retval.result.add((d1!=null?d1.result:null));

            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:199:3: (d2= col_decl )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==COL_DECL) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:199:4: d2= col_decl
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_col_decl_in_col_decls367);
            	    d2=col_decl();

            	    state._fsp--;

            	    adaptor.addChild(root_0, d2.getTree());


            	    retval.result.add((d2!=null?d2.result:null));

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "col_decls"


    public static class col_decl_return extends TreeRuleReturnScope {
        public ColumnDecl result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "col_decl"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:201:1: col_decl returns [ColumnDecl result] : ^( COL_DECL type ID ( col_opt )? ) ;
    public final SociaLiteRule.col_decl_return col_decl() throws RecognitionException {
        SociaLiteRule.col_decl_return retval = new SociaLiteRule.col_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree COL_DECL34=null;
        CommonTree ID36=null;
        SociaLiteRule.type_return type35 =null;

        SociaLiteRule.col_opt_return col_opt37 =null;


        CommonTree COL_DECL34_tree=null;
        CommonTree ID36_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:202:2: ( ^( COL_DECL type ID ( col_opt )? ) )
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:202:4: ^( COL_DECL type ID ( col_opt )? )
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();
            _last = (CommonTree)input.LT(1);
            COL_DECL34=(CommonTree)match(input,COL_DECL,FOLLOW_COL_DECL_in_col_decl388); 
            COL_DECL34_tree = (CommonTree)adaptor.dupNode(COL_DECL34);


            root_1 = (CommonTree)adaptor.becomeRoot(COL_DECL34_tree, root_1);


            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_type_in_col_decl390);
            type35=type();

            state._fsp--;

            adaptor.addChild(root_1, type35.getTree());


            _last = (CommonTree)input.LT(1);
            ID36=(CommonTree)match(input,ID,FOLLOW_ID_in_col_decl392); 
            ID36_tree = (CommonTree)adaptor.dupNode(ID36);


            adaptor.addChild(root_1, ID36_tree);


            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:202:23: ( col_opt )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==ITER||LA13_0==RANGE||LA13_0==SIZE) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:202:23: col_opt
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_col_opt_in_col_decl394);
                    col_opt37=col_opt();

                    state._fsp--;

                    adaptor.addChild(root_1, col_opt37.getTree());


                    }
                    break;

            }


            match(input, Token.UP, null); 
            adaptor.addChild(root_0, root_1);
            _last = _save_last_1;
            }



            	retval.result = new ColumnDecl((type35!=null?type35.result:null), (ID36!=null?ID36.getText():null), (col_opt37!=null?col_opt37.result:null));
            	

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "col_decl"


    public static class col_opt_return extends TreeRuleReturnScope {
        public ColOpt result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "col_opt"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:206:1: col_opt returns [ColOpt result] : ( ^( RANGE i1= INT i2= INT ) | ^( SIZE i1= INT ) | ITER );
    public final SociaLiteRule.col_opt_return col_opt() throws RecognitionException {
        SociaLiteRule.col_opt_return retval = new SociaLiteRule.col_opt_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree i1=null;
        CommonTree i2=null;
        CommonTree RANGE38=null;
        CommonTree SIZE39=null;
        CommonTree ITER40=null;

        CommonTree i1_tree=null;
        CommonTree i2_tree=null;
        CommonTree RANGE38_tree=null;
        CommonTree SIZE39_tree=null;
        CommonTree ITER40_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:207:2: ( ^( RANGE i1= INT i2= INT ) | ^( SIZE i1= INT ) | ITER )
            int alt14=3;
            switch ( input.LA(1) ) {
            case RANGE:
                {
                alt14=1;
                }
                break;
            case SIZE:
                {
                alt14=2;
                }
                break;
            case ITER:
                {
                alt14=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;

            }

            switch (alt14) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:207:3: ^( RANGE i1= INT i2= INT )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    RANGE38=(CommonTree)match(input,RANGE,FOLLOW_RANGE_in_col_opt412); 
                    RANGE38_tree = (CommonTree)adaptor.dupNode(RANGE38);


                    root_1 = (CommonTree)adaptor.becomeRoot(RANGE38_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    i1=(CommonTree)match(input,INT,FOLLOW_INT_in_col_opt416); 
                    i1_tree = (CommonTree)adaptor.dupNode(i1);


                    adaptor.addChild(root_1, i1_tree);


                    _last = (CommonTree)input.LT(1);
                    i2=(CommonTree)match(input,INT,FOLLOW_INT_in_col_opt420); 
                    i2_tree = (CommonTree)adaptor.dupNode(i2);


                    adaptor.addChild(root_1, i2_tree);


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }



                    	    retval.result = new ColRange(Integer.parseInt((i1!=null?i1.getText():null)), Integer.parseInt((i2!=null?i2.getText():null)));
                    	

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:210:3: ^( SIZE i1= INT )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    SIZE39=(CommonTree)match(input,SIZE,FOLLOW_SIZE_in_col_opt428); 
                    SIZE39_tree = (CommonTree)adaptor.dupNode(SIZE39);


                    root_1 = (CommonTree)adaptor.becomeRoot(SIZE39_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    i1=(CommonTree)match(input,INT,FOLLOW_INT_in_col_opt432); 
                    i1_tree = (CommonTree)adaptor.dupNode(i1);


                    adaptor.addChild(root_1, i1_tree);


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }



                    	  retval.result = new ColSize(Integer.parseInt((i1!=null?i1.getText():null)));
                    	

                    }
                    break;
                case 3 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:213:4: ITER
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    ITER40=(CommonTree)match(input,ITER,FOLLOW_ITER_in_col_opt440); 
                    ITER40_tree = (CommonTree)adaptor.dupNode(ITER40);


                    adaptor.addChild(root_0, ITER40_tree);



                    	 retval.result = new ColIter();
                    	

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "col_opt"


    public static class type_return extends TreeRuleReturnScope {
        public Class result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "type"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:217:1: type returns [Class result] : ( 'int' ( '[' ']' )? | 'long' ( '[' ']' )? | 'float' ( '[' ']' )? | 'double' ( '[' ']' )? | 'String' ( '[' ']' )? | 'Object' ( '[' ']' )? | ID ( '[' ']' )? );
    public final SociaLiteRule.type_return type() throws RecognitionException {
        SociaLiteRule.type_return retval = new SociaLiteRule.type_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal41=null;
        CommonTree char_literal42=null;
        CommonTree char_literal43=null;
        CommonTree string_literal44=null;
        CommonTree char_literal45=null;
        CommonTree char_literal46=null;
        CommonTree string_literal47=null;
        CommonTree char_literal48=null;
        CommonTree char_literal49=null;
        CommonTree string_literal50=null;
        CommonTree char_literal51=null;
        CommonTree char_literal52=null;
        CommonTree string_literal53=null;
        CommonTree char_literal54=null;
        CommonTree char_literal55=null;
        CommonTree string_literal56=null;
        CommonTree char_literal57=null;
        CommonTree char_literal58=null;
        CommonTree ID59=null;
        CommonTree char_literal60=null;
        CommonTree char_literal61=null;

        CommonTree string_literal41_tree=null;
        CommonTree char_literal42_tree=null;
        CommonTree char_literal43_tree=null;
        CommonTree string_literal44_tree=null;
        CommonTree char_literal45_tree=null;
        CommonTree char_literal46_tree=null;
        CommonTree string_literal47_tree=null;
        CommonTree char_literal48_tree=null;
        CommonTree char_literal49_tree=null;
        CommonTree string_literal50_tree=null;
        CommonTree char_literal51_tree=null;
        CommonTree char_literal52_tree=null;
        CommonTree string_literal53_tree=null;
        CommonTree char_literal54_tree=null;
        CommonTree char_literal55_tree=null;
        CommonTree string_literal56_tree=null;
        CommonTree char_literal57_tree=null;
        CommonTree char_literal58_tree=null;
        CommonTree ID59_tree=null;
        CommonTree char_literal60_tree=null;
        CommonTree char_literal61_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:218:2: ( 'int' ( '[' ']' )? | 'long' ( '[' ']' )? | 'float' ( '[' ']' )? | 'double' ( '[' ']' )? | 'String' ( '[' ']' )? | 'Object' ( '[' ']' )? | ID ( '[' ']' )? )
            int alt22=7;
            switch ( input.LA(1) ) {
            case 93:
                {
                alt22=1;
                }
                break;
            case 94:
                {
                alt22=2;
                }
                break;
            case 90:
                {
                alt22=3;
                }
                break;
            case 88:
                {
                alt22=4;
                }
                break;
            case 84:
                {
                alt22=5;
                }
                break;
            case 83:
                {
                alt22=6;
                }
                break;
            case ID:
                {
                alt22=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;

            }

            switch (alt22) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:218:3: 'int' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    string_literal41=(CommonTree)match(input,93,FOLLOW_93_in_type455); 
                    string_literal41_tree = (CommonTree)adaptor.dupNode(string_literal41);


                    adaptor.addChild(root_0, string_literal41_tree);


                    retval.result = int.class;

                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:218:32: ( '[' ']' )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==85) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:218:33: '[' ']'
                            {
                            _last = (CommonTree)input.LT(1);
                            char_literal42=(CommonTree)match(input,85,FOLLOW_85_in_type460); 
                            char_literal42_tree = (CommonTree)adaptor.dupNode(char_literal42);


                            adaptor.addChild(root_0, char_literal42_tree);


                            _last = (CommonTree)input.LT(1);
                            char_literal43=(CommonTree)match(input,86,FOLLOW_86_in_type462); 
                            char_literal43_tree = (CommonTree)adaptor.dupNode(char_literal43);


                            adaptor.addChild(root_0, char_literal43_tree);


                            retval.result = int[].class;

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:219:3: 'long' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    string_literal44=(CommonTree)match(input,94,FOLLOW_94_in_type473); 
                    string_literal44_tree = (CommonTree)adaptor.dupNode(string_literal44);


                    adaptor.addChild(root_0, string_literal44_tree);


                    retval.result =long.class;

                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:219:31: ( '[' ']' )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==85) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:219:32: '[' ']'
                            {
                            _last = (CommonTree)input.LT(1);
                            char_literal45=(CommonTree)match(input,85,FOLLOW_85_in_type477); 
                            char_literal45_tree = (CommonTree)adaptor.dupNode(char_literal45);


                            adaptor.addChild(root_0, char_literal45_tree);


                            _last = (CommonTree)input.LT(1);
                            char_literal46=(CommonTree)match(input,86,FOLLOW_86_in_type479); 
                            char_literal46_tree = (CommonTree)adaptor.dupNode(char_literal46);


                            adaptor.addChild(root_0, char_literal46_tree);


                            retval.result = long[].class;

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:220:3: 'float' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    string_literal47=(CommonTree)match(input,90,FOLLOW_90_in_type488); 
                    string_literal47_tree = (CommonTree)adaptor.dupNode(string_literal47);


                    adaptor.addChild(root_0, string_literal47_tree);


                    retval.result = float.class;

                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:220:36: ( '[' ']' )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==85) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:220:37: '[' ']'
                            {
                            _last = (CommonTree)input.LT(1);
                            char_literal48=(CommonTree)match(input,85,FOLLOW_85_in_type493); 
                            char_literal48_tree = (CommonTree)adaptor.dupNode(char_literal48);


                            adaptor.addChild(root_0, char_literal48_tree);


                            _last = (CommonTree)input.LT(1);
                            char_literal49=(CommonTree)match(input,86,FOLLOW_86_in_type495); 
                            char_literal49_tree = (CommonTree)adaptor.dupNode(char_literal49);


                            adaptor.addChild(root_0, char_literal49_tree);


                            retval.result = float[].class;

                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:221:3: 'double' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    string_literal50=(CommonTree)match(input,88,FOLLOW_88_in_type505); 
                    string_literal50_tree = (CommonTree)adaptor.dupNode(string_literal50);


                    adaptor.addChild(root_0, string_literal50_tree);


                    retval.result = double.class;

                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:221:38: ( '[' ']' )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==85) ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:221:39: '[' ']'
                            {
                            _last = (CommonTree)input.LT(1);
                            char_literal51=(CommonTree)match(input,85,FOLLOW_85_in_type510); 
                            char_literal51_tree = (CommonTree)adaptor.dupNode(char_literal51);


                            adaptor.addChild(root_0, char_literal51_tree);


                            _last = (CommonTree)input.LT(1);
                            char_literal52=(CommonTree)match(input,86,FOLLOW_86_in_type512); 
                            char_literal52_tree = (CommonTree)adaptor.dupNode(char_literal52);


                            adaptor.addChild(root_0, char_literal52_tree);


                            retval.result = double[].class;

                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:222:3: 'String' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    string_literal53=(CommonTree)match(input,84,FOLLOW_84_in_type522); 
                    string_literal53_tree = (CommonTree)adaptor.dupNode(string_literal53);


                    adaptor.addChild(root_0, string_literal53_tree);


                    retval.result = String.class;

                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:222:38: ( '[' ']' )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==85) ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:222:39: '[' ']'
                            {
                            _last = (CommonTree)input.LT(1);
                            char_literal54=(CommonTree)match(input,85,FOLLOW_85_in_type527); 
                            char_literal54_tree = (CommonTree)adaptor.dupNode(char_literal54);


                            adaptor.addChild(root_0, char_literal54_tree);


                            _last = (CommonTree)input.LT(1);
                            char_literal55=(CommonTree)match(input,86,FOLLOW_86_in_type529); 
                            char_literal55_tree = (CommonTree)adaptor.dupNode(char_literal55);


                            adaptor.addChild(root_0, char_literal55_tree);


                            retval.result = String[].class;

                            }
                            break;

                    }


                    }
                    break;
                case 6 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:223:3: 'Object' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    string_literal56=(CommonTree)match(input,83,FOLLOW_83_in_type539); 
                    string_literal56_tree = (CommonTree)adaptor.dupNode(string_literal56);


                    adaptor.addChild(root_0, string_literal56_tree);


                    retval.result = Object.class;

                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:223:37: ( '[' ']' )?
                    int alt20=2;
                    int LA20_0 = input.LA(1);

                    if ( (LA20_0==85) ) {
                        alt20=1;
                    }
                    switch (alt20) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:223:38: '[' ']'
                            {
                            _last = (CommonTree)input.LT(1);
                            char_literal57=(CommonTree)match(input,85,FOLLOW_85_in_type543); 
                            char_literal57_tree = (CommonTree)adaptor.dupNode(char_literal57);


                            adaptor.addChild(root_0, char_literal57_tree);


                            _last = (CommonTree)input.LT(1);
                            char_literal58=(CommonTree)match(input,86,FOLLOW_86_in_type545); 
                            char_literal58_tree = (CommonTree)adaptor.dupNode(char_literal58);


                            adaptor.addChild(root_0, char_literal58_tree);


                            retval.result = Object[].class;

                            }
                            break;

                    }


                    }
                    break;
                case 7 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:224:4: ID ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    ID59=(CommonTree)match(input,ID,FOLLOW_ID_in_type556); 
                    ID59_tree = (CommonTree)adaptor.dupNode(ID59);


                    adaptor.addChild(root_0, ID59_tree);


                     
                    	    String fullname = "socialite.type."+(ID59!=null?ID59.getText():null);
                    	    try {  retval.result = Class.forName(fullname);
                    	    } catch (ClassNotFoundException e) {
                    	        throw new ParseException(getParser(), (ID59!=null?ID59.getLine():0)-1, (ID59!=null?ID59.getCharPositionInLine():0), "Class "+fullname+" cannot be resolved");	        
                    	    }
                    	

                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:230:4: ( '[' ']' )?
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0==85) ) {
                        alt21=1;
                    }
                    switch (alt21) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:230:5: '[' ']'
                            {
                            _last = (CommonTree)input.LT(1);
                            char_literal60=(CommonTree)match(input,85,FOLLOW_85_in_type561); 
                            char_literal60_tree = (CommonTree)adaptor.dupNode(char_literal60);


                            adaptor.addChild(root_0, char_literal60_tree);


                            _last = (CommonTree)input.LT(1);
                            char_literal61=(CommonTree)match(input,86,FOLLOW_86_in_type563); 
                            char_literal61_tree = (CommonTree)adaptor.dupNode(char_literal61);


                            adaptor.addChild(root_0, char_literal61_tree);


                            retval.result = Array.newInstance((Class)retval.result,0).getClass();

                            }
                            break;

                    }


                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "type"


    public static class rule_return extends TreeRuleReturnScope {
        public Object result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "rule"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:232:1: rule returns [Object result] : ^( RULE ^( HEAD predicate ) ^( BODY body1= litlist ) ^( BODY (body2= litlist )? ) ) ;
    public final SociaLiteRule.rule_return rule() throws RecognitionException {
        SociaLiteRule.rule_return retval = new SociaLiteRule.rule_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree RULE62=null;
        CommonTree HEAD63=null;
        CommonTree BODY65=null;
        CommonTree BODY66=null;
        SociaLiteRule.litlist_return body1 =null;

        SociaLiteRule.litlist_return body2 =null;

        SociaLiteRule.predicate_return predicate64 =null;


        CommonTree RULE62_tree=null;
        CommonTree HEAD63_tree=null;
        CommonTree BODY65_tree=null;
        CommonTree BODY66_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:233:2: ( ^( RULE ^( HEAD predicate ) ^( BODY body1= litlist ) ^( BODY (body2= litlist )? ) ) )
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:233:3: ^( RULE ^( HEAD predicate ) ^( BODY body1= litlist ) ^( BODY (body2= litlist )? ) )
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();
            _last = (CommonTree)input.LT(1);
            RULE62=(CommonTree)match(input,RULE,FOLLOW_RULE_in_rule582); 
            RULE62_tree = (CommonTree)adaptor.dupNode(RULE62);


            root_1 = (CommonTree)adaptor.becomeRoot(RULE62_tree, root_1);


            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_2 = _last;
            CommonTree _first_2 = null;
            CommonTree root_2 = (CommonTree)adaptor.nil();
            _last = (CommonTree)input.LT(1);
            HEAD63=(CommonTree)match(input,HEAD,FOLLOW_HEAD_in_rule585); 
            HEAD63_tree = (CommonTree)adaptor.dupNode(HEAD63);


            root_2 = (CommonTree)adaptor.becomeRoot(HEAD63_tree, root_2);


            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_predicate_in_rule587);
            predicate64=predicate();

            state._fsp--;

            adaptor.addChild(root_2, predicate64.getTree());


            match(input, Token.UP, null); 
            adaptor.addChild(root_1, root_2);
            _last = _save_last_2;
            }


            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_2 = _last;
            CommonTree _first_2 = null;
            CommonTree root_2 = (CommonTree)adaptor.nil();
            _last = (CommonTree)input.LT(1);
            BODY65=(CommonTree)match(input,BODY,FOLLOW_BODY_in_rule591); 
            BODY65_tree = (CommonTree)adaptor.dupNode(BODY65);


            root_2 = (CommonTree)adaptor.becomeRoot(BODY65_tree, root_2);


            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_litlist_in_rule595);
            body1=litlist();

            state._fsp--;

            adaptor.addChild(root_2, body1.getTree());


            match(input, Token.UP, null); 
            adaptor.addChild(root_1, root_2);
            _last = _save_last_2;
            }


            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_2 = _last;
            CommonTree _first_2 = null;
            CommonTree root_2 = (CommonTree)adaptor.nil();
            _last = (CommonTree)input.LT(1);
            BODY66=(CommonTree)match(input,BODY,FOLLOW_BODY_in_rule599); 
            BODY66_tree = (CommonTree)adaptor.dupNode(BODY66);


            root_2 = (CommonTree)adaptor.becomeRoot(BODY66_tree, root_2);


            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:233:62: (body2= litlist )?
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==EXPR||LA23_0==PREDICATE) ) {
                    alt23=1;
                }
                switch (alt23) {
                    case 1 :
                        // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:233:62: body2= litlist
                        {
                        _last = (CommonTree)input.LT(1);
                        pushFollow(FOLLOW_litlist_in_rule603);
                        body2=litlist();

                        state._fsp--;

                        adaptor.addChild(root_2, body2.getTree());


                        }
                        break;

                }


                match(input, Token.UP, null); 
            }
            adaptor.addChild(root_1, root_2);
            _last = _save_last_2;
            }


            match(input, Token.UP, null); 
            adaptor.addChild(root_0, root_1);
            _last = _save_last_1;
            }


               
            	    ArrayList body = new ArrayList((body1!=null?body1.result:null));
            	    RuleDecl rd = new RuleDecl((predicate64!=null?predicate64.result:null), body);
            	    if ((body2!=null?body2.result:null)==null) {
            	        retval.result = rd;
            	    } else {
            	        ArrayList<RuleDecl> list = new ArrayList<RuleDecl>();
            	        list.add((RuleDecl)rd);
            	        rd = new RuleDecl((predicate64!=null?predicate64.result:null), (body2!=null?body2.result:null));
            	        list.add((RuleDecl)rd);
            	        retval.result = list;
            	    }
            	    Variable.nextRule(); 
            	    Const.nextRule();
            	

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "rule"


    public static class litlist_return extends TreeRuleReturnScope {
        public List result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "litlist"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:250:1: litlist returns [List result] :l1= literal (l2= literal )* ;
    public final SociaLiteRule.litlist_return litlist() throws RecognitionException {
        SociaLiteRule.litlist_return retval = new SociaLiteRule.litlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.literal_return l1 =null;

        SociaLiteRule.literal_return l2 =null;



        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:251:2: (l1= literal (l2= literal )* )
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:251:4: l1= literal (l2= literal )*
            {
            root_0 = (CommonTree)adaptor.nil();


             retval.result = new ArrayList(); 

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_literal_in_litlist628);
            l1=literal();

            state._fsp--;

            adaptor.addChild(root_0, l1.getTree());



            	    for (Variable v: dotVars) {
            	        String root=v.name.substring(0, v.name.indexOf('.'));
            	        Variable rootVar=Variable.getVariable(root);
            	        AssignDotVar a;
            	        try { a=new AssignDotVar(v, rootVar); } 
            	        catch (InternalException e) { throw new RuntimeException(e);}
            	        retval.result.add(new Expr(a));
            	    }
            	    dotVars.clear();

            	    if ((l1!=null?l1.result:null) instanceof List) {
            	        for(Object o:(List)(l1!=null?l1.result:null)) 
            	            retval.result.add(o);
            	    } else { retval.result.add((l1!=null?l1.result:null)); }
            	

            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:268:2: (l2= literal )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==EXPR||LA24_0==PREDICATE) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:268:3: l2= literal
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_literal_in_litlist637);
            	    l2=literal();

            	    state._fsp--;

            	    adaptor.addChild(root_0, l2.getTree());



            	    	    for (Variable v: dotVars) {
            	    	        String root=v.name.substring(0, v.name.indexOf('.'));
            	    	        Variable rootVar=Variable.getVariable(root);
            	    	        AssignDotVar a;
            	    	        try { a=new AssignDotVar(v, rootVar); } 
            	    	        catch (InternalException e) { throw new RuntimeException(e);}
            	    	        retval.result.add(new Expr(a));
            	    	    }
            	    	    dotVars.clear();

            	    	    if ((l2!=null?l2.result:null) instanceof List) {
            	    	        for(Object o:(List)(l2!=null?l2.result:null)) 
            	    	            retval.result.add(o);
            	    	    } else { retval.result.add((l2!=null?l2.result:null)); }
            	    	

            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "litlist"


    public static class literal_return extends TreeRuleReturnScope {
        public Object result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "literal"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:285:1: literal returns [Object result] : ( ^( PREDICATE ( NOT )? predicate ) | ^( EXPR expr ) );
    public final SociaLiteRule.literal_return literal() throws RecognitionException {
        SociaLiteRule.literal_return retval = new SociaLiteRule.literal_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree PREDICATE67=null;
        CommonTree NOT68=null;
        CommonTree EXPR70=null;
        SociaLiteRule.predicate_return predicate69 =null;

        SociaLiteRule.expr_return expr71 =null;


        CommonTree PREDICATE67_tree=null;
        CommonTree NOT68_tree=null;
        CommonTree EXPR70_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:286:2: ( ^( PREDICATE ( NOT )? predicate ) | ^( EXPR expr ) )
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==PREDICATE) ) {
                alt26=1;
            }
            else if ( (LA26_0==EXPR) ) {
                alt26=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;

            }
            switch (alt26) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:286:3: ^( PREDICATE ( NOT )? predicate )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    PREDICATE67=(CommonTree)match(input,PREDICATE,FOLLOW_PREDICATE_in_literal655); 
                    PREDICATE67_tree = (CommonTree)adaptor.dupNode(PREDICATE67);


                    root_1 = (CommonTree)adaptor.becomeRoot(PREDICATE67_tree, root_1);


                    match(input, Token.DOWN, null); 
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:286:15: ( NOT )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==NOT) ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:286:15: NOT
                            {
                            _last = (CommonTree)input.LT(1);
                            NOT68=(CommonTree)match(input,NOT,FOLLOW_NOT_in_literal657); 
                            NOT68_tree = (CommonTree)adaptor.dupNode(NOT68);


                            adaptor.addChild(root_1, NOT68_tree);


                            }
                            break;

                    }


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_predicate_in_literal660);
                    predicate69=predicate();

                    state._fsp--;

                    adaptor.addChild(root_1, predicate69.getTree());


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }



                    	    retval.result = (predicate69!=null?predicate69.result:null);
                    	    if ((NOT68!=null?NOT68.getText():null) != null) {
                    	        ((Predicate)(predicate69!=null?predicate69.result:null)).setNegated(); 
                    	    }
                    	    if (!tmpVarAssigns.isEmpty()) {
                    	        retval.result = new ArrayList();
                    	        for (AssignOp op:tmpVarAssigns)
                    	            ((List)retval.result).add(new Expr(op));
                    	        ((List)retval.result).add((predicate69!=null?predicate69.result:null));
                    	        tmpVarAssigns.clear();
                    	    }
                    	

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:298:5: ^( EXPR expr )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    EXPR70=(CommonTree)match(input,EXPR,FOLLOW_EXPR_in_literal667); 
                    EXPR70_tree = (CommonTree)adaptor.dupNode(EXPR70);


                    root_1 = (CommonTree)adaptor.becomeRoot(EXPR70_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expr_in_literal669);
                    expr71=expr();

                    state._fsp--;

                    adaptor.addChild(root_1, expr71.getTree());


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }



                    	    Expr e = new Expr((Op)(expr71!=null?expr71.result:null));
                    	    retval.result = e;
                    	    if (!tmpVarAssigns.isEmpty()) {
                    	        retval.result = new ArrayList();
                    	        for (AssignOp op:tmpVarAssigns)
                    	            ((List)retval.result).add(new Expr(op));
                    	        ((List)retval.result).add(e);
                    	        tmpVarAssigns.clear();
                    	    }
                    	

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "literal"


    public static class predicate_return extends TreeRuleReturnScope {
        public Predicate result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "predicate"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:310:1: predicate returns [Predicate result] : ID ^( INDEX ( param )? ) paramlist ;
    public final SociaLiteRule.predicate_return predicate() throws RecognitionException {
        SociaLiteRule.predicate_return retval = new SociaLiteRule.predicate_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree ID72=null;
        CommonTree INDEX73=null;
        SociaLiteRule.param_return param74 =null;

        SociaLiteRule.paramlist_return paramlist75 =null;


        CommonTree ID72_tree=null;
        CommonTree INDEX73_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:311:2: ( ID ^( INDEX ( param )? ) paramlist )
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:311:4: ID ^( INDEX ( param )? ) paramlist
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            ID72=(CommonTree)match(input,ID,FOLLOW_ID_in_predicate687); 
            ID72_tree = (CommonTree)adaptor.dupNode(ID72);


            adaptor.addChild(root_0, ID72_tree);


            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();
            _last = (CommonTree)input.LT(1);
            INDEX73=(CommonTree)match(input,INDEX,FOLLOW_INDEX_in_predicate690); 
            INDEX73_tree = (CommonTree)adaptor.dupNode(INDEX73);


            root_1 = (CommonTree)adaptor.becomeRoot(INDEX73_tree, root_1);


            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:311:15: ( param )?
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==COMPOUND_EXPR||LA27_0==FUNCTION||LA27_0==TERM||(LA27_0 >= 67 && LA27_0 <= 68)||LA27_0==70||LA27_0==72||LA27_0==95) ) {
                    alt27=1;
                }
                switch (alt27) {
                    case 1 :
                        // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:311:15: param
                        {
                        _last = (CommonTree)input.LT(1);
                        pushFollow(FOLLOW_param_in_predicate692);
                        param74=param();

                        state._fsp--;

                        adaptor.addChild(root_1, param74.getTree());


                        }
                        break;

                }


                match(input, Token.UP, null); 
            }
            adaptor.addChild(root_0, root_1);
            _last = _save_last_1;
            }


            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_paramlist_in_predicate696);
            paramlist75=paramlist();

            state._fsp--;

            adaptor.addChild(root_0, paramlist75.getTree());


               TableDecl decl=tableDeclMap.get((ID72!=null?ID72.getText():null));
            	    if (decl==null) {
             	        throw new ParseException(getParser(), (ID72!=null?ID72.getLine():0)-1, (ID72!=null?ID72.getCharPositionInLine():0), "Table "+(ID72!=null?ID72.getText():null)+" is not declared");	 
            	    } else {
            	       try {
            	           decl.checkTypes((param74!=null?param74.result:null), (paramlist75!=null?paramlist75.result:null));
            	       } catch(InternalException e) {
            	           throw new ParseException(getParser(), (ID72!=null?ID72.getLine():0)-1, (ID72!=null?ID72.getCharPositionInLine():0), e.getMessage());
            	       }
            	    }
            	    retval.result = new Predicate((ID72!=null?ID72.getText():null), (param74!=null?param74.result:null), (paramlist75!=null?paramlist75.result:null));
            	

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "predicate"


    public static class function_return extends TreeRuleReturnScope {
        public Function result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "function"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:325:1: function returns [Function result] : ^( FUNC dotname ( fparamlist )? ) ;
    public final SociaLiteRule.function_return function() throws RecognitionException {
        SociaLiteRule.function_return retval = new SociaLiteRule.function_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree FUNC76=null;
        SociaLiteRule.dotname_return dotname77 =null;

        SociaLiteRule.fparamlist_return fparamlist78 =null;


        CommonTree FUNC76_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:326:2: ( ^( FUNC dotname ( fparamlist )? ) )
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:326:4: ^( FUNC dotname ( fparamlist )? )
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();
            _last = (CommonTree)input.LT(1);
            FUNC76=(CommonTree)match(input,FUNC,FOLLOW_FUNC_in_function715); 
            FUNC76_tree = (CommonTree)adaptor.dupNode(FUNC76);


            root_1 = (CommonTree)adaptor.becomeRoot(FUNC76_tree, root_1);


            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_dotname_in_function717);
            dotname77=dotname();

            state._fsp--;

            adaptor.addChild(root_1, dotname77.getTree());


            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:326:19: ( fparamlist )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==COMPOUND_EXPR||LA28_0==FUNCTION||LA28_0==TERM||(LA28_0 >= 67 && LA28_0 <= 68)||LA28_0==70||LA28_0==72||LA28_0==95) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:326:19: fparamlist
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_fparamlist_in_function719);
                    fparamlist78=fparamlist();

                    state._fsp--;

                    adaptor.addChild(root_1, fparamlist78.getTree());


                    }
                    break;

            }


            match(input, Token.UP, null); 
            adaptor.addChild(root_0, root_1);
            _last = _save_last_1;
            }



            	    retval.result = new Function((dotname77!=null?dotname77.result:null), (fparamlist78!=null?fparamlist78.result:null));
            	

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "function"


    public static class param_return extends TreeRuleReturnScope {
        public Object result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "param"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:330:1: param returns [Object result] : simpleExpr ;
    public final SociaLiteRule.param_return param() throws RecognitionException {
        SociaLiteRule.param_return retval = new SociaLiteRule.param_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.simpleExpr_return simpleExpr79 =null;



        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:331:2: ( simpleExpr )
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:331:4: simpleExpr
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_simpleExpr_in_param737);
            simpleExpr79=simpleExpr();

            state._fsp--;

            adaptor.addChild(root_0, simpleExpr79.getTree());



            	    if (tmpVarAssignHasFunc()) {
            	        throw new ParseException(getParser(), 
            	                        (simpleExpr79!=null?((CommonTree)simpleExpr79.tree):null).getLine()-1,
            	                        (simpleExpr79!=null?((CommonTree)simpleExpr79.tree):null).getCharPositionInLine()+1, 
            	                        "Cannot use functions with operators in a param list");
            	    }
            	    retval.result = (simpleExpr79!=null?simpleExpr79.result:null);
            	    /*if ((simpleExpr79!=null?simpleExpr79.result:null) instanceof TypeCast) {
            	        TypeCast cast=(TypeCast)retval.result;
            	        if (cast.arg instanceof Function) {
            	           throw new ParseException(getParser(), 
            	                        (simpleExpr79!=null?((CommonTree)simpleExpr79.tree):null).getLine()-1,
            	                        (simpleExpr79!=null?((CommonTree)simpleExpr79.tree):null).getCharPositionInLine()+1, 
            	                        "Cannot use type cast to aggregate functions");
            	        }
            	    }*/
            	    if ((simpleExpr79!=null?simpleExpr79.result:null) instanceof Function) {
            	        retval.result = new AggrFunction((Function)(simpleExpr79!=null?simpleExpr79.result:null)); 
            	    }
            	

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "param"


    public static class fparam_return extends TreeRuleReturnScope {
        public Object result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "fparam"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:352:1: fparam returns [Object result] : simpleExpr ;
    public final SociaLiteRule.fparam_return fparam() throws RecognitionException {
        SociaLiteRule.fparam_return retval = new SociaLiteRule.fparam_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.simpleExpr_return simpleExpr80 =null;



        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:353:2: ( simpleExpr )
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:353:4: simpleExpr
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_simpleExpr_in_fparam751);
            simpleExpr80=simpleExpr();

            state._fsp--;

            adaptor.addChild(root_0, simpleExpr80.getTree());


             retval.result = (simpleExpr80!=null?simpleExpr80.result:null); 

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "fparam"


    public static class paramlist_return extends TreeRuleReturnScope {
        public List result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "paramlist"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:355:1: paramlist returns [List result] :p1= param (p2= param )* ;
    public final SociaLiteRule.paramlist_return paramlist() throws RecognitionException {
        SociaLiteRule.paramlist_return retval = new SociaLiteRule.paramlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.param_return p1 =null;

        SociaLiteRule.param_return p2 =null;



        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:356:2: (p1= param (p2= param )* )
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:356:3: p1= param (p2= param )*
            {
            root_0 = (CommonTree)adaptor.nil();


             retval.result = new ArrayList(); 

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_param_in_paramlist770);
            p1=param();

            state._fsp--;

            adaptor.addChild(root_0, p1.getTree());


             
            	    if (isSimpleIntValue((p1!=null?p1.result:null))) {
            	        retval.result.add(new Const(getSimpleIntValue((p1!=null?p1.result:null))));
            	    } else if (((p1!=null?p1.result:null) instanceof BinOp)||((p1!=null?p1.result:null) instanceof UnaryOp)) {
            	        Variable tmpVar = addTmpVarAssign((p1!=null?p1.result:null));
            	        retval.result.add(tmpVar);
            	    } else if ((p1!=null?p1.result:null) instanceof Op) {
            	        throw new ParseException(getParser(), 
            	                        (p1!=null?((CommonTree)p1.tree):null).getLine()-1, (p1!=null?((CommonTree)p1.tree):null).getCharPositionInLine()+1, 
            	                        "Cannot use "+(p1!=null?p1.result:null)+" in parameter list"); 
            	    } else { retval.result.add((p1!=null?p1.result:null)); }
            	

            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:369:2: (p2= param )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==COMPOUND_EXPR||LA29_0==FUNCTION||LA29_0==TERM||(LA29_0 >= 67 && LA29_0 <= 68)||LA29_0==70||LA29_0==72||LA29_0==95) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:369:3: p2= param
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_param_in_paramlist778);
            	    p2=param();

            	    state._fsp--;

            	    adaptor.addChild(root_0, p2.getTree());


            	     
            	    	    if (isSimpleIntValue((p2!=null?p2.result:null))) {
            	    	        retval.result.add(new Const(getSimpleIntValue((p2!=null?p2.result:null))));
            	    	    } else if (((p2!=null?p2.result:null) instanceof BinOp)||((p2!=null?p2.result:null) instanceof UnaryOp)) {
            	    	        Variable tmpVar = addTmpVarAssign((p2!=null?p2.result:null));
            	    	        retval.result.add(tmpVar);
            	    	    } else if ((p2!=null?p2.result:null) instanceof Op) {
            	    	        throw new ParseException(getParser(),
            	    	                        (p2!=null?((CommonTree)p2.tree):null).getLine()-1, (p2!=null?((CommonTree)p2.tree):null).getCharPositionInLine()+1, 
            	    	                        "Cannot use "+(p2!=null?p2.result:null)+" in parameter list"); 
            	    	    } else { retval.result.add((p2!=null?p2.result:null)); }
            	    	

            	    }
            	    break;

            	default :
            	    break loop29;
                }
            } while (true);


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "paramlist"


    public static class fparamlist_return extends TreeRuleReturnScope {
        public List result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "fparamlist"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:383:1: fparamlist returns [List result] :p1= fparam (p2= fparam )* ;
    public final SociaLiteRule.fparamlist_return fparamlist() throws RecognitionException {
        SociaLiteRule.fparamlist_return retval = new SociaLiteRule.fparamlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.fparam_return p1 =null;

        SociaLiteRule.fparam_return p2 =null;



        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:384:2: (p1= fparam (p2= fparam )* )
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:384:3: p1= fparam (p2= fparam )*
            {
            root_0 = (CommonTree)adaptor.nil();


             retval.result = new ArrayList(); 

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_fparam_in_fparamlist801);
            p1=fparam();

            state._fsp--;

            adaptor.addChild(root_0, p1.getTree());


             
            	    if (((p1!=null?p1.result:null) instanceof BinOp)||((p1!=null?p1.result:null) instanceof UnaryOp)||((p1!=null?p1.result:null) instanceof Function)) {
            	        Variable tmpVar = addTmpVarAssign((p1!=null?p1.result:null));
            	        retval.result.add(tmpVar);
            	    } else if ((p1!=null?p1.result:null) instanceof Op) {
            	        throw new ParseException(getParser(), 
            	                        (p1!=null?((CommonTree)p1.tree):null).getLine()-1, (p1!=null?((CommonTree)p1.tree):null).getCharPositionInLine()+1, 
            	                        "Cannot use "+(p1!=null?p1.result:null)+" in parameter list"); 
            	    } else { retval.result.add((p1!=null?p1.result:null)); }
            	

            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:395:2: (p2= fparam )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==COMPOUND_EXPR||LA30_0==FUNCTION||LA30_0==TERM||(LA30_0 >= 67 && LA30_0 <= 68)||LA30_0==70||LA30_0==72||LA30_0==95) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:395:3: p2= fparam
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_fparam_in_fparamlist809);
            	    p2=fparam();

            	    state._fsp--;

            	    adaptor.addChild(root_0, p2.getTree());


            	     
            	    	    if (((p2!=null?p2.result:null) instanceof BinOp)||((p2!=null?p2.result:null) instanceof UnaryOp)||((p2!=null?p2.result:null) instanceof Function)) {
            	    	        Variable tmpVar = addTmpVarAssign((p2!=null?p2.result:null));
            	    	        retval.result.add(tmpVar);
            	    	    } else if ((p2!=null?p2.result:null) instanceof Op) {
            	    	        throw new ParseException(getParser(),
            	    	                        (p2!=null?((CommonTree)p2.tree):null).getLine()-1, (p2!=null?((CommonTree)p2.tree):null).getCharPositionInLine()+1, 
            	    	                        "Cannot use "+(p2!=null?p2.result:null)+" in parameter list"); 
            	    	    } else { retval.result.add((p2!=null?p2.result:null)); }
            	    	

            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "fparamlist"


    public static class term_return extends TreeRuleReturnScope {
        public Object result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "term"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:408:1: term returns [Object result] : ( ^( T_INT INT ) | ^( T_FLOAT FLOAT ) | ^( T_STR STRING ) | ^( T_UTF8 UTF8 ) | ^( T_VAR dotname ) );
    public final SociaLiteRule.term_return term() throws RecognitionException {
        SociaLiteRule.term_return retval = new SociaLiteRule.term_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree T_INT81=null;
        CommonTree INT82=null;
        CommonTree T_FLOAT83=null;
        CommonTree FLOAT84=null;
        CommonTree T_STR85=null;
        CommonTree STRING86=null;
        CommonTree T_UTF887=null;
        CommonTree UTF888=null;
        CommonTree T_VAR89=null;
        SociaLiteRule.dotname_return dotname90 =null;


        CommonTree T_INT81_tree=null;
        CommonTree INT82_tree=null;
        CommonTree T_FLOAT83_tree=null;
        CommonTree FLOAT84_tree=null;
        CommonTree T_STR85_tree=null;
        CommonTree STRING86_tree=null;
        CommonTree T_UTF887_tree=null;
        CommonTree UTF888_tree=null;
        CommonTree T_VAR89_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:409:2: ( ^( T_INT INT ) | ^( T_FLOAT FLOAT ) | ^( T_STR STRING ) | ^( T_UTF8 UTF8 ) | ^( T_VAR dotname ) )
            int alt31=5;
            switch ( input.LA(1) ) {
            case T_INT:
                {
                alt31=1;
                }
                break;
            case T_FLOAT:
                {
                alt31=2;
                }
                break;
            case T_STR:
                {
                alt31=3;
                }
                break;
            case T_UTF8:
                {
                alt31=4;
                }
                break;
            case T_VAR:
                {
                alt31=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;

            }

            switch (alt31) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:409:3: ^( T_INT INT )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    T_INT81=(CommonTree)match(input,T_INT,FOLLOW_T_INT_in_term829); 
                    T_INT81_tree = (CommonTree)adaptor.dupNode(T_INT81);


                    root_1 = (CommonTree)adaptor.becomeRoot(T_INT81_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    INT82=(CommonTree)match(input,INT,FOLLOW_INT_in_term831); 
                    INT82_tree = (CommonTree)adaptor.dupNode(INT82);


                    adaptor.addChild(root_1, INT82_tree);


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }



                    	    if ((INT82!=null?INT82.getText():null).endsWith("l") || (INT82!=null?INT82.getText():null).endsWith("L")) {
                    	        Long v = new Long(Long.parseLong((INT82!=null?INT82.getText():null).substring(0, (INT82!=null?INT82.getText():null).length()-1)));
                    	        retval.result = new Const(v);
                    	    } else {
                    	        Integer v = new Integer(Integer.parseInt((INT82!=null?INT82.getText():null)));
                    	        retval.result = new Const(v);
                    	    }
                    	 

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:417:6: ^( T_FLOAT FLOAT )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    T_FLOAT83=(CommonTree)match(input,T_FLOAT,FOLLOW_T_FLOAT_in_term839); 
                    T_FLOAT83_tree = (CommonTree)adaptor.dupNode(T_FLOAT83);


                    root_1 = (CommonTree)adaptor.becomeRoot(T_FLOAT83_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    FLOAT84=(CommonTree)match(input,FLOAT,FOLLOW_FLOAT_in_term841); 
                    FLOAT84_tree = (CommonTree)adaptor.dupNode(FLOAT84);


                    adaptor.addChild(root_1, FLOAT84_tree);


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }



                     	    if ((FLOAT84!=null?FLOAT84.getText():null).endsWith("f") || (FLOAT84!=null?FLOAT84.getText():null).endsWith("F")) {
                     	        Float v = new Float(Float.parseFloat((FLOAT84!=null?FLOAT84.getText():null).substring(0, (FLOAT84!=null?FLOAT84.getText():null).length()-1)));
                     	        retval.result = new Const(v);
                     	    } else if ((FLOAT84!=null?FLOAT84.getText():null).endsWith("d") || (FLOAT84!=null?FLOAT84.getText():null).endsWith("D")) {
                     	        Double v = new Double(Double.parseDouble((FLOAT84!=null?FLOAT84.getText():null).substring(0, (FLOAT84!=null?FLOAT84.getText():null).length()-1)));
                     	        retval.result = new Const(v);
                     	    } else {
                         	        Double v = new Double(Double.parseDouble((FLOAT84!=null?FLOAT84.getText():null)));
                         	        retval.result = new Const(v);
                     	    }
                    	

                    }
                    break;
                case 3 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:429:3: ^( T_STR STRING )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    T_STR85=(CommonTree)match(input,T_STR,FOLLOW_T_STR_in_term850); 
                    T_STR85_tree = (CommonTree)adaptor.dupNode(T_STR85);


                    root_1 = (CommonTree)adaptor.becomeRoot(T_STR85_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    STRING86=(CommonTree)match(input,STRING,FOLLOW_STRING_in_term852); 
                    STRING86_tree = (CommonTree)adaptor.dupNode(STRING86);


                    adaptor.addChild(root_1, STRING86_tree);


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }


                    String v = new String((STRING86!=null?STRING86.getText():null).substring(1, (STRING86!=null?STRING86.getText():null).length()-1)); retval.result = new Const(v); 

                    }
                    break;
                case 4 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:430:3: ^( T_UTF8 UTF8 )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    T_UTF887=(CommonTree)match(input,T_UTF8,FOLLOW_T_UTF8_in_term861); 
                    T_UTF887_tree = (CommonTree)adaptor.dupNode(T_UTF887);


                    root_1 = (CommonTree)adaptor.becomeRoot(T_UTF887_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    UTF888=(CommonTree)match(input,UTF8,FOLLOW_UTF8_in_term863); 
                    UTF888_tree = (CommonTree)adaptor.dupNode(UTF888);


                    adaptor.addChild(root_1, UTF888_tree);


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }


                     Utf8 v =  new Utf8((UTF888!=null?UTF888.getText():null).substring(2, (UTF888!=null?UTF888.getText():null).length()-1)); retval.result = new Const(v); 

                    }
                    break;
                case 5 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:431:3: ^( T_VAR dotname )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    T_VAR89=(CommonTree)match(input,T_VAR,FOLLOW_T_VAR_in_term872); 
                    T_VAR89_tree = (CommonTree)adaptor.dupNode(T_VAR89);


                    root_1 = (CommonTree)adaptor.becomeRoot(T_VAR89_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_dotname_in_term874);
                    dotname90=dotname();

                    state._fsp--;

                    adaptor.addChild(root_1, dotname90.getTree());


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }



                    	    retval.result = Variable.getVariable((dotname90!=null?dotname90.result:null));
                    	    if ((dotname90!=null?dotname90.result:null).indexOf('.')>=0) {
                    	        dotVars.add((Variable)retval.result);
                    	    }
                    	

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "term"


    public static class expr_return extends TreeRuleReturnScope {
        public Object result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "expr"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:437:1: expr returns [Object result] : ( ^(cmpStr= ( '<' | '<=' | '>' | '>=' | '==' | '!=' ) e1= simpleExpr e2= simpleExpr ) | ^(eq= '=' e1= simpleExpr e2= simpleExpr ) | ^( MULTI_ASSIGN varlist function (c= cast )? ) );
    public final SociaLiteRule.expr_return expr() throws RecognitionException {
        SociaLiteRule.expr_return retval = new SociaLiteRule.expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree cmpStr=null;
        CommonTree eq=null;
        CommonTree MULTI_ASSIGN91=null;
        SociaLiteRule.simpleExpr_return e1 =null;

        SociaLiteRule.simpleExpr_return e2 =null;

        SociaLiteRule.cast_return c =null;

        SociaLiteRule.varlist_return varlist92 =null;

        SociaLiteRule.function_return function93 =null;


        CommonTree cmpStr_tree=null;
        CommonTree eq_tree=null;
        CommonTree MULTI_ASSIGN91_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:438:2: ( ^(cmpStr= ( '<' | '<=' | '>' | '>=' | '==' | '!=' ) e1= simpleExpr e2= simpleExpr ) | ^(eq= '=' e1= simpleExpr e2= simpleExpr ) | ^( MULTI_ASSIGN varlist function (c= cast )? ) )
            int alt33=3;
            switch ( input.LA(1) ) {
            case 63:
            case 76:
            case 77:
            case 79:
            case 80:
            case 81:
                {
                alt33=1;
                }
                break;
            case 78:
                {
                alt33=2;
                }
                break;
            case MULTI_ASSIGN:
                {
                alt33=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 33, 0, input);

                throw nvae;

            }

            switch (alt33) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:438:3: ^(cmpStr= ( '<' | '<=' | '>' | '>=' | '==' | '!=' ) e1= simpleExpr e2= simpleExpr )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    cmpStr=(CommonTree)input.LT(1);

                    if ( input.LA(1)==63||(input.LA(1) >= 76 && input.LA(1) <= 77)||(input.LA(1) >= 79 && input.LA(1) <= 81) ) {
                        input.consume();
                        cmpStr_tree = (CommonTree)adaptor.dupNode(cmpStr);


                        root_1 = (CommonTree)adaptor.becomeRoot(cmpStr_tree, root_1);

                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_simpleExpr_in_expr907);
                    e1=simpleExpr();

                    state._fsp--;

                    adaptor.addChild(root_1, e1.getTree());


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_simpleExpr_in_expr911);
                    e2=simpleExpr();

                    state._fsp--;

                    adaptor.addChild(root_1, e2.getTree());


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }



                    	    try { retval.result = new CmpOp((cmpStr!=null?cmpStr.getText():null), (e1!=null?e1.result:null), (e2!=null?e2.result:null)); }
                    	    catch (InternalException e) {
                    	        throw new ParseException(getParser(), (cmpStr!=null?cmpStr.getLine():0)-1, (cmpStr!=null?cmpStr.getCharPositionInLine():0)+1, e.getMessage()); 
                    	    }
                    	

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:443:5: ^(eq= '=' e1= simpleExpr e2= simpleExpr )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    eq=(CommonTree)match(input,78,FOLLOW_78_in_expr920); 
                    eq_tree = (CommonTree)adaptor.dupNode(eq);


                    root_1 = (CommonTree)adaptor.becomeRoot(eq_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_simpleExpr_in_expr924);
                    e1=simpleExpr();

                    state._fsp--;

                    adaptor.addChild(root_1, e1.getTree());


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_simpleExpr_in_expr928);
                    e2=simpleExpr();

                    state._fsp--;

                    adaptor.addChild(root_1, e2.getTree());


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }



                    	    if (!((e1!=null?e1.result:null) instanceof Variable)) {
                    	        throw new ParseException(getParser(), (eq!=null?eq.getLine():0)-1, (eq!=null?eq.getCharPositionInLine():0)+1, "Invalid assignment to "+(e1!=null?e1.result:null)); 
                    	    }
                    	    TypeCast cast = null;
                    	    Object a1=(e1!=null?e1.result:null), a2=(e2!=null?e2.result:null);
                    	    if ((e2!=null?e2.result:null) instanceof TypeCast) {
                    	        cast = (TypeCast)(e2!=null?e2.result:null);
                    	        a2 = cast.arg;
                    	        if (a2 instanceof TypeCast) throw new ParseException(getParser(), (eq!=null?eq.getLine():0)-1, (eq!=null?eq.getCharPositionInLine():0)+1, "Invalid type cast"); 
                    	    }
                    	    try { retval.result = new AssignOp(a1, cast, a2); }
                    	    catch (InternalException e) {
                    	        throw new ParseException(getParser(), (eq!=null?eq.getLine():0)-1, (eq!=null?eq.getCharPositionInLine():0)+1, e.getMessage()); 
                    	    }
                    	

                    }
                    break;
                case 3 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:458:6: ^( MULTI_ASSIGN varlist function (c= cast )? )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    MULTI_ASSIGN91=(CommonTree)match(input,MULTI_ASSIGN,FOLLOW_MULTI_ASSIGN_in_expr936); 
                    MULTI_ASSIGN91_tree = (CommonTree)adaptor.dupNode(MULTI_ASSIGN91);


                    root_1 = (CommonTree)adaptor.becomeRoot(MULTI_ASSIGN91_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_varlist_in_expr938);
                    varlist92=varlist();

                    state._fsp--;

                    adaptor.addChild(root_1, varlist92.getTree());


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_function_in_expr940);
                    function93=function();

                    state._fsp--;

                    adaptor.addChild(root_1, function93.getTree());


                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:458:39: (c= cast )?
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==ID||(LA32_0 >= 83 && LA32_0 <= 84)||LA32_0==88||LA32_0==90||(LA32_0 >= 93 && LA32_0 <= 94)) ) {
                        alt32=1;
                    }
                    switch (alt32) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:458:39: c= cast
                            {
                            _last = (CommonTree)input.LT(1);
                            pushFollow(FOLLOW_cast_in_expr944);
                            c=cast();

                            state._fsp--;

                            adaptor.addChild(root_1, c.getTree());


                            }
                            break;

                    }


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }



                    	    try { 
                    	        TypeCast typecast=null;
                    	        if ((c!=null?c.result:null)!=null) {
                    	            int line=(c!=null?((CommonTree)c.tree):null).getLine()-1, pos=(c!=null?((CommonTree)c.tree):null).getCharPositionInLine()+1;
                    	            throw new ParseException(getParser(), line, pos, "Invalid type cast"); 
                    	        }
                    	        retval.result = new AssignOp((varlist92!=null?varlist92.result:null), typecast, (function93!=null?function93.result:null)); 
                    	    } catch (InternalException e) {
                    	        int line=(varlist92!=null?((CommonTree)varlist92.tree):null).getLine()-1, pos=(varlist92!=null?((CommonTree)varlist92.tree):null).getCharPositionInLine()+1;
                    	        throw new ParseException(getParser(), line, pos, e.getMessage()); 
                    	    }
                    	

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "expr"


    public static class simpleExpr_return extends TreeRuleReturnScope {
        public Object result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "simpleExpr"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:471:1: simpleExpr returns [Object result] : ( multExpr | ^(op= ( '+' | '-' ) e1= simpleExpr e2= simpleExpr ) );
    public final SociaLiteRule.simpleExpr_return simpleExpr() throws RecognitionException {
        SociaLiteRule.simpleExpr_return retval = new SociaLiteRule.simpleExpr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree op=null;
        SociaLiteRule.simpleExpr_return e1 =null;

        SociaLiteRule.simpleExpr_return e2 =null;

        SociaLiteRule.multExpr_return multExpr94 =null;


        CommonTree op_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:472:2: ( multExpr | ^(op= ( '+' | '-' ) e1= simpleExpr e2= simpleExpr ) )
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==COMPOUND_EXPR||LA34_0==FUNCTION||LA34_0==TERM||LA34_0==67||LA34_0==72||LA34_0==95) ) {
                alt34=1;
            }
            else if ( (LA34_0==68||LA34_0==70) ) {
                alt34=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 34, 0, input);

                throw nvae;

            }
            switch (alt34) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:472:3: multExpr
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_multExpr_in_simpleExpr959);
                    multExpr94=multExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, multExpr94.getTree());


                    retval.result = (multExpr94!=null?multExpr94.result:null);

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:473:3: ^(op= ( '+' | '-' ) e1= simpleExpr e2= simpleExpr )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    op=(CommonTree)input.LT(1);

                    if ( input.LA(1)==68||input.LA(1)==70 ) {
                        input.consume();
                        op_tree = (CommonTree)adaptor.dupNode(op);


                        root_1 = (CommonTree)adaptor.becomeRoot(op_tree, root_1);

                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_simpleExpr_in_simpleExpr976);
                    e1=simpleExpr();

                    state._fsp--;

                    adaptor.addChild(root_1, e1.getTree());


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_simpleExpr_in_simpleExpr980);
                    e2=simpleExpr();

                    state._fsp--;

                    adaptor.addChild(root_1, e2.getTree());


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }



                    	    Object arg1=(e1!=null?e1.result:null), arg2=(e2!=null?e2.result:null);
                    	    if (arg1 instanceof Function) arg1=addTmpVarAssign(arg1);
                    	    if (arg2 instanceof Function) arg2=addTmpVarAssign(arg2); 

                    	    try { retval.result = new BinOp((op!=null?op.getText():null), arg1, arg2); }
                    	    catch (InternalException e) {
                    	        throw new ParseException(getParser(), (op!=null?op.getLine():0)-1, (op!=null?op.getCharPositionInLine():0)+1, e.getMessage()); 
                    	    }
                    	

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "simpleExpr"


    public static class multExpr_return extends TreeRuleReturnScope {
        public Object result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "multExpr"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:483:1: multExpr returns [Object result] : ( exprValue | ^(op= ( '*' | '/' | 'mod' ) v1= multExpr v2= multExpr ) );
    public final SociaLiteRule.multExpr_return multExpr() throws RecognitionException {
        SociaLiteRule.multExpr_return retval = new SociaLiteRule.multExpr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree op=null;
        SociaLiteRule.multExpr_return v1 =null;

        SociaLiteRule.multExpr_return v2 =null;

        SociaLiteRule.exprValue_return exprValue95 =null;


        CommonTree op_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:484:2: ( exprValue | ^(op= ( '*' | '/' | 'mod' ) v1= multExpr v2= multExpr ) )
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==COMPOUND_EXPR||LA35_0==FUNCTION||LA35_0==TERM) ) {
                alt35=1;
            }
            else if ( (LA35_0==67||LA35_0==72||LA35_0==95) ) {
                alt35=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;

            }
            switch (alt35) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:484:4: exprValue
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_exprValue_in_multExpr995);
                    exprValue95=exprValue();

                    state._fsp--;

                    adaptor.addChild(root_0, exprValue95.getTree());


                     retval.result = (exprValue95!=null?exprValue95.result:null); 

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:485:3: ^(op= ( '*' | '/' | 'mod' ) v1= multExpr v2= multExpr )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    op=(CommonTree)input.LT(1);

                    if ( input.LA(1)==67||input.LA(1)==72||input.LA(1)==95 ) {
                        input.consume();
                        op_tree = (CommonTree)adaptor.dupNode(op);


                        root_1 = (CommonTree)adaptor.becomeRoot(op_tree, root_1);

                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_multExpr_in_multExpr1014);
                    v1=multExpr();

                    state._fsp--;

                    adaptor.addChild(root_1, v1.getTree());


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_multExpr_in_multExpr1018);
                    v2=multExpr();

                    state._fsp--;

                    adaptor.addChild(root_1, v2.getTree());


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }



                    	    Object arg1=(v1!=null?v1.result:null), arg2=(v2!=null?v2.result:null);
                    	    if (arg1 instanceof Function) arg1=addTmpVarAssign(arg1);
                    	    if (arg2 instanceof Function) arg2=addTmpVarAssign(arg2); 

                    	    try { retval.result = new BinOp((op!=null?op.getText():null), arg1, arg2); }
                    	    catch (InternalException e) {
                    	        throw new ParseException(getParser(), (op!=null?op.getLine():0)-1, (op!=null?op.getCharPositionInLine():0)+1, e.getMessage()); 
                    	    }
                    	

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "multExpr"


    public static class compExpr_return extends TreeRuleReturnScope {
        public Object result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "compExpr"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:495:1: compExpr returns [Object result] : e1= simpleExpr ;
    public final SociaLiteRule.compExpr_return compExpr() throws RecognitionException {
        SociaLiteRule.compExpr_return retval = new SociaLiteRule.compExpr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.simpleExpr_return e1 =null;



        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:496:2: (e1= simpleExpr )
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:496:4: e1= simpleExpr
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_simpleExpr_in_compExpr1035);
            e1=simpleExpr();

            state._fsp--;

            adaptor.addChild(root_0, e1.getTree());


            retval.result =(e1!=null?e1.result:null);

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "compExpr"


    public static class varlist_return extends TreeRuleReturnScope {
        public Object result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "varlist"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:498:1: varlist returns [Object result] : id1= dotname (id2= dotname )+ ;
    public final SociaLiteRule.varlist_return varlist() throws RecognitionException {
        SociaLiteRule.varlist_return retval = new SociaLiteRule.varlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.dotname_return id1 =null;

        SociaLiteRule.dotname_return id2 =null;



        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:499:2: (id1= dotname (id2= dotname )+ )
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:499:3: id1= dotname (id2= dotname )+
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_dotname_in_varlist1055);
            id1=dotname();

            state._fsp--;

            adaptor.addChild(root_0, id1.getTree());


             
            	    List<Variable> vars = new ArrayList<Variable>();
            	    vars.add(Variable.getVariable((id1!=null?id1.result:null))); retval.result = vars; 

            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:502:2: (id2= dotname )+
            int cnt36=0;
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==ID) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:502:3: id2= dotname
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_dotname_in_varlist1062);
            	    id2=dotname();

            	    state._fsp--;

            	    adaptor.addChild(root_0, id2.getTree());


            	     
            	    	    vars.add(Variable.getVariable((id2!=null?id2.result:null)));

            	    }
            	    break;

            	default :
            	    if ( cnt36 >= 1 ) break loop36;
                        EarlyExitException eee =
                            new EarlyExitException(36, input);
                        throw eee;
                }
                cnt36++;
            } while (true);


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "varlist"


    public static class exprValue_return extends TreeRuleReturnScope {
        public Object result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "exprValue"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:505:1: exprValue returns [Object result] : ( ^( TERM term (neg= '-' )? (c= cast )? ) | ^( FUNCTION function (c= cast )? ) | ^( COMPOUND_EXPR compExpr (c= cast )? ) );
    public final SociaLiteRule.exprValue_return exprValue() throws RecognitionException {
        SociaLiteRule.exprValue_return retval = new SociaLiteRule.exprValue_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree neg=null;
        CommonTree TERM96=null;
        CommonTree FUNCTION98=null;
        CommonTree COMPOUND_EXPR100=null;
        SociaLiteRule.cast_return c =null;

        SociaLiteRule.term_return term97 =null;

        SociaLiteRule.function_return function99 =null;

        SociaLiteRule.compExpr_return compExpr101 =null;


        CommonTree neg_tree=null;
        CommonTree TERM96_tree=null;
        CommonTree FUNCTION98_tree=null;
        CommonTree COMPOUND_EXPR100_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:506:2: ( ^( TERM term (neg= '-' )? (c= cast )? ) | ^( FUNCTION function (c= cast )? ) | ^( COMPOUND_EXPR compExpr (c= cast )? ) )
            int alt41=3;
            switch ( input.LA(1) ) {
            case TERM:
                {
                alt41=1;
                }
                break;
            case FUNCTION:
                {
                alt41=2;
                }
                break;
            case COMPOUND_EXPR:
                {
                alt41=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 41, 0, input);

                throw nvae;

            }

            switch (alt41) {
                case 1 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:506:3: ^( TERM term (neg= '-' )? (c= cast )? )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    TERM96=(CommonTree)match(input,TERM,FOLLOW_TERM_in_exprValue1079); 
                    TERM96_tree = (CommonTree)adaptor.dupNode(TERM96);


                    root_1 = (CommonTree)adaptor.becomeRoot(TERM96_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_term_in_exprValue1081);
                    term97=term();

                    state._fsp--;

                    adaptor.addChild(root_1, term97.getTree());


                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:506:15: (neg= '-' )?
                    int alt37=2;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0==70) ) {
                        alt37=1;
                    }
                    switch (alt37) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:506:16: neg= '-'
                            {
                            _last = (CommonTree)input.LT(1);
                            neg=(CommonTree)match(input,70,FOLLOW_70_in_exprValue1086); 
                            neg_tree = (CommonTree)adaptor.dupNode(neg);


                            adaptor.addChild(root_1, neg_tree);


                            }
                            break;

                    }


                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:506:27: (c= cast )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==ID||(LA38_0 >= 83 && LA38_0 <= 84)||LA38_0==88||LA38_0==90||(LA38_0 >= 93 && LA38_0 <= 94)) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:506:27: c= cast
                            {
                            _last = (CommonTree)input.LT(1);
                            pushFollow(FOLLOW_cast_in_exprValue1092);
                            c=cast();

                            state._fsp--;

                            adaptor.addChild(root_1, c.getTree());


                            }
                            break;

                    }


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }



                    	    retval.result = (term97!=null?term97.result:null);
                    	    if ((c!=null?c.result:null) != null) {
                    	        TypeCast cast = new TypeCast((c!=null?c.result:null), retval.result);
                    	        retval.result = cast;
                    	    }
                    	    if ((neg!=null?neg.getText():null) != null) {
                    	        if ((term97!=null?term97.result:null) instanceof Const) {
                    	            ((Const)(term97!=null?term97.result:null)).negate();
                    	        } else {
                    	            try { retval.result = new UnaryMinus(retval.result); }
                    	            catch (InternalException e) {
                    	                throw new ParseException(getParser(), (neg!=null?neg.getLine():0)-1, (neg!=null?neg.getCharPositionInLine():0)+1, e.getMessage()); 
                    	            }
                    	        }
                    	    }
                    	

                    }
                    break;
                case 2 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:523:3: ^( FUNCTION function (c= cast )? )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    FUNCTION98=(CommonTree)match(input,FUNCTION,FOLLOW_FUNCTION_in_exprValue1101); 
                    FUNCTION98_tree = (CommonTree)adaptor.dupNode(FUNCTION98);


                    root_1 = (CommonTree)adaptor.becomeRoot(FUNCTION98_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_function_in_exprValue1103);
                    function99=function();

                    state._fsp--;

                    adaptor.addChild(root_1, function99.getTree());


                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:523:24: (c= cast )?
                    int alt39=2;
                    int LA39_0 = input.LA(1);

                    if ( (LA39_0==ID||(LA39_0 >= 83 && LA39_0 <= 84)||LA39_0==88||LA39_0==90||(LA39_0 >= 93 && LA39_0 <= 94)) ) {
                        alt39=1;
                    }
                    switch (alt39) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:523:24: c= cast
                            {
                            _last = (CommonTree)input.LT(1);
                            pushFollow(FOLLOW_cast_in_exprValue1107);
                            c=cast();

                            state._fsp--;

                            adaptor.addChild(root_1, c.getTree());


                            }
                            break;

                    }


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }


                     
                    	    retval.result = (function99!=null?function99.result:null); 
                    	    if ((c!=null?c.result:null) != null) {
                    	        Object tmpvar = addTmpVarAssign((function99!=null?function99.result:null));
                    	        TypeCast cast = new TypeCast((c!=null?c.result:null), tmpvar);
                    	        retval.result = cast;
                    	    }
                    	

                    }
                    break;
                case 3 :
                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:531:3: ^( COMPOUND_EXPR compExpr (c= cast )? )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    COMPOUND_EXPR100=(CommonTree)match(input,COMPOUND_EXPR,FOLLOW_COMPOUND_EXPR_in_exprValue1116); 
                    COMPOUND_EXPR100_tree = (CommonTree)adaptor.dupNode(COMPOUND_EXPR100);


                    root_1 = (CommonTree)adaptor.becomeRoot(COMPOUND_EXPR100_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_compExpr_in_exprValue1118);
                    compExpr101=compExpr();

                    state._fsp--;

                    adaptor.addChild(root_1, compExpr101.getTree());


                    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:531:29: (c= cast )?
                    int alt40=2;
                    int LA40_0 = input.LA(1);

                    if ( (LA40_0==ID||(LA40_0 >= 83 && LA40_0 <= 84)||LA40_0==88||LA40_0==90||(LA40_0 >= 93 && LA40_0 <= 94)) ) {
                        alt40=1;
                    }
                    switch (alt40) {
                        case 1 :
                            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:531:29: c= cast
                            {
                            _last = (CommonTree)input.LT(1);
                            pushFollow(FOLLOW_cast_in_exprValue1122);
                            c=cast();

                            state._fsp--;

                            adaptor.addChild(root_1, c.getTree());


                            }
                            break;

                    }


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }


                     
                    	    retval.result = (compExpr101!=null?compExpr101.result:null);
                    	    if ((c!=null?c.result:null) != null) {
                    	        TypeCast cast=new TypeCast((c!=null?c.result:null), retval.result);
                    	        retval.result = cast;
                    	    }
                    	

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "exprValue"


    public static class cast_return extends TreeRuleReturnScope {
        public Class result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "cast"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:539:1: cast returns [Class result] : type ;
    public final SociaLiteRule.cast_return cast() throws RecognitionException {
        SociaLiteRule.cast_return retval = new SociaLiteRule.cast_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.type_return type102 =null;



        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:540:2: ( type )
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:540:3: type
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_type_in_cast1139);
            type102=type();

            state._fsp--;

            adaptor.addChild(root_0, type102.getTree());


            retval.result = (type102!=null?type102.result:null); 

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "cast"


    public static class dotname_return extends TreeRuleReturnScope {
        public String result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "dotname"
    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:542:1: dotname returns [String result] : ID ( DOT_ID )* ;
    public final SociaLiteRule.dotname_return dotname() throws RecognitionException {
        SociaLiteRule.dotname_return retval = new SociaLiteRule.dotname_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree ID103=null;
        CommonTree DOT_ID104=null;

        CommonTree ID103_tree=null;
        CommonTree DOT_ID104_tree=null;

        try {
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:543:2: ( ID ( DOT_ID )* )
            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:543:3: ID ( DOT_ID )*
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            ID103=(CommonTree)match(input,ID,FOLLOW_ID_in_dotname1153); 
            ID103_tree = (CommonTree)adaptor.dupNode(ID103);


            adaptor.addChild(root_0, ID103_tree);


             retval.result = new String((ID103!=null?ID103.getText():null)); 

            // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:544:3: ( DOT_ID )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==DOT_ID) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // /x/jiwon/workspace/socialite/grammar/SociaLiteRule.g:544:4: DOT_ID
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    DOT_ID104=(CommonTree)match(input,DOT_ID,FOLLOW_DOT_ID_in_dotname1160); 
            	    DOT_ID104_tree = (CommonTree)adaptor.dupNode(DOT_ID104);


            	    adaptor.addChild(root_0, DOT_ID104_tree);


            	     retval.result += new String((DOT_ID104!=null?DOT_ID104.getText():null)); 

            	    }
            	    break;

            	default :
            	    break loop42;
                }
            } while (true);


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "dotname"

    // Delegated rules


 

    public static final BitSet FOLLOW_stat_in_prog65 = new BitSet(new long[]{0x0000A00000009080L});
    public static final BitSet FOLLOW_EOF_in_prog77 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_decl_in_stat90 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule_in_stat96 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_query_in_stat102 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_stmt_in_stat107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CLEAR_in_table_stmt124 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_table_stmt126 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DROP_in_table_stmt135 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_table_stmt137 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_QUERY_in_query155 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_predicate_in_query157 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DECL_in_table_decl174 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_KIND1_in_table_decl177 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_KIND2_in_table_decl182 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_ID_in_table_decl188 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_INDEX_in_table_decl191 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_col_decl_in_table_decl193 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_decls_in_table_decl197 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_OPTION_in_table_decl201 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_table_opts_in_table_decl203 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_t_opt_in_table_opts229 = new BitSet(new long[]{0x0002060821000002L});
    public static final BitSet FOLLOW_t_opt_in_table_opts237 = new BitSet(new long[]{0x0002060821000002L});
    public static final BitSet FOLLOW_SORT_BY_in_t_opt256 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_t_opt260 = new BitSet(new long[]{0x0004000000000008L});
    public static final BitSet FOLLOW_SORT_ORDER_in_t_opt265 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ORDER_BY_in_t_opt275 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_t_opt277 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INDEX_BY_in_t_opt285 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_t_opt287 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GROUP_BY_in_t_opt295 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_INT_in_t_opt297 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PREDEFINED_in_t_opt305 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MULTISET_in_t_opt312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COL_DECLS_in_decls329 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_col_decls_in_decls331 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_DECL_in_decls334 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_decls_in_decls338 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_col_decl_in_col_decls358 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_col_decl_in_col_decls367 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COL_DECL_in_col_decl388 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_col_decl390 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_ID_in_col_decl392 = new BitSet(new long[]{0x0001400080000008L});
    public static final BitSet FOLLOW_col_opt_in_col_decl394 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_RANGE_in_col_opt412 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_INT_in_col_opt416 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_INT_in_col_opt420 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SIZE_in_col_opt428 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_INT_in_col_opt432 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ITER_in_col_opt440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_type455 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type460 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_type473 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type477 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type479 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_type488 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type493 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type495 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_type505 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type510 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_type522 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type527 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_type539 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type543 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type545 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_type556 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_type561 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_type563 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_in_rule582 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_HEAD_in_rule585 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_predicate_in_rule587 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BODY_in_rule591 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_litlist_in_rule595 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BODY_in_rule599 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_litlist_in_rule603 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_literal_in_litlist628 = new BitSet(new long[]{0x0000080000040002L});
    public static final BitSet FOLLOW_literal_in_litlist637 = new BitSet(new long[]{0x0000080000040002L});
    public static final BitSet FOLLOW_PREDICATE_in_literal655 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_NOT_in_literal657 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_predicate_in_literal660 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXPR_in_literal667 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_literal669 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ID_in_predicate687 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_INDEX_in_predicate690 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_param_in_predicate692 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_paramlist_in_predicate696 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FUNC_in_function715 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_dotname_in_function717 = new BitSet(new long[]{0x0020000000400808L,0x0000000080000158L});
    public static final BitSet FOLLOW_fparamlist_in_function719 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_simpleExpr_in_param737 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleExpr_in_fparam751 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_param_in_paramlist770 = new BitSet(new long[]{0x0020000000400802L,0x0000000080000158L});
    public static final BitSet FOLLOW_param_in_paramlist778 = new BitSet(new long[]{0x0020000000400802L,0x0000000080000158L});
    public static final BitSet FOLLOW_fparam_in_fparamlist801 = new BitSet(new long[]{0x0020000000400802L,0x0000000080000158L});
    public static final BitSet FOLLOW_fparam_in_fparamlist809 = new BitSet(new long[]{0x0020000000400802L,0x0000000080000158L});
    public static final BitSet FOLLOW_T_INT_in_term829 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_INT_in_term831 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_T_FLOAT_in_term839 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_FLOAT_in_term841 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_T_STR_in_term850 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_STRING_in_term852 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_T_UTF8_in_term861 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_UTF8_in_term863 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_T_VAR_in_term872 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_dotname_in_term874 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_expr891 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_simpleExpr_in_expr907 = new BitSet(new long[]{0x0020000000400800L,0x0000000080000158L});
    public static final BitSet FOLLOW_simpleExpr_in_expr911 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_78_in_expr920 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_simpleExpr_in_expr924 = new BitSet(new long[]{0x0020000000400800L,0x0000000080000158L});
    public static final BitSet FOLLOW_simpleExpr_in_expr928 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MULTI_ASSIGN_in_expr936 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_varlist_in_expr938 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_function_in_expr940 = new BitSet(new long[]{0x0000000008000008L,0x0000000065180000L});
    public static final BitSet FOLLOW_cast_in_expr944 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_multExpr_in_simpleExpr959 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_simpleExpr968 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_simpleExpr_in_simpleExpr976 = new BitSet(new long[]{0x0020000000400800L,0x0000000080000158L});
    public static final BitSet FOLLOW_simpleExpr_in_simpleExpr980 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_exprValue_in_multExpr995 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_multExpr1004 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_multExpr_in_multExpr1014 = new BitSet(new long[]{0x0020000000400800L,0x0000000080000108L});
    public static final BitSet FOLLOW_multExpr_in_multExpr1018 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_simpleExpr_in_compExpr1035 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dotname_in_varlist1055 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_dotname_in_varlist1062 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_TERM_in_exprValue1079 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_term_in_exprValue1081 = new BitSet(new long[]{0x0000000008000008L,0x0000000065180040L});
    public static final BitSet FOLLOW_70_in_exprValue1086 = new BitSet(new long[]{0x0000000008000008L,0x0000000065180000L});
    public static final BitSet FOLLOW_cast_in_exprValue1092 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FUNCTION_in_exprValue1101 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_function_in_exprValue1103 = new BitSet(new long[]{0x0000000008000008L,0x0000000065180000L});
    public static final BitSet FOLLOW_cast_in_exprValue1107 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COMPOUND_EXPR_in_exprValue1116 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_compExpr_in_exprValue1118 = new BitSet(new long[]{0x0000000008000008L,0x0000000065180000L});
    public static final BitSet FOLLOW_cast_in_exprValue1122 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_type_in_cast1139 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_dotname1153 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_DOT_ID_in_dotname1160 = new BitSet(new long[]{0x0000000000004002L});

}