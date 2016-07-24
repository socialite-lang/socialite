package socialite.tables;

import gnu.trove.map.hash.TIntObjectHashMap;

public class IndexMap {
    static final int SLOT_SIZE = 8;
    int size;
    SIndex[] fastIndex = new SIndex[SLOT_SIZE];
    TIntObjectHashMap<SIndex> indexMap;
    public IndexMap() { }

    public void put(int col, SIndex index) {
        if (col < SLOT_SIZE) {
            fastIndex[col] = index;
        } else {
            indexMap.put(col, index);
        }
    }

    public SIndex get(int col) {
        if (col < SLOT_SIZE) {
            return fastIndex[col];
        } else {
            return indexMap.get(col);
        }
    }

    public int size() {
        return size;
    }
}
