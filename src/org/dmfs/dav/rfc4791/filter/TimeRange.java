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
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlAttributeWriter;


/**
 * A filter that limits the results to a certain range of time.
 * <p>
 * FIXME: the serialization of the date-time values is wrong. We need to convert the time stamps to a UTC date string. We need to import the DateTime for that.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class TimeRange extends CalDavFilter
{
	private final static QualifiedName ATTRIBUTE_START = QualifiedName.get("start");
	private final static QualifiedName ATTRIBUTE_END = QualifiedName.get("end");

	public final static ElementDescriptor<TimeRange> DESCRIPTOR = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "time-range"),
		new AbstractObjectBuilder<TimeRange>()
		{
			@Override
			public void writeAttributes(ElementDescriptor<TimeRange> descriptor, TimeRange object, IXmlAttributeWriter attributeWriter,
				SerializerContext context) throws SerializerException, IOException
			{
				if (object.start != Long.MIN_VALUE)
				{
					// FIXME: this is wrong and only for testing
					attributeWriter.writeAttribute(ATTRIBUTE_START, String.valueOf(object.start));
				}
				if (object.end != Long.MAX_VALUE)
				{
					// FIXME: this is wrong and only for testing
					attributeWriter.writeAttribute(ATTRIBUTE_END, String.valueOf(object.end));
				}
			};
		});

	public final long start;
	public final long end;


	/**
	 * Create a {@link TimeRange} filter for the given start and without a limit for the end value.
	 * 
	 * @param start
	 *            The time in milliseconds of the start of the range or {@link Long#MIN_VALUE} for an open range in the past.
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
	 *            The time in milliseconds of the end of the range or {@link Long#MAX_VALUE}for an open range in the future.
	 */
	public TimeRange(long start, long end)
	{
		if (end <= start)
		{
			throw new IllegalArgumentException("start must be before end");
		}
		this.start = start;
		this.end = end;
	}


	@Override
	public ElementDescriptor<? extends FilterBase> getElementDescriptor()
	{
		return DESCRIPTOR;
	}
}
