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

import org.dmfs.dav.PropertyRequest;
import org.dmfs.dav.rfc4791.filter.CompFilter;
import org.dmfs.dav.rfc4918.WebDav;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.builder.IObjectBuilder;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;

import java.io.IOException;


/**
 * Implements the <code>calendar-query</code> object as specified in <a href="http://tools.ietf.org/html/rfc4791#section-9.5">RFC 4791 section 9.5</a> and <a
 * href="http://tools.ietf.org/html/rfc4791#section-7.8">RFC 4791 section 7.8</a>.
 *
 * <pre>
 * Name:  calendar-query
 *
 * Namespace:  urn:ietf:params:xml:ns:caldav
 *
 * Purpose:  Defines a report for querying calendar object resources.
 *
 * Description:  See <a href="http://tools.ietf.org/html/rfc4791#section-7.8">Section 7.8</a>.
 *
 * Definition:
 *
 *       &lt;!ELEMENT calendar-query ((DAV:allprop |
 *                                  DAV:propname |
 *                                  DAV:prop)?, filter, timezone?)&gt;
 *
 *
 * </pre>
 * <p>
 * TODO: add server side support
 */
public class CalendarQuery extends PropertyRequest
{

    /**
     * An {@link IObjectBuilder} to serialize calendar-query objects to XML.
     */
    public final static IObjectBuilder<CalendarQuery> BUILDER = new AbstractObjectBuilder<CalendarQuery>()
    {
        @Override
        public void writeChildren(ElementDescriptor<CalendarQuery> descriptor, CalendarQuery object, IXmlChildWriter childWriter, SerializerContext context)
            throws SerializerException, IOException
        {
            if (object.mPropName)
            {
                childWriter.writeChild(WebDav.PROPNAME, null, context);
            }
            else if (object.mAllProp)
            {
                childWriter.writeChild(WebDav.ALLPROP, null, context);
            }
            else
            {
                childWriter.writeChild(WebDav.PROP, object.mProp, context);
            }

            childWriter.writeChild(CalDav.FILTER, object.mFilter, context);

            // TODO: add timezone
        }


        ;
    };

    /**
     * Specifies that all names and values of dead properties and the live properties defined by this document existing on the resource are to be returned.
     */
    private boolean mAllProp = false;

    /**
     * Specifies that only a list of property names on the resource is to be returned.
     */
    private boolean mPropName = false;

    /**
     * The filter.
     */
    private CompFilter mFilter;


    /**
     * Specifies that all names and values of dead properties and the live properties defined by this document existing on the resource are to be returned. The
     * default is <code>false</code>. If this is set to <code>true</code>, properties added with {@link #addProperty(ElementDescriptor)} will be added to the
     * include element.
     *
     * @param allProp
     *     Whether to send an allprop request or not.
     *
     * @return This instance.
     */
    public CalendarQuery setAllProp(boolean allProp)
    {
        mAllProp = allProp;
        return this;
    }


    /**
     * Specifies that only a list of property names on the resource is to be returned. The default is <code>false</code>.
     *
     * @param propNames
     *     <code>true</code> to request the list of property names only, <code>false</code> to request property values too.
     *
     * @return This instance.
     */
    public CalendarQuery setPropName(boolean propNames)
    {
        mPropName = propNames;
        return this;
    }


    /**
     * Sets a filter to send with the request.
     *
     * @param filter
     *     A {@link CompFilter}.
     */
    public void setFilter(CompFilter filter)
    {
        mFilter = filter;
    }


    @Override
    public void recycle()
    {
        mAllProp = false;
        mPropName = false;
        mFilter = null;
        if (mProp != null)
        {
            mProp.clear();
        }
    }

}
