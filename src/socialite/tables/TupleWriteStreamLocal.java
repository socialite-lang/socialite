package socialite.tables;

import socialite.eval.ConcurrentLoadCommand;
import socialite.eval.Manager;

import java.lang.reflect.Array;

public class TupleWriteStreamLocal implements TupleWriteStream {
    static final int CHUNK_SIZE = 64*1024;
    final int tableid;
    final Class[] types;
    Object[] columns;
    volatile int length;
    final Tuple tuple;
    TupleWriteStreamLocal(int _tableid, Class[] _types) {
        this(_tableid, _types, Tuple.create(_types));
    }
    TupleWriteStreamLocal(int _tableid, Class[] _types, Tuple _tuple) {
        tableid = _tableid;
        types = new Class[_types.length];
        columns = new Object[_types.length];
        for (int i=0; i<_types.length; i++) {
            types[i] = _types[i];
            Class type = types[i];
            if (!type.isPrimitive())
                type = Object.class;
            columns[i] = Array.newInstance(type, CHUNK_SIZE);
        }
        tuple = _tuple;
    }
    public boolean write(Object ...args) {
        if (args == null) {
            flush();
            return true;
        }
        for (int i=0; i<columns.length; i++) {
            Class type = types[i];
            if (type.equals(int.class)) {
                ((int[])columns[i])[length] = (Integer)args[i];
            } else if (type.equals(long.class)) {
                ((long[])columns[i])[length] = (Long)args[i];
            } else if (type.equals(float.class)) {
                ((float[])columns[i])[length] = (Float)args[i];
            } else if (type.equals(double.class)) {
                ((double[])columns[i])[length] = (Double)args[i];
            } else {
                ((Object[])columns[i])[length] = args[i];
            }
        }
        length++;
        if (length == CHUNK_SIZE) { flush(); }
        return true;
    }
    public boolean write(Tuple t) {
        if (t == null) {
            flush();
            return true;
        }
        for (int i=0; i<t.size(); i++) {
            Class type = types[i];
            if (type.equals(int.class)) {
                ((int[])columns[i])[length] = t.getInt(i);
            } else if (type.equals(long.class)) {
                ((long[])columns[i])[length] = t.getLong(i);
            } else if (type.equals(float.class)) {
                ((float[])columns[i])[length] = t.getFloat(i);
            } else if (type.equals(double.class)) {
                ((double[])columns[i])[length] = t.getDouble(i);
            } else {
                ((Object[])columns[i])[length] = t.get(i);
            }
        }
        length++;
        if (length == CHUNK_SIZE) { flush(); }
        return true;
    }
    public void flush() {
        if (length==0) return;
        TupleBlock block = new TupleBlock(this);
        try {
            Manager.getInst().addCmd(new ConcurrentLoadCommand(block));
        } catch (InterruptedException e) { }
        length = 0;
    }
    public void close() {
        flush();
        return;
    }
    public int getTableId() { return tableid; }
}