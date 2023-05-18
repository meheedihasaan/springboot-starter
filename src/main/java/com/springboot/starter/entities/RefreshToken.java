package com.springboot.starter.entities;

import com.springboot.starter.constants.AppTables;
import com.springboot.starter.constants.AppTables.RefreshTokenTable;
import com.springboot.starter.models.AuditModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = AppTables.REFRESH_TOKEN_TABLE)
public class RefreshToken extends AuditModel<String> {

    @Column(name = RefreshTokenTable.USER_ID)
    private Long userId;

    @Column(name = RefreshTokenTable.TOKEN)
    private String token;

    @Column(name = RefreshTokenTable.EXPIRY_DATE, nullable = false)
    private Instant expiryDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiryData() {
        return expiryDate;
    }

    public void setExpiryData(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }
}
