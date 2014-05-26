package socialite.dist;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Writable;

import socialite.util.SociaLiteException;
import socialite.util.SocialiteInputStream;
import socialite.util.SocialiteOutputStream;

public class Status {
	static final String FreeMem="FreeMem";
	static final String Table="Table";
	static final String Progress="Progress";
	static final String NodeNum="NodeNum";
	
	HashMap<Object, Object> elem;
	
	public Status() {
		elem = new HashMap<Object, Object>();
		putNodeNum(1);
	}
	public Object getNodeNum() { return get(NodeNum); }
	public Object getMemStatus() { return get(FreeMem); }
	public Object getTableStatus() { return get(Table); }
	public Object getProgress() { return get(Progress); }
	
	Object get(String key) {
		Object val=elem.get(key);
		if (val==null) val="";
		return val;
	}
	public Object putNodeNum(Object val) { return elem.put(NodeNum, val); }
	public Object putTableStatus(Object val) { return elem.put(Table, val); }
	public Object putMemStatus(Object val) { return elem.put(FreeMem, val); }
	public Object putProgress(Object val) { return elem.put(Progress, val); }
	

	public static BytesWritable toWritable(Status s) {		
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream(4*1024);
			ObjectOutputStream oos=new SocialiteOutputStream(byteOut);
			oos.writeObject(s.elem);
			oos.close();
			return new BytesWritable(byteOut.toByteArray());
		} catch (IOException e) {
			throw new SociaLiteException(e);			
		}
	}	
	public static Status fromWritable(BytesWritable bw) {
		byte[] bytes = bw.getBytes();
		try {
			ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new SocialiteInputStream(byteIn);
			@SuppressWarnings("unchecked")
			HashMap<Object, Object> map = (HashMap<Object, Object>)ois.readObject();
			Status s = new Status();
			s.elem = map;
			ois.close();
			return s;
		} catch (Exception e) {
			throw new SociaLiteException(e);
		}
	}
}
