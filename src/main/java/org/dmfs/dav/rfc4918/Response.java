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
import java.net.URI;
import java.util.*;


/**
 * This class represents a multistatus <code>response</code> element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.24">RFC 4918 section
 * 14.24</a>.
 */
public class Response implements Recyclable
{

    /**
     * The status code if there was no status element.
     */
    public final static int STATUS_NONE = -1;

    /**
     * An {@link IObjectBuilder} for {@link Response} elements.
     */
    public final static IObjectBuilder<Response> BUILDER = new AbstractObjectBuilder<Response>()
    {
        @Override
        public Response get(ElementDescriptor<Response> descriptor, Response recycle, ParserContext context) throws XmlObjectPullParserException
        {
            if (recycle != null)
            {
                recycle.recycle();
                return recycle;
            }
            return new Response();
        }


        ;


        @Override
        public <V> Response update(ElementDescriptor<Response> descriptor,
            Response object,
            ElementDescriptor<V> childDescriptor,
            V child,
            ParserContext context)
            throws XmlObjectPullParserException
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
            else if (childDescriptor == WebDav.HREF)
            {
                object.mHrefs.add((URI) child);
            }
            else if (childDescriptor == WebDav.STATUS)
            {
                object.mStatus = (Integer) child;
            }
            else if (childDescriptor == WebDav.LOCATION)
            {
                object.mLocation = (URI) child;
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
        public void writeChildren(ElementDescriptor<Response> descriptor, Response object, IXmlChildWriter childWriter, SerializerContext context)
            throws SerializerException, IOException
        {
            if (object.mStatus != STATUS_NONE)
            {
                for (URI href : object.mHrefs)
                {
                    childWriter.writeChild(WebDav.HREF, href, context);
                }
                childWriter.writeChild(WebDav.STATUS, object.mStatus, context);
            }
            else
            {
                childWriter.writeChild(WebDav.HREF, object.mHrefs.get(0), context);
                for (PropStat propstat : object.mPropStatByStatus.values())
                {
                    childWriter.writeChild(WebDav.PROPSTAT, propstat, context);
                }
            }

            if (object.mError != null)
            {
                childWriter.writeChild(WebDav.ERROR, object.mError, context);
            }

            if (object.mResponseDescription != null)
            {
                childWriter.writeChild(WebDav.RESPONSEDESCRIPTION, object.mResponseDescription, context);
            }

            if (object.mLocation != null)
            {
                childWriter.writeChild(WebDav.LOCATION, object.mLocation, context);
            }
        }


        ;

    };

    private final List<URI> mHrefs = new ArrayList<URI>(16);

    /**
     * The status of this element. If the response didn't contain any <code>status</code> element (because it contained propstat elements), this will have the
     * value -1.
     */
    private int mStatus = STATUS_NONE;

    /**
     * All {@link PropStat} children by status code.
     */
    private Map<Integer, PropStat> mPropStatByStatus;

    /**
     * All {@link PropStat} children by {@link ElementDescriptor}.
     */
    private Map<ElementDescriptor<?>, PropStat> mPropStatByProperty;

    /**
     * The value of the <code>responsedescription</code> element, if there was any.
     */
    private String mResponseDescription;

    /**
     * The value of the <code>error</code> element, if there was any.
     */
    private Error mError;

    /**
     * The value of the <code>location</code> element, if there was any.
     */
    private URI mLocation;


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


    @Override
    public void recycle()
    {
        mHrefs.clear();

        mStatus = STATUS_NONE;

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

        mResponseDescription = null;
        mError = null;
        mLocation = null;
    }


    /**
     * Get the response description, if any.
     *
     * @return The response description or <code>null</code>.
     */
    public String getResponseDescription()
    {
        return mResponseDescription;
    }


    /**
     * Get the error, if any.
     *
     * @return The {@link Error} or <code>null</code>.
     */
    public Error getError()
    {
        return mError;
    }


    /**
     * Get the location, if any.
     *
     * @return The location or <code>null</code>.
     */
    public URI getLocation()
    {
        return mLocation;
    }


    /**
     * Return the status int of this response or {@link #STATUS_NONE} if the response didn't contain a status.
     *
     * @return The status int or {@link #STATUS_NONE}.
     */
    public int getStatus()
    {
        return mStatus;
    }


    /**
     * Returns the href value of this response if {@link #getStatus()} returns {@link #STATUS_NONE}, otherwise this returns <code>null</code> (use
     * {@link #getHRefs()} in that case).
     *
     * @return The href URI or <code>null</code>.
     */
    public URI getHRef()
    {
        if (mStatus == STATUS_NONE)
        {
            return mHrefs.get(0);
        }
        return null;
    }


    /**
     * Return all href {@link URI}s in this response. This is only valid if {@link #getStatus()} does not return {@link #STATUS_NONE},
     *
     * @return An unmodifiable {@link List} of {@link URI}s or <code>null</code>.
     */
    public List<URI> getHRefs()
    {
        if (mStatus != STATUS_NONE)
        {
            return Collections.unmodifiableList(mHrefs);
        }
        return null;
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


    /**
     * Resolve the {@link URI}s of the href elements of this response object against the given {@link URI}.
     * <p>
     * <strong>Note:</strong> This will only resolve the href URIs of the response object itself. It will not resolve any URI value of any property. If you need
     * to resolve those you should do that against the the URI you get from {@link #getHRef()}.
     * </p>
     *
     * @param uri
     *     The {@link URI} to resolve against.
     */
    public void resolveHRefs(URI uri)
    {
        if (mLocation != null && !mLocation.isAbsolute())
        {
            mLocation = uri.resolve(mLocation);
        }

        List<URI> hrefs = mHrefs;
        for (int i = 0, count = hrefs.size(); i < count; ++i)
        {
            URI href = hrefs.get(i);
            if (!href.isAbsolute())
            {
                hrefs.set(i, uri.resolve(href));
            }
        }
    }
}
