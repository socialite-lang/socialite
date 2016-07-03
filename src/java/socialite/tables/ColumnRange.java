package socialite.tables;

public class ColumnRange extends ColumnValue {
    Number val2;
    public ColumnRange(int _col, Number _from, Number _to) {
        super(_col, _from);
        setRange(_from, _to);
    }
    public int getColumn() { return col; }
    public Number getFrom() { return (Number)val; }
    public Number getTo() { return val2; }
    public void setRange(Number from, Number to) {
        val = from;
        val2 = to;
        if (val == null) {
            if (val2 instanceof Integer) {
                val = Integer.MIN_VALUE;
            } else if (val2 instanceof Long) {
                val = Long.MIN_VALUE;
            } else if (val2 instanceof Float) {
                val = Float.MIN_VALUE;
            } else {
                val = Double.MIN_VALUE;
            }
        } else if (val2 == null) {
            if (val instanceof Integer) {
                val2 = Integer.MAX_VALUE;
            } else if (val instanceof Long) {
                val2 = Long.MAX_VALUE;
            } else if (val instanceof Float) {
                val2 = Float.MAX_VALUE;
            } else {
                val2 = Double.MAX_VALUE;
            }
        }
    }

    public String toString() {
        return col+":"+val+"~"+val2;
    }
}
