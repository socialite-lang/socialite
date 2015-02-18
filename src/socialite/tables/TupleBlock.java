package socialite.tables;


import socialite.resource.TableSliceMap;

import java.util.Arrays;
import java.util.Iterator;

public class TupleBlock {
    final int tableid;
    final Class[] types;
    final Object[] columns;
    final Tuple tuple;
    final int length;

    public TupleBlock(TupleWriteStreamLocal s) {
        tableid = s.tableid;
        types = s.types;
        tuple = s.tuple.clone();
        columns = new Object[types.length];

        length = s.length;
        for (int i=0; i<columns.length; i++) {
            Class type = types[i];
            if (type.equals(int.class)) {
                columns[i] = Arrays.copyOf((int[])s.columns[i], length);
            } else if (type.equals(long.class)) {
                columns[i] = Arrays.copyOf((long[])s.columns[i], length);
            } else if (type.equals(float.class)) {
                columns[i] = Arrays.copyOf((float[]) s.columns[i], length);
            } else if (type.equals(double.class)) {
                columns[i] = Arrays.copyOf((double[]) s.columns[i], length);
            } else {
                columns[i] = Arrays.copyOf((Object[])s.columns[i], length);
            }
        }
    }
    public int getTableId() { return tableid; }

    public Iterator<Tuple> iterator(final TableSliceMap sliceMap) {
        return iterator(sliceMap, -1);
    }
    public Iterator<Tuple> iterator(final TableSliceMap sliceMap, final int sliceIdx) {
        return new Iterator<Tuple>() {
            Tuple t = tuple.clone();
            int pos = 0;
            public boolean hasNext() {
                while (true) {
                    if (pos == length) return false;
                    if (isMatch(pos, tableid, sliceIdx))
                        return true;
                    pos++;
                }
            }
            boolean isMatch(int pos, int tableId, int sliceIdx) {
                if (sliceIdx < 0) return true;
                Class type = types[0];
                if (type.equals(int.class)) {
                    int val = ((int[])columns[0])[pos];
                    return sliceMap.getIndex(tableId, val) == sliceIdx;
                } else if (type.equals(long.class)) {
                    long val = ((long[])columns[0])[pos];
                    return sliceMap.getIndex(tableId, val) == sliceIdx;
                } else if (type.equals(float.class)) {
                    float val = ((float[])columns[0])[pos];
                    return sliceMap.getIndex(tableId, val) == sliceIdx;
                } else if (type.equals(double.class)) {
                    double val = ((double[])columns[0])[pos];
                    return sliceMap.getIndex(tableId, val) == sliceIdx;
                } else {
                    Object val = ((Object[])columns[0])[pos];
                    return sliceMap.getIndex(tableId, val) == sliceIdx;
                }
            }
            public Tuple next() {
                for (int i=0; i<t.size(); i++) {
                    Class type = types[i];
                    if (type.equals(int.class)) {
                        int v = ((int[])columns[i])[pos];
                        t.setInt(i, v);
                    } else if (type.equals(long.class)) {
                        long v = ((long[])columns[i])[pos];
                        t.setLong(i, v);
                    } else if (type.equals(float.class)) {
                        float v = ((float[])columns[i])[pos];
                        t.setFloat(i, v);
                    } else if (type.equals(double.class)) {
                        double v = ((double[])columns[i])[pos];
                        t.setDouble(i, v);
                    } else {
                        Object v = ((Object[])columns[i])[pos];
                        t.setObject(i, v);
                    }
                }
                pos++;
                return t;
            }
            public void remove() { }
        };
    }

}

