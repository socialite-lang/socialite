package socialite.parser;

import socialite.collection.SArrayList;

import java.util.ArrayList;
import java.util.List;
import socialite.parser.Param;

/* Private predicate (is mapped to PrivateTable) */
public class PrivPredicate extends Predicate {
    private static final long serialVersionUID = 1;

    @Override
	public PrivPredicate clone() {
		return new PrivPredicate(name, params);
	}

    public PrivPredicate() {};
    public PrivPredicate(String _name, List<Param> _params, boolean rename) {
		super(_name, renameVariables(_params));
		assert rename;
	}	
	public PrivPredicate(String _name, List<Param> _params) {
		super(_name, _params);
	}	
	
	static Param renameVar(Param param) {
		if (param instanceof Variable) {
			Variable v=(Variable)param;
			Variable newV = new Variable(v.name+"$priv", v.type);
			newV.dontCare = v.dontCare;
			return newV;
		}
		return param;
	}

	static List<Param> renameVariables(List<Param> _params) {
		List<Param> params = new SArrayList<Param>(_params.size());
		for (Param o:_params) {
			if (o instanceof Variable) {
				params.add((Variable)renameVar(o));
			} else {
				params.add(o);			
			}
		}
		return params;
	}
}