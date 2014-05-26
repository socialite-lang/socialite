package socialite.parser;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import socialite.util.InternalException;

public class Predicate implements Serializable {
	public static Predicate NIL = new Predicate();
	
	private static final long serialVersionUID = 1;
	
	public Object idxParam;	
	@SuppressWarnings("rawtypes")
	public List params;

	Object[] allParams;
	Object[] allParamsExp;
	Object[] allOutputParams;
	boolean negated;
	int posAtRuleBody;
	String name;
	boolean isHeadPredicate;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Predicate clone() {
		@SuppressWarnings("rawtypes")
		List newParams = new ArrayList();
		newParams.addAll(params);
		Predicate p = new Predicate(name, idxParam, new ArrayList<Object>(newParams));
		p.negated = negated;
		p.posAtRuleBody = posAtRuleBody;
		p.isHeadPredicate = isHeadPredicate;
		return p;
	}
	protected Predicate() {	}
	public Predicate(String _name, Object _idxParam, List<?> _params) {
		name = _name;
		idxParam = _idxParam;
		params = _params;
		negated = false;
		posAtRuleBody = -1;
		isHeadPredicate = false;	
		setAggrFuncIdx();
	}
	
	void setAggrFuncIdx() {
		int offset=0;
		if (idxParam!=null) offset=1;
		for (int i=0; i<params.size(); i++) {
			Object p=params.get(i);
			if (p instanceof AggrFunction) 
				((AggrFunction)p).setIdx(offset+i);
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
	
	public void setNegated() {
		/*Assert.die("need to handle negation!");*/
		negated = true;
	}
	
	public boolean isNegated() {
		return negated;
	}
	
	public List<Object> getConstValues() {
		List<Object> consts = new ArrayList<Object>();
		Object[] params = getAllParamsExpanded();
		for (int i=0; i<params.length; i++) {
			if (params[i] instanceof Const) {
				Const c = (Const)params[i];
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
		
		if (idxParam != null) {
			if (idxParam instanceof Const) {
				result += "[" + ((Const)idxParam).constValStr()+"]"; 
			} else  result += "[" + idxParam + "]";
		}
		result += "(";
		for (int i=0; i<params.size(); i++) {
			Object p = params.get(i);
			if (sig) result += p;
			else {
				if (p instanceof Const) {
					Const c=(Const)p;
					result += c.constValStr();
				} else result += p;
			}
			if (i!= params.size()-1) result += ",";
		}
		result += ")";
		
		return result;
	}
	
	public boolean hasFunctionParam() {
		for (Object o:params) {
			if (o instanceof Function)
				return true;
		}
		return false;
	}
	
	public Function getF() {
		for (Object o:params) {
			if (o instanceof Function)
				return (Function)o;
		}
		return null;
	}
	public AggrFunction getAggrF() {		
		for (Object o:params) {
			if (o instanceof AggrFunction)
				return (AggrFunction)o;
		}
		return null;
	}		
	public int functionIdx() {
		int offset=0;
		if (idxParam!=null) offset=1;
		int i=0;
		for (Object o:params) {
			if (o instanceof Function)
				return offset+i;
			i++;
		}
		assert false:"should not reach here";
		return -1;
	}
	@SuppressWarnings("unchecked")
	public void replaceParamAt(int idx, Object newParam) {
		if (idxParam!=null) {
			if (idx==0) {
				idxParam = newParam;
				return;
			}
			idx--;
		}
		params.set(idx, newParam);
		allParams = null;
		allParamsExp = null;
		allOutputParams = null;
	}
	public Object removeParamAt(int i) {
		Object ret;
		if (i==0 && idxParam != null) {
			ret = idxParam;
			idxParam = null;
			return ret;
		}		
		
		if (idxParam!=null) i--;
		
		ret = params.remove(i);
		allParams = null;
		allParamsExp = null;
		allOutputParams = null;
		return ret;
	}	
	public Set<Variable> getVariables() {
		Set<Variable> vars = new LinkedHashSet<Variable>();
		
		if (idxParam instanceof Variable) vars.add((Variable)idxParam);
		
		for(Object p:params) {
			if (p instanceof Variable) vars.add((Variable)p);			
			if (p instanceof AggrFunction) {
				AggrFunction f = (AggrFunction)p;
				vars.addAll(f.getInputVariables());				
			}
		}
		return vars;
	}
	
	public Object first() { return getAllParamsExpanded()[0]; }
	public Object last() { return getAllParamsExpanded()[getAllParamsExpanded().length-1]; }
	
	public Object[] getAllInputParams() { return getAllParamsExpanded(); }	
	public Object[] getAllOutputParams() { 
		if (allOutputParams==null) {
			List<Object> tmp = new ArrayList<Object>();
			if (idxParam!=null) tmp.add(idxParam);
			for (Object o:params) {
				if (o instanceof AggrFunction) {
					AggrFunction f=(AggrFunction)o;
					for (Object arg:f.getReturns()) tmp.add(arg);
				} else tmp.add(o);
			}
			allOutputParams = tmp.toArray();			
		}
		return allOutputParams;
	}	
	
	public Object[] getAllParams() {
		if (allParams==null) {
			List<Object> tmp = new ArrayList<Object>();
			if (idxParam!=null) tmp.add(idxParam);
			for (Object o:params) {
				tmp.add(o);
			}
			allParams = tmp.toArray();			
		}
		return allParams;
	}
	public Object[] getAllParamsExpanded() {
		if (allParamsExp==null) {
			List<Object> tmp = new ArrayList<Object>();
			if (idxParam!=null) tmp.add(idxParam);
			for (Object o:params) {
				if (o instanceof AggrFunction) {
					AggrFunction f=(AggrFunction)o;
					for (Object arg:f.getArgs()) tmp.add(arg);
				} else tmp.add(o);
			}
			allParamsExp = tmp.toArray();			
		}
		return allParamsExp;
	}
	public List<?> getRestInputParams() {
		List<Object> tmp=new ArrayList<Object>();
		for (Object o:params) {
			if (o instanceof AggrFunction) {
				AggrFunction f=(AggrFunction)o;
				for (Object arg:f.getArgs()) tmp.add(arg);
			} else tmp.add(o);
		}
		return tmp;
	}
	public List<?> getRestOutputParams() {
		List<Object> tmp=new ArrayList<Object>();
		for (Object o:params) {
			if (o instanceof AggrFunction) {
				AggrFunction f=(AggrFunction)o;
				for (Object arg:f.getReturns()) tmp.add(arg);
			} else tmp.add(o);
		}
		return tmp;
	}
	
	public void computeVarTypes(Table t) throws InternalException {
		Variable v;
		int offset= (idxParam==null)?0:1;
		if (idxParam instanceof Variable) {
			v = (Variable)idxParam;
			v.setType(t.idxType());			
		}
		@SuppressWarnings("rawtypes")
		Class[] types = t.types();		
		for(int i=0; i<params.size(); ) {
			if (params.get(i) instanceof Variable) {
				v = (Variable)params.get(i);				
				v.setType(types[offset+i]);
				i++;
			} else if (params.get(i) instanceof AggrFunction) {
				AggrFunction f=(AggrFunction)params.get(i);								
				i+=f.args.size();
			} else {
				i++; // constants
			}
		}
	}	
}