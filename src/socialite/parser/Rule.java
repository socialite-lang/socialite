package socialite.parser;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import socialite.codegen.Epoch;
import socialite.parser.antlr.RuleDecl;
import socialite.util.AnalysisException;
import socialite.util.Assert;
import socialite.util.IdFactory;
import socialite.util.InternalException;
import gnu.trove.set.hash.TIntHashSet;

public class Rule implements Externalizable {
	private static final long serialVersionUID = 1;
	
	transient List<Rule> deps = new ArrayList<Rule>();
	transient List<Rule> usedBy = new ArrayList<Rule>();
	transient Epoch epoch=null;

    int epochId;
    int id;
    RuleDecl ruleDecl;
	boolean isLeftRec=false; // indicates that this rule is (linearly) left-recursive
	boolean isLeftRecOpt=false; // above plus array table is used for the rule-head
	boolean deltaStepOpt=false; // use delta-step algorithm
	transient Variable deltaStepVar=null;
	boolean inScc=false; 
	boolean simpleArrayInit=false;
	boolean hasPipelined = false;
	boolean useArrayTableLock = false;

	public Rule() { }
	
	public Rule(RuleDecl _ruleDecl) {
		ruleDecl = _ruleDecl;
		id = IdFactory.nextRuleId();
	}		
	
	Set<Function> findFunctions() {
		Set<Function> s = new LinkedHashSet<Function>();
		if (ruleDecl.head.hasFunctionParam()) {
			s.add(ruleDecl.head.getAggrF());
		}
		for (Object o:ruleDecl.body) {
			if (!(o instanceof Expr)) continue;
			Expr e=(Expr)o;
			if (!(e.root instanceof AssignOp)) continue;
			AssignOp op = (AssignOp)e.root;
			if (op.fromFunction()) {
				Function f=op.getFunction();
				s.add(f);
			}
		}
		return s;
	}

    public int getEpochId() { return epochId; }
	public void setInScc() { inScc=true; }
	public boolean inScc() { return inScc; }
	public void setDijkstraOpt() { 
		isLeftRecOpt=true;
		setLeftRec();
	}
	public boolean isDijkstraOpt() { return isLeftRecOpt; }	
	public void setLeftRec() { isLeftRec=true; }
	public boolean isLeftRec() { return isLeftRec; }
	public void setDeltaStepOpt() { deltaStepOpt = true; }
	public void setDeltaStepOpt(Variable v) {
		assert getBodyVariables().contains(v);
		deltaStepVar = v;
		deltaStepOpt=true; 
	}
	public boolean isDeltaStepOpt() { return deltaStepOpt; }
	
	public boolean isSimpleUpdate() { return ruleDecl.isSimpleUpdate(); }
	
	public void copyRuleProperties(Rule r) {
        epochId = r.epochId;
		inScc = r.inScc;
        simpleArrayInit = r.simpleArrayInit;
		isLeftRecOpt = r.isLeftRecOpt;	
		isLeftRec = r.isLeftRec;
		deltaStepOpt = r.deltaStepOpt;
		deltaStepVar = r.deltaStepVar;
		hasPipelined = r.hasPipelined;
		useArrayTableLock = r.useArrayTableLock;
		// deps and usedBy fields should be updated later				
	}
	
	public void setEpoch(Epoch _e) {
        epoch = _e;
        epochId = epoch.id();
    }
	public void recomputeDeps() {
		Iterator<Rule> it = deps.iterator();
		while(it.hasNext()) {
			Rule r=it.next();
			if (r.epoch != epoch)
				it.remove();
		}
		it = usedBy.iterator();
		while(it.hasNext()) {
			Rule r=it.next();
			if (r.epoch != epoch)
				it.remove();
		}
	}
	
	public int id() {return id;}
	public int hashCode() { return id;}
	
	public boolean equals(Object o) {
		if (!(o instanceof Rule)) return false;
		Rule r=(Rule)o;
		return r.ruleDecl.equals(ruleDecl) && getClass().equals(r.getClass());
	}
	
	public List<Expr> getExprs() {
		List<Expr> exprlist = null;
		for (Object o:getBody()) {
			if (o instanceof Expr) {
				if (exprlist==null) exprlist = new ArrayList<Expr>();
				exprlist.add((Expr)o);
			}
		}
		if (exprlist==null) return Collections.emptyList();
		else return exprlist;
	}
	public Set<Function> getFunctions() {
		return findFunctions();
		//return ruleDecl.getFunctions(); 
	}
	
	public List<Const> getConsts() {
		SortedSet<Const> _consts = new TreeSet<Const>();		
		for (Object o:getHead().getAllParamsExpanded()) {
			if (o instanceof Const) 
				_consts.add((Const)o);
		}
		for (Object o:getBody()) {
			if (o instanceof Predicate) {
				Predicate p=(Predicate)o;
				for (Object x:p.getAllParamsExpanded()) {
					if (x instanceof Const)
						_consts.add((Const)x);
				}
			} else {
				Expr e = (Expr)o;
				_consts.addAll(e.getConsts());
			}
		}
		return new ArrayList<Const>(_consts);
	}
	public void computeParamTypes(Map<String, Table> tableMap) {
		Predicate currentP=null;
		try {
			for (Predicate p:getBodyP()) {
				currentP=p;
				Table t = tableMap.get(p.name());
				p.computeVarTypes(t);
			}
			//currentP=getHead();
			//getHead().computeVarTypes(tableMap);
		} catch (InternalException e) {
			throw new AnalysisException(e, this, currentP);
		}
	}
	
	public void setHasPipelinedRule() {
		hasPipelined = true;
	}
	public boolean hasPipelinedRules() {
		return hasPipelined;
	}
	
	void addVariablesForPredicate(Predicate p, Set<Variable> vars) {		
		if (p.idxParam instanceof Variable) {
			vars.add((Variable)p.idxParam);
		}
		for(int i=0; i<p.params.size(); i++) {
			if (p.params.get(i) instanceof Variable) {
				vars.add((Variable)p.params.get(i));
			} else if (p.params.get(i) instanceof Function) {
				Function f = (Function)p.params.get(i);
				vars.addAll(f.getInputVariables());
				vars.addAll(f.getReturnVars());
			}
		}
	}
	
	void addVariablesForExpr(Expr expr, Set<Variable> vars) {
		vars.addAll(expr.getVariables());
	}
	
	void addVariablesForFunc(Function f, Set<Variable> vars) {
		vars.addAll(f.getInputVariables());
		vars.addAll(f.getReturnVars());
	}
	
	public Map<String, Variable> getBodyVariableMap() {
		HashMap<String, Variable> map = new HashMap<String, Variable>();
		for (Variable v:getBodyVariables()) {
			map.put(v.name, v);
		}
		return map;
	}
	public Set<Variable> getBodyVariables() {
		Set<Variable> vars = new LinkedHashSet<Variable>();
		for (Object o:getBody()) {
			if (o instanceof Predicate) {
				addVariablesForPredicate((Predicate)o, vars);
			} else if (o instanceof Expr) {
				addVariablesForExpr((Expr)o, vars);
			} else if (o instanceof Function) {
				addVariablesForFunc((Function)o, vars);
			} else {
				Assert.die("Unexpected type, o["+
						o.getClass().getSimpleName()+"]");
			}			
		}
		return vars;
	}
	
	public boolean hasOnlySimpleAssignExpr() {
		for (Object o:getBody()) {
			if (!(o instanceof Expr)) 
				return false;
			Expr expr = (Expr)o;
			if (!(expr.root instanceof AssignOp))
				return false;
			AssignOp op=(AssignOp)expr.root;
			if (op.fromFunction()) // not a simple assignment 
				return false;
		}
		return true;
	}
	boolean allVariables(Object[] params) {
		for (Object o:params) {
			if (!(o instanceof Variable)) return false;
		}
		return true;
	}
	
	public void setSimpleArrayInit() { simpleArrayInit=true; }
	public boolean isSimpleArrayInit() { return simpleArrayInit; }
	
	public Set<Variable> getHeadVariables() {
		return ruleDecl.head.getVariables();
	}
	
	public String name() {
		return ruleDecl.head.name()+"#"+id;
	}
	public Predicate getHead() {
		return ruleDecl.head;
	}
	
	public List<Literal> getBody() {
		return ruleDecl.body;
	}
	
	public Object firstPredOrFunction() {
		for (Object o:ruleDecl.body){
			if (o instanceof Predicate ||o instanceof Function) 
				return o;
		}		
		//Assert.die("No predicate or function in rule:"+ name());
		return null;
	}
	
	public Predicate firstP() {
		for (Object o:ruleDecl.body){
			if (o instanceof Predicate) return (Predicate)o;
		}
		return null;
	}
	
	public Predicate secondP() {
		int count=0;
		for (Object o:ruleDecl.body){
			if (o instanceof Predicate) {
				count++;				
				if (count==2) return (Predicate)o;
			}			
		}
		return null;
	}
	
	public Predicate lastP() {
		Predicate p=null;
		for (Object o:ruleDecl.body){
			if (o instanceof Predicate) p = (Predicate)o;
		}
		return p;
	}
	
	public List<Predicate> getBodyP() {
		List<Predicate> preds = new ArrayList<Predicate>();
		for (Object o:ruleDecl.body){
			if (o instanceof Predicate) {
				preds.add((Predicate)o);
			}
		}
		return preds;
	}
	
	public List<Predicate> getAllP() {
		List<Predicate> list = getBodyP();
		list.add(getHead());
		return list;
	}
	
	public List<Rule> getDependingRules() { return deps; }	
	public List<Rule> getRulesUsingThis() { return usedBy; }
	
	public void dependOn(Rule r) {
		if (deps.contains(r)) return;
		deps.add(r);
	}
	
	public void usedBy(Rule r) {
		if (usedBy.contains(r)) return;
		usedBy.add(r);
	}
		
	public String toString() {
		return ruleDecl.toString();
	}
	public String signature(Map<String, Table> tableMap) {
		String sig="";
		Predicate p=getHead();
		Table t=tableMap.get(p.name());
		sig += p.signature(t.signature()); //sig += p.signature(p.name());
		sig += ":-";
		boolean first=true;
		for (Object o:getBody()) {
			if (!first) sig += ",";			
			if (o instanceof Predicate) {
				Predicate p2=(Predicate)o;
				t=tableMap.get(((Predicate)o).name());
				sig += p2.signature(t.signature());
				//sig += p2.signature(p2.name());
			} else {
				Expr e=(Expr)o;
				sig += e.sig();
			}
			first=false;
		}
		sig += ".";
		return sig;
	}
	/*
	public String descr() {
		String descr = ruleDecl.toString() + " <rule-id:"+ id() + ">";
		if (isLeftRecOpt) descr += ", linearly-recursive";
		if (inScc) descr += ", in-SCC";
		
		descr += "\n\t";
		descr += "depends on ";
		for (Rule r:deps) {
			descr += r.name() + " ,";
		}
		descr += "\n\t";
		descr += "used by ";
		for (Rule r:usedBy) {
			descr += r.name() + " ,";
		}
		return descr;
	}*/
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		ruleDecl = new RuleDecl();
		ruleDecl.readExternal(in);
		id = in.readInt();
        epochId = in.readInt();
		isLeftRec = in.readBoolean();
		isLeftRecOpt = in.readBoolean();
		deltaStepOpt = in.readBoolean();
		inScc = in.readBoolean();
		simpleArrayInit = in.readBoolean();
		hasPipelined = in.readBoolean();
		useArrayTableLock = in.readBoolean();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		ruleDecl.writeExternal(out);
		out.writeInt(id);
        out.writeInt(epochId);
		out.writeBoolean(isLeftRec);
		out.writeBoolean(isLeftRecOpt);
		out.writeBoolean(deltaStepOpt);
		out.writeBoolean(inScc);
		out.writeBoolean(simpleArrayInit);
		out.writeBoolean(hasPipelined);
		out.writeBoolean(useArrayTableLock);
	}
}
