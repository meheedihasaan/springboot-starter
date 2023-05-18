package com.springboot.starter.controllers.auth_thymeleaf;

import com.springboot.starter.configs.AppProperties;
import com.springboot.starter.entities.Secret;
import com.springboot.starter.entities.User;
import com.springboot.starter.enums.UserTokenPurpose;
import com.springboot.starter.services.SecretService;
import com.springboot.starter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VerificationController {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private SecretService secretService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/verify", produces = MediaType.TEXT_HTML_VALUE)
    public String userVerifyHtml(@RequestParam(name = "verificationToken") String verificationToken, Model model) {
        Secret secret = secretService.findByUserTokenAndUserTokenPurpose(
                verificationToken, UserTokenPurpose.EMAIL_VERIFICATION);
        System.out.println(secret);

        if (secret == null) {
            model.addAttribute("projectName", appProperties.getName());
            model.addAttribute("message", "User token not found!");
            return "dispatchMessage";
        }

        User user = userService.findById(secret.getUserId());
        if (user == null) {
            model.addAttribute(
                    "message",
                    "User verification failed. Verification token is not from server. Please follow link from mail.");
            return "Token not found!";
        } else {
            user.setVerified(true);
            userService.saveUser(user);
            secretService.deleteSecret(secret);

            model.addAttribute("projectName", appProperties.getName());
            model.addAttribute("message", "Your account is verified. You may now sign in.");
            return "dispatchMessage";
        }
    }
}
