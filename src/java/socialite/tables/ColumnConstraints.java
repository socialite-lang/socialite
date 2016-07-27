package socialite.tables;

import socialite.collection.SArrayList;

public class ColumnConstraints {
    SArrayList<Constraint> constraints;
    int maxCol=-1, minCol=Integer.MAX_VALUE;
    boolean hasRange=false;

    public ColumnConstraints() {
        constraints = new SArrayList(2);
    }

    public ColumnConstraints add(int col, int val) {
        constraints.add(new ColumnValue(col, val));
        return this;
    }
    public ColumnConstraints add(int col, long val) {
        constraints.add(new ColumnValue(col, val));
        return this;
    }
    public ColumnConstraints add(int col, Object val) {
        constraints.add(new ColumnValue(col, val));
        return this;
    }
    public ColumnConstraints set(int col, int val) {
        for (int i=0; i<constraints.size(); i++) {
            Constraint c = constraints.getQuick(i);
            if (c.getColumn()==col) {
                ((ColumnValue)c).setValue(col, val);
                return this;
            }
        }
        return add(col, val);
    }
    public ColumnConstraints set(int col, long val) {
        for (int i=0; i<constraints.size(); i++) {
            Constraint c = constraints.getQuick(i);
            if (c.getColumn()==col) {
                ((ColumnValue)c).setValue(col, val);
                return this;
            }
        }
        return add(col, val);
    }
    public ColumnConstraints set(int col, Object val) {
        for (int i=0; i<constraints.size(); i++) {
            Constraint c = constraints.getQuick(i);
            if (c.getColumn()==col) {
                ((ColumnValue)c).setValue(col, val);
                return this;
            }
        }
        return add(col, val);
    }

    public ColumnConstraints addRange(int col, int from, int to) {
        hasRange = true;
        constraints.add(new ColumnRange(col, from, to));
        return this;
    }
    public ColumnConstraints addRange(int col, long from, long to) {
        hasRange = true;
        constraints.add(new ColumnRange(col, from, to));
        return this;
    }
    public ColumnConstraints addRange(int col, float from, float to) {
        hasRange = true;
        constraints.add(new ColumnRange(col, from, to));
        return this;
    }
    public ColumnConstraints addRange(int col, double from, double to) {
        hasRange = true;
        constraints.add(new ColumnRange(col, from, to));
        return this;
    }
    public ColumnConstraints setRange(int col, int from, int to) {
        for (int i=0; i<constraints.size(); i++) {
            Constraint c = constraints.getQuick(i);
            if (c instanceof ColumnRange) {
                ColumnRange colRange = (ColumnRange)c;
                if (colRange.getColumn() == col) {
                    colRange.setRange(from, to);
                    return this;
                }
            }
        }
        return addRange(col, from, to);
    }
    public ColumnConstraints setRange(int col, long from, long to) {
        for (int i=0; i<constraints.size(); i++) {
            Constraint c = constraints.getQuick(i);
            if (c instanceof ColumnRange) {
                ColumnRange colRange = (ColumnRange)c;
                if (colRange.getColumn() == col) {
                    colRange.setRange(from, to);
                    return this;
                }
            }
        }
        return addRange(col, from, to);
    }
    public ColumnConstraints setRange(int col, float from, float to) {
        for (int i=0; i<constraints.size(); i++) {
            Constraint c = constraints.getQuick(i);
            if (c instanceof ColumnRange) {
                ColumnRange colRange = (ColumnRange)c;
                if (colRange.getColumn() == col) {
                    colRange.setRange(from, to);
                    return this;
                }
            }
        }
        return addRange(col, from, to);
    }
    public ColumnConstraints setRange(int col, double from, double to) {
        for (int i=0; i<constraints.size(); i++) {
            Constraint c = constraints.getQuick(i);
            if (c instanceof ColumnRange) {
                ColumnRange colRange = (ColumnRange)c;
                if (colRange.getColumn() == col) {
                    colRange.setRange(from, to);
                    return this;
                }
            }
        }
        return addRange(col, from, to);
    }


    public Constraint getAt(int i) { return constraints.getQuick(i); }
    public int size() { return constraints.size(); }
    public boolean isEmpty() {
        return constraints.isEmpty();
    }
    public boolean hasRange(int col) {
        if (hasRange) {
            return getRange(col) != null;
        }
        return false;
    }
    public ColumnRange getRange(int col) {
        for (int i=0; i<constraints.size(); i++) {
            Constraint val = constraints.getQuick(i);
            if (val.getColumn() == col &&
                    val instanceof ColumnRange) {
                return (ColumnRange)val;
            }
        }
        return null;
    }
    public ColumnValue getColumnValue(int col) {
          for (int i=0; i<constraints.size(); i++) {
              Constraint val = constraints.getQuick(i);
              if (val.getColumn() == col) {
                  return (ColumnValue)val;
              }
          }
        return null;
    }
    public boolean hasColumn(int col) {
        for (int i=0; i<constraints.size(); i++) {
            Constraint val = constraints.getQuick(i);
            if (val.getColumn() == col) {
                return true;
            }
        }
        return false;
    }
    public int getMinColumn() {
        if (minCol != Integer.MAX_VALUE) { return minCol; }

        for (int i=0; i<constraints.size(); i++) {
            Constraint val = constraints.get(i);
            minCol = Math.min(minCol, val.getColumn());
        }
        return minCol;
    }
    public int getMaxColumn() {
        if (maxCol != -1) { return maxCol; }

        for (int i=0; i<constraints.size(); i++) {
            Constraint val = constraints.get(i);
            maxCol = Math.max(maxCol, val.getColumn());
        }
        return maxCol;
    }
    public String toString() {
        String str = "Constraint(";
        for (int i=0; i<constraints.size(); i++) {
            Constraint val = constraints.get(i);
            str += "["+val+"]";
        }
        str += ")";
        return str;
    }
}
