package socialite.codegen;

import java.util.*;
import java.util.concurrent.atomic.AtomicReferenceArray;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import socialite.engine.Config;
import socialite.parser.Column;
import socialite.parser.DeltaTable;
import socialite.parser.FixedSizeTable;
import socialite.parser.PrivateTable;
import socialite.parser.RemoteBodyTable;
import socialite.parser.RemoteHeadTable;
import socialite.parser.Table;
import socialite.parser.antlr.ColumnGroup;
import socialite.resource.SRuntime;
import socialite.tables.TableUtil;
import socialite.util.Assert;
import socialite.util.InternalException;
import socialite.util.MySTGroupFile;
import socialite.util.SociaLiteException;
import socialite.util.concurrent.AtomicByteArray;
import socialite.util.concurrent.NonAtomicByteArray;
import socialite.util.concurrent.NonAtomicReferenceArray;

//import org.antlr.stringtemplate.StringTemplate;
//import org.antlr.stringtemplate.StringTemplateGroup;

public class TableCodeGen {
	static String tableGroupFile = "Table.stg";		
	static STGroup tmplGroup = 
		new MySTGroupFile(TableCodeGen.class.getResource(tableGroupFile),"UTF-8", '<', '>');

    enum TableType {
        ArrayTable(true, false),
        DynamicTable(false, false),
        ArrayNestedTable(true, true),
        DynamicNestedTable(false, true);

        final boolean isArrayTable;
        final boolean hasNestedTable;
        TableType(boolean _isArrayTable, boolean _hasNestedTable) {
            isArrayTable = _isArrayTable;
            hasNestedTable = _hasNestedTable;
        }
        public boolean isArrayTable() { return isArrayTable; }
        public boolean hasNestedTable() { return hasNestedTable; }
    }

	ST tableTmpl;	
	List<ST> tableTmplList;
	Table table;
	Config conf;
	public TableCodeGen(Table t) {
		this(t, Config.seq());
	}
	public TableCodeGen(Table t, Config _conf) {
		table = t;
		conf = _conf;
	}
	
	String tableName() { return TableUtil.getTablePath(table.className());}

	boolean notNested() { return !table.hasNestedT(); }
	public String generate() {
		String src=null;
		
		if (table instanceof FixedSizeTable) {
			assert table instanceof DeltaTable || table instanceof RemoteHeadTable;
			return genFixedSizeTable(table);
		} else if (table instanceof RemoteBodyTable) {
			RemoteBodyTable rt=(RemoteBodyTable)table;
			return genRemoteBodyT(rt);			
		}
		
		if (table.hasNestedT()) {
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
        tableTmpl.add("visitorClass", visitorClass());
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
		
		tableTmpl.add("visitorClass", visitorClass());
		return tableTmpl.render();
	}
	
	String genArrayTableSimple() {
		tableTmpl = tmplGroup.getInstanceOf("arrayTable");
		
		tableTmpl.add("tableName", table.name());
		tableTmpl.add("name", table.className());		
		tableTmpl.add("id", table.id());
		tableTmpl.add("multiSet", table.isMultiSet());
        if (table.isConcurrent()) {
            tableTmpl.add("byteArrayClass", AtomicByteArray.class.getName());
            tableTmpl.add("concurrent", true);
        } else {
            tableTmpl.add("byteArrayClass", NonAtomicByteArray.class.getName());
        }
		
		List<ColumnGroup> colGroups = table.getColumnGroups();
		ColumnGroup group = colGroups.get(0);
				
		if (table.groupbyColNum()>0)
			tableTmpl.add("gbAggrColumn", table.getColumn(table.groupbyColNum()));
		for (Column c:group.columns()) {
			if (c.isIter()) continue;
			
			tableTmpl.add("columns", c);			
			if (c.position() < table.groupbyColNum())
				tableTmpl.add("gbColumns", c);
			if (c.isIndexed() && !c.isArrayIndex())
				tableTmpl.add("idxCols", c);
		}
        tableTmpl.add("base", table.arrayBeginIndex());
        tableTmpl.add("size", table.arrayTableSize());
        
		tableTmpl.add("visitorClass", visitorClass());
		return tableTmpl.render();
	}
	
	String visitorClass() {
		String visitorClass = "VisitorImpl";
		if (table.visitorInterface()!=null)
			visitorClass = table.visitorInterface();
		return visitorClass;
	}
	
	String getColumnType(Class<?> type) {
		return type.getSimpleName();
	}
	
	boolean isDelta() { return table instanceof DeltaTable; }
	boolean isRemoteHT() { return table instanceof RemoteHeadTable; }

	boolean isPriv() { return table instanceof PrivateTable; }
	boolean isDeltaOrPriv() { return isDelta() || isPriv(); }

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

    @Deprecated
    Map<ColumnGroup, ST> columnGroupToTableST = new HashMap<ColumnGroup, ST>();
    @Deprecated
	ST OLD_getTableTemplate(ColumnGroup g, boolean hasNested) {
		if (columnGroupToTableST.containsKey(g) )
			return columnGroupToTableST.get(g);
		ST st=null;
		if (hasNested) {
			if (g.first().hasRange()) st = tmplGroup.getInstanceOf("arrayNestedTable");
			else st = tmplGroup.getInstanceOf("dynamicNestedTable");
		} else {
			if (g.first().hasRange()) st = tmplGroup.getInstanceOf("arrayTable");
			else  st = tmplGroup.getInstanceOf("dynamicTable");
		}		
		ST old=columnGroupToTableST.put(g, st);
		assert old==null;
		return st;
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
            if (tableType == TableType.ArrayTable) {
                String byteArrayClass = NonAtomicByteArray.class.getName();
                if (table.isConcurrent())
                    byteArrayClass = AtomicByteArray.class.getName();
                tmpl.add("byteArrayClass", byteArrayClass);
            }
            if (tableType == TableType.ArrayNestedTable) {
                String refArrayClass = NonAtomicReferenceArray.class.getName();
                if (table.isConcurrent())
                    refArrayClass = AtomicReferenceArray.class.getName();
                tmpl.add("refArrayClass", refArrayClass);
            }
            
            if (table.isConcurrent()) {
                tmpl.add("concurrent", true);
            }

			tmpl.add("tableName", table.name());
			tmpl.add("name", nestedTableName(i));
			tmpl.add("id", table.id());
            tmpl.add("multiSet", table.isMultiSet());
			if (i>0) tmpl.add("isNested", true);
			
			ColumnGroup group=table.getColumnGroups().get(i);
			for (Column c:group.columns()) {
				if (c.isIter()) continue;
				tmpl.add("columns", c);
                if (c.isIndexed() && !c.isArrayIndex()) { tmpl.add("idxCols", c); }
                if (c.isSorted()) { tmpl.add("sortedCol", c); }
			}
			if (tableType.isArrayTable()) {
				Column first = group.first();
            	tmpl.add("base", first.getRange()[0]);
            	tmpl.add("size", first.getRange()[1]-first.getRange()[0]+1);
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
			tmpl.add("visitorClass", visitorClass());
			if (i==0) tableTmpl = tmpl;
			else tableTmpl.add("classes", tmpl);
			tableTmplList.add(tmpl);
		}
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
		if (table.groupbyColNum()>0)
			tableTmpl.add("gbAggrColumn", table.getColumn(table.groupbyColNum()));
		for (Column c:group.columns()) {
			if (c.isIter()) continue;
			tableTmpl.add("columns", c);			
			if (c.position() < table.groupbyColNum()) { tableTmpl.add("gbColumns", c); }
			if (c.isIndexed() && !c.isArrayIndex()) { tableTmpl.add("idxCols", c); }
			if (c.isSorted()) { tableTmpl.add("sortedCol", c); }
		}

		tableTmpl.add("visitorClass", visitorClass());
		return tableTmpl.render();
	}

	public boolean isParallel() { return conf.isParallel(); }
	
	/* static methods  */	
	public static LinkedHashMap<String,byte[]> ensureExist(List<Table> tables) throws InternalException {
		return ensureExist(Config.seq(), tables);
	}
	public static LinkedHashMap<String,byte[]> ensureExistOrDie(List<Table> tables) {
		try {
			return ensureExist(Config.seq(), tables);
		} catch (InternalException e) {
			throw new SociaLiteException(e);
		}
	}
	
	/**
	 *  See {@link Config#setDebugOpt(String opt, boolean val)}, {@link Config#getDebugOpt(String opt)} */
	//static final boolean GENERATE_NEW_TABLE_JAVA_SOURCES = true;

    static final LinkedHashMap<String,byte[]> EMPTY_MAP = new LinkedHashMap<String,byte[]>(0);
	public static LinkedHashMap<String, byte[]> ensureExist(Config conf, List<Table> tables) throws InternalException {
		if (tables.isEmpty()) return EMPTY_MAP;
		Compiler c=new Compiler(conf.isVerbose());
        LinkedHashMap<String,byte[]> generatedClasses = new LinkedHashMap<String,byte[]>();
		Class<?> tableClass;
		for (Table t:tables) {
			TableCodeGen gen = new TableCodeGen(t, conf);
			if (t.isPredefined()) {
				if (!TableUtil.exists(t.className())) {
					String msg="Cannot load class "+TableUtil.getTablePath(t.className());
					msg += " "+c.getErrorMsg();
					throw new InternalException(msg);
				}
				tableClass=TableUtil.load(t.className());
				t.setCompiled();
			} else {				
				if (conf.getDebugOpt("GenerateTable")) {
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
				}				
				tableClass=TableUtil.load(t.className());
				t.setCompiled();
			}			
		}
		return generatedClasses;
	}
}
