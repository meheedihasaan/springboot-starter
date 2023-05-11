package com.springdata.jpa.services;

import com.springdata.jpa.entities.Role;
import com.springdata.jpa.models.requests.CreateRoleRequest;

public interface RoleService {

    public Role createRole(CreateRoleRequest request);

    public Role findById(Long id);

    public Role findByIdWithException(Long id);

    public Role findByRoleName(String roleName);

    public Role findByRoleNameWithException(String roleName);

}
