package info.novatec.testit.livingdoc2.reflection.scanner;

import org.reflections.Reflections;

import info.novatec.testit.livingdoc2.api.reflection.Scanner;
import info.novatec.testit.livingdoc2.reflection.RowTypeStore;
import info.novatec.testit.livingdoc2.reflection.annotations.LivingDocRowType;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Scans for all classes annotated with {@link LivingDocRowType}
 */
public class RowTypeScanner implements Scanner {

    private static final String ROWTYPE_PACKAGE = "info.novatec.testit.livingdoc2.interpreter";

    @Override
    public void scan(Reflections reflections) {
        fillRowTypeStore(reflections);
    }

    @Override
    public String getPackage() {
        return ROWTYPE_PACKAGE;
    }

    /**
     * Adds all by reflection found classes to the {@link RowTypeStore}.
     * Only classes annotated with {@link LivingDocRowType} and in the {@code ROWTYPE_PACKAGE}
     *
     * @param reflections configured {@link Reflections}
     */
    private void fillRowTypeStore(Reflections reflections) {
        RowTypeStore.getInstance().addItemList(reflections.getTypesAnnotatedWith(LivingDocRowType.class));
    }

}
