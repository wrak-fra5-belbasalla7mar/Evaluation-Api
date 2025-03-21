package com.spring.evalapi.service;

import com.spring.evalapi.common.exception.CycleNotFoundException;
import com.spring.evalapi.common.exception.KpiAlreadyAssignedException;
import com.spring.evalapi.common.exception.RatingNotFoundException;
import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.entity.Kpi;
import com.spring.evalapi.repository.CycleRepository;
import com.spring.evalapi.repository.KpiRepository;
import com.spring.evalapi.utils.CycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class KPIService {

    @Autowired
    private KpiRepository kpiRepository;

    @Autowired
    private CycleRepository cycleRepository;

    public Kpi getKpiById(Long id) {
        return kpiRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException("KPI with ID " + id + " not found"));
    }

    public List<Kpi> getKPIsByCycleId(Long cycleId) {
        return kpiRepository.findByCycle_Id(cycleId);
    }


    public Kpi addKPI( Kpi kpi) {
        return kpiRepository.save(kpi);
    }

    public Kpi updateKPI(Kpi kpiDetails) {
        if (!kpiRepository.existsById(kpiDetails.getId())) {
            throw new RatingNotFoundException("KPI with ID " + kpiDetails.getId() + " not found");
        }

        Kpi existingKPI = kpiRepository.findById(kpiDetails.getId())
                .orElseThrow(() -> new RatingNotFoundException("KPI with ID " + kpiDetails.getId() + " not found"));

        if (kpiDetails.getName() != null && !kpiDetails.getName().isEmpty()) {
            existingKPI.setName(kpiDetails.getName());
        }

        return kpiRepository.save(existingKPI);
    }

    public Kpi assignKpiToCycle(Long kpiId, Long cycleId) {
        Kpi kpi = kpiRepository.findById(kpiId)
                .orElseThrow(() -> new RatingNotFoundException("KPI with ID " + kpiId + " not found"));
        Cycle cycle = cycleRepository.findById(cycleId)
                .orElseThrow(() -> new CycleNotFoundException("Cycle with ID " + cycleId + " not found"));

        kpi.setCycle(cycle);
        return kpiRepository.save(kpi);
    }

    public String deleteKPI(Long id) {
        if (!kpiRepository.existsById(id)) {
            throw new RatingNotFoundException("KPI with ID " + id + " not found");
        }
        kpiRepository.deleteById(id);
        return "KPI was Deleted Successfully!";
    }
}