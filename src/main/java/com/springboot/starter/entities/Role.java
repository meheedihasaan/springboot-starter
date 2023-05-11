package com.springboot.starter.entities;

import com.springboot.starter.constants.AppTables;
import com.springboot.starter.enums.RoleType;
import com.springboot.starter.models.AuditModel;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = AppTables.ROLE_TABLE)
public class Role extends AuditModel<String> {

    @Column(name = AppTables.RoleTable.ROLE_NAME)
    private String roleName;

    @Column(name = AppTables.RoleTable.ROLE_TYPE)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(name = AppTables.RoleTable.DESCRIPTION, columnDefinition = "TEXT")
    private String description;

    @Column(name = AppTables.RoleTable.IMAGE_URL)
    private String imageUrl;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(
            name = AppTables.ROLE_PRIVILEGE_TABLE,
            joinColumns = @JoinColumn(name = AppTables.RoleTable.ROLE_ID),
            inverseJoinColumns = @JoinColumn(name = AppTables.PrivilegeTable.PRIVILEGE_ID)
    )
    private Set<Privilege> privileges = new HashSet<>();

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

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

}
