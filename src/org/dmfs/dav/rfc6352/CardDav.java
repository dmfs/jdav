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

package org.dmfs.dav.rfc6352;

import java.util.List;

import org.dmfs.dav.rfc4918.WebDav;
import org.dmfs.dav.rfc6352.filter.PropFilter;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.ListObjectBuilder;
import org.dmfs.xmlobjects.builder.QualifiedNameObjectBuilder;


/**
 * Contains names & properties defined in <a href="http://tools.ietf.org/html/rfc6352">RFC 6352</a>
 * <p>
 * TODO: add missing properties and definitions
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class CardDav
{
	/**
	 * The CardDAV namespace.
	 */
	public final static String NAMESPACE = "urn:ietf:params:xml:ns:carddav";

	private final static QualifiedName QN_ADDRESSBOOK_QUERY = QualifiedName.get(NAMESPACE, "addressbook-query");

	/**
	 * Definition of addressbook-query in the default (response) context.
	 */
	public final static ElementDescriptor<QualifiedName> REPORT_ADDRESSBOOK_QUERY = ElementDescriptor.register(QN_ADDRESSBOOK_QUERY,
		QualifiedNameObjectBuilder.INSTANCE);

	/**
	 * Definition of the addressbook-query report.
	 * <p>
	 * <strong>Note:</strong> to avoid conflicts with {@link #REPORT_ADDRESSBOOK_QUERY} this is defined in the {@link WebDav#REQUEST_CONTEXT}.
	 * </p>
	 */
	public final static ElementDescriptor<AddressbookQuery> ADDRESSBOOK_QUERY_REPORT = ElementDescriptor.register(QN_ADDRESSBOOK_QUERY,
		AddressbookQuery.BUILDER, WebDav.REQUEST_CONTEXT);

	/**
	 * addressbook element as defined in <a href="http://tools.ietf.org/html/rfc6352#section-10.1">RFC 6352, section 10.1</a>.
	 */
	public final static ElementDescriptor<QualifiedName> RESOURCE_ADDRESSBOOK = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "addressbook"),
		QualifiedNameObjectBuilder.INSTANCE);

	/**
	 * {@link QualifiedName} of the addressbook-multiget element.
	 */
	private final static QualifiedName QN_ADDRESSBOOK_MULTIGET = QualifiedName.get(NAMESPACE, "addressbook-multiget");

	/**
	 * Definition of <code>addressbook-multiget</code> in the default (response) context.
	 */
	public final static ElementDescriptor<QualifiedName> REPORT_ADDRESSBOOK_MULTIGET = ElementDescriptor.register(QN_ADDRESSBOOK_MULTIGET,
		QualifiedNameObjectBuilder.INSTANCE);

	/**
	 * Definition of the <code>addressbook-multiget</code> report.
	 * <p>
	 * <strong>Note:</strong> to avoid conflicts with {@link #REPORT_ADDRESSBOOK_MULTIGET} this is defined in the {@value WebDav#REQUEST_CONTEXT}-
	 * </p>
	 */
	public final static ElementDescriptor<AddressbookMultiget> ADDRESSBOOK_MULTIGET_REPORT = ElementDescriptor.register(QN_ADDRESSBOOK_MULTIGET,
		AddressbookMultiget.BUILDER, WebDav.REQUEST_CONTEXT);

	public final static ElementDescriptor<List<PropFilter>> FILTER = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "filter"),
		new ListObjectBuilder<PropFilter>(PropFilter.DESCRIPTOR, 8));


	/**
	 * No instances allowed.
	 */
	private CardDav()
	{
	}
}
