package com.springboot.starter.models.requests;

import jakarta.validation.constraints.NotBlank;

public class ForgetPasswordChangeRequest {

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

    private String token;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
