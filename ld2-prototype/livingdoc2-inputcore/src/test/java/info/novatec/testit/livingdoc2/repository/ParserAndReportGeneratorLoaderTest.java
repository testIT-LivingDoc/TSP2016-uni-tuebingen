package info.novatec.testit.livingdoc2.repository;

import static junit.framework.TestCase.assertTrue;

import org.junit.Test;

import info.novatec.testit.livingdoc2.LivingDocHTMLParser;
import info.novatec.testit.livingdoc2.LivingDocHTMLReportGenerator;
import info.novatec.testit.livingdoc2.api.parser.InputParser;
import info.novatec.testit.livingdoc2.api.reporter.ReportGenerator;


public class ParserAndReportGeneratorLoaderTest {

    @Test(expected = ClassNotFoundException.class)
    public void shouldThrowExceptionIfNoParserPresent() throws ClassNotFoundException {
        ParserAndReportGeneratorLoader.MARKUP.isParserPresent();
    }

    @Test
    public void shouldInstanciateParser() throws Exception {
        InputParser parser = ParserAndReportGeneratorLoader.HTML.getSpecificParser();
        assertTrue(parser instanceof LivingDocHTMLParser);
    }

    @Test
    public void shouldInstanciateReporter() throws Exception {
        ReportGenerator reporter = ParserAndReportGeneratorLoader.HTML.getSpecificReportGenerator();
        assertTrue(reporter instanceof LivingDocHTMLReportGenerator);
    }

}
