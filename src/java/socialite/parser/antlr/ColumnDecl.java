package socialite.parser.antlr;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.List;

import socialite.parser.MyType;
import socialite.type.Utf8;
import socialite.util.Assert;
import socialite.util.SociaLiteException;


public class ColumnDecl implements Externalizable {
	private static final long serialVersionUID = 1;
	static final int refSize;
	static {
		String arch = System.getProperty("sun.arch.data.model");
		if (arch.equals("32")) { refSize = 4; }
		else { refSize = 8; }
	}
	
	public static int guessRowSizeFromTypes(List<Class> types) {
		assert types!=null;
		int size=0;
		for (Class t:types)
			size += getTypeSize(t);
		return size;
	}
	static int getPrimTypeSize(Class klass) {
		if (klass.equals(int.class)) return 4;
		else if (klass.equals(long.class)) return 8;
		else if (klass.equals(float.class)) return 4;
		else if (klass.equals(double.class)) return 8;
		else throw new SociaLiteException("Unsupported primitive type:"+klass.getSimpleName());
	}
	public static int getTypeSize(Class klass) {
		klass = MyType.javaType(klass);
		if (klass.isPrimitive()) {
			return getPrimTypeSize(klass);			
		} else if (klass.equals(Utf8.class)) {
            // XXX: HACK!
            return 10;
        } else {
			return refSize;
		}
	}
	
	Class type;
	String name;
	int pos;
	ColOpt opt;
	public ColumnDecl() {}
	public ColumnDecl(Class _type, String _name) {
		this(_type, _name, null);
	}
	public ColumnDecl(Class _type, String _name, ColOpt _opt) {
		type = _type;
		name = _name;
		opt = _opt;
		pos = -1;
	}
	
	public ColumnDecl clone() {
		ColumnDecl decl=new ColumnDecl(type, name, null);
		if (opt!=null) decl.opt = opt.clone();
		decl.setPos(pos);
		return decl;
	}
	public Class type() { return type; }
	public String name() { return name; }
	
	public String toString() {
		return type.getSimpleName()+" "+name;
	}
	public void setPos(int _pos) {
		Assert.true_(pos == -1 || pos==_pos);
		pos = _pos;
	}
	public int pos() {
		if (pos==-1) {
			Assert.die("Position of column ["+name + "] is not initialized.");
		}
		return pos;
	}
	
	public ColOpt option() { return opt; }	
	
	public int getTypeSize() { return getTypeSize(type); }
	
	public int hashCode() {
		int h=0;
		h ^= type.hashCode();		
		if (opt!=null) h ^= opt.hashCode();
		h ^= pos;
		return h;
	}
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof ColumnDecl)) return false;		
		
		ColumnDecl vd=(ColumnDecl)o;
		if (opt==null) {
			if (vd.opt!=null) return false;
		} else {
			if (!opt.equals(vd.opt)) return false;
		}
		return type.equals(vd.type) && pos == vd.pos;
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
        char[] _typename = new char[in.readInt()];
        for (int i=0; i<_typename.length; i++)
            _typename[i] = in.readChar();
        Class objType = Class.forName(new String(_typename));
		type=MyType.javaType(objType);

        char[] _name = new char[in.readInt()];
        for (int i=0; i<_name.length; i++)
            _name[i] = in.readChar();
		name=new String(_name);
		pos=in.readInt();
		opt=(ColOpt)in.readObject();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
        String typeName = MyType.javaObjectType(type).getName();
		out.writeInt(typeName.length());
        out.writeChars(typeName);
        out.writeInt(name.length());
		out.writeChars(name);
		out.writeInt(pos);
		out.writeObject(opt);		
	}
}