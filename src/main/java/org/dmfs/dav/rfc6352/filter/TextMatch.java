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
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlAttributeWriter;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;

import java.io.IOException;


/**
 * A text-match filter. This filter matches a text value with options to match case insensitive and to negate the match condition.
 */
public final class TextMatch extends CardDavFilter
{
    public enum MatchType
    {
        equals, contains, starts_with
        {
            @Override
            public String toString()
            {
                return "starts-with";
            }
        },
        ends_with
            {
                @Override
                public String toString()
                {
                    return "ends-with";
                }
            };
    }


    private final static QualifiedName ATTRIBUTE_COLLATION = QualifiedName.get("collation");
    private final static QualifiedName ATTRIBUTE_NEGATE_CONDITION = QualifiedName.get("negate-condition");
    private final static QualifiedName ATTRIBUTE_MATCH_TYPE = QualifiedName.get("match-type");

    /**
     * The default collation that must be supported by every CalDAV server.
     */
    public final static String COLLATION_I_UNICODE_CASEMAP = "i;unicode-casemap";

    public final static ElementDescriptor<TextMatch> DESCRIPTOR = ElementDescriptor.register(QualifiedName.get(NAMESPACE, "text-match"),
        new AbstractObjectBuilder<TextMatch>()
        {
            @Override
            public void writeAttributes(ElementDescriptor<TextMatch> descriptor, TextMatch object, IXmlAttributeWriter attributeWriter,
                SerializerContext context) throws SerializerException, IOException
            {
                attributeWriter.writeAttribute(ATTRIBUTE_NEGATE_CONDITION, object.negate ? "yes" : "no", context);

                if (object.collation != null)
                {
                    attributeWriter.writeAttribute(ATTRIBUTE_COLLATION, object.collation, context);
                }

                attributeWriter.writeAttribute(ATTRIBUTE_MATCH_TYPE, object.matchType.toString(), context);
            }


            ;


            @Override
            public void writeChildren(ElementDescriptor<TextMatch> descriptor, TextMatch object, IXmlChildWriter childWriter, SerializerContext context)
                throws SerializerException, IOException
            {
                childWriter.writeText(object.value, context);
            }


            ;
        });

    /**
     * The text value to match.
     */
    public final String value;

    /**
     * Whether to match case insensitive or not.
     */
    public final String collation;

    /**
     * Whether to negate the confition, i.e. only fields that not contain {@link #value} match.
     */
    public final boolean negate;

    public final MatchType matchType;


    /**
     * Creates a non-negated, case-insensitive text-match filter, using the default collation {@link #COLLATION_I_UNICODE_CASEMAP}.
     *
     * @param value
     *     The filter value.
     */
    public TextMatch(String value)
    {
        this(value, false, true);
    }


    /**
     * Create a case-insensitive text-match filter, using the default collation {@link #COLLATION_I_UNICODE_CASEMAP}.
     *
     * @param value
     *     The filter value.
     * @param negate
     *     Negate the search condition (i.e. return only results that do not match the value).
     */
    public TextMatch(String value, boolean negate)
    {
        this(value, negate, true);
    }


    /**
     * Create a text-match filter.
     *
     * @param value
     *     The filter value.
     * @param negate
     *     Negate the search condition (i.e. return only results that do not match the value).
     * @param caseInsensitive
     *     Perform case-insensitive matching using the default collation {@link #COLLATION_I_UNICODE_CASEMAP}.
     */
    public TextMatch(String value, boolean negate, boolean caseInsensitive)
    {
        this(value, negate, caseInsensitive ? COLLATION_I_UNICODE_CASEMAP : null, MatchType.equals);
    }


    /**
     * Create a text-match filter.
     *
     * @param value
     *     The filter value.
     * @param negate
     *     Negate the search condition (i.e. return only results that do not match the value).
     * @param collation
     *     The collation to use, see <a href="http://tools.ietf.org/html/rfc4790">RFC 4790</a> and <a
     *     href="http://tools.ietf.org/html/rfc4791#section-7.5.1">supported-collation-set</a>.
     */
    public TextMatch(String value, boolean negate, String collation, MatchType matchType)
    {
        this.value = value;
        this.negate = negate;
        this.collation = collation;
        this.matchType = matchType;
    }


    @Override
    public ElementDescriptor<? extends FilterBase> getElementDescriptor()
    {
        return DESCRIPTOR;
    }

}
