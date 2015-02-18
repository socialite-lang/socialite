package socialite.codegen;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import socialite.engine.Config;
import socialite.engine.LocalEngine;
import socialite.eval.Eval;
import socialite.functions.FunctionLoader;
import socialite.parser.Parser;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.resource.SRuntime;
import socialite.resource.TableInstRegistry;
import socialite.tables.QueryVisitor;
import socialite.tables.TableInst;
import socialite.tables.TableUtil;
import socialite.tables.Tuple;
import socialite.util.AnalysisException;
import socialite.util.Assert;
import socialite.util.SociaLiteException;
import socialite.visitors.IVisitor;


public class VisitorCodeGenTest {
	
	static Epoch findEpoch(Analysis an, Rule r) {
		for (Epoch e:an.getEpochs()) {
			if (e.getRules().contains(r))
				return e;
		}
		assert false:"unexpected!";
		return null;
	}
	static void simpleIterate() {
		String query = "Foo(int a, int b).\n" +					
		 		"Bar(int a, int b).\n" +
				"Bar(a,b) :- a=10, b=11. \n" +
		 		"Foo(n1, n3) :- Bar(n3, n1).";
		Config conf=Config.par();
		//conf.setDebugOpt("GenerateTable", false);
		//conf.setDebugOpt("GenerateVisitor", false);
		LocalEngine e=new LocalEngine(conf);
		e.run(query);
		e.shutdown();
	}
	
	static void simpleJoin() {
		String query = "Foo(int a, int b).\n" +					
		 			"Bar(int a, int b).\n" +
		 			"Baz(String a, int b).\n" +
		 			"Qux(int a, String b) indexby a, indexby b.\n" +
		 		"Foo(n1, n2) :- Bar(n3, n1), Bar(n1,n2).\n" +
		 		"Foo(a, b) :- Bar(a, c), Baz(s, c), Qux(b, s).";
		 		
		Analysis an = parseAndAnalysis(query);
		TableCodeGen.ensureExistOrDie(an.getNewTables());		
			
		for (Rule r:an.getRules()) {
			Epoch s=findEpoch(an, r);
			compileRule(s, r, an.getTableMap());
		}
	}

	static void funcExpr() {
		String query = "Edge(int s:0..10, (int t)).\n" +					
		 			"Edge(s, t) :- s=1, t1=\"2\", t=$toInt(t1).\n";
		Analysis an = parseAndAnalysis(query);
		TableCodeGen.ensureExistOrDie(an.getNewTables());		
			
		for (Rule r:an.getRules()) {
			Epoch s=findEpoch(an, r);
			compileRule(s, r, an.getTableMap());
		}
	}
	
	static void exprAndJoin() {
		String query = "Edge(int s:0..10, (int t)).\n" +
					"Triangle(int x, int y, int z) sortby x.\n" +
		 			"Triangle(x, y, z):-Edge(x, y),y>x,Edge(y, z),z>y,Edge(z, x).";		 		
		Analysis an = parseAndAnalysis(query);
		TableCodeGen.ensureExistOrDie(an.getNewTables());		
			
		for (Rule r:an.getRules()) {
			Epoch s=findEpoch(an, r);
			compileRule(s, r, an.getTableMap());
		}
	}
	
	static void funcAndJoin() {
		String simpleQuery = "Edge(int s:0..10, (int t)).\n" +
					"Foo(int x, int y) sortby x.\n" +
		 			"Foo(x, z):-Edge(x, a), b=a+3*2, y=$toInt(\"1\"), Edge(b, z) .";		 		
		Analysis an = parseAndAnalysis(simpleQuery);
		TableCodeGen.ensureExistOrDie(an.getNewTables());		
			
		for (Rule r:an.getRules()) {
			Epoch s=findEpoch(an, r);
			compileRule(s, r, an.getTableMap());
		}
	}
	
	static void createInst() {
		String query = "Edge1(int s:0..10, (int t)).\n"
			+ "Triangle1(int x, int y, int z) sortby x.\n"
			+ "Triangle1(x, y, z):-Edge1(x, y),y>x,Edge1(y, z),z>y,Edge1(z, x).";
		LocalEngine en=new LocalEngine(Config.seq());
		CodeGenMain c=en.compile(query);
		Analysis an=c.an;
		
		List<Eval> evals=c.getEvalInsts();
		//for (Eval e:evals) e.run();
		
		Rule r = an.getRules().get(0);
		IVisitor[] visitors = 
			c.getRuntime().getVisitorBuilder(r.id()).getNewVisitorInst(r.id());
		Assert.not_null(visitors);
		Assert.equals(visitors.length, 1);
		IVisitor v=visitors[0];
		en.shutdown();
	}
	
	static void iterateRange() {
		String query="Edge(int s:1..1000, (int t)) sortby t.\n" +
					"Clique(int x, int y, int z) sortby x.\n" +
					"Clique(x, y, z) :- Edge(x, y), x<y, Edge(y, z), y<z, Edge(x, z).";
		Analysis an=parseAndAnalysis(query);
		Config conf=Config.seq();
		CodeGenMain c=new CodeGenMain(conf, an, SRuntime.create(conf));
		c.generate();
	}
	static void iterateRange2() {
		String query="Edge(int s:1..1000, int a, (int s2:1..100, int t)).\n" +
					"Clique2(int x, int y, int z) sortby x.\n" +
					"Clique2(x, y, z) :- Edge(1, u, 9, y), Edge(y, 2, z, _), y<z, Edge(x, z, _, _).";
		Analysis an=parseAndAnalysis(query);
		Config conf=Config.seq();
		CodeGenMain c=new CodeGenMain(conf, an, SRuntime.create(conf));
		c.generate();
	}
	static void iterateWithIndex() {
        String query="RDF(Utf8 s, Utf8 p, Utf8 o) indexby s, indexby p.\n"+
				"RDF(s,p,o) :- s=u\"s1\", p=u\"p1\", o=u\"o1\". \n"+
				"RDF(s,p,o) :- s=u\"s1\", p=u\"p2\", o=u\"o2\". \n"+
				"RDF(s,p,o) :- s=u\"s2\", p=u\"p3\", o=u\"o3\". \n"+
				"RDF(s,p,o) :- s=u\"s2\", p=u\"p4\", o=u\"s1\". \n";
		LocalEngine en = new LocalEngine(Config.par(4));
		en.run(query);

		query = "Tmp(int a:0..10, Utf8 x1, Utf8 x2).\n" +
			"Tmp(0, x1, x2) :- RDF(x1, u\"p1\", x2). "+
			"?-Tmp(0, x1, x2).";
		final String[] x = new String[]{""};
		en.run(query, new QueryVisitor() {
			public boolean visit(Tuple t) {
				//System.out.println(t);
				x[0] += t.get(1);
				x[0] += t.get(2);
				return true;
			}
		});
		assert x[0].equals("s1o1"): "x[0]:"+x[0];
		en.shutdown();
	}
	static void iterateWithIndex2() {
		String query="Foo(int a:0..100, (int b:0..10, double c)).\n"+
			"Foo(a,b,c) :- a=1, b=2, c=3.3. \n"+ 
			"Bar(int a, int b, double c). \n"+
			"Bar(a,b,c) :- a=10, b=20, Foo(1,2,c).";
		LocalEngine en = new LocalEngine(Config.par(4));
		en.compile(query);
		en.shutdown();
	}
	static void testAggrFunction() {
		String query="Edge(int s:1..1000, (int t)) sortby t.\n" +
			"Foo(int a, int b).\n" +
			"Bar(int a, String b).\n" +
			"Edge(s, t) :- s=1, t=1.\n" +
			"Edge(s, t) :- s=1, t=2.\n" +
			"Edge(s, t) :- s=1, t=3.\n" +
			"Foo(a, $sum(b)) :- Edge(a, b).\n"+
			"Bar(a, $sum(c)) :- Edge(a, b), c=$toStr(b).";
		LocalEngine en = new LocalEngine(Config.par(4));
		en.run(query);
		final String s[]=new String[]{""}; 
		en.run("?-Bar(a,b).", new QueryVisitor() {
			public boolean visit(Tuple t) {
				s[0] += t.get(0)+","+t.get(1)+";";
				return true;
			}
		});
		assert s[0].equals("1,123;"):s[0];
		en.shutdown();
	}
	
	static void testScc() {
		String query = "Foo(int s, int t).\n" +
					"Bar(int a, int b).\n" +
					"Baz(int a, int b).\n" +					
					"Foo(a,b) :- a=10, b=11.\n" +
					"Foo(a,b) :- Bar(a,b).\n" +
					"Baz(a,b) :- Foo(a,b).\n" +
					"Bar(a,b) :- Baz(a,b).\n";
		Config conf = Config.par(4);
		//conf.setDebugOpt("GenerateTable", false);
		//conf.setDebugOpt("GenerateVisitor", false);		
		LocalEngine en = new LocalEngine(conf);
		en.run(query);
		final String[] x = new String[]{""};
		en.run("?-Bar(a,b).", new QueryVisitor() {
			public boolean visit(Tuple t) {
				x[0] += t.getInt(0)+","+t.getInt(1)+";";
				return true;
			}
		});
		assert x[0].equals("10,11;"):"x[0]:"+x[0];
		en.shutdown();
	}
	
	static void testIterCol() {
		String query ="InEdge(int t:0..100, (int src)) sortby src. \n" +
				"EdgeCnt(int s:0..100, int cnt).\n" +
				"Rank(int i:iterator, int s:0..100, double rank) groupby(2).\n" +
				"InEdge(t, s) :- t=$range(0, 99), s=t+1. \n"+
				"EdgeCnt(s, $inc(1)) :- InEdge(t, s).\n";				
		Config conf = Config.par(4);
		LocalEngine en = new LocalEngine(conf);
		en.run(query);		

		query = "Rank(0,_,r) :- r=100.0/100.0 . \n"+
				"Rank(1,_,r) :- r=100.0/100.0 . \n"+					
				"Rank(1,p,$sum(r)) :- InEdge(p,n), Rank(0,n,r1), EdgeCnt(n,cnt), r=0.85*r1/cnt.\n";			
		en.run(query);
		
		query = "Rank(2,_,r) :- r=100.0/100.0 . \n"+
				"Rank(2,p,$sum(r)) :- InEdge(p,n), Rank(1,n,r1), EdgeCnt(n,cnt), r=0.85*r1/cnt.\n";
		en.run(query);

		en.shutdown();
	}
	
	static void testContains() {
		String query ="Base(int a, int b). \n" +
				"Arr1(int a:0..10, int b) indexby a. \n" +
				"Test(int a, int b). \n" +
				"Base(0, b) :- b=1 . \n" +
				"Arr1(1, b) :- b=1 . \n"+
				"Test(a,b) :- Base(a,b), !Arr1(b, _). \n"+
				"?-Test(a,b).";
		LocalEngine en = new LocalEngine(Config.par(4));
		final String[] s=new String[]{""};		
		en.run(query, new QueryVisitor() {
			public boolean visit(Tuple t) {
				s[0] += t.get(0)+","+t.get(1)+";";
				return true;
			}
		});
		assert s[0].equals("");
		
		en.run("clear Test.");
		s[0]="";		
		query = "Arr2(int a:0..10, (int b:0..10, int c)) indexby a. \n" +
				"Arr2(1, b, b) :- b=1 . \n" +
				"Test(a,b) :- Base(a,b), !Arr2(b, _, _). \n"+
				"?-Test(a,b).";

		en.run("?-Test(a,b).", new QueryVisitor() {
			public boolean visit(Tuple t) {
				s[0] += t.get(0)+","+t.get(1)+";";
				return true;
			}
		});
		assert s[0].equals("");		
		en.run("clear Test."); s[0]="";		
		
		query = "Dyn1(int a, int b) indexby a. \n" +
				"Dyn1(1, b) :- b=1 . \n"+
				"Test(a,b) :- Base(a,b), !Dyn1(b, _). \n"+
				"?-Test(a,b).";		
		en.run(query, new QueryVisitor() {
			public boolean visit(Tuple t) {
				s[0] += t.get(0)+","+t.get(1)+";";
				return true;
			}
		});
		
		assert s[0].equals("");		
		en.run("clear Test."); s[0]="";
		
		query = "Dyn2(int a, (int b)) indexby a. \n" +
				"Dyn2(1, b) :- b=1 . \n"+
				"Test(a,b) :- Base(a,b), !Dyn2(b, _). \n"+
				"?-Test(a,b).";
		en.run(query, new QueryVisitor() {
			public boolean visit(Tuple t) {
				s[0] += t.get(0)+","+t.get(1)+";";
				return true;
			}
		});
		assert s[0].equals("");		

		en.shutdown();
	}
	static void testChoice() {
		String query ="Diff(int n:0..0, int t) groupby(1).\n"+
					"Gradient(int x, int y , int z).\n"+
					"Gradient(a,b,c) :- a=1,b=1,c=1.\n"+
					"Gradient(a,b,c) :- a=1,b=1,c=2.\n"+
					"Diff(0, $choice(d)) :- Gradient(x,y,z), tmp=x+y+z, tmp>1, d=z.\n";
		
		Config c=Config.par(4);
		LocalEngine en = new LocalEngine(c);
		en.run(query);
		final int[] count=new int[]{0};
		en.run("?-Diff(a,b).", new QueryVisitor() {
			public boolean visit(Tuple t) {
				count[0]++;
				return true;
			}
		});
		assert count[0]==1;
		en.shutdown();
	}
	static void testChoice2() {
		String query ="Path(int i:0..100, (int depth, int to)). \n"+
					"Edge(int s:0..100, (int t)).\n"+
					"EdgeCnt(int s:0..100, int cnt).\n"+
					"PathCnt(int s:0..100, int cnt).\n"+
					"Source(int n:0..0, int s).\n"+
					"Depth(int n:0..0, int s). \n"+

				 "Edge(int s:0..100, (int t)).\n"+
				 "Edge(s, t) :- s=0, t=1. \n"+
				 "Edge(s, t) :- s=1, t=2. \n"+
				 "Edge(s, t) :- s=2, t=3. \n"+
				 "Edge(s, t) :- s=3, t=4. \n"+
				 "Edge(s, t) :- s=4, t=5. \n"+
				 "Edge(s, t) :- s=5, t=6. \n"+
				 "Edge(s, t) :- s=6, t=0. \n"+

				 "Edge(s, t) :- s=2, t=7. \n"+
				 "Edge(s, t) :- s=7, t=8. \n"+
				 "Edge(s, t) :- s=8, t=2. \n"+

				 "Edge(s, t) :- s=5, t=10. \n"+
				 "Edge(s, t) :- s=10, t=11. \n"+
				 "Edge(s, t) :- s=11, t=12. \n"+
				 "Edge(s, t) :- s=12, t=15. \n";
			
		Config c=Config.par(4);
		LocalEngine en = new LocalEngine(c);
		en.run(query);
		
		System.err.println("Starting eulerian path..");
		query = "Path(s, depth, $choice(to)) :- s=0, depth=1, Edge(s, to), !Path(s, _, to); \n"+
				" 			  :- Path(_, prevDepth, s), depth=prevDepth+1, Edge(s, to), !Path(s, _, to).\n" +
				"PathCnt(s, $inc(1)) :- Path(s, depth, to).\n"+
				"Depth(0, $max(depth)) :- Path(s, depth, to). \n";
		en.run(query);
		
		en.run("Source(0, $choice(s)) :- PathCnt(s, cnt1), EdgeCnt(s, cnt2), cnt1 < cnt2.");
		en.run("clear PathCnt. clear Source.");
		
		query = "Path(s, depth, $choice(to)) :- s=2, depth=8, Edge(s, to), !Path(s, _, to); \n"+
				" 			  :- Path(_, prevDepth, s), depth=prevDepth+1, Edge(s, to), !Path(s, _, to).\n"+
				"PathCnt(s, $inc(1)) :- Path(s, depth, to).\n"+
				"Depth(0, $max(depth)) :- Path(s, depth, to). \n";
		en.run(query);
		en.run("clear PathCnt. clear Source.");
		
		en.run("Source(0, $choice(s)) :- PathCnt(s, cnt1), EdgeCnt(s, cnt2), cnt1 < cnt2.");
		
		System.err.println("Querying ?-Path");
		en.run("?-Path(a,d, c).", new QueryVisitor() {
			public boolean visit(Tuple t) {
				System.out.println(t.get(0)+"-->"+t.get(2)+":"+t.get(1));
				return true;
			}
		});
		en.shutdown();
	}
	static void testit2() {
		int N=1000;
		String q =  "InEdge(int t:0.." + N + ", (int s)) sortby s.\n" +
			     "EdgeCnt(int s:0.." + N+ ", int cnt).\n" +
			     "EdgeCntInv(int s:0.." + N + ", float i).\n" +
			     "Rank(int n:0.." + N + ", int i:iter, float val) groupby(2).\n" +
			     "Term(int i:0..1, float d).\n" +
			     "InEdge(t, s) :- t=1, s=23 .\n" +
			     "EdgeCnt(s, $inc(1)) :- InEdge(t, s).\n" +
			     "EdgeCntInv(s, i) :- EdgeCnt(s, c), i=0.7f/c.\n" +
			     "Rank(_, 0, v) :- v=1.0f .";
		Config c=Config.par(2);
		LocalEngine en = new LocalEngine(c);
		en.run(q);
		en.shutdown();
	}
	static void testNegated() {
		String query = "Foo(int a, int b).\n"+				
				"Bar(int a, int b).\n"+
				"Bar(a,b) :- a=10, b=20. \n"+
				"Bar(a,b) :- a=20, b=10. \n"+
				"Foo(a,b) :- Bar(a, b), ! Bar(b, a).\n";
		Config c=Config.seq();
		LocalEngine en = new LocalEngine(c);
		en.run(query);
		en.shutdown();
	}
	static void testCastCodeGen() {
		String query = "Foo(int a, double b).\n"+				
				"Foo(a,b) :- a=100, c=20, d=3.3, b= -(double)d, b=(double)d+d.\n"+
				"Foo(a,b) :- a=100, c=20, b=(double)c.\n";
		Config c=Config.seq();
		LocalEngine en = new LocalEngine(c);
		en.run(query);
		en.shutdown();
	}
	static void testDottedVar() {
		String query = "Foo(int a, String s).\n"+				
				"Foo(a,b) :- b=\"100\", a=$toInt(b).\n";
		Config c=Config.par(4);
		LocalEngine en = new LocalEngine(c);
		en.run(query);
		en.shutdown();
	}
	static void testAggr() {
		String query = "Foo(int a, Avg x).\n"+   
					"Foo(0, $avg(x)) :- x=42.42. ";

		Config c=Config.par(4);
		LocalEngine en = new LocalEngine(c);
		en.run(query);
		
		query="Bar(String a, Count b).\n"+
                "  Bar(a, $count(b)) :- a=\"abc\", b=3.3."; 
		en.run(query);
		en.shutdown();
	}
	static void testArgmin() {
		String query ="Data(int n:0..40000, double x, double y).\n"+
				"Data(id, lat, lng) :- id=$range(0, 40000), lat=1.2, lng=5.2.\n"+
				"Center(int k:0..100, Avg[] avg) groupby(1).\n";
		Config c=Config.par(2);
		LocalEngine en = new LocalEngine(c);		
		en.run(query);
		
		double x=-10.5, y=-10.5;
		for (int i=0; i<100; i++) {
			query="Center("+i+", $avg(a)) :- a=$darray("+x+","+y+").";
			en.run(query);
			x+=0.2; y+=0.2;
		}
		en.run("Cluster(int did:0..40000, int i:iter,ArgMin min) groupby(2).");
		for (int i=0; i<10; i++) {
			query="Cluster(did, "+i+", $argmin(idx, diff)) :- Data(did, x, y), Center(idx, a),  (cx,cy)=$unpack(a),"+
					"diff= (x-cx.value)*(x-cx.value)+(y-cy.value)*(y-cy.value).";
			en.run(query);
		}
		en.shutdown();
	}
	public static void main(String args[]) {
        //System.out.println("Some tests are disabled");
		simpleIterate(); dot();
		simpleJoin(); dot();
		funcExpr(); dot();
		exprAndJoin(); dot();		
		funcAndJoin(); dot();
		createInst(); dot();
		iterateRange();	dot();
		iterateRange2(); dot();
		
		iterateWithIndex(); dot();
		iterateWithIndex2(); dot();
		
		testScc(); dot();
		testIterCol(); dot();
				
		testAggrFunction();	dot();	
		testContains(); dot();
				
		testChoice(); dot();
		testCastCodeGen(); dot();
		testNegated(); dot();
		testDottedVar(); dot();
		testAggr(); dot();

		testArgmin(); dot();		
		
		System.out.println("Visitor Code Generation Test done");
	}
	static void dot() { System.out.print("."); }
	
	static Analysis parseAndAnalysis(String query) {
		Parser p = new Parser(query);
		p.parse();
		Analysis an = new Analysis(p);
		an.run();		
		return an;		
	}
	static void compileRule(Epoch e, Rule r, Map<String, Table> tableMap) {
		VisitorCodeGen gen = new VisitorCodeGen(e, r, tableMap, Config.par(2));
		String src = gen.generate();
		Compiler c= new Compiler();
		boolean success=c.compile(gen.visitorName(), src);
		if (!success) {
			System.out.println(c.getErrorMsg());
			throw new SociaLiteException();
		}
	}	
}
