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

import java.lang.reflect.Array;
import java.util.List;


public final class CollectionUtil {
    private CollectionUtil() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(List<?> list, Class<?> type) {
        T[] array = ( T[] ) Array.newInstance(type, list.size());
        return list.toArray(array);
    }

    public static Object toPrimitiveIntArray(List<?> values) {
        int[] array = new int[values.size()];
        int cursor = 0;

        for (Object o : values) {
            array[cursor] = ( Integer ) o;
            cursor++;
        }
        return array;
    }

    public static Object toPrimitiveFloatArray(List<?> values) {
        float[] array = new float[values.size()];
        int cursor = 0;

        for (Object o : values) {
            array[cursor] = ( Float ) o;
            cursor++;
        }
        return array;
    }

    public static Object toPrimitiveLongArray(List<?> values) {
        long[] array = new long[values.size()];
        int cursor = 0;

        for (Object o : values) {
            array[cursor] = ( Long ) o;
            cursor++;
        }
        return array;
    }

    public static Object toPrimitiveDoubleArray(List<?> values) {
        double[] array = new double[values.size()];
        int cursor = 0;

        for (Object o : values) {
            array[cursor] = ( Double ) o;
            cursor++;
        }
        return array;
    }

    public static Object toPrimitiveBoolArray(List<?> values) {
        boolean[] array = new boolean[values.size()];
        int cursor = 0;

        for (Object o : values) {
            array[cursor] = ( Boolean ) o;
            cursor++;
        }
        return array;
    }
}
