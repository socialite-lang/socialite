package socialite.eval;

import gnu.trove.list.array.TIntArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import socialite.util.AtomicDouble;
import socialite.util.AtomicFloat;

// This keeps track of the maximum and minimum value so far.
public class DeltaStepWindow {
	static AtomicInteger sharedIntMin = new AtomicInteger(), sharedIntMax = new AtomicInteger();
	static AtomicLong sharedLongMin = new AtomicLong(), sharedLongMax = new AtomicLong();
	static AtomicFloat sharedFloatMin = new AtomicFloat(), sharedFloatMax = new AtomicFloat();
	static AtomicDouble sharedDoubleMin = new AtomicDouble(), sharedDoubleMax = new AtomicDouble();	
	static final int maxLevel = TaskQueue.MAX_LEVEL;
	static ArrayList<DeltaStepWindow> allWindows = new ArrayList<DeltaStepWindow>();
	
	static { reset(); }
	public static void reset() {
		// min
		sharedIntMin.set(Integer.MAX_VALUE);
		sharedLongMin.set(Long.MAX_VALUE);
		sharedFloatMin.set(Float.POSITIVE_INFINITY);
		sharedDoubleMin.set(Double.POSITIVE_INFINITY);
		// max
		sharedIntMax.set(Integer.MIN_VALUE);
		sharedLongMax.set(Long.MIN_VALUE);
		sharedFloatMax.set(Float.NEGATIVE_INFINITY);
		sharedDoubleMax.set(Double.NEGATIVE_INFINITY);
		for (DeltaStepWindow w:allWindows) 
			w._reset();
	}
	
	// instance variables, methods
	
	int workerid;
	
	IntWindow iwin;
	LongWindow lwin;
	FloatWindow fwin;
	DoubleWindow dwin;
	
	IntWindow iwin() {
		if (iwin==null) iwin = new IntWindow();
		return iwin;
	}
	LongWindow lwin() {
		if (lwin==null) lwin = new LongWindow();
		return lwin;
	}
	FloatWindow fwin() {
		if (fwin==null) fwin = new FloatWindow();
		return fwin;
	}
	DoubleWindow dwin() {
		if (dwin==null) dwin = new DoubleWindow();
		return dwin;
	}

	public DeltaStepWindow(int _workerid) {
		workerid = _workerid;
		allWindows.add(this);
	}	
	public int maxLevel() { return maxLevel; }
	public boolean isEnabled() {
		if (iwin!=null) return true;
		if (lwin!=null) return true;		
		if (fwin!=null) return true;
		if (dwin!=null) return true;
		return false;
	}
	void _reset() {
		iwin=null;
		lwin=null;
		fwin=null;
		dwin=null;
	}
	public int selectLevel(int v, boolean min) {
		return iwin().selectLevel(v, min);
	}	
	public int selectLevel(long v, boolean min) {
		return lwin().selectLevel(v, min);
	}
	public int selectLevel(float v, boolean min) {
		return fwin().selectLevel(v, min);
	}
	public int selectLevel(double v, boolean min) {
		return dwin().selectLevel(v, min);
	}
	
	public void updateShared() {
		if (iwin!=null) iwin.syncShared();
		if (lwin!=null) lwin.syncShared();
		if (fwin!=null) fwin.syncShared();
		if (dwin!=null) dwin.syncShared();
	}		
	public void step() {
		if (iwin!=null) iwin.step();
		if (lwin!=null) lwin.step();
		if (fwin!=null) fwin.step();
		if (dwin!=null) dwin.step();
	}	
}

class IntWindow {
	boolean isMin;	
	int[] boundaries=new int[DeltaStepWindow.maxLevel];
	int myMin=Integer.MAX_VALUE, myMax=Integer.MIN_VALUE;
	
	public String toString() {
		String str;
		if (isMin) str = "$Min [";
		else str = "$Max [";		
		for (int i=0; i<boundaries.length; i++) 
			str += boundaries[i]+" ";
		return str+"]";
	}
	
	public int selectLevel(int v, boolean min) {
		int level=-1;
		isMin = min;		
		if (myMin==Integer.MAX_VALUE) myMin = DeltaStepWindow.sharedIntMin.get();
		if (myMax==Integer.MIN_VALUE) myMax = DeltaStepWindow.sharedIntMax.get();

		boolean updated=false;
		if (v < myMin) { 
			myMin = v;
			updated = true;
			level = 0;
		}
		if (v > myMax) { 
			myMax = v;
			updated = true;
		}
		
		if (updated) updateBoundaries();
		
		if (level < 0) level = selectLevelReally(v);
		
		if (!min) level = DeltaStepWindow.maxLevel - level;
		return level;
	}	
	int selectLevelReally(int v) {
		for (int i=0; i<boundaries.length; i++) {
			if (v <= boundaries[i]) return i;
		}
		return boundaries.length-1;
	}
	
	void updateBoundaries() {				
		int delta = (myMax-myMin)/DeltaStepWindow.maxLevel;
		if (delta<1) delta=1;

		for (int i=0; i<DeltaStepWindow.maxLevel; i++)
			boundaries[i] = myMin + delta*(i+1);
	}
	public void syncShared() {
		int sharedMin = DeltaStepWindow.sharedIntMin.get();
		if (sharedMin < myMin) {
			myMin = sharedMin;
		} else {		
			while (myMin < sharedMin) {
				boolean success=DeltaStepWindow.sharedIntMin.compareAndSet(sharedMin, myMin);
				if (success) break;
				sharedMin = DeltaStepWindow.sharedIntMin.get();
			}
		}		
		int sharedMax = DeltaStepWindow.sharedIntMax.get();
		if (sharedMax > myMax) {
			myMax = sharedMax;
		} else {		
			while (myMax > sharedMax) {
				boolean success=DeltaStepWindow.sharedIntMax.compareAndSet(sharedMax, myMax);
				if (success) break;
				sharedMax = DeltaStepWindow.sharedIntMax.get();
			}
		}
	}
	public void step() {
		if (isMin) {
			myMin = boundaries[0];
			DeltaStepWindow.sharedIntMin.set(myMin);
		} else {
			myMax = boundaries[boundaries.length-2];
			DeltaStepWindow.sharedIntMax.set(myMax);
		}
		updateBoundaries();
	}
}
class LongWindow {
	boolean isMin;	
	long[] boundaries=new long[DeltaStepWindow.maxLevel];
	long myMin=Long.MAX_VALUE, myMax=Long.MIN_VALUE;
	
	public String toString() {
		String str;
		if (isMin) str = "$Min [";
		else str = "$Max [";		
		for (int i=0; i<boundaries.length; i++) 
			str += boundaries[i]+" ";
		return str+"]";
	}
	
	public int selectLevel(long v, boolean min) {
		int level=-1;
		isMin = min;		
		if (myMin==Long.MAX_VALUE) myMin = DeltaStepWindow.sharedLongMin.get();
		if (myMax==Long.MIN_VALUE) myMax = DeltaStepWindow.sharedLongMax.get();

		boolean updated=false;
		if (v < myMin) { 
			myMin = v;
			updated = true;
			level = 0;
		}
		if (v > myMax) { 
			myMax = v;
			updated = true;
		}
		
		if (updated) updateBoundaries();
		
		if (level < 0) level = selectLevelReally(v);
		
		if (!min) level = DeltaStepWindow.maxLevel - level;
		return level;
	}
	
	int selectLevelReally(long v) {
		for (int i=0; i<boundaries.length; i++) {
			if (v <= boundaries[i]) return i;
		}
		return boundaries.length-1;
	}
	
	void updateBoundaries() {				
		long delta = (myMax-myMin)/DeltaStepWindow.maxLevel;
		if (delta<1) delta=1;

		for (int i=0; i<DeltaStepWindow.maxLevel; i++)
			boundaries[i] = myMin + delta*(i+1);
	}
	public void syncShared() {
		long sharedMin = DeltaStepWindow.sharedLongMin.get();
		if (sharedMin < myMin) {
			myMin = sharedMin;
		} else {		
			while (myMin < sharedMin) {
				boolean success=DeltaStepWindow.sharedLongMin.compareAndSet(sharedMin, myMin);
				if (success) break;
				sharedMin = DeltaStepWindow.sharedLongMin.get();
			}
		}		
		long sharedMax = DeltaStepWindow.sharedLongMax.get();
		if (sharedMax > myMax) {
			myMax = sharedMax;
		} else {		
			while (myMax > sharedMax) {
				boolean success=DeltaStepWindow.sharedLongMax.compareAndSet(sharedMax, myMax);
				if (success) break;
				sharedMax = DeltaStepWindow.sharedLongMax.get();
			}
		}
	}
	public void step() {
		if (isMin) {
			myMin = boundaries[0];
			DeltaStepWindow.sharedLongMin.set(myMin);
		} else {
			myMax = boundaries[boundaries.length-2];
			DeltaStepWindow.sharedLongMax.set(myMax);
		}
		updateBoundaries();
	}
}

class FloatWindow {
	boolean isMin;	
	float[] boundaries=new float[DeltaStepWindow.maxLevel];
	float myMin=Float.POSITIVE_INFINITY, myMax=Float.NEGATIVE_INFINITY;
	
	public String toString() {
		String str;
		if (isMin) str = "$Min [";
		else str = "$Max [";		
		for (int i=0; i<boundaries.length; i++) 
			str += boundaries[i]+" ";
		return str+"]";
	}
	
	public int selectLevel(float v, boolean min) {
		int level=-1;
		isMin = min;		
		if (myMin==Float.POSITIVE_INFINITY) myMin = DeltaStepWindow.sharedFloatMin.get();
		if (myMax==Float.NEGATIVE_INFINITY) myMax = DeltaStepWindow.sharedFloatMax.get();

		boolean updated=false;
		if (v < myMin) { 
			myMin = v;
			updated = true;
			level = 0;
		}
		if (v > myMax) { 
			if (myMax-myMin < (v-myMax)*2)
				level = 0;			
			myMax = v;
			updated = true;
		}
		
		if (updated) updateBoundaries();
		
		if (level < 0) level = selectLevelReally(v);
		
		if (!min) level = DeltaStepWindow.maxLevel - level;
		return level;
	}	
	int selectLevelReally(float v) {
		for (int i=0; i<boundaries.length; i++) {
			if (v <= boundaries[i]) return i;
		}
		return boundaries.length-1;
	}
	
	void updateBoundaries() {				
		float delta = (myMax-myMin)/DeltaStepWindow.maxLevel;
		if (delta < Float.MIN_VALUE*2) delta = Float.MIN_VALUE*2;

		for (int i=0; i<DeltaStepWindow.maxLevel; i++)
			boundaries[i] = myMin + delta*(i+1);
	}
	public void syncShared() {
		float sharedMin = DeltaStepWindow.sharedFloatMin.get();
		if (sharedMin < myMin) {
			myMin = sharedMin;
		} else {		
			while (myMin < sharedMin) {
				boolean success=DeltaStepWindow.sharedFloatMin.compareAndSet(sharedMin, myMin);
				if (success) break;
				sharedMin = DeltaStepWindow.sharedFloatMin.get();
			}
		}		
		float sharedMax = DeltaStepWindow.sharedFloatMax.get();
		if (sharedMax > myMax) {
			myMax = sharedMax;
		} else {		
			while (myMax > sharedMax) {
				boolean success=DeltaStepWindow.sharedFloatMax.compareAndSet(sharedMax, myMax);
				if (success) break;
				sharedMax = DeltaStepWindow.sharedFloatMax.get();
			}
		}
	}
	public void step() {
		if (isMin) {
			myMin = boundaries[0];
			DeltaStepWindow.sharedFloatMin.set(myMin);
		} else {
			myMax = boundaries[boundaries.length-2];
			DeltaStepWindow.sharedFloatMax.set(myMax);
		}
		updateBoundaries();
	}
}

class DoubleWindow {
	boolean isMin;	
	double[] boundaries=new double[DeltaStepWindow.maxLevel];
	double myMin=Double.POSITIVE_INFINITY, myMax=Double.NEGATIVE_INFINITY;
	
	public String toString() {
		String str;
		if (isMin) str = "$Min [";
		else str = "$Max [";		
		for (int i=0; i<boundaries.length; i++) 
			str += boundaries[i]+" ";
		return str+"]";
	}
	
	public int selectLevel(double v, boolean min) {
		int level=-1;
		isMin = min;		
		if (myMin==Double.POSITIVE_INFINITY) myMin = DeltaStepWindow.sharedDoubleMin.get();
		if (myMax==Double.NEGATIVE_INFINITY) myMax = DeltaStepWindow.sharedDoubleMax.get();

		boolean updated=false;
		if (v < myMin) { 
			myMin = v;
			updated = true;
			level = 0;
		}
		if (v > myMax) { 
			if (myMax-myMin < (v-myMax)*2)
				level = 0;			
			myMax = v;
			updated = true;
		}
		
		if (updated) updateBoundaries();
		
		if (level < 0) level = selectLevelReally(v);
		
		if (!min) level = DeltaStepWindow.maxLevel - level;
		return level;
	}	
	int selectLevelReally(double v) {
		for (int i=0; i<boundaries.length; i++) {
			if (v <= boundaries[i]) return i;
		}
		return boundaries.length-1;
	}
	
	void updateBoundaries() {				
		double delta = (myMax-myMin)/DeltaStepWindow.maxLevel;
		if (delta < Double.MIN_VALUE*2) delta = Double.MIN_VALUE*2;

		for (int i=0; i<DeltaStepWindow.maxLevel; i++)
			boundaries[i] = myMin + delta*(i+1);
	}
	public void syncShared() {
		double sharedMin = DeltaStepWindow.sharedDoubleMin.get();
		if (sharedMin < myMin) {
			myMin = sharedMin;
		} else {		
			while (myMin < sharedMin) {
				boolean success=DeltaStepWindow.sharedDoubleMin.compareAndSet(sharedMin, myMin);
				if (success) break;
				sharedMin = DeltaStepWindow.sharedDoubleMin.get();
			}
		}		
		double sharedMax = DeltaStepWindow.sharedDoubleMax.get();
		if (sharedMax > myMax) {
			myMax = sharedMax;
		} else {		
			while (myMax > sharedMax) {
				boolean success=DeltaStepWindow.sharedDoubleMax.compareAndSet(sharedMax, myMax);
				if (success) break;
				sharedMax = DeltaStepWindow.sharedDoubleMax.get();
			}
		}
	}
	public void step() {
		if (isMin) {
			myMin = boundaries[0];
			DeltaStepWindow.sharedDoubleMin.set(myMin);
		} else {
			myMax = boundaries[boundaries.length-2];
			DeltaStepWindow.sharedDoubleMax.set(myMax);
		}
		updateBoundaries();
	}
}