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
import socialite.parser.TableType;
import socialite.util.AnalysisException;
import socialite.util.Assert;
import socialite.util.InternalException;
import socialite.util.ParseException;

public class TableDecl implements Serializable {
    private static final long serialVersionUID = 1;

    String name;
    int partitionCol;
    List<ColumnDecl> colDecls;
    NestedTableDecl nestedTable;
    Map<String, SortBy> sortBy;
    Map<String, IndexBy>  indexBy;
    TObjectIntHashMap<String> colNameToPos;
    boolean predefined=false;
    boolean concurrent=false;
    boolean multiSet=false;
    boolean approx=false;
    int iterCol = -1;
    int maxIter = 2;
    int groupby = -1;
    TemplateType templateType; // for future use

    public TableDecl(String _name, List<ColumnDecl> _colDecls, NestedTableDecl _table) {
        name = _name;
        colDecls = _colDecls;
        if (colDecls==null) {
            colDecls = new ArrayList<>();
        }
        nestedTable = _table;
        sortBy = new HashMap<>();
        indexBy = new HashMap<>();

        colNameToPos = new TObjectIntHashMap<>();
        setColumnPositions();
    }

    public void checkTypes(List<?> params) throws InternalException {
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

    public int getPartitionColumn() {
        return partitionCol;
    }
    public String toString() {
        String s=name;
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
        assert colDecls.size()>0;
        ColumnDecl d = colDecls.get(0);
        if (d.option() instanceof ColIter) {
            assert colDecls.size() > 1;
            d = colDecls.get(1);
        }
        return d;
    }
    public ColumnDecl last() {
        if (nestedTable==null) {
            int size = colDecls.size();
            assert size>0;
            ColumnDecl d=colDecls.get(size-1);
            if (d.option() instanceof ColIter) {
                assert colDecls.size() > 1;
                d = colDecls.get(size-2);
            }
            return d;
        } else {
            return nestedTable.last();
        }
    }

    public int hashCode() {
        return name.hashCode();
    }
    public boolean equals(Object o) {
        if (o==this) return true;
        if (!(o instanceof TableDecl)) return false;

        TableDecl td=(TableDecl)o;
        if (!name.equals(td.name)) return false;
        if (!colDecls.equals(td.colDecls)) return false;
        if (nestedTable!=null && !nestedTable.equals(td.nestedTable))
            return false;

        if (multiSet!=td.multiSet) return false;
        if (predefined!=td.predefined) return false;

        if (sortBy==null && td.sortBy!=null) return false;
        if (sortBy!=null && td.sortBy==null) return false;
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

    public List<ColumnDecl> colDecls() {
        List<ColumnDecl> decls = new ArrayList<ColumnDecl>();
        decls.addAll(colDecls);
        if (nestedTable!=null) {
            nestedTable.colDecls(decls);
        }
        return decls;
    }

    public TIntArrayList getNestedTableIndices() {
        TIntArrayList nestedTableIndices = new TIntArrayList();
        if (nestedTable!=null) {
            int nestedIdx = colDecls.size();
            for (ColumnDecl decl:colDecls) {
                if (decl.option() instanceof ColIter)
                    nestedIdx--;
            }
            nestedTableIndices.add(nestedIdx);
            nestedTable.addNestedTableIndices(colDecls.size(), nestedTableIndices);
        }

        return nestedTableIndices;
    }

    public List<ColumnGroup> buildColumnGroups() {
        List<ColumnGroup> columnGroupList = new ArrayList<ColumnGroup>();
        List<Column> cols = new ArrayList<Column>();
        int relPos=0;

        for (int i=0; i<colDecls.size(); i++) {
            ColumnDecl d=colDecls.get(i);
            if (d.option() instanceof ColIter) continue;
            Column c=new Column(d);
            c.setRelPos(relPos++);
            cols.add(c);
        }
        Column tmp[] = new Column[cols.size()];
        columnGroupList.add(new ColumnGroup(cols.toArray(tmp)));

        if (nestedTable!=null) nestedTable.buildColumnGroups(columnGroupList);

        for (ColumnGroup cg:columnGroupList) {
            for(Column c:cg.columns()) {
                SortBy sb=sortBy.get(c.name());
                if (sb!=null) c.setSorted(sb.isAsc());
                IndexBy ib=indexBy.get(c.name());
                if (ib!=null) c.setIndexed();
            }
        }
        return columnGroupList;
    }

    void setColumnPositions() {
        int pos=0;
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
    public int numAllColumns() { return numColumns(); }
    int numColumns() {
        int num = colDecls.size();
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

            if (opt instanceof TemplateType) {
                templateType = (TemplateType) opt;
            } else if (opt instanceof SortBy) {
                sortBy.put(opt.columnName(), (SortBy) opt);
            } else if (opt instanceof IndexBy) {
                indexBy.put(opt.columnName(), (IndexBy)opt);
            } else if (opt instanceof ShardBy) {
                String col = opt.columnName();
                partitionCol = colNameToPos.get(col);
                ColOpt colOpt = allColDecls().get(partitionCol).option();
                if (partitionCol != 0 && colOpt instanceof ColRange) {
                    throw new ParseException("Cannot use shardby for a column with a range annotation, except for a first column.");
                }
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
