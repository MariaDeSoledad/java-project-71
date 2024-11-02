package hexlet.code;

import java.io.IOException;
import java.util.Map;
import java.util.List;

import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

public class Formatter {
    public static String constructFormatFromMap(List<Map<String, Object>> diffList, String format) throws IOException {
        return switch (format) {
            case "stylish" -> Stylish.formatStylish(diffList);
            case "plain" -> Plain.formatPlain(diffList);
            case "json" -> Json.formatJson(diffList);
            default -> throw new IOException("Unknown format! => " + format);
        };
    }
}
