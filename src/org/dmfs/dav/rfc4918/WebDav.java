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

package org.dmfs.dav.rfc4918;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dmfs.httpclientinterfaces.ContentType;
import org.dmfs.httpclientinterfaces.HttpMethod;
import org.dmfs.httpclientinterfaces.HttpStatus;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.builder.ElementMapObjectBuilder;
import org.dmfs.xmlobjects.builder.IntegerObjectBuilder;
import org.dmfs.xmlobjects.builder.ListObjectBuilder;
import org.dmfs.xmlobjects.builder.QualifiedNameObjectBuilder;
import org.dmfs.xmlobjects.builder.SetObjectBuilder;
import org.dmfs.xmlobjects.builder.StringObjectBuilder;
import org.dmfs.xmlobjects.builder.TransientObjectBuilder;
import org.dmfs.xmlobjects.builder.UriObjectBuilder;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;


/**
 * Contains names and properties defined in <a href="http://tools.ietf.org/html/rfc4918">RFC 4918</a>.
 * <p>
 * TODO: add the missing fields
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class WebDav
{
	/**
	 * WebDAV namespace.
	 */
	public final static String NAMESPACE = "DAV:";

	/**
	 * PROPFIND HTTP method.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc4918#section-9.1">RFC 4918, section 9.1</a>
	 */
	public final static HttpMethod METHOD_PROPFIND = HttpMethod.safeMethod("PROPFIND");

	/**
	 * PROPPATCH HTTP method.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc4918#section-9.2">RFC 4918, section 9.2</a>
	 */
	public final static HttpMethod METHOD_PROPPATCH = HttpMethod.idempotentMethod("PROPPATCH");

	/**
	 * MKCOL HTTP method.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc4918#section-9.3">RFC 4918, section 9.3</a>
	 */
	public final static HttpMethod METHOD_MKCOL = HttpMethod.idempotentMethod("MKCOL");

	/*
	 * Names & elements defined in Section 14 of RFC 4918.
	 */

	// TODO: add activelock

	/**
	 * allprop element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.2">RFC 4918 Section 14.2</a>.
	 */
	public final static ElementDescriptor<QualifiedName> ALLPROP = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "allprop"),
		QualifiedNameObjectBuilder.INSTANCE);

	/**
	 * {@link QualifiedName} of the collection element.
	 */
	public final static QualifiedName COLLECTION = QualifiedName.get(NAMESPACE, "collection");

	/**
	 * collection element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.3">RFC 4918 Section 14.3</a>.
	 */
	public final static ElementDescriptor<QualifiedName> RESOURCE_TYPE_COLLECTION = ElementDescriptor.register(COLLECTION, QualifiedNameObjectBuilder.INSTANCE);

	/**
	 * depth is defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.4">RFC 4918 Section 14.4</a>.
	 */
	public final static ElementDescriptor<Depth> DEPTH = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "depth"), Depth.BUILDER);

	/**
	 * error element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.5">RFC 4918 Section 14.5</a>.
	 */
	public final static ElementDescriptor<Error> ERROR = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "error"), Error.BUILDER);

	/**
	 * exclusive element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.6">RFC 4918 Section 14.6</a>.
	 */
	public final static ElementDescriptor<QualifiedName> EXCLUSIVE = ElementDescriptor.register(LockScopes.EXCLUSIVE, QualifiedNameObjectBuilder.INSTANCE);

	/**
	 * href element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.7">RFC 4918 Section 14.7</a>. The value must not contain any white
	 * spaces.
	 */
	public final static ElementDescriptor<URI> HREF = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "href"), UriObjectBuilder.INSTANCE_STRICT);

	/**
	 * include element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.8">RFC 4918 Section 14.8</a>. The definition and purpose are similar
	 * to the one of {@link WebDav#PROP}.
	 */
	public final static ElementDescriptor<Map<ElementDescriptor<?>, Object>> INCLUDE = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "include"),
		ElementMapObjectBuilder.INSTANCE);

	/**
	 * location element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.9">RFC 4918 Section 14.9</a>.
	 */
	public final static ElementDescriptor<URI> LOCATION = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "location"), new TransientObjectBuilder<URI>(
		HREF));

	// TODO: add lock* elements (sections 14.10 - 14.13)

	/**
	 * lockroot element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.12">RFC 4918 Section 14.12</a>.
	 */
	public final static ElementDescriptor<URI> LOCKROOT = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "lockroot"), new TransientObjectBuilder<URI>(
		HREF));

	/**
	 * locktoken element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.14">RFC 4918 Section 14.14</a>.
	 */
	public final static ElementDescriptor<URI> LOCKTOKEN = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "locktoken"),
		new TransientObjectBuilder<URI>(HREF));

	/**
	 * locktype element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.15">RFC 4918 Section 14.15</a>.
	 * <p>
	 * The only value specified in RFC 4918 is {@link LockTypes#WRITE}.
	 * </p>
	 */
	public final static ElementDescriptor<QualifiedName> LOCKTYPE = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "locktype"),
		QualifiedNameObjectBuilder.INSTANCE);

	/**
	 * multistatus element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.16">RFC 4918 Section 14.16</a>.
	 */
	public final static ElementDescriptor<MultiStatus> MULTISTATUS = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "multistatus"),
		MultiStatus.BUILDER);

	// TODO: add owner element

	/**
	 * prop element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.18">RFC 4918 Section 14.18</a>.
	 */
	public final static ElementDescriptor<Map<ElementDescriptor<?>, Object>> PROP = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "prop"),
		ElementMapObjectBuilder.INSTANCE);

	/**
	 * propertyupdate element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.19">RFC 4918 Section 14.19</a>.
	 */
	public final static ElementDescriptor<PropertyUpdate> PROPERTYUPDATE = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "propertyupdate"),
		PropertyUpdate.BUILDER);

	/**
	 * propfind element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.20">RFC 4918 Section 14.20</a>.
	 */
	public final static ElementDescriptor<PropFind> PROPFIND = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "propfind"), PropFind.BUILDER);

	/**
	 * propname element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.21">RFC 4918 Section 14.21</a>.
	 */
	public final static ElementDescriptor<QualifiedName> PROPNAME = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "propname"),
		QualifiedNameObjectBuilder.INSTANCE);

	/**
	 * propstat element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.22">RFC 4918 Section 14.22</a>.
	 */
	public final static ElementDescriptor<PropStat> PROPSTAT = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "propstat"), PropStat.BUILDER);

	/**
	 * remove element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.23">RFC 4918 Section 14.23</a>.
	 */
	public final static ElementDescriptor<Map<ElementDescriptor<?>, Object>> REMOVE = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "remove"),
		new TransientObjectBuilder<Map<ElementDescriptor<?>, Object>>(PROP));

	/**
	 * response element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.24">RFC 4918 Section 14.24</a>.
	 */
	public final static ElementDescriptor<Response> RESPONSE = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "response"), Response.BUILDER);

	/**
	 * responsedescription element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.25">RFC 4918 Section 14.25</a>.
	 */
	public final static ElementDescriptor<String> RESPONSEDESCRIPTION = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "responsedescription"),
		StringObjectBuilder.INSTANCE);

	/**
	 * set element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.26">RFC 4918 Section 14.26</a>. At present it doesn't have a builder
	 * because it's not used in requests.
	 */
	public final static ElementDescriptor<Map<ElementDescriptor<?>, Object>> SET = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "set"),
		new TransientObjectBuilder<Map<ElementDescriptor<?>, Object>>(PROP));

	/**
	 * shared element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.27">RFC 4918 Section 14.27</a>.
	 */
	public final static ElementDescriptor<QualifiedName> SHARED = ElementDescriptor.register(LockScopes.SHARED, QualifiedNameObjectBuilder.INSTANCE);

	/**
	 * status element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.28">RFC 4918 Section 14.28</a>. This element returns the actual
	 * status code only, not the entire status line.
	 */
	public final static ElementDescriptor<Integer> STATUS = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "status"),
		new AbstractObjectBuilder<Integer>()
		{
			public Integer update(ElementDescriptor<Integer> descriptor, Integer object, String text, ParserContext context)
				throws XmlObjectPullParserException
			{
				return HttpStatus.parseCode(text.trim());
			};


			public void writeChildren(ElementDescriptor<Integer> descriptor, Integer object, IXmlChildWriter childWriter, SerializerContext context)
				throws SerializerException, IOException
			{
				childWriter.writeText(HttpStatus.getStatusLine(object), context);
			};
		});

	/**
	 * timeout element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.29">RFC 4918 Section 14.29</a>.
	 */
	public final static ElementDescriptor<Integer> TIMEOUT = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "timeout"),
		IntegerObjectBuilder.INSTANCE_STRICT);

	/**
	 * write element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.30">RFC 4918 Section 14.30</a>.
	 * 
	 * <p>
	 * <strong>Note:</strong> RFC 4918 defined this as a lock type, but other RFCs use the same element for other purposes, see <a
	 * href="https://tools.ietf.org/html/rfc3744#section-3.2">RFC 3744, Section 3.2</a>
	 * </p>
	 */
	public final static ElementDescriptor<QualifiedName> WRITE = ElementDescriptor.register(LockTypes.WRITE, QualifiedNameObjectBuilder.INSTANCE);

	/**
	 * DAV lock types. <a href="http://tools.ietf.org/html/rfc4918">RFC 4918</a> specifies only a single lock type: {@link #WRITE}.
	 */
	public final static class LockTypes
	{
		/**
		 * {@link QualifiedName} of the write lock type.
		 */
		public final static QualifiedName WRITE = QualifiedName.get(NAMESPACE, "write");


		/**
		 * No instances allowed.
		 */
		private LockTypes()
		{
		}
	}

	/**
	 * The lock scopes defined in RFC 4918.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc4918#section-6.2">RFC 4918, Section 6.2</a>
	 */
	public final static class LockScopes
	{
		/**
		 * {@link QualifiedName} of the exclusive lock scope.
		 */
		public final static QualifiedName EXCLUSIVE = QualifiedName.get(NAMESPACE, "exclusive");

		/**
		 * {@link QualifiedName} of the shared lock scope.
		 */
		public final static QualifiedName SHARED = QualifiedName.get(NAMESPACE, "shared");


		/**
		 * No instances allowed.
		 */
		private LockScopes()
		{
		}
	}

	/**
	 * DAV Properties defined in http://tools.ietf.org/html/rfc4918#section-15
	 */
	public final static class Properties
	{
		// TODO: add creationdate property

		/**
		 * displayname property as defined in <a href="http://tools.ietf.org/html/rfc4918#section-15.2">RFC 4918 Section 15.2</a>
		 */
		public final static ElementDescriptor<String> DISPLAYNAME = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "displayname"),
			StringObjectBuilder.INSTANCE);

		/**
		 * getcontentlanguage property as defined in <a href="http://tools.ietf.org/html/rfc4918#section-15.3">RFC 4918 Section 15.3</a>
		 */
		public final static ElementDescriptor<String> GETCONTENTLANGUAGE = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "getcontentlanguage"),
			StringObjectBuilder.INSTANCE);

		/**
		 * getcontentlength property as defined in <a href="http://tools.ietf.org/html/rfc4918#section-15.4">RFC 4918 Section 15.4</a>
		 */
		public final static ElementDescriptor<Integer> GETCONTENTLENGTH = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "getcontentlength"),
			IntegerObjectBuilder.INSTANCE);

		/**
		 * getcontenttype property as defined in <a href="http://tools.ietf.org/html/rfc4918#section-15.5">RFC 4918 Section 15.5</a>
		 */
		public final static ElementDescriptor<ContentType> GETCONTENTTYPE = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "getcontenttype"),
			new AbstractObjectBuilder<ContentType>()
			{
				public ContentType update(ElementDescriptor<ContentType> descriptor, ContentType object, String text, ParserContext context)
					throws XmlObjectPullParserException
				{
					return new ContentType(text);
				}


				public void writeChildren(ElementDescriptor<ContentType> descriptor, ContentType object, IXmlChildWriter childWriter, SerializerContext context)
					throws SerializerException, IOException
				{
					if (object != null)
					{
						childWriter.writeText(object.toString(), context);
					}
				};
			});

		/**
		 * getetag property as defined in <a href="http://tools.ietf.org/html/rfc4918#section-15.6">RFC 4918 Section 15.6</a>
		 */
		public final static ElementDescriptor<String> GETETAG = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "getetag"),
			StringObjectBuilder.INSTANCE);

		// TODO: add getlastmodified property

		// TODO: add lockdiscovery property

		/**
		 * resourcetype property as defined in <a href="http://tools.ietf.org/html/rfc4918#section-15.9">RFC 4918 Section 15.9</a>
		 */
		public final static ElementDescriptor<Set<QualifiedName>> RESOURCETYPE = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "resourcetype"),
			new SetObjectBuilder<QualifiedName>(QualifiedNameObjectBuilder.INSTANCE));


		// TODO: add supportedlock property

		/**
		 * No instances allowed.
		 */
		private Properties()
		{
		}
	}

	/**
	 * Preconditon elements as defined in <a href="http://tools.ietf.org/html/rfc4918#section-16">RFC 4918 Section 16</a>
	 */
	public static class PreConditions
	{
		/**
		 * <pre>
		 * Name:  lock-token-matches-request-uri
		 * 
		 * Use with:  409 Conflict
		 * 
		 * Purpose:  (precondition) -- A request may include a Lock-Token header
		 *    to identify a lock for the UNLOCK method.  However, if the
		 *    Request-URI does not fall within the scope of the lock identified
		 *    by the token, the server SHOULD use this error.  The lock may have
		 *    a scope that does not include the Request-URI, or the lock could
		 *    have disappeared, or the token may be invalid.
		 * </pre>
		 * 
		 * @see <a href="http://tools.ietf.org/html/rfc4918#section-16">RFC 4918 Section 16</a>
		 */
		public final static ElementDescriptor<QualifiedName> LOCK_TOKEN_MATCHES_REQUEST_URI = ElementDescriptor.register(
			QualifiedName.get(NAMESPACE, "lock-token-matches-request-uri"), QualifiedNameObjectBuilder.INSTANCE);

		/**
		 * <pre>
		 * Name:  lock-token-submitted
		 * 
		 * Use with:  423 Locked
		 * 
		 * Purpose:  The request could not succeed because a lock token should
		 *    have been submitted.  This element, if present, MUST contain at
		 *    least one URL of a locked resource that prevented the request.  In
		 *    cases of MOVE, COPY, and DELETE where collection locks are
		 *    involved, it can be difficult for the client to find out which
		 *    locked resource made the request fail -- but the server is only
		 *    responsible for returning one such locked resource.  The server
		 *    MAY return every locked resource that prevented the request from
		 *    succeeding if it knows them all.
		 * </pre>
		 * 
		 * @see <a href="http://tools.ietf.org/html/rfc4918#section-16">RFC 4918 Section 16</a>
		 */
		public final static ElementDescriptor<List<URI>> LOCK_TOKEN_SUBMITTED = ElementDescriptor.register(
			QualifiedName.get(NAMESPACE, "lock-token-submitted"), new ListObjectBuilder<URI>(HREF));

		/**
		 * <pre>
		 * Name:  no-conflicting-lock
		 * 
		 * Use with:  Typically 423 Locked
		 * 
		 * Purpose:  A LOCK request failed due the presence of an already
		 *    existing conflicting lock.  Note that a lock can be in conflict
		 *    although the resource to which the request was directed is only
		 *    indirectly locked.  In this case, the precondition code can be
		 *    used to inform the client about the resource that is the root of
		 *    the conflicting lock, avoiding a separate lookup of the
		 *    "lockdiscovery" property.
		 * </pre>
		 * 
		 * @see <a href="http://tools.ietf.org/html/rfc4918#section-16">RFC 4918 Section 16</a>
		 */
		public final static ElementDescriptor<List<URI>> NO_CONFLICTING_LOCK = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "no-conflicting-lock "),
			new ListObjectBuilder<URI>(HREF));

		/**
		 * <pre>
		 * Name:  no-external-entities
		 * 
		 * Use with:  403 Forbidden
		 * 
		 * Purpose:  (precondition) -- If the server rejects a client request
		 *    because the request body contains an external entity, the server
		 *    SHOULD use this error.
		 * </pre>
		 * 
		 * @see <a href="http://tools.ietf.org/html/rfc4918#section-16">RFC 4918 Section 16</a>
		 */
		public final static ElementDescriptor<QualifiedName> NO_EXTERNAL_ENTITIES = ElementDescriptor.register(
			QualifiedName.get(NAMESPACE, "no-external-entities"), QualifiedNameObjectBuilder.INSTANCE);

		/**
		 * <pre>
		 * Name:  propfind-finite-depth
		 * 
		 * Use with:  403 Forbidden
		 * 
		 * Purpose:  (precondition) -- This server does not allow infinite-depth
		 *    PROPFIND requests on collections.
		 * </pre>
		 * 
		 * @see <a href="http://tools.ietf.org/html/rfc4918#section-16">RFC 4918 Section 16</a>
		 */
		public final static ElementDescriptor<QualifiedName> PROPFIND_FINITE_DEPTH = ElementDescriptor.register(
			QualifiedName.get(NAMESPACE, "propfind-finite-depth"), QualifiedNameObjectBuilder.INSTANCE);

		/**
		 * <pre>
		 * Name:  cannot-modify-protected-property
		 * 
		 * Use with:  403 Forbidden
		 * 
		 * Purpose:  (precondition) -- The client attempted to set a protected
		 *    property in a PROPPATCH (such as DAV:getetag).  See also
		 *    [RFC3253], Section 3.12.
		 * </pre>
		 * 
		 * @see <a href="http://tools.ietf.org/html/rfc4918#section-16">RFC 4918, Section 16</a>
		 * @see <a href="http://tools.ietf.org/html/rfc3253#section-3.12">RFC 3253, Section 3.12</a>
		 */
		public final static ElementDescriptor<QualifiedName> CANNOT_MODIFY_PROTECTED_PROPERTY = ElementDescriptor.register(
			QualifiedName.get(NAMESPACE, "cannot-modify-protected-property"), QualifiedNameObjectBuilder.INSTANCE);


		/**
		 * No instances allowed.
		 */
		private PreConditions()
		{
		}
	}

	/**
	 * Postconditon elements as defined in http://tools.ietf.org/html/rfc4918#section-16
	 */
	public static class PostConditions
	{
		/**
		 * <pre>
		 * Name:  preserved-live-properties
		 * 
		 * Use with:  409 Conflict
		 * 
		 * Purpose:  (postcondition) -- The server received an otherwise-valid
		 *    MOVE or COPY request, but cannot maintain the live properties with
		 *    the same behavior at the destination.  It may be that the server
		 *    only supports some live properties in some parts of the
		 *    repository, or simply has an internal error.
		 * </pre>
		 * 
		 * @see <a href="http://tools.ietf.org/html/rfc4918#section-16">RFC 4918 Section 16</a>
		 */
		public final static ElementDescriptor<QualifiedName> PRESERVED_LIVE_PROPERTIES = ElementDescriptor.register(
			QualifiedName.get(NAMESPACE, "preserved-live-properties"), QualifiedNameObjectBuilder.INSTANCE);


		/**
		 * No instances allowed.
		 */
		private PostConditions()
		{
		}
	}


	/**
	 * No instances allowed.
	 */
	private WebDav()
	{
	}
}
