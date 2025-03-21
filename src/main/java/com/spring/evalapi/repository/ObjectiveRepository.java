package com.spring.evalapi.repository;

import com.spring.evalapi.entity.Objective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Repository
public interface ObjectiveRepository extends JpaRepository<Objective,Integer> {
    Optional<Objective> findByAssignedUserId(Long assignedUserId);

}
