package socialite.engine;

import gnu.trove.map.TIntFloatMap;
import gnu.trove.map.hash.TIntFloatHashMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyException;
import org.python.core.Py;
import org.python.core.PyJavaType;

import socialite.codegen.Analysis;
import socialite.codegen.CodeGenMain;
import socialite.dist.PathTo;
import socialite.dist.Status;
import socialite.eval.Eval;
import socialite.eval.Manager;
import socialite.functions.PyInvoke;
import socialite.parser.GeneratedT;
import socialite.parser.Parser;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.resource.SRuntime;
import socialite.resource.TableInstRegistry;
import socialite.tables.QueryVisitor;
import socialite.tables.TableRefLocal;
import socialite.util.Loader;
import socialite.util.ParseException;
import socialite.util.SociaLiteException;


public class LocalEngine {
    public static final Log L=LogFactory.getLog(LocalEngine.class);

    Parser parser;
    SRuntime runtime;

    public LocalEngine() {
        init();
        Manager.create().setRuntime(runtime);
    }
    void init() {
        parser=new Parser();
        runtime = SRuntime.create();
    }

    public TableRefLocal getTableRef(String name) {
        Table t = runtime.getTableMap().get(name);
        if (t==null)
            return null;
        return new TableRefLocal(t);
    }
    public CodeGenMain compile(String program) {
        CodeGenMain codeGen;
        try {
            synchronized(parser.monitor()) {
                parser.parse(program);
                Analysis an = new Analysis(parser);
                an.run();
                codeGen = new CodeGenMain(an, runtime);
                //long start=System.currentTimeMillis();
                codeGen.generate();
                //System.out.println("generate time:"+(System.currentTimeMillis()-start)+" ms");
            }
        } catch (ParseException pe) {
            throw new SociaLiteException(pe.compileErrorMsg());
        }
        return codeGen;
    }

    public void run(String program) {
        try {
            List<Eval> evals = compile(program).getEvalInsts();
            for (Eval e:evals) {
                e.run();
			   /* if (runtime.getException()!=null) {
			    	throw new SociaLiteException(runtime.getException());
			    }*/
            }
        } catch (Exception e) {
            L.warn("Exception while running program:"+program);
            L.warn("Stack trace:"+ExceptionUtils.getStackTrace(e));
            throw new SociaLiteException(e);
        }
    }

    public void run(String program, final QueryVisitor qv) {
        try {
            CodeGenMain codeGen = compile(program);
            //System.out.println("Code generation:"+(System.currentTimeMillis()-start)+"ms");
            List<Eval> evals = codeGen.getEvalInsts();
            for (Eval e:evals) {
                e.run();
            }
            codeGen.generateQuery(qv);
            Runnable query = codeGen.getQueryInst();
            if (query==null) qv.finish();
            else {
                final Thread t=Thread.currentThread();
                Runtime.getRuntime().addShutdownHook(new Thread(){
                    public void run() {
                        t.interrupt();
                    }
                });
                query.run();
            }
        } catch (Exception e) {
            L.error("Exception while running "+program);
            L.error(ExceptionUtils.getStackTrace(e));
            if (Thread.currentThread().isInterrupted()) {
                return;
            }
            if (e instanceof PyException) {
                PyException pye = ((PyException)e);
                if (pye.type instanceof PyJavaType) {
                    Object error = pye.value.__tojava__(Throwable.class);
                    if (error instanceof InterruptedException) { return; }
                    if (Py.matchException(pye, Py.KeyboardInterrupt)) { return; }
                }
            }
            throw new SociaLiteException(e);
        }
    }

    public void clearTable(String name) {
        TableInstRegistry reg=runtime.getTableRegistry();
        reg.clearTable(name);
    }
    public void dropTable(String name) {
        TableInstRegistry reg=runtime.getTableRegistry();
        if (name.equals("*")) {
            Map<String, Table> tableMap = parser.getTableMap();
            List<String> tableNames = new ArrayList(tableMap.keySet());
            for (String n:tableNames) {
                parser.dropTable(n);
                reg.dropTable(n);
            }
        } else {
            parser.dropTable(name);
            reg.dropTable(name);
        }
    }


    public void shutdown() {
        Manager.shutdownAll();

        File outputDir = new File(PathTo.classOutput());
        cleanup(outputDir);
        Loader.cleanup();
        CodeGenMain.clearCache();
    }
    void cleanup(File outdir) {
        if (!outdir.exists()) return;
        assert outdir.isDirectory();
        for (File f:outdir.listFiles()) {
            if (f.isDirectory()) { cleanup(f); }
            //else if (f.getName().endsWith(".java")) { f.delete(); }
        }
    }

    public Status status() { return status(0); }
    public Status status(int verbose) {
        Status s = new Status();
        s.putNodeNum("1");
        int freeMem=(int)(SRuntime.freeMemory()/1024/1024);
        s.putMemStatus(""+freeMem+"MB");
        TIntFloatMap progressMap = runtime.getProgress();
        int[] rules=progressMap.keys();
        Arrays.sort(rules);
        String evalStat="";
        for (int rule:rules) {
            Rule r=parser.getRuleById(rule);
            int x=(int)(progressMap.get(rule)*100);
            if (x==100) evalStat += r+": Finished\n";
            else if (x<0) evalStat += r+": Finished (error thrown)\n";
            else evalStat += r+":"+x+"%\n";
        }
        s.putProgress(evalStat);
        tableStatus(s);
        return s;
    }
    void tableStatus(Status s) {
        Map<String, Table> tableMap = parser.getTableMap();
        String tableInfo="";
        boolean first=true;
        for (String name:tableMap.keySet()) {
            if (tableMap.get(name) instanceof GeneratedT) continue;
            if (!first) tableInfo += "\n";
            Table t=tableMap.get(name);
            tableInfo += t.decl()+" id="+t.id();
            first=false;
        }
        s.putTableStatus(tableInfo);
    }
    public void update(Object pyfunc) {
        if (pyfunc instanceof PyFunction)
            PyInvoke.update((PyFunction)pyfunc);
    }
}
