package com.krushna.AutomatedOffboardingManagementSystem.service;

import com.krushna.AutomatedOffboardingManagementSystem.exception.ApplicationException;
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
import org.springframework.http.HttpStatus;
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
        return employeeRepository.findById(id).orElseThrow(() -> new ApplicationException(
                                                              "Employee not found..........!!!!!!!!!!!!",
                                                              String.format("Employee with id=%d not found", id),
                                                              HttpStatus.NOT_FOUND));
    }

    public Employee createEmployee(Employee employee) {
        employee.setStatus(EmployeeStatus.ACTIVE);
        if(employee.getDepartment()==null) {
            employee.setDepartment(Department.DEVELOPMENT);
        }
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ApplicationException(
                "Employee not found",
                String.format("Employee with id=%d not found", id),
                HttpStatus.NOT_FOUND

        ));

        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setStatus(employeeDetails.getStatus());

        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ApplicationException(
                "Employee not found",
                String.format("Employee with id=%d not found", id),
                HttpStatus.NOT_FOUND
        ));
        employeeRepository.delete(employee);
    }

    public AssetReturn returnAsset(Long id){
        Employee employee = null;
        Optional<AssetReturn> assetReturn = assetReturnRepository.findById(id);
        if(assetReturn.isPresent())
        {
           employee =assetReturn.get().getEmployee();
               if (employee.getStatus().equals(EmployeeStatus.OFFBOARDING) ) {
                   assetReturn.get().setStatus(AssetStatus.RETURNED);
                   assetReturn.get().setReturnDate(new Date());
                   assetReturnRepository.save(assetReturn.get());
                   OffboardingProcess offboarding = offboardingProcessRepository.findOffBoardingByEmployeeId(employee.getId());
                   if (offboarding != null) {
                       offboardingProcessService.approveOffboardingProcess(offboarding.getId());
                   } else {
                       throw new ApplicationException(
                               "OffBoarding Not Found..........!!!!!!!!",
                               String.format("Off-Boarding with employee id=%d not found", id),
                               HttpStatus.NOT_FOUND
                       );
                   }
               } else {
                   throw new ApplicationException(
                           "Status Should Be OFFBOARDING.........!!!! First apply for off boarding.",
                           " ",
                           HttpStatus.NOT_FOUND
                   );
               }
           }
        return assetReturn.get();
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
                throw new ApplicationException(
                        "OffBoarding Not Found!!!! ...........please first submit all the remaining asset.",
                        String.format("Off-Boarding with employee id=%d not found", emp_id),
                        HttpStatus.NOT_FOUND
                );
            }
        }else{
            throw new ApplicationException(
                    "Please check pending assets Or Please check with Departmental Approve..!!!!!!!",
                    " ",
                    HttpStatus.NOT_FOUND
            );
        }
        employee.setStatus(EmployeeStatus.EXITED);
      return employeeRepository.save(employee);
    }
}