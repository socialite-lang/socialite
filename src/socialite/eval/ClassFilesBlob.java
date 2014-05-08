package socialite.eval;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.BytesWritable;

import socialite.util.Assert;
import socialite.util.SociaLiteException;
import socialite.util.SocialiteInputStream;
import socialite.util.SocialiteOutputStream;

public class ClassFilesBlob implements Externalizable {
	static final long serialVersionUID = 1;
	
	List<String> classNames;
	List<byte[]> classFiles;

	public ClassFilesBlob() {}
	public ClassFilesBlob(List<String> _classNames, List<byte[]> _classFiles) {
		classNames = _classNames;
		classFiles = _classFiles;
	}
	
	public static ClassFilesBlob fromBytesWritable(BytesWritable bw) {
		byte[] bytes = bw.getBytes();
		try {
			ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new SocialiteInputStream(byteIn);
			ClassFilesBlob blob=(ClassFilesBlob)ois.readObject();
			ois.close();
			return blob;
		} catch (Exception e) {
			throw new SociaLiteException(e);
		}
	}
	public static BytesWritable toBytesWritable(ClassFilesBlob c) {
		try {
			int numClasses = c.classFiles.size();
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream(numClasses*12*1024);
			ObjectOutputStream oos=new SocialiteOutputStream(byteOut);
			oos.writeObject(c);
			oos.close();		
			return new BytesWritable(byteOut.toByteArray());
		} catch (Exception e) {			
			throw new SociaLiteException(e);
		}
	}
	public List<String> names() { return classNames; }
	public List<byte[]> files() { return classFiles; }
	
	@Override
	public void readExternal(ObjectInput in)  throws IOException,
			ClassNotFoundException {
		int size = in.readInt();
		classNames = new ArrayList<String>(size);
		for (int i=0; i<size; i++) {
			char[] ch = new char[in.readInt()];
			for (int j=0; j<ch.length; j++) 
				ch[j] = in.readChar();			
			classNames.add(new String(ch));
		}
		classFiles = new ArrayList<byte[]>(size);
		for (int i=0; i<size; i++) {
			byte[] b = new byte[in.readInt()];			
			int read=0;
			while (read < b.length) {
				int r=in.read(b, read, b.length-read);
				if (r<0) throw new SociaLiteException("Unexpected EOF "+classNames.get(i));
				read += r;
			}
			classFiles.add(b);
		}
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(classNames.size());
		for (String s:classNames) {
			out.writeInt(s.length());
			out.writeChars(s);
		}		
		for (byte[] b:classFiles) {
			out.writeInt(b.length);
			out.write(b);
		}
	}	
}
