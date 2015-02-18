package socialite.dist.master;

import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.ipc.VersionedProtocol;

public interface WorkerRequest extends VersionedProtocol {
	public static final long versionID = 1L;
	
	public void register(Text selfIp);
	public void handleError(IntWritable workerid, IntWritable ruleid, Text errorMsg);
	public void reportIdle(IntWritable epochId, IntWritable workerId, IntWritable time);
}
