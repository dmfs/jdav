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
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;


/**
 * A filter that matches based on the value or presence of a specific property.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class PropFilter extends StructuredFilter
{
	private final static QualifiedName ATTRIBUTE_NAME = QualifiedName.get("name");

	public final static ElementDescriptor<PropFilter> DESCRIPTOR = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "prop-filter"),
		new AbstractObjectBuilder<PropFilter>()
		{
			@Override
			public void writeAttributes(ElementDescriptor<PropFilter> descriptor, PropFilter object, IXmlAttributeWriter attributeWriter,
				SerializerContext context) throws SerializerException, IOException
			{
				attributeWriter.writeAttribute(ATTRIBUTE_NAME, object.name, context);
			};


			@SuppressWarnings("unchecked")
			@Override
			public void writeChildren(ElementDescriptor<PropFilter> descriptor, PropFilter object, IXmlChildWriter childWriter, SerializerContext context)
				throws SerializerException, IOException
			{
				if (object.isNotDefined)
				{
					childWriter.writeChild(FILTER_ISNOTDEFINED, null, context);
				}
				else
				{
					if (object.timeRange != null)
					{
						childWriter.writeChild(TimeRange.DESCRIPTOR, object.timeRange, context);
					}
					else if (object.textMatch != null)
					{
						childWriter.writeChild(TextMatch.DESCRIPTOR, object.textMatch, context);
					}

					if (object.filters != null)
					{
						for (ParamFilter filter : object.filters)
						{
							childWriter.writeChild((ElementDescriptor<ParamFilter>) filter.getElementDescriptor(), filter);
						}
					}
				}
			};
		});

	public final String name;
	public final boolean isNotDefined;
	public final TimeRange timeRange;
	public final TextMatch textMatch;
	public final ParamFilter[] filters;


	/**
	 * Creates a filter that matches the presence of a specific property.
	 * 
	 * @param name
	 *            The name of the property.
	 */
	public PropFilter(String name)
	{
		this(name, false);
	}


	/**
	 * Creates a filter that matches the presence or absence of a specific property.
	 * 
	 * @param name
	 *            The name of the property.
	 * @param isNotDefined
	 *            <code>true</code> to match when the property is absent, <code>false</code> to match when it's present.
	 */
	public PropFilter(String name, boolean isNotDefined)
	{
		this.name = name;
		this.isNotDefined = isNotDefined;
		this.timeRange = null;
		this.textMatch = null;
		this.filters = null;
	}


	/**
	 * Creates a filter that matches a property by a list of {@link ParamFilter}s.
	 * 
	 * @param name
	 *            The name of the property to match.
	 * @param filters
	 *            Optional list of {@link ParamFilter}s.
	 */
	public PropFilter(String name, ParamFilter... filters)
	{
		this(name, (TextMatch) null, filters);
	}


	/**
	 * Creates a filter that matches a property by a specific time range and optionally a list of {@link ParamFilter}s.
	 * 
	 * @param name
	 *            The name of the property to match.
	 * @param timeRange
	 *            The {@link TimeRange} to match.
	 * @param filters
	 *            Optional list of {@link ParamFilter}s.
	 */
	public PropFilter(String name, TimeRange timeRange, ParamFilter... filters)
	{
		this.name = name;
		this.isNotDefined = false;
		this.timeRange = timeRange;
		this.textMatch = null;
		this.filters = filters;
	}


	/**
	 * Creates a filter that matches a property by a specific text value and optionally a list of {@link ParamFilter}s.
	 * 
	 * @param name
	 *            The name of the property to match.
	 * @param textMatch
	 *            The text filter.
	 * @param filters
	 *            Optional list of {@link ParamFilter}s.
	 */
	public PropFilter(String name, TextMatch textMatch, ParamFilter... filters)
	{
		this.name = name;
		this.isNotDefined = false;
		this.timeRange = null;
		this.textMatch = textMatch;
		this.filters = filters;
	}


	@Override
	public ElementDescriptor<? extends FilterBase> getElementDescriptor()
	{
		return DESCRIPTOR;
	}

}
