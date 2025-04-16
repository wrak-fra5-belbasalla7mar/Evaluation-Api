package com.spring.evalapi.service;
import com.spring.evalapi.dto.TeamDto;
import com.spring.evalapi.dto.UserDto;
import com.spring.evalapi.exception.CycleNotOpenException;
import com.spring.evalapi.exception.NotFoundException;
import com.spring.evalapi.exception.ObjectiveForUserNotFound;
import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.entity.Objective;
import com.spring.evalapi.repository.CycleRepository;
import com.spring.evalapi.repository.ObjectiveRepository;
import com.spring.evalapi.utils.CycleState;
import com.spring.evalapi.utils.ObjectiveState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ObjectiveService {

    private final ObjectiveRepository objectiveRepository;
    private final CycleRepository cycleRepository;
    private final TeamService teamService;
    private final  UserService userService;


    @Transactional
    public Objective assignObjectiveByUserId(Objective objective ) {

        UserDto user=userService.getUserById(objective.getAssignedUserId());

        if(user==null)throw new RuntimeException("user not found ID "+objective.getAssignedUserId());

        TeamDto team=teamService.getTeamId(objective.getTeamId());

        if(team ==null )throw new RuntimeException("Team for User ID not found "+objective.getAssignedUserId());
        
        if(!Objects.equals(team.getManagerId(), objective.getManagerId()))throw new RuntimeException("can,t add objective by this user "+objective.getManagerId());

        Optional<Cycle> cycle = cycleRepository.findById(objective.getCycleId());
        if (cycle.isEmpty()) {
            throw new NotFoundException(
                    String.format("Cycle with id: %d is not found", objective.getCycleId())
            );
        }

        if (cycle.get().getState() == CycleState.OPEN) {
            objective.setCycle(cycle.get());
            objective.setManagerId(team.getManagerId());
            objective.setTeamId(team.getId());
            return objectiveRepository.save(objective);
        } else {
            throw new CycleNotOpenException(String.format("Cycle is %s, can't add objective to it", cycle.get().getState()));
        }
    }

    public List<Objective> findAllByAssignedUserId(Long id){
        List<Objective> objective= objectiveRepository.findAllByAssignedUserId(id);
        if (objective.isEmpty()) {
            throw new ObjectiveForUserNotFound("User with ID: " + id + " is not have objectives");
        }
        return objective;
    }

    @Transactional
    public String deleteByAssignIdAndObjectiveId(Long userId,Long objectiveId) {
        Objective objective= objectiveRepository.findByAssignedUserIdAndId(userId,objectiveId);
        if (objective == null ) {
            throw new ObjectiveForUserNotFound("Objective  " + objectiveId + " is not found");
        }
        objectiveRepository.delete(objective);
        return String.format("objective with is  : %d  and assignId : %d is deleted", userId,objectiveId);
    }

    @Transactional
    public Objective UpdateByAssignId(Long id , Objective updateObjective){
        Objective objective= objectiveRepository.findByAssignedUserId(id);
        if (objective == null) {
            throw new ObjectiveForUserNotFound("User with ID: " + id + " is not found");
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



    @Transactional
    public Objective completeObjective(Long id){
        Objective objective= objectiveRepository.findById(id);
        if (objective == null)throw new NotFoundException(String.format("objective with id : %d not found",id));
        if(objective.getState()== ObjectiveState.OPEN)
        {
            objective.setState(ObjectiveState.COMPLETED);
            objectiveRepository.save(objective);
            return objective;
        }
        throw new NotFoundException("cant change the state of the objective");
    }

}
