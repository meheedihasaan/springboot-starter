package com.springdata.jpa.models.requests;

import com.springdata.jpa.enums.RoleType;

public class CreateRoleRequest {

    private String roleName;

    private RoleType roleType;

    private long[] privilegesId;

    private String description;

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

    public long[] getPrivilegesId() {
        return privilegesId;
    }

    public void setPrivilegesId(long[] privilegesId) {
        this.privilegesId = privilegesId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
