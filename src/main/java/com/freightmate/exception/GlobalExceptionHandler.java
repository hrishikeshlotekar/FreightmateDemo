package com.freightmate.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hrishikesh.Lotekar
 * @implNote ControllerAdvice for handling exceptions and resolve with custom messages.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

	/**
	 * Description : To handle ResourceNotFoundException exceptions
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> resourceNotFoundException(Exception ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), null);
		return handleExceptionInternal(ex, errorResponse, HttpHeaders.EMPTY, HttpStatus.NOT_FOUND, request);
	}

	/**
	 * Description : To handle Method Argument If not valid
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldError = result.getFieldErrors();
		List<String> errors = new ArrayList<>();
		for (FieldError error : fieldError) {
			errors.add(error.getDefaultMessage());
		}

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), "Bad Request", errors);

		return handleExceptionInternal(ex, errorResponse, headers, status, request);
	}

	/**
	 * Description : To handle Http Method Type Request
	 */
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported() {

		return new ResponseEntity<>("Please Change Http Method Type Request", HttpStatus.NOT_FOUND);
	}

	/**
	 * Description : To handle IllegalArgumentException exception
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(RuntimeException e, HttpServletRequest request) {
		log.error("IllegalArgumentException error occurred.", e);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
	}

	/**
	 * Description : To handle Exceptions
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAllException(RuntimeException e, HttpServletRequest request) {
		log.error("Unexpected error occurred.", e);
		return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse("INTERNAL_SERVER_ERROR"));
	}

}