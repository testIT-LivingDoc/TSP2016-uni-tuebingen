/**
 * Copyright (c) 2009 Pyxis Technologies inc.
 * <p/>
 * This is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * <p/>
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p/>
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA, or see the FSF site:
 * http://www.fsf.org.
 */
package info.novatec.testit.livingdoc2.expectation.expectations;

import info.novatec.testit.livingdoc2.api.expectation.Expectation;


/**
 * Adapted from LivingDoc 1.0.
 * <p/>
 * Inverted expectation
 */
public class NotExpectation implements Expectation {

    private final Expectation expectation;

    public NotExpectation(Expectation expectation) {
        this.expectation = expectation;
    }

    @Override
    public StringBuilder describeTo(StringBuilder string) {
        string.append(" NOT ");
        return expectation.describeTo(string);
    }

    @Override
    public boolean meets(Object result) {
        return !(expectation.meets(result));
    }
}
