package socialite.tables;

import socialite.parser.Table;

/**
 * A SociaLite table exposed to end-users
 */
public class TableRefLocal {
    Table table;
    public TableRefLocal(Table t) {
        table = t;
    }
    public TupleWriteStream getWriteStream() {
        return new TupleWriteStreamLocal(table.id(), table.types());
    }
}