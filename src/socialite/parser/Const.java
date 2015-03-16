package socialite.parser;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import socialite.type.Utf8;

public class Const implements Param, Comparable<Const> {
	private static final long serialVersionUID = 1L;
	/*static int constCountInARule = 0;
	public static void nextRule() {
		constCountInARule = 0;
	}*/
	public Class type;
	public Object val;
	public int id;
  public Const() {}
	public Const(Object v, int _id) {
		assert v instanceof Integer || v instanceof Long ||
				   v instanceof Float || v instanceof Double ||
				   v instanceof String || v instanceof Utf8;
		val = v;
		type = val.getClass();
		id = _id;
	}
	
	@Override	
	public String toString() {
		return "$const"+id;
	}

	public void negate() {
		assert MyType.isPrimitive(val);
		if (val instanceof Integer) {
			val = -(Integer)val;
		} else if (val instanceof Long) {
			val = -(Long)val;
		} else if (val instanceof Float) {
			val = -(Float)val;
		} else if (val instanceof Double) {
			val = -(Double)val;
		} else {
      throw new AssertionError("Unexpected constant type:"+val.getClass());
		}
	}
	public String constValStr() {
		if (val instanceof String) {
			return "\""+val+"\"";
		} else if (val instanceof Utf8) {
			return "u\""+val+"\"";
		} else {
			return ""+val;
		}
	}
	
	@Override
	public int hashCode() {
		return val.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Const)) return false;
		Const c=(Const)o;
		return val.equals(c.val) && id==c.id;
	}

	@Override
	public int compareTo(Const other) {
		return id - other.id;
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		char[] typename = new char[in.readInt()];
		for (int i=0; i<typename.length; i++)
			typename[i] = in.readChar();
		type = Class.forName(new String(typename));
		val = in.readObject();
		id = in.readInt();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		String typename = type.getName();
		out.writeInt(typename.length());
		out.writeChars(typename);
		out.writeObject(val);
		out.writeInt(id);
	}
}
