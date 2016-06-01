package info.novatec.testit.livingdoc2.reflection.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Annotation to mark classes used in flow interpreters for row keywords
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LivingDocRowType {

    /**
     * Can be used to define aliases for the keyword of the annotated rowclass.
     *
     * @return annotated alias values
     */
    String[] value() default {};
}
