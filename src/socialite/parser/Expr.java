package socialite.parser;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.stringtemplate.v4.ST;

public class Expr implements Serializable {
	public Op root;
	public Expr(Op op) {
		root = op;
	}
	
	public boolean isFunctionAssign() {
		if (root instanceof AssignOp) {
			AssignOp assign=(AssignOp)root;
			return assign.fromFunction();
		}
		return false;
	}
	
	public ST codegen() { return root.codegen(); }
	
	public Set<Variable> getVariables() { return root.getVars(); }	
	public SortedSet<Const> getConsts() { return root.getConsts(); }
	public void visit(OpVisitor v) { root.visit(v); }
	
	public String toString() {
		if (root instanceof AssignOp)
			return root.toString();
		return "("+root.toString()+")";
	}
	
	public String sig() {
		return root.sig();
	}
}
