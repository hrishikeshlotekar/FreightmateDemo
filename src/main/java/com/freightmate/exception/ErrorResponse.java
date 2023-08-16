package com.freightmate.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hrishikesh.Lotekar
 * @implNote ErrorResponse class for custom error response messages.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

	@JsonProperty("Date-time")
	private LocalDateTime timeStamp;
	@JsonProperty("Error message")
	private String message;
	@JsonProperty("Field Errors")
	private List<String> fieldErrors = new ArrayList<>();

	public ErrorResponse() {
		super();

	}

	public ErrorResponse(LocalDateTime timeStamp, String message, List<String> fieldErrors) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
		this.fieldErrors = fieldErrors;
	}

	public ErrorResponse(LocalDateTime timeStamp, String message) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
	}

	public ErrorResponse( String message) {
		super();
		this.message = message;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public List<String> getFieldErrors() {
		return fieldErrors;
	}
}
