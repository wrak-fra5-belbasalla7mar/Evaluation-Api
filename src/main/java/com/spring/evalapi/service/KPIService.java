package com.spring.evalapi.service;


import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.entity.KPI;
import com.spring.evalapi.repository.CycleRepository;
import com.spring.evalapi.repository.KpiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KPIService {

    @Autowired
    private KpiRepository kpiRepository;

    @Autowired
    private CycleRepository  cycleRepository;

    public Optional<KPI> getKPIById(long id) {
        return kpiRepository.findById(id);
    }


    public List<KPI> getKPIsByCycleId(long cycleId) {
        return kpiRepository.findByCycle_Id(cycleId);
    }


    public List<KPI> getKPIsByKPIProfileId(long kpiProfileId) {
        return kpiRepository.findByKpiProfile_Id(kpiProfileId);
    }

    public KPI addKPI(KPI kpi) {
        return kpiRepository.save(kpi);
    }
    public KPI updateKPI(KPI kpi) {
        return kpiRepository.save(kpi);
    }
    public void deleteKPI(long id) {
        kpiRepository.deleteById(id);
    }

}
