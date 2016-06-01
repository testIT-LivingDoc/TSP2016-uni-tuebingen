package info.novatec.testit.livingdoc2.reflection.scanner;

import org.reflections.Reflections;

import info.novatec.testit.livingdoc2.api.reflection.Scanner;
import info.novatec.testit.livingdoc2.reflection.FixtureStore;
import info.novatec.testit.livingdoc2.reflection.annotations.FixtureClass;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Scans for all Fixtures annotated with {@link FixtureClass}
 */
public class FixtureScanner implements Scanner {

    private static final String FIXTURE_PACKAGE = "";

    @Override
    public void scan(Reflections reflections) {
        fillFixtureStore(reflections);
    }

    @Override
    public String getPackage() {
        return FIXTURE_PACKAGE;
    }

    /**
     * Scans for all classes with the annotation {@link FixtureClass} and adds the to the {@link FixtureStore}.
     * If scanned, sets the indicator of the {@link FixtureStore} to true.
     * <p/>
     * Indicator is necessary, for checking the scans. If previous actions only used imports, then this will be lazily
     * executed
     */
    private void fillFixtureStore(Reflections reflections) {
        FixtureStore.getInstance().addItemList(reflections.getTypesAnnotatedWith(FixtureClass.class));
        FixtureStore.getInstance().setAnnotationScanned(true);
    }
}
