package socialite.parser;

import socialite.parser.antlr.TableDecl;

public class IterTable extends Table implements IterTableMixin {
    static final long serialVersionUID = 1;

    public static String name(String declName, int iter) {
        return declName + "$" + iter;
    }

    int iterationCount;

    public IterTable(TableDecl _decl, int _iter) {
        super(_decl);
        iterationCount = _iter;
    }

    @Override
    public int getIterationCount() {
        return iterationCount;
    }
}


