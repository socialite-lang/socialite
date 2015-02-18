package socialite.functions;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import socialite.resource.SRuntimeWorker;
import socialite.tables.Tuple;
import socialite.tables.Tuple_Object;
import socialite.util.Assert;
import socialite.util.SociaLiteException;


public class Read {
	public static final Log L=LogFactory.getLog(Read.class);
	
	static final int BUFFER_SIZE = 1024*1024*8;

	public static Iterator<String> invoke(String file) {
	    return invoke(file, "UTF-8");
	}

	public static Iterator<String> invoke(String file, String encoding) {
		if (file.startsWith("hdfs://") || file.startsWith("HDFS://")) {
			if (SRuntimeWorker.getInst() == null) {
				return readFromHdfs(file, encoding);
			} else {
				return SplitRead.invoke(file, encoding);
			}
		}
		return new LineIterator(file, encoding);
	}
	
	static class LineIterator implements Iterator<String> {
		FileInputStream fis;
		BufferedReader reader;
		String line;
		LineIterator(String file, String encoding) {
			if (file.startsWith("file://") || file.startsWith("FILE://")) {
				file= file.substring("file://".length());
			}
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
	static Iterator<String> readFromHdfs(String file, String encoding) {
		file= file.substring("hdfs://".length());
		try {
			FileSystem hdfs = FileSystem.get(new Configuration());
			Path f = new Path(file);
			if (hdfs.isFile(f)) {
				return new LineIteratorDist(hdfs, f, encoding);
			} else {
				return new LineIteratorDistDir(hdfs, f, encoding);
			}
		} catch (IOException e) {
			L.error("Error while accessing HDFS:"+e);
			L.error("Check HDFS configuration");
			return new Iterator<String>() {
				public boolean hasNext() { return false; }
 				public String next() { return null; }
				public void remove() {}};
		}
		
	}
	static class LineIteratorDistDir implements Iterator<String> {
		ArrayList<Path> files = new ArrayList<Path>();
		FileSystem hdfs;
		String encoding;
		LineIteratorDist lines;
		LineIteratorDistDir(FileSystem _hdfs, Path _file, String _encoding) {
			encoding = _encoding;
			try {
				hdfs = _hdfs;
				for (FileStatus fs:hdfs.listStatus(_file)) {
					files.add(fs.getPath());
				}
				if (!files.isEmpty()) {
					lines = new LineIteratorDist(hdfs, files.remove(0), encoding);
				}
			} catch (IOException e) {
				L.error("Error while accessing HDFS:"+e);
				L.error("Check HDFS configuration");
			}
		}
		@Override
		public boolean hasNext() {
			if (lines==null) return false;
			if (lines.hasNext()) return true;
			
			if (files.isEmpty()) {
				return false;
			} else {
				while (!files.isEmpty()) {
					lines = new LineIteratorDist(hdfs, files.remove(0), encoding);
					if (lines.hasNext()) { return true; }
				}
				return false;
			}
		}

		@Override
		public String next() {
			if (lines==null) return null;
			return lines.next();
		}

		@Override
		public void remove() { }
	}
	static class LineIteratorDist implements Iterator<String> {		
		BufferedReader reader;
		String line;
		
		LineIteratorDist(FileSystem hdfs, Path file, String encoding) {
			try {
				FSDataInputStream fsis = hdfs.open(file);
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
