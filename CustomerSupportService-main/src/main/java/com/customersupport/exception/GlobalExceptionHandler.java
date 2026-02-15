package com.customersupport.exception;

//src/main/java/com/insurance/support/exception/GlobalExceptionHandler.java

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

 @ExceptionHandler(NotFoundException.class)
 public ResponseEntity<ApiError> handleNotFound(NotFoundException ex, HttpServletRequest req) {
     ApiError err = new ApiError(Instant.now(), 404, "Not Found", ex.getMessage(), req.getRequestURI());
     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
 }

 @ExceptionHandler(DownstreamServiceException.class)
 public ResponseEntity<ApiError> handleDownstream(DownstreamServiceException ex, HttpServletRequest req) {
     ApiError err = new ApiError(Instant.now(), ex.getStatus().value(), "Downstream Error",
             ex.getMessage(), req.getRequestURI());
     return ResponseEntity.status(ex.getStatus()).body(err);
 }

 @ExceptionHandler(Exception.class)
 public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
     ApiError err = new ApiError(Instant.now(), 500, "Internal Server Error",
             ex.getMessage(), req.getRequestURI());
     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
 }
}