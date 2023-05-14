package com.springboot.starter.exceptions;

import com.springboot.starter.models.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        String className = ex.getClassName().getSimpleName().toLowerCase();
        String message = "No " +className+ " found.";
        return buildResponseEntity(HttpStatus.BAD_REQUEST, message, null, false);
    }

    @ResponseBody
    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<Object> handleResponseException(ResponseException ex) {
        HttpStatus httpStatus = ex.getHttpStatus();
        String message = ex.getMessage();
        Object payload = ex.getPayload();
        return buildResponseEntity(httpStatus != null ? httpStatus : HttpStatus.BAD_REQUEST, message, payload, false);
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String message, Object payload, boolean success) {
        HttpStatus httpStatus;
        try {
            httpStatus = HttpStatus.valueOf(status.value());
        }
        catch (Exception ex) {
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        Response errorResponse = new Response(httpStatus, message, payload, success);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

}
