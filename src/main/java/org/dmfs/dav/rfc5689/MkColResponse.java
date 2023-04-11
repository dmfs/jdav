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

import org.dmfs.dav.DavParserContext;
import org.dmfs.dav.rfc4918.PropStat;
import org.dmfs.dav.rfc4918.Response;
import org.dmfs.dav.rfc4918.WebDav;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Implements the <code>mkcol-response</code> object as specified in <a href="https://tools.ietf.org/html/rfc5689#section-5.2">RFC 5689 section 5.2</a>.
 *
 * <pre>
 * Name:  mkcol-response
 *
 * Namespace:  DAV:
 *
 * Purpose:  Used in a response to indicate the status of properties
 *     that were set or failed to be set during an extended MKCOL
 *     request.
 *
 * Description:   This XML element is a container for the information
 *     returned about a resource that has been created in an extended
 *     MKCOL request.
 *
 * Definition:
 *
 * &lt;!ELEMENT mkcol-response (propstat+)&gt;
 * </pre>
 * <p>
 * TODO: this shares some code with {@link Response}. Check if can improve the implementation.
 */
public class MkColResponse implements Recyclable
{
    /**
     * The status code if there was no status element.
     */
    public final static int STATUS_NONE = -1;

    /**
     * An {@link IObjectBuilder} to serialize and build {@link MkColResponse} objects.
     */
    public final static IObjectBuilder<MkColResponse> BUILDER = new AbstractObjectBuilder<MkColResponse>()
    {
        @Override
        public MkColResponse get(ElementDescriptor<MkColResponse> descriptor, MkColResponse recycle, org.dmfs.xmlobjects.pull.ParserContext context)
            throws XmlObjectPullParserException
        {
            if (recycle != null)
            {
                recycle.recycle();
                return recycle;
            }

            return new MkColResponse();
        }


        @Override
        public <V extends Object> MkColResponse update(ElementDescriptor<MkColResponse> descriptor, MkColResponse object, ElementDescriptor<V> childDescriptor,
            V child, ParserContext context) throws XmlObjectPullParserException
        {
            if (childDescriptor == WebDav.PROPSTAT)
            {
                PropStat propStat = (PropStat) child;
                if (propStat.getStatusCode() == HttpStatus.NOT_FOUND.statusCode() && context instanceof DavParserContext
                    && !((DavParserContext) context).getKeepNotFoundProperties())
                {
                    // the PropStat element has status NOT_FOUND and we shall not keep it, so just recycle it
                    context.recycle(WebDav.PROPSTAT, propStat);
                }
                else
                {
                    // add new propstat element and recycle old one with the same status, if any
                    context.recycle(WebDav.PROPSTAT, object.addPropStat(propStat));
                }
            }
            return object;
        }


        @Override
        public void writeChildren(ElementDescriptor<MkColResponse> descriptor, MkColResponse object, IXmlChildWriter childWriter, SerializerContext context)
            throws SerializerException, IOException
        {
            for (PropStat propstat : object.mPropStatByStatus.values())
            {
                childWriter.writeChild(WebDav.PROPSTAT, propstat, context);
            }
        }
    };

    /**
     * All {@link PropStat} children by status code.
     */
    private Map<Integer, PropStat> mPropStatByStatus;

    /**
     * All {@link PropStat} children by {@link ElementDescriptor}.
     */
    private Map<ElementDescriptor<?>, PropStat> mPropStatByProperty;


    /**
     * Add the given {@link PropStat}. If there already is a {@link PropStat} with the same status code it's replaced by the new one and returned.
     *
     * @param propStat
     *     The new {@link PropStat}.
     *
     * @return The old {@link PropStat} instance with the same status code or <code>null</code> if there is none.
     */
    private PropStat addPropStat(PropStat propStat)
    {
        // create Map if necessary
        Map<ElementDescriptor<?>, PropStat> propStatByProperty = mPropStatByProperty;
        if (propStatByProperty == null)
        {
            propStatByProperty = mPropStatByProperty = new HashMap<ElementDescriptor<?>, PropStat>(16);
        }

        Set<ElementDescriptor<?>> properties = propStat.getPropertyDescriptors();
        if (properties != null)
        {
            for (ElementDescriptor<?> property : properties)
            {
                propStatByProperty.put(property, propStat);
            }
        }

        if (mPropStatByStatus == null)
        {
            mPropStatByStatus = new HashMap<Integer, PropStat>(6 /* the average case has no more than 2 PropStats per Response */);
        }

        return mPropStatByStatus.put(propStat.getStatusCode(), propStat);
    }


    /**
     * Return the status of a specific property.
     *
     * @param descriptor
     *     The {@link ElementDescriptor} of the property.
     *
     * @return The status of the property or {@link #STATUS_NONE} if the property was not found.
     */
    public int getPropertyStatus(ElementDescriptor<?> descriptor)
    {
        PropStat propStat = mPropStatByProperty.get(descriptor);
        if (propStat == null)
        {
            return STATUS_NONE;
        }

        return propStat.getStatusCode();
    }


    /**
     * Get the value of a specific property.
     *
     * @param descriptor
     *     The {@link ElementDescriptor} of the property to return.
     *
     * @return The property value, may be <code>null</code> if the property was not present or didn't contain any value (because it had a non-
     * {@link HttpStatus#OK} status).
     */
    public <T> T getPropertyValue(ElementDescriptor<T> descriptor)
    {
        PropStat propStat = mPropStatByProperty.get(descriptor);
        if (propStat == null)
        {
            return null;
        }

        return propStat.getPropertyValue(descriptor);
    }


    /**
     * Get the {@link Set} of {@link ElementDescriptor}s of all properties in this response.
     *
     * @return An unmodifiable {@link Set} of {@link ElementDescriptor}s, may be <code>null</code> or empty if there are no properties in this response.
     */
    public Set<ElementDescriptor<?>> getProperties()
    {
        if (mPropStatByProperty == null)
        {
            return null;
        }

        return Collections.unmodifiableSet(mPropStatByProperty.keySet());
    }


    @Override
    public void recycle()
    {
        if (mPropStatByProperty != null)
        {
            mPropStatByProperty.clear();
        }

        if (mPropStatByStatus != null)
        {
            // recycle the propstat objects, but don't remove them, we probably can use them later
            for (PropStat propStat : mPropStatByStatus.values())
            {
                propStat.recycle();
            }
        }

    }

}
