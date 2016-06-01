package info.novatec.testit.livingdoc2.cli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;

import info.novatec.testit.livingdoc2.runner.CommandLineRunner;


public class CommandLineInterpreterTest {

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setup() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testIfHelpParameterExits() throws Exception {
        String[] args = { "-help" };
        new CommandLineInterpreter(args);
        assertTrue(outContent.toString().startsWith("usage: livingdoc2-cli"));
    }

    @Test(expected = MissingArgumentException.class)
    public void testIfWithoutInputParameterExits() throws ParseException {
        String[] args = {};
        new CommandLineInterpreter(args);
    }

    @Test
    public void shouldReturnValidDefaultCommandLineRunner() throws Exception {
        String[] args = { "-i default" };
        CommandLineRunner runner = new CommandLineInterpreter(args).getRunner();

        assertEquals(CommandLineRunner.class, runner.getClass());
    }

}
