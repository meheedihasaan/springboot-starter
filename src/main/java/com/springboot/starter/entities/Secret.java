package com.springboot.starter.entities;

import com.springboot.starter.constants.AppTables;
import com.springboot.starter.constants.AppTables.SecretTable;
import com.springboot.starter.enums.UserTokenPurpose;
import com.springboot.starter.models.AuditModel;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = AppTables.SECRET_TABLE)
public class Secret extends AuditModel<String> {

    @Column(name = SecretTable.USER_ID)
    private Long userId;

    @Column(name = SecretTable.GOOGLE_ACCESS_TOKEN)
    private String googleAccessToken;

    @Column(name = SecretTable.FACEBOOK_ACCESS_TOKEN)
    private String facebookAccessToken;

    @Column(name= SecretTable.APPLE_ACCESS_TOKEN)
    private String appleAccessToken;

    @Column(name = SecretTable.USER_TOKEN)
    private String userToken;

    @Column(name = SecretTable.USER_TOKEN_EXPIRES_AT)
    private Instant userTokenExpiresAt;

    @Column(name = SecretTable.USER_TOKEN_PURPOSE)
    @Enumerated(EnumType.STRING)
    private UserTokenPurpose userTokenPurpose;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGoogleAccessToken() {
        return googleAccessToken;
    }

    public void setGoogleAccessToken(String googleAccessToken) {
        this.googleAccessToken = googleAccessToken;
    }

    public String getFacebookAccessToken() {
        return facebookAccessToken;
    }

    public void setFacebookAccessToken(String facebookAccessToken) {
        this.facebookAccessToken = facebookAccessToken;
    }

    public String getAppleAccessToken() {
        return appleAccessToken;
    }

    public void setAppleAccessToken(String appleAccessToken) {
        this.appleAccessToken = appleAccessToken;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Instant getUserTokenExpiresAt() {
        return userTokenExpiresAt;
    }

    public void setUserTokenExpiresAt(Instant userTokenExpiresAt) {
        this.userTokenExpiresAt = userTokenExpiresAt;
    }

    public UserTokenPurpose getUserTokenPurpose() {
        return userTokenPurpose;
    }

    public void setUserTokenPurpose(UserTokenPurpose userTokenPurpose) {
        this.userTokenPurpose = userTokenPurpose;
    }

}
