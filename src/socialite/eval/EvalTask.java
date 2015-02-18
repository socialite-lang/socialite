package socialite.eval;

import socialite.tables.TableInst;
import socialite.util.SocialiteFinishEval;
import socialite.visitors.IVisitor;

public class EvalTask implements Task {
	IVisitor visitor;
	TableInst deltaT;
	int priority;
	public EvalTask(IVisitor _v) {
		visitor = _v;
	}	
	
	public void setPriority(int _priority) {
		priority = _priority;
	}
	public int getPriority() {
		return priority;
	}
	
	@Override
	public void run(Worker w) {
		visitor.setWorker(w);		
		visitor.run();
	}
	
	public TableInst[] getResultDeltaTable() {
		return visitor.getDeltaTables();
	}

    public int getEpochId() {
        return visitor.getEpochId();
    }
	public int getRuleId() {
		return visitor.getRuleId();
	}
	
	public String toString() {
		return visitor.toString();
	}
}
