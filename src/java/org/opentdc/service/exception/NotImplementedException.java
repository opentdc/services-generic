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
package org.opentdc.service.exception;

import java.util.logging.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public class NotImplementedException extends WebApplicationException {
	private static final Logger logger = Logger.getLogger(NotImplementedException.class.getName());

	// version number for serializable class
	// (see java.io.Serializable and uids.txt)
	private static final long serialVersionUID = 4518202010101L;

	public NotImplementedException(Status status, String message) {
		// TODO: CXF3/Jax-RS2: super(message, status);
		super(status);
		log(status, message);
	}

	public NotImplementedException(String message) {
		// TODO: CXF3/Jax-RS2: super(message, Status.NOT_IMPLEMENTED);
		super(Status.NOT_IMPLEMENTED);
		log(Status.NOT_IMPLEMENTED, message);
	}

	public NotImplementedException() {
		// TODO: CXF3/Jax-RS2: super("Method is not implemented", Status.NOT_IMPLEMENTED);
		super(Status.NOT_IMPLEMENTED);
		log(Status.NOT_IMPLEMENTED, "Method is not implemented");
	}
	
	private void log(Status status, String message) {
		logger.severe(message + "; returning with Status " + status);
	}
}
