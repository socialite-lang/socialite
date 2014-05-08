package socialite.util;

import socialite.parser.Expr;
import socialite.parser.Function;
import socialite.parser.Predicate;
import socialite.parser.Query;
import socialite.parser.Rule;
import socialite.parser.Table;

public class AnalysisException extends SociaLiteException {
	private static final long serialVersionUID = 1L;
	public AnalysisException(String _msg, Rule _r) {
		super("Error in:"+_r+"\n\t"+_msg);
	}
	public AnalysisException(String _msg, Table t) {
		super("Error in:"+t+"\n\t"+_msg);
	}
	public AnalysisException(String _msg, Query q) {
		super("Error in:"+q+"\n\t"+_msg);
	}
	public AnalysisException(Exception _e, Rule _r) {
		super("Error in:"+_r+"\n\t"+_e.getMessage());
	}
	public AnalysisException(Exception _e, Query q) {
		super("Error in:"+q+"\n\t"+_e.getMessage());
	}
	public AnalysisException(Exception _e, Rule _r, Predicate _p) {
		super("Error in:"+_r+"\n\t"+_e.getMessage() + " in "+_p);
	}
	public AnalysisException(Exception _e, Rule _r, Expr _expr) {
		super("Error in:"+_r+"\n\t"+_e.getMessage() + " in "+_expr);
	}
	public AnalysisException(String msg) {
		super(msg);
	}
}
