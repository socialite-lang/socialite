package socialite.parser;

public enum TableType {
    ArrayTable(true, false),
    DynamicTable(false, false),
    ArrayNestedTable(true, true),
    DynamicNestedTable(false, true),
    Other();

    final boolean isArrayTable;
    final boolean hasNestedTable;
    final boolean isBuiltin;
    TableType(boolean _isArrayTable, boolean _hasNestedTable) {
        isArrayTable = _isArrayTable;
        hasNestedTable = _hasNestedTable;
        isBuiltin = true;
    }
    TableType() {
        isArrayTable = false;
        hasNestedTable = false;
        isBuiltin = false;
    }

    public boolean isArrayTable() { return isArrayTable; }
    public boolean hasNestedTable() { return hasNestedTable; }
    public boolean isBuiltin() { return isBuiltin; }
}
