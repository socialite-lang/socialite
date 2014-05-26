package socialite.tables;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.apache.hadoop.io.Writable;

import socialite.util.SociaLiteException;
import socialite.util.SocialiteInputStream;
import socialite.util.SocialiteOutputStream;

public class ConstsWritable implements Writable {
	List<Object> consts;
	
	public ConstsWritable() {}
	public ConstsWritable(List<Object> _consts) {
		consts = _consts;
	}
	
	public List<?> get() { return consts; }
	
	@SuppressWarnings("unchecked")
	@Override
	public void readFields(DataInput in) throws IOException {
		int size=in.readInt();
		byte[] inBytes = new byte[size];
		in.readFully(inBytes);
		ByteArrayInputStream byteIn = new ByteArrayInputStream(inBytes);		
		ObjectInputStream ois = new SocialiteInputStream(byteIn);
		try {
			consts = (List<Object>)ois.readObject();
		} catch (ClassNotFoundException e) {
			ois.close();
			throw new SociaLiteException(e);
		}
		ois.close();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream(512);
		ObjectOutputStream oos=new SocialiteOutputStream(byteOut);
		oos.writeObject(consts);
		oos.close();		
		out.writeInt(byteOut.size());
		out.write(byteOut.toByteArray());
	} 	
}