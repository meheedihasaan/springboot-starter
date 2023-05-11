package com.springboot.starter.entities;

import com.springboot.starter.constants.AppTables;
import com.springboot.starter.models.AuditModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = AppTables.PRIVILEGE_TABLE)
public class Privilege extends AuditModel<String> {

    @Column(name = AppTables.PrivilegeTable.PRIVILEGE_NAME)
    private String privilegeName;

    @Column(name = AppTables.PrivilegeTable.DESCRIPTION)
    private String description;

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
