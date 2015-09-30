package socialite.dist.master;

import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.ipc.VersionedProtocol;

public interface WorkerRequest extends VersionedProtocol {
	public static final long versionID = 1L;

	void register(String workerAddr, int cmdPort, int dataPort);
	void handleError(IntWritable workerid, IntWritable ruleid, Text errorMsg);
	void reportIdle(IntWritable epochId, IntWritable workerId, IntWritable time);
}
