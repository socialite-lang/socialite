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
	
	public void setDeltaT(TableInst _deltaT) {
		deltaT = _deltaT;
	}
	public TableInst getDeltaT() { return deltaT; }
	
	public void setPriority(int _priority) {
		priority = _priority;
	}
	public int getPriority() {
		return priority;
	}
	
	public boolean safeToSteal() {
		if (deltaT==null) return true;
		if (deltaT.isAccessed()) return false;
		else return true;		
	}
	@Override
	public void run(Worker w) {
		visitor.setWorker(w);		
		visitor.run();
	}
	
	public TableInst[] getHeadPrivateTable() {
		return visitor.getPrivateTable();
	}
	public TableInst[] getResultDeltaTable() {
		return visitor.getDeltaTables();
	}
	/*public TableInst[] getResultDeltaTable0() {
		return visitor.getDeltaTable0();
	}*/
	
	public int getRuleId() {
		return visitor.getRuleId();
	}
	
	public String toString() {
		return visitor.toString();
	}
}
