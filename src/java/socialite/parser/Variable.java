package socialite.parser;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;

import socialite.util.InternalException;
import socialite.util.SociaLiteException;

public class Variable implements Param {
	private static final long serialVersionUID = 1L;
/*
	static HashMap<String, Variable> varMapInARule=new HashMap<String, Variable>();
	public static Variable getVariable(String name) {
		if (name.equals("_")) return new Variable(name);
		if (varMapInARule.containsKey(name)) return varMapInARule.get(name);
		Variable v = new Variable(name);
		varMapInARule.put(name, v);
		return v;
	}
	public static Variable getTmpVar() {
		String name = "_tmp$"+(varCountInARule++);
		return new Variable(name);
	}
	public static void nextRule() { 
		varMapInARule.clear();
		varCountInARule=0;
	}
	private static int varCountInARule=0;*/
	
	
	public String name;
	public Class type;	
	public boolean dontCare=false;

  public Variable() { }
  public Variable(String _name) {
    this(_name, false);
  }
	public Variable(String _name, boolean _dontcare) {
		if (_dontcare) { dontCare=true;}
		name = _name;
		type = NoType.class;
	}

	public Variable(String _name, Class _type) {
		name = _name;
		type = _type;
	}

	public String toString() { return name; }

	//public void rename(String _name) { name = _name; }
	public void setType(Class _type) throws InternalException {
		_type = MyType.javaType(_type);		
		if (hasType()) {
			if (type.equals(_type)) return;
			if (_type.equals(Object.class)) return;
			if (type.equals(Object.class)) {
				type = _type;
				return;
			}			
						
			String msg="Variable "+name+" is "+type.getSimpleName()+ " type"+
						", incompatible with type "+ _type.getSimpleName();
			throw new InternalException(msg);
		}		
		type = MyType.javaType(_type);
	}

	public boolean isRealVar() {
		if (name.indexOf('.')>=0)
			return false;
		return true;
	}
	public boolean hasType() {
		if (type.equals(NoType.class)) return false;
		else return true;
	}

	@Override
	public int hashCode() {
		return name.hashCode() << 2 + type.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Variable)) return false;

		Variable v = (Variable) o;
		if (name.equals(v.name) && type.equals(v.type))
			return true;
            return false;
        }

        public String description() {
            int id=System.identityHashCode(this);
            return name + "@"+id+":" + type.getSimpleName();
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException,
                ClassNotFoundException {
            char[] _name = new char[in.readInt()];
		for (int i=0; i<_name.length; i++)
			_name[i] = in.readChar();
		name = new String(_name);

		char[] _typename = new char[in.readInt()];
		for (int i=0; i<_typename.length; i++)
			_typename[i] = in.readChar();
		Class objtype = Class.forName(new String(_typename));
        type = MyType.javaType(objtype);
		dontCare = in.readBoolean();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(name.length());
		out.writeChars(name);
		String typeName = MyType.javaObjectType(type).getName();
		out.writeInt(typeName.length());
		out.writeChars(typeName);
		out.writeBoolean(dontCare);
	}
}
