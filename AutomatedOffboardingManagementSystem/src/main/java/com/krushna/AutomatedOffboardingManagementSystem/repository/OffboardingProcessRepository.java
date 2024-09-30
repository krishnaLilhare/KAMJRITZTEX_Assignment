package com.krushna.AutomatedOffboardingManagementSystem.repository;

import com.krushna.AutomatedOffboardingManagementSystem.model.Employee;
import com.krushna.AutomatedOffboardingManagementSystem.model.OffboardingProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OffboardingProcessRepository extends JpaRepository<OffboardingProcess, Long> {

    public  OffboardingProcess  findOffBoardingByEmployeeId(Long id);
}
