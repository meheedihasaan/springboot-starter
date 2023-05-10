package com.springdata.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springdata.jpa.constants.AppTables;
import com.springdata.jpa.constants.AppTables.UserTable;
import com.springdata.jpa.models.AuditModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = AppTables.USER)
public class User extends AuditModel<String> {

    @Column(name = UserTable.EMAIL, nullable = false, unique = true)
    private String email;

    @Column(name = UserTable.PASSWORD)
    @JsonIgnore
    @Size(max = 120)
    private String password;

    @Column(name = UserTable.PASSWORD_RESET_TOKEN)
    private String passwordResetToken;

    @Column(name = UserTable.NAME)
    private String name;

    @Column(name = UserTable.VERIFIED)
    private boolean verified = false;

    @Column(name = UserTable.BANNED)
    private boolean banned = false;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }
}
