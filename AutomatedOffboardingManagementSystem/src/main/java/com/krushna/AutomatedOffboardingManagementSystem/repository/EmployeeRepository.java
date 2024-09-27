package com.krushna.AutomatedOffboardingManagementSystem.repository;

import com.krushna.AutomatedOffboardingManagementSystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}