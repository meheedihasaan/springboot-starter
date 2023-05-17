package com.springboot.starter.controllers;

import com.springboot.starter.entities.Role;
import com.springboot.starter.entities.User;
import com.springboot.starter.models.Response;
import com.springboot.starter.services.RoleService;
import com.springboot.starter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

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
        if(user == null) {
            return Response.getResponseEntity(false, "No user found with given email!");
        }

        Set<Role> roles = user.getRoles();
        return Response.getResponseEntity(true, "Data loaded successfully.", roles);
    }

}
