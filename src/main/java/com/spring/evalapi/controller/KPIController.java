package com.spring.evalapi.controller;

import com.spring.evalapi.entity.KPI;
import com.spring.evalapi.service.KPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/kpis")
public class KPIController {

    @Autowired
    private KPIService kpiService;

    @PostMapping("/profile/{profileId}")
    public ResponseEntity<KPI> addKPI(@PathVariable Long profileId, @RequestBody KPI kpi) {
        KPI savedKPI = kpiService.addKPI(profileId, kpi);
        return new ResponseEntity<>(savedKPI, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KPI> getKPIById(@PathVariable Long id) {
        return ResponseEntity.ok(kpiService.getKPIById(id));
    }

    @GetMapping("/cycle/{cycleId}")
    public ResponseEntity<List<KPI>> getKPIsByCycleId(@PathVariable Long cycleId) {
        return ResponseEntity.ok(kpiService.getKPIsByCycleId(cycleId));
    }

    @GetMapping("/profile/{profileId}")
    public ResponseEntity<List<KPI>> getKPIsByProfileId(@PathVariable Long profileId) {
        return ResponseEntity.ok(kpiService.getKPIsByProfileId(profileId));
    }

    @PutMapping
    public ResponseEntity<KPI> updateKPI(@RequestBody KPI kpi) {
        return ResponseEntity.ok(kpiService.updateKPI(kpi));
    }

    @PutMapping("/{kpiId}/cycle/{cycleId}")
    public ResponseEntity<KPI> assignKpiToCycle(@PathVariable Long kpiId, @PathVariable Long cycleId) {
        KPI updatedKPI = kpiService.assignKpiToCycle(kpiId, cycleId);
        return ResponseEntity.ok(updatedKPI);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteKPI(@PathVariable Long id) {
        return ResponseEntity.ok(kpiService.deleteKPI(id));
    }
}