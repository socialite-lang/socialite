package socialite.codegen;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Writable;

import socialite.util.ByteBufferOutputStream;
import socialite.util.ByteBufferPool;
import socialite.util.SocialiteInputStream;
import socialite.util.SocialiteOutputStream;
import socialite.util.SociaLiteException;

public class EpochW implements Writable {
	public static final Log L=LogFactory.getLog(EpochW.class);
	
	Epoch e;
	byte[] data;
	public EpochW() { }
	public EpochW(Epoch _e) {
		e=_e;
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream(12*1024);
			ObjectOutputStream oos=new SocialiteOutputStream(byteOut);
			oos.writeObject(e);
			oos.close();
			data = byteOut.toByteArray();
            //L.info("EpochW size:"+data.length);
		} catch (Exception e) {
            L.error("Exception during serialization:"+ExceptionUtils.getStackTrace(e));
			throw new SociaLiteException(e);
		}
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		int size=in.readInt();
		byte[] inBytes=new byte[size];

        try {
            in.readFully(inBytes);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(inBytes);
            ObjectInputStream ois = new SocialiteInputStream(byteIn);
            try {
                e = (Epoch) ois.readObject();
            } catch (ClassNotFoundException e) {
                L.fatal("readFields():" + ExceptionUtils.getStackTrace(e));
            }
            ois.close();
        } catch (Exception e) {
            L.error("readFields():"+ExceptionUtils.getStackTrace(e));
        }
	}

	@Override
	public void write(DataOutput out) throws IOException {
        out.writeInt(data.length);
        out.write(data);
	}

	public Epoch get() { return e; }
}
