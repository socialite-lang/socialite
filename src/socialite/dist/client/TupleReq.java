package socialite.dist.client;


import java.io.IOException;

import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.ipc.RemoteException;
import org.apache.hadoop.ipc.VersionedProtocol;

import socialite.tables.TupleArrayWritable;

public interface TupleReq extends VersionedProtocol {
	public static final long versionID = 1L;
	
	public BooleanWritable consume(LongWritable id, TupleArrayWritable tuples);
	public void done(LongWritable id);
}
