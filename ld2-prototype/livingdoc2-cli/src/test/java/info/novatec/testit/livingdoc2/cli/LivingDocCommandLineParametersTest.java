package info.novatec.testit.livingdoc2.cli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.cli.Options;
import org.junit.Before;
import org.junit.Test;


public class LivingDocCommandLineParametersTest {
    LivingDocCommandLineParameters parameterBuilder;
    Options options;

    @Before
    public void setup() {
        parameterBuilder = new LivingDocCommandLineParameters();
        options = parameterBuilder.getParameters();
    }

    @Test
    public void testExistenceOfShortParameters() throws Exception {
        assertTrue(options.hasOption("help"));
        assertTrue(options.hasOption("o"));
        assertTrue(options.hasOption("r"));
        assertTrue(options.hasOption("f"));
        assertTrue(options.hasOption("s"));
        assertTrue(options.hasOption("l"));
    }

    @Test
    public void testExisctenceOfLongParameters() throws Exception {
        assertTrue(options.hasOption("output"));
        assertTrue(options.hasOption("repo"));
        assertTrue(options.hasOption("format"));
        assertTrue(options.hasOption("suite"));
        assertTrue(options.hasOption("lazy"));
    }

    @Test
    public void testGetBANNER() throws Exception {
        String header = "Run the input specification and produce a report in output file or in directory";

        assertEquals(header, LivingDocCommandLineParameters.HEADER);
    }
}
