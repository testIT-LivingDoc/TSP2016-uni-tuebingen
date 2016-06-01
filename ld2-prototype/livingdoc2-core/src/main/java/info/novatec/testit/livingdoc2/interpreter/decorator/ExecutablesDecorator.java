package info.novatec.testit.livingdoc2.interpreter.decorator;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;
import info.novatec.testit.livingdoc2.api.interpreter.Interpreter;
import info.novatec.testit.livingdoc2.executables.ContentModificationIndicator;
import info.novatec.testit.livingdoc2.executables.ValueCell;
import info.novatec.testit.livingdoc2.executionresult.ExceptionResult;
import info.novatec.testit.livingdoc2.interpreter.interpreters.NullInterpreter;
import info.novatec.testit.livingdoc2.reflection.InterpreterStore;
import info.novatec.testit.livingdoc2.util.StringUtil;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Utility class for decorating the ExecutableStructure with the appropriate interpreter
 */
public final class ExecutablesDecorator {

    private ExecutablesDecorator() {

    }

    /**
     * @param executableList the list of executables received from the input-plugin for the parsed {@link
     * info.novatec.testit.livingdoc2.specification.Specification}
     * @return returns a list of with {@link Interpreter} decorated {@link ExecutableStructure}s
     * @throws ReflectiveOperationException condenses all thrown exceptions of following methods
     */
    public static List<Interpreter> decorate(List<ExecutableStructure> executableList) throws ReflectiveOperationException {
        List<Interpreter> decoratedStructures = new ArrayList<>();

        for (ExecutableStructure structure : executableList) {
            Interpreter interpreter = decorateStructure(structure);
            decoratedStructures.add(interpreter);
        }
        return decoratedStructures;
    }

    /**
     * Finds the appropriate {@link Interpreter} by checking the first cell of the {@link ExecutableStructure}.
     * If a suitable {@link Interpreter} is found in the {@link InterpreterStore} it will be invoked and returned.
     * If this isn't the case, the first {@link ValueCell} will be annotated with the indicator for {@link Exception} and
     * return a {@link NullInterpreter} to avoid execution.
     *
     * @param structure the {@link ExecutableStructure} to be decorated
     * @return the appropriate {@link Interpreter}
     */
    public static Interpreter decorateStructure(ExecutableStructure structure) {
        try {
            String interpreterName = structure.getFirstCell().getContent();
            interpreterName = StringUtil.convertToUpperCamelcase(interpreterName);
            Class<? extends Interpreter> interpreterClass = getInterpreter(interpreterName);

            return invokeInterpreter(interpreterClass, structure);
        } catch (ReflectiveOperationException e) {
            ValueCell interpreterCell = ( ValueCell ) structure.getFirstCell();
            interpreterCell.setIndicator(ContentModificationIndicator.EXCEPTION);
            interpreterCell.setResult(new ExceptionResult(null, e));
            return new NullInterpreter(structure);
        }
    }

    /**
     * Invokes the {@link Interpreter} with the given {@link ExecutableStructure} as argument.
     *
     * @param interpreterClass {@link Class} object of the {@link Interpreter}
     * @param structure the {@link ExecutableStructure} as argument for invoking
     * @return the invoked {@link Interpreter}
     */
    private static <T> T invokeInterpreter(Class<? extends T> interpreterClass,
        ExecutableStructure structure) throws ReflectiveOperationException {
        Class x = ExecutableStructure.class;
        Constructor<? extends T> interpreterConstructor = interpreterClass.getConstructor(x);
        return interpreterConstructor.newInstance(structure);
    }

    /**
     * Receives, if existent, the class of the {@link Interpreter} from the {@link InterpreterStore} and returns the {@link
     * Class} object of it.
     *
     * @param interpreterName the name of the {@link Interpreter} as {@link String}
     * @return {@link Class} object of the found interpreter.
     */
    private static Class<? extends Interpreter> getInterpreter(String interpreterName) throws ClassNotFoundException {
        Class<?> storedClass = InterpreterStore.getInstance().getInterpreter(interpreterName);
        return Class.forName(storedClass.getName()).asSubclass(Interpreter.class);
    }
}
