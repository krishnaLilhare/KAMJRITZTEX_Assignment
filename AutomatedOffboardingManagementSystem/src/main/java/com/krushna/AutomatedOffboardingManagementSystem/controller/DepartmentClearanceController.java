package com.krushna.AutomatedOffboardingManagementSystem.controller;

import com.krushna.AutomatedOffboardingManagementSystem.model.DepartmentClearance;
import com.krushna.AutomatedOffboardingManagementSystem.service.DepartmentClearanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentClearanceController {

    @Autowired
    private DepartmentClearanceService departmentClearanceService;

    @GetMapping
    public List<DepartmentClearance> getAllClearances() {
        return departmentClearanceService.getAllClearances();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentClearance> getClearanceById(@PathVariable Long id) {
        DepartmentClearance clearance = departmentClearanceService.getClearanceById(id);
        return ResponseEntity.ok(clearance);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<DepartmentClearance> approveClearance(@PathVariable Long id) {
        DepartmentClearance approvedClearance = departmentClearanceService.approveClearance(id);
        return ResponseEntity.ok(approvedClearance);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<DepartmentClearance> rejectClearance(@PathVariable Long id) {
        DepartmentClearance rejectedClearance = departmentClearanceService.rejectClearance(id);
        return ResponseEntity.ok(rejectedClearance);
    }
}
