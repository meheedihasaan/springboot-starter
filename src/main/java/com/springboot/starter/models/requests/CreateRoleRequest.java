package com.springboot.starter.models.requests;

import com.springboot.starter.enums.RoleType;

public class CreateRoleRequest {

    private String roleName;

    private RoleType roleType;

    private String description;

    private long[] privilegesId;

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
