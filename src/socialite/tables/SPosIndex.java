package socialite.tables;

import gnu.trove.iterator.TDoubleIntIterator;
import gnu.trove.iterator.TDoubleObjectIterator;
import gnu.trove.iterator.TFloatIntIterator;
import gnu.trove.iterator.TFloatObjectIterator;
import gnu.trove.iterator.TIntIntIterator;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.iterator.TLongIntIterator;
import gnu.trove.iterator.TLongObjectIterator;
import gnu.trove.iterator.TObjectIntIterator;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.TDoubleIntHashMap;
import gnu.trove.map.hash.TDoubleObjectHashMap;
import gnu.trove.map.hash.TFloatIntHashMap;
import gnu.trove.map.hash.TFloatObjectHashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TLongIntHashMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.util.HashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

import socialite.collection.SArrayList;
import socialite.util.Assert;
import socialite.util.SociaLiteException;
import socialite.util.concurrent.*;

import java.util.concurrent.ConcurrentSkipListMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.visitors.IntVisitor;
import socialite.visitors.ObjectVisitor;

public class SPosIndex {
	public static final Log L = LogFactory.getLog(SPosIndex.class);

	Object index;
	SArrayList<MyIndexPosList> posLists;

	public SPosIndex() {
	}

	public void clear() {
		if (posLists != null) { posLists.clear(); }
		if (index == null) { return; }
		
		if (index instanceof TIntIntHashMap)
			iIndex().clear();
		else if (index instanceof TLongIntHashMap)
			lIndex().clear();
		else if (index instanceof TFloatIntHashMap)
			fIndex().clear();
		else if (index instanceof TDoubleIntHashMap)
			dIndex().clear();
		else if (index instanceof TObjectIntHashMap)
			oIndex().clear();
		else {
			throw new AssertionError("Unexpected index type:"
					+ index.getClass().getSimpleName());
		}
	}

	SArrayList<MyIndexPosList> getPosLists() {
		if (posLists == null) {
			posLists = new SArrayList<MyIndexPosList>();
		}
		return posLists;
	}

	void visitPos(int val, IntVisitor v) {
		if (val >= 0) {
			v.visit(val);
		} else {
			int offset = -val - 1;
			MyIndexPosList list = getPosLists().getQuick(offset);			
			list.iterate(v);
		}
	}

	// int-type key
	@SuppressWarnings({ "unchecked", "rawtypes" })
	TIntIntHashMap iIndex() {
		if (index == null) {
			index = new TIntIntHashMap(32, 0.75f, -1, -1);
		}
		return (TIntIntHashMap) index;
	}

	public void add(int key, int pos) {
		if (!iIndex().contains(key)) {
			iIndex().put(key, pos);
		} else {
			int val = iIndex().get(key);
			if (val < 0) {
				int offset = -val - 1;
				getPosLists().getQuick(offset).add(pos);
			} else {
				int offset = getPosLists().size();
				MyIndexPosList list = new MyIndexPosList();
				list.add(val);
				list.add(pos);
				getPosLists().add(list);
				iIndex().put(key, -(offset + 1));
			}
		}
	}

	public boolean contains(int key) {
		return iIndex().containsKey(key);
	}

	public int get(int key) {
		return iIndex().get(key);
	}

	public void iterateBy(int key, IntVisitor v) {
		if (!iIndex().containsKey(key)) {
			return;
		}
		int val = iIndex().get(key);
		visitPos(val, v);
	}

	public void iterateFrom(int from, boolean fromInclusive, IntVisitor v) {
		TIntIntIterator iter = iIndex().iterator();
		while (iter.hasNext()) {
			iter.advance();
			int k = iter.key();
			if (fromInclusive && k < from)
				continue;
			if (!fromInclusive && k <= from)
				continue;
			visitPos(iter.value(), v);
		}
	}

	public void iterateTo(int to, boolean toInclusive, IntVisitor v) {
		TIntIntIterator iter = iIndex().iterator();
		while (iter.hasNext()) {
			iter.advance();
			int k = iter.key();
			if (toInclusive && k > to)
				continue;
			if (!toInclusive && k >= to)
				continue;
			visitPos(iter.value(), v);
		}
	}

	public void iterateFromTo(int from, boolean fromInclusive, int to,
			boolean toInclusive, IntVisitor v) {
		TIntIntIterator iter = iIndex().iterator();
		while (iter.hasNext()) {
			iter.advance();
			int k = iter.key();
			if (fromInclusive && k < from)
				continue;
			if (!fromInclusive && k <= from)
				continue;
			if (toInclusive && k > to)
				continue;
			if (!toInclusive && k >= to)
				continue;
			visitPos(iter.value(), v);
		}
	}

	// long-type key
	@SuppressWarnings({ "unchecked", "rawtypes" })
	TLongIntHashMap lIndex() {
		if (index == null) {
			index = new TLongIntHashMap(32, 0.75f, -1, -1);
		}
		return (TLongIntHashMap) index;
	}

	public void add(long key, int pos) {
		if (!lIndex().contains(key)) {
			lIndex().put(key, pos);
		} else {
			int val = lIndex().get(key);
			if (val < 0) {
				int offset = -val - 1;
				getPosLists().getQuick(offset).add(pos);
			} else {
				int offset = getPosLists().size();
				MyIndexPosList list = new MyIndexPosList();
				list.add(val);
				list.add(pos);
				getPosLists().add(list);
				lIndex().put(key, -(offset + 1));
			}
		}
	}

	public boolean contains(long key) {
		return lIndex().containsKey(key);
	}

	public int get(long key) {
		return lIndex().get(key);
	}

	public void iterateBy(long key, IntVisitor v) {
		if (!lIndex().containsKey(key))
			return;
		int val = lIndex().get(key);
		visitPos(val, v);
	}

	public void iterateFrom(long from, boolean fromInclusive, IntVisitor v) {
		TLongIntIterator iter = lIndex().iterator();
		while (iter.hasNext()) {
			iter.advance();
			long k = iter.key();
			if (fromInclusive && k < from)
				continue;
			if (!fromInclusive && k <= from)
				continue;
			visitPos(iter.value(), v);
		}
	}

	public void iterateTo(long to, boolean toInclusive, IntVisitor v) {
		TLongIntIterator iter = lIndex().iterator();
		while (iter.hasNext()) {
			iter.advance();
			long k = iter.key();
			if (toInclusive && k > to)
				continue;
			if (!toInclusive && k >= to)
				continue;
			visitPos(iter.value(), v);
		}
	}

	public void iterateFromTo(long from, boolean fromInclusive, int to,
			boolean toInclusive, IntVisitor v) {
		TLongIntIterator iter = lIndex().iterator();
		while (iter.hasNext()) {
			iter.advance();
			long k = iter.key();
			if (fromInclusive && k < from)
				continue;
			if (!fromInclusive && k <= from)
				continue;
			if (toInclusive && k > to)
				continue;
			if (!toInclusive && k >= to)
				continue;
			visitPos(iter.value(), v);
		}
	}

	// float-type key
	@SuppressWarnings({ "unchecked", "rawtypes" })
	TFloatIntHashMap fIndex() {
		if (index == null) {
			index = new TFloatIntHashMap(32, 0.75f, -1, -1);
		}
		return (TFloatIntHashMap) index;
	}

	public void add(float key, int pos) {
		if (!fIndex().contains(key)) {
			fIndex().put(key, pos);
		} else {
			int val = fIndex().get(key);
			if (val < 0) {
				int offset = -val - 1;
				getPosLists().getQuick(offset).add(pos);
			} else {
				int offset = getPosLists().size();
				MyIndexPosList list = new MyIndexPosList();
				list.add(val);
				list.add(pos);
				getPosLists().add(list);
				fIndex().put(key, -(offset + 1));
			}
		}
	}

	public boolean contains(float key) {
		return fIndex().containsKey(key);
	}

	public int get(float key) {
		return fIndex().get(key);
	}

	public void iterateBy(float key, IntVisitor v) {
		if (!fIndex().containsKey(key))
			return;
		int val = fIndex().get(key);
		visitPos(val, v);
	}

	public void iterateFrom(float from, boolean fromInclusive, IntVisitor v) {
		TFloatIntIterator iter = fIndex().iterator();
		while (iter.hasNext()) {
			iter.advance();
			float k = iter.key();
			if (fromInclusive && k < from)
				continue;
			if (!fromInclusive && k <= from)
				continue;
			visitPos(iter.value(), v);
		}
	}

	public void iterateTo(float to, boolean toInclusive, IntVisitor v) {
		TFloatIntIterator iter = fIndex().iterator();
		while (iter.hasNext()) {
			iter.advance();
			float k = iter.key();
			if (toInclusive && k > to)
				continue;
			if (!toInclusive && k >= to)
				continue;
			visitPos(iter.value(), v);
		}
	}

	public void iterateFromTo(float from, boolean fromInclusive, float to,
			boolean toInclusive, IntVisitor v) {
		TFloatIntIterator iter = fIndex().iterator();
		while (iter.hasNext()) {
			iter.advance();
			float k = iter.key();
			if (fromInclusive && k < from)
				continue;
			if (!fromInclusive && k <= from)
				continue;
			if (toInclusive && k > to)
				continue;
			if (!toInclusive && k >= to)
				continue;
			visitPos(iter.value(), v);
		}
	}

	// double-type key
	@SuppressWarnings({ "unchecked", "rawtypes" })
	TDoubleIntHashMap dIndex() {
		if (index == null) {
			index = new TDoubleIntHashMap(32, 0.75f, -1, -1);
		}
		return (TDoubleIntHashMap) index;
	}

	public void add(double key, int pos) {
		if (!dIndex().contains(key)) {
			dIndex().put(key, pos);
		} else {
			int val = dIndex().get(key);
			if (val < 0) {
				int offset = -val - 1;
				getPosLists().getQuick(offset).add(pos);
			} else {
				int offset = getPosLists().size();
				MyIndexPosList list = new MyIndexPosList();
				list.add(val);
				list.add(pos);
				getPosLists().add(list);
				dIndex().put(key, -(offset + 1));
			}
		}
	}

	public boolean contains(double key) {
		return dIndex().containsKey(key);
	}

	public int get(double key) {
		return dIndex().get(key);
	}

	public void iterateBy(double key, IntVisitor v) {
		if (!dIndex().containsKey(key))
			return;
		int val = dIndex().get(key);
		visitPos(val, v);
	}

	public void iterateFrom(double from, boolean fromInclusive, IntVisitor v) {
		TDoubleIntIterator iter = dIndex().iterator();
		while (iter.hasNext()) {
			iter.advance();
			double k = iter.key();
			if (fromInclusive && k < from)
				continue;
			if (!fromInclusive && k <= from)
				continue;
			visitPos(iter.value(), v);
		}
	}

	public void iterateTo(double to, boolean toInclusive, IntVisitor v) {
		TDoubleIntIterator iter = dIndex().iterator();
		while (iter.hasNext()) {
			iter.advance();
			double k = iter.key();
			if (toInclusive && k > to)
				continue;
			if (!toInclusive && k >= to)
				continue;
			visitPos(iter.value(), v);
		}
	}

	public void iterateFromTo(double from, boolean fromInclusive, double to,
			boolean toInclusive, IntVisitor v) {
		TDoubleIntIterator iter = dIndex().iterator();
		while (iter.hasNext()) {
			iter.advance();
			double k = iter.key();
			if (fromInclusive && k < from)
				continue;
			if (!fromInclusive && k <= from)
				continue;
			if (toInclusive && k > to)
				continue;
			if (!toInclusive && k >= to)
				continue;
			visitPos(iter.value(), v);
		}
	}

	// object-type key
	@SuppressWarnings({ "unchecked", "rawtypes" })
	TObjectIntHashMap oIndex() {
		if (index == null) {
			index = new TObjectIntHashMap(32, 0.75f, -1);
		}
		return (TObjectIntHashMap) index;
	}

	public void add(Object key, int pos) {
		if (!oIndex().contains(key)) {
			oIndex().put(key, pos);
		} else {
			int val = oIndex().get(key);
			if (val < 0) {
				int offset = -val - 1;
				getPosLists().getQuick(offset).add(pos);
			} else {
				int offset = getPosLists().size();
				MyIndexPosList list = new MyIndexPosList();
				list.add(val);
				list.add(pos);
				getPosLists().add(list);
				oIndex().put(key, -(offset + 1));
			}
		}
	}

	public boolean contains(Object key) {
		return oIndex().containsKey(key);
	}

	public int get(Object key) {
		return oIndex().get(key);
	}

	public void iterateBy(Object key, IntVisitor v) {
		if (!oIndex().containsKey(key))
			return;
		int val = oIndex().get(key);
		visitPos(val, v);
	}

	public void iterateFrom(Object _from, boolean fromInclusive, IntVisitor v) {
		if (!(_from instanceof Comparable)) {
			L.warn("Object " + _from +" cannot be converted to Comparable type");
			return;
		}
		Comparable from = (Comparable) _from;
		TObjectIntIterator iter = oIndex().iterator();
		while (iter.hasNext()) {
			iter.advance();
			Comparable k = (Comparable) iter.key();
			if (fromInclusive && k.compareTo(from) < 0)
				continue;
			if (!fromInclusive && k.compareTo(from) <= 0)
				continue;
			visitPos(iter.value(), v);
		}
	}

	public void iterateTo(Object _to, boolean toInclusive, IntVisitor v) {
		if (!(_to instanceof Comparable)) {
			L.warn("Object " + _to + " cannot be converted to Comparable type");
			return;
		}
		Comparable to = (Comparable) _to;
		TIntIntIterator iter = iIndex().iterator();
		while (iter.hasNext()) {
			iter.advance();
			Comparable k = (Comparable) iter.key();
			if (toInclusive && k.compareTo(to) > 0)
				continue;
			if (!toInclusive && k.compareTo(to) >= 0)
				continue;
			visitPos(iter.value(), v);
		}
	}

	public void iterateFromTo(Object _from, boolean fromInclusive, Object _to,
			boolean toInclusive, IntVisitor v) {
		if (!(_from instanceof Comparable)) {
			L.warn("Object " + _from +" cannot be converted to Comparable type");
			return;
		}
		Comparable from = (Comparable) _from;
		if (!(_to instanceof Comparable)) {
			L.warn("Object " + _to + " cannot be converted to Comparable type");
			return;
		}
		Comparable to = (Comparable) _to;
		TIntIntIterator iter = iIndex().iterator();
		while (iter.hasNext()) {
			iter.advance();
			Comparable k = (Comparable) iter.key();
			if (fromInclusive && k.compareTo(from) < 0)
				continue;
			if (!fromInclusive && k.compareTo(from) <= 0)
				continue;
			if (toInclusive && k.compareTo(to) > 0)
				continue;
			if (!toInclusive && k.compareTo(to) >= 0)
				continue;
			visitPos(iter.value(), v);
		}
	}
}