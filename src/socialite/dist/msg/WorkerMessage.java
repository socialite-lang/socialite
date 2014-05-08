package socialite.dist.msg;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

import socialite.eval.Command;
import socialite.eval.EvalWithTable;
import socialite.eval.TmpTablePool;
import socialite.resource.Sender;
import socialite.tables.AbstractTableInst;
import socialite.tables.TableInst;
import socialite.util.Assert;
import socialite.util.ByteBufferOutputStream;
import socialite.util.ByteBufferPool;
import socialite.util.FastOutputStream;


public class WorkerMessage implements Externalizable {
	static final long serialVersionUID = 1;
	
	public EvalWithTable evalT;
	transient int slaveid;
	transient ByteBuffer serialized;
	transient int tableid=-1;

	public WorkerMessage() { }	
	public WorkerMessage(EvalWithTable _cmd) {
		evalT = _cmd;	
	}
	
	public int tableId() { return evalT.getTable().id(); }
	
	public boolean isSerialized() { return serialized!=null; }
	public ByteBuffer serialize(boolean free) throws IOException  {
		if (serialized==null) {
			final TableInst t = evalT.getTable();
			tableid = t.id();
			int size = guessMessageSize();		
			ByteBufferOutputStream bbos = new ByteBufferOutputStream(size);
			final float incby = 2f;
			bbos.onCapacityInc(incby, new Runnable() {
				public void run() {
					if (t.requireFree()) {
						TmpTablePool.forget(t);
						t.setRequireFree(false);
						t.setRequireFreeSmall(false);
					}
					t.incEstimFactor(incby);
				}
			});
			ObjectOutputStream oos = new FastOutputStream(bbos);
			oos.writeObject(this);			
			oos.flush();
			oos.close();
			t.setSharedSizeEstimFactor(t.sizeEstimFactor());			
			serialized=bbos.buffer();			
			if (free && t.requireFree()) {
				TmpTablePool.free(t);
			}
			evalT = null;
		}
		return serialized;
	}
	public ByteBuffer serialize() throws IOException  {
		return serialize(true);
	}	
	
	int getSize(TableInst table) {	
		return (int)table.totalDataSize();
	}
	public int guessMessageSize() {
		int size=128;
		size += (int)getSize(evalT.getTable());
		if (size < ByteBufferPool.smallBufferSize()) {
			size = (int)(size*1.2f);
			if (size > ByteBufferPool.smallBufferSize())
				size = ByteBufferPool.smallBufferSize();
		}
		return size;
	}
	
	public void emptyTable() { getTable().clear(); }
	public void copyAndEmptyTable() { evalT.copyAndEmptyTable(); }
	public void setSlaveId(int _slaveid) { slaveid = _slaveid; }
	public int getSlaveId() { return slaveid; }
	public TableInst getTable() { return evalT.getTable(); }
	public int getTableId() {
		if (tableid >= 0) return tableid;
		return evalT.getTable().id(); 
	}
	public int getSliceIdx() { return evalT.getSliceIdx(); }
	
	public Command get() { return evalT; }

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		evalT=(EvalWithTable)in.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(evalT);
	}
	
	public String toString() {
		return evalT.toString();
	}
}
