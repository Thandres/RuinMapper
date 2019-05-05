package ruinMapper.hexagon.domain;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CircularManagmentHelper {
    public static <T, D> void removeFromMap(
            Map<T, Set<D>> map,
            T key, D value) {
        if (map.containsKey(key)) {
            map.get(key).remove(
                    value);
        } else {
            //TODO Exceptions for everything
        }
    }

    public static <T, D> void linkedRemove(
            Map<T, Set<D>> tMap,
            Map<D, Set<T>> dMap,
            T key, D value) {
        removeFromMap(tMap, key, value);
        removeFromMap(dMap, value, key);
    }

    public static <T, D> void addToMap(
            Map<T, Set<D>> map, T key, D value) {
        if (map.containsKey(key)) {
            map.get(key).add(
                    value);
        } else {
            map.put(key,
                    new HashSet<>());
            map.get(key).add(value);
        }
    }

    public static <T, D> void linkedAdd(Map<T, Set<D>> tMap,
                                        Map<D, Set<T>> dMap,
                                        T key, D value) {
        addToMap(tMap, key, value);
        addToMap(dMap, value, key);
    }

    // deletes recordToDelete from every Set in dSetMap
    public static <T, D> void deleteRecord(
            Map<D, Set<T>> dSetMap,
            T recordToDelete) {
        for (Set<T> tSet : dSetMap.values()) {
            tSet.remove(recordToDelete);
        }
    }

    public static <T, D> void deleteLinkedRecord(
            Map<T, Set<D>> tSetMap,
            Map<D, Set<T>> dSetMap,
            T recordToDelete) {
        if (tSetMap.containsKey(recordToDelete)) {
            tSetMap.remove(recordToDelete)
                    .forEach(d -> removeFromMap(dSetMap, d,
                            recordToDelete));
        }
    }
}
