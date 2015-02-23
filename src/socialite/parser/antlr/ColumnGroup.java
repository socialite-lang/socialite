package socialite.parser.antlr;


import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import socialite.parser.Column;
import socialite.parser.MyType;
import socialite.parser.Variable;
import socialite.util.Assert;

/* Represents all the columns in the same nesting level
 */
public class ColumnGroup implements Externalizable {
	private static final long serialVersionUID = 1;

	Column[] columns;
	Class[] types;	
	
	public ColumnGroup() {}
	public ColumnGroup(Column _columns[]) {
		columns = _columns;
	}
	
	public int hashCode() {
		int h=0;
		for (Column c:columns)
			h ^= c.hashCode();
		return h;
	}
	public boolean equals(Object o) {
		if (this==o) return true;
		if (!(o instanceof ColumnGroup)) return false;
		ColumnGroup group = (ColumnGroup)o;
		if (columns.length != group.columns.length) return false;
		for (int i=0; i<columns.length; i++) {
			if (!columns[i].equals(group.columns[i]))
				return false;
		}
		return true;
	}
	
	public boolean isFirst() { return startIdx()==0; }
	public int startIdx() { return columns[0].position(); }
	public int endIdx() { return startIdx()+columns.length-1; }
	
	public boolean isSorted() {
		for (Column c:columns) {
			if (c.isSorted()) return true;
		}
		return false;
	}
	public boolean hasIndex() {
		for (Column c:columns) {
			if (c.isIndexed()) return true;
		}
		return false;
	}
	public Class[] types() {
		if (types==null) {
			types = new Class[columns.length];
			for (int i=0; i<types.length; i++)
				types[i] = columns[i].type();
		}
		return types;
	}	
	
	public Column first() { return columns[0]; }
	public Column last() { return columns[columns.length-1]; }
	public Column[] columns() { return columns;}
	public Class firstType() { return types()[0]; }
	public Class lastType() { return types()[types().length-1]; }
	public int size() { return columns.length; }
	
	public String stringify() {
		int start=startIdx();
		String ret="";
		for (int i=start; i<start+columns.length; i++) {
			ret += "_"+i;
		}
		return ret;
	}
	
	public String toString() { return stringify(); }

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		columns = (Column[])in.readObject();
		types = (Class[])in.readObject();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(columns);
		out.writeObject(types);
	}	
}