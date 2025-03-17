package com.spring.evalapi.service;

import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.repository.CycleRepository;
import com.spring.evalapi.repository.KpiRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CycleService {


    private final CycleRepository cycleRepository;
    private final KpiRepository kpiRepository;

    public CycleService(CycleRepository cycleRepository, KpiRepository kpiRepository) {
        this.cycleRepository = cycleRepository;
        this.kpiRepository = kpiRepository;
    }


    @Transactional
    public Cycle addCycle(Cycle cycle) {
        Cycle newCycle = new Cycle();
        newCycle.setName(cycle.getName());
        newCycle.setEndDate(cycle.getEndDate());
        newCycle.setStartDate(cycle.getStartDate());
        newCycle.setCycleState(cycle.getCycleState());
        newCycle.setKpis(cycle.getKpis());
        return cycleRepository.save(newCycle);

    }

    public Cycle viewCycle() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        return cycleRepository.findFirstCycleClosestToNow(now);
    }

    public void deleteCycle(long id) {

        cycleRepository.deleteById(id);
    }

    public Cycle updateCycle(Cycle cycle) {
        Optional<Cycle> optionalCycle = cycleRepository.findById(cycle.getId());

        if (optionalCycle.isPresent()) {
            Cycle updatedcycle = optionalCycle.get();
            return cycleRepository.save(updatedcycle);
        }
        return cycle;
    }
}
