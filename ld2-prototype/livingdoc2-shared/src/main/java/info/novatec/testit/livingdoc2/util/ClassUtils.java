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

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;

import com.google.common.base.Joiner;


public final class ClassUtils {
    private ClassUtils() {
    }

    public static Class<?> loadClass(String className) throws ClassNotFoundException {
        // Instead of the Thread.currentThread().getContextClassLoader() we are
        // using the current class classLoader, to be compatible with a OSGI
        // environment (mainly for the confluence plugin). For more information
        // see: https://wiki.eclipse.org/Context_Class_Loader_Enhancements or
        // http://njbartlett.name/2010/08/30/osgi-readiness-loading-classes.html
        return loadClass(ClassUtils.class.getClassLoader(), className);
    }

    public static Class<?> loadClass(ClassLoader classLoader, String className) throws ClassNotFoundException {
        return classLoader.loadClass(className);
    }

    /**
     * @throws UndeclaredThrowableException if any exception occurs.
     */
    public static <C> C createInstanceFromClassNameWithArguments(ClassLoader classLoader, String[] classWithArguments,
        Class<C> expectedType) throws UndeclaredThrowableException {
        try {
            Class<?> klass = ClassUtils.loadClass(classLoader, classWithArguments[0]);

            if (!expectedType.isAssignableFrom(klass)) {
                throw new IllegalArgumentException(
                    "Class " + expectedType.getName() + " is not assignable from " + klass.getName());
            }

            if (classWithArguments.length == 0) {
                return expectedType.cast(klass.newInstance());
            }

            String[] args = new String[classWithArguments.length - 1];
            System.arraycopy(classWithArguments, 1, args, 1, args.length);

            Constructor<?> constructor = klass.getConstructor(args.getClass());
            return expectedType.cast(constructor.newInstance(new Object[] { args }));
        } catch (Exception e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    public static boolean isStatic(Member member) {
        return Modifier.isStatic(member.getModifiers());
    }

    public static boolean isPublic(Member member) {
        return Modifier.isPublic(member.getModifiers());
    }

    private static String toString(Object... args) {
        if (args.length == 0) {
            return "";
        }
        Joiner joiner = Joiner.on(",").skipNulls();
        String argsAsString = joiner.join(args);
        return "[" + argsAsString + "]";
    }


}
