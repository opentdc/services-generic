package org.opentdc.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotImplementedException extends WebApplicationException {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

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
		logger.error(message + "; returning with Status " + status);
	}
}
