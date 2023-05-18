package com.springboot.starter.controllers;

import com.springboot.starter.models.Response;
import com.springboot.starter.services.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/privilege")
public class PrivilegeController {

    @Autowired
    private PrivilegeService privilegeService;

    @GetMapping(value = "/all")
    public ResponseEntity<Response> getAllPrivileges() {
        return Response.getResponseEntity(true, "Data loaded successfully", privilegeService.getAllPrivileges());
    }
}
