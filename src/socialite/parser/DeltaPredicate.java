package socialite.parser;


import java.util.List;

import socialite.util.Assert;

public class DeltaPredicate extends Predicate {
	String origName;
	Predicate origP;

	@Override
	public DeltaPredicate clone() {
		return new DeltaPredicate(origP);
	}

	public DeltaPredicate(Predicate p) {
		idxParam = p.idxParam;
		params = p.params;
		negated = p.negated;
		posAtRuleBody = p.posAtRuleBody;
		name = DeltaTable.name(p);
		origName = p.name;
		origP = p;
		isHeadPredicate = p.isHeadPredicate;
		//Assert.not_true(isHeadPredicate);
	}

	public String origName() {
		return origName;
	}

	public Predicate getOrigP() {
		return origP;
	}
}
