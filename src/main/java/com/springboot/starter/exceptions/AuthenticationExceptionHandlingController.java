package com.springboot.starter.exceptions;

import com.springboot.starter.models.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.naming.AuthenticationException;

@ControllerAdvice
public class AuthenticationExceptionHandlingController {

    @ResponseBody
    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        Response response = new Response(HttpStatus.FORBIDDEN, "Invalid email or password.", ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public String handleNoHandlerFoundException(NoHandlerFoundException ex, WebRequest request) {
        return "error-404";
    }

}
