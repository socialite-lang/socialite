package socialite.codegen;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Writable;

import socialite.util.ByteBufferOutputStream;
import socialite.util.ByteBufferPool;
import socialite.util.SocialiteInputStream;
import socialite.util.SocialiteOutputStream;

public class EpochW implements Writable {
	public static final Log L=LogFactory.getLog(EpochW.class);
	
	Epoch e;
	public EpochW() { }
	public EpochW(Epoch _e) {
		e=_e;
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		int size=in.readInt();
		byte[] inBytes=new byte[size];
		
		in.readFully(inBytes);
		ByteArrayInputStream byteIn = new ByteArrayInputStream(inBytes);		
		ObjectInputStream ois = new SocialiteInputStream(byteIn);
		try {
			e = (Epoch)ois.readObject();
		} catch (ClassNotFoundException e) {
			L.fatal("StratumWritable.readFields:"+e);
		}
		ois.close();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream(12*1024);
		ObjectOutputStream oos=new SocialiteOutputStream(byteOut);
		oos.writeObject(e);
		oos.close();
		
		out.writeInt(byteOut.size());
		out.write(byteOut.toByteArray());
	}

	public Epoch get() { return e; }
}