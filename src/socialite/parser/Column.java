package socialite.parser;

import java.io.*;

import socialite.parser.antlr.ColumnDecl;
import socialite.parser.antlr.ColIter;
import socialite.parser.antlr.ColOpt;
import socialite.parser.antlr.ColRange;
import socialite.util.Assert;
import socialite.util.InternalException;
import socialite.util.SociaLiteException;


public class Column implements Externalizable {
	private static final long serialVersionUID = 1;

	ColumnDecl decl;
	boolean isPrimShard;
	boolean indexed=false;
	boolean sorted=false;
	boolean asc=false;
	boolean ordered=false;
	boolean isConst=false;
	Object constVal=null;	
	int relPos=-1;

    public Column() { }
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
	
	public int getRelPos() { // used by StringTemplate
		Assert.true_(relPos!=-1);
		return relPos; 
	}
	
	public int getAbsPos() { return decl.pos(); } // used by StringTemplate
	
	public void setPrimaryShardIndex(boolean f) { 
		isPrimShard = f; 
	}
	public boolean isPrimaryShard() { return isPrimShard; }
	public int position() { return decl.pos(); }
	public String name() { return decl.name(); }
	public Class type() { return decl.type(); }
  public Class getComponentType() {
      if (type().isArray()) { return type().getComponentType(); }
      else { return type(); }
  }
	
	public boolean isPrimitive() { return type().isPrimitive(); }
	public boolean isConst() { return isConst; }
	public void setConst(Object _constVal) {
		isConst = true;
		constVal = _constVal;
	}
	public Object getConstVal() {
		assert isConst;
		return constVal;
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
	public boolean isSortedAsc() { return sorted; }
	public boolean isSortedDesc() { return false; }
	public boolean isOrdered() { return ordered; }
	
	public boolean hasRange() {
		ColOpt opt = decl.option();
		if (opt instanceof ColRange) return true;
		return false;
	}
	
	public void setRange(int from, int to) {
		ColRange range = (ColRange)decl.option();
		range.set(from, to);
	}
	public int[] getRange() {
		int range[] = new int[2];
		ColRange _range = (ColRange)decl.option();
		range[0] = _range.from();
		range[1] = _range.to();
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

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        decl = new ColumnDecl();
        decl.readExternal(in);
        byte encoded = in.readByte();
        if ((encoded & 0x01)>0) isPrimShard = true;
        if ((encoded & 0x02)>0) indexed = true;
        if ((encoded & 0x04)>0) sorted = true;
        if ((encoded & 0x08)>0) asc = true;
        if ((encoded & 0x10)>0) ordered = true;
        if ((encoded & 0x20)>0) isConst = true;

        if (in.readBoolean()) {
            constVal = in.readObject();
        }
        relPos = in.readInt();
    }
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        byte encoded=0;
        decl.writeExternal(out);
        if (isPrimShard) encoded |= 0x01;
        if (indexed) encoded |= 0x02;
        if (sorted) encoded |= 0x04;
        if (asc) encoded |= 0x08;
        if (ordered) encoded |= 0x10;
        if (isConst) encoded |= 0x20;
        out.writeByte(encoded);

        if (constVal==null) {
            out.writeBoolean(false);
        } else {
            out.writeBoolean(true);
            out.writeObject(constVal);
        }
        out.writeInt(relPos);
    }
}
