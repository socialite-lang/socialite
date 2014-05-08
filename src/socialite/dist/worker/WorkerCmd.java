package socialite.dist.worker;


import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.ipc.RemoteException;
import org.apache.hadoop.ipc.VersionedProtocol;

import socialite.codegen.EpochW;
import socialite.resource.WorkerAddrMapW;
import socialite.tables.ConstsWritable;

public interface WorkerCmd extends VersionedProtocol {
	public static final long versionID = 1L;
	
	public IntWritable getCpuNum();
	public void setWorkerNum(IntWritable n);
	
	public BooleanWritable makeConnections(ArrayWritable otherWorkers);
	public BooleanWritable beginSession(Text path, WorkerAddrMapW workerAddrMap, boolean newSession);
	public BooleanWritable storeSession(Text path);
	public BooleanWritable endSession();
	
	public BooleanWritable isStillIdle(IntWritable idleFrom);
	public void epochDone();
	public void haltEpoch();

	public void addPyFunctions(BytesWritable classFilesBlob, BytesWritable pyfuncs);
	public void addClassFiles(BytesWritable classFilesBlob);
	
	public BooleanWritable addToClassPath(Text _path);
	public BooleanWritable run(EpochW ew);
	public BooleanWritable runQuery(IntWritable queryTid,
						   			Text queryClass,
						   			LongWritable iterId,
						   			ConstsWritable args);
	public void cleanupTableIter(LongWritable id);

	public Writable status();
	public Writable status(IntWritable verbose);
	
	public void runGc();
	
	//public void setVerbose(BooleanWritable verb);
}
