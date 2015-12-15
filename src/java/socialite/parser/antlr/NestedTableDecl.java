package socialite.parser.antlr;

import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

import socialite.parser.Column;
import socialite.util.Assert;

public class NestedTableDecl implements Externalizable {
	private static final long serialVersionUID = 1;

	List<ColumnDecl> colDecls;
	NestedTableDecl nestedTable;
	
	public NestedTableDecl() {}
	public NestedTableDecl(List<ColumnDecl> _colDecls, NestedTableDecl _table) {
		assert _colDecls!=null && _colDecls.size()>0;
		colDecls = _colDecls;
		nestedTable = _table;
	}
	
	public ColumnDecl first() { return colDecls.get(0); }
	public ColumnDecl last() { 
		if (nestedTable!=null)
			return nestedTable.last();
		return colDecls.get(colDecls.size()-1);
	}
	public String toString() {
		String s="";
		boolean first=true;
		for (ColumnDecl decl:colDecls) {
			if (!first) s += ",";
			s += decl;
			first=false;
		}
		if (nestedTable!=null)
			s += ",("+nestedTable+")";
		return s;
	}
	public NestedTableDecl clone() {
		List<ColumnDecl> colDeclsClone=new ArrayList<ColumnDecl>();
		for (ColumnDecl cd:colDecls) colDeclsClone.add(cd.clone());
		NestedTableDecl nestedClone = null;
		if (nestedTable!=null)
			nestedClone = nestedTable.clone();
		return new NestedTableDecl(colDeclsClone, nestedClone);
	}
	public boolean equals(Object o) {
		if (o==this) return true;
		if (!(o instanceof NestedTableDecl)) return false;
		NestedTableDecl d=(NestedTableDecl)o;
		if (!colDecls.equals(d.colDecls)) return false;
		
		if (nestedTable!=null)
			return nestedTable.equals(d.nestedTable);
		else return true;
	}
	
	public List<ColumnDecl> getAllColDecls() {
		List<ColumnDecl> result=new ArrayList<ColumnDecl>();
		result.addAll(colDecls);
		if (nestedTable!=null) {
			result.addAll(nestedTable.getAllColDecls());
		}
		return result;
	}
	public int numColumns() {
		int numColumns = colDecls.size();
		if (nestedTable != null) {
			numColumns += nestedTable.numColumns();
		} 
		return numColumns;
	}
	
	public void setColumnPosition(int start, TObjectIntHashMap<String> colNameToPos) {
		int i=0;
		for ( ; i<colDecls.size(); i++) {
			ColumnDecl d = colDecls.get(i);
			d.setPos(i+start);
			if (colNameToPos.contains(d.name))
				Assert.die("Column name "+d.name+" is already used");
			colNameToPos.put(d.name, i+start);
		}
		if (nestedTable!=null)
			nestedTable.setColumnPosition(start+i, colNameToPos);
	}	
	
	public void colDecls(List<ColumnDecl> decls) {
		decls.addAll(colDecls);
		if (nestedTable!=null)
			nestedTable.colDecls(decls);
	}
	
	public void addNestedTableIndices(int startIdx, TIntArrayList indices) {
		if (nestedTable != null) {
			indices.add(startIdx + colDecls.size());
			nestedTable.addNestedTableIndices(startIdx + colDecls.size(), indices);
		}
	}
	
	public void buildColumnGroups(List<ColumnGroup> columnGroupList) {
		Column[] cols = new Column[colDecls.size()];
		for (int i=0; i<colDecls.size(); i++) {
			Column c=new Column(colDecls.get(i));
			c.setRelPos(i);
			cols[i]=c;
		}
		ColumnGroup columns = new ColumnGroup(cols);
		columnGroupList.add(columns);
		
		if (nestedTable!=null) {
			nestedTable.buildColumnGroups(columnGroupList);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		colDecls = (List<ColumnDecl>)in.readObject();
		nestedTable = (NestedTableDecl)in.readObject();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(colDecls);
		out.writeObject(nestedTable);
	}
}
