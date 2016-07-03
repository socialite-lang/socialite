package socialite.parser;

import socialite.parser.antlr.ColOpt;
import socialite.parser.antlr.ColRange;
import socialite.parser.antlr.TableDecl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TableFactory {
    public static List<Table> create(TableDecl decl) {
        if (_isArrayTable(decl)) {
            return _createArrayTable(decl);
        } else {
            return _createTable(decl);
        }
    }

    static List<Table> _createTable(TableDecl decl) {
        if (_isIterTable(decl)) {
            List<Table> list = new ArrayList<>();
            for (int i=0; i<decl.maxIter(); i++) {
                list.add(new IterTable(decl, i));
            }
            return list;
        } else {
            return Arrays.asList(new Table(decl));
        }
    }
    static boolean _isIterTable(TableDecl decl) {
        return decl.hasIterColumn();
    }
    static boolean _isArrayTable(TableDecl decl) {
        ColOpt copt = decl.first().option();
        if (copt instanceof ColRange) {
            return true;
        }
        return false;
    }

    static List<Table> _createArrayTable(TableDecl decl) {
        if (_isIterTable(decl)) {
            List<Table> list = new ArrayList<>();
            for (int i=0; i<decl.maxIter(); i++) {
                list.add(new ArrayIterTable(decl, i));
            }
            return list;
        } else {
            return Arrays.asList(new ArrayTable(decl));
        }
    }
}
