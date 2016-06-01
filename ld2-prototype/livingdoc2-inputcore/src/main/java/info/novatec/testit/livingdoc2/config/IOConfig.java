package info.novatec.testit.livingdoc2.config;

import java.nio.file.Path;
import java.util.List;

import info.novatec.testit.livingdoc2.api.repository.DocumentRepository;
import info.novatec.testit.livingdoc2.config.builder.IOConfigBuilder;


/**
 * @author Sebastian Letzel
 * Config object to separate IOConfig from execution process
 */
public class IOConfig {
    private Path outputDirectory;
    private DocumentRepository repository;
    private List<String> outputFormats;

    public IOConfig(IOConfigBuilder builder) {
        this.outputDirectory = builder.getOutputDirectory();
        this.repository = builder.getRepository();
        this.outputFormats = builder.getOutputFormats();
    }

    public Path getOutputDirectory() {
        return outputDirectory;
    }

    public DocumentRepository getRepository() {
        return repository;
    }

    public List<String> getOutputFormats() {
        return outputFormats;
    }
}
