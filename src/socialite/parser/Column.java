package socialite.parser;

import java.io.Serializable;

import socialite.parser.antlr.ColumnDecl;
import socialite.parser.antlr.ColIter;
import socialite.parser.antlr.ColOpt;
import socialite.parser.antlr.ColRange;
import socialite.parser.antlr.ColSize;
import socialite.util.Assert;
import socialite.util.InternalException;
import socialite.util.SociaLiteException;


public class Column implements Serializable {
	private static final long serialVersionUID = 1;
	static final int DEFAULT_SIZE = 1024*4;
	static final int DEFAULT_NESTED_SIZE = 32;
	
	final ColumnDecl decl;
	boolean isPrimShard;
	boolean indexed=false;
	boolean sorted=false;
	boolean asc=false;
	boolean ordered=false;
	boolean isConst=false;
	Object constVal=null;	
	int relPos=-1;
	boolean isAtomicArray=false;
	
	public Column(ColumnDecl _decl) { 
		decl=_decl;
		isPrimShard = false;
	}
	public Column(ColumnDecl _decl, boolean primShardIdx) { 
		decl=_decl;
		isPrimShard = primShardIdx;
		assert(isPrimShard);
	}
	
	public int hashCode() {
		int h=0;
		h ^= decl.hashCode();
		return h;
	}
	public boolean equals(Object o) {
		if (!(o instanceof Column)) return false;
		
		Column c=(Column)o;
		if (!decl.equals(c.decl)) return false;
		if (isPrimShard!=c.isPrimShard) return false;
		if (indexed!=c.indexed) return false;
		if (sorted!=c.sorted) return false;
		if (asc!=c.asc) return false;
		if (ordered!=c.ordered) return false;
		if (isConst!=c.isConst) return false;
		if (constVal==null) { if (c.constVal!=null) return false;
		} else { if (!constVal.equals(c.constVal)) return false; }
		if (relPos!=c.relPos) return false;
		return true;
	}
	public void setRelPos(int idx) {
		Assert.true_(relPos==-1);
		relPos=idx;
	}
	// For StringTemplate
	public int getRelPos() {
		Assert.true_(relPos!=-1);
		return relPos; 
	}
	// For StringTemplate
	public int getAbsPos() { return decl.pos(); }
	
	public void setPrimaryShardIndex(boolean f) { 
		isPrimShard = f; 
	}
	public boolean isPrimaryShard() { return isPrimShard; }
	public int position() { return decl.pos(); }
	public String name() { return decl.name(); }
	public Class type() { return decl.type(); }
	
	public boolean isConst() { return isConst; }
	public void setConst(Object _constVal) {
		isConst = true;
		constVal = _constVal;
	}
	public Object getConstVal() {
		assert isConst;
		return constVal;
	}
	public void setAtomicArray() {
		isAtomicArray=true;
	}
	public boolean isAtomicArray() { 
		return isAtomicArray;
	}
		
	// For StringTemplate
	public String getName() { return name(); }
	public String getType() { return type().getSimpleName(); }
	public String getCompType() {
		return type().getComponentType().getSimpleName(); 
	}
	
	public int getTypeSize() {
		if (isConst) return 0;
		return decl.getTypeSize();		
	}
	
	public void setIndexed() { indexed=true; }
	public void setSorted(boolean _asc) { 
		sorted=true;
		asc=_asc;
	}	
	public void setOrdered() { ordered=true; }	

	public boolean isArrayType() { return type().isArray();}
	public boolean isArrayIndex() { return hasRange();}
	public boolean isIndexed() { return isPrimShard || indexed || hasRange();}
	public boolean isSorted() { return sorted; }
	public boolean isSortedAsc() { return sorted && asc; }
	public boolean isSortedDesc() { return sorted && !asc; }
	public boolean isOrdered() { return ordered; }
	public boolean hasSize() {
		ColOpt opt = decl.option();
		if (opt instanceof ColSize) return true;
		return false;
	}
	public int getSize() {
		if (hasSize()) {
			ColSize opt = (ColSize)decl.option();
			return opt.size();
		} else {
			if (getAbsPos()==0) return DEFAULT_SIZE;
			else return DEFAULT_NESTED_SIZE;
		}
	}
	public void setSize(int size) {
		ColSize sz = new ColSize(size);
		decl.setOption(sz);
	}
	
	public boolean hasRange() {
		ColOpt opt = decl.option();
		if (opt instanceof ColRange) return true;
		return false;
	}
	
	public void setRange(int from, int to) {
		ColRange vrange = (ColRange)decl.option();
		vrange.set(from, to);
	}
	public int[] getRange() {
		int range[] = new int[2];
		ColRange vrange = (ColRange)decl.option();
		range[0] = vrange.from();
		range[1] = vrange.to();
		return range;
	}
	public boolean isIter() {
		if (decl.option() instanceof ColIter) 
			return true;
		return false;
	}
	public String toString() {
		return type().getSimpleName()+":"+name()+"@"+position();
	}
}
