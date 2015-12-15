package socialite.tables;

import socialite.collection.TupleVisitor;

public abstract class QueryVisitor implements TupleVisitor {
	public boolean visit(int _0) { System.out.println(_0); return true;}
	public boolean visit(long _0) { System.out.println(_0); return true;}
	public boolean visit(float _0) { System.out.println(_0); return true;}
	public boolean visit(double _0) { System.out.println(_0); return true;}
	public boolean visit(Object _0) { System.out.println(_0); return true;}
	public boolean visit(Tuple _0) { System.out.println(_0); return true;}
	public void finish() {}
	public void finish(boolean force) {}
}
