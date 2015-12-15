package socialite.type;

public class ArgMin {
	public int value; // arg
	Number min;
	double dmin=Double.MAX_VALUE;
	public ArgMin() {}
	public void init() {
		value=0;
		min=null;
		dmin=Double.MAX_VALUE;
	}
	public boolean argmin(int i, int v) {
		if (min==null || v<min.intValue()) {
			min = (Integer)v;
			value=i;
			return true;
		} 
		return false;
	}
	public boolean argmin(int i, long v) {
		if (min==null || v<min.longValue()) {
			min = (Long)v;
			value=i;
			return true;
		} 
		return false;
	}
	public boolean argmin(int i, float v) {
		if (min==null || v<min.floatValue()) {
			min = (Float)v;
			value=i;
			return true;
		} 
		return false;
	}
	/*public boolean argmin(int i, double v) {
		if (min==null || v<min.longValue()) {
			min = (Double)v;
			value=i;
			return true;
		} 
		return false;
	}*/
	public boolean argmin(int i, double v) {
		if (v<dmin) {
			dmin = v;
			value=i;
			return true;
		} 
		return false;
	}
	public String toString() {
		return "ArgMin(value="+value+")";
	}
	
}
