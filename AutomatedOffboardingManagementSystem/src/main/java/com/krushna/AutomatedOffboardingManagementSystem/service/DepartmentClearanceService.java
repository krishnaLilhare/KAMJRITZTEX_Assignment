package com.krushna.AutomatedOffboardingManagementSystem.service;

import com.krushna.AutomatedOffboardingManagementSystem.exception.ApplicationException;
import com.krushna.AutomatedOffboardingManagementSystem.model.AssetReturn;
import com.krushna.AutomatedOffboardingManagementSystem.model.DepartmentClearance;
import com.krushna.AutomatedOffboardingManagementSystem.model.Employee;
import com.krushna.AutomatedOffboardingManagementSystem.model.enums.AssetStatus;
import com.krushna.AutomatedOffboardingManagementSystem.model.enums.DepartmentStatus;
import com.krushna.AutomatedOffboardingManagementSystem.repository.AssetReturnRepository;
import com.krushna.AutomatedOffboardingManagementSystem.repository.DepartmentClearanceRepository;
import com.krushna.AutomatedOffboardingManagementSystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentClearanceService {

    @Autowired
    private DepartmentClearanceRepository departmentClearanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AssetReturnRepository assetReturnRepository;

    public List<DepartmentClearance> getAllClearances() {
        return departmentClearanceRepository.findAll();
    }

    public DepartmentClearance getClearanceById(Long id) {
        return departmentClearanceRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                                         "Clearance not found......!!!!!",
                                         String.format("Department clearance with id=%d not found", id),
                                         HttpStatus.NOT_FOUND));
    }

    public DepartmentClearance approveClearance(Long id) {
        DepartmentClearance clearance = getClearanceById(id);
        AssetReturn assetReturn =clearance.getAsset();
        if (assetReturn.getStatus().equals(AssetStatus.RETURNED)) {
            clearance.setStatus(DepartmentStatus.APPROVED);
            clearance.setApprovalDate(new Date());
        }else {
            throw new ApplicationException(
                                "Asset is not returned yet............!!!!!!!!!",
                                " ",
                                HttpStatus.NOT_FOUND
            );
        }
        return departmentClearanceRepository.save(clearance);
    }

    public DepartmentClearance rejectClearance(Long id) {
        DepartmentClearance clearance = getClearanceById(id);
        clearance.setStatus(DepartmentStatus.PENDING);
        return departmentClearanceRepository.save(clearance);
    }

    public DepartmentClearance save(Long emp_id){
      Optional<Employee> employee= employeeRepository.findById(emp_id);
      DepartmentClearance departmentClearance = null;

      if(employee.isPresent()){
          List<AssetReturn> assetReturns = assetReturnRepository.getAssetsByEmployeeId(employee.get().getId());
          for (AssetReturn assetReturn:assetReturns){
               departmentClearance = new DepartmentClearance();
               departmentClearance.setStatus(DepartmentStatus.PENDING);
               departmentClearance.setDepartment(assetReturn.getAssetDept());
               departmentClearance.setAsset(assetReturn);
               departmentClearance.setEmployee(employee.get());
               departmentClearanceRepository.save(departmentClearance);
          }
      }
      return departmentClearance;
    }
}
