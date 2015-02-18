package socialite.eval;


import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.resource.VisitorBuilder;
import socialite.tables.TmpTableInst;
import socialite.tables.TableInst;
import socialite.visitors.IVisitor;

public class EvalWithTable extends EvalCommand implements Externalizable {
	static final long serialVersionUID = 1;
	public static final Log L=LogFactory.getLog(EvalWithTable.class);
	
	TmpTableInst table;
	//int sliceIdx;
	transient int tableId=-1;
	
	public EvalWithTable() {}
	public EvalWithTable(int _epochId, int _ruleId, TmpTableInst _table, int _sliceIdx) {
		super(_epochId, _ruleId);
		table =_table;
	}

	public TmpTableInst getTable() { return table; }
	
	public String toString() { 
		int id=table.id();
		return "Eval w/Table rule["+ruleId+"], table["+id+"]";
	}	
	public IVisitor[] newInst(VisitorBuilder builder) {
		IVisitor[] visitors;
		visitors=builder.getNewVisitorInst(ruleId, new TableInst[]{table});
		return visitors;
	}	

	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		super.readExternal(in);
	
		//sliceIdx = in.readInt();
		table = (TmpTableInst)in.readObject();		
		assert table!=null;
	}
	
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);		
		assert table!=null;
		
		//out.writeInt(sliceIdx);
		out.writeObject(table);
	}
}