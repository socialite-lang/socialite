package socialite.parser;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.stringtemplate.v4.ST;

public class Expr implements Literal {
	private static final long serialVersionUID = 42;	

	public Op root;
    public Expr() { }
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
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		root = (Op)in.readObject();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(root);
	}
}
