package socialite.parser;


import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

import socialite.util.Assert;

public class DeltaPredicate extends Predicate {
    private static final long serialVersionUID = 1;

    String origName;
	Predicate origP;

	@Override
	public DeltaPredicate clone() {
		return new DeltaPredicate(origP);
	}

    public DeltaPredicate() {}
	public DeltaPredicate(Predicate p) {
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

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        super.readExternal(in);
        char[] _name = new char[in.readInt()];
        for (int i=0; i<_name.length; i++)
            _name[i] = in.readChar();
        origName = new String(_name);
        origP = new Predicate();
        origP.readExternal(in);
    }
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeInt(origName.length());
        out.writeChars(origName);
        origP.writeExternal(out);
    }
}
