package com.springboot.starter.models.responses;

public class PasswordValidationResponse {

    private Boolean isValidate;

    private String message;

    public PasswordValidationResponse(Boolean isValidate, String message) {
        this.isValidate = isValidate;
        this.message = message;
    }

    public Boolean getValidate() {
        return isValidate;
    }

    public void setValidate(Boolean validate) {
        isValidate = validate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
