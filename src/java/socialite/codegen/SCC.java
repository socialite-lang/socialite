package socialite.codegen;

import socialite.collection.SArrayList;
import socialite.parser.Rule;

import java.util.List;

/**
 * Rule component representing SCC in rule dependency graph
 */
public class SCC extends RuleComp {

    public SCC(List<Rule> _rules) {
        rules = new SArrayList<>(_rules);
        startingRules = new SArrayList<>(_rules);
        for (Rule r:rules) {
            r.setInScc();
            ruleToRuleComp.put(r, this); //XXX: remove this
        }
    }

    public boolean scc() { return true; }
}
