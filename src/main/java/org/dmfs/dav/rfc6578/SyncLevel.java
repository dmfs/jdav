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

import org.dmfs.dav.rfc4918.Depth;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.builder.IObjectBuilder;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;

import java.io.IOException;


/**
 * Defines the set of valid sync levels.
 * <p>
 * Note: the sync level {@link #one} is serialized as <code>1</code>. To get a {@link SyncLevel} from a {@link String} you have to use {@link #get(String)},
 * otherwise <code>"1"</code> will not map correctly to {@link #one}.
 * </p>
 * <p>
 * {@link SyncLevel} is similar to {@link Depth}, but it doesn't support a value of <code>0</code>.
 * </p>
 *
 * @see <a href="https://tools.ietf.org/html/rfc6578#section-6.3">RFC 6578, section 6.3</a>
 */
public enum SyncLevel
{
    /**
     * The infinite sync level.
     */
    infinite,

    /**
     * Sync level 1.
     */
    one
        {
            @Override
            public String toString()
            {
                return "1";
            }
        };

    /**
     * An {@link IObjectBuilder} that knows how to build and serialize a {@link SyncLevel}.
     */
    final static IObjectBuilder<SyncLevel> BUILDER = new AbstractObjectBuilder<SyncLevel>()
    {
        public SyncLevel update(ElementDescriptor<SyncLevel> descriptor, SyncLevel object, String text, ParserContext context)
            throws XmlObjectPullParserException
        {
            try
            {
                // don't forget to use get(String) instead of valueOf(String)!
                return SyncLevel.get(text);
            }
            catch (IllegalArgumentException e)
            {
                throw new XmlObjectPullParserException("invalid sync-level value " + text, e);
            }
        }


        ;


        public void writeChildren(ElementDescriptor<SyncLevel> descriptor, SyncLevel object, IXmlChildWriter childWriter, SerializerContext context)
            throws SerializerException, IOException
        {
            childWriter.writeText(object.toString(), context);
        }


        ;
    };


    /**
     * Get a {@link SyncLevel} from a {@link String}. In contrast to {@link #valueOf(String)} this method returns {@link #one} for <code>"1"</code>.
     *
     * @param value
     *     The sync level string.
     *
     * @return A {@link SyncLevel}.
     */
    public static SyncLevel get(String value)
    {
        if (value != null && value.length() == 1 && value.charAt(0) == '1')
        {
            return one;
        }
        else
        {
            return SyncLevel.valueOf(value);
        }
    }
}
