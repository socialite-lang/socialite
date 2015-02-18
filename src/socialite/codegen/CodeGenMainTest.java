package socialite.codegen;

import java.lang.reflect.Method;
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
import socialite.util.Assert;
import socialite.util.IdFactory;
import socialite.visitors.VisitorImpl;


public class CodeGenMainTest {
	static void createInst() {
		String query = "Edge(int s:0..10, (int t)).\n"
				+ "Triangle(int x, int y, int z) sortby x.\n"
				+ "Triangle(x, y, z):-Edge(x, y),y>x,Edge(y, z),z>y,Edge(z, x).\n";
		LocalEngine en = new LocalEngine(Config.seq());	
		CodeGenMain c=en.compile(query);
		en.shutdown();
	}

	static void evalSimpleJoinRules() {
		String query = "Edge(int s:1..10, (int t)).\n"
				+ "Triangle(int x, int y, int z) sortby x, orderby x.\n"				
				+ "Edge(s, t) :- s=1,t=2.\n"
				+ "Edge(s, t) :- s=2,t=3.\n"
				+ "Edge(s, t) :- s=3,t=1.\n"
				+ "Edge(s, t) :- s=3,t=8.\n"
				+ "Edge(s, t) :- s=8,t=9.\n"
				+ "Edge(s, t) :- s=9,t=10.\n"
				+ "Edge(s, t) :- s=10,t=8.\n"
				+ "Triangle(x, y, z):-Edge(x, y),y>x,Edge(y, z),z>y,Edge(z, x).\n";
		LocalEngine en = new LocalEngine(Config.par(4));
		CodeGenMain c=en.compile(query);
		
		List<Eval> evals = c.getEvalInsts();
		for (Eval e:evals) e.run();
		
		Table t = c.tableMap.get("Triangle");
		TableInst triangleArray[]=c.getTableRegistry().getTableInstArray(t.id());
		Assert.true_(triangleArray.length==1);
		TableInst triangle=triangleArray[0];		
		try{
			Method m = FunctionLoader.loadMethod(triangle.getClass(), "iterate", new Class[]{VisitorImpl.class});
			class TestVisitor extends VisitorImpl {			
				int count=0;
                public int getEpochId() { return 0; }
                public int getRuleId() { return 0; }
                public boolean visit(int x, int y, int z) {
					count++;
					Assert.true_((x==1 && y==2 && z==3)||(x==8 && y==9 && z==10));
					return true;
				}
				public boolean success() { return count==2; }

			}
			TestVisitor v=new TestVisitor();
			m.invoke(triangle, v);
			Assert.true_(v.success());
		} catch(Exception e) { 
			Assert.die(e.toString()); 
		} 
		en.shutdown();
	}

	static void evalAggregateRule() {
		String query = "Edge(int s:1..10, (int t)).\n"
				+ "Triangle(int x, int y, int z) sortby x.\n"
				+ "Count(int k:0..0, int c).\n"
				+ "Edge(s, t) :- s=1,t=2.\n"
				+ "Edge(s, t) :- s=2,t=3.\n"
				+ "Edge(s, t) :- s=3,t=1.\n"
				+ "Edge(s, t) :- s=3,t=8.\n"
				+ "Edge(s, t) :- s=8,t=9.\n"
				+ "Edge(s, t) :- s=9,t=10.\n"
				+ "Edge(s, t) :- s=10,t=8.\n"
				+ "Triangle(x, y, z):-Edge(x, y),y>x,Edge(y, z),z>y,Edge(z, x).\n"
				+ "Count(0, $inc(1)) :- Triangle(x, y, z).\n";
		Config conf=Config.par(4);
		/*conf.setDebugOpt("GenerateTable", false);
		conf.setDebugOpt("GenerateVisitor", false);
		conf.setDebugOpt("GenerateEval", false);*/
		LocalEngine en = new LocalEngine(conf);	
		CodeGenMain c=en.compile(query);
		
		List<Eval> evals = c.getEvalInsts();
		for (Eval e:evals) e.run();
		
		Table t = c.tableMap.get("Count");
		TableInst countTable[]=c.getTableRegistry().getTableInstArray(t.id());
		Assert.true_(countTable.length==1);
		TableInst count=countTable[0];		
		try{
			Method m = FunctionLoader.loadMethod(count.getClass(), "iterate", new Class[]{VisitorImpl.class});
			class TestVisitor extends VisitorImpl {			
				int count=0;
                public int getEpochId() { return 0; }
                public int getRuleId() { return 0; }
				public boolean visit(int c, int x) {
					count++;
					Assert.true_(c==0 && x==2);
					return true;
				}
				public boolean success() { return count==1; }
			}
			TestVisitor v=new TestVisitor();
			m.invoke(count, v);
			Assert.true_(v.success());
		} catch(Exception e) { Assert.die(e.toString()); }
		en.shutdown();
	}
	
	static void evalAggr2() {
		String query = "Edge(int s:1..10, (int t)).\n"
			+ "Triangle(int x, int y, int z) sortby x.\n"
			+ "Count(int k:0..0, int c).\n"
			+ "Edge(s, t) :- s=1, t=2.\n"
			+ "Edge(s, t) :- s=2, t=3.\n"
			+ "Edge(s, t) :- s=3, t=1.\n"
			+ "Count(0, $sum(1)) :-Edge(x, y),y>x,Edge(y, z),z>y,Edge(z, x).\n"
			+ "?- Count(0, s).\n";
		
		LocalEngine en = new LocalEngine(Config.par(4));
		en.run(query, new QueryVisitor() {
			int count=0;
			public boolean visit(Tuple t) {
				int c=t.getInt(0);
				int s=t.getInt(1);
				count++;
				Assert.true_(c==0 && s==1 && count==1);
				return true;
			}});
		en.shutdown();
	}
	
	static void evalSP() {
		System.out.println("Running shortest-path test on lastfm data. This test may take some time.");
		String query = "Edge(int s:1..1768198, (int t)).\n"
			+ "Edge(s, t) :- (s,t) = $LoadEdge(\"data/lastfm.txt\").\n";		
		LocalEngine en = new LocalEngine(Config.par(4));
		en.run(query);
	
		query = "SP(int x:1..1768198, int dist).\n"
				+"SP(s, d) :- s=1, d=0.\n"				
				+"SP(t, $Min(d)) :- SP(s, d1), Edge(s, t), d=d1+1.\n";		
		en.run(query);
		
		en.run("?-SP(9285, d).\n", new QueryVisitor() {
			public boolean visit(Tuple t) {
				int c=t.getInt(0);
				int d=t.getInt(1);
				Assert.true_(c==9285 && d==4);
				return true;
			}});
		en.run("?-SP(9296, d).\n", new QueryVisitor() {
			public boolean visit(Tuple t) {
				int c=t.getInt(0);
				int d=t.getInt(1);
				Assert.true_(c==9296 && d==6);
				return true;
			}});
		en.shutdown();
		System.out.println("Shortest-path test on lastfm data finished.");
	}	
	
	static void evalSP2() {
		String query = "Edge3(int s:1..15, (int t, int d)).\n"
			+ "Edge3(s, t, d) :- (s,t,d) = $LoadEdge3(\"data/test-dist.txt\").\n";
		
		LocalEngine en = new LocalEngine(Config.seq());
		en.run(query);
	
		query = "SP3(int x:1..15, int dist, int prev).\n"
				+"SP3(s, d, p) :- s=1, d=0, p=1.\n"				
				+"SP3(t, $Min(d), s) :- SP3(s, d1, _), Edge3(s, t, d2), d=d1+d2.\n";		
		en.run(query);
		
		en.run("?-SP3(t, d, p).\n", new QueryVisitor() {
			public boolean visit(Tuple _t) {
				int t=_t.getInt(0);
				int d=_t.getInt(1);
				int p=_t.getInt(2);
				if (t==10) Assert.true_(d==3 && p==6);
				if (t==12) Assert.true_(d==5 && p==9);
				return true;
			}});
	}
	
	static void evalSP_dir() {
		String query = "Edge(int s:1..1768198, (int t, int d)) sortby d.\n"+
			"Edge(s, t, d) :- line=$Read(\"data/rand-dist.txt\"), (s1, s2, s3)=$Split(line),"+
			"                 s=$ToInt(s1), t=$ToInt(s2), d=$ToInt(s3).\n";
		
		LocalEngine en = new LocalEngine(Config.par(4));
		en.run(query);
	
		query = "SP(int x:1..1768198, int dist).\n"
				+"SP(s, d) :- s=1, d=0.\n"				
				+"SP(t, $Min(d)) :- SP(s, d1), Edge(s, t, d2), d=d1+d2.\n";		
		en.run(query);
		
		en.run("?-SP(t, d).\n", new QueryVisitor() {
			int sum=0;
			public boolean visit(Tuple t) {
				int s=t.getInt(0);
				int d=t.getInt(1);
				if (s>1768174) {				
					System.out.println("s:"+s+", d:"+d);
					sum += d;
				}
				if (s==1768196) System.out.println("sum:"+sum);
				return true;
			}
		});
/*
s:1768175, d:37
s:1768176, d:38
s:1768177, d:57
s:1768178, d:53
s:1768179, d:30
s:1768180, d:31
s:1768181, d:58
s:1768182, d:61
s:1768183, d:16
s:1768184, d:20
s:1768185, d:59
s:1768186, d:43
s:1768187, d:40
s:1768188, d:28
s:1768189, d:15
s:1768190, d:24
s:1768191, d:19
s:1768192, d:56
s:1768193, d:57
s:1768194, d:36
s:1768195, d:21
s:1768196, d:62		 
 */
		en.shutdown();
	}
	
	static void evalMultipleQueryParallel() {
		String query = "Foo(int a, int b).\n" +
					"Bar(int a, int b).\n" +
					"Baz(int a, int b).\n" +
					"Qux(int a, int b).\n" +
				"Foo(a,b) :- a=$range(0, 1000), b=a+1 . \n";
		final LocalEngine en = new LocalEngine(Config.par(4));
		en.run(query);	
		final String query1 = "Bar(a,b) :- Foo(a,x), k=x+107, y=$range(x, k), Foo(y, b). \n"+
							"?-Bar(a,b).\n";
		final String query2 = "Baz(a,b) :- Foo(a,x), k=x+37, y=$range(x, k), Foo(y, b). \n"+
							"?-Baz(a,b).\n";
		final String query3 = "Qux(a,b) :- Foo(a,x), k=x+81, y=$range(x, k), Foo(y, b). \n"+
							"?-Qux(a,b).\n";
		final int count[] = new int[3];
		final Object lock = new Object();
		Thread t1=new Thread(new Runnable() {
			public void run() {
				en.run(query1, new QueryVisitor() {
					public boolean visit(Tuple t) {
						count[0]++;
						return true;
					}
				});
				synchronized(lock) {}
			}
		});
		Thread t2=new Thread(new Runnable() {
			public void run() {
				en.run(query2, new QueryVisitor() {
					public boolean visit(Tuple t) {
						count[1]++;
						return true;
					}
				});
				synchronized(lock) {}
			}
		});
		Thread t3=new Thread(new Runnable() {
			public void run() {
				en.run(query3, new QueryVisitor() {
					public boolean visit(Tuple t) {
						count[2]++;
						return true;
					}
				});
				synchronized(lock) {}
			}
		});
		long start=System.currentTimeMillis();
		
		t1.start();
		t2.start();			
		t3.start();			

		try {						
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) { }
		synchronized(lock) {}
		assert count[0]==101222;
		assert count[1]==36297;
		assert count[2]==77679;
		
		//System.out.println("Exec time for 3 queries:"+(System.currentTimeMillis()-start)+" ms");
		en.shutdown();
	}
	static void testVisitorClassCache() {
		String query = "Rdf(Utf8 s, Utf8 p, Utf8 o) indexby s, indexby p.\n" +
				"Rdf(s, p, o) :- s=u\"s1\",p=u\"p1\", o=u\"o1\".\n"+
				"Rdf(s, p, o) :- s=u\"s1\",p=u\"p2\", o=u\"o2\".\n"+
				"Rdf(s, p, o) :- s=u\"s2\",p=u\"p2\", o=u\"o3\".\n"+
				"Result(Utf8 x1, Utf8 x1name) multiset.\n";
		final LocalEngine en = new LocalEngine(Config.par(4));
		en.run(query);	
		
		query = "Result(x, y) :- Rdf(x, u\"p1\", y).\n";
		CodeGenMain codeGen = en.compile(query);
		List<Eval> evals = codeGen.getEvalInsts();
		for (Eval e:evals) e.run();
		Rule r = codeGen.getEpoch().get(0).getRules().get(0);
		Class prevKlass =CodeGenMain.visitorClass$.get(r.signature(codeGen.getRuntime().getTableMap())); 
		query = "Result(x, y) :- Rdf(x, u\"p2\", y).\n";
		codeGen = en.compile(query);		
		r=codeGen.getEpoch().get(0).getRules().get(0);
		Class klass = CodeGenMain.visitorClass$.get(r.signature(codeGen.getRuntime().getTableMap()));
		assert klass == prevKlass;
		evals = codeGen.getEvalInsts();
		for (Eval e:evals) e.run();
		
		final int[] count={0};
		en.run("?-Result(x, y).\n", new QueryVisitor() {
			public boolean visit(Tuple t) {
				count[0]++;
				return true;
			}
		});
		assert count[0]==3;
		
		en.shutdown();
	}
	static void dot() { System.out.print("."); }

	public static void main(String args[]) {
		//System.out.println("some tests are disabled");
		createInst(); dot();
		evalSimpleJoinRules(); dot();
		evalAggregateRule(); dot();
		evalAggr2(); dot();
		
		//evalSP();
		//evalSP2();
		//evalSP_dir();
		for (int i=0; i<10; i++) {
			evalMultipleQueryParallel(); dot();
		}
		testVisitorClassCache(); dot();
		
		System.out.println("CodeGenMain Test done");
	}
}