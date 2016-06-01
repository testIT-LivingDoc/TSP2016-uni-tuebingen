package info.novatec.testit.livingdoc2.interpreter.decorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;
import info.novatec.testit.livingdoc2.api.interpreter.Interpreter;
import info.novatec.testit.livingdoc2.executables.BasicExecutableStructure;
import info.novatec.testit.livingdoc2.executables.ValueCell;
import info.novatec.testit.livingdoc2.executables.ValueRow;
import info.novatec.testit.livingdoc2.interpreter.interpreters.RuleForInterpreter;
import info.novatec.testit.livingdoc2.reflection.ClassPathScanner;
import info.novatec.testit.livingdoc2.reflection.InterpreterStore;
import info.novatec.testit.livingdoc2.reflection.scanner.InterpreterScanner;


public class ExecutablesDecoratorTest {

    @Before
    public void setup() throws Exception {
        new ClassPathScanner().scan(new InterpreterScanner());
    }

    @After
    public void teardown() throws Exception {
        InterpreterStore.getInstance().cleanup();
    }

    @Test
    public void shouldInvokeAppropriateInterpreter() throws Exception {
        List<ValueRow> rows = new ArrayList<>();
        rows.add(new ValueRow(Arrays.asList(new ValueCell("rule for"), new ValueCell("zweitezelle"))));
        ExecutableStructure testTable = new BasicExecutableStructure(rows);

        Interpreter test = ExecutablesDecorator.decorateStructure(testTable);

        assertTrue(test instanceof RuleForInterpreter);
        assertEquals("rule for", test.getFirstCell().getContent());
    }
}
