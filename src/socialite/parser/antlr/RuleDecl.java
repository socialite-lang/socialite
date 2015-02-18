package socialite.parser.antlr;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;
import java.util.Set;

import socialite.collection.SArrayList;
import socialite.parser.Literal;
import socialite.parser.Const;
import socialite.parser.Function;
import socialite.parser.Predicate;
import socialite.parser.Variable;
import socialite.type.Utf8;
import socialite.util.Assert;
import socialite.collection.SArrayList;

public class RuleDecl implements Externalizable {
	private static final long serialVersionUID = 1;
	
	public Predicate head;
	public SArrayList<Literal> body;
	
	boolean simpleUpdate=false;
	public RuleDecl() {}
	public RuleDecl(Predicate _head, List<Literal> _body) {
		head = _head;
		body = new SArrayList<Literal>(_body);
	}
	
	public String toString() {
		String result = head.toString() + " :- ";
		for (int i=0; i<body.size(); i++) {
			Object o = body.get(i);
			boolean noComma=false;
			if (o instanceof Const) {
				Const c=(Const)o;
				result += c.constValStr();
			} else {
				result += o;
				if ((""+o).length()==0)
					noComma=true;
			}
			if (i!=body.size()-1 && !noComma) result += ",";
		}
		result += ".";
		return result;
	}
	
	public boolean isSimpleUpdate() {
		return simpleUpdate;
	}
	public void setAnnotation(String ann) {
		if ("SimpleUpdate".equalsIgnoreCase(ann)) {
			simpleUpdate=true;
		} else {
			Assert.die("Unsupported annotation:"+ann);
		}
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		head = (Predicate)in.readObject();
		body = new SArrayList<Literal>(0);
		body.readExternal(in);
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(head);
		body.writeExternal(out);
	}
}
