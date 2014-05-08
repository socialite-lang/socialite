package socialite.parser;

import java.util.ArrayList;
import java.util.List;

import socialite.parser.antlr.RuleDecl;
import socialite.util.Assert;


public class DeltaRule extends Rule {
	RuleDecl origRuleDecl;
	DeltaPredicate theP;
	Rule origRule;
	public DeltaRule(Rule rule, DeltaPredicate _deltaPred) {
		super();
		assert !rule.isSimpleUpdate();
		assert !(rule instanceof DeltaRule);
		
		theP=_deltaPred;
		origRule=rule;
		copyRuleProperties(origRule);
		ruleDecl = createRuleDecl();		
	}
	
	public DeltaRule(RuleDecl decl, DeltaPredicate p) {
		super(decl);
		theP = p;
		origRule = this;
	}
	
	public Rule origRule() { return origRule; }
	
	public void updateRuleDeps() {
		Rule r=origRule;
		deps = new ArrayList<Rule>(r.deps);
		for (Rule r1:deps) { r1.usedBy(this); }
		usedBy = new ArrayList<Rule>(r.usedBy);
		for (Rule r2:usedBy) { r2.dependOn(this); }
	}
	public DeltaPredicate getTheP() { return theP; }
	
	protected RuleDecl createRuleDecl() {
		List newBody=new ArrayList();
		int pos=0;
		for (Object o:origRuleDecl.body) {
			if (pos==theP.getPos()) 
				newBody.add(theP);
			else if (o instanceof Predicate) 
				newBody.add(((Predicate) o).clone());
			else newBody.add(o);
			pos++;			
		}
		return new RuleDecl(origRuleDecl.head, newBody);
	}
	
	@Override
	public void copyRuleProperties(Rule r) {		
		super.copyRuleProperties(r);
		origRuleDecl= r.ruleDecl;
	}
}
