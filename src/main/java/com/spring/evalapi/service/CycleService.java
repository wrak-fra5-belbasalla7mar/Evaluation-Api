package com.spring.evalapi.service;

import com.spring.evalapi.common.exception.CycleNotFoundException;
import com.spring.evalapi.common.exception.CycleStateException;
import com.spring.evalapi.entity.KPI;
import com.spring.evalapi.entity.Objective;
import com.spring.evalapi.repository.KPIRepository;
import com.spring.evalapi.utils.CycleState;
import com.sun.source.tree.NewArrayTree;
import org.springframework.transaction.annotation.Transactional;
import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.repository.CycleRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;
@Service
public class CycleService {

    private static final Logger logger = LoggerFactory.getLogger(CycleService.class);

    private final CycleRepository cycleRepository;
    public CycleService(CycleRepository cycleRepository) {
        this.cycleRepository = cycleRepository;
    }
    @Transactional
    public Cycle addCycle(Cycle cycle) {
        if (cycle == null) {
            throw new IllegalArgumentException("Cycle information cannot be null");
        }
        Cycle newCycle = new Cycle(
                cycle.getName(),
                cycle.getStartDate(),
                cycle.getEndDate(),
                CycleState.CREATED,
                cycle.getKpis()
        );
        if (cycle.getKpis() != null) {
            for (KPI kpi : cycle.getKpis()) {
                kpi.setCycle(newCycle);
            }
        }
        return cycleRepository.save(newCycle);
    }

    public Cycle ViewTheLatestCycle() {
        return cycleRepository.findLatestCycle();
    }

    @Transactional
    public Cycle openCycle() {
        Cycle cycle = cycleRepository.findLatestCycle();
        if (cycle == null) {
            throw new CycleNotFoundException("No cycle found to open");
        }
        cycle.setState(CycleState.OPEN);
        return cycleRepository.save(cycle);
    }

    @Transactional
    public Cycle passCycle() {
        Cycle cycle = cycleRepository.findLatestCycle();
        if (cycle == null) throw new CycleNotFoundException("No cycle found to mark as passed");
        if (cycle.getState() == CycleState.OPEN)
        {
            cycle.setState(CycleState.PASSED);
            return cycleRepository.save(cycle);
        }
        else throw new CycleStateException(String.format("Cycle is  : %s",cycle.getState()));
    }

    @Transactional
    public Cycle closeCycle() {
        Cycle cycle = cycleRepository.findLatestCycle();
        if (cycle == null) {
            throw new CycleNotFoundException("No cycle found to close");
        }
        if (cycle.getState() == CycleState.PASSED)
        {
            cycle.setState(CycleState.CLOSED);
            return cycleRepository.save(cycle);
        }
        else throw new CycleStateException(String.format("Cycle is  : %s",cycle.getState()));
    }

    @Transactional
    public Cycle putObjectives(List<Objective> objectives) {
        Cycle cycle = cycleRepository.findLatestCycle();
        if (cycle == null) {
            throw new CycleNotFoundException("No cycle found to add objectives");
        }
        cycle.setObjectives(objectives);
        return cycleRepository.save(cycle);
    }

    public Cycle returnCycleByID(long id) {
        Optional<Cycle> cycle = cycleRepository.findById(id);
        if (cycle.isEmpty()) {
            throw new CycleNotFoundException(String.format("Cycle with ID %d not found", id));
        }
        return cycle.get();
    }

    public String deleteCycleById(long id){
       Optional<Cycle> cycle=cycleRepository.findById(id);
       if(cycle.isEmpty()){
           throw new CycleNotFoundException(String.format("Cycle with id : %d is not found",id));
       }
       else return "Cycle deleted";
    }

    public String deleteCycleBy(long id){
        Optional<Cycle> cycle=cycleRepository.findById(id);
        if(cycle.isEmpty()){
            throw new CycleNotFoundException(String.format("Cycle with id : %d is not found",id));
        }
        else return "Cycle deleted";
    }

}