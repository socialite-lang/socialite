package socialite.util;


import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang3.exception.ExceptionUtils;

import socialite.eval.TmpTablePool;
import socialite.resource.SRuntimeWorker;
import socialite.tables.TmpTableInst;
import socialite.tables.TableUtil;

public class FastInputStream extends ObjectInputStream {
	public static final Log L=LogFactory.getLog(FastInputStream.class);

	InputStream in;
	FastClassLookup lookup;
	public FastInputStream(InputStream _in) throws IOException {
		super();
		in=_in;
		lookup=new FastClassLookup();
	}
	
	@Override
	public void close() throws IOException {		
		in.close();
	}
	@Override
	public int read() throws IOException {
		return in.read();
	}
	@Override
	public int read(byte[] b) throws IOException {
		return in.read(b);
	}
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return in.read(b, off, len);
	}
	@Override
	public Object readObjectOverride() throws IOException, ClassNotFoundException {
		int classNameLen=readInt();
		final char[] _className = new char[classNameLen];
		for (int i=0; i<_className.length; i++) {
			_className[i] = readChar();
		}
		String className = new String(_className);
		if (className.startsWith("[")) {
			return readArray(className);
		}
		Class<?> cls = getClass(className);
		if (cls.equals(String.class)) {
			char[] s = new char[readInt()];
			for (int i=0; i<s.length; i++) s[i] = readChar();
			return new String(s);
		} 
		Object obj=null;
		try {
			if (TmpTableInst.class.isAssignableFrom(cls)) {
				char size = readChar();
				if (size==2) obj = TmpTablePool._get(cls);
				else if (size==1) obj = TmpTablePool._getSmall(cls);
				else new AssertionError("Unexpected size for "+cls.getName());
			} else {
				obj = cls.newInstance();
			}
			assert obj instanceof Externalizable;
			Externalizable ext=(Externalizable)obj;
			ext.readExternal(this);
			return obj;
		} catch (Exception e) {			
			L.fatal("Exception while creating table instance:"+e);
			L.fatal(ExceptionUtils.getStackTrace(e));
			throw new SociaLiteException(e);
		} 
	}	
	static TmpTableInst newInstance(Class<?> tableCls) throws Exception {
		Constructor<?> c = tableCls.getDeclaredConstructor((Class[])null);
		c.setAccessible(true);
		TmpTableInst i= (TmpTableInst)c.newInstance((Object[])null);
		return i;
	}
	
	private Class<?> getClass(String className) {
		if (className.charAt(0)=='#') {
			int fastIdx=Integer.parseInt(className.substring(1));
			return lookup.getClass(fastIdx);
		} else {
			Class<?> cls=Loader.forName(className);
			lookup.addClass(cls);
			return cls;
		}
	}
	
	Object readArray(String className) throws IOException, ClassNotFoundException {
		className = className.substring(1, className.length());
		Class<?> cls = getClass(className);
		if (cls.isPrimitive()) {
			return readPrimArray(cls);
		} else {
			return readObjectArray(cls);
		}
	}
	Object readPrimArray(Class<?> cls) throws IOException, ClassNotFoundException {
		int arraylen = readInt();
		Object ret=null;
		if (cls.equals(int.class)) {
			int[] array = new int[arraylen];
			for (int i=0; i<array.length; i++) {
				array[i] = readInt();
			}
			ret = array;
		} else if (cls.equals(long.class)){
			long[] array = new long[arraylen];
			for (int i=0; i<array.length; i++) {
				array[i] = readLong();
			}
			ret = array;
		} else if (cls.equals(float.class)){
			float[] array = new float[arraylen];
			for (int i=0; i<array.length; i++) {
				array[i] = readFloat();
			}
			ret = array;
		} else if (cls.equals(double.class)){
			double[] array = new double[arraylen];
			for (int i=0; i<array.length; i++) {
				array[i] = readDouble();
			}
			ret = array;
		} else if (cls.equals(byte.class)){
			byte[] array = new byte[arraylen];
			for (int i=0; i<array.length; i++) {
				array[i] = readByte();
			}
			ret = array;
		} else if (cls.equals(boolean.class)){
			boolean[] array = new boolean[arraylen];
			for (int i=0; i<array.length; i++) {
				array[i] = readBoolean();
			}
			ret = array;
		} else if (cls.equals(short.class)){
			short[] array = new short[arraylen];
			for (int i=0; i<array.length; i++) {
				array[i] = readShort();
			}
			ret = array;
		} else if (cls.equals(char.class)){
			char[] array = new char[arraylen];
			for (int i=0; i<array.length; i++) {
				array[i] = readChar();
			}
			ret = array;
		} else {
			throw new UnsupportedOperationException("Unsupported type array:"+cls.getSimpleName()+"[");
		}
		return ret;
	}
	
	Object readObjectArray(Class<?> cls) throws IOException, ClassNotFoundException {		
		int arrayLen = readInt();
		Object[] array=(Object[])Array.newInstance(cls, arrayLen);
		
		for (int i=0; i<array.length; i++) {
			if (readBoolean()) {
				try { 
					if (cls.equals(String.class)) {
						int slen = readInt();
						final char[] chars = new char[slen];
						for (int j=0; j< slen; j++)
							chars[j] = readChar();
						String s = new String(chars);
						array[i] = s;
						continue;
					}
					
					if (TmpTableInst.class.isAssignableFrom(cls)) {
						char size = readChar();
						if (size==2) array[i] = TmpTablePool._get(cls);
						else if (size==1) array[i] = TmpTablePool._getSmall(cls);
						else new AssertionError("Unexpected size for "+cls.getName());
						
						((Externalizable)array[i]).readExternal(this);
					} else {
						array[i] = cls.newInstance();
						((Externalizable)array[i]).readExternal(this);
					}
					
				} catch (Exception e) {
					L.fatal("Exception while creating table instance:"+e);
					L.fatal(ExceptionUtils.getStackTrace(e));
					throw new SociaLiteException(e);
				}
			} else {
				array[i] = null;
			}
		}
		return array;
	}
	
	@Override
	public boolean readBoolean() throws IOException {
		if (in.read()==1) return true;
		else return false;
	}
	@Override
	public byte readByte() throws IOException {
		return (byte)in.read();
	}
	@Override
	public char readChar() throws IOException {
		char a=(char)readByte();
		char b=(char)readByte();
		return (char)((a << 8) | (b & 0xff));
	}
	@Override
	public double readDouble() throws IOException {
		long l=readLong();
		return Double.longBitsToDouble(l);
	}
	@Override
	public float readFloat() throws IOException {
		int i=readInt();
		return Float.intBitsToFloat(i);
	}
	@Override
	public int readInt() throws IOException {
		int a=(int)readByte();
		int b=(int)readByte();
		int c=(int)readByte();
		int d=(int)readByte();
		return (((a & 0xff) << 24) | ((b & 0xff) << 16) |
				  ((c & 0xff) << 8) | (d & 0xff));
	}
	@Override
	public long readLong() throws IOException {
		long a=(long)readByte();
		long b=(long)readByte();
		long c=(long)readByte();
		long d=(long)readByte();
		long e=(long)readByte();
		long f=(long)readByte();
		long g=(long)readByte();
		long h=(long)readByte();
		return (((long)(a & 0xff) << 56) |
				  ((long)(b & 0xff) << 48) |
				  ((long)(c & 0xff) << 40) |
				  ((long)(d & 0xff) << 32) |
				  ((long)(e & 0xff) << 24) |
				  ((long)(f & 0xff) << 16) |
				  ((long)(g & 0xff) <<  8) |
				  ((long)(h & 0xff)));
	}	
	@Override
	public short readShort() throws IOException {
		short a=(short)readByte();
		short b=(short)readByte();
		return (short)((a << 8) | (b & 0xff));
	}
	@Override
	public int readUnsignedShort() throws IOException {
		short a=(short)readByte();
		short b=(short)readByte();
		return (((a & 0xff) << 8) | (b & 0xff));
	}
	@Override
	public int readUnsignedByte() throws IOException {
		throw new UnsupportedOperationException();
	}
	@Override
	public String readUTF() throws IOException {
		throw new UnsupportedOperationException();
	}
}
