package com.spring.evalapi.controller;


import com.spring.evalapi.entity.Kpi;
import com.spring.evalapi.service.KPIService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kpis")
public class KPIController {

    private final KPIService kpiService;

    public KPIController(KPIService kpiService) {
        this.kpiService = kpiService;
    }

    @PostMapping
    public ResponseEntity<Kpi> addKPI(@Valid @RequestBody Kpi kpi) {
        Kpi savedKPI = kpiService.addKPI( kpi);
        return new ResponseEntity<>(savedKPI, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kpi> getKPIById(@PathVariable Long id) {
        return ResponseEntity.ok(kpiService.getKpiById(id));
    }

    @GetMapping("/cycle/{cycleId}")
    public ResponseEntity<List<Kpi>> getKPIsByCycleId(@PathVariable Long cycleId) {
        return ResponseEntity.ok(kpiService.getKPIsByCycleId(cycleId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kpi> updateKPI(@Valid @RequestBody Kpi kpi) {
        return ResponseEntity.ok(kpiService.updateKPI(kpi));
    }

    @PutMapping("/{kpiId}/cycle/{cycleId}")
    public ResponseEntity<Kpi> assignKpiToCycle(@PathVariable Long kpiId, @PathVariable Long cycleId) {
        Kpi updatedKPI = kpiService.assignKpiToCycle(kpiId, cycleId);
        return ResponseEntity.ok(updatedKPI);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteKPI(@PathVariable Long id) {
        return ResponseEntity.ok(kpiService.deleteKPI(id));
    }
}