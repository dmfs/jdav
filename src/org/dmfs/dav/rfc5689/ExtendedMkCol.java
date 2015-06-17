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

package org.dmfs.dav.rfc5689;

import org.dmfs.dav.rfc4918.WebDav;
import org.dmfs.httpclientinterfaces.HttpMethod;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;


/**
 * Defines the new XML elements specified in <a href="https://tools.ietf.org/html/rfc5689#section-5.1">RFC 5689</a>, Extended MKCOL for Web Distributed
 * Authoring and Versioning (WebDAV).
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class ExtendedMkCol
{
	/**
	 * Extended MkCol uses the {@link WebDav#NAMESPACE} namespace.
	 */
	public final static String NAMESPACE = WebDav.NAMESPACE;

	/**
	 * The HTTP method to be used with this request. It's the same as {@link WebDav#METHOD_MKCOL}.
	 */
	public final static HttpMethod METHOD_MKCOL = WebDav.METHOD_MKCOL;

	/**
	 * Element descriptor of {@link MkCol}.
	 */
	public final static ElementDescriptor<MkCol> MKCOL = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "mkcol"), MkCol.BUILDER);

	/**
	 * Element descriptor of {@link MkColResponse}.
	 */
	public final static ElementDescriptor<MkColResponse> MKCOL_RESPONSE = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "mkcol-response"),
		MkColResponse.BUILDER);

}
