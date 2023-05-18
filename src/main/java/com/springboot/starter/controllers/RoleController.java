package com.springboot.starter.controllers;

import com.springboot.starter.constants.AppConstant;
import com.springboot.starter.entities.Role;
import com.springboot.starter.entities.User;
import com.springboot.starter.enums.AscOrDesc;
import com.springboot.starter.enums.RoleType;
import com.springboot.starter.models.PaginationArgs;
import com.springboot.starter.models.Response;
import com.springboot.starter.models.requests.CreateRoleRequest;
import com.springboot.starter.models.requests.UpdateRoleRequest;
import com.springboot.starter.services.RoleService;
import com.springboot.starter.services.UserService;
import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

    @GetMapping(value = "/types")
    public ResponseEntity<Response> getRolTypes() {
        List<String> roleTypes =
                Arrays.stream(RoleType.values()).map(Enum::name).toList();
        return Response.getResponseEntity(true, "Data loaded successfully.", roleTypes);
    }

    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    @PostMapping(value = "/create")
    public ResponseEntity<Response> createRole(@Valid @RequestBody CreateRoleRequest request) {
        Role role = roleService.createRole(request);
        if (role == null) {
            return Response.getResponseEntity(false, "Role not created! Please try again later.", null);
        }

        return Response.getResponseEntity(true, "Role created successfully.", role);
    }

    @PreAuthorize("hasAuthority('ROLE_READ')")
    @GetMapping(value = "/all")
    public ResponseEntity<Response> getPaginatedRoles(
            @RequestParam(name = AppConstant.PAGE_NUMBER, defaultValue = "0") int pageNumber,
            @RequestParam(name = AppConstant.PAGE_SIZE, defaultValue = "20") int pageSize,
            @RequestParam(name = AppConstant.SORT_BY, defaultValue = "") String sortBy,
            @RequestParam(name = AppConstant.ASC_OR_DESC, defaultValue = "") AscOrDesc ascOrDesc,
            @RequestParam(required = false) Map<String, Object> parameters) {
        PaginationArgs paginationArgs = new PaginationArgs(pageNumber, pageSize, sortBy, ascOrDesc, parameters);
        return Response.getResponseEntity(
                true, "Data loaded successfully.", roleService.getPaginatedUsers(paginationArgs));
    }

    @PreAuthorize("hasAuthority('ROLE_READ')")
    @GetMapping(value = "/type")
    public ResponseEntity<Response> getRolesByRoleType(@RequestParam(name = "roleType") String roleType) {
        return Response.getResponseEntity(true, "Data loaded successfully.", roleService.getRolesByRoleType(roleType));
    }

    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    @PutMapping(value = "/update")
    public ResponseEntity<Response> updateRole(@Valid @RequestBody UpdateRoleRequest request) {
        return Response.getResponseEntity(true, "Role is updated.", roleService.updateRole(request));
    }

    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    @DeleteMapping(value = "/{roleId}/delete")
    public ResponseEntity<Response> deleteRole(@PathVariable long roleId) {
        Boolean isDeleted = roleService.deleteRole(roleId);
        if (!isDeleted) {
            return Response.getResponseEntity(false, "Role not found!");
        }

        return Response.getResponseEntity(true, "Role is deleted successfully.");
    }
}
