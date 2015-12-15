package socialite.util;

public class IdFactory {
	static int nextTableId=0;
    static int nextEpochId=0;
	static int nextRuleId=0;
	static int nextVarId=0;
	static int pyfuncId=0;
	public static void reset() {
		nextTableId=0;
        nextEpochId=0;
		nextRuleId=0;
		nextVarId=0;
		pyfuncId=0;
	}
	public synchronized static int nextTableId() {
		return nextTableId++;
	}
	
	public synchronized static void tableIdAdvanceTo(int id) {
		if (id > nextTableId)
			nextTableId = id;
	}

    public synchronized static int nextEpochId() {
        return nextEpochId++;
    }

	public synchronized static int nextRuleId() {
		return nextRuleId++;
	}
	
	public synchronized static int nextVarId() {
		return nextVarId++;
	}
	
	public synchronized static int nextPyFuncId() {
		return pyfuncId++;
	}
}