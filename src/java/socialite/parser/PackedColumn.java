package socialite.parser;

import socialite.util.BitUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackedColumn {
    List<Column> cols;
    Class type;
    int pos;
    Map<BitPos, Column> bitColumnMap;
    int usedBits;

    public PackedColumn(Column c, int _pos) {
        cols = new ArrayList<>();
        type = c.type();
        pos = _pos;
        addColumn(c);
    }
    public PackedColumn(Class _type, int _pos) {
        cols = new ArrayList<>();
        type = _type;
        pos = _pos;
        bitColumnMap = new HashMap<>();
        usedBits = 0;
    }

    public void addColumn(Column c) {
        cols.add(c);
        c.setPcolIdx(pos);
        if (c.hasNumBits()) {
            int nbits = c.getNumBits();
            BitPos bpos = new BitPos(usedBits, usedBits + nbits);
            int mask = BitUtils.getIntBitMask(bpos.from, bpos.to);
            c.setBitMaskAndShift(mask, bpos.from);
            bitColumnMap.put(bpos, c);
            usedBits += nbits;
        }
    }

    public List<Column> getColumns() {
        return cols;
    }

    public int getPos() {
        return pos;
    }

    public String getType() {
        return type.getSimpleName();
    }

    static class BitPos {
        int from;
        int to;
        BitPos(int _from, int _to) {
            from = _from;
            to = _to;
        }
        public boolean equals(Object o) {
            if (o instanceof BitPos) {
                BitPos b = (BitPos)o;
                return from == b.from  && to == b.to;
            }
            return false;
        }
        public int hashCode() {
            return from ^ to;
        }
    }
}
