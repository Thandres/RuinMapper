package ruinMapper.hexagon.domain;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CircularManagmentHelper {
    public static <D> void removeFromMap(
            Map<String, Set<D>> map,
            String key, D value) {
        if (map.containsKey(key)) {
            map.get(key).remove(
                    value);
        } else {
            //TODO Exceptions for everything
        }
    }

    public static <T, D> void linkedRemove(
            Map<String, Set<D>> tMap,
            Map<String, Set<T>> dMap,
            T key, D value) {
        removeFromMap(tMap, key.toString(), value);
        removeFromMap(dMap, value.toString(), key);
    }

    public static <D> void addToSetMap(
            Map<String, Set<D>> map, String key, D value) {
        if (map.containsKey(key)) {
            map.get(key).add(
                    value);
        } else {
            map.put(key,
                    new HashSet<>());
            map.get(key).add(value);
        }
    }

    public static <T, D> void linkedAdd(
            Map<String, Set<D>> tMap,
            Map<String, Set<T>> dMap,
            T key, D value) {
        addToSetMap(tMap, key.toString(), value);
        addToSetMap(dMap, value.toString(), key);
    }

    // deletes recordToDelete from every Set in dSetMap
    public static <T> void deleteRecord(
            Map<String, Set<T>> dSetMap,
            T recordToDelete) {
        for (Set<T> tSet : dSetMap.values()) {
            tSet.remove(recordToDelete);
        }
    }

    public static <T, D> void deleteLinkedRecord(
            Map<String, Set<D>> tSetMap,
            Map<String, Set<T>> dSetMap,
            T recordToDelete) {
        if (tSetMap
                .containsKey(recordToDelete.toString())) {
            tSetMap.remove(recordToDelete.toString())
                    .forEach(d -> removeFromMap(dSetMap,
                            d.toString(),
                            recordToDelete));
        }
    }
}
