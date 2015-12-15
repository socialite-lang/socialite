package socialite.tables;
import socialite.util.HashCode;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
import socialite.util.SociaLiteException;

public class Tuple_double extends Tuple implements Externalizable {
	private static final long serialVersionUID = 1;

	public double _0;


	public Tuple_double() {}

	public Tuple_double(double __0) {
		_0 = __0;

	}

	public Tuple_double clone() {
		return new Tuple_double(_0);
	}

	public int size() { return 1; }

	public void update(Tuple _t) {
		if (!(_t instanceof Tuple_double))
			throw new SociaLiteException("Not supported operation");

		Tuple_double t = (Tuple_double)_t;
		_0 = t._0;

	}

	public int hashCode() {
		return HashCode.get(_0);
	}	
	public boolean equals(Object o) {
		if (!(o instanceof Tuple)) return false;

		Tuple _t = (Tuple)o;
		if (_t.getClass().equals(Tuple_double.class)) {
			Tuple_double t=(Tuple_double)_t;
			return (_0==(t._0));
		}
		return false;
	}

	public Object get(int column) {
		if (column==0) return _0;
	
		return null;
	}
	public int getInt(int column) {
		
		throw new UnsupportedOperationException();
	}
	public long getLong(int column) {
		
		throw new UnsupportedOperationException();
	}
	public float getFloat(int column) {
		
		throw new UnsupportedOperationException();
	}
	public double getDouble(int column) {
		if (column==0) return _0;
		
		throw new UnsupportedOperationException();
	}
	public Object getObject(int column) {
		throw new UnsupportedOperationException();
	}

	public void setInt(int column, int v) {
		throw new UnsupportedOperationException();
	}
	public void setLong(int column, long v) {
		throw new UnsupportedOperationException();
	}
	public void setFloat(int column, float v) {
		throw new UnsupportedOperationException();
	}
	public void setDouble(int column, double  v) {
		if (column==0) { _0=v; return; }

		throw new UnsupportedOperationException();
	}
	public void setObject(int column, Object v) {
		throw new UnsupportedOperationException();
	}
	public String toString() {
		return ""+_0; 
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		_0=in.readDouble();

	}	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeDouble(_0);

	}
} 