package socialite.parser.antlr;

import java.util.List;

import org.antlr.runtime.*;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

import socialite.parser.AssignOp;
import socialite.parser.BinOp;
import socialite.parser.Expr;
import socialite.parser.Function;
import socialite.parser.Predicate;
import socialite.parser.Variable;
import socialite.parser.antlr.SociaLiteParser.prog_return;
import socialite.util.Assert;


@Deprecated
public class AntlrParserTest {
	/**
	static SociaLiteParser getParser(String query) {
		SociaLiteLexer lexer = new SociaLiteLexer(new ANTLRStringStream(query));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		SociaLiteParser parser = new SociaLiteParser(tokens);
		return parser;
	}
	static void test1() {
		String decl = "Foo[int f](double a, int b).\n";
		SociaLiteParser parser = getParser(decl);
		prog_return prog=null;
		try { prog = parser.prog();
		} catch (RecognitionException e) { e.printStackTrace(); }
		
		//System.out.println(((CommonTree) prog.getTree()).toStringTree());
	}
	static void test2() {
		String decl = "Foo[int f](double a, int b).\n" +
					  "Bar(double a, double b).\n";
		SociaLiteParser parser = getParser(decl);
		prog_return prog=null;
		try { prog = parser.prog();
		} catch (RecognitionException e) { e.printStackTrace(); }
		
		//System.out.println(((CommonTree) prog.getTree()).toStringTree());
	}
	static void testSimpleRule() {
		String query = "Foo[int f](double a, int b).\n" +
		  			   "Bar(double a, double b).\n" +
		  			   "Foo[a](b,c) :- Bar(c,b).\n";
		SociaLiteParser parser = getParser(query);
		prog_return prog=null;
		try { prog = parser.prog();
		} catch (RecognitionException e) { e.printStackTrace(); }
		
		//System.out.println(((CommonTree) prog.getTree()).toStringTree());
	}
	static void testComplexRule() {
		String query = "Zoo[int a](double d, double b).\n" +
			"Foo[int f](double a, int b).\n" +
			"Bar[int i](double a, double b).\n" +
			"Foo[a](b,c) :- Bar[1](c,b).\n" +
			"Bar[a](3.0, x) :- Zoo[a](b,c), d=a*(3+4.0)/4, d>10, x=$toInt(\"11\").\n";		
		SociaLiteParser parser = getParser(query);
		prog_return prog=null;
		
		try { prog = parser.prog();
		} catch (RecognitionException e) { e.printStackTrace(); }
		
		CommonTree ast = (CommonTree)prog.getTree();		
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(ast);
		SociaLiteRule graphlite = new SociaLiteRule(nodes);
		SociaLiteRule.prog_return ret=null;
		try {
			ret = graphlite.prog();
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		}	
	
		Assert.true_(ret.result.get(3) instanceof RuleDecl);
		
		RuleDecl rule = (RuleDecl) ret.result.get(3);
		Assert.true_(rule.head.name().equals("Foo"));		
		
		Predicate bar = (Predicate)rule.body.get(0);
		Variable c = (Variable)bar.inputParams()[1];
		Assert.true_(c.name.equals("c"));		
		Variable b = (Variable)bar.inputParams()[2];
		Assert.true_(b.name.equals("b"));
	}
	
	
	static void testTableDecl() {
		String query = "Foo[int f](double a, (String b)).\n" +
		   "Bar(double a, double b, (String s, int i)).\n" +
		   "Zoo(int a:0..10, double b, (String s, int i)).\n";		   
		SociaLiteParser parser = getParser(query);
		prog_return prog=null;
		try { prog = parser.prog();
		} catch (RecognitionException e) { e.printStackTrace(); }
		
		CommonTree ast = (CommonTree)prog.getTree();		
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(ast);
		SociaLiteRule graphlite = new SociaLiteRule(nodes);
		SociaLiteRule.prog_return ret=null;
		try {
			ret = graphlite.prog();
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		}
		Assert.true_(ret.result.get(0) instanceof TableDecl);
		TableDecl foo = (TableDecl) ret.result.get(0);
		Assert.equals(foo.name, "Foo");
		Assert.equals(foo.locationColDecl.name, "f");
		Assert.equals(foo.locationColDecl.type, int.class);
		Assert.equals(foo.nestedTable.colDecls.size(), 1);
		Assert.equals(foo.nestedTable.colDecls.get(0).name, "b");
		Assert.equals(foo.nestedTable.colDecls.get(0).type, String.class);
		
		TableDecl zoo = (TableDecl) ret.result.get(2);
		Assert.equals(zoo.name, "Zoo");
		Assert.equals(zoo.colDecls.get(0).name, "a");
		Assert.true_(zoo.colDecls.get(0).opt instanceof ColRange);
	}
	
	static void testTableDeclWithOption() {
		String query = "Foo[int f](double a, (String b)) sortby a.\n" +
		   "Bar(double a, double b, (String s, int i)) sortby a, indexby b.\n" +
		   "Zoo(int a:0..10, double b, (String s, int i)) orderby b, indexby s.\n";		   
		
		SociaLiteParser parser = getParser(query);
		prog_return prog=null;
		try { prog = parser.prog();
		} catch (RecognitionException e) { e.printStackTrace(); }
		
		CommonTree ast = (CommonTree)prog.getTree();		
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(ast);
		SociaLiteRule graphlite = new SociaLiteRule(nodes);
		SociaLiteRule.prog_return ret=null;
		try {
			ret = graphlite.prog();
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		}
		
		Assert.true_(ret.result.get(0) instanceof TableDecl);
		TableDecl foo = (TableDecl) ret.result.get(0);
		Assert.equals(foo.name, "Foo");
		Assert.true_(foo.sortbyCols().get(0)==1);
		
		TableDecl zoo = (TableDecl) ret.result.get(2);
		Assert.equals(zoo.name, "Zoo");
		Assert.equals(zoo.colDecls.get(0).name, "a");
		Assert.true_(zoo.colDecls.get(0).opt instanceof ColRange);
		Assert.true_(zoo.orderbyCols().get(0)==1);
		Assert.true_(zoo.indexbyCols().get(0)==2);
	}
	
	static void testExpr() {
		String query = "Foo[int a](int b, int c).\n" +
				"Bar[int a](int b).\n"+
				"Foo[t](a,b):-Bar[x](y), a= (3-1)*(x+30-y/2), b=x*y*z.";
		SociaLiteParser parser = getParser(query);
		prog_return prog=null;
		try { prog = parser.prog();
		} catch (RecognitionException e) { e.printStackTrace(); }
		
		CommonTree ast = (CommonTree)prog.getTree();		
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(ast);
		SociaLiteRule graphlite = new SociaLiteRule(nodes);
		SociaLiteRule.prog_return ret=null;
		try {
			ret = graphlite.prog();
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		}
		Expr expr = (Expr)((RuleDecl)ret.result.get(2)).body.get(1);
		Assert.true_(expr.root instanceof AssignOp);
		AssignOp op1 = (AssignOp)expr.root;
		Assert.true_(op1.arg2 instanceof BinOp);
		BinOp op2 = (BinOp) op1.arg2;
		Assert.equals(op2.arg1.toString(), "(3-1)");
	}
	
	static void testFunctionExpr() {
		String query = "Foo[int a](int b, int c).\n" +
				"Bar[int a](int b).\n"+
				"Foo[t](a,b) :- Bar[x](y), a=$Func(x, y), (b,c)=$Func1(a).\n";
		SociaLiteParser parser = getParser(query);
		prog_return prog=null;
		try { prog = parser.prog();
		} catch (RecognitionException e) { e.printStackTrace(); }
		
		CommonTree ast = (CommonTree)prog.getTree();		
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(ast);
		SociaLiteRule graphlite = new SociaLiteRule(nodes);
		SociaLiteRule.prog_return ret=null;
		try {
			ret = graphlite.prog();
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		}
		Expr expr = (Expr)((RuleDecl)ret.result.get(2)).body.get(1);
		Assert.true_(expr.root instanceof AssignOp);
		AssignOp op1 = (AssignOp)expr.root;
		Assert.true_(op1.arg1 instanceof Variable);
		Assert.true_(op1.arg2 instanceof Function);
		
		expr = (Expr)((RuleDecl)ret.result.get(2)).body.get(2);
		Assert.true_(expr.root instanceof AssignOp);
		op1 = (AssignOp)expr.root;
		Assert.true_(op1.arg1 instanceof List);
		Assert.true_(((List)op1.arg1).size()==2);
		Assert.true_(((List)op1.arg1).get(0) instanceof Variable);
		Assert.true_(op1.arg2 instanceof Function);		
	}
	
	static void testParsingMultipleString() {
		String query = "Foo[int a](int b, int c).\n" +
				"Bar[int a](int b).\n"+
				"Foo[t](a,b):- Bar[x](y), a=x, y=b.";
		SociaLiteParser parser = getParser(query);
		prog_return prog=null;
		try { prog = parser.prog();
		} catch (RecognitionException e) { e.printStackTrace(); }
		
		CommonTree ast = (CommonTree)prog.getTree();		
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(ast);
		SociaLiteRule graphlite = new SociaLiteRule(nodes);
		SociaLiteRule.prog_return ret=null;
		try {
			ret = graphlite.prog();
		} catch (RecognitionException e) {
			Assert.die(e.toString());
		}
		
		String query2="Foo[a](b,c) :- Foo[a](b,1), c=10 .";
		parser = getParser(query2);
		try { prog = parser.prog();
		} catch (RecognitionException e) { e.printStackTrace(); }
		
		CommonTree ast2 = (CommonTree)prog.getTree();		
		CommonTreeNodeStream nodes2 = new CommonTreeNodeStream(ast2);
		SociaLiteRule graphlite2 = new SociaLiteRule(nodes2);
		graphlite2.tableDeclMap = graphlite.tableDeclMap;		
		try {
			ret = graphlite2.prog();
		} catch (RecognitionException e) {
			Assert.die(e.toString());
		}
		Assert.true_(ret.result.size()==1);
		RuleDecl rd = (RuleDecl)ret.result.get(0);
		Assert.equals(rd.head.name(), "Foo");
		
		String query3="Foo2[a](b,c) :- Foo[a](b,1), c=10 .";
		parser = getParser(query3);
		try { prog = parser.prog();
		} catch (RecognitionException e) { e.printStackTrace(); }
		
		CommonTree ast3 = (CommonTree)prog.getTree();		
		CommonTreeNodeStream nodes3 = new CommonTreeNodeStream(ast3);
		SociaLiteRule graphlite3 = new SociaLiteRule(nodes3);
		graphlite3.tableDeclMap = graphlite2.tableDeclMap;		
		try {
			ret = graphlite3.prog();
			System.out.println("Should not reach here");
		} catch (RecognitionException e) {
			Assert.die(e.toString());
		} catch (RuntimeException e) {
			//pass
		}
	}
	static void dot() { System.out.print("."); }
    public static void main(String args[]) throws Exception {
    	test1(); dot();
    	test2(); dot();
    	testSimpleRule(); dot();
    	testComplexRule(); dot();
    	testTableDecl(); dot();
    	testTableDeclWithOption(); dot();    	
    	testExpr(); dot();
    	testFunctionExpr(); dot();
    	testParsingMultipleString(); dot();    	
    	
    	System.out.println("AntlrParserTest done");
    }*/
}
