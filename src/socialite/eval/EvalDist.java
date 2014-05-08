package socialite.eval;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.codegen.Epoch;
import socialite.codegen.RuleComp;
import socialite.engine.Config;
import socialite.parser.Const;
import socialite.parser.MyType;
import socialite.parser.Predicate;
import socialite.parser.Rule;
import socialite.resource.RuleMap;
import socialite.resource.SRuntime;
import socialite.resource.TableInstRegistry;
import socialite.resource.TableSliceMap;
import socialite.resource.VisitorBuilder;
import socialite.util.Assert;


public class EvalDist extends EvalParallel {
	public static final Log L=LogFactory.getLog(EvalDist.class);
	
	public EvalDist(SRuntime _runtime, Epoch _epoch, Config _conf) {
		super(_runtime, _epoch, _conf);
	}
	/*
	public EvalDist(Epoch _epoch, RuleMap _ruleMap, TableSliceMap _sliceMap, 
			VisitorBuilder _vbuilder, TableInstRegistry _registry, Config _conf) {
		super(_epoch, _ruleMap, _sliceMap, _vbuilder, _registry, _conf);
	}*/	
	
	@Override
	public void run() {		
		init();
		initDone();
		Iterator<RuleComp> it = epoch.topologicalOrder();
		while(it.hasNext()) {
			RuleComp rc=it.next();
			
			if (rc.scc()) evalSCC(rc);
			else eval(rc);
		}
		/** 
		 * finish() will be called when the epoch (across all the machines) is finished
		 * see {@link: CmdListener.WatchEpochDone#finish() } 
		 */
	}
	
	void evalSCC(RuleComp scc) {
		eval(scc);
	}
	void eval(RuleComp rc) {
		//Rule r=rc.getStartingRule();
		for (Rule r:rc.getStartingRules()) {
			if (r!=null) {
				if (needsLocalEval(r)) {
					manager.addCmd(new EvalCommand(r.id()));
				}
			}
		}
		shutdownInitThreads();
	}
	boolean needsLocalEval(Rule r) {
		Predicate firstP=r.firstP();
		if (firstP==null) return true;
		if (firstP.idxParam==null) return true;
		
		if (firstP.idxParam instanceof Const) {
			Const c = (Const)firstP.idxParam;
			int tid=visitorBuilder.firstTableId(r.id());
			int rangeOrHash = sliceMap.getIndex(tid, c.val);
			return sliceMap.isLocal(tid, rangeOrHash);
		}
		/*
		if (firstP.idxParam instanceof Integer) {
			int val=(Integer)firstP.idxParam;			
			int tid=visitorBuilder.firstTableId(r.id());
			return sliceMap.isLocal(tid, val);
		}*/
		return true;
	}
	
	public String toString() {
		String str="EvalDist:";
		for (Rule r:epoch.getRules()) {
			str += r;
		}
		return str;
	}
}