package info.novatec.testit.livingdoc2.runner;

import java.util.List;

import info.novatec.testit.livingdoc2.Statistics;
import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;
import info.novatec.testit.livingdoc2.api.repository.DocumentRepository;
import info.novatec.testit.livingdoc2.api.runner.SpecificationRunner;
import info.novatec.testit.livingdoc2.config.ExecutionConfig;
import info.novatec.testit.livingdoc2.config.IOConfig;
import info.novatec.testit.livingdoc2.repository.exceptions.UnsupportedDocumentException;
import info.novatec.testit.livingdoc2.specification.Specification;


/**
 * @author Sebastian Letzel
 *         Abstract class implementing base functionality for InputRunners
 */
abstract class AbstractSpecificationRunner implements SpecificationRunner {
    private final ExecutionConfig executionConfig;
    private final IOConfig ioConfig;
    private final DocumentRepository repository;
    private Statistics completeStatistic = new Statistics();

    AbstractSpecificationRunner(IOConfig ioConfig, ExecutionConfig executionConfig) {
        this.executionConfig = executionConfig;
        this.ioConfig = ioConfig;
        repository = ioConfig.getRepository();
    }

    public void run() {
        try {
            if (executionConfig.isSuite()) {
                parseAndRunSuite();
            } else {
                Specification spec = repository.parseSpecification();
                parseAndRunSpecification(spec);
            }
            System.out.print("Complete: " + completeStatistic.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseAndRunSuite() throws Exception {
        List<Specification> specList = repository.parseSpecificationsFromRoot();
        for (Specification spec : specList) {
            parseAndRunSpecification(spec);
        }
    }

    private void parseAndRunSpecification(Specification spec)
        throws UnsupportedDocumentException, ReflectiveOperationException {
        sendToCore(spec, executionConfig);
    }

    protected abstract void sendToCore(Specification spec, ExecutionConfig executionConfig)
        throws ReflectiveOperationException, UnsupportedDocumentException;

    /**
     * Entry method for all specific {@link SpecificationRunner} implementations to process the executed {@link
     * ExecutableStructure}s.
     *
     * @param executedStructures the {@link List} of executed {@link ExecutableStructure}s
     * @param specification the used {@link Specification}
     */
    public void processExecuted(List<ExecutableStructure> executedStructures, Specification specification)
        throws ClassNotFoundException, UnsupportedDocumentException, InstantiationException, IllegalAccessException {
        mergeStructuresWithSpecification(specification, executedStructures);
        printResult(specification);
    }

    /**
     * Starts the workflow for printing the report to a executed {@link Specification}.
     */
    protected void printResult(Specification specToPrint)
        throws ClassNotFoundException, UnsupportedDocumentException, IllegalAccessException, InstantiationException {
        Statistics specStatistic = repository.generateOutput(specToPrint, ioConfig);
        tallyCompleteStatistic(specToPrint, specStatistic);
    }

    private void tallyCompleteStatistic(Specification spec, Statistics specStatistic) {
        System.out.println(spec.getFileName() + ": " + specStatistic.toString());
        completeStatistic.tally(specStatistic);
    }

    /**
     * With remote execution the executedStructure {@link ExecutableStructure}s have to be merged into the {@link Specification} to
     * receive the modified {@link info.novatec.testit.livingdoc2.executables.ValueCell}s.
     *
     * @param specification {@link Specification} which was sent to the execution
     * @param executedStructures with {@link info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult}s modified {@link
     * ExecutableStructure}
     */
    protected void mergeStructuresWithSpecification(Specification specification,
        List<ExecutableStructure> executedStructures) {
        executedStructures.forEach(structure -> specification.mergeStructureIntoContentMap(structure.getKey(), structure));
    }
}
