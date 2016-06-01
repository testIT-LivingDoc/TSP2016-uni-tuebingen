package info.novatec.testit.livingdoc2.reflection.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Annotation to mark Interpreters which will be loaded by reflection
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FixtureClass {

    /**
     * Can be used to define aliases for the Fixture class.
     *
     * @return annotated alias values
     */
    String[] value() default {};
}
