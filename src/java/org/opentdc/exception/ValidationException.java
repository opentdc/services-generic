package org.opentdc.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationException extends WebApplicationException {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// version number for serializable class
	// (see java.io.Serializable and uids.txt)
	private static final long serialVersionUID = 4518202010101L;

	public ValidationException(Status status, String message) {
		// TODO: CXF3/Jax-RS2: super(message, status);
		super(status);
		log(status, message);
	}

	public ValidationException(String message) {
		// TODO: CXF3/Jax-RS2: super(message, Status.BAD_REQUEST);
		super(Status.BAD_REQUEST);
		log(Status.BAD_REQUEST, message);
	}

	public ValidationException() {
		// TODO: CXF3/Jax-RS2: super("Validation failed", Status.BAD_REQUEST);
		super(Status.BAD_REQUEST);
		log(Status.BAD_REQUEST, "Validation failed");
	}
	
	private void log(Status status, String message) {
		logger.error(message + "; returning with Status " + status);
	}
}
