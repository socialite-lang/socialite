package socialite.type;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;

import java.util.BitSet;

import socialite.util.Murmur3;

/**
 * Implementation of FM Sketch (PCSA, Probabilistic Counting with Stochastic Averaging)  
 * Flajolet, P. Martin, G. Probabilistic counting algorithms for data base applications
 *
 * Near-Optimal Compression of Probabilistic Counting Sketches for Networking Applications
 * Björn Scheuermann Martin Mauve  (adjustment for small counting value)
 */
public class ApproxCount implements Comparable {
	public int get() { return count(); }
	
	static final double phi=0.775351;
	
	static final int numBitMaps = 64;
	static final int maxNumBitsPerBitMap = 28;	

	BitSet bitSet;
	short numBitsPerBitMap=6;
	int count$=-1;
	public ApproxCount() {		
		bitSet = new BitSet(numBitsPerBitMap * numBitMaps);
	}
	
	public String toString() {
		return "ApproxCount(count="+count()+")";
	}
	public int hashCode() {
		return bitSet.hashCode();
	}
	public boolean equals(Object o) {
		if (!(o instanceof ApproxCount)) return false;
		ApproxCount c=(ApproxCount)o;
		if (numBitsPerBitMap != c.numBitsPerBitMap) return false;
		if (!bitSet.equals(c.bitSet)) return false;
		return true;
	}
	
	boolean getBit(int bitMapIdx, int bitIdx) {
		assert bitMapIdx < numBitMaps;
		assert bitIdx < numBitsPerBitMap;
		int idx = bitMapIdx*numBitsPerBitMap+bitIdx;
		return bitSet.get(idx);
	}	
	boolean setBit(int bitMapIdx, int bitIdx) {
		assert bitMapIdx < numBitMaps;
		assert bitIdx < numBitsPerBitMap;
		int idx = bitMapIdx*numBitsPerBitMap+bitIdx;
		boolean exist = bitSet.get(idx);
		if (exist) return false;
		else {
			invalidateCount$();
			bitSet.set(idx);
			return true;
		}
	}
	void invalidateCount$() { count$=-1; }
	boolean validCount$() { return count$>=0; }
	void setCount$(double cnt) {
		count$ = (int)Math.round(cnt);
		if (count$==0 && cnt > 0) count$ = 1;		
	}
	int count$() { return count$; }
	
	public int count() {
		if (validCount$()) return count$(); 
		
		int Rsum=0;
		for (int i=0; i<numBitMaps; i++) {
			int R=numBitsPerBitMap;
			for (int j=0; j<numBitsPerBitMap; j++) {
				if (!getBit(i, j)) {
					R = j;
					break;
				}
			}	
			Rsum += R;
		}
		
		double Rn = ((double)Rsum) / (double) numBitMaps;		
		double k = 1.75;
		double n = numBitMaps * (Math.pow(2, Rn)-Math.pow(2, -k*Rn))/phi;
		setCount$(n);		
		return count$();
	}
	void expand() {
		//System.out.println("Expanding...");
		int prevNumBitsPerBitMap = numBitsPerBitMap;
		numBitsPerBitMap *= 2;
		if (numBitsPerBitMap>maxNumBitsPerBitMap)
			numBitsPerBitMap = maxNumBitsPerBitMap;
		
		BitSet oldSet = bitSet;
		bitSet = new BitSet(numBitsPerBitMap * numBitMaps);
		for (int i=0; i<numBitMaps; i++) {
			for (int j=0; j<prevNumBitsPerBitMap; j++) {
				int oldIdx = i*prevNumBitsPerBitMap+j;
				if (oldSet.get(oldIdx))
					setBit(i, j);
			}
		}
	}
	int findFirstNonZeroIdx(int h) {
		int origH = h;
		for (int i=0; i<numBitsPerBitMap; i++) {
			if ((h & 1)==1) {
				return i;
			}
			h = h>>>1;
		}				
		expand();
		//System.out.println("h:"+h+", numBitsPerBitMap:"+numBitsPerBitMap);
		return findFirstNonZeroIdx(origH);
	}
	
	public boolean add(int i) {
		int h = Murmur3.hash32(i);
		int bitMapIdx = h%numBitMaps;
		bitMapIdx = bitMapIdx > 0?bitMapIdx:-bitMapIdx;
		h = h/numBitMaps;
		int idx = findFirstNonZeroIdx(h);
		setBit(bitMapIdx, idx);
		return true;
	}	
	
	public boolean add(ApproxCount other) {
		boolean added=false;
		for (int i=0; i<numBitMaps; i++) {
			int R=numBitsPerBitMap;
			for (int j=0; j<numBitsPerBitMap; j++) {
				if (other.getBit(i, j)) {
					setBit(i, j);
					added=true;
				}
			}
		}
		return added;
	}
    
    public static void main(String[] args) {
    	ApproxCount s = new ApproxCount();
    	TIntHashSet set = new TIntHashSet();
    	long start=System.currentTimeMillis();
    	for (int i=0; i<50; i++) {
    		double r = Math.random();
    		int val= (int)(r*10000);
    		set.add(val);
    	}
    	long end=System.currentTimeMillis();
    	System.out.println("set count:"+set.size());
    	System.out.println("Exec time:"+(end-start)/1000.0);
    	
    	start=System.currentTimeMillis();
    	TIntIterator it=set.iterator();
    	while(it.hasNext()) {
    		s.add(it.next());
    	}
    	s.add(74*numBitMaps);
    	end=System.currentTimeMillis();
    	
    	System.out.println("sketch count:"+s.count());
    	
    	System.out.println("Exec time:"+(end-start)/1000.0);
    }
    
    static void bitSetGetTest() {
    	ApproxCount s = new ApproxCount();    
    	s.setBit(0, 0);    	
    	s.setBit(1, 0);
    	s.setBit(2, 5);
    	s.setBit(3, 19);    	
    	System.out.println("true=="+s.getBit(0, 0));
    	System.out.println("true=="+s.getBit(1, 0));
    	System.out.println("true=="+s.getBit(2, 5));
    	System.out.println("true=="+s.getBit(3, 19));
    	
    	System.out.println("false=="+s.getBit(0, 1));
    	System.out.println("false=="+s.getBit(0, 2));
    	System.out.println("false=="+s.getBit(0, 19));
    	System.out.println("false=="+s.getBit(2, 4));
    	System.out.println("false=="+s.getBit(3, 18));
    }
    
	@Override
	public int compareTo(Object o) {
		if (!(o instanceof ApproxCount)) return -1;
		ApproxCount c = (ApproxCount)o;
		int myCount=count();
		int yourCount=c.count();
		if (myCount == yourCount) return 0;
		else if (myCount < yourCount) return -1;
		else return 1;		
	}    
}
