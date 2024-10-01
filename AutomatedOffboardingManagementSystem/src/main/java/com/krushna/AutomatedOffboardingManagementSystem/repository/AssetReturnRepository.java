package com.krushna.AutomatedOffboardingManagementSystem.repository;

import com.krushna.AutomatedOffboardingManagementSystem.model.AssetReturn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetReturnRepository extends JpaRepository<AssetReturn, Long> {
    public List<AssetReturn> getAssetsByEmployeeId(Long id);
}

