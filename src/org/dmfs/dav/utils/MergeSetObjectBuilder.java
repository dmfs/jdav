/*
 * Copyright (C) 2015 Marten Gajda <marten@dmfs.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package org.dmfs.dav.utils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.dmfs.dav.rfc3253.WebDavVersioning;
import org.dmfs.dav.rfc3744.WebDavAcl;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;


/**
 * This is a builder that merges {@link Set}s built by child element builders into one set of the same type. However, When serializing each element will be
 * written as a separate set with just one element.
 * <p>
 * The purpose of this builder is to support servers that return broken responses for certain set properties, like
 * {@link WebDavAcl.Properties#CURRENT_USER_PRIVILEGE_SET} or {@link WebDavVersioning.Properties#SUPPORTED_REPORT_SET}.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class MergeSetObjectBuilder<T> extends AbstractObjectBuilder<Set<T>>
{
	private final static int DEFAULT_INITIAL_CAPACITY = 16;

	private final ElementDescriptor<Set<T>> mElementSetDescriptor;

	private final int mInitialCapacity;


	public MergeSetObjectBuilder(ElementDescriptor<Set<T>> setElementDescriptor)
	{
		this(setElementDescriptor, DEFAULT_INITIAL_CAPACITY);
	}


	public MergeSetObjectBuilder(ElementDescriptor<Set<T>> elementSetDescriptor, int initialCapacity)
	{
		mElementSetDescriptor = elementSetDescriptor;
		mInitialCapacity = initialCapacity;
	}


	@Override
	public Set<T> get(ElementDescriptor<Set<T>> descriptor, Set<T> recycle, ParserContext context)
	{
		if (recycle != null)
		{
			recycle.clear();
			return recycle;
		}
		else
		{
			return new HashSet<T>(mInitialCapacity);
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public <V> Set<T> update(ElementDescriptor<Set<T>> descriptor, Set<T> object, ElementDescriptor<V> childDescriptor, V child, ParserContext context)
		throws XmlObjectPullParserException
	{
		if (childDescriptor == mElementSetDescriptor)
		{
			if (child != null)
			{
				// absorb all elements and recycle the set
				object.addAll((Set<T>) child);
				context.recycle(childDescriptor, child);
			}
		}
		return object;
	}


	@Override
	public void writeChildren(ElementDescriptor<Set<T>> descriptor, Set<T> object, IXmlChildWriter childWriter, SerializerContext context)
		throws SerializerException, IOException
	{
		if (object != null)
		{
			// put each element into a separate set when writing
			Set<T> helper = new HashSet<T>(1);
			for (T element : object)
			{
				helper.clear();
				helper.add(element);
				childWriter.writeChild(mElementSetDescriptor, helper, context);
			}
		}
	}

}
