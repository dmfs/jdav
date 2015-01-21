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

package org.dmfs.dav.rfc3744;

import java.net.URI;
import java.util.Set;

import org.dmfs.dav.rfc4918.WebDav;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.builder.QualifiedNameObjectBuilder;
import org.dmfs.xmlobjects.builder.SetObjectBuilder;
import org.dmfs.xmlobjects.builder.TransientObjectBuilder;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;


/**
 * Names and definitions from <a href="http://tools.ietf.org/html/rfc3744">Access Control Protocol Extensions to WebDAV, RFC 3744</a>.
 * <p>
 * TODO: add all the missing names and properties.
 * </p>
 * <p>
 * TODO: add missing javadoc.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class WebDavAcl
{
	/**
	 * WebDAV ACL uses the {@link WebDav#NAMESPACE} namespace.
	 */
	public final static String NAMESPACE = WebDav.NAMESPACE;

	public final static ElementDescriptor<QualifiedName> READ = ElementDescriptor.register(Privileges.READ, QualifiedNameObjectBuilder.INSTANCE);

	/**
	 * principal as defined in <a href="http://tools.ietf.org/html/rfc3744#section-4">RFC 3744, section 4</a> and <a
	 * href="http://tools.ietf.org/html/rfc3744#appendix-A">RFC 3744, appendix A</a>. It accepts any element and stores the {@link QualifiedName}.
	 */
	public final static ElementDescriptor<QualifiedName> PRINCIPAL = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "principal"),
		new TransientObjectBuilder<QualifiedName>(QualifiedNameObjectBuilder.INSTANCE));

	/**
	 * privilege as defined in <a href="http://tools.ietf.org/html/rfc3744#section-5.4">RFC 3744, section 5.4</a> and <a
	 * href="http://tools.ietf.org/html/rfc3744#appendix-A">RFC 3744, appendix A</a>. It accepts any element and stores the {@link QualifiedName}.
	 */
	public final static ElementDescriptor<QualifiedName> PRIVILEGE = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "privilege"),
		new TransientObjectBuilder<QualifiedName>(QualifiedNameObjectBuilder.INSTANCE));

	/**
	 * This is the pseudo principal <code>unauthenticated</code>. Please note that this exists for simplification reasons. The actual value of this is not of
	 * any relevance and should not be used.
	 */
	public final static URI PRINCIPAL_UNAUTHENTICATED = URI.create("http://dmfs.org/pseudo-principals/unauthenticated");

	public final static ElementDescriptor<URI> UNAUTHENTICATED = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "unauthenticated"),
		new AbstractObjectBuilder<URI>()
		{
			public URI get(ElementDescriptor<URI> descriptor, URI recycle, ParserContext context) throws XmlObjectPullParserException
			{
				return PRINCIPAL_UNAUTHENTICATED;
			};
		});

	/**
	 * This is the pseudo principal <code>authenticated</code>. Please note that this exists for simplification reasons. The actual value of this is not of any
	 * relevance and should not be used.
	 */
	public final static URI PRINCIPAL_AUTHENTICATED = URI.create("http://dmfs.org/pseudo-principals/authenticated");

	public final static ElementDescriptor<URI> AUTHENTICATED = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "authenticated"),
		new AbstractObjectBuilder<URI>()
		{
			public URI get(ElementDescriptor<URI> descriptor, URI recycle, ParserContext context) throws XmlObjectPullParserException
			{
				return PRINCIPAL_AUTHENTICATED;
			};
		});

	/**
	 * This is the pseudo principal <code>all</code>. The actual value of this is not of any relevance and should not be used.
	 */
	public final static URI PRINCIPAL_ALL = URI.create("http://dmfs.org/pseudo-principals/all");

	public final static ElementDescriptor<URI> ALL = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "all"), new AbstractObjectBuilder<URI>()
	{
		public URI get(ElementDescriptor<URI> descriptor, URI recycle, ParserContext context) throws XmlObjectPullParserException
		{
			return PRINCIPAL_ALL;
		};
	});

	public final static class Privileges
	{
		public final static QualifiedName READ = QualifiedName.get(NAMESPACE, "read");

		public final static QualifiedName WRITE = WebDav.LockTypes.WRITE;


		/**
		 * No instances allowed.
		 */
		private Privileges()
		{
		}
	}

	public final static class Properties
	{

		/**
		 * current-user-privilege-set as defined in <a href="http://tools.ietf.org/html/rfc3744#section-5.4">RFC 3744, section 5.4</a> and <a
		 * href="http://tools.ietf.org/html/rfc3744#appendix-A">RFC 3744, appendix A</a>.
		 */
		public final static ElementDescriptor<Set<QualifiedName>> CURRENT_USER_PRIVILEGE_SET = ElementDescriptor.register(
			QualifiedName.get(NAMESPACE, "current-user-privilege-set"), new SetObjectBuilder<QualifiedName>(PRIVILEGE));

		/**
		 * principal-collection-set as defined in <a href="http://tools.ietf.org/html/rfc3744#section-5.8">RFC 3744, section 5.8</a> and <a
		 * href="http://tools.ietf.org/html/rfc3744#appendix-A">RFC 3744, appendix A</a>
		 */
		public final static ElementDescriptor<Set<URI>> PRINCIPAL_COLLECTION_SET = ElementDescriptor.register(
			QualifiedName.get(NAMESPACE, "principal-collection-set"), new SetObjectBuilder<URI>(WebDav.HREF));


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
	private WebDavAcl()
	{
	}
}
