package info.novatec.testit.livingdoc2.reflection.scanner;

import java.util.Set;

import org.reflections.Reflections;

import info.novatec.testit.livingdoc2.api.reflection.Scanner;
import info.novatec.testit.livingdoc2.reflection.FixtureStore;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Adds given fixtures to the store which are not annotated with {@link info.novatec.testit.livingdoc2.reflection.annotations.FixtureClass}
 */
public class FixtureByNameScanner implements Scanner {

    private final String packageName;

    public FixtureByNameScanner(String packageName) {
        this.packageName = packageName;
    }

    /**
     * This scanner uses the subtype of {@link Object} as marker to get ALL classes of the scanned package.
     *
     * @param reflections represents the configured {@link info.novatec.testit.livingdoc2.reflection.ClassPathScanner}.
     */
    @Override
    public void scan(Reflections reflections) {
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        classes.forEach(this::checkNameAndAddToFixureStore);
    }

    /**
     * Checks if found classes have the indicator "Fixture" in its name and add it to the {@link FixtureStore} if true.
     *
     * @param possibleFixtureClass class found by {@link Reflections}
     */
    private void checkNameAndAddToFixureStore(Class<?> possibleFixtureClass) {
        if (possibleFixtureClass.getName().endsWith("Fixture")) {
            FixtureStore.getInstance().addItem(possibleFixtureClass);
        }
    }

    @Override
    public String getPackage() {
        return packageName;
    }
}
