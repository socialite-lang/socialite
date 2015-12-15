package socialite.resource;

import java.util.Arrays;

/**
 * Mapping table partitions to nodes
 */
public class PartitionNodeMap {
    public static PartitionNodeMap create(int nodeNum, int totalPartitionNum) {
        return create(nodeNum, totalPartitionNum, false);
    }
    public static PartitionNodeMap create(int nodeNum, int totalPartitionNum, boolean range) {
        if (range) {
            return new RangePartitionNodeMap(nodeNum, totalPartitionNum);
        } else {
            return new PartitionNodeMap(nodeNum, totalPartitionNum);
        }
    }

    final Object partitionIdxToNode;
    int nodeNum;
    PartitionNodeMap(int _nodeNum, int totalPartitionNum) {
        partitionIdxToNode = new int[totalPartitionNum];
        nodeNum = _nodeNum;
        initMapping(nodeNum);
    }

    void initMapping(int nodeNum) {
        int[] map = map();
        int nodeid = 0;
        for (int i = 0; i < map.length; i++) {
            map[i] = nodeid++;
            nodeid %= nodeNum;
        }
    }

    int[] map() {
        return (int[])partitionIdxToNode;
    }

    public int node(int partition) {
        return map()[partition];
    }

    public int[] partitions(int node) {
        int[] p = new int[map().length];
        int idx = 0;
        for (int i=0; i<map().length; i++) {
            if (map()[i] == node) {
                p[idx++] = i;
            }
        }
        return Arrays.copyOfRange(p, 0, idx);
    }

    public int update(int partition, int node) {
        int old = map()[partition];
        map()[partition] = node;
        return old;
    }
}

class RangePartitionNodeMap extends PartitionNodeMap {
    RangePartitionNodeMap(int nodeNum, int totalPartitionNum) {
        super(nodeNum, totalPartitionNum);
    }
    void initMapping(int nodeNum) {
        int[] map = map();
        int nodeid = 0;
        for (int i = 0; i < map.length; i++) {
            map[i] = nodeid++;
            if (nodeid >= nodeNum) {
                nodeid = 0;
            }
        }
    }
}
