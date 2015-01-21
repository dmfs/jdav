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

package org.dmfs.dav.rfc6352.filter;

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
 * A filter that matches base on the value or presence of a property parameter.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class ParamFilter extends CardDavFilter
{
	private final static QualifiedName ATTRIBUTE_NAME = QualifiedName.get("name");

	public final static ElementDescriptor<ParamFilter> DESCRIPTOR = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "param-filter"),
		new AbstractObjectBuilder<ParamFilter>()
		{
			@Override
			public void writeAttributes(ElementDescriptor<ParamFilter> descriptor, ParamFilter object, IXmlAttributeWriter attributeWriter,
				SerializerContext context) throws SerializerException, IOException
			{
				attributeWriter.writeAttribute(ATTRIBUTE_NAME, object.name);
			};


			@Override
			public void writeChildren(ElementDescriptor<ParamFilter> descriptor, ParamFilter object, IXmlChildWriter childWriter, SerializerContext context)
				throws SerializerException, IOException
			{
				if (object.isNotDefined)
				{
					childWriter.writeChild(FILTER_ISNOTDEFINED, null);
				}
				else if (object.textMatch != null)
				{
					childWriter.writeChild(TextMatch.DESCRIPTOR, object.textMatch);
				}
			};
		});

	public final String name;
	public final boolean isNotDefined;
	public final TextMatch textMatch;


	/**
	 * Create a filter that matches a present parameter.
	 * 
	 * @param name
	 *            The name of the parameter, must not be empty.
	 */
	public ParamFilter(String name)
	{
		this(name, false);
	}


	/**
	 * Create a filter based on the presence or absence of a parameter.
	 * 
	 * @param name
	 *            The name of the parameter, must not be empty.
	 * @param isNotDefined
	 *            <code>true</code> if the parameter needs to be absent to match, <code>false</code> otherwise.
	 */
	public ParamFilter(String name, boolean isNotDefined)
	{
		if (name == null)
		{
			throw new NullPointerException("the parameter name of a param filter must not be null");
		}
		if (name.length() == 0)
		{
			throw new IllegalArgumentException("the parameter name of a param filter must not be empty");
		}
		this.name = name;
		this.isNotDefined = isNotDefined;
		this.textMatch = null;
	}


	/**
	 * Create a filter that matches the value of a parameter.
	 * 
	 * @param name
	 *            The name of the parameter, must not be empty.
	 * @param textMatch
	 *            The filter for the text.
	 */
	public ParamFilter(String name, TextMatch textMatch)
	{
		if (name == null)
		{
			throw new NullPointerException("the parameter name of a param filter must not be null");
		}
		if (name.length() == 0)
		{
			throw new IllegalArgumentException("the parameter name of a param filter must not be empty");
		}
		this.name = name;
		this.isNotDefined = false;
		this.textMatch = textMatch;
	}


	@Override
	public ElementDescriptor<? extends FilterBase> getElementDescriptor()
	{
		return DESCRIPTOR;
	}
}
