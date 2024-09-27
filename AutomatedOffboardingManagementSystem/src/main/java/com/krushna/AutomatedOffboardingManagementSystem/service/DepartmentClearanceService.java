package com.krushna.AutomatedOffboardingManagementSystem.service;

import com.krushna.AutomatedOffboardingManagementSystem.model.DepartmentClearance;
import com.krushna.AutomatedOffboardingManagementSystem.repository.DepartmentClearanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentClearanceService {

    @Autowired
    private DepartmentClearanceRepository departmentClearanceRepository;

    public List<DepartmentClearance> getAllClearances() {
        return departmentClearanceRepository.findAll();
    }

    public DepartmentClearance getClearanceById(Long id) {
        return departmentClearanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clearance not found"));
    }

    public DepartmentClearance approveClearance(Long id) {
        DepartmentClearance clearance = getClearanceById(id);
        clearance.setStatus("Approved");
        return departmentClearanceRepository.save(clearance);
    }

    public DepartmentClearance rejectClearance(Long id) {
        DepartmentClearance clearance = getClearanceById(id);
        clearance.setStatus("Rejected");
        return departmentClearanceRepository.save(clearance);
    }
}
