package com.krushna.AutomatedOffboardingManagementSystem.service;

import com.krushna.AutomatedOffboardingManagementSystem.model.UserRole;
import com.krushna.AutomatedOffboardingManagementSystem.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<UserRole> getAllRoles() {
        return userRoleRepository.findAll();
    }

    public List<UserRole> getRolesForUser(Long userId) {
        return userRoleRepository.findByUserId(userId);
    }

    public UserRole assignRoleToUser(Long userId, String role) {
        // Check if the user already has this role
        Optional<UserRole> existingRole = userRoleRepository.findByUserIdAndRole(userId, role);
        if (existingRole.isPresent()) {
            throw new RuntimeException("User already has the role: " + role);
        }

        UserRole userRole = new UserRole(userId, role);
        return userRoleRepository.save(userRole);
    }

    public void revokeRoleFromUser(Long userId, String role) {
        Optional<UserRole> existingRole = userRoleRepository.findByUserIdAndRole(userId, role);
        if (!existingRole.isPresent()) {
            throw new RuntimeException("User does not have the role: " + role);
        }

        userRoleRepository.deleteByUserIdAndRole(userId, role);
    }
}
