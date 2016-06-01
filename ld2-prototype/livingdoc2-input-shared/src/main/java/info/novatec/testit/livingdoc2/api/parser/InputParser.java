package info.novatec.testit.livingdoc2.api.parser;

import java.io.IOException;
import java.nio.file.Path;

import info.novatec.testit.livingdoc2.specification.Specification;


/**
 * @author Sebastian Letzel
 * Parser have to implement this Interface to enable dynamic loading as optional dependency.
 */
public interface InputParser {
    Specification parse(Path file) throws IOException;
}
