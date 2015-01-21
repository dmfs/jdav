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

package org.dmfs.dav.rfc6352;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.dmfs.dav.PropertyRequest;
import org.dmfs.dav.rfc4918.WebDav;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.builder.IObjectBuilder;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;


/**
 * <code>addressbook-multiget</code> report as specified in <a href="http://tools.ietf.org/html/rfc6352#section-8.7">RFC 6352, section 8.7</a> and <a
 * href="http://tools.ietf.org/html/rfc6352#section-10.7">RFC 6352, section 10.7</a>.
 * <p>
 * This report takes a number of properties and a list of <code>href</code>s to retrieve from the server. It's meant to be much more efficient than spearate GET
 * requests.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class AddressbookMultiget extends PropertyRequest
{

	/**
	 * An {@link IObjectBuilder} to serialize addressbook-multiget objects to XML.
	 */
	public final static IObjectBuilder<AddressbookMultiget> BUILDER = new AbstractObjectBuilder<AddressbookMultiget>()
	{
		@Override
		public void writeChildren(ElementDescriptor<AddressbookMultiget> descriptor, AddressbookMultiget object, IXmlChildWriter childWriter,
			SerializerContext context) throws SerializerException, IOException
		{
			if (object.mPropName)
			{
				childWriter.writeChild(WebDav.PROPNAME, null);
			}
			else if (object.mAllProp)
			{
				childWriter.writeChild(WebDav.ALLPROP, null);
			}
			else
			{
				childWriter.writeChild(WebDav.PROP, object.mProp);
			}

			for (URI uri : object.mUris)
			{
				childWriter.writeChild(WebDav.HREF, uri);
			}
		};
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
	 * The URIs to retrieve in the response.
	 */
	private final List<URI> mUris = new ArrayList<URI>(16);


	/**
	 * Specifies that all names and values of dead properties and the live properties defined by this document existing on the resource are to be returned. The
	 * default is <code>false</code>. If this is set to <code>true</code>, properties added with {@link #addProperty(ElementDescriptor)} will be added to the
	 * include element.
	 * 
	 * @param allProp
	 *            Whether to send an allprop request or not.
	 * @return This instance.
	 */
	public AddressbookMultiget setAllProp(boolean allProp)
	{
		mAllProp = allProp;
		return this;
	}


	/**
	 * Specifies that only a list of property names on the resource is to be returned. The default is <code>false</code>.
	 * 
	 * @param propNames
	 *            <code>true</code> to request the list of property names only, <code>false</code> to request property values too.
	 * @return This instance.
	 */
	public AddressbookMultiget setPropName(boolean propNames)
	{
		mPropName = propNames;
		return this;
	}


	/**
	 * Add the specified {@link URI} to the requested hrefs.
	 * 
	 * @param href
	 *            The {@link URI} to add.
	 * @return This instance.
	 */
	public AddressbookMultiget addHref(URI href)
	{
		mUris.add(href);
		return this;
	}


	/**
	 * Remove the specified {@link URI} from the requested hrefs.
	 * 
	 * @param href
	 *            The {@link URI} to remove.
	 * @return This instance.
	 */
	public AddressbookMultiget removeHref(URI href)
	{
		mUris.remove(href);
		return this;
	}


	/**
	 * Get the list of hrefs in this report. You're allowed to use this to modify the list of hrefs to request directly.
	 * 
	 * @return A {@link List} of {@link URI}s.
	 */
	public List<URI> getHrefs()
	{
		return mUris;
	}


	@Override
	public void recycle()
	{
		mAllProp = false;
		mPropName = false;
		if (mUris != null)
		{
			mUris.clear();
		}
		if (mProp != null)
		{
			mProp.clear();
		}
	}
}
