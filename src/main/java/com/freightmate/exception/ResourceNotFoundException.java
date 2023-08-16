package com.freightmate.exception;

/**
 * @author Hrishikesh.Lotekar
 * @implNote ResourceNotFoundException class for handling exception and resolve with custom messages.
 */
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);

	}

}
