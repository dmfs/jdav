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

package org.dmfs.dav.rfc6578;

import java.net.URI;

import org.dmfs.dav.rfc3253.WebDavVersioning;
import org.dmfs.dav.rfc4918.WebDav;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.QualifiedNameObjectBuilder;
import org.dmfs.xmlobjects.builder.StringObjectBuilder;


/**
 * Names and definitions from <a href="http://tools.ietf.org/html/rfc6578">RFC 6578</a>.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class WebDavSync
{
	/**
	 * WebDAV Syncronization uses the {@link WebDav#NAMESPACE} namespace.
	 */
	public final static String NAMESPACE = WebDav.NAMESPACE;

	/**
	 * Definition of <code>sync-collection</code> in the default (response) context. See <a href="http://tools.ietf.org/html/rfc6578#section-6.1">RFC 6578,
	 * section 6.1</a>. In a response context this is identified by it's {@link QualifiedName}.
	 */
	public final static ElementDescriptor<QualifiedName> REPORT_SYNC_COLLECTION = ElementDescriptor.register(ReportTypes.SYNC_COLLECTION,
		QualifiedNameObjectBuilder.INSTANCE);

	/**
	 * Definition of the <code>sync-collection</code> report. See <a href="http://tools.ietf.org/html/rfc6578#section-6.1">RFC 6578, section 6.1</a>.
	 * <p>
	 * <strong>Note:</strong> to avoid conflicts with {@link #REPORT_SYNC_COLLECTION} this is defined in the {@link WebDav#REQUEST_CONTEXT}.
	 * </p>
	 * 
	 * @see SyncCollection
	 */
	public final static ElementDescriptor<SyncCollection> SYNC_COLLECTION_REPORT = ElementDescriptor.register(ReportTypes.SYNC_COLLECTION,
		SyncCollection.BUILDER, WebDav.REQUEST_CONTEXT);

	/**
	 * sync-token member of multistatus as defined in <a href="http://tools.ietf.org/html/rfc6578#section-6.2">RFC 6578, section 6.2</a>. It equals the
	 * {@link Properties#SYNC_TOKEN} property.
	 */
	public final static ElementDescriptor<String> SYNC_TOKEN = Properties.SYNC_TOKEN;

	/**
	 * sync-level is defined in <a href="http://tools.ietf.org/html/rfc6578#section-6.3">RFC 6578, section 6.3</a>.
	 */
	public final static ElementDescriptor<SyncLevel> SYNC_LEVEL = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "sync-level"), SyncLevel.BUILDER);

	/**
	 * Report types defined in <a href="http://tools.ietf.org/html/rfc6578">RFC 6578</a>.
	 */
	public static class ReportTypes
	{
		/**
		 * The {@link QualifiedName} of the report type as reported in a {@link WebDavVersioning#PROP_SUPPORTED_REPORT_SET}.
		 */
		private final static QualifiedName SYNC_COLLECTION = QualifiedName.get(NAMESPACE, "sync-collection");
	}

	/**
	 * New properties types defined in <a href="http://tools.ietf.org/html/rfc6578">RFC 6578</a>.
	 */
	public final static class Properties
	{
		/**
		 * sync-token property as defined in <a href="http://tools.ietf.org/html/rfc6578#section-6.2">RFC 6578, section 6.2</a>.
		 * <p>
		 * <strong>Note:</strong> RFC 6578 defines the sync-token to be a URI. This field diverges from the original definition and returns {@link String}s
		 * instead of {@link URI}s. In practise that won't make any difference to the client (because for the client it's just an opaque token) but it will save
		 * some resources. Servers must ensure this string represents a valid URI.
		 * </p>
		 */
		public final static ElementDescriptor<String> SYNC_TOKEN = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "sync-token"),
			StringObjectBuilder.INSTANCE);


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
	private WebDavSync()
	{
	}
}
