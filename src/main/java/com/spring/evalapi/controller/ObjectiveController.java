package com.spring.evalapi.controller;

import com.spring.evalapi.entity.Objective;
import com.spring.evalapi.service.ObjectiveService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/objectives")
public class ObjectiveController {

    private final ObjectiveService objectiveService;

    public ObjectiveController(ObjectiveService objectiveService) {
        this.objectiveService = objectiveService;
    }

    @GetMapping("")
    public ResponseEntity<List<Objective>> viewObjectiveForCycle() {
        return ResponseEntity.status(HttpStatus.OK).body(objectiveService.viewObjectiveForCycle());
    }

    @PostMapping("")
    public ResponseEntity<List<Objective>> assignObjective(@Valid @RequestBody List<Objective> objectives) {
        return ResponseEntity.status(HttpStatus.CREATED).body(objectiveService.assignObjective(objectives));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Objective> getObjectiveByAssignedUserId(@PathVariable int id) {
        Objective objective = objectiveService.findByAssignId(id);
        return ResponseEntity.status(HttpStatus.OK).body(objective);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Objective> updateObjective(
            @PathVariable int id,
            @Valid @RequestBody Objective updatedObjective) {
        Objective updatedObj = objectiveService.UpdateByAssignId(id, updatedObjective);
        return ResponseEntity.status(HttpStatus.OK).body(updatedObj);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteObjective(@PathVariable int id) {
        String responseMessage = objectiveService.deleteByAssignId(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
