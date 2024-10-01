package com.krushna.AutomatedOffboardingManagementSystem.repository;

import com.krushna.AutomatedOffboardingManagementSystem.model.OffboardingProcess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OffboardingProcessRepository extends JpaRepository<OffboardingProcess, Long> {
    public OffboardingProcess findOffBoardingByEmployeeId(Long id);
}
