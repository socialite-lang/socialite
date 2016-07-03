package socialite.codegen;

import java.util.*;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import socialite.parser.*;
import socialite.parser.antlr.ColumnGroup;
import socialite.tables.TableUtil;
import socialite.util.Assert;
import socialite.util.InternalException;
import socialite.util.MySTGroupFile;
import socialite.parser.TableType;

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

    boolean notNested() { return !table.hasNesting(); }
    public String generate() {
        String src=null;

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
            if (table.isArrayTable()) src = genArrayTableSimple();
            else src = genDynamicTableSimple();
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
        tableTmpl.add("visitorClass", CodeGen.visitorClass(table));
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

        tableTmpl.add("visitorClass", CodeGen.visitorClass(table));
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
        tableTmpl = tmplGroup.getInstanceOf("arrayTable");

        tableTmpl.add("tableName", table.name());
        tableTmpl.add("name", table.className());
        tableTmpl.add("id", table.id());
        tableTmpl.add("multiSet", table.isMultiSet());

        List<ColumnGroup> colGroups = table.getColumnGroups();
        ColumnGroup group = colGroups.get(0);

        if (table.groupbyColNum()>0) {
            tableTmpl.add("gbAggrColumn", table.getColumn(table.groupbyColNum()));
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
        tableTmpl.add("visitorClass", CodeGen.visitorClass(table));

        tableTmpl.add("base", table.arrayBeginIndex());
        tableTmpl.add("size", table.arrayTableSize());
        return tableTmpl.render();
    }

    boolean isDelta() { return table instanceof DeltaTable; }

    ST getTableTemplate(TableType type) {
        switch (type) {
            case ArrayTable:
                return tmplGroup.getInstanceOf("arrayTable");
            case ArrayNestedTable:
                return tmplGroup.getInstanceOf("arrayNestedTable");
            case DynamicTable:
                return tmplGroup.getInstanceOf("dynamicTable");
            case DynamicNestedTable:
                return tmplGroup.getInstanceOf("dynamicNestedTable");
            default:
                Assert.impossible("Unexpected table type:"+type);
                return null;
        }
    }

    TableType getTableType(ColumnGroup g, boolean hasNested) {
        if (hasNested) {
            if (g.first().hasRange()) return TableType.ArrayNestedTable;
            else return TableType.DynamicNestedTable;
        } else {
            if (g.first().hasRange()) return TableType.ArrayTable;
            else return TableType.DynamicTable;
        }
    }
    String genNestedTable() {
        int nestDepth=table.getColumnGroups().size();
        tableTmplList = new ArrayList<ST>(nestDepth);
        for (int i=0; i<nestDepth; i++) {
            boolean hasNested=false;
            if (i<nestDepth-1) hasNested=true;

            TableType tableType = getTableType(table.getColumnGroups().get(i), hasNested);
            ST tmpl = getTableTemplate(tableType);
            if (hasNested) tmpl.add("nestedTable", nestedTableName(i+1));

            tmpl.add("tableName", table.name());
            tmpl.add("name", nestedTableName(i));
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
            tmpl.add("visitorClass", CodeGen.visitorClass(table));
            if (tableType.isArrayTable()) {
                tmpl.add("base", table.arrayBeginIndex());
                tmpl.add("size", table.arrayTableSize());
            }
            if (i==0) tableTmpl = tmpl;
            else tableTmpl.add("classes", tmpl);
            tableTmplList.add(tmpl);
        }
        /*if (table.isArrayTable()) {
            tableTmpl.add("base", table.arrayBeginIndex());
            tableTmpl.add("size", table.arrayTableSize());
        }*/
        addGroupbyCode();
        return tableTmpl.render();
    }

    void addGroupbyCode() {
        int groupby = table.groupbyColNum();
        if (groupby<=0) return;

        Column aggrColumn = table.getColumn(groupby);
        int nestDepth=table.getColumnGroups().size();
        for (int i=0; i<nestDepth; i++) {
            ST tmpl = tableTmplList.get(i);
            ColumnGroup group = table.getColumnGroups().get(i);

            if (group.first().getAbsPos() >= groupby)
                break;

            tmpl.add("gbAggrColumn", aggrColumn);
            for (Column c:group.columns()) {
                if (c.getAbsPos()<groupby)
                    tmpl.add("gbColumns", c);
            }
            if (group.last().getAbsPos() < groupby) {
                for (int j=group.last().getAbsPos()+1; j<groupby; j++) {
                    Column c=table.getColumn(j);
                    tmpl.add("gbNestedColumns", c);
                }
            }
        }
    }

    String nestedTableName(int depth) {
        /** This is used in {@link Compiler} for ordering compilation.
         *  @see JavaMemFileManager#topoSortedClasses.
         */
        String name=table.className();
        for (int i=0; i<depth; i++) name += "$Nested";
        return name;
    }

    String genDynamicTableSimple() {
        tableTmpl = tmplGroup.getInstanceOf("dynamicTable");

        tableTmpl.add("tableName", table.name());
        tableTmpl.add("name", table.className());
        tableTmpl.add("id", table.id());
        tableTmpl.add("multiSet", table.isMultiSet());

        List<ColumnGroup> colGroups = table.getColumnGroups();
        assert colGroups.size()==1;
        ColumnGroup group = colGroups.get(0);
        if (table.groupbyColNum()>0) {
            tableTmpl.add("gbAggrColumn", table.getColumn(table.groupbyColNum()));
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

        tableTmpl.add("visitorClass", CodeGen.visitorClass(table));
        return tableTmpl.render();
    }

    /* static methods  */

    static final LinkedHashMap<String,byte[]> EMPTY_MAP = new LinkedHashMap<String,byte[]>(0);
    public static LinkedHashMap<String, byte[]> ensureExist(List<Table> tables) throws InternalException {
        if (tables.isEmpty()) return EMPTY_MAP;
        Compiler c=new Compiler();
        LinkedHashMap<String,byte[]> generatedClasses = new LinkedHashMap<String,byte[]>();
        Class<?> tableClass;
        for (Table t:tables) {
            TableCodeGen gen = new TableCodeGen(t);
            if (t.isPredefined()) {
                if (!TableUtil.exists(t.className())) {
                    String msg="Cannot load class "+TableUtil.getTablePath(t.className());
                    msg += " "+c.getErrorMsg();
                    throw new InternalException(msg);
                }
                tableClass=TableUtil.load(t.className());
                t.setCompiled();
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
                tableClass=TableUtil.load(t.className());
                t.setCompiled();
            }
        }
        return generatedClasses;
    }
}
