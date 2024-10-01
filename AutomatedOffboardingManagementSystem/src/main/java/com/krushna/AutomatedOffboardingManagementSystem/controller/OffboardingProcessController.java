package com.krushna.AutomatedOffboardingManagementSystem.controller;

import com.krushna.AutomatedOffboardingManagementSystem.model.OffboardingProcess;
import com.krushna.AutomatedOffboardingManagementSystem.service.OffboardingProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/offboarding")
public class OffboardingProcessController {

    @Autowired
    private OffboardingProcessService offboardingProcessService;

    @GetMapping
    public List<OffboardingProcess> getAllOffboardingProcesses() {
        return offboardingProcessService.getAllOffboardingProcesses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OffboardingProcess> getOffboardingProcessById(@PathVariable Long id) {
        OffboardingProcess process = offboardingProcessService.getOffboardingProcessById(id);
        return ResponseEntity.ok(process);
    }

    @PostMapping
    public ResponseEntity<OffboardingProcess> startOffboardingProcess(@RequestBody OffboardingProcess process) {
        OffboardingProcess newProcess = offboardingProcessService.startOffboardingProcess(process);
        return ResponseEntity.ok(newProcess);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<OffboardingProcess> approveOffboardingProcess(@PathVariable Long id) {
        OffboardingProcess approvedProcess = offboardingProcessService.approveOffboardingProcess(id);
        return ResponseEntity.ok(approvedProcess);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<OffboardingProcess> completeOffboardingProcess(@PathVariable Long id) {
        OffboardingProcess rejectedProcess = offboardingProcessService.complteOffboardingProcess(id);
        return ResponseEntity.ok(rejectedProcess);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffboardingProcess(@PathVariable Long id) {
        offboardingProcessService.deleteOffboardingProcess(id);
        return ResponseEntity.ok().build();
    }
}

