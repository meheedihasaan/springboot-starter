package com.springboot.starter.models.requests;

public class CreatePrivilegeRequest {

    private String privilegeName;

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
