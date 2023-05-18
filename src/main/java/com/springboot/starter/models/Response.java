package com.springboot.starter.models;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {

    private HttpStatus status;

    private String message;

    private Object payload;

    private boolean success;

    public static ResponseEntity<Response> getResponseEntity(boolean success, String message) {
        return new ResponseEntity<>(
                new Response(success ? HttpStatus.OK : HttpStatus.BAD_REQUEST, message, null, success),
                success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Response> getResponseEntity(boolean success, String message, Object payload) {
        return new ResponseEntity<>(
                new Response(success ? HttpStatus.OK : HttpStatus.BAD_REQUEST, message, payload, success),
                success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Response> getResponseEntity(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(
                new Response(httpStatus, message, null, httpStatus.equals(HttpStatus.OK)), httpStatus);
    }

    public static ResponseEntity<Response> getResponseEntity(HttpStatus httpStatus, String message, Object payload) {
        return new ResponseEntity<>(
                new Response(httpStatus, message, payload, httpStatus.equals(HttpStatus.OK)), httpStatus);
    }

    public Response() {}

    public Response(HttpStatus status, String message, boolean success) {
        super();
        this.status = status;
        this.message = message;
        this.success = success;
    }

    public Response(HttpStatus status, String message, Object payload, boolean success) {
        super();
        this.status = status;
        this.message = message;
        this.payload = payload;
        this.success = success;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
