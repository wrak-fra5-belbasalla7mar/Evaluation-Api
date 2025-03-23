package com.spring.evalapi.service;

import com.spring.evalapi.common.exception.CycleNotFoundException;
import com.spring.evalapi.common.exception.CycleStateException;
import com.spring.evalapi.common.exception.FieldIsRequiredException;
import com.spring.evalapi.entity.Kpi;
import com.spring.evalapi.entity.Objective;
import com.spring.evalapi.utils.CycleState;
import jakarta.validation.Valid;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.repository.CycleRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class CycleService {

    private static final Logger logger = LoggerFactory.getLogger(CycleService.class);
    private final CycleRepository cycleRepository;
    private final RatingService ratingService;

    public CycleService(CycleRepository cycleRepository, RatingService ratingService) {
        this.cycleRepository = cycleRepository;
        this.ratingService = ratingService;
    }

    @Transactional
    public Cycle addCycle(Cycle cycle) {
        if (cycle == null) {
            throw new FieldIsRequiredException("Cycle information cannot be null");
        }
        Cycle newCycle = new Cycle(
                cycle.getName(),
                cycle.getStartDate(),
                cycle.getEndDate(),
                CycleState.OPEN,
                cycle.getKpis()
        );
        if (cycle.getKpis() != null) {
            for (Kpi kpi : cycle.getKpis()) {
                kpi.setCycle(newCycle);
            }
        }
        return cycleRepository.save(newCycle);
    }

    public Cycle ViewTheLatestCycle() {
        return cycleRepository.findLatestCycle();
    }

    public List<Cycle> findAllByOrderByStartDateDesc(){
        return cycleRepository.findAllByOrderByStartDateDesc();
    }

    public List<Cycle> findAllByOrderByStartDateAsc(){
        return cycleRepository.findAllByOrderByStartDateAsc();
    }

    @Transactional
    public Cycle passCycle(Long id) {
        Optional<Cycle> cycle = cycleRepository.findById(id);
        if (cycle.isEmpty()) throw new CycleNotFoundException("No cycle found to mark as passed");
        if (cycle.get().getState() == CycleState.OPEN)
        {
            cycle.get().setState(CycleState.PASSED);
            return cycleRepository.save(cycle.get());
        }
        else throw new CycleStateException(String.format("Cycle is  : %s",cycle.get().getState()));
    }

    @Transactional
    public Cycle closeCycle(Long id) {
        Optional<Cycle> cycle = cycleRepository.findById(id);
        if (cycle.isEmpty()) {
            throw new CycleNotFoundException("No cycle found to close");
        }
        if (cycle.get().getState() == CycleState.PASSED)
        {
            cycle.get().setState(CycleState.CLOSED);
            ratingService.calculateAndStoreAverage(cycle.get().getId());
            return cycleRepository.save(cycle.get());
        }
        else throw new CycleStateException(String.format("Cycle is  : %s",cycle.get().getState()));
    }

    public Cycle cycleByID(Long id) {
        Optional<Cycle> cycle = cycleRepository.findById(id);
        if (cycle.isEmpty()) {
            throw new CycleNotFoundException(String.format("Cycle with ID %d not found", id));
        }
        return cycle.get();
    }


    @Transactional
    public String deleteCycleById(Long id){
       Optional<Cycle> cycle=cycleRepository.findById(id);
       if(cycle.isEmpty()){
           throw new CycleNotFoundException(String.format("Cycle with id : %d is not found",id));
       }
       cycleRepository.deleteById(id);
       return "Cycle deleted";
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void closeExpiredCycles() {
        Cycle expiredCycles = cycleRepository.findByEndDateBefore(new Date());
        expiredCycles.setState(CycleState.CLOSED);
        cycleRepository.save(expiredCycles);
    }


}