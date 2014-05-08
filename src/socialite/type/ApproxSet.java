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
		set = new BloomFilter(8, 16, 4);
	}
	
	public ApproxSet(double bitsPerElem, int expectedSize, int numHash) {
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
    BloomFilter set() {
    	if (set==null) set = new BloomFilter(8, 16, 4);
    	return set;
    }
    public ApproxSet get() { return this; }

	public boolean add(int v) {		
		if (set.isFull()) {
			if (prevSets==null) prevSets = new ArrayList<BloomFilter>(2);
			
			prevSets.add(set);
			int expandby= 4;			
			int expectedSize = set.size()*expandby;			
			double bitsPerElm = set.getExpectedBitsPerElement()+0.25;
			if (bitsPerElm > 10) bitsPerElm = 10;
			int numHash = (int)(bitsPerElm*0.7f);
			set = new BloomFilter(bitsPerElm, expectedSize, numHash);
		}
		return set.add(v);		
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
	
	public int size() {
		int size=set.size();
		if (prevSets!=null) {
			for (BloomFilter s:prevSets) 
				size+=s.size();
		}
		return size;
	}
	
	public static void main(String[] args) {
		ApproxSet set = new ApproxSet(5.0, 500, 3);
		for (int i=-5000; i<5000; i++) {
			set.add(i);
		}
		
		int cnt=0;
		for (int i=5001; i<105000; i++) {
			if (set.contains(i))
				cnt++;
		}
    	System.out.println("False positives:"+cnt+" ratio:"+(cnt/100000.0));
	}
}
