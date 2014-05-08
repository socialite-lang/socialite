package socialite.tables;
import socialite.util.HashCode;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
import socialite.util.SociaLiteException;

public class Tuple_long_long extends Tuple implements Externalizable {
	private static final long serialVersionUID = 1;

	public long _0;
	public long _1;


	public Tuple_long_long() {}

	public Tuple_long_long(long __0, long __1) {
		_0 = __0;
		_1 = __1;

	}

	public Tuple_long_long clone() {
		return new Tuple_long_long(_0, _1);
	}

	public int size() { return 2; }

	public void update(Tuple _t) {
		if (!(_t instanceof Tuple_long_long))
			throw new SociaLiteException("Not supported operation");

		Tuple_long_long t = (Tuple_long_long)_t;
		_0 = t._0;
		_1 = t._1;

	}

	public int hashCode() {
		return HashCode.get(_0)^HashCode.get(_1);
	}	
	public boolean equals(Object o) {
		if (!(o instanceof Tuple)) return false;

		Tuple _t = (Tuple)o;
		if (_t.getClass().equals(Tuple_long_long.class)) {
			Tuple_long_long t=(Tuple_long_long)_t;
			return (_0==(t._0))&& (_1==(t._1));
		}
		return false;
	}

	public Object get(int column) {
		if (column==0) return _0;
		if (column==1) return _1;
	
		return null;
	}
	public int getInt(int column) {
		
		throw new UnsupportedOperationException();
	}
	public long getLong(int column) {
		if (column==0) return _0;
		if (column==1) return _1;
		
		throw new UnsupportedOperationException();
	}
	public float getFloat(int column) {
		
		throw new UnsupportedOperationException();
	}
	public double getDouble(int column) {
		
		throw new UnsupportedOperationException();
	}
	public Object getObject(int column) {
		throw new UnsupportedOperationException();
	}

	public void setInt(int column, int v) {
		throw new UnsupportedOperationException();
	}
	public void setLong(int column, long v) {
		if (column==0) { _0=v; return; }
		if (column==1) { _1=v; return; }

		throw new UnsupportedOperationException();
	}
	public void setFloat(int column, float v) {
		throw new UnsupportedOperationException();
	}
	public void setDouble(int column, double  v) {
		throw new UnsupportedOperationException();
	}
	public void setObject(int column, Object v) {
		throw new UnsupportedOperationException();
	}
	public String toString() {
		return ""+_0+", "+_1; 
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		_0=in.readLong();
		_1=in.readLong();

	}	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeLong(_0);
		out.writeLong(_1);

	}
} 