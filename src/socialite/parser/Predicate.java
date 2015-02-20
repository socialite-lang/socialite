package socialite.parser;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import socialite.util.InternalException;
import socialite.collection.SArrayList;

public class Predicate implements Literal {
	private static final long serialVersionUID = 1;

	public static Predicate NIL = new Predicate();
	
	//public Param idxParam;	
	@SuppressWarnings("rawtypes")
	public SArrayList<Param> params;

	transient Param[] inputParams;
	transient Param[] outputParams;
	//transient Param[] allParams;
	//transient Param[] allParamsExp;
	//transient Param[] allOutputParams;
	boolean negated;
	int posAtRuleBody;
	String name;
	boolean isHeadPredicate;
	
	@SuppressWarnings("unchecked")
	@Override
	public Predicate clone() {
		@SuppressWarnings("rawtypes")
		SArrayList<Param> newParams = new SArrayList<Param>();
		newParams.addAll(params);
		Predicate p = new Predicate(name, newParams);
		p.negated = negated;
		p.posAtRuleBody = posAtRuleBody;
		p.isHeadPredicate = isHeadPredicate;
		return p;
	}
	public Predicate() {	}
	public Predicate(String _name, List<Param> _params) {
		name = _name;
		params = new SArrayList<Param>(_params);
		negated = false;
		posAtRuleBody = -1;
		isHeadPredicate = false;	
		setAggrFuncIdx();
	}
	
	void setAggrFuncIdx() {
		for (int i=0; i<params.size(); i++) {
			Object p=params.get(i);
			if (p instanceof AggrFunction) 
				((AggrFunction)p).setIdx(i);
		}
	}
	
	public void setName(String _name) {
		name = _name;
	}
	public String name() { return name;	}
	
	public int getPos() {		
		return posAtRuleBody;
	}	
	public void setPos(int i) { posAtRuleBody = i; }	
	
	public boolean isHeadP() { return isHeadPredicate; }
	public void setAsHeadP() { isHeadPredicate=true; }
	
	public void setNegated() { negated = true; }
	public boolean isNegated() { return negated; }
	
	public List<Object> getConstValues() {
		List<Object> consts = new ArrayList<Object>();
		for (int i=0; i<params.size(); i++) {
			if (params.get(i) instanceof Const) {
				Const c = (Const)params.get(i);
				consts.add(c.val);
			}			
		}
		return consts;
	}
	
	public String signature(String tableSignature) {
		return descr(tableSignature, true);
	}
	public String toString() {
		return descr(name(), false);		
	}
	
	String descr(String _name, boolean sig) {
		String result = _name;
		if (negated) result = "!" + result;
		
		result += "(";
		for (int i=0; i<params.size(); i++) {
			Object p = params.get(i);
			if (sig) {
                result += p;
            } else {
				if (p instanceof Const) {
					result += ((Const)p).constValStr();
				} else { result += p; }
			}
			if (i!= params.size()-1) result += ",";
		}
		result += ")";
		
		return result;
	}
	
	public boolean hasFunctionParam() {
		for (Param o:params) {
			if (o instanceof Function)
				return true;
		}
		return false;
	}
	
	public Function getF() {
		for (Param o:params) {
			if (o instanceof Function)
				return (Function)o;
		}
		return null;
	}
	public AggrFunction getAggrF() {		
		for (Param o:params) {
			if (o instanceof AggrFunction)
				return (AggrFunction)o;
		}
		return null;
	}		
	public int functionIdx() {
		int i=0;
		for (Param o:params) {
			if (o instanceof Function)
				return i;
			i++;
		}
		assert false:"should not reach here";
		return -1;
	}
	@SuppressWarnings("unchecked")
	public void replaceParamAt(int idx, Param newParam) {
		params.set(idx, newParam);
		inputParams = null;
		outputParams = null;
	}
	public Param removeParamAt(int i) {
		inputParams = null;
		outputParams = null;
		return params.remove(i);
	}	
	public Set<Variable> getVariables() {
		Set<Variable> vars = new LinkedHashSet<Variable>();		
		for(Param p:params) {
			if (p instanceof Variable) vars.add((Variable)p);			
			if (p instanceof AggrFunction) {
				AggrFunction f = (AggrFunction)p;
				vars.addAll(f.getInputVariables());
			}
		}
		return vars;
	}
	
	public Param first() { return params.get(0); }
	public Param last() { return params.get(params.size()-1); }
	
	@Deprecated
	public Param[] getAllInputParams() { return params.toArray(new Param[0]); }
	@Deprecated
	public Param[] getAllOutputParams() { 
		List<Param> tmp = new ArrayList<Param>();
		for (Param o:params) {
			if (o instanceof AggrFunction) {
				AggrFunction f=(AggrFunction)o;
				for (Param arg:f.getReturns()) tmp.add(arg);
			} else tmp.add(o);
		}
		return tmp.toArray(new Param[0]);
	}	
	
	public Param[] outputParams() {
		if (outputParams==null) {
			List<Param> tmp = new ArrayList<Param>();
			for (Param o:params) {
				if (o instanceof AggrFunction) {
					AggrFunction f=(AggrFunction)o;
					for (Param arg:f.getReturns()) tmp.add(arg);
				} else tmp.add(o);
			}
			outputParams = tmp.toArray(new Param[0]);
		}
		return outputParams;
	}
	public Param[] inputParams() {
		if (inputParams==null) {
			List<Param> tmp = new ArrayList<Param>();
			for (Param o:params) {
				if (o instanceof AggrFunction) {
					AggrFunction f=(AggrFunction)o;
					for (Param arg:f.getArgs()) tmp.add(arg);
				} else tmp.add(o);
			}
			inputParams = (Param[])tmp.toArray(new Param[0]);
		}
		return inputParams;
	}
	/*
	public Param[] getAllParams() {
		return getAllInputParams();
	}*/
	/*
	public Param[] getAllParamsExpanded() {
		if (allParamsExp==null) {
			List<Param> tmp = new ArrayList<Param>();
			for (Param o:params) {
				if (o instanceof AggrFunction) {
					AggrFunction f=(AggrFunction)o;
					for (Param arg:f.getArgs()) tmp.add(arg);
				} else tmp.add(o);
			}
			allParamsExp = (Param[])tmp.toArray(new Param[0]);
		}
		return allParamsExp;
	}*/
	public List<Param> getRestInputParams() {
		List<Param> tmp=new ArrayList<Param>();
		for (Param o:params) {
			if (o instanceof AggrFunction) {
				AggrFunction f=(AggrFunction)o;
				for (Param arg:f.getArgs()) tmp.add(arg);
			} else tmp.add(o);
		}
		return tmp;
	}
	public List<Param> getRestOutputParams() {
		List<Param> tmp=new ArrayList<Param>();
		for (Param o:params) {
			if (o instanceof AggrFunction) {
				AggrFunction f=(AggrFunction)o;
				for (Param arg:f.getReturns()) tmp.add(arg);
			} else tmp.add(o);
		}
		return tmp;
	}
	
	public void computeVarTypes(Table t) throws InternalException {
		Class<?>[] types = t.types();		
		for(int i=0; i<params.size(); ) {
			if (params.get(i) instanceof Variable) {
				Variable v = (Variable)params.get(i);				
				v.setType(types[i]);
				i++;
			} else if (params.get(i) instanceof AggrFunction) {
				AggrFunction f=(AggrFunction)params.get(i);								
				i+=f.args.size();
			} else {
				i++; // constants
			}
		}
	}	
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		params = new SArrayList(0);
		params.readExternal(in);
		negated = in.readBoolean();
		posAtRuleBody = in.readInt();
		char[] n = new char[in.readInt()];
		for (int i=0; i<n.length; i++) 
			n[i] = in.readChar();	
		name = new String(n);
		isHeadPredicate = in.readBoolean();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		params.writeExternal(out);
		out.writeBoolean(negated);
		out.writeInt(posAtRuleBody);
		out.writeInt(name.length());
		out.writeChars(name);
		out.writeBoolean(isHeadPredicate);
	}
}
