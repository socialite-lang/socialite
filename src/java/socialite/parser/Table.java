package socialite.parser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import socialite.parser.antlr.ColumnGroup;
import socialite.parser.antlr.TableDecl;
import socialite.parser.antlr.TemplateType;
import socialite.util.IdFactory;
import socialite.util.InternalException;
import socialite.util.SociaLiteException;
import gnu.trove.list.array.TIntArrayList;

public class Table implements Serializable {
    static final long serialVersionUID = 1;

    TableDecl decl;
    String className;
    int id;

    Column[] columns;
    Class[] types;

    List<ColumnGroup> columnGroupList;
    TIntArrayList nestedTableIndices;

    int groupby=0;
    int[] sortbyCols;
    int[] indexedCols;
    String visitorClass="VisitorImpl";

    public Table() {
        decl=null;
        id=-1;
        columns=null;
    }
    public Table(TableDecl _decl) {
        decl = _decl;
        id = IdFactory.nextTableId();
        init();
    }
    public void setVisitorClass(String _visitorClass) { visitorClass = _visitorClass; }
    public String visitorClass() { return visitorClass; }

    public TableDecl decl() { return decl; }

    public int getPartitionColumn() {
        return decl.getPartitionColumn();
    }

    void init() {
        columnGroupList = decl.buildColumnGroups();
        columns = buildColumns();
        nestedTableIndices = decl.getNestedTableIndices();
        if (decl.hasGroupBy()) {
            try { setGroupByColNum(decl.groupby()); }
            catch (InternalException e) {
                throw new SociaLiteException(e);
            }
        }

        if (isPredefined()) { className = name(); }
        else { className = signature(); }

        types = new Class[numColumns()];
        for (int i=0; i<numColumns(); i++) {
            types[i] = columns[i].type();
        }
    }

    public int hashCode() { return decl.hashCode();	}
    public boolean equals(Object o) {
        if (o==this) return true;
        if (!(o instanceof Table)) return false;
        Table t=(Table)o;
        return decl.equals(t.decl) && id == t.id;
    }

    public int id() { return id; }
    public String name() { return decl.name(); }

    public boolean isDistributed() { return true; }

    public boolean isArrayTable() { return false; }

    public boolean hasOrderBy() {
        Column first=getColumn(0);
        if (first.isOrdered()) return true;
        return false;
    }

    public String signature() {
        return defaultsig()+"_id"+id;
    }
    String defaultsig() {
        String sig = name();
        sig += columnStr(columns);

        for (int i:nestPos()) {
            sig += "_nest"+i;
        }
        for (int i:indexedCols()) {
            sig += "_indexby"+i;
            if (columns[i].hasRange()) {
                sig += "_range";
                int[] range=columns[i].getRange();
                sig += range[0]+"to"+range[1];
            }
        }
        for (int i:sortbyCols()) {
            sig += "_sortby"+i;
            if (columns[i].isSortedAsc()) sig += "asc";
            else sig += "asc";
        }
        sig += "_groupby"+groupbyColNum();
        sig += "_shardby"+getPartitionColumn();
        if (isConcurrent()) sig += "_concurrent";
        return sig;
    }
    public int arrayTableSize() {
        throw new UnsupportedOperationException("Table is not instance of ArrayTable");
    }
    public int arrayBeginIndex() {
        throw new UnsupportedOperationException("Table is not instance of ArrayTable");
    }
    public int arrayEndIndex() {
        throw new UnsupportedOperationException("Table is not instance of ArrayTable");
    }

    public void setGroupByColNum(int num) throws InternalException {
        if (groupby>0 && groupby!=num) {
            throw new InternalException("Table "+ name()+" is already configured with #"+groupby+" groupby columns.");
        }

        for (int nest:nestPos()) {
            if (num==nest) {
                String msg=name()+": cannot apply groupby to the table nesting boundaries";
                throw new InternalException(msg);
            }
        }
        groupby = num;
    }
    public boolean hasGroupby() { return groupby>0; }
    public int groupbyColNum() { return groupby; }
    public int groupbyRestColNum() { return columns.length-groupby;}

    public boolean hasIndexby() { return indexedCols().length>0; }
    public boolean hasSortby() { return sortbyCols().length>0; }

    public List<ColumnGroup> getColumnGroups() { return columnGroupList; }

    Column[] buildColumns() {
        Column[] cols = new Column[decl.numAllColumns()];
        List<ColumnGroup> groups = getColumnGroups();
        int pos=0;

        for (ColumnGroup cg:groups) {
            for (Column c:cg.columns()) {
                cols[pos] = c;
                pos++;
            }
        }
        return cols;
    }

    public Column[] getColumns() { return columns; }
    public Column getColumn(int idx) { return columns[idx]; }
    public Column[] getSortedColumns() {
        ArrayList<Column> cols = new ArrayList<>();
        for (Column c:columns) {
            if (c.isSorted()) {
                cols.add(c);
            }
        }
        return cols.toArray(new Column[]{});
    }
    public int numColumns() { return columns.length; }

    public Class[] types() { return types; }
    public String className() { return className; }
    public String nestedClassName(int level) {
        /** This is used in {@link Compiler} for ordering compilation.
         *  @see JavaMemFileManager#topoSortedClasses.
         */
        String name = className();
        for (int i=0; i<level; i++) {
            name += "$Nested";
        }
        return name;
    }

    public boolean isConcurrent() { return decl.isConcurrent(); }
    public boolean isApprox() { return decl.isApproxSet(); }
    public boolean isPredefined() { return decl.isPredefined(); }
    public boolean isMultiSet() { return decl.isMultiSet(); }

    public boolean hasNesting() {
        if (nestedTableIndices.isEmpty()) {
            return false;
        }
        return true;
    }

    public int nestingDepth() {
        return nestedTableIndices.size();
    }

    public int[] nestPos() {
        return nestedTableIndices.toArray();
    }

    public boolean nestingBegins(int col) {
        if (nestedTableIndices.contains(col)) return true;
        return false;
    }

    public int[] sortbyCols() {
        if (sortbyCols==null) { sortbyCols = decl.sortbyCols().toArray(); }
        return sortbyCols;
    }
    public int[] indexedCols() {
        if (indexedCols==null) {
            TIntArrayList cols= decl.indexbyCols();
            for (int i=0; i<numColumns(); i++) {
                Column c=getColumn(i);
                if (c.hasRange() && !cols.contains(i)) {
                    cols.add(i);
                }
            }
            cols.sort();
            indexedCols=cols.toArray();
        }
        return indexedCols;
    }
    public String toString() {
        return name() + "(class="+className()+", id:"+id+")";
    }

    public static String columnStr(Column[] cols) {
        String str="";
        for (Column c:cols) {
            String columnType = c.type().getSimpleName();
            if (c.type().isArray()) {
                columnType=columnType.substring(0,columnType.length()-2)+"_array";
            }
            str += "_"+columnType;
        }
        return str;
    }
}
