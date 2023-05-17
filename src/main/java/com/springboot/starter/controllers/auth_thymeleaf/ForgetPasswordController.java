package com.springboot.starter.controllers.auth_thymeleaf;

import com.springboot.starter.configs.AppProperties;
import com.springboot.starter.constants.AppUtils;
import com.springboot.starter.entities.User;
import com.springboot.starter.models.requests.ForgetPasswordChangeRequest;
import com.springboot.starter.models.responses.PasswordValidationResponse;
import com.springboot.starter.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForgetPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/resetpassword")
    public String viewResetPasswordPage(@RequestParam(name = "passResetToken") String passwordResetToken, Model model) {
        if(passwordResetToken == null) {
            model.addAttribute("projectName", appProperties.getName());
            model.addAttribute("message", "User token not found!");
            return "dispatchMessage";
        }

        User user = userService.findByPasswordResetToken(passwordResetToken);
        if(user == null) {
            model.addAttribute("projectName", appProperties.getName());
            model.addAttribute("message", "User token mismatch!");
            return "dispatchMessage";
        }

        ForgetPasswordChangeRequest request = new ForgetPasswordChangeRequest();
        request.setToken(passwordResetToken);
        model.addAttribute("passwordChangeRequest", request);
        model.addAttribute("projectName", appProperties.getName());
        return "password-change";
    }

    @PostMapping(value = "/password-reset")
    public String resetPassword(
            @Valid @ModelAttribute("passwordChangeRequest") ForgetPasswordChangeRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        User user = userService.findByPasswordResetToken(request.getToken());
        if(user == null) {
            model.addAttribute("projectName", appProperties.getName());
            model.addAttribute("message", "User token mismatch!");
            return "dispatchMessage";
        }

        if(bindingResult.hasErrors()) {
            model.addAttribute("projectName", appProperties.getName());
            model.addAttribute("message", "Field can not be empty.");
            return "dispatchMessage";
        }

        if(!request.getPassword().equals(request.getConfirmPassword())) {
            model.addAttribute("projectName", appProperties.getName());
            model.addAttribute("message", "Passwords are not same!");
            return "dispatchMessage";
        }

        PasswordValidationResponse passwordValidationResponse = AppUtils.getPasswordValidationResponse(request.getPassword());
        if(!passwordValidationResponse.isValid()) {
            model.addAttribute("projectName", appProperties.getName());
            model.addAttribute("message", passwordValidationResponse.getMessage());
            return "dispatchMessage";
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPasswordResetToken(null);
        userService.saveUser(user);
        model.addAttribute("projectName", appProperties.getName());
        model.addAttribute("message", "Password changed successfully.");
        return "dispatchMessage";
    }

}
