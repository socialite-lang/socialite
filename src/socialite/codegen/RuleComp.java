package socialite.codegen;


import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import socialite.collection.SArrayList;
import socialite.parser.Rule;
import socialite.util.Assert;

// Rule Component:
//   represents 1. recursive rules in a strongly-connected component or
//              2. a single rule or
//              3. rules that are pipelined from a rule (that is also included)

public class RuleComp implements Externalizable {
	private static final long serialVersionUID = 1;
	
	static Map<Rule, RuleComp> ruleToRuleComp = null;
	public static void init() { ruleToRuleComp = new HashMap<Rule, RuleComp>(); }
	public static RuleComp findRuleComp(Rule r) { return ruleToRuleComp.get(r); }
	public static List<RuleComp> findRuleComps(List<Rule> rules) {
		List<RuleComp> result=new ArrayList<RuleComp>();
		for (Rule r:rules) {
			RuleComp rc=ruleToRuleComp.get(r);
			if (rc==null) {
				System.out.println("rc is null!");
			}
			if (!result.contains(rc))
				result.add(rc);
		}
		return result;
	}

    transient List<RuleComp> deps, usedBy;
    transient Rule pipeliningFrom = null;

	Epoch epoch;
    int epochId;
	SArrayList<Rule> rules;
	SArrayList<Rule> startingRules = new SArrayList<Rule>();
	boolean scc;
	boolean disabled=false;

	public RuleComp() {
		rules = new SArrayList<Rule>(0);
		startingRules = new SArrayList<Rule>(0);
	}
	public RuleComp(List<Rule> _rules, boolean _scc) {
		rules = new SArrayList<Rule>(_rules);
		for (Rule r:rules) {
			assert !ruleToRuleComp.containsKey(r);
			ruleToRuleComp.put(r, this);
		}
		scc = _scc;
		if (rules.size()==1) addStartingRule(rules.get(0));
	}
	public RuleComp(Rule r, boolean _scc) {
		rules = new SArrayList<Rule>();
		rules.add(r);
		scc = _scc;
		if (!scc) addStartingRule(rules.get(0));
		
		ruleToRuleComp.put(r, this);
	}
	public boolean scc() { return scc; }

	public void setPipeliningFrom(Rule r) {
		assert pipeliningFrom == null:"pipeliningFrom is not null:"+pipeliningFrom;
		pipeliningFrom = r;
		r.setHasPipelinedRule();
	}
	public Rule pipeliningFrom() { return pipeliningFrom; }
	public boolean hasPipeliningFrom() { return pipeliningFrom!=null; }
	
	public List<Rule> getRules() { return rules; }
	
	public void setEpoch(Epoch _e) {
		epoch = _e;
        epochId = epoch.id();
		for (Rule r:rules) r.setEpoch(epoch);				
	}	
	public Epoch epoch() { return epoch; }
	
	public void recomputeDeps() {
		// recompute dependency considering the epoch.
		Assert.not_null(epoch);
		if (deps!=null) filterByEpoch(deps);
		if (usedBy!=null) filterByEpoch(usedBy);
		
		for (Rule r:rules) r.recomputeDeps();
	}
	
	void filterByEpoch(List<RuleComp> ruleComps) {// ruleComps: deps or UsedBy
		Assert.not_null(epoch());
		Iterator<RuleComp> it = ruleComps.iterator();
		while(it.hasNext()) {
			RuleComp rc=it.next();
			if (!epoch().equals(rc.epoch()))
				it.remove();
		}
	}
	
	public List<RuleComp> getDependingRuleComps() {
		if (deps==null) {
			deps = new ArrayList<RuleComp>();
			for (Rule r:rules) {
				List<RuleComp> rcs=findRuleComps(r.getDependingRules());
				for (RuleComp rc:rcs) {
					if (rc.equals(this)) continue;
					if (!deps.contains(rc)) deps.add(rc);
				}
			}
		}
		return deps;
	}
	
	public List<RuleComp> getRuleCompsUsingThis() {
		if (usedBy==null) {
			usedBy = new ArrayList<RuleComp>();
			for (Rule r:rules) {
				addToUsedByFrom(r);
			}
		}
		return usedBy;
	}
	void addToUsedByFrom(Rule r) {
		List<RuleComp> rcs=findRuleComps(r.getRulesUsingThis());
		for (RuleComp rc:rcs) {
			if (rc.equals(this)) continue;
			if (!usedBy.contains(rc)) usedBy.add(rc);
		}
	}

	public void removeAllStartingRule() {
		startingRules.clear();
	}
	public void addStartingRule(Rule r) {
		if (r.isSimpleArrayInit()) return;
		startingRules.add(r);
        if (epoch!=null) {
            r.setEpoch(epoch);
        }
	}
	public List<Rule> getStartingRules() {
		return startingRules;
	}
	
	public void add(Rule r) { 
		rules.add(r); 
		if (usedBy != null) {
			addToUsedByFrom(r);
		}
	}	
	public void addAll(List<? extends Rule> rlist) { rules.addAll(rlist); }
	public void removeAll(List<? extends Rule> rlist) { rules.removeAll(rlist); }
	
	public Rule get(int i) { return rules.get(i); }	
	public int size() { return rules.size(); }
	
	@Override
	public String toString() {
		String s="RuleComp {";
		for (Rule r:rules) s += r +",";
		s+="}";
		return s;
	}

    static Rule getRule(List<Rule> rules, int rid) {
        for (Rule r:rules) {
            if (r.id()==rid)
                return r;
        }
        return null;
    }
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
        epochId = in.readInt();
		rules = new SArrayList<Rule>(0);
		rules.readExternal(in);
		startingRules = new SArrayList<Rule>(0);
		startingRules.readExternal(in);
		scc = in.readBoolean();
		disabled = in.readBoolean();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(epochId);
		rules.writeExternal(out);
        startingRules.writeExternal(out);
		out.writeBoolean(scc);
		out.writeBoolean(disabled);
	}
}
