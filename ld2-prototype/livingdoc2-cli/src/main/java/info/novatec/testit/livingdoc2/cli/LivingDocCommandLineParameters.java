package info.novatec.testit.livingdoc2.cli;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;


/**
 * @author Sebastian Letzel
 *         Class for defining viable CommandLine parameters for LivingDoc CLI
 */
public class LivingDocCommandLineParameters {
    final static String HEADER = "Run the input specification and produce a report in output file or in directory";

    public Options getParameters() {
        return defineOptions();
    }

    private Options defineOptions() {
        Options options = new Options();
        List<Option> definedOptions = new ArrayList<>();
        createAndAddBooleanOptions(definedOptions);
        createAndAddArgumentOptions(definedOptions);
        definedOptions.forEach(options::addOption);
        return options;
    }

    private void createAndAddArgumentOptions(List<Option> definedOptions) {
        Option input = createInputOption();
        definedOptions.add(input);

        Option outputDirectory = createOutputDirectoryOption();
        definedOptions.add(outputDirectory);

        Option repository = createRepositoryOption();
        definedOptions.add(repository);

        Option outputFormat = createOutputFormatOption();
        definedOptions.add(outputFormat);
    }

    private void createAndAddBooleanOptions(List<Option> definedOptions) {
        definedOptions.add(createLazyOption());
        definedOptions.add(createSuiteOption());
        definedOptions.add(createHelpOption());
    }

    private Option createOutputFormatOption() {
        return Option.builder("f")
            .longOpt("format")
            .argName("OuputFormat")
            .hasArgs()
            .valueSeparator(',')
            .desc("Generate defined reports (e.g. HTML, WikiText, XML (defaults to Inputformat)")
            .build();
    }

    private Option createRepositoryOption() {
        return Option.builder("r")
            .longOpt("repo")
            .argName("CLASS;ARGS")
            .hasArgs()
            .valueSeparator(';')
            .desc("Use CLASS as the document repository and instantiate it with ARGS (defaults to FileSystemRepository)")
            .build();
    }

    private Option createOutputDirectoryOption() {
        return Option.builder("o")
            .longOpt("output")
            .hasArg()
            .argName("DIRECTORY")
            .desc("Produce reports in DIRECTORY (defaults to current directory)")
            .build();
    }

    private Option createInputOption() {
        return Option.builder("i")
            .longOpt("input")
            .hasArg()
            .argName("Inputfilepath or Directorypath")
            .desc("Location of the Specifications to run")
            .build();
    }

    private Option createHelpOption() {
        return new Option("help", "Display this help and exit");
    }

    private Option createSuiteOption() {
        return new Option("s", "suite", false, "Run a suite rather than a single test (output must refer to a directory)");
    }

    private Option createLazyOption() {
        return new Option("l", "lazy", false, "Execute document in lazy mode");
    }

}
