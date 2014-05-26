package socialite.parser;

import java.util.ArrayList;
import java.util.List;

/* Private predicate (is mapped to PrivateTable) */
public class PrivPredicate extends Predicate {
	@Override
	public PrivPredicate clone() {
		return new PrivPredicate(name, idxParam, params);
	}

	public PrivPredicate(String _name, Object _idxParam, @SuppressWarnings("rawtypes")List _params, boolean rename) {
		super(_name, renameVar(_idxParam), renameVariables(_params));
		assert rename;
	}	
	public PrivPredicate(String _name, Object _idxParam, @SuppressWarnings("rawtypes")List _params) {
		super(_name, _idxParam, _params);
	}	
	
	static Object renameVar(Object param) {
		if (param instanceof Variable) {
			Variable v=(Variable)param;
			Variable newV = new Variable(v.name+"$priv", v.type);
			newV.dontCare = v.dontCare;
			return newV;
		}
		return param;
	}
	@SuppressWarnings("rawtypes")
	static List renameVariables(List _params) {
		List<Object> params = new ArrayList<Object>(_params.size());
		for (Object o:_params) {
			if (o instanceof Variable) {
				params.add(renameVar(o));
			} else {
				params.add(o);			
			}
		}
		return params;
	}
}
