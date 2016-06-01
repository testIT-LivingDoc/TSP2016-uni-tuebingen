package info.novatec.testit.livingdoc2.reflection;

import org.reflections.Configuration;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import info.novatec.testit.livingdoc2.api.reflection.Scanner;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Scans the current classpath with the provided scanner
 */
public final class ClassPathScanner {

    public void scan(Scanner scanner) {
        Reflections reflection = buildReflection(scanner.getPackage());
        scanner.scan(reflection);
    }

    private Reflections buildReflection(String aPackage) {
        Configuration config =
            new ConfigurationBuilder().setScanners(new MethodAnnotationsScanner(), new TypeAnnotationsScanner(),
                new SubTypesScanner(false)).addUrls(ClasspathHelper.forPackage("info.novatec.testit.livingdoc2"))
                .filterInputsBy(new FilterBuilder().includePackage(aPackage))
                .useParallelExecutor();
        return new Reflections(config);
    }
}
