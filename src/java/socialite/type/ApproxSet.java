package socialite.type;


import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import socialite.util.BloomFilter;

public class ApproxSet implements Externalizable {
	static final long serialVersionUID = 1L;
	
	List<BloomFilter> prevSets;
	BloomFilter set;
	public ApproxSet() {
		int numHash = getOptimalNumHash(10);
		set = new BloomFilter(10, 1024, numHash);
	}	
	public ApproxSet(double bitsPerElem, int expectedSize) {
		int numHash = getOptimalNumHash(bitsPerElem);
		set = new BloomFilter(bitsPerElem, expectedSize, numHash);
	}
    public void writeExternal(ObjectOutput out) throws IOException {
    	if (prevSets==null) {
    		out.writeInt(0);
    	} else {
    		out.writeInt(prevSets.size());
    		for (BloomFilter bf:prevSets) {
    			out.writeObject(bf);
    		}
    	}
    	out.writeObject(set);    	
	}
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    	int prevSize=in.readInt();
    	if (prevSize > 0) {
    		prevSets = new ArrayList<BloomFilter>(prevSize);
    		for (int i=0; i<prevSize; i++) {
    			BloomFilter bf=(BloomFilter)in.readObject();
    			prevSets.add(bf);
    		}
    	}
    	set = (BloomFilter) in.readObject();
	}
    
    int getOptimalNumHash(double bitsPerElem) {
    	// See http://corte.si/posts/code/bloom-filter-rules-of-thumb/index.html
    	int numHash = (int) Math.round(bitsPerElem*0.71f); 
    	if (numHash<=0) numHash = 1;
    	return numHash;
    }
    public ApproxSet get() { return this; }

	public boolean add(int v) {		
		if (set.isFull()) expand();

		return set.add(v);		
	}
	public boolean add(long v) {		
		if (set.isFull()) expand();

		return set.add(v);		
	}

	void expand() {
		if (prevSets==null) prevSets = new ArrayList<BloomFilter>(2);
		
		prevSets.add(set);
		int expandby= 4;			
		int expectedSize = set.getNumExpectedElements()*expandby;
		double bitsPerElm = set.getExpectedBitsPerElement()+2*(1+prevSets.size());
		int numHash = getOptimalNumHash(bitsPerElm);
		set = new BloomFilter(bitsPerElm, expectedSize, numHash);
	}
	
	public boolean contains(int v) {
		if (set.contains(v)) 
			return true;
		if (prevSets!=null) {
			for (BloomFilter prevSet:prevSets) {
				if (prevSet.contains(v))
					return true;
			}
		}
		return false;
	}
	public boolean contains(long v) {
		if (set.contains(v)) 
			return true;
		if (prevSets!=null) {
			for (BloomFilter prevSet:prevSets) {
				if (prevSet.contains(v))
					return true;
			}
		}
		return false;
	}
	
	public int size() {
		int size=set.size();
		if (prevSets!=null) {
			for (BloomFilter s:prevSets) 
				size+=s.size();
		}
		return size;
	}
	
	public static void main(String[] args) {
		ApproxSet set = new ApproxSet(10, 1024);
		for (int i=-5000; i<5000; i++) {
			boolean added=set.add(i);
			if (!added) {
				System.out.println("added:"+added);
			}
		}
		
		int cnt=0;
		for (int i=5001; i<1005000; i++) {
			if (set.contains(i))
				cnt++;
		}
    	System.out.println("False positives:"+cnt+" ratio:"+(cnt/1000000.0));
	}
}
