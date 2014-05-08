package socialite.tables;
import socialite.util.HashCode;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
import socialite.util.SociaLiteException;

public class Tuple_int_int extends Tuple implements Externalizable {
	private static final long serialVersionUID = 1;

	public int _0;
	public int _1;


	public Tuple_int_int() {}

	public Tuple_int_int(int __0, int __1) {
		_0 = __0;
		_1 = __1;

	}

	public Tuple_int_int clone() {
		return new Tuple_int_int(_0, _1);
	}

	public int size() { return 2; }

	public void update(Tuple _t) {
		if (!(_t instanceof Tuple_int_int))
			throw new SociaLiteException("Not supported operation");

		Tuple_int_int t = (Tuple_int_int)_t;
		_0 = t._0;
		_1 = t._1;

	}

	public int hashCode() {
		return HashCode.get(_0)^HashCode.get(_1);
	}	
	public boolean equals(Object o) {
		if (!(o instanceof Tuple)) return false;

		Tuple _t = (Tuple)o;
		if (_t.getClass().equals(Tuple_int_int.class)) {
			Tuple_int_int t=(Tuple_int_int)_t;
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
		if (column==0) return _0;
		if (column==1) return _1;
		
		throw new UnsupportedOperationException();
	}
	public long getLong(int column) {
		
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
		if (column==0) { _0=v; return; }
		if (column==1) { _1=v; return; }

		throw new UnsupportedOperationException();
	}
	public void setLong(int column, long v) {
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
		_0=in.readInt();
		_1=in.readInt();

	}	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(_0);
		out.writeInt(_1);

	}
} 