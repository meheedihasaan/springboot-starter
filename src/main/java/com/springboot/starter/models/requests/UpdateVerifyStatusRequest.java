package com.springboot.starter.models.requests;

public class UpdateVerifyStatusRequest {

    private Long userId;

    private Boolean verified;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean isVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

}
