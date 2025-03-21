package com.spring.evalapi.controller;
import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.entity.Objective;
import com.spring.evalapi.service.CycleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cycles")
public class CycleController {

    private final CycleService cycleService;
    public CycleController(CycleService cycleService) {
        this.cycleService = cycleService;
    }

    @PostMapping("")
    public ResponseEntity<?> createCycle(@Valid @RequestBody Cycle newCycle) {
            Cycle savedCycle = cycleService.addCycle(newCycle);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCycle);
    }

    @GetMapping("")
    public ResponseEntity<Cycle> getLatestCycle() {
        Cycle latestCycle = cycleService.ViewTheLatestCycle();
        return ResponseEntity.status(HttpStatus.OK).body(latestCycle);
    }

    @PutMapping("/open")
    public ResponseEntity<Cycle> openCycle() {
        Cycle cycle = cycleService.openCycle();
        return ResponseEntity.ok(cycle);
    }

    @PutMapping("/pass")
    public ResponseEntity<Cycle> passCycle() {
        Cycle cycle = cycleService.passCycle();
        return ResponseEntity.ok(cycle);
    }

    @PutMapping("/close")
    public ResponseEntity<Cycle> closeCycle() {
        Cycle cycle = cycleService.closeCycle();
        return ResponseEntity.ok(cycle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cycle> getCycleByID(@PathVariable("id") long id) {
        Cycle cycle = cycleService.returnCycleByID(id);
        return ResponseEntity.ok(cycle);
    }

    @DeleteMapping("/{id}")
    public String deleteCycleById(@PathVariable ("id") long id){
        return cycleService.deleteCycleById(id);
    }

}
