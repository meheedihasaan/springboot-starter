package com.springdata.jpa.services;

import com.springdata.jpa.entities.Privilege;
import com.springdata.jpa.entities.Role;
import com.springdata.jpa.enums.RoleType;
import com.springdata.jpa.exceptions.NotFoundException;
import com.springdata.jpa.models.requests.CreateRoleRequest;
import com.springdata.jpa.repositories.PrivilegeRepository;
import com.springdata.jpa.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    public Role createRole(CreateRoleRequest request) {
        Set<Privilege> privileges = new HashSet<>();
        for (Long privilegeId : request.getPrivilegesId()) {
            Privilege privilege = privilegeRepository.findById(privilegeId).orElseThrow(()-> new NotFoundException(Privilege.class));
            privileges.add(privilege);
        }

        Role role = new Role();
        role.setRoleName(request.getRoleName());
        role.setRoleType(request.getRoleType());
        role.setDescription(request.getDescription());
        role.setPrivileges(privileges);
        return roleRepository.save(role);
    }

    public Role createRole(String roleName, RoleType roleType, Set<Privilege> privileges, String description) {
        Role role = new Role();
        role.setRoleName(roleName);
        role.setRoleType(roleType);
        role.setDescription(description);
        role.setPrivileges(privileges);
        return roleRepository.save(role);
    }

    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Role findByIdWithException(Long id) {
        return roleRepository.findById(id).orElseThrow(()-> new NotFoundException(Role.class));
    }

    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName).orElse(null);
    }

    public Role findByRoleNameWithException(String roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(()-> new NotFoundException(Role.class));
    }

}
