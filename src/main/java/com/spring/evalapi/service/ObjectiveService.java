package com.spring.evalapi.service;


import com.spring.evalapi.common.exception.CycleNotFoundException;
import com.spring.evalapi.common.exception.CycleNotOpenException;
import com.spring.evalapi.common.exception.ObjectiveForUserNotFound;
import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.entity.Objective;
import com.spring.evalapi.repository.CycleRepository;
import com.spring.evalapi.repository.ObjectiveRepository;
import com.spring.evalapi.utils.CycleState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ObjectiveService {

//    private static final Logger logger = LoggerFactory.getLogger(CycleService.class);

    private final ObjectiveRepository objectiveRepository;
    private final CycleRepository cycleRepository;


    public ObjectiveService( ObjectiveRepository objectiveRepository1, CycleRepository cycleRepository) {
        this.objectiveRepository = objectiveRepository1;
        this.cycleRepository = cycleRepository;
    }

    public List<Objective> viewObjectiveForCycle(){
        Cycle cycle = cycleRepository.findLatestCycle();
        if (cycle == null || cycle.getState() != CycleState.OPEN) {
            throw new CycleNotOpenException("No open cycle found! Please ensure an open cycle exists to reach to the objectives");
        }
       return objectiveRepository.findAll();
    }

    @Transactional
    public List<Objective> assignObjective(List<Objective> objectives){
        Cycle cycle = cycleRepository.findLatestCycle();
        if (cycle == null || cycle.getState() != CycleState.OPEN) {
            throw new CycleNotOpenException("No open cycle found! Please ensure an open cycle exists before assigning objectives.");
        }
        objectives.forEach(objective -> objective.setCycle(cycle));
        return objectiveRepository.saveAll(objectives);
    }

    public Objective findByAssignId(int id){
        Objective objective = objectiveRepository.findByAssignedUserId(id);
        if (objective == null) {
            throw new ObjectiveForUserNotFound("User with ID: " + id + " is not found");
        }
        return objective;
    }

    @Transactional
    public String deleteByAssignId(int id) {
        Objective objective = objectiveRepository.findByAssignedUserId(id);
        if (objective == null) {
            throw new ObjectiveForUserNotFound(String.format("User with ID: %d is not found", id));
        }
        objectiveRepository.delete(objective);
        return String.format("Objective for user with ID: %d has been successfully deleted", id);
    }

    @Transactional
    public Objective UpdateByAssignId(int id , Objective updateObjective){
        Objective objective = objectiveRepository.findByAssignedUserId(id);
        if (objective == null) {
            throw new ObjectiveForUserNotFound(String.format("User with ID: %d is not found", id));
        }
        if (updateObjective.getTitle() != null) {
            objective.setTitle(updateObjective.getTitle());
        }
        if (updateObjective.getAssignedUserId() > 0) {
            objective.setAssignedUserId(updateObjective.getAssignedUserId());
        }
        if (updateObjective.getDescription() != null) {
            objective.setDescription(updateObjective.getDescription());
        }
        if (updateObjective.getDeadline() != null) {
            objective.setDeadline(updateObjective.getDeadline());
        }
        return objectiveRepository.save(objective);
    }

}
