package socialite.functions;

import socialite.util.SocialiteFinishEval;
import gnu.trove.iterator.TIntIterator;

public class Limit {
	static class IntIter implements TIntIterator {
		int limit;
		int current;
		IntIter(int upto) {
			limit = upto;
			current = 0;
		}
		@Override
		public boolean hasNext() {
			return current < limit;
		}
		@Override
		public void remove() { }
		@Override
		public int next() {
			// TODO Auto-generated method stub
			return current++;
		}
		
	}
	public static int invoke(int val, int limit) {
		if (val >= limit) {
			throw new SocialiteFinishEval();
		}		
		return val+1;
	}
	
	public static void main(String[] args) {
		
	}
}
