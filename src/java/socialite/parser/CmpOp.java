package socialite.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.stringtemplate.v4.ST;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


import socialite.codegen.CodeGen;
import socialite.util.Assert;
import socialite.util.InternalException;


public class CmpOp extends Op {
    public static enum CmpType {
        LT(-2), LT_EQ(-1), EQ(0), GT_EQ(1), GT(2);
        private int value;
        private CmpType(int val) {
            value = val;
        }
        public boolean lessThan() {
            return this == LT_EQ || this == LT;
        }
        public boolean greaterThan() {
            return this == GT_EQ || this == GT;
        }
        public boolean inclusive() {
            return this == LT_EQ || this == GT_EQ || this == EQ;
        }
        public CmpType reverse() {
            switch(this) {
                case LT: return GT;
                case LT_EQ: return GT_EQ;
                case EQ: return EQ;
                case GT_EQ: return LT_EQ;
                case GT: return LT;
                default:
                    Assert.impossible();
                    return this;
            }
        }
    }
    public static int EQ=1, LT=1, GT=1;

    private static final long serialVersionUID = 1L;

	String op;
	Object arg1, arg2;
    public CmpOp() { }
	public CmpOp(String _op, Object _arg1, Object _arg2) throws InternalException {
		op = _op;
		arg1=_arg1;
		arg2=_arg2;
		assert !(arg1 instanceof Function);
		assert !(arg2 instanceof Function);

	}
	
	public ST codegen() {
		ST expr=CodeGen.expr();	
		
		String cmpExpr="("+cmpExprStr()+")";
		expr.add("expr", cmpExpr);
		return expr;
	}

	public String sig() {
		return descr(true);
	}
	public String toString() {
		return descr(false);
	}
	String descr(boolean sig) {
		String arg1Str=descr(sig, arg1);		
		String arg2Str=descr(sig, arg2);
		return arg1Str + op + arg2Str;
	}

	public String cmpExprStr() {
		if (!MyType.isPrimitive(arg1)) {
			return toStringForObjectArgs();
		}
		return arg1 + op + arg2;
	}
	
	String toStringForObjectArgs() {
		if (op.equals("==")) {
			return arg1+".equals("+arg2+")";
		} else if (op.equals("!=")) {
			return "!"+arg1+".equals("+arg2+")";
		} else if (op.equals("<")) {
			return arg1+".compareTo("+arg2+")<0";
		} else if (op.equals("<=")) {
			return arg1+".compareTo("+arg2+")<=0";
		} else if (op.equals(">")) {
			return arg1+".compareTo("+arg2+")>0";
		} else if (op.equals(">=")) {
			return arg1+".compareTo("+arg2+")>=0";
		} else {
			Assert.not_supported();
			return null;
		}
	}

	public void getTypes(Collection<Class> types) {
		if (arg1 instanceof Op) ((Op)arg1).getTypes(types);
		else types.add(MyType.javaType(arg1));
		
		if (arg2 instanceof Op) ((Op)arg2).getTypes(types);
		else types.add(MyType.javaType(arg2));
	}
	
	public Set<Variable> getVars() {
		Set<Variable> vars = new HashSet<Variable>();
		getVariables(vars, arg1);
		getVariables(vars, arg2);
		return vars;
	}
	
	public SortedSet<Const> getConsts() {
		SortedSet<Const> consts = new TreeSet<Const>();
		getConsts(consts, arg1);
		getConsts(consts, arg2);
		return consts;
	}
	
	public Object getLHS() { return arg1; }
	public Object getRHS() { return arg2; }
	public String getOp() { return op; }
	
	public void visit(OpVisitor v) {
		v.visit(this);
		if (arg1 instanceof Op)
			v.visit((Op)arg1);
		if (arg2 instanceof Op)
			v.visit((Op)arg2);
	}

    public CmpType cmpType() {
        if (op.equals("<")) {
            return CmpType.LT;
        } else if (op.equals("<=")) {
            return CmpType.LT_EQ;
        } else if (op.equals("==")) {
            return CmpType.EQ;
        } else if (op.equals(">=")) {
            return CmpType.GT_EQ;
        } else if (op.equals(">")) {
            return CmpType.GT;
        } else {
            Assert.impossible();
            return CmpType.EQ;
        }
    }
	public int opIdReversed() {
		return -opId();
	}
	public int opId() {
		if (op.equals("<")) {
			return -2;
		} else if (op.equals("<=")) {
			return -1;
		} else if (op.equals("==")) {
			return 0;
		} else if (op.equals(">=")) {
			return 1;
		} else if (op.equals(">")) {
			return 2;
		} else {
			throw new RuntimeException("Unsupported cmp-op:"+op);
		}
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		char[] _op = new char[in.readInt()];
		for (int i=0; i<_op.length; i++)
			_op[i] = in.readChar();
		op = new String(_op);
		arg1=in.readObject();
		arg2=in.readObject();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(op.length());
		out.writeChars(op);
		out.writeObject(arg1);
		out.writeObject(arg2);
	}

	/*
	public Set<Variable> getAllVariables() {
		return getVariables(arg1, arg2);
	}

	public Set<Variable> getReadVariables() {
		return getVariables(arg1, arg2);
	}

	public Set<Object> getAllVarsAndConsts() {
		return getVarsAndConsts(arg1, arg2);
	}

	public Set<Object> getReadVarsAndConsts() {
		return getVarsAndConsts(arg1, arg2);
	}*/
}
