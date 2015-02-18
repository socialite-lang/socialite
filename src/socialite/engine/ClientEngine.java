package socialite.engine;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RemoteException;
import org.apache.hadoop.security.UserGroupInformation;
import org.python.core.PyFunction;
import org.python.core.PyTableCode;

import socialite.dist.Host;
import socialite.dist.PathTo;
import socialite.dist.Status;
import socialite.dist.client.TupleReqListener;
import socialite.dist.master.QueryProtocol;
import socialite.eval.ClassFilesBlob;
import socialite.functions.PyInterp;
import socialite.functions.returns;
import socialite.parser.Parser;
import socialite.tables.QueryVisitor;
import socialite.util.SociaLiteException;


public class ClientEngine {
	public static final Log L=LogFactory.getLog(ClientEngine.class);
	
	public Config conf;
	QueryClient client;

	public ClientEngine() {
		this(Config.client());
	}
	
	public ClientEngine(Config _conf) {
		conf = _conf;
		client = new QueryClient(conf);	
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				client.shutdown();
			}
		});
	}

	public Status status() { return status(0); }
	public Status status(int verbose) {
		return client.status(verbose);
	}
	public void run(String program) {
		try { client.run(program); }
		catch (RemoteException e) {
			throw new SociaLiteException(e.getMessage());
		}
	}

	public void run(String program, QueryVisitor qv, int id) {
		try { client.run(program, qv, id); }
		catch (RemoteException e) {
			throw new SociaLiteException(e.getMessage());
		}
	}
	public void cleanupTableIter(int id) {
		client.cleanupTableIter(id);
	}
	public void update(PyFunction f) {}
	
	public void runGc() {
		client.runGc();
	}	
	public void info() {
		client.info();
	}	
	public void shutdown() {	
		client.shutdown();
	}
	public void setVerbose() {	
		client.setVerbose();
	}
}

class QueryClient {
	public static final Log L=LogFactory.getLog(ClientEngine.class);
	
	static long machineId;
	static {
		byte[] addr;
		try { addr = InetAddress.getByName(Host.get()).getAddress(); }
		catch (UnknownHostException e) {
			throw new SociaLiteException("Cannot determine host address:"+e);
		}
		machineId = 0;
		for (int i=0; i<addr.length; i++) {
			machineId <<= 8;
			machineId |= addr[i] & 0xff;
		}
		machineId = machineId << 10;
	}
	
	Config conf;
	String masterAddr;
	int queryPort;
	Socket sock;
	QueryProtocol proto;
	int tupleReqListenPort;
	TupleReqListener listener;
	volatile boolean shutdown=false;
	
	public QueryClient(Config _conf) {
		conf = _conf;
		masterAddr = conf.portMap().masterAddr();
		queryPort = conf.portMap().queryListen();
		
		Configuration hConf=new Configuration();
		InetSocketAddress addr = new InetSocketAddress(masterAddr, queryPort);
		try {
			proto = (QueryProtocol)RPC.waitForProxy(QueryProtocol.class,
													   QueryProtocol.versionID, 
													   addr, hConf);
		} catch (IOException e) {
			L.error("Cannot connect to master:"+e);
		}
		boolean success=false;
		tupleReqListenPort = conf.portMap().tupleReqClientListen();
		do {
			try {
				listener = new TupleReqListener(conf, tupleReqListenPort);
				success = true;
			} catch (java.net.BindException e) {
				tupleReqListenPort++;
			}
		} while (!success);
	}

	Map<String, PyFunction> pyfuncMap = new HashMap<String, PyFunction>(512);
	synchronized void maybeCopyPyFunctions() throws RemoteException {
        if (true) return;

		// XXX: this should be fixed to use BytecodeNotification in Jython when it is added.
		
		File pydir=new File(PathTo.pythonOutput());
		assert pydir.isDirectory();		
		Map<String, byte[]> klassBlobs = new HashMap<String, byte[]>();		
		for (File f:pydir.listFiles()) {			
		    
			if (f.getName().endsWith(".class")) {				
				String name=f.getName();
				name = name.substring(0,name.length()-".class".length());
				String fullName="org.python.pycode."+name;
				byte[] blob = new byte[(int)f.length()];
				readFile(f, blob);
				f.delete();
				klassBlobs.put(fullName, blob);
			}				
		}
		if (klassBlobs.size()==0) return;
			
		List<String> classFileNames=new ArrayList<String>(klassBlobs.size());
		List<byte[]> classFiles = new ArrayList<byte[]>(klassBlobs.size());		
		for (String k:klassBlobs.keySet()) {
			classFileNames.add(k);
			classFiles.add(klassBlobs.get(k));
		}
		
		List<PyFunction> funcs=PyInterp.getFunctions();
		Iterator<PyFunction> iter=funcs.iterator();
		while (iter.hasNext()) {
			PyFunction pyf=iter.next();
			if (pyf.equals(pyfuncMap.get(pyf.__name__)))
				iter.remove();					
		}
		
		ClassFilesBlob b = new ClassFilesBlob(classFileNames, classFiles);
		proto.addPyFunctions(ClassFilesBlob.toBytesWritable(b), PyInterp.toBytesWritable(funcs));
		
		for (PyFunction pyf:funcs) {
			pyfuncMap.put(pyf.__name__, pyf);
		}
	}
	void readFile(File f, byte[] blob) {
		try {
			FileInputStream fis = new FileInputStream(f);
			int byteRead=0, len=blob.length;
			while (byteRead < len) {
				int r=fis.read(blob, byteRead, len-byteRead);
				if (r<0) throw new SociaLiteException("Unexpected EOF:"+f.getName());
				byteRead += r;
			}
			fis.close();
		} catch (Exception e) { throw new SociaLiteException(e); } 	
	}
	public void run(final String program) throws RemoteException {
		maybeCopyPyFunctions();
		proto.run(new Text(program));
	}
		
	public void run(final String program, QueryVisitor qv, int _id) throws RemoteException {
		maybeCopyPyFunctions();
		long longid = machineId + _id;
		
		listener.registerQueryVisitor(longid, qv);
		IntWritable port=new IntWritable(tupleReqListenPort);
		LongWritable id = new LongWritable(longid);
		proto.run(new Text(program), new Text(Host.get()), port, id);
	}
	
	public Status status(int verbose) {
		if (proto==null) return null;
		
		BytesWritable statW = proto.status(new IntWritable(verbose));
		if (statW==null) return null;
		
		return Status.fromWritable(statW);
	}	
	public void cleanupTableIter(int _id) {
		long longid = machineId + _id;
		proto.cleanupTableIter(new LongWritable(longid)); 
	}
	
	public void runGc() { proto.runGc(); }	
	public void info() { proto.info(); }	

	public void setVerbose() { proto.setVerbose(new BooleanWritable(true)); }
	
	public void shutdown() {
		if (shutdown) return;
		
		shutdown=true;

		listener.shutdown();

		RPC.stopProxy(proto);
		File pydir = new File(PathTo.pythonOutput());
		for (File f:pydir.listFiles()) {
			f.delete();
		}
	}	
}
