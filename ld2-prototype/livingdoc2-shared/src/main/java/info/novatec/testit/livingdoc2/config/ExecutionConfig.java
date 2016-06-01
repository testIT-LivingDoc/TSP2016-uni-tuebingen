package info.novatec.testit.livingdoc2.config;

import info.novatec.testit.livingdoc2.config.builder.ExecutionConfigurationBuilder;


/**
 * @author Sebastian Letzel
 *
 */
public class ExecutionConfig {
    private boolean lazy;
    private boolean suite;
    private ClassLoader classLoader;

    public ExecutionConfig(ExecutionConfigurationBuilder executionConfigurationBuilder) {
        lazy = executionConfigurationBuilder.isLazy();
        suite = executionConfigurationBuilder.isSuite();
        classLoader = executionConfigurationBuilder.getClassLoader();
    }

    public boolean isLazy() {
        return lazy;
    }

    public boolean isSuite() {
        return suite;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }
}
