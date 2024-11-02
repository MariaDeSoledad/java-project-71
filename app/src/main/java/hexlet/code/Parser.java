package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;


import java.io.IOException;
import java.util.Map;

public class Parser {
    public static Map<String, Object> parse(String fileContent, String fileFormat) throws IOException {
        if (fileFormat.equals("json")) {
            return getJson(fileContent);
        } else if (fileFormat.equals("yaml")) {
            return getYaml(fileContent);
        } else {
            throw new IOException("Unsupported file format: " + fileFormat);
        }
    }
    private static Map<String, Object> getJson(String fileContent) throws IOException {
        ObjectMapper mapperJson = new JsonMapper();
        return mapperJson.readValue(fileContent, new TypeReference<Map<String, Object>>() {});
    }
    private static Map<String, Object> getYaml(String fileContent) throws IOException {
        ObjectMapper mapperYaml = new YAMLMapper();
        return mapperYaml.readValue(fileContent, new TypeReference<>() { });
    }
}
