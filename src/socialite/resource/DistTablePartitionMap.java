package socialite.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.parser.GeneratedT;
import socialite.parser.Table;

public class DistTablePartitionMap extends TablePartitionMap {
	public static final Log L=LogFactory.getLog(DistTablePartitionMap.class);

	WorkerAddrMap addrMap;
	public DistTablePartitionMap(WorkerAddrMap _addrMap) {
		super();
		addrMap = _addrMap;		
	}
	@Override
	public void addTable(Table t) {
        assert !(t instanceof GeneratedT) && partitionInfo.get(t.id()) == null;
        if (t.id() >= partitionInfo.size()) {
            addNullPartitionInfo(t.id());
        }
		partitionInfo.add(t.id(), DistPartitionInfo.create(this, t));
	}
	@Override
	public int machineIndexFor(int tableId, Object o) {
		DistPartitionInfo info = (DistPartitionInfo)partitionInfo.get(tableId);
		return info.machineIndexFor(o);
	}
	@Override
	public int machineIndexFor(int tableId, int i) {
		PartitionInfo info = partitionInfo.get(tableId);
		return info.machineIndexFor(i);
	}
	@Override
	public int machineIndexFor(int tableId, long l) {
		PartitionInfo info = partitionInfo.get(tableId);
		return info.machineIndexFor(l);
	}
	@Override
	public int machineIndexFor(int tableId, float f) {
		PartitionInfo info = partitionInfo.get(tableId);
		return info.machineIndexFor(f);
	}
	@Override
	public int machineIndexFor(int tableId, double d) {
		PartitionInfo info = partitionInfo.get(tableId);
		return info.machineIndexFor(d);
	}
	
	public boolean isLocal(int tableId, int key) {
		return partitionInfo.get(tableId).isLocal(key);
	}
}
