package socialite.tables;

public class ColumnValue implements Constraint {
    int col;
    int ival;
    long lval;
    Object val;
    Class type;
    public ColumnValue(int _col, int _ival) {
        col = _col;
        ival = _ival;
        type = int.class;
    }
    public ColumnValue(int _col, long _lval) {
        col = _col;
        lval = _lval;
        type = long.class;
    }
    public ColumnValue(int _col, Object _val) {
        col = _col;
        val = _val;
        if (val == null) {
            type = Object.class;
        } else {
            type = val.getClass();
        }
    }
    public int getColumn() { return col; }
    public Object getValue() { return val; }
    public Class getType() { return type; }

    public ColumnValue setValue(int _col, int _ival) {
        col = _col;
        ival = _ival;
        return this;
    }
    public ColumnValue setValue(int _col, long _lval) {
        col = _col;
        lval = _lval;
        return this;
    }
    public ColumnValue setValue(int _col, Object _val) {
        col = _col;
        val = _val;
        return this;
    }

    public int getValue(int unused) { return ival; }
    public long getValue(long unused) { return lval; }
    public Object getValue(Object unused) { return val; }

    public String toString() {
        return col+","+val;
    }
}
