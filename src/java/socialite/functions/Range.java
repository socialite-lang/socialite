package socialite.functions;

import gnu.trove.iterator.TIntIterator;

import java.util.Iterator;

import socialite.util.Assert;


class RangeIterator2 implements TIntIterator {
	int[] a={0, 1,5,4,3,6,7,8,2,9};
	int current;
	RangeIterator2(int _start, int _end) {
		current=0;
	}
	
	public boolean hasNext() {
		if (current < a.length) return true;
		return false;
	}
	public int next() {
		return a[current++];
	}
	public void remove() {
		
	}
}
class RangeIterator implements TIntIterator {
	int start, end;
	int current;
	RangeIterator(int _start, int _end) {
		start=_start;
		end=_end;
		current=start;
	}
	@Override
	public boolean hasNext() {
		if (current < end) return true;
		return false;
	}

	@Override
	public int next() {
		int next = current;
		current++;
		return next;
	}

	@Override
	public void remove() { Assert.not_supported(); }
	
}
/** 
 * Iterates numbers from start to end (not inclusive) 
 */
public class Range {	
	public static TIntIterator invoke(int start, int end) {
		return new RangeIterator(start, end);
	}
	
	public static void main(String args[]) {
		TIntIterator it=Range.invoke(0,  10);
		while(it.hasNext()) {
			int i=it.next();
			System.out.println(i);
		}
	}
}
