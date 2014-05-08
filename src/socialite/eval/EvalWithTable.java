package socialite.eval;


import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.resource.VisitorBuilder;
import socialite.tables.TableInst;
import socialite.visitors.IVisitor;

public class EvalWithTable extends EvalCommand implements Externalizable {
	static final long serialVersionUID = 1;
	public static final Log L=LogFactory.getLog(EvalWithTable.class);
	
	TableInst table;
	int sliceIdx;
	transient int tableId=-1;
	
	public EvalWithTable() {}
	public EvalWithTable(int _ruleId, TableInst _table, int _sliceIdx) {
		super(_ruleId);
		table =_table;
		sliceIdx = _sliceIdx;
	}

	public int getSliceIdx() { return sliceIdx; }
	public TableInst getTable() { return table; }
	
	public int getTableSize() { return table.size(); }	
	public String toString() { 
		int id=table.id();
		int size=getTableSize();		
		return "Eval w/Table rule["+ruleId+"], table["+id+"] size:"+size+"\n table(idx:"+sliceIdx+")";
	}	
	public IVisitor[] newInst(VisitorBuilder builder) {
		IVisitor[] visitors;
		visitors=builder.getNewVisitorInst(ruleId, table, sliceIdx);
		return visitors;
	}	

	public void copyAndEmptyTable() {
		TableInst oldTable = table;
		table = oldTable.copy();
		oldTable.clear();
	}
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		super.readExternal(in);
	
		sliceIdx = in.readInt();
		table = (TableInst)in.readObject();		
		assert table!=null;
	}
	
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);		
		assert table!=null;
		
		out.writeInt(sliceIdx);
		out.writeObject(table);
	}
}