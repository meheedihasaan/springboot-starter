package com.springdata.jpa.entities;

import com.springdata.jpa.constants.AppTables;
import com.springdata.jpa.constants.AppTables.UserTable;
import com.springdata.jpa.constants.AppTables.RoleTable;
import com.springdata.jpa.models.AuditModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = AppTables.USER_TABLE)
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

    @Column (name = UserTable.VERIFIED)
    private Boolean verified = false;

    @Column (name = UserTable.BANNED)
    private Boolean banned = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = AppTables.USER_ROLE_TABLE,
            joinColumns = @JoinColumn(name = UserTable.USER_ID),
            inverseJoinColumns = @JoinColumn(name = RoleTable.ROLE_ID)
    )
    private Set<Role> roles = new HashSet<>();

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

    public Boolean isVerified() {
        return verified != null && verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Boolean isBanned() {
        return banned != null && banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}