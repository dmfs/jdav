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

package org.dmfs.dav.rfc4918;

import java.io.IOException;

import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.builder.IObjectBuilder;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;


/**
 * Defines the set of depth values.
 * <p>
 * Note: the depth {@link #one} is serialized as <code>1</code> and {@link #zero} is serialized as <code>0</code>. To get a {@link Depth} from a {@link String}
 * you have to use {@link #get(String)}, otherwise these values won't be mappes correctly.
 * </p>
 * 
 * @see <a href="http://tools.ietf.org/html/rfc4918#section-14.4">RFC 4918 Section 14.4</a>.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public enum Depth
{
	/**
	 * The infinite depth.
	 */
	infinity,

	/**
	 * Depth 1.
	 */
	one {
		@Override
		public String toString()
		{
			return "1";
		}
	},

	/**
	 * Depth 0.
	 */
	zero {
		@Override
		public String toString()
		{
			return "0";
		}
	};

	/**
	 * An {@link IObjectBuilder} that knows how to build and serialize a {@link Depth}.
	 */
	public final static IObjectBuilder<Depth> BUILDER = new AbstractObjectBuilder<Depth>()
	{
		public Depth update(ElementDescriptor<Depth> descriptor, Depth object, String text, ParserContext context) throws XmlObjectPullParserException
		{
			try
			{
				// don't forget to use get(String) instead of valueOf(String)!
				return Depth.get(text);
			}
			catch (IllegalArgumentException e)
			{
				throw new XmlObjectPullParserException("invalid depth value " + text, e);
			}
		};


		public void writeChildren(ElementDescriptor<Depth> descriptor, Depth object, IXmlChildWriter childWriter, SerializerContext context)
			throws SerializerException, IOException
		{
			childWriter.writeText(object.toString(), context);
		};
	};


	/**
	 * Get a {@link Depth} from a {@link String}. In contrast to {@link #valueOf(String)} this method returns {@link #one} for <code>"1"</code> and
	 * {@link #zero} for <code>"0"</code>.
	 * 
	 * @param value
	 *            The depth string.
	 * @return A {@link Depth}.
	 */
	public static Depth get(String value)
	{
		if (value != null && value.length() == 1)
		{
			if (value.charAt(0) == '0')
			{
				return zero;
			}
			else if (value.charAt(0) == '1')
			{
				return one;
			}
		}
		return Depth.valueOf(value);
	}
}
