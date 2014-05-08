package socialite.codegen;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import socialite.engine.Config;
import socialite.parser.Column;
import socialite.parser.DeltaRule;
import socialite.parser.DeltaTable;
import socialite.parser.MyType;
import socialite.parser.PrivateTable;
import socialite.parser.RemoteBodyTable;
import socialite.parser.RemoteHeadTable;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.parser.hash;
import socialite.parser.antlr.ColumnGroup;
import socialite.resource.SRuntime;
import socialite.tables.TableUtil;
import socialite.util.Assert;
import socialite.util.InternalException;
import socialite.util.MySTGroupFile;
import socialite.util.SociaLiteException;
import socialite.visitors.IVisitor;

//import org.antlr.stringtemplate.StringTemplate;
//import org.antlr.stringtemplate.StringTemplateGroup;

public class TableCodeGen {
	static String tableGroupFile = "Table.stg";		
	static STGroup tmplGroup = 
		new MySTGroupFile(TableCodeGen.class.getResource(tableGroupFile),"UTF-8", '<', '>');
	
	ST tableTmpl;	
	List<ST> tableTmplList;
	String tableName, deltaTableName;
	ST deltaTable;	
	Table table;
	Config conf;
	public TableCodeGen(Table t) {
		this(t, Config.seq());
	}
	public TableCodeGen(Table t, Config _conf) {
		table = t;
		conf = _conf;
		adjustTableSizeToShardSize();
	}
	void adjustTableSizeToShardSize() {
		if (table instanceof RemoteHeadTable) {
			// no need to adjust. Table size is configured with the network buffer size
		} else if (table instanceof RemoteBodyTable) {
			// no need to adjust. Table size is configured with the network buffer size
		} else {
			// Delta Table, Private Table, or normal Table
			int divideby = 1;
			if (conf.isDistributed()) {
				int clusterSize = SRuntime.masterRt().getWorkerAddrMap().size();
				divideby *= clusterSize;
			}
			if (conf.isParallel()) {
				divideby *= conf.getWorkerNum();
			}
			Column c = table.getColumn(0);
			if (c.hasSize()) {
				int size = c.getSize();
				size = size / divideby;
				if (size <= 1) size = 1;
				c.setSize(size);
			}
		}
	}
	
	String tableName() { return TableUtil.getTablePath(table.className());}
	String tableSimpleName() { return table.className(); }
	
	boolean notNested() { return !table.hasNestedT(); }
	public String generate() {
		String src=null;
		
		if (table instanceof RemoteBodyTable) {
			RemoteBodyTable rt=(RemoteBodyTable)table;
			return genRemoteBodyT(rt);			
		}
		
		if (notNested()) {
			if (table.isArrayTable()) src = genArrayTableSimple();
			else src = genDynamicTableSimple(); 
		} else  src = genNestedTable();
		
		return src;		
	}
	String genRemoteBodyT(RemoteBodyTable rt) {
		tableTmpl = tmplGroup.getInstanceOf("remoteTable");
		
		tableTmpl.add("name", rt.className());
		tableTmpl.add("id", rt.id());
		tableTmpl.add("size", rt.lastColumnSize());
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
		
		tableTmpl.add("rowSize", rt.rowSize());
		
		tableTmpl.add("visitorClass", visitorClass());
		return tableTmpl.render();
	}
	
	String genArrayTableSimple() {
		tableTmpl = tmplGroup.getInstanceOf("arrayTable");
		
		tableTmpl.add("name", table.className());		
		tableTmpl.add("id", table.id());
		tableTmpl.add("multiSet", table.isMultiSet());
		
		List<ColumnGroup> colGroups = table.getColumnGroups();
		Assert.true_(colGroups.size()==1);
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
		tableTmpl.add("size", getInitSize(group.first()));
		
		tableTmpl.add("visitorClass", visitorClass());
		return tableTmpl.render();
	}
	
	String visitorClass() {
		String visitorClass = "VisitorImpl";
		if (table.visitorInterface()!=null)
			visitorClass = table.visitorInterface();
		return visitorClass;
	}
	
	String getColumnType(Class type) {
		return type.getSimpleName();
	}
	
	boolean isDelta() { return table instanceof DeltaTable; }
	boolean isRemoteHT() { return table instanceof RemoteHeadTable; }

	boolean isPriv() { return table instanceof PrivateTable; }
	boolean isDeltaOrPriv() { return isDelta() || isPriv(); }
	
	Map<ColumnGroup, ST> columnGroupToTableST = new HashMap<ColumnGroup, ST>();	
	ST getTableTemplate(ColumnGroup g, boolean hasNested) {
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
	
	String genNestedTable() {
		int nestDepth=table.getColumnGroups().size();
		tableTmplList = new ArrayList<ST>(nestDepth);
		boolean hasNestedIndex=false;
		for (ColumnGroup group:table.getColumnGroups()) {
			if (group.isFirst()) continue;
			if (group.hasIndex()) hasNestedIndex=true;
		}		
		for (int i=0; i<nestDepth; i++) {
			ST tmpl=null;
			boolean hasNested=false;
			if (i<nestDepth-1) hasNested=true;
			
			tmpl = getTableTemplate(table.getColumnGroups().get(i), hasNested);
			if (hasNested) tmpl.add("nestedTable", nestedTableName(i+1));
						
			tmpl.add("name", nestedTableName(i));
			tmpl.add("id", table.id());
			if (i>0) tmpl.add("isNested", true);			
			tmpl.add("multiSet", table.isMultiSet());
			
			ColumnGroup group=table.getColumnGroups().get(i);
			boolean sortDesc=false, sortedCol=false, hasIndex=false;
			for (Column c:group.columns()) {
				if (c.isIter()) continue;
				
				tmpl.add("columns", c);
				if (c.isSorted()) {
					tmpl.add("sortedCol", c);
					sortedCol=true;
					if (c.isSortedDesc()) sortDesc = true;
				} else if (c.isIndexed() && !c.isArrayIndex()) {
					tmpl.add("idxCols", c);
					hasIndex=true;
				}			
			}			
			if (sortDesc) tmpl.add("sortOrder", "Desc");
			if (sortedCol && (!hasIndex && !hasNestedIndex) && !table.hasGroupby())
				tmpl.add("inplaceSort", true);
						
			if (group.first().hasRange())
				tmpl.add("base", group.first().getRange()[0]);
			tmpl.add("size", getInitSize(group.first()));
			
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
		String name=table.className();
		for (int i=0; i<depth; i++) name += "$Nested";
		return name;
	}
	
	int arrayInitSize(Column first) {
		Assert.true_(first.hasRange());
		return first.getRange()[1]-first.getRange()[0]+1;
	}
	
	int getInitSize(Column first) {
		if (first.hasRange()) return arrayInitSize(first);
		else return first.getSize();
	}

	String genDynamicTableSimple() {
		tableTmpl = tmplGroup.getInstanceOf("dynamicTable");
		
		tableTmpl.add("name", table.className());
		tableTmpl.add("id", table.id());
		tableTmpl.add("multiSet", table.isMultiSet());
		
		List<ColumnGroup> colGroups = table.getColumnGroups();
		assert colGroups.size()==1;
		
		ColumnGroup group = colGroups.get(0);
		int initSize=getInitSize(group.first());
		tableTmpl.add("size", initSize);		 

		if (table.groupbyColNum()>0)
			tableTmpl.add("gbAggrColumn", table.getColumn(table.groupbyColNum()));
		boolean sortDesc=false;
		for (Column c:group.columns()) {
			if (c.isIter()) continue;
			
			tableTmpl.add("columns", c);			
			if (c.position() < table.groupbyColNum())
				tableTmpl.add("gbColumns", c);
			
			if (c.isSorted()) tableTmpl.add("sortedCol", c);
			if (c.isIndexed() && !c.isArrayIndex()) tableTmpl.add("idxCols", c);
			if (c.isSortedDesc()) sortDesc = true;
		}
		if (!group.hasIndex() && !table.hasGroupby()) tableTmpl.add("inplaceSort", true);
		if (sortDesc) tableTmpl.add("sortOrder", "Desc");		
		

		tableTmpl.add("visitorClass", visitorClass());
		return tableTmpl.render();
	}

	public boolean isParallel() { return conf.isParallel(); }
	
	/* static methods  */	
	public static List<Class> ensureExist(List<Table> tables) throws InternalException {
		return ensureExist(Config.seq(), tables);
	}
	
	public static List<Class> ensureExistOrDie(List<Table> tables) {
		try {
			return ensureExist(Config.seq(), tables);
		} catch (InternalException e) {
			throw new SociaLiteException(e);
		}
	}
	
	/**
	 *  See {@link Config#setDebugOpt()},  {@link Config#getDebugOpt()} 
	 */
	//static final boolean GENERATE_NEW_TABLE_JAVA_SOURCES = true;
	
	public static List<Class> ensureExist(Config conf, List<Table> tables) throws InternalException {
		if (tables.isEmpty()) return Collections.EMPTY_LIST;
		Compiler c=new Compiler(conf);
		List<Class> generated = new ArrayList<Class>(tables.size());
		Class tableClass;		
		boolean is_generated=false;
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
						is_generated=true;
					}
				}				
				tableClass=TableUtil.load(t.className());
				t.setCompiled();
				if (is_generated) generated.add(tableClass);
			}			
		}
		return generated;
	}
}
