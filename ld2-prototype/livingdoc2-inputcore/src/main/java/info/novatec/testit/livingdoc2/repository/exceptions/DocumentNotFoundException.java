/* Copyright (c) 2007 Pyxis Technologies inc.
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

package info.novatec.testit.livingdoc2.repository.exceptions;

import java.net.URI;
import java.net.URL;


/**
 * Adapted from LivingDoc 1.0.
 */
@SuppressWarnings("serial")
public class DocumentNotFoundException extends LivingDocParseException {
    private final String location;

    public DocumentNotFoundException(URI location) {
        this(location.toASCIIString());
    }

    public DocumentNotFoundException(URL location) {
        this(location.toExternalForm());
    }

    public DocumentNotFoundException(String location) {
        super();

        this.location = location;
    }

    @Override
    public String getMessage() {
        return "Document not found " + location;
    }
}
