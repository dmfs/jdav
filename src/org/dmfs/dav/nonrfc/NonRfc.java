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

package org.dmfs.dav.nonrfc;

import java.io.IOException;

import org.dmfs.dav.rfc6578.SyncCollection;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.builder.StringObjectBuilder;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;


/**
 * Element definitions that are not part of any official RFC, but still commonly used.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class NonRfc
{

	/**
	 * Apple calendar & contacts server namespace.
	 */
	public final static String NAMESPACE_CALENDARSERVER = "http://calendarserver.org/ns/";

	/**
	 * Apple iCal name space.
	 */
	public final static String NAMESPACE_APPLE_ICAL = "http://apple.com/ns/ical/";

	/**
	 * Attribute symbolic-color.
	 */
	private final static QualifiedName ATTR_SYMBOLIC_COLOR = QualifiedName.get("symbolic-color");

	/**
	 * DAV Properties that are not standardized, but still used in common.
	 */
	public final static class Properties
	{
		/**
		 * <p>
		 * getctag property as defined in <a
		 * href="http://svn.calendarserver.org/repository/calendarserver/CalendarServer/trunk/doc/Extensions/caldav-ctag.txt">caldav-ctag</a>.
		 * </p>
		 * A ctag is the calendar/collection counterpart of an etag. It has been superseded by {@link org.dmfs.dav.rfc6578.WebDavSync.Properties#SYNC_TOKEN},
		 * but is still supported by most servers.
		 * 
		 * @see SyncCollection
		 */
		public final static ElementDescriptor<String> GETCTAG = ElementDescriptor.register(QualifiedName.get(NAMESPACE_CALENDARSERVER, "getctag"),
			StringObjectBuilder.INSTANCE);

		/**
		 * <p>
		 * calendar-color property.
		 * </p>
		 * The format of the value varies from server to server, but in general it's <code>#rrggbbaa</code> or <code>#rrggbb</code>.
		 */
		public final static ElementDescriptor<Integer> CALENDAR_COLOR = ElementDescriptor.register(QualifiedName.get(NAMESPACE_APPLE_ICAL, "calendar-color"),
			new AbstractObjectBuilder<Integer>()
			{
				public Integer update(ElementDescriptor<Integer> descriptor, Integer object, String text, ParserContext context)
					throws XmlObjectPullParserException
				{
					if (text == null)
					{
						return null;
					}

					if (text.startsWith("#"))
					{
						text = text.substring(1);
					}

					try
					{
						int len = text.length();
						int color = (int) Long.parseLong(text, 16);
						if (len > 6)
						{
							// the format is expected to be #rrggbbaa
							// convert from rgba to argb and make the color fully opaque
							color = ((color >> 8) & 0x00ffffff) | 0xff000000;
						}
						else if (len == 6)
						{
							// the format is expected to be #rrggbb
							color = color | 0xff000000;
						}
						else if (len == 3)
						{
							// this is very uncommon
							// the format is expected to be #rgb
							int r = (color >> 8) & 0x0f;
							r = r | (r << 4);
							int g = color & 0x0f0;
							g = g | (g >> 4);
							int b = color & 0x0f;
							b = b | (b << 4);

							color = (r << 16) | (g << 8) | b | 0xff000000;
						}
						return color;
					}
					catch (NumberFormatException e)
					{
						// not a valid color
						return null;
					}
				};


				@Override
				public void writeAttributes(ElementDescriptor<Integer> descriptor, Integer object, XmlObjectSerializer.IXmlAttributeWriter attributeWriter,
					SerializerContext context) throws SerializerException, IOException
				{
					// for now we always create "custom" colors, at some point we might support css color names
					attributeWriter.writeAttribute(ATTR_SYMBOLIC_COLOR, "custom", context);
				};


				@Override
				public void writeChildren(ElementDescriptor<Integer> descriptor, Integer object, IXmlChildWriter childWriter, SerializerContext context)
					throws SerializerException, IOException
				{
					if (object == null)
					{
						return;
					}
					int color = object;

					// convert from argb to rgba
					color = (color << 8) | ((color >> 24) & 0x0ff);

					String hexString = Integer.toHexString(color);

					childWriter.writeText("#" + ("0000000".substring(hexString.length() - 1)) + hexString, context);
				}
			});


		/**
		 * No instances allowed.
		 */
		private Properties()
		{
		}
	}

}
