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

import java.util.Arrays;

import info.novatec.testit.livingdoc2.api.expectation.Expectation;


/**
 *
 * Adapted from LivingDoc 1.0.
 *
 * Usually used for boolean comparison
 *
 * @version $Revision: $ $Date: $
 */
public class EqualExpectation implements Expectation {
    private final Object matchee;

    public EqualExpectation(Object matchee) {
        this.matchee = matchee;
    }

    @Override
    public StringBuilder describeTo(StringBuilder sb) {
        return sb.append(toString());
    }

    @Override
    public boolean meets(Object result) {
        if (isArray(matchee)) {
            return isArray(result) && Arrays.deepEquals(( Object[] ) matchee, ( Object[] ) result);
        }

        return matchee.equals(result);
    }

    private boolean isArray(Object o) {
        return o.getClass().getComponentType() != null;
    }

    @Override
    public String toString() {
        return isArray(matchee) ? Arrays.deepToString(( Object[] ) matchee) : matchee.toString();
    }
}
