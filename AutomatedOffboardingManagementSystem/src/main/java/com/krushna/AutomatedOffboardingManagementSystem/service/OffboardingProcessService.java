package com.krushna.AutomatedOffboardingManagementSystem.service;

import com.krushna.AutomatedOffboardingManagementSystem.exception.ApplicationException;
import com.krushna.AutomatedOffboardingManagementSystem.model.Employee;
import com.krushna.AutomatedOffboardingManagementSystem.model.OffboardingProcess;
import com.krushna.AutomatedOffboardingManagementSystem.model.enums.EmployeeStatus;
import com.krushna.AutomatedOffboardingManagementSystem.model.enums.OffBoardingStatus;
import com.krushna.AutomatedOffboardingManagementSystem.repository.EmployeeRepository;
import com.krushna.AutomatedOffboardingManagementSystem.repository.OffboardingProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OffboardingProcessService {

    @Autowired
    private OffboardingProcessRepository offboardingProcessRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentClearanceService departmentClearanceService;

    public List<OffboardingProcess> getAllOffboardingProcesses() {
        return offboardingProcessRepository.findAll();
    }

    public OffboardingProcess getOffboardingProcessById(Long id) {
        return offboardingProcessRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        "Offboarding Process not found",
                        String.format("Offboarding with id=%d not found", id),
                        HttpStatus.NOT_FOUND
                ));
    }

    public OffboardingProcess startOffboardingProcess(OffboardingProcess process) {
        Long id =process.getEmployee().getId();
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()) {
            employee.get().setStatus(EmployeeStatus.OFFBOARDING);
            employeeRepository.save(employee.get());
            process.setEmployee(employee.get());
        }
        else {
            throw new ApplicationException(
                    "Employee not found",
                    String.format("Employee with id=%d not found", id),
                    HttpStatus.NOT_FOUND
            );
        }
        process.setStatus(OffBoardingStatus.PENDING);
        process.setStartDate(new Date());
        departmentClearanceService.save(employee.get().getId());
        return offboardingProcessRepository.save(process);
    }

    public OffboardingProcess approveOffboardingProcess(Long id) {
        OffboardingProcess process = getOffboardingProcessById(id);
        process.setStatus(OffBoardingStatus.IN_PROGRESS);
        return offboardingProcessRepository.save(process);
    }

    public OffboardingProcess complteOffboardingProcess(Long id) {
        OffboardingProcess process = getOffboardingProcessById(id);
        process.setStatus(OffBoardingStatus.COMPLETED);
        process.setEndDate(new Date());
        return offboardingProcessRepository.save(process);
    }

    public void deleteOffboardingProcess(Long id) {
        OffboardingProcess process = getOffboardingProcessById(id);
        offboardingProcessRepository.delete(process);
    }
}
