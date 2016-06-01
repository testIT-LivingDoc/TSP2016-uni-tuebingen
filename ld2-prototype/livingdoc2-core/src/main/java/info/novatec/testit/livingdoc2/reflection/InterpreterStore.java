package info.novatec.testit.livingdoc2.reflection;

import info.novatec.testit.livingdoc2.api.interpreter.Interpreter;
import info.novatec.testit.livingdoc2.reflection.annotations.LivingDocInterpreter;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Saves all Interpreters found by the {@link info.novatec.testit.livingdoc2.reflection.scanner.InterpreterScanner}
 *         and returns an requested interpreter based on name or alias.
 *
 *         Uses double checked locking for singleton instantiation
 */
public final class InterpreterStore extends AbstractReflectionStore {

    public static InterpreterStore getInstance() {
        return InterpreterStoreHolder.INSTANCE;
    }

    /**
     * Method for adding Items to the {@link InterpreterStore}.
     *
     * @param possibleInterpreterClass the class to add.
     */
    @Override
    protected void checkAndAdd(Class<?> possibleInterpreterClass) throws ClassCastException {
        try {
            Class<? extends Interpreter> interpreterClass = possibleInterpreterClass.asSubclass(Interpreter.class);
            addInterpreter(interpreterClass);
        } catch (ClassCastException e) {
            System.err.println(e + " is not subclass of Interpreter");
        }
    }

    /**
     * Adds the {@link Interpreter} to the {@code itemMap}.
     * Also looks for possible aliases in the {@link LivingDocInterpreter} annotation an adds them, too.
     *
     * @param interpreterClass {@link Interpreter} to add
     */
    private void addInterpreter(Class<? extends Interpreter> interpreterClass) {
        String interpreterName = stripInterpreterExtension(interpreterClass.getSimpleName());

        checkAndPut(interpreterName, interpreterClass);

        LivingDocInterpreter annotationValues = interpreterClass.getAnnotation(LivingDocInterpreter.class);
        if (annotationValues.value().length > 0) {
            for (String alias : annotationValues.value()) {
                checkAndPut(alias, interpreterClass);
            }
        }
    }

    public Class<?> getInterpreter(String interpreterName) {
        return getItem(interpreterName);
    }

    /**
     * This method removes optional "Interpreter"-String at the end of the classname.
     * e.g.: RuleForInterpreter -> RuleFor
     *
     * @param simpleName of the {@link Interpreter} implementing class
     * @return stripped name
     */
    private String stripInterpreterExtension(String simpleName) {
        String extension = "Interpreter";
        return stripExtension(simpleName, extension);
    }

    private static class InterpreterStoreHolder {
        public static final InterpreterStore INSTANCE = new InterpreterStore();
    }
}
