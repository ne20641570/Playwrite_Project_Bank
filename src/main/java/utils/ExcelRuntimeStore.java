package utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ExcelRuntimeStore {

    private static final Map<String, Map<Integer, Map<String, String>>> DATA =
            new ConcurrentHashMap<>();

    private ExcelRuntimeStore() {}

    public static void put(String sheet, int row, String column, String value) {
        DATA
                .computeIfAbsent(sheet, s -> new ConcurrentHashMap<>())
                .computeIfAbsent(row, r -> new ConcurrentHashMap<>())
                .put(column, value);
    }

    public static int getMaxRow(String sheet, String column) {
        return DATA.getOrDefault(sheet, Map.of())
                .keySet()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
    }

    public static Map<String, Map<Integer, Map<String, String>>> getAll() {
        return DATA;
    }

    public static void clear() {
        DATA.clear();
    }
}
