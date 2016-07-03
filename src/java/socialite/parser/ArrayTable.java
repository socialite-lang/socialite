package socialite.parser;

import socialite.parser.antlr.TableDecl;

public class ArrayTable extends Table {
    int arrayTableSize=-1;
    int arrayBeginIndex=-1;
    int arrayEndIndex=-1;

    public ArrayTable() { super(); }
    public ArrayTable(TableDecl _decl) {
        super(_decl);
        int first=columns[0].getRange()[0];
        int end=columns[0].getRange()[1];
        arrayTableSize = end-first+1;
        arrayBeginIndex= first;
        arrayEndIndex = end;
    }

    public boolean isArrayTable() { return true; }

    public int arrayTableSize() {
        return arrayTableSize;
    }
    public int arrayBeginIndex() {
        return arrayBeginIndex;
    }
    public int arrayEndIndex() {
        return arrayEndIndex;
    }
}
