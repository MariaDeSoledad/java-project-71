package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Command(
        name = "gendiff",
        description = "Compares two configuration files and shows a difference."
)
public class App implements Runnable {
    @Parameters(index = "0", paramLabel = "filepath1", description = "path to first file")
    private String filePath1;
    @Parameters(index = "1", paramLabel = "filepath2", description = "path to second file")
    private String filePath2;
    @Option(names = {"-f", "--format"}, description = "output format [default: ${DEFAULT-VALUE}]",
            defaultValue = "stylish", paramLabel = "format")
    private String format;
    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Show this help message and exit.")
    private boolean help;
    @Option(names = { "-V", "--version" }, versionHelp = true, description = "Print version information and exit.")
    private boolean version;
    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            var map1 = mapper.readValue(Files.readAllBytes(Paths.get(filePath1)),
                    new TypeReference<Map<String, Object>>() { });
            var map2 = mapper.readValue(Files.readAllBytes(Paths.get(filePath2)),
                    new TypeReference<Map<String, Object>>() { });
            Map<String, String> differences = getDifferences(map1, map2);
            System.out.println("{");
            differences.forEach((key, value) -> {
                if (key.endsWith("_new")) {
                    System.out.println("  + " + key.replace("_new", "") + ": " + value.substring(2));
                } else if (value.startsWith("- ") || value.startsWith("+ ")) {
                    System.out.println("  " + value.charAt(0) + " " + key + ": " + value.substring(2));
                } else {
                    System.out.println("    " + key + ": " + value);
                }
            });
            System.out.println("}");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    Map<String, String> getDifferences(Map<String, Object> map1, Map<String, Object> map2) {
        Map<String, String> differences = new HashMap<>();
        map1.forEach((key, value1) -> {
            if (!map2.containsKey(key)) {
                differences.put(key, "- " + value1);
            } else {
                var value2 = map2.get(key);
                if (!value1.equals(value2)) {
                    differences.put(key, "- " + value1);
                    differences.put(key + "_new", "+ " + value2);
                } else {
                    differences.put(key, "  " + value1);
                }
            }
        });
        map2.forEach((key, value2) -> {
            if (!map1.containsKey(key)) {
                differences.put(key, "+ " + value2);
            }
        });
        Map<String, String> sortedDifferences = new TreeMap<>(Comparator.naturalOrder());
        sortedDifferences.putAll(differences);

        return sortedDifferences;
    }
}
