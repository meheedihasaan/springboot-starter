package com.springboot.starter.models.requests;

import jakarta.validation.Valid;

public class ForgetPasswordRequest {

    @Valid
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
