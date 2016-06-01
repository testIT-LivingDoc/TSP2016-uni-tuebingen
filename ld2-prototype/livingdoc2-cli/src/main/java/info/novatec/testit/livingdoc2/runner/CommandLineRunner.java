package info.novatec.testit.livingdoc2.runner;

import info.novatec.testit.livingdoc2.config.ExecutionConfig;
import info.novatec.testit.livingdoc2.config.IOConfig;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Runner for CLI
 */
public class CommandLineRunner extends LocalRunner {

    public CommandLineRunner(IOConfig ioConfig, ExecutionConfig executionConfig) {
        super(ioConfig, executionConfig);
    }

}
