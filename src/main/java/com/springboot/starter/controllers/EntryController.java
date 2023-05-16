package com.springboot.starter.controllers;

import com.springboot.starter.models.Response;
import com.springboot.starter.models.requests.SignInRequest;
import com.springboot.starter.models.requests.SignUpRequest;
import com.springboot.starter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class EntryController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/signin")
    public ResponseEntity<Response> authenticateUser(@RequestBody SignInRequest request) {
        return Response.getResponseEntity(true, "You're logged in.", userService.signIn(request));
    }

    @PostMapping(value = "signup")
    public ResponseEntity<Response> signup(@RequestBody SignUpRequest signUpRequest) {
        System.out.println("inside");
        userService.createNewUser(signUpRequest);
        return Response.getResponseEntity(true, "Account created. Please check your email address and verify your account.");
    }

}
