package socialite.collection;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import java.util.ListIterator;
import java.util.RandomAccess;

public class ArrayList<E> extends AbstractCollection<E>
implements List<E>, RandomAccess, Cloneable, java.io.Serializable
{
	private static final long serialVersionUID = 42L;

	protected transient Object[] elems;
	protected int size;

	public ArrayList(int initCapacity) {
		super();
		this.elems = new Object[initCapacity];
	}
	public ArrayList() { this(10); }

	public ArrayList(Collection<? extends E> c) {
		size = c.size();
		Object[] tmp = c.toArray();

		if (tmp.getClass() == Object[].class) {
			elems = tmp;
		} else {
			elems = Arrays.copyOf(elems, size, Object[].class);
		}
	}

	public void trimToSize() {
		int oldCapacity = elems.length;
		if (size < oldCapacity) {
			elems = Arrays.copyOf(elems, size);
		}
	}

	public void ensureCapacity(int minCapacity) {
		int oldCapacity = elems.length;
		if (minCapacity > oldCapacity) {
			@SuppressWarnings("unused")
			Object oldData[] = elems;
			int newCapacity = (oldCapacity*3)/2+1;
			if (newCapacity < minCapacity)
				newCapacity = minCapacity;
			elems = Arrays.copyOf(elems, newCapacity);
		}
	}

	public int size() { return size; }

	public boolean isEmpty() { return size == 0; }

	public boolean contains(Object o) { 
		return indexOf(o) >= 0;
	}

	public int indexOf(Object o) {
		if (o == null) {
			for (int i=0; i<size; i++)
				if (elems[i]==null)
					return i;
		} else {
			for (int i=0; i < size; i++)
				if (o.equals(elems[i]))
					return i;
		}
		return -1;
	}

	public int lastIndexOf(Object o) {
		if (o == null) {
			for (int i=size-1; i>=0; i--)
				if (elems[i]==null)
					return i;
		} else {
			for (int i=size-1; i>=0; i--)
				if (o.equals(elems[i]))
					return i;
		}
		return -1;
	}

	public Object clone() {
		try {
			@SuppressWarnings("unchecked")
			ArrayList<E> v = (ArrayList<E>) super.clone();
			v.elems = Arrays.copyOf(elems, size);
			return v;
		} catch (CloneNotSupportedException e) {
			return null; // should not happen
		}
	}

	public Object[] toArray() {
		return Arrays.copyOf(elems, size);
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		if (a.length < size) {
			return (T[]) Arrays.copyOf(elems, size, a.getClass());
		}
		System.arraycopy(elems, 0, a, 0, size);
		if (a.length > size)
			a[size] = null;
		return a;
	}
	@SuppressWarnings("unchecked")
	public E get(int idx) {
		checkBounds(idx);
		return (E) elems[idx];
	}

	public E set(int idx, E e) {
		checkBounds(idx);
		@SuppressWarnings("unchecked")
		E oldVal = (E) elems[idx];
		elems[idx] = e;
		return oldVal;
	}

	public boolean add(E e) {
		ensureCapacity(size + 1);  // Increments modCount!!
		elems[size++] = e;
		return true;
	}

	public void add(int idx, E e) {
		if (idx<0||idx>size) { 
			throw new IndexOutOfBoundsException("Index:"+idx+", Size:"+size); 
		}
		
		ensureCapacity(size+1);
		System.arraycopy(elems, idx, elems, idx+1, size-idx);
		elems[idx] = e;
		size++;
	}

	public E remove(int idx) {
		checkBounds(idx);
		@SuppressWarnings("unchecked")
		E oldValue = (E)elems[idx];
		removeReallyAt(idx);
		return oldValue;
	}

	public boolean remove(Object o) {
		if (o == null) {
			for (int i = 0; i<size; i++) {
				if (elems[i] == null) {
					removeReallyAt(i);
					return true;
				}
			}
		} else {
			for (int i = 0; i<size; i++) {
				if (o.equals(elems[i])) {
					removeReallyAt(i);
					return true;
				}
			}
		}
		return false;
	}

	void removeReallyAt(int idx) {
		int shiftNum = size-idx-1;
		if (shiftNum>0)
			System.arraycopy(elems, idx+1, elems, idx, shiftNum);
		elems[--size] = null;
	}

	public void clear() {
		for (int i=0; i<size; i++)
			elems[i] = null;
		size = 0;
	}

	public boolean addAll(Collection<? extends E> c) {
		Object[] tmp = c.toArray();
		int added = tmp.length;
		ensureCapacity(size + added);
		System.arraycopy(tmp, 0, elems, size, added);
		size += added;
		return added != 0;
	}

	public boolean addAll(int idx, Collection<? extends E> c) {
		if (idx<0||idx>size) { 
			throw new IndexOutOfBoundsException("Index:"+idx+", Size:"+size); 
		}

		Object[] tmp = c.toArray();
		int added = tmp.length;
		ensureCapacity(size + added);
		
		int shiftNum = size - idx;
		if (shiftNum>0)
			System.arraycopy(elems, idx, elems, idx + added, shiftNum);

		System.arraycopy(tmp, 0, elems, idx, added);
		size += added;
		return added != 0;
	}

	void checkBounds(int idx) {
		if (idx >= size) {
			throw new IndexOutOfBoundsException("Index:"+idx+", Size:"+size);
		}
	}
	
    public List<E> subList(int from, int to) {
        throw new UnsupportedOperationException();
    }
    public Iterator<E> iterator() {
    	return new Itr();
    }
    
    private class Itr implements Iterator<E> {
    	int pos=0;
    	public Itr() {}
		@Override
		public boolean hasNext() {
			return pos<size;
		}

		@SuppressWarnings("unchecked")
		@Override
		public E next() { return (E)elems[pos++]; }

		@Override
		public void remove() {}
    }
    
    public ListIterator<E> listIterator() {
    	throw new UnsupportedOperationException();
    }
    public ListIterator<E> listIterator(int from) {
    	throw new UnsupportedOperationException();
    }

	private void writeObject(java.io.ObjectOutputStream s)
	throws java.io.IOException{
		s.defaultWriteObject();
		
		s.writeInt(elems.length);

		Object[] tmp = elems;
		for (int i=0; i<size; i++)
			s.writeObject(tmp[i]);
	}

	private void readObject(java.io.ObjectInputStream s)
	throws java.io.IOException, ClassNotFoundException {
		s.defaultReadObject();

		int arrayLength = s.readInt();
		elems = new Object[arrayLength];
		Object[] tmp = elems;
		for (int i=0; i<size; i++)
			tmp[i] = s.readObject();
	}
}