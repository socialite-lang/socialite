package socialite.dist.client;


import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RemoteException;
import org.apache.hadoop.ipc.Server;
import org.apache.hadoop.ipc.VersionedProtocol;
import org.apache.commons.lang3.exception.ExceptionUtils;

import socialite.dist.Host;
import socialite.dist.PortMap;
import socialite.engine.Config;
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

class QueryVisitorInfo {
	final QueryVisitor visitor;
	boolean reallyFinish;
	QueryVisitorInfo(QueryVisitor _visitor, boolean _reallyFinish) {
		visitor=_visitor;
		reallyFinish=_reallyFinish;
	}
}
public class TupleReqListener implements TupleReq {
	public static final Log L=LogFactory.getLog(TupleReqListener.class);
	
	ConcurrentHashMap<Long, QueryVisitorInfo> visitorMap = new ConcurrentHashMap<Long, QueryVisitorInfo>();
	Config conf;
	Server server;
	int port=-1;
	String name="unnamed";
	
	public TupleReqListener(Config _conf, int _port) throws java.net.BindException {
		conf=_conf;		
		port = _port;
		start();
	}	
	public TupleReqListener(Config _conf) {
		conf=_conf;
		if (conf.isClient())  port = conf.portMap().tupleReqClientListen();
		else port=conf.portMap().tupleReqListen();
		try { start(); } 
		catch (java.net.BindException e) {
			L.fatal("Cannot bind to port "+port+": "+e);
		}
	}
	
	public void registerQueryVisitor(long id, QueryVisitor qv) {
		visitorMap.put(id, new QueryVisitorInfo(qv, true));
	}
	
	public void start() throws java.net.BindException {
		try {
			Configuration hConf = new Configuration();
			String host=Host.get();
			int numHandlers = 8;
			server=RPC.getServer(this, host, port, numHandlers, false, hConf);
			server.start();
		} catch (java.net.BindException e) {
			throw e;
		} catch (IOException e) {
			L.fatal("Cannot start TupleReq listener:"+e);
			L.fatal(ExceptionUtils.getStackTrace(e));
			throw new SociaLiteException(e);
		}
	}
	
	@Override
	public BooleanWritable consume(LongWritable id, TupleArrayWritable tuples) {
		if (!visitorMap.containsKey(id.get())) return new BooleanWritable(false);
		
		Writable[] tupleW = tuples.get();
		QueryVisitor qv = visitorMap.get(id.get()).visitor;
		try {
			for (Writable w:tupleW) {			
				Tuple t=((TupleW)w).get();
				boolean cont;
				if (t.getClass().equals(Tuple_int.class)) {
					cont=qv.visit(((Tuple_int)t)._0);
				} else if (t.getClass().equals(Tuple_long.class)) {
					cont=qv.visit(((Tuple_long)t)._0);
				} else if (t.getClass().equals(Tuple_float.class)) {
					cont=qv.visit(((Tuple_float)t)._0);
				} else if (t.getClass().equals(Tuple_double.class)) {
					cont=qv.visit(((Tuple_double)t)._0);
				} else if (t.getClass().equals(Tuple_Object.class)) {
					cont=qv.visit(((Tuple_Object)t)._0);
				} else {
					cont=qv.visit(t);
				}
			}
			return new BooleanWritable(true);
		} catch (Throwable t) {
			L.error("Exception while receiving tuples:"+t);
			L.error(ExceptionUtils.getStackTrace(t));
			visitorMap.remove(id.get());
			return new BooleanWritable(false);
		}
	}
	public boolean exists(long id) {
		return visitorMap.containsKey(id);
	}
	public void setInvokeFinish(long id, boolean finish) {
		QueryVisitorInfo info = visitorMap.get(id);
		if (info==null) return;
		info.reallyFinish = finish;
	}
	public void done(long id, boolean force) {
		QueryVisitorInfo info = visitorMap.get(id);
		if (info==null) return;
		
		QueryVisitor qv=info.visitor;
		boolean reallyFinish = info.reallyFinish;		
		if (reallyFinish) {
			qv.finish(force);
			visitorMap.remove(id);			
		}
	}
	public void done(LongWritable id) {
		QueryVisitorInfo info = visitorMap.get(id.get());
		if (info==null) return;
		
		QueryVisitor qv=info.visitor;
		boolean reallyFinish = info.reallyFinish;		
		if (reallyFinish) {			
			qv.finish();
			visitorMap.remove(id.get());
		}
	}
	public void shutdown() {
		if (server!=null) {
			server.stop();
			try { server.join(); }
			catch (InterruptedException e) { }
		}
	}
	@Override
	public long getProtocolVersion(String arg0, long arg1) throws IOException {
		return TupleReq.versionID;
	}
}
