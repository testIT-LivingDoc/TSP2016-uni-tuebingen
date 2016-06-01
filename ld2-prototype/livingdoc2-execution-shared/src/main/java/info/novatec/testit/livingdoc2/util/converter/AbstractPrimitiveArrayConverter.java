package info.novatec.testit.livingdoc2.util.converter;

import info.novatec.testit.livingdoc2.util.TypeConversion;


public abstract class AbstractPrimitiveArrayConverter extends ArrayConverter {
    @Override
    public boolean canConvertTo(Class<?> type) {
        return isArray(type) && TypeConversion.supports(type.getComponentType()) && type.getComponentType().isPrimitive();
    }
}
