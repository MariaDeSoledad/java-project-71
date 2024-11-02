package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;


@Command(
        name = "gendiff",
        description = "Compares two configuration files and shows a difference."
)
public class App implements Callable<Integer> {
    private static final int SUCCESS_EXIT_CODE = 0;
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

    @Override
    public Integer call() throws Exception {
        String diff = Differ.generate(filePath1, filePath2, format);
        System.out.println(diff);
        return SUCCESS_EXIT_CODE;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
