package info.novatec.testit.livingdoc2.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;
import info.novatec.testit.livingdoc2.executables.ValueCell;
import info.novatec.testit.livingdoc2.specification.Specification;


public class HTMLStructureConstructorTest {
    Document html;
    Specification spec;

    @Before
    public void setup() throws Exception {
        html = Jsoup.parse(testHTMLString());
        spec = HTMLStructureConstructor.create(html, "tester");
    }

    @Test
    public void canCreateSpecification() throws Exception {
        List<ExecutableStructure> executableList = spec.getExecutablesList();

        assertEquals("html", spec.getInputFormat());
        assertTrue(spec.getContentMap().get("jSoupDocument") instanceof Document);
        assertEquals(2, executableList.size());
        assertTrue(executableList.get(0).getRow(0) != null);
        assertTrue(executableList.get(0).getRow(0).get(0) instanceof ValueCell);
        assertEquals("import", executableList.get(0).getFirstCell().getContent());
        assertEquals("rule for", executableList.get(1).getFirstCell().getContent());
    }

    private String testHTMLString() {
        return "<html> <head></head><body> <h1>Sample calculator test</h1>" + "<div>"
            + "<p>The first thing we need to do is import the package containing our calculator code.</p>"
            + " <table> <tbody> " + "<tr>" + "<td>import</td> " + "</tr> " + "<tr> "
            + "<td>info.novatec.testit.livingdoc.samples.application.calculator</td> " + "</tr> " + "</tbody> "
            + "</table> </div> " + "<div> <p>" + "<code> RuleForInterpreter</code>)</p>"
            + "<p>Notice how errors are reported when business rules validation fails.</p> " + "<table> <tbody> " + "<tr> "
            + "<td colspan=" + 5 + ">rule for</td> " + "<td colspan=" + 5 + ">calculator</td> " + "</tr> " + "<tr> "
            + "<td>x</td> " + "<td>y</td> " + "<td>sum?</td> " + "<td>product?</td> " + "<td>quotient?</td> " + "</tr> "
            + "<tr> " + "<td>6</td> " + "<td>2</td> " + "<td>8</td> " + "<td>12</td> " + "<td>3</td> " + "</tr>" + "</tbody>"
            + "</table>" + "</div></body></html>";
    }
}
