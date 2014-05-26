package socialite.codegen;

import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import socialite.parser.Const;
import socialite.parser.DeltaRule;
import socialite.parser.DeltaTable;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.parser.antlr.ClearTable;
import socialite.parser.antlr.DropTable;
import socialite.parser.antlr.TableStmt;
import socialite.resource.RuleMap;
import socialite.resource.VisitorBuilder;
import socialite.util.Assert;
import socialite.util.Loader;

public class Epoch implements Externalizable {
	private static final long serialVersionUID = 42;	
	
	Map<String, Table> tableMap;
	List<Table> newTables = new ArrayList<Table>();
	List<Table> accessedTables = new ArrayList<Table>();
	List<TableStmt> tableStmts = new ArrayList<TableStmt>(4);
	
	transient Class evalClass;
	String evalClassName;
	
	transient List<Class> initClasses = new ArrayList<Class>();
	List<String> initClassNames = new ArrayList<String>();
	List<List<Const>> initClassConsts = new ArrayList<List<Const>>();
	
	TIntObjectHashMap<String> visitorClassMap=new TIntObjectHashMap<String>(); // rule-id to visitor class
	
	RuleMap ruleMap;
	transient List<Rule> rules;
	List<RuleComp> ruleComps;
	List<RuleComp> topologicalOrder;
	transient int level;
	public Epoch() {	
		level=0;
		ruleComps = new ArrayList<RuleComp>(0);
		topologicalOrder = new ArrayList<RuleComp>(0);	
	}
	
	public Epoch(List<RuleComp> _ruleComps, int _level) {
		ruleComps = _ruleComps;
		level = _level;
		
		for (RuleComp rc:ruleComps) rc.setEpoch(this);
		for (RuleComp rc:ruleComps) rc.recomputeDeps();		
		
		topologicalOrder = topologicalSort(ruleComps);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		tableMap = (Map<String, Table>) in.readObject();
		if (in.readByte()==1) {
			newTables = (List<Table>) in.readObject();
		}
		if (in.readByte()==1) {
			accessedTables = (List<Table>) in.readObject();
		}
		if (in.readByte()==1) {
			tableStmts = (List<TableStmt>) in.readObject();
		}
		char[] _name = new char[in.readInt()];
		for (int i=0; i<_name.length; i++)
			_name[i] = in.readChar();
		evalClassName = new String(_name);
		if (in.readByte()==1) {
			initClassNames = (List<String>) in.readObject();
		}
		if (in.readByte()==1) {
			initClassConsts = (List<List<Const>>) in.readObject();
		}
		visitorClassMap = (TIntObjectHashMap<String>) in.readObject();
		ruleMap = (RuleMap) in.readObject();
		ruleComps = (List<RuleComp>) in.readObject();
		topologicalOrder = (List<RuleComp>) in.readObject();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(tableMap);
		if (newTables.isEmpty()) { out.writeByte(0); } 
		else { out.writeByte(1); out.writeObject(newTables); }
		
		if (accessedTables.isEmpty()) { out.writeByte(0); }
		else { out.writeByte(1); out.writeObject(accessedTables); }

		if (tableStmts.isEmpty()) { out.writeByte(0); } 
		else { out.writeByte(1); out.writeObject(tableStmts); }
		
		out.writeInt(evalClassName.length());
		out.writeChars(evalClassName);
		if (initClassNames.isEmpty()) { out.writeByte(0); } 
		else { out.writeByte(1); out.writeObject(initClassNames); }
		
		if (initClassConsts.isEmpty()) { out.writeByte(0); } 
		else { out.writeByte(1); out.writeObject(initClassConsts); }
		
		out.writeObject(visitorClassMap);
		out.writeObject(ruleMap);
		out.writeObject(ruleComps);
		out.writeObject(topologicalOrder);
	}
	
	public void addTableStmts(List<TableStmt> stmts) {
		tableStmts.addAll(stmts);
	}
	public List<TableStmt> tableStmts() { return tableStmts; }
	
	public void setTableMap(Map<String, Table> map) { tableMap = map; }
	public Map<String, Table> getTableMap() {
		if (tableMap==null) tableMap = new HashMap<String, Table>();
		return tableMap;
	}
	
	public void setRuleMap(RuleMap _map) { ruleMap = _map; }
	public RuleMap getRuleMap() {
		if (ruleMap==null) ruleMap = new RuleMap();
		return ruleMap;
	}
	
	public int getRuleNum() { return getRules().size(); }
	public int getRuleCompNum() { return ruleComps.size(); }
	
	// should be used after RuleAnalysis.run() (not while RuleAnalysis.run())
	// since delta-rules and pipelined-rules can be added later
	public List<Rule> getRules() {
		if (rules==null) {
			rules = new ArrayList<Rule>();
			for (RuleComp rc:ruleComps) {
				for (Rule r:rc.getRules()) {
					if (!rules.contains(r))
						rules.add(r);
				}
				for (Rule r:rc.getStartingRules()) {
					if (!rules.contains(r))
						rules.add(r);
				}				
			}
		}
		return rules;
	}

	public List<RuleComp> getRuleComps() { return ruleComps; }

	public Iterator<RuleComp> topologicalOrder() { 
		return topologicalOrder.iterator();
	}
	
	public void addInitClass(@SuppressWarnings("rawtypes") Class _initClass, List<Const> consts) {
		assert initClasses.size()==initClassNames.size() && initClasses.size()==initClassConsts.size();

		initClasses.add(_initClass);
		initClassNames.add(_initClass.getName());
		initClassConsts.add(consts);
	}
	public List<List<Const>> getInitConsts() {
		return initClassConsts;
	}

	@SuppressWarnings("rawtypes")
	public List<Class> getInitClasses() {
		if (initClasses==null || initClasses.isEmpty()) {
			if (initClasses==null)
				initClasses = new ArrayList<Class>(4); 
			for (String n:initClassNames) {
				Class c=Loader.forName(n);
				assert c!=null;				
				initClasses.add(c);
			}
		}
		return initClasses;
	}
	
	public void setEvalClass(@SuppressWarnings("rawtypes") Class _evalClass) {
		evalClass=_evalClass;
		evalClassName = evalClass.getName();
	}
	@SuppressWarnings("rawtypes")
	public Class getEvalclass() {
		if (evalClass==null) {
			evalClass = Loader.forName(evalClassName);
			assert evalClass!=null;
		}
		return evalClass;
	}
	public void addNewTables(List<Table> _newTables) {
		for (Table t:_newTables) {
			if (!newTables.contains(t))
				newTables.add(t);
		}
	}
	public List<Table> getNewTables() {
		return newTables;
	}
	
	public List<Table> getAccessedTables() {
		return accessedTables;
	}
	public void addAccessedTable(Table t) {
		assert t!=null;
		if (!accessedTables.contains(t))
			accessedTables.add(t);
	}
	
	public boolean hasInitRule() {
		for (Rule r:getRules()) {
			if (r.isSimpleArrayInit())
				return true;
		}
		return false;
	}
	/*
	public boolean isWriteOnly(Table t) { return writeOnly.contains(t); }
	public void setWriteOnly(Table t) {
		if (!writeOnly.contains(t))
			writeOnly.add(t);
	}*/
	/*
	public void updateVisitorBuilder(VisitorBuilder builder) {
		builder.addRules(getRules());
		for (int ruleId:visitorClassMap.keys()) {
			String visitorClassName=visitorClassMap.get(ruleId);
			Class visitorClass=Loader.forName(visitorClassName);
			builder.setVisitorClass(ruleId, visitorClass);
		}
	}*/
	public void addVisitorClass(int rule, Class<?> visitorClass) {
		assert !visitorClassMap.contains(rule):"rule["+rule+"] already has visitor:"+visitorClass;
		visitorClassMap.put(rule, visitorClass.getName());
	}
	public String getVisitorClassName(int rule) {
		if (visitorClassMap.contains(rule))
			return visitorClassMap.get(rule);
		return null;
	}
	public String toString() {
		String s="Epoch ("+level+") [";
		for (RuleComp rc:ruleComps) {
			s += rc +", ";
		}
		s += "], ";
		for (Table t:newTables) {
			s += ", new table("+t.name()+")";
		}
		for (TableStmt ts:tableStmts) {
			s += ", "+ts;
		}
		return s;
	}	
	
	// Static methods
	
	static List<RuleComp> topologicalSort(List<RuleComp> _ruleComps) {
		List<RuleComp> sorted = new	 ArrayList<RuleComp>(_ruleComps.size());
		Set<RuleComp> initSet = new HashSet<RuleComp>(_ruleComps.size());
		Set<RuleComp> visited = new HashSet<RuleComp>(_ruleComps.size());
		
		for (RuleComp rc: _ruleComps) {
			if (rc.getRuleCompsUsingThis().size()==0)
				initSet.add(rc);
		}
		
		for (RuleComp rc: initSet)
			dfsVisit(rc, visited, sorted);
		
		removeSimpleAssignRules(sorted);
		
		Iterator<RuleComp> it = sorted.iterator();
		while (it.hasNext()) {
			RuleComp rc = it.next();
			if (!_ruleComps.contains(rc))
				it.remove();
		}
		
		return sorted;
	}
	
	static void removeSimpleAssignRules(List<RuleComp> list) {
		Iterator<RuleComp> it=list.iterator();
		while(it.hasNext()) {
			RuleComp rc=it.next();
			if (rc.size()==1 && rc.get(0).isSimpleArrayInit())
				it.remove();
		}
	}
	
	static void dfsVisit(RuleComp rc, Set<RuleComp> visited, List<RuleComp> sorted) {
		if (visited.contains(rc)) return;
		
		visited.add(rc);
		for (RuleComp dep:rc.getDependingRuleComps())
			dfsVisit(dep, visited, sorted);
		sorted.add(rc);
	}
}
