package com.krushna.AutomatedOffboardingManagementSystem.service;

import com.krushna.AutomatedOffboardingManagementSystem.model.AssetReturn;
import com.krushna.AutomatedOffboardingManagementSystem.repository.AssetReturnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetReturnService {

    @Autowired
    private AssetReturnRepository assetReturnRepository;

    public List<AssetReturn> getAllAssetReturns() {
        return assetReturnRepository.findAll();
    }

    public AssetReturn getAssetReturnById(Long id) {
        return assetReturnRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset return not found"));
    }

    public AssetReturn createAssetReturn(AssetReturn assetReturn) {
        return assetReturnRepository.save(assetReturn);
    }

    public AssetReturn updateAssetReturnStatus(Long id, String status) {
        AssetReturn assetReturn = getAssetReturnById(id);
        assetReturn.setStatus(status);
        return assetReturnRepository.save(assetReturn);
    }

    public void deleteAssetReturn(Long id) {
        AssetReturn assetReturn = getAssetReturnById(id);
        assetReturnRepository.delete(assetReturn);
    }
}
