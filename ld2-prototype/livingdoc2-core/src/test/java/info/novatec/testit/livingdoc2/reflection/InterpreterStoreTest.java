package info.novatec.testit.livingdoc2.reflection;


import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Test;

import info.novatec.testit.livingdoc2.reflection.scanner.InterpreterScanner;


public class InterpreterStoreTest {

    @After
    public void teardown() throws Exception {
        InterpreterStore.getInstance().cleanup();
    }

    @Test
    public void testIsNotFilled() throws Exception {
        assertTrue(InterpreterStore.getInstance().isNotFilled());
    }

    @Test
    public void testGetInterpreter() throws Exception {
        new ClassPathScanner().scan(new InterpreterScanner());
        assertTrue(InterpreterStore.getInstance().getInterpreter("RuleFor").getSimpleName().equals("RuleForInterpreter"));
        assertTrue(InterpreterStore.getInstance().getInterpreter("DoWith").getSimpleName().equals("DoWithInterpreter"));
    }

    @Test
    public void shouldOnlyAddInterpretersWithGivenPackage() throws Exception {
        new ClassPathScanner().scan(new InterpreterScanner());
        assertNull(InterpreterStore.getInstance().getInterpreter("Mock"));
    }
}
