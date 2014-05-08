package socialite.functions;

import java.util.Random;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.ArrayList;

import socialite.util.Assert;
import socialite.tables.Tuple_int_int;
import socialite.tables.Tuple;
import gnu.trove.iterator.TDoubleIterator;
import gnu.trove.iterator.TIntIterator;

class RandPermuteIterator implements Iterator {

  ListIterator<Integer> itr;
  Tuple tuple;
  int curr;

	RandPermuteIterator(int _begin, int _end) {
    ArrayList<Integer> perm = new ArrayList<Integer>();
    for (int i = _begin; i < _end; ++i) {
      perm.add(i);
    }
    java.util.Collections.shuffle(perm);
    itr = perm.listIterator();
    tuple = new Tuple_int_int();
    curr = _begin;
	}
	@Override
	public Tuple next() {
    int v = itr.next();
    tuple.setInt(0, curr);
    tuple.setInt(1, v);
    curr++;
    return tuple;
	}

	@Override
	public boolean hasNext() {
    return itr.hasNext();
	}

	@Override
	public void remove() {
		Assert.not_implemented();		
	}
	
}

public class RandPermute {
  public static Class[] tupleType() {
    return new Class[] {int.class, int.class};
  }
	public static Iterator<Tuple> invoke(int begin, int end) {
		return new RandPermuteIterator(begin, end);
	}
	
}
