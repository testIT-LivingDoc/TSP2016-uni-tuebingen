package info.novatec.testit.livingdoc2.perform.annotations;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         This class holds a list of the available method annotations.
 *         This enables us to add/remove annotations without directly modifying the loops which check for annotations on a
 *         method
 *         <p/>
 *         Could be extended to use reflection to scan for annotations at runtime.
 */
public final class AnnotationListing {

    public static final List<Class<? extends Annotation>> ANNOTATIONS;

    static {
        List<Class<? extends Annotation>> annotations = new ArrayList<>();
        annotations.add(AfterRow.class);
        annotations.add(AfterStructure.class);
        annotations.add(BeforeFirstExpectation.class);
        annotations.add(BeforeRow.class);
        annotations.add(BeforeStructure.class);

        ANNOTATIONS = Collections.unmodifiableList(annotations);
    }

    private AnnotationListing() {
    }
}
