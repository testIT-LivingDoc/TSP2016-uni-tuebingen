package info.novatec.testit.livingdoc2;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;


/**
 * @author Sebastian Letzel
 *         <p>
 *         Successful completion of this test proves the concept of the architecture of this prototype works
 */
public class PrototypeSuccessTest {

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setup() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void canRunSampleSpecsSuccessful() {
        String[] args = { "-i=default", "-s" };

        Main.main(args);

        assertTrue(outContent.toString().contains("Complete: 27 tests: 25 right, 2 wrong, 0 ignored, 0 exception(s)"));
    }
}


