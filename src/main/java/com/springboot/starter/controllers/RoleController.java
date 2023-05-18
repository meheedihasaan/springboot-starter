package com.springboot.starter.controllers;

import com.springboot.starter.entities.Role;
import com.springboot.starter.entities.User;
import com.springboot.starter.enums.RoleType;
import com.springboot.starter.models.Response;
import com.springboot.starter.models.requests.CreateRoleRequest;
import com.springboot.starter.services.RoleService;
import com.springboot.starter.services.UserService;
import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/role")
public class RoleController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PreAuthorize("#email == authentication.name")
    @GetMapping(value = "/user-roles")
    public ResponseEntity<Response> getUserRoles(@RequestParam(name = "email") String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return Response.getResponseEntity(false, "No user found with given email!");
        }

        Set<Role> roles = user.getRoles();
        return Response.getResponseEntity(true, "Data loaded successfully.", roles);
    }

    @GetMapping("/types")
    public ResponseEntity<Response> getRolTypes() {
        List<String> roleTypes =
                Arrays.stream(RoleType.values()).map(Enum::name).toList();
        return Response.getResponseEntity(true, "Data loaded successfully.", roleTypes);
    }

    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    @PostMapping("/create")
    public ResponseEntity<Response> createRole(@Valid @RequestBody CreateRoleRequest request) {
        Role role = roleService.createRole(request);
        if (role == null) {
            return Response.getResponseEntity(false, "Role not created! Please try again later.", null);
        }

        return Response.getResponseEntity(true, "Role created successfully.", role);
    }
}
