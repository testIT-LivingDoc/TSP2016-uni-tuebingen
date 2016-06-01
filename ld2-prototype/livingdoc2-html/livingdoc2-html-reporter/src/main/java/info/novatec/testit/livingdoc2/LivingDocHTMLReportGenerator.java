package info.novatec.testit.livingdoc2;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import info.novatec.testit.livingdoc2.api.reporter.ReportGenerator;
import info.novatec.testit.livingdoc2.reportgenerator.HTMLReportGenerator;
import info.novatec.testit.livingdoc2.specification.Specification;


/**
 * @author Sebastian Letzel
 *         Entry point for HTML-Reportgenerator
 */
public class LivingDocHTMLReportGenerator implements ReportGenerator {

    @Override
    public Statistics generate(Specification spec, Path output) {
        Statistics specStats = new Statistics();

        if (!isSpecHtml(spec)) {
            return specStats;//implement formatter here
        }

        try {
            Path writeTo = output;
            if (!Files.exists(output)) {
                if (FileSystems.getDefault().getPathMatcher("glob:**/*.html").matches(output)) {
                    Files.createFile(output);
                } else {
                    Files.createDirectories(output);
                }
            }
            if (Files.isDirectory(output)) {
                writeTo = output.resolve(spec.getFileName() + "." + spec.getInputFormat());
            }

            return HTMLReportGenerator.writeOutput(spec, writeTo);
        } catch (IOException e) {
            e.printStackTrace();
            return specStats;
        }
    }

    private boolean isSpecHtml(Specification spec) {
        return spec.getInputFormat().equals("html");
    }

}
