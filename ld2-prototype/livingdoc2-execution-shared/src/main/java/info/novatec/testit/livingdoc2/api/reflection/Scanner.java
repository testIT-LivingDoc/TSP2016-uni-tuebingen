package info.novatec.testit.livingdoc2.api.reflection;

import org.reflections.Reflections;


/**
 * @author Sebastian Letzel
 *         Interface for scannerclasses using reflection
 */
public interface Scanner {

    /**
     * @param reflections represents the configured {@link info.novatec.testit.livingdoc2.reflection.ClassPathScanner}.
     */
    void scan(Reflections reflections);

    /**
     * @return the set package restriction for the {@link info.novatec.testit.livingdoc2.reflection.ClassPathScanner}
     */
    String getPackage();
}
