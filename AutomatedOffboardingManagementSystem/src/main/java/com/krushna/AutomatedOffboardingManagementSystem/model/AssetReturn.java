package com.krushna.AutomatedOffboardingManagementSystem.model;

import com.krushna.AutomatedOffboardingManagementSystem.model.enums.AssetStatus;
import com.krushna.AutomatedOffboardingManagementSystem.model.enums.Department;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
public class AssetReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String assetName;
    private AssetStatus status;
    private Department assetDept;
    private Date returnDate;
}