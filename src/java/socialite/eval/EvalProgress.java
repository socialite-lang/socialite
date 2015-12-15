package socialite.eval;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import socialite.resource.SRuntime;
import gnu.trove.iterator.TIntFloatIterator;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.TIntFloatHashMap;
import gnu.trove.procedure.TIntFloatProcedure;

public class EvalProgress {
	static EvalProgress inst=new EvalProgress();
	
	public static EvalProgress getInst() { return inst; }
	
	SynchIntFloatHashMap ruleProgress;
	SynchIntHistory finished;	
	SynchIntHistory halted;
	public EvalProgress() {
		ruleProgress = new SynchIntFloatHashMap(127);
		finished = new SynchIntHistory(10);
		halted = new SynchIntHistory(4);
	}
	public void init(int ruleid) {
		ruleProgress.put(ruleid, 0);
	}
	public void clear() {
		ruleProgress.clear();
		finished.clear();
		halted.clear();
	}
	public void halt(int ruleid) {
		halted.add(ruleid);
		ruleProgress.remove(ruleid);
	}
	public void update(int ruleid, int executed, int total) {
		if (executed==total) {
			finished.add(ruleid);
			ruleProgress.remove(ruleid);			
		} else {
			float val=(float)executed/(float)total;
			ruleProgress.put(ruleid, val);
		}
	}
	public TIntFloatHashMap get() {
		final TIntFloatHashMap progress = new TIntFloatHashMap(32);
		ruleProgress.foreach(new TIntFloatProcedure() {
			public boolean execute(int i, float f) {
				progress.put(i, f);
				return true;
			}
		});
		for (int ruleid:finished.get()) {
			progress.put(ruleid, 1);
		}
		for (int ruleid:halted.get()) {
			progress.put(ruleid, -1);
		}
		return progress;		
	}
	
	public static void main(String[] args) {
		EvalProgress ep = new EvalProgress();
		ep.update(1, 2, 10);
		ep.update(1, 5, 10);
		ep.update(2, 5, 5);
		
		TIntFloatHashMap map = ep.get();
		TIntFloatIterator it = map.iterator();
		while(it.hasNext()) {
			it.advance();
			int rid=it.key();
			float percent=it.value()*100;
			
			System.out.println("Rule["+rid+"]:"+percent+"%");			
		}
	}
}

class SynchIntFloatHashMap {
	TIntFloatHashMap map;	
	
	SynchIntFloatHashMap(int capacity) {
		map = new TIntFloatHashMap(capacity, 0.8f, -1, -1);
	}
	public synchronized float get(int i) {
		return map.get(i);
	}
	public synchronized float put(int i, float f) {
		return map.put(i, f);
	}
	public synchronized float remove(int i) {
		return map.remove(i);
	}
	public synchronized void foreach(TIntFloatProcedure f) {		
		map.forEachEntry(f);
	}
	public synchronized void clear() {
		map.clear();
	}
	int size() {
		return map.size();
	}	
}

class SynchIntHistory {
	TIntArrayList list;	
	int limit;
	SynchIntHistory(int _limit) {
		limit = _limit;
		list = new TIntArrayList(limit);		
	}
	public synchronized boolean add(int i) {
		if (list.size()>=limit) {
			list.removeAt(0);
		}
		return list.add(i);
	}
	public synchronized int[] get() {
		return list.toArray();
	}	
	public synchronized void clear() {
		list.clear();
	}
	int size() { return list.size(); }
}
