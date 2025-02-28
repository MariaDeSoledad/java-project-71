package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Differ {

    public static String generate(String filePath1, String filePath2) throws IOException {
        return generate(filePath1, filePath2, "stylish");
    }

    public static String generate(String filePath1, String filePath2, String formatName) throws IOException {
        String file1Format = getFileFormat(filePath1);
        String file2Format = getFileFormat(filePath2);

        if (!file1Format.equals("json") && !file1Format.equals("yml")) {
            throw new IOException("Unsupported file format for file 1: " + file1Format);
        }
        if (!file2Format.equals("json") && !file2Format.equals("yml")) {
            throw new IOException("Unsupported file format for file 2: " + file2Format);
        }


        String file1Content = getContent(filePath1);
        String file2Content = getContent(filePath2);

        Map<String, Object> parsedFile1 = Parser.parse(file1Content, file1Format);
        Map<String, Object> parsedFile2 = Parser.parse(file2Content, file2Format);

        List<Map<String, Object>> diffList = Differences.getDiff(parsedFile1, parsedFile2);

        return Formatter.constructFormatFromMap(diffList, formatName);
    }

    private static String getFileFormat(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("File path cannot be null");
        }

        int dotIndex = filePath.lastIndexOf(".");
        return (dotIndex > 0 && dotIndex < filePath.length() - 1)
                ? filePath.substring(dotIndex + 1)
                : "";
    }


    private static Path getAbsolutePath(String filePath) {
        return Paths.get(filePath).toAbsolutePath().normalize();
    }

    private static String getContent(String filePath) throws IOException {
        return Files.readString(getAbsolutePath(filePath));
    }
}
