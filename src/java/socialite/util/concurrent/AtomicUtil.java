package socialite.util.concurrent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AtomicUtil {
    static private class AtomicMergeHelper {
        final public Map map;

        AtomicMergeHelper(Map old, Map newMap) {
            if (old.keySet().equals(newMap.keySet())) {
                map = newMap;
            } else {
                map = new HashMap<>(old);
                map.putAll(newMap);
            }
        }
    }
    public static Map atomicMerge(Map dest, Map addedMapping) {
        return new AtomicMergeHelper(dest, addedMapping).map;
    }


    static private class AtomicIncCapHelper<T> {
        final T[][] array;
        AtomicIncCapHelper(T[][] old, int size) {
            array = Arrays.copyOf(old, size);
        }
    }

    public static <T> T[][] atomicIncreaseCapacity(T[][] array, int newCapacity) {
        assert newCapacity > array.length;
        return new AtomicIncCapHelper<T>(array, newCapacity).array;
    }
}
