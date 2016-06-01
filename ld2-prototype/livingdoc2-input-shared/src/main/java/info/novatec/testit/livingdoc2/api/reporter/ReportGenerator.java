package info.novatec.testit.livingdoc2.api.reporter;

import java.nio.file.Path;

import info.novatec.testit.livingdoc2.Statistics;
import info.novatec.testit.livingdoc2.specification.Specification;


/**
 * @author Sebastian Letzel
 * Interface for ReportGenerators
 */
public interface ReportGenerator {

    Statistics generate(Specification spec, Path outputDirectory);
}
