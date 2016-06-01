package info.novatec.testit.livingdoc2.config.builder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.novatec.testit.livingdoc2.api.repository.DocumentRepository;
import info.novatec.testit.livingdoc2.config.IOConfig;
import info.novatec.testit.livingdoc2.repository.FileSystemRepository;
import info.novatec.testit.livingdoc2.util.ClassUtils;


/**
 * @author Sebastian Letzel
 *         This builder is used to build an immutable IOConfig object and supply it with default values.
 */
public class IOConfigBuilder {

    private Path outputDirectory;
    private String input;
    private DocumentRepository repository;
    private List<String> outputFormats;

    /**
     * Constructor initializes default values.
     */
    public IOConfigBuilder() {
        Path workingDirectory = Paths.get(".");
        outputDirectory = workingDirectory.resolve("LivingDocResults");
        repository = new FileSystemRepository();
        outputFormats = new ArrayList<>();
        input = workingDirectory.resolve("specs").toAbsolutePath().toString();
    }

    public IOConfigBuilder withOutputDirectory(String outputDirectory) {
        this.outputDirectory = Paths.get(outputDirectory);
        return this;
    }

    public IOConfigBuilder withInput(String inputString) {
        this.input = inputString.equals("default") ? input : inputString;
        return this;
    }

    public IOConfigBuilder withRepository(String[] repositoryWithArgs) {
        instantiateDocumentRepository(repositoryWithArgs);
        return this;
    }

    public IOConfigBuilder withOutputFormats(String[] outputFormats) {
        this.outputFormats.addAll(Arrays.asList(outputFormats));
        return this;
    }

    public IOConfig build() {
        repository.setInput(input);
        return new IOConfig(this);
    }

    /**
     * Instantiate the document repository from a string, to be able to use
     * third party repositories.
     *
     * @param repositoryWithArgs String name of repository
     */
    private void instantiateDocumentRepository(String[] repositoryWithArgs) {
        ClassLoader classLoader = this.getClass().getClassLoader();

        repository =
            ClassUtils.createInstanceFromClassNameWithArguments(classLoader, repositoryWithArgs, DocumentRepository.class);
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
