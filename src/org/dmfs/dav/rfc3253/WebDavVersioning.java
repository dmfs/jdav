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

package org.dmfs.dav.rfc3253;

import java.util.Set;

import org.dmfs.dav.rfc4918.WebDav;
import org.dmfs.httpclientinterfaces.HttpMethod;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.QualifiedNameObjectBuilder;
import org.dmfs.xmlobjects.builder.SetObjectBuilder;
import org.dmfs.xmlobjects.builder.TransientObjectBuilder;


/**
 * Names and definitions as defined in <a href="http://tools.ietf.org/html/rfc3253">Versioning Extensions to WebDAV, RFC 3253</a>.
 * <p>
 * TODO: add all the missing names and properties
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class WebDavVersioning
{

	/**
	 * WebDAV Versioning uses the {@link WebDav#NAMESPACE} namespace.
	 */
	public final static String NAMESPACE = WebDav.NAMESPACE;

	/**
	 * REPORT HTTP method.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc3253#section-3.6">RFC 3253, section 3.6</a>
	 */
	public final static HttpMethod METHOD_REPORT = HttpMethod.safeMethod("REPORT");

	/**
	 * <code>DAV:report</code> as defined in <a href="http://tools.ietf.org/html/rfc3253#section-3.1.5">RFC 3253, section 3.1.5</a>. It accepts any element and
	 * stores the {@link QualifiedName}.
	 */
	public final static ElementDescriptor<QualifiedName> REPORT = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "report"),
		new TransientObjectBuilder<QualifiedName>(QualifiedNameObjectBuilder.INSTANCE));

	/**
	 * <code>DAV:supported-report</code> as defined in <a href="http://tools.ietf.org/html/rfc3253#section-3.1.5">RFC 3253, section 3.1.5</a>
	 */
	public final static ElementDescriptor<QualifiedName> SUPPORTED_REPORT = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "supported-report"),
		new TransientObjectBuilder<QualifiedName>(REPORT));

	/* --------------------------------------------- Property elements --------------------------------------------- */

	final static ElementDescriptor<Set<QualifiedName>> PROP_SUPPORTED_REPORT_SET = ElementDescriptor.register(
		QualifiedName.get(NAMESPACE, "supported-report-set"), new SetObjectBuilder<QualifiedName>(SUPPORTED_REPORT, false /* don't store null values */));

	/**
	 * Properties defined in <a href="http://tools.ietf.org/html/rfc3253">RFC 3253</a>.
	 */
	public final static class Properties
	{
		/**
		 * <code>DAV:supported-report-set</code> as defined in <a href="http://tools.ietf.org/html/rfc3253#section-3.1.5">RFC 3253, section 3.1.5</a>
		 */
		public final static ElementDescriptor<Set<QualifiedName>> SUPPORTED_REPORT_SET = WebDavVersioning.PROP_SUPPORTED_REPORT_SET;


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
	private WebDavVersioning()
	{
	}
}
