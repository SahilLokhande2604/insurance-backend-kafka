package com.customersupport.exception;

//src/main/java/com/insurance/support/exception/DownstreamServiceException.java

import org.springframework.http.HttpStatusCode;

public class DownstreamServiceException extends RuntimeException {
 private final HttpStatusCode status;
 public DownstreamServiceException(String msg, HttpStatusCode httpStatusCode) {
     super(msg);
     this.status = httpStatusCode;
 }
 
 public HttpStatusCode getStatus() { return status; }
}
