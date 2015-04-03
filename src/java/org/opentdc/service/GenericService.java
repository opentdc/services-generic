/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Arbalo AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.opentdc.service;

import java.lang.reflect.Constructor;

import javax.servlet.ServletContext;

public abstract class GenericService<T> {
	
	// query: a semicolon-separated list of query verbs. e.g. modifiedAt();lessThan(3);orderByFirstName();ascending()
	protected static final String DEFAULT_QUERY = "";
	// queryType: specifies the type of objects to be returned
	// TODO: how to make queryTypes generic, i.e. independent of serviceProviders ?
	protected static final String DEFAULT_QUERY_TYPE = "";
	// position: the result set starts from the given position. 
	// Increasing the position by a batch size allows to iterate the result set.
	// hasMore=false indicates that there are no more objects to be returned
	protected static final String DEFAULT_POSITION = "0";
	// size: specifies the batch size, i.e. the amount of records returned starting from position.
	protected static final String DEFAULT_SIZE = "25";
	
	/**
	 * Return service provider implementation configured on context.
	 * 
	 * @param serviceType
	 * @param context
	 * @return
	 * @throws ReflectiveOperationException
	 */
	public T getServiceProvider(
		Class<? extends GenericService<?>> serviceProviderClass,
		ServletContext context
	) throws ReflectiveOperationException {
		String serviceProviderName = context.getInitParameter(serviceProviderClass.getSimpleName() + ".serviceProvider");
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) Class.forName(serviceProviderName);
		Constructor<T> c = clazz.getConstructor(
			ServletContext.class,
			String.class
		);
		return c.newInstance(
			context, 
			serviceProviderClass.getSimpleName()
		);
	}

}
