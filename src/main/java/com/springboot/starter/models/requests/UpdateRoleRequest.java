package com.springboot.starter.models.requests;

import com.springboot.starter.enums.RoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateRoleRequest {

    @NotNull
    private Long id;

    @NotBlank
    private String roleName;

    @NotNull
    private RoleType roleType;

    private String description;

    @NotNull
    private long[] privilegesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long[] getPrivilegesId() {
        return privilegesId;
    }

    public void setPrivilegesId(long[] privilegesId) {
        this.privilegesId = privilegesId;
    }
}
