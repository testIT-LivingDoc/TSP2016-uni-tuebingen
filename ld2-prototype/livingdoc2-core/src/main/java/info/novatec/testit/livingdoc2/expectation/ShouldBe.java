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
package info.novatec.testit.livingdoc2.expectation;

import java.util.LinkedList;
import java.util.List;

import info.novatec.testit.livingdoc2.api.expectation.Expectation;
import info.novatec.testit.livingdoc2.expectation.expectations.DuckExpectation;
import info.novatec.testit.livingdoc2.expectation.expectations.Either;
import info.novatec.testit.livingdoc2.expectation.expectations.EqualExpectation;
import info.novatec.testit.livingdoc2.expectation.expectations.ErrorExpectation;
import info.novatec.testit.livingdoc2.expectation.expectations.IsInstanceExpectation;
import info.novatec.testit.livingdoc2.expectation.expectations.NotExpectation;
import info.novatec.testit.livingdoc2.expectation.expectations.NullExpectation;
import info.novatec.testit.livingdoc2.expectation.expectations.VoidExpectation;
import info.novatec.testit.livingdoc2.expectation.factory.Factory;


/**
 * Adapted from LivingDoc 1.0.
 *
 * Expectation Builder class for fluid code.
 *
 * @version $Revision: $ $Date: $
 */
public final class ShouldBe {
    public static final Expectation TRUE = new EqualExpectation(true);

    public static final Expectation FALSE = new EqualExpectation(false);

    public static final Expectation VOID = new VoidExpectation();

    private static List<Factory<Expectation>> factories = new LinkedList<>();

    static {
        register(IsInstanceExpectation.class);
        register(ErrorExpectation.class);
        register(NullExpectation.class);
    }

    private ShouldBe() {
    }

    public static void register(Class<? extends Expectation> factoryClass) {
        factories.add(new Factory<>(factoryClass));
    }

    public static Expectation equal(Object o) {
        return new EqualExpectation(o);
    }

    public static Expectation instanceOf(Class<?> c) {
        return new IsInstanceExpectation(c);
    }

    public static Expectation literal(String expected) {
        if (expected == null) {
            throw new NullPointerException("expected");
        }

        for (int i = factories.size() - 1; i >= 0; i--) {
            Factory<Expectation> factory = factories.get(i);
            Expectation expectation = factory.newInstance(expected);
            if (expectation != null) {
                return expectation;
            }
        }

        return new DuckExpectation(expected);
    }

    public static Either either(Expectation expectation) {
        return new Either(expectation);
    }

    public static Expectation not(Expectation expectation) {
        return new NotExpectation(expectation);
    }
}
