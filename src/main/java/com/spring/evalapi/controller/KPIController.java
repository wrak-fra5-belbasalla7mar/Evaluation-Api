package com.spring.evalapi.controller;


import com.spring.evalapi.entity.KPI;
import com.spring.evalapi.service.KPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/kpis")
public class KPIController {
    @Autowired
    private  KPIService kpiService;
    @GetMapping("/cycle/{cycleId}")
    public ResponseEntity<List<KPI>> getKPIsByCycleId(@PathVariable Long cycleId) {
        return ResponseEntity.ok(kpiService.getKPIsByCycleId(cycleId));
    }

//    @GetMapping("/kpiProfile/{kpiProfileId}")
//    public ResponseEntity<List<KPI>> getKPIsByKPIProfileId(@PathVariable long kpiProfileId) {
//        return ResponseEntity.ok(kpiService.getKPIsByKPIProfileId(kpiProfileId));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<KPI>>getKPIById(@PathVariable Long id){
        return ResponseEntity.ok(kpiService.getKPIById(id));
    }

    @PutMapping
    public ResponseEntity<KPI>addKPI(@RequestBody KPI kpi){
        return ResponseEntity.ok(kpiService.addKPI(kpi));
    }

    @PostMapping
    public ResponseEntity<KPI>updateKPI(@RequestBody KPI kpi ){
        return ResponseEntity.ok(kpiService.updateKPI(kpi));
    }
    @DeleteMapping("/{id}")
    public void deleteKPI(@PathVariable Long id){
        kpiService.deleteKPI(id);
    }

}
