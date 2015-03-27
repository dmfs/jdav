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

	/**
	 * principal as defined in <a href="http://tools.ietf.org/html/rfc3744#section-4">RFC 3744, section 4</a> and <a
	 * href="http://tools.ietf.org/html/rfc3744#appendix-A">RFC 3744, appendix A</a>. It accepts any element and stores the {@link QualifiedName}.
	 */
	public final static ElementDescriptor<QualifiedName> PRINCIPAL = ElementDescriptor.register(ResourceTypes.PRINCIPAL,
		new TransientObjectBuilder<QualifiedName>(QualifiedNameObjectBuilder.INSTANCE));

	/**
	 * Defines new resource types introduced in <a href="http://tools.ietf.org/html/rfc3744#section-5.4">RFC 3744, section 5.4</a>.
	 */
	public final static class ResourceTypes
	{
		/**
		 * {@link QualifiedName} of the principal resource-type.
		 */
		public final static QualifiedName PRINCIPAL = QualifiedName.get(NAMESPACE, "principal");

	}

	/* --------------------------------------------- Principal elements --------------------------------------------- */

	public final static ElementDescriptor<URI> PRINCIPAL_ALL = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "all"), new AbstractObjectBuilder<URI>()
	{
		public URI get(ElementDescriptor<URI> descriptor, URI recycle, ParserContext context) throws XmlObjectPullParserException
		{
			return PseudoPrincipals.ALL;
		};
	});

	public final static ElementDescriptor<URI> PRINCIPAL_AUTHENTICATED = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "authenticated"),
		new AbstractObjectBuilder<URI>()
		{
			public URI get(ElementDescriptor<URI> descriptor, URI recycle, ParserContext context) throws XmlObjectPullParserException
			{
				return PseudoPrincipals.AUTHENTICATED;
			};
		});

	public final static ElementDescriptor<URI> PRINCIPAL_UNAUTHENTICATED = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "unauthenticated"),
		new AbstractObjectBuilder<URI>()
		{
			public URI get(ElementDescriptor<URI> descriptor, URI recycle, ParserContext context) throws XmlObjectPullParserException
			{
				return PseudoPrincipals.UNAUTHENTICATED;
			};
		});

	public final static ElementDescriptor<URI> PRINCIPAL_SELF = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "self"),
		new AbstractObjectBuilder<URI>()
		{
			public URI get(ElementDescriptor<URI> descriptor, URI recycle, ParserContext context) throws XmlObjectPullParserException
			{
				return PseudoPrincipals.SELF;
			};
		});

	public final static class PseudoPrincipals
	{
		/**
		 * This is the pseudo principal <code>all</code>. The actual value of this is not of any relevance and should not be used.
		 */
		public final static URI ALL = URI.create("http://dmfs.org/pseudo-principals/all");

		/**
		 * This is the pseudo principal <code>authenticated</code>. The actual value of this is not of any relevance and should not be used.
		 */
		public final static URI AUTHENTICATED = URI.create("http://dmfs.org/pseudo-principals/authenticated");

		/**
		 * This is the pseudo principal <code>unauthenticated</code>. The actual value of this is not of any relevance and should not be used.
		 */
		public final static URI UNAUTHENTICATED = URI.create("http://dmfs.org/pseudo-principals/unauthenticated");

		/**
		 * This is the pseudo principal <code>self</code>. The actual value of this is not of any relevance and should not be used.
		 */
		public final static URI SELF = URI.create("http://dmfs.org/pseudo-principals/self");
	}

	/* --------------------------------------------- Privilege elements --------------------------------------------- */

	/**
	 * privilege as defined in <a href="http://tools.ietf.org/html/rfc3744#section-5.4">RFC 3744, section 5.4</a> and <a
	 * href="http://tools.ietf.org/html/rfc3744#appendix-A">RFC 3744, appendix A</a>. It accepts any element and stores the {@link QualifiedName}.
	 */
	public final static ElementDescriptor<QualifiedName> PRIVILEGE = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "privilege"),
		new TransientObjectBuilder<QualifiedName>(QualifiedNameObjectBuilder.INSTANCE));

	public final static ElementDescriptor<QualifiedName> PRIVILEGE_ALL = ElementDescriptor.registerWithParents(Privileges.ALL,
		QualifiedNameObjectBuilder.INSTANCE, PRIVILEGE);

	public final static ElementDescriptor<QualifiedName> PRIVILEGE_BIND = ElementDescriptor.registerWithParents(Privileges.BIND,
		QualifiedNameObjectBuilder.INSTANCE, PRIVILEGE);

	public final static ElementDescriptor<QualifiedName> PRIVILEGE_READ_ACL = ElementDescriptor.registerWithParents(Privileges.READ_ACL,
		QualifiedNameObjectBuilder.INSTANCE, PRIVILEGE);

	public final static ElementDescriptor<QualifiedName> PRIVILEGE_READ_CURRENT_USER_PRIVILEGE_SET = ElementDescriptor.registerWithParents(
		Privileges.READ_CURRENT_USER_PRIVILEGE_SET, QualifiedNameObjectBuilder.INSTANCE, PRIVILEGE);

	public final static ElementDescriptor<QualifiedName> PRIVILEGE_WRITE = ElementDescriptor.registerWithParents(Privileges.WRITE,
		QualifiedNameObjectBuilder.INSTANCE, PRIVILEGE);

	public final static ElementDescriptor<QualifiedName> PRIVILEGE_WRITE_ACL = ElementDescriptor.registerWithParents(Privileges.WRITE_ACL,
		QualifiedNameObjectBuilder.INSTANCE, PRIVILEGE);

	public final static ElementDescriptor<QualifiedName> PRIVILEGE_WRITE_PROPERTIES = ElementDescriptor.registerWithParents(Privileges.WRITE_PROPERTIES,
		QualifiedNameObjectBuilder.INSTANCE, PRIVILEGE);

	public final static ElementDescriptor<QualifiedName> PRIVILEGE_WRITE_CONTENT = ElementDescriptor.registerWithParents(Privileges.WRITE_CONTENT,
		QualifiedNameObjectBuilder.INSTANCE, PRIVILEGE);

	public final static ElementDescriptor<QualifiedName> PRIVILEGE_UNBIND = ElementDescriptor.registerWithParents(Privileges.UNBIND,
		QualifiedNameObjectBuilder.INSTANCE, PRIVILEGE);

	public final static ElementDescriptor<QualifiedName> PRIVILEGE_UNLOCK = ElementDescriptor.registerWithParents(Privileges.UNLOCK,
		QualifiedNameObjectBuilder.INSTANCE, PRIVILEGE);

	/**
	 * Privileges defined in <a href="http://tools.ietf.org/html/rfc3744#section-3">RFC 3744, Section 3</a>.
	 */
	public final static class Privileges
	{
		public final static QualifiedName ALL = QualifiedName.get(NAMESPACE, "all");

		public final static QualifiedName BIND = QualifiedName.get(NAMESPACE, "bind");

		public final static QualifiedName READ = QualifiedName.get(NAMESPACE, "read");

		public final static QualifiedName READ_ACL = QualifiedName.get(NAMESPACE, "read-acl");

		public final static QualifiedName READ_CURRENT_USER_PRIVILEGE_SET = QualifiedName.get(NAMESPACE, "read-current-user-privilege-set");

		public final static QualifiedName WRITE = QualifiedName.get(NAMESPACE, "write");

		public final static QualifiedName WRITE_ACL = QualifiedName.get(NAMESPACE, "write-acl");

		public final static QualifiedName WRITE_PROPERTIES = QualifiedName.get(NAMESPACE, "write-properties");

		public final static QualifiedName WRITE_CONTENT = QualifiedName.get(NAMESPACE, "write-content");

		public final static QualifiedName UNBIND = QualifiedName.get(NAMESPACE, "unbind");

		public final static QualifiedName UNLOCK = QualifiedName.get(NAMESPACE, "unlock");


		/**
		 * No instances allowed.
		 */
		private Privileges()
		{
		}
	}

	/* --------------------------------------------- Property elements --------------------------------------------- */

	final static ElementDescriptor<Set<QualifiedName>> PROP_CURRENT_USER_PRIVILEGE_SET = ElementDescriptor.register(
		QualifiedName.get(NAMESPACE, "current-user-privilege-set"), new SetObjectBuilder<QualifiedName>(PRIVILEGE));

	final static ElementDescriptor<Set<URI>> PROP_PRINCIPAL_COLLECTION_SET = ElementDescriptor.register(
		QualifiedName.get(NAMESPACE, "principal-collection-set"), new SetObjectBuilder<URI>(WebDav.HREF));

	/**
	 * Properties defined in <a href="http://tools.ietf.org/html/rfc3744#section-4">RFC 3744, Section 4</a> and <a
	 * href="http://tools.ietf.org/html/rfc3744#section-5">RFC 3744, Section 5</a>.
	 */
	public final static class Properties
	{

		/**
		 * current-user-privilege-set as defined in <a href="http://tools.ietf.org/html/rfc3744#section-5.4">RFC 3744, section 5.4</a> and <a
		 * href="http://tools.ietf.org/html/rfc3744#appendix-A">RFC 3744, appendix A</a>.
		 */
		public final static ElementDescriptor<Set<QualifiedName>> CURRENT_USER_PRIVILEGE_SET = WebDavAcl.PROP_CURRENT_USER_PRIVILEGE_SET;

		/**
		 * principal-collection-set as defined in <a href="http://tools.ietf.org/html/rfc3744#section-5.8">RFC 3744, section 5.8</a> and <a
		 * href="http://tools.ietf.org/html/rfc3744#appendix-A">RFC 3744, appendix A</a>
		 */
		public final static ElementDescriptor<Set<URI>> PRINCIPAL_COLLECTION_SET = WebDavAcl.PROP_PRINCIPAL_COLLECTION_SET;


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
