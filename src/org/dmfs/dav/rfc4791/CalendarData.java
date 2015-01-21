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

import org.dmfs.httpclientinterfaces.ContentType;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.Recyclable;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;


/**
 * Represents a <code>calendar-data</code> element as defined in <a href="http://tools.ietf.org/html/rfc4791#section-9.6">RFC 4791, section 9.6</a>.
 * <p>
 * It serves three purposes:
 * </p>
 * 
 * <pre>
 *       1.  A supported media type for calendar object resources when
 *           nested in the CALDAV:supported-calendar-data property;
 * 
 *       2.  The parts of a calendar object resource should be returned by
 *           a calendaring report;
 * 
 *       3.  The content of a calendar object resource in a response to a
 *           calendaring report.
 * </pre>
 * <p>
 * TODO: add serializer
 * </p>
 * <p>
 * TODO: add support for comp, expand, limit-recurrence-set and limit-free-busy-set elements
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class CalendarData implements Recyclable
{
	/**
	 * Attribute name of the calendar-data content-type.
	 */
	private final static QualifiedName CONTENT_TYPE = QualifiedName.get("content-type");

	/**
	 * Attribute name of the calendar-data version.
	 */
	private final static QualifiedName VERSION = QualifiedName.get("version");

	/**
	 * That's the most likely content-type, so we keep a static instance that we can return.
	 * <p>
	 * TODO: what about parameters like <code>charset</code> or <code>component</code>?
	 * </p>
	 */
	private final static ContentType TEXT_CALENDAR = new ContentType("text/calendar");

	/**
	 * An {@link IXmlObjectBuilder} for {@link CalendarData} objects.
	 */
	public final static AbstractObjectBuilder<CalendarData> BUILDER = new AbstractObjectBuilder<CalendarData>()
	{

		@Override
		public CalendarData get(ElementDescriptor<CalendarData> descriptor, CalendarData recycle, ParserContext context) throws XmlObjectPullParserException
		{
			if (recycle != null)
			{
				recycle.recycle();
				return recycle;
			}
			return new CalendarData();
		};


		@Override
		public CalendarData update(ElementDescriptor<CalendarData> descriptor, CalendarData object, QualifiedName attribute, String value, ParserContext context)
			throws XmlObjectPullParserException
		{
			if (attribute == CONTENT_TYPE)
			{
				if (TEXT_CALENDAR.type.equals(value))
				{
					// avoid creating a new instance if possible
					object.mContentType = TEXT_CALENDAR;
				}
				else
				{
					object.mContentType = new ContentType(value);
				}
			}
			else if (attribute == VERSION)
			{
				object.mVersion = value;
			}
			return object;
		};


		@Override
		public CalendarData update(ElementDescriptor<CalendarData> descriptor, CalendarData object, String text, ParserContext context)
			throws XmlObjectPullParserException
		{
			object.mCalendarData = text;
			return object;
		};
	};

	private ContentType mContentType;
	private String mVersion;
	private String mCalendarData;


	@Override
	public void recycle()
	{
		mContentType = null;
		mVersion = null;
		mCalendarData = null;
	}


	/**
	 * Returns the content type as returned by the server.
	 * 
	 * @return The {@link ContentType}.
	 */
	public ContentType getContentType()
	{
		return mContentType;
	}


	/**
	 * Return the version of the calendar data format as returned by the server.
	 * 
	 * @return A String containing the version, if available.
	 */
	public String getVersion()
	{
		return mVersion;
	}


	/**
	 * Returns the actual calendar data if the server returned any.
	 * 
	 * @return The calendar data or <code>null</code>.
	 */
	public String getCalendarData()
	{
		return mCalendarData;
	}
}
