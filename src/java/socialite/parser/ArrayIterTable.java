package socialite.parser;

import socialite.parser.antlr.TableDecl;

public class ArrayIterTable extends ArrayTable implements IterTableMixin {
    int iterationCount;

    public ArrayIterTable(TableDecl _decl, int _iter) {
        super(_decl);
        iterationCount = _iter;
    }
    @Override
    public int getIterationCount() {
        return iterationCount;
    }

    @Override
    public String name() {
        return IterTableMixin.super.name();
    }
}
