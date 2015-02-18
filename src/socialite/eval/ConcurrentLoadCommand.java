package socialite.eval;

import socialite.resource.TableSliceMap;
import socialite.tables.Tuple;
import socialite.tables.TupleBlock;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;


public class ConcurrentLoadCommand implements Command {

    TupleBlock block;
    public ConcurrentLoadCommand(TupleBlock _block) {
        block = _block;
    }

    public Iterator<Tuple> iterator(TableSliceMap sliceMap, int sliceIdx) {
        return block.iterator(sliceMap, sliceIdx);
    }
    public int getTableId() { return block.getTableId(); }

    @Override
    public void setReceived() { return; }

    @Override
    public boolean isReceived() { return true; }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }
}
