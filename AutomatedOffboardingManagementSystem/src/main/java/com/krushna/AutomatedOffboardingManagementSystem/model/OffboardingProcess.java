package com.krushna.AutomatedOffboardingManagementSystem.model;

import com.krushna.AutomatedOffboardingManagementSystem.model.enums.OffBoardingStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
public class OffboardingProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private Date startDate;
    private Date endDate;
    private OffBoardingStatus status;
}