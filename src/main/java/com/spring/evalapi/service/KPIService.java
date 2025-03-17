package com.spring.evalapi.service;


import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.entity.KPI;
import com.spring.evalapi.repository.CycleRepository;
import com.spring.evalapi.repository.KPIRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KPIService {

    @Autowired
    private KPIRepository kpiRepository;

    @Autowired
    private CycleRepository  cycleRepository;

    public Optional<KPI> getKPIById(long id) {
        return kpiRepository.findById(id);
    }


    public List<KPI> getKPIsByCycleId(long cycleId) {
        return kpiRepository.findByCycle_Id(cycleId);
    }


//    public List<KPI> getKPIsByKPIProfileId(long kpiProfileId) {
//        return kpiRepository.findByProfile_Id(kpiProfileId);
//    }

    public KPI addKPI(KPI kpi) {
        if (kpi.getCycle()!=null) {
            Cycle cycle = cycleRepository.findById(kpi.getCycle().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Cycle not found !"));
            kpi.setCycle(cycle);
        }
        return kpiRepository.save(kpi);
    }
    public KPI updateKPI(KPI kpi) {
        if (!kpiRepository.existsById(kpi.getId())){
            throw new IllegalArgumentException("KPI  not Found");
        }
        if (kpi.getCycle()!=null) {
            Cycle cycle = cycleRepository.findById(kpi.getCycle().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Cycle not found !"));
            kpi.setCycle(cycle);
        }
        return kpiRepository.save(kpi);
    }
    public void deleteKPI(long id) {
        if (!kpiRepository.existsById(id)){
            throw new IllegalArgumentException("KPI is Already not Found");
        }
        kpiRepository.deleteById(id);
    }

}
