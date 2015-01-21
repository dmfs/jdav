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
import java.util.Map;

import org.dmfs.dav.PropertyRequest;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.builder.IObjectBuilder;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;


/**
 * Represents the propfind request as defined in <a href="https://tools.ietf.org/html/rfc4918#section-9.1">RFC 4918, section 9.1</a> and <a
 * href="https://tools.ietf.org/html/rfc4918#section-14.20">RFC 4918, section 14.20</a>. It extends the former definition in <a
 * href="http://tools.ietf.org/html/rfc2518#section-12.14">RFC 2518, section 12.14</a> in that it adds an include element that allows to specify additional
 * properties when using <code>DAV:allprop</code>.
 * 
 * <pre>
 * Name:   propfind
 * 
 * Purpose:   Specifies the properties to be returned from a PROPFIND
 *    method.  Four special elements are specified for use with
 *    'propfind': 'prop', 'allprop', 'include', and 'propname'.  If
 *    'prop' is used inside 'propfind', it MUST NOT contain property
 *    values.
 * 
 * <!ELEMENT propfind ( propname | (allprop, include?) | prop ) >
 * </pre>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class PropFind extends PropertyRequest
{
	/**
	 * An {@link IObjectBuilder} to parse and serialize propfind objects.
	 * 
	 * <p>
	 * TODO: at present this wouldn't throw if &lt;DAV:prop> and &lt;DAV:allprop> or &lt;DAV:propname> are present in the same XML document. It also allows
	 * &lt;DAV:include> to be used with all the other elements. In strict mode we should throw an exception in that case.
	 * </p>
	 */
	public final static IObjectBuilder<PropFind> BUILDER = new AbstractObjectBuilder<PropFind>()
	{
		public PropFind get(ElementDescriptor<PropFind> descriptor, PropFind recycle, ParserContext context) throws XmlObjectPullParserException
		{
			if (recycle != null)
			{
				recycle.recycle();
				return recycle;
			}
			return new PropFind();
		};


		@SuppressWarnings("unchecked")
		public <V extends Object> PropFind update(ElementDescriptor<PropFind> descriptor, PropFind object, ElementDescriptor<V> childDescriptor, V child,
			ParserContext context) throws XmlObjectPullParserException
		{
			if (childDescriptor == WebDav.PROP || childDescriptor == WebDav.INCLUDE)
			{
				if (object.mProp == null)
				{
					object.mProp = (Map<ElementDescriptor<?>, Object>) child;
				}
				else
				{
					object.mProp.putAll((Map<ElementDescriptor<?>, Object>) child);
				}
			}
			else if (childDescriptor == WebDav.ALLPROP)
			{
				object.mAllProp = true;
			}
			else if (childDescriptor == WebDav.PROPNAME)
			{
				object.mPropName = true;
			}
			return object;
		};


		@Override
		public void writeChildren(ElementDescriptor<PropFind> descriptor, PropFind object, IXmlChildWriter childWriter, SerializerContext context)
			throws SerializerException, IOException
		{
			if (object.mPropName)
			{
				childWriter.writeChild(WebDav.PROPNAME, null);
			}
			else if (object.mAllProp)
			{
				childWriter.writeChild(WebDav.ALLPROP, null);
				if (object.mProp != null && object.mProp.size() > 0)
				{
					childWriter.writeChild(WebDav.INCLUDE, object.mProp);
				}
			}
			else
			{
				childWriter.writeChild(WebDav.PROP, object.mProp);
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
	 * Specifies that all names and values of dead properties and the live properties defined by this document existing on the resource are to be returned. The
	 * default is <code>false</code>. If this is set to <code>true</code>, properties added with {@link #addProperty(ElementDescriptor)} will be added to the
	 * include element.
	 * 
	 * @param allProp
	 *            Whether to send an allprop request or not.
	 * @return This instance.
	 * 
	 * @see #addProperty(ElementDescriptor)
	 */
	public PropFind setAllProp(boolean allProp)
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
	public PropFind setPropName(boolean propNames)
	{
		mPropName = propNames;
		return this;
	}


	@Override
	public void recycle()
	{
		mAllProp = false;
		mPropName = false;
		if (mProp != null)
		{
			mProp.clear();
		}
	}
}
