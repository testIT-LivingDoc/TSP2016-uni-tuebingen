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

package info.novatec.testit.livingdoc2.expectation.expectations;

import java.util.regex.Pattern;

import info.novatec.testit.livingdoc2.api.expectation.Expectation;
import info.novatec.testit.livingdoc2.expectation.ShouldBe;
import info.novatec.testit.livingdoc2.expectation.factory.FactoryMethod;
import info.novatec.testit.livingdoc2.util.TypeConversion;


/**
 * Adapted from LivingDoc 1.0.
 *
 * Compares two values with each other.
 *
 * @version $Revision: $ $Date: $
 */
public class DuckExpectation implements Expectation {
    private final String expected;

    public DuckExpectation(String expected) {
        this.expected = removeDoubleQuotes(expected);
    }

    @FactoryMethod
    public static DuckExpectation create(String expected) {
        return new DuckExpectation(expected);
    }

    private String removeDoubleQuotes(String paramExpected) {
        if (Pattern.matches("(?s)^\\s*\".*?\"\\s*$", paramExpected)) {
            return paramExpected.trim().substring(1, paramExpected.length() - 1);
        }
        return paramExpected;
    }

    @Override
    public StringBuilder describeTo(StringBuilder sb) {
        return sb.append(expected);
    }

    @Override
    public boolean meets(Object result) {
        Object expectedValue = canCoerceTo(result) ? coerceTo(result) : expected;
        return ShouldBe.equal(expectedValue).meets(result);
    }

    private Object coerceTo(Object result) {
        return TypeConversion.parse(expected, result.getClass());
    }

    private boolean canCoerceTo(Object result) {
        return result != null && TypeConversion.supports(result.getClass());
    }

    @Override
    public String toString() {
        return expected;
    }
}
