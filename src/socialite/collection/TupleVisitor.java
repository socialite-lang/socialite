package socialite.collection;

import socialite.tables.Tuple;

public interface TupleVisitor {	
	public boolean visit(int i);
	public boolean visit(long l);
	public boolean visit(float f);
	public boolean visit(double d);
	public boolean visit(Object o);
	public boolean visit(Tuple t);
}
