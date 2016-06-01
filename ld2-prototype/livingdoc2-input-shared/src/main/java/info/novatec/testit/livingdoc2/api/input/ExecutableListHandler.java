package info.novatec.testit.livingdoc2.api.input;

import java.util.List;

import info.novatec.testit.livingdoc2.api.executables.Executable;
import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;
import info.novatec.testit.livingdoc2.config.ExecutionConfig;


/**
 * @author Sebastian Letzel
 */
public interface ExecutableListHandler {

    /**
     * Method used to load the specific executor or client/server module.
     */
    void initializeExecutor();

    void setExecutionConfig(ExecutionConfig executionConfig);

    /**
     * Accepts the List of Executables from the Input-Plugin and
     * returns the Executed and modified List.
     *
     * @param executables List of to be executed {@link Executable}s
     */
    List<ExecutableStructure> handleExecutableList(List<ExecutableStructure> executables)
        throws ReflectiveOperationException;

}
