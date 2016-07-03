package socialite.functions;

import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.List;

public class EnumId {
    static ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> kindToKeyMap = new ConcurrentHashMap<>();
    static ConcurrentHashMap<String, ArrayList<String>> kindToIdMap = new ConcurrentHashMap<>();
    public static int get(String kind, String key) {
        ConcurrentHashMap<String, Integer> map = kindToKeyMap.get(kind);
        if (map == null) {
            map = new ConcurrentHashMap<>();
            ConcurrentHashMap<String, Integer> prev = kindToKeyMap.putIfAbsent(kind, map);
            if (prev != null) { map = prev; }
        }
        Integer id = map.get(key);
        if (id == null) {
            synchronized (map) {
                if (!map.containsKey(key)) {
                    id = map.size();
                    map.put(key, id);
                    ArrayList<String> list = kindToIdMap.get(kind);
                    if (list == null) {
                        list = new ArrayList<>();
                        kindToIdMap.put(kind, list);
                    }
                    list.add(key);
                }
            }
            if (id == null) {
                id = map.get(key);
            }
        }
        return id;
    }

    public static List<String> get(String kind) {
        return kindToIdMap.get(kind);
    }

    public static String get(String kind, int id) {
        ArrayList<String> list = kindToIdMap.get(kind);
        if (list == null) {
            return "null";
        }
        return list.get(id);
    }
}
