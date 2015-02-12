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

package org.dmfs.dav.rfc4791;

import java.net.URI;
import java.util.Set;

import org.dmfs.dav.rfc4791.filter.CompFilter;
import org.dmfs.dav.rfc4918.WebDav;
import org.dmfs.httpclientinterfaces.HttpMethod;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.IntegerObjectBuilder;
import org.dmfs.xmlobjects.builder.QualifiedNameObjectBuilder;
import org.dmfs.xmlobjects.builder.SetObjectBuilder;
import org.dmfs.xmlobjects.builder.StringAttributeObjectBuilder;
import org.dmfs.xmlobjects.builder.StringObjectBuilder;
import org.dmfs.xmlobjects.builder.TransientObjectBuilder;


/**
 * Names and properties defined in <a href="http://tools.ietf.org/html/rfc4791">RFC 4791</a>.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class CalDav
{
	/**
	 * CalDAV namespace.
	 */
	public final static String NAMESPACE = "urn:ietf:params:xml:ns:caldav";

	public final static HttpMethod MKCALENDAR = HttpMethod.method("MKCALENDAR");

	/**
	 * calendar element as defined in <a href="http://tools.ietf.org/html/rfc4791#section-9.1">RFC 4791, section 9.1</a>.
	 */
	public final static ElementDescriptor<QualifiedName> RESOURCE_CALENDAR = ElementDescriptor.register(ResourceTypes.CALENDAR,
		QualifiedNameObjectBuilder.INSTANCE);

	/**
	 * The prop element is used to list the iCalendar properties to return in a response, see <a href="http://tools.ietf.org/html/rfc4791#section-9.6.4">RFC
	 * 4791, section 9.6.4</a>. Since it's never returned in responses it has no builder.
	 */
	public final static ElementDescriptor<String> PROP = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "prop"), null);

	/**
	 * The comp element is used to list the iCalendar components to return in a response, see <a href="http://tools.ietf.org/html/rfc4791#section-9.6.1">RFC
	 * 4791, section 9.6.1</a>.
	 */
	public final static ElementDescriptor<String> COMP = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "comp"), new StringAttributeObjectBuilder(
		QualifiedName.get("name")));

	/**
	 * Definition of MkCalendar.
	 */
	public final static ElementDescriptor<MkCalendar> MK_CALENDAR = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "mkcalendar"), MkCalendar.BUILDER);

	/**
	 * Definition of calendar-query in the default (response) context.
	 */
	public final static ElementDescriptor<QualifiedName> CALENDAR_QUERY = ElementDescriptor.register(ReportTypes.CALENDAR_QUERY,
		QualifiedNameObjectBuilder.INSTANCE);

	/**
	 * Definition of the calendar-query report.
	 * <p>
	 * <strong>Note:</strong> to avoid conflicts with {@link #CALENDAR_QUERY} this is defined in the {@value WebDav#REQUEST_CONTEXT}-
	 * </p>
	 */
	public final static ElementDescriptor<CalendarQuery> CALENDAR_QUERY_REPORT = ElementDescriptor.register(ReportTypes.CALENDAR_QUERY, CalendarQuery.BUILDER,
		WebDav.REQUEST_CONTEXT);

	/**
	 * Definition of calendar-multiget in the default (response) context.
	 */
	public final static ElementDescriptor<QualifiedName> CALENDAR_MULTIGET = ElementDescriptor.register(ReportTypes.CALENDAR_MULTIGET,
		QualifiedNameObjectBuilder.INSTANCE);

	/**
	 * Definition of the calendar-multiget report.
	 * <p>
	 * <strong>Note:</strong> to avoid conflicts with {@link #CALENDAR_MULTIGET} this is defined in the {@value WebDav#REQUEST_CONTEXT}-
	 * </p>
	 */
	public final static ElementDescriptor<CalendarMultiget> CALENDAR_MULTIGET_REPORT = ElementDescriptor.register(ReportTypes.CALENDAR_MULTIGET,
		CalendarMultiget.BUILDER, WebDav.REQUEST_CONTEXT);

	public final static ElementDescriptor<CompFilter> FILTER = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "filter"),
		new TransientObjectBuilder<CompFilter>(CompFilter.DESCRIPTOR));

	public final static class ResourceTypes
	{
		/**
		 * {@link QualifiedName} of the calendar element.
		 */
		public final static QualifiedName CALENDAR = QualifiedName.get(NAMESPACE, "calendar");

	}

	/**
	 * Report types defined in <a href="http://tools.ietf.org/html/rfc4791#section-7">RFC 4791, Section 7</a>
	 */
	public final static class ReportTypes
	{
		public final static QualifiedName CALENDAR_QUERY = QualifiedName.get(NAMESPACE, "calendar-query");

		/**
		 * {@link QualifiedName} of the calendar-multiget element.
		 */
		public final static QualifiedName CALENDAR_MULTIGET = QualifiedName.get(NAMESPACE, "calendar-multiget");

		/**
		 * {@link QualifiedName} of the free-busy-query element.
		 */
		public final static QualifiedName FREE_BUSY_QUERY = QualifiedName.get(NAMESPACE, "free-busy-query");
	}

	/**
	 * Properties defined in <a href="http://tools.ietf.org/html/rfc4791#section-5.2">RFC 4791, Section 5.2</a> and <a
	 * href="http://tools.ietf.org/html/rfc4791#section-6.2">RFC 4791, Section 6.2</a>
	 */
	public final static class Properties
	{
		public final static ElementDescriptor<String> CALENDAR_DESCRIPTION = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "calendar-description"),
			StringObjectBuilder.INSTANCE);

		public final static ElementDescriptor<String> CALENDAR_TIMEZONE = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "calendar-timezone"),
			StringObjectBuilder.INSTANCE);

		public final static ElementDescriptor<Set<String>> SUPPORTED_CALENDAR_COMPONENT_SET = ElementDescriptor.register(
			QualifiedName.get(NAMESPACE, "supported-calendar-component-set"), new SetObjectBuilder<String>(COMP));

		public final static ElementDescriptor<CalendarData> CALENDAR_DATA = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "calendar-data"),
			CalendarData.BUILDER);

		public final static ElementDescriptor<Set<CalendarData>> SUPPORTED_CALENDAR_DATA = ElementDescriptor.register(
			QualifiedName.get(NAMESPACE, "supported-calendar-data"), new SetObjectBuilder<CalendarData>(CALENDAR_DATA));

		public final static ElementDescriptor<Integer> MAX_RESOURCE_SIZE = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "max-resource-size"),
			IntegerObjectBuilder.INSTANCE_STRICT);

		// public final static ElementDescriptor<DateTime> MIN_DATE_TIME = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "min-date-time"),
		// DateTimeObjectBuilder.INSTANCE);

		// public final static ElementDescriptor<DateTime> MAX_DATE_TIME = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "max-date-time"),
		// DateTimeObjectBuilder.INSTANCE);

		public final static ElementDescriptor<Integer> MAX_INSTANCES = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "max-instances"),
			IntegerObjectBuilder.INSTANCE_STRICT);

		public final static ElementDescriptor<Integer> MAX_ATTENDEES_PER_INSTANCE = ElementDescriptor.register(
			QualifiedName.get(NAMESPACE, "max-attendees-per-instance"), IntegerObjectBuilder.INSTANCE_STRICT);

		/**
		 * calendar-home-set is defined in <a href="http://tools.ietf.org/html/rfc4791#section-6.2.1">RFC 4791, section 6.2.1</a>. It's a principal property and
		 * may not be defined on other resources.
		 * <p>
		 * This property is represented by a {@link Set} of {@link URI}s.
		 * </p>
		 * 
		 * <pre>
		 * Name:  calendar-home-set
		 * 
		 * Namespace:  urn:ietf:params:xml:ns:caldav
		 * 
		 * Purpose:  Identifies the URL of any WebDAV collections that contain
		 *    calendar collections owned by the associated principal resource.
		 * 
		 * Conformance:  This property SHOULD be defined on a principal
		 *    resource.  If defined, it MAY be protected and SHOULD NOT be
		 *    returned by a PROPFIND DAV:allprop request (as defined in Section
		 *    12.14.1 of [RFC2518]).
		 * 
		 * Description:  The CALDAV:calendar-home-set property is meant to allow
		 *     users to easily find the calendar collections owned by the
		 *     principal.  Typically, users will group all the calendar
		 *     collections that they own under a common collection.  This
		 *     property specifies the URL of collections that are either calendar
		 *     collections or ordinary collections that have child or descendant
		 *     calendar collections owned by the principal.
		 * 
		 * Definition:
		 * 
		 *     &lt;!ELEMENT calendar-home-set (DAV:href*)>
		 * 
		 * Example:
		 * 
		 *     &lt;C:calendar-home-set xmlns:D="DAV:"
		 *                            xmlns:C="urn:ietf:params:xml:ns:caldav">
		 *        &lt;D:href>http://cal.example.com/home/bernard/calendars/&lt;/D:href>
		 *     &lt;/C:calendar-home-set>
		 * </pre>
		 */
		public final static ElementDescriptor<Set<URI>> CALENDAR_HOME_SET = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "calendar-home-set"),
			new SetObjectBuilder<URI>(WebDav.HREF));


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
	private CalDav()
	{
	}
}
