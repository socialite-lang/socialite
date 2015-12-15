package socialite.dist.master;

import gnu.trove.map.hash.TIntFloatHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.ipc.ProtocolSignature;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RemoteException;
import org.apache.hadoop.ipc.VersionedProtocol;
import org.python.core.PyFunction;

import socialite.codegen.CodeGenMain;
import socialite.dist.ErrorRecord;
import socialite.dist.PathTo;
import socialite.dist.PortMap;
import socialite.dist.Status;
import socialite.dist.client.TupleSend;
import socialite.dist.worker.WorkerCmd;
import socialite.engine.DistEngine;
import socialite.eval.ClassFilesBlob;
import socialite.functions.PyInterp;
import socialite.functions.PyInvoke;
import socialite.parser.GeneratedT;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.parser.Parser;
import socialite.resource.SRuntime;
import socialite.resource.SRuntimeMaster;
import socialite.resource.TableInstRegistry;
import socialite.resource.WorkerAddrMap;
import socialite.util.Loader;
import socialite.util.SocialiteInputStream;
import socialite.util.SocialiteOutputStream;
import socialite.util.UnresolvedSocketAddr;
import socialite.yarn.ClusterConf;

public class QueryListener implements QueryProtocol {
	public static final Log L = LogFactory.getLog(QueryListener.class);

	MasterNode master;
	DistEngine distEngine;

	public QueryListener(MasterNode _master) {
		master = _master;

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() { cleanupGeneratedCode();}
        });
	}

	public void start() {
		try {
            RPC.Server server = new RPC.Builder(new Configuration()).
                    setInstance(this).
                    setProtocol(QueryProtocol.class).
                    setBindAddress(PortMap.master().masterAddr()).
                    setPort(PortMap.master().usePort("query")).
                    setNumHandlers(16).
                    build();
			server.start();
		} catch (IOException e) {
			L.fatal("Exception while starting QueryListener:");
			L.fatal(ExceptionUtils.getStackTrace(e));
		}
	}

	String localExtLibPath() {
		return PathTo.output("ext");
	}

	String localExtLibPath(String file) {
		return PathTo.concat(localExtLibPath(), file);
	}

	@Override
	public BooleanWritable addToClassPath(Text hdfsPathText) {
		String filePath = hdfsPathText.toString();
		String file = PathTo.basename(filePath);
		try {
			Configuration hConf = new Configuration();
			FileSystem hdfs = FileSystem.get(hConf);
			FileSystem localFs = FileSystem.getLocal(hConf);

			Path hdfsPath = new Path(filePath);
			Path localPath = new Path(localExtLibPath());

			FileUtil.copy(hdfs, hdfsPath, localFs, localPath, false, true,
					hConf);

			String localFilePath = localExtLibPath(file);
			Loader.addClassPath(new File(localFilePath));
			return new BooleanWritable(true);
		} catch (IOException e) {
			L.error("Error while adding class path:" + e);
			return new BooleanWritable(false);
		}
	}

	@Override
	public BooleanWritable removeFromClassPath(Text _file) {
		String file = PathTo.basename(_file.toString());
		try {
			URL url = new File(localExtLibPath(file.toString())).toURI().toURL();
			Loader.removeClassPath(url);
			return new BooleanWritable(true);
		} catch (MalformedURLException e) {
			L.error("Malformed path[" + file + "]:" + e);
			return new BooleanWritable(false);
		}
	}

	void deleteRecur(File dir) {
		if (!dir.exists()) return;
		assert dir.isDirectory();
		for (File f : dir.listFiles()) {
			if (f.isDirectory())
				deleteRecur(f);
			f.delete();
		}
	}
	void prepareWorkSpace() {
		// clean-up workspace in master's local-fs
		String rootDir = PathTo.concat(PathTo.classOutput(), "socialite");
		if (new File(rootDir).listFiles() != null) {
			for (File f : new File(rootDir).listFiles()) {
				if (f.isDirectory())
					deleteRecur(f);
			}
		}		
	}

    void cleanupGeneratedCode() {
        deleteRecur(new File(PathTo.classOutput()));
    }

	/*void cleanupLocalWorkSpace(String path) {
		File ws = new File(path);
		deleteRecur(ws);
	}*/
	@Override
	public void cleanupTableIter(LongWritable id) {
		try {
			Method cleanup = WorkerCmd.class.getMethod("cleanupTableIter", new Class[] {LongWritable.class});
			Object param[] = new Object[]{id};			
			MasterNode.callWorkers(cleanup, param);
			getEngine().cleanupTableIter(id.get());
		} catch (Exception e) {
			L.fatal("Exception while calling WorkerCmd.cleanupTableIter():" + e);
		}
	}

	void printMemInfo(String prefix) {
		long used = Runtime.getRuntime().totalMemory() -
						Runtime.getRuntime().freeMemory();
		L.info(prefix + " Used Memory:" + used / 1024 / 1024 + "M");
	}

	void shutdownEngine() {
		if (distEngine!=null) {
			distEngine.shutdown();	
			distEngine = null;
		}
	}

    synchronized void init() {
        WorkerAddrMap workerAddrMap = master.makeWorkerAddrMap();
        SRuntimeMaster runtime = SRuntimeMaster.create(workerAddrMap);
        WorkerAddrMap addrMap = runtime.getWorkerAddrMap();
        Map<UnresolvedSocketAddr, WorkerCmd> workerMap = master.getWorkerCmdMap();

        distEngine = new DistEngine(addrMap, workerMap);
    }

	synchronized DistEngine getEngine() { return distEngine; }
	
	void runReally(Text prog, TupleSend qv) throws RemoteException {
		DistEngine en = null;
		CodeGenMain codeMain=null;
		synchronized(this) {
			String program = prog.toString();
			en = getEngine();		

			try { codeMain = en.compile(program, qv); }
			catch (Exception e) {			
			    L.error("Error while compiling:");
			    L.error(ExceptionUtils.getStackTrace(e));
				throw new RemoteException("SociaLiteException", e.getMessage());
			}
		}
		String err=null;
        long s=System.currentTimeMillis();
		try {
			en.run(codeMain, qv);
            if (err==null) err = ErrorRecord.getInst().getErrorMsg(codeMain);
            if (err!=null) {
                L.warn("QueryListener::runReally: error while running:"+prog+", err="+err);
            }
			ErrorRecord.getInst().clearError(codeMain);			
		} catch (Exception e) {			
			L.error("Error while running query ("+prog+"):");
			L.error(ExceptionUtils.getStackTrace(e));
			throw new RemoteException("SociaLiteException", e.getMessage());
		}
        long e=System.currentTimeMillis();
        L.info("Exec time:"+(e-s)+"ms for "+prog);
		if (err!=null) throw new RemoteException("SociaLiteException", err);
	}
	@Override
	public void run(Text prog) throws RemoteException {
		runReally(prog, (TupleSend)null);
	}
	@Override
	public void run(Text prog, Text clientIp, IntWritable port) throws RemoteException {
		TupleSend queryVisitor = new TupleSend("QueryListener", clientIp.toString(), port.get());
		runReally(prog, queryVisitor);
	}
	@Override
	public void run(Text prog, Text clientIp, IntWritable port, LongWritable id) throws RemoteException {
		TupleSend queryVisitor= new TupleSend("QueryListener", clientIp.toString(), port.get(), id.get());
		runReally(prog, queryVisitor);
	}
	
	@Override
	public long getProtocolVersion(String arg0, long arg1) throws IOException {
		return QueryProtocol.versionID;
	}

    @Override
    public ProtocolSignature getProtocolSignature(String protocol, long clientVersion, int clientMethodsHash)
            throws IOException {
        Class<? extends VersionedProtocol> inter;
        try {
            inter = (Class<? extends VersionedProtocol>)getClass().getGenericInterfaces()[0];
        } catch (Exception e) {
            throw new IOException(e);
        }
        return ProtocolSignature.getProtocolSignature(clientMethodsHash,
                getProtocolVersion(protocol, clientVersion), inter);
    }

    public void addPyFunctions(BytesWritable bytesClassFilesBlob, BytesWritable bytesPyfuncs) throws RemoteException {
		ClassFilesBlob classFilesBlob = ClassFilesBlob.fromBytesWritable(bytesClassFilesBlob);
		Loader.loadFromBytes(classFilesBlob.names(), classFilesBlob.files());
		for (String pyClassName:classFilesBlob.names()) {			
			try {
				Class<?> pyClass=Loader.forName(pyClassName);
				Constructor<?> constr = pyClass.getConstructor(new Class[]{String.class});
				constr.newInstance(new Object[] {"<SociaLite>"});
			} catch (Exception e) {
				L.warn("Failed to make PyCode object:"+e);
				continue;
			}
		}		
		List<PyFunction> pyfuncs=PyInterp.fromBytesWritable(bytesPyfuncs);
		PyInterp.addFunctions(pyfuncs);
		PyInvoke.update(pyfuncs);
		try {
			Method get = WorkerCmd.class.getMethod("addPyFunctions",
										new Class[]{BytesWritable.class, BytesWritable.class});
			Object p[] = new Object[]{bytesClassFilesBlob, bytesPyfuncs};
			MasterNode.callWorkers(get, p);
		} catch (Exception e) {
			throw new RemoteException("SociaLiteException", e.getMessage());
		}		
	}
	public void addClassFiles(BytesWritable bytesClassFilesBlob) throws RemoteException {
		ClassFilesBlob classFilesBlob = ClassFilesBlob.fromBytesWritable(bytesClassFilesBlob);
		Loader.loadFromBytes(classFilesBlob.names(), classFilesBlob.files());		
		try {
			Method add = WorkerCmd.class.getMethod("addClassFiles",
													new Class[]{BytesWritable.class});
			Object p[] = new Object[]{bytesClassFilesBlob};
			MasterNode.callWorkers(add, p);
		} catch (Exception e) {
			throw new RemoteException("SociaLiteException", e.getMessage());
		}
	}	

	public BytesWritable status() {
		return status(new IntWritable(0));
	}
	public BytesWritable status(IntWritable _verbose) {
		int verbose = _verbose.get();
		Status summary=new Status();

		Object[] _workerStats;
		summary.putNodeNum("" + ClusterConf.get().getNumWorkers());
		try {
			Method status = WorkerCmd.class.getMethod("status",new Class[]{IntWritable.class});
			_workerStats=MasterNode.callWorkers(status, new Object[]{_verbose});
		} catch (Exception e) {
			L.error("Exception while getting status from workers:");
			L.error(ExceptionUtils.getStackTrace(e));
			return Status.toWritable(summary);
		}
		
		Status[] workerStats=new Status[_workerStats.length];
		for (int i=0; i<_workerStats.length; i++) {
			Status s=(Status)Status.fromWritable((BytesWritable)_workerStats[i]);
			workerStats[i] = s;
        }
        WorkerAddrMap workerAddrMap = SRuntimeMaster.getInst().getWorkerAddrMap();
		memStatus(summary, workerAddrMap, workerStats, verbose);
		tableStatus(summary);
		progressStatus(summary, workerAddrMap, workerStats, verbose);
		
		return Status.toWritable(summary);
	}
	void tableStatus(Status summary) {
		DistEngine engine = getEngine();
		Parser p = getEngine().getParser();
		Map<String, Table> tableMap = p.getTableMap();
		String tableInfo="";
		boolean first=true;
		for (String name:tableMap.keySet()) {
            if (tableMap.get(name) instanceof GeneratedT) continue;
			if (!first) tableInfo += "\n";
            Table t=tableMap.get(name);
			tableInfo += t.decl()+" id="+t.id();
			first=false;
		}
		summary.putTableStatus(tableInfo);
	}
	void memStatus(Status summary, WorkerAddrMap workerAddrMap, Status[] workerStats, int verbose) {
		int minFreeMB=Integer.MAX_VALUE, sumFreeMB=0;
		String minFreeMemInfo="";
		String allMemStat="";
		for (int i=0; i<workerStats.length; i++) {
			Status s=workerStats[i];
			int freeMB=(int)((Long)s.getMemStatus()/1024.0/1024.0);						
			String freeMemInfo = workerAddrMap.get(i).getHostName()+":"+ freeMB+"MB\n";
			allMemStat += freeMemInfo;						
			if (freeMB < minFreeMB) {
				minFreeMB = freeMB;
				minFreeMemInfo = freeMemInfo;
			}
			sumFreeMB += freeMB;
		}		
		if (verbose==0) {
			String memStat="Min free memory: " + minFreeMemInfo;
			memStat += "Average free memory: "+ (sumFreeMB/workerStats.length)+"MB\n";
			summary.putMemStatus(memStat);
		} else { summary.putMemStatus(allMemStat); }
	}
	void progressStatus(Status summary, WorkerAddrMap workerAddrMap, Status[] workerStats, int verbose) {
		if (workerStats.length==0) return;

		TIntFloatHashMap progStats[] = new TIntFloatHashMap[workerStats.length];
		for (int i=0; i<workerStats.length; i++)
			progStats[i] = (TIntFloatHashMap )workerStats[i].getProgress();
		String allEvalStat="", aggrEvalStat="";
		int[] rules=progStats[0].keys();
		Arrays.sort(rules);
	next_rule:
		for (int rule:rules) {
			String ruleEvalStat="";
			Rule r=getEngine().getParser().getRuleById(rule);
			ruleEvalStat += r+"\n";
			int minEval=Integer.MAX_VALUE, maxEval=Integer.MIN_VALUE;
			String minEvalInfo="", maxEvalInfo="";
			for (int i=0; i<progStats.length; i++) {
				if (!progStats[i].contains(rule)) continue next_rule;
					
				int x=(int)(progStats[i].get(rule)*100);
				String evalInfo = workerAddrMap.get(i).getHostName()+":"+x+"%\n";
				ruleEvalStat += "  "+evalInfo;				
				if (x <= minEval) {
					minEval = x;
					minEvalInfo = "  Min progress: "+evalInfo;
				}
                if (x >= maxEval) {
                    maxEval = x;
                    maxEvalInfo = "  Max progress: "+evalInfo;
                }
			}
			if (minEval==100) {
				allEvalStat += r+": Finished\n";
				aggrEvalStat += r+": Finished\n";
			} else if (minEval<0) {
				allEvalStat += r+": Finished (error thrown)\n";
				aggrEvalStat += r+": Finished (error thrown)\n";
			} else {
				allEvalStat += ruleEvalStat;
				aggrEvalStat += r+"\n"+minEvalInfo;
                aggrEvalStat += maxEvalInfo;
            }
		}
		if (verbose==0) summary.putProgress(aggrEvalStat);
		else summary.putProgress(allEvalStat);
	}
	
	@Override
	public void runGc() {
		Method runGc = null;
		try {
			runGc = WorkerCmd.class.getMethod("runGc", new Class[] {});
            MasterNode.callWorkers(runGc, new Object[0]);
		} catch (Exception e) {
			L.error("Exception while calling WorkerCmd.runGc():" + e);
		}
		System.gc();

		// printMemInfo("MasterNode, After System.gc():");
	}

	@Override
	public void setVerbose(BooleanWritable verbose) {

		try {
            Method setVerbose = WorkerCmd.class.getMethod("setVerbose",
			    		                new Class[] { BooleanWritable.class });
            Object param[] = new Object[0];
            MasterNode.callWorkers(setVerbose, param);
		} catch (Exception e) {
			L.error("Exception while calling WorkerCmd.setVerbose():" + e);
		}
	}

	@Override
	public void info() {
		try {
            Method info = WorkerCmd.class.getMethod("info", new Class[] {});
            Object param[] = new Object[0];
            MasterNode.callWorkers(info, param);
		} catch (Exception e) {
			L.error("Exception while calling WorkerCmd.info():" + e);
		}
	}
}

