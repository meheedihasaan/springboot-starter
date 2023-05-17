package com.springboot.starter.models.requests;

import jakarta.validation.constraints.NotBlank;

public class ChangePasswordRequest {

    @NotBlank
    private String previousPassword;

    @NotBlank
    private String newPassword;

    public String getPreviousPassword() {
        return previousPassword;
    }

    public void setPreviousPassword(String previousPassword) {
        this.previousPassword = previousPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
