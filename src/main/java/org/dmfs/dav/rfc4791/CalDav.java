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

import org.dmfs.dav.rfc3253.WebDavVersioning;
import org.dmfs.dav.rfc4791.filter.CompFilter;
import org.dmfs.dav.rfc4918.WebDav;
import org.dmfs.httpessentials.HttpMethod;
import org.dmfs.httpessentials.methods.Method;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.*;

import java.net.URI;
import java.util.Set;


/**
 * Names and properties defined in <a href="http://tools.ietf.org/html/rfc4791">RFC 4791</a>.
 */
public class CalDav
{
    /**
     * CalDAV namespace.
     */
    public final static String NAMESPACE = "urn:ietf:params:xml:ns:caldav";

    public final static HttpMethod MKCALENDAR = new Method("MKCALENDAR", true);

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
     * Definition of calendar-query. This definition is only valid in the context of a {@link WebDavVersioning#REPORT} element.
     */
    final static ElementDescriptor<QualifiedName> REPORT_TYPE_CALENDAR_QUERY = ElementDescriptor.registerWithParents(ReportTypes.CALENDAR_QUERY,
        QualifiedNameObjectBuilder.INSTANCE, WebDavVersioning.REPORT);

    /**
     * Definition of the calendar-query report.
     */
    public final static ElementDescriptor<CalendarQuery> CALENDAR_QUERY = ElementDescriptor.register(ReportTypes.CALENDAR_QUERY, CalendarQuery.BUILDER);

    /**
     * Definition of calendar-multiget. This definition is only valid in the context of a {@link WebDavVersioning#REPORT} element.
     */
    final static ElementDescriptor<QualifiedName> REPORT_TYPE_CALENDAR_MULTIGET = ElementDescriptor.registerWithParents(ReportTypes.CALENDAR_MULTIGET,
        QualifiedNameObjectBuilder.INSTANCE, WebDavVersioning.REPORT);

    /**
     * Definition of the calendar-multiget report.
     */
    public final static ElementDescriptor<CalendarMultiget> CALENDAR_MULTIGET = ElementDescriptor.register(ReportTypes.CALENDAR_MULTIGET,
        CalendarMultiget.BUILDER);

    public final static ElementDescriptor<CompFilter> FILTER = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "filter"),
        new TransientObjectBuilder<CompFilter>(CompFilter.DESCRIPTOR));


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
     * calendar element as defined in <a href="http://tools.ietf.org/html/rfc4791#section-9.1">RFC 4791, section 9.1</a>.
     */
    public final static ElementDescriptor<QualifiedName> RESOURCE_TYPE_CALENDAR = ElementDescriptor.register(ResourceTypes.CALENDAR,
        QualifiedNameObjectBuilder.INSTANCE);


    public final static class ResourceTypes
    {
        /**
         * {@link QualifiedName} of the calendar element.
         */
        public final static QualifiedName CALENDAR = QualifiedName.get(NAMESPACE, "calendar");

    }


    public final static ElementDescriptor<String> PROPERTY_CALENDAR_DESCRIPTION = ElementDescriptor.register(
        QualifiedName.get(NAMESPACE, "calendar-description"), StringObjectBuilder.INSTANCE);

    public final static ElementDescriptor<String> PROPERTY_CALENDAR_TIMEZONE = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "calendar-timezone"),
        StringObjectBuilder.INSTANCE);

    public final static ElementDescriptor<Set<String>> PROPERTY_SUPPORTED_CALENDAR_COMPONENT_SET = ElementDescriptor.register(
        QualifiedName.get(NAMESPACE, "supported-calendar-component-set"), new SetObjectBuilder<String>(COMP, false /* don't store null values */));

    public final static ElementDescriptor<CalendarData> PROPERTY_CALENDAR_DATA = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "calendar-data"),
        CalendarData.BUILDER);

    public final static ElementDescriptor<Set<CalendarData>> PROPERTY_SUPPORTED_CALENDAR_DATA = ElementDescriptor.register(
        QualifiedName.get(NAMESPACE, "supported-calendar-data"),
        new SetObjectBuilder<CalendarData>(PROPERTY_CALENDAR_DATA, false /* don't store null values */));

    public final static ElementDescriptor<Integer> PROPERTY_MAX_RESOURCE_SIZE = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "max-resource-size"),
        IntegerObjectBuilder.INSTANCE_STRICT);

    // public final static ElementDescriptor<DateTime> PROPERTY_MIN_DATE_TIME = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "min-date-time"),
    // DateTimeObjectBuilder.INSTANCE);

    // public final static ElementDescriptor<DateTime> PROPERTY_MAX_DATE_TIME = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "max-date-time"),
    // DateTimeObjectBuilder.INSTANCE);

    public final static ElementDescriptor<Integer> PROPERTY_MAX_INSTANCES = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "max-instances"),
        IntegerObjectBuilder.INSTANCE_STRICT);

    public final static ElementDescriptor<Integer> PROPERTY_MAX_ATTENDEES_PER_INSTANCE = ElementDescriptor.register(
        QualifiedName.get(NAMESPACE, "max-attendees-per-instance"), IntegerObjectBuilder.INSTANCE_STRICT);

    public final static ElementDescriptor<Set<URI>> PROPERTY_CALENDAR_HOME_SET = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "calendar-home-set"),
        new SetObjectBuilder<URI>(WebDav.HREF));


    /**
     * Properties defined in <a href="http://tools.ietf.org/html/rfc4791#section-5.2">RFC 4791, Section 5.2</a> and <a
     * href="http://tools.ietf.org/html/rfc4791#section-6.2">RFC 4791, Section 6.2</a>
     */
    public final static class Properties
    {
        public final static ElementDescriptor<String> CALENDAR_DESCRIPTION = CalDav.PROPERTY_CALENDAR_DESCRIPTION;

        public final static ElementDescriptor<String> CALENDAR_TIMEZONE = CalDav.PROPERTY_CALENDAR_TIMEZONE;

        public final static ElementDescriptor<Set<String>> SUPPORTED_CALENDAR_COMPONENT_SET = CalDav.PROPERTY_SUPPORTED_CALENDAR_COMPONENT_SET;

        public final static ElementDescriptor<CalendarData> CALENDAR_DATA = CalDav.PROPERTY_CALENDAR_DATA;

        public final static ElementDescriptor<Set<CalendarData>> SUPPORTED_CALENDAR_DATA = CalDav.PROPERTY_SUPPORTED_CALENDAR_DATA;

        public final static ElementDescriptor<Integer> MAX_RESOURCE_SIZE = CalDav.PROPERTY_MAX_RESOURCE_SIZE;

        // public final static ElementDescriptor<DateTime> MIN_DATE_TIME = CalDav.PROPERTY_MIN_DATE_TIME;

        // public final static ElementDescriptor<DateTime> MAX_DATE_TIME = CalDav.PROPERTY_MAX_DATE_TIME;

        public final static ElementDescriptor<Integer> MAX_INSTANCES = CalDav.PROPERTY_MAX_INSTANCES;

        public final static ElementDescriptor<Integer> MAX_ATTENDEES_PER_INSTANCE = CalDav.PROPERTY_MAX_ATTENDEES_PER_INSTANCE;

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
         *     &lt;!ELEMENT calendar-home-set (DAV:href*)&gt;
         *
         * Example:
         *
         *     &lt;C:calendar-home-set xmlns:D="DAV:"
         *                            xmlns:C="urn:ietf:params:xml:ns:caldav"&gt;
         *        &lt;D:href&gt;http://cal.example.com/home/bernard/calendars/&lt;/D:href&gt;
         *     &lt;/C:calendar-home-set&gt;
         * </pre>
         */
        public final static ElementDescriptor<Set<URI>> CALENDAR_HOME_SET = CalDav.PROPERTY_CALENDAR_HOME_SET;


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
