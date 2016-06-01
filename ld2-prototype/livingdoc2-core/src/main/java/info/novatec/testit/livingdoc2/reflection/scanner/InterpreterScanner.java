package info.novatec.testit.livingdoc2.reflection.scanner;

import org.reflections.Reflections;

import info.novatec.testit.livingdoc2.api.reflection.Scanner;
import info.novatec.testit.livingdoc2.reflection.InterpreterStore;
import info.novatec.testit.livingdoc2.reflection.annotations.LivingDocInterpreter;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Scans for alle Interpreters
 */
public class InterpreterScanner implements Scanner {

    private static final String INTERPRETER_PACKAGE = "info.novatec.testit.livingdoc2.interpreter";

    @Override
    public void scan(Reflections reflections) {
        fillInterpreterStore(reflections);
    }

    @Override
    public String getPackage() {
        return INTERPRETER_PACKAGE;
    }

    /**
     * Adds all by reflection found classes to the {@link InterpreterStore}.
     * Only classes annotated with {@link LivingDocInterpreter} and in the {@code INTERPRETER_PACKAGE}
     *
     * @param reflections configured {@link Reflections}
     */
    private void fillInterpreterStore(Reflections reflections) {
        InterpreterStore.getInstance().addItemList(reflections.getTypesAnnotatedWith(LivingDocInterpreter.class));
    }
}
