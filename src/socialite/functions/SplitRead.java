package socialite.functions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.LineRecordReader;

import socialite.resource.SRuntimeWorker;
import socialite.util.SociaLiteException;
import socialite.resource.SRuntime;
import socialite.resource.WorkerAddrMap;
import socialite.dist.Host;
import socialite.eval.Worker;
import socialite.eval.EvalProgress;

public class SplitRead {
	static final int BUFFER_SIZE = 1024*1024*8;

	public static Iterator<String> invoke(String file) {
	    return invoke(file, "UTF-8");
	}

	public static Iterator<String> invoke(String file, String encoding) {
		if (file.startsWith("hdfs://") || file.startsWith("HDFS://")) {
			file=file.substring("hdfs://".length());
		}
		return new LineIteratorDist(file, encoding);
	}
	

	static class LineIteratorDist implements Iterator<String> {
		public static final Log L=LogFactory.getLog(LineIteratorDist.class);

		List<FileSplit> mySplits;
		int splitIdx;
		LineRecordReader reader;
		LongWritable lineOffset;
		Text line;
		int ruleid;
		EvalProgress evalProgress;
		
		LineIteratorDist(String file, String encoding) {
			ruleid = Worker.getCurrentRuleId();
			evalProgress = EvalProgress.getInst();
			try {
				FileSystem hdfs = FileSystem.get(new Configuration());
				Path f = new Path(file);
				FileStatus fstat = hdfs.getFileStatus(f);
				long length = fstat.getLen();
				if (length==0) {
					mySplits = null;
				} else {
					BlockLocation[] blockLocs=hdfs.getFileBlockLocations(fstat, 0, length);
					mySplits = getMySplits(fstat, blockLocs);
					if (mySplits!=null) {
						splitIdx=0;
						reader = new LineRecordReader(new Configuration(), mySplits.get(splitIdx));
						lineOffset = new LongWritable(0L);
						line = new Text("");
					}
				}
			} catch (IOException e) {
				L.error("Error while accessing HDFS:"+e);
				L.error("Check HDFS configuration");
				throw new SociaLiteException(e);
			}
		}
		public boolean hasNext() {
			if (mySplits==null) return false;

			try {
				while (true) {
					boolean hasNext = reader.next(lineOffset, line);
					if (hasNext) return true;
	
					reader.close();
					if (splitIdx < mySplits.size()-1) {
						splitIdx++;
						reader = new LineRecordReader(new Configuration(), mySplits.get(splitIdx));
						evalProgress.update(ruleid, splitIdx, mySplits.size());
					} else {
						return false;
					}
				}
			} catch (IOException e) {
				throw new SociaLiteException(e);
			}
		}
		
		public String next() {
			return line.toString();
		}
	
		List<FileSplit> getMySplits(FileStatus fstat, BlockLocation[] blockLocs) throws IOException {
			String[] servers = getWorkers();
			for (int i=0; i<servers.length; i++) servers[i] = servers[i].toLowerCase();

			int maxBlockNum=(int)(((blockLocs.length+servers.length-1)/servers.length)*1.1);
			HashMap<String, List<Integer>> blockMap = new HashMap<String, List<Integer>>();

			for (String s:servers) blockMap.put(s, new ArrayList<Integer>());
			
			for (int blockIdx=0; blockIdx<blockLocs.length; blockIdx++) {
				BlockLocation loc = blockLocs[blockIdx];
				String[] hosts=loc.getHosts();
				for (int i=0; i<hosts.length; i++) hosts[i] = hosts[i].toLowerCase();
				Arrays.sort(Arrays.copyOf(hosts, hosts.length));
				boolean assigned = false;
				for (String h:hosts) {
					List<Integer> blocks = blockMap.get(h);
					if (blocks==null) continue;
					
					if (blocks.size()<maxBlockNum) {
						blocks.add(blockIdx);
						assigned = true;
						break;
					}
				}
				if (!assigned) {
					for (String s:servers) {
						List<Integer> blocks = blockMap.get(s);
						if (blocks.size()<maxBlockNum) {
							blocks.add(blockIdx);
							assigned=true;
							break;
						}
					}
					assert assigned;
				}
			}
			List<Integer> blocks = blockMap.get(servers[getmyid()]);
			if (blocks.size()==0) return null;
			List<FileSplit> fsplits = new ArrayList<FileSplit>(blocks.size());
                        for (int blockIdx:blocks) {
                        	long offset=blockLocs[blockIdx].getOffset();
                        	long length=blockLocs[blockIdx].getLength();
                        	FileSplit fs = new FileSplit(fstat.getPath(), 
                        				offset, length,
                        				new String[] {servers[getmyid()]});
                        	fsplits.add(fs);
                        }
                        return fsplits;
		}

		int getmyid() {
			SRuntime runtime= SRuntimeWorker.getInst();
			if (runtime == null) return 0;
			WorkerAddrMap addrMap = runtime.getWorkerAddrMap();
			return addrMap.myIndex();
		}
		String[] getWorkers() {
			SRuntime runtime=SRuntimeWorker.getInst();
			if (runtime == null) {
				return new String[] {Host.get()};
			}
			WorkerAddrMap addrMap = runtime.getWorkerAddrMap();
			String[] workers = new String[addrMap.size()];
			for (int i=0; i<addrMap.size(); i++) {
				workers[i] = addrMap.get(i).getHostName();
			}
			return workers;
		}

	
		public void remove() {}
	}
}
