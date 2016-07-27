package socialite.codegen;

import java.util.*;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import socialite.parser.*;
import socialite.parser.antlr.ColumnGroup;
import socialite.tables.TableUtil;
import socialite.util.InternalException;
import socialite.util.MySTGroupFile;


class TableTemplateSelector {
    static String tableGroupFile = "Table.stg";
    static STGroup tmplGroup =
        new MySTGroupFile(TableCodeGen.class.getResource(tableGroupFile),"UTF-8", '<', '>');

    public static ST select(Table t) {
        assert !t.hasNesting();
        return select(t, t.getColumnGroups().get(0), false);
    }
    public static ST select(Table t, ColumnGroup g, boolean hasNesting) {
        if (g.first().hasRange()) {
            return selectArrayTable(g, hasNesting);
        } else {
            return selectDynamicTable(g, hasNesting);
        }
    }
    static ST selectArrayTable(ColumnGroup g, boolean hasNesting) {
        if (hasNesting) {
            return tmplGroup.getInstanceOf("arrayNestedTable");
        } else {
            return tmplGroup.getInstanceOf("arrayTable");
        }
    }
    static ST selectDynamicTable(ColumnGroup g, boolean hasNesting) {
        if (hasNesting) {
            if (g.isSorted()) {
                return tmplGroup.getInstanceOf("dynamicNestedSortedTable");
            } else {
                return tmplGroup.getInstanceOf("dynamicNestedTable");
            }
        } else {
            if (g.isSorted()) {
                return tmplGroup.getInstanceOf("dynamicSortedTable");
            } else {
                return tmplGroup.getInstanceOf("dynamicTable");
            }
        }
    }
}

public class TableCodeGen {
    static String tableGroupFile = "Table.stg";
    static STGroup tmplGroup =
        new MySTGroupFile(TableCodeGen.class.getResource(tableGroupFile),"UTF-8", '<', '>');

    ST tableTmpl;
    List<ST> tableTmplList;
    Table table;
    public TableCodeGen(Table t) {
        table = t;
    }

    String tableName() { return TableUtil.getTablePath(table.className());}

    public String generate() {
        String src;
        if (table instanceof FixedSizeTable) {
            assert table instanceof DeltaTable || table instanceof RemoteHeadTable;
            return genFixedSizeTable(table);
        } else if (table instanceof RemoteBodyTable) {
            RemoteBodyTable rt=(RemoteBodyTable)table;
            return genRemoteBodyT(rt);
        }

        if (table.hasNesting()) {
            src = genNestedTable();
        } else {
            if (table instanceof ArrayTable) {
                src = genArrayTableSimple();
            } else {
                src = genDynamicTableSimple();
            }
        }

        return src;
    }
    String genFixedSizeTable(Table t) {
        tableTmpl = tmplGroup.getInstanceOf("fixedSizeTable");

        tableTmpl.add("tableName", table.name());
        tableTmpl.add("name", table.className());
        tableTmpl.add("id", table.id());
        int size = ((FixedSizeTable)t).size();
        tableTmpl.add("size", size);
        for (Column c:table.getColumns()) {
            tableTmpl.add("columns", c);
        }
        tableTmpl.add("visitorClass", CodeGenBase.visitorClass(table));
        return tableTmpl.render();
    }
    String genRemoteBodyT(RemoteBodyTable rt) {
        tableTmpl = tmplGroup.getInstanceOf("remoteTable");

        tableTmpl.add("tableName", table.name());
        tableTmpl.add("name", rt.className());
        tableTmpl.add("id", rt.id());

        int nest=0;
        for (ColumnGroup cg:rt.getColumnGroups()) {
            List<Column> cols = new ArrayList<Column>();
            for (Column c:cg.columns()) cols.add(c);
            tableTmpl.add("cols"+nest, cols);
            if (nest < 3) nest++;
        }
        int nestDepth = rt.getColumnGroups().size();
        if (nestDepth > 4) nestDepth = 4;
        tableTmpl.add("nest", nestDepth);

        tableTmpl.add("visitorClass", CodeGenBase.visitorClass(table));
        return tableTmpl.render();
    }

    PackedColumn createPackedColumn(Class type, int pos) {
        return new PackedColumn(type, pos);
    }
    PackedColumn createPackedColumn(Column c, int pos) {
        PackedColumn pc = new PackedColumn(c, pos);
        return pc;
    }
    List<PackedColumn> createPackedColumns(ColumnGroup colGroup) {
        List<PackedColumn> packedCols = new ArrayList<>();
        PackedColumn pcol = null;
        int nbits = 0;
        for (Column c: colGroup.columns()) {
            if (c.hasNumBits()) {
                if (pcol == null) {
                    pcol = createPackedColumn(int.class, packedCols.size());
                    packedCols.add(pcol);
                }
                nbits += c.getNumBits();
                if (nbits > 32) {
                    nbits = c.getNumBits();
                    pcol = createPackedColumn(int.class, packedCols.size());
                    packedCols.add(pcol);
                }
                pcol.addColumn(c);
            } else {
                nbits = 0;
                packedCols.add(createPackedColumn(c, packedCols.size()));
            }
        }
        return packedCols;
    }

    void maybeAddPackedColumns(ST tmpl, ColumnGroup group) {
        List<PackedColumn> packedColumns = null;
        if (group.hasBitPackedColumn()) {
            packedColumns = createPackedColumns(group);
            for (PackedColumn pcol:packedColumns) {
                tableTmpl.add("pcolumns", pcol);
            }
        }
    }

    String genArrayTableSimple() {
        tableTmpl = TableTemplateSelector.select(table);

        tableTmpl.add("tableName", table.name());
        tableTmpl.add("name", table.className());
        tableTmpl.add("id", table.id());
        tableTmpl.add("multiSet", table.isMultiSet());

        List<ColumnGroup> colGroups = table.getColumnGroups();
        ColumnGroup group = colGroups.get(0);

        if (table.groupbyColNum()>0) {
            for (int i=table.groupbyColNum(); i<table.numColumns(); i++) {
                tableTmpl.add("gbAggrColumns", table.getColumn(i));
            }
        }
        maybeAddPackedColumns(tableTmpl, group);
        for (Column c:group.columns()) {
            if (c.isIter()) continue;

            tableTmpl.add("columns", c);
            if (c.position() < table.groupbyColNum()) {
                tableTmpl.add("gbColumns", c);
            }
            if (c.isIndexed() && !c.isArrayIndex()) {
                tableTmpl.add("idxCols", c);
            }
        }
        tableTmpl.add("visitorClass", CodeGenBase.visitorClass(table));

        tableTmpl.add("base", table.arrayBeginIndex());
        tableTmpl.add("size", table.arrayTableSize());
        return tableTmpl.render();
    }

    boolean isDelta() { return table instanceof DeltaTable; }

    String genNestedTable() {
        int nestDepth=table.getColumnGroups().size();
        tableTmplList = new ArrayList<>(nestDepth);
        for (int i=0; i<nestDepth; i++) {
            boolean hasNested=false;
            if (i<nestDepth-1) { hasNested=true; }

            ST tmpl = TableTemplateSelector.select(table, table.getColumnGroups().get(i), hasNested);
            if (hasNested) { tmpl.add("nestedTable", table.nestedClassName(i+1));}

            tmpl.add("tableName", table.name());
            tmpl.add("name", table.nestedClassName(i));
            tmpl.add("id", table.id());
            tmpl.add("multiSet", table.isMultiSet());
            if (i>0) tmpl.add("isNested", true);

            ColumnGroup group=table.getColumnGroups().get(i);
            maybeAddPackedColumns(tableTmpl, group);
            for (Column c:group.columns()) {
                if (c.isIter()) continue;
                tmpl.add("columns", c);
                if (c.isIndexed() && !c.isArrayIndex()) { tmpl.add("idxCols", c); }
                if (!c.isArrayIndex() && c.isSorted()) { tmpl.add("sortedCol", c); }
            }
            if (hasNested) {
                for (int j=group.endIdx()+1; j<table.numColumns(); j++) {
                    Column c = table.getColumn(j);
                    tmpl.add("nestedColumns", c);
                }
                if (i+1<nestDepth) {
                    ColumnGroup nested=table.getColumnGroups().get(i+1);
                    for (int j=nested.endIdx()+1; j<table.numColumns(); j++) {
                        Column c = table.getColumn(j);
                        tmpl.add("nestedNestedColumns", c);
                    }
                }
            }
            tmpl.add("visitorClass", CodeGenBase.visitorClass(table));
            if (group.first().hasRange()) {
                int[] range = group.first().getRange();
                tmpl.add("base", range[0]);
                tmpl.add("size", range[1]-range[0]);
            }
            if (i==0) { tableTmpl = tmpl; }
            else { tableTmpl.add("classes", tmpl); }
            tableTmplList.add(tmpl);
        }

        addGroupbyCode();
        return tableTmpl.render();
    }

    void addGroupbyCode() {
        int groupby = table.groupbyColNum();
        if (groupby<=0) { return; }

        int nestDepth=table.getColumnGroups().size();
        List<Column> aggrCols = new ArrayList<>();
        for (int i=0; i<nestDepth; i++) {
            ST tmpl = tableTmplList.get(i);
            ColumnGroup group = table.getColumnGroups().get(i);

            if (group.first().getAbsPos() >= groupby) {
                break;
            }

            for (Column c:group.columns()) {
                if (c.getAbsPos()<groupby) {
                    tmpl.add("gbColumns", c);
                } else {
                    aggrCols.add(c);
                }
            }
            if (group.last().getAbsPos() < groupby) {
                for (int j=group.last().getAbsPos()+1; j<groupby; j++) {
                    Column c=table.getColumn(j);
                    tmpl.add("gbNestedColumns", c);
                }
            }
        }
        for (int i=0; i<nestDepth; i++) {
            ST tmpl = tableTmplList.get(i);
            for (Column c:aggrCols) {
                tmpl.add("gbAggrColumns", c);
            }
        }
    }

    String genDynamicTableSimple() {
        tableTmpl = TableTemplateSelector.select(table);

        tableTmpl.add("tableName", table.name());
        tableTmpl.add("name", table.className());
        tableTmpl.add("id", table.id());
        tableTmpl.add("multiSet", table.isMultiSet());

        List<ColumnGroup> colGroups = table.getColumnGroups();
        assert colGroups.size()==1;
        ColumnGroup group = colGroups.get(0);
        if (table.groupbyColNum()>0) {
            for (int i=table.groupbyColNum(); i<table.numColumns(); i++) {
                tableTmpl.add("gbAggrColumns", table.getColumn(i));
            }
        }
        List<PackedColumn> packedColumns = null;
        if (group.hasBitPackedColumn()) {
            packedColumns = createPackedColumns(group);
            for (PackedColumn pcol:packedColumns) {
                tableTmpl.add("pcolumns", pcol);
            }
        }
        for (Column c:group.columns()) {
            if (c.isIter()) continue;
            tableTmpl.add("columns", c);
            if (c.position() < table.groupbyColNum()) { tableTmpl.add("gbColumns", c); }
            if (c.isIndexed() && !c.isArrayIndex()) { tableTmpl.add("idxCols", c); }
            if (c.isSorted()) { tableTmpl.add("sortedCol", c); }
        }

        tableTmpl.add("visitorClass", CodeGenBase.visitorClass(table));
        return tableTmpl.render();
    }

    /* static methods  */

    static final LinkedHashMap<String,byte[]> EMPTY_MAP = new LinkedHashMap<String,byte[]>(0);
    public static LinkedHashMap<String, byte[]> ensureExist(List<Table> tables) throws InternalException {
        if (tables.isEmpty()) {
            return EMPTY_MAP;
        }
        Compiler c=new Compiler();
        LinkedHashMap<String,byte[]> generatedClasses = new LinkedHashMap<String,byte[]>();
        for (Table t:tables) {
            TableCodeGen gen = new TableCodeGen(t);
            if (t.isPredefined()) {
                if (!TableUtil.exists(t.className())) {
                    String msg="Cannot load class "+TableUtil.getTablePath(t.className());
                    msg += " "+c.getErrorMsg();
                    throw new InternalException(msg);
                }
                TableUtil.load(t.className());
            } else {
                if (!TableUtil.exists(t.className())) {
                    String src = gen.generate();
                    boolean success=c.compile(gen.tableName(), src);
                    if (!success) {
                        String msg="Compilation error for "+gen.tableName();
                        msg += " "+c.getErrorMsg();
                        throw new InternalException(msg);
                    }
                    generatedClasses.putAll(c.getCompiledClasses());
                }
                TableUtil.load(t.className());
            }
        }
        return generatedClasses;
    }
}
