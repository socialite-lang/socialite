package socialite.parser;

public class Value {
	public Class type;
	String value; /* string representation of the value */
	public Value(Class _type, String _value) {
		type = _type;
		value = _value;
	}
	
	public String toString() {
		return "(("+type.getSimpleName()+")"+value+")";
	}
}
