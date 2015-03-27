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

package org.dmfs.dav.rfc6578;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.dmfs.dav.PropertyRequest;
import org.dmfs.dav.rfc4918.MultiStatus;
import org.dmfs.dav.rfc4918.WebDav;
import org.dmfs.dav.rfc5323.WebDavSearch;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.builder.IObjectBuilder;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;


/**
 * sync-collection element as specified in <a href="http://tools.ietf.org/html/rfc6578">RFC 6578</a>.
 * <p>
 * Note: RFC 6578 extends the multistatus element by a sync-token element. For the sake of simplicity the {@link MultiStatus} class already contains a field for
 * the sync-token, so we don't have to override it here.
 * </p>
 * <p>
 * The sync-collecion request is defined as follows:
 * </p>
 * 
 * <pre>
 * Name:  sync-collection
 * 
 * Namespace:  DAV:
 * 
 * Purpose:  WebDAV report used to synchronize data between client and
 *    server.
 * 
 * Description:  See Section 3.
 * 
 * &lt;!ELEMENT sync-collection (sync-token, sync-level, limit?, prop)>
 * 
 * &lt;!-- DAV:limit defined in RFC 5323, Section 5.17 -->
 * &lt;!-- DAV:prop defined in RFC 4918, Section 14.18 -->
 * </pre>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class SyncCollection extends PropertyRequest
{
	/**
	 * {@link IObjectBuilder} to build and serialize {@link SyncCollection} instances.
	 */
	final static IObjectBuilder<SyncCollection> BUILDER = new AbstractObjectBuilder<SyncCollection>()
	{
		@SuppressWarnings("unchecked")
		@Override
		public <V> SyncCollection update(ElementDescriptor<SyncCollection> descriptor, SyncCollection object, ElementDescriptor<V> childDescriptor, V child,
			ParserContext context) throws XmlObjectPullParserException
		{
			if (childDescriptor == WebDavSync.SYNC_TOKEN)
			{
				object.mSyncToken = (String) child;
			}
			else if (childDescriptor == WebDavSync.SYNC_LEVEL)
			{
				object.mSyncLevel = (SyncLevel) child;
			}
			else if (childDescriptor == WebDav.PROP)
			{
				object.addProperty(childDescriptor);
			}
			else if (childDescriptor == WebDavSearch.LIMIT)
			{
				object.mLimit = (Map<ElementDescriptor<?>, Object>) child;
			}

			return object;
		}


		@Override
		public void writeChildren(ElementDescriptor<SyncCollection> descriptor, SyncCollection object, IXmlChildWriter childWriter, SerializerContext context)
			throws SerializerException, IOException
		{
			childWriter.writeChild(WebDavSync.SYNC_TOKEN, object.mSyncToken, context);
			childWriter.writeChild(WebDavSync.SYNC_LEVEL, object.mSyncLevel, context);
			if (object.mLimit != null && object.mLimit.size() > 0)
			{
				childWriter.writeChild(WebDavSearch.LIMIT, object.mLimit, context);
			}
			childWriter.writeChild(WebDav.PROP, object.mProp, context);
		};
	};

	/**
	 * The sync-token to sent. The default value is the empty sync-token to perform the inital sync.
	 */
	private String mSyncToken = "";

	/**
	 * The current sync-level. The default is {@link SyncLevel#one}.
	 */
	private SyncLevel mSyncLevel = SyncLevel.one;

	/**
	 * The limits.
	 */
	private Map<ElementDescriptor<?>, Object> mLimit = null;


	/**
	 * Set the sync token to send. A <code>null</code> value will cause this to send an empty sync-token.
	 * 
	 * @param syncToken
	 *            The sync token to send to the server, may be <code>null</code>.
	 * @return This instance.
	 */
	public SyncCollection setSyncToken(String syncToken)
	{
		mSyncToken = syncToken == null ? "" : syncToken;
		return this;
	}


	/**
	 * Set the sync-level. This has to be either {@link SyncLevel#infinite} or {@link SyncLevel#one}, the later one being the default value.
	 * 
	 * @param syncLevel
	 *            The new sync-level.
	 * @return This instance.
	 */
	public SyncCollection setSyncLevel(SyncLevel syncLevel)
	{
		mSyncLevel = syncLevel;

		return this;
	}


	/**
	 * Get the {@link SyncLevel}.
	 * 
	 * @return A {@link SyncLevel}.
	 */
	public SyncLevel getSyncLevel()
	{
		return mSyncLevel;
	}


	/**
	 * Limit the number of results in the response, if supported by the server. A non-positive value will remove the limit.
	 * 
	 * <p>
	 * <strong>Note:</strong> At present it's recommended to not use this feature. There are only a few servers that support it and some of them are broken and
	 * may return wrong results.
	 * </p>
	 * 
	 * @param limit
	 *            The maximum number of result in the response if this is a positive integer.
	 * @return This instance.
	 */
	public SyncCollection limitNumberOfResults(int limit)
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
	 * Returns the limit for the number of results in this request.
	 * 
	 * @return The limit or 0 if there is no limit.
	 */
	public int getNumberOfResultsLimit()
	{
		if (mLimit == null)
		{
			return 0;
		}
		Integer limit = (Integer) mLimit.get(WebDavSearch.NRESULTS);
		return limit == null ? 0 : limit;
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
			mLimit = new HashMap<ElementDescriptor<?>, Object>(6);
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


	@Override
	public void recycle()
	{
		mSyncToken = "";
		mSyncLevel = SyncLevel.one;
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
