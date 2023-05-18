package com.springboot.starter.models.responses;

public class PasswordValidationResponse {

    private Boolean valid;

    private String message;

    public PasswordValidationResponse(Boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    public Boolean isValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
