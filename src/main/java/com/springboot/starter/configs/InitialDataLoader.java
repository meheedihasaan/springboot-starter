package com.springboot.starter.configs;

import com.springboot.starter.constants.AppConstant;
import com.springboot.starter.entities.Privilege;
import com.springboot.starter.entities.Role;
import com.springboot.starter.entities.User;
import com.springboot.starter.services.PrivilegeService;
import com.springboot.starter.services.RoleService;
import com.springboot.starter.services.UserService;
import com.springboot.starter.enums.RoleType;
import com.springboot.starter.models.requests.CreatePrivilegeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class InitialDataLoader implements ApplicationListener<ApplicationContextEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private boolean isSetup;

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        Set<Privilege> superAdminPrivileges = new HashSet<>();
        for(Map.Entry<String, String> permission : AppConstant.PERMISSIONS.entrySet()) {
            boolean isPrivilegeExists = checkIfPrivilegeExists(permission.getKey());
            if(!isPrivilegeExists) {
                CreatePrivilegeRequest request = new CreatePrivilegeRequest();
                request.setPrivilegeName(permission.getKey());
                request.setDescription(permission.getValue());
                Privilege privilege = privilegeService.createPrivilege(request);
                superAdminPrivileges.add(privilege);
            }
        }

        if(checkIfRoleExists(AppConstant.INITIAL_ROLE)) {
            Role superAdminRole = roleService.findByRoleName(AppConstant.INITIAL_ROLE);
            superAdminRole.getPrivileges().addAll(superAdminPrivileges);
            roleService.saveRole(superAdminRole);
        }

        if(isSetup || checkIfSuperAdminExists()) {
            return;
        }

        Set<Privilege> consumerPrivileges = new HashSet<>();
        CreatePrivilegeRequest request = new CreatePrivilegeRequest();
        request.setPrivilegeName(AppConstant.CONSUMER_PERMISSION);
        request.setDescription(AppConstant.CONSUMER_PERMISSION_DESCRIPTION);
        Privilege privilege = privilegeService.createPrivilege(request);
        consumerPrivileges.add(privilege);

        roleService.createRole(AppConstant.INITIAL_ROLE, RoleType.ADMIN, null, superAdminPrivileges);
        roleService.createRole(AppConstant.USER_ROLE, RoleType.USER, null, consumerPrivileges);

        Set<Role> superAdminRoles = new HashSet<>();
        Role role = roleService.findByRoleName(AppConstant.INITIAL_ROLE);
        if(role != null) {
            superAdminRoles.add(role);
        }

        User superAdminUser = new User();
        superAdminUser.setName(AppConstant.INITIAL_ROLE);
        superAdminUser.setEmail(AppConstant.INITIAL_USERNAME);
        superAdminUser.setPassword(passwordEncoder.encode(AppConstant.INITIAL_PASSWORD));
        superAdminUser.setVerified(true);
        superAdminUser.setBanned(false);
        superAdminUser.setRoles(superAdminRoles);
        userService.saveUser(superAdminUser);

        isSetup = true;
    }

    private Boolean checkIfRoleExists(String roleName) {
        return roleService.existsRoleByRoleName(roleName);
    }

    private Boolean checkIfSuperAdminExists() {
        return roleService.existsRoleByRoleName(AppConstant.INITIAL_ROLE);
    }

    private Boolean checkIfPrivilegeExists(String privilegeName) {
        return privilegeService.existsPrivilegeByPrivilegeName(privilegeName);
    }

}
