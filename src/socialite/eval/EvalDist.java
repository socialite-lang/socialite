package socialite.eval;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.codegen.Epoch;
import socialite.codegen.RuleComp;
import socialite.engine.Config;
import socialite.parser.Const;
import socialite.parser.Predicate;
import socialite.parser.Rule;
import socialite.resource.SRuntime;


public class EvalDist extends EvalParallel {
	public static final Log L=LogFactory.getLog(EvalDist.class);
	
	public EvalDist(SRuntime _runtime, Epoch _epoch, Config _conf) {
		super(_runtime, _epoch, _conf);
	}

	@Override
	public void run() {
    	init();
    	runReally();
	}

	boolean eval(RuleComp rc) {
        boolean issued=false;
		for (Rule r:rc.getStartingRules()) {
			if (r!=null) {
                assert r.getEpochId()==epoch.id();
                if (!needsLocalEval(r))
                    continue;
				try { manager.addCmd(new EvalCommand(epoch.id(), r.id())); }
                catch (InterruptedException e) { break; }
                issued = true;
			}
		}
        return issued;
	}
	boolean needsLocalEval(Rule r) {
		Predicate firstP=r.firstP();
		if (firstP==null) return true;
		
		if (firstP.first() instanceof Const) {
			Const c = (Const)firstP.first();
            int tid = runtime.getVisitorBuilder(r.id()).firstTableId(r.id());
            return sliceMap.isLocal(tid, c.val);
		}
		return true;
	}
	
	public String toString() {
		String str="EvalDist:";
		/*for (Rule r:epoch.getRules()) {
			str += r;
		}*/
		return str;
	}
}