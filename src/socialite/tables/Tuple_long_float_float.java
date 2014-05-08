package socialite.tables;
import socialite.util.HashCode;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
import socialite.util.SociaLiteException;

public class Tuple_long_float_float extends Tuple implements Externalizable {
	private static final long serialVersionUID = 1;

	public long _0;
	public float _1;
	public float _2;


	public Tuple_long_float_float() {}

	public Tuple_long_float_float(long __0, float __1, float __2) {
		_0 = __0;
		_1 = __1;
		_2 = __2;

	}

	public Tuple_long_float_float clone() {
		return new Tuple_long_float_float(_0, _1, _2);
	}

	public int size() { return 3; }

	public void update(Tuple _t) {
		if (!(_t instanceof Tuple_long_float_float))
			throw new SociaLiteException("Not supported operation");

		Tuple_long_float_float t = (Tuple_long_float_float)_t;
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
		if (_t.getClass().equals(Tuple_long_float_float.class)) {
			Tuple_long_float_float t=(Tuple_long_float_float)_t;
			return (_0==(t._0))&& (_1==(t._1))&& (_2==(t._2));
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
		
		throw new UnsupportedOperationException();
	}
	public long getLong(int column) {
		if (column==0) return _0;
		
		throw new UnsupportedOperationException();
	}
	public float getFloat(int column) {
		if (column==1) return _1;
		if (column==2) return _2;
		
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

		throw new UnsupportedOperationException();
	}
	public void setFloat(int column, float v) {
		if (column==1) { _1=v; return; }
		if (column==2) { _2=v; return; }

		throw new UnsupportedOperationException();
	}
	public void setDouble(int column, double  v) {
		throw new UnsupportedOperationException();
	}
	public void setObject(int column, Object v) {
		throw new UnsupportedOperationException();
	}
	public String toString() {
		return ""+_0+", "+_1+", "+_2; 
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		_0=in.readLong();
		_1=in.readFloat();
		_2=in.readFloat();

	}	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeLong(_0);
		out.writeFloat(_1);
		out.writeFloat(_2);

	}
} 