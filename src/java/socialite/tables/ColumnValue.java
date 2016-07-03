package socialite.tables;

public class ColumnValue {
    int col;
    Object val;
    public ColumnValue(int _col, Object _val) {
        col = _col;
        val = _val;
    }
    public int getColumn() { return col; }
    public Object getValue() { return val; }

    public Integer getValue(int unused) { return (Integer)val; }
    public Long getValue(long unused) { return (Long)val; }
    public Float getValue(float unused) { return (Float)val; }
    public Double getValue(double unused) { return (Double)val; }
    public Object getValue(Object unused) { return val; }

    public String toString() {
        return col+","+val;
    }
}
