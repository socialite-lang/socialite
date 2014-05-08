package socialite.util;


import java.io.EOFException;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.StreamCorruptedException;
import java.lang.reflect.Array;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.resource.SRuntime;

public class SocialiteInputStream extends ObjectInputStream {
	public static final Log L=LogFactory.getLog(SocialiteInputStream.class);
		
    FastClassLookup lookup;

	public SocialiteInputStream(InputStream in) throws IOException {
		super(in);
		lookup = new FastClassLookup(true);
	}
	
    @Override
    protected ObjectStreamClass readClassDescriptor()
            throws IOException, ClassNotFoundException {
        int type = read();
        if (type < 0) {
            throw new EOFException();
        }
        switch (type) {
        case SocialiteOutputStream.TYPE_FAT_DESCRIPTOR:
            return super.readClassDescriptor();
        case SocialiteOutputStream.TYPE_THIN_DESCRIPTOR:
        	Class<?> cls;
            String className = readUTF();
            if (className.charAt(0)=='#') {
            	int fastIdx=Integer.parseInt(className.substring(1));
    			cls = lookup.getClass(fastIdx);
            } else {
            	cls = Loader.forName(className);
            }
            ObjectStreamClass streamClass = ObjectStreamClass.lookup(cls);            
            return streamClass;
        default:
            throw new StreamCorruptedException(
                    "Unexpected class descriptor type: " + type);
        }
    }


    @Override
	protected Class<?> resolveClass(ObjectStreamClass desc)
	throws IOException, ClassNotFoundException {		
		try {
			return super.resolveClass(desc);
		} catch (Exception e) {
			String name=desc.getName();
			return Loader.forName(name);
		}
	}
}