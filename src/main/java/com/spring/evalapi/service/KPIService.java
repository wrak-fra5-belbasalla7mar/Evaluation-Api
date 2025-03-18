package com.spring.evalapi.service;

import com.spring.evalapi.common.exception.CycleNotFoundException;
import com.spring.evalapi.common.exception.KpiAlreadyAssignedException;
import com.spring.evalapi.common.exception.KpiNotFoundException;
import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.entity.KPI;
import com.spring.evalapi.repository.CycleRepository;
import com.spring.evalapi.repository.KPIRepository;
import com.spring.evalapi.utils.CycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KPIService {

    @Autowired
    private KPIRepository kpiRepository;

    @Autowired
    private CycleRepository cycleRepository;

    public Optional<KPI> getKPIById(Long id) {
        return kpiRepository.findById(id);
    }

    public List<KPI> getKPIsByCycleId(Long cycleId) {
        return kpiRepository.findByCycle_Id(cycleId);
    }

    public KPI addKPI(KPI kpi) {
        return kpiRepository.save(kpi);
    }

    public KPI updateKPI(KPI kpiDetails) {
        if (!kpiRepository.existsById(kpiDetails.getId())) {
            throw new KpiNotFoundException("KPI with ID " + kpiDetails.getId() + " not found");
        }

        KPI existingKPI = kpiRepository.findById(kpiDetails.getId())
                .orElseThrow(() -> new KpiNotFoundException("KPI with ID " + kpiDetails.getId() + " not found"));

        if (kpiDetails.getName() != null && !kpiDetails.getName().isEmpty()) {
            existingKPI.setName(kpiDetails.getName());
        }
        if (kpiDetails.getWeights() != null && !kpiDetails.getWeights().isEmpty()) {
            existingKPI.setWeights(kpiDetails.getWeights());
        }

        return kpiRepository.save(existingKPI);
    }

    public KPI assignKpiToCycle(Long kpiId, Long cycleId) {
        KPI kpi = kpiRepository.findById(kpiId)
                .orElseThrow(() -> new KpiNotFoundException("KPI with ID " + kpiId + " not found"));
        Cycle cycle = cycleRepository.findById(cycleId)
                .orElseThrow(() -> new CycleNotFoundException("Cycle with ID " + cycleId + " not found"));

        if (kpi.getCycle() != null && kpi.getCycle().getCycleState() == CycleState.OPEN) {
            throw new KpiAlreadyAssignedException("KPI is already assigned to an open cycle");
        }

        kpi.setCycle(cycle);
        return kpiRepository.save(kpi);
    }

    public String deleteKPI(Long id) {
        if (!kpiRepository.existsById(id)) {
            throw new KpiNotFoundException("KPI with ID " + id + " not found");
        }
        kpiRepository.deleteById(id);
        return "KPI was Deleted Successfully!";
    }
}