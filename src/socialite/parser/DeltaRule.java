package socialite.parser;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

import socialite.parser.antlr.RuleDecl;
import socialite.util.Assert;
import socialite.collection.SArrayList;
import socialite.util.IdFactory;


public class DeltaRule extends Rule {
    private static final long serialVersionUID = 1;

    RuleDecl origRuleDecl;
	DeltaPredicate theP;
	Rule origRule;
	public DeltaRule(Rule rule, DeltaPredicate _deltaPred) {
		super();
        id= IdFactory.nextRuleId();
		assert !rule.isSimpleUpdate();
		assert !(rule instanceof DeltaRule);
		
		theP=_deltaPred;
		origRule=rule;
		copyRuleProperties(origRule);
		ruleDecl = createRuleDecl();		
	}

    public DeltaRule() { }
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
		SArrayList<Literal> newBody=new SArrayList<Literal>();
		int pos=0;
		for (Literal o:origRuleDecl.body) {
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

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        super.readExternal(in);
        origRuleDecl = new RuleDecl();
        origRuleDecl.readExternal(in);
        theP = new DeltaPredicate();
        theP.readExternal(in);
        origRule = new Rule();
        origRule.readExternal(in);
    }
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        origRuleDecl.writeExternal(out);
        theP.writeExternal(out);
        origRule.writeExternal(out);
    }
}