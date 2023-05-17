package com.springboot.starter.models.requests;

import jakarta.validation.constraints.NotBlank;

public class UpdateBannedStatusRequest {

    private Long userId;

    private Boolean banned;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean isBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

}
