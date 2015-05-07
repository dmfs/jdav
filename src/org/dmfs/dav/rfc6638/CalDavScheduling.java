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

package org.dmfs.dav.rfc6638;

import java.net.URI;
import java.util.Set;

import org.dmfs.dav.rfc4791.CalDav;
import org.dmfs.dav.rfc4918.WebDav;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.SetObjectBuilder;


/**
 * Names and definitions from <a href="http://tools.ietf.org/html/rfc6638">Scheduling Extensions to CalDAV, RFC 6638</a>.
 * <p>
 * TODO: add all the missing elements and properties.
 * </p>
 * <p>
 * TODO: add missing javadoc.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class CalDavScheduling
{
	/**
	 * CalDAV Scheduling uses the {@link CalDav#NAMESPACE} namespace.
	 */
	public final static String NAMESPACE = CalDav.NAMESPACE;

	/* --------------------------------------------- Property elements --------------------------------------------- */

	final static ElementDescriptor<Set<URI>> PROPERTY_CALENDAR_USER_ADDRESS_SET = ElementDescriptor.register(
		QualifiedName.get(NAMESPACE, "calendar-user-address-set"), new SetObjectBuilder<URI>(WebDav.HREF, 8));

	/**
	 * Properties defined in <a href="https://tools.ietf.org/html/rfc6638#section-2.4">RFC 6638, section 2.4</a>.
	 */
	public final static class Properties
	{

		/**
		 * calendar-user-address-set as defined in <a href="https://tools.ietf.org/html/rfc6638#section-2.4.1">RFC 6683, section 2.4.1</a>.
		 * 
		 * <pre>
		 * Name:  calendar-user-address-set
		 * 
		 * Namespace:  urn:ietf:params:xml:ns:caldav
		 * 
		 * Purpose:  Identify the calendar addresses of the associated principal
		 *    resource.
		 * 
		 * Protected:  This property MAY be protected.
		 * 
		 * PROPFIND behavior:  This property SHOULD NOT be returned by a
		 *    PROPFIND DAV:allprop request (as defined in Section 14.2 of
		 *    <a href="https://tools.ietf.org/html/rfc4918#section-14.2">RFC 4918, section 14.2</a>).
		 * 
		 * COPY/MOVE behavior:  This property value SHOULD be preserved in COPY
		 *    and MOVE operations.
		 * 
		 * Description:  Support for this property is REQUIRED.  This property
		 *    is needed to map calendar user addresses in iCalendar data to
		 *    principal resources and their associated scheduling Inbox and
		 *    Outbox collections.  In the event that a user has no well-defined
		 *    identifier for his calendar user address, the URI of his principal
		 *    resource can be used.  This property SHOULD be searchable using
		 *    the DAV:principal-property-search REPORT.  The DAV:principal-
		 *    search-property-set REPORT SHOULD identify this property as such.
		 *    If not present, then the associated calendar user is not enabled
		 *    for scheduling on the server.
		 * 
		 * Definition:
		 * 
		 *   <!ELEMENT calendar-user-address-set (DAV:href*)>
		 * </pre>
		 */
		public final static ElementDescriptor<Set<URI>> CALENDAR_USER_ADDRESS_SET = CalDavScheduling.PROPERTY_CALENDAR_USER_ADDRESS_SET;


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
	private CalDavScheduling()
	{
	}
}
