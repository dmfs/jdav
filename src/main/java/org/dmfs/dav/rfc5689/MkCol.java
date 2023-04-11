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

package org.dmfs.dav.rfc5689;

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
 * Implements the <code>mkcol</code> object as specified in <a href="https://tools.ietf.org/html/rfc5689#section-5.1">RFC 5689 section 5.1</a>.
 *
 * <pre>
 * Name:  mkcol
 *
 * Namespace:  DAV:
 *
 * Purpose:  Used in a request to specify properties to be set in an
 *     extended MKCOL request, as well as any additional information
 *     needed when creating the resource.
 *
 *
 * Description:  This XML element is a container for the information
 *     required to modify the properties on a collection resource as it
 *     is created in an extended MKCOL request.
 *
 * Definition:
 *
 * &lt;!ELEMENT mkcol (set&gt;
 * </pre>
 * <p>
 * TODO: mkcol and mkcalendar have almost identical implementations, can we inherit one from the other?
 */
public class MkCol implements Recyclable
{

    /**
     * An {@link IObjectBuilder} to serialize and build {@link MkCol} objects.
     */
    public final static IObjectBuilder<MkCol> BUILDER = new AbstractObjectBuilder<MkCol>()
    {
        @Override
        public MkCol get(ElementDescriptor<MkCol> descriptor, MkCol recycle, org.dmfs.xmlobjects.pull.ParserContext context)
            throws XmlObjectPullParserException
        {
            if (recycle != null)
            {
                recycle.recycle();
                return recycle;
            }

            return new MkCol();
        }


        ;


        @Override
        public <V extends Object> MkCol update(ElementDescriptor<MkCol> descriptor, MkCol object, ElementDescriptor<V> childDescriptor, V child,
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
        public void writeChildren(ElementDescriptor<MkCol> descriptor, MkCol object, IXmlChildWriter childWriter, SerializerContext context)
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
     * Set a specific property when creating the collection.
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
