/*
 * Copyright (C) 2015 Marten Gajda <marten@dmfs.org>
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

package org.dmfs.dav.rfc5842;

import org.dmfs.dav.rfc4918.WebDav;
import org.dmfs.httpessentials.HttpMethod;
import org.dmfs.httpessentials.methods.IdempotentMethod;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.StringObjectBuilder;


/**
 * Names and definitions from <a href="http://tools.ietf.org/html/rfc5842">Binding Extensions to Web Distributed Authoring and Versioning (WebDAV), RFC
 * 5842</a>.
 * <p>
 * TODO: add all the missing elements and properties.
 * </p>
 * <p>
 * TODO: add missing javadoc.
 * </p>
 */
public final class WebDavBind
{
    /**
     * WebDAV Bind uses the {@link WebDav#NAMESPACE} namespace.
     */
    public final static String NAMESPACE = WebDav.NAMESPACE;

    /**
     * BIND HTTP method.
     *
     * @see <a href="https://tools.ietf.org/html/rfc5842#section-4">RFC 5842, section 4</a>.
     */
    public final static HttpMethod METHOD_BIND = new IdempotentMethod("BIND", true);

    /**
     * UNBIND HTTP method.
     *
     * @see <a href="https://tools.ietf.org/html/rfc5842#section-5">RFC 5842, section 5</a>.
     */
    public final static HttpMethod METHOD_UNBIND = new IdempotentMethod("UNBIND", true);

    /**
     * REBIND HTTP method.
     *
     * @see <a href="https://tools.ietf.org/html/rfc5842#section-6">RFC 5842, section 6</a>.
     */
    public final static HttpMethod METHOD_REBIND = new IdempotentMethod("REBIND", true);

    /* --------------------------------------------- Property elements --------------------------------------------- */

    final static ElementDescriptor<String> PROPERTY_RESOURCE_ID = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "resource-id"),
        StringObjectBuilder.INSTANCE);


    /**
     * Properties defined in <a href="https://tools.ietf.org/html/rfc5842#section-3">RFC 5842, section 3</a>.
     * <p>
     * TODO: add parent-set: <a href="https://tools.ietf.org/html/rfc5842#section-3.2">RFC 5842, section 3.2</a>
     * </p>
     */
    public final static class Properties
    {

        /**
         * resource-id as defined in <a href="https://tools.ietf.org/html/rfc5842#section-3.1">RFC 5842, section 3.1</a>.
         * <p>
         * Note: In contrast to the definition in RFC 5842 this property is defined to be a {@link String} not a URI. That way save some overhead for parsing a
         * URI, which we don't need to parse, since it's an opaque identifier to the client. However, a server needs to ensure the value complies with the
         * requirements for a URI (notably the encoding of reserved chars).
         * </p>
         *
         * <pre>
         * The DAV:resource-id property is a REQUIRED property that enables
         * clients to determine whether two bindings are to the same resource.
         * The value of DAV:resource-id is a URI, and may use any registered URI
         * scheme that guarantees the uniqueness of the value across all
         * resources for all time (e.g., the urn:uuid: URN namespace defined in
         * <a href="https://tools.ietf.org/html/rfc4122">[RFC4122]</a> or the opaquelocktoken: URI scheme defined in <a href="https://tools.ietf.org/html/rfc4918">[RFC4918]</a>).
         *
         * &lt;!ELEMENT resource-id (href)>
         * </pre>
         */
        public final static ElementDescriptor<String> RESOURCE_ID = WebDavBind.PROPERTY_RESOURCE_ID;


        /**
         * No instances allowed.
         */
        private Properties()
        {
        }
    }


    /**
     * No instances allowed.
     */
    private WebDavBind()
    {
    }
}
