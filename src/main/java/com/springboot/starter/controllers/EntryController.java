package com.springboot.starter.controllers;

import com.springboot.starter.configs.AppProperties;
import com.springboot.starter.constants.AppConstant;
import com.springboot.starter.constants.AppUtils;
import com.springboot.starter.entities.User;
import com.springboot.starter.enums.AscOrDesc;
import com.springboot.starter.models.PaginationArgs;
import com.springboot.starter.models.Response;
import com.springboot.starter.models.requests.*;
import com.springboot.starter.services.MailService;
import com.springboot.starter.services.UserService;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class EntryController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private MailService mailService;

    @PostMapping(value = "/signin")
    public ResponseEntity<Response> authenticateUser(@RequestBody SignInRequest request) {
        return Response.getResponseEntity(true, "You're logged in.", userService.signIn(request));
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<Response> signup(@RequestBody SignUpRequest signUpRequest) {
        System.out.println("inside");
        userService.createNewUser(signUpRequest);
        return Response.getResponseEntity(
                true, "Account created. Please check your email address and verify your account.");
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PostMapping(value = "/user/update-banned-status")
    public ResponseEntity<Response> updateBannedStatusManually(@RequestBody UpdateBannedStatusRequest request) {
        User user = userService.findByIdWithException(request.getUserId());
        if (user.getEmail().equals(AppConstant.INITIAL_USERNAME) && request.isBanned()) {
            return Response.getResponseEntity(false, "You can't ban the super admin.");
        }

        user.setBanned(request.isBanned());
        User savedUser = userService.saveUser(user);

        if (savedUser == null) {
            return Response.getResponseEntity(
                    false, "Failed to update banned status due to unknown reason. Please try again later.");
        }

        return Response.getResponseEntity(true, "User banned status updated.");
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PostMapping(value = "/user/update-verify-status")
    public ResponseEntity<Response> updateVerifyStatusManually(@RequestBody UpdateVerifyStatusRequest request) {
        User user = userService.findByIdWithException(request.getUserId());
        if (user.getEmail().equals(AppConstant.INITIAL_USERNAME) && !request.isVerified()) {
            return Response.getResponseEntity(false, "You can't unverify the super admin.");
        }

        user.setVerified(request.isVerified());
        User savedUser = userService.saveUser(user);

        if (savedUser == null) {
            return Response.getResponseEntity(
                    false, "Failed to update verify status due to unknown error. Please try again later.");
        }

        return Response.getResponseEntity(true, "User verified status updated.");
    }

    @GetMapping(value = "/user/all")
    public ResponseEntity<Response> getPaginatedUsers(
            @RequestParam(name = AppConstant.PAGE_NUMBER, defaultValue = "0") int pageNumber,
            @RequestParam(name = AppConstant.PAGE_SIZE, defaultValue = "20") int pageSize,
            @RequestParam(name = AppConstant.SORT_BY, defaultValue = "") String sortBy,
            @RequestParam(name = AppConstant.ASC_OR_DESC, defaultValue = "") AscOrDesc ascOrDesc,
            @RequestParam(required = false) Map<String, Object> parameters) {
        PaginationArgs paginationArgs = new PaginationArgs(pageNumber, pageSize, sortBy, ascOrDesc, parameters);
        return Response.getResponseEntity(
                true, "Data loaded successfully.", userService.getPaginatedUsers(paginationArgs));
    }

    @GetMapping(value = "/me")
    public ResponseEntity<Response> getLoggedInUserInfo() {
        return Response.getResponseEntity(true, "User info loaded.", userService.getLoggedInUserInfo(userService));
    }

    @GetMapping(value = "/user/id/{userId}")
    public ResponseEntity<Response> getUserById(@PathVariable Long userId) {
        return Response.getResponseEntity(true, "User info loaded.", userService.findByIdWithException(userId));
    }

    @PutMapping(value = "/user/info/update")
    public ResponseEntity<Response> updateUserInfo(@Valid @RequestBody UpdateUserInfoRequest request) {
        User user = userService.findByEmailWithException(
                SecurityContextHolder.getContext().getAuthentication().getName());
        user.setName(request.getName());

        return Response.getResponseEntity(true, "User info is updated.", userService.saveUser(user));
    }

    @PreAuthorize("hasAuthority('USER_CREATE')")
    @PostMapping(value = "/user/create-admin")
    public ResponseEntity<Response> createAdmin(@Valid @RequestBody CreateAdminRequest request) {
        return Response.getResponseEntity(true, "Admin is created.", userService.createAdmin(request));
    }

    @PreAuthorize("hasAuthority('USER_UPDATE_FROM_ADMIN')")
    @PutMapping(value = "/user/update-user-by-admin")
    public ResponseEntity<Response> updateUserInfoByAdmin(@Valid @RequestBody UpdateUserInfoByAdminRequest request) {
        return Response.getResponseEntity(true, "User is updated.", userService.updateUserInfoByAdmin(request));
    }

    @PreAuthorize("#authentication.name != null")
    @PutMapping(value = "/user/change-password")
    public ResponseEntity<Response> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        return Response.getResponseEntity(true, "Password is updated.", userService.changePassword(request));
    }

    @PostMapping(value = "/forgetpassword")
    public ResponseEntity<Response> forgetPassword(@Valid @RequestBody ForgetPasswordRequest request) {
        if (!AppUtils.isValidEmail(request.getEmail())) {
            return Response.getResponseEntity(HttpStatus.EXPECTATION_FAILED, "Your email format is incorrect!");
        }

        User user = userService.findByEmail(request.getEmail());
        if (user == null) {
            return Response.getResponseEntity(HttpStatus.BAD_REQUEST, "User not found with the given email.");
        }

        String token = UUID.randomUUID().toString();
        user.setPasswordResetToken(token);
        userService.saveUser(user);

        if (appProperties.getActiveProfile() != AppConstant.Environment.DEVELOPMENT) {
            mailService.sendMail(
                    user.getEmail(),
                    AppConstant.FORGET_PASSWORD_SUBJECT,
                    AppConstant.FORGET_PASSWORD_TEXT
                            + appProperties.getBackendUrl()
                            + AppConstant.RESET_PASSWORD_SUBURL
                            + token);
        }

        return Response.getResponseEntity(true, "Password reset link sent to your registered email address.");
    }
}
