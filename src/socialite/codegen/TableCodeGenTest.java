package socialite.codegen;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import socialite.engine.Config;
import socialite.engine.LocalEngine;
import socialite.eval.Eval;
import socialite.functions.FunctionLoader;
import socialite.parser.DeltaTable;
import socialite.parser.Parser;
import socialite.parser.Table;
import socialite.parser.antlr.ColumnGroup;
import socialite.tables.QueryVisitor;
import socialite.tables.TableInst;
import socialite.tables.TableUtil;
import socialite.tables.Tuple;
import socialite.util.AnalysisException;
import socialite.util.Assert;
import socialite.util.InternalException;
import socialite.util.SociaLiteException;
import socialite.visitors.VisitorImpl;
import gnu.trove.list.array.TIntArrayList;

public class TableCodeGenTest {
	static void testFlatTable() {
		String query = "Attr1a(int s:0..10, int t).\n" +
				   "Attr2a[int i](String s, int i2).\n" +
				   "Attr3a(String s, double d, int n) sortby d.\n" +
				   "Attr4a[int i:0..100](String s, int i2).";
		LocalEngine en = new LocalEngine(Config.par(4));
		CodeGenMain codeGen = en.compile(query);
		Analysis an = codeGen.an;
		for (Eval e:codeGen.getEvalInsts()) {
			e.run();
		}
		
		Map<String, Table> map = an.getTableMap();
		Table t1=map.get("Attr1a");
		Assert.true_(t1.getColumnGroups().size()==1);		
		t1=map.get("Attr2a");
		Assert.true_(t1.getColumnGroups().size()==1);
		
		t1=map.get("Attr3a");
		Assert.true_(t1.getColumn(1).isSortedAsc());
		Assert.not_true(t1.getColumn(2).isIndexed());
		Assert.not_true(t1.getColumn(1).isIndexed());
		Assert.not_true(t1.getColumn(0).isIndexed());
		t1=map.get("Attr4a");
		Assert.true_(t1.getColumn(0).isIndexed());
		Assert.true_(t1.getColumn(0).isPrimaryShard());
		Assert.equals(t1.getColumn(1).type(), String.class);
		Assert.equals(t1.getColumn(2).type(), int.class);
		
		en.shutdown();
	}
	
	static void testNestedTable() {
		String query = "Attr1b(int s:0..10, (int t:0..10, String str)).\n" +
					   "Attr2b[int i]((String s, int i2)).\n" +
					   "Attr3b(String s, (double d, (int n))) sortby d, indexby n.\n"+
					   "Attr4b[int i:0..100](String s, (int i2)).\n" +
					   "Edge(int n:0..100, (int t)) sortby t.\n";
		LocalEngine en = new LocalEngine(Config.par(4));
		CodeGenMain codeGen = en.compile(query);
		Analysis an = codeGen.an;
		for (Eval e:codeGen.getEvalInsts()) {
			e.run();
		}

		Map<String, Table> map = an.getTableMap();
		Table t1=map.get("Attr1b"); Assert.true_(t1.getColumnGroups().size()==2);
		Assert.equals(t1.getColumn(0).type(), int.class);
		Assert.equals(t1.getColumn(1).type(), int.class);
		Assert.equals(t1.getColumn(2).type(), String.class);
		t1=map.get("Attr2b"); Assert.true_(t1.getColumnGroups().size()==2);	
		t1=map.get("Attr3b"); Assert.true_(t1.getColumnGroups().size()==3);
		t1=map.get("Attr4b"); Assert.true_(t1.getColumnGroups().size()==2);
		t1=map.get("Edge"); Assert.true_(t1.getColumnGroups().size()==2);
		en.shutdown();
	}
	
	static void testInstantiateTable() {
		String query = "Attr3c(String s, (double d, String s2, (int n))) sortby d, indexby n.\n"+
						"Attr3c(s1,d,s2,i) :- s1=\"s1\", d=1.1, s2=\"s2\", i=1 .";
		
		LocalEngine en = new LocalEngine(Config.par(4));
		CodeGenMain codeGen = en.compile(query);
		Analysis an = codeGen.an;
		for (Eval e:codeGen.getEvalInsts()) {
			e.run();
		}
		
		en.run("?-Attr3c(s,d,s2,n).", new QueryVisitor() {
			public boolean visit(Tuple t) {
				Assert.true_(t.get(0).equals("s1"));
				assert t.get(1).equals(1.1);
				assert t.get(2).equals("s2");
				assert t.get(3).equals(1);
				return true;
			}
		});		
		en.shutdown();
	}	
	
	static void testFlatTableWithIndex() {
		String query = "Attr1d(int n:0..10, int t, String s).\n" +
		   "Attr2d[int i](String s, double d) indexby d.\n"+
		   "Attr1d(n,t,s) :- n=1, t=42, s=\"s\".\n"+
		   "Attr2d[i](s,d) :- i=1, s=\"s\", d=4.2 .";
		LocalEngine en = new LocalEngine(Config.par(4));
		CodeGenMain codeGen = en.compile(query);
		Analysis an = codeGen.an;
		for (Eval e:codeGen.getEvalInsts()) {
			e.run();
		}

		en.run("?-Attr1d(1, t, s).", new QueryVisitor() {
			public boolean visit(Tuple t) {
				assert t.get(0).equals(1);
				assert t.get(1).equals(42);
				assert t.get(2).equals("s");
				return true;
			}
		});
		en.run("?-Attr2d[1](s, d).", new QueryVisitor() {
			public boolean visit(Tuple t) {
				assert t.get(0).equals(1);
				assert t.get(1).equals("s");
				assert t.get(2).equals(4.2);
				return true;
			}
		});
		
		en.shutdown();
	}
	
	static void testNestedTableWithIndex() {
		String query = "Nested1e(int n:0..10, (int t, String s)).\n" +
		   "Nested2e[int i](String s, (double d)) indexby i, indexby d.\n" +
		   "Nested3e[int i:0..10](int n, (String s, double d)).\n"+		   
		   "Nested1e(n,t,s) :- n=1, t=1, s=\"s\".\n"+
		   "Nested2e[i](s,d) :- i=1, s=\"s\", d=4.2 .\n"+
		   "Nested3e[i](n,s,d) :- i=1, n=1, s=\"s\", d=4.2.\n";		   
		LocalEngine en = new LocalEngine(Config.par(4));
		CodeGenMain codeGen = en.compile(query);
		Analysis an = codeGen.an;
		for (Eval e:codeGen.getEvalInsts()) {
			e.run();
		}
		
		en.run("?-Nested1e(1,t,s).", new QueryVisitor() {
			public boolean visit(Tuple t) {
				assert t.get(0).equals(1);
				assert t.get(1).equals(1);
				assert t.get(2).equals("s");
				return true;
			}
		});
		en.run("?-Nested2e[1](s,d).", new QueryVisitor() {
			public boolean visit(Tuple t) {
				assert t.get(0).equals(1);				
				assert t.get(1).equals("s");
				assert t.get(2).equals(4.2);
				return true;
			}
		});
		en.run("?-Nested3e[1](n,s,d).", new QueryVisitor() {
			public boolean visit(Tuple t) {
				assert t.get(0).equals(1);
				assert t.get(1).equals(1);
				assert t.get(2).equals("s");
				assert t.get(3).equals(4.2);
				return true;
			}
		});

		en.shutdown();
	}
	
	static void testIterateBy() {
		String query = "UnNested1f(int n:0..10, int t, String s).\n" +
			"UnNested2f(int n, int t, String s) indexby n.\n" +
		   "Nested1f[int i](String s, (double d)) indexby i.\n" +
		   "Nested2f(int n, (String s, double d)) indexby n.\n" +
		   "UnNested1f(n,t,s) :- n=1, t=2, s=\"s\".\n"+
		   "UnNested2f(n,t,s) :- n=1, t=2, s=\"s\".\n"+
		   "Nested1f[i](s,d) :- i=1, s=\"s\", d=4.2 .\n"+
		   "Nested2f(i,s,d) :- i=1, s=\"s\", d=4.2 .\n";
		LocalEngine en = new LocalEngine(Config.par(4));
		CodeGenMain codeGen = en.compile(query);
		Analysis an = codeGen.an;
		for (Eval e:codeGen.getEvalInsts()) { e.run(); }
		
		en.run("?-UnNested1f(1,t,s).", new QueryVisitor() {
			public boolean visit(Tuple t) {
				assert t.get(0).equals(1);
				assert t.get(1).equals(2);
				assert t.get(2).equals("s");
				return true;
			}
		});
		en.run("?-UnNested2f(1,t,s).", new QueryVisitor() {
			public boolean visit(Tuple t) {
				assert t.get(0).equals(1);
				assert t.get(1).equals(2);
				assert t.get(2).equals("s");
				return true;
			}
		});
		en.run("?-Nested1f[1](s,d).", new QueryVisitor() {
			public boolean visit(Tuple t) {
				assert t.get(0).equals(1);
				assert t.get(1).equals("s");
				assert t.get(2).equals(4.2);
				return true;
			}
		});
		en.run("?-Nested2f(1,s,d).", new QueryVisitor() {
			public boolean visit(Tuple t) {
				assert t.get(0).equals(1);
				assert t.get(1).equals("s");
				assert t.get(2).equals(4.2);
				return true;
			}
		});
		en.shutdown();
	}
	
	static void testDeltaTable() {
		String query = "Edge(int s:1..35000, (int t)).\n"
			+ "SP(int x:1..35000, int dist).\n"
			+ "Edge(s, t) :- s=1, t=2.\n"
			+ "Edge(s, t) :- s=2, t=3.\n"
			+ "Edge(s, t) :- s=2, t=5.\n"
			+ "Edge(s, t) :- s=3, t=4.\n"
			+ "Edge(s, t) :- s=5, t=9.\n"
			+ "Edge(s, t) :- s=2, t=10.\n"
			+ "Edge(s, t) :- s=7, t=8.\n"
			+ "SP(s, d) :- s=1, d=1.\n"
			+ "SP(t, $min(d)) :- SP(s, d1), Edge(s, t), d=d1+1.\n"
			+ "?- SP(5, d).";		
		LocalEngine en = new LocalEngine(Config.par(4));
		CodeGenMain codeGen = en.compile(query);
		for (Eval e:codeGen.getEvalInsts()) { e.run(); }
		Analysis an = codeGen.an;		
		
		Assert.not_null(an.getDeltaTables());
		TableCodeGen.ensureExistOrDie(an.getDeltaTables());
		
		Assert.true_(an.getDeltaTables().size()==1);
		Table dt=an.getDeltaTables().get(0);
		Assert.true_(dt instanceof DeltaTable);
		en.shutdown();		
	}
	
	static void testGroupby1() {
		String query = "Edge(int s:1..15, (int t, int d)).\n"
				+ "Edge(s, t, d) :- s=1, t=2, d=1.\n"
				+ "Edge(s, t, d) :- s=2, t=3, d=1.\n"
				+ "Edge(s, t, d) :- s=2, t=5, d=1.\n"
				+ "Edge(s, t, d) :- s=3, t=4, d=1.\n"
				+ "Edge(s, t, d) :- s=5, t=9, d=1.\n"
				+ "Edge(s, t, d) :- s=2, t=10, d=1.\n"
				+ "Edge(s, t, d) :- s=7, t=8, d=1.\n"
			+ "SP(int x:1..15, int dist, int prev).\n"
			+"SP(s, d, p) :- s=1, d=0, p=1.\n"				
			+"SP(t, $min(d), s) :- SP(s, d1, _), Edge(s, t, d2), d=d1+d2.\n";
		LocalEngine en = new LocalEngine(Config.par(4));
		en.run(query);
		en.shutdown();
	}
	
	static void testGroupby2() {
		String query = "Edge(int s:1..15, (int t, int d)).\n"
			+ "Edge(s, t, d) :- s=1, t=2, d=1.\n"
			+ "Edge(s, t, d) :- s=2, t=3, d=1.\n"
			+ "Edge(s, t, d) :- s=2, t=5, d=1.\n"
			+ "Edge(s, t, d) :- s=3, t=4, d=1.\n"
			+ "Edge(s, t, d) :- s=5, t=9, d=1.\n"
			+ "Edge(s, t, d) :- s=2, t=10, d=1.\n"
			+ "Edge(s, t, d) :- s=7, t=8, d=1.\n"
			+ "SP(int x:1..15, int dist, int prev).\n"
			+"SP(s, d, p) :- s=1, d=0, p=1.\n"				
			+"SP(t, $min(d), s) :- SP(s, d1, _), Edge(s, t, d2), d=d1+d2.\n";
		LocalEngine en = new LocalEngine(Config.par(4));
		CodeGenMain codeGen = en.compile(query);
		for (Eval e:codeGen.getEvalInsts()) { e.run(); }
		en.shutdown();
	}
	
	static void testRemoteTable() {
		String query ="Edge[int a:0..100]((int b)).\n" +
			"Triangle[int a:0..10]((int x, int y, int z)).\n" +
			"InEdge[int a:0..100]((int b)).\n" +
			"Edge[s](t) :- s=1, t=99.\n" +
			"InEdge[t](s) :- Edge[s](t).\n" +
			"Triangle[0](x, y, z) :- Edge[x](y), Edge[y](z), Edge[z](x).\n";
		LocalEngine en = new LocalEngine(Config.par(4));
		CodeGenMain codeGen = en.compile(query);
		for (Eval e:codeGen.getEvalInsts()) { e.run(); }
		en.shutdown();
	}
	
	static void testObjectColumnGen() {
		String query = "ObjTable(int s:1..128, (String obj)) sortby obj desc .\n"+			
			"ObjTable(1, o) :- o1=$range(1, 10), o=$toStr(o1).\n"+
			"?-ObjTable(1, o).";
		LocalEngine en = new LocalEngine(Config.seq());		
		final String x[] = new String[]{""};
		en.run(query, new QueryVisitor() {
			public boolean visit(Tuple t) {
				x[0] += t.getObject(1);
				return true;
			}	
		});
		Assert.true_("987654321".equals(x[0]), x[0]);
		en.shutdown();
	}
	
	static void testSortbyInPlace() {
		String query = "TestSb(int s, (int a)) sortby a asc .\n"+
		"TestSb(i, o) :- i=1, o=2. "+
		"TestSb(i, o) :- i=1, o=8. "+
		"TestSb(i, o) :- i=1, o=1. "+
		"TestSb(i, o) :- i=1, o=9. "+
		"TestSb(i, o) :- i=1, o=6. "+
		"TestSb(i, o) :- i=1, o=3. "+
		"TestSb(i, o) :- i=1, o=4. "+
		"TestSb(i, o) :- i=1, o=5. "+		
		"TestSb(i, o) :- i=1, o=7. "+
		"?-TestSb(i, o).";
		LocalEngine en = new LocalEngine(Config.seq());
		final String[] x = new String[]{""};
		en.run(query, new QueryVisitor() {
			public boolean visit(Tuple t) {
				//System.out.println(t);
				x[0] += t.get(1);
				return true;
			}
		});
		
		Assert.true_("123456789".equals(x[0]));
		en.shutdown();
	}
	static void testSortby() {
		String query = "TestSb(int s, String obj) indexby s, sortby obj asc. \n"+			
		"TestSb(i, o) :- i=1, o=$toStr(i). "+
		"TestSb(i, o) :- i=5, o=$toStr(i). "+
		"TestSb(i, o) :- i=4, o=$toStr(i). "+				
		"TestSb(i, o) :- i=8, o=$toStr(i). "+
		"TestSb(i, o) :- i=9, o=$toStr(i). "+
		"TestSb(i, o) :- i=6, o=$toStr(i). "+
		"TestSb(i, o) :- i=7, o=$toStr(i). "+		
		"TestSb(i, o) :- i=1, o=$toStr(i). "+
		"TestSb(i, o) :- i=3, o=$toStr(i). "+		
		"TestSb(i, o) :- i=2, o=$toStr(i). "+
		"TestSb(i, o) :- i=1, o=$toStr(i). "+
		"TestSb(i, o) :- i=5, o=$toStr(i). "+
		"?-TestSb(i, o).";
		LocalEngine en = new LocalEngine(Config.seq());
		final String[] x=new String[]{""};
		en.run(query, new QueryVisitor() {
			public boolean visit(Tuple t) {
				//System.out.println(t);
				x[0] += t.get(1);
				return true;
			}
		});
		Assert.true_("123456789".equals(x[0]), x[0]);
		en.shutdown();
	}
	
	static void testNestedSortby() {
		String query = "TestSb(int s, (String obj, int a)) indexby s, sortby a asc.\n"+
			"TestSb(i, o, a) :- i=1, o=$toStr(i), a=3. "+
			"TestSb(i, o, a) :- i=1, o=$toStr(i), a=8. "+
			"TestSb(i, o, a) :- i=1, o=$toStr(i), a=9. "+
			"TestSb(i, o, a) :- i=1, o=$toStr(i), a=9. "+
			"TestSb(i, o, a) :- i=1, o=$toStr(i), a=9. "+
			"TestSb(i, o, a) :- i=1, o=$toStr(i), a=5. "+
			"TestSb(i, o, a) :- i=1, o=$toStr(i), a=4. "+
			"TestSb(i, o, a) :- i=1, o=$toStr(i), a=5. "+
			"TestSb(i, o, a) :- i=1, o=$toStr(i), a=7. "+
			"TestSb(i, o, a) :- i=1, o=$toStr(i), a=6. "+
			"TestSb(i, o, a) :- i=1, o=$toStr(i), a=1. "+		
			"TestSb(i, o, a) :- i=1, o=$toStr(i), a=2. "+
			"TestSb(i, o, a) :- i=1, o=$toStr(i), a=7. "+			
			"TestSb(i, o, a) :- i=1, o=$toStr(i), a=0. "+
			"?-TestSb(i, o, a).";
		Config conf=Config.seq();				
		LocalEngine en = new LocalEngine(conf);
		final String[] x=new String[]{""};
		en.run(query, new QueryVisitor() {
			public boolean visit(Tuple t) {
				//System.out.println(t);
				x[0] += t.get(2);
				return true;
			}
		});
		Assert.true_("0123456789".equals(x[0]), x[0]);		
		en.shutdown();
	}
	
	static void testTableSize() {
		String query = "TestSize(int s:1000, String obj).\n"+
			"TestSize(i, o) :- i=1, o=$toStr(i). "+
			"TestSize(i, o) :- i=2, o=$toStr(i). ";
			
		Config conf=Config.par(4);				
		LocalEngine en = new LocalEngine(conf);
		en.run(query);
		en.shutdown();
	}
		
	static void testIterColumn() {
		String query ="Foo(int a:iter, int b). \n" +
				"Bar(int a, int b:iter). \n"+
				"Foo(1,b) :- b=10. " +
				"Foo(2,b) :- Foo(1, c), b=c+1.";
		LocalEngine en = new LocalEngine(Config.par(4));
		en.run(query);
		en.shutdown();		
	}
	
	static void testAggrColumn1() {
		String query ="Foo1(int a:0..1, int b).\n" +
					"Foo1(1,$sum(b)) :- b=10. \n"+ "?-Foo1(a, b).";
		LocalEngine en = new LocalEngine(Config.seq());
		final String[] s=new String[]{""};
		en.run(query, new QueryVisitor() {
			public boolean visit(Tuple t) {
				s[0] +=  t.get(0)+","+t.get(1)+";";
				return true;
		}});
		assert s[0].equals("1,10;"):s[0];
		s[0]="";
		
		query ="Foo2(int a, int b).\n" +
				"Foo2(1,$sum(b)) :- b=10. \n" + "?-Foo2(a, b).";
		en.run(query, new QueryVisitor() {
		public boolean visit(Tuple t) {
			s[0] +=  t.get(0)+","+t.get(1)+";";
			return true;
		}});
		assert s[0].equals("1,10;"):s[0];
		s[0]="";
		
		query = "Bar1(int a:0..1, int b, double d).\n" +
				"Bar1(1,$min(b), d) :- b=10, d=1.0 .\n"+
				"Bar1(1,$min(b), d) :- b=0, d=2.0 .\n";
		en.run(query);
		query = "?-Bar1(1, b, c).";
		en.run(query, new QueryVisitor() {
			public boolean visit(Tuple t) {
				s[0] +=  t.get(0)+","+t.get(1)+","+t.get(2)+";";
				return true;
		}});
		assert s[0].equals("1,0,2.0;"):s[0];
		s[0]="";
		query ="Bar2(int a, int b, double d).\n" +
				"Bar2(1,$min(b), d) :- b=10, d=1.0 . "+
				"Bar2(1,$min(b), d) :- b=0, d=2.0 . "+ "?-Bar2(1, b, c).";
		en.run(query, new QueryVisitor() {
			public boolean visit(Tuple t) {
				s[0] +=  t.get(0)+","+t.get(1)+","+t.get(2)+";";
				return true;
		}});
		assert s[0].equals("1,0,2.0;"):s[0];
		en.shutdown();
	}
	static void testAggrColumn2() {
		String query ="Foo1(int a:0..1, int b, (double c)) sortby c asc.\n" +
					"Foo1(1,$min(b), c) :- b=10, c=0.0 . \n"+ 
					"Foo1(1,$min(b), c) :- b=10, c=0.1 . \n"+
					"Foo1(1,$min(b), c) :- b=1, c=0.2 . \n"+
					"Foo1(1,$min(b), c) :- b=1, c=0.3 . \n"+
					"?-Foo1(a, b, c).";
		LocalEngine en = new LocalEngine(Config.seq());
		final String[] s=new String[]{""};
		en.run(query, new QueryVisitor() {
			public boolean visit(Tuple t) {
				s[0] +=  t.get(0)+","+t.get(1)+","+t.get(2)+";";
				return true;
		}});
		assert s[0].equals("1,1,0.2;1,1,0.3;"):s[0];
		s[0]="";

		query ="Bar1(int a:0..1, int b, String s, (double c)) sortby c asc.\n" +
				"Bar1(1,$min(b), s, c) :- b=10, s=\"10\", c=0.0. \n"+ 
				"Bar1(1,$min(b), s, c) :- b=10, s=\"10\", c=0.1. \n"+
				"Bar1(1,$min(b), s, c) :- b=1, s=\"1\", c=0.2. \n"+
				"Bar1(1,$min(b), s, c) :- b=1, s=\"1\", c=0.3. \n"+
				"?-Bar1(a, b, s, c).";
		en.run(query, new QueryVisitor() {
			public boolean visit(Tuple t) {
				s[0] +=  t.get(0)+","+t.get(1)+","+t.get(2)+","+t.get(3)+";";
				return true;
		}});
		assert s[0].equals("1,1,1,0.2;1,1,1,0.3;"):s[0];
		s[0]="";
				
		query ="Foo2(int a, int b, (double c)) sortby c asc.\n" +
				"Foo2(1,$min(b), c) :- b=10, c=0.0 . \n"+ 
				"Foo2(1,$min(b), c) :- b=10, c=0.1 . \n"+
				"Foo2(1,$min(b), c) :- b=1, c=0.2 . \n"+
				"Foo2(1,$min(b), c) :- b=1, c=0.3 . \n"+
				"?-Foo2(a, b, c).";
	
		en.run(query, new QueryVisitor() {
			public boolean visit(Tuple t) {
				s[0] +=  t.get(0)+","+t.get(1)+","+t.get(2)+";";
				return true;
			}});
		assert s[0].equals("1,1,0.2;1,1,0.3;"):s[0];
		s[0]="";
		
		query ="Bar2(int a, int b, String s, (double c)) sortby c.\n" +
				"Bar2(1,$min(b), s, c) :- b=10, s=\"10\", c=0.0 . \n"+ 
				"Bar2(1,$min(b), s, c) :- b=10, s=\"10\", c=0.1 . \n"+
				"Bar2(1,$min(b), s, c) :- b=1, s=\"1\", c=0.2 . \n"+
				"Bar2(1,$min(b), s, c) :- b=1, s=\"1\", c=0.3 . \n"+
				"?-Bar2(a, b, s, c).";
		en.run(query, new QueryVisitor() {
			public boolean visit(Tuple t) {
				s[0] +=  t.get(0)+","+t.get(1)+","+t.get(2)+","+t.get(3)+";";
				return true;
		}});
		assert s[0].equals("1,1,1,0.2;1,1,1,0.3;"):s[0];
		s[0]="";
		
		query ="Bar3(int a:0..10, (String s, int b, (double c))) sortby c.\n" +
				"Bar3(1,s,$min(b), c) :- b=10, s=\"1\", c=0.0, x=0 . \n"+ 
				"Bar3(1,s,$min(b), c) :- b=10, s=\"1\", c=0.1, x=0 . \n"+
				"Bar3(1,s,$min(b), c) :- b=1, s=\"1\", c=0.2, x=0 . \n"+
				"Bar3(1,s,$min(b), c) :- b=1, s=\"1\", c=0.3, x=0 . \n"+
				"?-Bar3(a, s, b, c).";
		en.run(query, new QueryVisitor() {
			public boolean visit(Tuple t) {
				s[0] +=  t.get(0)+","+t.get(1)+","+t.get(2)+","+t.get(3)+";";
				return true;
		}});
		assert s[0].equals("1,1,1,0.2;1,1,1,0.3;"):s[0];
		s[0]="";
		
		query ="Foo3(int a, (String s, int b, (double c))) sortby c.\n" +
				"Foo3(1,s,$min(b), c) :- b=10, s=\"1\", c=0.0, x=0 . \n"+ 
				"Foo3(1,s,$min(b), c) :- b=10, s=\"1\", c=0.1, x=0 . \n"+
				"Foo3(1,s,$min(b), c) :- b=1, s=\"1\", c=0.2, x=0 . \n"+
				"Foo3(1,s,$min(b), c) :- b=1, s=\"1\", c=0.3, x=0 . \n"+
				"?-Foo3(a, s, b, c).";
		en.run(query, new QueryVisitor() {
			public boolean visit(Tuple t) {
				s[0] +=  t.get(0)+","+t.get(1)+","+t.get(2)+","+t.get(3)+";";
				return true;
		}});
		assert s[0].equals("1,1,1,0.2;1,1,1,0.3;"):s[0];
		s[0]="";

		en.shutdown();
	}
	static void dot() { System.out.print("."); }
	static TableInst getTableInst(String className) {
		return TableUtil.create(className);
	}
	
	public static void main(String args[]) {
		//System.out.println("Some tests are disabled...");
		
		testFlatTable(); dot();
		testNestedTable(); dot();
		testInstantiateTable(); dot();
		testFlatTableWithIndex(); dot();
		testNestedTableWithIndex(); dot();
		testIterateBy(); dot();
		testDeltaTable(); dot();
		testGroupby1(); dot();
		testGroupby2(); dot();
		testRemoteTable(); dot();		
		testObjectColumnGen(); dot();
		testSortbyInPlace(); dot();
		testSortby(); dot();
		testNestedSortby(); dot();
		testTableSize(); dot();
		testIterColumn(); dot();
		testAggrColumn1(); dot();
		testAggrColumn2(); dot();
		
		System.out.println("TableCodeGenTest done");
	}
}
