package socialite.dist.worker;

import gnu.trove.map.TIntFloatMap;
import gnu.trove.map.hash.TIntFloatHashMap;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
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
import org.apache.hadoop.ipc.ProtocolSignature;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RemoteException;
import org.apache.hadoop.ipc.VersionedProtocol;
import org.apache.hadoop.net.NetUtils;
import org.python.core.PyFunction;

import socialite.codegen.Epoch;
import socialite.codegen.EpochW;
import socialite.dist.*;
import socialite.dist.client.TupleSend;
import socialite.eval.ClassFilesBlob;
import socialite.eval.Eval;
import socialite.eval.EvalDist;
import socialite.eval.Manager;
import socialite.eval.Worker;
import socialite.functions.PyInterp;
import socialite.functions.PyInvoke;
import socialite.resource.*;
import socialite.tables.ConstsWritable;
import socialite.tables.QueryRunnable;
import socialite.tables.QueryVisitor;
import socialite.eval.TmpTablePool;
import socialite.util.Loader;
import socialite.util.SocialiteFinishEval;


public class CmdListener implements WorkerCmd {
    public static final Log L=LogFactory.getLog(CmdListener.class);

    WorkerNode worker;
    FileSystem hdfs;
    FileSystem localFs;
    ConcurrentHashMap<Long, QueryRunnable> runningQueryMap =
            new ConcurrentHashMap<Long, QueryRunnable>();
    public CmdListener(WorkerNode _worker) {
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
            String host = NetUtils.getHostname().split("/")[1];
            RPC.Server server = new RPC.Builder(new Configuration()).
                    setInstance(this).
                    setProtocol(WorkerCmd.class).
                    setBindAddress(host).
                    setPort(PortMap.worker().usePort("workerCmd")).
                    setNumHandlers(8).
                    build();
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
        TIntFloatMap progressMap;
        if (runtime==null) { progressMap = new TIntFloatHashMap(); }
        else { progressMap = runtime.getProgress();}

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
        workerMap.initMyIndex();
        SRuntimeWorker runtime = SRuntimeWorker.create(workerMap, worker.getConnPool());
        Manager.getInst().setRuntime(runtime);
        if (!worker.isReady()) {
            int tryCnt;
            for (tryCnt=0; tryCnt<20; tryCnt++) {
                try { Thread.sleep(50);}
                catch (InterruptedException e) { }
                if (worker.isReady()) break;
            }
            if (!worker.isReady()) {
                L.error("WorkerNode is not ready.");
                throw new RemoteException("SociaLiteException", "WorkerNode is not ready. See logs.");
            }
        }
    }

    void cleanupLocalWorkSpace(String path) {
        File ws = new File(path);
        assert ws.isDirectory();
        deleteRecur(ws);
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
        String masterAddr = PortMap.worker().masterAddr();
        int tupleReqPort = PortMap.worker().getPort("tupleReq");
        QueryVisitor qv = new TupleSend("CmdListener", masterAddr, tupleReqPort, iterId.get());
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

        qr.kill();
    }

    @Override
    public void makeConnections(ArrayWritable otherWorkers) {
        worker.connect(otherWorkers.toStrings());
    }

    @Override
    public void runGc() {
        L.info("Running System.gc()");
        TmpTablePool.clear();
        System.gc();
        System.gc();
        TmpTablePool.clear();
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
}

