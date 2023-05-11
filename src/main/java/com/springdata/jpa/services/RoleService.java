package com.springdata.jpa.services;

import com.springdata.jpa.entities.Role;
import com.springdata.jpa.models.requests.CreateRoleRequest;

public interface RoleService {

    public Role createRole(CreateRoleRequest request);

    public Role getRoleById(Long id);

    public Role getRoleByIdWithException(Long id);

    public Role getRoleByRoleName(String roleName);

    public Role getRoleByRoleNameWithException(String roleName);

}
