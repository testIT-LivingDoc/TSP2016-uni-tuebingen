package info.novatec.testit.livingdoc2.expectation.factory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Adapted from LivingDoc 1.0.
 * <p/>
 * Used to annotate methods in classes, which can be used to instantiate them by {@link Factory}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FactoryMethod {
    // No implementation needed.
}
