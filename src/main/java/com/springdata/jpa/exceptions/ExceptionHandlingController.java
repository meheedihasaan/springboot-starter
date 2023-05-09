package com.springdata.jpa.exceptions;

import com.springdata.jpa.models.Response;
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
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "No " +className+ " found.", null, false);
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
