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

package org.dmfs.dav.rfc6352.filter;

import org.dmfs.dav.FilterBase;
import org.dmfs.dav.rfc6352.CardDav;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.QualifiedNameObjectBuilder;


/**
 * The base class of all filters in the CardDAV namespace.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public abstract class CardDavFilter extends FilterBase
{
	/**
	 * The CalDAV namespace. It's repeated here for the sake of simplicity.
	 */
	public final static String NAMESPACE = CardDav.NAMESPACE;

	/**
	 * A special filter that matches when a structured element (i.e. a {@link PropFilter}) is not present. This is for internal use.
	 */
	final static ElementDescriptor<QualifiedName> FILTER_ISNOTDEFINED = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "is-not-defined"),
		QualifiedNameObjectBuilder.INSTANCE);

}
