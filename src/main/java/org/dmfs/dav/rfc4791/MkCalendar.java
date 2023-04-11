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

import org.dmfs.dav.rfc4918.WebDav;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.builder.IObjectBuilder;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.Recyclable;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Implements the <code>mkcalendar</code> object as specified in <a href="http://tools.ietf.org/html/rfc4791#section-9.2">RFC 4791 section 9.2</a> and <a
 * href="http://tools.ietf.org/html/rfc4791#section-5.3.1">RFC 4791 section 5.3.1</a>.
 *
 * <pre>
 * Name:  mkcalendar
 *
 * Namespace:  urn:ietf:params:xml:ns:caldav
 *
 * Purpose:  Specifies a request that includes the WebDAV property
 *    values to be set for a calendar collection resource when it is
 *    created.
 *
 * Description:  See <a href="http://tools.ietf.org/html/rfc4791#section-5.3.1">Section 5.3.1</a>.
 *
 * Definition:
 *
 * &lt;!ELEMENT mkcalendar (DAV:set)>
 * </pre>
 */
public class MkCalendar implements Recyclable
{

    /**
     * An {@link IObjectBuilder} to serialize and build {@link MkCalendar} objects.
     */
    public final static IObjectBuilder<MkCalendar> BUILDER = new AbstractObjectBuilder<MkCalendar>()
    {
        @Override
        public MkCalendar get(ElementDescriptor<MkCalendar> descriptor, MkCalendar recycle, org.dmfs.xmlobjects.pull.ParserContext context)
            throws XmlObjectPullParserException
        {
            if (recycle != null)
            {
                recycle.recycle();
                return recycle;
            }

            return new MkCalendar();
        }


        ;


        @Override
        public <V extends Object> MkCalendar update(ElementDescriptor<MkCalendar> descriptor, MkCalendar object, ElementDescriptor<V> childDescriptor, V child,
            ParserContext context) throws XmlObjectPullParserException
        {
            if (childDescriptor == WebDav.SET)
            {
                @SuppressWarnings("unchecked")
                Map<ElementDescriptor<?>, Object> set = (Map<ElementDescriptor<?>, Object>) child;
                if (object.mSet == null)
                {
                    object.mSet = set;
                }
                else
                {

                    object.mSet.putAll(set);
                    context.recycle(WebDav.SET, set);
                }
            }
            return object;
        }


        ;


        @Override
        public void writeChildren(ElementDescriptor<MkCalendar> descriptor, MkCalendar object, IXmlChildWriter childWriter, SerializerContext context)
            throws SerializerException, IOException
        {
            if (object.mSet != null && object.mSet.size() > 0)
            {
                childWriter.writeChild(WebDav.SET, object.mSet, context);
            }
        }


        ;
    };

    /**
     * A map of new properties to set.
     */
    private Map<ElementDescriptor<?>, Object> mSet;


    /**
     * Set a specific property when creating the calendar.
     *
     * @param property
     *     The {@link ElementDescriptor} of the property.
     * @param value
     *     The value of the property.
     */
    public <T> void set(ElementDescriptor<T> property, T value)
    {
        if (mSet == null)
        {
            mSet = new HashMap<ElementDescriptor<?>, Object>(16);
        }
        mSet.put(property, value);
    }


    /**
     * Remove a property from the initial values.
     *
     * @param property
     *     The {@link ElementDescriptor} of the property.
     */
    public <T> void clear(ElementDescriptor<T> property)
    {
        if (mSet != null)
        {
            mSet.remove(property);
        }
    }


    @Override
    public void recycle()
    {
        if (mSet != null)
        {
            mSet.clear();
        }
    }

}
