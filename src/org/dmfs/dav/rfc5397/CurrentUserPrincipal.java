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

package org.dmfs.dav.rfc5397;

import java.io.IOException;
import java.net.URI;

import org.dmfs.dav.rfc3744.WebDavAcl;
import org.dmfs.dav.rfc4918.WebDav;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.TransientObjectBuilder;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;


/**
 * Defines the <code>current-user-principal</code> property as specified in <a href="http://tools.ietf.org/html/rfc5397">RFC 5397</a>. The value of this
 * property is either an href pointing to a principal resource of the authenticated user or the pseudo principal {@link WebDavAcl#PRINCIPAL_UNAUTHENTICATED}.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class CurrentUserPrincipal
{

	/**
	 * <code>DAV:current-user-principal</code> property, see <a href="http://tools.ietf.org/html/rfc5397#section-3">RFC 5397, section 3</a>.
	 * 
	 * <p>
	 * <strong>Note: </strong> if the server returns the pseudo principal <code>unauthenticated</code> the value of this property will be
	 * {@link WebDavAcl#PRINCIPAL_UNAUTHENTICATED}.
	 * </p>
	 * 
	 * <pre>
	 *   Name:  current-user-principal
	 * 
	 *    Namespace:  DAV:
	 * 
	 *    Purpose:  Indicates a URL for the currently authenticated user's
	 *       principal resource on the server.
	 * 
	 *    Value:  A single DAV:href or DAV:unauthenticated element.
	 * 
	 *    Protected:  This property is computed on a per-request basis, and
	 *       therefore is protected.
	 * 
	 *    Description:  The DAV:current-user-principal property contains either
	 *       a DAV:href or DAV:unauthenticated XML element.  The DAV:href
	 *       element contains a URL to a principal resource corresponding to
	 *       the currently authenticated user.  That URL MUST be one of the
	 *       URLs in the DAV:principal-URL or DAV:alternate-URI-set properties
	 *       defined on the principal resource and MUST be an http(s) scheme
	 *       URL.  When authentication has not been done or has failed, this
	 *       property MUST contain the DAV:unauthenticated pseudo-principal.
	 * 
	 *       In some cases, there may be multiple principal resources
	 *       corresponding to the same authenticated principal.  In that case,
	 *       the server is free to choose any one of the principal resource
	 *       URIs for the value of the DAV:current-user-principal property.
	 *       However, servers SHOULD be consistent and use the same principal
	 *       resource URI for each authenticated principal.
	 * 
	 *    COPY/MOVE behavior:  This property is computed on a per-request
	 *       basis, and is thus never copied or moved.
	 * 
	 *    Definition:
	 * 
	 *       &lt;!ELEMENT current-user-principal (unauthenticated | href)>
	 *       &lt;!-- href value: a URL to a principal resource -->
	 * 
	 *    Example:
	 * 
	 *       &lt;D:current-user-principal xmlns:D="DAV:">
	 *         &lt;D:href>/principals/users/cdaboo&lt;/D:href>
	 *       &lt;/D:current-user-principal>
	 * </pre>
	 */
	public final static ElementDescriptor<URI> CURRENT_USER_PRINCIPAL = ElementDescriptor.register(
		QualifiedName.get(WebDav.NAMESPACE, "current-user-principal"), new TransientObjectBuilder<URI>(WebDav.HREF)
		{
			@Override
			public <V> URI update(ElementDescriptor<URI> descriptor, URI object, ElementDescriptor<V> childDescriptor, V child, ParserContext context)
				throws XmlObjectPullParserException
			{
				// the unauthenticated pseudo principal is not inside of an href element
				if (childDescriptor == WebDavAcl.UNAUTHENTICATED)
				{
					return (URI) child;
				}
				return super.update(descriptor, object, childDescriptor, child, context);
			}


			public void writeChildren(ElementDescriptor<URI> descriptor, URI object, IXmlChildWriter childWriter, SerializerContext context)
				throws SerializerException, IOException
			{
				if (WebDavAcl.PRINCIPAL_UNAUTHENTICATED.equals(object))
				{
					// don't write href element if the URI equals the unauthenticated pseudo principal
					childWriter.writeChild(WebDavAcl.UNAUTHENTICATED, object, context);
				}
				else
				{
					super.writeChildren(descriptor, object, childWriter, context);
				}
			};
		});


	/**
	 * No instances allowed.
	 */
	private CurrentUserPrincipal()
	{
	}
}
