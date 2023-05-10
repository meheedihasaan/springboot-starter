package com.springdata.jpa.entities;

import com.springdata.jpa.constants.AppTables;
import com.springdata.jpa.constants.AppTables.RoleTable;
import com.springdata.jpa.enums.RoleType;
import com.springdata.jpa.models.AuditModel;
import jakarta.persistence.*;

@Entity
@Table(name = AppTables.ROLE)
public class Role extends AuditModel<String> {

    @Column(name = RoleTable.ROLE_NAME)
    private String roleName;

    @Column(name = RoleTable.ROLE_TYPE)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(name = RoleTable.DESCRIPTION, columnDefinition = "TEXT")
    private String description;

    @Column(name = RoleTable.IMAGE_URL)
    private String imageUrl;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
