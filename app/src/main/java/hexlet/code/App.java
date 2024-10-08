package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

@Command(
        name = "gendiff",
        description = "Compares two configuration files and shows a difference."
)
public class App implements Runnable {
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
    }
}
