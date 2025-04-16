package com.spring.evalapi.controller;
import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.service.CycleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("cycles")
@RequiredArgsConstructor
public class CycleController {

    private final CycleService cycleService;

    @PutMapping("/{cycleId}")
    public ResponseEntity<Cycle> updateCycle(@PathVariable Long cycleId, @RequestBody Cycle updatedCycle) {
        Cycle cycle = cycleService.updateCycle(cycleId, updatedCycle);
        return ResponseEntity.ok(cycle);
    }

    @PostMapping("")
    public ResponseEntity<?> createCycle(@Valid @RequestBody Cycle newCycle) {
            Cycle savedCycle = cycleService.addCycle(newCycle );
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCycle);
    }

    @GetMapping
    public ResponseEntity<List<Cycle>> getAllCycles() {
        List<Cycle> cycles = cycleService.getAllCycles();
        return ResponseEntity.status(HttpStatus.OK).body(cycles);
    }

    @GetMapping("/Latest")
    public ResponseEntity<Cycle> getLatestCycle() {
        Cycle latestCycle = cycleService.ViewTheLatestCycle();
        return ResponseEntity.status(HttpStatus.OK).body(latestCycle);
    }

    @GetMapping("/Asc")
    public ResponseEntity<List<Cycle>> findAllByOrderByStartDateAsc() {
        List<Cycle> cycles = cycleService.findAllByOrderByStartDateAsc();
        return ResponseEntity.status(HttpStatus.OK).body(cycles);
    }

    @GetMapping("/Desc")
    public ResponseEntity<List<Cycle>> findAllByOrderByStartDateDesc() {
        List<Cycle> cycles = cycleService.findAllByOrderByStartDateDesc();
        return ResponseEntity.status(HttpStatus.OK).body(cycles);
    }


    @PutMapping("/pass/{id}")
    public ResponseEntity<String> passCycle(@Valid @PathVariable Long id) {
        String cycle = cycleService.passCycle(id);
        return ResponseEntity.ok(cycle);
    }

    @PutMapping("/close/{id}")
    public ResponseEntity<String> closeCycle(@Valid @PathVariable Long id) {
        String cycle=cycleService.closeCycle(id);
        return ResponseEntity.ok(cycle);
    }


    @PutMapping("/open/{id}")
    public ResponseEntity<String> openCycle(@Valid @PathVariable Long id) {
        String cycle=cycleService.openCycle(id);
        return ResponseEntity.ok(cycle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cycle> getCycleByID(@Valid @PathVariable("id") Long id) {
        Cycle cycle = cycleService.cycleByID(id);
        return ResponseEntity.ok(cycle);
    }

    @DeleteMapping("/{id}")
    public String deleteCycleById(@Valid @PathVariable ("id") Long id){
        return cycleService.deleteCycleById(id);
    }
}