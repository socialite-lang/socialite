package socialite.codegen;

import java.util.*;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import socialite.engine.Config;
import socialite.engine.LocalEngine;
import socialite.parser.AssignOp;
import socialite.parser.DeltaPredicate;
import socialite.parser.Expr;
import socialite.parser.IterTable;
import socialite.parser.MyType;
import socialite.parser.Predicate;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.parser.Variable;
import socialite.tables.QueryVisitor;
import socialite.tables.Tuple;
import socialite.util.Loader;
import socialite.util.SociaLiteException;

public class TupleCodeGen {
	static String tuplePackage = "socialite.tables";
	static STGroup tmplGroup = CodeGen.getTupleGroup();

	Class<?>[] types;
	ST tupleTmpl;
	String tupleClassName;

	public TupleCodeGen(Class<?>[] _types) {
		tupleTmpl = tmplGroup.getInstanceOf("tuple");
        types = _types;
	}

	public String name() {
		if (tupleClassName == null) {
			ST tupleNameTmpl = tmplGroup.getInstanceOf("name");
			for (int i = 0; i < types.length; i++) {
				if (MyType.isPrimitive(types[i]))
					tupleNameTmpl.add("types", MyType.javaTypeName(types[i]));
				else
					tupleNameTmpl.add("types", "Object");
			}
			tupleClassName = tupleNameTmpl.render().replace("\n", "");
		}
		return tuplePackage + "." + tupleClassName;
	}

	public String generate() {
		for (int i = 0; i < types.length; i++) {
			if (MyType.isPrimitive(types[i]))
				tupleTmpl.add("types", MyType.javaTypeName(types[i]));
			else
				tupleTmpl.add("types", "Object");
		}
		return tupleTmpl.render();
	}

	// static (helper) methods

	public static void main(String[] args) {
		String query = "Foo(int s:0..1, String s2, int d, double d2). \n"
				+ "Bar(int s:0..1, String s2).\n"
				+ "Foo(s, $Debug(a,b,c)) :- s=0, a=\"str\", b=10, c=3.3 .\n"
				+ "Bar(s, s2) :- Foo(s, s2, a,b). \n" + "?-Foo(a,b,c,d).";

		LocalEngine e = new LocalEngine(Config.seq());
		e.run(query, new QueryVisitor() {
			public boolean visit(Tuple t) {
				System.out.println(t.get(0) + "," + t.get(1) + "," + t.get(2)
						+ "," + t.get(3));
				return true;
			}
		});

		query = "Edge(int s:0..430000, (int t, int length)). \n"
				+ "Edge(s,t,d) :- l=$Read(\"data/amazon-dist.txt\"),(s1,s2,s3)=$Split(l,\"\t\"),s=$ToInt(s1),t=$ToInt(s2),d=$ToInt(s3).";
		e.run(query);

		query = "Path(int s:0..430000, int d).\n"
				+ "Path(s, d) :- s=0, d=0. \n"
				+ "Path(s, $Min(d)) :- Path(n,d1), Edge(n,s,d2), d=d1+d2.";
		e.run(query);
		e.shutdown();
	}

	final static int PREGEN_WIDTH = 3; // Max # of columns for pre-generated tuples
	public static LinkedHashMap<String, byte[]> generate(Config conf, List<Rule> rules,
			Map<String, Table> tableMap) {
		Compiler c = new Compiler(conf.isVerbose());
		TupleDecls toGen = new TupleDecls();
		for (Rule r : rules) {
			findTuplesToGen(r, tableMap, toGen);
		}
		if (toGen.isEmpty())
			return new LinkedHashMap<String, byte[]>(0);

        LinkedHashMap<String, byte[]> generatedClasses = new LinkedHashMap<String, byte[]>();
        for (TupleDecl tupDecl : toGen) {
			TupleCodeGen tgen = new TupleCodeGen(tupDecl.types);
			if (Loader.exists(tgen.name()))
				continue;

			boolean success = c.compile(tgen.name(), tgen.generate());
			if (!success) {
				String msg = "compilation error for " + tgen.name() + " "
						+ c.getErrorMsg();
				throw new SociaLiteException(msg);
			}
            generatedClasses.putAll(c.getCompiledClasses());
			Loader.forName(tgen.name());
		}
		return generatedClasses;
	}

	static void findTuplesToGen(Rule rule, Map<String, Table> tableMap,
			TupleDecls toGen) {
		Predicate h = rule.getHead();
		Table ht = tableMap.get(h.name());
		_findTuplesToGen(ht, toGen);

		for (Object o : rule.getBody()) {
			if (o instanceof Predicate) {
				Predicate p = (Predicate) o;
				if (p instanceof DeltaPredicate)
					continue;
				Table t = tableMap.get(p.name());
				_findTuplesToGen(t, toGen);
			} else if (o instanceof Expr) {
				Expr expr = (Expr) o;
				if (!(expr.root instanceof AssignOp))
					continue;
				AssignOp assign = (AssignOp) expr.root;
				if (!assign.isMultipleAssign())
					continue;
				if (assign.isArrayAssign())
					continue;

				@SuppressWarnings("unchecked")
				List<Variable> assigned = (List<Variable>) assign.arg1;
				if (assigned.size() > PREGEN_WIDTH) {
					Class<?>[] types = new Class[assigned.size()];
					for (int i = 0; i < types.length; i++)
						types[i] = assigned.get(i).type;
					toGen.add(types);
				}
			}
		}
	}
	static void _findTuplesToGenForIterTable(Table t, TupleDecls required) {
		if (t instanceof IterTable && t.numColumns()+1 > PREGEN_WIDTH) {
			ArrayList<Class<?>> types = new ArrayList<Class<?>>(t.numColumns()+1);
			for (int i = 0; i < t.numColumns(); i++)
				types.add(t.getColumn(i).type());
			IterTable itable=(IterTable)t;
			types.add(itable.iterColumn(), int.class);
			required.add(types.toArray(new Class<?>[]{}));
		}
	}
	static void _findTuplesToGen(Table t, TupleDecls required) {
		if (t.numColumns() <= PREGEN_WIDTH) {
			_findTuplesToGenForIterTable(t, required);
			return;
		}

		Class<?>[] types = new Class[t.numColumns()];
		for (int i = 0; i < t.numColumns(); i++)
			types[i] = t.getColumn(i).type();
		required.add(types);
		_findTuplesToGenForIterTable(t, required);

		if (t.groupbyColNum() > PREGEN_WIDTH) {
			types = new Class[t.groupbyColNum()];
			for (int i = 0; i < t.groupbyColNum(); i++)
				types[i] = t.getColumn(i).type();
			required.add(types);
		}
		if (t.groupbyRestColNum() > PREGEN_WIDTH) {
			types = new Class[t.groupbyRestColNum()];
			for (int i = t.groupbyColNum(); i < t.numColumns(); i++)
				types[i - t.groupbyColNum()] = t.getColumn(i).type();
			required.add(types);
		}
	}
}

class TupleDecls implements Iterable<TupleDecl> {
	Set<TupleDecl> tupleDecls;

	TupleDecls() {
	}

	Set<TupleDecl> tupleTypes() {
		if (tupleDecls == null)
			tupleDecls = new LinkedHashSet<TupleDecl>();
		return tupleDecls;
	}

	public boolean isEmpty() {
		if (tupleDecls == null)
			return true;
		return tupleDecls.isEmpty();
	}

	public void add(Class<?>[] types) {
		TupleDecl tt = new TupleDecl(types);
		add(tt);
	}

	public void add(TupleDecl tt) {
		if (!tupleTypes().contains(tt)) {
			addReally(tt);
		}
	}

	void addReally(TupleDecl tt) {
		/*
		 * for (int i=4; i<tt.columnNum(); i++) { Class[] types =
		 * Arrays.copyOf(tt.types, i); TupleDecl tt2 = new TupleDecl(types);
		 * tupleTypes().add(tt2); }
		 */
		tupleTypes().add(tt);
	}

	public Iterator<TupleDecl> iterator() {
		return tupleDecls.iterator();
	}
}

class TupleDecl {
	Class<?>[] types;

	TupleDecl(Class<?>[] _types) {
		types = _types;
	}

	public int columnNum() {
		return types.length;
	}

	@Override
	public int hashCode() {
		int h = 0;
		for (Class<?> t : types)
			h ^= t.hashCode();
		return h;
	}

	public boolean equals(Object o) {
		if (!(o instanceof TupleDecl))
			return false;

		TupleDecl t = (TupleDecl) o;
		if (types.length != t.types.length)
			return false;

		for (int i = 0; i < types.length; i++) {
			if (!types[i].equals(t.types[i]))
				return false;
		}
		return true;
	}
}