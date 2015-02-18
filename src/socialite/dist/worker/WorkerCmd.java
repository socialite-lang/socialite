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
	
	public IntWritable getWorkerThreadNum();
	public void setWorkerThreadNum(IntWritable n);
	
	public void makeConnections(ArrayWritable otherWorkers);

    public void init(WorkerAddrMapW workerAddrMapW) throws RemoteException;

	public BooleanWritable isStillIdle(IntWritable epochId, IntWritable timestamp);
    public void setEpochDone(IntWritable epochId);

	public void haltEpoch();

	public void addPyFunctions(BytesWritable classFilesBlob, BytesWritable pyfuncs);
	public void addClassFiles(BytesWritable classFilesBlob);
	
	public BooleanWritable addToClassPath(Text _path);
	public void run(EpochW ew);
	public BooleanWritable runQuery(IntWritable queryTid,
						   			Text queryClass,
						   			LongWritable iterId,
						   			ConstsWritable args);
	public void cleanupTableIter(LongWritable id);

	public Writable status();
	public Writable status(IntWritable verbose);
	
	public void runGc();
	public void info();
	
	//public void setVerbose(BooleanWritable verb);
}
