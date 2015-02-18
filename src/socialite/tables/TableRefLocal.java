package socialite.tables;

import socialite.parser.Table;

public class TableRefLocal implements TableRef {
    Table table;
    public TableRefLocal(Table t) {
        table = t;
    }
    public TupleWriteStream getWriteStream() {
        return new TupleWriteStreamLocal(table.id(), table.types());
    }
}