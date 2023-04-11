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

import org.dmfs.dav.DavParserContext;
import org.dmfs.httpessentials.HttpStatus;
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
import java.util.Collections;
import java.util.Map;
import java.util.Set;


/**
 * Represents a <code>propstat</code> element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.22">RFC 4918 section 14.22</a>.
 */
public class PropStat implements Recyclable
{
    /**
     * The initial value of {@link #mStatus};
     */
    private final static int STATUS_NONE = -1;

    /**
     * An {@link IObjectBuilder} for {@link PropStat} elements.
     * <p>
     * TODO: At present it doesn't feature a serializer.
     * </p>
     */
    public final static IObjectBuilder<PropStat> BUILDER = new AbstractObjectBuilder<PropStat>()
    {
        @Override
        public PropStat get(ElementDescriptor<PropStat> descriptor, PropStat recycle, ParserContext context) throws XmlObjectPullParserException
        {
            if (recycle != null)
            {
                recycle.recycle();
                return recycle;
            }
            return new PropStat();
        }


        ;


        @SuppressWarnings("unchecked")
        @Override
        public <V> PropStat update(ElementDescriptor<PropStat> descriptor,
            PropStat object,
            ElementDescriptor<V> childDescriptor,
            V child,
            ParserContext context)
            throws XmlObjectPullParserException
        {
            if (childDescriptor == WebDav.PROP)
            {
                // recycle old property map, if any
                context.recycle(WebDav.PROP, object.mProperties);
                // set new property list
                object.mProperties = (Map<ElementDescriptor<?>, Object>) child;
            }
            else if (childDescriptor == WebDav.STATUS)
            {
                object.mStatus = (Integer) child;
            }
            else if (childDescriptor == WebDav.ERROR)
            {
                object.mError = (Error) child;
            }
            else if (childDescriptor == WebDav.RESPONSEDESCRIPTION)
            {
                object.mResponseDescription = child.toString();
            }
            return object;
        }


        ;


        @Override
        public PropStat finish(ElementDescriptor<PropStat> descriptor, PropStat object, ParserContext context) throws XmlObjectPullParserException
        {
            boolean strict = !(context instanceof DavParserContext) || ((DavParserContext) context).isStrict();

            if (object.mStatus == STATUS_NONE)
            {
                if (strict)
                {
                    throw new XmlObjectPullParserException("<propstat> must contain a status element!");
                }
                object.mStatus = HttpStatus.OK.statusCode();
            }
            if (strict && object.mProperties == null)
            {
                throw new XmlObjectPullParserException("<propstat> must contain a prop element!");
            }
            return object;
        }


        ;


        @Override
        public void writeChildren(ElementDescriptor<PropStat> descriptor, PropStat object, IXmlChildWriter childWriter, SerializerContext context)
            throws SerializerException, IOException
        {
            // the prop element is mandatory, but it may be empty
            childWriter.writeChild(WebDav.PROP, object.mProperties, context);

            childWriter.writeChild(WebDav.STATUS, object.mStatus, context);

            if (object.mError != null)
            {
                childWriter.writeChild(WebDav.ERROR, object.mError, context);
            }

            if (object.mResponseDescription != null)
            {
                childWriter.writeChild(WebDav.RESPONSEDESCRIPTION, object.mResponseDescription, context);
            }
        }


        ;
    };

    /**
     * A map of properties to values or <code>null</code> if no value was returned (because status was not "OK").
     */
    private Map<ElementDescriptor<?>, Object> mProperties;

    /**
     * The status of this propstat element.
     */
    private int mStatus;

    /**
     * The error element of this propstat element.
     */
    private Error mError;

    /**
     * The value of the responsedescription element of this propstat element.
     */
    private String mResponseDescription;


    @Override
    public void recycle()
    {
        // Note: we don't recycle the individual property objects, because the client may still use these property values.
        // Also, in general many of them are represented by immutable objects, which can not be recycled anyway.
        mStatus = STATUS_NONE;
        if (mProperties != null)
        {
            mProperties.clear();
        }
        mError = null;
        mResponseDescription = null;
    }


    /**
     * Returns the value of a property in this propstat element.
     *
     * @param property
     *     the {@link ElementDescriptor} of the property to return.
     *
     * @return the value of the property or <code>null</code> if there is no property of this name.
     */
    @SuppressWarnings("unchecked")
    public <T> T getPropertyValue(ElementDescriptor<T> property)
    {
        if (mProperties == null)
        {
            return null;
        }
        return (T) mProperties.get(property);
    }


    /**
     * Returns the status of this propstat element.
     *
     * @return The status code.
     */
    public int getStatusCode()
    {
        return mStatus;
    }


    /**
     * Returns the responsedescription of this propstat element, if there is any.
     *
     * @return The response description or <code>null</code> if there is none.
     */
    public String getResponseDescription()
    {
        return mResponseDescription;
    }


    /**
     * Returns the error element of this propstat element, if there is any.
     *
     * @return The {@link Error} or <code>null</code> if there was no &lt;error> element.
     */
    public Error getError()
    {
        return mError;
    }


    /**
     * Returns the {@link Set} of properties in this PropStat element, each represented by its {@link ElementDescriptor}s.
     *
     * @return A {@link Set} of {@link ElementDescriptor}s or <code>null</code>.
     */
    public Set<ElementDescriptor<?>> getPropertyDescriptors()
    {
        return mProperties == null ? null : Collections.unmodifiableSet(mProperties.keySet());
    }
}
