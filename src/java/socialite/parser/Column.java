package socialite.parser;

import java.io.*;

import socialite.parser.antlr.*;
import socialite.util.Assert;
import socialite.util.InternalException;
import socialite.util.SociaLiteException;
import socialite.type.Utf8;

import javax.naming.OperationNotSupportedException;


public class Column implements Externalizable {
    private static final long serialVersionUID = 1;

    ColumnDecl decl;
    boolean indexed=false;
    boolean sorted=false;
    boolean asc=false;
    boolean ordered=false;
    boolean isConst=false;
    Object constVal=null;
    int relPos=-1;
    int bitMask=0xffffffff;
    int bitShift=0;
    int pcolIdx=-1;

    public Column() { }
    public Column(ColumnDecl _decl) {
        decl=_decl;
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

    public int position() { return decl.pos(); }

    public String name() { return decl.name(); }
    public Class type() { return decl.type(); }
    public Class getComponentType() {
        if (type().isArray()) { return type().getComponentType(); }
        else { return type(); }
    }

    public boolean isPrimitive() { return type().isPrimitive(); }
    public boolean isImmutable() { // used by template
        if (isPrimitive()) {
            return true;
        } else if (type().equals(String.class)) {
            return true;
        } else if (type().equals(Utf8.class)) {
            return true;
        } else {
            return false;
        }
    }
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

    public boolean isArrayIndex() { return hasRange();}
    public boolean isIndexed() { return indexed || hasRange();}
    public boolean isSorted() { return sorted || isArrayIndex(); }
    public boolean isSortedAsc() { return sorted; }
    public boolean isSortedDesc() { return false; }
    public boolean isOrdered() { return ordered; }

    public boolean hasRange() {
        return (decl.option() instanceof ColRange);
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
        return (decl.option() instanceof ColIter);
    }
    public boolean hasNumBits() {
        return (decl.option() instanceof ColBit);
    }
    public int getNumBits() {
        if (!hasNumBits()) {
            throw new AssertionError("Column["+name()+"] does not have the bit option");
        }
        return ((ColBit)decl.option()).getNumBits();
    }
    public void setBitMaskAndShift(int mask, int shift) {
        bitMask = mask;
        bitShift = shift;
    }
    public int getBitMask() { return bitMask; }
    public int getBitShift() { return bitShift; }
    public void setPcolIdx(int idx) { pcolIdx = idx;}
    public int getPcolIdx() { return pcolIdx;}
    public boolean isBitPacked() { return hasNumBits(); }

    public String toString() {
        return type().getSimpleName()+":"+name()+"@"+position();
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        decl = new ColumnDecl();
        decl.readExternal(in);
        byte encoded = in.readByte();
        if ((encoded & 0x01)>0) indexed = true;
        if ((encoded & 0x02)>0) sorted = true;
        if ((encoded & 0x04)>0) asc = true;
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
        if (indexed) encoded |= 0x01;
        if (sorted) encoded |= 0x02;
        if (asc) encoded |= 0x04;
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
