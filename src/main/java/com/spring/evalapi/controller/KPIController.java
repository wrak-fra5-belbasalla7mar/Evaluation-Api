package com.spring.evalapi.controller;

import com.spring.evalapi.entity.KPI;
import com.spring.evalapi.service.KPIService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kpis")
public class KPIController {

    private final KPIService kpiService;

    public KPIController(KPIService kpiService) {
        this.kpiService = kpiService;
    }

    @PostMapping("/profile/{profileId}")
    @PreAuthorize("hasAnyRole('TEAM_MANAGER', 'COMPANY_MANAGER')")
    public ResponseEntity<KPI> addKPI(@PathVariable Long profileId, @Valid @RequestBody KPI kpi) {
        KPI savedKPI = kpiService.addKPI(profileId, kpi);
        return new ResponseEntity<>(savedKPI, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'TEAM_MANAGER', 'COMPANY_MANAGER')")
    public ResponseEntity<KPI> getKPIById(@PathVariable Long id) {
        return ResponseEntity.ok(kpiService.getKPIById(id));
    }

    @GetMapping("/cycle/{cycleId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'TEAM_MANAGER', 'COMPANY_MANAGER')")
    public ResponseEntity<List<KPI>> getKPIsByCycleId(@PathVariable Long cycleId) {
        return ResponseEntity.ok(kpiService.getKPIsByCycleId(cycleId));
    }

    @GetMapping("/profile/{profileId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'TEAM_MANAGER', 'COMPANY_MANAGER')")
    public ResponseEntity<List<KPI>> getKPIsByProfileId(@PathVariable Long profileId) {
        return ResponseEntity.ok(kpiService.getKPIsByProfileId(profileId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEAM_MANAGER', 'COMPANY_MANAGER')")
    public ResponseEntity<KPI> updateKPI(@Valid @RequestBody KPI kpi) {
        return ResponseEntity.ok(kpiService.updateKPI(kpi));
    }

    @PutMapping("/{kpiId}/cycle/{cycleId}")
    @PreAuthorize("hasAnyRole('TEAM_MANAGER', 'COMPANY_MANAGER')")
    public ResponseEntity<KPI> assignKpiToCycle(@PathVariable Long kpiId, @PathVariable Long cycleId) {
        KPI updatedKPI = kpiService.assignKpiToCycle(kpiId, cycleId);
        return ResponseEntity.ok(updatedKPI);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEAM_MANAGER', 'COMPANY_MANAGER')")
    public ResponseEntity<String> deleteKPI(@PathVariable Long id) {
        return ResponseEntity.ok(kpiService.deleteKPI(id));
    }
}