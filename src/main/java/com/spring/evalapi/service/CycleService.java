package com.spring.evalapi.service;

import com.spring.evalapi.dto.UserDto;
import com.spring.evalapi.exception.AccessDeniedException;
import com.spring.evalapi.exception.CycleStateException;
import com.spring.evalapi.exception.FieldIsRequiredException;
import com.spring.evalapi.exception.NotFoundException;
import com.spring.evalapi.utils.CycleState;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CycleService {

    private static final Logger logger = LoggerFactory.getLogger(CycleService.class);

    private final CycleRepository cycleRepository;
    private final RatingService ratingService;
    private final UserService userService;


    @Transactional
    public List<Cycle> getAllCycles() {
        return cycleRepository.findAll();
    }

    @Transactional
    public Cycle updateCycle(Long cycleId, Cycle updatedCycle) {
        Cycle existingCycle = cycleRepository.findById(cycleId)
                .orElseThrow(() -> new NotFoundException("Cycle not found with ID: " + cycleId));

        if (updatedCycle.getName() != null) {
            existingCycle.setName(updatedCycle.getName());
        }
        if (updatedCycle.getStartDate() != null) {
            existingCycle.setStartDate(updatedCycle.getStartDate());
        }
        if (updatedCycle.getCompanyManagerId() != null) {
            existingCycle.setCompanyManagerId(updatedCycle.getCompanyManagerId());
        }
        if (updatedCycle.getEndDate() != null) {
            existingCycle.setEndDate(updatedCycle.getEndDate());
        }
        if (updatedCycle.getState() != null) {
            existingCycle.setState(updatedCycle.getState());
        }
        if (updatedCycle.getKpis() != null) {
            existingCycle.setKpis(updatedCycle.getKpis());
            updatedCycle.getKpis().forEach(kpi -> kpi.setCycle(existingCycle));
        }
        if (updatedCycle.getObjectives() != null) {
            existingCycle.setObjectives(updatedCycle.getObjectives());
            updatedCycle.getObjectives().forEach(obj -> obj.setCycle(existingCycle));
        }

        return cycleRepository.save(existingCycle);
    }

    @Transactional
    public Cycle addCycle(Cycle cycle) {
        UserDto userDto = userService.getUserById(cycle.getCompanyManagerId());
        logger.info("Attempting to add a cycle for user with ID: {}",userDto.toString());

        if (!userDto.getRole().equals("COMPANY_MANAGER")&& !userDto.getId().equals(userDto.getManagerId())) {
            throw new AccessDeniedException("Only company managers can add a cycle");
        }
        if (cycle.getCompanyManagerId()== null) {
            throw new FieldIsRequiredException("Cycle information can't be null");
        }

        Cycle newCycle = new Cycle(
                cycle.getName(),
                cycle.getStartDate(),
                cycle.getEndDate(),
                CycleState.CREATED,
                cycle.getKpis(),
                cycle.getCompanyManagerId()
        );
        if (cycle.getKpis() != null && !cycle.getKpis().isEmpty()) {
            cycle.getKpis().forEach(kpi -> kpi.setCycle(newCycle));
        }
        return cycleRepository.save(newCycle);
    }


    @Transactional
    public Cycle ViewTheLatestCycle() {
        return cycleRepository.findLatestCycle();
    }

    @Transactional
    public List<Cycle> findAllByOrderByStartDateDesc(){
        return cycleRepository.findAllByOrderByStartDateDesc();
    }

    @Transactional
    public List<Cycle> findAllByOrderByStartDateAsc(){
        return cycleRepository.findAllByOrderByStartDateAsc();
    }


    @Transactional
    public String passCycle(Long id) {
        Optional<Cycle> cycle = cycleRepository.findById(id);
        if (cycle.isEmpty()) throw new NotFoundException("No cycle found to mark as passed");
        if (cycle.get().getState() == CycleState.OPEN)
        {
            cycle.get().setState(CycleState.PASSED);
            cycleRepository.save(cycle.get());
            return "Cycle passed ";
        }
        else throw new CycleStateException(String.format("Cycle is  : %s",cycle.get().getState()));
    }

    @Transactional
    public String closeCycle(Long id) {
        Optional<Cycle> cycle = cycleRepository.findById(id);
        if (cycle.isEmpty()) {
            throw new NotFoundException("No cycle found to close");
        }
        if (cycle.get().getState() == CycleState.PASSED)
        {
            cycle.get().setState(CycleState.CLOSED);
            ratingService.calculateAverageScores(cycle.get().getId());
            cycleRepository.save(cycle.get());
            return "Cycle closed ";
        }
        else throw new CycleStateException(String.format("Cycle is  : %s",cycle.get().getState()));
    }


    @Transactional
    public String openCycle(Long id) {
        Optional<Cycle> cycle = cycleRepository.findById(id);
        if (cycle.isEmpty()) {
            throw new NotFoundException("No cycle found to Open");
        }
        if (cycle.get().getState() == CycleState.CREATED)
        {
            cycle.get().setState(CycleState.OPEN);
            cycleRepository.save(cycle.get());
            return "cycle Opened";
        }
        else throw new CycleStateException(String.format("Cycle is  : %s",cycle.get().getState()));
    }

    @Transactional
    public Cycle cycleByID(Long id) {
        Optional<Cycle> cycle = cycleRepository.findById(id);
        if (cycle.isEmpty()) {
            throw new NotFoundException(String.format("Cycle with ID %d not found", id));
        }
        return cycle.get();
    }


    @Transactional
    public String deleteCycleById(Long id){
       Optional<Cycle> cycle=cycleRepository.findById(id);
       if(cycle.isEmpty()){
           throw new NotFoundException(String.format("Cycle with id : %d is not found",id));
       }
       cycleRepository.deleteById(id);
       return "Cycle deleted";
    }



    @Scheduled(cron = "0 0 0 * * ?")
    public void OpenCyclesScheduled() {
        Cycle expiredCycles = cycleRepository.findByEndDateBefore(new Date());
        expiredCycles.setState(CycleState.OPEN);
        cycleRepository.save(expiredCycles);
    }
    @Scheduled(cron = "0 0 0 * * ?")
    public void closeCyclesScheduled() {
        Cycle expiredCycles = cycleRepository.findByStartDate(new Date());
        expiredCycles.setState(CycleState.CLOSED);
        cycleRepository.save(expiredCycles);
    }



}