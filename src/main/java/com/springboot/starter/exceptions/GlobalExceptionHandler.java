package com.springboot.starter.exceptions;

import com.springboot.starter.models.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        Response errorResponse = new Response(HttpStatus.NOT_FOUND, ex.getMessage(), request.getDescription(false), false);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        Response errorResponse = new Response(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getDescription(false), false);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

}
