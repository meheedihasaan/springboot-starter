package com.springboot.starter.exceptions;

import org.springframework.http.HttpStatus;

public class ResponseException extends RuntimeException {

    private HttpStatus httpStatus;

    private Object payload;

    public ResponseException(String message) {
        super(message);
    }

    public ResponseException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ResponseException(HttpStatus httpStatus, String message, Object payload) {
        super(message);
        this.httpStatus = httpStatus;
        this.payload = payload;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

}
