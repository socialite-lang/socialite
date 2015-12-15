package socialite.util;


import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import socialite.resource.SRuntime;
import socialite.resource.SRuntimeWorker;
import socialite.tables.TmpTableInst;

public class FastOutputStream extends ObjectOutputStream {
	OutputStream out;
    FastClassLookup lookup;
   
	public FastOutputStream(OutputStream _out) throws IOException {
		super();
		out=_out;
		lookup= new FastClassLookup();
	}
	
	@Override
	public void close() throws IOException {
		out.close();
	}	
	@Override
	public void flush() throws IOException {		
		out.flush();	
	}
	
	@Override
	public void write(int b) throws IOException {
		out.write(b);
	}
	
	@Override
	public void write(byte[] b) throws IOException {	
		out.write(b);
	}
	@Override 
	public void write(byte[] b, int off, int len) throws IOException {
		out.write(b, off, len);
	}

	static String[] classDescr;
	static {
		classDescr = new String[128];
		for (int i=0; i<classDescr.length; i++) {
			classDescr[i] = "#"+i;
		}
	}
	private String getShortClassDescr(int idx) {
		if (idx < classDescr.length)
			return classDescr[idx];
		return "#"+idx;			                  
	}
	private String getClassDescr(Class cls) {
		int fastIdx=lookup.getIdx(cls);
		String className;
		if (fastIdx>=0) className=getShortClassDescr(fastIdx);
		else className=cls.getName();
		return className;
	}
	@Override 
	protected void writeObjectOverride(Object obj) throws IOException {
		if (obj instanceof Object[]) {
			writeObjectArray(obj);
		} else if (obj.getClass().isArray()) { 
			writePrimArray(obj);
		} else {
			String className = getClassDescr(obj.getClass());
			writeInt(className.length());
			writeClassName(className, obj.getClass());
						
			if (TmpTableInst.class.isAssignableFrom(obj.getClass())) {				
				TmpTableInst inst = (TmpTableInst)obj;
				writeTableInstSize(inst);	
			}

			if (obj instanceof String) {
				String s=(String)obj;
				writeInt(s.length());
				writeChars(s);
			} else if (obj instanceof Externalizable) {
				Externalizable e=(Externalizable)obj;
				e.writeExternal(this);
			} else {
				String msg="Only Externalizable object can be written in fast mode";
				throw new UnsupportedOperationException(msg);	
			}
		} 
			
	}
	
	void writeTableInstSize(TmpTableInst inst) throws IOException {
		char size;
		if (inst.isSmall()) size=1;
		else size=2;		

		writeChar(size);	
	}
	
	void writeClassName(String className, Class cls) throws IOException {
		writeChars(className);
		/*if (className.charAt(0)=='#') 
			lookup.addClass(cls);*/
	}
	
	void writePrimArray(Object a) throws IOException {
		Class itemType = a.getClass().getComponentType();
		assert itemType.isPrimitive();
		
		String className=getClassDescr(itemType);
		
		writeInt(1+className.length());
		writeChar('[');
		writeClassName(className, itemType);		
		if (itemType.equals(int.class)) {
			int[] array=(int[])a;
			writeInt(array.length);
			for (int i=0; i<array.length; i++) {
				writeInt(array[i]);
			}
		} else if (itemType.equals(long.class)) {
			long[] array=(long[])a;
			writeInt(array.length);
			for (int i=0; i<array.length; i++) {
				writeLong(array[i]);
			}
		} else if (itemType.equals(float.class)) {
			float[] array=(float[])a;
			writeInt(array.length);
			for (int i=0; i<array.length; i++) {
				writeFloat(array[i]);
			}
		} else if (itemType.equals(double.class)) {
			double[] array=(double[])a;
			writeInt(array.length);
			for (int i=0; i<array.length; i++) {
				writeDouble(array[i]);
			}
		} else if (itemType.equals(byte.class)) {
			byte[] array=(byte[])a;
			writeInt(array.length);
			for (int i=0; i<array.length; i++) {
				writeByte(array[i]);
			}
		} else if (itemType.equals(boolean.class)) {
			boolean[] array=(boolean[])a;
			writeInt(array.length);
			for (int i=0; i<array.length; i++) {
				writeBoolean(array[i]);
			}
		} else if (itemType.equals(short.class)) {
			short[] array=(short[])a;
			writeInt(array.length);
			for (int i=0; i<array.length; i++) {
				writeShort(array[i]);
			}
		} else if (itemType.equals(char.class)) {
			char[] array=(char[])a;
			writeInt(array.length);
			for (int i=0; i<array.length; i++) {
				writeChar(array[i]);
			}
		} else {
			throw new UnsupportedOperationException("Unsupported primitive array type:"+itemType+"[");	
		}
	}
	void writeObjectArray(Object a) throws IOException {
		Object[] array=(Object[])a;
		Class itemType = array.getClass().getComponentType();		
		String className=getClassDescr(itemType);
		for (int i=0; i<array.length; i++) {
			if (array[i]!=null) {
				itemType = array[i].getClass();
				className=getClassDescr(itemType);
				break;
			}
		}
		boolean tableArray=false;
		if (TmpTableInst.class.isAssignableFrom(itemType))
			tableArray=true;

		writeInt(1+className.length());
		writeChar('[');
		writeClassName(className, itemType);
		writeInt(array.length);		
		for (int i=0; i<array.length; i++) {
			if (array[i]==null) {
				writeBoolean(false);
			} else {
				writeBoolean(true);
				assert array[i] instanceof Externalizable ||
						array[i] instanceof String;
				if (tableArray) {
					TmpTableInst inst = (TmpTableInst)array[i];
					writeTableInstSize(inst);
				}				
				
				if (itemType.equals(String.class)) {
					String s=(String)array[i];
					writeInt(s.length());
					writeChars(s);
				} else {
					Externalizable ext=(Externalizable)array[i];
					ext.writeExternal(this);
				}
			}
		}
	}
	
	@Override 
	public void writeBoolean(boolean f) throws IOException {
		if (f) out.write(1);
		else out.write(0);		
	}
	@Override
	public void writeByte(int b) throws IOException {
		out.write(b);
	}
	
	@Override
	public void writeBytes(String s) throws IOException {
		for (int i=0; i<s.length(); i++) {
			char c=s.charAt(i);
			out.write(c);
		}
	}
	
	@Override
	public void writeChar(int v) throws IOException {
		writeByte((byte)(0xff & (v >> 8)));
		writeByte((byte)(0xff & v));
	}

	@Override
	public void writeChars(String s) throws IOException {		
		for (int i=0; i<s.length(); i++) {
			char c=s.charAt(i);
			writeChar(c);
		}
	}
	
	@Override
	public void writeDouble(double v) throws IOException {
		long l = Double.doubleToRawLongBits(v);
		writeLong(l);
	}
	
	@Override
	public void writeFloat(float v) throws IOException {
		int i = Float.floatToRawIntBits(v);
		writeInt(i);		
	}
	
	@Override
	public void writeInt(int i) throws IOException {
		out.write((byte)(0xff & (i >> 24)));
		out.write((byte)(0xff & (i >> 16)));
		out.write((byte)(0xff & (i >>  8)));
		out.write((byte)(0xff & i));
	}
	
	@Override
	public void writeLong(long l) throws IOException {
		out.write((byte)(0xff & (l >> 56)));
		out.write((byte)(0xff & (l >> 48)));
		out.write((byte)(0xff & (l >> 40)));
		out.write((byte)(0xff & (l >> 32)));
		out.write((byte)(0xff & (l >> 24)));
		out.write((byte)(0xff & (l >> 16)));
		out.write((byte)(0xff & (l >>  8)));
		out.write((byte)(0xff & l));
	}
		
	public void writeShort(short s) throws IOException {
		out.write((byte)(0xff & (s >> 8)));
		out.write((byte)(0xff & s));
	}
	
	@Override
	public void writeUTF(String s) throws IOException {
		throw new UnsupportedOperationException("writeUTF is not supported for fast mode");
	}

}
