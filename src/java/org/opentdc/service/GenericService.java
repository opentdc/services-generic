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
	
	/**
	 * Return service provider implementation configured on context.
	 * 
	 * @param serviceType
	 * @param context
	 * @return
	 * @throws ReflectiveOperationException
	 */
	public T getServiceProvider(
		ServletContext context
	) throws ReflectiveOperationException {
		String serviceProvider = context.getInitParameter("serviceProvider");
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) Class.forName(serviceProvider);
		Constructor<T> c = clazz.getConstructor(ServletContext.class);
		return c.newInstance(context);
	}

}
