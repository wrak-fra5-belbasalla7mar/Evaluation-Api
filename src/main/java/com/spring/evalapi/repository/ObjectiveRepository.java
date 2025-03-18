package com.spring.evalapi.repository;

import com.spring.evalapi.entity.Objective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface ObjectiveRepository extends JpaRepository<Objective,Integer> {
    Objective findByAssignedUserId(int id);
}
