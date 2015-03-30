package org.opentdc.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InternalServerErrorException  extends WebApplicationException {
	// version number for serializable class
	// (see java.io.Serializable and uids.txt)
	private static final long serialVersionUID = 4518202010105L;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public InternalServerErrorException(Status status, String message) {
		// TODO: CXF3/Jax-RS2: super(message, status);
		super(status);
		log(status, message);
	}

	public InternalServerErrorException(String message) {
		// TODO: CXF3/Jax-RS2: super(message, Status.CONFLICT);
		super(Status.INTERNAL_SERVER_ERROR);
		log(Status.INTERNAL_SERVER_ERROR, message);
	}

	public InternalServerErrorException() {
		// TODO: CXF3/Jax-RS2: super("Object is already stored", status);
		super(Status.INTERNAL_SERVER_ERROR);
		log(Status.INTERNAL_SERVER_ERROR, "Internal Server Error");
	}
	
	private void log(Status status, String message) {
		logger.error(message + "; returning with Status " + status);
	}
}
