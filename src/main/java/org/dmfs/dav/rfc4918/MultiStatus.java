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

import org.dmfs.dav.rfc6578.WebDavSync;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * Represents a multi-status element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.16">RFC 4918 section 14.16</a> including the extension
 * specified in <a href="http://tools.ietf.org/html/rfc6578#section-6.4">RFC 6578 section 6.4</a> to return a <code>&lt;sync-token></code> element.
 *
 * <pre>
 * Name:  multistatus
 *
 * Namespace:  DAV:
 *
 * Purpose:  Extends the DAV:multistatus element to include
 *    synchronization details.
 *
 * Description:  See Section 3.
 *
 * &lt;!ELEMENT multistatus (response*, responsedescription?,
 *                        sync-token?) >
 *
 * &lt;!-- DAV:multistatus originally defined in RFC 4918, Section 14.16
 *      but overridden here to add the DAV:sync-token element -->
 * &lt;!-- DAV:response defined in RFC 4918, Section 14.24 -->
 * &lt;!-- DAV:responsedescription defined in RFC 4918, Section 14.25 -->
 * </pre>
 * <p>
 * TODO: we probably shouldn't use a list to store responses. Instead we could switch to something that allows to render the response on the fly.
 */
public class MultiStatus implements Recyclable
{

    /**
     * An {@link IObjectBuilder} for {@link MultiStatus} elements.
     */
    public final static IObjectBuilder<MultiStatus> BUILDER = new AbstractObjectBuilder<MultiStatus>()
    {
        @Override
        public MultiStatus get(ElementDescriptor<MultiStatus> descriptor, MultiStatus recycle, ParserContext context) throws XmlObjectPullParserException
        {
            if (recycle != null)
            {
                recycle.recycle();
                return recycle;
            }
            return new MultiStatus();
        }


        @Override
        public <V extends Object> MultiStatus update(ElementDescriptor<MultiStatus> descriptor, MultiStatus object, ElementDescriptor<V> childDescriptor,
            V child, ParserContext context) throws XmlObjectPullParserException
        {
            if (childDescriptor == WebDav.RESPONSE)
            {
                List<Response> responses = object.mResponses;
                if (responses == null)
                {
                    responses = object.mResponses = new ArrayList<Response>(32);
                }
                responses.add((Response) child);
            }
            else if (childDescriptor == WebDavSync.SYNC_TOKEN)
            {
                object.mSyncToken = child.toString();
            }
            else if (childDescriptor == WebDav.RESPONSEDESCRIPTION)
            {
                object.mResponseDescription = child.toString();
            }
            return object;
        }


        @Override
        public void writeChildren(ElementDescriptor<MultiStatus> descriptor, MultiStatus object, IXmlChildWriter childWriter, SerializerContext context)
            throws SerializerException, IOException
        {
            if (object.mResponses != null)
            {
                Iterator<Response> iterator = object.mResponses.iterator();
                while (iterator.hasNext())
                {
                    childWriter.writeChild(WebDav.RESPONSE, iterator.next(), context);
                }
            }
            if (object.mResponseDescription != null)
            {
                childWriter.writeChild(WebDav.RESPONSEDESCRIPTION, object.mResponseDescription, context);
            }
            if (object.mSyncToken != null)
            {
                childWriter.writeChild(WebDavSync.SYNC_TOKEN, object.mSyncToken, context);
            }
        }
    };

    /**
     * The list of responses in this multistatus response. This may be <code>null</code> or empty if no responses have been added. This can happen if there were
     * no responses or if the responses have been processed "on-the-fly".
     */
    private List<Response> mResponses;

    /**
     * A response description.
     */
    private String mResponseDescription;

    /**
     * sync-token is defined in <a href="http://tools.ietf.org/html/rfc6578#section-6.2">RFC 6578 section 6.2</a>.
     */
    private String mSyncToken;


    /**
     * Sets a list of responses. All responses that may have been added before will be removed by this call.
     */
    public void setResponses(List<Response> responses)
    {
        mResponses = responses;
    }


    /**
     * Returns the responses that have been stored in this object. Note that this might return <code>null</code> if all responses have been processed
     * on-the-fly.
     *
     * @return An unmodifiable {@link List} of {@link Response}s or <code>null</code> if none have been returned or added.
     */
    public List<Response> getResponses()
    {
        return mResponses != null ? Collections.unmodifiableList(mResponses) : null;
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
     * Returns the sync-token or <code>null</code> if there was no sync-token in the response.
     *
     * @return The sync-token string or <code>null</code>.
     */
    public String getSyncToken()
    {
        return mSyncToken;
    }


    /**
     * Resolve the {@link URI}s of the {@link Response} href elements objects against the given {@link URI}. This will only work for {@link Response} objects
     * stored in this {@link MultiStatus}.
     * <p>
     * <strong>Note:</strong> This will only resolve the href URIs of the response objects itself. It will not resolve any URI value of any property.
     * </p>
     *
     * @param uri
     *     The {@link URI} to resolve against.
     */
    public void resolveHRefs(URI uri)
    {
        if (mResponses != null)
        {
            for (Response response : mResponses)
            {
                response.resolveHRefs(uri);
            }
        }
    }


    @Override
    public void recycle()
    {
        if (mResponses != null)
        {
            mResponses.clear();
        }
        mResponseDescription = null;
        mSyncToken = null;
    }
}
