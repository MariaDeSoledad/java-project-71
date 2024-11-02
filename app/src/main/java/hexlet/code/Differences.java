package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.Set;
import java.util.LinkedHashMap;
import java.util.Objects;

public class Differences {
    public static List<Map<String, Object>> getDiff(Map<String, Object> file1, Map<String, Object> file2) {

        Set<String> sortedKeys = getSortedKeys(file1, file2);

        List<Map<String, Object>> result = new ArrayList<>();

        for (var key: sortedKeys) {
            var value1 = file1.getOrDefault(key, "Value not found");
            var value2 = file2.getOrDefault(key, "Value not found");
            Map<String, Object> mapResult;

            if (!file1.containsKey(key)) {
                mapResult = getDifferencesMap("added", key, value2);
            } else if (!file2.containsKey(key)) {
                mapResult = getDifferencesMap("deleted", key, value1);
            } else if (Objects.equals(file1.get(key), file2.get(key))) {
                mapResult = getDifferencesMap("unchanged", key, value1);
            } else {
                mapResult = getDifferencesMap("changed", key, value1, value2);
            }
            result.add(mapResult);
        }
        return result;
    }
    public static Map<String, Object> getDifferencesMap(String status, String name, Object value) {
        Map<String, Object> diffMap = new LinkedHashMap<>();

        diffMap.put("status", status);
        diffMap.put("name", name);

        if (value == null) {
            diffMap.put("value1", null);
        } else {
            diffMap.put("value1", value);
        }

        return diffMap;
    }

    public static Map<String, Object> getDifferencesMap(String status, String name, Object value1, Object value2) {
        Map<String, Object> diffMap = getDifferencesMap(status, name, value1);
        if (value2 == null) {
            diffMap.put("value2", null);
        } else {
            diffMap.put("value2", value2);
        }
        return diffMap;
    }

    private static Set<String> getSortedKeys(Map<String, Object> map1, Map<String, Object> map2) {
        Set<String> sortedKeys = new TreeSet<>();
        sortedKeys.addAll(map1.keySet());
        sortedKeys.addAll(map2.keySet());
        return sortedKeys;
    }
}
