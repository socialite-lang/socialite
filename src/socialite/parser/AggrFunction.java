package socialite.parser;


import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

import org.python.core.PyBaseCode;
import org.stringtemplate.v4.ST;

import socialite.codegen.CodeGen;
import socialite.collection.SArrayList;
import socialite.functions.PyInvoke;
import socialite.util.Assert;
import socialite.util.InternalException;
import socialite.util.SociaLiteException;

public class AggrFunction extends Function implements Externalizable {
	static final long serialVersionUID = 1;
	
	int idxInParamList=-1;
	Table table;          // the table this function is applied to.
	Predicate predicate;  // the predicate this aggregate function is applied

	Class aggrColumnType;
	public AggrFunction() {
		super(); 
	}
	public AggrFunction(Function f) {
		name = f.name;
		args = f.getArgs();
        returns = null;
	}
	public AggrFunction(AggrFunction aggr, List<Param> _args) {
		assert aggr.isLoaded();
		name = aggr.name;
		args = new SArrayList<Param>(_args);
		returns = null;
		isArrayType = aggr.isArrayType;
		klass = aggr.klass();
		method = aggr.method;
		pyname = aggr.pyname;
		pyfunc = aggr.pyfunc;
		pyfuncIdx = aggr.pyfuncIdx;
	}
	
	public void setIdx(int idx) { idxInParamList = idx; }
	public int getIdx() { return idxInParamList; }
	
	public boolean isAggregate() { return true; }
	public boolean isInRuleHead() { return true; }
		
	public boolean multiRow() {
		int funcIdx=idxInParamList;
		for (int i=funcIdx; i<funcIdx+getArgs().size(); i++) {
			if (table.nestingBegins(i)) return true;	
		}
		return false;
	}
	public boolean singleRow() {
		return !multiRow();					
	}
	
	void computeJavaReturnVarTypes(TypeCast cast) throws InternalException {
		Class<?> columnType=getAggrColumnType();
		Class<?> arg0Type=MyType.javaType(getArgs().get(0));
		if (!arg0Type.isAssignableFrom(columnType)) {
			throw new InternalException("Unexpected 1st argument type "+nameWithArgTypes());
		}
		checkReturnType();		
	}	
	void computePyReturnVarTypes(TypeCast cast) throws InternalException {
		PyBaseCode code = (PyBaseCode)pyfunc.func_code;
		if (code.co_argcount != getArgs().size()+1) {
			throw new InternalException("Unexpected number of arguments in "+pyname);
		}
		checkReturnType();
	}
	void checkReturnType() throws InternalException {
		Class retType = getMethodReturnType();
		if (isSimpleAggr()) {
			if (!retType.equals(getAggrColumnType())) {
				String t=retType.getSimpleName();
				throw new InternalException("Unexpected return type("+t+") from "+nameWithArgTypes());
			}
		} else {
			if (!retType.equals(boolean.class)) {
				String t=retType.getSimpleName();
				throw new InternalException("Unexpected return type("+t+") from "+nameWithArgTypes());
			}
		}
	}
	
	public void load() throws InternalException {
		if (!allArgsTyped()) return;
		
		loadReally();
	}
		
	public List<Variable> getReturns() {
		if (returns==null) {
            returns = makeReturnVars();
        }
		return returns;
	}
	SArrayList<Variable> makeReturnVars() {
		SArrayList<Variable> retVars = new SArrayList<Variable>(1);
		Variable v=new Variable("aggr$ret", getMethodReturnType());
		retVars.add(v);
		return retVars;
	}
	public boolean isSimpleAggr() {
		if (getArgs().size()==1) {
			Class argType = MyType.javaType(getArgs().get(0));					
			return getAggrColumnType().equals(argType);
		}
		return false;
	}
	
	public Class getAggrColumnType() {
		if (aggrColumnType==null) {
			int offset = table.groupbyColNum();
			aggrColumnType = table.getColumn(offset).type();
		}
		return aggrColumnType;
	}
	@Override
	Class[] makeArgTypes() throws InternalException {
		Class[] argTypes = new Class[getArgs().size()+1];
		argTypes[0] = getAggrColumnType();
		for (int i=0; i<getArgs().size(); i++) {
			argTypes[i+1] = MyType.javaType(getArgs().get(i));
		}
		return argTypes;
	}	
	
	public boolean hasRemainingParam() {
		int fidx=idxInParamList;		
		if (predicate.inputParams().length==fidx+getReturns().size()) 
			return false;
		return true;
	}
	
	public void initPredicateInfo(Table t, Predicate p, int pos) {
		table = t;
		predicate = p;
		idxInParamList = pos;		
	}
	
	public ST codegen(String ans) {
		ST expr = CodeGen.expr();
		String invoke = invokeBegin()+ans;
		for (int i = 0; i < getArgs().size(); i++) {
			Object o=getArgs().get(i);
			invoke += ", "+o;			
		}
		invoke += ")";		
		if (isPython()) {
			Variable ret = getReturns().get(0); 
			if (MyType.isPrimNumberType(ret.type)) {
				String pyToJava=MyType.javaObjectTypeName(ret.type);
				invoke="("+pyToJava+")((Number)"+invoke+")";
			} else {
				String pyToJava=MyType.javaObjectTypeName(ret.type);
				invoke="("+pyToJava+")"+invoke;
			}	
		}		
		expr.add("expr", invoke);
		return expr;
	}
	public ST codegen() {
		Assert.die("AggrFunction.codegen() is disabled. Use codegen(String ans) instead.");
		return null;
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		idxInParamList = in.readInt();
		table = (Table) in.readObject();
		predicate = (Predicate) in.readObject();
		super.readExternal(in);
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
        getReturns();
		out.writeInt(idxInParamList);
		out.writeObject(table);
		out.writeObject(predicate);		
		super.writeExternal(out);
	}
}
