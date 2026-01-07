package com.example.timetable.controller;

import com.example.timetable.exception.ResourceNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
    logger.warn("Resource not found: {}", ex.getMessage());
    Map<String, Object> body = new HashMap<>();
    body.put("code", "NOT_FOUND");
    body.put("message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<Map<String, Object>> handleNoHandlerFound(NoHandlerFoundException ex) {
    logger.error("No handler found for request: {} {}", ex.getHttpMethod(), ex.getRequestURL());
    Map<String, Object> body = new HashMap<>();
    body.put("code", "ENDPOINT_NOT_FOUND");
    body.put("message", "The requested endpoint does not exist");
    body.put("requestedMethod", ex.getHttpMethod());
    body.put("requestedURL", ex.getRequestURL().toString());
    body.put("hint", "Check the API endpoint URL. Available endpoints are under /api/*");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<Map<String, Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
    logger.error("Method not supported: {} for URL", ex.getMethod());
    Map<String, Object> body = new HashMap<>();
    body.put("code", "METHOD_NOT_SUPPORTED");
    body.put("message", "The HTTP method is not supported for this endpoint");
    body.put("requestedMethod", ex.getMethod());
    body.put("supportedMethods", ex.getSupportedHttpMethods());
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(body);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
    logger.warn("Validation failed: {}", ex.getMessage());
    Map<String, Object> body = new HashMap<>();
    body.put("code", "VALIDATION_ERROR");
    body.put("message", "Validation failed");
    body.put("details", ex.getBindingResult().getAllErrors());
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException ex) {
    logger.error("Illegal state: {}", ex.getMessage());
    Map<String, Object> body = new HashMap<>();
    body.put("code", "ILLEGAL_STATE");
    body.put("message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
    logger.error("Unexpected error: ", ex);
    Map<String, Object> body = new HashMap<>();
    body.put("code", "INTERNAL_ERROR");
    body.put("message", "An unexpected error occurred");
    body.put("error", ex.getClass().getSimpleName());
    body.put("hint", "Check server logs for more details. Ensure the backend is running on port 8080.");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }
}
