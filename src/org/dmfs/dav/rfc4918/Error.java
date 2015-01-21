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

import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.builder.IObjectBuilder;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.Recyclable;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;


/**
 * Implements the error element as defined in <a href="http://tools.ietf.org/html/rfc4918#section-14.5">RFC 4918 section 14.5</a>.
 * 
 * <p>
 * TODO: store any pre- or postcondition and the actual error message. At present we don't store anything inside of that element. The problem is that is allows
 * any content, which makes it difficult to map to Java Objects. We probably need a generic XmlNode Object that stores any attributes, child nodes and text.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class Error implements Recyclable
{
	/**
	 * An {@link IXmlObjectBuilder} for {@link Error} elements.
	 */
	public final static IObjectBuilder<Error> BUILDER = new AbstractObjectBuilder<Error>()
	{
		@Override
		public Error get(ElementDescriptor<Error> descriptor, Error recycle, ParserContext context) throws XmlObjectPullParserException
		{
			if (recycle != null)
			{
				recycle.recycle();
				return recycle;
			}
			return new Error();
		};
	};


	@Override
	public void recycle()
	{

	}
}
