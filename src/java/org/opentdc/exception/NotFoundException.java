package org.opentdc.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotFoundException extends WebApplicationException {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	// version number for serializable class
	// (see java.io.Serializable and uids.txt)
	private static final long serialVersionUID = 4518202010103L;

	public NotFoundException(Status status, String message) {
		// TODO: CXF3/Jax-RS2: super(message, status);
		super(status);
		log(status, message);
	}

	public NotFoundException(String message) {
		// TODO: CXF3/Jax-RS2: super(message, Status.NOT_FOUND);
		super(Status.NOT_FOUND);
		log(Status.NOT_FOUND, message);

	}

	public NotFoundException() {
		// TODO: CXF3/Jax-RS2: super("Object not found", Status.NOT_FOUND);
		super(Status.NOT_FOUND);
		log(Status.NOT_FOUND, "Object not found");		
	}
	
	private void log(Status status, String message) {
		logger.error(message + "; returning with Status " + status);
	}
}
