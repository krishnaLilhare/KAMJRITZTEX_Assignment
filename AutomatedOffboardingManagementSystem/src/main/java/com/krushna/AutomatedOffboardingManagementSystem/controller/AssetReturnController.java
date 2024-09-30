package com.krushna.AutomatedOffboardingManagementSystem.controller;

import com.krushna.AutomatedOffboardingManagementSystem.model.AssetReturn;
import com.krushna.AutomatedOffboardingManagementSystem.service.AssetReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetReturnController {

    @Autowired
    private AssetReturnService assetReturnService;

    @GetMapping
    public List<AssetReturn> getAllAssetReturns() {
        return assetReturnService.getAllAssetReturns();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetReturn> getAssetReturnById(@PathVariable Long id) {
        AssetReturn assetReturn = assetReturnService.getAssetReturnById(id);
        return ResponseEntity.ok(assetReturn);
    }

    @PostMapping("/save")
    public ResponseEntity<AssetReturn> createAssetReturn(@RequestBody AssetReturn assetReturn) {
        AssetReturn newAssetReturn = assetReturnService.createAssetReturn(assetReturn);
        return ResponseEntity.ok(newAssetReturn);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetReturn> updateAssetReturnStatus(@PathVariable Long id) {
        AssetReturn updatedAssetReturn = assetReturnService.updateAssetReturnStatus(id);
        return ResponseEntity.ok(updatedAssetReturn);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssetReturn(@PathVariable Long id) {
        assetReturnService.deleteAssetReturn(id);
        return ResponseEntity.ok().build();
    }
}
