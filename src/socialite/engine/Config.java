package socialite.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.dist.PortMap;
import socialite.util.Assert;
import socialite.util.SociaLiteException;

import gnu.trove.map.hash.TObjectByteHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

enum EngineType {
	SEQ, PARALLEL, DIST, CLIENT 
}	 
public class Config {
	public static final Log L=LogFactory.getLog(Config.class);
	
	
	public static Config seq() { return new Config(EngineType.SEQ); }
	public static Config par() { 
		Config conf = new Config(EngineType.PARALLEL);
		return conf; }
	public static Config par(int n) { 
		Config conf = new Config(EngineType.PARALLEL, n);
		return conf; 
	}
	public static Config client(String master, int port) {
		Config conf=new Config(EngineType.CLIENT);
		conf.portMap = new PortMap(master, port);
		return conf;
	}
	public static Config client() { 
		Config conf=new Config(EngineType.CLIENT);
		conf.portMap=PortMap.get();
		return conf;
	}	
	public static Config dist() {
		Config conf=new Config(EngineType.DIST);
		conf.portMap=PortMap.get();
		return conf;
	}		
	public static Config dist(int cpuNum) {
		Config conf=new Config(EngineType.DIST, cpuNum);
		conf.portMap=PortMap.get();
		return conf;
	}
	public static int getWorkerNumFromConf() {
		try {
			FileInputStream fis=new FileInputStream("conf/slaves");
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			int count=0;
			while (true) {
				String line=reader.readLine();
				if (line==null) break;
				line = line.trim();
				if (line.length()==0) continue;
				if (line.startsWith("#")) continue;
				count++;
			}
			return count;
		} catch (FileNotFoundException e) {
			L.warn("Cannot find conf/slaves. Assuming 1 worker node.");
			return 1;
		} catch (IOException e) {
			L.warn("Cannot parse conf/slaves file. Assuming 1 worker node.");
			return 1;
		}
	}	
	public static int systemWorkerNum=-1;
	static {
		String p=System.getProperty("socialite.worker.num");
		if (p!=null) {
			systemWorkerNum = Integer.parseInt(p);
		} else {
			systemWorkerNum = -1;			
		}
	}
	
	int workerNum=1;	
	boolean verbose=false;
	boolean cleanup=false;
	
	EngineType engineType=null;
	boolean isClient=false;
	boolean isDistributed=false;
	PortMap portMap=null;
		
	boolean errorRecovery=true;
	TObjectIntHashMap<String> debugInfo;
	
	void setupDebugInfo() {
		debugInfo = new TObjectIntHashMap<String>();
		debugInfo.put("GenerateTable", (byte)1);		
		debugInfo.put("GenerateVisitor", (byte)1);
		debugInfo.put("GenerateEval", (byte)1);
		debugInfo.put("DeltaStepOpt", (byte)1);
		debugInfo.put("DijkstraOpt", (byte)1);
	}
	
	public Config(EngineType type) {
		engineType = type;
		setWorkerNum(type, -1);
		setupDebugInfo();
	}	
	public Config(EngineType type, int _cpuNum) {
		engineType = type;
		setWorkerNum(type, _cpuNum);
		setupDebugInfo();
	}
	public void setDebugOpt(String debugOpt, boolean value) {
		if (!debugInfo.containsKey(debugOpt)) {
			throw new SociaLiteException("Unexpected Debug Option:"+debugOpt);
		}
		if (value) debugInfo.put(debugOpt, 1);
		else debugInfo.put(debugOpt, 0);
	}
	public boolean getDebugOpt(String debugOpt) {
		int value = debugInfo.get(debugOpt);
		if (value==1) return true;
		else return false;
	}

	public void setCleanup(boolean _cleanup) { cleanup = _cleanup; }
	public boolean cleanup() { return cleanup; }
	public boolean errorRecovery() { return errorRecovery; }
	public void setErrorRecovery(boolean recover) { errorRecovery = recover; }
	
	void setWorkerNum(EngineType type, int _cpuNum) {
		if (_cpuNum<=0) {
			if (type == EngineType.DIST) {
				if (systemWorkerNum > 0) {
					workerNum = systemWorkerNum;
				} else {
					Assert.impossible("systemWorkerNum<0 and cpuNum<0");
					//System.out.println(" WARN uncomment Assert.impossible in Config::setWorkerNum");
				}				
			} else if (type == EngineType.PARALLEL) {
				workerNum = Runtime.getRuntime().availableProcessors();
			} else {
				workerNum = 1;
			}
		} else {
			workerNum = _cpuNum;
		}
	}
	public int getWorkerNum() { return workerNum;}
	
	public int sliceNum() {
		if (workerNum==1) return 1;
		else {
			if (isDistributed()) return workerNum*8; 
			else return workerNum*16;
		}
	}
	
	public int virtualSliceNum() {
		if (workerNum==1) return 1;
		else {
			if (isDistributed()) return workerNum*32; 
			else return workerNum*32;
		}
		//else return workerNum*2;
	}
	
	public void setVerbose() { verbose=true; }
	public boolean isVerbose() { return verbose; }
	public boolean isClient() { return engineType == EngineType.CLIENT; }
	public boolean isSequential() { return workerNum==1 && !isDistributed(); }
	public boolean isParallel() { return workerNum>=2 || isDistributed(); }
	public boolean isDistributed() { return engineType == EngineType.DIST; }	
	public int minSliceSize() { return 1; }	
	
	public PortMap portMap() {
		assert(portMap!=null);
		return portMap;
	}
}
