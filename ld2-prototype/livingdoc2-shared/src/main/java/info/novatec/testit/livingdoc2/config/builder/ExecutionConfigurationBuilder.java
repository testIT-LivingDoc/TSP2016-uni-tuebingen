package info.novatec.testit.livingdoc2.config.builder;

import info.novatec.testit.livingdoc2.config.ExecutionConfig;


/**
 * @author Sebastian Letzel
 * Creates an immutable ExecutionConfiguration based on input parameters
 * Uses default values if no parameters are given.
 */
public class ExecutionConfigurationBuilder {
    private boolean lazy;
    private boolean suite;
    private ClassLoader classLoader;

    public ExecutionConfigurationBuilder() {
        lazy = false;
        suite = false;
        classLoader = this.getClass().getClassLoader();
    }

    public boolean isLazy() {
        return lazy;
    }

    public ExecutionConfigurationBuilder withLazy(boolean lazy) {
        this.lazy = lazy;
        return this;
    }

    public boolean isSuite() {
        return suite;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public ExecutionConfigurationBuilder withClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
        return this;
    }

    public ExecutionConfigurationBuilder asSuite(boolean suite) {
        this.suite = suite;
        return this;
    }

    public ExecutionConfig build() {

        return new ExecutionConfig(this);
    }
}
