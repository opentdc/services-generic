package org.opentdc.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DuplicateException extends WebApplicationException {
	// version number for serializable class
	// (see java.io.Serializable and uids.txt)
	private static final long serialVersionUID = 4518202010102L;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public DuplicateException(Status status, String message) {
		// TODO: CXF3/Jax-RS2: super(message, status);
		super(status);
		log(status, message);
	}

	public DuplicateException(String message) {
		// TODO: CXF3/Jax-RS2: super(message, Status.CONFLICT);
		super(Status.CONFLICT);
		log(Status.CONFLICT, message);
	}

	public DuplicateException() {
		// TODO: CXF3/Jax-RS2: super("Object is already stored", status);
		super(Status.CONFLICT);
		log(Status.CONFLICT, "Object is already stored");
	}
	
	private void log(Status status, String message) {
		logger.error(message + "; returning with Status " + status);
	}
}
