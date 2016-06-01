/* Copyright (c) 2006 Pyxis Technologies inc.
 * 
 * This is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA, or see the FSF site:
 * http://www.fsf.org. */
package info.novatec.testit.livingdoc2.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import info.novatec.testit.livingdoc2.util.converter.ArrayConverter;
import info.novatec.testit.livingdoc2.util.converter.BigDecimalConverter;
import info.novatec.testit.livingdoc2.util.converter.BooleanConverter;
import info.novatec.testit.livingdoc2.util.converter.DateConverter;
import info.novatec.testit.livingdoc2.util.converter.DoubleConverter;
import info.novatec.testit.livingdoc2.util.converter.EnumConverter;
import info.novatec.testit.livingdoc2.util.converter.FloatConverter;
import info.novatec.testit.livingdoc2.util.converter.IntegerConverter;
import info.novatec.testit.livingdoc2.util.converter.LongConverter;
import info.novatec.testit.livingdoc2.util.converter.PrimitiveBoolArrayConverter;
import info.novatec.testit.livingdoc2.util.converter.PrimitiveDoubleArrayConverter;
import info.novatec.testit.livingdoc2.util.converter.PrimitiveFloatArrayConverter;
import info.novatec.testit.livingdoc2.util.converter.PrimitiveIntArrayConverter;
import info.novatec.testit.livingdoc2.util.converter.PrimitiveLongArrayConverter;
import info.novatec.testit.livingdoc2.util.converter.StringConverter;
import info.novatec.testit.livingdoc2.util.converter.TypeConverter;


public final class TypeConversion {

    private static final List<TypeConverter> CONVERTERS = new LinkedList<>();
    private static final Deque<TypeConverter> CUSTOM_CONVERTERS = new LinkedList<>();

    static {
        CONVERTERS.add(new EnumConverter());
        CONVERTERS.add(new IntegerConverter());
        CONVERTERS.add(new BigDecimalConverter());
        CONVERTERS.add(new LongConverter());
        CONVERTERS.add(new FloatConverter());
        CONVERTERS.add(new DoubleConverter());
        CONVERTERS.add(new DateConverter());
        CONVERTERS.add(new BooleanConverter());
        CONVERTERS.add(new ArrayConverter());
        CONVERTERS.add(new PrimitiveIntArrayConverter());
        CONVERTERS.add(new PrimitiveLongArrayConverter());
        CONVERTERS.add(new PrimitiveDoubleArrayConverter());
        CONVERTERS.add(new PrimitiveFloatArrayConverter());
        CONVERTERS.add(new PrimitiveBoolArrayConverter());
        CONVERTERS.add(new StringConverter());
    }

    private TypeConversion() {
    }

    public static void register(TypeConverter converter) {
        if (!CONVERTERS.contains(converter)) {
            CUSTOM_CONVERTERS.push(converter);
        }
    }

    public static Deque<TypeConverter> getCustomConverters() {
        return CUSTOM_CONVERTERS;
    }

    public static void unregisterAllCustomConverters() {
        CUSTOM_CONVERTERS.clear();
    }

    public static void unregisterLastAddedCustomConverter() {
        if (!CUSTOM_CONVERTERS.isEmpty()) {
            CUSTOM_CONVERTERS.pop();
        }
    }

    public static boolean supports(Class<?> type) {
        return converterRegisteredFor(type) || canSelfConvert("parse", type) || canSelfConvert("valueOf", type);
    }

    private static boolean converterRegisteredFor(Class<?> type) {
        return converterForType(type) != null;
    }

    private static boolean canSelfConvert(String parsingMethod, Class<?> type) {
        try {
            Method method = type.getMethod(parsingMethod, String.class);
            return type.isAssignableFrom(method.getReturnType()) && ClassUtils.isPublic(method) && ClassUtils.isStatic(
                method);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Converts <code>value</code> to the object type of <code>type</code> by
     * using the appropriate <code>TypeConverter</code>.
     *
     * @param value The string value to convert
     * @param type The type to convert to
     * @return The converted value
     */
    public static Object parse(String value, Class<?> type) {
        if (canSelfConvert("parse", type)) {
            return selfConvert("parse", value, type);
        }
        if (converterRegisteredFor(type)) {
            return converterForType(type).parse(value, type);
        }
        if (canSelfConvert("valueOf", type)) {
            return selfConvert("valueOf", value, type);
        }

        throw new UnsupportedOperationException("No converter registered for: " + type.getName());
    }

    /**
     * SelfConversion implies that if a class has the given static method named
     * that receive a String and that returns a instance of the class, then it
     * can serve for conversion purpose.
     *
     * @param parsingMethod The parsing method to use
     * @param value The string value to convert
     * @param type The type to convert to
     * @return The converted value
     */
    private static Object selfConvert(String parsingMethod, String value, Class<?> type) {
        try {
            Method method = type.getMethod(parsingMethod, String.class);
            return method.invoke(null, value);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException("Can't convert " + value + " to " + type.getName(), e.getCause());
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't convert " + value + " to " + type.getName(), e);
        }
    }

    /**
     * Converts <code>value</code> to a String by using the appropriate
     * <code>TypeConverter</code>.
     *
     * @param value The object value to convert
     * @return The string value
     */
    public static String toString(Object value) {
        if (value == null) {
            return "";
        }

        Class<?> type = value.getClass();

        if (canSelfRevert(type)) {
            return selfRevert(value);
        }
        if (converterRegisteredFor(type)) {
            return converterForType(type).toString(value);
        }

        return String.valueOf(value);
    }

    private static boolean canSelfRevert(Class<?> type) {
        try {
            Method method = type.getMethod("toString", type);
            return String.class.isAssignableFrom(method.getReturnType()) && ClassUtils.isPublic(method)
                && ClassUtils.isStatic(method);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    private static String selfRevert(Object value) {
        Class<?> type = value.getClass();
        try {
            Method method = type.getMethod("toString", type);
            return ( String ) method.invoke(null, value);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException("Can't get a string for " + value + " of to " + type.getName(), e.getCause());
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't get a string for " + value + " of to " + type.getName(), e);
        }
    }

    public static TypeConverter converterForType(Class<?> type) {
        TypeConverter converter = getConverterForType(CUSTOM_CONVERTERS, type);
        if (converter == null) {
            converter = getConverterForType(CONVERTERS, type);
        }
        return converter;
    }

    private static TypeConverter getConverterForType(Collection<TypeConverter> typeConverters, Class<?> type) {
        for (TypeConverter converter : typeConverters) {
            if (converter.canConvertTo(type)) {
                return converter;
            }
        }

        return null;
    }

    public static Object[] convert(String[] values, Class<?>[] toTypes) {
        Object[] converted = new Object[values.length];
        for (int i = 0; i < values.length; i++) {
            converted[i] = parse(values[i], toTypes[i]);
        }
        return converted;
    }
}
