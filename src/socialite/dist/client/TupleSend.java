package socialite.dist.client;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RemoteException;
import org.apache.commons.lang3.exception.ExceptionUtils;

import socialite.tables.QueryVisitor;
import socialite.tables.Tuple;
import socialite.tables.TupleArrayWritable;
import socialite.tables.TupleW;
import socialite.tables.Tuple_Object;
import socialite.tables.Tuple_double;
import socialite.tables.Tuple_float;
import socialite.tables.Tuple_int;
import socialite.tables.Tuple_long;
import socialite.util.SociaLiteException;
import socialite.util.SocialiteFinishEval;


public 	class TupleSend extends QueryVisitor {
	public static final Log L=LogFactory.getLog(TupleSend.class);
	
	TupleArrayWritable tuplesW = new TupleArrayWritable();
	Tuple tuples[] = new Tuple[1024*16];
	int tupleNum=0;
	TupleReq tupleReq;
	String remoteIp;
	int remotePort;
	long remoteIterId;
	String name="unnamed";
	public TupleSend(String _name, String reqIP, int port, long id) {
		this(reqIP, port, id);
		name = _name;
	}
	public TupleSend(String _name, String reqIP, int port) {
		this(reqIP, port, 0);
		name = _name;
	}
	public TupleSend(String reqIP, int port, long id) {
		remoteIp = reqIP; 
		remotePort = port;
		remoteIterId = id;
		InetSocketAddress addr = new InetSocketAddress(reqIP, port);
		
		Configuration hConf=new Configuration();
		try {
			tupleReq = (TupleReq)RPC.waitForProxy(TupleReq.class, TupleReq.versionID, addr, hConf);
		} catch (IOException e) {
			L.fatal("Cannot connect to ["+addr+"] for table iterator:"+e);
			L.fatal(ExceptionUtils.getStackTrace(e));
			throw new SociaLiteException(e);
		}
	}
	public long remoteIterId() { return remoteIterId; }
	public boolean visit(int i) {
		if (tupleNum==tuples.length) send();
		if (tuples[tupleNum]!=null && tuples[tupleNum].getClass().equals(Tuple_int.class)) {
			((Tuple_int)tuples[tupleNum])._0 = i;
		} else {			
			tuples[tupleNum] = new Tuple_int(i);
		}
		tupleNum++;		
		return true;
	}
	public boolean visit(long l) {
		if (tupleNum==tuples.length) send();		
		if (tuples[tupleNum]!=null && tuples[tupleNum].getClass().equals(Tuple_long.class)) {
			((Tuple_long)tuples[tupleNum])._0 = l;
		} else {			
			tuples[tupleNum] = new Tuple_long(l);
		}
		tupleNum++;
		return true;
	}
	public boolean visit(float f) {
		if (tupleNum==tuples.length) send();		
		if (tuples[tupleNum]!=null && tuples[tupleNum].getClass().equals(Tuple_float.class)) {
			((Tuple_float)tuples[tupleNum])._0 = f;
		} else {			
			tuples[tupleNum] = new Tuple_float(f);
		}
		tupleNum++;		
		return true;
	}
	public boolean visit(double d) {
		if (tupleNum==tuples.length) send();		
		if (tuples[tupleNum]!=null && tuples[tupleNum].getClass().equals(Tuple_double.class)) {
			((Tuple_double)tuples[tupleNum])._0 = d;
		} else {			
			tuples[tupleNum] = new Tuple_double(d);
		}
		tupleNum++;		
		return true;
	}
	public boolean visit(Object o) {
		if (tupleNum==tuples.length) send();
		if (tuples[tupleNum]!=null && tuples[tupleNum].getClass().equals(Tuple_Object.class)) {
			((Tuple_Object)tuples[tupleNum])._0 = o;
		} else {			
			tuples[tupleNum] = new Tuple_Object(o);
		}
		tupleNum++;
		return true;
	}
	public boolean visit(Tuple t) {
		if (tupleNum==tuples.length)
			send();
		if (tuples[tupleNum]!=null && tuples[tupleNum].getClass().equals(t.getClass())) {
			tuples[tupleNum].update(t);
		} else {
			tuples[tupleNum] = t.clone();
		}
		tupleNum++;
		return true;
	}	
	void send() {
		TupleArrayWritable taw = new TupleArrayWritable();
		TupleW[] tuplesW = new TupleW[tupleNum];
		for (int i=0; i<tupleNum; i++) {
			tuplesW[i] = new TupleW(tuples[i]);
		}
		taw.set(tuplesW);
		boolean cont=tupleReq.consume(new LongWritable(remoteIterId), taw).get();
		if (!cont) throw new SocialiteFinishEval();
			
		tupleNum=0;		
	}
	public void finish(boolean force) {
		if (!force) finish();
		tupleReq.done(new LongWritable(remoteIterId));
	}
	public void finish() {
		if (tupleNum>0)	{
			send();
		}
		tupleReq.done(new LongWritable(remoteIterId));
	}
	
	public String toString() {
		return "TupleSend("+remoteIp+", "+remotePort+")";
	}
}
