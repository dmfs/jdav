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

package org.dmfs.dav;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.pull.Recyclable;


/**
 * An abstract request to request certain properties from the server. It maintains a list of properties and provides methods to add or remove properties.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public abstract class PropertyRequest implements Recyclable
{

	protected Map<ElementDescriptor<?>, Object> mProp;


	/**
	 * Add another property to the list of requested properties.
	 * 
	 * @param property
	 *            The property to request from the server.
	 * @return This instance.
	 */
	public PropertyRequest addProperty(ElementDescriptor<?> property)
	{
		if (mProp == null)
		{
			mProp = new HashMap<ElementDescriptor<?>, Object>(16);
		}
		mProp.put(property, null);
		return this;
	}


	/**
	 * Remove a property from the list of requested properties.
	 * 
	 * @param property
	 *            The property to remove.
	 * @return This instance.
	 */
	public PropertyRequest removeProperty(ElementDescriptor<?> property)
	{
		mProp.remove(property);
		return this;
	}


	/**
	 * Returns an modifyable {@link Set} of properties that have been requested. The result may be <code>null</code> if there are no properties.
	 * 
	 * @return A {@link Set} of {@link ElementDescriptor}s, one for each requested property or <code>null</code>.
	 */
	public Set<ElementDescriptor<?>> getProperties()
	{
		return mProp == null ? null : Collections.unmodifiableSet(mProp.keySet());
	}
}
