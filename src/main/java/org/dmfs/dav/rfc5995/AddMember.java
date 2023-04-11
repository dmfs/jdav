/*
 * Copyright (C) 2014 Marten Gajda <marten@dmfs.org>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 */

package org.dmfs.dav.rfc5995;

import org.dmfs.dav.rfc4918.WebDav;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.TransientObjectBuilder;

import java.net.URI;


/**
 * Defines the <code>DAV:add-member</code> property as specified in <a href="http://tools.ietf.org/html/rfc5995">RFC 5995</a>.
 */
public final class AddMember
{

    /**
     * Defines properties specified in <a href="http://tools.ietf.org/html/rfc5995">RFC 5995</a>.
     */
    public static class Properties
    {
        /**
         * Defines the <code>add-member</code> property, see <a href="http://tools.ietf.org/html/rfc5995#section-3.1">RFC 5995, section 3.1</a>.
         *
         * <pre>
         *  The "Add-Member" URI of a WebDAV collection is a URI that will accept
         *  HTTP POST requests, and will interpret these as requests to store the
         *  enclosed entity as a new internal member of the collection (see
         *  <a href="http://tools.ietf.org/html/rfc4918#section-3">Section 3 of [RFC4918]</a> for the definition of "internal member").  It
         *  MUST identify a resource on the same server as the WebDAV collection
         *  (the host and port components (<a href="http://tools.ietf.org/html/rfc2616#section-3.2.2">[RFC2616], Section 3.2.2</a>) of the URIs
         *  must match).
         * </pre>
         */
        public final static ElementDescriptor<URI> ADD_MEMBER = ElementDescriptor.register(QualifiedName.get(WebDav.NAMESPACE, "add-member"),
            new TransientObjectBuilder<URI>(WebDav.HREF));


        /**
         * No instances allowed.
         */
        private Properties()
        {
        }
    }


    /**
     * Defines preconditions as specified in <a href="http://tools.ietf.org/html/rfc5995">RFC 5995</a>.
     */
    public static class PreConditions
    {
        /**
         * Defines the <code>allow-client-defined-uri</code> precondition, see <a href="http://tools.ietf.org/html/rfc5995#section-4.1">RFC 5995, section
         * 4.1</a>:
         *
         * <pre>
         * (DAV:allow-client-defined-URI): the server allows clients to specify
         * the last path segment for newly created resources.
         *
         * The precondition element MAY contain an add-member-uri XML element
         * specifying the "Add-Member" URI associated with the collection, on
         * which the creation of a new child resource was attempted:
         *
         * &lt;!ELEMENT allow-client-defined-uri (add-member?)>
         * </pre>
         */
        public final static ElementDescriptor<URI> ALLOW_CLIENT_DEFINED_URI = ElementDescriptor.register(
            QualifiedName.get(WebDav.NAMESPACE, "allow-client-defined-uri"), new TransientObjectBuilder<URI>(Properties.ADD_MEMBER));


        /**
         * No instances allowed.
         */
        private PreConditions()
        {
        }
    }


    /**
     * No instances allowed.
     */
    private AddMember()
    {
    }
}
