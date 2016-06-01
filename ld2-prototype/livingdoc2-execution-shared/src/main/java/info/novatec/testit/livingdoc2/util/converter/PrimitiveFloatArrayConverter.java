package info.novatec.testit.livingdoc2.util.converter;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;

import info.novatec.testit.livingdoc2.util.CollectionUtil;
import info.novatec.testit.livingdoc2.util.TypeConversion;


public class PrimitiveFloatArrayConverter extends AbstractPrimitiveArrayConverter {

    @Override
    public Object parse(String value, Class<?> type) {
        String text = removeSquareBrackets(value);

        List<Object> values = new ArrayList<>();
        if (Strings.isNullOrEmpty(text)) {
            return CollectionUtil.toArray(values, type.getComponentType());
        }

        String[] parts = text.split(separators);
        for (String part : parts) {
            values.add(TypeConversion.parse(part.trim(), type.getComponentType()));
        }

        return CollectionUtil.toPrimitiveFloatArray(values);
    }

    @Override
    public String toString(Object value) {
        float[] array = ( float[] ) value;

        if (array.length == 0) {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        builder.append(TypeConversion.toString(array[0]));
        if (array.length == 1) {
            return builder.toString();
        }

        for (int i = 1; i < array.length; i++) {
            builder.append(", ").append(TypeConversion.toString(array[i]));
        }
        return builder.toString();
    }
}
