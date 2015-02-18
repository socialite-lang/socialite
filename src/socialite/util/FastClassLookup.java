package socialite.util;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

import socialite.dist.msg.WorkerMessage;
import socialite.eval.EvalWithTable;
import socialite.parser.*;
import socialite.tables.TableInst;
import socialite.type.Utf8;


public class FastClassLookup implements Externalizable {
	private static final long serialVersionUID = 1;
	
	static class ClassArray {
		Class[] array;
		ClassArray(Class[] old, int size) { array = Arrays.copyOf(old, size); }
	}
	Class[] classLookup; 
	int pos=0;
	
	public FastClassLookup() {		
		classLookup = new Class[32];
		init();
	}
	void init() {
		classLookup[0] = WorkerMessage.class;
		classLookup[1] = TableInst.class;
		classLookup[2] = EvalWithTable.class;
		classLookup[3] = int.class;
		classLookup[4] = long.class;
		classLookup[5] = float.class;
		classLookup[6] = double.class;				
		classLookup[7] = String.class;
		classLookup[8] = Utf8.class;
		pos=9;
	}
    void initForSysResource() {
        classLookup[0] = Predicate.class;
        classLookup[1] = DeltaPredicate.class;
        classLookup[2] = PrivPredicate.class;
        classLookup[3] = Expr.class;
        classLookup[4] = AssignOp.class;
        classLookup[5] = AssignDotVar.class;
        classLookup[6] = BinOp.class;
        classLookup[7] = CmpOp.class;
        classLookup[8] = UnaryMinus.class;
        classLookup[9] = TypeCast.class;
        pos=10;
    }
	public FastClassLookup(boolean lookupSysRes) {
		classLookup = new Class[32];
		
		if (lookupSysRes) { initForSysResource(); }
		else { init(); }
	}
		
	public int size() {
		return pos;
	}
	public boolean contains(Class cls) {
		for (int i=0; i<pos; i++) {
			if (classLookup[i].equals(cls))
				return true;
		}
		return false;
	}
	public void addClass(Class cls) {
		if (contains(cls)) return;
		
		ensureCapacity(pos+1);
		classLookup[pos] = cls;
		pos++;
	}
	
	public Class getClass(int idx) {
		assert idx < pos;
		return classLookup[idx];
	}
	public int getIdx(Class cls) {
		for (int i=0; i<pos; i++) {
			if (cls.equals(classLookup[i]))
				return i;
		}
		return -1;
	}
	
	synchronized void ensureCapacity(int minCapacity) {
		int oldCapacity = classLookup.length;
		if (minCapacity > oldCapacity) {
			int newCapacity = (int)Math.max(minCapacity, oldCapacity*1.5);
			classLookup = new ClassArray(classLookup, newCapacity).array;
		}
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		classLookup = (Class[])in.readObject();
		pos=in.readInt();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(pos);
		for (int i=0; i<pos; i++) {
			String name=classLookup[i].getName();
			out.writeInt(name.length());
			out.writeChars(name);			
		}
	}
}
