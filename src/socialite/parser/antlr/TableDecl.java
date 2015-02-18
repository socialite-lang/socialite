package socialite.parser.antlr;

import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import socialite.parser.Column;
import socialite.parser.Const;
import socialite.parser.MyType;
import socialite.util.AnalysisException;
import socialite.util.Assert;
import socialite.util.InternalException;
import socialite.util.ParseException;

public class TableDecl implements Serializable {
	private static final long serialVersionUID = 1;
	
	String name;
	ColumnDecl locationColDecl;
	List<ColumnDecl> colDecls;
	NestedTableDecl nestedTable;
	Map<String, SortBy> sortBy;
	Map<String, OrderBy>  orderBy;
	Map<String, IndexBy>  indexBy;	
	TObjectIntHashMap<String> colNameToPos;
	boolean predefined=false;
    boolean concurrent=false;
	boolean multiSet=false;
	boolean approx=false;
	int iterCol = -1;
	int maxIter = 2;
	int groupby = -1;
	
	public TableDecl(String _name, ColumnDecl _locationColDecl, List<ColumnDecl> _colDecls, 
					 NestedTableDecl _table) {
		name = _name;
		locationColDecl = _locationColDecl;
		colDecls = _colDecls;
		if (colDecls==null) colDecls = new ArrayList<ColumnDecl>();
		nestedTable = _table;
		sortBy = new HashMap<String, SortBy>();
		orderBy = new HashMap<String, OrderBy>();
		indexBy = new HashMap<String, IndexBy>();
		
		colNameToPos = new TObjectIntHashMap<String>();
		setColumnPositions();
	}
		
	public void checkTypes(Object locationParam, List<?> _params) throws InternalException {
		if (locationColDecl==null && locationParam!=null)
			throw new InternalException("unexpected location operator for "+name());			
		if (locationColDecl!=null && locationParam==null)
			throw new InternalException("location operator is required for "+name());
		
		List<Object> params = new ArrayList<Object>();
		if (locationParam!=null) params.add(locationParam);
		params.addAll(_params);
		
		List<ColumnDecl> allColDecls = allColDecls();
		int colNum = allColDecls.size();
		if (hasIterColumn()) colNum++;
			
		if (colNum!=params.size())
			throw new InternalException("unexpected number of parameters for "+name());
				
		for (int i=0,j=0; i<params.size(); i++) {
			Object p=params.get(i);
			if (!(p instanceof Const)) {
				j++;
				continue;
			}
			
			Const c = (Const)p;
			Class<?> constType = MyType.javaType(c);
			Class<?> columnType;
			if (iterColumn()==i) { columnType = int.class; }
			else { columnType = allColDecls.get(j++).type(); }
			
			if (!columnType.equals(constType)) {
				throw new InternalException("unexpected type in column "+i+" of "+name());
			}
		}
	}	
	
	public String toString() {
		String s=name;		
		if (locationColDecl!=null) 
			s += "["+locationColDecl+"]";
		s+="(";
		boolean first=true;
		for (ColumnDecl cd:colDecls) {
			if (!first) s+= ",";
			s += cd;
			first=false;
		}
		if (nestedTable!=null) 
			s += ",("+nestedTable+")";
		s += ")";
		
		first=true;
		for (String n:indexBy.keySet()) {
			if (first) s+= " ";
			else s+= ",";
			s += "indexby "+n;
		}
		first=true;
		for (String n:sortBy.keySet()) {
			if (first) s+= " ";
			else s+= ",";
			s += "sortby "+n;
			SortBy sb=sortBy.get(n);
			if (sb.isAsc()) s += " asc";
			else s += " desc";
		}
		s+=".";
		return s;
	}	

	public ColumnDecl first() {
		if (locationColDecl !=null)
			return locationColDecl;
		if (colDecls.size()>0) {
			ColumnDecl d = colDecls.get(0);
			if (d.option() instanceof ColIter) {
				assert colDecls.size() > 1;
				d = colDecls.get(1);
			}
			return d;
		}
		else return nestedTable.first();
	}
	public ColumnDecl last() {
		if (nestedTable==null) {
			int size = colDecls.size();
			if (size>0) {
				ColumnDecl d=colDecls.get(size-1);
				if (d.option() instanceof ColIter) {
					assert colDecls.size() > 1;
					d = colDecls.get(size-2);
				}
				return d;
			}
			else {
				assert locationColDecl != null;
				return locationColDecl;
			}
		} else {
			return nestedTable.last();
		}
	}
	
	/*
	public TableDecl clone(String newName) {
		List<ColumnDecl> colDeclsClone=new ArrayList<ColumnDecl>();
		for (ColumnDecl cd:colDecls) colDeclsClone.add(cd.clone());
		
		ColumnDecl locationColDeclClone=null;
		if (locationColDecl!=null)
			locationColDeclClone=locationColDecl.clone();
		NestedTableDecl nestedTableClone=null;
		if (nestedTable!=null)
			nestedTableClone = nestedTable.clone();
		TableDecl td=new TableDecl(newName, locationColDeclClone, 
								   colDeclsClone, nestedTableClone);
		td.sortBy = sortBy;
		td.orderBy = orderBy;
		td.indexBy = indexBy;
		return td;
	}*/
	public int hashCode() {
		return name.hashCode();
	}
	public boolean equals(Object o) {
		if (o==this) return true;
		if (!(o instanceof TableDecl)) return false;
		TableDecl td=(TableDecl)o;
		
		if (!name.equals(td.name)) return false;
		if (locationColDecl==null) {
			if (td.locationColDecl!=null) return false;
		} else {
			if (!locationColDecl.equals(td.locationColDecl)) return false;
		}		
		if (!colDecls.equals(td.colDecls)) return false;
		
		if (nestedTable!=null)
			return nestedTable.equals(td.nestedTable);
		
		if (multiSet!=td.multiSet) return false;
		if (predefined!=td.predefined) return false;

		if (sortBy==null && td.sortBy!=null) return false;
		if (sortBy!=null && td.sortBy==null) return false;
		if (orderBy==null && td.orderBy!=null) return false;
		if (orderBy!=null && td.orderBy==null) return false;
		if (indexBy==null && td.indexBy!=null) return false;
		if (indexBy!=null && td.indexBy==null) return false;
		
		List<ColumnGroup> group1 = buildColumnGroups();
		List<ColumnGroup> group2 = td.buildColumnGroups();		
		for (int i=0; i<group1.size(); i++) {
			ColumnGroup g1=group1.get(i);
			ColumnGroup g2=group2.get(i);
			if (!g1.equals(g2)) return false;
		}		
		return true;
	}
	
	public void setName(String _name) { name=_name; }
	public String name() { return name; }
	
	public ColumnDecl locationColDecl() { return locationColDecl; }
	public List<ColumnDecl> colDecls() {
		List<ColumnDecl> decls = new ArrayList<ColumnDecl>();
		if (locationColDecl!=null) decls.add(locationColDecl);
		decls.addAll(colDecls);
		if (nestedTable!=null) {
			nestedTable.colDecls(decls);
		}
		return decls;
	}
	
	public TIntArrayList getNestedTableIndices() {
		TIntArrayList nestedTableIndices = new TIntArrayList();
		if (nestedTable!=null) {
			int offset=0;
			if (locationColDecl!=null) offset++;
			int nestedIdx = offset+colDecls.size();
			for (ColumnDecl decl:colDecls) {
				if (decl.option() instanceof ColIter)
					nestedIdx--;
			}
			nestedTableIndices.add(nestedIdx);
			nestedTable.addNestedTableIndices(offset+colDecls.size(), nestedTableIndices);
		}
		
		return nestedTableIndices;
	}
	
	public List<ColumnGroup> buildColumnGroups() {
		List<ColumnGroup> columnGroupList = new ArrayList<ColumnGroup>();
		
		List<Column> cols = new ArrayList<Column>();
		int relPos=0;
		if (locationColDecl!=null) {
			Column c=new Column(locationColDecl, true);
			c.setRelPos(relPos++);
			cols.add(c);			
		}
		if (colDecls!=null) {
			for (int i=0; i<colDecls.size(); i++) {
				ColumnDecl d=colDecls.get(i);
				if (d.option() instanceof ColIter) continue;
				Column c=new Column(d);
				c.setRelPos(relPos++);
				cols.add(c);
			}
		}
		Column tmp[] = new Column[cols.size()];
		columnGroupList.add(new ColumnGroup(cols.toArray(tmp)));
		
		if (nestedTable!=null) nestedTable.buildColumnGroups(columnGroupList);
		
		for (ColumnGroup cg:columnGroupList) {
			for(Column c:cg.columns()) {
				SortBy sb=sortBy.get(c.name());
				if (sb!=null) c.setSorted(sb.isAsc());
				OrderBy ob=orderBy.get(c.name());
				if (ob!=null) c.setOrdered();
				IndexBy ib=indexBy.get(c.name());
				if (ib!=null) c.setIndexed();
			}
		}
		return columnGroupList;
	}
	
	void setColumnPositions() {
		int pos=0;
		if (locationColDecl!=null) {
			if (locationColDecl.option() instanceof ColIter) {
				throw new AnalysisException("Table "+name+":location operator cannot be applied to iteration column");
			}
			locationColDecl.setPos(pos);
			colNameToPos.put(locationColDecl.name, pos);
			pos++;
		}
		for (ColumnDecl d:colDecls) {
			if (d.option() instanceof ColIter) {
				iterCol = pos;
				continue;
			}
			d.setPos(pos);
			assert !colNameToPos.contains(d.name);			
			colNameToPos.put(d.name, pos);
			pos++;
		}
		if (nestedTable!=null)
			nestedTable.setColumnPosition(pos, colNameToPos);
	}	
	public int getColumnPos(String name) {
		if (colNameToPos.contains(name))
			return colNameToPos.get(name);
		return -1;
	}
	
	public boolean hasIterColumn() { return iterCol >=0; }
	public int iterColumn() { return iterCol; }
	public int maxIter() { return maxIter; }
	
	public TIntArrayList sortbyCols() { return columnsWithOption(sortBy); }
	public TIntArrayList orderbyCols() { return columnsWithOption(orderBy); }
	public TIntArrayList indexbyCols() { return columnsWithOption(indexBy); }
	
	TIntArrayList columnsWithOption(Map<String, ? extends TableOpt> optMap) {
		TIntArrayList cols = new TIntArrayList();
		for (TableOpt o:optMap.values()) {
			int col = colNameToPos.get(o.columnName());
			cols.add(col);
		}
		cols.sort();
		return cols;
	}
	
	// includes index column
	public int numAllColumns() {		
		if (locationColDecl == null) return numColumns();
		else return 1+numColumns();
	}
	int numColumns() {
		int num=0;
		num += colDecls.size();
		if (hasIterColumn()) num--;
		if (nestedTable != null)
			num += nestedTable.numColumns();
		return num;
	}
	
	public boolean isNested() { return nestedTable!=null; }
	public boolean isMultiSet() { return multiSet; }
	public boolean isApproxSet() { return approx; }
    public boolean isConcurrent() { return concurrent; }
	public boolean isPredefined() { return predefined; }
	public boolean hasGroupBy() { return groupby>0; }
	public int groupby() { return groupby; }
	
	List<ColumnDecl> allColDecls() {
		List<ColumnDecl> decls = new ArrayList<ColumnDecl>();
		if (locationColDecl!=null) decls.add(locationColDecl);
		for (ColumnDecl d:colDecls) {
			if (d.option() instanceof ColIter) continue;
			decls.add(d);
		}
		if (nestedTable!=null) {
			nestedTable.colDecls(decls);
		}
		return decls;
	}
	
	public void setOptions(List<TableOpt> opts) {
		if (opts==null) return;		
		for (TableOpt opt:opts) {
			String name = opt.columnName();
			if (name!=null && !colNameToPos.containsKey(name))
				throw new ParseException("Column "+name+" does not exist in "+this.name);
			
			if (opt instanceof SortBy) {
				sortBy.put(opt.columnName(), (SortBy) opt);
			} else if (opt instanceof OrderBy) {
				if (orderBy.size()!=0) {
					String n=orderBy.keySet().iterator().next();
					throw new ParseException("Orderby column already exists: column "+n);
				}
				orderBy.put(opt.columnName(), (OrderBy)opt);				
			} else if (opt instanceof IndexBy) {
				indexBy.put(opt.columnName(), (IndexBy)opt);
			} else if (opt instanceof GroupBy) {
				int gb = ((GroupBy)opt).groupby();
				if (hasIterColumn()) gb--;
				if (gb<=0) throw new ParseException("groupby argument should be greater than 0 (1 if iteration column is used).");
				groupby = gb;
			} else if (opt instanceof Predefined) { 
				predefined = true;
			} else if (opt instanceof Concurrent) {
                concurrent = true;
            } else if (opt instanceof MultiSet) {
				multiSet = true;
			} else if (opt instanceof Approx) {
				approx = true;
			} else {
				Assert.impossible();
			}
		}
	}
}