package socialite.tables;
import socialite.util.HashCode;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
import socialite.util.SociaLiteException;

public class Tuple_long_Object_int extends Tuple implements Externalizable {
	private static final long serialVersionUID = 1;

	public long _0;
	public Object _1;
	public int _2;


	public Tuple_long_Object_int() {}

	public Tuple_long_Object_int(long __0, Object __1, int __2) {
		_0 = __0;
		_1 = __1;
		_2 = __2;

	}

	public Tuple_long_Object_int clone() {
		return new Tuple_long_Object_int(_0, _1, _2);
	}

	public int size() { return 3; }

	public void update(Tuple _t) {
		if (!(_t instanceof Tuple_long_Object_int))
			throw new SociaLiteException("Not supported operation");

		Tuple_long_Object_int t = (Tuple_long_Object_int)_t;
		_0 = t._0;
		_1 = t._1;
		_2 = t._2;

	}

	public int hashCode() {
		return HashCode.get(_0)^HashCode.get(_1)^HashCode.get(_2);
	}	
	public boolean equals(Object o) {
		if (!(o instanceof Tuple)) return false;

		Tuple _t = (Tuple)o;
		if (_t.getClass().equals(Tuple_long_Object_int.class)) {
			Tuple_long_Object_int t=(Tuple_long_Object_int)_t;
			return (_0==(t._0))&& (_1.equals(t._1))&& (_2==(t._2));
		}
		return false;
	}

	public Object get(int column) {
		if (column==0) return _0;
		if (column==1) return _1;
		if (column==2) return _2;
	
		return null;
	}
	public int getInt(int column) {
		if (column==2) return _2;
		
		throw new UnsupportedOperationException();
	}
	public long getLong(int column) {
		if (column==0) return _0;
		
		throw new UnsupportedOperationException();
	}
	public float getFloat(int column) {
		
		throw new UnsupportedOperationException();
	}
	public double getDouble(int column) {
		
		throw new UnsupportedOperationException();
	}
	public Object getObject(int column) {
		if (column==1) return _1;

		throw new UnsupportedOperationException();
	}

	public void setInt(int column, int v) {
		if (column==2) { _2=v; return; }

		throw new UnsupportedOperationException();
	}
	public void setLong(int column, long v) {
		if (column==0) { _0=v; return; }

		throw new UnsupportedOperationException();
	}
	public void setFloat(int column, float v) {
		throw new UnsupportedOperationException();
	}
	public void setDouble(int column, double  v) {
		throw new UnsupportedOperationException();
	}
	public void setObject(int column, Object v) {
		if (column==1) { _1=v; return; }

		throw new UnsupportedOperationException();
	}
	public String toString() {
		return ""+_0+", "+_1+", "+_2; 
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		_0=in.readLong();
		_1=in.readObject();
		_2=in.readInt();

	}	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeLong(_0);
		out.writeObject(_1);
		out.writeInt(_2);

	}
} 