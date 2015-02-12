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

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.builder.IObjectBuilder;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.Recyclable;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;


/**
 * Implements the propertyupdate object as specified in <a href="http://tools.ietf.org/html/rfc4918#section-14.20">RFC 4918 section 14.20</a>:
 * 
 * <pre>
 * Name:   propertyupdate
 * 
 * Purpose:   Contains a request to alter the properties on a resource.
 * 
 * Description:   This XML element is a container for the information
 *    required to modify the properties on the resource.
 * 
 * &lt;!ELEMENT propertyupdate (remove | set)+ >
 * </pre>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class PropertyUpdate implements Recyclable
{

	/**
	 * An {@link IObjectBuilder} to build and serialize {@link PropertyUpdate} objects.
	 */
	public final static IObjectBuilder<PropertyUpdate> BUILDER = new AbstractObjectBuilder<PropertyUpdate>()
	{
		public PropertyUpdate get(ElementDescriptor<PropertyUpdate> descriptor, PropertyUpdate recycle, ParserContext context)
			throws XmlObjectPullParserException
		{
			if (recycle != null)
			{
				recycle.recycle();
				return recycle;
			}

			return new PropertyUpdate();
		};


		@SuppressWarnings("unchecked")
		public <V extends Object> PropertyUpdate update(ElementDescriptor<PropertyUpdate> descriptor, PropertyUpdate object,
			ElementDescriptor<V> childDescriptor, V child, ParserContext context) throws XmlObjectPullParserException
		{
			if (childDescriptor == WebDav.SET)
			{
				if (object.mSet == null)
				{
					object.mSet = (Map<ElementDescriptor<?>, Object>) child;
				}
				else
				{
					object.mSet.putAll((Map<? extends ElementDescriptor<?>, ? extends Object>) child);
					context.recycle(WebDav.SET, (Map<ElementDescriptor<?>, Object>) child);
				}
			}
			if (childDescriptor == WebDav.REMOVE)
			{
				if (object.mRemove == null)
				{
					object.mRemove = (Map<ElementDescriptor<?>, Object>) child;
				}
				else
				{
					object.mRemove.putAll((Map<? extends ElementDescriptor<?>, ? extends Object>) child);
					context.recycle(WebDav.REMOVE, (Map<ElementDescriptor<?>, Object>) child);
				}
			}
			return object;
		};


		@Override
		public void writeChildren(ElementDescriptor<PropertyUpdate> descriptor, PropertyUpdate object, IXmlChildWriter childWriter, SerializerContext context)
			throws SerializerException, IOException
		{
			if (object.mSet != null && object.mSet.size() > 0)
			{
				childWriter.writeChild(WebDav.SET, object.mSet, context);
			}
			if (object.mRemove != null && object.mRemove.size() > 0)
			{
				childWriter.writeChild(WebDav.REMOVE, object.mRemove, context);
			}
		};
	};

	/**
	 * A map of new properties to set.
	 */
	private Map<ElementDescriptor<?>, Object> mSet;

	/**
	 * A map of properties to remove.
	 */
	private Map<ElementDescriptor<?>, Object> mRemove;


	/**
	 * Add a new property with a specific value to the resource.
	 * 
	 * @param property
	 *            The {@link ElementDescriptor} of the property to add.
	 * @param value
	 *            The value of the property.
	 */
	public <T> void set(ElementDescriptor<T> property, T value)
	{
		if (mSet == null)
		{
			mSet = new HashMap<ElementDescriptor<?>, Object>(16);
		}
		mSet.put(property, value);

		if (mRemove != null)
		{
			mRemove.remove(property);
		}
	}


	/**
	 * Remove a property from the resource.
	 * 
	 * @param property
	 *            The {@link ElementDescriptor} of the property to remove.
	 */
	public <T> void remove(ElementDescriptor<T> property)
	{
		if (mRemove == null)
		{
			mRemove = new HashMap<ElementDescriptor<?>, Object>(16);
		}
		mRemove.put(property, null);

		if (mSet != null)
		{
			mSet.remove(property);
		}
	}


	/**
	 * Clear the modification of given property, i.e. neither change nor remove the property.
	 * 
	 * @param property
	 *            The {@link ElementDescriptor} of the property to clear.
	 */
	public <T> void clear(ElementDescriptor<T> property)
	{
		if (mRemove != null)
		{
			mRemove.remove(property);
		}

		if (mSet != null)
		{
			mSet.remove(property);
		}
	}


	/**
	 * Returns an unmodifiable {@link Map} of values to set. The result may be <code>null</code> if there are no values to set.
	 * 
	 * @return An unmodifiable map of {@link ElementDescriptor}s to values.
	 */
	public Map<ElementDescriptor<?>, Object> getSet()
	{
		return mSet == null ? null : Collections.unmodifiableMap(mSet);
	}


	/**
	 * Get the set of {@link ElementDescriptor} of values to remove.
	 * 
	 * @return An unmodifiable {@link Set} of {@link ElementDescriptor}s.
	 */
	public Set<ElementDescriptor<?>> getRemoved()
	{
		return mRemove == null ? null : Collections.unmodifiableSet(mRemove.keySet());
	}


	@Override
	public void recycle()
	{
		if (mRemove != null)
		{
			mRemove.clear();
		}
		if (mSet != null)
		{
			mSet.clear();
		}
	}
}
