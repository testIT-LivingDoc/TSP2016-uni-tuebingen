package info.novatec.testit.livingdoc2.repository;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import info.novatec.testit.livingdoc2.api.parser.InputParser;
import info.novatec.testit.livingdoc2.api.reporter.ReportGenerator;
import info.novatec.testit.livingdoc2.repository.exceptions.DocumentNotFoundException;
import info.novatec.testit.livingdoc2.repository.exceptions.LivingDocParseException;
import info.novatec.testit.livingdoc2.repository.exceptions.UnsupportedDocumentException;


/**
 * @author Sebastian Letzel
 *         <p>
 *         This ENUM handles dynamic loading of {@link InputParser} and reporters based on the file extension.
 *         It also checks if the requested module is present.
 *         This allows the user to implement only the {@link InputParser} and {@link ReportGenerator}
 *         that he wants to use in his project.
 */
public enum ParserAndReportGeneratorLoader {
    HTML("html"),
    MARKUP("markup");

    ParserAndReportGeneratorLoader(String s) {
        Holder.typeMap.put(s, this);
    }

    /**
     * Gets the {@link InputParser} by file extension and instantiates it via reflection.
     */
    public static InputParser getParser(Path file) throws LivingDocParseException, ReflectiveOperationException {

        ParserAndReportGeneratorLoader type = getExtensionAndCheckFileType(file);

        return type.getSpecificParser();
    }

    /**
     * Gets the {@link ReportGenerator} by file format (aka extension) and instantiates it via reflection.
     */
    public static ReportGenerator getReportGenerator(String format)
        throws UnsupportedDocumentException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ParserAndReportGeneratorLoader type = checkFileType(format);

        return type.getSpecificReportGenerator();
    }

    private static ParserAndReportGeneratorLoader getExtensionAndCheckFileType(Path file)
        throws UnsupportedDocumentException, DocumentNotFoundException {
        String fileExtension = extractFileExtension(file);
        return checkFileType(fileExtension);
    }

    /**
     * The {@link Path} file can't be null as it is checked beforehand.
     */
    @SuppressFBWarnings("NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE")
    private static String extractFileExtension(Path file) throws DocumentNotFoundException {
        String fileName = file.getFileName().toString();
        int extensionSeparator = fileName.lastIndexOf(".") + 1;
        return fileName.substring(extensionSeparator);
    }

    /**
     * Checks the {@link Enum} {@link ParserAndReportGeneratorLoader} by file extension to determine if the file type is supported.
     */
    public static ParserAndReportGeneratorLoader checkFileType(String fileExtension) throws UnsupportedDocumentException {
        ParserAndReportGeneratorLoader type = Holder.typeMap.get(fileExtension);
        if (type == null) {
            throw new UnsupportedDocumentException(String.format("Unsupported type %s.", fileExtension));
        }
        return type;
    }

    InputParser getSpecificParser() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<? extends InputParser> parser = isParserPresent();
        return parser.newInstance();
    }

    ReportGenerator getSpecificReportGenerator()
        throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<? extends ReportGenerator> reportGenerator = isReporterPresent();
        return reportGenerator.newInstance();
    }

    Class<? extends InputParser> isParserPresent() throws ClassNotFoundException {
        return isPresent("Parser").asSubclass(InputParser.class);
    }

    Class<? extends ReportGenerator> isReporterPresent() throws ClassNotFoundException {
        return isPresent("ReportGenerator").asSubclass(ReportGenerator.class);
    }

    /**
     * Checks for the presence of a parser or composer with the package convention: info.novatec.testit.livingdoc2.
     * Name convention for files: LivingDoc+Filetype Uppercase+Moduletype camelcase
     * e.G.: info.novatec.testit.livingdoc2.LivingDocHTMLParser
     *
     * @param module defines the type of the module to which the presence will be checked
     */
    protected Class<?> isPresent(String module) throws ClassNotFoundException {
        String ext = this.name();
        return Class.forName("info.novatec.testit.livingdoc2.LivingDoc" + ext + module);
    }

    private static class Holder {
        static Map<String, ParserAndReportGeneratorLoader> typeMap = new HashMap<>();
    }
}
