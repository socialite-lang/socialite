package socialite.engine;

import gnu.trove.set.hash.TIntHashSet;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.*;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import socialite.codegen.Analysis;
import socialite.codegen.CodeGenMain;
import socialite.codegen.DistCodeGenMain;
import socialite.codegen.Epoch;
import socialite.codegen.EpochW;
import socialite.dist.ErrorRecord;
import socialite.dist.PathTo;
import socialite.dist.client.TupleReqListener;
import socialite.dist.client.TupleSend;
import socialite.dist.master.MasterNode;
import socialite.dist.worker.WorkerCmd;
import socialite.eval.ClassFilesBlob;
import socialite.parser.Const;
import socialite.parser.Parser;
import socialite.parser.Predicate;
import socialite.parser.Query;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.resource.DistTableSliceMap;
import socialite.resource.SRuntime;
import socialite.resource.SRuntimeMaster;
import socialite.resource.WorkerAddrMap;
import socialite.tables.ConstsWritable;
import socialite.tables.QueryVisitor;
import socialite.util.Loader;
import socialite.util.SociaLiteException;

// compiles a give program from the master, and distributes the class files to workers.
public class DistEngine {
	public static final Log L=LogFactory.getLog(DistEngine.class);
	
	Parser parser;
	Config conf;
	SRuntimeMaster runtime;
	WorkerAddrMap workerAddrMap;
	Map<InetAddress, WorkerCmd> workerCmdMap;
		
	public DistEngine(Config _conf, WorkerAddrMap _addrMap, 
					    Map<InetAddress, WorkerCmd> _workerCmdMap) {
		runtime = SRuntimeMaster.getInst();
		parser = new Parser(runtime.getTableMap());		
		conf = _conf;
		workerAddrMap = _addrMap;
		workerCmdMap = _workerCmdMap;
		
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
		if (codeGen.getGeneratedClasses().isEmpty()) { return; }

		transferClassFileBlobs(codeGen.getGeneratedClasses());
	}	

	void transferClassFileBlobs(LinkedHashMap<String, byte[]> classes) {
		List<String> classFileNames=new ArrayList<String>(classes.size());
		List<byte[]> classFiles = new ArrayList<byte[]>(classes.size());
        for (Map.Entry<String, byte[]> entry:classes.entrySet()) {
            classFileNames.add(entry.getKey());
            classFiles.add(entry.getValue());
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

	public void run(CodeGenMain codeGen) {
		Set<Table> reads=codeGen.getReads();
		Set<Table> writes=codeGen.getWrites();
		ErrorRecord errorRec=ErrorRecord.getInst();
		for (Epoch e:codeGen.getEpoch()) {
			errorRec.registerRules(e.getRules(), codeGen);
			run(e);
			if (errorRec.hasError(codeGen)) break;
			errorRec.removeRules(codeGen, e.getRules());
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
		Map<String, Table> tableMap = getTableMap();
		Table t = tableMap.get(p.name());
		if (p.first() instanceof Const) {
			Const c = (Const)p.first();
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
        long s=System.currentTimeMillis();
        EpochW ew = new EpochW(e);
        Method run=null;
        try {
            run = WorkerCmd.class.getMethod("run", new Class[]{EpochW.class});
            Object param[] = new Object[]{ew};
            MasterNode.callWorkersAsync(run, param);
        } catch (Exception e1) {
            L.fatal("Exception while running WorkerCmd.run():"+e1);
            L.fatal(ExceptionUtils.getStackTrace(e1));
        }
        
        long e1=System.currentTimeMillis();
        L.info("WorkerCmd.run() all called:"+(e1-s)+"ms");

        SRuntimeMaster runtime = SRuntimeMaster.getInst();
        try {
            runtime.waitForIdle(e.id());
        } catch (InterruptedException e2) {
            L.info("Running epoch("+e+") interrupted.");
            return;
        }
        long end=System.currentTimeMillis();

        L.info("Epoch done! Exec Time:"+(end-s)+"ms");
    }
}