package socialite.dist.master;

import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.ipc.VersionedProtocol;

public interface WorkerRequest extends VersionedProtocol {
	public static final long versionID = 1L;
	
	public BooleanWritable register(Text selfIp);
	
	public void handleError(IntWritable workerid, IntWritable ruleid, Text errorMsg);
	public void haltEpoch(IntWritable workerid);
	public void reportIdle(IntWritable workerId, IntWritable time);
}
