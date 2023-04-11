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

import org.dmfs.dav.rfc3253.WebDavVersioning;
import org.dmfs.dav.rfc6352.filter.PropFilter;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.ListObjectBuilder;
import org.dmfs.xmlobjects.builder.QualifiedNameObjectBuilder;

import java.util.List;


/**
 * Contains names &amp; properties defined in <a href="http://tools.ietf.org/html/rfc6352">RFC 6352</a>
 * <p>
 * TODO: add missing properties and definitions
 * </p>
 */
public final class CardDav
{
    /**
     * The CardDAV namespace.
     */
    public final static String NAMESPACE = "urn:ietf:params:xml:ns:carddav";

    public final static ElementDescriptor<List<PropFilter>> FILTER = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "filter"),
        new ListObjectBuilder<PropFilter>(PropFilter.DESCRIPTOR, 8));

    /**
     * Definition of <code>addressbook-query</code>. This definition is only valid in the context of a {@link WebDavVersioning#REPORT} element.
     */
    final static ElementDescriptor<QualifiedName> REPORT_TYPE_ADDRESSBOOK_QUERY = ElementDescriptor.registerWithParents(ReportTypes.ADDRESSBOOK_QUERY,
        QualifiedNameObjectBuilder.INSTANCE, WebDavVersioning.REPORT);

    /**
     * Definition of the addressbook-query report.
     */
    public final static ElementDescriptor<AddressbookQuery> ADDRESSBOOK_QUERY = ElementDescriptor.register(ReportTypes.ADDRESSBOOK_QUERY,
        AddressbookQuery.BUILDER);

    /**
     * Definition of <code>addressbook-multiget</code>. This definition is only valid in the context of a {@link WebDavVersioning#REPORT} element.
     */
    final static ElementDescriptor<QualifiedName> REPORT_TYPE_ADDRESSBOOK_MULTIGET = ElementDescriptor.registerWithParents(ReportTypes.ADDRESSBOOK_MULTIGET,
        QualifiedNameObjectBuilder.INSTANCE, WebDavVersioning.REPORT);

    /**
     * Definition of the <code>addressbook-multiget</code> report.
     */
    public final static ElementDescriptor<AddressbookMultiget> ADDRESSBOOK_MULTIGET = ElementDescriptor.register(ReportTypes.ADDRESSBOOK_MULTIGET,
        AddressbookMultiget.BUILDER);


    /**
     * Report types defined in <a href="http://tools.ietf.org/html/rfc4791#section-7">RFC 4791, Section 7</a>
     */
    public final static class ReportTypes
    {

        /**
         * {@link QualifiedName} of the addressbook-query element.
         */
        public final static QualifiedName ADDRESSBOOK_QUERY = QualifiedName.get(NAMESPACE, "addressbook-query");

        /**
         * {@link QualifiedName} of the addressbook-multiget element.
         */
        public final static QualifiedName ADDRESSBOOK_MULTIGET = QualifiedName.get(NAMESPACE, "addressbook-multiget");

    }


    /**
     * addressbook element as defined in <a href="http://tools.ietf.org/html/rfc6352#section-10.1">RFC 6352, section 10.1</a>.
     */
    public final static ElementDescriptor<QualifiedName> RESOURCE_TYPE_ADDRESSBOOK = ElementDescriptor.register(ResourceTypes.ADDRESSBOOK,
        QualifiedNameObjectBuilder.INSTANCE);


    public final static class ResourceTypes
    {
        /**
         * {@link QualifiedName} of the addressbook element.
         */
        public final static QualifiedName ADDRESSBOOK = QualifiedName.get(NAMESPACE, "addressbook");

    }


    /**
     * No instances allowed.
     */
    private CardDav()
    {
    }
}
