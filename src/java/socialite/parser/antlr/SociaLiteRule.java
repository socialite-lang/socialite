// $ANTLR 3.4 /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g 2016-01-20 11:05:19

    package socialite.parser.antlr;    
    import socialite.parser.Query;
    import socialite.parser.Literal;
    import socialite.parser.Param;
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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "APPROX", "BIT", "CHAR", "CLEAR", "COL_DECL", "COL_DECLS", "COMMENT", "COMPOUND_EXPR", "CONCURRENT", "DECL", "DOT_END", "DOT_ID", "DROP", "ESC_SEQ", "EXPONENT", "EXPR", "FLOAT", "FUNC", "FUNCTION", "GROUP_BY", "HEX_DIGIT", "ID", "INDEX", "INDEX_BY", "INT", "ITER", "ITER_DECL", "MULTISET", "MULTI_ASSIGN", "NOT", "OCTAL_ESC", "OPTION", "ORDER_BY", "PREDEFINED", "PREDICATE", "PROG", "QUERY", "RANGE", "RULE", "SORT_BY", "SORT_ORDER", "STRING", "TERM", "T_FLOAT", "T_INT", "T_STR", "T_UTF8", "T_VAR", "UNICODE_ESC", "UTF8", "WS", "'!='", "'$'", "'('", "')'", "'*'", "'+'", "','", "'-'", "'..'", "'/'", "':'", "':-'", "';'", "'<'", "'<='", "'='", "'=='", "'>'", "'>='", "'?-'", "'Object'", "'String'", "'['", "']'", "'bit'", "'clear'", "'concurrent'", "'double'", "'drop'", "'float'", "'groupby'", "'indexby'", "'int'", "'long'", "'mod'", "'multiset'", "'orderby'", "'predefined'", "'sortby'"
    };

    public static final int EOF=-1;
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
    public static final int T__92=92;
    public static final int T__93=93;
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
    public static final int SORT_BY=43;
    public static final int SORT_ORDER=44;
    public static final int STRING=45;
    public static final int TERM=46;
    public static final int T_FLOAT=47;
    public static final int T_INT=48;
    public static final int T_STR=49;
    public static final int T_UTF8=50;
    public static final int T_VAR=51;
    public static final int UNICODE_ESC=52;
    public static final int UTF8=53;
    public static final int WS=54;

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
    public String getGrammarFileName() { return "/Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g"; }


        public Parser parser;
        public Map<String, TableDecl> tableDeclMap = new HashMap<String, TableDecl>();
        Set<Variable> dotVars = new LinkedHashSet<Variable>();
        List<AssignOp> headTmpVarAssigns = new ArrayList<AssignOp>();
        List<AssignOp> tmpVarAssigns = new ArrayList<AssignOp>();

        HashMap<String, Variable> varMapInRule = new HashMap<String, Variable>();
        int tmpVarCount=0;
        int constCount=0;

        public Parser getParser() { return parser; }
        public String maybeGetDuplicateColumnName(List<ColumnDecl> decls) {
            LinkedHashSet<String> names = new LinkedHashSet<String>(decls.size());
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

        Variable getVariable(String name) {
            if (name.equals("_")) {
                tmpVarCount++;
                return new Variable(name+"$"+tmpVarCount, true);
            }
            if (varMapInRule.containsKey(name)) {return varMapInRule.get(name);}
            Variable v = new Variable(name);
            varMapInRule.put(name, v);
            return v;
        }
        Variable getNextTmpVar() {
            tmpVarCount++;
            return new Variable("_tmp$"+tmpVarCount);
        }
        int getNextConstId() { return constCount++; }
        void nextRule() {
            varMapInRule.clear();
            constCount = 0;
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
            Variable tmpVar = getNextTmpVar();
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:135:1: prog returns [List result] : ( stat )+ EOF ;
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
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:136:5: ( ( stat )+ EOF )
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:136:7: ( stat )+ EOF
            {
            root_0 = (CommonTree)adaptor.nil();


            retval.result = new ArrayList();

            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:137:8: ( stat )+
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
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:137:9: stat
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_stat_in_prog74);
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
            EOF2=(CommonTree)match(input,EOF,FOLLOW_EOF_in_prog80); 
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:144:1: stat returns [Object result] : ( table_decl | rule | query | table_stmt );
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
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:145:2: ( table_decl | rule | query | table_stmt )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:145:3: table_decl
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_table_decl_in_stat93);
                    table_decl3=table_decl();

                    state._fsp--;

                    adaptor.addChild(root_0, table_decl3.getTree());


                    retval.result = (table_decl3!=null?table_decl3.result:null);

                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:146:3: rule
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_rule_in_stat99);
                    rule4=rule();

                    state._fsp--;

                    adaptor.addChild(root_0, rule4.getTree());


                    retval.result = (rule4!=null?rule4.result:null);

                    }
                    break;
                case 3 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:147:3: query
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_query_in_stat105);
                    query5=query();

                    state._fsp--;

                    adaptor.addChild(root_0, query5.getTree());


                    retval.result = (query5!=null?query5.result:null);

                    }
                    break;
                case 4 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:148:3: table_stmt
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_table_stmt_in_stat110);
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:150:1: table_stmt returns [TableStmt result] : ( ^( CLEAR ID ) | ^( DROP ( ID )? ) );
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
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:151:2: ( ^( CLEAR ID ) | ^( DROP ( ID )? ) )
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:151:4: ^( CLEAR ID )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    CLEAR7=(CommonTree)match(input,CLEAR,FOLLOW_CLEAR_in_table_stmt127); 
                    CLEAR7_tree = (CommonTree)adaptor.dupNode(CLEAR7);


                    root_1 = (CommonTree)adaptor.becomeRoot(CLEAR7_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID8=(CommonTree)match(input,ID,FOLLOW_ID_in_table_stmt129); 
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:152:3: ^( DROP ( ID )? )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    DROP9=(CommonTree)match(input,DROP,FOLLOW_DROP_in_table_stmt138); 
                    DROP9_tree = (CommonTree)adaptor.dupNode(DROP9);


                    root_1 = (CommonTree)adaptor.becomeRoot(DROP9_tree, root_1);


                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:152:10: ( ID )?
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==ID) ) {
                            alt3=1;
                        }
                        switch (alt3) {
                            case 1 :
                                // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:152:10: ID
                                {
                                _last = (CommonTree)input.LT(1);
                                ID10=(CommonTree)match(input,ID,FOLLOW_ID_in_table_stmt140); 
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:154:1: query returns [Query result] : ^( QUERY predicate ) ;
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
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:155:2: ( ^( QUERY predicate ) )
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:155:3: ^( QUERY predicate )
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();
            _last = (CommonTree)input.LT(1);
            QUERY11=(CommonTree)match(input,QUERY,FOLLOW_QUERY_in_query158); 
            QUERY11_tree = (CommonTree)adaptor.dupNode(QUERY11);


            root_1 = (CommonTree)adaptor.becomeRoot(QUERY11_tree, root_1);


            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_predicate_in_query160);
            predicate12=predicate();

            state._fsp--;

            adaptor.addChild(root_1, predicate12.getTree());


            match(input, Token.UP, null); 
            adaptor.addChild(root_0, root_1);
            _last = _save_last_1;
            }


             
            	retval.result = new Query((predicate12!=null?predicate12.result:null));
            	nextRule();
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:161:1: table_decl returns [TableDecl result] : ^( DECL ID decls ^( OPTION ( table_opts )? ) ) ;
    public final SociaLiteRule.table_decl_return table_decl() throws RecognitionException {
        SociaLiteRule.table_decl_return retval = new SociaLiteRule.table_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree DECL13=null;
        CommonTree ID14=null;
        CommonTree OPTION16=null;
        SociaLiteRule.decls_return decls15 =null;

        SociaLiteRule.table_opts_return table_opts17 =null;


        CommonTree DECL13_tree=null;
        CommonTree ID14_tree=null;
        CommonTree OPTION16_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:162:5: ( ^( DECL ID decls ^( OPTION ( table_opts )? ) ) )
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:162:6: ^( DECL ID decls ^( OPTION ( table_opts )? ) )
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();
            _last = (CommonTree)input.LT(1);
            DECL13=(CommonTree)match(input,DECL,FOLLOW_DECL_in_table_decl180); 
            DECL13_tree = (CommonTree)adaptor.dupNode(DECL13);


            root_1 = (CommonTree)adaptor.becomeRoot(DECL13_tree, root_1);


            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID14=(CommonTree)match(input,ID,FOLLOW_ID_in_table_decl182); 
            ID14_tree = (CommonTree)adaptor.dupNode(ID14);


            adaptor.addChild(root_1, ID14_tree);


            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_decls_in_table_decl184);
            decls15=decls();

            state._fsp--;

            adaptor.addChild(root_1, decls15.getTree());


            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_2 = _last;
            CommonTree _first_2 = null;
            CommonTree root_2 = (CommonTree)adaptor.nil();
            _last = (CommonTree)input.LT(1);
            OPTION16=(CommonTree)match(input,OPTION,FOLLOW_OPTION_in_table_decl187); 
            OPTION16_tree = (CommonTree)adaptor.dupNode(OPTION16);


            root_2 = (CommonTree)adaptor.becomeRoot(OPTION16_tree, root_2);


            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:162:31: ( table_opts )?
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==CONCURRENT||LA5_0==GROUP_BY||LA5_0==INDEX_BY||LA5_0==MULTISET||(LA5_0 >= ORDER_BY && LA5_0 <= PREDEFINED)||LA5_0==SORT_BY) ) {
                    alt5=1;
                }
                switch (alt5) {
                    case 1 :
                        // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:162:31: table_opts
                        {
                        _last = (CommonTree)input.LT(1);
                        pushFollow(FOLLOW_table_opts_in_table_decl189);
                        table_opts17=table_opts();

                        state._fsp--;

                        adaptor.addChild(root_2, table_opts17.getTree());


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



                    String dupCol=maybeGetDuplicateColumnName((decls15!=null?decls15.result:null).getAllColDecls());
                    if (dupCol!=null) 
                        throw new ParseException(getParser(), (ID14!=null?ID14.getLine():0)-1, (ID14!=null?ID14.getCharPositionInLine():0),  "Duplicate column name "+dupCol+" in "+ID14);
                    retval.result = new TableDecl((ID14!=null?ID14.getText():null), (decls15!=null?decls15.result:null).colDecls, (decls15!=null?decls15.result:null).nestedTable);    
                    if (retval.result.nestedTable!=null) {
                        for (ColumnDecl d:retval.result.nestedTable.getAllColDecls()) {
                            if (d.option() instanceof ColIter) {
                                throw new ParseException(getParser(), (ID14!=null?ID14.getLine():0)-1, (ID14!=null?ID14.getCharPositionInLine():0)+(ID14!=null?ID14.getText():null).length()+1, "Iteration column cannot be nested."); 
                            }
                        }
                    }
                    try { retval.result.setOptions((table_opts17!=null?table_opts17.result:null)); }
                    catch (ParseException e) {
                        e.setLine((ID14!=null?ID14.getLine():0)-1); e.setPos(0);e.setParser(getParser());
                        throw e;
                    }
                    if (tableDeclMap.containsKey((ID14!=null?ID14.getText():null))) {
                        if (!retval.result.equals(tableDeclMap.get((ID14!=null?ID14.getText():null)))) {
                            throw new ParseException(getParser(), (ID14!=null?ID14.getLine():0)-1, (ID14!=null?ID14.getCharPositionInLine():0), 
                                        (ID14!=null?ID14.getText():null)+" was previously declared with a different signature.");     
                        }
                        retval.result =null;
                    } else { tableDeclMap.put((ID14!=null?ID14.getText():null), retval.result); }
                

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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:190:1: table_opts returns [List<TableOpt> result] : opt1= t_opt (opt2= t_opt )* ;
    public final SociaLiteRule.table_opts_return table_opts() throws RecognitionException {
        SociaLiteRule.table_opts_return retval = new SociaLiteRule.table_opts_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.t_opt_return opt1 =null;

        SociaLiteRule.t_opt_return opt2 =null;



        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:191:2: (opt1= t_opt (opt2= t_opt )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:191:3: opt1= t_opt (opt2= t_opt )*
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_t_opt_in_table_opts223);
            opt1=t_opt();

            state._fsp--;

            adaptor.addChild(root_0, opt1.getTree());


            retval.result = new ArrayList<TableOpt>(); retval.result.add((opt1!=null?opt1.result:null));

            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:192:2: (opt2= t_opt )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==CONCURRENT||LA6_0==GROUP_BY||LA6_0==INDEX_BY||LA6_0==MULTISET||(LA6_0 >= ORDER_BY && LA6_0 <= PREDEFINED)||LA6_0==SORT_BY) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:192:3: opt2= t_opt
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_t_opt_in_table_opts231);
            	    opt2=t_opt();

            	    state._fsp--;

            	    adaptor.addChild(root_0, opt2.getTree());


            	    retval.result.add((opt2!=null?opt2.result:null));

            	    }
            	    break;

            	default :
            	    break loop6;
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:194:1: t_opt returns [TableOpt result] : ( ^( SORT_BY col= ID (order= SORT_ORDER )? ) | ^( ORDER_BY ID ) | ^( INDEX_BY ID ) | ^( GROUP_BY INT ) | PREDEFINED | CONCURRENT | MULTISET );
    public final SociaLiteRule.t_opt_return t_opt() throws RecognitionException {
        SociaLiteRule.t_opt_return retval = new SociaLiteRule.t_opt_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree col=null;
        CommonTree order=null;
        CommonTree SORT_BY18=null;
        CommonTree ORDER_BY19=null;
        CommonTree ID20=null;
        CommonTree INDEX_BY21=null;
        CommonTree ID22=null;
        CommonTree GROUP_BY23=null;
        CommonTree INT24=null;
        CommonTree PREDEFINED25=null;
        CommonTree CONCURRENT26=null;
        CommonTree MULTISET27=null;

        CommonTree col_tree=null;
        CommonTree order_tree=null;
        CommonTree SORT_BY18_tree=null;
        CommonTree ORDER_BY19_tree=null;
        CommonTree ID20_tree=null;
        CommonTree INDEX_BY21_tree=null;
        CommonTree ID22_tree=null;
        CommonTree GROUP_BY23_tree=null;
        CommonTree INT24_tree=null;
        CommonTree PREDEFINED25_tree=null;
        CommonTree CONCURRENT26_tree=null;
        CommonTree MULTISET27_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:195:2: ( ^( SORT_BY col= ID (order= SORT_ORDER )? ) | ^( ORDER_BY ID ) | ^( INDEX_BY ID ) | ^( GROUP_BY INT ) | PREDEFINED | CONCURRENT | MULTISET )
            int alt8=7;
            switch ( input.LA(1) ) {
            case SORT_BY:
                {
                alt8=1;
                }
                break;
            case ORDER_BY:
                {
                alt8=2;
                }
                break;
            case INDEX_BY:
                {
                alt8=3;
                }
                break;
            case GROUP_BY:
                {
                alt8=4;
                }
                break;
            case PREDEFINED:
                {
                alt8=5;
                }
                break;
            case CONCURRENT:
                {
                alt8=6;
                }
                break;
            case MULTISET:
                {
                alt8=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;

            }

            switch (alt8) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:195:3: ^( SORT_BY col= ID (order= SORT_ORDER )? )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    SORT_BY18=(CommonTree)match(input,SORT_BY,FOLLOW_SORT_BY_in_t_opt250); 
                    SORT_BY18_tree = (CommonTree)adaptor.dupNode(SORT_BY18);


                    root_1 = (CommonTree)adaptor.becomeRoot(SORT_BY18_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    col=(CommonTree)match(input,ID,FOLLOW_ID_in_t_opt254); 
                    col_tree = (CommonTree)adaptor.dupNode(col);


                    adaptor.addChild(root_1, col_tree);


                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:195:20: (order= SORT_ORDER )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==SORT_ORDER) ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:195:21: order= SORT_ORDER
                            {
                            _last = (CommonTree)input.LT(1);
                            order=(CommonTree)match(input,SORT_ORDER,FOLLOW_SORT_ORDER_in_t_opt259); 
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:200:3: ^( ORDER_BY ID )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    ORDER_BY19=(CommonTree)match(input,ORDER_BY,FOLLOW_ORDER_BY_in_t_opt269); 
                    ORDER_BY19_tree = (CommonTree)adaptor.dupNode(ORDER_BY19);


                    root_1 = (CommonTree)adaptor.becomeRoot(ORDER_BY19_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID20=(CommonTree)match(input,ID,FOLLOW_ID_in_t_opt271); 
                    ID20_tree = (CommonTree)adaptor.dupNode(ID20);


                    adaptor.addChild(root_1, ID20_tree);


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }


                    retval.result = new OrderBy((ID20!=null?ID20.getText():null));

                    }
                    break;
                case 3 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:201:3: ^( INDEX_BY ID )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    INDEX_BY21=(CommonTree)match(input,INDEX_BY,FOLLOW_INDEX_BY_in_t_opt279); 
                    INDEX_BY21_tree = (CommonTree)adaptor.dupNode(INDEX_BY21);


                    root_1 = (CommonTree)adaptor.becomeRoot(INDEX_BY21_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID22=(CommonTree)match(input,ID,FOLLOW_ID_in_t_opt281); 
                    ID22_tree = (CommonTree)adaptor.dupNode(ID22);


                    adaptor.addChild(root_1, ID22_tree);


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }


                    retval.result = new IndexBy((ID22!=null?ID22.getText():null));

                    }
                    break;
                case 4 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:202:3: ^( GROUP_BY INT )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    GROUP_BY23=(CommonTree)match(input,GROUP_BY,FOLLOW_GROUP_BY_in_t_opt289); 
                    GROUP_BY23_tree = (CommonTree)adaptor.dupNode(GROUP_BY23);


                    root_1 = (CommonTree)adaptor.becomeRoot(GROUP_BY23_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    INT24=(CommonTree)match(input,INT,FOLLOW_INT_in_t_opt291); 
                    INT24_tree = (CommonTree)adaptor.dupNode(INT24);


                    adaptor.addChild(root_1, INT24_tree);


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }


                     retval.result = new GroupBy(Integer.parseInt((INT24!=null?INT24.getText():null)));

                    }
                    break;
                case 5 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:203:4: PREDEFINED
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    PREDEFINED25=(CommonTree)match(input,PREDEFINED,FOLLOW_PREDEFINED_in_t_opt299); 
                    PREDEFINED25_tree = (CommonTree)adaptor.dupNode(PREDEFINED25);


                    adaptor.addChild(root_0, PREDEFINED25_tree);


                    retval.result = new Predefined(); 

                    }
                    break;
                case 6 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:204:4: CONCURRENT
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    CONCURRENT26=(CommonTree)match(input,CONCURRENT,FOLLOW_CONCURRENT_in_t_opt306); 
                    CONCURRENT26_tree = (CommonTree)adaptor.dupNode(CONCURRENT26);


                    adaptor.addChild(root_0, CONCURRENT26_tree);


                    retval.result = new Concurrent(); 

                    }
                    break;
                case 7 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:205:4: MULTISET
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    MULTISET27=(CommonTree)match(input,MULTISET,FOLLOW_MULTISET_in_t_opt313); 
                    MULTISET27_tree = (CommonTree)adaptor.dupNode(MULTISET27);


                    adaptor.addChild(root_0, MULTISET27_tree);


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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:207:1: decls returns [NestedTableDecl result] : ^( COL_DECLS col_decls ^( DECL (nested= decls )? ) ) ;
    public final SociaLiteRule.decls_return decls() throws RecognitionException {
        SociaLiteRule.decls_return retval = new SociaLiteRule.decls_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree COL_DECLS28=null;
        CommonTree DECL30=null;
        SociaLiteRule.decls_return nested =null;

        SociaLiteRule.col_decls_return col_decls29 =null;


        CommonTree COL_DECLS28_tree=null;
        CommonTree DECL30_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:208:2: ( ^( COL_DECLS col_decls ^( DECL (nested= decls )? ) ) )
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:208:3: ^( COL_DECLS col_decls ^( DECL (nested= decls )? ) )
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();
            _last = (CommonTree)input.LT(1);
            COL_DECLS28=(CommonTree)match(input,COL_DECLS,FOLLOW_COL_DECLS_in_decls330); 
            COL_DECLS28_tree = (CommonTree)adaptor.dupNode(COL_DECLS28);


            root_1 = (CommonTree)adaptor.becomeRoot(COL_DECLS28_tree, root_1);


            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_col_decls_in_decls332);
            col_decls29=col_decls();

            state._fsp--;

            adaptor.addChild(root_1, col_decls29.getTree());


            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_2 = _last;
            CommonTree _first_2 = null;
            CommonTree root_2 = (CommonTree)adaptor.nil();
            _last = (CommonTree)input.LT(1);
            DECL30=(CommonTree)match(input,DECL,FOLLOW_DECL_in_decls335); 
            DECL30_tree = (CommonTree)adaptor.dupNode(DECL30);


            root_2 = (CommonTree)adaptor.becomeRoot(DECL30_tree, root_2);


            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:208:38: (nested= decls )?
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==COL_DECLS) ) {
                    alt9=1;
                }
                switch (alt9) {
                    case 1 :
                        // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:208:38: nested= decls
                        {
                        _last = (CommonTree)input.LT(1);
                        pushFollow(FOLLOW_decls_in_decls339);
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


             retval.result = new NestedTableDecl((col_decls29!=null?col_decls29.result:null), (nested!=null?nested.result:null));
            	

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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:211:1: col_decls returns [List<ColumnDecl> result] : d1= col_decl (d2= col_decl )* ;
    public final SociaLiteRule.col_decls_return col_decls() throws RecognitionException {
        SociaLiteRule.col_decls_return retval = new SociaLiteRule.col_decls_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.col_decl_return d1 =null;

        SociaLiteRule.col_decl_return d2 =null;



        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:212:2: (d1= col_decl (d2= col_decl )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:212:3: d1= col_decl (d2= col_decl )*
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_col_decl_in_col_decls359);
            d1=col_decl();

            state._fsp--;

            adaptor.addChild(root_0, d1.getTree());


            retval.result = new ArrayList<ColumnDecl>(); retval.result.add((d1!=null?d1.result:null));

            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:213:3: (d2= col_decl )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==COL_DECL) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:213:4: d2= col_decl
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_col_decl_in_col_decls368);
            	    d2=col_decl();

            	    state._fsp--;

            	    adaptor.addChild(root_0, d2.getTree());


            	    retval.result.add((d2!=null?d2.result:null));

            	    }
            	    break;

            	default :
            	    break loop10;
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:215:1: col_decl returns [ColumnDecl result] : ^( COL_DECL type ID ( col_opt )? ) ;
    public final SociaLiteRule.col_decl_return col_decl() throws RecognitionException {
        SociaLiteRule.col_decl_return retval = new SociaLiteRule.col_decl_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree COL_DECL31=null;
        CommonTree ID33=null;
        SociaLiteRule.type_return type32 =null;

        SociaLiteRule.col_opt_return col_opt34 =null;


        CommonTree COL_DECL31_tree=null;
        CommonTree ID33_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:216:2: ( ^( COL_DECL type ID ( col_opt )? ) )
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:216:4: ^( COL_DECL type ID ( col_opt )? )
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();
            _last = (CommonTree)input.LT(1);
            COL_DECL31=(CommonTree)match(input,COL_DECL,FOLLOW_COL_DECL_in_col_decl389); 
            COL_DECL31_tree = (CommonTree)adaptor.dupNode(COL_DECL31);


            root_1 = (CommonTree)adaptor.becomeRoot(COL_DECL31_tree, root_1);


            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_type_in_col_decl391);
            type32=type();

            state._fsp--;

            adaptor.addChild(root_1, type32.getTree());


            _last = (CommonTree)input.LT(1);
            ID33=(CommonTree)match(input,ID,FOLLOW_ID_in_col_decl393); 
            ID33_tree = (CommonTree)adaptor.dupNode(ID33);


            adaptor.addChild(root_1, ID33_tree);


            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:216:23: ( col_opt )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==BIT||LA11_0==ITER||LA11_0==RANGE) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:216:23: col_opt
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_col_opt_in_col_decl395);
                    col_opt34=col_opt();

                    state._fsp--;

                    adaptor.addChild(root_1, col_opt34.getTree());


                    }
                    break;

            }


            match(input, Token.UP, null); 
            adaptor.addChild(root_0, root_1);
            _last = _save_last_1;
            }



                    if ((col_opt34!=null?col_opt34.result:null) instanceof ColRange ||
                          (col_opt34!=null?col_opt34.result:null) instanceof ColBit ||
                          (col_opt34!=null?col_opt34.result:null) instanceof ColIter) {
                        if ((type32!=null?type32.result:null) != int.class && (type32!=null?type32.result:null) != long.class) {
                            throw new ParseException(getParser(), (ID33!=null?ID33.getLine():0)-1, (ID33!=null?ID33.getCharPositionInLine():0), 
                                        "Column "+(ID33!=null?ID33.getText():null)+" has unsupported option.");	 
                        }
                    }

            	retval.result = new ColumnDecl((type32!=null?type32.result:null), (ID33!=null?ID33.getText():null), (col_opt34!=null?col_opt34.result:null));
            	

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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:229:1: col_opt returns [ColOpt result] : ( ^( RANGE i1= INT i2= INT ) | ITER | ^( BIT i1= INT ) );
    public final SociaLiteRule.col_opt_return col_opt() throws RecognitionException {
        SociaLiteRule.col_opt_return retval = new SociaLiteRule.col_opt_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree i1=null;
        CommonTree i2=null;
        CommonTree RANGE35=null;
        CommonTree ITER36=null;
        CommonTree BIT37=null;

        CommonTree i1_tree=null;
        CommonTree i2_tree=null;
        CommonTree RANGE35_tree=null;
        CommonTree ITER36_tree=null;
        CommonTree BIT37_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:230:2: ( ^( RANGE i1= INT i2= INT ) | ITER | ^( BIT i1= INT ) )
            int alt12=3;
            switch ( input.LA(1) ) {
            case RANGE:
                {
                alt12=1;
                }
                break;
            case ITER:
                {
                alt12=2;
                }
                break;
            case BIT:
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:230:3: ^( RANGE i1= INT i2= INT )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    RANGE35=(CommonTree)match(input,RANGE,FOLLOW_RANGE_in_col_opt413); 
                    RANGE35_tree = (CommonTree)adaptor.dupNode(RANGE35);


                    root_1 = (CommonTree)adaptor.becomeRoot(RANGE35_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    i1=(CommonTree)match(input,INT,FOLLOW_INT_in_col_opt417); 
                    i1_tree = (CommonTree)adaptor.dupNode(i1);


                    adaptor.addChild(root_1, i1_tree);


                    _last = (CommonTree)input.LT(1);
                    i2=(CommonTree)match(input,INT,FOLLOW_INT_in_col_opt421); 
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:233:4: ITER
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    ITER36=(CommonTree)match(input,ITER,FOLLOW_ITER_in_col_opt429); 
                    ITER36_tree = (CommonTree)adaptor.dupNode(ITER36);


                    adaptor.addChild(root_0, ITER36_tree);



                    	 retval.result = new ColIter();
                    	

                    }
                    break;
                case 3 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:236:4: ^( BIT i1= INT )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    BIT37=(CommonTree)match(input,BIT,FOLLOW_BIT_in_col_opt437); 
                    BIT37_tree = (CommonTree)adaptor.dupNode(BIT37);


                    root_1 = (CommonTree)adaptor.becomeRoot(BIT37_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    i1=(CommonTree)match(input,INT,FOLLOW_INT_in_col_opt441); 
                    i1_tree = (CommonTree)adaptor.dupNode(i1);


                    adaptor.addChild(root_1, i1_tree);


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }



                    	    retval.result = new ColBit(Integer.parseInt((i1!=null?i1.getText():null)));
                    	

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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:240:1: type returns [Class result] : ( 'int' ( '[' ']' )? | 'long' ( '[' ']' )? | 'float' ( '[' ']' )? | 'double' ( '[' ']' )? | 'String' ( '[' ']' )? | 'Object' ( '[' ']' )? | ID ( '[' ']' )? );
    public final SociaLiteRule.type_return type() throws RecognitionException {
        SociaLiteRule.type_return retval = new SociaLiteRule.type_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal38=null;
        CommonTree char_literal39=null;
        CommonTree char_literal40=null;
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
        CommonTree ID56=null;
        CommonTree char_literal57=null;
        CommonTree char_literal58=null;

        CommonTree string_literal38_tree=null;
        CommonTree char_literal39_tree=null;
        CommonTree char_literal40_tree=null;
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
        CommonTree ID56_tree=null;
        CommonTree char_literal57_tree=null;
        CommonTree char_literal58_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:241:2: ( 'int' ( '[' ']' )? | 'long' ( '[' ']' )? | 'float' ( '[' ']' )? | 'double' ( '[' ']' )? | 'String' ( '[' ']' )? | 'Object' ( '[' ']' )? | ID ( '[' ']' )? )
            int alt20=7;
            switch ( input.LA(1) ) {
            case 87:
                {
                alt20=1;
                }
                break;
            case 88:
                {
                alt20=2;
                }
                break;
            case 84:
                {
                alt20=3;
                }
                break;
            case 82:
                {
                alt20=4;
                }
                break;
            case 76:
                {
                alt20=5;
                }
                break;
            case 75:
                {
                alt20=6;
                }
                break;
            case ID:
                {
                alt20=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;

            }

            switch (alt20) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:241:3: 'int' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    string_literal38=(CommonTree)match(input,87,FOLLOW_87_in_type457); 
                    string_literal38_tree = (CommonTree)adaptor.dupNode(string_literal38);


                    adaptor.addChild(root_0, string_literal38_tree);


                    retval.result = int.class;

                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:241:32: ( '[' ']' )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==77) ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:241:33: '[' ']'
                            {
                            _last = (CommonTree)input.LT(1);
                            char_literal39=(CommonTree)match(input,77,FOLLOW_77_in_type462); 
                            char_literal39_tree = (CommonTree)adaptor.dupNode(char_literal39);


                            adaptor.addChild(root_0, char_literal39_tree);


                            _last = (CommonTree)input.LT(1);
                            char_literal40=(CommonTree)match(input,78,FOLLOW_78_in_type464); 
                            char_literal40_tree = (CommonTree)adaptor.dupNode(char_literal40);


                            adaptor.addChild(root_0, char_literal40_tree);


                            retval.result = int[].class;

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:242:3: 'long' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    string_literal41=(CommonTree)match(input,88,FOLLOW_88_in_type475); 
                    string_literal41_tree = (CommonTree)adaptor.dupNode(string_literal41);


                    adaptor.addChild(root_0, string_literal41_tree);


                    retval.result =long.class;

                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:242:31: ( '[' ']' )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==77) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:242:32: '[' ']'
                            {
                            _last = (CommonTree)input.LT(1);
                            char_literal42=(CommonTree)match(input,77,FOLLOW_77_in_type479); 
                            char_literal42_tree = (CommonTree)adaptor.dupNode(char_literal42);


                            adaptor.addChild(root_0, char_literal42_tree);


                            _last = (CommonTree)input.LT(1);
                            char_literal43=(CommonTree)match(input,78,FOLLOW_78_in_type481); 
                            char_literal43_tree = (CommonTree)adaptor.dupNode(char_literal43);


                            adaptor.addChild(root_0, char_literal43_tree);


                            retval.result = long[].class;

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:243:3: 'float' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    string_literal44=(CommonTree)match(input,84,FOLLOW_84_in_type490); 
                    string_literal44_tree = (CommonTree)adaptor.dupNode(string_literal44);


                    adaptor.addChild(root_0, string_literal44_tree);


                    retval.result = float.class;

                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:243:36: ( '[' ']' )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==77) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:243:37: '[' ']'
                            {
                            _last = (CommonTree)input.LT(1);
                            char_literal45=(CommonTree)match(input,77,FOLLOW_77_in_type495); 
                            char_literal45_tree = (CommonTree)adaptor.dupNode(char_literal45);


                            adaptor.addChild(root_0, char_literal45_tree);


                            _last = (CommonTree)input.LT(1);
                            char_literal46=(CommonTree)match(input,78,FOLLOW_78_in_type497); 
                            char_literal46_tree = (CommonTree)adaptor.dupNode(char_literal46);


                            adaptor.addChild(root_0, char_literal46_tree);


                            retval.result = float[].class;

                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:244:3: 'double' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    string_literal47=(CommonTree)match(input,82,FOLLOW_82_in_type507); 
                    string_literal47_tree = (CommonTree)adaptor.dupNode(string_literal47);


                    adaptor.addChild(root_0, string_literal47_tree);


                    retval.result = double.class;

                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:244:38: ( '[' ']' )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==77) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:244:39: '[' ']'
                            {
                            _last = (CommonTree)input.LT(1);
                            char_literal48=(CommonTree)match(input,77,FOLLOW_77_in_type512); 
                            char_literal48_tree = (CommonTree)adaptor.dupNode(char_literal48);


                            adaptor.addChild(root_0, char_literal48_tree);


                            _last = (CommonTree)input.LT(1);
                            char_literal49=(CommonTree)match(input,78,FOLLOW_78_in_type514); 
                            char_literal49_tree = (CommonTree)adaptor.dupNode(char_literal49);


                            adaptor.addChild(root_0, char_literal49_tree);


                            retval.result = double[].class;

                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:245:3: 'String' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    string_literal50=(CommonTree)match(input,76,FOLLOW_76_in_type524); 
                    string_literal50_tree = (CommonTree)adaptor.dupNode(string_literal50);


                    adaptor.addChild(root_0, string_literal50_tree);


                    retval.result = String.class;

                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:245:38: ( '[' ']' )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==77) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:245:39: '[' ']'
                            {
                            _last = (CommonTree)input.LT(1);
                            char_literal51=(CommonTree)match(input,77,FOLLOW_77_in_type529); 
                            char_literal51_tree = (CommonTree)adaptor.dupNode(char_literal51);


                            adaptor.addChild(root_0, char_literal51_tree);


                            _last = (CommonTree)input.LT(1);
                            char_literal52=(CommonTree)match(input,78,FOLLOW_78_in_type531); 
                            char_literal52_tree = (CommonTree)adaptor.dupNode(char_literal52);


                            adaptor.addChild(root_0, char_literal52_tree);


                            retval.result = String[].class;

                            }
                            break;

                    }


                    }
                    break;
                case 6 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:246:3: 'Object' ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    string_literal53=(CommonTree)match(input,75,FOLLOW_75_in_type541); 
                    string_literal53_tree = (CommonTree)adaptor.dupNode(string_literal53);


                    adaptor.addChild(root_0, string_literal53_tree);


                    retval.result = Object.class;

                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:246:37: ( '[' ']' )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==77) ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:246:38: '[' ']'
                            {
                            _last = (CommonTree)input.LT(1);
                            char_literal54=(CommonTree)match(input,77,FOLLOW_77_in_type545); 
                            char_literal54_tree = (CommonTree)adaptor.dupNode(char_literal54);


                            adaptor.addChild(root_0, char_literal54_tree);


                            _last = (CommonTree)input.LT(1);
                            char_literal55=(CommonTree)match(input,78,FOLLOW_78_in_type547); 
                            char_literal55_tree = (CommonTree)adaptor.dupNode(char_literal55);


                            adaptor.addChild(root_0, char_literal55_tree);


                            retval.result = Object[].class;

                            }
                            break;

                    }


                    }
                    break;
                case 7 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:247:4: ID ( '[' ']' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    ID56=(CommonTree)match(input,ID,FOLLOW_ID_in_type558); 
                    ID56_tree = (CommonTree)adaptor.dupNode(ID56);


                    adaptor.addChild(root_0, ID56_tree);


                     
                    	    String fullname = "socialite.type."+(ID56!=null?ID56.getText():null);
                    	    try {  retval.result = Class.forName(fullname);
                    	    } catch (ClassNotFoundException e) {
                    	        throw new ParseException(getParser(), (ID56!=null?ID56.getLine():0)-1, (ID56!=null?ID56.getCharPositionInLine():0), "Class "+fullname+" cannot be resolved");	        
                    	    }
                    	

                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:253:4: ( '[' ']' )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==77) ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:253:5: '[' ']'
                            {
                            _last = (CommonTree)input.LT(1);
                            char_literal57=(CommonTree)match(input,77,FOLLOW_77_in_type563); 
                            char_literal57_tree = (CommonTree)adaptor.dupNode(char_literal57);


                            adaptor.addChild(root_0, char_literal57_tree);


                            _last = (CommonTree)input.LT(1);
                            char_literal58=(CommonTree)match(input,78,FOLLOW_78_in_type565); 
                            char_literal58_tree = (CommonTree)adaptor.dupNode(char_literal58);


                            adaptor.addChild(root_0, char_literal58_tree);


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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:256:1: rule returns [Object result] : ( ^( RULE head litlist ) )+ DOT_END ;
    public final SociaLiteRule.rule_return rule() throws RecognitionException {
        SociaLiteRule.rule_return retval = new SociaLiteRule.rule_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree RULE59=null;
        CommonTree DOT_END62=null;
        SociaLiteRule.head_return head60 =null;

        SociaLiteRule.litlist_return litlist61 =null;


        CommonTree RULE59_tree=null;
        CommonTree DOT_END62_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:257:9: ( ( ^( RULE head litlist ) )+ DOT_END )
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:257:10: ( ^( RULE head litlist ) )+ DOT_END
            {
            root_0 = (CommonTree)adaptor.nil();


             retval.result = new ArrayList<RuleDecl>(); 
                        List<Literal> first = null;
                     

            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:260:10: ( ^( RULE head litlist ) )+
            int cnt21=0;
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==RULE) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:260:11: ^( RULE head litlist )
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    {
            	    CommonTree _save_last_1 = _last;
            	    CommonTree _first_1 = null;
            	    CommonTree root_1 = (CommonTree)adaptor.nil();
            	    _last = (CommonTree)input.LT(1);
            	    RULE59=(CommonTree)match(input,RULE,FOLLOW_RULE_in_rule604); 
            	    RULE59_tree = (CommonTree)adaptor.dupNode(RULE59);


            	    root_1 = (CommonTree)adaptor.becomeRoot(RULE59_tree, root_1);


            	    match(input, Token.DOWN, null); 
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_head_in_rule606);
            	    head60=head();

            	    state._fsp--;

            	    adaptor.addChild(root_1, head60.getTree());


            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_litlist_in_rule608);
            	    litlist61=litlist();

            	    state._fsp--;

            	    adaptor.addChild(root_1, litlist61.getTree());


            	    match(input, Token.UP, null); 
            	    adaptor.addChild(root_0, root_1);
            	    _last = _save_last_1;
            	    }



            	                Predicate head = (Predicate)(head60!=null?head60.result:null);
            	                List<Literal> body;
            	                if (first==null) { 
            	                    first = (litlist61!=null?litlist61.result:null);
            	                    body = new ArrayList<Literal>((litlist61!=null?litlist61.result:null));
            	                } else {
            	                    body = (litlist61!=null?litlist61.result:null).subList(first.size(), (litlist61!=null?litlist61.result:null).size());
            	                }
            	                if (!headTmpVarAssigns.isEmpty()) {
            	                    for (AssignOp op:headTmpVarAssigns) {
            	                        body.add(new Expr(op));
            	                    }
            	                }
            	                ((List<RuleDecl>)retval.result).add(new RuleDecl(head.clone(), body));
            	            

            	    }
            	    break;

            	default :
            	    if ( cnt21 >= 1 ) break loop21;
                        EarlyExitException eee =
                            new EarlyExitException(21, input);
                        throw eee;
                }
                cnt21++;
            } while (true);


            _last = (CommonTree)input.LT(1);
            DOT_END62=(CommonTree)match(input,DOT_END,FOLLOW_DOT_END_in_rule616); 
            DOT_END62_tree = (CommonTree)adaptor.dupNode(DOT_END62);


            adaptor.addChild(root_0, DOT_END62_tree);


             headTmpVarAssigns.clear();
                      nextRule();
                    

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


    public static class head_return extends TreeRuleReturnScope {
        public Predicate result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "head"
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:280:1: head returns [Predicate result] : predicate ;
    public final SociaLiteRule.head_return head() throws RecognitionException {
        SociaLiteRule.head_return retval = new SociaLiteRule.head_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.predicate_return predicate63 =null;



        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:281:5: ( predicate )
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:281:7: predicate
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_predicate_in_head643);
            predicate63=predicate();

            state._fsp--;

            adaptor.addChild(root_0, predicate63.getTree());



                    retval.result = (predicate63!=null?predicate63.result:null);
            	    if (!tmpVarAssigns.isEmpty()) {
                        headTmpVarAssigns.addAll(tmpVarAssigns);
                        tmpVarAssigns.clear();
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
    // $ANTLR end "head"


    public static class litlist_return extends TreeRuleReturnScope {
        public List<Literal> result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "litlist"
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:289:1: litlist returns [List<Literal> result] :l1= literal (l2= literal )* ;
    public final SociaLiteRule.litlist_return litlist() throws RecognitionException {
        SociaLiteRule.litlist_return retval = new SociaLiteRule.litlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.literal_return l1 =null;

        SociaLiteRule.literal_return l2 =null;



        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:290:2: (l1= literal (l2= literal )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:290:4: l1= literal (l2= literal )*
            {
            root_0 = (CommonTree)adaptor.nil();


             retval.result = new ArrayList<Literal>(); 

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_literal_in_litlist667);
            l1=literal();

            state._fsp--;

            adaptor.addChild(root_0, l1.getTree());



            	    for (Variable v: dotVars) {
            	        String root=v.name.substring(0, v.name.indexOf('.'));
            	        Variable rootVar=getVariable(root);
            	        AssignDotVar a;
            	        try { a=new AssignDotVar(v, rootVar); } 
            	        catch (InternalException e) { throw new RuntimeException(e);}
            	        retval.result.add(new Expr(a));
            	    }
            	    dotVars.clear();

            	    if ((l1!=null?l1.result:null) instanceof List) {
            	        for(Literal o:(List<Literal>)(l1!=null?l1.result:null)) 
            	            retval.result.add(o);
            	    } else { retval.result.add((Literal)((l1!=null?l1.result:null))); }
            	

            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:307:2: (l2= literal )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==EXPR||LA22_0==PREDICATE) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:307:3: l2= literal
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_literal_in_litlist676);
            	    l2=literal();

            	    state._fsp--;

            	    adaptor.addChild(root_0, l2.getTree());



            	    	    for (Variable v: dotVars) {
            	    	        String root=v.name.substring(0, v.name.indexOf('.'));
            	    	        Variable rootVar=getVariable(root);
            	    	        AssignDotVar a;
            	    	        try { a=new AssignDotVar(v, rootVar); } 
            	    	        catch (InternalException e) { throw new RuntimeException(e);}
            	    	        retval.result.add(new Expr(a));
            	    	    }
            	    	    dotVars.clear();

            	    	    if ((l2!=null?l2.result:null) instanceof List) {
            	    	        for(Literal o:(List<Literal>)(l2!=null?l2.result:null)) 
            	    	            retval.result.add(o);
            	    	    } else { retval.result.add((Literal)(l2!=null?l2.result:null)); }
            	    	

            	    }
            	    break;

            	default :
            	    break loop22;
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:324:1: literal returns [Object result] : ( ^( PREDICATE ( NOT )? predicate ) | ^( EXPR expr ) );
    public final SociaLiteRule.literal_return literal() throws RecognitionException {
        SociaLiteRule.literal_return retval = new SociaLiteRule.literal_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree PREDICATE64=null;
        CommonTree NOT65=null;
        CommonTree EXPR67=null;
        SociaLiteRule.predicate_return predicate66 =null;

        SociaLiteRule.expr_return expr68 =null;


        CommonTree PREDICATE64_tree=null;
        CommonTree NOT65_tree=null;
        CommonTree EXPR67_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:325:2: ( ^( PREDICATE ( NOT )? predicate ) | ^( EXPR expr ) )
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==PREDICATE) ) {
                alt24=1;
            }
            else if ( (LA24_0==EXPR) ) {
                alt24=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;

            }
            switch (alt24) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:325:3: ^( PREDICATE ( NOT )? predicate )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    PREDICATE64=(CommonTree)match(input,PREDICATE,FOLLOW_PREDICATE_in_literal694); 
                    PREDICATE64_tree = (CommonTree)adaptor.dupNode(PREDICATE64);


                    root_1 = (CommonTree)adaptor.becomeRoot(PREDICATE64_tree, root_1);


                    match(input, Token.DOWN, null); 
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:325:15: ( NOT )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0==NOT) ) {
                        alt23=1;
                    }
                    switch (alt23) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:325:15: NOT
                            {
                            _last = (CommonTree)input.LT(1);
                            NOT65=(CommonTree)match(input,NOT,FOLLOW_NOT_in_literal696); 
                            NOT65_tree = (CommonTree)adaptor.dupNode(NOT65);


                            adaptor.addChild(root_1, NOT65_tree);


                            }
                            break;

                    }


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_predicate_in_literal699);
                    predicate66=predicate();

                    state._fsp--;

                    adaptor.addChild(root_1, predicate66.getTree());


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }



                    	    retval.result = (predicate66!=null?predicate66.result:null);
                    	    if ((NOT65!=null?NOT65.getText():null) != null) {
                    	        ((Predicate)(predicate66!=null?predicate66.result:null)).setNegated(); 
                    	    }
                    	    if (!tmpVarAssigns.isEmpty()) {
                    	        retval.result = new ArrayList<Literal>();
                    	        for (AssignOp op:tmpVarAssigns)
                    	            ((List<Literal>)retval.result).add(new Expr(op));
                    	        ((List<Literal>)retval.result).add((predicate66!=null?predicate66.result:null));
                    	        tmpVarAssigns.clear();
                    	    }
                    	

                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:337:5: ^( EXPR expr )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    EXPR67=(CommonTree)match(input,EXPR,FOLLOW_EXPR_in_literal706); 
                    EXPR67_tree = (CommonTree)adaptor.dupNode(EXPR67);


                    root_1 = (CommonTree)adaptor.becomeRoot(EXPR67_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expr_in_literal708);
                    expr68=expr();

                    state._fsp--;

                    adaptor.addChild(root_1, expr68.getTree());


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }



                    	    Expr e = new Expr((Op)(expr68!=null?expr68.result:null));
                    	    retval.result = e;
                    	    if (!tmpVarAssigns.isEmpty()) {
                    	        retval.result = new ArrayList<Literal>();
                    	        for (AssignOp op:tmpVarAssigns)
                    	            ((List<Literal>)retval.result).add(new Expr(op));
                    	        ((List<Literal>)retval.result).add(e);
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:349:1: predicate returns [Predicate result] : ID paramlist ;
    public final SociaLiteRule.predicate_return predicate() throws RecognitionException {
        SociaLiteRule.predicate_return retval = new SociaLiteRule.predicate_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree ID69=null;
        SociaLiteRule.paramlist_return paramlist70 =null;


        CommonTree ID69_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:350:2: ( ID paramlist )
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:350:4: ID paramlist
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            ID69=(CommonTree)match(input,ID,FOLLOW_ID_in_predicate726); 
            ID69_tree = (CommonTree)adaptor.dupNode(ID69);


            adaptor.addChild(root_0, ID69_tree);


            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_paramlist_in_predicate728);
            paramlist70=paramlist();

            state._fsp--;

            adaptor.addChild(root_0, paramlist70.getTree());


               TableDecl decl=tableDeclMap.get((ID69!=null?ID69.getText():null));
            	    if (decl==null) {
                        throw new ParseException(getParser(), (ID69!=null?ID69.getLine():0)-1, (ID69!=null?ID69.getCharPositionInLine():0), "Table "+(ID69!=null?ID69.getText():null)+" is not declared");	 
                    } else {
                       try {
                           decl.checkTypes((paramlist70!=null?paramlist70.result:null));
                       } catch(InternalException e) {
                           throw new ParseException(getParser(), (ID69!=null?ID69.getLine():0)-1, (ID69!=null?ID69.getCharPositionInLine():0), e.getMessage());
                       }
                    }
                    retval.result = new Predicate((ID69!=null?ID69.getText():null), (paramlist70!=null?paramlist70.result:null));
                

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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:364:1: function returns [Function result] : ^( FUNC dotname ( fparamlist )? ) ;
    public final SociaLiteRule.function_return function() throws RecognitionException {
        SociaLiteRule.function_return retval = new SociaLiteRule.function_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree FUNC71=null;
        SociaLiteRule.dotname_return dotname72 =null;

        SociaLiteRule.fparamlist_return fparamlist73 =null;


        CommonTree FUNC71_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:365:2: ( ^( FUNC dotname ( fparamlist )? ) )
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:365:4: ^( FUNC dotname ( fparamlist )? )
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();
            _last = (CommonTree)input.LT(1);
            FUNC71=(CommonTree)match(input,FUNC,FOLLOW_FUNC_in_function749); 
            FUNC71_tree = (CommonTree)adaptor.dupNode(FUNC71);


            root_1 = (CommonTree)adaptor.becomeRoot(FUNC71_tree, root_1);


            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_dotname_in_function751);
            dotname72=dotname();

            state._fsp--;

            adaptor.addChild(root_1, dotname72.getTree());


            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:365:19: ( fparamlist )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==COMPOUND_EXPR||LA25_0==FUNCTION||LA25_0==TERM||(LA25_0 >= 59 && LA25_0 <= 60)||LA25_0==62||LA25_0==64||LA25_0==89) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:365:19: fparamlist
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_fparamlist_in_function753);
                    fparamlist73=fparamlist();

                    state._fsp--;

                    adaptor.addChild(root_1, fparamlist73.getTree());


                    }
                    break;

            }


            match(input, Token.UP, null); 
            adaptor.addChild(root_0, root_1);
            _last = _save_last_1;
            }



            	    retval.result = new Function((dotname72!=null?dotname72.result:null), (fparamlist73!=null?fparamlist73.result:null));
            	

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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:369:1: param returns [Object result] : simpleExpr ;
    public final SociaLiteRule.param_return param() throws RecognitionException {
        SociaLiteRule.param_return retval = new SociaLiteRule.param_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.simpleExpr_return simpleExpr74 =null;



        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:370:2: ( simpleExpr )
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:370:4: simpleExpr
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_simpleExpr_in_param771);
            simpleExpr74=simpleExpr();

            state._fsp--;

            adaptor.addChild(root_0, simpleExpr74.getTree());



            	    if (tmpVarAssignHasFunc()) {
            	        throw new ParseException(getParser(), 
            	                        (simpleExpr74!=null?((CommonTree)simpleExpr74.tree):null).getLine()-1,
            	                        (simpleExpr74!=null?((CommonTree)simpleExpr74.tree):null).getCharPositionInLine()+1, 
            	                        "Cannot use functions with operators in a param list");
            	    }
            	    retval.result = (simpleExpr74!=null?simpleExpr74.result:null);
            	    /*if ((simpleExpr74!=null?simpleExpr74.result:null) instanceof TypeCast) {
            	        TypeCast cast=(TypeCast)retval.result;
            	        if (cast.arg instanceof Function) {
            	           throw new ParseException(getParser(), 
            	                        (simpleExpr74!=null?((CommonTree)simpleExpr74.tree):null).getLine()-1,
            	                        (simpleExpr74!=null?((CommonTree)simpleExpr74.tree):null).getCharPositionInLine()+1, 
            	                        "Cannot use type cast to aggregate functions");
            	        }
            	    }*/
            	    if ((simpleExpr74!=null?simpleExpr74.result:null) instanceof Function) {
            	        retval.result = new AggrFunction((Function)(simpleExpr74!=null?simpleExpr74.result:null)); 
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:391:1: fparam returns [Object result] : simpleExpr ;
    public final SociaLiteRule.fparam_return fparam() throws RecognitionException {
        SociaLiteRule.fparam_return retval = new SociaLiteRule.fparam_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.simpleExpr_return simpleExpr75 =null;



        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:392:2: ( simpleExpr )
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:392:4: simpleExpr
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_simpleExpr_in_fparam785);
            simpleExpr75=simpleExpr();

            state._fsp--;

            adaptor.addChild(root_0, simpleExpr75.getTree());


             retval.result = (simpleExpr75!=null?simpleExpr75.result:null); 

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
        public List<Param> result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "paramlist"
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:394:1: paramlist returns [List<Param> result] :p1= param (p2= param )* ;
    public final SociaLiteRule.paramlist_return paramlist() throws RecognitionException {
        SociaLiteRule.paramlist_return retval = new SociaLiteRule.paramlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.param_return p1 =null;

        SociaLiteRule.param_return p2 =null;



        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:395:2: (p1= param (p2= param )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:395:3: p1= param (p2= param )*
            {
            root_0 = (CommonTree)adaptor.nil();


             retval.result = new ArrayList<Param>(); 

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_param_in_paramlist804);
            p1=param();

            state._fsp--;

            adaptor.addChild(root_0, p1.getTree());


             
            	    if (isSimpleIntValue((p1!=null?p1.result:null))) {
            	        retval.result.add(new Const(getSimpleIntValue((p1!=null?p1.result:null)), getNextConstId()));
            	    } else if (((p1!=null?p1.result:null) instanceof BinOp)||((p1!=null?p1.result:null) instanceof UnaryOp)) {
            	        Variable tmpVar = addTmpVarAssign((p1!=null?p1.result:null));
            	        retval.result.add(tmpVar);
            	    } else if ((p1!=null?p1.result:null) instanceof Op) {
            	        throw new ParseException(getParser(), 
            	                        (p1!=null?((CommonTree)p1.tree):null).getLine()-1, (p1!=null?((CommonTree)p1.tree):null).getCharPositionInLine()+1, 
            	                        "Cannot use "+(p1!=null?p1.result:null)+" in parameter list"); 
            	    } else { retval.result.add((Param)((p1!=null?p1.result:null))); }
            	

            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:408:2: (p2= param )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==COMPOUND_EXPR||LA26_0==FUNCTION||LA26_0==TERM||(LA26_0 >= 59 && LA26_0 <= 60)||LA26_0==62||LA26_0==64||LA26_0==89) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:408:3: p2= param
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_param_in_paramlist812);
            	    p2=param();

            	    state._fsp--;

            	    adaptor.addChild(root_0, p2.getTree());


            	     
            	    	    if (isSimpleIntValue((p2!=null?p2.result:null))) {
            	    	        retval.result.add(new Const(getSimpleIntValue((p2!=null?p2.result:null)), getNextConstId()));
            	    	    } else if (((p2!=null?p2.result:null) instanceof BinOp)||((p2!=null?p2.result:null) instanceof UnaryOp)) {
            	    	        Variable tmpVar = addTmpVarAssign((p2!=null?p2.result:null));
            	    	        retval.result.add(tmpVar);
            	    	    } else if ((p2!=null?p2.result:null) instanceof Op) {
            	    	        throw new ParseException(getParser(),
            	    	                        (p2!=null?((CommonTree)p2.tree):null).getLine()-1, (p2!=null?((CommonTree)p2.tree):null).getCharPositionInLine()+1, 
            	    	                        "Cannot use "+(p2!=null?p2.result:null)+" in parameter list"); 
            	    	    } else { retval.result.add((Param)((p2!=null?p2.result:null))); }
            	    	

            	    }
            	    break;

            	default :
            	    break loop26;
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
        public List<Param> result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "fparamlist"
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:422:1: fparamlist returns [List<Param> result] :p1= fparam (p2= fparam )* ;
    public final SociaLiteRule.fparamlist_return fparamlist() throws RecognitionException {
        SociaLiteRule.fparamlist_return retval = new SociaLiteRule.fparamlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.fparam_return p1 =null;

        SociaLiteRule.fparam_return p2 =null;



        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:423:2: (p1= fparam (p2= fparam )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:423:3: p1= fparam (p2= fparam )*
            {
            root_0 = (CommonTree)adaptor.nil();


             retval.result = new ArrayList<Param>(); 

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_fparam_in_fparamlist835);
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
            	    } else { retval.result.add((Param)((p1!=null?p1.result:null))); }
            	

            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:434:2: (p2= fparam )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==COMPOUND_EXPR||LA27_0==FUNCTION||LA27_0==TERM||(LA27_0 >= 59 && LA27_0 <= 60)||LA27_0==62||LA27_0==64||LA27_0==89) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:434:3: p2= fparam
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_fparam_in_fparamlist843);
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
            	    	    } else { retval.result.add((Param)((p2!=null?p2.result:null))); }
            	    	

            	    }
            	    break;

            	default :
            	    break loop27;
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:447:1: term returns [Object result] : ( ^( T_INT INT ) | ^( T_FLOAT FLOAT ) | ^( T_STR STRING ) | ^( T_UTF8 UTF8 ) | ^( T_VAR dotname ) );
    public final SociaLiteRule.term_return term() throws RecognitionException {
        SociaLiteRule.term_return retval = new SociaLiteRule.term_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree T_INT76=null;
        CommonTree INT77=null;
        CommonTree T_FLOAT78=null;
        CommonTree FLOAT79=null;
        CommonTree T_STR80=null;
        CommonTree STRING81=null;
        CommonTree T_UTF882=null;
        CommonTree UTF883=null;
        CommonTree T_VAR84=null;
        SociaLiteRule.dotname_return dotname85 =null;


        CommonTree T_INT76_tree=null;
        CommonTree INT77_tree=null;
        CommonTree T_FLOAT78_tree=null;
        CommonTree FLOAT79_tree=null;
        CommonTree T_STR80_tree=null;
        CommonTree STRING81_tree=null;
        CommonTree T_UTF882_tree=null;
        CommonTree UTF883_tree=null;
        CommonTree T_VAR84_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:448:2: ( ^( T_INT INT ) | ^( T_FLOAT FLOAT ) | ^( T_STR STRING ) | ^( T_UTF8 UTF8 ) | ^( T_VAR dotname ) )
            int alt28=5;
            switch ( input.LA(1) ) {
            case T_INT:
                {
                alt28=1;
                }
                break;
            case T_FLOAT:
                {
                alt28=2;
                }
                break;
            case T_STR:
                {
                alt28=3;
                }
                break;
            case T_UTF8:
                {
                alt28=4;
                }
                break;
            case T_VAR:
                {
                alt28=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;

            }

            switch (alt28) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:448:3: ^( T_INT INT )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    T_INT76=(CommonTree)match(input,T_INT,FOLLOW_T_INT_in_term863); 
                    T_INT76_tree = (CommonTree)adaptor.dupNode(T_INT76);


                    root_1 = (CommonTree)adaptor.becomeRoot(T_INT76_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    INT77=(CommonTree)match(input,INT,FOLLOW_INT_in_term865); 
                    INT77_tree = (CommonTree)adaptor.dupNode(INT77);


                    adaptor.addChild(root_1, INT77_tree);


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }



                    	    if ((INT77!=null?INT77.getText():null).endsWith("l") || (INT77!=null?INT77.getText():null).endsWith("L")) {
                    	        Long v = new Long(Long.parseLong((INT77!=null?INT77.getText():null).substring(0, (INT77!=null?INT77.getText():null).length()-1)));
                    	        retval.result = new Const(v, getNextConstId());
                    	    } else {
                    	        Integer v = new Integer(Integer.parseInt((INT77!=null?INT77.getText():null)));
                    	        retval.result = new Const(v, getNextConstId());
                    	    }
                    	 

                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:456:6: ^( T_FLOAT FLOAT )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    T_FLOAT78=(CommonTree)match(input,T_FLOAT,FOLLOW_T_FLOAT_in_term873); 
                    T_FLOAT78_tree = (CommonTree)adaptor.dupNode(T_FLOAT78);


                    root_1 = (CommonTree)adaptor.becomeRoot(T_FLOAT78_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    FLOAT79=(CommonTree)match(input,FLOAT,FOLLOW_FLOAT_in_term875); 
                    FLOAT79_tree = (CommonTree)adaptor.dupNode(FLOAT79);


                    adaptor.addChild(root_1, FLOAT79_tree);


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }



                     	    if ((FLOAT79!=null?FLOAT79.getText():null).endsWith("f") || (FLOAT79!=null?FLOAT79.getText():null).endsWith("F")) {
                     	        Float v = new Float(Float.parseFloat((FLOAT79!=null?FLOAT79.getText():null).substring(0, (FLOAT79!=null?FLOAT79.getText():null).length()-1)));
                     	        retval.result = new Const(v, getNextConstId());
                     	    } else if ((FLOAT79!=null?FLOAT79.getText():null).endsWith("d") || (FLOAT79!=null?FLOAT79.getText():null).endsWith("D")) {
                     	        Double v = new Double(Double.parseDouble((FLOAT79!=null?FLOAT79.getText():null).substring(0, (FLOAT79!=null?FLOAT79.getText():null).length()-1)));
                     	        retval.result = new Const(v, getNextConstId());
                     	    } else {
                         	        Double v = new Double(Double.parseDouble((FLOAT79!=null?FLOAT79.getText():null)));
                         	        retval.result = new Const(v, getNextConstId());
                     	    }
                    	

                    }
                    break;
                case 3 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:468:3: ^( T_STR STRING )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    T_STR80=(CommonTree)match(input,T_STR,FOLLOW_T_STR_in_term884); 
                    T_STR80_tree = (CommonTree)adaptor.dupNode(T_STR80);


                    root_1 = (CommonTree)adaptor.becomeRoot(T_STR80_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    STRING81=(CommonTree)match(input,STRING,FOLLOW_STRING_in_term886); 
                    STRING81_tree = (CommonTree)adaptor.dupNode(STRING81);


                    adaptor.addChild(root_1, STRING81_tree);


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }


                    String v = new String((STRING81!=null?STRING81.getText():null).substring(1, (STRING81!=null?STRING81.getText():null).length()-1)); retval.result = new Const(v, getNextConstId()); 

                    }
                    break;
                case 4 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:469:3: ^( T_UTF8 UTF8 )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    T_UTF882=(CommonTree)match(input,T_UTF8,FOLLOW_T_UTF8_in_term895); 
                    T_UTF882_tree = (CommonTree)adaptor.dupNode(T_UTF882);


                    root_1 = (CommonTree)adaptor.becomeRoot(T_UTF882_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    UTF883=(CommonTree)match(input,UTF8,FOLLOW_UTF8_in_term897); 
                    UTF883_tree = (CommonTree)adaptor.dupNode(UTF883);


                    adaptor.addChild(root_1, UTF883_tree);


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }


                     Utf8 v =  new Utf8((UTF883!=null?UTF883.getText():null).substring(2, (UTF883!=null?UTF883.getText():null).length()-1)); retval.result = new Const(v, getNextConstId()); 

                    }
                    break;
                case 5 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:470:3: ^( T_VAR dotname )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    T_VAR84=(CommonTree)match(input,T_VAR,FOLLOW_T_VAR_in_term906); 
                    T_VAR84_tree = (CommonTree)adaptor.dupNode(T_VAR84);


                    root_1 = (CommonTree)adaptor.becomeRoot(T_VAR84_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_dotname_in_term908);
                    dotname85=dotname();

                    state._fsp--;

                    adaptor.addChild(root_1, dotname85.getTree());


                    match(input, Token.UP, null); 
                    adaptor.addChild(root_0, root_1);
                    _last = _save_last_1;
                    }



                    	    retval.result = getVariable((dotname85!=null?dotname85.result:null));
                    	    if ((dotname85!=null?dotname85.result:null).indexOf('.')>=0) {
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:476:1: expr returns [Object result] : ( ^(cmpStr= ( '<' | '<=' | '>' | '>=' | '==' | '!=' ) e1= simpleExpr e2= simpleExpr ) | ^(eq= '=' e1= simpleExpr e2= simpleExpr ) | ^( MULTI_ASSIGN varlist function (c= cast )? ) );
    public final SociaLiteRule.expr_return expr() throws RecognitionException {
        SociaLiteRule.expr_return retval = new SociaLiteRule.expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree cmpStr=null;
        CommonTree eq=null;
        CommonTree MULTI_ASSIGN86=null;
        SociaLiteRule.simpleExpr_return e1 =null;

        SociaLiteRule.simpleExpr_return e2 =null;

        SociaLiteRule.cast_return c =null;

        SociaLiteRule.varlist_return varlist87 =null;

        SociaLiteRule.function_return function88 =null;


        CommonTree cmpStr_tree=null;
        CommonTree eq_tree=null;
        CommonTree MULTI_ASSIGN86_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:477:2: ( ^(cmpStr= ( '<' | '<=' | '>' | '>=' | '==' | '!=' ) e1= simpleExpr e2= simpleExpr ) | ^(eq= '=' e1= simpleExpr e2= simpleExpr ) | ^( MULTI_ASSIGN varlist function (c= cast )? ) )
            int alt30=3;
            switch ( input.LA(1) ) {
            case 55:
            case 68:
            case 69:
            case 71:
            case 72:
            case 73:
                {
                alt30=1;
                }
                break;
            case 70:
                {
                alt30=2;
                }
                break;
            case MULTI_ASSIGN:
                {
                alt30=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;

            }

            switch (alt30) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:477:3: ^(cmpStr= ( '<' | '<=' | '>' | '>=' | '==' | '!=' ) e1= simpleExpr e2= simpleExpr )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    cmpStr=(CommonTree)input.LT(1);

                    if ( input.LA(1)==55||(input.LA(1) >= 68 && input.LA(1) <= 69)||(input.LA(1) >= 71 && input.LA(1) <= 73) ) {
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
                    pushFollow(FOLLOW_simpleExpr_in_expr941);
                    e1=simpleExpr();

                    state._fsp--;

                    adaptor.addChild(root_1, e1.getTree());


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_simpleExpr_in_expr945);
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:482:5: ^(eq= '=' e1= simpleExpr e2= simpleExpr )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    eq=(CommonTree)match(input,70,FOLLOW_70_in_expr954); 
                    eq_tree = (CommonTree)adaptor.dupNode(eq);


                    root_1 = (CommonTree)adaptor.becomeRoot(eq_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_simpleExpr_in_expr958);
                    e1=simpleExpr();

                    state._fsp--;

                    adaptor.addChild(root_1, e1.getTree());


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_simpleExpr_in_expr962);
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:497:6: ^( MULTI_ASSIGN varlist function (c= cast )? )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    MULTI_ASSIGN86=(CommonTree)match(input,MULTI_ASSIGN,FOLLOW_MULTI_ASSIGN_in_expr970); 
                    MULTI_ASSIGN86_tree = (CommonTree)adaptor.dupNode(MULTI_ASSIGN86);


                    root_1 = (CommonTree)adaptor.becomeRoot(MULTI_ASSIGN86_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_varlist_in_expr972);
                    varlist87=varlist();

                    state._fsp--;

                    adaptor.addChild(root_1, varlist87.getTree());


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_function_in_expr974);
                    function88=function();

                    state._fsp--;

                    adaptor.addChild(root_1, function88.getTree());


                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:497:39: (c= cast )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==ID||(LA29_0 >= 75 && LA29_0 <= 76)||LA29_0==82||LA29_0==84||(LA29_0 >= 87 && LA29_0 <= 88)) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:497:39: c= cast
                            {
                            _last = (CommonTree)input.LT(1);
                            pushFollow(FOLLOW_cast_in_expr978);
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
                    	        retval.result = new AssignOp((varlist87!=null?varlist87.result:null), typecast, (function88!=null?function88.result:null)); 
                    	    } catch (InternalException e) {
                    	        int line=(varlist87!=null?((CommonTree)varlist87.tree):null).getLine()-1, pos=(varlist87!=null?((CommonTree)varlist87.tree):null).getCharPositionInLine()+1;
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:510:1: simpleExpr returns [Object result] : ( multExpr | ^(op= ( '+' | '-' ) e1= simpleExpr e2= simpleExpr ) );
    public final SociaLiteRule.simpleExpr_return simpleExpr() throws RecognitionException {
        SociaLiteRule.simpleExpr_return retval = new SociaLiteRule.simpleExpr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree op=null;
        SociaLiteRule.simpleExpr_return e1 =null;

        SociaLiteRule.simpleExpr_return e2 =null;

        SociaLiteRule.multExpr_return multExpr89 =null;


        CommonTree op_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:511:2: ( multExpr | ^(op= ( '+' | '-' ) e1= simpleExpr e2= simpleExpr ) )
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==COMPOUND_EXPR||LA31_0==FUNCTION||LA31_0==TERM||LA31_0==59||LA31_0==64||LA31_0==89) ) {
                alt31=1;
            }
            else if ( (LA31_0==60||LA31_0==62) ) {
                alt31=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;

            }
            switch (alt31) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:511:3: multExpr
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_multExpr_in_simpleExpr993);
                    multExpr89=multExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, multExpr89.getTree());


                    retval.result = (multExpr89!=null?multExpr89.result:null);

                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:512:3: ^(op= ( '+' | '-' ) e1= simpleExpr e2= simpleExpr )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    op=(CommonTree)input.LT(1);

                    if ( input.LA(1)==60||input.LA(1)==62 ) {
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
                    pushFollow(FOLLOW_simpleExpr_in_simpleExpr1010);
                    e1=simpleExpr();

                    state._fsp--;

                    adaptor.addChild(root_1, e1.getTree());


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_simpleExpr_in_simpleExpr1014);
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:522:1: multExpr returns [Object result] : ( exprValue | ^(op= ( '*' | '/' | 'mod' ) v1= multExpr v2= multExpr ) );
    public final SociaLiteRule.multExpr_return multExpr() throws RecognitionException {
        SociaLiteRule.multExpr_return retval = new SociaLiteRule.multExpr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree op=null;
        SociaLiteRule.multExpr_return v1 =null;

        SociaLiteRule.multExpr_return v2 =null;

        SociaLiteRule.exprValue_return exprValue90 =null;


        CommonTree op_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:523:2: ( exprValue | ^(op= ( '*' | '/' | 'mod' ) v1= multExpr v2= multExpr ) )
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==COMPOUND_EXPR||LA32_0==FUNCTION||LA32_0==TERM) ) {
                alt32=1;
            }
            else if ( (LA32_0==59||LA32_0==64||LA32_0==89) ) {
                alt32=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;

            }
            switch (alt32) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:523:4: exprValue
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_exprValue_in_multExpr1029);
                    exprValue90=exprValue();

                    state._fsp--;

                    adaptor.addChild(root_0, exprValue90.getTree());


                     retval.result = (exprValue90!=null?exprValue90.result:null); 

                    }
                    break;
                case 2 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:524:3: ^(op= ( '*' | '/' | 'mod' ) v1= multExpr v2= multExpr )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    op=(CommonTree)input.LT(1);

                    if ( input.LA(1)==59||input.LA(1)==64||input.LA(1)==89 ) {
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
                    pushFollow(FOLLOW_multExpr_in_multExpr1048);
                    v1=multExpr();

                    state._fsp--;

                    adaptor.addChild(root_1, v1.getTree());


                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_multExpr_in_multExpr1052);
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:534:1: compExpr returns [Object result] : e1= simpleExpr ;
    public final SociaLiteRule.compExpr_return compExpr() throws RecognitionException {
        SociaLiteRule.compExpr_return retval = new SociaLiteRule.compExpr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.simpleExpr_return e1 =null;



        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:535:2: (e1= simpleExpr )
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:535:4: e1= simpleExpr
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_simpleExpr_in_compExpr1069);
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:537:1: varlist returns [Object result] : id1= dotname (id2= dotname )+ ;
    public final SociaLiteRule.varlist_return varlist() throws RecognitionException {
        SociaLiteRule.varlist_return retval = new SociaLiteRule.varlist_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.dotname_return id1 =null;

        SociaLiteRule.dotname_return id2 =null;



        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:538:2: (id1= dotname (id2= dotname )+ )
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:538:3: id1= dotname (id2= dotname )+
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_dotname_in_varlist1089);
            id1=dotname();

            state._fsp--;

            adaptor.addChild(root_0, id1.getTree());


             
            	    List<Variable> vars = new ArrayList<Variable>();
            	    vars.add(getVariable((id1!=null?id1.result:null))); retval.result = vars; 

            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:541:2: (id2= dotname )+
            int cnt33=0;
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==ID) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:541:3: id2= dotname
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_dotname_in_varlist1096);
            	    id2=dotname();

            	    state._fsp--;

            	    adaptor.addChild(root_0, id2.getTree());


            	     
            	    	    vars.add(getVariable((id2!=null?id2.result:null)));

            	    }
            	    break;

            	default :
            	    if ( cnt33 >= 1 ) break loop33;
                        EarlyExitException eee =
                            new EarlyExitException(33, input);
                        throw eee;
                }
                cnt33++;
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:544:1: exprValue returns [Object result] : ( ^( TERM term (neg= '-' )? (c= cast )? ) | ^( FUNCTION function (c= cast )? ) | ^( COMPOUND_EXPR compExpr (c= cast )? ) );
    public final SociaLiteRule.exprValue_return exprValue() throws RecognitionException {
        SociaLiteRule.exprValue_return retval = new SociaLiteRule.exprValue_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree neg=null;
        CommonTree TERM91=null;
        CommonTree FUNCTION93=null;
        CommonTree COMPOUND_EXPR95=null;
        SociaLiteRule.cast_return c =null;

        SociaLiteRule.term_return term92 =null;

        SociaLiteRule.function_return function94 =null;

        SociaLiteRule.compExpr_return compExpr96 =null;


        CommonTree neg_tree=null;
        CommonTree TERM91_tree=null;
        CommonTree FUNCTION93_tree=null;
        CommonTree COMPOUND_EXPR95_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:545:2: ( ^( TERM term (neg= '-' )? (c= cast )? ) | ^( FUNCTION function (c= cast )? ) | ^( COMPOUND_EXPR compExpr (c= cast )? ) )
            int alt38=3;
            switch ( input.LA(1) ) {
            case TERM:
                {
                alt38=1;
                }
                break;
            case FUNCTION:
                {
                alt38=2;
                }
                break;
            case COMPOUND_EXPR:
                {
                alt38=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 38, 0, input);

                throw nvae;

            }

            switch (alt38) {
                case 1 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:545:3: ^( TERM term (neg= '-' )? (c= cast )? )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    TERM91=(CommonTree)match(input,TERM,FOLLOW_TERM_in_exprValue1113); 
                    TERM91_tree = (CommonTree)adaptor.dupNode(TERM91);


                    root_1 = (CommonTree)adaptor.becomeRoot(TERM91_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_term_in_exprValue1115);
                    term92=term();

                    state._fsp--;

                    adaptor.addChild(root_1, term92.getTree());


                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:545:15: (neg= '-' )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==62) ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:545:16: neg= '-'
                            {
                            _last = (CommonTree)input.LT(1);
                            neg=(CommonTree)match(input,62,FOLLOW_62_in_exprValue1120); 
                            neg_tree = (CommonTree)adaptor.dupNode(neg);


                            adaptor.addChild(root_1, neg_tree);


                            }
                            break;

                    }


                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:545:27: (c= cast )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==ID||(LA35_0 >= 75 && LA35_0 <= 76)||LA35_0==82||LA35_0==84||(LA35_0 >= 87 && LA35_0 <= 88)) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:545:27: c= cast
                            {
                            _last = (CommonTree)input.LT(1);
                            pushFollow(FOLLOW_cast_in_exprValue1126);
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



                    	    retval.result = (term92!=null?term92.result:null);
                    	    if ((c!=null?c.result:null) != null) {
                    	        TypeCast cast = new TypeCast((c!=null?c.result:null), retval.result);
                    	        retval.result = cast;
                    	    }
                    	    if ((neg!=null?neg.getText():null) != null) {
                    	        if ((term92!=null?term92.result:null) instanceof Const) {
                    	            ((Const)(term92!=null?term92.result:null)).negate();
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
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:562:3: ^( FUNCTION function (c= cast )? )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    FUNCTION93=(CommonTree)match(input,FUNCTION,FOLLOW_FUNCTION_in_exprValue1135); 
                    FUNCTION93_tree = (CommonTree)adaptor.dupNode(FUNCTION93);


                    root_1 = (CommonTree)adaptor.becomeRoot(FUNCTION93_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_function_in_exprValue1137);
                    function94=function();

                    state._fsp--;

                    adaptor.addChild(root_1, function94.getTree());


                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:562:24: (c= cast )?
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==ID||(LA36_0 >= 75 && LA36_0 <= 76)||LA36_0==82||LA36_0==84||(LA36_0 >= 87 && LA36_0 <= 88)) ) {
                        alt36=1;
                    }
                    switch (alt36) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:562:24: c= cast
                            {
                            _last = (CommonTree)input.LT(1);
                            pushFollow(FOLLOW_cast_in_exprValue1141);
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


                     
                    	    retval.result = (function94!=null?function94.result:null); 
                    	    if ((c!=null?c.result:null) != null) {
                    	        Object tmpvar = addTmpVarAssign((function94!=null?function94.result:null));
                    	        TypeCast cast = new TypeCast((c!=null?c.result:null), tmpvar);
                    	        retval.result = cast;
                    	    }
                    	

                    }
                    break;
                case 3 :
                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:570:3: ^( COMPOUND_EXPR compExpr (c= cast )? )
                    {
                    root_0 = (CommonTree)adaptor.nil();


                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    _last = (CommonTree)input.LT(1);
                    COMPOUND_EXPR95=(CommonTree)match(input,COMPOUND_EXPR,FOLLOW_COMPOUND_EXPR_in_exprValue1150); 
                    COMPOUND_EXPR95_tree = (CommonTree)adaptor.dupNode(COMPOUND_EXPR95);


                    root_1 = (CommonTree)adaptor.becomeRoot(COMPOUND_EXPR95_tree, root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_compExpr_in_exprValue1152);
                    compExpr96=compExpr();

                    state._fsp--;

                    adaptor.addChild(root_1, compExpr96.getTree());


                    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:570:29: (c= cast )?
                    int alt37=2;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0==ID||(LA37_0 >= 75 && LA37_0 <= 76)||LA37_0==82||LA37_0==84||(LA37_0 >= 87 && LA37_0 <= 88)) ) {
                        alt37=1;
                    }
                    switch (alt37) {
                        case 1 :
                            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:570:29: c= cast
                            {
                            _last = (CommonTree)input.LT(1);
                            pushFollow(FOLLOW_cast_in_exprValue1156);
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


                     
                    	    retval.result = (compExpr96!=null?compExpr96.result:null);
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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:578:1: cast returns [Class result] : type ;
    public final SociaLiteRule.cast_return cast() throws RecognitionException {
        SociaLiteRule.cast_return retval = new SociaLiteRule.cast_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        SociaLiteRule.type_return type97 =null;



        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:579:2: ( type )
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:579:3: type
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_type_in_cast1173);
            type97=type();

            state._fsp--;

            adaptor.addChild(root_0, type97.getTree());


            retval.result = (type97!=null?type97.result:null); 

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
    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:581:1: dotname returns [String result] : ID ( DOT_ID )* ;
    public final SociaLiteRule.dotname_return dotname() throws RecognitionException {
        SociaLiteRule.dotname_return retval = new SociaLiteRule.dotname_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree ID98=null;
        CommonTree DOT_ID99=null;

        CommonTree ID98_tree=null;
        CommonTree DOT_ID99_tree=null;

        try {
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:582:2: ( ID ( DOT_ID )* )
            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:582:3: ID ( DOT_ID )*
            {
            root_0 = (CommonTree)adaptor.nil();


            _last = (CommonTree)input.LT(1);
            ID98=(CommonTree)match(input,ID,FOLLOW_ID_in_dotname1187); 
            ID98_tree = (CommonTree)adaptor.dupNode(ID98);


            adaptor.addChild(root_0, ID98_tree);


             retval.result = new String((ID98!=null?ID98.getText():null)); 

            // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:583:3: ( DOT_ID )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==DOT_ID) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // /Users/jiwon/workspace/socialite/grammar/SociaLiteRule.g:583:4: DOT_ID
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    DOT_ID99=(CommonTree)match(input,DOT_ID,FOLLOW_DOT_ID_in_dotname1194); 
            	    DOT_ID99_tree = (CommonTree)adaptor.dupNode(DOT_ID99);


            	    adaptor.addChild(root_0, DOT_ID99_tree);


            	     retval.result += new String((DOT_ID99!=null?DOT_ID99.getText():null)); 

            	    }
            	    break;

            	default :
            	    break loop39;
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


 

    public static final BitSet FOLLOW_stat_in_prog74 = new BitSet(new long[]{0x0000050000012080L});
    public static final BitSet FOLLOW_EOF_in_prog80 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_decl_in_stat93 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule_in_stat99 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_query_in_stat105 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_stmt_in_stat110 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CLEAR_in_table_stmt127 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_table_stmt129 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DROP_in_table_stmt138 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_table_stmt140 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_QUERY_in_query158 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_predicate_in_query160 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DECL_in_table_decl180 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_table_decl182 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_decls_in_table_decl184 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_OPTION_in_table_decl187 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_table_opts_in_table_decl189 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_t_opt_in_table_opts223 = new BitSet(new long[]{0x0000083088801002L});
    public static final BitSet FOLLOW_t_opt_in_table_opts231 = new BitSet(new long[]{0x0000083088801002L});
    public static final BitSet FOLLOW_SORT_BY_in_t_opt250 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_t_opt254 = new BitSet(new long[]{0x0000100000000008L});
    public static final BitSet FOLLOW_SORT_ORDER_in_t_opt259 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ORDER_BY_in_t_opt269 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_t_opt271 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INDEX_BY_in_t_opt279 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_t_opt281 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GROUP_BY_in_t_opt289 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_INT_in_t_opt291 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PREDEFINED_in_t_opt299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONCURRENT_in_t_opt306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MULTISET_in_t_opt313 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COL_DECLS_in_decls330 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_col_decls_in_decls332 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_DECL_in_decls335 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_decls_in_decls339 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_col_decl_in_col_decls359 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_col_decl_in_col_decls368 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COL_DECL_in_col_decl389 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_col_decl391 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_ID_in_col_decl393 = new BitSet(new long[]{0x0000020020000028L});
    public static final BitSet FOLLOW_col_opt_in_col_decl395 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_RANGE_in_col_opt413 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_INT_in_col_opt417 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_INT_in_col_opt421 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ITER_in_col_opt429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BIT_in_col_opt437 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_INT_in_col_opt441 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_87_in_type457 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_type462 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_type464 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_type475 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_type479 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_type481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_type490 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_type495 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_type497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_type507 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_type512 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_type514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_type524 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_type529 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_type531 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_type541 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_type545 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_type547 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_type558 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_type563 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_type565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_in_rule604 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_head_in_rule606 = new BitSet(new long[]{0x0000004000080000L});
    public static final BitSet FOLLOW_litlist_in_rule608 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DOT_END_in_rule616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_predicate_in_head643 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_litlist667 = new BitSet(new long[]{0x0000004000080002L});
    public static final BitSet FOLLOW_literal_in_litlist676 = new BitSet(new long[]{0x0000004000080002L});
    public static final BitSet FOLLOW_PREDICATE_in_literal694 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_NOT_in_literal696 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_predicate_in_literal699 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXPR_in_literal706 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_literal708 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ID_in_predicate726 = new BitSet(new long[]{0x5800400000400800L,0x0000000002000001L});
    public static final BitSet FOLLOW_paramlist_in_predicate728 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FUNC_in_function749 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_dotname_in_function751 = new BitSet(new long[]{0x5800400000400808L,0x0000000002000001L});
    public static final BitSet FOLLOW_fparamlist_in_function753 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_simpleExpr_in_param771 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleExpr_in_fparam785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_param_in_paramlist804 = new BitSet(new long[]{0x5800400000400802L,0x0000000002000001L});
    public static final BitSet FOLLOW_param_in_paramlist812 = new BitSet(new long[]{0x5800400000400802L,0x0000000002000001L});
    public static final BitSet FOLLOW_fparam_in_fparamlist835 = new BitSet(new long[]{0x5800400000400802L,0x0000000002000001L});
    public static final BitSet FOLLOW_fparam_in_fparamlist843 = new BitSet(new long[]{0x5800400000400802L,0x0000000002000001L});
    public static final BitSet FOLLOW_T_INT_in_term863 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_INT_in_term865 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_T_FLOAT_in_term873 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_FLOAT_in_term875 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_T_STR_in_term884 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_STRING_in_term886 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_T_UTF8_in_term895 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_UTF8_in_term897 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_T_VAR_in_term906 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_dotname_in_term908 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_expr925 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_simpleExpr_in_expr941 = new BitSet(new long[]{0x5800400000400800L,0x0000000002000001L});
    public static final BitSet FOLLOW_simpleExpr_in_expr945 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_70_in_expr954 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_simpleExpr_in_expr958 = new BitSet(new long[]{0x5800400000400800L,0x0000000002000001L});
    public static final BitSet FOLLOW_simpleExpr_in_expr962 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MULTI_ASSIGN_in_expr970 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_varlist_in_expr972 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_function_in_expr974 = new BitSet(new long[]{0x0000000002000008L,0x0000000001941800L});
    public static final BitSet FOLLOW_cast_in_expr978 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_multExpr_in_simpleExpr993 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_simpleExpr1002 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_simpleExpr_in_simpleExpr1010 = new BitSet(new long[]{0x5800400000400800L,0x0000000002000001L});
    public static final BitSet FOLLOW_simpleExpr_in_simpleExpr1014 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_exprValue_in_multExpr1029 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_multExpr1038 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_multExpr_in_multExpr1048 = new BitSet(new long[]{0x0800400000400800L,0x0000000002000001L});
    public static final BitSet FOLLOW_multExpr_in_multExpr1052 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_simpleExpr_in_compExpr1069 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dotname_in_varlist1089 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_dotname_in_varlist1096 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_TERM_in_exprValue1113 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_term_in_exprValue1115 = new BitSet(new long[]{0x4000000002000008L,0x0000000001941800L});
    public static final BitSet FOLLOW_62_in_exprValue1120 = new BitSet(new long[]{0x0000000002000008L,0x0000000001941800L});
    public static final BitSet FOLLOW_cast_in_exprValue1126 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FUNCTION_in_exprValue1135 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_function_in_exprValue1137 = new BitSet(new long[]{0x0000000002000008L,0x0000000001941800L});
    public static final BitSet FOLLOW_cast_in_exprValue1141 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COMPOUND_EXPR_in_exprValue1150 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_compExpr_in_exprValue1152 = new BitSet(new long[]{0x0000000002000008L,0x0000000001941800L});
    public static final BitSet FOLLOW_cast_in_exprValue1156 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_type_in_cast1173 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_dotname1187 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_DOT_ID_in_dotname1194 = new BitSet(new long[]{0x0000000000008002L});

}