package info.novatec.testit.livingdoc2.util;

import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Utility class utilizing guava for {@link String} operations
 */
public final class StringUtil {
    private StringUtil() {
    }

    public static String convertToUpperCamelcase(String type) {
        String converted = type.replaceAll(" ", "-");
        converted = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, converted);
        return trimWhiteSpace(converted);
    }

    public static String trimWhiteSpace(String converted) {
        String trimmed = CharMatcher.BREAKING_WHITESPACE.trimFrom(converted);
        return CharMatcher.WHITESPACE.trimAndCollapseFrom(trimmed, ' ');
    }

    public static String convertToLowerCamelcase(String type) {
        String converted = type.replaceAll(" ", "-");
        converted = CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, converted);
        return trimWhiteSpace(converted);
    }

    public static String toJavaIdentifierForm(String name) {
        String noJavaControlChars = CharMatcher.JAVA_ISO_CONTROL.removeFrom(name);
        return convertToLowerCamelcase(noJavaControlChars);
    }

    public static String toMethodForm(String name) {
        String converted = toJavaIdentifierForm(name);
        return CharMatcher.WHITESPACE.removeFrom(converted);
    }
}
