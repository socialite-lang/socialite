package socialite.parser.antlr;


import java.io.Serializable;
import java.util.List;
import java.util.Set;

import socialite.parser.Const;
import socialite.parser.Function;
import socialite.parser.Predicate;
import socialite.parser.Variable;
import socialite.type.Utf8;
import socialite.util.Assert;

public class RuleDecl implements Serializable {
	private static final long serialVersionUID = 1;
	
	public Predicate head;
	public List<?> body;
	
	boolean simpleUpdate=false;
	public RuleDecl(Predicate _head, List<?> _body) {
		head = _head;
		body = _body;
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
}