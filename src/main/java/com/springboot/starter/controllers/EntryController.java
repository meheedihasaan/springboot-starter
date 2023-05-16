package com.springboot.starter.controllers;

import com.springboot.starter.constants.AppConstant;
import com.springboot.starter.entities.User;
import com.springboot.starter.models.Response;
import com.springboot.starter.models.requests.SignInRequest;
import com.springboot.starter.models.requests.SignUpRequest;
import com.springboot.starter.models.requests.UpdateBannedStatusRequest;
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

    @PostMapping(value = "/signup")
    public ResponseEntity<Response> signup(@RequestBody SignUpRequest signUpRequest) {
        System.out.println("inside");
        userService.createNewUser(signUpRequest);
        return Response.getResponseEntity(true, "Account created. Please check your email address and verify your account.");
    }

    @PostMapping(value = "/user/update-banned-status")
    public ResponseEntity<Response> updateBannedStatusManually(@RequestBody UpdateBannedStatusRequest request) {
        User user = userService.findByIdWithException(request.getUserId());
        if(user.getEmail().equals(AppConstant.INITIAL_USERNAME) && !request.isBanned()) {
            return Response.getResponseEntity(false, "You can't ban the super admin.");
        }

        user.setBanned(request.isBanned());
        User savedUser = userService.saveUser(user);

        if(savedUser == null) {
            return Response.getResponseEntity(false, "Failed to update banned status due to unknown reason. Please try again later");
        }
        else{
            return Response.getResponseEntity(true, "User banned status updated");
        }
    }

}
