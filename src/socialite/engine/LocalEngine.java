package socialite.engine;

import gnu.trove.map.hash.TIntFloatHashMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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
	Config conf;
	SRuntime runtime;

    public LocalEngine() { this(Config.par()); }
	public LocalEngine(Config _conf) {		
		conf = _conf;		
		init();
		Manager.getInst(conf).setRuntime(runtime);
	}
	void init() {
		parser=new Parser();
		runtime = SRuntime.create(conf);
	}

    public TableRefLocal getTableRef(String name) {
        Table t = runtime.getTableMap().get(name);
        if (t==null)
            return null;
        return new TableRefLocal(t);
    }
	public Config getConf() { return conf; }
	
	public CodeGenMain compile(String program) {
		CodeGenMain codeGen;
		try {
			synchronized(parser.monitor()) {
				parser.parse(program);				
				Analysis an = new Analysis(parser, conf);				
				an.run();
				codeGen = new CodeGenMain(conf, an, runtime);
				//long start=System.currentTimeMillis();
				codeGen.generate();
				//System.out.println("generate time:"+(System.currentTimeMillis()-start)+" ms");
			}
		} catch (ParseException pe) {
			throw new SociaLiteException(pe.compileErrorMsg());
		}		
		return codeGen;
	}
	
	static class TimesUp extends Thread {
        /*
		Manager m;
		long limit;
		long start;
		long quantum;
		volatile boolean run=true;
		TimesUp(long _millis) {
			m = Manager.getInst();
			limit  = _millis;
			start = System.currentTimeMillis();
			quantum=10;
		}
		public void run() {
			while (run) {
				long now = System.currentTimeMillis();
				if (now-start >= limit) {
					m.timesUp();
					break;
				}
				try { sleep(quantum); }
				catch (InterruptedException e) { 
					break;
				}
			}
		}*/
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
			if (conf.isVerbose()) {
				L.error("Exception while running "+program);
				L.error(ExceptionUtils.getStackTrace(e));
			}
			throw new SociaLiteException(e);
		}
	}
	/*public void run(String program, long millis) {
		try {
			List<Eval> evals = compile(program).getEvalInsts();
			TimesUp timesUp = new TimesUp(millis);
			timesUp.start();
			for (Eval e:evals) e.run();
			timesUp.interrupt();
			try { timesUp.join(); } catch (InterruptedException e) {}
		} catch (Exception e) {
			if (conf.isVerbose()) {
				L.error("Exception while running "+program);
				L.error(ExceptionUtils.getStackTrace(e));
			}
			throw new SociaLiteException(e);
		}		
	}	*/
	public void run(String program, QueryVisitor qv, int id) {
		run(program, qv);
	}
	public void run(String program, final QueryVisitor qv) {
		try {
			long start=System.currentTimeMillis();
			
			CodeGenMain codeGen = compile(program);
			//System.out.println("Code generation:"+(System.currentTimeMillis()-start)+"ms");
			List<Eval> evals = codeGen.getEvalInsts();
			start=System.currentTimeMillis();
			for (Eval e:evals) e.run();			
			//System.out.println("Evaluation:"+(System.currentTimeMillis()-start)+"ms");
			start=System.currentTimeMillis();
			codeGen.generateQuery(qv);
			//System.out.println("Query generation:"+(System.currentTimeMillis()-start)+"ms");
			start=System.currentTimeMillis();
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
			//System.out.println("Query running:"+(System.currentTimeMillis()-start)+"ms");
			//L.debug("All exec time:"+(System.currentTimeMillis()-start)+"ms");
		} catch (Exception e) {
			if (conf.isVerbose()) {
				L.error("Exception while running "+program);
				L.error(ExceptionUtils.getStackTrace(e));
			}
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
	public void cleanupTableIter(int id) {
		
	}
	
	public String cwd() { return PathTo.cwd(); }
	public void chdir(String path) {
		File f = new File(path);
		if (!f.isAbsolute()) f = new File(PathTo.cwd(), f.getPath());
		if (!f.isDirectory()) throw new SociaLiteException(path+" is not a directory");
		if (!f.exists()) f.mkdirs();
		
		PathTo.setcwd(f.getPath());
	}
	public void clearTable(String name) {
		assert runtime!=null;
		
		TableInstRegistry reg=runtime.getTableRegistry();
		reg.clearTable(name);
	}
	
	public void store(String path) { storeWorkspace(path); }
	public void storeWorkspace(String path) {
		chdir(path);
		storeWorkspace();
	}
	public void store() { storeWorkspace(); }
	public void storeWorkspace() {
		copyTableClassesTo(new File(PathTo.cwd("_classes")));
		TableInstRegistry reg=runtime.getTableRegistry();
		reg.store(new File(PathTo.cwd("_table_storage")));
	}

	public void load(String path) { loadWorkspace(path); }
	public void loadWorkspace(String path) {
		chdir(path);
		loadWorkspace();
	}
	public void load() { loadWorkspace(); }
	public void loadWorkspace() {
		assert runtime!=null:"runtime is null";
		if (runtime.getTableMap().size()!=0) {
			L.warn("Cannot load tables if tables are already declared.");
			return;
		}
		Loader.addClassPath(new File(PathTo.cwd("_classes")));
		
		TableInstRegistry reg=runtime.getTableRegistry();		
		Map<String, Table> _tableMap = reg.load(new File(PathTo.cwd("_table_storage")));
		parser.addExistingTables(_tableMap);
		for (Table t:_tableMap.values()) {
			runtime.getSliceMap().addTable(t);
		}
	}
	
	void copyTableClassesTo(File to) {
		if (!to.exists()) to.mkdirs();
		assert to.isDirectory();
		File from = new File(PathTo.classOutput());
		copyTableClassesRecur(from, to);
	}
	void copyTableClassesRecur(File from, File to) {
		to.mkdirs();		
		assert from.isDirectory();
		assert to.isDirectory():to+" is not a directory.";		
		FileInputStream ins=null;
		FileOutputStream os=null;
		byte[] b = new byte[8*1024];
		try {
			for (File f:from.listFiles()) {
				File dest = new File(to.getAbsolutePath(), f.getName());
				if (f.isDirectory()) {
					if (f.getName().endsWith("query")) continue;
					if (f.getName().endsWith("visitors")) continue;
					if (f.getName().endsWith("eval")) continue;
					copyTableClassesRecur(f.getAbsoluteFile(), dest);
				} else if (f.getName().endsWith(".class")) {
					ins = new FileInputStream(f);
					os = new FileOutputStream(dest);
					int length;
					while ((length=ins.read(b)) > 0) {
						os.write(b, 0, length);
					}
				}
			}
		} catch (IOException e) {
			throw new SociaLiteException(e);
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
			if (f.isDirectory()) cleanup(f);
			else if (f.getName().endsWith(".class")) f.delete();
			//else if (f.getName().endsWith(".java")) f.delete();
		}
	}
	
	public Status status() { return status(0); }
	public Status status(int verbose) {
		Status s = new Status();
		s.putNodeNum("1");
		int freeMem=(int)(SRuntime.freeMemory()/1024/1024);
		s.putMemStatus(""+freeMem+"MB");
		TIntFloatHashMap progressMap = runtime.getProgress().get();
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
