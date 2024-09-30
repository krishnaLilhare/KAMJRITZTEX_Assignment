package com.krushna.AutomatedOffboardingManagementSystem.model;

import com.krushna.AutomatedOffboardingManagementSystem.model.enums.Department;
import com.krushna.AutomatedOffboardingManagementSystem.model.enums.DepartmentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class DepartmentClearance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Department department;
    private DepartmentStatus status;
    private Date approvalDate;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private AssetReturn asset;
}