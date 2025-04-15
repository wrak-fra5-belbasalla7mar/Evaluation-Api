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
        Cycle findOpenCycle=cycleRepository.findByState(CycleState.OPEN);
        Cycle findPassCycle =cycleRepository.findByState(CycleState.PASSED);

        if (findOpenCycle !=null ||findPassCycle!=null)
            throw new IllegalStateException("Only One open cycle  be can add a cycle");

        UserDto userDto = userService.getUserById(cycle.getCompanyManagerId());
        logger.info("Attempting to add a cycle for user with ID: {}",userDto.toString());

        if (!userDto.getRole().equals("CompanyManager")&& !userDto.getId().equals(userDto.getManagerId())) {
            throw new AccessDeniedException("Only company managers can add a cycle");
        }
        if (cycle.getCompanyManagerId()== null) {
            throw new FieldIsRequiredException("Cycle information can't be null");
        }
        if(cycle.getState().equals(CycleState.OPEN))
            throw new IllegalStateException("cycle state must be open");

        Cycle newCycle = new Cycle(
                cycle.getName(),
                cycle.getStartDate(),
                cycle.getEndDate(),
                CycleState.OPEN,
                cycle.getKpis(),
                cycle.getCompanyManagerId()
        );
        if (cycle.getKpis() != null && !cycle.getKpis().isEmpty()) {
            cycle.getKpis().forEach(kpi -> kpi.setCycle(newCycle));
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
        if (cycle.isEmpty()) throw new NotFoundException("No cycle found to mark as passed");
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
            throw new NotFoundException("No cycle found to close");
        }
        if (cycle.get().getState() == CycleState.PASSED)
        {
            cycle.get().setState(CycleState.CLOSED);
            ratingService.calculateAverageScores(cycle.get().getId());
            return cycleRepository.save(cycle.get());
        }
        else throw new CycleStateException(String.format("Cycle is  : %s",cycle.get().getState()));
    }

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
    public void closeExpiredCycles() {
        Cycle expiredCycles = cycleRepository.findByEndDateBefore(new Date());
        expiredCycles.setState(CycleState.CLOSED);
        cycleRepository.save(expiredCycles);
    }


}