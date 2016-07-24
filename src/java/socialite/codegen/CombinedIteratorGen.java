package socialite.codegen;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import socialite.parser.*;
import socialite.parser.antlr.ColumnGroup;

/**
 * Helper of JoinerCodeGen.
 * Generates combined-iterators for lock-step iterations.
 */
public class CombinedIteratorGen {
    static STGroup combinerGroup = CodeGenBase.getCombinedIterateGroup();;

    public static String generate(Table t1, Table t2, String visitorVar1, String visitorVar2) {
        String code = "";
        for(int nest=0; nest<=t1.nestingDepth(); nest++) {
            ColumnGroup cols1 = t1.getColumnGroups().get(nest);
            ColumnGroup cols2 = t2.getColumnGroups().get(nest);
            if (cols1.first().hasRange() && cols2.first().hasRange()) {
                code += generate_arr_and_arr(nest, t1, t2, visitorVar1, visitorVar2);
            } else if (!cols1.first().hasRange() && !cols2.first().hasRange()) {
                code += generate_dyn_and_dyn(nest, t1, t2, visitorVar1, visitorVar2);
            } else if (cols1.first().hasRange()) {
                code += generate_arr_and_dyn(nest, t1, t2, visitorVar1, visitorVar2);
            } else {
                code += generate_dyn_and_arr(nest, t1, t2, visitorVar1, visitorVar2);
            }
        }
        return code;
    }

    public static boolean canCombineIterate(Table t1, Table t2, Predicate p, Predicate nextP) {
        // Returns true if the following conditions are all met:
        // 1. T1 and T2 have the same partitioning scheme.
        // 2. The 1st column of the two tables must be sorted.
        // 3. The sorted columns must have the same params.
        // 4. The values of the sorted columns are unique.
        // 5. (a) Tables are either flat tables or
        //    (b) Have same nesting structure, with nested columns being sorted in the same way.

        if (t1 instanceof ArrayTable && t2 instanceof ArrayTable) {
            // if t1 and t2 are both ArrayTable, we use 
            return false;
        }
        if (t1 instanceof ArrayTable && !(t2 instanceof ArrayTable)) {return false;}
        if (t2 instanceof ArrayTable && !(t1 instanceof ArrayTable)) {return false;}

        if (!t1.getColumn(0).isSorted()) { return false; }
        if (!t2.getColumn(0).isSorted()) { return false; }

        Param param1 = p.inputParams()[0];
        Param param2 = nextP.inputParams()[0];
        if (param1 instanceof Variable && param2 instanceof Variable) {
            Variable v1 = (Variable)param1;
            Variable v2 = (Variable)param2;
            if (t1.nestingDepth() != t2.nestingDepth()) { return false; }

            if (!v1.equals(v2)) { return false; }
            for (int nest = 0; nest < t1.nestingDepth(); nest++) {
                ColumnGroup cols1 = t1.getColumnGroups().get(nest);
                ColumnGroup cols2 = t2.getColumnGroups().get(nest);
                if (!canCombineIterate(t1, t2, cols1, cols2, p, nextP)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    static boolean isUniqueCol(Column c, ColumnGroup cols) {
        if (c.hasRange()) { return true; }
        if (cols.size()==1) { return true; }
        return false;
    }
    static boolean canCombineIterate(Table t1, Table t2, ColumnGroup cols1, ColumnGroup cols2, Predicate p, Predicate nextP) {
        if (!cols1.isSorted() || !cols2.isSorted()) {
            return false;
        }
        if (t1.isMultiSet() || t2.isMultiSet()) {
            return false;
        }
        Column sortedCol1 = cols1.getSortedCol();
        Column sortedCol2 = cols2.getSortedCol();
        if (!isUniqueCol(sortedCol1, cols1)) { return false; }
        if (!isUniqueCol(sortedCol2, cols2)) { return false; }

        Param param1 = p.inputParams()[sortedCol1.getAbsPos()];
        Param param2 = nextP.inputParams()[sortedCol2.getAbsPos()];
        if (!(param1 instanceof Variable)) { return false; }
        if (!(param2 instanceof Variable)) { return false; }

        Variable v1 = (Variable)param1;
        Variable v2 = (Variable)param2;
        return v1.equals(v2);
    }

    static String generate_arr_and_arr(int nestLevel, Table t1, Table t2, String visitorVar1, String visitorVar2) {
        ST template = combinerGroup.getInstanceOf("combinedIterate_arr");
        _generate(nestLevel, template, t1, t2, visitorVar1, visitorVar2);
        return template.render();
    }
    static String generate_dyn_and_dyn(int nestLevel, Table t1, Table t2, String visitorVar1, String visitorVar2) {
        ST template = combinerGroup.getInstanceOf("combinedIterate_dyn");
        _generate(nestLevel, template, t1, t2, visitorVar1, visitorVar2);
        return template.render();
    }
    static String generate_arr_and_dyn(int nestLevel, Table t1, Table t2, String visitorVar1, String visitorVar2) {
        ST template = combinerGroup.getInstanceOf("combinedIterate_arr_dyn");
        _generate(nestLevel, template, t1, t2, visitorVar1, visitorVar2);
        return template.render();
    }
    static String generate_dyn_and_arr(int nestLevel, Table t1, Table t2, String visitorVar1, String visitorVar2) {
        ST template = combinerGroup.getInstanceOf("combinedIterate_dyn_arr");
        _generate(nestLevel, template, t1, t2, visitorVar1, visitorVar2);
        return template.render();
    }
    static void _generate(int nestLevel, ST st, Table t1, Table t2, String visitorVar1, String visitorVar2) {
        st.add("name1", t1.nestedClassName(nestLevel));
        st.add("name2", t2.nestedClassName(nestLevel));
        st.add("isNested", t1.nestingDepth() > nestLevel);
        ColumnGroup cols1 = t1.getColumnGroups().get(nestLevel);
        for (Column c:cols1.columns()) {
            st.add("columns1", c);
        }
        ColumnGroup cols2 = t2.getColumnGroups().get(nestLevel);
        for (Column c:cols2.columns()) {
            st.add("columns2", c);
        }
        st.add("sortedCol1", cols1.getSortedCol());
        st.add("sortedCol2", cols2.getSortedCol());
        st.add("visitor1", visitorVar1);
        st.add("visitor2", visitorVar2);
    }

    // if nested column is sorted... lock-step nested column..
}
