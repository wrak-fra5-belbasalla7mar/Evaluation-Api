package com.spring.evalapi.controller;

import com.spring.evalapi.entity.Objective;
import com.spring.evalapi.service.ObjectiveService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/objectives")
public class ObjectiveController {

    private final ObjectiveService objectiveService;

    public ObjectiveController(ObjectiveService objectiveService) {
        this.objectiveService = objectiveService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('TEAM_MANAGER', 'COMPANY_MANAGER')")
    public ResponseEntity<List<Objective>> assignObjective(@Valid @RequestBody List<Objective> objectives) {
        return ResponseEntity.status(HttpStatus.CREATED).body(objectiveService.assignObjective(objectives));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'TEAM_MANAGER', 'COMPANY_MANAGER')")
    public ResponseEntity<List<Objective>> viewObjectiveForCycle() {
        return ResponseEntity.status(HttpStatus.OK).body(objectiveService.viewObjectiveForCycle());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'TEAM_MANAGER', 'COMPANY_MANAGER')")
    public ResponseEntity<Objective> getObjectiveById(@PathVariable Long id) {
        Objective objective = objectiveService.findByAssignId(id);
        return ResponseEntity.status(HttpStatus.OK).body(objective);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEAM_MANAGER', 'COMPANY_MANAGER')")
    public ResponseEntity<Objective> updateObjective(@PathVariable Long id, @Valid @RequestBody Objective updatedObjective) {
        Objective updatedObj = objectiveService.UpdateByAssignId(id,updatedObjective);
        return ResponseEntity.status(HttpStatus.OK).body(updatedObj);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEAM_MANAGER', 'COMPANY_MANAGER')")
    public ResponseEntity<String> deleteObjective(@PathVariable Long id) {
        String responseMessage = objectiveService.deleteByAssignId(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}