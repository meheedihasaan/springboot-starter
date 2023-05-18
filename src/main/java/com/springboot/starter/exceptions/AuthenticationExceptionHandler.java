package com.springboot.starter.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.starter.models.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        Response errorResponse = new Response(HttpStatus.FORBIDDEN, "(JWT) " + authException.getMessage(), null, false);
        String responseMessage = mapper.writeValueAsString(errorResponse);
        response.getWriter().write(responseMessage);
        response.setStatus(403);
    }
}
