package socialite.resource;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Writable;
import org.apache.commons.lang3.exception.ExceptionUtils;

import socialite.util.SocialiteInputStream;
import socialite.util.SocialiteOutputStream;

public class WorkerAddrMapW implements Writable {
	public static final Log L=LogFactory.getLog(WorkerAddrMapW.class);
	
	WorkerAddrMap addrMap;	
	public WorkerAddrMapW() {}	
	public WorkerAddrMapW(WorkerAddrMap _addrMap) { addrMap=_addrMap; }

	public WorkerAddrMap get() {return addrMap;}
	@Override
	public void readFields(DataInput in) throws IOException {
		int size=in.readInt();
		byte[] inBytes=new byte[size];
			
		in.readFully(inBytes);
				
		ByteArrayInputStream byteIn = new ByteArrayInputStream(inBytes);		
		ObjectInputStream ois = new SocialiteInputStream(byteIn);
		try {
			addrMap=(WorkerAddrMap)ois.readObject();
		} catch (ClassNotFoundException e) {
			L.fatal("Exception while calling readFields(): "+e);
			L.fatal(ExceptionUtils.getStackTrace(e));
		}
	}

	@Override
	public void write(DataOutput out) throws IOException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream(256);
		ObjectOutputStream oos=new SocialiteOutputStream(byteOut);//new ObjectOutputStream(byteOut);
		oos.writeObject(addrMap);
		oos.close();
		out.writeInt(byteOut.size());
		out.write(byteOut.toByteArray());
	}

}
