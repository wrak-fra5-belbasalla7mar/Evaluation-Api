package com.spring.evalapi.controller;


import com.spring.evalapi.dto.NewCycleDto;
import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.entity.Objective;
import com.spring.evalapi.service.CycleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cycle")
public class CycleController {
    private final CycleService cycleService;
    public CycleController(CycleService cycleService) {
        this.cycleService = cycleService;
    }

    @PostMapping("")
    public ResponseEntity<Cycle> createCycle(@Valid @RequestBody NewCycleDto newCycleDto) {
        Cycle cycle = cycleService.addCycle(newCycleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cycle);
    }

    @GetMapping("")
    public ResponseEntity<Cycle> getLatestCycle() {
        Cycle latestCycle = cycleService.ViewTheLatestCycle();
        return ResponseEntity.ok(latestCycle);
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

    @PutMapping("/objectives")
    public ResponseEntity<Cycle> putObjectives(@RequestBody List<Objective> objectives) {
        Cycle cycle = cycleService.putObjectives(objectives);
        return ResponseEntity.ok(cycle);
    }
}
