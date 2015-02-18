package socialite.eval;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import socialite.resource.VisitorBuilder;
import socialite.tables.TableInst;
import socialite.util.Assert;
import socialite.visitors.IVisitor;


public class EvalCommand implements Command, Externalizable {
	private static final long serialVersionUID = 1;	

    public int epochId;
	public int ruleId;
	transient boolean received=false;
	
	public EvalCommand() {}
	public EvalCommand(int _epochId, int _ruleId) {
        epochId = _epochId;
		ruleId = _ruleId;
	}

    public int getEpochId() {return epochId;}
	public int getRuleId() {return ruleId;}
	public String toString() { return "Eval rule["+ruleId+"]";}
	
	public IVisitor[] newInst(VisitorBuilder builder) {		
		return builder.getNewVisitorInst(ruleId);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
        epochId = in.readInt();
        ruleId = in.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(epochId);
        out.writeInt(ruleId);
	}
	
	@Override
	public void setReceived() { received=true; }
	@Override
	public boolean isReceived() { return received; }
}