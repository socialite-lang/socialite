package socialite.functions;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import socialite.tables.Tuple;
import socialite.tables.Tuple_Object;
import socialite.util.Assert;
import socialite.util.SociaLiteException;


public class Read {
	static final int BUFFER_SIZE = 1024*1024*8;

	public static Iterator<String> invoke(String file) {
	    return invoke(file, "UTF-8");
	}

	public static Iterator<String> invoke(String file, String encoding) {
		if (file.startsWith("hdfs://") || file.startsWith("HDFS://")) {
			return new LineIteratorDist(file, encoding);
		}
		return new LineIterator(file, encoding);
	}
	
	static class LineIterator implements Iterator<String> {
		FileInputStream fis;
		BufferedReader reader;
		String line;
		LineIterator(String file, String encoding) {
			try {
				fis=new FileInputStream(file);
				reader = new BufferedReader(new InputStreamReader(fis, encoding), Read.BUFFER_SIZE);
			} catch (Exception e) {
				throw new SociaLiteException(e);
			}
		}
		@Override
		public boolean hasNext() {
			try {
				line=reader.readLine();
				if (line==null) {
					reader.close();
					return false;
				}
				return true;
			} catch (IOException e) {
				throw new SociaLiteException(e);
			}
		}
		@Override
		public String next() {
			return line;
		}
		@Override
		public void remove() { Assert.not_supported(); }	
	}
	static class LineIteratorDist implements Iterator<String> {
		public static final Log L=LogFactory.getLog(LineIteratorDist.class);
		
		BufferedReader reader;
		String line;
		
		LineIteratorDist(String _file, String encoding) {
			String file=_file.substring("hdfs://".length());
			try {
				FileSystem hdfs = FileSystem.get(new Configuration());
				Path f = new Path(file);
				FSDataInputStream fsis = hdfs.open(f);
				reader = new BufferedReader(new InputStreamReader(fsis, encoding), Read.BUFFER_SIZE);
			} catch (IOException e) {
				L.error("Error while accessing HDFS:"+e);
				L.error("Check HDFS configuration");
				throw new SociaLiteException(e);
			}
		}
		void closeFile() {
			try {
				reader.close();
			} catch (IOException e) {
				L.error("Error while closing file:"+e);
			}
		}
		public boolean hasNext() {
			try {
				line = reader.readLine();
				if (line==null) {
					closeFile();
					return false;
				}
				return true;			
			} catch (IOException e) {
				throw new SociaLiteException(e);
			}
		}
		
		public String next() {
			return line;
		}
		
		public void remove() {}
	}
}
