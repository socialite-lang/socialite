package socialite.tables;

import java.util.Iterator;

import java.io.Externalizable;

import socialite.visitors.IVisitor;

public interface TableInst {
	public int id();
	public boolean isEmpty();
	public void clear();
	public void clear(int from, int to);
    public String name();
	public int totalAllocSize(); // total allocated memory.
}