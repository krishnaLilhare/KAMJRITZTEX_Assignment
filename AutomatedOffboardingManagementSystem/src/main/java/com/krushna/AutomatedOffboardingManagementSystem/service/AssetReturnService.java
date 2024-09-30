package com.krushna.AutomatedOffboardingManagementSystem.service;

import com.krushna.AutomatedOffboardingManagementSystem.model.AssetReturn;
import com.krushna.AutomatedOffboardingManagementSystem.model.Employee;
import com.krushna.AutomatedOffboardingManagementSystem.model.enums.AssetStatus;
import com.krushna.AutomatedOffboardingManagementSystem.model.enums.Department;
import com.krushna.AutomatedOffboardingManagementSystem.repository.AssetReturnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetReturnService {

    @Autowired
    private AssetReturnRepository assetReturnRepository;
    @Autowired
    private EmployeeService employeeService;

    public List<AssetReturn> getAllAssetReturns() {
        return assetReturnRepository.findAll();
    }

    public AssetReturn getAssetReturnById(Long id) {
        return assetReturnRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset return not found"));
    }

    public AssetReturn createAssetReturn(AssetReturn assetReturn) {
        Long id =assetReturn.getEmployee().getId();
       Employee employee =employeeService.getEmployeeById(id);
       if(employee!=null)
         assetReturn.setEmployee(employee);
       else
           throw new RuntimeException("Employee not found");
       assetReturn.setStatus(AssetStatus.PENDING);
       if(assetReturn.getAssetDept().equals("ADMIN")){
           assetReturn.setAssetDept(Department.ADMIN);
       } else if (assetReturn.getAssetDept().equals("HR")) {
           assetReturn.setAssetDept(Department.HR);
       } else if (assetReturn.getAssetDept().equals("FINANCE")) {
           assetReturn.setAssetDept(Department.FINANCE);
       } else if (assetReturn.getAssetDept().equals("IT")) {
           assetReturn.setAssetDept(Department.IT);
       }
        return assetReturnRepository.save(assetReturn);
    }

    public AssetReturn updateAssetReturnStatus(Long id) {
        AssetReturn assetReturn = getAssetReturnById(id);
        if(assetReturn == null)
            throw new RuntimeException("Asset not found");
        assetReturn.setStatus(AssetStatus.RETURNED);
        return assetReturnRepository.save(assetReturn);
    }

    public void deleteAssetReturn(Long id) {
        AssetReturn assetReturn = getAssetReturnById(id);
        assetReturnRepository.delete(assetReturn);
    }
}
