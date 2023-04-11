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

import org.dmfs.dav.FilterBase;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlAttributeWriter;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;

import java.io.IOException;


/**
 * A filter that matches if a specific component is present (or not). It allows to specify further conditions (like the presense of specific sub-components or
 * properties) that the components in the result must satisfy in order to match.
 */
public final class CompFilter extends StructuredFilter
{
    private final static QualifiedName ATTRIBUTE_NAME = QualifiedName.get("name");

    /**
     * The {@link ElementDescriptor} of the {@link CompFilter} element.
     */
    public final static ElementDescriptor<CompFilter> DESCRIPTOR = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "comp-filter"),
        new AbstractObjectBuilder<CompFilter>()
        {
            @Override
            public void writeAttributes(ElementDescriptor<CompFilter> descriptor, CompFilter object, IXmlAttributeWriter attributeWriter,
                SerializerContext context) throws SerializerException, IOException
            {
                attributeWriter.writeAttribute(ATTRIBUTE_NAME, object.name, context);
            }


            ;


            @SuppressWarnings("unchecked")
            @Override
            public void writeChildren(ElementDescriptor<CompFilter> descriptor, CompFilter object, IXmlChildWriter childWriter, SerializerContext context)
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
                    if (object.filters != null)
                    {
                        for (StructuredFilter filter : object.filters)
                        {
                            childWriter.writeChild((ElementDescriptor<StructuredFilter>) filter.getElementDescriptor(), filter, context);
                        }
                    }
                }
            }


            ;
        });

    /**
     * The name of the component to match.
     */
    public final String name;

    /**
     * Whether this component needs to be present to match.
     */
    public final boolean isNotDefined;

    /**
     * An optional {@link TimeRange} to match.
     */
    public final TimeRange timeRange;

    /**
     * Additional filters to match.
     */
    public final StructuredFilter[] filters;


    /**
     * Creates a filter that matches a component when it's present.
     *
     * @param name
     *     the name of the component to match, must not be empty.
     */
    public CompFilter(String name)
    {
        this(name, false);
    }


    /**
     * Creates a filter that matches a component based on it's presence.
     *
     * @param name
     *     the name of the component to match, must not be empty.
     * @param isNotDefined
     *     <code>true</code> to match when the component is absent, <code>false</code> to match when it's present.
     */
    public CompFilter(String name, boolean isNotDefined)
    {
        if (name == null)
        {
            throw new NullPointerException("the component name of a comp filter must not be null");
        }
        if (name.length() == 0)
        {
            throw new IllegalArgumentException("the component name of a comp filter must not be empty");
        }
        this.name = name;
        this.isNotDefined = isNotDefined;
        this.timeRange = null;
        this.filters = null;
    }


    /**
     * Creates a filter for a specific component with subfilters to match.
     *
     * @param name
     *     the name of the component, must not be empty.
     * @param filters
     *     The subfilters.
     */
    public CompFilter(String name, StructuredFilter... filters)
    {
        this(name, null, filters);
    }


    /**
     * Creates a filter for a spcecific component with subfilters and a time range filter to match.
     *
     * @param name
     *     the name of the component to match, must not be empty.
     * @param timeRange
     *     the {@link TimeRange} to match. May be <code>null</code>.
     * @param filters
     *     Optional subfilters to match.
     */
    public CompFilter(String name, TimeRange timeRange, StructuredFilter... filters)
    {
        if (name == null)
        {
            throw new NullPointerException("the component name of a comp filter must not be null");
        }
        if (name.length() == 0)
        {
            throw new IllegalArgumentException("the component name of a comp filter must not be empty");
        }
        this.name = name;
        this.isNotDefined = false;
        this.timeRange = timeRange;
        this.filters = filters;
    }


    @Override
    public ElementDescriptor<? extends FilterBase> getElementDescriptor()
    {
        return DESCRIPTOR;
    }

}
