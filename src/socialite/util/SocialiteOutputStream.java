package socialite.util;


import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.ObjectStreamClass;

import socialite.resource.SRuntime;

public class SocialiteOutputStream extends ObjectOutputStream {
    static final int TYPE_FAT_DESCRIPTOR = 0;
    static final int TYPE_THIN_DESCRIPTOR = 1;
    static String[] classDescr;
	static {
		classDescr = new String[128];
		for (int i=0; i<classDescr.length; i++) {
			classDescr[i] = "#"+i;
		}
	}
	
    FastClassLookup lookup;
	public SocialiteOutputStream(OutputStream _out) throws IOException {
		super(_out);
		lookup = new FastClassLookup(true);
	}	
	
	String getShortClassDescr(int idx) {
		if (idx < classDescr.length) {
			return classDescr[idx];
		}
		return "#"+idx;
	}
	@Override
    protected void writeClassDescriptor(ObjectStreamClass desc) throws IOException {
        Class<?> cls = desc.forClass();
        int fastIdx=lookup.getIdx(cls);
        if (fastIdx>=0) {
        	write(TYPE_THIN_DESCRIPTOR);
        	writeUTF(getShortClassDescr(fastIdx));
        	return;
        }
        
        if (cls.isPrimitive() || cls.isArray() || cls.isInterface() ||
            desc.getSerialVersionUID() == 0) {
            write(TYPE_FAT_DESCRIPTOR);
            super.writeClassDescriptor(desc);
        } else {
            write(TYPE_THIN_DESCRIPTOR);
            writeUTF(desc.getName());
        }
        lookup.addClass(cls);
    }   
}