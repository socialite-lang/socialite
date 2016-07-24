package socialite.tables;

public class ColumnRange implements Constraint {
    int col;
    long lfrom, lto;
    double dfrom, dto;
    Object from, to;

    public ColumnRange(int _col, int _from, int _to) {
        col = _col;
        setRange(_from, _to);
    }
    public ColumnRange(int _col, long _from, long _to) {
        col = _col;
        setRange(_from, _to);
    }
    public ColumnRange(int _col, float _from, float _to) {
        col = _col;
        setRange(_from, _to);
    }
    public ColumnRange(int _col, double _from, double _to) {
        col = _col;
        setRange(_from, _to);
    }
    public ColumnRange(int _col, Object _from, Object _to) {
        col = _col;
        setRange(_from, _to);
    }
    public int getColumn() { return col; }

    public int getFrom(int unused) { return (int)lfrom; }
    public int getTo(int unused) { return (int)lto; }

    public long getFrom(long unused) { return lfrom; }
    public long getTo(long unused) { return lto; }

    public float getFrom(float unused) { return (float)dfrom; }
    public float getTo(float unused) { return (float)dto; }

    public double getFrom(double unused) { return dfrom; }
    public double getTo(double unused) { return dto; }

    public void setRange(int from, int to) {
        lfrom = from;
        lto = to;
    }
    public void setRange(long from, long to) {
        lfrom = from;
        lto = to;
    }
    public void setRange(float from, float to) {
        dfrom = from;
        dto = to;
    }
    public void setRange(double from, double to) {
        dfrom = from;
        dto = to;
    }
    public void setRange(Object _from, Object _to) {
        from = _from;
        to = _to;
    }

}
