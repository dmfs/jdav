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

package org.dmfs.dav.rfc5323;

import java.util.Map;

import org.dmfs.dav.rfc4918.WebDav;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.ElementMapObjectBuilder;
import org.dmfs.xmlobjects.builder.IntegerObjectBuilder;


/**
 * Names and definitions from <a href="http://tools.ietf.org/html/rfc5323">RFC 5323, WebDAV SEARCH</a>.
 * <p>
 * TODO: add all missing definitions
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class WebDavSearch
{
	/**
	 * WebDAV Search uses the {@link WebDav#NAMESPACE} namespace.
	 */
	public final static String NAMESPACE = WebDav.NAMESPACE;

	/**
	 * <code>DAV:nresults</code> is defined in <a href="http://tools.ietf.org/html/rfc5323#section-5.17">RFC 5323 section 5.17</a>.
	 * 
	 * <pre>
	 * The DAV:nresults XML element contains a requested maximum
	 * number of DAV:response elements to be returned in the response body.
	 * The server MAY disregard this limit.  The value of this element is an
	 * unsigned integer.
	 * </pre>
	 */
	public final static ElementDescriptor<Integer> NRESULTS = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "nresults"),
		IntegerObjectBuilder.INSTANCE_STRICT);

	/**
	 * <code>DAV:limit</code> is defined in <a href="http://tools.ietf.org/html/rfc5323#section-5.17">RFC 5323 section 5.17</a>. The limit element is
	 * represented by a Map of {@link ElementDescriptor}s to values. Each entry represents a specific limit.
	 * 
	 * <pre>
	 * The DAV:limit XML element contains requested limits from the client
	 * to limit the size of the reply or amount of effort expended by the
	 * server.
	 * </pre>
	 * 
	 */
	public final static ElementDescriptor<Map<ElementDescriptor<?>, Object>> LIMIT = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "limit"),
		ElementMapObjectBuilder.INSTANCE);


	/**
	 * No instances allowed.
	 */
	private WebDavSearch()
	{
	}
}
