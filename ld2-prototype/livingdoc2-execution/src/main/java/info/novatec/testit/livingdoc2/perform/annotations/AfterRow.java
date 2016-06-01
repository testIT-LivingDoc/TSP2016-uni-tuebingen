package info.novatec.testit.livingdoc2.perform.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Annotation to mark methods which will be executed after a completed row
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterRow {
    //No implementation needed
}
