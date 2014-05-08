package socialite.resource;

import gnu.trove.TIntCollection;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.Serializable;
import java.util.List;

import socialite.parser.Rule;
import socialite.util.Assert;

// A map from a rule id A to delta rule ids which are dependent on the rule A 
public class RuleMap implements Serializable {
	private static final long serialVersionUID = 1;
	
	TIntObjectHashMap<TIntArrayList> ruleToDeltaRules1;
	TIntObjectHashMap<TIntArrayList> ruleToDeltaRulesRest;
	TIntObjectHashMap<TIntArrayList> ruleToDeltaRules;
	
	TIntIntHashMap privDepMap;
	TIntIntHashMap remoteHeadDepMap;
	TIntObjectHashMap<TIntArrayList> remoteBodyDepMap;
	
	public String toString() {
		String descr="";
		descr += "\n remoteHeadDepMap {";
		for (int k:remoteHeadDepMap.keys()) {
			descr += k+":"+remoteHeadDepMap.get(k)+",  ";
		}
		
		descr += "} \n";
		descr += "remoteBodyDep {";
		for (int k:remoteBodyDepMap.keys()) {
			descr += k+":"+remoteBodyDepMap.get(k)+",  ";
		}
		descr += "} \n";
		return descr;
	}
	public RuleMap() {		
		ruleToDeltaRules1 = new TIntObjectHashMap<TIntArrayList>(8);
		ruleToDeltaRulesRest = new TIntObjectHashMap<TIntArrayList>(8);
		ruleToDeltaRules = new TIntObjectHashMap<TIntArrayList>(8);
		
		privDepMap = new TIntIntHashMap(8);
		remoteHeadDepMap = new TIntIntHashMap(8);
		remoteBodyDepMap = new TIntObjectHashMap<TIntArrayList>(8);
	}
	public void merge(RuleMap map) {
		ruleToDeltaRules1.putAll(map.ruleToDeltaRules1);
		ruleToDeltaRulesRest.putAll(map.ruleToDeltaRulesRest);
		
		privDepMap.putAll(map.privDepMap);
		remoteHeadDepMap.putAll(map.remoteHeadDepMap);
		remoteBodyDepMap.putAll(map.remoteBodyDepMap);
	}
	
	public void setRemoteBodyDep(int from, int pos, int to) {
		TIntArrayList depRules = remoteBodyDepMap.get(from);
		if (depRules==null) {
			depRules = new TIntArrayList();
			remoteBodyDepMap.put(from, depRules);
		}
		if (depRules.size() <= pos) {
			depRules.fill(depRules.size(), pos+1, -1);
		}
		depRules.set(pos, to);
	}
	public int getRemoteBodyDep(int from, int pos) {
		TIntArrayList depRules = remoteBodyDepMap.get(from);		
		return depRules.get(pos);
	}
	
	public void setRemoteHeadDep(int from, int to) {
		assert !remoteHeadDepMap.containsKey(from);	
		remoteHeadDepMap.put(from, to);
	}
	public int getRemoteHeadDep(int from) {
		assert remoteHeadDepMap.containsKey(from);
		return remoteHeadDepMap.get(from);
	}
	
	public void setPrivDep(int from, int to) {
		assert !privDepMap.containsKey(from);
		privDepMap.put(from, to);
	}	
	public int getPrivDepRule(int from) {		
		assert privDepMap.containsKey(from);
		return privDepMap.get(from);
	}
	
	// if the rule(ruleId) triggers the delta rule(deltaRuleId), 
	// add it to this rule map.
	// For instance, say we have rule Foo(a,b) :- Bar(a,b). (rule1) and rule Zoo(a,b) :- Foo(a,b). (rule2)
	// then rule1 induces delta rule (generated from rule 2) Zoo(a,b) :- DeltaFoo(a,b).
	public void addDeltaRuleMapping(int ruleId, int deltaRuleId, boolean isDeltaFirst) {
		TIntObjectHashMap<TIntArrayList> map;
		if (isDeltaFirst) map = ruleToDeltaRules1;
		else map = ruleToDeltaRulesRest;
		
		addToDeltaMapping(map, ruleId, deltaRuleId);				
		addToDeltaMapping(ruleToDeltaRules, ruleId, deltaRuleId);
	}
	void addToDeltaMapping(TIntObjectHashMap<TIntArrayList> map, int ruleId, int deltaRuleId) {
		TIntArrayList ilist = map.get(ruleId);
		if (ilist==null) {
			ilist = new TIntArrayList(2);
			map.put(ruleId, ilist);
		}
		if (!ilist.contains(deltaRuleId))
			ilist.add(deltaRuleId);
	}
	
	// returns the thread-local rule that is dependent on the given rule
	public int getThreadLocalRule(int ruleId) {
		return privDepMap.get(ruleId);
	}
	
	// returns delta rules related to the given rule id
	// where the delta tables are used in first predicate in the rule
	public TIntArrayList getDeltaRules1(int ruleId) {
		return ruleToDeltaRules1.get(ruleId);
	}
	// returns delta rules related to the given rule id
	// where the delta rules have delta tables NOT used in 1st predicate in its rule
	public TIntArrayList getDeltaRulesRest(int ruleId) {
		return ruleToDeltaRulesRest.get(ruleId);
	}
	
	public TIntArrayList getDeltaRules(int ruleId) {
		return ruleToDeltaRules.get(ruleId);
	}
}