package socialite.engine;

import gnu.trove.map.hash.TIntLongHashMap;
import gnu.trove.set.hash.TIntHashSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.security.UserGroupInformation;
import org.python.core.PyFunction;

import socialite.codegen.Analysis;
import socialite.codegen.CodeGen;
import socialite.codegen.CodeGenMain;
import socialite.codegen.DistCodeGenMain;
import socialite.codegen.Epoch;
import socialite.codegen.EpochW;
import socialite.codegen.TableCodeGen;
import socialite.dist.ErrorRecord;
import socialite.dist.Host;
import socialite.dist.PathTo;
import socialite.dist.client.TupleReqListener;
import socialite.dist.client.TupleSend;
import socialite.dist.master.MasterNode;
import socialite.dist.worker.WorkerCmd;
import socialite.eval.ClassFilesBlob;
import socialite.eval.EvalProgress;
import socialite.functions.PyInterp;
import socialite.parser.Const;
import socialite.parser.MyType;
import socialite.parser.Parser;
import socialite.parser.Predicate;
import socialite.parser.Query;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.resource.DistTableSliceMap;
import socialite.resource.SRuntime;
import socialite.resource.WorkerAddrMap;
import socialite.tables.ConstsWritable;
import socialite.tables.QueryVisitor;
import socialite.util.Loader;
import socialite.util.SociaLiteException;

class TableReservationStation {
	TIntHashSet readTables;
	TIntHashSet writeTables;
	TableReservationStation() {
		readTables = new TIntHashSet(64);
		writeTables = new TIntHashSet(32);
	}
	void _wait() {
		try { wait(); }
		catch (InterruptedException e) {}
	}
	public synchronized void reserve(Set<Table> reads, Set<Table> writes) {
		while (true) {
			if (isWriteAccessed(reads) || isAccessed(writes)) {
				_wait();
			} else { break; }
		}
		addReads(reads);
		addWrites(writes);
	}
	
	public synchronized void release(Set<Table> read, Set<Table> write) {
		for (Table t:read) {
			readTables.remove(t.id());			
		}
		for (Table t:write) {
			writeTables.remove(t.id());			
		}
		notifyAll();		
	}
	public synchronized void addReads(Set<Table> tables) {
		for (Table t:tables) {
			readTables.add(t.id());
		}
	}
	public synchronized void addReads(int[] tables) {
		for (int tid:tables) {
			readTables.add(tid);
		}
	}
	public synchronized void addWrites(Set<Table> tables) {
		for (Table t:tables) {
			writeTables.add(t.id());
		}
	}
	public synchronized void addWrites(int[] tables) {
		for (int tid:tables) {
			writeTables.add(tid);
		}
	}
	
	public synchronized boolean isAccessed(Set<Table> tables) {
		return isWriteAccessed(tables) || isReadAccessed(tables);
	}
	public synchronized boolean isWriteAccessed(Set<Table> tables) {
		for (Table t:tables) {
			if (writeTables.contains(t.id()))
				return true;
		}
		return false;
	}
	public synchronized boolean isReadAccessed(Set<Table> tables) {
		for (Table t:tables) {
			if (readTables.contains(t.id()))
				return true;
		}
		return false;
	}
}

// compiles a give program from the master, and distributes the class files to workers.
public class DistEngine {
	public static final Log L=LogFactory.getLog(DistEngine.class);
	
	Parser parser;
	Config conf;
	SRuntime runtime;
	WorkerAddrMap workerAddrMap;
	Map<InetAddress, WorkerCmd> workerCmdMap;
	TableReservationStation tableReserv;
	
	public DistEngine(Config _conf, WorkerAddrMap _addrMap, 
					    Map<InetAddress, WorkerCmd> _workerCmdMap) {
		runtime = SRuntime.masterRt();
		parser = new Parser(runtime.getTableMap());		
		conf = _conf;
		workerAddrMap = _addrMap;
		workerCmdMap = _workerCmdMap;
		tableReserv = new TableReservationStation();
		
		String dirStr=PathTo.classOutput();
		File classDir = new File(dirStr);
		classDir.mkdirs();
	}
	
	public Parser getParser() { return parser; }	
	
	public void shutdown() {
		Loader.cleanup();		
	}
	
	public CodeGenMain justCompile(String program) {
		return justCompile(program, null);
	}
	static Object codegenLock = new Object();
	CodeGenMain justCompile(String program, QueryVisitor qv) {
		assert workerAddrMap!=null;
		DistCodeGenMain codeGen;
		Analysis an;
		synchronized(codegenLock) {
			parser.parse(program);
			an=new Analysis(parser, conf);
			an.run();
			codeGen = new DistCodeGenMain(conf, an, runtime);		
			codeGen.generate();
		}
		
		for (Rule r:an.getRules()) {
			runtime.getProgress().init(r.id());
		}		
		if (qv!=null) codeGen.generateQuery(qv);
		return codeGen;
	}
	
	public CodeGenMain compile(String program) {
		return compile(program, null);
	}
	public CodeGenMain compile(String program, QueryVisitor qv) {
		CodeGenMain codeGen = justCompile(program, qv);
		
		copyClassFiles(codeGen);
		return codeGen;
	}	
	void copyClassFiles(CodeGenMain codeGen) {
		if (codeGen.getGeneratedClasses().isEmpty()) {
			return;		
		}
		List<String> classNames = new ArrayList<String>();
		for (Class c:codeGen.getGeneratedClasses())
			classNames.add(c.getName());

		Map<String, byte[]> klassBlobs=collectClassFileBlobs(PathTo.classOutput(), classNames);
		transferClassFileBlobs(classNames, klassBlobs);
	}	
	
	Map<String, byte[]> collectClassFileBlobs(String localDir, List<String> klasses) {		
		Map<String, byte[]> klassBlobs = new HashMap<String, byte[]>();
		collectClassFileBlobs(localDir, klasses, klassBlobs);
		return klassBlobs;
	}
	void transferClassFileBlobs(List<String> depSortedClassNames, Map<String, byte[]> klassBlobs) {
		List<String> classFileNames=new ArrayList<String>(klassBlobs.size());
		List<byte[]> classFiles = new ArrayList<byte[]>(klassBlobs.size());		
		for (String k:depSortedClassNames) {
			assert klassBlobs.containsKey(k);
			byte[] v=klassBlobs.get(k);
			classFileNames.add(k);
			classFiles.add(v);
		}
		try {			
			ClassFilesBlob e = new ClassFilesBlob(classFileNames, classFiles);
			Method get = WorkerCmd.class.getMethod("addClassFiles",
													new Class[]{BytesWritable.class});
			Object p[] = new Object[]{ClassFilesBlob.toBytesWritable(e)};
			MasterNode.callWorkers(get, p);
		} catch (Exception e) {
			L.fatal("Cannot copy class files to workers:"+e);
			L.fatal(ExceptionUtils.getStackTrace(e));
		}
	}
	
	/*
	void copyClassFilesToHdfs(List<String> klasses) {
		String localDir = PathTo.classOutput();
		String distDir = PathTo.distClassOutput();
		copyClassFilesFromLocalDir(klasses, localDir, distDir);				
	}
	*/
	
	boolean containsName(List<String> klasses, String name) {
		for (String klass:klasses) {
			if (klass.contains(name)) 
				return true;
		}
		return false;
	}
	void collectClassFileBlobs(String dir, List<String> klasses, Map<String,byte[]> klassBlobs) {
		File local = new File(dir);
		assert local.exists():dir+" does not exists.";
		assert local.isDirectory():dir+" is not a directory.";
		for (File f:local.listFiles()) {
			if (f.isDirectory()) {
				String dirName = f.getName();
				if (!containsName(klasses, dirName)) continue;
				collectClassFileBlobs(f.getAbsolutePath(), klasses, klassBlobs);
			} else if (f.getName().endsWith(".class")) {
				String name = f.getName();
				name = name.substring(0, name.length()-".class".length());
				if (!containsName(klasses, name)) continue;
					
				if (f.length() > Integer.MAX_VALUE) 
					throw new SociaLiteException("File too big:"+name);
				byte[] blob = new byte[(int)f.length()];		
				readFile(f, blob);				
				String fullName = "socialite."+f.getParentFile().getName()+"."+name;
				klassBlobs.put(fullName, blob);
			}
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
		} catch (Exception e) { throw new SociaLiteException(e); }		
	}
	public void run(CodeGenMain codeGen) {
		Set<Table> reads=codeGen.getReads();
		Set<Table> writes=codeGen.getWrites();
		ErrorRecord errorRec=ErrorRecord.getInst();
		try {
			tableReserv.reserve(reads, writes);
			for (Epoch e:codeGen.getEpoch()) {
				errorRec.registerRules(e.getRules(), codeGen);
				run(e);
				if (errorRec.hasError(codeGen)) break;
				errorRec.removeRules(codeGen, e.getRules());
			}
		} finally {
			tableReserv.release(reads, writes);
		}
	}
	
	Map<String, Table> getTableMap() {
		return runtime.getTableMap();
	}
	
	public void run(CodeGenMain codeGen, TupleSend qv) {
		run(codeGen);
		if (qv==null) return;
		
		Query query = codeGen.getQuery();
		if (query==null) {
			qv.finish();
			return;		
		}
		runQuery(codeGen, qv);
	}
	
	public void cleanupTableIter(long id) {
		TupleReqListener listener=getTupleReqListener(conf);
		if (listener.exists(id)) {
			listener.setInvokeFinish(id, true);
			listener.done(id, true);
		}
	}
	volatile static TupleReqListener _tupleListen=null;
	static TupleReqListener getTupleReqListener(Config conf) {
		if (_tupleListen==null) {
			synchronized (DistEngine.class) {
				if (_tupleListen==null) {
					_tupleListen = new TupleReqListener(conf);
				}
			}
		}
		return _tupleListen;
	}
	void runQuery(CodeGenMain codeGen, TupleSend qv) {
		Query query = codeGen.getQuery();
		Class queryClass=codeGen.getQueryClass();
		String queryClassName=queryClass.getName();
		
		TupleReqListener tupleReqListener = getTupleReqListener(conf);
		tupleReqListener.registerQueryVisitor(qv.remoteIterId(), qv);
			
		LongWritable iterId = new LongWritable(qv.remoteIterId());
		Predicate p=query.getP();
		ConstsWritable args = new ConstsWritable(p.getConstValues());
		boolean success;
		if (p.idxParam==null) {
			L.warn("Querying to worker node #0");
			InetAddress worker0 = workerAddrMap.get(0);
			WorkerCmd cmd=workerCmdMap.get(worker0);
							
			Map<String, Table> tableMap = getTableMap();
			Table t = tableMap.get(p.name());
			requestRunQuery(cmd, new IntWritable(t.id()), new Text(queryClassName), iterId, args);
		} else {
			Map<String, Table> tableMap = getTableMap();
			Table t = tableMap.get(p.name());
			if (p.idxParam instanceof Const) {
				Const c = (Const)p.idxParam;
				DistTableSliceMap sliceMap=(DistTableSliceMap)runtime.getSliceMap();
				int workerId = sliceMap.machineIndexFor(t.id(), c.val);
				
				InetAddress workerInetAddr = workerAddrMap.get(workerId);				
				WorkerCmd cmd = workerCmdMap.get(workerInetAddr);
				requestRunQuery(cmd, new IntWritable(t.id()), new Text(queryClassName), iterId, args);
			} else {
				tupleReqListener.setInvokeFinish(iterId.get(), false);
				for (int i=0; i<workerAddrMap.size(); i++) {
					WorkerCmd cmd = workerCmdMap.get(workerAddrMap.get(i));						
					if (i==workerAddrMap.size()-1) 
						tupleReqListener.setInvokeFinish(iterId.get(), true);
					success=requestRunQuery(cmd, new IntWritable(t.id()), new Text(queryClassName), iterId, args);
					if (!success) break;
					if (!tupleReqListener.exists(iterId.get())) break;
				}
			}
		}
	}	
	boolean requestRunQuery(WorkerCmd cmd, 
			IntWritable tid, Text queryClass, LongWritable iterId, ConstsWritable constArgs) {
		try {
			cmd.runQuery(tid, queryClass, iterId, constArgs);
			return true;
		} catch (Throwable t) {
			L.fatal("Error while IPC call to CmdListener.runQuery:"+t);
			L.fatal(ExceptionUtils.getStackTrace(t));
			return false;
		}
	}
	
	void run(Epoch e) {
		if (runtime.isVoid()) return;
		MasterNode.getInstance().prepareEpoch();
		
		//long start=System.currentTimeMillis();
		EpochW ew = new EpochW(e);
		Method run=null;
		try {
			run = WorkerCmd.class.getMethod("run", new Class[]{EpochW.class});
			int workerNum = workerAddrMap.size();
			Object param[] = new Object[]{ew};
			MasterNode.callWorkers(workerAddrMap, run, param);
		} catch (Exception e1) {
			L.fatal("Exception while running WorkerCmd.run():"+e1);
			L.fatal(ExceptionUtils.getStackTrace(e1));
		}
		MasterNode master = MasterNode.getInstance();
		master.waitForEpochDone();
		//long end=System.currentTimeMillis();
		//L.info("Epoch done! (Exec Time:"+(end-start)/1000.0+"sec)");
	}
	
	boolean canIssue() {
		return MasterNode.getInstance().isClusterIdle();
	}	
}

class RuleTimestampMap {
	TIntLongHashMap map;
	public RuleTimestampMap(int _size) {
		map = new TIntLongHashMap(_size);		
	}
	public synchronized long getTs(int ruleid) {
		return map.get(ruleid);
	}
}
