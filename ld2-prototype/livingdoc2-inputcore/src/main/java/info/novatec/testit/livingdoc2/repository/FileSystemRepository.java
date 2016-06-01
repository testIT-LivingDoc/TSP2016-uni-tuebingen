package info.novatec.testit.livingdoc2.repository;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import info.novatec.testit.livingdoc2.Statistics;
import info.novatec.testit.livingdoc2.api.parser.InputParser;
import info.novatec.testit.livingdoc2.api.reporter.ReportGenerator;
import info.novatec.testit.livingdoc2.api.repository.DocumentRepository;
import info.novatec.testit.livingdoc2.config.IOConfig;
import info.novatec.testit.livingdoc2.repository.exceptions.DocumentNotFoundException;
import info.novatec.testit.livingdoc2.repository.exceptions.LivingDocParseException;
import info.novatec.testit.livingdoc2.repository.exceptions.UnsupportedDocumentException;
import info.novatec.testit.livingdoc2.specification.Specification;


/**
 * @author Sebastian Letzel
 *         <p>
 *         Repository for managing local input files
 */
public class FileSystemRepository implements DocumentRepository {

    private final static int FIRST_GENERATOR_INDEX = 0;
    private Path fileString;
    private Path root;

    @Override
    public void setInput(String input) {
        fileString = Paths.get(input);

        if (Files.isDirectory(fileString)) {
            root = fileString;
        } else {
            root = fileString.getParent();
        }
    }

    /**
     * Method used to execute a suite of specifications.
     * First scans the given inputpath for specification files, then parses them.
     *
     * @return parsed list of {@link Specification}s
     */
    @Override
    public List<Specification> parseSpecificationsFromRoot()
        throws LivingDocParseException, ReflectiveOperationException, IOException {
        List<Path> documentsInPath = new ArrayList<>();

        addFilesFromRoot(documentsInPath);

        List<Specification> suiteSpecList = new ArrayList<>();

        for (Path path : documentsInPath) {
            suiteSpecList.add(parseFile(path));
        }

        return suiteSpecList;
    }

    /**
     * Used to parse a single input file.
     *
     * @return the into {@link Specification} parsed input file
     */
    @Override
    public Specification parseSpecification() throws LivingDocParseException, ReflectiveOperationException, IOException {
        return parseFile(fileString);
    }

    private void addFilesFromRoot(List<Path> documentsInPath) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(root)) {

            stream.forEach(documentsInPath::add);

        } catch (IOException | DirectoryIteratorException x) {
            System.err.println(x);
        }
    }

    private Specification parseFile(Path file) throws LivingDocParseException, ReflectiveOperationException, IOException {
        if (!Files.exists(file)) {
            throw new DocumentNotFoundException("Path object of file is null");
        }

        InputParser parser = ParserAndReportGeneratorLoader.getParser(file);

        return parser.parse(file);
    }

    /**
     * This implementation gets the appropriate {@link ReportGenerator}s and processes them.
     * <p>
     * After getting the {@link ReportGenerator}s the first {@link ReportGenerator} is used to receive the stats of the
     * {@link Specification}.
     * Further generators will be executed separately to avoid duplicate result statistics
     */
    @Override
    public Statistics generateOutput(Specification spec, IOConfig ioConfig)
        throws ClassNotFoundException, UnsupportedDocumentException, InstantiationException, IllegalAccessException {
        List<String> outputFormats = ioConfig.getOutputFormats();
        Path outputDirectory = ioConfig.getOutputDirectory();

        List<ReportGenerator> generators = getReportGenerators(spec, outputFormats);

        Statistics resultFromFirstGenerator = processFirstGenerator(spec, outputDirectory, generators);

        processAdditionalGenerators(spec, outputDirectory, generators);

        return resultFromFirstGenerator;
    }

    /**
     * Finds the appropriate {@link ReportGenerator}s and returns them as a {@link List}.
     * If no {@code outputFormats} are specified, it uses the input format of the {@link Specification} as default.
     */
    private List<ReportGenerator> getReportGenerators(Specification spec, List<String> outputFormats)
        throws ClassNotFoundException, UnsupportedDocumentException, IllegalAccessException, InstantiationException {
        ReportGenerator generator;
        List<ReportGenerator> generators = new ArrayList<>();

        if (!outputFormats.isEmpty()) {
            for (String format : outputFormats) {
                generator = ParserAndReportGeneratorLoader.getReportGenerator(format);
                generators.add(generator);
            }
        } else {
            generator = ParserAndReportGeneratorLoader.getReportGenerator(spec.getInputFormat());
            generators.add(generator);
        }
        return generators;
    }

    /**
     * @return {@link Statistics} of the {@link info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult}s from
     * this {@link Specification}
     */
    private Statistics processFirstGenerator(Specification specToPrint, Path outputDirectory,
        List<ReportGenerator> generators) {
        ReportGenerator firstGenerator = generators.get(FIRST_GENERATOR_INDEX);
        return firstGenerator.generate(specToPrint, outputDirectory);
    }

    /**
     * Uses parallelStream as execution order of those generators is not important.
     */
    private void processAdditionalGenerators(Specification spec, Path outputDirectory, List<ReportGenerator> generators) {
        if (generators.size() > 1) {
            generators.stream().skip(1).parallel().forEach(generator -> generator.generate(spec, outputDirectory));
        }
    }
}
