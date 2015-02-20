package socialite.parser;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import socialite.codegen.Analysis;
import socialite.codegen.Epoch;
import socialite.codegen.RuleComp;
import socialite.engine.Config;
import socialite.functions.FunctionLoader;
import socialite.parser.antlr.ColumnGroup;
import socialite.util.AnalysisException;
import socialite.util.Assert;
import socialite.util.ParseException;
import socialite.util.SociaLiteException;

public class ParserTest {

	static void test1() {
		// not really a PageRank algorithm
		String prQuery2 = "Edge(int s,int t).\n"
				+ "PageRank(int i:0..10,(int n:0..100, double d)).\n"
				+ "PageRank(1, n, $sum(r)) :- PageRank(0, p, r1),"
				+ 	"  Edge(p,n), r = r1/100.\n"; 
		Parser p = new Parser(prQuery2);
		try { p.parse();
		} catch (ParseException pe) {
			System.out.println(pe.compileErrorMsg());
		}

		Assert.true_(p.getRules().size() == 1, "rules:"+p.getRules());
		//Analysis analysis = new Analysis(p.getRules(), p.getTables(), null, p.getQuery());
		Analysis analysis = new Analysis(p);
		analysis.run();

		FunctionLoader.loadAll(p.getRules());
		analysis.requireTypeChecking();
		
		Rule r = p.getRules().get(0);

		Set<Variable> vars = r.getBodyVariables();
		vars.addAll(r.getHeadVariables());
		Assert.true_(vars.contains(new Variable("n", int.class)));
		Assert.true_(vars.contains(new Variable("r", double.class)));
		Assert.true_(vars.contains(new Variable("r1", double.class)));
		Assert.true_(vars.size()==4);		
	}

	static void testVariables() {
		String query = "Edge(int s,int t).\n"
				+ "Foo(int f,int a, int b).\n"
				+ "Foo(a, b,d) :- Foo(a,b,c), Edge(b,d).\n";
		Parser p = new Parser(query);
		p.parse();

		Assert.true_(p.getRules().size() == 1);
		//Analysis analysis = new Analysis(p.getRules(), p.getTables(), null, p.getQuery());
		Analysis analysis = new Analysis(p);
		analysis.run();

		
		Rule r = p.getRules().get(0);
		Predicate p0=r.getBodyP().get(0);
		Predicate p1=r.getBodyP().get(1);
		Variable b1=(Variable)p0.params.get(0);
		Variable b2=(Variable)p1.first();
		Assert.true_(b1==b2, "expecting the same instance");		
		Variable b3=(Variable)r.getHead().params.get(0);
		Assert.true_(b1==b3, "expecting the same instance");
	}
	
	static void testVarResolution() {
		String simpleQuery = "Edge(int s,(int t)).\n"
				+ "Foaf(int a,int b).\n"
				+ "Foo(int a,int b).\n"
				+ "Foaf(n1,n3) :- Foo(n1,n2), Edge(n2,n3), n4=n1*(n2+1).\n";
		Parser p = new Parser(simpleQuery);
		p.parse();
		Analysis an = new Analysis(p);
		an.run();

		Rule r = p.getRules().get(0);
		Set<Variable> resolvedVars[] = Analysis.getResolvedVars(r);
		Assert.equals(resolvedVars.length, 4);
		Assert.true_(resolvedVars[0].isEmpty());
		Assert.true_(resolvedVars[1].contains(new Variable("n1", int.class)));
		Assert.true_(resolvedVars[1].contains(new Variable("n2", int.class)));
		Assert.true_(!resolvedVars[1].contains(new Variable("n3", int.class)));
		Assert.true_(!resolvedVars[1].contains(new Variable("n3", int.class)));
		
		Assert.true_(!resolvedVars[2].contains(new Variable("n4", int.class)));

		Assert.true_(resolvedVars[3].contains(new Variable("n1", int.class)));
		Assert.true_(resolvedVars[3].contains(new Variable("n2", int.class)));
		Assert.true_(resolvedVars[3].contains(new Variable("n3", int.class)));
		Assert.true_(resolvedVars[3].contains(new Variable("n4", int.class)));
	}

	static void testVarResolution2() {
		String simpleQuery = "Edge(int s,(int t)).\n"
				+ "Foaf(int a,int b).\n"
				+ "Foo(int a,int b).\n"
				+ "Foaf(n1,n3) :- Foo(n1,n2), Edge(n2,n3), n4=	n5*(n2+1).\n";
		Parser p = new Parser(simpleQuery);
		p.parse();
		Analysis an = new Analysis(p);
		try {
			an.run();
			Assert.die("should not reach here!");
		} catch (Exception e) { /* pass */ }
	}

	static void testQuery() {
		String prQuery = "PageRank(int n,int i, double r).\n"
				+ "Edge(int s,int t).\n"
				+ "PageRank(t, 0, r) :- Edge(p,t), r = 1.0/100.\n"
				+ "?-PageRank(1,0, r).\n";
		Parser p = new Parser(prQuery);
		p.parse();
		Analysis an = new Analysis(p);
		an.run();		
		
		Query q = p.getQuery();
		Assert.true_(q!=null);
		Assert.equals(q.getP().name(), "PageRank");
	}

	static void testFunction() {
		String prQuery = "Edge(int s,int t).\n" +
				"Foo(int a, String b, int c).\n" +
				"Bar(int a, String b).\n" +
				"Edge(s,t) :- s=1, t=2.\n" +
				"Foo(a, b, d) :- Bar(a, b), c=$toInt(\"11\"), d=$toInt(\"12\")+3*$toInt(\"12\").\n";
				
		Parser p = new Parser(prQuery);
		p.parse();

		Analysis analysis = new Analysis(p);
				//new Analysis(p.getRules(), p.getTables(), null, p.getQuery());
		analysis.run();
		Rule r = p.getRules().get(1);
		Expr expr = (Expr)r.getBody().get(1);		
		AssignOp op = (AssignOp)expr.root;
		Function f = (Function)op.arg2;
		Assert.equals(f.name(), "Builtin.toInt");
		Assert.equals(f.getReturns().get(0).name, "c");
		Assert.equals(f.getReturns().get(0).type, int.class);
	}

	static void testReadWrite() {
		String prQuery = "Edge(int s,int t).\n"
				+ "Foo(int a,int b).\n"
				+ "Bar(int a,int b).\n"
				+ "Zoo(int a,int b).\n"
				+ "Foo(a,b) :- Bar(a,c), Zoo(b,c).\n"
				+ "Bar(a,b) :- Bar(a,c), Zoo(b,c).\n"
				+ "Edge(s,t) :- s=1, t=2.\n";

		Parser p = new Parser(prQuery);
		p.parse();
		Analysis an = new Analysis(p);
		an.run();
		Map<String, Table> map = an.getTableMap();
		
		Table foo=map.get("Foo");
		Table bar=map.get("Bar");
		Table zoo=map.get("Zoo");
		Epoch epoch=an.getEpochs().get(0);
				
		epoch=an.getEpochs().get(1);
		
	}
	
	static void testRuleDependency() {
		String prQuery = "Edge(int s,int t).\n"
				+ "Foo(int a,int b).\n"
				+ "Bar(int a,int b).\n"
				+ "Zoo(int a,int b).\n"
				+ "Foo(a,b) :- Bar(a,c), Zoo(b,c).\n"
				+ "Bar(a,b) :- Bar(a,c), Zoo(b,c).\n"
				+ "Edge(s,t) :- s=1, t=2 .\n";

		Parser p = new Parser(prQuery);
		p.parse();

		//Analysis an = new Analysis(p.getRules(), p.getTables(), null, p.getQuery());
		Analysis an = new Analysis(p);
		an.run();
		List<Epoch> strata=an.getEpochs();
		Assert.true_(strata.size()==2, "Strata #:"+strata.size());
		
		List<Rule> rules = an.getRules();
		Rule fooRule = rules.get(0);
		Assert.true_(fooRule.name().startsWith("Foo"));
		Assert.true_(fooRule.getDependingRules().size()==0, // no deps in the same strata
					 "Foo deps #:"+fooRule.getDependingRules().size());		
		
		Rule barRule = rules.get(1);		
		Assert.true_(barRule.name().startsWith("Bar"));
		Assert.true_(barRule.getDependingRules().size()==2);
		
		Rule edgeRule = rules.get(2);
		Assert.true_(edgeRule.name().startsWith("Edge"));
		Assert.true_(edgeRule.getDependingRules().size()==0);
	}	
	
	static void testParseMultipleProg() {
		String query = "Foo(int f,int b).\n"
			+ "Bar(int b,int c).\n"
			+ "Zoo(int z,int a).\n"
			+ "Foo(a,b) :- Bar(a,c), Zoo(b,c).\n"
			+ "Bar(a,b) :- Bar(a,c), Zoo(b,c).\n";
	
		Parser p = new Parser(query);
		p.parse();
		
		String query2 ="Foo(a,b) :- Foo(a,c), Zoo(b,c).\n";
		p.parse(query2);
		Assert.true_(p.getRules().get(0).name().startsWith("Foo"));
		
		String query3="Zoo(a,b) :- Foo(a,b).\n";
		p.parse(query3);
		Assert.true_(p.getRules().get(0).name().startsWith("Zoo"));		
	}
	
	static void testTableReadWrite() {
		String query="PageRank(int n,int _iter, double r).\n" +
					 "Edge(int s,int t).\n" +
					 "PageRank(t,0, r) :- Edge(p,t), r = 1.0/100.\n";

		Parser p = new Parser();
		p.parse(query);

		//Analysis an = new Analysis(p.getRules(), p.getTables(), null, p.getQuery());
		Analysis an = new Analysis(p);
		an.run();

		Table pagerank=an.getTableMap().get("PageRank");
		Table edge=an.getTableMap().get("Edge");
		
		Epoch s=an.getEpochs().get(0);			
	}
	
	static void testTableColumns() {
		String query = "PageRank(int n,(int _iter, double r)) sortby _iter desc.\n"
				+ "Edge(int s:0..10,int t).\n"
				+ "Foo(int a:0..10, double d).\n";

		Parser p = new Parser();
		p.parse(query);

		Analysis an = new Analysis(p);
		an.run();

		Table pagerank = an.getTableMap().get("PageRank");
		Assert.true_(pagerank.getColumn(1).isSortedDesc());
		
		Table edge = an.getTableMap().get("Edge");
		Assert.true_(edge.getColumn(0).hasRange());
		
		Table foo = an.getTableMap().get("Edge");
		Assert.true_(foo.getColumn(0).hasRange());
	}
	
	static void testNestedTable() {
		String query="Triangle(int h,(int x, int y, int z)).\n" +
				"Edge(int s,int t).\n";
		
		Parser p = new Parser();
		p.parse(query);
		
		//Analysis an = new Analysis(p.getRules(), p.getTables(), null, p.getQuery());
		Analysis an = new Analysis(p);
		an.run();
		
		Table triangle = an.getTableMap().get("Triangle");
		Column col = triangle.getColumn(0);
		Assert.true_(col.isPrimaryShard());
		col = triangle.getColumn(1);
		Assert.not_true(col.isPrimaryShard());
		List<ColumnGroup> cg=triangle.getColumnGroups();
		Assert.true_(cg.size()==2);
		
		Table edge = an.getTableMap().get("Edge");
		cg=edge.getColumnGroups();
		Assert.true_(cg.size()==1);
	}
	
	static void testTableOptions() {
		String query="Edge(int s,int t) predefined.\n" +
					"B(int a,int b, (int i, double c)) sortby i.\n";
		Parser p = new Parser();
		p.parse(query);
		
		List<Table> tables = p.getNewTables();
		Table edge = tables.get(0);
		Table b = tables.get(1);
		
		Assert.true_(edge.isPredefined());
		Assert.true_(b.sortbyCols().length==1);
		Assert.true_(b.sortbyCols()[0]==2);
		
		query = "Foo(int i:0..100, (int s, String t)) sortby s, sortby t.\n";
		p.parse(query);
		Analysis an = new Analysis(p);
		try {
			an.run();
			throw new SociaLiteException("should throw analysis exception");
		} catch (AnalysisException e) { /* expected */ }
	}
	
	static void testUserClass() {
		String query="Edge(int s,int t) predefined.\n" +
					"Conn(int n,(int n1, int n2, int s)).\n"+
					"Edge(n1,n2) :- n1=10, n2=20.\n"+
					"Conn(h,n1, n2, $inc(1)) :- Edge(n1,c1), Edge(n2,c1).\n";
		Parser p = new Parser();
		p.parse(query);
	}
	static void testConsts() {
		String query="Foo(int a, double b).\n"+
				"Bar(float a, String b, Utf8 c).\n"+
				"Baz(long a, String b, Utf8 c).\n"+
				"Baz(a,b,c) :- Bar(1.0f, x, u\"2\"), Foo(3, 4.0), a=5L, b=\"6\", c=u\"7\" .\n";
		Parser p = new Parser();
		p.parse(query);
		List<Rule> rules = p.getRules();
		assert rules.size()==1;
		Collection<Const> consts = rules.get(0).getConsts();
		assert consts.size()==7;
	}
	static void testCast() {
		String query="Foo(int a, double b).\n"+				
				"Foo(a,b) :- a=10, c=30, b=(double)c.\n";
		Parser p = new Parser();
		p.parse(query);
	}
	static void testExprParam() {
		String query="Foo(int a, double b).\n"+				
				"Bar(int a, double b).\n"+
				"Foo(a,b) :- a=10, b=11.1, Bar(a, (double)(a+10)).\n";
		Parser p = new Parser();
		p.parse(query);
		Analysis an  = new Analysis(p);
		an.run();
		Rule r=an.getRules().get(0);
	}
	static void testEpochRecursive() {
		String query = "Edge(int s:1..35000, (int t)).\n"
			+ "SP(int x:1..35000, int dist).\n"
			+ "Edge(s, t) :- s=1,t=2.\n"
			+ "SP(s, d) :- s=1, d=1.\n"
			+ "SP(t, $min(d)) :- SP(s, d1), Edge(s, t), d=d1+1.\n"
			+ "?- SP(5, d).\n";
		Parser p = new Parser();
		p.parse(query);
		Analysis an  = new Analysis(p);
		an.run();		
		List<Epoch> epoch = an.getEpochs();
		
		Assert.true_(epoch.size() == 2);
		Epoch s = epoch.get(0);
		Assert.true_(s.getRuleCompNum()==1);
		Rule edgeLoad=s.getRuleComps().get(0).get(0);
		Assert.true_(edgeLoad.getHead().name().equals("Edge"));
	}
	static void testEpochRecursive2() {
		String query = "Foo(int s, int t).\n"
			+ "Bar(int a, int b).\n"
			+ "Baz(int a, int b).\n"
			+ "Foo(a,b) :- Bar(a,b).\n"
			+ "Baz(a,b) :- Foo(a,b).\n"
			+ "Bar(a,b) :- Baz(a,b).\n";			
		Parser p = new Parser();
		p.parse(query);
		Analysis an  = new Analysis(p);
		an.run();	
		List<Epoch> epochs = an.getEpochs();
		Epoch e = epochs.get(0);
		List<RuleComp> comps = e.getRuleComps();
		assert comps.size()==1;
		List<Rule> startRules = comps.get(0).getStartingRules();
		assert startRules.size()==3;		
		
		query = "Node(int n:0..100,int i).\n" +
				"Edge(int n:0..100,int i).\n" +
				"Comp(int n:0..100,int i).\n" +
				"Comp(n,n) :- Node(n,_). \n" + 
				"Comp(m,$min(i)) :- Comp(n,i), Edge(n,m).\n";
		p = new Parser();
		p.parse(query);
		an  = new Analysis(p);
		an.run();
		epochs = an.getEpochs();
		assert epochs.size()==1;
		comps = epochs.get(0).getRuleComps();
		assert comps.size()==1;
		startRules = comps.get(0).getStartingRules();
		assert startRules.size()==1;
	}
	static void testEpochs() {
		String query="SP(int n:0..2000000,int d).\n"+
				"Edge(int s,int t).\n" +
				"Foo(int n, int a).\n" +
			"SP(t,d) :- Edge(1,t), d=1 .\n" +
			"SP(t,$min(d)) :- SP(p,d1), Edge(p,t), d=d1+1 .\n" +
			"Foo(a, b) :- SP(a,b).\n" +
			"Edge(n1,n2) :- n1=2, n2=20.\n";
		Parser p = new Parser();
		p.parse(query);
		Analysis an  = new Analysis(p);
		an.run();		
		List<Epoch> epochs = an.getEpochs();
		
		Assert.true_(epochs.size() == 3);
		Epoch e=epochs.get(0);
		Assert.true_(e.getRuleCompNum()==1);
		Rule edgeLoad=e.getRuleComps().get(0).get(0);
		Assert.true_(edgeLoad.getHead().name().equals("Edge"));
		
		e=epochs.get(1);
		Assert.true_(e.getRuleCompNum()==1);
		Rule sp=e.getRuleComps().get(0).get(0);
		Assert.true_(sp.getHead().name().equals("SP") && sp.getHead().hasFunctionParam());
		e=epochs.get(2);
		Assert.true_(e.getRuleCompNum()==1);
		Rule foo = e.getRuleComps().get(0).get(0);
		Assert.true_(foo.getHead().name().equals("Foo"));
	}
	static void testEpochs2() {
		String query ="Edge(int s:0..410235, (int t)).\n"
				+ "InEdge(int s:0..410235,(int t)).\n"
				+ "EdgeCnt(int s:0..410235, int cnt).\n" 
			    + "PR0(int p:0..410235,double r).\n" 
				+"PR1(int p:0..410235,double r).\n"
				+"PR2(int p:0..410235,double r).\n"
				+"PR3(int p:0..410235,double r).\n"
				+"PR4(int p:0..410235,double r).\n"
				+"PR0(_,r) :- r= 1/410236.0.\n"
				+"PR1(p,$sum(r)) :- InEdge(p,n), PR0(n,r1), EdgeCnt(n, cnt), r=0.85*r1/cnt.\n"
				+"PR2(p,$sum(r)) :- InEdge(p,n), PR1(n,r1), EdgeCnt(n, cnt), r=0.85*r1/cnt.\n"
				+"PR3(p,$sum(r)) :- InEdge(p,n), PR2(n,r1), EdgeCnt(n, cnt), r=0.85*r1/cnt.\n"
				+"PR4(p,$sum(r)) :- InEdge(p,n), PR3(n,r1), EdgeCnt(n, cnt), r=0.85*r1/cnt.\n";
		Parser p = new Parser();
		p.parse(query);
		Analysis an  = new Analysis(p);
		an.run();		
		List<Epoch> epoch = an.getEpochs();
		Assert.true_(epoch.size()==4, "Epoch #:"+epoch.size());
	}
	
	static void testPrivatization() {
		String query="Edge(int s:0..100000, (int t)).\n"+
				"Clique(int x:10, int y, int z) indexby x.\n"+
				"Clique(x,y,z):-Edge(x,y), Edge(y, z), Edge(z, s).\n";
		
		Parser p=new Parser();
		p.parse(query);
		Analysis an=new Analysis(p, Config.par(2));
		an.run();
		List<Epoch> epochs=an.getEpochs();
		Epoch e=epochs.get(0);

		List<Rule> rules=e.getRules();
        boolean hasPrivateTable=false;
        for (Rule r:rules) {
            Map<String, Table> tableMap = an.getTableMap();
            Table t = tableMap.get(r.getHead().name());
            if (t instanceof PrivateTable)
                hasPrivateTable=true;
        }
        Assert.true_(hasPrivateTable);
	}
	
	static void testDistRule() {
		String query ="Edge(int a:0..100,(int b)).\n" +
		"InEdge(int a:0..100,(int b)).\n" +
		"Edge(s,t) :- s=1, t=99.\n" +
		"InEdge(t,s) :- Edge(s,t).\n" +
		"?-InEdge(s,t).\n";
		Parser p=new Parser();
		p.parse(query);
		Analysis an=new Analysis(p, Config.dist(4));
		an.run();	
		
		for (Table t:an.getRemoteTables()) {
			Assert.true_(t instanceof RemoteHeadTable);
			Column c=t.getColumn(0);
			Assert.not_true(c.hasRange(), "RemoteTables are not array-tables");
		}
	}
	
	static void testLocationOpInPredicate() {
		String query ="Edge(int a:0..100, (int b)). \n" +
		"Attr(int a,int b).\n" +
		"Attr(a,b) :- Edge(a,b).\n";
		Parser p=new Parser();		
		Analysis an;
		try {
			p.parse(query);
			an=new Analysis(p, Config.dist());
			an.run();
			Assert.die("should not reach here");
		} catch (ParseException e) {/* expected */}
		
		query ="Edge(int a:0..100, (int b)). \n" +
		"Attr(int a,int b).\n" +
		"Attr(a, b) :- Edge(a, b).\n";
		p=new Parser();		
		try {
			p.parse(query);
			an=new Analysis(p, Config.dist());
			an.run();
			Assert.die("should not reach here");
		} catch (ParseException e) { /* expected */ }
	}
	
	static void testAggrFunc() {
		String query ="Foo(int a:0..100, int i). \n" +
				"Foo(a, $min(b)) :- a=1, b=20.\n";
		Parser p=new Parser();
		p.parse(query);
		Analysis an=new Analysis(p, Config.par(4));
		an.run();
		
		query ="Bar(int a:0..100, (int i, double d)). \n" +
				"Bar(a, $min(b), c) :- a=1, b=20, c=1.1.\n";
		p.parse(query);
		an=new Analysis(p, Config.par(4));
		try { 
			an.run();
			assert false:"Expecting an exception";
		} catch (AnalysisException e) { /* expected */ }
		
	}
	
	static void testIterColumn() {
		String query ="Foo(int a:iter, ApproxCount b). \n" +
				"Bar(int a, int b:iter). \n";
				
		Parser p=new Parser();
		p.parse(query);
		Analysis an=new Analysis(p, Config.par(4));
		an.run();
		
		query ="Foo(int a:iter, int b). \n" +
				"Bar(int a, int b:iter). \n"+
				"Foo(0,b) :- b=10. " +
				"Foo(1,b) :- Foo(0, c), b=c+1.\n";
		p = new Parser();
		p.parse(query);
		an=new Analysis(p, Config.par(4));
		an.run();
		
		query = "Baz(int a, String b, (int i:iterator)).\n";
		p = new Parser();
		try { 
			p.parse(query);
			Assert.die("Expecting parse exception for nested iterator column");
		} catch (ParseException e) { }
		
		query ="Foo(int a:iter, int b). \n" +
				"Bar(int a, int b:iter). \n"+
				"Foo(1,b) :- Foo(0, c), b=c+1.\n";
		p = new Parser();
		p.parse(query);
		an=new Analysis(p, Config.par(4));
		an.run();
		query = "Foo(2,b) :- Foo(1, c), b=c+1.\n";
		p.parse(query);
		an = new Analysis(p, Config.par(4));
		an.run();
		List<Rule> rules = an.getRules();
		
		query = "Foo(3,b) :- Foo(2, c), b=c+1.\n";
		p.parse(query);
		an = new Analysis(p, Config.par(4));
		an.run();
		rules = an.getRules();
	}
	static void testSyntaxErrorWithNonConstantIter() {
		String query ="Foo(int b, int a). \n" +
				"Bar(int a, int b:iter). \n"+
				"Foo(1,b) :- Bar(0, c), b=c+1. .\n"+
				"?-Foo(a,b).\n";
		Parser p = new Parser();
		try {
			p.parse(query);
			assert false:"Should raise parse execption!";
		} catch (ParseException e) {
			//System.out.println(e.compileErrorMsg());
		}
	}
	
	static void dot() { System.out.print("."); }
	public static void main(String args[]) {
		//System.out.println("some tests are disabled");
		test1(); dot();
		testVariables(); dot();
		testVarResolution(); dot();
		testVarResolution2(); dot();
		testQuery(); dot();
		testFunction(); dot();
		testReadWrite(); dot();
		testRuleDependency(); dot();
		testParseMultipleProg(); dot();
		testTableReadWrite(); dot();
		testTableColumns(); dot();
		testNestedTable(); dot();
		testTableOptions(); dot();
		testUserClass(); dot();
		testConsts(); dot();
		testCast(); dot();
		testExprParam(); dot();
		
		testEpochs(); dot();
		testEpochs2(); dot();
		testEpochRecursive(); dot();
		testEpochRecursive2(); dot();
				
		testPrivatization(); dot(); 
		testDistRule(); dot();
		testLocationOpInPredicate(); dot();
	
		testAggrFunc(); dot();
		testIterColumn(); dot();
		
		testSyntaxErrorWithNonConstantIter(); dot();
		
		System.out.println("Parser test done");
	}
}
