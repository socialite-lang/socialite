package socialite.tables;

import java.util.ArrayList;
import java.util.Iterator;

public class ColumnConstraints implements Iterable<ColumnValue> {
    ArrayList<ColumnValue> constraints;
    ColumnValue first;
    ColumnValue second;
    ColumnRange range;

    public ColumnConstraints() {
        constraints = new ArrayList(2);
        first = new ColumnValue(-1, -1);
        range = new ColumnRange(-1, -1, -1);
        second = (ColumnValue)range;
    }
    public ColumnConstraints add(int col, Object val) {
        if (constraints.size() == 0) {
            first.col = col;
            first.val = val;
            constraints.add(first);
        } else if (constraints.size() == 1) {
            second.col = col;
            second.val = val;
            constraints.add(second);
        } else {
            constraints.add(new ColumnValue(col, val));
        }
        return this;
    }

    public ColumnConstraints addRange(int col, Number from, Number to) {
        assert isEmpty();
        range.col = col;
        range.setRange(from, to);
        constraints.add(range);
        return this;
    }

    public int size() { return constraints.size(); }
    public boolean isEmpty() {
        return constraints.isEmpty();
    }
    public boolean hasRange(int col) {
        return getRange(col) != null;
    }
    public ColumnRange getRange(int col) {
         for (int i=0; i<constraints.size(); i++) {
            ColumnValue val = constraints.get(i);
            if (val.getColumn() == col &&
                    val instanceof ColumnRange) {
                return (ColumnRange)val;
            }
        }
        return null;
    }
    public ColumnValue getColumnValue(int col) {
          for (int i=0; i<constraints.size(); i++) {
              ColumnValue val = constraints.get(i);
              if (val.getColumn() == col) {
                  return val;
              }
          }
        return null;
    }
    public boolean hasColumn(int col) {
        for (int i=0; i<constraints.size(); i++) {
            ColumnValue val = constraints.get(i);
            if (val.getColumn() == col) {
                return true;
            }
        }
        return false;
    }
    public int getMinColumn() {
        int minCol = Integer.MAX_VALUE;
        for (int i=0; i<constraints.size(); i++) {
            ColumnValue val = constraints.get(i);
            minCol = Math.min(minCol, val.getColumn());
        }
        return minCol;
    }
    public int getMaxColumn() {
        int maxCol = -1;
        for (int i=0; i<constraints.size(); i++) {
            ColumnValue val = constraints.get(i);
            maxCol = Math.max(maxCol, val.getColumn());
        }
        return maxCol;
    }
    public ColumnValue first() {
        return constraints.get(0);
    }
    public Iterator<ColumnValue> iterator() {
        return constraints.iterator();
    }

    public String toString() {
        String str = "Constraint(";
        for (int i=0; i<constraints.size(); i++) {
            ColumnValue val = constraints.get(i);
            str += "["+val+"]";
        }
        str += ")";
        return str;
    }
}
