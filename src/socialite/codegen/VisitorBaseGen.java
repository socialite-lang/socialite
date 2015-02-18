package socialite.codegen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import socialite.engine.Config;
import socialite.parser.Column;
import socialite.parser.Table;
import socialite.parser.antlr.ColumnGroup;
import socialite.parser.antlr.ColumnDecl;
import socialite.util.Loader;
import socialite.util.SociaLiteException;


public class VisitorBaseGen {
	static STGroup tmplGroup = CodeGen.getVisitorBaseGroup();
	static int id = 0;
	
	String name;
	ST visitorBase;
	Set<MethodDecl> addedDecls;
	boolean visitorImpl;
	public VisitorBaseGen(boolean _visitorImpl) {
		visitorImpl = _visitorImpl;
		if (visitorImpl) {
			visitorBase = tmplGroup.getInstanceOf("visitorImpl");
			addedDecls = new LinkedHashSet<MethodDecl>();
			name = "VisitorImpl";
		} else {
			visitorBase = tmplGroup.getInstanceOf("visitorBase");
			addedDecls = new LinkedHashSet<MethodDecl>();
			name = "Visitor"+(id++);
			visitorBase.add("name", name);
		}
	}
	public String name() { return name; }
	
	public void addAll(Collection<MethodDecl> decls) {
		for (MethodDecl decl:decls) 
			add(decl);
	}
	public void add(MethodDecl decl) {
		if (addedDecls.contains(decl)) return;
		ST visit;
		if (visitorImpl) visit = tmplGroup.getInstanceOf("visit");
		else visit = tmplGroup.getInstanceOf("declVisit");
		for (Column c:decl.columns) {			
			visit.add("columns", c);
		}
		if (decl.isLastColumn())
			visit.add("outmost", true);
		visitorBase.add("methodDecls", visit);
		addedDecls.add(decl);
	}
	public String generate() {
		return visitorBase.render();
	}
	
	// static methods...
	
	static List<MethodDecl> getMethodDecls(int startPos, int len, boolean last) {	
		Column[] _cols = new Column[len];
		List<Column[]> results = new ArrayList<Column[]>();
		for (int i=0; i<types.length; i++) {
			getColumns(results, _cols, 0, startPos, i);
		}
		List<MethodDecl> decls = new ArrayList<MethodDecl>();
		for (Column[] cols:results) {
			MethodDecl md = new MethodDecl(cols, last);
			decls.add(md);
		}
		return decls;
	}
	static Class<?>[] types = {int.class, long.class, float.class, double.class, Object.class};
	static void getColumns(List<Column[]> results, Column[] cols, int pos, int startPos, int type) {
		ColumnDecl cd = new ColumnDecl(types[type], "col"+(pos+startPos));
		cd.setPos(startPos+pos);
		cols[pos] = new Column(cd);
		if (cols.length == pos+1) {
			Column[] copied = Arrays.copyOf(cols, cols.length);
			results.add(copied);
			return;
		}
		
		for (int i=0; i<types.length; i++) {
			getColumns(results, cols, pos+1, startPos, i);
		}
	}
	
	public static LinkedHashMap<String,byte[]> generate(Config conf, List<Table> tables) {
		LinkedHashMap<String,byte[]> generatedClasses = new LinkedHashMap<String,byte[]>();
		for (Table t:tables) {
			if (t.numColumns()<=3) continue;
		
			MethodDecls decls = new MethodDecls();
			for (int i=0; i<t.getColumnGroups().size(); i++) {				
				ColumnGroup cg = t.getColumnGroups().get(i);
				if (i==0 && cg.size() <= 3) continue;
				if (i==1 && cg.size() == 1) continue;
					
				boolean last = (i==t.getColumnGroups().size()-1);
				if (last && cg.size()<=3) continue;
				
				decls.add(new MethodDecl(cg.columns(), last));				
			}		
			if (decls.isEmpty()) continue;
			
			Compiler c = new Compiler(conf.isVerbose());
			VisitorBaseGen baseGen = new VisitorBaseGen(false);
			String fullname = VisitorCodeGen.visitorPackage+"."+baseGen.name();
			t.setVisitorInterface(fullname);
			for (MethodDecl d:decls)
				baseGen.add(d);
			boolean success=c.compile(fullname, baseGen.generate());
			if (!success) {
				String msg="Cannot compile "+fullname+".class";			
				throw new SociaLiteException(msg);			
			}
			generatedClasses.putAll(c.getCompiledClasses());
		}
		return generatedClasses;
	}	
	
	static boolean exists(Column[] columns, boolean last) {
		if (columns.length==0) return true;
		if (columns.length==1 && columns[0].getAbsPos()==0)
			return true;
		if (columns.length<=2 && last)
			return true;
		return false;
	}
	
	public static void main(String[] args) {
		gen();
	}
	public static void gen() {
		Compiler c = new Compiler();
		//VisitorBaseGen baseGen = new VisitorBaseGen(true);
		VisitorBaseGen baseGen = new VisitorBaseGen(false);
		
		for (int nest=0; nest<=1; nest++) {
			for (int len=1; len<=3; len++) {
				List<MethodDecl> decls;				
				if (nest==1) {
					decls = getMethodDecls(nest, len, true);
					baseGen.addAll(decls);
				} else {
					decls = getMethodDecls(nest, len, false);
					baseGen.addAll(decls);
				}
			}
		}		
		String visitorImpl = VisitorCodeGen.visitorPackage+".VisitorImpl";
		boolean success=c.compile(visitorImpl, baseGen.generate());
		if (!success) {
			String msg="Cannot compile VisitorImpl class";
			System.out.println(c.getErrorMsg());
			throw new SociaLiteException(msg);			
		}
	}	
}

class MethodDecls implements Iterable<MethodDecl> {
	Set<MethodDecl> methodDecls;
	
	MethodDecls() {	}
	
	Set<MethodDecl> methodDecls() {
		if (methodDecls==null)
			methodDecls = new LinkedHashSet<MethodDecl>();
		return methodDecls;
	}
	public String toString() {
		String s="[";
		if (!isEmpty()) {
			for (MethodDecl d:methodDecls) {
				s+= d;
			}
		}
		s+="]";
		return s;
	}
	public void add(MethodDecl decl) {
		if (decl.length()==0) return;
		
		if (!methodDecls().contains(decl))
			methodDecls().add(decl);
	}
	
	public boolean isEmpty() {
		if (methodDecls==null) return true;
		return methodDecls.isEmpty();
	}
	public Iterator<MethodDecl> iterator() {
		if (methodDecls==null) {
			return Collections.<MethodDecl>emptyList().iterator();
		}
		return methodDecls.iterator();
	}
}
class MethodDecl {
	Column[] columns;
	boolean last;
	MethodDecl(Column[] _columns, boolean _last) {
		columns = _columns;
		last = _last;
	}
	
	public int length() { return columns.length; }
	
	public String toString() {
		String s="(";
		for (Column c:columns) {
			s += c.type().getSimpleName()+" "+c.name()+", ";
		}
		s+=")";
		return s;
	}
	public boolean isLastColumn() {
		return last;
	}
	public int hashCode() {
		int h=0;
		for (Column c:columns) 
			h ^= c.type().hashCode();
		h ^= last ? 1:0;
		return h;
	}
	public boolean equals(Object o) {
		if (!(o instanceof MethodDecl)) return false;
		
		MethodDecl d=(MethodDecl)o;
		if (columns.length != d.columns.length) return false; 
		for (int i=0; i<columns.length; i++) {
			Column c1=columns[i];
			Column c2=d.columns[i];
			if (!c1.type().equals(c2.type()))
				return false;
		}
		if (last != d.last) return false;
		
		return true;
	}
}
