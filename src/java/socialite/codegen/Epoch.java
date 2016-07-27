package socialite.codegen;

import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import socialite.collection.SArrayList;
import socialite.parser.Const;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.parser.antlr.TableStmt;
import socialite.resource.RuleMap;
import socialite.util.IdFactory;
import socialite.util.Loader;

public class Epoch implements Externalizable {
	private static final long serialVersionUID = 42;
    public static final Log L= LogFactory.getLog(Epoch.class);

    int id = IdFactory.nextEpochId();
    Map <String,Table> tableMap;
	SArrayList<Table> newTables = new SArrayList<Table>(4);
    SArrayList<TableStmt> tableStmts = new SArrayList<TableStmt>(4);
	
	transient Class evalClass;
	String evalClassName;
	
	TIntObjectHashMap<String> visitorClassMap=new TIntObjectHashMap<String>(); // rule-id to visitor class
	
	RuleMap ruleMap;
	transient List<Rule> rules;
	transient SArrayList<RuleComp> ruleComps;
	SArrayList<RuleComp> topologicalOrder;
	transient int level;
	public Epoch() {	
		level=0;
		ruleComps = new SArrayList<RuleComp>(0);
		topologicalOrder = new SArrayList<RuleComp>(0);
	}
	
	public Epoch(List<RuleComp> _ruleComps, int _level) {
		ruleComps = new SArrayList(_ruleComps);
		level = _level;
		
		for (RuleComp rc:ruleComps) rc.setEpoch(this);
		for (RuleComp rc:ruleComps) rc.recomputeDeps();		
		
		topologicalOrder = topologicalSort(ruleComps);
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
        id = in.readInt();
        tableMap = new HashMap<>();
        if (in.readByte()==1) {
			newTables = (SArrayList<Table>) in.readObject();
            for (Table t:newTables) {
                tableMap.put(t.name(), t);
            }
		}
		if (in.readByte()==1) {
			tableStmts = (SArrayList<TableStmt>) in.readObject();
		}
		char[] _name = new char[in.readInt()];
		for (int i=0; i<_name.length; i++)
			_name[i] = in.readChar();
		evalClassName = new String(_name);

    	visitorClassMap = new TIntObjectHashMap<>(0);
        visitorClassMap.readExternal(in);
		ruleMap = new RuleMap();
		ruleMap.readExternal(in);
		topologicalOrder = new SArrayList<>(0);
		topologicalOrder.readExternal(in);
        ruleComps = new SArrayList<>(topologicalOrder);
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(id);
        if (newTables.isEmpty()) { out.writeByte(0); }
		else { out.writeByte(1); out.writeObject(newTables); }
		
		if (tableStmts.isEmpty()) { out.writeByte(0); }
		else { out.writeByte(1); out.writeObject(tableStmts); }

		out.writeInt(evalClassName.length());
		out.writeChars(evalClassName);

        visitorClassMap.writeExternal(out);
        ruleMap.writeExternal(out);
        topologicalOrder.writeExternal(out);
	}

    public int id() { return id; }

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
	
	public int getRuleCompNum() { return ruleComps.size(); }
	
	// should be used after RuleAnalysis.run() (not while RuleAnalysis.run())
	// since delta-rules and pipelined-rules can be added later
	public List<Rule> getRules() {
		if (rules==null) {
			rules = new SArrayList<>();
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
	
    public List<Pair<Table, List<Const>>> getInitRuleInfo() {
        List<Pair<Table, List<Const>>> initRuleInfo = new ArrayList<>();
        for (Rule r: getRules()) {
            if (r.isSimpleArrayInit()) {
                Table t = tableMap.get(r.getHead().name());
                Pair<Table, List<Const>> info = new ImmutablePair<>(t, r.getConstsAssigns());
                initRuleInfo.add(info);
            }
        }
        return initRuleInfo;
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
			if (!newTables.contains(t)) {
				newTables.add(t);
			}
		}
	}
	public List<Table> getNewTables() {
		return newTables;
	}

	public boolean hasInitRule() {
		for (Rule r:getRules()) {
			if (r.isSimpleArrayInit())
				return true;
		}
		return false;
	}

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
		String s="Epoch ("+id+") [";
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
	
	static SArrayList<RuleComp> topologicalSort(List<RuleComp> _ruleComps) {
		SArrayList<RuleComp> sorted = new SArrayList<RuleComp>(_ruleComps.size());
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
	
	static void dfsVisit(RuleComp rc, Set<RuleComp> visited, SArrayList<RuleComp> sorted) {
		if (visited.contains(rc)) return;
		
		visited.add(rc);
		for (RuleComp dep:rc.getDependingRuleComps())
			dfsVisit(dep, visited, sorted);
		sorted.add(rc);
	}
}
