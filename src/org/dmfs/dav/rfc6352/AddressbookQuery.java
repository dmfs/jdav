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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dmfs.dav.PropertyRequest;
import org.dmfs.dav.rfc4791.filter.CompFilter;
import org.dmfs.dav.rfc4918.PropFind;
import org.dmfs.dav.rfc4918.WebDav;
import org.dmfs.dav.rfc5323.WebDavSearch;
import org.dmfs.dav.rfc6352.filter.PropFilter;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.builder.IObjectBuilder;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;


/**
 * A special request to search contacts in an address book. It's similar to a {@link PropFind} request, but also allows to set a filter and a limit.
 * 
 * <p>
 * TODO: finish filters
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class AddressbookQuery extends PropertyRequest
{
	/**
	 * An {@link IObjectBuilder} to serialize addressbook-query objects to XML.
	 */
	public final static IObjectBuilder<AddressbookQuery> BUILDER = new AbstractObjectBuilder<AddressbookQuery>()
	{
		@Override
		public void writeChildren(ElementDescriptor<AddressbookQuery> descriptor, AddressbookQuery object, IXmlChildWriter childWriter,
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

			if (object.mFilter != null && object.mFilter.size() > 0)
			{
				childWriter.writeChild(CardDav.FILTER, object.mFilter);
			}

			if (object.mLimit != null && object.mLimit.size() > 0)
			{
				childWriter.writeChild(WebDavSearch.LIMIT, object.mLimit);
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
	 * The limits.
	 */
	private Map<ElementDescriptor<?>, Object> mLimit = null;

	/**
	 * The list of filters.
	 */
	private List<PropFilter> mFilter;


	/**
	 * Specifies that all names and values of dead properties and the live properties defined by this document existing on the resource are to be returned. The
	 * default is <code>false</code>. If this is set to <code>true</code>, properties added with {@link #addProperty(ElementDescriptor)} will be added to the
	 * include element.
	 * 
	 * @param allProp
	 *            Whether to send an allprop request or not.
	 * @return This instance.
	 */
	public AddressbookQuery setAllProp(boolean allProp)
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
	public AddressbookQuery setPropName(boolean propNames)
	{
		mPropName = propNames;
		return this;
	}


	/**
	 * Limit the number of results in the response, if supported by the server. A negative value will remove the limit.
	 * 
	 * @param limit
	 *            The maximum number of result in the response if this is a positive integer.
	 * @return This instance.
	 */
	public AddressbookQuery limitNumberOfResults(int limit)
	{
		if (limit > 0)
		{
			addLimit(WebDavSearch.NRESULTS, limit);
		}
		else
		{
			removeLimit(WebDavSearch.NRESULTS);
		}
		return this;
	}


	/**
	 * Add a limit to the request.
	 * 
	 * @param descriptor
	 *            The {@link ElementDescriptor} of the limit element.
	 * @param limit
	 *            The limit value.
	 */
	private <T> void addLimit(ElementDescriptor<T> descriptor, T limit)
	{
		if (mLimit == null)
		{
			mLimit = new HashMap<ElementDescriptor<?>, Object>(8);
		}
		mLimit.put(descriptor, limit);
	}


	/**
	 * Remove a limit from the request.
	 * 
	 * @param descriptor
	 *            The {@link ElementDescriptor} of the limit to remove.
	 */
	private void removeLimit(ElementDescriptor<?> descriptor)
	{
		if (mLimit != null)
		{
			mLimit.remove(descriptor);
		}
	}


	/**
	 * Adds a {@link PropFilter} to send with the request.
	 * 
	 * @param filter
	 *            A {@link CompFilter}.
	 */
	public void addFilter(PropFilter filter)
	{
		if (mFilter == null)
		{
			mFilter = new ArrayList<PropFilter>(8);
		}

		mFilter.add(filter);
	}


	@Override
	public void recycle()
	{
		mAllProp = false;
		mPropName = false;
		if (mLimit != null)
		{
			mLimit.clear();
		}
		if (mProp != null)
		{
			mProp.clear();
		}
	}

}
