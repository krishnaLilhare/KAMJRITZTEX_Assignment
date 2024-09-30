package com.krushna.AutomatedOffboardingManagementSystem.service;

import com.krushna.AutomatedOffboardingManagementSystem.model.AssetReturn;
import com.krushna.AutomatedOffboardingManagementSystem.model.DepartmentClearance;
import com.krushna.AutomatedOffboardingManagementSystem.model.Employee;
import com.krushna.AutomatedOffboardingManagementSystem.model.OffboardingProcess;
import com.krushna.AutomatedOffboardingManagementSystem.model.enums.AssetStatus;
import com.krushna.AutomatedOffboardingManagementSystem.model.enums.Department;
import com.krushna.AutomatedOffboardingManagementSystem.model.enums.DepartmentStatus;
import com.krushna.AutomatedOffboardingManagementSystem.model.enums.EmployeeStatus;
import com.krushna.AutomatedOffboardingManagementSystem.repository.AssetReturnRepository;
import com.krushna.AutomatedOffboardingManagementSystem.repository.DepartmentClearanceRepository;
import com.krushna.AutomatedOffboardingManagementSystem.repository.EmployeeRepository;
import com.krushna.AutomatedOffboardingManagementSystem.repository.OffboardingProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Autowired
    OffboardingProcessRepository offboardingProcessRepository;

    @Autowired
    DepartmentClearanceRepository departmentClearanceRepository;

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
               if (employee.getStatus().equals(EmployeeStatus.OFFBOARDING) ) {
                   assetReturn.get().setStatus(AssetStatus.RETURNED);
                   assetReturnRepository.save(assetReturn.get());
                   OffboardingProcess offboarding = offboardingProcessRepository.findOffBoardingByEmployeeId(employee.getId());
                   if (offboarding != null) {
                       offboardingProcessService.approveOffboardingProcess(offboarding.getId());
                   } else {
                       throw new RuntimeException("OffBoarding Not Found");
                   }
               } else {
                   throw new RuntimeException("Status Should Be OFFBOARDING");
               }
           }
        return employee;
    }


    public Employee completeOffBoarding(Long emp_id) {
        Employee employee = employeeRepository.findById(emp_id).get();
        List<AssetReturn> assetReturns = assetReturnRepository.getAssetsByEmployeeId(emp_id);
        boolean flag= false;
        for (AssetReturn asset : assetReturns){
           List<DepartmentClearance> departmentClearances = departmentClearanceRepository.findDepartmentClearanceByEmployee_id(employee.getId());
           for (DepartmentClearance dc : departmentClearances) {
                   if (asset.getStatus().equals(AssetStatus.PENDING) || dc.getStatus().equals(DepartmentStatus.PENDING)) {
                       flag = false;
                       break;
                   } else {
                       asset.setReturnDate(new Date());
                       assetReturnRepository.save(asset);
                       flag = true;
                   }
               }
        }
        if(flag){
            OffboardingProcess offboarding = offboardingProcessRepository.findOffBoardingByEmployeeId(emp_id);
            if(offboarding!= null)
            {
                offboardingProcessService.complteOffboardingProcess(offboarding.getId());
            }else{
                throw new RuntimeException("OffBoarding Not Found!!!! ...........please submit all the asset.");
            }
        }else{
            throw new RuntimeException("Please check pending assets Or Please check with Departmental Approve..!!!!!!!");
        }
        employee.setStatus(EmployeeStatus.EXITED);
      return employeeRepository.save(employee);
    }
}