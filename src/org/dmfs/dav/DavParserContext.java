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

import org.dmfs.httpclientinterfaces.HttpStatus;
import org.dmfs.xmlobjects.pull.ParserContext;


/**
 * A {@link ParserContext} for DAV responses.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class DavParserContext extends ParserContext
{
	private boolean mStrict = true;
	private boolean mKeepNotFoundProperties = false;


	/**
	 * Create a new {@link DavParserContext}.
	 */
	public DavParserContext()
	{
		mStrict = true;
	}


	/**
	 * Enable or disable strict parsing. Strict parsing will throw an error in some cases when the response is malformed. If strict parsing is not enabled the
	 * parser is supposed to make assumptions about the intent and continue if possible.
	 * 
	 * @param strict
	 *            <code>true</code> to be strict with errors, <code>false</code> otherwise.
	 * @return
	 */
	public DavParserContext setStrict(boolean strict)
	{
		mStrict = strict;
		return this;
	}


	/**
	 * Return whether stict parsing has been requested or not.
	 * 
	 * @return <code>true</code> to be strict with issues.
	 */
	public boolean isStrict()
	{
		return mStrict;
	}


	/**
	 * Set whether to allow dropping propstat elements with status {@link HttpStatus#NOT_FOUND}. In many cases there is no benefit in keeping them, so the
	 * default is not to store them.
	 * 
	 * @param keepNotFoundProperties
	 *            <code>true</code> to keep propstat elements with status {@link HttpStatus#NOT_FOUND}.
	 */
	public DavParserContext setKeepNotFoundProperties(boolean keepNotFoundProperties)
	{
		mKeepNotFoundProperties = keepNotFoundProperties;
		return this;
	}


	/**
	 * Returns whether to keep properties with status {@link HttpStatus#NOT_FOUND} or not.
	 * 
	 * @return <code>true</code> to keep such properties, <code>false</code> otherwise.
	 */
	public boolean getKeepNotFoundProperties()
	{
		return mKeepNotFoundProperties;
	}
}
