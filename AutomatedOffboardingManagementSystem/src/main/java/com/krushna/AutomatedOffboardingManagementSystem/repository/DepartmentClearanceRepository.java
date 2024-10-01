package com.krushna.AutomatedOffboardingManagementSystem.repository;

import com.krushna.AutomatedOffboardingManagementSystem.model.DepartmentClearance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentClearanceRepository extends JpaRepository<DepartmentClearance, Long> {
    public List<DepartmentClearance> findDepartmentClearanceByEmployee_id(Long id);
}

