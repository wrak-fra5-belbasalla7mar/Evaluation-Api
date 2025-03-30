package com.spring.evalapi.controller;

import com.spring.evalapi.entity.Objective;
import com.spring.evalapi.service.ObjectiveService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("objectives")
public class ObjectiveController {

    private final ObjectiveService objectiveService;
    public ObjectiveController(ObjectiveService objectiveService) {
        this.objectiveService = objectiveService;
    }
    @PostMapping("")
    public ResponseEntity<?> assignObjectiveByUserId(@Valid @RequestBody Objective objective){
        return ResponseEntity.status(HttpStatus.CREATED).body(objectiveService.assignObjectiveByUserId(objective));
    }


    @GetMapping("/{id}")
    public ResponseEntity<List<Objective>> findObjectiveByAssignId(@Valid @PathVariable Long id) {
        List<Objective> objective = objectiveService.findAllByAssignedUserId(id);
        return ResponseEntity.status(HttpStatus.OK).body(objective);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Objective> updateObjective(@Valid @PathVariable Long id, @Valid @RequestBody Objective updatedObjective) {
        Objective updatedObj = objectiveService.UpdateByAssignId(id,updatedObjective);
        return ResponseEntity.status(HttpStatus.OK).body(updatedObj);
    }

    @DeleteMapping("/{assignId}/{objectiveId}")
    public ResponseEntity<String> deleteByAssignIdAndObjectiveId(@PathVariable Long assignId,@PathVariable Long objectiveId) {
        String responseMessage = objectiveService.deleteByAssignIdAndObjectiveId(assignId,objectiveId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseMessage);
    }

    @PutMapping("/state/{id}/inProgress")
    public ResponseEntity<?> inProgressObjective(@PathVariable Long id){
        Objective responseMessage=  objectiveService.inProgressObjective(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseMessage);
    }

    @PutMapping("/state/{id}/complete")
    public ResponseEntity<?> completeObjective(@PathVariable Long id){
        Objective responseMessage=  objectiveService.completeObjective(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseMessage);
    }



}