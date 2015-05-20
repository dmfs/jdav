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

package org.dmfs.dav.rfc4791.filter;

import java.io.IOException;

import org.dmfs.dav.FilterBase;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.Weekday;
import org.dmfs.rfc5545.calendarmetrics.CalendarMetrics;
import org.dmfs.rfc5545.calendarmetrics.GregorianCalendarMetrics;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlAttributeWriter;


/**
 * A filter that limits the results to a certain range of time.
 * <p>
 * TODO: add server side support
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class TimeRange extends CalDavFilter
{
	private final static CalendarMetrics GREGORIAN_METRICS = new GregorianCalendarMetrics(Weekday.MO, 4);

	private final static QualifiedName ATTRIBUTE_START = QualifiedName.get("start");
	private final static QualifiedName ATTRIBUTE_END = QualifiedName.get("end");

	/**
	 * The {@link ElementDescriptor} of the {@link TimeRange} element.
	 */
	public final static ElementDescriptor<TimeRange> DESCRIPTOR = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "time-range"),
		new AbstractObjectBuilder<TimeRange>()
		{
			@Override
			public void writeAttributes(ElementDescriptor<TimeRange> descriptor, TimeRange object, IXmlAttributeWriter attributeWriter,
				SerializerContext context) throws SerializerException, IOException
			{
				if (object.start != null)
				{
					attributeWriter.writeAttribute(ATTRIBUTE_START, object.start.toString(), context);
				}
				if (object.end != null)
				{
					attributeWriter.writeAttribute(ATTRIBUTE_END, object.end.toString(), context);
				}
			};
		});

	/**
	 * The start value of this filter. May be <code>null</code> if no start value has been specified.
	 */
	private final DateTime start;

	/**
	 * The end value of this filter. May be <code>null</code> if no end value has been specified.
	 */
	private final DateTime end;


	/**
	 * Create a {@link TimeRange} filter for the given start and without a limit for the end value.
	 * 
	 * @param start
	 *            The time in milliseconds of the start of the range.
	 */
	public TimeRange(long start)
	{
		this(start, Long.MAX_VALUE);
	}


	/**
	 * Create a {@link TimeRange} filter for the given start and end date.
	 * 
	 * @param start
	 *            The time in milliseconds of the start of the range or {@link Long#MIN_VALUE} for an open range in the past.
	 * @param end
	 *            The time in milliseconds of the end of the range or {@link Long#MAX_VALUE} for an open range in the future.
	 */
	public TimeRange(long start, long end)
	{
		if (end == Long.MAX_VALUE && start == Long.MIN_VALUE)
		{
			throw new IllegalArgumentException("at least one of start or end must be given");
		}
		if (end <= start)
		{
			throw new IllegalArgumentException("start must be before end");
		}
		this.start = start == Long.MIN_VALUE ? null : new DateTime(start);
		this.end = end == Long.MAX_VALUE ? null : new DateTime(end);
	}


	/**
	 * Create a {@link TimeRange} filter for the given start and without a limit for the end value.
	 * 
	 * @param start
	 *            The {@link DateTime} of the start of the range or <code>null</code> for an open range in the past.
	 */
	public TimeRange(DateTime start)
	{
		this(start, null);
	}


	/**
	 * Create a {@link TimeRange} filter for the given start and end date.
	 * 
	 * @param start
	 *            The {@link DateTime} of the start of the range or <code>null</code> for an open range in the past.
	 * @param end
	 *            The {@link DateTime} of the end of the range or <code>null</code> for an open range in the future.
	 */
	public TimeRange(DateTime start, DateTime end)
	{
		if (start == null && end == null)
		{
			throw new IllegalArgumentException("at least one of start or end must be given");
		}

		if (end != null && start != null && !end.after(start))
		{
			throw new IllegalArgumentException("start must be before end");
		}

		if (start != null && start.isFloating() || end != null && end.isFloating())
		{
			throw new IllegalArgumentException("start and end dates must be absolute times");
		}

		if (start != null)
		{
			// DateTime is not immutable, so we get or own private copy to ensure it's not modifed afterwards
			this.start = new DateTime(GREGORIAN_METRICS, start);
			this.start.shiftTimeZone(DateTime.UTC);
		}
		else
		{
			this.start = null;
		}

		if (end != null)
		{
			// DateTime is not immutable, so we get or own private copy to ensure it's not modifed afterwards
			this.end = new DateTime(GREGORIAN_METRICS, end);
			this.end.shiftTimeZone(DateTime.UTC);
		}
		else
		{
			this.end = null;
		}
	}


	@Override
	public ElementDescriptor<? extends FilterBase> getElementDescriptor()
	{
		return DESCRIPTOR;
	}
}
