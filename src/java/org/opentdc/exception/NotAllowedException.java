package org.opentdc.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotAllowedException extends WebApplicationException {
		// version number for serializable class
		// (see java.io.Serializable and uids.txt)
		private static final long serialVersionUID = 4518202010106L;
		protected Logger logger = LoggerFactory.getLogger(this.getClass());

		public NotAllowedException(Status status, String message) {
			// TODO: CXF3/Jax-RS2: super(message, status);
			super(status);
			log(status, message);
		}

		public NotAllowedException(String message) {
			// TODO: CXF3/Jax-RS2: super(message, Status.CONFLICT);
			super(Status.METHOD_NOT_ALLOWED);
			log(Status.METHOD_NOT_ALLOWED, message);
		}

		public NotAllowedException() {
			// TODO: CXF3/Jax-RS2: super("Object is already stored", status);
			super(Status.METHOD_NOT_ALLOWED);
			log(Status.METHOD_NOT_ALLOWED, "Method is not allowed");
		}
		
		private void log(Status status, String message) {
			logger.error(message + "; returning with Status " + status);
		}
	}
