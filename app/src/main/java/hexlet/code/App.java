package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    @Option(names = {"-f", "--format"}, description = "output format [default: ${DEFAULT-VALUE}]", defaultValue = "stylish", paramLabel = "format")
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
            var map1 = mapper.readValue(Files.readAllBytes(Paths.get(filePath1)), Map.class);
            var map2 = mapper.readValue(Files.readAllBytes(Paths.get(filePath2)), Map.class);
            Map<String, String> differences = getDifferences(map1, map2);
            System.out.println("Differences:");
            differences.forEach((key, value) -> System.out.println(key + ": " + value));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private Map<String, String> getDifferences(Map<String, Object> map1, Map<String, Object> map2) {
        Map<String, String> differences = new TreeMap<>();
        map1.forEach((key, value) -> {
            if (!map2.containsKey(key)) {
                differences.put(key, "removed");
            } else if (!value.equals(map2.get(key))) {
                differences.put(key, "changed");
            }
        });

        map2.forEach((key, value) -> {
            if (!map1.containsKey(key)) {
                differences.put(key, "added");
            }
        });

        return differences;
    }
}
