package info.novatec.testit.livingdoc2.perform;

import static org.reflections.ReflectionUtils.withParametersCount;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.reflections.ReflectionUtils;

import info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult;
import info.novatec.testit.livingdoc2.executionresult.ExceptionResult;
import info.novatec.testit.livingdoc2.executionresult.VoidResult;
import info.novatec.testit.livingdoc2.fixture.Fixture;
import info.novatec.testit.livingdoc2.perform.annotations.AnnotationListing;
import info.novatec.testit.livingdoc2.reflection.ClassPathScanner;
import info.novatec.testit.livingdoc2.reflection.FixtureStore;
import info.novatec.testit.livingdoc2.reflection.annotations.Alias;
import info.novatec.testit.livingdoc2.reflection.scanner.FixtureScanner;
import info.novatec.testit.livingdoc2.util.StringUtil;
import info.novatec.testit.livingdoc2.util.TypeConversion;


/**
 * @author Sebastian Letzel
 *         <p>
 *         Instantiates the Fixture for later use
 */
public class FixtureInstantiatePerformer extends AbstractPerformer {

    /**
     * Starts instantiation of the Fixture.
     *
     * @return an {@link ExceptionResult} if the invocation fails. Otherwise a {@link VoidResult}
     */
    @Override
    public ExecutionResult perform() {
        try {
            instantiateFixture(action.getTarget(), getActionArguments());
        } catch (Exception e) {
            return new ExceptionResult(getCallID(), e);
        }
        return new VoidResult(getCallID());
    }

    /**
     * Gets the name of the fixture and checks if the store has it. If the store does not it checks if the scan was
     * performed.
     * <p>
     * If an class was found it invokes the Fixture and scans its methods.
     * The {@link Object} of the invoked fixture and the {@link Map} of the methods are stored in the {@link Fixture} class
     * which itself will be stored in the {@link FixtureStore} as the current fixture.
     *
     * @param target name of the fixture
     * @param arguments arguments for the fixture
     */
    private void instantiateFixture(String target, List<String> arguments) throws ReflectiveOperationException {
        String fixtureName = StringUtil.convertToUpperCamelcase(target);
        if (!FixtureStore.getInstance().isItemAlreadyPresent(fixtureName)) {
            checkIfStoreHasScanned();
        }
        Class<?> fixtureClass = FixtureStore.getInstance().getFixtureClass(fixtureName);
        if (fixtureClass == null) {
            throw new ClassNotFoundException(fixtureName + " not found");
        }
        Object invokedClass = invokeFixture(fixtureClass, arguments);

        Map<String, Method> methodMap = arrangeMethods(fixtureClass);

        Fixture fixture = new Fixture(invokedClass, methodMap);
        FixtureStore.getInstance().setCurrentFixture(fixture);
    }

    /**
     * Unchecked as the ReflectionUtils cause the warning.
     * Scans the fixture for the appropriate constructor and invokes it with the give arguments.
     *
     * @return the invoked fixture class
     */
    @SuppressWarnings("unchecked")
    private Object invokeFixture(Class<?> fixtureClass, List<String> arguments) throws ReflectiveOperationException {

        String[] args = arguments.toArray(new String[arguments.size()]);

        List<Constructor> constructors =
            new ArrayList<>(ReflectionUtils.getConstructors(fixtureClass, withParametersCount(args.length)));

        if (constructors.size() == 1) {
            Constructor constructor = constructors.get(0);
            Class<?>[] types = constructor.getParameterTypes();
            Object[] parameters = TypeConversion.convert(args, types);
            return constructor.newInstance(parameters);
        }
        throw new NoSuchMethodException("No suitable constructor found");
    }

    /**
     * Creates a map of the methods including its aliases and annotations as well as method aliases.
     *
     * @param fixture the fixture with the methods
     * @return filled {@link Map} of all methods and their annotations/aliases
     */
    private Map<String, Method> arrangeMethods(Class<?> fixture) {
        Map<String, Method> temp = new HashMap<>();
        Method[] allMethods = fixture.getDeclaredMethods();

        for (Method method : allMethods) {
            temp.putAll(checkAnnotations(method));
            temp.putAll(checkAliases(method));
            temp.put(method.getName(), method);
        }

        return temp;
    }

    /**
     * Checks given method for the {@link Alias} annotation and adds them to the map.
     *
     * @param method {@link Method} to scan
     * @return {@link Map} of aliases for this method.
     */
    private Map<String, Method> checkAliases(Method method) {
        Map<String, Method> aliasTemp = new HashMap<>();

        if (method.isAnnotationPresent(Alias.class)) {
            Alias annotations = method.getAnnotation(Alias.class);
            for (String alias : annotations.value()) {
                aliasTemp.put(alias, method);
            }
        }
        return aliasTemp;
    }

    /**
     * Checks the given method for the annotations present in {@link AnnotationListing} and adds them to the map if present.
     *
     * @param method the {@link Method} to scan
     * @return the {@link Map} of annotations for this method.
     */
    private Map<String, Method> checkAnnotations(Method method) {
        Map<String, Method> annotationTemp = new HashMap<>();

        AnnotationListing.ANNOTATIONS.stream()
            .filter(method::isAnnotationPresent)
            .forEach(annotation -> annotationTemp.put(annotation.getSimpleName().toLowerCase(), method));
        return annotationTemp;
    }

    private void checkIfStoreHasScanned() {
        if (!FixtureStore.getInstance().isAnnotationScanned()) {
            new ClassPathScanner().scan(new FixtureScanner());
        }
    }
}

