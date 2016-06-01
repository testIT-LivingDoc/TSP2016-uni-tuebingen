package info.novatec.testit.livingdoc2.util;

import com.google.common.base.Strings;


public final class NumberUtil {
    private NumberUtil() {

    }

    public static String substituteDecimalSeparatorToPeriod(String value) {
        if (Strings.isNullOrEmpty(value)) {
            return value;
        }

        int commaPosition = value.lastIndexOf(',');

        if (commaPosition == -1) {
            return value;
        }

        return value.substring(0, commaPosition) + "." + value.substring(commaPosition + 1);
    }
}
