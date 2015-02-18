package socialite.dist.worker;

import gnu.trove.map.hash.TIntFloatHashMap;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RemoteException;
import org.apache.hadoop.ipc.Server;
import org.python.core.PyFunction;

import socialite.codegen.Epoch;
import socialite.codegen.EpochW;
import socialite.dist.*;
import socialite.dist.client.TupleSend;
import socialite.engine.Config;
import socialite.eval.ClassFilesBlob;
import socialite.eval.Eval;
import socialite.eval.EvalDist;
import socialite.eval.Manager;
import socialite.eval.Worker;
import socialite.functions.PyInterp;
import socialite.functions.PyInvoke;
import socialite.parser.Table;
import socialite.resource.*;
import socialite.tables.ConstsWritable;
import socialite.tables.QueryRunnable;
import socialite.tables.QueryVisitor;
import socialite.tables.TableInst;
import socialite.util.*;


public class CmdListener implements WorkerCmd {
	public static final Log L=LogFactory.getLog(CmdListener.class);

	WorkerNode worker;
	int cmdListenPort;	
	FileSystem hdfs;
	FileSystem localFs;	
	ConcurrentHashMap<Long, QueryRunnable> runningQueryMap = 
					new ConcurrentHashMap<Long, QueryRunnable>();
	public CmdListener(WorkerNode _worker) {
		cmdListenPort=PortMap.get().workerCmdListen();
		worker=_worker;
	}

	FileSystem hdfs() {
		if (hdfs==null) {
			try { hdfs = FileSystem.get(new Configuration()); }
			catch (IOException e) {
				L.error("Cannot access HDFS. Check Hadoop configuration:");
				L.error(ExceptionUtils.getStackTrace(e));
			}	
		}
		return hdfs;
	}
	FileSystem localfs() {
		if (localFs==null) {
			try { localFs = FileSystem.getLocal(new Configuration()); }
			catch (IOException e) {
				L.error("Error while calling FileSystem.getLocal():");
				L.error(ExceptionUtils.getStackTrace(e));
			}
		}
		return localFs;
	}
	public void start() {
		try {
			String host=Host.get();			
			int numHandlers = 32;
			Server server = RPC.getServer(this, host, cmdListenPort, numHandlers, false, new Configuration());
			server.start();
		} catch (IOException e) {
			L.fatal("Exception while starting CmdListener");
			L.fatal(ExceptionUtils.getStackTrace(e));
		}
	}
	
	@Override
	public void haltEpoch() {
		Worker.haltEpoch();
	}
	@Override
	public IntWritable getWorkerThreadNum() {
		int cpuNum = Runtime.getRuntime().availableProcessors();
		return new IntWritable(cpuNum);
	}
	@Override
	public void setWorkerThreadNum(IntWritable num) {
        worker.initManagerAndWorkers(Config.dist(num.get()));
	}
	@Override
	public BooleanWritable addToClassPath(Text _path) {
		String path=_path.toString();
		Loader.addClassPath(new File(path));
		return new BooleanWritable(true);
	}
	
	public Writable status() {
		return status(new IntWritable(0));
	}
	public Writable status(IntWritable verbose) {
		Status s=new Status();		
		s.putMemStatus(SRuntime.freeMemory());
		SRuntime runtime = SRuntimeWorker.getInst();
		TIntFloatHashMap progressMap;
		if (runtime==null) { progressMap = new TIntFloatHashMap(); } 
		else { progressMap = runtime.getProgress().get(); }

		s.putProgress(progressMap);
		return Status.toWritable(s);
	}
	
	public void addPyFunctions(BytesWritable bytesClassFilesBlob, BytesWritable bytesPyfuncs) {
		ClassFilesBlob classFilesBlob = ClassFilesBlob.fromBytesWritable(bytesClassFilesBlob);
		Loader.loadFromBytes(classFilesBlob.names(), classFilesBlob.files());
		for (String pyClassName:classFilesBlob.names()) {			
			try {
				Class<?> pyClass=Loader.forName(pyClassName);
				Constructor<?> constr = pyClass.getConstructor(new Class[]{String.class});
				constr.newInstance(new Object[] {"<SociaLite>"});
			} catch (Exception e) {
				L.warn("Failed to make PyCode object:"+e);
			    L.warn(ExceptionUtils.getStackTrace(e));
				continue;
			}
		}		
		
		List<PyFunction> pyfuncs=PyInterp.fromBytesWritable(bytesPyfuncs);
		PyInterp.addFunctions(pyfuncs);
		PyInvoke.update(pyfuncs);
	}
	
	public void addClassFiles(BytesWritable bytesClassFilesBlob) {
		ClassFilesBlob classFilesBlob = ClassFilesBlob.fromBytesWritable(bytesClassFilesBlob);
		Loader.loadFromBytes(classFilesBlob.names(), classFilesBlob.files());
	}	
	
	boolean containsName(List<String> klasses, String name) {
		for (String klass:klasses) {
			if (klass.contains(name)) 
				return true;
		}
		return false;
	}
	void copyClassFilesToLocalDir(List<String> klasses, String hdfsDir, String localDir) {
		Path hdfsPath = new Path(hdfsDir);
		try {
			if (!hdfs().exists(hdfsPath)) return;			
			assert hdfs().getFileStatus(hdfsPath).isDir();
			
			File local = new File(localDir);
			local.mkdirs();
			for (FileStatus fs:hdfs().listStatus(new Path(hdfsDir))) {
				if (fs.isDir()) {
					String dirName = fs.getPath().getName();
					if (!containsName(klasses, dirName)) continue;
					copyClassFilesToLocalDir(klasses, fs.getPath().toString(),
											 PathTo.concat(localDir, fs.getPath().getName()));
				} else if (fs.getPath().getName().endsWith(".class")) {
					String name=fs.getPath().getName();
					name = name.substring(0, name.length()-".class".length());
					if (!containsName(klasses, name)) continue;
					
					Path from=fs.getPath();  Path to=new Path(localDir);
					hdfs().copyToLocalFile(from, to);
				}
			}
		} catch (IOException e) {
			L.error("Error while copying class files to worker local dir:"+e);
			L.warn(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public BooleanWritable loadResource(Text resourcePath) {
		return new BooleanWritable(false);
	}
	
	void deleteRecur(File dir) {
		assert dir.isDirectory();
		for (File f:dir.listFiles()) {
			if (f.isDirectory())
				deleteRecur(f);
			 
			f.delete();			
		}
	}
	void prepareWorkSpace() {
		// prepare workspace in local-fs
		String localDir = PathTo.classOutput();
		
		File local = new File(localDir);
		if (local.exists()) {
			assert local.isDirectory():localDir+" is not a directory";
			deleteRecur(local);
		}	
	}

    @Override  /* MaterNode calls CmdListener.init after all the worker nodes are registered. */
    public synchronized void init(WorkerAddrMapW workerAddrMapW) throws RemoteException {
        WorkerAddrMap workerMap = workerAddrMapW.get();
        SRuntimeWorker runtime = SRuntimeWorker.create(worker.getConf(), workerMap, worker.getConnPool());
        Manager.getInst().setRuntime(runtime);
        if (!worker.isReady()) {
            int tryCnt;
            for (tryCnt=0; tryCnt<20; tryCnt++) {
                try { Thread.sleep(10);}
                catch (InterruptedException e) { }
                if (worker.isReady()) break;
            }
            if (!worker.isReady()) {
                L.error("tryCnt:"+tryCnt);
                throw new RemoteException("SociaLiteException", "WorkerNode is not ready. See logs.");
            }
        }
    }

    /*
	@Override
	public BooleanWritable beginSession(Text _path, WorkerAddrMapW workerAddrMapW, boolean loadSession) {
		String path=_path.toString();
		WorkerAddrMap workerMap = workerAddrMapW.get();
		
		SRuntime.newWorkerRt(worker.getConf(), workerMap, worker.getConnPool());
		
		PathTo.setcwd(path);
		prepareWorkSpace();
		if (loadSession) {
			loadTables();
		}
		return new BooleanWritable(true);
	}
	@Override	
	public BooleanWritable storeSession(Text _path) {
		String path=_path.toString();
		PathTo.setcwd(path);
		storeTables();
		return new BooleanWritable(true);
	}
	@Override	
	public BooleanWritable endSession() {
		Worker.haltEpoch();
		
		SRuntime.voidWorkerRt();
		Loader.cleanup();
		EvalProgress.getInst().clear();
		CodeGenMain.clearCache();		
		return new BooleanWritable(true);
	}*/
	
	void cleanupLocalWorkSpace(String path) {
		File ws = new File(path);
		assert ws.isDirectory();
		deleteRecur(ws);
	}
	
	void loadTables() {
		SRuntime rt=SRuntimeWorker.getInst();		
		try {
			FileSystem fs;
			if (PathTo.cwd().startsWith("hdfs://"))
				fs = FileSystem.get(new Configuration());
			else fs = FileSystem.getLocal(new Configuration());
			String mapFile=PathTo.cwd(TableInstRegistry.tableMapFile());
			FSDataInputStream fsis = fs.open(new Path(mapFile));
			SocialiteInputStream sis = new SocialiteInputStream(fsis);
			
			TableInstRegistry reg=rt.getTableRegistry();
			Map<String, Table> _tableMap=reg.loadTableMap(sis);
			for (Table t:_tableMap.values()) {
				rt.getSliceMap().addTable(t);
			}			
			String myidx = ""+rt.getWorkerAddrMap().myIndex();
			String dataFile=PathTo.cwd("_table_storage", myidx, TableInstRegistry.tableDataFile());
			fsis = fs.open(new Path(dataFile));
			sis = new SocialiteInputStream(fsis);
			reg.loadTableInsts(sis);
		} catch (IOException e) {
			L.error("Error while loading tables:");
			L.warn(ExceptionUtils.getStackTrace(e));
		}
		L.info("Loading tables done");
	}
	void storeTables() {
		SRuntime rc=SRuntimeWorker.getInst();
		try {
			FileSystem fs;
			if (PathTo.cwd().startsWith("hdfs://"))
				fs = FileSystem.get(new Configuration());
			else fs = FileSystem.getLocal(new Configuration());
			String myidx = ""+rc.getWorkerAddrMap().myIndex();
			String dataFile=PathTo.cwd("_table_storage", myidx, TableInstRegistry.tableDataFile());
			FSDataOutputStream fsos = fs.create(new Path(dataFile), true);
			SocialiteOutputStream sos = new SocialiteOutputStream(fsos);
			
			TableInstRegistry reg=rc.getTableRegistry();
			reg.storeTableInsts(sos);
			fs.close();
		} catch (Exception e) {
			L.error("Error while storing tables:");
			L.warn(ExceptionUtils.getStackTrace(e));
		}
	}
	
	void prepare(SRuntime runtime, Epoch e) {
		runtime.updateTableMap(e.getTableMap());
		runtime.update(e);
		Worker.setHalted(false);
	}
	
	void printMemInfo(String prefix) {		
		long used=Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		
		L.info(prefix+" Used Memory:"+used/1024/1024+"M");
	}

	@Override
	public void run(EpochW ew) {
        SRuntime runtime = SRuntimeWorker.getInst();
        Epoch e = ew.get();
		prepare(runtime, e);
		Eval eval=runtime.getEvalInst(e);
		assert eval instanceof EvalDist;
        eval.run();
		return;
	}
	
	@Override
	public BooleanWritable isStillIdle(IntWritable _epochId, IntWritable timestamp) {
        int epochId = _epochId.get();
		int ts = timestamp.get();
        return new BooleanWritable(EvalRefCount.getInst().stillIdle(epochId, ts));
    }
    @Override
    public void setEpochDone(IntWritable epochId) {
    	EvalRefCount.getInst().clear(epochId.get());
    }

	public BooleanWritable runQuery(IntWritable queryTid,
									Text queryClass,
									LongWritable iterId, 
									ConstsWritable args) {
        int tid=queryTid.get();
		String queryClsName = queryClass.toString();

		SRuntime runtime = SRuntimeWorker.getInst();
		Config conf=runtime.getConf();
		String masterIp = conf.portMap().masterAddr();
        int tupleReqPort=conf.portMap().tupleReqListen();
		QueryVisitor qv = new TupleSend("CmdListener", masterIp, tupleReqPort, iterId.get());
		QueryRunnable query=runtime.getQueryInst(tid, queryClsName, qv);
		query.setArgs(args.get());
		runningQueryMap.put(iterId.get(), query);
		try { query.run(); }
		catch (SocialiteFinishEval e) {
			runningQueryMap.remove(iterId.get());
		}
		return new BooleanWritable(true);
	}	
	@Override
	public void cleanupTableIter(LongWritable id) {
		QueryRunnable qr=runningQueryMap.get(id.get());
		if (qr==null) return;
		
		qr.setTerminated();
	}
	
	@Override
	public void makeConnections(ArrayWritable otherWorkers) {
		String[] restWorkerAddrs=otherWorkers.toStrings();
		worker.connect(restWorkerAddrs);
		L.info(restWorkerAddrs.length+"'th worker registered to MasterNode");
	}
	
	@Override
	public void runGc() {
		L.info("Running System.gc()");
		System.gc();
		System.gc();
		
		printMemInfo("After System.gc():");
	}
	
	@Override
	public void info() {
		SRuntimeWorker runtime = SRuntimeWorker.getInst();
	}
	
	@Override
	public long getProtocolVersion(String arg0, long arg1) throws IOException {
		return WorkerCmd.versionID;
	}	
}

