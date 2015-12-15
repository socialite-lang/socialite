package socialite.dist.master;

import org.apache.hadoop.ipc.RemoteException;
import org.apache.hadoop.ipc.VersionedProtocol;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import socialite.util.SociaLiteException;

public interface QueryProtocol extends VersionedProtocol {
	public static final long versionID = 1L;
	
	public void addPyFunctions(BytesWritable classFilesBlob, BytesWritable bytesPyfuncs) throws RemoteException;
	public void addClassFiles(BytesWritable classFilesBlob) throws RemoteException;

	public void run(Text program) throws RemoteException;
	public void run(Text program, Text clientIp, IntWritable port) throws RemoteException;
	public void run(Text prog, Text clientIp, IntWritable port, LongWritable id) throws RemoteException;
	public void cleanupTableIter(LongWritable id);
	
	public BytesWritable status();
	public BytesWritable status(IntWritable verbose);

	public BooleanWritable addToClassPath(Text hdfsPath);
	public BooleanWritable removeFromClassPath(Text hdfsPath);

	// for debugging
	public void runGc();
	public void info();
	void setVerbose(BooleanWritable verbose);
}
