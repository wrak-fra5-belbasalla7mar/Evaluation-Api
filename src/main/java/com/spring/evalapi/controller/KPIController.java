package com.spring.evalapi.controller;

import com.spring.evalapi.entity.Kpi;
import com.spring.evalapi.service.KPIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kpis")
@RequiredArgsConstructor
public class KPIController {

    private final KPIService kpiService;

    @PostMapping("/{userId}")
    public ResponseEntity<Kpi> addKPI(@Valid @RequestBody Kpi kpi,@PathVariable  Long userId) {
        Kpi savedKPI = kpiService.addKpi(kpi,userId);
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

    @GetMapping
    public ResponseEntity<List<Kpi>> getAllKPIs() {
        return ResponseEntity.ok(kpiService.getAllKpis());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kpi> updateKPI(@PathVariable Long id, @Valid @RequestBody Kpi kpi) {
        kpi.setId(id);
        return ResponseEntity.ok(kpiService.updateKPI(kpi));
    }

    @PutMapping("/{kpiId}/cycle/{cycleId}")
    public ResponseEntity<Kpi> assignKpiToCycle(@PathVariable Long kpiId, @PathVariable Long cycleId) {
        Kpi updatedKPI = kpiService.assignKpiToCycle(kpiId, cycleId);
        return ResponseEntity.ok(updatedKPI);
    }

    @PostMapping("/{kpiId}/role/{roleName}/{roleLevel}")
    public ResponseEntity<Void> assignKpiToRole(@PathVariable Long kpiId, @PathVariable String roleName,
                                                @PathVariable String roleLevel, @RequestParam Double weight) {
        kpiService.assignKpiToRole(kpiId, roleName, roleLevel, weight);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKPI(@PathVariable Long id) {
        kpiService.deleteKPI(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}