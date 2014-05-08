package socialite.tables;

import socialite.parser.MyType;
import socialite.util.Assert;
import socialite.util.Loader;
import socialite.util.SociaLiteException;
import socialite.visitors.IVisitor;

public abstract class Tuple {
	public Tuple() {}
	
	public void setInt(int column, int val) {throw new UnsupportedOperationException();}
	public void setLong(int column, Long val) { throw new UnsupportedOperationException(); }	
	public void setFloat(int column, float val) { throw new UnsupportedOperationException(); }	
	public void setDouble(int column, double val) { throw new UnsupportedOperationException(); }	
	public void setObject(int column, Object val) {throw new UnsupportedOperationException();}
	
	public int getInt(int column) {throw new UnsupportedOperationException();}
	public long getLong(int column) { throw new UnsupportedOperationException(); }
	public float getFloat(int column) { throw new UnsupportedOperationException(); }
	public double getDouble(int column) { throw new UnsupportedOperationException(); }
	public Object getObject(int column) {throw new UnsupportedOperationException();}
	
	public abstract int size();
	// should only be used for Python interfacing
	public Object get(int columns) { return null; } 
		
	public Tuple clone() { Assert.not_implemented(); return null; }
	public void update(Tuple t) { Assert.not_implemented(); }
	
	public void visit(IVisitor v) { Assert.not_implemented();}	
	public void visit(QueryVisitor v) { Assert.not_implemented();}
	
	public static Tuple create(Class... types) {		
		String tupleClassName = "Tuple";
		for (Class type:types) {
			if (MyType.isPrimitive(type)) {
				tupleClassName += "_"+MyType.javaTypeName(type);
			} else {
				tupleClassName += "_Object";
			}
		}
		tupleClassName = "socialite.tables."+tupleClassName;
		if (Loader.exists(tupleClassName)) {
			try {
				Class tupleClass = Loader.forName(tupleClassName);
				return (Tuple)tupleClass.newInstance();
			} catch (Exception e) {
				throw new SociaLiteException(e);
			}
		} else {
			String msg="Class "+tupleClassName+" is not created.\nUse bin/tupleGen to generate the class.";
			throw new SociaLiteException(msg);
		}		
	}
			
	public static void main(String[] args) {
		Tuple t=Tuple.create(int.class, int.class);
		assert t instanceof Tuple_int_int;		
		
		Tuple t2=Tuple.create(int.class, String.class);
		assert t2 instanceof Tuple_int_Object;		
	}
}