package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Testik {
    @Test
    public void testCompareJson() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream json1Stream = getClass().getResourceAsStream("/file1.json");
             InputStream json2Stream = getClass().getResourceAsStream("/file2.json")) {

            Map<String, Object> json1 = objectMapper.readValue(
                    json1Stream,
                    new TypeReference<Map<String, Object>>() {}
            );
            Map<String, Object> json2 = objectMapper.readValue(
                    json2Stream,
                    new TypeReference<Map<String, Object>>() {}
            );

            App app = new App();
            Map<String, String> differences = app.getDifferences(json1, json2);

            // Ожидаемые различия для текущих JSON-файлов
            Map<String, String> expectedDifferences = Map.of(
                    "host", "  hexlet.io",
                    "timeout", "- 50",
                    "timeout_new", "+ 20",
                    "proxy", "- 123.234.53.22",
                    "verbose", "+ true",
                    "follow", "- false"
            );

            assertEquals(expectedDifferences, differences, "Differences are not correct");
        }
    }
}