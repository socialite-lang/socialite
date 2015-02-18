package socialite.parser;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import socialite.parser.antlr.ColumnGroup;
import socialite.parser.antlr.TableDecl;
import socialite.parser.antlr.ColumnDecl;
import socialite.util.IdFactory;
import socialite.util.InternalException;
import socialite.util.SociaLiteException;
import gnu.trove.list.array.TIntArrayList;

public class Table implements Serializable {
	static final long serialVersionUID = 1;
    static final Log L= LogFactory.getLog(Table.class);

	TableDecl decl;
	String className;
	int id;
	
	Column[] columns;
	Class[] types;
	
	List<ColumnGroup> columnGroupList;	
	TIntArrayList nestedTableIndices;
	boolean isArrayTable;
	int arrayTableSize=-1;
	int arrayBeginIndex=-1;
	int arrayEndIndex=-1;
	
	int groupby=0;
	int[] sortbyCols;
	int[] orderbyCols;
	int[] indexedCols;
	String visitorInterface=null;

	boolean compiled;
	
	public Table() { 
		decl=null;
		id=-1;
		columns=null;
		isArrayTable=false;
	}
	public Table(TableDecl _decl) {
		decl = _decl;
		id = IdFactory.nextTableId();
        init();
	}
	public void setVisitorInterface(String _visitorInterface) { visitorInterface = _visitorInterface; }
	public String visitorInterface() { return visitorInterface; }
	
	public TableDecl decl() { return decl; }
	
	void init() {
		columnGroupList = decl.buildColumnGroups();
		columns = buildColumns();		
		nestedTableIndices = decl.getNestedTableIndices();
		isArrayTable = columns[0].hasRange();
		if (decl.hasGroupBy()) {
			try { setGroupByColNum(decl.groupby()); }
			catch (InternalException e) {
				throw new SociaLiteException(e);
			}
		}		
		maybeSetArrayBaseAndSize();
		
		if (isPredefined()) className = name();
		else className = signature();
		
		compiled=false;
		
		types = new Class[numColumns()];
		for (int i=0; i<numColumns(); i++)
			types[i] = columns[i].type();
	}
		
	void maybeSetArrayBaseAndSize() {
		if (isArrayTable) {
			int first=columns[0].getRange()[0];
			int end=columns[0].getRange()[1];
			arrayTableSize = end-first+1;
			arrayBeginIndex= first;
			arrayEndIndex = end;
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
	
	public boolean isDistributed() { return hasPrimaryShardColumn(); }
	boolean hasPrimaryShardColumn() { return columns[0].isPrimaryShard(); }	

	public boolean isSliced() { return isModTable(); }
	public boolean isArrayTable() { return isArrayTable; }
	public boolean isModTable() { 
		if (isArrayTable) return false;
		if (hasOrderBy()) return false;
		return true;
	}
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
		for (int i:orderbyCols()) {
			sig += "_orderby"+i;
		}
		sig += "_groupby"+groupbyColNum();
        if (isConcurrent()) sig += "_concurrent";
		return sig;
	}
	public int arrayTableSize() {
		assert isArrayTable;
		return arrayTableSize;		
	}
	public int arrayBeginIndex() {
		assert isArrayTable;
		return arrayBeginIndex;		
	}	
	public int arrayEndIndex() {
		assert isArrayTable;
		return arrayEndIndex;
	}
	
	public void setGroupByColNum(int num) throws InternalException {
		if (compiled && groupby!=num) {
			String msg="Cannot add groupby with "+num+" columns: table "+ name()+" is already compiled. "+
					"Add groupby("+num+") to "+name()+".";			
			throw new InternalException(msg);
		}
		
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
	public int numColumns() { return columns.length; }
	
	public Class idxType() {
		ColumnDecl d = decl.locationColDecl();
		if (d==null) return null;
		return d.type();
	}
	public Class[] types() { return types; }
	public String className() { return className; }

    public boolean isConcurrent() { return decl.isConcurrent(); }
	public boolean isApprox() { return decl.isApproxSet(); }
	public boolean isPredefined() { return decl.isPredefined(); }
	public boolean isMultiSet() { return decl.isMultiSet(); }
	
	public boolean hasSameNestStructure(Table t) {
		List<ColumnGroup> groups1 = getColumnGroups();
		List<ColumnGroup> groups2 = t.getColumnGroups();
		if (groups1.size()!=groups2.size()) return false;
		
		for (int i=0; i<groups1.size(); i++) {
			ColumnGroup g1 = groups1.get(i);
			ColumnGroup g2 = groups2.get(i);
			if (!Arrays.equals(g1.types(), g2.types()))
				return false;
		}
		return true;
	}
	public boolean hasNestedT() {
		if (nestedTableIndices.isEmpty())
			return false;
		return true;
	}
	
	public int[] nestPos() {
		return nestedTableIndices.toArray();
	}
	
	public boolean nestingBeginsAfter(int col) {		
		for (int i=0; i<nestedTableIndices.size(); i++) {
			int nestAt=nestedTableIndices.get(i);
			if (nestAt >= col)
				return true;
		}
		return false;
	}
	public boolean nestingBegins(int col) {
		if (nestedTableIndices.contains(col)) return true;
		return false;
	}
	
	public int[] sortbyCols() {
		if (sortbyCols==null) sortbyCols = decl.sortbyCols().toArray(); 
		return sortbyCols;
	}
	public int[] orderbyCols() {
		if (orderbyCols==null) orderbyCols = decl.orderbyCols().toArray();
		return orderbyCols; 
	}
	public int[] indexedCols() {
		if (indexedCols==null) {
			TIntArrayList cols= decl.indexbyCols();
			for (int i=0; i<numColumns(); i++) {
				Column c=getColumn(i);
				if (c.hasRange() && !cols.contains(i))
					cols.add(i);
			}
			cols.sort();
			indexedCols=cols.toArray();
		}		
		return indexedCols; 
	}
	public void setCompiled() { compiled=true; }
	public boolean isCompiled() { return compiled; }

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
