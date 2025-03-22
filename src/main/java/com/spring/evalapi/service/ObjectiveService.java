package com.spring.evalapi.service;
import com.spring.evalapi.common.exception.CycleNotOpenException;
import com.spring.evalapi.common.exception.ObjectiveForUserNotFound;
import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.entity.Objective;
import com.spring.evalapi.repository.CycleRepository;
import com.spring.evalapi.repository.ObjectiveRepository;
import com.spring.evalapi.utils.CycleState;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ObjectiveService {

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

    public List<Objective> assignObjective(List<Objective> objectives){
        Cycle cycle = cycleRepository.findLatestCycle();
        if (cycle == null || cycle.getState() != CycleState.OPEN) {
            throw new CycleNotOpenException("No open cycle found! Please ensure an open cycle exists before assigning objectives.");
        }
        objectives.forEach(objective -> objective.setCycle(cycle));
        return objectiveRepository.saveAll(objectives);
    }

    public Objective findByAssignId(Long id){
        Optional<Objective> objective= objectiveRepository.findByAssignedUserId(id);
        if (objective.isEmpty()) {
            throw new ObjectiveForUserNotFound("User with ID: " + id + " is not found");
        }
        return objective.get();
    }

    public String deleteByAssignId(Long id) {
        Optional<Objective> objective= objectiveRepository.findByAssignedUserId(id);
        if (objective.isEmpty()) {
            throw new ObjectiveForUserNotFound("User with ID: " + id + " is not found");
        }
        objectiveRepository.delete(objective.get());
        return String.format("Objective for user with ID: %d has been successfully deleted", id);
    }

    public Objective UpdateByAssignId(Long id , Objective updateObjective){
        Optional<Objective> objective= objectiveRepository.findByAssignedUserId(id);
        if (objective.isEmpty()) {
            throw new ObjectiveForUserNotFound("User with ID: " + id + " is not found");
        }
        if (updateObjective.getTitle() != null) {
            objective.get().setTitle(updateObjective.getTitle());
        }
        if (updateObjective.getAssignedUserId() > 0) {
            objective.get().setAssignedUserId(updateObjective.getAssignedUserId());
        }
        if (updateObjective.getDescription() != null) {
            objective.get().setDescription(updateObjective.getDescription());
        }
        if (updateObjective.getDeadline() != null) {
            objective.get().setDeadline(updateObjective.getDeadline());
        }
        return objectiveRepository.save(objective.get());
    }

}
