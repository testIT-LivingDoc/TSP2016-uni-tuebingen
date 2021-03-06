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

import info.novatec.testit.livingdoc2.api.expectation.Expectation;
import info.novatec.testit.livingdoc2.expectation.factory.FactoryMethod;


/**
 * Adapted from LivingDoc 1.0.
 *
 * Checks for a null return
 *
 * @version $Revision: $ $Date: $
 */
public class NullExpectation implements Expectation {

    @FactoryMethod
    public static NullExpectation create(String expected) {
        return expected.equals("null") ? new NullExpectation() : null;
    }

    @Override
    public StringBuilder describeTo(StringBuilder sb) {
        return sb.append("null");
    }

    @Override
    public boolean meets(Object result) {
        return (result == null);
    }

}
