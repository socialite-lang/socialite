package socialite.parser;

import socialite.parser.antlr.TableDecl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface IterTableMixin {
    TableDecl decl();
    int getIterationCount();
    default int iterColumn() {
        return decl().iterColumn();
    }
    default String name() {
        return makeName(decl().name(), getIterationCount());
    }

    default List<String> getAllTableNames() {
        List<String> list = new ArrayList<>();
        for (int i=0; i<decl().maxIter(); i++) {
            list.add(makeName(decl().name(), i));
        }
        return list;
    }

    default String getOtherTableName(int iterCount) {
        return makeName(decl().name(), iterCount%decl().maxIter());
    }

    static String makeName(String name, int iterCount) {
        return name + "$"+iterCount;
    }
}