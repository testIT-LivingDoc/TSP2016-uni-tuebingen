package info.novatec.testit.livingdoc2.runner;

import java.util.List;

import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;
import info.novatec.testit.livingdoc2.api.input.ExecutableListHandler;
import info.novatec.testit.livingdoc2.config.ExecutionConfig;
import info.novatec.testit.livingdoc2.config.IOConfig;
import info.novatec.testit.livingdoc2.repository.exceptions.UnsupportedDocumentException;
import info.novatec.testit.livingdoc2.specification.Specification;


/**
 * @author Sebastian Letzel
 *         <p>
 *         Runner for local execution of {@link Specification}s
 */
public class LocalRunner extends AbstractSpecificationRunner {

    private final static String FULLY_QUALIFIED_CLASSNAME_LOCALEXECUTOR =
        "info.novatec.testit.livingdoc2.input.LocalExecutableListHandler";

    LocalRunner(IOConfig ioConfig, ExecutionConfig executionConfig) {
        super(ioConfig, executionConfig);
    }

    /**
     * Gets all {@link ExecutableStructure}s from the {@link Specification} which have to be executed and to reduce the
     * object size which is sent to the core.
     * <p>
     * In a local context reflection is used to invoke the local executor.
     *
     * @param specification the {@link Specification} to be executed
     * @param executionConfig current {@link ExecutionConfig}
     */
    @Override
    public void sendToCore(Specification specification, ExecutionConfig executionConfig)
        throws ReflectiveOperationException, UnsupportedDocumentException {
        List<ExecutableStructure> executableList = specification.getExecutablesList();

        ExecutableListHandler handler = invokeLocalExecutableListHandler();
        handler.setExecutionConfig(executionConfig);
        List<ExecutableStructure> executed = handler.handleExecutableList(executableList);
        processExecuted(executed, specification);
    }

    private ExecutableListHandler invokeLocalExecutableListHandler()
        throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Class<? extends ExecutableListHandler> localHandler =
            Class.forName(FULLY_QUALIFIED_CLASSNAME_LOCALEXECUTOR).asSubclass(ExecutableListHandler.class);

        return localHandler.newInstance();
    }
}
