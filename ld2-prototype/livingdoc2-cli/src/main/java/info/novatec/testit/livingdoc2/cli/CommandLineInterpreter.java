package info.novatec.testit.livingdoc2.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import info.novatec.testit.livingdoc2.config.ExecutionConfig;
import info.novatec.testit.livingdoc2.config.IOConfig;
import info.novatec.testit.livingdoc2.config.builder.ExecutionConfigurationBuilder;
import info.novatec.testit.livingdoc2.config.builder.IOConfigBuilder;
import info.novatec.testit.livingdoc2.runner.CommandLineRunner;


/**
 * @author Sebastian Letzel
 *         Reads the commandline and creates {@link IOConfig} and {@link ExecutionConfig} based on the input.
 *         Creates {@link CommandLineRunner} with the config classes.
 */
public class CommandLineInterpreter {
    private CommandLine cli;

    public CommandLineInterpreter(String[] args) throws ParseException {
        Options cliParameters = new LivingDocCommandLineParameters().getParameters();
        CommandLineParser parser = new DefaultParser();

        cli = parser.parse(cliParameters, args);

        checkExitArguments(cliParameters);
    }

    /**
     * Gets {@link IOConfig} and {@link ExecutionConfig} to build the {@link CommandLineRunner}.
     *
     * @return returns the configured instance of {@link CommandLineRunner}
     */
    public CommandLineRunner getRunner() {
        IOConfig ioconfig = createIOConfig();
        ExecutionConfig executionConfig = createExecutionConfig();

        return new CommandLineRunner(ioconfig, executionConfig);
    }

    private IOConfig createIOConfig() {
        IOConfigBuilder ioConfigBuilder = new IOConfigBuilder();

        if (cli.hasOption("i")) {
            ioConfigBuilder.withInput(cli.getOptionValue("i"));
        }
        if (cli.hasOption("o")) {
            ioConfigBuilder.withOutputDirectory(cli.getOptionValue("o"));
        }
        if (cli.hasOption("r")) {
            ioConfigBuilder.withRepository(cli.getOptionValues("r"));
        }
        if (cli.hasOption("f")) {
            ioConfigBuilder.withOutputFormats(cli.getOptionValues("f"));
        }

        return ioConfigBuilder.build();
    }

    private ExecutionConfig createExecutionConfig() {
        ExecutionConfigurationBuilder exec = new ExecutionConfigurationBuilder();

        if (cli.hasOption("s")) {
            exec.asSuite(true);
        }
        if (cli.hasOption("l")) {
            exec.withLazy(true);
        }

        return exec.build();
    }

    /**
     * Checks the input for Parameters which causes the program to stop.
     * Examples are: Help, Version and no given Input
     *
     * @param cliParameters parameters received from {@link LivingDocCommandLineParameters}
     */
    private void checkExitArguments(Options cliParameters) throws MissingArgumentException {

        if (cli.hasOption("help")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("livingdoc2-cli", LivingDocCommandLineParameters.HEADER, cliParameters, "", true);
            return;
        }

        if (!cli.hasOption("i")) {
            throw new MissingArgumentException("Input");
        }
    }

}
