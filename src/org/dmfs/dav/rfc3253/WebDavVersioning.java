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

import java.io.IOException;
import java.util.Set;

import org.dmfs.dav.rfc4918.WebDav;
import org.dmfs.dav.utils.MergeSetObjectBuilder;
import org.dmfs.httpclientinterfaces.HttpMethod;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.builder.QualifiedNameObjectBuilder;
import org.dmfs.xmlobjects.builder.SetObjectBuilder;
import org.dmfs.xmlobjects.builder.StringObjectBuilder;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer;


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
	 * {@link QualifiedName} of a name attribute.
	 */
	private final static QualifiedName ATTRIBUTE_NAME = QualifiedName.get("name");

	/**
	 * REPORT HTTP method.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc3253#section-3.6">RFC 3253, section 3.6</a>
	 */
	public final static HttpMethod METHOD_REPORT = HttpMethod.safeMethod("REPORT");

	/**
	 * <code>DAV:supported-method</code> as defined in <a href="https://tools.ietf.org/html/rfc3253#section-3.1.3">RFC 3253, section 3.1.3</a>
	 * <p/>
	 * Note: this will return <code>null</code> for unknown method names, i.e. if no {@link HttpMethod} has been created for that verb.
	 */
	public final static ElementDescriptor<HttpMethod> SUPPORTED_METHOD = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "supported-method"),
		new AbstractObjectBuilder<HttpMethod>()
		{
			@Override
			public HttpMethod update(ElementDescriptor<HttpMethod> descriptor, HttpMethod object, QualifiedName attribute, String value, ParserContext context)
				throws XmlObjectPullParserException
			{
				if (attribute == ATTRIBUTE_NAME)
				{
					return HttpMethod.get(value);
				}
				return null;
			};


			@Override
			public void writeAttributes(ElementDescriptor<HttpMethod> descriptor, HttpMethod object, XmlObjectSerializer.IXmlAttributeWriter attributeWriter,
				SerializerContext context) throws SerializerException, IOException
			{
				attributeWriter.writeAttribute(ATTRIBUTE_NAME, object.verb, context);
			};
		});

	/**
	 * <code>DAV:report</code> as defined in <a href="http://tools.ietf.org/html/rfc3253#section-3.1.5">RFC 3253, section 3.1.5</a>. It accepts any element and
	 * stores the {@link QualifiedName}.
	 * <p>
	 * <strong>Note:</strong> Some servers return all reports within one report element. According to the specs that's not allowed. To be compatible with these
	 * this element is modeled by a {@link Set} of {@link QualifiedName}s instead of a single {@link QualifiedName}. Elements that have this as child element
	 * must make sure they serialize all reports separately.
	 * </p>
	 * TODO: we should revert this eventually and switch back to a TransientObjectBuilder.
	 */
	public final static ElementDescriptor<Set<QualifiedName>> REPORT = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "report"),
		new SetObjectBuilder<QualifiedName>(QualifiedNameObjectBuilder.INSTANCE, false));

	/**
	 * <code>DAV:supported-report</code> as defined in <a href="http://tools.ietf.org/html/rfc3253#section-3.1.5">RFC 3253, section 3.1.5</a>
	 * <p>
	 * TODO: switch back to TransientObjectBuilder
	 * </p>
	 */
	public final static ElementDescriptor<Set<QualifiedName>> SUPPORTED_REPORT = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "supported-report"),
		new MergeSetObjectBuilder<QualifiedName>(REPORT));

	/* --------------------------------------------- Property elements --------------------------------------------- */

	final static ElementDescriptor<String> PROP_COMMENT = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "comment"), StringObjectBuilder.INSTANCE);

	final static ElementDescriptor<String> PROP_CREATOR_DISPLAYNAME = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "creator-displayname"),
		StringObjectBuilder.INSTANCE);

	final static ElementDescriptor<Set<HttpMethod>> PROP_SUPPORTED_METHOD_SET = ElementDescriptor.register(
		QualifiedName.get(NAMESPACE, "supported-method-set"), new SetObjectBuilder<>(SUPPORTED_METHOD, false));

	/*
	 * TODO: we use a MergeSetObjectBuilder to support broken servers that return all reports in one report element. We should revert this once all known
	 * servers have been fixed for some time.
	 */
	final static ElementDescriptor<Set<QualifiedName>> PROP_SUPPORTED_REPORT_SET = ElementDescriptor.register(
		QualifiedName.get(NAMESPACE, "supported-report-set"), new MergeSetObjectBuilder<QualifiedName>(SUPPORTED_REPORT));

	/**
	 * Properties defined in <a href="http://tools.ietf.org/html/rfc3253">RFC 3253</a>.
	 */
	public final static class Properties
	{

		/**
		 * <code>DAV:comment</code> as defined in <a href="http://tools.ietf.org/html/rfc3253#section-3.1.1">RFC 3253, section 3.1.1</a>
		 */
		public final static ElementDescriptor<String> COMMENT = WebDavVersioning.PROP_COMMENT;

		/**
		 * <code>DAV:creator-displayname</code> as defined in <a href="http://tools.ietf.org/html/rfc3253#section-3.1.2">RFC 3253, section 3.1.2</a>
		 */
		public final static ElementDescriptor<String> CREATOR_DISPLAYNAME = WebDavVersioning.PROP_CREATOR_DISPLAYNAME;

		/**
		 * <code>DAV:supported-method-set</code> as defined in <a href="http://tools.ietf.org/html/rfc3253#section-3.1.3">RFC 3253, section 3.1.3</a>
		 * <p/>
		 * Note: this will only contain {@link HttpMethod}s registered at the time a response as been parsed. Make sure all classes defining HttpMethods you're
		 * interested in have been loaded.
		 */
		public final static ElementDescriptor<Set<HttpMethod>> SUPPORTED_METHOD_SET = WebDavVersioning.PROP_SUPPORTED_METHOD_SET;

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
