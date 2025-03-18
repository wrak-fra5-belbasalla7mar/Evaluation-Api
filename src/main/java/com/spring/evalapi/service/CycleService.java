package com.spring.evalapi.service;

import com.spring.evalapi.entity.Objective;
import com.spring.evalapi.utils.CycleState;
import org.springframework.transaction.annotation.Transactional;

import com.spring.evalapi.dto.NewCycleDto;
import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.entity.KPI;
import com.spring.evalapi.repository.CycleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CycleService {


    private final CycleRepository cycleRepository;
    public CycleService(CycleRepository cycleRepository) {
        this.cycleRepository = cycleRepository;
    }

    @Transactional
    public Cycle addCycle(NewCycleDto cycleDto) {
        Cycle newCycle = new Cycle();
            newCycle.setName(cycleDto.getName());
            newCycle.setEndDate(cycleDto.getEndDate());
            newCycle.setStartDate(cycleDto.getStartDate());
            newCycle.setState(CycleState.CREATED);
            for (KPI kpi : cycleDto.getKpis()) {
                    newCycle.addKPI(kpi);
            }
            return cycleRepository.save(newCycle);
    }

    public Cycle ViewTheLatestCycle(){
        return cycleRepository.findLatestCycle();
    }

    @Transactional
    public Cycle openCycle() {
        Cycle cycle = cycleRepository.findLatestCycle();
        cycle.setState(CycleState.OPEN);
        return cycleRepository.save(cycle);
    }

    public  Cycle passCycle(){
        Cycle cycle = cycleRepository.findLatestCycle();
        cycle.setState(CycleState.PASSED);
        return cycleRepository.save(cycle);
    }

    @Transactional
    public Cycle closeCycle() {

        Cycle cycle = cycleRepository.findLatestCycle();
        cycle.setState(CycleState.CLOSED);
        return cycleRepository.save(cycle);
    }

    public Cycle putObjectives(List<Objective> objectives){
        Cycle cycle =cycleRepository.findLatestCycle();
        cycle.setObjectives(objectives);
        return cycleRepository.save(cycle);
    }


}
