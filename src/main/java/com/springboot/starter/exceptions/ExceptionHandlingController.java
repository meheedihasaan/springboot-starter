package com.springboot.starter.exceptions;

import com.springboot.starter.models.Response;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.Optional;

@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        String className = ex.getClassName().getSimpleName().toLowerCase();
        String message = "No " +className+ " found.";
        return buildResponseEntity(HttpStatus.BAD_REQUEST, message, null, false);
    }

    @ResponseBody
    @ExceptionHandler(value = ResponseException.class)
    public ResponseEntity<Object> handleResponseException(ResponseException ex) {
        HttpStatus httpStatus = ex.getHttpStatus();
        String message = ex.getMessage();
        Object payload = ex.getPayload();
        return buildResponseEntity(httpStatus != null ? httpStatus : HttpStatus.BAD_REQUEST, message, payload, false);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Optional<ObjectError> objectErrorOptional = ex.getBindingResult().getAllErrors().stream().findFirst();
        if(objectErrorOptional.isPresent()) {
            ObjectError error = objectErrorOptional.get();
            return buildResponseEntity(status, ((FieldError) error).getField() + " field " + error.getDefaultMessage(), null, false);
        }
        return buildResponseEntity(status, "Invalid request body!", null, false);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return buildResponseEntity(status, "Invalid input!", ex.getLocalizedMessage(), false);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return buildResponseEntity(status, "Server error. Write failed!", ex.getLocalizedMessage(), false);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return buildResponseEntity(status, "Invalid input!", ex.getLocalizedMessage(), false);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return buildResponseEntity(status, "Invalid file type provided!", ex.getLocalizedMessage(), false);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return buildResponseEntity(status, "Request failed! Invalid request. Please try again.", ex.getLocalizedMessage(), false);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return buildResponseEntity(status, "Invalid type of request!", ex.getLocalizedMessage(), false);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return buildResponseEntity(statusCode, "Server error occurred!", ex.getLocalizedMessage(), false);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return buildResponseEntity(status, "Path variable is missing!", ex.getLocalizedMessage(), false);
    }

    @ResponseBody
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSqlException(SQLException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Request failed! Invalid request. Please try again.", ex.getLocalizedMessage(), false);
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatusCode statusCode, String message, Object payload, boolean success) {
        HttpStatus httpStatus;
        try {
            httpStatus = HttpStatus.valueOf(statusCode.value());
        }
        catch (Exception ex) {
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        Response errorResponse = new Response(httpStatus, message, payload, success);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

}