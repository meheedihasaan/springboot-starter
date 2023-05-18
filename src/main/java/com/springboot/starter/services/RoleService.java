package com.springboot.starter.services;

import com.springboot.starter.constants.AppUtils;
import com.springboot.starter.entities.Privilege;
import com.springboot.starter.entities.Role;
import com.springboot.starter.enums.RoleType;
import com.springboot.starter.exceptions.NotFoundException;
import com.springboot.starter.exceptions.ResponseException;
import com.springboot.starter.models.PaginationArgs;
import com.springboot.starter.models.requests.CreateRoleRequest;
import com.springboot.starter.models.requests.UpdateRoleRequest;
import com.springboot.starter.repositories.PrivilegeRepository;
import com.springboot.starter.repositories.RoleRepository;
import com.springboot.starter.specification.AppSpecification;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Role findByIdWithException(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new NotFoundException(Role.class));
    }

    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName).orElse(null);
    }

    public Role findByRoleNameWithException(String roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(() -> new NotFoundException(Role.class));
    }

    public Boolean existsRoleByRoleName(String roleName) {
        return roleRepository.existsRoleByRoleName(roleName);
    }

    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    public Role createRole(CreateRoleRequest request) {
        //        Set<Privilege> privileges = new HashSet<>();
        //        for (Long privilegeId : request.getPrivilegesId()) {
        //            Privilege privilege = privilegeRepository.findById(privilegeId).orElseThrow(()-> new
        // NotFoundException(Privilege.class));
        //            privileges.add(privilege);
        //        }
        Set<Privilege> privileges = Arrays.stream(request.getPrivilegesId())
                .mapToObj(privilegeId -> {
                    return privilegeRepository
                            .findById(privilegeId)
                            .orElseThrow(() -> new NotFoundException(Privilege.class));
                })
                .collect(Collectors.toSet());

        Role role = new Role();
        role.setRoleName(request.getRoleName());
        role.setRoleType(request.getRoleType());
        role.setDescription(request.getDescription());
        role.setPrivileges(privileges);
        return roleRepository.save(role);
    }

    public Role createRole(String roleName, RoleType roleType, String description, Set<Privilege> privileges) {
        Role role = new Role();
        role.setRoleName(roleName);
        role.setRoleType(roleType);
        role.setDescription(description);
        role.setPrivileges(privileges);
        return roleRepository.save(role);
    }

    public Page<Role> getPaginatedUsers(PaginationArgs paginationArgs) {
        Pageable pageable = AppUtils.getPageable(paginationArgs);

        Map<String, Object> specParameters = AppUtils.getSpecParameters(paginationArgs.getParameters());
        if (!specParameters.isEmpty()) {
            Specification<Role> roleSpecification = AppSpecification.getSpecification(specParameters);
            return roleRepository.findAll(roleSpecification, pageable);
        }

        return roleRepository.findAll(pageable);
    }

    public List<Role> getRolesByRoleType(String roleType) {
        if (roleType.isEmpty()) {
            throw new ResponseException("Role type is empty!");
        }

        return roleRepository.findAllByRoleType(RoleType.valueOf(roleType));
    }

    public Role updateRole(UpdateRoleRequest request) {
        Role role = roleRepository.findById(request.getId()).orElse(null);
        if (role == null) {
            throw new NotFoundException(Role.class);
        }

        Set<Privilege> privileges = Arrays.stream(request.getPrivilegesId())
                .mapToObj(privilegeId -> {
                    return privilegeRepository
                            .findById(privilegeId)
                            .orElseThrow(() -> new NotFoundException(Privilege.class));
                })
                .collect(Collectors.toSet());

        role.setRoleName(request.getRoleName());
        role.setRoleType(request.getRoleType());
        role.setDescription(request.getDescription());
        role.setPrivileges(privileges);
        return roleRepository.save(role);
    }
}
