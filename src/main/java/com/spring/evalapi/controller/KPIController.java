package com.spring.evalapi.controller;


import com.spring.evalapi.entity.KPI;
import com.spring.evalapi.service.KPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kpis")
public class KPIController {
    @Autowired
    private  KPIService kpiService;
    @GetMapping("/cycle/{cycleId}")
    public ResponseEntity<List<KPI>> getKPIsByCycleId(@PathVariable long cycleId) {
        return ResponseEntity.ok(kpiService.getKPIsByCycleId(cycleId));
    }

    @GetMapping("/kpiProfile/{kpiProfileId}")
    public ResponseEntity<List<KPI>> getKPIsByKPIProfileId(@PathVariable long kpiProfileId) {
        return ResponseEntity.ok(kpiService.getKPIsByKPIProfileId(kpiProfileId));
    }



}
