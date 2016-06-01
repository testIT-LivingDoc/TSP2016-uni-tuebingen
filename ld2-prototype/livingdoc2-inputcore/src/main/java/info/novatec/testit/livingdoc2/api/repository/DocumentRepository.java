package info.novatec.testit.livingdoc2.api.repository;

import java.io.IOException;
import java.util.List;

import info.novatec.testit.livingdoc2.Statistics;
import info.novatec.testit.livingdoc2.config.IOConfig;
import info.novatec.testit.livingdoc2.repository.exceptions.LivingDocParseException;
import info.novatec.testit.livingdoc2.repository.exceptions.UnsupportedDocumentException;
import info.novatec.testit.livingdoc2.specification.Specification;


/**
 * @author Sebastian Letzel
 *         Interface for Repository Implementations
 */
public interface DocumentRepository {

    /**
     * @param input {@link String} which contains the folder or file which is to be processed by LivingDoc
     */
    void setInput(String input);

    /**
     * Scans the given input folder for files and forwards them to further processing. Used in suite execution.
     *
     * @return {@link List} of parsed {@link Specification}s
     */
    List<Specification> parseSpecificationsFromRoot()
        throws ReflectiveOperationException, LivingDocParseException, IOException;

    /**
     * parses a single file into a {@link Specification}.
     *
     * @return created {@link Specification}
     */
    Specification parseSpecification() throws ReflectiveOperationException, LivingDocParseException, IOException;

    /**
     * Generates output based on configuration. Usually finds an appropriate {@link info.novatec.testit.livingdoc2.api.reporter.ReportGenerator}
     * and forwards the {@link Specification} to it for further processing.
     *
     * @param spec executed {@link Specification}
     * @param ioConfig {@link IOConfig} of the current {@link info.novatec.testit.livingdoc2.api.runner.SpecificationRunner}
     * @return a {@link Statistics} object which has the information about the success of the execution
     */
    Statistics generateOutput(Specification spec, IOConfig ioConfig)
        throws ClassNotFoundException, UnsupportedDocumentException, InstantiationException, IllegalAccessException;
}
