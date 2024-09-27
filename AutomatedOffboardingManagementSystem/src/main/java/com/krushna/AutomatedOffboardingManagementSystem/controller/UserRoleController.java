package com.krushna.AutomatedOffboardingManagementSystem.controller;

import com.krushna.AutomatedOffboardingManagementSystem.model.UserRole;
import com.krushna.AutomatedOffboardingManagementSystem.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping
    public List<UserRole> getAllRoles() {
        return userRoleService.getAllRoles();
    }

    @PostMapping("/assign")
    public ResponseEntity<UserRole> assignRoleToUser(@RequestParam Long userId, @RequestParam String role) {
        UserRole assignedRole = userRoleService.assignRoleToUser(userId, role);
        return ResponseEntity.ok(assignedRole);
    }
}

