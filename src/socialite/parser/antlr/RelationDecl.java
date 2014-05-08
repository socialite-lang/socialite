package socialite.parser.antlr;

import gnu.trove.list.array.TIntArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import socialite.parser.MyType;
import socialite.parser.hash;
import socialite.parser.iter;
import socialite.util.Assert;

public class RelationDecl {	
	/*
	public String name;
	public Class idxType;
	public Class[] types;	
	public boolean iteration;
	
	// options
	public TIntArrayList singleRowColumns;
	public int subIndexSize;
	public boolean isPredefined;
	public String[] columnNames;
	
	boolean sort;
	boolean sortAsc;
	
	boolean isMultiSet;
	public boolean isEdgeRelation;
	public int[] edgeColumns;
	
	List<Pair<Class, String>> columnInfos;
	//public List<ColumnGroup> columnGroups;
	public RelationDecl(String _name, Pair<Class, String> _idxColumn, List<Pair<Class, String>> _columnInfos) {
		name = _name;
		idxType = _idxColumn.first;
		columnInfos = _columnInfos;
		checkIterationColumn(columnInfos);
		types = extractTypes(columnInfos);
		columnNames = extractColumnNames(_idxColumn.second, columnInfos);
		isPredefined=false;
		singleRowColumns = new TIntArrayList();
		subIndexSize = 0;
		isEdgeRelation=false;
		sort=false;
		sortAsc=false;
		isMultiSet=false;
	}
	
	void checkIterationColumn(List<Pair<Class, String>> _columnInfos) {
		Pair<Class, String> firstColumn=_columnInfos.get(0);
		if (MyType.isIterType(firstColumn.first)) iteration=true;
		else iteration=false;
		
		for (int i=1; i<_columnInfos.size(); i++) {
			Pair<Class, String> column=_columnInfos.get(i);
			Assert.not_true(MyType.isIterType(column.first),
					"iteration column should be the first column");
		}
	}
	
	Class[] extractTypes(List<Pair<Class, String>> _columnInfos) {
		ArrayList<Class> types=new ArrayList<Class>(_columnInfos.size());
		for (Pair<Class, String> column: _columnInfos) {
			if (MyType.isIterType(column.first)) continue;
			types.add(column.first);
		}
		Class[] typeArray = new Class[types.size()];
		return types.toArray(typeArray);
	}
	
	String[] extractColumnNames(String idxName, List<Pair<Class, String>> _columnInfos) {
		ArrayList<String> names = new ArrayList<String>();
		names.add(idxName);
		for (Pair<Class, String> column:_columnInfos) {
			if (MyType.isIterType(column.first)) continue; 
			names.add(column.second);
		}
		String[] nameArray = new String[names.size()];
		return names.toArray(nameArray);
	} 
	
	
	public void setOptions(List<Option> options) {
		for (Option opt: options) {
			switch (opt.opt) {
			case Predefined:
				isPredefined=true;
				break;
			case Single:
				addSingleRowColumns(opt.args);
				break;
			case Index:
				addIndexColumns(opt.args);
				break;
			case Edge:
				setEdgeSrcSnkColumns(opt.args);
				break;
			case SortAsc:
				setSortOrderAsc();
				break;
			case SortDesc:
				setSortOrderDesc();
				break;
			case MultiSet:
				setMultiSet();
				break;
			default:
				Assert.not_supported();
			}
		}		
	}
	
	void setMultiSet() { isMultiSet=true; }
	public boolean isMultiSet() { return isMultiSet; }
	
	
	void setSortOrderAsc() { setSortOrder(true); }	
	void setSortOrderDesc() { setSortOrder(false); }
	void setSortOrder(boolean asc) {
		Assert.true_(types.length==1,
			"Sort option is only supported for relations with 2 or less columns.");
		Assert.true_(idxType.equals(hash.class));
		sort=true;
		if (asc) sortAsc=true;
		else sortAsc=false;
	}
	
	public boolean isSorted() { return sort; }
	public boolean isSortOrderAsc() {
		Assert.true_(sort, "Cannot ask sort-order for not sorted relation");
		if (sortAsc) return true;
		else return false;
	}
	public boolean isSortOrderDesc() {
		return !isSortOrderAsc();
	}
	
	
	int getColumnIndexFromName(String name) {
		int idx=0;
		for (String columnName: columnNames) {
			if (columnName.equals(name)) return idx;
			idx++;
		}
		Assert.die("Cannot find column name["+name+"]");
		return idx;
	}
	
	boolean isContinous(TIntArrayList ilist) {
		if (ilist.size()==1) return true;		
		int prev=ilist.get(0);
		
		for (int i=1; i<ilist.size(); i++) {
			int curr= ilist.get(i);
			if (curr!=prev+1) return false;
			prev=curr;
		}
		return true;
	}
	
	List<Integer> sort(List<Integer> list) {
		Integer[] indices = new Integer[list.size()];
		list.toArray(indices);
		Arrays.sort(indices);
		List<Integer> ret = new ArrayList<Integer>();
		for (int i:indices) ret.add(i);
		return ret;
	}
	
	void addIndexColumns(List<String> columns) {
		if (subIndexSize != 0) {
			Assert.die("Relations can have only single sub-index.");
		}
		TIntArrayList columnIndices = new TIntArrayList();
		for (String col: columns) {
			int idx=getColumnIndexFromName(col);
			if (idxType!=null) idx--;				
			columnIndices.add(idx);			 
		}		
		columnIndices.sort();
		
		Assert.true_(columnIndices.get(0)==0, 
				"Sub-index should start from the first column!");
		if (!isContinous(columnIndices)) {
			Assert.die("A group of index columns should be continuous:"+columns);
		}		
		subIndexSize = columnIndices.size();
	}	
	
	void setEdgeSrcSnkColumns(List<String> columns) {
		edgeColumns = new int[2];
		isEdgeRelation = true;
		Assert.true_(columns.size()==2);
				
		int src = getColumnIndexFromName(columns.get(0));
		int sink = getColumnIndexFromName(columns.get(1));
		edgeColumns[0] = src;
		edgeColumns[1] = sink;
		Assert.true_(src==0, 
			"Src column should be the 1st column, but "+columns.get(0)+" is not the 1st column");
	}	
	
	public int getEdgeSrc() {
		Assert.true_(isEdgeRelation);
		return edgeColumns[0];
	}
	public int getEdgeSink() {
		Assert.true_(isEdgeRelation);
		return edgeColumns[1];
	}
	
	void addSingleRowColumns(List<String> columns) {
		for (String col: columns) {
			int idx=getColumnIndexFromName(col);
			if (idxType!=null) idx--;
			singleRowColumns.add(idx);
		}
	}
	
	public String toString() {
		String result = "DECL";
		
		if (idxType != null) {
			result += "[" + idxType + "]";
		}
		result += "(";
		for (int i=0; i<types.length; i++) {
			Class t = (Class)types[i];
			result += t;
			if (i!= types.length-1) result += ",";
		}
		result += ")";
		return result;
	}
	
	*/
}
