package com.springdata.jpa.entities;

import com.springdata.jpa.constants.AppTables;
import com.springdata.jpa.constants.AppTables.PrivilegeTable;
import com.springdata.jpa.models.AuditModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = AppTables.PRIVILEGE_TABLE)
public class Privilege extends AuditModel<String> {

    @Column(name = PrivilegeTable.PRIVILEGE_NAME)
    private String privilegeName;

    @Column(name = PrivilegeTable.DESCRIPTION)
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
