package com.krushna.AutomatedOffboardingManagementSystem.service;

import com.krushna.AutomatedOffboardingManagementSystem.model.OffboardingProcess;
import com.krushna.AutomatedOffboardingManagementSystem.model.enums.OffBoardingStatus;
import com.krushna.AutomatedOffboardingManagementSystem.repository.OffboardingProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffboardingProcessService {

    @Autowired
    private OffboardingProcessRepository offboardingProcessRepository;

    public List<OffboardingProcess> getAllOffboardingProcesses() {
        return offboardingProcessRepository.findAll();
    }

    public OffboardingProcess getOffboardingProcessById(Long id) {
        return offboardingProcessRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offboarding Process not found"));
    }

    public OffboardingProcess startOffboardingProcess(OffboardingProcess process) {
        process.setStatus(OffBoardingStatus.PENDING);
        return offboardingProcessRepository.save(process);
    }

    public OffboardingProcess approveOffboardingProcess(Long id) {
        OffboardingProcess process = getOffboardingProcessById(id);
        process.setStatus(OffBoardingStatus.COMPLETED);
        return offboardingProcessRepository.save(process);
    }

    public OffboardingProcess rejectOffboardingProcess(Long id) {
        OffboardingProcess process = getOffboardingProcessById(id);
        process.setStatus(OffBoardingStatus.IN_PROGRESS);
        return offboardingProcessRepository.save(process);
    }

    public void deleteOffboardingProcess(Long id) {
        OffboardingProcess process = getOffboardingProcessById(id);
        offboardingProcessRepository.delete(process);
    }
}
