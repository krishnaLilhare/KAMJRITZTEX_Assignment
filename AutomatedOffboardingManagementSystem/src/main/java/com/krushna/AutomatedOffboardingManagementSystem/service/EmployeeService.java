package com.krushna.AutomatedOffboardingManagementSystem.service;

import com.krushna.AutomatedOffboardingManagementSystem.model.AssetReturn;
import com.krushna.AutomatedOffboardingManagementSystem.model.Employee;
import com.krushna.AutomatedOffboardingManagementSystem.model.enums.AssetStatus;
import com.krushna.AutomatedOffboardingManagementSystem.model.enums.Department;
import com.krushna.AutomatedOffboardingManagementSystem.model.enums.DepartmentStatus;
import com.krushna.AutomatedOffboardingManagementSystem.model.enums.EmployeeStatus;
import com.krushna.AutomatedOffboardingManagementSystem.repository.AssetReturnRepository;
import com.krushna.AutomatedOffboardingManagementSystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    AssetReturnRepository assetReturnRepository;

    @Autowired
    OffboardingProcessService offboardingProcessService;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public Employee createEmployee(Employee employee) {
        employee.setStatus(EmployeeStatus.ACTIVE);
        if(employee.getDepartment()==null) {
            employee.setDepartment(Department.DEVELOPMENT);
        }
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setStatus(employeeDetails.getStatus());

        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        employeeRepository.delete(employee);
    }
    public Employee returnAsset(Long id){
        Employee employee = null;
        Optional<AssetReturn> assetReturn = assetReturnRepository.findById(id);
        if(assetReturn.isPresent())
        {
           employee =assetReturn.get().getEmployee();
            if(employee.getStatus().equals(EmployeeStatus.OFFBOARDING)) {
                assetReturn.get().setStatus(AssetStatus.RETURNED);
                assetReturnRepository.save(assetReturn.get());
//                offboardingProcessService.approveOffboardingProcess();
            }else {
                throw new RuntimeException("Status Should Be OFFBOARDING");
            }
        }
        return employee;
    }
}